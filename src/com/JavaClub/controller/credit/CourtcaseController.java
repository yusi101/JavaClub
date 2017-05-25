
package com.JavaClub.controller.credit;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.util.Connect;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * @Description:   TODO 得到失信被执行人信息
 * @author          李坡
 * @version         V1.0  
 * @Date           2016年9月12日 上午11:24:09 
 */
@Controller
@RequestMapping(value="/courtcaseController")
public class CourtcaseController extends BaseController{
	/**
	 * @Title: getCourt
	 * @Description: TODO 得到失信被执行人信息
	 * @param: 李坡
	 * @param: @throws Exception   
	 * @return: ModelAndView   
	 * @throws
	 */
	@RequestMapping(value = "/queryCourtinfo", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView queryCourtinfo() throws Exception{
		ModelAndView mv =  new ModelAndView("courtcase/court_list");
		PageData pd = new PageData();
		pd=this.getPageData();

		//设置接口的加密
		Verification.EncodeKeyNo(pd, "iname");
		//设置分页的页码和每页显示的条数
		Verification.setPageParameter(pd);
		//调用失信被执行人信息接口
		Map<String, Object> QueryCurt = Connect.sendConnectByPdToMap("Interface/courtcaseInterface/queryCourtcaseInfo", pd, "POST");
		Verification.DecodeKeyNo(pd, "iname");
		//判断接口调用是否成功
		if(Verification.StatusIsSuccess(QueryCurt)){
			//得到商标信息JSON数据中的data数据
			@SuppressWarnings("unchecked")
			Map<String,Object> dataMap_court = (Map<String, Object>) QueryCurt.get("data");
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> court = (List<Map<String, Object>>) dataMap_court.get("courtcaseInfo");
			mv.addObject("court",court);
			//分页的拼接
			Page page=Verification.getPage(dataMap_court);
			mv.addObject("page", page);
		}

		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * @author 李坡
	 * @Title: findCourtinfo
	 * @Description: TODO 失信被执行人详细信息
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: ModelAndView   
	 * @throws
	 */
	@RequestMapping(value = "/queryCourtById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView findCourtinfo() throws Exception{
		ModelAndView mv =  new ModelAndView("courtcase/court_detail");
		PageData pd = new PageData();
		pd=this.getPageData();

		//设置接口的加密
		Verification.EncodeKeyNo(pd, "iname");
		//调用查询商标接口接口
		Map<String, Object> queryCourt = Connect.sendConnectByPdToMap("Interface/courtcaseInterface/queryCourtcaseInfo", pd, "POST");
		Verification.DecodeKeyNo(pd, "iname");
		//判断接口调用是否成功
		if(Verification.StatusIsSuccess(queryCourt)){
			//得到商标信息JSON数据中的data数据
			@SuppressWarnings("unchecked")
			Map<String,Object> dataMap_court = (Map<String, Object>) queryCourt.get("data");
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> court = (List<Map<String, Object>>) dataMap_court.get("courtcaseInfo");
			Map<String, Object> map_court= court.get(0);
			mv.addObject("court",map_court);
		}

		mv.addObject("pd", pd);
		return mv;
	}

}
