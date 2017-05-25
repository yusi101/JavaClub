package com.JavaClub.controller.credit;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.credit.ConsumerService;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (消费者投诉举报统计)
 * @author 余思
 * @createTime 2017年3月16日下午1:41:16
 * @version 1.0
 */
 @Controller
 @RequestMapping(value = "/consumerController")
public class ConsumerController extends BaseController{

	 @Autowired
	 ConsumerService consumerService;
	
	/**
	 * 
	 * @descript 消费者投诉举报统计
	 * @author 余思
	 * @since 2017年3月16日下午1:41:40
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/queryConsumerinfo", produces = "text/html;charset=UTF-8")
	public ModelAndView queryEmployee() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		
		List<Map<String,Object>> listpage =consumerService.queryConsumerinfo(pd);
		String yuan="";
		String yuanw="";
		if(ListUtils.isNotEmpty(listpage)){
			for(Map<String,Object> map:listpage){
				yuan +="{value:"+map.get("COUNTS")+",name:\'"+map.get("REGNOTYPE")+"\'},";
				yuanw +="\'"+map.get("REGNOTYPE")+"\',";
			}
		}
		// {value:335, name:'直接访问'},
		mv.addObject("listpages", listpage);
		mv.addObject("pd", pd);
		mv.addObject("yuan", yuan);
		mv.addObject("yuanw", yuanw);
		
		mv.setViewName("consumer/consumerinfo");
		return mv;
	}
	 
}
