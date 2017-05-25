/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年9月26日上午11:31:32
 * @version 1.0
 */
package com.JavaClub.controller.Interface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.SecurityHelper;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.Interface.VersionService;
import com.JavaClub.util.CreateCode;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * @descript (生成和下载二维码接口)
 * @author 李海涛
 * @createTime 2016年9月26日上午11:31:32
 * @version 1.0
 */
@Controller
@RequestMapping(value="/Interface/qrCodeInterface")
public class QrCodeInterface extends BaseController{

    @Autowired
    public VersionService versionService;
    
    /**
     * 生成二维码名片，带外圈二维码
     * @author 李海涛
     * @return
     * @throws Exception
     */ 
    @RequestMapping(value = "/createTwoDimensionCode", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String createTwoDimensionCode(HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultdata=new HashMap<>();
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@entname");
            if(!VerificationParameter.equals("")){
                resultdata=Verification.LackParameter(VerificationParameter);
                return  MyGson.toJson(resultdata);
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "pripid");
            resultdata.put("result", CreateCode.CreateQRCodeCircle2(request, pd));
            map.put("data",resultdata);
        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
    /**
     * 生成二维码名片，不带外圈二维码
     * @author 李海涛
     * @return
     * @throws Exception
     */ 
    @RequestMapping(value = "/CreateQRCode", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String CreateQRCode(HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultdata=new HashMap<>();
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@entname");
            if(!VerificationParameter.equals("")){
                resultdata=Verification.LackParameter(VerificationParameter);
                return  MyGson.toJson(resultdata);
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "pripid");
            resultdata.put("result", CreateCode.CreateQRCodeCircle1(request, pd));
            map.put("data",resultdata);
        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
    
    /**
     * 二维码
     * @author 李海涛
     * @return
     * @throws Exception
     */ 
    @RequestMapping(value = "/creditHomePage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String creditHomePage(HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();

        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            //选择调用该接口必须传入的参数
            String VerificationParameter=Verification.VerificationParameter(pd,"token@KeyNo@deviceId");
             //如果上面的必要参数没有，则返回异常
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            
            //验证加密是否比配，不匹配则返回加密错误
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "dowmload");
            pd.put("status", 0);
            List<Map<String, Object>>  Version= versionService.queryVersionInfo(pd);
            for (int i = 0; i < Version.size(); i++) {
                Map<String, Object> Version_map=Version.get(i);
                Version_map.put("PATH", SecurityHelper.getEncrypt(Version_map.get("PATH").toString()));
                Version.set(i, Version_map);
            }
            MapReplaceUtils.handleLsitMapData(Version);
            resultMap.put("version", Version);
            
        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
}
