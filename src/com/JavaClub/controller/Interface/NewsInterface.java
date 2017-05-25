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
import com.JavaClub.service.Interface.NewsService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**
 * 新闻类型
 * @author 李海涛
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
@Controller
@RequestMapping(value="/Interface/newsInterface")
public class NewsInterface extends BaseController{
	
	@Autowired
	private NewsService newsService;
	
	
	/**
	 * 得到最新资讯
	 * @author 李海涛
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryNewInformation",produces = "text/html;charset=UTF-8")
	public String getNewslistPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
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
            Verification.DecodeKeyNo(pd, "contentId");
            List<Map<String, Object>> newInformation=new ArrayList<>();
            
            //如果pageSize存在，并且大于1，则该接口分页，否则不分页
            if(StringUtil.isInt(pd.getString("pageSize"))){
                Page page = new Page();
                Verification.SetPageList(pd, page);//设置page的分页
                newInformation= newsService.queryNewInformation(page);
                Verification.getPageMessage(resultMap, page);
            }else{
                newInformation= newsService.queryNewInformation(pd);
            }
            
            MapReplaceUtils.handleLsitMapData(newInformation);
            resultMap.put("newInformation",newInformation);
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
	}
	
	/**
	 * 得到企业新闻
	 * @author 李海涛
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryEntNewsInfo",produces = "text/html;charset=UTF-8")
	public String queryEntNewsInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
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
            Verification.DecodeKeyNo(pd, "entname");
            List<Map<String, Object>> entNewsInfo=new ArrayList<>();
            
            //如果pageSize存在，并且大于1，则该接口分页，否则不分页
            if(StringUtil.isInt(pd.getString("pageSize"))){
                Page page = new Page();
                Verification.SetPageList(pd, page);//设置page的分页
                entNewsInfo= newsService.queryEntNewsInfo(page);
                Verification.getPageMessage(resultMap, page);
            }else{
                entNewsInfo= newsService.queryEntNewsInfo(pd);
            }
            
            MapReplaceUtils.handleLsitMapData(entNewsInfo);
            resultMap.put("entNewsInfo",entNewsInfo);
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
	}
	
	
}
