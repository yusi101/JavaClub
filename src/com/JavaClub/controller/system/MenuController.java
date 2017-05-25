package com.JavaClub.controller.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.system.MenuService;
import com.JavaClub.util.FileUtil;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;
import com.google.gson.Gson;

/**
 * @descript (系统菜单)
 * @author 李海涛
 * @createTime 2016年9月16日下午4:28:00
 * @version 1.0
 */
@Controller
@RequestMapping(value = "menuController")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "/queryMenuList", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView queryMenuList() throws Exception {
		ModelAndView mv = this.getModelAndView();
		/* User user=this.getUser(); */
		List<Map<String, Object>> menuList = menuService.queryAllParentMenu();
		mv.addObject("menuList", menuList);
		mv.setViewName("system/menu/menu_list");
		return mv;
	}

	/**
	 * 
	 * @descript (获取当前菜单的所有子菜单)
	 * @author 李海涛
	 * @since 2016年9月16日下午5:11:56
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySubMenuByParentId", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String querySubMenuByParentId() throws Exception {
		PageData pd = this.getPageData();
		List<Map<String, Object>> subMenu = menuService.querySubMenuByParentId(pd);
		return MyGson.toJson(subMenu);
	}

	/**
	 * 
	 * @descript (跳转到添加菜单页面)
	 * @author 李海涛
	 * @since 2016年9月18日上午11:14:21
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/toCreateMenu", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView toCreateMenu() throws Exception {
		ModelAndView mv = new ModelAndView("system/menu/menu_add");
		List<Map<String, Object>> menuList = menuService.queryAllMenu(null);
		Gson g = new Gson();
		String json = g.toJson(menuList);
		String result = "[{ MENU_ID:0, PARENT_ID:0, MENU_NAME:'顶级菜单',MENU_TYPE:'0', open:true},"
				+ json.substring(1, json.length() - 1) + "]";
		mv.addObject("menuList", result);
		mv.addObject("menuIconList", FileUtil
				.getFiles(this.getRequest().getSession().getServletContext().getRealPath("/static/images/menuIcon")));
		return mv;
	}

	/**
	 * 
	 * @descript (增加菜单)
	 * @author 李海涛
	 * @since 2016年9月18日下午3:03:35
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createMenu", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String createMenu() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("MENU_TYPE", Integer.parseInt(pd.getString("PARENT_TYPE")) + 1);
		
		return Verification.getResultString(menuService.insertMenu(pd));
	}

	/**
	 * 
	 * @descript (跳转到修改菜单页面)
	 * @author 李海涛
	 * @since 2016年9月18日上午11:14:21
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/toUpdateMenu", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView toUpdateMenu() throws Exception {
		ModelAndView mv = new ModelAndView("system/menu/menu_edit");
		PageData pd = new PageData();
		Gson g = new Gson();
		String menuTree = "";
		pd = this.getPageData();
		// 得到所有的菜单信息
		pd.put("checkId", pd.getString("parentId"));
		if ("0".equals(pd.getString("parentId"))) {
			menuTree = "[{ \"MENU_ID\":0, \"PARENT_ID\":0, \"MENU_NAME\":'顶级菜单',\"MENU_TYPE\":'0', open:true,checked:1},";
		} else {
			menuTree = "[{ \"MENU_ID\":0, \"PARENT_ID\":0, \"MENU_NAME\":'顶级菜单',\"MENU_TYPE\":'0', open:true},";
		}
		List<Map<String, Object>> queryAllMenu = menuService.queryAllMenu(pd);

		// 得到将要修改的菜单信息
		Map<String, Object> queryMenu = ListUtils.getListMap(menuService.queryMenuByMenuId(pd), 0);

		String json = g.toJson(queryAllMenu);
		menuTree += json.substring(1, json.length() - 1) + "]";
		mv.addObject("menuTree", menuTree);
		mv.addObject("queryMenu", queryMenu);
		mv.addObject("menuIconList", FileUtil.getFiles(this.getRequest().getSession().getServletContext().getRealPath("/static/images/menuIcon")));
		return mv;
	}

	/**
	 * 
	 * @descript (修改菜单)
	 * @author 李海涛
	 * @since 2016年9月18日下午3:03:35
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateMenu", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateMenu() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("MENU_TYPE", Integer.parseInt(pd.getString("PARENT_TYPE")) + 1);
		return Verification.getResultString(menuService.updateMenuByMenuId(pd));
	}

	/**
	 * 
	 * @descript (删除菜单)
	 * @author 李海涛
	 * @since 2016年9月18日下午3:03:35
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteMenu", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deleteMenu() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		return menuService.deleteMenuByMenuId(pd);
	}
}