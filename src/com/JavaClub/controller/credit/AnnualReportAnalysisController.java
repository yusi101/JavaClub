/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年11月28日上午9:34:06
 * @version 1.0
 */
package com.JavaClub.controller.credit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.credit.AnnualReportAnalysisService;
import com.JavaClub.service.credit.IndustryTypeService;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;

/**
 * @descript (年报数据分析)
 * @author 李海涛
 * @createTime 2016年11月28日上午9:34:06
 * @version 1.0
 */
@Controller
@RequestMapping("/annualReportAnalysisController")
public class AnnualReportAnalysisController extends BaseController {

	private static final String TOTAL_CAPITAL = "TOTAL_CAPITAL";
	
	@Autowired
	IndustryTypeService industryTypeService;
	
	@Autowired
	AnnualReportAnalysisService annualReportAnalysisService;
	/**
	 * 
	 * @descript (区域营业额分析)
	 * @author 李海涛
	 * @since 2016年11月28日上午9:41:48
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/areaTurnoverAnalysis",produces="text/html;charset=UTF-8")
	public ModelAndView areaTurnoverAnalysis() throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd = this.getPageData();
		List<Map<String, Object>> industryList = industryTypeService.queryIndustry(pd);
		List  countyList = industryTypeService.queryCounty(pd);
		mv.addObject("industryList",industryList);
		mv.addObject("pd", pd);
		Map<String,Object> map_data=new HashMap<>();
		List<Map<String, Object>> AnnualrlinfoList;
		if(pd.get("industry") != null && !pd.get("industry").equals("")){
			AnnualrlinfoList= annualReportAnalysisService.queryAnnualrindustry(pd);
		}else{
			AnnualrlinfoList= annualReportAnalysisService.queryAnnualrlinfo(pd);
		}
		
		List list_2015=new ArrayList();
		List list_2014=new ArrayList();
		List list_2013=new ArrayList();
		
		List list_2015s=new ArrayList();
		List list_2014s=new ArrayList();
		List list_2013s=new ArrayList();
		/*市场主体类型 1为企业，2为农专，3为个体*/
		for (int i = 0; i < countyList.size(); i++) {
			countyList.set(i, ""+((Map<String,Object>)countyList.get(i)).get("C_NAME")+"");
			
			long sum1=0,sum1qy=0,sum1gt=0,sum1ny=0;
			long sum2=0,sum2qy=0,sum2gt=0,sum2ny=0;
			long sum3=0,sum3qy=0,sum3gt=0,sum3ny=0;
			
			for(int j=0;j<AnnualrlinfoList.size();j++){
				if(AnnualrlinfoList.get(j).get("AREA_NAME").toString().equals(countyList.get(i).toString()) && AnnualrlinfoList.get(j).get("ANCHEYEAR").toString().equals("2015")){
					BigDecimal account=(BigDecimal)AnnualrlinfoList.get(j).get(TOTAL_CAPITAL);
					sum1+=account.doubleValue();
					if(AnnualrlinfoList.get(j).get("TYPE").toString().equals("1")){
						BigDecimal account1=(BigDecimal)AnnualrlinfoList.get(j).get(TOTAL_CAPITAL);
						sum1qy+=account1.doubleValue();
					}else if(AnnualrlinfoList.get(j).get("TYPE").toString().equals("2")){
						BigDecimal account1=(BigDecimal)AnnualrlinfoList.get(j).get(TOTAL_CAPITAL);
						sum1ny+=account1.doubleValue();
					}else if(AnnualrlinfoList.get(j).get("TYPE").toString().equals("3")){
						BigDecimal account1=(BigDecimal)AnnualrlinfoList.get(j).get(TOTAL_CAPITAL);
						sum1gt+=account1.doubleValue();
					}
					
				}
				if(AnnualrlinfoList.get(j).get("AREA_NAME").toString().equals(countyList.get(i).toString()) && AnnualrlinfoList.get(j).get("ANCHEYEAR").toString().equals("2014")){
					BigDecimal account=(BigDecimal)AnnualrlinfoList.get(j).get(TOTAL_CAPITAL);
					sum2+=account.doubleValue();
					if(AnnualrlinfoList.get(j).get("TYPE").toString().equals("1")){
						BigDecimal account1=(BigDecimal)AnnualrlinfoList.get(j).get(TOTAL_CAPITAL);
						sum2qy+=account1.doubleValue();
					}else if(AnnualrlinfoList.get(j).get("TYPE").toString().equals("2")){
						BigDecimal account1=(BigDecimal)AnnualrlinfoList.get(j).get(TOTAL_CAPITAL);
						sum2ny+=account1.doubleValue();
					}else if(AnnualrlinfoList.get(j).get("TYPE").toString().equals("3")){
						BigDecimal account1=(BigDecimal)AnnualrlinfoList.get(j).get(TOTAL_CAPITAL);
						sum2gt+=account1.doubleValue();
					}
				}
				if(AnnualrlinfoList.get(j).get("AREA_NAME").toString().equals(countyList.get(i).toString()) && AnnualrlinfoList.get(j).get("ANCHEYEAR").toString().equals("2013")){
					BigDecimal account=(BigDecimal)AnnualrlinfoList.get(j).get(TOTAL_CAPITAL);
					sum3+=account.doubleValue();
					if(AnnualrlinfoList.get(j).get("TYPE").toString().equals("1")){
						BigDecimal account1=(BigDecimal)AnnualrlinfoList.get(j).get(TOTAL_CAPITAL);
						sum3qy+=account1.doubleValue();
					}else if(AnnualrlinfoList.get(j).get("TYPE").toString().equals("2")){
						BigDecimal account1=(BigDecimal)AnnualrlinfoList.get(j).get(TOTAL_CAPITAL);
						sum3ny+=account1.doubleValue();
					}else if(AnnualrlinfoList.get(j).get("TYPE").toString().equals("3")){
						BigDecimal account1=(BigDecimal)AnnualrlinfoList.get(j).get(TOTAL_CAPITAL);
						sum3gt+=account1.doubleValue();
					}
				}
			}
			String su1=sum1+","+sum1qy+","+sum1gt+","+sum1ny;
			String su2=sum2+","+sum2qy+","+sum2gt+","+sum2ny;
			String su3=sum3+","+sum3qy+","+sum3gt+","+sum3ny;
			list_2015.add(sum1);
			list_2014.add(sum2);
			list_2013.add(sum3);
			
			list_2015s.add(su1);
			list_2014s.add(su2);
			list_2013s.add(su3);
			
		}
		map_data.put("dataAxis", countyList.toArray());
		map_data.put("list_2015", list_2015);
		map_data.put("list_2014", list_2014);
		map_data.put("list_2013", list_2013);
		
		map_data.put("list_2015s", list_2015s);
		map_data.put("list_2014s", list_2014s);
		map_data.put("list_2013s", list_2013s);
		
		mv.addObject("data", MyGson.toJson(map_data));
		mv.setViewName("industry/areaturnoveranalysis");
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
		PageData pd = this.getPageData();
		List<Map<String, Object>> countyList = industryTypeService.queryCounty(pd);
		mv.addObject("countyList",countyList);
		mv.addObject("pd", pd);
		Map<String,Object> map_data=new HashMap<>();
		List industryList = industryTypeService.queryIndustry(pd);
		
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
		mv.setViewName("industry/industryturnoveranalysis");
		return mv;
	}
}
