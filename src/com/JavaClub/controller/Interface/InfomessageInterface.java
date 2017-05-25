package com.JavaClub.controller.Interface;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.Interface.InfomessageService;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript (市场主体基本工商信息及其他信息)
 * @author 李海涛
 * @createTime 2016年9月23日上午8:24:45
 * @version 1.0
 */
@Controller
@RequestMapping(value="/Interface/infomessageInterface")
public class InfomessageInterface extends BaseController{
	@Autowired
	private InfomessageService infomessageservice;


	/**
	 * 企业或个体基本工商信息及其他信息
	 * @author 李海涛
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value = "/queryinfomessage", produces = "text/html;charset=UTF-8")
	public String queryinfomessage(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
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
            //工商基本信息
			List allBaseInfo = infomessageservice.queryAllBaseInfo(pd);
			Map<String,Object> baseInfo=ListUtils.getListMap((List<Map<String, Object>>)allBaseInfo.get(0), 0);
			MapReplaceUtils.handleMapData(baseInfo);
			resultMap.put("baseInfo", baseInfo);
			
			//自然人
			List<Map<String,Object>> partnersInfo=(List<Map<String, Object>>) allBaseInfo.get(1);
			MapReplaceUtils.handleLsitMapData(partnersInfo);
			resultMap.put("partnersInfo", partnersInfo);
			
			//主要人员
			List<Map<String,Object>> employeesInfo=(List<Map<String, Object>>) allBaseInfo.get(2);
			MapReplaceUtils.handleLsitMapData(employeesInfo);
			resultMap.put("employeesInfo", employeesInfo);
			
			//变更信息
			List<Map<String,Object>> changeRecordsInfo=(List<Map<String, Object>>) allBaseInfo.get(3);
			MapReplaceUtils.handleLsitMapData(changeRecordsInfo);
			for (int i = 0; i < changeRecordsInfo.size(); i++) {
			    Map<String, Object> listChangemap=changeRecordsInfo.get(i);
			    if("投资人(股权)变更".equals(listChangemap.get("ALTITEM_CN"))){
			        String altaf = listChangemap.get("ALTAF").toString();
			        String altbe = listChangemap.get("ALTBE").toString();
                    String newaltaf="";
                    String newaaltbe="";
			        String[] altafString = altaf.split(";");
			        String[] altbeString = altbe.split(";"); 
                    for (int j = 0; j < altafString.length; j++) {
                        newaltaf+="投资人："+altafString[j].split(",")[0]+"  ";
                    }
                    for (int j = 0; j < altbeString.length; j++) {
                        newaaltbe+="投资人："+altbeString[j].split(",")[0]+"  ";
                    }
                    listChangemap.put("ALTAF", newaltaf);
                    listChangemap.put("ALTBE", newaaltbe);
			    }else if("注册资本(金)变更".equals(listChangemap.get("ALTITEM_CN"))){
                    listChangemap.put("ALTAF", StringUtil.subZeroAndDot(listChangemap.get("ALTAF").toString()));
                    listChangemap.put("ALTBE", StringUtil.subZeroAndDot(listChangemap.get("ALTBE").toString()));
                }else if("更换统一社会信用代码".equals(listChangemap.get("ALTITEM_CN"))){
                    listChangemap.put("ALTAF",listChangemap.get("ALTAF"));
                    listChangemap.put("ALTBE",listChangemap.get("ALTBE"));
                }else{
			        listChangemap.put("ALTAF", StringUtil.replace(listChangemap.get("ALTAF").toString()));
                    listChangemap.put("ALTBE", StringUtil.replace(listChangemap.get("ALTBE").toString()));
			    }
			    
			    changeRecordsInfo.set(i, listChangemap);
            }
			resultMap.put("changeRecordsInfo", changeRecordsInfo);
			
			//分支机构
			List<Map<String,Object>> annualReportsInfo=(List<Map<String, Object>>) allBaseInfo.get(4);
			MapReplaceUtils.handleLsitMapData(annualReportsInfo);
			resultMap.put("annualReportsInfo", annualReportsInfo);
			
			//清算信息
			List<Map<String,Object>>  liqMbrInfo=(List<Map<String, Object>>)allBaseInfo.get(5);
			MapReplaceUtils.handleLsitMapData(liqMbrInfo);
			resultMap.put("liqMbrInfo", liqMbrInfo);
			
			return MyGson.toJson2(map);
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
	}
}