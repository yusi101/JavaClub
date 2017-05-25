package com.JavaClub.controller.credit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.credit.TaxService;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;

/**
 * 
 * @descript (纳税分析)
 * @author 余思
 * @createTime 2017年1月10日上午11:03:41
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/taxController")
public class TaxController extends BaseController{

	@Autowired
	public TaxService taxService;

	/**
	 * 
	 * @descript (查询纳税总额)
	 * @author 余思
	 * @since 2017年1月10日上午11:03:54
	 * @return
	 * @throws Exception  9100 农专 9500 个体
	 * //		市场主体类型 1为企业，2为农专，3为个体
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/queryTexinfo", produces = "text/html;charset=UTF-8")
	public ModelAndView queryTexinfo() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		List<Map<String, Object>> listpages = taxService.queryTax(pd);
		List list2013 =new ArrayList<>();
		List list2014 =new ArrayList<>();
		List list2015 =new ArrayList<>();
		List list2016 =new ArrayList<>();
		if(!listpages.isEmpty()){
			for(int i=0;i<3;i++){
				list2013.add(StringUtil.clearDian(listpages.get(i).get("NUMBER").toString()));
			}
			for(int i=3;i<6;i++){
				list2014.add(StringUtil.clearDian(listpages.get(i).get("NUMBER").toString()));
			}
			for(int i=6;i<9;i++){
				list2015.add(StringUtil.clearDian(listpages.get(i).get("NUMBER").toString()));
			}
			for(int i=9;i<12;i++){
				list2016.add(StringUtil.clearDian(listpages.get(i).get("NUMBER").toString()));
			}
		}


		String yuan1 = taxService.queryTaxSum(pd);

		mv.addObject("pd", pd);
		mv.addObject("yuan", yuan1);
		mv.addObject("list2013", list2013);
		mv.addObject("list2014", list2014);
		mv.addObject("list2015", list2015);
		mv.addObject("list2016", list2016);
		mv.setViewName("tax/taxAnalysis");
		return mv;
	}
}
