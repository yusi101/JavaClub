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
import com.JavaClub.service.credit.BusinessMapService;
import com.JavaClub.util.Const;
import com.JavaClub.util.JsonUtils;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.google.gson.Gson;

/**
 * 
 * @descript 营业执照区域分布
 * @author 余思
 * @createTime 2017年5月19日下午2:06:19
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/businessMapController")
public class BusinessMapController extends BaseController {

	@Autowired
	public BusinessMapService businessMapService;

	/**
	 * 
	 * @descript 跳转到查询地图坐标界面
	 * @author 余思
	 * @since 2017年5月19日下午2:27:19
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toQueryBaseInfoList")
	public ModelAndView toQueryBaseInfoList() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		/*mv.addObject("C_COUNTY_CN", Const.C_COUNTY_CN);
		mv.addObject("C_CITY_CN", "南昌市");
		mv.addObject("C_C_CN", "新建区");*/
		List<Map<String,Object>> list = businessMapService.queryaddresscode(pd);
		mv.addObject("countyList",list);
		//mv.setViewName("enterprise/businessMap");
		
		mv.setViewName("enterprise/rounddotcount2");
		
		return mv;
	}

	/**
	 * 
	 * @descript  查询地图坐标
	 * @author 余思
	 * @since 2017年5月19日下午2:27:24
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryBaseInfoList", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryBaseInfoList() throws Exception {
		PageData pd = this.getPageData();
		Gson gson = new Gson();
		if(StringUtil.isEmpty(pd.getString("C_COUNTY")) ){
			pd.put("C_COUNTY", Const.C_COUNTY);
		}
		// 查询区域营业执照的企业
		List<PageData> list = businessMapService.queryBaseInfoListpd(pd);
		return gson.toJson(list);
	}

	/**
	 * 
	 * @descript 根据pripid查询企业详情
	 * @author 余思
	 * @since 2017年5月19日下午2:27:29
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findBaseInfoById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findBaseInfoById() throws Exception {
		PageData pd = this.getPageData();
		Gson gson = new Gson();
		PageData pp = businessMapService.findBaseInfoById(pd);
		if (pp != null) {
			List<PageData> list = new ArrayList<PageData>();
			list.add(pp);
			return gson.toJson(list);
		} else {
			return "error";
		}
	}
	/**
	 * 
	 * @descript 根据x y查询企业详情
	 * @author 余思
	 * @since 2017年5月19日下午2:27:29
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findBasexyInfoById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findBasexyInfoById() throws Exception {
		PageData pd = this.getPageData();
		Gson gson = new Gson();
		List<Map<String, Object>> entList = businessMapService.queryBaseInfoList(pd);
		for(Map<String, Object> list:entList){
			if(null != list.get("MAPX") && null != list.get("MAPY")){
				if(list.get("MAPX").equals(pd.getString("map[]").split(",")[0]) && list.get("MAPY").equals(pd.getString("map[]").split(",")[1])){
					pd.put("PRIPID", list.get("PRIPID"));
				}	
			}
		
		}
		PageData pp = businessMapService.findBaseInfoById(pd);
		if (pp != null) {
			List<PageData> list = new ArrayList<PageData>();
			list.add(pp);
			return gson.toJson(list);
		} else {
			return "error";
		}
	}
	/**
	 * 
	 * @descript (查询省市县)
	 * @author 余思
	 * @since 2017年5月19日下午2:27:34
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryCityList", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryCityList() throws Exception {
		PageData pd = this.getPageData();
		List<PageData> cityList = businessMapService.queryCityList(pd);
		Gson gson = new Gson();
		return gson.toJson(cityList);
	}
	
	/**
	 * 
	 * @descript 查询区县的活跃度分布情况
	 * @author 余思
	 * @since 2017年4月20日下午2:02:32
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryRoundEntactive", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryRoundDotCount() throws Exception {
		PageData pd = this.getPageData();
		if(StringUtil.isEmpty(pd.getString("C_COUNTY")) ){
			pd.put("C_COUNTY", Const.C_COUNTY);
		}
		//获取南昌市区县的企业分布状况
		List<Map<String, Object>> entList = businessMapService.queryBaseInfoList(pd);
		
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
		return JsonUtils.toJson(dataList) + "::" + entList.size() ;
	}
}