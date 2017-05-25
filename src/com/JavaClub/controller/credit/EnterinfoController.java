package com.JavaClub.controller.credit;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.Interface.EnterinfoService;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.PageData;
import com.google.gson.Gson;

/**
 * @descript 查询行业门类的总数
 * @author 李坡
 * @createTime 2016年9月21日下午3:21:41
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/enterinfoController")
public class EnterinfoController extends BaseController{

	@Autowired
	public EnterinfoService enterinfoService;


	/**
	 *
	 * @descript 查询行业门类的总数
	 * @author 李坡
	 * @since 2016年9月21日下午3:26:02
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEnterinfo", produces = "text/html;charset=UTF-8")
	public ModelAndView queryEnterinfo() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		Gson g=new Gson();
		List<Map<String,Object>> listpage = enterinfoService.queryEnterInfo(pd);

		String modulename = "";
		String classifyCount = "";
		String yuan="";
		if(ListUtils.isNotEmpty(listpage)){
			for (Map<String, Object> map : listpage) {
				modulename += "'"+map.get("NAME") + "',";
				classifyCount += map.get("COUNT") + ",";
				String name = "'"+map.get("NAME") + "',";
				//饼图所需要的格式
				yuan +="["+name+map.get("COUNT")+"],";
			}

			modulename = modulename.substring(0,modulename.length()-1);
			classifyCount = classifyCount.substring(0,classifyCount.length()-1);
		}
		long sum=0;
		for(int i=0;i<listpage.size();i++){
			sum=sum+(long)listpage.get(i).get("COUNT");
		}
		pd.put("sum", sum);
		String list=g.toJson(listpage);
		mv.addObject("modulename", modulename);
		mv.addObject("classifyCount", classifyCount);
		mv.addObject("returnMap", list);
		mv.addObject("listpages", listpage);
		mv.addObject("pd", pd);
		mv.addObject("yuan", yuan);
		mv.setViewName("code/code_list");
		return mv;
	}
}
