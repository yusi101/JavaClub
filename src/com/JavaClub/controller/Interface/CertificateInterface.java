package com.JavaClub.controller.Interface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.Interface.CertificateService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * @descript 许可信息（行政审批）
 * @author 李海涛
 * @since 2016年9月9日上午9:09:29
 *
 */
@Controller
@RequestMapping(value="/Interface/certificateInterface")
public class CertificateInterface extends BaseController{
	
	@Autowired
	public CertificateService certificateService;

	
	/**
	 * 
	 * @descript 得到许可信息（行政审批）
	 * @author 李海涛
	 * @since 2016年9月9日上午9:09:29
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
    @ResponseBody
	@RequestMapping(value = "/queryCertificateInfo", produces = "text/html;charset=UTF-8")
	public String queryCertificateInfo() throws Exception{
	    PageData pd = new PageData();
		pd = this.getPageData();

		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultMap=new HashMap<>();
		map.put("data",resultMap);

		try {
		    //选择调用该接口必须传入的参数
			String VerificationParameter=Verification.VerificationParameter(pd,"token@KeyNo@deviceId");
			 //如果上面的必要参数没有，则返回异常
			if(!VerificationParameter.equals("")){
				return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
			}

			//验证加密是否比配，不匹配则返回加密错误
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}

			Verification.DecodeKeyNo(pd, "pripid");
			List certificateInfo=certificateService.queryCertificateInfo(pd);
			//得到企业或个体许可信息
			List<Map<String, Object>> LIC_Certificate= (List<Map<String, Object>>) certificateInfo.get(0);
			MapReplaceUtils.handleLsitMapData(LIC_Certificate);
            resultMap.put("lic_Certificate",LIC_Certificate);
            //得到其他行政许可信息  
			List<Map<String, Object>> OT_Permit= (List<Map<String, Object>>) certificateInfo.get(1);

			MapReplaceUtils.handleLsitMapData(OT_Permit);
			resultMap.put("ot_Permit",OT_Permit);
		}catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}
	
	
}
