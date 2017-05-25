package com.JavaClub.controller.credit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.Interface.BaseInfoMapService;
import com.JavaClub.util.Const;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.google.gson.Gson;

/**
 * 
 * @descript 地图
 * @author 李坡
 * @createTime 2016年9月22日上午10:35:44
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/baseInfoMapController")
public class BaseInfoMapController extends BaseController {

	@Autowired
	public BaseInfoMapService baseInfoMapService;

	/**
	 * 
	 * @descript 跳转到查询地图坐标界面
	 * @author 李坡
	 * @since 2016年9月22日上午10:35:53
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toQueryBaseInfoList")
	public ModelAndView toQueryBaseInfoList() throws Exception {
		ModelAndView mv = new ModelAndView();

		mv.addObject("C_COUNTY_CN", Const.C_COUNTY_CN);
		mv.addObject("C_CITY_CN", "南昌市");
		mv.addObject("C_C_CN", "新建区");
		mv.setViewName("enterprise/BaseInfoMap");

		return mv;
	}

	/**
	 * 
	 * @descript 查询地图坐标
	 * @author 李坡
	 * @since 2016年9月22日上午10:36:00
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryBaseInfoList", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryBaseInfoList() throws Exception {
		PageData pd = new PageData();
		pd=this.getPageData();
		Gson gson = new Gson();
		if(StringUtil.isEmpty(pd.getString("C_COUNTY")) ){
			pd.put("C_COUNTY", Const.C_COUNTY);
		}
		
		// 查询区域领取牌照的企业
		List<PageData> list = baseInfoMapService.queryBaseInfoList(pd);

		return gson.toJson(list);
	}

	/**
	 * 
	 * @descript 根据pripid查询企业详情
	 * @author 李坡
	 * @since 2016年9月22日上午10:36:08
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findBaseInfoById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findBaseInfoById() throws Exception {
		PageData pd = this.getPageData();
		Gson gson = new Gson();

		PageData pp = baseInfoMapService.findBaseInfoById(pd);

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
	 * @author 李文海
	 * @since 2016年10月20日上午9:28:45
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryCityList", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryCityList() throws Exception {
		PageData pd = new PageData();
		pd=this.getPageData();
		
		List<PageData> cityList = baseInfoMapService.queryCityList(pd);
		Gson gson = new Gson();
		
		return gson.toJson(cityList);
	}
	
	/**
	 * 
	 * @descript (统计某个区域有多少牌照未领取)
	 * @author 李文海
	 * @since 2016年10月20日上午9:28:45
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryCount", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryCount() throws Exception {
		PageData pd = new PageData();
		pd=this.getPageData();
		if(StringUtil.isEmpty(pd.getString("C_CODE")) ){
			pd.put("C_CODE", Const.C_COUNTY);
		}
		if(StringUtil.isEmpty(pd.get("C_AREA_LEVEL").toString()) ){
			pd.put("C_AREA_LEVEL", 2);
		}
		
		PageData resultPd=new PageData();
		
		//查询某个地区下的所有子地区   如查江西省的所有市
		List<PageData> cityList = baseInfoMapService.queryCityList(pd);
		
		//地区数组
		String[] name=new String[cityList.size()];
		//数量数组
		int[] count=new int[cityList.size()];
				
		for(int i=0;i<cityList.size();i++){
			name[i]=cityList.get(i).getString("C_NAME");
			//统计该地区有多少个牌照未领取
			count[i]=baseInfoMapService.queryCount(cityList.get(i));
		}
		
		resultPd.put("name", name);
		resultPd.put("count", count);
		Gson gson = new Gson();
		return gson.toJson(resultPd);
	}
	
}