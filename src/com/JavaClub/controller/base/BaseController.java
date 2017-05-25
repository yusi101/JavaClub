package com.JavaClub.controller.base;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.entity.Page;
import com.JavaClub.entity.system.User;
import com.JavaClub.util.Const;
import com.JavaClub.util.Logger;
import com.JavaClub.util.PageData;
import com.JavaClub.util.UuidUtil;

public class BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		return request;
	}

	/**
	 * 得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		
		return UuidUtil.get32UUID();
	}
	
	/**
     * 得到当前用户
     * @return
     */
    public User getUser(){
      Subject currentUser = SecurityUtils.getSubject();  
      Session session = currentUser.getSession();
      User user = (User)session.getAttribute(Const.SESSION_USER);
      return user;
    }
    
    /**
     * 得到session
     * @return
     */
    public Session getSession(){
      Subject currentUser = SecurityUtils.getSubject();  
      Session session = currentUser.getSession();
      return session;
    }
    
    
    /**
     * 
     * @descript (得到服务器的路径)
     * @author 李海涛
     * @since 2016年9月19日下午5:12:27
     * @param path 服务器的相对路径 WebContent为跟目录
     * @return
     */
    public String getServicePath(String path){
         path = this.getRequest().getServletContext().getRealPath(path);
      return path;
    }
    
    
    /**
     * 
     * @descript (得到物理的路径)
     * @author 李海涛
     * @since 2016年9月19日下午5:12:05
     * @param path 物理路径名称 
     * @return
     */
    public String getPath(String path){
         path = path.replaceAll(":", File.separator).replaceAll("/", File.separator);
      return path;
    }
    
	/**
	 * 得到分页列表的信息 
	 */
	public Page getPage(){
		
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	
	
}
