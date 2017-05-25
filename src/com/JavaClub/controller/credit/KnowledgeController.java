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
import com.JavaClub.service.credit.KnowledgeService;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript (知识库)
 * @author 魏旋
 * @createTime 2016年12月7日上午8:54:34
 * @version 1.0
 */
@Controller
@RequestMapping("/knowledgeController")
public class KnowledgeController extends BaseController{
	
	@Autowired
	public KnowledgeService knowledgeservice;
	
	/**
	 * 
	 * @descript (查询知识库信息)
	 * @author 魏旋
	 * @since 2016年12月7日上午9:24:34
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryKnowInfo", produces = "text/html;charset=UTF-8")
	public ModelAndView queryKnowInfo(Page page) throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//设置分页
		page.setPd(pd);
		
		//获取所有用户数据
		List<Map<String, Object>> know_list = knowledgeservice.queryKnowInfo(page);

		//设置返回数据和视图
		mv.addObject("pd", pd);
		mv.addObject("know_list", know_list);
		mv.setViewName("/system/knowledge/knowledge_list");
		
		return mv;
	}
	
	/**
	 * 
	 * @descript (跳转新增知识信息)
	 * @author 魏旋
	 * @since 2016年12月7日上午10:43:10
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addKnowPage")
	public ModelAndView addKnowPage(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.setViewName("/system/knowledge/knowledge_add");
		
		return mv;
	}
	
	/**
	 * 
	 * @descript (跳转知识详情信息)
	 * @author 魏旋
	 * @since 2016年12月7日上午10:43:10
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/knowDetailPage")
	public ModelAndView knowDetailPage(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List<Map<String, Object>> knowDetail =knowledgeservice.queryKnowDetailsInfo(pd);
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject("list",knowDetail);
		mv.setViewName("/system/knowledge/knowledge_details");
		
		return mv;
	}
	
	/**
	 * 
	 * @descript (跳转编辑知识信息)
	 * @author 魏旋
	 * @since 2016年12月7日上午10:43:10
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/knowEditPage")
	public ModelAndView knowEditPage(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List<Map<String, Object>> knowDetail =knowledgeservice.queryKnowDetailsInfo(pd);
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject("list",knowDetail);
		mv.setViewName("/system/knowledge/knowledge_edit");
		
		return mv;
	}
	
	
	/**
	 * 
	 * @descript (新增知识信息)
	 * @author 魏旋
	 * @since 2016年12月7日上午11:21:56
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addKnowInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addKnowInfo()throws Exception{
		PageData pd = this.getPageData();
		User user =this.getUser();
		
		List<Map<String, Object>> list = knowledgeservice.queryKnow(pd);
		
		if (!list.isEmpty()) {
			return "error2";
		}else {
			//设置角色ID
			pd.put("ID", get32UUID());
			//添加用户ID
			pd.put("CREATEUSER_ID", user.getUSER_ID());
			//添加用户
			pd.put("CREATEUSER_NAME", user.getNAME());
			
			return Verification.getResultString(knowledgeservice.insertKnowInfo(pd));
		}
		
		
	}
	
	/**
	 * 
	 * @descript (修改知识信息)
	 * @author 魏旋
	 * @since 2016年12月7日上午11:21:56
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateKnowInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateKnowInfo()throws Exception{
		PageData pd = this.getPageData();

		List<Map<String, Object>> list = knowledgeservice.queryKnow(pd);
		
		if (!list.isEmpty()) {
			String orderby = String.valueOf(list.get(0).get("ORDERBY")) ;
			if (orderby.equals(pd.get("ORDERBY"))) {
				return "error2";
			}
			else {
				return Verification.getResultString(knowledgeservice.updateKnowInfo(pd));	
			}
			
		}else {
			return Verification.getResultString(knowledgeservice.updateKnowInfo(pd));
		}
		
	}
	
	/**
	 * 
	 * @descript (删除知识信息)
	 * @author 魏旋
	 * @since 2016年12月7日上午11:21:56
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteKnowInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteKnowInfo()throws Exception{
		PageData pd = this.getPageData();

		return Verification.getResultString(knowledgeservice.deleteKnow(pd));
	}
	/**
	 * 
	 * @descript (常见问题FAQ)
	 * @author 余思
	 * @since 2016年12月12日下午4:00:34
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryKnowfaqInfo", produces = "text/html;charset=UTF-8")
	public ModelAndView queryKnowfaqInfo(Page page) throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//设置分页
		page.setPd(pd);
		
		//获取所有用户数据
		List<Map<String, Object>> know_list = knowledgeservice.queryKnowInfo(page);

		//设置返回数据和视图
		mv.addObject("pd", pd);
		mv.addObject("know_list", know_list);
		mv.setViewName("../faqbase");
		
		return mv;
	}
}
