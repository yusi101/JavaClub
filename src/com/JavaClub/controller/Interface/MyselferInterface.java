package com.JavaClub.controller.Interface;

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
import com.JavaClub.service.Interface.Myselferservice;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript (获取自主公示信息 )
 * @author 李海涛
 * @createTime 2016年9月23日下午3:25:55
 * @version 1.0
 */
@Controller
@RequestMapping(value="/Interface/myselferInterface")
public class MyselferInterface extends BaseController{

    @Autowired
	public Myselferservice myselferservice;
	
	
	
	/**
	 * 
	 * @descript (获取自主公示信息 )
	 * @author 李海涛
	 * @since 2016年9月23日下午3:26:07
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value = "/queryMyselferInfo", produces = "text/html;charset=UTF-8")
	public String queryMyselferInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
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
			List GetAllMyselferinfo = myselferservice.queryAllMyselferinfo(pd);
			
			//企业年报基本信息
			List<Map<String,Object>> anbaseInfo=(List<Map<String, Object>>)GetAllMyselferinfo.get(0);
			MapReplaceUtils.handleLsitMapData(anbaseInfo);
			resultMap.put("anbaseInfo",anbaseInfo);
			
			//企业公示_股东及出资信息
			List<Map<String,Object>> shareholdersandInvestment=myselferservice.queryShareholdersandInvestment(pd);
			MapReplaceUtils.handleLsitMapData(shareholdersandInvestment);
			resultMap.put("shareholdersandInvestment",shareholdersandInvestment);
			
			//企业公示_股东股权修改信息
			List<Map<String,Object>> anSubcapitalInfo=(List<Map<String, Object>>)GetAllMyselferinfo.get(1);
			MapReplaceUtils.handleLsitMapData(anSubcapitalInfo);
			resultMap.put("anSubcapitalInfo",anSubcapitalInfo);
			
			//企业公示_许可信息
			List<Map<String,Object>> eimpermitInfo=(List<Map<String, Object>>)GetAllMyselferinfo.get(2);
			MapReplaceUtils.handleLsitMapData(eimpermitInfo);
			if(eimpermitInfo.size()!=0){
				for(int i=0;i<eimpermitInfo.size();i++){
					Map<String,Object> m=eimpermitInfo.get(i);
					String  from =(String) m.get("VALFROM");
					String  to =(String) m.get("VALTO");
					String fromto=from+"-"+to;
					m.put("invalidDate", fromto);
				}
			}
			resultMap.put("eimpermitInfo",eimpermitInfo);
			
			//企业公示_知识产权登记信息
			List<Map<String,Object>> eimippldgInfo=(List<Map<String, Object>>)GetAllMyselferinfo.get(3);
			MapReplaceUtils.handleLsitMapData(eimippldgInfo);
			resultMap.put("eimippldgInfo",eimippldgInfo);
			
			//企业公示_行政处罚信息
			List<Map<String,Object>> eimcaseInfo=(List<Map<String, Object>>)GetAllMyselferinfo.get(4);
			MapReplaceUtils.handleLsitMapData(eimcaseInfo);
			resultMap.put("eimcaseInfo",eimcaseInfo);
			
		}catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson2(map);
	}
	
}