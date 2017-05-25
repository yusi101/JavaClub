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
import com.JavaClub.util.JsonUtils;
import com.JavaClub.util.PageData;

/**
 * 创业地图
 * @descript 分析南昌市行业分布情况，提取出具有商业价值的信息
 * @author 龚志强
 * @createTime 2016年11月25日下午5:05:55
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/industryTypeController")
public class IndustryTypeController extends BaseController {
	
	@Autowired
	IndustryTypeService industryTypeService;

	/**
	 * 
	 * @descript 跳转投资地图
	 * @author 龚志强
	 * @since 2016年11月23日下午5:09:42
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/creadRoundDotPage", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView creadRoundDotPage() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List<Map<String, Object>> countyList = industryTypeService.queryCounty(null);
		List<Map<String, Object>> industryList = industryTypeService.queryIndustry(null);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		
		mv.addObject("pd", pd);
		mv.addObject("countyList", countyList);
		mv.addObject("industryList", industryList);
		mv.addObject("points", JsonUtils.toJson(dataList));
		mv.setViewName("industry/rounddotcount");
		
		return mv;
	}
	
	/**
	 * 查询区县的行业分布情况
	 * @descript (用一句话描述改方法的作用)
	 * @author 龚志强
	 * @since 2016年11月25日下午5:04:48
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryRoundDotCount", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryRoundDotCount() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//获取南昌市区县的行业分布状况
		List<Map<String, Object>> entList = industryTypeService.queryTypeEnt(pd);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		
		//处理百度地图所有数据结构
		for(int i = 0; i < entList.size(); i++){
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Map<String, Object> dataMap2 = new HashMap<String, Object>();
			
			dataMap2.put("type", "Point");
			//判断数据中xy轴是否存在，不存在就调用百度接口创建xy轴
			if(null == entList.get(i).get("MAPX") || null == entList.get(i).get("MAPY")){
				continue;
//				//调用百度接口创建xy轴
//				Map<String,Double> map = LatAndLngUtil.getLngAndLat(entList.get(i).get("DOM") + "");
//				//判断是否调用百度接口成功，不成功就忽略这个
//				if(map.isEmpty()){
//					continue;
//				}
//				//修改xy轴
//				dataMap2.put("coordinates", new double[]{map.get("lng"), map.get("lat")});
			}else{
				dataMap2.put("coordinates", new double[]{Double.valueOf(entList.get(i).get("MAPX") + "") , Double.valueOf(entList.get(i).get("MAPY") + "")});
			}
			dataMap.put("geometry", dataMap2);
			dataList.add(dataMap);
		}
		
		return JsonUtils.toJson(dataList) + "::" + entList.size();
	}
}