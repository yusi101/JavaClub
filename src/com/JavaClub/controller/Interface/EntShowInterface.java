package com.JavaClub.controller.Interface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.Interface.EntShowService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;


/**
 * 
 * @descript (企业展示控制器)
 * @author 李海涛
 * @createTime 2016年9月30日上午10:32:19
 * @version 1.0
 */
@Controller
@RequestMapping(value="/Interface/entShowInterface")
public class EntShowInterface extends BaseController{

	@Autowired
	private EntShowService entShowService;
	
	/**
	 * 企业展示
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEntShowInfo", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryEntShowInfo() throws Exception {
	    PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
		//初始化数据
		try {			
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
			if(!VerificationParameter.equals("")){
				return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
			}
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}
			Verification.DecodeKeyNo(pd, "pripid");
			List<Map<String, Object>> entShowInfo = entShowService.queryEntShowInfo(pd);			
			MapReplaceUtils.handleLsitMapData(entShowInfo);
			resultMap.put("entShowInfo", entShowInfo);					
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}
	
}
