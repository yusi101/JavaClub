package com.JavaClub.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.system.UserService;
import com.JavaClub.util.JsonUtils;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StrUtil;
import com.JavaClub.util.Verification;

/**
 * @descript  用户
 * @author 李坡
 * @createTime 2016年9月23日上午9:33:41
 * @version 1.0
 */
@Controller
@RequestMapping("/userController")
public class UserController extends BaseController{

	@Autowired
	public UserService userservice;

	/**
	 * 
	 * @descript 进入添加系统用户页面
	 * @author 龚志强
	 * @since 2016年9月28日下午3:44:18
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toCreateSysUser")
	@SuppressWarnings("unchecked")
	public ModelAndView toCreateSysUser()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//获取父节点JSON数据
		List<Map<String,Object>> parentList = new ArrayList<Map<String,Object>>();
		getParentZTree(userservice.queryRoleParnet(), parentList, "");
		
		mv.addObject("pd", pd);
		mv.addObject("parentList", JsonUtils.toJson(parentList));
		mv.setViewName("system/user/user_add");
		
		return mv;
	}
	/**
	 * 
	 * @descript (查询用户菜单信息)
	 * @author 李海涛
	 * @since 2016年11月14日下午4:17:09
	 * @return
	 * @throws Exception
	 */
   @ResponseBody
   @RequestMapping(value = "/queryUserRoles", produces = "text/html;charset=UTF-8")
   public ModelAndView queryUserRoles() throws Exception{
       ModelAndView mv = new ModelAndView();
       PageData pd = new PageData();
       pd = this.getPageData();
       //获取所有用户数据
       List<Map<String, Object>> user_menulist = userservice.queryUserRoleMenuInfo(pd);
       //设置返回数据和视图
       mv.addObject("pd", pd);
       mv.addObject("user_menulist", MyGson.toJson(user_menulist));
       mv.setViewName("rightmenu");
       
       return mv;
   }
	
	
	
	
	/**
	 *
	 * @descript 查询用户信息
	 * @author 李坡
	 * @since 2016年9月23日上午9:35:34
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryUserInfo", produces = "text/html;charset=UTF-8")
	public ModelAndView queryUserInfo(Page page) throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//获取当前用户ID
		if(null != getUser()){
			pd.put("UID", getUser().getUSER_ID());
		}
		
		//设置分页
		page.setPd(pd);
		//获取所有用户数据
		List<Map<String, Object>> user_list = userservice.queryUserInfo(page);

		//设置返回数据和视图
		mv.addObject("pd", pd);
		mv.addObject("user_list", user_list);
		mv.setViewName("system/user/user_list");
		
		return mv;
	}


	/**
	 *
	 * @descript 新增用户信息
	 * @author 李坡
	 * @since 2016年9月23日上午9:44:36
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertUser", produces = "text/html;charset=UTF-8")
	public String insertUser() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//返回用户名查询结果
		List<Map<String, Object>> list = userservice.queryUserByName(pd);
		
		//获取当前用户ID
		pd.put("UID", this.getUser().getUSER_ID());
		
		if(list.size() > 0){
			if (list.get(0).get("STATUS").equals("1")) {
					String passwd = new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString();
					//设置江西智容科技有限公司的PRIPID
					pd.put("PRIPID", "3601032011041300098564");
					pd.put("PASSWORD", passwd);
					String flag = userservice.updateUser(pd);
					return flag;
			}else {
				return "message";
			}
			
		}else{
			pd.put("USERID", this.get32UUID());
			String passwd = new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString();
			pd.put("PASSWORD", passwd);
			//设置江西智容科技有限公司的PRIPID
			pd.put("PRIPID", "3601032011041300098564");
			
			//添加用户名
			String flag = userservice.insertUserInfo(pd);

			return flag;
		}
	}

	/**
	 *
	 * @descript 删除用户信息
	 * @author 李坡
	 * @since 2016年9月23日上午9:45:31
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserByid", produces = "text/html;charset=UTF-8")
	public String updateUserByid() throws Exception{
		PageData pd = new PageData();
		pd=this.getPageData();
		int num = userservice.updateUserByid(pd);
		return Verification.getResultString(num);
	}
	
	/**
	 *
	 * @descript  进入修改用户页面
	 * @author 李坡
	 * @since 2016年9月23日上午10:50:03
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@ResponseBody
	@RequestMapping(value = "/queryUserById", produces = "text/html;charset=UTF-8")
	public ModelAndView queryUserById() throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//查询用户数据
		List<Map<String, Object>> user_list = userservice.queryUserById(pd);
		
		//获取父节点JSON数据
		List<Map<String,Object>> parentList = new ArrayList<Map<String,Object>>();
		getParentZTree(userservice.queryUserRoleInfo(pd), parentList, "update");
		
		if(ListUtils.isNotEmpty(user_list)){
			Map<String, Object> user_map = user_list.get(0);
			mv.addObject("pd", user_map);
		}
		
		mv.addObject("parentList", JsonUtils.toJson(parentList));
		mv.setViewName("system/user/user_edt");

		return mv;
	}

	/**
	 *
	 * @descript 修改用户信息
	 * @author 李坡
	 * @since 2016年9月23日下午12:09:38
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserById", produces = "text/html;charset=UTF-8")
	public String updateUserById() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();

		//获取当前用户ID
		pd.put("UID", this.getUser().getUSER_ID());
		
		//修改用户
		String flag = userservice.updateUserById(pd);
		
		return flag;
	}
	
	/**
	 * 
	 * @descript 设置父角色数据
	 * @author 龚志强
	 * @since 2016年10月14日下午4:07:46
	 * @param list
	 * @param parentList
	 * @param flag
	 */
	public void getParentZTree(List<Map<String,Object>> list, List<Map<String,Object>> parentList, String flag){
		Map<String,Object> mapdata = null;
		
		for(int i = 0; i < list.size(); i++){
			//修改
			if(!"".equals(flag) && null != flag){
				if(null != list.get(i).get("USER_ID")){
					mapdata = setNode(list.get(i).get("ROLE_ID"), list.get(i).get("PARENT_ID"), list.get(i).get("ROLE_NAME"), false, true);	
				}else{
					mapdata = setNode(list.get(i).get("ROLE_ID"), list.get(i).get("PARENT_ID"), list.get(i).get("ROLE_NAME"), false, false);	
				}
			//添加
			}else{
				mapdata = setNode(list.get(i).get("ROLE_ID"), list.get(i).get("PARENT_ID"), list.get(i).get("ROLE_NAME"), false, false);	
			}
			parentList.add(mapdata);
		}
	}
	
	/**
	 * 
	 * @descript 设置树节点
	 * @author 龚志强
	 * @since 2016年9月20日上午11:36:44
	 * @param id 节点ID
	 * @param pId 父节点ID
	 * @param name 节点名称
	 * @param open 节点是否打开
	 * @param checked 节点是否选中
	 * @return
	 */
	public Map<String,Object> setNode(Object id, Object pId, Object name, boolean open, boolean checked){
		Map<String,Object> pd = new HashMap<String,Object>();
		
		pd.put("id", StrUtil.delNull(id));
		pd.put("pId", StrUtil.delNull(pId));
		pd.put("name", StrUtil.delNull(name));
		pd.put("open", open);
		pd.put("checked", checked);
		
		return pd;
	}
}