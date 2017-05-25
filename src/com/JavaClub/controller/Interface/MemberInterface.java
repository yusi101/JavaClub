package com.JavaClub.controller.Interface;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.Interface.MemberService;
import com.JavaClub.util.DateUtil;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.Logger;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.SmsUtil;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.UuidUtil;
import com.JavaClub.util.Verification;
import com.util.SendMailUtil;



@Controller
@RequestMapping(value="/Interface/memberInterface")
public class MemberInterface extends BaseController{

	protected Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private MemberService memberService;


/**
 * 
 * @descript (用户注册时发送邮件)
 * @author 余思
 * @since 2016年12月19日下午5:45:51
 * @return
 * @throws Exception
 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/sendEmailForRegister", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String sendEmailForRegister(HttpServletRequest request) throws Exception{
		PageData pd = this.getPageData();	//获取页面信息
		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultdata=new HashMap<>();
		try {
			//判断必要参数
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
			if(!"".equals(VerificationParameter)){
				resultdata=Verification.LackParameter(VerificationParameter);
				return  MyGson.toJson(resultdata);
			}
			//判断token
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}
			Verification.DecodeKeyNo(pd, "email");
			//判断注册邮箱是否重复	
			List redistList = memberService.userRegisteremail(pd);
			if(!redistList.isEmpty()){
				map=Verification.ResultMessage(12);
				return  MyGson.toJson(map);	
			}
//			随机数 验证码
			pd.put("code", StringUtil.getRandom(6));
			String title="账号激活";
			List<String> sendTo =new ArrayList<>();
			sendTo.add(pd.getString("email"));
			String htmlMessage = "<html><body><font size='15' color='red'>"+pd.getString("code")+"</font>";
			//发送邮件
			String status=SendMailUtil.sendHtmlEmail(sendTo, title, htmlMessage);
			if("success".equals(status)){
				map=Verification.ResultMessage(13);
				map.put("code", pd.getString("code"));
				return MyGson.toJson(map);
			}else{
				map=Verification.ResultMessage(11);
				return MyGson.toJson(map);
			}
		}catch (Exception e){
			logger.error(e.toString(),e);
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
	}

	/**
	 * 
	 * @descript (用户注册)
	 * @author 李海涛
	 * @since 2016年11月16日上午11:44:02
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/userRegister", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String userRegister(HttpServletRequest request) throws Exception{
		PageData pd = this.getPageData();
		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultMap=new HashMap<>();
		map.put("data",resultMap);
		try {
			//判断必要参数
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@openType");
			if(!"".equals(VerificationParameter)){
				return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
			}
			//判断token
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}
			if("0".equals(pd.getString("openType"))){
				//判断必要参数
				//VerificationParameter=Verification.VerificationParameter(pd,"password@code");
				VerificationParameter=Verification.VerificationParameter(pd,"password");
				if(!"".equals(VerificationParameter)){
					return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
				}
				//排除验证码h5的登录校验，因为在h5后台已做校验。
//                if(!"123".equals(pd.getString("deviceId"))){
                    //验邮箱验证码录验证码是否正确
//                    try {
//                        if(!request.getSession().getAttribute(pd.getString("deviceId")).equals(pd.getString("code"))){
//                            return MyGson.toJson(Verification.ResultMessage(9));
//                        } 
//                    } catch (Exception e) {
//                        return MyGson.toJson(Verification.ResultMessage(9));
//                    }

//                }
				Verification.DecodeKeyNo(pd, "username");
				//判断手机号  是否重复
				List redistList = memberService.userRegister(pd);
				if(!redistList.isEmpty()){
					map=Verification.ResultMessage(4);
					return  MyGson.toJson(map);
				}
				//判断邮箱  是否重复
				List redistListrl = memberService.userRegisteremail(pd);
				if(!redistListrl.isEmpty()){
					map=Verification.ResultMessage(12);
					return  MyGson.toJson(map);
				}
				
				String password = new SimpleHash("SHA-1", pd.getString("username"), pd.getString("password")) + "";	//密码加密
				pd.put("id", get32UUID());
				pd.put("password", password);
				int i=memberService.saveUserRegister(pd);
				resultMap.put("affectedRow", Integer.toString(i));
				return MyGson.toJson(map);
			}else if("1".equals(pd.getString("openType"))){
				//判断邮箱是否重复
				List redistListrl = memberService.userRegisteremail(pd);
				if(!redistListrl.isEmpty()){
					map=Verification.ResultMessage(4);
					return  MyGson.toJson(map);
				}
				pd.put("id", pd.get("KeyNo"));
				int i = memberService.userModify(pd);
				resultMap.put("affectedRow", Integer.toString(i));
				map=Verification.ResultMessage(1);
				return MyGson.toJson(map);
			}
			return MyGson.toJson(map);
		}catch (Exception e){
			logger.error(e.toString(),e);
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
	}

	/**
	 * 用户登录
	 * 李海涛
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userLogin", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String userLogin(HttpServletRequest request) throws Exception {
		PageData pd = this.getPageData();
		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultdata=new HashMap<>();

		try {
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@password");

			if(!"".equals(VerificationParameter)){
				resultdata=Verification.LackParameter(VerificationParameter);
				return  MyGson.toJson(resultdata);
			}

			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}



			Verification.DecodeKeyNo(pd, "email");
			List<Map<String, Object>> pwdlist = memberService.passwordGet(pd);

			if(ListUtils.isNotEmpty(pwdlist) && !pwdlist.isEmpty()){
				String passwd = new SimpleHash("SHA-1", pd.getString("email"), pd.getString("password")).toString();	//密码加密
				if(!passwd.equals(ListUtils.getListMap(pwdlist, 0).get("PASSWORD").toString())){
					return  MyGson.toJson(Verification.ResultMessage(5));
				}else{
					pd.put("id", ListUtils.getListMap(pwdlist, 0).get("ID").toString());
					//登录成功后返回的值
					List<Map<String, Object>> memberInfo = memberService.getMemberInfo(pd);
					MapReplaceUtils.handleLsitMapData(memberInfo);
					resultdata.put("memberInfo", memberInfo);
				}

			}else{
				return  MyGson.toJson(Verification.ResultMessage(6));
			}
			map.put("data",resultdata);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}


	/**
	 * 
	 * @descript (得到登录后成功返回的值)
	 * @author 李海涛
	 * @since 2016年9月27日下午3:00:50
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMemberInfo", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryMemberInfo() throws Exception {
		PageData pd = this.getPageData();
		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultdata=new HashMap<>();
		try {
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
			if(!"".equals(VerificationParameter)){
				resultdata=Verification.LackParameter(VerificationParameter);
				return  MyGson.toJson(resultdata);
			}
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}
			Verification.DecodeKeyNo(pd, "id");    //用户ID
			//登录成功后返回的值
			List<Map<String, Object>> memberInfo = memberService.getMemberInfo(pd);
			MapReplaceUtils.handleLsitMapData(memberInfo);
			resultdata.put("memberInfo", memberInfo);
			map.put("data",resultdata);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}

		return MyGson.toJson(map);
	}

	/*
	 * 用户资料修改
	 * 喻超
	 */
	@RequestMapping(value = "/userModify", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String userModify() throws Exception{
		PageData pd = this.getPageData();
		Map<String,Object> map = Verification.Success();
		Map<String,Object> resultdata = new HashMap<>();
		try{
			//判断必要参数
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
			if(!"".equals(VerificationParameter)){
				resultdata=Verification.LackParameter(VerificationParameter);
				return  MyGson.toJson(resultdata);
			}
			//判断token
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}
			Verification.DecodeKeyNo(pd, "id");
			int i=memberService.userModify(pd);
			resultdata.put("result", Integer.toString(i));
			List<Map<String, Object>> memberList = memberService.getMemberInfo(pd);
			MapReplaceUtils.handleLsitMapData(memberList);
			resultdata.put("memberList", memberList);
			map.put("data", resultdata);
			return MyGson.toJson(map);
		}catch (Exception e){
			logger.error(e.toString(),e);
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
	}
	/*
	 * 密码修改
	 * 喻超
	 */
	@RequestMapping(value = "/passwordModify", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String passwordModify() throws Exception{
		PageData pd = this.getPageData();
		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultdata=new HashMap<>();
		//结果参数
		try{
			//判断必要参数
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@username@oldPassword@newPassword");
			if(!"".equals(VerificationParameter)){
				resultdata=Verification.LackParameter(VerificationParameter);
				return  MyGson.toJson(resultdata);
			}
			//判断token
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}
			//判断旧密码是否正确
			Verification.DecodeKeyNo(pd, "id");
			List<Map<String, Object>> pwdlist=memberService.passwordGet(pd);
			if(ListUtils.isNotEmpty(pwdlist) && !pwdlist.isEmpty()){
				String nowPassword=ListUtils.getListMap(pwdlist, 0).get("PASSWORD").toString();
				String oldPassword=new SimpleHash("SHA-1", pd.getString("username"), pd.getString("oldPassword")).toString();	//密码加密
				pd.put("oldPassword", oldPassword);
				if(!nowPassword.equals(oldPassword)){
					map=Verification.ResultMessage(3);       //返回结果参数
					return  MyGson.toJson(map);
				}
				pd.put("newPassword", new SimpleHash("SHA-1", pd.getString("username"), pd.getString("newPassword")).toString());
				int i=memberService.passwordModify(pd);
				resultdata.put("result", Integer.toString(i));
				map=Verification.ResultMessage(1);
				map.put("data", resultdata);
				return MyGson.toJson(map);
			}else{
				map=Verification.ResultMessage(2);       //用户不存在
				return  MyGson.toJson(map);
			}
		}catch (Exception e){
			logger.error(e.toString(),e);
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
	}




	/**
	 * 用户激活邮箱
	 * @author 龚志强
	 * @param validatecode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/activatedUser", produces = "text/html;charset=UTF-8")
	public String activatedUser(String validatecode) {
		PageData pd = this.getPageData();
		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultdata=new HashMap<>();
		try{
			//判断必要参数
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
			pd.put("evcode", pd.getString("KeyNo"));
			if(!"".equals(VerificationParameter)){
				resultdata=Verification.LackParameter(VerificationParameter);
				return MyGson.toJson(resultdata);
			}
			//判断token
			if(!Verification.VerificationMd5(pd)){
				return MyGson.toJson(Verification.Md5Match());
			}

			//查询激活邮箱验
			Map<String,Object> emilMap = memberService.queryEmailHistory(pd);
			if(null == emilMap){
				return MyGson.toJson(Verification.ResultMessage(7));
			}

			//获取发送验证码的时间,比较时间是否过期			
			Date date = DateUtil.parseDate(emilMap.get("EVTIME").toString());
			int result = DateUtil.compareTime(date);
			//当返回1时表示验证码过期,精确到秒！
			if(result == 1){  
				return MyGson.toJson(Verification.ResultMessage(8));
			}
			//激活用户
			pd.put("id", emilMap.get("ID"));
			if(0 < memberService.activatedUser(pd)){
				resultdata.put("resultdata", "1");
				map.put("data", resultdata);
			}	
		}catch (Exception e){
			logger.error(e.toString(),e);
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}	
		return MyGson.toJson(map);
	}

	/**
	 * 
	 * @descript (忘記密碼--发送邮件)
	 * @author 余思
	 * @since 2016年12月2日上午10:35:46
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/forgetpassword", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String forgetpassword(HttpServletRequest request) throws Exception{
		PageData pd = this.getPageData();
		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultdata=new HashMap<>();
		//结果参数
		try{
			//判断必要参数
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@type");
			if(!"".equals(VerificationParameter)){
				resultdata=Verification.LackParameter(VerificationParameter);
				return  MyGson.toJson(resultdata);
			}
			//判断token
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}
			if("0".equals(pd.getString("type"))){// 0为 判断用户发送短信  1为重置密码 2为 发送短信 3为 发送邮件
				//判断用户是否存在
				Verification.DecodeKeyNo(pd, "username");
				List<Map<String,Object>> usernameCount=memberService.usernameGet(pd);
				if(ListUtils.isNotEmpty(usernameCount) && !usernameCount.isEmpty()){
					pd.put("code", StringUtil.getRandom(6));
					//短信验证码
					String status=SmsUtil.sendSmsPassword(usernameCount.get(0).get("TEL")+"", pd.getString("code"));
					if("true".equals(status)){
						map=Verification.ResultMessage(1);
						map.put("data", usernameCount);
						map.put("code", pd.getString("code"));
						return MyGson.toJson(map);
					}else{
						map=Verification.ResultMessage(10);
						return MyGson.toJson(map);
					}
					
				}else{
					map=Verification.ResultMessage(6);       
					return  MyGson.toJson(map);
				}
			}else if("2".equals(pd.getString("type"))){// 0为 判断用户发送短信  1为重置密码 2为 发送短信 3为 发送邮件
					Verification.DecodeKeyNo(pd, "username");
					pd.put("code", StringUtil.getRandom(6));
					//短信验证码
					String status=SmsUtil.sendSmsPassword(pd.getString("username"), pd.getString("code"));
					if("true".equals(status)){
						map=Verification.ResultMessage(1);
						map.put("code", pd.getString("code"));
						return MyGson.toJson(map);
					}else{
						map=Verification.ResultMessage(10);
						return MyGson.toJson(map);
					}
					
			}else if("3".equals(pd.getString("type"))){// 0为 判断用户发送短信  1为重置密码 2为 发送短信 3为 发送邮件
					Verification.DecodeKeyNo(pd, "username");
					pd.put("code", StringUtil.getRandom(6));
					//邮箱验证码
					String title="忘记密码";
					List<String> sendTo =new ArrayList<String>();
					sendTo.add(pd.getString("username"));
					String htmlMessage = "<html><body><font size='15' color='red'>"+pd.getString("code")+"</font>";
					String status=SendMailUtil.sendHtmlEmail(sendTo, title, htmlMessage);
					if("success".equals(status)){
						map=Verification.ResultMessage(1);
						map.put("code", pd.getString("code"));
						return MyGson.toJson(map);
					}else{
						map=Verification.ResultMessage(11);
						return MyGson.toJson(map);
					}
			}else if("4".equals(pd.getString("type"))){
				//判断用户是否存在
				Verification.DecodeKeyNo(pd, "username");
				List<Map<String,Object>> usernameCount=memberService.queryUseremailandphone(pd);
				if(ListUtils.isNotEmpty(usernameCount) && !usernameCount.isEmpty()){
					pd.put("id", usernameCount.get(0).get("ID"));
					Verification.DecodeKeyNo(pd, "id");
					pd.put("czpwd", new SimpleHash("SHA-1", pd.getString("username"), pd.getString("czpwd")).toString());
					int i=memberService.czpassword(pd);
					resultdata.put("result", Integer.toString(i));
					map=Verification.ResultMessage(14);
					map.put("data", resultdata);
					return MyGson.toJson(map);
				}else{
					map=Verification.ResultMessage(6);       
					return  MyGson.toJson(map);
				}
			}else{
				Verification.DecodeKeyNo(pd, "id");
				pd.put("czpwd", new SimpleHash("SHA-1", pd.getString("username"), pd.getString("czpwd")).toString());
				int i=memberService.czpassword(pd);
				resultdata.put("result", Integer.toString(i));
				map=Verification.ResultMessage(1);
				map.put("data", resultdata);
				return MyGson.toJson(map);
			}

		}catch (Exception e){
			logger.error(e.toString(),e);
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
	}
	/**
	 * 
	 * @descript 支付记录的添加与查询
	 * @author 余思
	 * @since 2017年4月28日下午2:26:49
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/paymoneyhistory", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String paymoneyhistory(HttpServletRequest request) throws Exception{
		PageData pd = this.getPageData();
		Map<String,Object> map=Verification.Success();
		//结果参数
		try{
			if("0".equals(pd.getString("type"))){// 0为添加  1为查询
				pd.put("id",  UuidUtil.get32UUID());
				Verification.DecodeKeyNo(pd, "username");
				int i=memberService.savepay(pd);
				map=Verification.ResultMessage(i);       
				return  MyGson.toJson(map);
			}else{
				List<Map<String,Object>> payHistory=memberService.querypayHistory(pd);
				map=Verification.ResultMessage(14);
				map.put("data", payHistory);
				return MyGson.toJson(map);
			}
		}catch (Exception e){
			logger.error(e.toString(),e);
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
	}
}
