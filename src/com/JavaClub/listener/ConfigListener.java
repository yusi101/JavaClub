package com.JavaClub.listener;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.JavaClub.service.credit.KeywordService;
import com.JavaClub.service.system.ConfigService;
import com.JavaClub.util.Const;
import com.JavaClub.util.Logger;
import com.JavaClub.util.PageData;

/**
 * 配置监听器
 * @author gongzhiqiang
 *
 */
public class ConfigListener implements ServletContextListener{
	
	protected Logger logger = Logger.getLogger(this.getClass());
	private static final String KEYNAME = "KEYNAME";
	private static final String VALUE = "VALUE";
	@Override
	public void contextDestroyed(ServletContextEvent context) {
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent sc) {
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc.getServletContext());
		//WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc.getServletContext());
		ConfigService configService = (ConfigService) applicationContext.getBean("configService");
		KeywordService keywordService = (KeywordService) applicationContext.getBean("keywordService"); 
		
    	PageData pd = new PageData();
    	//后台父ID
    	pd.put("id", "655daf1e7c984b6092077fb4c75227c8");
		List<Map<String, Object>> configList = null;		
		
		try {
			configList = configService.queryConfigInfo(pd);
		} catch (Exception e) {		
			logger.error(e.toString(),e);
		}  
		
		if(null != configList){
			for(int i = 0; i < configList.size(); i++){
	 			//获取系统名称
	 			if("BACKGROUND".equals(configList.get(i).get(KEYNAME) + "") && null != configList.get(i).get(VALUE)){
	 				sc.getServletContext().setAttribute("sysTitle", configList.get(i).get(VALUE) + "");
	 			}
	 			//获取邮箱发送者的名称
	 			if("emailName".equals(configList.get(i).get(KEYNAME) + "") && null != configList.get(i).get(VALUE)){
	 				Const.EMAIL_NAME = configList.get(i).get(VALUE) + "";
	 			}
	 			//获取邮箱协议
	 			if("emailProtocol".equals(configList.get(i).get(KEYNAME) + "") && null != configList.get(i).get(VALUE)){
	 				Const.EMAILPROTOCOL = configList.get(i).get(VALUE) + "";
	 			}
	 			//获取邮箱端口
	 			if("emailPort".equals(configList.get(i).get(KEYNAME) + "") && null != configList.get(i).get(VALUE)){
	 				Const.EMAILPORT = Integer.valueOf(configList.get(i).get(VALUE) + "");
	 			}
	 			//获取邮箱用户
	 			if("emailUser".equals(configList.get(i).get(KEYNAME) + "") && null != configList.get(i).get(VALUE)){
	 				Const.EMAILUSER = configList.get(i).get(VALUE) + "";
	 			}
	 			//获取邮箱密码
	 			if("emailPassword".equals(configList.get(i).get(KEYNAME) + "") && null != configList.get(i).get(VALUE)){
	 				Const.EMAILPASSWORD = configList.get(i).get(VALUE) + "";
	 			}
			}			
		}
		
		//查询敏感词到内存中
		try {
			pd=new PageData();
			pd.put("STATUS", "1");
			keywordService.queryKeyword(pd);
		} catch (Exception e) {
			
		}
	}
}
