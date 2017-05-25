package com.JavaClub.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.system.User;
import com.JavaClub.service.system.LoginService;
import com.JavaClub.service.system.UserService;
import com.JavaClub.util.Const;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

@Controller
public class LoginController extends BaseController {

	@Autowired
	private LoginService loginService;
    @Autowired
	public UserService userservice;

	/**
	 * 
	 * @descript 用户登录
	 * @author 
	 * @since 2016年10月9日下午2:11:59
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user_Login", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String user_Login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();

		// 选择调用该接口必须传入的参数
		String VerificationParameter = Verification.VerificationParameter(pd, "userName@password@code");

		// 如果上面的必要参数没有，则返回参数异常
		if (!"".equals(VerificationParameter)) {
			return "error";
		}

		// 验证码为空
		if (StringUtil.isEmpty(pd.getString("code"))) {
			return "nullcode";
		}

		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String sessionCode = (String) session.getAttribute(Const.SESSION_SECURITY_CODE);

		// 获取session中的验证码
		if (StringUtil.isNotEmpty(sessionCode) && sessionCode.equalsIgnoreCase(pd.getString("code"))) {
			// 密码加密
			String passwd = new SimpleHash("SHA-1", pd.getString("userName"), pd.getString("password")).toString();
			pd.put("password", passwd);

			User user = loginService.getUserByNameAndPwd(pd);
			pd.clear();

			if (null != user) {
				user.setLAST_LOGIN(new Date());
				user.setIP(this.getRemortIP());
				
				//修改用户最近登陆时间和IP地址
				PageData userinfo = new PageData();
				userinfo.put("USERID", user.getUSER_ID());
				userinfo.put("LAST_LOGIN", user.getLAST_LOGIN());
				userinfo.put("IP", user.getIP());
				userservice.updateUserInfo(userinfo);
				
				
				session.setAttribute(Const.SESSION_USER, user);
				// 清除session中的验证码
				session.removeAttribute(Const.SESSION_SECURITY_CODE);

				// shiro加入身份验证
				Subject subject = SecurityUtils.getSubject();
				UsernamePasswordToken token = new UsernamePasswordToken(user.getUSERNAME(), user.getPASSWORD());

				try {
					subject.login(token);
					
					return "success"; // 登陆成功
				} catch (AuthenticationException e) {
					return "身份验证失败！"; // 身份验证失败！
				}
			} else {
				// 用户名或密码有误
				return "usererror";
			}
		} else {
			return "codeerror";
		}
	}

	/**
	 * 
	 * @descript 跳转到首页
	 * @author 
	 * @since 2016年10月9日下午2:05:48
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/main/index", produces = "text/html;charset=UTF-8")
	public ModelAndView system_index() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		// 角色的权限集合
		List<Map<String,Object>> authList = null;

		//获取当前用户
		if(null != getUser()){
			pd.put("uid", getUser().getUSER_ID());
		}
		
		// 判断当前用户是否是超级管理员
		if("0".equals(pd.get("uid"))){
			// 获取所有权限
			authList = loginService.queryAuth();
		}else{
			// 查询用户拥有的角色
			List<Map<String, Object>> roleList = loginService.queryRoleByUid(pd);

			// 当前用户的所有角色权限
			String auths = "";
			for(int i = 0 ; i < roleList.size(); i++){
				auths += roleList.get(i).get("RIGHTS");
			}

			String auth = "";
			if(auths.length() > 0){
				auth = auths.replaceAll("@", ",");
			}
			
			// 查询角色的权限
			authList = loginService.queryAuthByRid(auth);
		}

		mv.addObject("authList", getMenu(authList));
		mv.setViewName("index");

		return mv;
	}
	
	/**
	 * 
	 * @descript 处理菜单结构
	 * @author 龚志强
	 * @since 2016年10月14日下午4:05:13
	 * @param authList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getMenu(List<Map<String,Object>> authList){
		List<Map<String,Object>> parentList = new ArrayList<Map<String,Object>>();	//父权限集合
		List<Map<String,Object>> twoMenuList = null;		//二级菜单集合
		List<Map<String,Object>> threeMenuList = null;		//三级菜单集合

		//获取所有的父级菜单
		for(int i = 0; i < authList.size(); i++){
			if("0".equals(authList.get(i).get("PARENT_ID") + "")){
				//添加一级菜单
				parentList.add(authList.get(i));
			}
		}

		//遍历所有父级菜单
		for(int n = 0; n < parentList.size(); n++){
			twoMenuList = new ArrayList<Map<String,Object>>();

			//遍历所有菜单，添加二级菜单
			for(int m = 0; m < authList.size(); m++){
				//如果是一级菜单就下一个
				if("0".equals(authList.get(m).get("PARENT_ID") + "")){
					continue;
				}

				//添加二级菜单
				if(authList.get(m).get("PARENT_ID").equals(parentList.get(n).get("MENU_ID") + "")){
					twoMenuList.add(authList.get(m));
				}
			}	

			parentList.get(n).put("sonMenuList", twoMenuList);
		}
		
		//遍历一二级菜单，添加三级菜单
		for(int j = 0; j < parentList.size(); j++){
			List<Map<String,Object>> tMenuList = (List<Map<String,Object>>) parentList.get(j).get("sonMenuList");
			for(int k = 0; k < tMenuList.size(); k++){
				threeMenuList = new ArrayList<Map<String,Object>>();
				for(int i = 0; i < authList.size(); i++){
					if(authList.get(i).get("PARENT_ID").equals(tMenuList.get(k).get("MENU_ID") + "")){
						threeMenuList.add(authList.get(i));
					}
				}
				tMenuList.get(k).put("sonMenuList", threeMenuList);
			}
			parentList.get(j).put("sonMenuList", tMenuList);
		}

		return parentList;
	}

	/**
	 * 访问登录页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login_toLogin")
	public ModelAndView toLogin() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("SYSNAME", "sdsdsdsds"); // 读取系统名称

		mv.setViewName("");
		// mv.setViewName("data/index");
		mv.addObject("pd", pd);

		return mv;
	}

	/**
	 *
	 * @descript (用户注销)
	 * @author 李海涛
	 * @since 2016年9月14日下午2:29:10
	 * @return
	 */
	@RequestMapping(value = "/user_Logout")
	public ModelAndView user_Logout() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();

		// shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		session.removeAttribute(Const.SESSION_USER);
		// shiro销毁登录
		Subject subject = SecurityUtils.getSubject();
		subject.logout();

		pd = this.getPageData();
		String msg = pd.getString("msg");
		pd.put("msg", msg);
		mv.setViewName("index");
		mv.addObject("pd", pd);

		return mv;
	}

	/**
	 *
	 * @descript (获取登录用户的IP)
	 * @author 李海涛
	 * @since 2016年9月14日下午2:29:23
	 * @return
	 * @throws Exception
	 */
	public String getRemortIP() throws Exception {
		HttpServletRequest request = this.getRequest();
		String ip = "";
		
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}

		return ip;
	}

	/**
	 *
	 * @descript 查询单个用户信息
	 * @author 李坡
	 * @since 2016年9月21日下午7:35:13
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user_ToUpdate")
	public ModelAndView toUpdate() throws Exception {
		ModelAndView mv = new ModelAndView("system/login/user_edt");
		PageData pd = new PageData();
		pd = this.getPageData();

		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		pd.put("userid", user.getUSER_ID());

		List<Map<String, Object>> user_list = loginService.queryUser(pd);
		Map<String, Object> user_map = user_list.get(0);
		mv.addObject("pd", user_map);

		return mv;
	}

	/**
	 *
	 * @descript 修改用户信息
	 * @author 李坡
	 * @since 2016年9月21日下午8:11:39
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user_UpdateUser", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateUser() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		String flag = "error";
		
		String passwd = new SimpleHash("SHA-1", pd.getString("userName"), pd.getString("oldPASSWORD")).toString();
		
		if(passwd.equals(this.getUser().getPASSWORD())){
		    pd.put("oldPassword", passwd);
			String password = new SimpleHash("SHA-1", pd.getString("userName"), pd.getString("PASSWORD")).toString();
			pd.put("PASSWORD", password);
			
			return Verification.getResultString(loginService.updateUserInfo(pd));
		}
		return flag;
	}
    
    /**
     * 
     * @descript (到修改自己信息页面)
     * @author 汤彬
     * @since 2016年10月9日下午2:03:25
     * @return
     * @throws Exception
     */
	@ResponseBody
	@RequestMapping(value = "/queryUserById", produces = "text/html;charset=UTF-8")
	public ModelAndView queryUserById() throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
	
		pd.put("USERID", this.getUser().getUSER_ID());
		
		//查询用户数据
		List<Map<String, Object>> user_list = userservice.queryUserById(pd);	
		
		if(ListUtils.isNotEmpty(user_list)){
			Map<String, Object> user_map = user_list.get(0);
			mv.addObject("pd", user_map);
		}

		mv.setViewName("system/login/user_upd");

		return mv;
	}
	
	/**
	 * 
	 * @descript (修改登录用户自己的信息)
	 * @author 汤彬
	 * @since 2016年10月9日下午2:32:00
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserInfo", produces = "text/html;charset=UTF-8")
	public String updateUserInfo() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();

		//获取当前用户ID
		pd.put("UID", this.getUser().getUSER_ID());
		
		//修改用户
		String flag = userservice.updateUserInfo(pd);
		if(flag.equals("success")){
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			user.setNAME(pd.getString("NAME"));
			session.setAttribute(Const.SESSION_USER, user);
		}
		return flag;
	}
}
