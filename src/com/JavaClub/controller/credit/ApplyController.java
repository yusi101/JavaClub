package com.JavaClub.controller.credit;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.credit.ApplyService;
import com.JavaClub.service.credit.QRCodeService;
import com.JavaClub.util.Const;
import com.JavaClub.util.PageData;
import com.JavaClub.util.UuidUtil;

/**
 * @descript 在线牌照申请
 * @author 李坡
 * @createTime 2016年9月28日上午8:58:39
 * @version 1.0
 */
@Controller
@RequestMapping("/ApplyController")
public class ApplyController extends BaseController{

	/*在线牌子业务层*/
	@Autowired
	private ApplyService applyservice;
	
	/*牌照业务层*/
	@Resource(name = "qrCodeService")
	private QRCodeService qrCodeService;
	
	/**
	 *
	 * @descript 查询牌照在线申请
	 * @author 李坡
	 * @since 2016年9月19日上午11:16:50
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryApplyInfo", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView queryApplyInfo(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		/*获取当前用户的PRIPID*/
		pd.put("pripid", getUser().getPRIPID());
		
		/*设置分页*/
		page.setPd(pd);
		/*获取所有牌照申请*/
		List<Map<String,Object>> qrList = null;
		if(!"".equals(pd.getString("pripid")) && null != pd.getString("pripid")){
			/*获取所有牌照申请*/
			qrList = applyservice.queryApplyInfo(page);
		}
		
		/*设置返回视图和牌照申请记录*/
		mv.addObject("pd", pd);
		mv.addObject("qrList", qrList);
		mv.setViewName("apply/apply_list");
		
		return mv;
	}
	
	/**
	 *
	 * @descript 跳转添加牌照申请页面
	 * @author 龚志强
	 * @since 2016年9月18日上午11:03:21
	 * @return 返回数据和视图
	 * @throws Exception
	 */
	@RequestMapping(value = "/toCreateQRCode")
	@SuppressWarnings("unchecked")
	public ModelAndView querySysCodeInfo() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		/*获取证件类型编号*/
		List<Map<String,Object>> sysCodeList = qrCodeService.querySysCodeInfo();

		mv.addObject("pd", pd);
		mv.addObject("sysCodeList", sysCodeList);
		mv.setViewName("apply/apply_add");

		return mv;
	}
	
	/**
	 *
	 * @descript 在线申请牌照
	 * @author 李坡
	 * @since 2016年9月27日下午1:46:08
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertApply", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String insertApply(HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String flag = "";
		
		pd.put("pripid", getUser().getPRIPID());
		
		/*如果获取不到用户的企业主体ID就返回失败。*/
		if("".equals(pd.getString("pripid"))){
			return Const.FAIL;
		}
		
		/*查询企业信息。*/
		List<Map<String, Object>> list = applyservice.queryApplyInfo(pd);
		if(null != list && !list.isEmpty()){
			/*设置牌照申请信息*/
			pd.put("entname", list.get(0).get("ENTNAME"));
			pd.put("uniscid", list.get(0).get("UNISCID"));
			pd.put("pripid", list.get(0).get("PRIPID"));
			pd.put("regno", list.get(0).get("REGNORE"));
			pd.put("applyWay", 0);
			pd.put("applyWayCN", "在线申请");
			pd.put("applyId", getUser().getUSER_ID());
			pd.put("applyName", getUser().getNAME());
			pd.put("STATUS", 0);
			pd.put("aid", get32UUID());
			pd.put("astatus", 0);
			
			/*在线申请*/
			flag = applyservice.insertApply(pd, request);
		}
		
		return flag;
	}
	
	/**
	 *
	 * @descript 在线申请牌照审核
	 * @author 李坡
	 * @since 2016年9月28日上午9:43:23
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryApply", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView queryApply(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//设置分页
		page.setPd(pd);
		//获取所有牌照申请
		List<Map<String,Object>> qrList = applyservice.queryApply(page);
		
		//设置返回视图和牌照申请记录
		mv.addObject("pd", pd);
		mv.addObject("qrList", qrList);
		mv.setViewName("apply/apply_list_card");
		
		return mv;
	}

	/**
	 *
	 * @descript 查看牌照在线审核详情
	 * @author 李坡
	 * @since 2016年9月28日上午10:02:12
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryApplyById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public  ModelAndView queryApplyById() throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//查看牌照在线审核详情
		List<Map<String, Object>> list_apply = applyservice.queryApplyById(pd);
		if(null != list_apply && !list_apply.isEmpty()){
			Map<String ,Object> map = list_apply.get(0);
			mv.addObject("map", map);
		}
		
		//openType：1为审核，2为详情
		if("1".equals(pd.getString("openType"))){
			mv.setViewName("apply/apply_audit");
		}else{
			mv.setViewName("apply/apply_det");
		}
		
		return mv;
	}
	
	/**
	 *
	 * @descript 审核通过
	 * @author 龚志强
	 * @since 2016年9月28日下午1:33:50
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/okPass", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String okPass(HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String flag = "";

		//获取当前用户ID和用户名
		pd.put("uid", getUser().getUSER_ID());
		pd.put("uname", getUser().getNAME());
		pd.put("pid", UuidUtil.get32UUID());
		pd.put("id", UuidUtil.get32UUID());
		
		//牌照在线审核通过
		flag = applyservice.okPass(pd, request);
		
		return flag;
	}

	/**
	 *
	 * @descript 审核不通过
	 * @author 龚志强
	 * @since 2016年9月28日下午1:33:50
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/noPass", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String noPass(HttpServletRequest request) throws Exception{
		PageData pd = new PageData();;
		pd = this.getPageData();
		String flag = "";
		
		//获取当前用户ID和用户名
		pd.put("uid", getUser().getUSER_ID());
		pd.put("uname", getUser().getNAME());
		pd.put("id", UuidUtil.get32UUID());
		
		//牌照在线审核通过
		flag = applyservice.noPass(pd);
		
		return flag;
	}
}