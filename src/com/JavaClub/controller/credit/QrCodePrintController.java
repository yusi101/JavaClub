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
import com.JavaClub.service.credit.QRCodeService;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.PageData;
import com.JavaClub.util.UuidUtil;

/**
 * @descript 牌照管理中的打印
 * @author 李海涛
 * @createTime 2016年10月14日下午5:17:28
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/qrCodePrintController")
public class QrCodePrintController extends BaseController {

	// 牌照业务层
	@Autowired
	private QRCodeService qrCodeService;

	/**
	 *
	 * @descript 打印回执单
	 * @author 李海涛
	 * @since 2016年9月19日上午11:16:50
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/printApplyReceipt", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView printApplyReceipt(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		
		page.setPd(pd);
		Map<String, Object> entMap = ListUtils.getListMap(qrCodeService.queryQRCodeInfo(page) , 0);
		if(null == entMap){
			mv.setViewName("qrcode/not_receipt");
		}else{
			mv.setViewName("qrcode/qrcode_apply_receipt");
		}
		
		mv.addObject("pd", pd);
		mv.addObject("entMap", entMap);
		
		return mv;
	}

	/**
	 * 
	 * @descript 跳转到打印营业执照
	 * @author 龚志强
	 * @since 2016年10月18日下午5:23:43
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/printBusinessLicense", produces = "text/html;charset=UTF-8")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public ModelAndView printBusinessLicense(Page page) throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//设置分页
		page.setPd(pd);
		//获取所有牌照申请记录
		List<Map<String,Object>> qrList = qrCodeService.queryQRCodeInfo(page);
		if(null != qrList){
			mv.addObject("qrList", qrList);
		}

		//设置返回视图和牌照申请记录
		mv.addObject("pd", pd);
		mv.setViewName("qrcode/print_businesslicense2");

		return mv;
	}
	
	/**
	 *
	 * @descript 跳转到回执单
	 * @author 李海涛
	 * @since 2016年9月19日上午11:16:50
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/printReceipts", produces = "text/html;charset=UTF-8")
	public ModelAndView printReceipts(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//设置分页
		page.setPd(pd);
		//获取所有牌照申请记录
		List<Map<String,Object>> qrList = qrCodeService.queryQRCodeInfo(page);
		if(null != qrList){
			mv.addObject("qrList", qrList);
		}

		mv.addObject("pd", pd);
		mv.setViewName("qrcode/print_businesslicense");

		return mv;
	}

	/**
	 *
	 * @descript 打印营业执照
	 * @author 李海涛
	 * @since 2016年9月19日上午11:16:50
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirm_businesslicense", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView confirm_businesslicense(Page page) throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		
		// 获取牌照和牌照编码数据
		mv.addObject("QRBase64Code", qrCodeService.queryQRBase64CodeInfo(pd));
		mv.addObject("pd", pd);
		mv.setViewName("qrcode/confirm_businesslicense");
		
		return mv;
	}
	
	/**
	 * 
	 * @descript 添加牌照回执单打印记录
	 * @author 龚志强
	 * @since 2016年10月17日下午4:46:04
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertQRCodeReceipts", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String insertQRCodeReceipts(HttpServletRequest request)throws Exception{
		PageData pd = new PageData();
		pd = getPageData();

		//添加牌照打印回执单ID
		pd.put("rid", UuidUtil.get32UUID());
		//获取当前用户ID和用户名
		if(null != getUser()){
			pd.put("uid", getUser().getUSER_ID());
			pd.put("uname", getUser().getNAME());
		}
		
		//添加申请牌照
		String flag = qrCodeService.insertQRCodeReceipts(pd);

		return flag;
	}
	
	/**
	 * 
	 * @descript 添加牌照营业执照打印记录
	 * @author 龚志强
	 * @since 2016年10月17日下午5:28:52
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertBusinessLicense", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String inseertBusinessLicense(HttpServletRequest request)throws Exception{
		PageData pd = new PageData();
		pd = getPageData();

		//添加牌照打印回执单ID
		pd.put("lid", UuidUtil.get32UUID());
		//获取当前用户ID和用户名
		if(null != getUser()){
			pd.put("uid", getUser().getUSER_ID());
			pd.put("uname", getUser().getNAME());
		}
		
		//添加申请牌照
		String flag = qrCodeService.inseertBusinessLicense(pd);

		return flag;
	}
}