package com.JavaClub.controller.Interface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.Interface.AnnualReportService;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;


/**
 * 
 * @descript (年报控制器)
 * @author 龚志强
 * @createTime 2016年9月22日下午6:39:47
 * @version 1.0
 */
@Controller
@RequestMapping(value="/Interface/annualReportInterface")
public class AnnualReportInterface extends BaseController{

	@Resource(name="annualreportService")
	private AnnualReportService annualreportService;
	
	private static final String BASEINFO = "baseInfo"; 
	private static final String WEBSITEINFO = "websiteInfo"; 
	private static final String LICENCEINFO = "licenceInfo"; 
	private static final String ISSHOW = "ISSHOW"; 
	private static final String ALTAF = "ALTAF"; 
	private static final String ALTBE = "ALTBE"; 
	private static final String MM = "******"; 
	private static final String UPDATEINFO = "updateInfo"; 
	
	/**
	 * 个体年报
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping(value = "/queryPbAnnualReport",produces = "text/html;charset=UTF-8")
	public String queryPbAnnualReport(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		
		Map<String,Object> map=Verification.Success();
		Map<String,Object> mapdata=new HashMap<>();
		
		try {
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
			if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "ancheId");
			
			List GetAnnualReport = annualreportService.GetPBAnnualReport(pd);
			
			//基本信息
			Map<String,Object> baseInfo = ListUtils.getListMap((List<Map<String, Object>>)GetAnnualReport.get(0), 0);
			MapReplaceUtils.handleMapData(baseInfo);
			mapdata.put(BASEINFO, baseInfo);
			
			//网站信息
			List<Map<String,Object>> websiteInfo = (List<Map<String, Object>>) GetAnnualReport.get(1);
			MapReplaceUtils.handleLsitMapData(websiteInfo);
			mapdata.put(WEBSITEINFO, websiteInfo);
			
			//行政许可
			List<Map<String,Object>> licenceInfo = (List<Map<String, Object>>) GetAnnualReport.get(2);
			MapReplaceUtils.handleLsitMapData(licenceInfo);
			mapdata.put(LICENCEINFO, licenceInfo);
			
			//修改信息
			List<Map<String,Object>> updateInfo = (List<Map<String, Object>>) GetAnnualReport.get(3);
			MapReplaceUtils.handleLsitMapData(updateInfo);
			for (int i = 0; i < updateInfo.size(); i++) {
				Map<String,Object> updateInfo_map=updateInfo.get(i);
				//如果ISSHOW为2则不现实修改前和修改后
				if(updateInfo_map.get(ISSHOW) !=null && "2".equals(updateInfo_map.get(ISSHOW))){
					updateInfo_map.put(ALTAF, MM);
					updateInfo_map.put(ALTBE, MM);
				}
				updateInfo.set(i, updateInfo_map);
			}
			mapdata.put(UPDATEINFO, updateInfo);
			
			MapReplaceUtils.handleMapData(mapdata);
			map.put("data", mapdata);
			
			return MyGson.toJson2(map);
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
	}
	
	/**
	 * 农专年报
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping(value = "/querySfcAnnualReport",produces = "text/html;charset=UTF-8")
	public String querySfcAnnualReport(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		
		Map<String,Object> map = Verification.Success();
		Map<String,Object> mapdata = new HashMap<>();
		
		try {
		    String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "ancheId");
			List GetAnnualReport = annualreportService.GetSFCAnnualReport(pd);
			
			//基本信息
			Map<String,Object> baseInfo = ListUtils.getListMap((List<Map<String, Object>>)GetAnnualReport.get(0), 0);
			MapReplaceUtils.handleMapData(baseInfo);
			mapdata.put(BASEINFO, baseInfo);
			
			//网站信息
			List<Map<String,Object>> websiteInfo = (List<Map<String, Object>>) GetAnnualReport.get(1);
			MapReplaceUtils.handleLsitMapData(websiteInfo);
			mapdata.put(WEBSITEINFO, websiteInfo);
			
			//行政许可
			List<Map<String,Object>> licenceInfo = (List<Map<String, Object>>) GetAnnualReport.get(2);
			MapReplaceUtils.handleLsitMapData(licenceInfo);
			mapdata.put(LICENCEINFO, licenceInfo);
			
			//分支机构
			List<Map<String,Object>> branchinfo = (List<Map<String, Object>>) GetAnnualReport.get(3);
			MapReplaceUtils.handleLsitMapData(branchinfo);
			mapdata.put("branchinfo", branchinfo);
			
			//修改信息
			List<Map<String,Object>> updateInfo = (List<Map<String, Object>>) GetAnnualReport.get(4);
			MapReplaceUtils.handleLsitMapData(updateInfo);
			for (int i = 0; i < updateInfo.size(); i++) {
				Map<String,Object> updateInfo_map = updateInfo.get(i);
				//如果ISSHOW为2则不现实修改前和修改后
				if(null != updateInfo_map.get(ISSHOW) && "2".equals(updateInfo_map.get(ISSHOW))){
					updateInfo_map.put(ALTAF, MM);
					updateInfo_map.put(ALTBE, MM);
				}
				updateInfo.set(i, updateInfo_map);
			}
			mapdata.put(UPDATEINFO, updateInfo);
			
			
			MapReplaceUtils.handleMapData(mapdata);
			map.put("data", mapdata);
			
			return MyGson.toJson2(map);
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
	}
	
	/**
	 * 企业年报
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping(value = "/queryEntAnnualReport",produces = "text/html;charset=UTF-8")
	public String queryEntAnnualReport(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		
		Map<String,Object> map=Verification.Success();
		Map<String,Object> mapdata=new HashMap<>();
		
		try {
		    String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "ancheId");
            
			List GetAnnualReport = annualreportService.GetENTAnnualReport(pd);
			
			//基本信息
			Map<String,Object> baseInfo = ListUtils.getListMap((List<Map<String, Object>>)GetAnnualReport.get(0), 0);
			MapReplaceUtils.handleMapData(baseInfo);
			mapdata.put(BASEINFO, baseInfo);
			
			//网站信息
			List<Map<String,Object>> websiteInfo = (List<Map<String, Object>>) GetAnnualReport.get(1);
			MapReplaceUtils.handleLsitMapData(websiteInfo);
			mapdata.put(WEBSITEINFO, websiteInfo);
			
			//股东及出资
			List<Map<String,Object>> subCapital = (List<Map<String, Object>>) GetAnnualReport.get(2);
			MapReplaceUtils.handleLsitMapData(subCapital);
			mapdata.put("subCapital", subCapital);
			
			//对外投资
			List<Map<String,Object>> forinvestMent = (List<Map<String, Object>>) GetAnnualReport.get(3);
			MapReplaceUtils.handleLsitMapData(forinvestMent);
			mapdata.put("forinvestMent", forinvestMent);
			
			//对外提供担保
			List<Map<String,Object>> forguaranteeInfo = (List<Map<String, Object>>)GetAnnualReport.get(4);
			MapReplaceUtils.handleLsitMapData(forguaranteeInfo);
			for (int i = 0; i < forguaranteeInfo.size();i++) {
				//Map<String,Object> forguaranteeInfo_map = forguaranteeInfo.get(i);
				//如果ISSHOW为2则不现实修改前和修改后
				if(null != forguaranteeInfo.get(i).get("MOREDIS") && "2".equals(forguaranteeInfo.get(i).get("MOREDIS"))){
					/*forguaranteeInfo_map.put("PRICLASECKIND", MM);
					forguaranteeInfo_map.put("GUARANPERIOD", MM);
					forguaranteeInfo_map.put("PEFPERFORM", MM);
					forguaranteeInfo_map.put("MORE", MM);
					forguaranteeInfo_map.put("GATYPE", MM);
					forguaranteeInfo_map.put("PRICLASECAM", MM);
					forguaranteeInfo_map.put("MORTGAGOR", MM);
					forguaranteeInfo_map.put("PEFPERTO", MM);*/
					forguaranteeInfo.remove(i);
					i=-1;
				}
				//forguaranteeInfo.set(i, forguaranteeInfo_map);
			}
			mapdata.put("forguaranteeInfo", forguaranteeInfo);
			
			//股权变更
			List<Map<String,Object>> alterstockInfo = (List<Map<String, Object>>)GetAnnualReport.get(5);
			MapReplaceUtils.handleLsitMapData(alterstockInfo);
			mapdata.put("alterstockInfo", alterstockInfo);
			
			//修改信息
			List<Map<String,Object>> updateInfo = (List<Map<String, Object>>) GetAnnualReport.get(6);
			MapReplaceUtils.handleLsitMapData(updateInfo);
			for (int i = 0; i < updateInfo.size(); i++) {
				Map<String,Object> updateInfo_map = updateInfo.get(i);
				//如果ISSHOW为2则不现实修改前和修改后
				if(null != updateInfo_map.get(ISSHOW) && "2".equals(updateInfo_map.get(ISSHOW))){
					updateInfo_map.put(ALTAF, MM);
					updateInfo_map.put(ALTBE, MM);
				}
				updateInfo.set(i, updateInfo_map);
			}
			mapdata.put(UPDATEINFO, updateInfo);
			
			
			MapReplaceUtils.handleMapData(mapdata);
			map.put("data", mapdata);
			
			return MyGson.toJson2(map);
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
	}
}