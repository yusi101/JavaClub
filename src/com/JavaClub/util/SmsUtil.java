/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年10月19日上午9:04:47
 * @version 1.0
 */
package com.JavaClub.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.JavaClub.service.credit.AnnualNotificationService;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年10月19日上午9:04:47
 * @version 1.0
 */
public class SmsUtil {
	
	protected static Logger logger = Logger.getLogger(PDFUtils.class);
	
	@Resource(name = "annualNotificationService")
	private static AnnualNotificationService anService;
    /**
     * 
     * @descript (发送短信验证码)
     * @author 李海涛
     * @since 2016年10月19日上午10:19:42
     * @param 接收的手机号码,只能一个
     * @param code 验证码内容
     * @return
     */
    public static String sendSmsVerificationCode(String phone,String code){
        String SMS_CONFIG = Tools.readTxtFile(Const.SMS_VERIFICATIONCODE);       //读取邮件配置
        String []SMS_CONFIGS=SMS_CONFIG.split(",");
        if(SMS_CONFIGS.length>=5){
            try {
                TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest","23479232","ab36828d57cccb91e88d6932f997a72e");
                AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
                req.setExtend(SMS_CONFIGS[0]);
                req.setSmsType(SMS_CONFIGS[1]);//短信类型，传入值请填写normal
                req.setSmsFreeSignName(SMS_CONFIGS[2]);//短信签名，传入的短信签名必须是在阿里大于“管理中心-短信签名管理”中的可用签名。
                req.setSmsParamString("{\""+SMS_CONFIGS[3]+"\":\""+code+"\"}");//短信模板变量
                req.setRecNum(phone);//短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，以英文逗号分隔，
                req.setSmsTemplateCode(SMS_CONFIGS[4]);//短信模板ID，传入的模板必须是在阿里大于“管理中心-短信模板管理”中的可用模板
                AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
                return rsp.getSubCode();
            } catch (Exception e) {
            	logger.error(e.toString(),e);
                return "isp.SYSTEM_ERROR";  
            }
        }else{
          return "isv.INVALID_PARAMETERS";  
        }
        
    }
    
    /**
     * 
     * @descript (牌照领取短信通知)
     * @author 李海涛
     * @since 2016年10月19日上午10:19:42
     * @param 接收的手机号码,只能一个
     * @param name 申请人
     * @param entname 企业名称
     * @return
     */
    public static String sendSmsNotice(String phone,String name,String entname){
        String SMS_CONFIG = Tools.readTxtFile(Const.SMS_NOTICE);       //读取邮件配置
        String []SMS_CONFIGS=SMS_CONFIG.split(",");
        if(SMS_CONFIGS.length>=5){
            try {
                TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest","23479232","ab36828d57cccb91e88d6932f997a72e");
                AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
                req.setExtend(SMS_CONFIGS[0]);
                req.setSmsType(SMS_CONFIGS[1]);//短信类型，传入值请填写normal
                req.setSmsFreeSignName(SMS_CONFIGS[2]);//短信签名，传入的短信签名必须是在阿里大于“管理中心-短信签名管理”中的可用签名。
                req.setSmsParamString("{\""+SMS_CONFIGS[3]+"\":\""+name+"\",\""+SMS_CONFIGS[4]+"\":\""+entname+"\"}");//短信模板变量
                req.setRecNum(phone);//短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，以英文逗号分隔，
                req.setSmsTemplateCode(SMS_CONFIGS[5]);//短信模板ID，传入的模板必须是在阿里大于“管理中心-短信模板管理”中的可用模板
                AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
                return rsp.getSubCode();
            } catch (Exception e) {
            	logger.error(e.toString(),e);
                return "isp.SYSTEM_ERROR";  
            }
        }else{
          return "isv.INVALID_PARAMETERS";  
        }
        
    }
    /**
     * 
     * @descript (新建区年报推送)
     * @author 余思
     * @since 2017年3月10日下午5:30:40
     * 				接收的手机号码,只能一个
     * @param phone
     * @return	
     */
    public static String sendSmsReport(String phone){
        String SMS_CONFIG = Tools.readTxtFile(Const.SMSREPORT);       //读取邮件配置
        String []SMS_CONFIGS=SMS_CONFIG.split(",");
        if(SMS_CONFIGS.length>=4){
            try {
                TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest","23479232","ab36828d57cccb91e88d6932f997a72e");
                AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
                req.setExtend(SMS_CONFIGS[0]);
                req.setSmsType(SMS_CONFIGS[1]);//短信类型，传入值请填写normal
                req.setSmsFreeSignName(SMS_CONFIGS[2]);//短信签名，传入的短信签名必须是在阿里大于“管理中心-短信签名管理”中的可用签名。
               // req.setSmsParamString("{\""+SMS_CONFIGS[4]+"\":\""+SMS_CONFIGS[5]+"\"}");//短信模板变量
                req.setRecNum(phone);//短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，以英文逗号分隔，
                req.setSmsTemplateCode(SMS_CONFIGS[3]);//短信模板ID，传入的模板必须是在阿里大于“管理中心-短信模板管理”中的可用模板
                AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
                return getMessage(rsp.getSubCode());
                
            } catch (Exception e) {
            	logger.error(e.toString(),e);
                return "isp.SYSTEM_ERROR";  
            }
        }else{
          return "isv.INVALID_PARAMETERS";  
        }
        
    }
    /**
     * 
     * @descript (找回密码发送验证码)
     * @author 李海涛
     * @since 2016年10月19日上午10:19:42
     * @param 接收的手机号码,只能一个
     * @param name 申请人
     * @param entname 企业名称
     * @return
     */
    @SuppressWarnings("unchecked")
	public static String sendSmsPassword(String phone,String code){
        String SMS_CONFIG = Tools.readTxtFile(Const.SMS_MM);       //读取邮件配置
        String []SMS_CONFIGS=SMS_CONFIG.split(",");
        if(SMS_CONFIGS.length>=5){
            try {
                TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest","23479232","ab36828d57cccb91e88d6932f997a72e");
                AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
                req.setExtend(SMS_CONFIGS[0]);
                req.setSmsType(SMS_CONFIGS[1]);//短信类型，传入值请填写normal
                req.setSmsFreeSignName(SMS_CONFIGS[2]);//短信签名，传入的短信签名必须是在阿里大于“管理中心-短信签名管理”中的可用签名。
                req.setSmsParamString("{\""+SMS_CONFIGS[3]+"\":\""+code+"\"}");//短信模板变量
                req.setRecNum(phone);//短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，以英文逗号分隔，
                req.setSmsTemplateCode(SMS_CONFIGS[4]);//短信模板ID，传入的模板必须是在阿里大于“管理中心-短信模板管理”中的可用模板
                AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
                 System.out.println("========SMS==="+getMessage(rsp.getSubCode()));
                Map<String,Object> result=(Map<String,Object>)((Map<String,Object>)JsonUtils.fromJsonToMap(rsp.getBody()).get("alibaba_aliqin_fc_sms_num_send_response")).get("result");
                System.out.println("=============="+result);
                return result.get("success").toString();
            } catch (Exception e) {
            	logger.error(e.toString(),e);
                return "isp.SYSTEM_ERROR";  
               
            }
        }else{
          return "isv.INVALID_PARAMETERS";  
        }
        
    }
    
    public static String getMessage(String message){
        if("".equals(message) || message ==null){
            return "短信发送成功";
        }else{
            Map<String, String> map=new HashMap<>(18);
            map.put("isv.OUT_OF_SERVICE", "业务停机");
            map.put("isv.PRODUCT_UNSUBSCRIBE", "产品服务未开通");
            map.put("isv.ACCOUNT_NOT_EXISTS", "账户信息不存在");
            map.put("isv.ACCOUNT_ABNORMAL", "账户信息异常");
            map.put("isv.SMS_TEMPLATE_ILLEGAL", "模板不合法");
            map.put("isv.SMS_SIGNATURE_ILLEGAL", "签名不合法");
            map.put("isv.MOBILE_NUMBER_ILLEGAL", "手机号码格式错误");
            map.put("isv.MOBILE_COUNT_OVER_LIMIT", "手机号码数量超过限制");
            map.put("isv.TEMPLATE_MISSING_PARAMETERS", "短信模板变量缺少参数");
            map.put("isv.INVALID_PARAMETERS", "参数异常");
            map.put("isv.INVALID_JSON_PARAM", "JSON参数不合法");
            map.put("isp.SYSTEM_ERROR", "-");
            map.put("isv.BLACK_KEY_CONTROL_LIMIT", "模板变量中存在黑名单关键字。如：阿里大鱼");
            map.put("isv.PARAM_NOT_SUPPORT_URL", "不支持url为变量");
            map.put("isv.PARAM_LENGTH_LIMIT", "变量长度受限");
            map.put("isv.AMOUNT_NOT_ENOUGH", "余额不足");
            map.put("isv.SUCCESS", "短信发送成功");
            return map.get(message);
        }
        
    }
    public static void main(String[] args) {
    	
        //System.out.println(sendSmsReport("13870908516"));
        System.out.println(sendSmsReport("13870908516"));
    }
}
