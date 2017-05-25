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
import com.JavaClub.service.Interface.OtCaseInfoService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;


/**
 * 
 * @descript (获取行政处罚信息)
 * @author 李海涛
 * @createTime 2016年9月22日下午8:09:53
 * @version 1.0
 */
@Controller
@RequestMapping(value="/Interface/otCaseInfoInterface")
public class OtCaseInfoInterface extends BaseController{


    @Autowired
    private OtCaseInfoService otCaseInfoService;

    /**
     * 
     * @descript (获取行政处罚信息)
     * @author 李海涛
     * @since 2016年9月22日下午8:11:32
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryOtCaseInfo", produces = "text/html;charset=UTF-8")
    public String queryOtCaseInfo() throws Exception{
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
            List<Map<String, Object>> otCaseInfo=new ArrayList<>();

            //如果pageSize存在，并且大于1，则该接口分页，否则不分页
            if(StringUtil.isInt(pd.getString("pageSize"))){
                Page page = new Page();
                Verification.SetPageList(pd, page);//设置page的分页
                otCaseInfo= otCaseInfoService.queryOtCaseInfo(page);
                Verification.getPageMessage(resultMap, page);
            }else{
                otCaseInfo= otCaseInfoService.queryOtCaseInfo(pd);
            }

            MapReplaceUtils.handleLsitMapData(otCaseInfo);
            resultMap.put("otCaseInfo",otCaseInfo);
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
}

