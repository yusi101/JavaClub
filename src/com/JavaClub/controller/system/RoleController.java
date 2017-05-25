package com.JavaClub.controller.system;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.entity.system.User;
import com.JavaClub.service.system.RoleService;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

import net.sf.json.JSONArray;

/**
 * 
 * @descript (角色-权限控制器)
 * @author 汤彬
 * @createTime 2016年9月23日上午11:21:33
 * @version 1.0
 */
@Controller
@RequestMapping(value="/roleController")
public class RoleController extends BaseController {

	@Resource(name = "roleService")
	private RoleService roleService;
	
	/**
	 * 
	 * @descript (角色信息查询)
	 * @author 汤彬
	 * @since 2016年9月23日上午11:44:10
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryRolelistPage")
	public ModelAndView queryRolelistPage(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//设置分页
		page.setPd(pd);
		//获取所有角色信息
		List<Map<String,Object>> roleList = roleService.queryRolelistPage(page);
		
		//遍历roleList
		for (Map<String, Object> roles : roleList)  {
		    pd.put("MENU_IDS", roles.get("RIGHTS").toString().replaceAll("@", ","));
		    List<Map<String,Object>> menuList = roleService.queryMenu(pd);
		    String menu_name = ""; //权限名称拼接字符串
		    for (int i = 0; i < menuList.size(); i++) {
		        menu_name+=menuList.get(i).get("MENU_NAME")+",";
            }
		    roles.put("RIGHTS", menu_name.subSequence(0, menu_name.length()>1 ? menu_name.length()-1 : 0));
			
	  
	    }  
		
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		mv.setViewName("role/role_list");
		
		return mv;
	}
	
	
	/**
	 * 
	 * @descript (查询所有权限)
	 * @author 汤彬
	 * @since 2016年9月23日下午2:28:28
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMenu")
	public ModelAndView queryMenu()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
	
		//获取所有权限信息
		List<Map<String,Object>> MenuList = roleService.queryMenu(pd);
		//将获取的集合转为JSON格式
		String Menujson = JSONArray.fromObject(MenuList).toString(); 
		
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject("MenuList", MenuList);
		mv.addObject("Menujson",Menujson);
		mv.setViewName("role/role_add");
		
		return mv;
	}
	
	
	/**
	 * 
	 * @descript (跳转编辑页面)
	 * @author 汤彬
	 * @since 2016年9月23日下午5:45:54
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toqueryRole")
	public ModelAndView toqueryRole(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String menu_name="";
		
		//设置分页
		page.setPd(pd);
		//获取角色信息
		Map<String,Object> role = ListUtils.getListMap(roleService.queryRolelistPage(page), 0);
		pd.put("MENU_IDS", role.get("RIGHTS").toString().replaceAll("@", ","));
		
		//得到所有权限，已有的权限的checked问1，否则为0
		List<Map<String,Object>> roleMenu = roleService.queryRoleMenu(pd);
        
        for (int i = 0; i < roleMenu.size(); i++) {//MENU_NAME
            menu_name+= "1".equals(roleMenu.get(i).get("checked").toString()) ? roleMenu.get(i).get("MENU_NAME")+",":"";
        }
        role.put("RIGHTSNAME", menu_name.subSequence(0, menu_name.length()>1 ? menu_name.length()-1 : 0));
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject("role", role);
		mv.addObject("roleMenu", MyGson.toJson(roleMenu));
		mv.setViewName("role/role_upd");
		
		return mv;
	}
	
	
	/**
	 * 
	 * @descript (添加角色)
	 * @author 汤彬
	 * @since 2016年9月23日下午4:41:57
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createRole",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String createRole()throws Exception{
		PageData pd = this.getPageData();
		User user =this.getUser();
		
		//设置角色ID
		pd.put("role_Id", get32UUID());
		//添加用户ID
		pd.put("createUser_Id", user.getUSER_ID());
		//添加用户
		pd.put("createUser_Name", user.getNAME());
		
		return Verification.getResultString(roleService.createRole(pd));
	}
	
	/**
	 * 
	 * @descript (删除角色)
	 * @author 汤彬
	 * @since 2016年9月23日下午5:20:51
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteRole",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteRole()throws Exception{
		PageData pd = this.getPageData();
		return Verification.getResultString(roleService.deleteRole(pd));
	}
	
	/**
	 * 
	 * @descript (修改角色)
	 * @author 汤彬
	 * @since 2016年9月27日下午12:05:13
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateRole",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateRole()throws Exception{
		PageData pd = this.getPageData();
		return Verification.getResultString(roleService.updateRole(pd));
	}
}
