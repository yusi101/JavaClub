package com.JavaClub.controller.credit;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.system.LogService;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (用户日志Controller)
 * @author 李文海
 * @createTime 2016年10月26日上午9:35:39
 * @version 1.0
 */
@Controller
@RequestMapping(value="/userLogController")
public class UserLogController extends BaseController{
		
	@Resource(name = "logService")
	private LogService logService;
	
	/**
	 * 
	 * @descript (查询用户操作日志)
	 * @author 李文海
	 * @since 2016年10月26日上午9:41:21
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userLoglistPage")
	public ModelAndView userLoglistPage(Page page)throws Exception{
		ModelAndView mv = new  ModelAndView("system/log/user_log_list");
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		
		List<PageData> userLogList = logService.userLoglistPage(page);
		
		mv.addObject("pd", pd);
		mv.addObject("userLogList", userLogList);
		return mv;
	}
	
	/**
	 * 
	 * @descript (根据id查看用户操作日志详情)
	 * @author 李文海
	 * @since 2016年10月26日上午9:56:54
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryUserLogById")
	public ModelAndView queryUserLogById()throws Exception{
		ModelAndView mv = new  ModelAndView("system/log/user_log_query");
		PageData pd = new PageData();
		pd = this.getPageData();
		
		PageData userLogPd = logService.queryUserLogById(pd);
		
		mv.addObject("userLogPd", userLogPd);
		return mv;
	}
	
	/**
	 * 
	 * @descript (查询数据库操作日志)
	 * @author 吕永青
	 * @since 2016年10月26日上午10:35:00
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDatabaseLoglistPage")
	public ModelAndView queryDatabaseLoglistPage(Page page)throws Exception{
		ModelAndView mv = new  ModelAndView("system/log/database_log_list");
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		
		List<PageData> DatabaseLogList = logService.queryDatabaseLoglistPage(page);
		
		mv.addObject("pd", pd);
		mv.addObject("DatabaseLogList", DatabaseLogList);
		return mv;
	}
	
	/**
	 * 
	 * @descript (查询数据库操作日志详情)
	 * @author 吕永青
	 * @since 2016年10月26日上午10:36:09
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDatabaseLogById")
	public ModelAndView queryDatabaseLogById()throws Exception{
		ModelAndView mv = new  ModelAndView("system/log/database_log_query");
		PageData pd = new PageData();
		pd = this.getPageData();
		
		PageData DatabaserLogPd = logService.queryDatabaseLogById(pd);
		
		mv.addObject("DatabaserLogPd", DatabaserLogPd);
		return mv;
	}
}
