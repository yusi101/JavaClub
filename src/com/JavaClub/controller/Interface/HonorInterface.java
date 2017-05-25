package com.JavaClub.controller.Interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.Interface.HonorService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**
 * @descript 得到荣誉信息
 * @author 李海涛
 * @since 2016年9月9日上午9:09:29
 *
 */
@Controller
@RequestMapping(value="/Interface/honorInterface")
public class HonorInterface extends BaseController{
	
	@Autowired
	public HonorService honorService;

	
	/**
	 * 
	 * @descript 得到荣誉信息，参数中有pageSize则代表分页
	 * @author 李海涛
	 * @since 2016年9月9日上午9:09:29
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryHonorInfo", produces = "text/html;charset=UTF-8")
	public String getQueryBrandinfo() throws Exception{
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
			List<Map<String, Object>> Honorinfo=new ArrayList<>();
			
			//如果pageSize存在，并且大于1，则该接口分页，否则不分页
	        if(StringUtil.isInt(pd.getString("pageSize"))){
	            Page page = new Page();
	            Verification.SetPageList(pd, page);//设置page的分页
	            Honorinfo= honorService.getHonorinfo(page);
	            Verification.getPageMessage(resultMap, page);
	        }else{
	            Honorinfo= honorService.getHonorinfo(pd);
	        }
	        
			MapReplaceUtils.handleLsitMapData(Honorinfo);
			resultMap.put("honorInfo",Honorinfo);
		}catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}
	
	
}
