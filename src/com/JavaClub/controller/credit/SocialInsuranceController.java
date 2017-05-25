package com.JavaClub.controller.credit;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.credit.SocialInsuranceService;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (社保信息分析)
 * @author 魏旋
 * @createTime 2017年1月10日上午11:16:14
 * @version 1.0
 */
@Controller
@RequestMapping("/socialInsuranceController")
public class SocialInsuranceController extends BaseController {

	@Autowired
	SocialInsuranceService socialInsuranceService;
	/**
	 * 
	 * @descript 社保信息分析
	 * @author 余思
	 * @since 2017年5月22日下午5:06:37
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryInsuranceInfo", produces = "text/html;charset=UTF-8")
	public ModelAndView queryInsuranceInfo() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		String SocialInsurance=socialInsuranceService.querySocialInsurance(pd);
		List<Map<String,Object>> Liquidation=socialInsuranceService.queryLiquidation(pd);
		mv.addObject("queryll", SocialInsurance.split("@")[0]);
		mv.addObject("queryl2", SocialInsurance.split("@")[1]);
		mv.addObject("queryl3", SocialInsurance.split("@")[2]);
		mv.addObject("queryl4", SocialInsurance.split("@")[3]);
		mv.addObject("map",Liquidation);
		mv.addObject("pd", pd);
		mv.setViewName("insurance/insurance_info");
		return mv;
	}
}
