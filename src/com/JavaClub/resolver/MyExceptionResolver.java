package com.JavaClub.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.util.Logger;
/**
 * 
* 类名称：MyExceptionResolver.java
* 类描述： 
* @author FH
* 作者单位： 
* 联系方式：QQ313596790
* @version 1.0
 */
public class MyExceptionResolver implements HandlerExceptionResolver{

	protected static Logger logger = Logger.getLogger(MyExceptionResolver.class);
	
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		System.out.println("==============异常开始=============");
		logger.error(ex.toString(),ex);
		System.out.println("==============异常结束=============");
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", ex.toString().replaceAll("\n", "<br/>"));
		return mv;
	}

}
