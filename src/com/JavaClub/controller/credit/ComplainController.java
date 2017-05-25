package com.JavaClub.controller.credit;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.credit.ComplainService;
import com.JavaClub.util.PageData;
import com.JavaClub.util.UuidUtil;

/**
 * 
 * @descript 投诉审核控制器
 * @author 龚志强
 * @createTime 2016年9月26日上午9:23:02
 * @version 1.0
 */
@Controller
@RequestMapping(value="/complainController")
public class ComplainController extends BaseController{
	
	//投诉业务层
	@Autowired
	public ComplainService complainService;
	
	/**
	 * 
	 * @descript 查询全部的投诉审核
	 * @author 龚志强
	 * @since 2016年9月26日上午9:33:22
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryComplainApplylistPage")
	@SuppressWarnings("unchecked")
	public ModelAndView queryComplainApplylistPage(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//设置分页
		page.setPd(pd);
		//获取全部的投诉审核
		List<Map<String,Object>> complainList = complainService.queryComplainApplylistPage(page);
		
		//设置返回视图和数据
		mv.addObject("pd", pd);
		mv.addObject("complainList", complainList);
		mv.setViewName("complain/complain_list");
		
		return mv;
	}
	
	/**
	 * 
	 * @descript 查询某个投诉审核
	 * @author 龚志强
	 * @since 2016年9月26日上午11:01:16
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryComplainById")
	@SuppressWarnings("unchecked")
	public ModelAndView queryComplainById() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//获取某个的投诉审核
		Map<String,Object> complainMap = complainService.queryComplainById(pd);
		List<Map<String,Object>> attachmentList = complainService.queryAttachmentById(pd);
		
		//设置返回视图和数据
		mv.addObject("pd", pd);
		mv.addObject("complainMap", complainMap);
		mv.addObject("attachmentList", attachmentList);
		//0为审核，1为详情
		if("0".equals(pd.getString("openType"))){
			mv.setViewName("complain/complain_deal");
		}else{
			mv.setViewName("complain/complain_det");
		}
		
		return mv;
	}

	/**
	 * 
	 * @descript 修改投诉审核信息
	 * @author 龚志强
	 * @since 2016年9月26日上午11:31:37
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateComplain", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateComplain(HttpServletRequest request)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();	
		
		//判断当前用户是否为空
		if(null != getUser()){
			//获取当前用户ID和用户名
			pd.put("uid", getUser().getUSER_ID());
			pd.put("uname", getUser().getNAME());	
		}
		//获取管理审核主键ID
		pd.put("rid", UuidUtil.get32UUID());
		//设置管理审核类型
		pd.put("type", "企业投诉");		
		
		//添加申请牌照
		String flag = complainService.updateComplain(pd);

		return flag;
	}
}
