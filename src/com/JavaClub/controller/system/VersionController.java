package com.JavaClub.controller.system;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.entity.system.User;
import com.JavaClub.service.Interface.VersionService;
import com.JavaClub.util.Connect;
import com.JavaClub.util.Const;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * @descript app上传查询
 * @author 李坡
 * @createTime 2016年9月18日下午5:39:32
 * @version 1.0
 */
@Controller
@RequestMapping("/VersionController")
public class VersionController extends BaseController{
	public static final String APP_PATH = "c://"; //APP上传路径
	
	@Autowired
	public VersionService versionService;

	/**
	 *
	 * @descript app上传查询
	 * @author 李坡
	 * @since 2016年9月18日下午5:41:25
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryVersionInfo", produces = "text/html;charset=UTF-8")
	public ModelAndView queryVersionInfo() throws Exception{
		ModelAndView mv=new ModelAndView("version/app_list");
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//状态0：有效,1：无效
		pd.put("state", "0");
		//设置接口的加密
		Verification.EncodeKeyNo(pd, "name");
		//设置分页的页码和每页显示的条数
		Verification.setPageParameter(pd);
		//调用查询app上传接口
		Map<String, Object> queryBrandInfo = Connect.sendConnectByPdToMap("Interface/VersionInterface/queryVersionInfo", pd, "POST");
		Verification.DecodeKeyNo(pd, "name");
		//判断接口调用是否成功
		if(Verification.StatusIsSuccess(queryBrandInfo)){
			//得到商标信息JSON数据中的data数据
			@SuppressWarnings("unchecked")
			Map<String,Object> dataMap_version = (Map<String, Object>) queryBrandInfo.get("data");
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> version = (List<Map<String, Object>>) dataMap_version.get("VersionInfo");
			for(int i = 0 ; i <version.size(); i ++){
				String type=version.get(i).get("TYPE").toString();
				if("".equals(type)||null==type){
					type= "";
				}else{
					if("1".equals(type)){
						type= "江西企业客户端";
					}else if("2".equals(type)){
						type= "IOS客户端";
					}else if("3".equals(type)){
						type= "警示系统客户端";
					}else if("4".equals(type)){
						type= "全国企业客户端";
					}else if("5".equals(type)){
						type= "新建区系统客户端";
					}else{
						type= "其他";
					}
				}
				version.get(i).put("TYPES", type);
			}
			mv.addObject("versionInfo",version);
			//分页的拼接
			Page page=Verification.getPage(dataMap_version);
			mv.addObject("page", page);
		}
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 *
	 * @descript (修改app上传的的状态)
	 * @author 李坡
	 * @since 2016年9月19日上午9:17:00
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateVersionById", produces = "text/html;charset=UTF-8")
	public String updateVersionById() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		
		org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
		org.apache.shiro.session.Session session = currentUser.getSession();
		
		User user = (User)session.getAttribute(Const.SESSION_USER);
		pd.put("USERNAME", user.getNAME());
		pd.put("USERID", user.getUSER_ID());
		
		String flag = Verification.getResultString(versionService.updateVersionById(pd));
		
		return flag;
	}

	/**
	 *
	 * @descript 添加app信息
	 * @author 李坡
	 * @since 2016年9月19日上午11:06:36
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertVersion", produces = "text/html;charset=UTF-8")
	public String insertVersion()throws Exception {
		PageData	pd=new PageData();
		pd=this.getPageData();
		org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
		org.apache.shiro.session.Session session = currentUser.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("USERNAME", user.getNAME());
		pd.put("USERID", user.getUSER_ID());
		pd.put("ID", this.get32UUID());
		int i=versionService.insertVersion(pd);
		System.out.println(Verification.getResultString(i));
		return Verification.getResultString(i);
	}

	/**
	 *
	 * @descript 单条查询
	 * @author 李坡
	 * @since 2016年9月19日下午5:06:57
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryVersionById", produces = "text/html;charset=UTF-8")
	public ModelAndView queryVersionById() throws Exception{
		ModelAndView mv=new ModelAndView("version/app_edt");
		PageData pd=new PageData();
		pd=this.getPageData();
		List<Map<String, Object>> version= versionService.queryVersionById(pd);
		Map<String, Object> version_map=version.get(0);
		mv.addObject("pd", version_map);
		return mv;
	}

	/**
	 *
	 * @descript 单条修改
	 * @author 李坡
	 * @since 2016年9月19日下午5:53:56
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updatesVersionById", produces = "text/html;charset=UTF-8")
	public String updatesVersionById() throws Exception{
		org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
		org.apache.shiro.session.Session session = currentUser.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		PageData	pd=this.getPageData();
		pd.put("USERNAME", user.getNAME());
		pd.put("USERID", user.getUSER_ID());
		int i= versionService.updatesVersionById(pd);
		return Verification.getResultString(i);
	}


	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		}
		return str.trim().length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

}
