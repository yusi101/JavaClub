package com.JavaClub.controller.Interface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.Interface.DictionarieService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

@Controller
@RequestMapping(value="/Interface/dictionarieInterface")
public class DictionarieInterface extends BaseController{
	//获取广告资质信息 
	
	@Resource(name="dictionarieService")
	private DictionarieService dictionarieService;
	
	
	@RequestMapping(value = "/queryDictionarieInfo", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryAllCountForEnterprise() throws Exception {
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
			Verification.DecodeKeyNo(pd, "zd_id");
	    	List<Map<String,Object>> dictionarieInfo=dictionarieService.queryDictionarieInfo(pd);
		    MapReplaceUtils.handleLsitMapData(dictionarieInfo);
		    resultMap.put("dictionarieInfo", dictionarieInfo);
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}
}
