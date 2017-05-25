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
import com.JavaClub.service.Interface.MortregService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript (获取动产或者不动产抵押登记信息)
 * @author 李海涛
 * @createTime 2016年9月22日下午7:55:00
 * @version 1.0
 */
@Controller	
@RequestMapping(value="/Interface/mortregInterface")
public class MortregInterface extends BaseController{

    
    @Autowired
	public MortregService mortregService;
	
	
    /**
     * 
     * @descript (获取动产抵押登记信息 )
     * @author 李海涛
     * @since 2016年9月22日下午6:37:01
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryMortregInfo", produces = "text/html;charset=UTF-8")
    public String queryMortregInfo() throws Exception{
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
            List<Map<String, Object>> mortregInfo=new ArrayList<>();
            
            //如果pageSize存在，并且大于1，则该接口分页，否则不分页
            if(StringUtil.isInt(pd.getString("pageSize"))){
                Page page = new Page();
                Verification.SetPageList(pd, page);//设置page的分页
                mortregInfo= mortregService.queryMortregInfo(page);
                Verification.getPageMessage(resultMap, page);
            }else{
                mortregInfo= mortregService.queryMortregInfo(pd);
            }
            MapReplaceUtils.handleLsitMapData(mortregInfo);
            resultMap.put("mortregInfo",mortregInfo);
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }

    /**
     * 
     * @descript (获取不动产抵押登记信息 )
     * @author 李海涛
     * @since 2016年9月22日下午6:37:01
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryChattelPledgeInfo", produces = "text/html;charset=UTF-8")
    public String queryChattelPledgeInfo() throws Exception{
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
            List<Map<String, Object>> chattelPledgeInfo=new ArrayList<>();
            
            //如果pageSize存在，并且大于1，则该接口分页，否则不分页
            if(StringUtil.isInt(pd.getString("pageSize"))){
                Page page = new Page();
                Verification.SetPageList(pd, page);//设置page的分页
                chattelPledgeInfo= mortregService.queryChattelPledgeInfo(page);
                Verification.getPageMessage(resultMap, page);
            }else{
                chattelPledgeInfo= mortregService.queryChattelPledgeInfo(pd);
            }
            MapReplaceUtils.handleLsitMapData(chattelPledgeInfo);
            resultMap.put("chattelPledgeInfo",chattelPledgeInfo);
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
	
}

