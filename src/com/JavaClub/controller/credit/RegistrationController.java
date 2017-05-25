package com.JavaClub.controller.credit;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.credit.RegistrationService;
import com.JavaClub.util.PageData;

@Controller
@RequestMapping("/registrationController")
public class RegistrationController extends BaseController{
	@Autowired
	RegistrationService registrationService;
	
	/**
	 * 
	 * @descript (用一句话描述改方法的作用)
	 * @author 魏旋
	 * @since 2017年2月15日上午10:59:53
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/regstateinfo",produces="text/html;charset=UTF-8")
	public ModelAndView regstateinfo() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		
		List<Map<String, Object>> regstate  = registrationService.queryRegstate(pd);
		List<Map<String, Object>> Countylist  = registrationService.queryCounty(pd);
		
		String pj="";		
		for (int j = 0; j < regstate.size(); j++) {
			
			pj += "['"+regstate.get(j).get("REGSTATE")+"("+regstate.get(j).get("REGSTATE_COUNT")+"家)',"+regstate.get(j).get("REGSTATE_COUNT")+"],";
		}
		
	
		mv.addObject("queryll", pj);
		mv.addObject("countylist", Countylist);
		mv.addObject("pd", pd);
		mv.setViewName("registration/registration_info");
		return mv;
	}

}
