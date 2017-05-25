package com.JavaClub.controller.Interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.Interface.BusinessService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

@Controller
@RequestMapping(value="/Interface/businessInterface")
public class BusinessInterface extends BaseController{
	
	
	@Autowired
	private BusinessService businessService;
	
	/**
	 * 
	 * @descript (查看我的历史查询)
	 * @author 李海涛
	 * @since 2016年9月23日上午9:02:26
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHistoryInfo", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryMyHistoryInfo() throws Exception {
	    PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
		try {
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
			if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            List<Map<String, Object>> historyInfo=new ArrayList<>();
            Verification.DecodeKeyNo(pd, "memberId");
            //如果pageSize存在，并且大于1，则该接口分页，否则不分页
            if(StringUtil.isInt(pd.getString("pageSize"))){
                Page page = new Page();
                Verification.SetPageList(pd, page);//设置page的分页
                historyInfo= businessService.queryHistoryInfo(page);
                Verification.getPageMessage(resultMap, page);
            }else{
                historyInfo= businessService.queryHistoryInfo(pd);
            }
            resultMap.put("historyInfo", historyInfo);
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		
		return MyGson.toJson(map);
	}

	
	
	
	/**
	 * 得到搜索历史
	 * @author 李海涛
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/querySearchHistoryInfo",produces = "text/html;charset=UTF-8")
	public String querySearchHistoryInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
	    PageData pd =  this.getPageData();
	    Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
		try {
		    String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "memberId");
            List<Map<String, Object>> searchHistory=new ArrayList<>();
            if(StringUtil.isInt(pd.getString("pageSize"))){
                Page page = new Page();
                Verification.SetPageList(pd, page);//设置page的分页
                searchHistory= businessService.querySearchHistoryInfo(page);
                Verification.getPageMessage(resultMap, page);
            }else{
                searchHistory= businessService.querySearchHistoryInfo(pd);
            }
            resultMap.put("searchHistory", searchHistory);
		}catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}
	
	/**
	 * 得到最多搜索的关键词（前10条）
	 * @author 李海涛
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getHotspotAnalysis",produces = "text/html;charset=UTF-8")
	public String getHotspotAnalysis(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Page page=new Page();
	    PageData pd = this.getPageData();
		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultdata=new HashMap<>();
		try {
		    String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "memberId");
			Verification.SetPageList(pd, page);//设置page的分页
            page.setPd(pd);
			List<Map<String,Object>> hotspotAnalysis=businessService.queryHotspotAnalysislistPage(page);
			MapReplaceUtils.handleLsitMapData(hotspotAnalysis);
			resultdata.put("hotspotAnalysis",hotspotAnalysis);
			map.put("data", resultdata);
		}catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}
	
	
	/**
     * 保存用户发聩
     * 李海涛
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/saveOpinion", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveOpinion() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultdata=new HashMap<>();
		try {
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
			if(!VerificationParameter.equals("")){
				return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
			}
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}
			pd.put("id", get32UUID());
			pd.put("memberId",  pd.getString("KeyNo"));
			map.put("data",resultdata);
			String result = businessService.saveOpinion(pd);
			resultdata.put("result", result);
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		
		return MyGson.toJson(map);
	}
}
