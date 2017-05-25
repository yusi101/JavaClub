
package com.JavaClub.controller.credit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @descript  地图专用
 * @author 李坡
 * @createTime 2016年9月21日下午2:25:52
 * @version 1.0
 */

@Controller("/EnterinfomapController")
public class EnterinfomapController {

	/**
	 * 
	 * @descript  跳转到地图
	 * @author 李坡
	 * @since 2016年9月21日下午2:25:00
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEnterinfomap", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView goEnterinfomap() throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("enterprise/enterinfomap");
		return mv;
	}
}
 