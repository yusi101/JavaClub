package com.JavaClub.controller.credit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.credit.IndustryTypeService;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;

/**
 * 外商投资来源
 * @descript 查询企业所有的股东人地区
 * @author 龚志强
 * @createTime 2016年11月25日下午5:05:55
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/foreignInvestmentController")
public class ForeignInvestmentController extends BaseController {
	
	@Autowired
	IndustryTypeService industryTypeService;

	/**
	 * 
	 * @descript 跳转外商投资来源
	 * @author 龚志强
	 * @since 2016年11月23日下午5:09:42
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@RequestMapping(value = "/creadForeignInvestmentPage", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView creadRoundDotPage() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//查询外商来源数据
		List<Map<String, Object>> fiList = industryTypeService.queryForeignInvestment(null);
		
		//设置目的地节点
		List fiPoint = new ArrayList();
		for(int i = 0; i < fiList.size(); i++){
			Map<String, Object> fiPointMap = new HashMap<String, Object>(1);
			fiPointMap.put("name", fiList.get(i).get("name"));
			fiPoint.add(fiPointMap);
		}
		
		//设置目的地连线
		List fiLink = new ArrayList();
		for(int i = 0; i < fiList.size(); i++){
			List fiLists = new ArrayList();
			Map<String, Object> fiLinkMap = new HashMap<String, Object>(1);
			fiLinkMap.put("name", "江西省");
			
			fiLists.add(fiLinkMap);
			fiLists.add(fiList.get(i));
			fiLink.add(fiLists);
		}
		
		//设置返回视图和数据
		mv.addObject("pd", pd);
		mv.addObject("fiList", fiList);
		mv.addObject("fiLink", MyGson.toJson(fiLink));
		mv.addObject("fiPoint", MyGson.toJson(fiPoint));
		mv.setViewName("industry/foreignInvestment");
		
		return mv;
	}
	
}