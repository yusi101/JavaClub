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
import com.JavaClub.service.credit.ClaimService;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.PageData;
import com.JavaClub.util.UuidUtil;

/**
 * 
 * @descript 认领审核控制器
 * @author 龚志强
 * @createTime 2016年9月26日上午9:23:02
 * @version 1.0
 */
@Controller
@RequestMapping(value="/claimController")
public class ClaimController  extends BaseController{
	
	//认领业务层
	@Autowired
	public ClaimService claimService;
	
	/**
	 * 
	 * @descript 查询全部的认领审核
	 * @author 龚志强
	 * @since 2016年9月26日上午9:33:22
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryClaimApplylistPage")
	@SuppressWarnings("unchecked")
	public ModelAndView queryQRCodeRecordlistPage(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//设置分页
		page.setPd(pd);
		//获取全部的认领审核
		List<Map<String,Object>> claimList = claimService.queryClaimApplylistPage(page);
		
		//设置返回视图和数据
		mv.addObject("pd", pd);
		mv.addObject("claimList", claimList);
		mv.setViewName("claim/claim_list");
		
		return mv;
	}
	
	/**
	 * 
	 * @descript 查询某条认领审核
	 * @author 龚志强
	 * @since 2016年9月26日上午11:01:16
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryClaimById")
	@SuppressWarnings("unchecked")
	public ModelAndView queryClaimById() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//获取某个的认领审核
		Map<String,Object> claimMap = ListUtils.getListMap(claimService.queryClaimById(pd), 0);
		List<Map<String,Object>> attachmentList = claimService.queryAttachmentById(pd);
		
		//设置返回视图和数据
		mv.addObject("pd", pd);
		mv.addObject("claimMap", claimMap);
		mv.addObject("attachmentList", attachmentList);
		//0为审核，1为详情
		if("0".equals(pd.getString("openType"))){
			mv.setViewName("claim/claim_deal");
		}else{
			mv.setViewName("claim/claim_det");
		}
		
		return mv;
	}

	/**
	 * 
	 * @descript 修改认领审核信息
	 * @author 龚志强
	 * @since 2016年9月26日上午11:31:37
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateClaim", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateClaim(HttpServletRequest request)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();	
		
		//判断当前用户是否为空
		if(null != getUser()){
			//获取当前用户ID和用户名
			pd.put("uid", getUser().getUSER_ID());
			pd.put("uname", getUser().getNAME());	
		}
		pd.put("rid", UuidUtil.get32UUID());	//设置管理审核主键ID
		pd.put("type", "认领企业");				//设置管理审核类型	
		
		//添加申请牌照
		String flag = claimService.updateClaim(pd);

		return flag;
	}
}
