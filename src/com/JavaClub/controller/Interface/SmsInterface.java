
package com.JavaClub.controller.Interface;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.SmsUtil;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**
 * @ClassName:     BiddingInterface
 * @Description: 短信接口 
 * @author:    Android_Robot
 * @date:        2016年9月14日 上午9:22:34
 *
 */
@Controller
@RequestMapping(value="/Interface/smsInterface")
public class SmsInterface extends BaseController{


    /**
     * 
     * @descript (发送验证码)
     * @author 李海涛
     * @since 2016年10月19日上午10:39:37
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/sendSmsVerificationCode", produces = "text/html;charset=UTF-8")
    public String sendSmsVerificationCode(HttpServletRequest request) throws Exception{
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map = Verification.Success();
        Map<String,Object> resultMap = new HashMap<>();
        map.put("data",resultMap);

        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");

            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }

            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            pd.put("code", StringUtil.getRandom(6));
            //记录验证码，用于登录接口验证
            request.getSession().setAttribute(pd.getString("deviceId"), pd.getString("code"));
            
            Verification.DecodeKeyNo(pd, "phone");
           
            String sub_code= SmsUtil.sendSmsVerificationCode(pd.getString("phone"), pd.getString("code"));

            resultMap.put("verification_code", pd.getString("code"));
            resultMap.put("sub_code", sub_code);
            resultMap.put("sub_msg", SmsUtil.getMessage(sub_code));
        }catch (Exception e) {

            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        
        return MyGson.toJson(map);
    }
    
    /**
     * 
     * @descript (牌照领取短信通知)
     * @author 李海涛
     * @since 2016年10月19日上午10:39:37
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/sendSmsNotice", produces = "text/html;charset=UTF-8")
    public String sendSmsNotice() throws Exception{
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@name@entname");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "phone");
            String sub_code= SmsUtil.sendSmsNotice(pd.getString("phone"), pd.getString("name"), pd.getString("entname"));
            resultMap.put("sub_code",sub_code);
            resultMap.put("sub_msg",SmsUtil.getMessage(sub_code));
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
    
    public static void main(String[] args) {
        
        Map<String,Object> pd=new HashMap<>();
        pd.put("phone", "13247819980");
        pd.put("code", "2451");
       
        System.out.println((String)pd.get("codes"));
    }
}
