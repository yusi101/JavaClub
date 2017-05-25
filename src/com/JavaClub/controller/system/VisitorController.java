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
import com.JavaClub.service.system.VisitorService;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript (访问记录控制器)
 * @author 汤彬
 * @createTime 2016年10月11日下午2:05:05
 * @version 1.0
 */
@Controller
@RequestMapping(value="/visitorController")
public class VisitorController extends BaseController {

	@Resource(name = "visitorService")
	private VisitorService visitorService;
	
	/**
	 * 
	 * @descript (查询访问记录)
	 * @author 汤彬
	 * @since 2016年10月11日下午2:01:38
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryVisitorlistPage")
	public ModelAndView queryVisitorlistPage(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//设置分页
		page.setPd(pd);
		//获取所有访客记录
		List<Map<String,Object>> visitorList = visitorService.queryVisitorlistPage(page);
		
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject("visitorList", visitorList);
		mv.setViewName("visitor/visitor_list");
		
		return mv;
	}
	
	
	
	/**
	 * 
	 * @descript (跳到修改访问记录页面)
	 * @author 汤彬
	 * @since 2016年10月11日下午2:05:31
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toqueryVisitor")
	public ModelAndView toqueryVisitor(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//设置分页
		page.setPd(pd);
		//获取当前访客记录信息
		List<Map<String,Object>> visitorList = visitorService.queryVisitorlistPage(page);
		
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject("visitorList", visitorList);
		mv.setViewName("visitor/visitor_upd");
		
		return mv;
	}
	
	
	/**
	 * 
	 * @descript (添加访问记录)
	 * @author 汤彬
	 * @since 2016年10月11日下午2:01:11
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createVisitor",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String createVisitor()throws Exception{
		PageData pd = this.getPageData();
		User user =this.getUser();
		
		//设置访问记录ID
		pd.put("ID", get32UUID());
		//添加记录人ID
		pd.put("createUser_ID", user.getUSER_ID());
		//添加记录人
		pd.put("createUser_Name", user.getNAME());
		
		return Verification.getResultString(visitorService.createVisitor(pd));
	}
	
	/**
	 * 
	 * @descript (删除访问记录)
	 * @author 汤彬
	 * @since 2016年10月11日下午2:00:51
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteVisitor",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteVisitor()throws Exception{
		PageData pd = this.getPageData();
		return Verification.getResultString(visitorService.deleteVisitor(pd));
	}
	
	/**
	 * 
	 * @descript (修改访问记录)
	 * @author 汤彬
	 * @since 2016年10月11日下午2:06:09
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateVisitor",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateVisitor()throws Exception{
		PageData pd = this.getPageData();
		return Verification.getResultString(visitorService.updateVisitor(pd));
	}
}
