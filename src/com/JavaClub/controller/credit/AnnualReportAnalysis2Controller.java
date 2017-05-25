package com.JavaClub.controller.credit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.credit.IndustryType2Service;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;

/**
 * @descript (年报数据分析（人员）)
 * @author 魏旋
 * @version 1.0
 */
@Controller
@RequestMapping("/annualReportAnalysis2Controller")
public class AnnualReportAnalysis2Controller extends BaseController {


	@Autowired
	IndustryType2Service industryType2Service;
	
	/**
	 * 
	 * @descript (区域人员分析)
	 * @author 魏旋
	 * @since 2017年1月9日上午8:57:27
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/areaTurnoverAnalysis",produces="text/html;charset=UTF-8")
	public ModelAndView areaTurnoverAnalysis() throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		List<Map<String, Object>> industryList = industryType2Service.queryIndustry(pd);
		List  countyList = industryType2Service.queryCounty(pd);
		List<Map<String, Object>>  countyList1 = industryType2Service.queryCounty(pd);
		List<Map<String,Object>> renList = industryType2Service.queryEmpnum(pd);
		mv.addObject("industryList",industryList);
		mv.addObject("pd", pd);
		Map<String,Object> map_data=new HashMap<>();
		
		List list_2016=new ArrayList();
		List list_2015=new ArrayList();
		List list_2014=new ArrayList();
		List list_2016_q=new ArrayList();
		List list_2015_n=new ArrayList();
		List list_2014_g=new ArrayList();
		int _2016 = 0,_2015=0,_2014=0;
		int q_2016,g_2016,n_2016,q_2015,g_2015,n_2015,q_2014,g_2014,n_2014;
		for(int i = 0; i < countyList.size(); i++){
			countyList.set(i, ""+((Map<String,Object>)countyList.get(i)).get("C_NAME")+"");
			String C_NAME =  (String) countyList1.get(i).get("C_NAME");
			_2016 = 0;_2015=0;_2014=0;
			q_2016=0;g_2016=0;n_2016=0;q_2015=0;g_2015=0;n_2015=0;q_2014=0;g_2014=0;n_2014=0;
			for (int j = 0; j < renList.size(); j++) {
				String ANCHEYEAR =  (String) renList.get(j).get("ANCHEYEAR");
				String AREA_NAME = (String) renList.get(j).get("AREA_NAME");
				String TYPE = (String) renList.get(j).get("TYPE");
				int TOTAL_EMPNUM = (int)renList.get(j).get("TOTAL_EMPNUM");
				
				if (ANCHEYEAR.equals("2015")&& AREA_NAME.equals(C_NAME)) {
					_2016 = _2016+ TOTAL_EMPNUM;
					if (TYPE.equals("1")) {//企业
						q_2016 = q_2016 + TOTAL_EMPNUM;
					}
					else if (TYPE.equals("2")) {//农专
						n_2016 = n_2016 + TOTAL_EMPNUM;
					}
					else if(TYPE.equals("3")){//个体
						g_2016 = g_2016 + TOTAL_EMPNUM;
					}
					
					
				}
				else if (ANCHEYEAR.equals("2014")&& AREA_NAME.equals(C_NAME)) {
					_2015 = _2015+TOTAL_EMPNUM;
					if (TYPE.equals("1")) {//企业
						q_2015 = q_2015 + TOTAL_EMPNUM;
					}
					else if (TYPE.equals("2")) {//农专
						n_2015 = n_2015 + TOTAL_EMPNUM;
					}
					else if(TYPE.equals("3")){//个体
						g_2015 = g_2015 + TOTAL_EMPNUM;
					}
					
				}
				else if (ANCHEYEAR.equals("2013") && AREA_NAME.equals(C_NAME)) {
					_2014 = _2014+TOTAL_EMPNUM;
					if (TYPE.equals("1")) {//企业
						q_2014 = q_2014 + TOTAL_EMPNUM;
					}
					else if (TYPE.equals("2")) {//农专
						n_2014 = n_2014 + TOTAL_EMPNUM;
					}
					else if(TYPE.equals("3")){//个体
						g_2014 = g_2014 + TOTAL_EMPNUM;
					}
				}
			}
			list_2016.add(_2016);
			list_2015.add(_2015);
			list_2014.add(_2014);
			list_2016_q.add(q_2016+"|"+n_2016+"|"+g_2016);
			list_2015_n.add(q_2015+"|"+n_2015+"|"+g_2015);
			list_2014_g.add(q_2014+"|"+n_2014+"|"+g_2014);
		}
		map_data.put("dataAxis", countyList.toArray());
		map_data.put("list_2014", list_2014);
		map_data.put("list_2015", list_2015);
		map_data.put("list_2016", list_2016);
		map_data.put("list_2016_q", list_2016_q);
		map_data.put("list_2015_n", list_2015_n);
		map_data.put("list_2014_g", list_2014_g);
		
		mv.addObject("data", MyGson.toJson(map_data));
		mv.setViewName("industry/areaturnoveranalysis2");
		return mv;
	}
	/**
	 * 
	 * @descript (行业营业额分析)
	 * @author 李海涛
	 * @since 2016年11月28日上午9:41:48
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/industryTurnoverAnalysis",produces="text/html;charset=UTF-8")
	public ModelAndView industryTurnoverAnalysis() throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		List<Map<String, Object>> countyList = industryType2Service.queryCounty(pd);
		mv.addObject("countyList",countyList);
		mv.addObject("pd", pd);
		Map<String,Object> map_data=new HashMap<>();
		List industryList = industryType2Service.queryIndustry(pd);
		
		List list_2016=new ArrayList();
		List list_2015=new ArrayList();
		List list_2014=new ArrayList();
		for (int i = 0; i < industryList.size(); i++) {
			industryList.set(i, ""+((Map<String,Object>)industryList.get(i)).get("EC_NAME")+"");
			list_2016.add(Integer.parseInt(StringUtil.getRandom(4))/4f);
			list_2015.add(Integer.parseInt(StringUtil.getRandom(4))/4f);
			list_2014.add(Integer.parseInt(StringUtil.getRandom(4))/4f);
		}
		map_data.put("dataAxis", industryList);
		map_data.put("list_2014", list_2014);
		map_data.put("list_2015", list_2015);
		map_data.put("list_2016", list_2016);
		
		mv.addObject("data", MyGson.toJson(map_data));
		mv.setViewName("industry/industryturnoveranalysis2");
		return mv;
	}
}
