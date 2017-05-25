package com.JavaClub.util;

public class Const {
    
    public static final String LOGIN = "/login_toLogin.do";             		//登录地址
	public static final String SESSION_SECURITY_CODE = "sessionSecCode"; 		//验证码
	public static final String PAGE = "admin/config/PAGE.txt";          		//分页条数配置路径
	
	public static final String CONNECT_PATH_JX = PropertyUtils.getPropertyValueByKey("CLIENT_PATH"); //调用接口的地址前缀
	public static final String CLIENT_PATH = PropertyUtils.getPropertyValueByKey("CLIENT_PATH"); //调用接口的地址前缀
	
	public static final String VERSION  = "v1.0";   //接口版本
	
	public static final String SMS_VERIFICATIONCODE = "admin/config/SMSVERIFICATIONCODE.txt";        //短信验证码服务器配置路径
	
	public static final String SMS_NOTICE = "admin/config/SMSNOTICE.txt";        //短信通知服务器配置路径
	public static final String SMS_MM = "admin/config/SMSPASSWORD.txt";        //短信通知服务器配置路径
	public static final String SMSREPORT = "admin/config/SMSREPORT.txt";        //新建区年报推送短信通知服务器配置路径
	
	//地区代码    (360122是新建县的代码)
	public static final String C_COUNTY ="36";
	public static final String C_COUNTY_CN ="江西省";
	//区域
    public static final String AREA_ALIAS = "赣";
	//区域
	public static final String AREA = "XJ";
	
	public static final String SESSION_USER = "sessionUser";
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(Interface/*)|(logout)|(code)|(statistics)|(static)|(uploadfiles/qrcode/*)).*"; //不对匹配该值的访问路径拦截（正则）

	//企业查询接口
	public static final String ENTERPRISEURL = PropertyUtils.getPropertyValueByKey("ENTERPRISEURL");
	public static final String ENTERPRISEURLFOUR = "/Interface/baseinfoInterface/queryEnterpriseInfo.do";
	
	//牌照二维码
	public static final String GETSEAL_TOP = PropertyUtils.getPropertyValueByKey("getSealTop");
	public static final String GETSEAL_BOTTOM = PropertyUtils.getPropertyValueByKey("getSealBottom"); 
	//二维码跳转地址
	public static final String TwoDimensionCodePath = PropertyUtils.getPropertyValueByKey("TwoDimensionCodePath"); 
	//客户端下载地址
    public static final String DownLoadPath = PropertyUtils.getPropertyValueByKey("DownLoadPath"); 
	
	//二维码存放路径
	public static final String FILEPATHTWODIMENSIONCODE = "uploadfiles/twoDimensionCode/"; 
	//导入模板Excel路径
	public static final String UPEXCEL = "demo/";
	//导入模板Excel名称
	public static final String EXCELNAME = "牌照导入模板.xls";
	//牌照生成记录的Excel名称
	public static final String EXCELRECORD = "牌照生成记录.xls";
	
	//成功
	public static final String SUCCESS = "success";
	//失败
	public static final String FAIL = "fail";
	//全部重复，用于制作牌照
	public static final String ALLREPEAT = "allrepeat";
	//重复
	public static final String REPEAT = "repeat";
	
	//邮箱
//	public static String EMAILPROTOCOL = "smtp.163.com";				//邮箱协议
//	public static int EMAILPORT = 25;									//邮箱端口
//	public static String EMAILUSER = "13247819980@163.com";				//发送邮箱的账号
//	public static String EMAILPASSWORD = "13247819980hai";				//发送邮箱的密码
//	public static String EMAIL_NAME = "江西瑞臻企业征信服务有限公司";
	public static  String EMAILPROTOCOL = "smtp.139.com";				//	邮箱协议
	public static  int EMAILPORT = 25;									//邮箱端口
	public static  String EMAILUSER = "18279189819@139.com";				//发送邮箱的账号
	public static  String EMAILPASSWORD = "yusi19960619";				//发送邮箱的密码
	public static  String EMAIL_NAME = "江西瑞臻企业征信服务有限公司";
	
	//江西智容科技有限公司电话号码
	public static final String TELEPHONE = "0791-86660006";
	//系统名称
	public static final String SYSNAME = "新建区信用二维码";
	
	
	

}