package com.JavaClub.controller.credit;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.entity.system.User;
import com.JavaClub.service.credit.SMSTemplateService;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * 短信模板
 * @descript (用一句话描述改方法的作用)
 * @author 魏旋
 * @createTime 2017年2月6日下午3:42:54
 * @version 1.0
 */
@Controller
@RequestMapping("/smsTemplateController")
public class SMSTemplateController extends BaseController{
	
	
	@Autowired
	public SMSTemplateService smsTemplateService;
	/**
	 * 
	 * @descript (短信模板列表)
	 * @author 魏旋
	 * @since 2017年2月6日下午3:44:06
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/templateListPage")
	public ModelAndView templateListPage(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List<Map<String, Object>> templateList =smsTemplateService.querySMSlist(pd);
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject("templateList", templateList);
		mv.setViewName("/smstemplate/smstemplate");
		
		return mv;
	}
	
	/**
	 * 
	 * @descript (跳转新增短信模板)
	 * @author 魏旋
	 * @since 2017年2月6日下午4:04:00
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addSmsTemplate")
	public ModelAndView addSmsTemplate(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.setViewName("/smstemplate/smstemplate_add");
		
		return mv;
	}
	
	/**
	 * 
	 * @descript (跳转修改短信模板)
	 * @author 魏旋
	 * @since 2017年2月7日下午2:59:31
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updataSmsTemplate")
	public ModelAndView updataSmsTemplate()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<Map<String, Object>> templateList =smsTemplateService.querySMSlist(pd);
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject("templateList", templateList);
		mv.setViewName("/smstemplate/smstemplate_edit");
		
		return mv;
	}
	
	/**
	 * 
	 * @descript (修改短信模板)
	 * @author 魏旋
	 * @since 2017年2月7日下午3:00:52
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updataSmsTemplateInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataSmsTemplateInfo()throws Exception{
		PageData pd = this.getPageData();
		
		
		
		//添加状态
		String active = (String) pd.get("STATUS");
		if (active.equals("0")) {
			pd.put("ACTIVE", "0");
		}else {
			smsTemplateService.updateActive(pd);
			pd.put("ACTIVE", "1");
		}
		
		return Verification.getResultString(smsTemplateService.updateSms(pd));
	}
	
	/**
	 * 
	 * @descript (删除短信模板)
	 * @author 魏旋
	 * @since 2017年2月7日下午3:02:16
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delSmsTemplateInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String delSmsTemplateInfo()throws Exception{
		PageData pd = this.getPageData();
		
		return Verification.getResultString(smsTemplateService.deleteSms(pd));
	}
	
	/**
	 * 
	 * @descript (新增短信模板)
	 * @author 魏旋
	 * @since 2017年2月7日上午10:45:21
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addSmsTemplateInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addSmsTemplateInfo()throws Exception{
		PageData pd = this.getPageData();
		User user =this.getUser();
		
		//设置角色ID
		pd.put("ID", get32UUID());
		//添加用户ID
		pd.put("USER_ID", user.getUSER_ID());
		
		//添加状态
		String active = (String) pd.get("STATUS");
		if (active.equals("0")) {
			pd.put("ACTIVE", "0");
		}else {
			smsTemplateService.updateActive(pd);
			pd.put("ACTIVE", "1");
		}
		
		
		return Verification.getResultString(smsTemplateService.insertSMSInfo(pd));
	}
}
