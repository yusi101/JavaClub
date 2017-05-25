package com.JavaClub.controller.credit;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.credit.DataMonService;
import com.JavaClub.util.DateUtil;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript 展示数据更新情况
 * @author 余思
 * @createTime 2017年4月25日上午11:54:59
 * @version 1.0
 */
@Controller
@RequestMapping(value="/dataMonController")
public class DataMonController  extends BaseController{
	
	
	@Autowired
	private DataMonService dataMonService;
	/**
	 * 
	 * @descript 统计企业表 个体表 新企业表 三个表的数据更新情况
	 * @author 余思
	 * @since 2017年4月25日下午1:58:47
	 * @param page
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/querydataMon", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView querydataMon(Page page) throws Exception {
        ModelAndView mv=new ModelAndView("datamon/datamon_list");
        PageData pd = this.getPageData();
        if(null == pd.get("startDay") || "".equals(pd.get("startDay"))){
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //设置日期格式
        	pd.put("startDay", sdf.format(DateUtil.getStartTime()));
        	pd.put("endDay", sdf.format(DateUtil.getEndTime()));
        }
        //List<Map<String, Object>> enterpriseInfo = dataMonService.queryDataMonqy(pd);
       // List<Map<String, Object>> individualInfo = dataMonService.queryDataMongt(pd);
        List<Map<String, Object>> newentInfo = dataMonService.queryDataMonnew(pd);
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		//mv.addObject("enterpriseInfo", enterpriseInfo);
		//mv.addObject("individualInfo", individualInfo);
		mv.addObject("newentInfo", newentInfo);
		return mv;
	}
    /**
     * 
     * @descript 统计 新企业表 7天数据更新情况
     * @author 余思
     * @since 2017年5月15日下午5:55:50
     * @param page
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/queryNewSevenDaysData", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryNewSevenDaysData(Page page) throws Exception {
        ModelAndView mv=new ModelAndView("datamon/sevendata");
        PageData pd = this.getPageData();
        String dataMon = dataMonService.queryNewSevenDaysData(pd);
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject("data", dataMon.split("@")[0]);
		mv.addObject("nums", dataMon.split("@")[1]);
		return mv;
	}
}
