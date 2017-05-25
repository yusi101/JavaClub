package com.JavaClub.controller.Interface;

import java.util.ArrayList;
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
import com.JavaClub.entity.Page;
import com.JavaClub.service.Interface.KchcInfoService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript (获取重用守信合同信息)
 * @author 李海涛
 * @createTime 2016年9月22日下午7:51:40
 * @version 1.0
 */
@Controller
@RequestMapping("/Interface/kchcInfoInterface")
public class KchcInfoInterface extends BaseController{
	
    @Autowired
	public KchcInfoService kchcinfoService;
	
	
	/**
	 * 
	 * @descript (获取重用守信合同信息)
	 * @author 李海涛
	 * @since 2016年9月22日下午7:50:47
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="queryKchcInfo",produces = "text/html;charset=UTF-8")
	public String getLcPatent(HttpServletRequest request,HttpServletResponse response){
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
            List<Map<String, Object>> kchcInfo=new ArrayList<>();
            
            //如果pageSize存在，并且大于1，则该接口分页，否则不分页
            if(StringUtil.isInt(pd.getString("pageSize"))){
                Page page = new Page();
                Verification.SetPageList(pd, page);//设置page的分页
                kchcInfo= kchcinfoService.queryKchcInfo(page);
                Verification.getPageMessage(resultMap, page);
            }else{
                kchcInfo= kchcinfoService.queryKchcInfo(pd);
            }
            
            MapReplaceUtils.handleLsitMapData(kchcInfo);
            resultMap.put("kchcInfo",kchcInfo);
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
		
	}
}
