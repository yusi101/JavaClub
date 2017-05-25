package com.JavaClub.controller.Interface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.Interface.WarninginfoService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript (获取预警信息)
 * @author 李海涛
 * @createTime 2016年9月22日下午8:26:28
 * @version 1.0
 */
@Controller
@RequestMapping("/Interface/warningInterface")
public class WarningInterface extends BaseController{
	
    
    @Autowired
	public WarninginfoService warninginfoService;
	
    /**
     * 
     * @descript (获取预警信息)
     * @author 李海涛
     * @since 2016年9月22日下午8:26:21
     * @param request
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value="/queryWarninginfo",produces = "text/html;charset=UTF-8")
	public String queryWarninginfo(HttpServletRequest request){
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
		try {
		    String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "pripid");
			List warningInfo = warninginfoService.queryWarningInfo(pd);
			//欠贷信息
			List<Map<String,Object>> oweLoan=(List<Map<String, Object>>) warningInfo.get(0);
			MapReplaceUtils.handleLsitMapData(oweLoan);
			resultMap.put("oweLoan", oweLoan);
			
			//欠税信息
			List<Map<String,Object>> oweTax=(List<Map<String, Object>>) warningInfo.get(1);
			MapReplaceUtils.handleLsitMapData(oweTax);
			resultMap.put("oweTax", oweTax);
			
			//欠薪信息
			List<Map<String,Object>> oweSalary=(List<Map<String, Object>>) warningInfo.get(2);
			MapReplaceUtils.handleLsitMapData(oweSalary);
			resultMap.put("oweSalary", oweSalary);
			
			//责令改正
			List<Map<String,Object>> orderCorrection=(List<Map<String, Object>>) warningInfo.get(3);
			MapReplaceUtils.handleLsitMapData(orderCorrection);
			resultMap.put("orderCorrection", orderCorrection);
			
			//证照到期
			List<Map<String,Object>> licenseExpires=(List<Map<String, Object>>) warningInfo.get(4);
			MapReplaceUtils.handleLsitMapData(licenseExpires);
			resultMap.put("licenseExpires", licenseExpires);
			
			//证照过期
			List<Map<String,Object>> licenseExpired=(List<Map<String, Object>>) warningInfo.get(5);
			MapReplaceUtils.handleLsitMapData(licenseExpired);
			resultMap.put("licenseExpired", licenseExpired);
			return MyGson.toJson(map);
		}catch(Exception e){
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));

		}

	}

}