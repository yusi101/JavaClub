package com.JavaClub.controller.credit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.credit.OperatingyearsService;
import com.JavaClub.service.credit.RegistrationService;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (经营年限分析)
 * @author 余思
 * @createTime 2017年2月15日下午3:23:25
 * @version 1.0
 */
 @Controller
 @RequestMapping(value = "/operatingyearsController")
public class OperatingyearsController extends BaseController{

	 @Autowired
	 OperatingyearsService operatingyearsService;
	 @Autowired
	 RegistrationService registrationService;
	/**
	 * 
	 * @descript (用一句话描述改方法的作用)
	 * @author 余思
	 * @since 2017年2月15日下午3:23:46
	 * @return
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 @RequestMapping(value = "/queryOperatingyears", produces = "text/html;charset=UTF-8")
	public ModelAndView queryOperatingyears() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		List<Map<String, Object>> Countylist  = registrationService.queryCounty(pd);
		List<Map<String,Object>> listpage =operatingyearsService.queryOperatingyears(pd);
		int sum1=0,sum2=0,sum3=0;
		if(ListUtils.isNotEmpty(listpage)){
			for (Map<String, Object> map : listpage) {
				if(map.get("ESTDATE") !=null){//判断成立日期是否为空
					if(map.get("CANDATE") !=null){//判断注销日期是否为空
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
						Date d1=sdf.parse(map.get("ESTDATE").toString());
						Date d2=sdf.parse(map.get("CANDATE").toString());
						long daysBetween=(d2.getTime()-d1.getTime()+1000000)/(3600*24*1000);
						if(daysBetween/365 <10){
							sum1++;
						}else if(daysBetween/365 >=10 && daysBetween/365 <20){
							sum2++;
						}else if(daysBetween/365 >=20){
							sum3++;
						}
					}else{
						Date now = new Date(); 
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
						Date d1=sdf.parse(map.get("ESTDATE").toString());
						Date d2=sdf.parse(sdf.format(now));
						long daysBetween=(d2.getTime()-d1.getTime()+1000000)/(3600*24*1000);
						if(daysBetween/365 <10){
							sum1++;
						}else if(daysBetween/365 >=10 && daysBetween/365 <20){
							sum2++;
						}else if(daysBetween/365 >=20){
							sum3++;
						}
					}
				}
			}
		}		
		List list =new ArrayList<>();
		list.add(sum1);
		list.add(sum2);
		list.add(sum3);
		mv.addObject("list", list);
		mv.addObject("countylist", Countylist);
		mv.addObject("pd", pd);
		
		mv.setViewName("operatingyears/operatingyears");
		return mv;
	}
	 
}
