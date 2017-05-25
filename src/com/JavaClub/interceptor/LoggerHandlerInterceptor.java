package com.JavaClub.interceptor;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.JavaClub.annotation.system.log.SysUserLog;
import com.JavaClub.annotation.system.log.SysUserLogSwitch;
import com.JavaClub.entity.system.User;
import com.JavaClub.service.system.LogService;
import com.JavaClub.util.AnnotationUtils;
import com.JavaClub.util.Const;
import com.JavaClub.util.PageData;
import com.JavaClub.util.PropertyUtils;
import com.JavaClub.util.UuidUtil;
/**
 * 
* @ClassName: LoginHandlerInterceptor 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author XWang
* @date 2016年3月21日 下午5:43:50 
*
 */
public class LoggerHandlerInterceptor extends HandlerInterceptorAdapter{

	@Resource(name="logService")
	private LogService logService;

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		PageData pd=new PageData();
		try {
			pd=(PageData)modelAndView.getModelMap().get("pd");
		} catch (Exception e) {}
		/*Map<String,Object> logInfo =  handleLoggerInfo(request,handler,pd);*/
		handleLoggerInfo(request,handler,pd);
		super.postHandle(request, response, handler, modelAndView);
	}

	public Map<String,Object> handleLoggerInfo(HttpServletRequest request,Object handler,PageData pd) throws Exception{
		Map<String, Object>  logInfo = new HashMap<String, Object> ();
		String loggerFlag = PropertyUtils.getPropertyValueByKey("syslog.properties","loggrade");
		boolean isLogger = true;
		AnnotationUtils annotationUtils = new AnnotationUtils();
		if(loggerFlag.contains("sysuser")){
		 if(handler instanceof HandlerMethod){
			    HandlerMethod handlerMethod = (HandlerMethod)handler;
			    MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
			    methodParameters.getClass().getDeclaredAnnotations();
			    for(MethodParameter methodParameter : methodParameters){
			    	logInfo = annotationUtils.loadVlaue(SysUserLog.class, "value", methodParameter.getMethod().getDeclaredAnnotations());
			    	isLogger = annotationUtils.loadBooleanVlaue(SysUserLogSwitch.class, "value", methodParameter.getMethod().getDeclaredAnnotations());

			    }
			}
		 	User user=  (User) request.getSession().getAttribute(Const.SESSION_USER);
		 	if(isLogger){
    		 	if(user != null){
    		 		logInfo.put("ACCOUNT", user.getUSER_ID());
    		 		logInfo.put("IP", user.getIP());
    		 		logInfo.put("ID", UuidUtil.get32UUID());
    		 		logInfo.put("CREATEDTIME",new Date());
    		 		logInfo.put("LOGTYPE", "user");
    		 		String remark=getPageDate(pd);
    		 		try {
    		 			if(("").equals(remark)){
    		 				remark=URLDecoder.decode(request.getQueryString(), "UTF-8");
    		 			}
    				} catch (Exception e) {}
    		 		logInfo.put("REMARK", remark);
    		 		if(logInfo.get("LOGINFO")==null||("") .equals(logInfo.get("LOGINFO"))){
    		 			logInfo.put("LOGINFO", request.getServletPath());
    		 			
    		 		}
    		 		logService.InsertLog(logInfo);
    		 		
    		 	}
		 	}
		}
		return logInfo;
		 	
	}
	
	
	@SuppressWarnings("rawtypes")
	public String getPageDate(Map map){
		String pagedate="";
		try {
			if(!map.isEmpty()){
				for (Object key : map.keySet()) {
					pagedate=pagedate+key+"="+map.get(key)+"&";
				}
				pagedate=pagedate.substring(0,pagedate.length()-1);
			}
		} catch (Exception e) {}
		
		return pagedate;
	}
}
