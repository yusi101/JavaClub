package com.JavaClub.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.AjaxPage;
import com.JavaClub.entity.Page;
import com.JavaClub.service.Interface.OperationService;
import com.JavaClub.util.DateUtil;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.PageData;
import com.google.gson.Gson;

/**
 * @descript (用一句话描述改方法的作用)
 * @author 李坡
 * @createTime 2016年9月20日下午4:10:46
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/OperationController")
public class OperationController extends BaseController{

	@Autowired
	public OperationService operationService;

	/**
	 *
	 * @descript  分类信息统计详情
	 * @author 李坡
	 * @since 2016年9月20日下午4:13:13
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryClassifyInfoXQ", produces = "text/html;charset=UTF-8")
	public String queryClassifyInfoXQ() throws Exception {
		PageData pd=this.getPageData();
		Gson g = new Gson();

		List<Map<String, Object>> classifyInfoXQ = operationService.queryClassifyInfoXQ(pd);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		String entname = "";
		if(ListUtils.isNotEmpty(classifyInfoXQ)){
			for (Map<String, Object> map : classifyInfoXQ) {
				entname += "'"+map.get("name") + "',";
			}
			entname = entname.substring(0,entname.length()-1);
			dataMap.put("entname", "["+entname+"]");
			dataMap.put("modulename", pd.getString("modulename"));
			dataMap.put("returnMap", classifyInfoXQ);
		}
		return g.toJson(dataMap);
	}


	/**
	 *
	 * @descript 查询每个公司的 访问量
	 * @author 李坡
	 * @since 2016年9月20日下午4:13:27
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/sysOperationlogDetailslistPage",produces = "text/html;charset=UTF-8")
	public String sysOperationlogDetailslistPage(Page page)throws Exception{
		PageData pd=this.getPageData();
		page=AjaxPage.setAjaxPage(pd, page);
		List<PageData> list=operationService.sysOperationlogDetailslistPage(page);
		return AjaxPage.toJson(list, page);

	}


	/**
	 *
	 * @descript 分类信息总量统计
	 * @author 李坡
	 * @since 2016年9月21日上午11:03:08
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryClassifyInfo", produces = "text/html;charset=UTF-8")
	public ModelAndView queryClassifyInfo() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();

		List<Map<String,Object>> listpage = operationService.queryClassifyInfo(pd);

		String modulename = "";
		String classifyCount = "";
		String pj="";
		if(ListUtils.isNotEmpty(listpage)){
			for (Map<String, Object> map : listpage) {
				modulename += "'"+map.get("modulename") + "',";
				classifyCount += map.get("classifyCount") + ",";
				String modulenames = "'"+map.get("modulename") + "'";
				String classifyCounts = map.get("classifyCount").toString();
				pj += "["+modulenames+","+classifyCounts+"],";
			}
			modulename = modulename.substring(0,modulename.length()-1);
			classifyCount = classifyCount.substring(0,classifyCount.length()-1);
		}
		mv.addObject("modulename", modulename);
		mv.addObject("classifyCount", classifyCount);
		mv.addObject("queryll", pj);
		mv.addObject("pd", pd);
		mv.setViewName("operation/operation_list");
		return mv;
	}


	/**
	 *
	 * @descript 系统流量分析
	 * @author 李坡
	 * @since 2016年9月21日上午11:05:34
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySystemFlowAnalysis", produces = "text/html;charset=UTF-8")
	public ModelAndView querySystemFlowAnalysis() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		List<String> datelist=new ArrayList<String>();
		if(("3").equals(pd.getString("select"))){
			pd.put("SimpleDateFormat", "%Y-%m");
			datelist=DateUtil.getMonthBetweenTwoDate(pd.getString("startTime"), pd.getString("endTime"));
			pd.put("startDate", pd.getString("startTime"));
			pd.put("endDate", pd.getString("endTime"));
			pd.put("startTime", pd.getString("startTime")+"-00");
			pd.put("endTime", pd.getString("endTime")+"-"+DateUtil.getDayByYearAndMonth(pd.getString("endTime")));
			
		}else if(("2").equals(pd.getString("select"))){
			pd.put("SimpleDateFormat", "%Y-%m-%d");
			datelist=DateUtil.getDaysBetweenTwoDate(pd.getString("startTime"), pd.getString("endTime"));
		}else if(("1").equals(pd.getString("select"))){
			pd.put("SimpleDateFormat", "%Y-%m-%d %H");
			datelist=DateUtil.getHoursBetweenTwoDate(pd.getString("startTime"), pd.getString("endTime"));
		}else{
			pd.put("SimpleDateFormat", "%Y-%m-%d %H");
			datelist=DateUtil.getHoursBetweenTwoDate(pd.getString("startTime"), pd.getString("endTime"));
		}
		List<Map<String,Object>> listpage = operationService.querySystemFlowAnalysis(pd);

		List<Integer> countlist=new ArrayList<Integer>();
		for (int i = 0; i < datelist.size(); i++) {
			countlist.add(0);
		}
		for (int i = 0; i < listpage.size(); i++) {
			Map<String,Object> map=listpage.get(i);
			if(datelist.indexOf(map.get("TIME"))>=0){
				countlist.set(datelist.indexOf(map.get("TIME")), Integer.parseInt(map.get("COUNT").toString()));
			}
		}
		Gson gson=new Gson();
		mv.addObject("timelist", gson.toJson(datelist));
		mv.addObject("datelist", datelist);
		mv.addObject("countlist", countlist);
		mv.addObject("pd", pd);
		mv.setViewName("operation/sys_operation");
		return mv;
	}

	/**
	 *
	 * @descript 点击系统流量分析详情
	 * @author 李坡
	 * @since 2016年9月21日上午11:03:17
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySystemFlowAnalysislistPage", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String querySystemFlowAnalysislistPage() throws Exception {
		Page page=new Page();
        PageData pd = new PageData();
        pd = this.getPageData();
        if(("3").equals(pd.getString("select"))){
            pd.put("SimpleDateFormat", "%Y-%m");
            pd.put("logTime", pd.getString("logTime")+"-"+DateUtil.getDayByYearAndMonth(pd.getString("logTime")));
        }else if(("2").equals(pd.getString("select"))){
            pd.put("SimpleDateFormat", "%Y-%m-%d");
        }else{
            pd.put("SimpleDateFormat", "%Y-%m-%d %H");

        }
        page=AjaxPage.setAjaxPage(pd, page);
        List<Map<String, Object>> list = operationService.queryOperationlistPage(page);
        return AjaxPage.toJson(list, page);
	}

}
