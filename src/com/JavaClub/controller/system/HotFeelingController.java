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

import com.JavaClub.annotation.system.log.SysUserLog;
import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.AjaxPage;
import com.JavaClub.entity.Page;
import com.JavaClub.service.credit.HotFeelingService;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.PageData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * @descript (舆情热度控制器)
 * @author 汤彬
 * @createTime 2016年9月30日下午2:28:41
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/hotFeelingController")
public class HotFeelingController extends BaseController {

	@Autowired
	public HotFeelingService hotFeelingService;

	/**
	 * 
	 * @descript (舆情热度)
	 * @author 汤彬
	 * @since 2016年9月30日下午2:33:14
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHotFeeling", produces = "text/html;charset=UTF-8")
	@SysUserLog("舆情热度分析")
	public ModelAndView queryHotFeeling() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<Map<String, Object>> hotlistList = new ArrayList<Map<String, Object>>();
		if (("2").equals(pd.getString("select"))) {
			/* 舆情热度分析--投诉 */
			hotlistList = hotFeelingService.queryComplatntInfo(pd);
		} else if (("3").equals(pd.getString("select"))) {
			/* 舆情热度分析--评论 */
			hotlistList = hotFeelingService.queryCommentInfo(pd);
		} else {
			/* 舆情热度分析--关注 */
			hotlistList = hotFeelingService.queryFollowInfo(pd);
		}

		String entName = "";
		String count = "";
		String pj = "";
		if (ListUtils.isNotEmpty(hotlistList)) {
			for (Map<String, Object> map : hotlistList) {
				entName += "'" + map.get("ENTNAME") + "',";
				count += map.get("COUNT") + ",";
				String entNames = "'" + map.get("ENTNAME") + "'";
				String counts = map.get("COUNT").toString();
				String pripid = map.get("PRIPID").toString();
				pj += "[" + entNames + "," + counts + "," + pripid + "],";
			}
			entName = entName.substring(0, entName.length() - 1);
			count = count.substring(0, count.length() - 1);
		}
		mv.addObject("hotlistList", hotlistList);
		mv.addObject("entName", entName);
		mv.addObject("count", count);
		mv.addObject("queryll", pj);
		mv.addObject("pd", pd);
		mv.setViewName("hotfeeling/hotfeeling_list");
		return mv;
	}

	/**
	 * 
	 * @descript (舆情热度分析详情)
	 * @author 汤彬
	 * @since 2016年9月30日下午5:04:30
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHotFeelingDetail", produces = "text/html;charset=UTF-8")
	@SysUserLog("点击舆情热度分析详情")
	@ResponseBody
	public String queryHotFeelingDetail(Page page) throws Exception {
		Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd").create();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<Map<String, Object>> hotlistList = new ArrayList<Map<String, Object>>();
		if (("2").equals(pd.getString("select"))) {
			/* 舆情热度分析--投诉 */
			hotlistList = hotFeelingService.queryComplatntDetailInfo(page);
		} else if (("3").equals(pd.getString("select"))) {
			/* 舆情热度分析--评论 */
			hotlistList = hotFeelingService.queryCommentDetailInfo(page);
		} else {
			/* 舆情热度分析--关注 */
			hotlistList = hotFeelingService.queryFollowDetailInfo(page);
		}
		String list = gson.toJson(hotlistList);
		return list;
	}

	/**
	 * 查询热点分析
	 * 
	 * @category @param page
	 * @author 林恒
	 * @time 2016.06.21
	 */
	@RequestMapping(value = "/queryHotspotAnalysis", produces = "text/html;charset=UTF-8")
	@SysUserLog("查询热点分析")
	public ModelAndView queryHotspotAnalysis() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<Map<String, Object>> Hotlistpage = new ArrayList<Map<String, Object>>();
		String name = "ENTNAME";
		if (("2").equals(pd.getString("select"))) {
			Hotlistpage = hotFeelingService.queryHotspotAnalysis(pd);
			name = "KEYWORDS";
		} else {
			Hotlistpage = hotFeelingService.queryHotEnterprise(pd);
		}
		List<Map<String, Object>> listEchars = new ArrayList<Map<String, Object>>();
		List<Object> listEcharsName = new ArrayList<Object>();
		for (Map<String, Object> map : Hotlistpage) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("value", map.get("COUNT"));
			m.put("name", map.get(name));
			m.put("pripid", map.get("PRIPID"));
			listEchars.add(m);
			listEcharsName.add(map.get(name));
		}
		Gson g = new Gson();
		String jsonEchars = g.toJson(listEchars);
		String jsonEcharsName = g.toJson(listEcharsName);
		mv.addObject("listEchars", jsonEchars);
		mv.addObject("EcharsName", jsonEcharsName);
		mv.addObject("Hotlistpage", Hotlistpage);
		mv.addObject("pd", pd);
		mv.setViewName("hotfeeling/hotspotanalysis");
		return mv;
	}

	/**
	 * 点击查询热点分析详情
	 * 
	 * @category @param page
	 * @author 胡华锋
	 * @time 2016.06.21
	 */
	@RequestMapping(value = "/queryHotspotAnalysisList", produces = "text/html;charset=UTF-8")
	@SysUserLog("点击查询热点分析详情")
	@ResponseBody
	public String queryHotspotAnalysisList() throws Exception {
		Page page = new Page();
		PageData pd = new PageData();
		pd = this.getPageData();
		page = AjaxPage.setAjaxPage(pd, page);
		List<Map<String, Object>> Hotlistpage = new ArrayList<Map<String, Object>>();
		if (("2").equals(pd.getString("select"))) {
			Hotlistpage = hotFeelingService.queryHotspotAnalysislistPage(page);
		} else {
			Hotlistpage = hotFeelingService.queryHotEnterpriselistPage(page);
		}
		return AjaxPage.toJson(Hotlistpage, page);
	}
}
