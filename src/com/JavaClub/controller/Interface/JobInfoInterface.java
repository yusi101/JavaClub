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
import com.JavaClub.service.Interface.JobInfoService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**
 * 招聘
 * @author 李海涛
 * @param request
 * @param response
 * @return
 * @throws Exception
 */

@Controller
@RequestMapping(value="/Interface/jobInfoInterface")
public class JobInfoInterface extends BaseController{
	 
	
	@Autowired
	private JobInfoService jobInfoService;
	
	
	/**
	 * 得到企业招聘
	 * @author 李海涛
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryJobInfo",produces = "text/html;charset=UTF-8")
	public String queryJobInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
		try {
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@pripid");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "entname");
            List<Map<String, Object>> jobInfo=new ArrayList<>();
            
            //如果pageSize存在，并且大于1，则该接口分页，否则不分页
            if(StringUtil.isInt(pd.getString("pageSize"))){
                  Page page = new Page();
                    Verification.SetPageList(pd, page);//设置page的分页
                    jobInfo= jobInfoService.queryJobInfo(page);
                    Verification.getPageMessage(resultMap, page);
            }else{
                jobInfo= jobInfoService.queryJobInfo(pd);
            }
            MapReplaceUtils.handleLsitMapData(jobInfo);
            resultMap.put("jobInfo",jobInfo);
		}catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}

}
