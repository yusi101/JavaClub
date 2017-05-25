package com.JavaClub.controller.Interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.Interface.PledgeService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**
 * @descript 得到股权出质登记信息
 * @author 李海涛
 * @since 2016年9月9日上午9:09:29
 *
 */
@Controller
@RequestMapping(value="/Interface/pledgeInterface")
public class PledgeInterface extends BaseController{
	
	@Autowired
	public PledgeService pledgeService;

	
	/**
	 * 
	 * @descript 得到股权出质登记信息，参数中有pageSize则代表分页
	 * @author 李海涛
	 * @since 2016年9月9日上午9:09:29
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPledgeInfo", produces = "text/html;charset=UTF-8")
	public String queryPledgeInfo() throws Exception{
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
            Verification.DecodeKeyNo(pd, "pripid");
            
			List<Map<String, Object>> pledgeInfo=new ArrayList<>();
			
			//如果pageSize存在，并且大于1，则该接口分页，否则不分页
	        if(StringUtil.isInt(pd.getString("pageSize"))){
	            Page page = new Page();
	            Verification.SetPageList(pd, page);//设置page的分页
	            pledgeInfo= pledgeService.queryPledgeInfo(page);
	            Verification.getPageMessage(resultMap, page);
	        }else{
	            pledgeInfo= pledgeService.queryPledgeInfo(pd);
	        }
	        MapReplaceUtils.handleLsitMapData(pledgeInfo);
			for (int i = 0; i < pledgeInfo.size(); i++) {
			    Map<String, Object>  pledgeInfo_map=pledgeInfo.get(i);
			    pledgeInfo_map.put("PLEDBLICNO",  StringUtil.replace((String)pledgeInfo_map.get("PLEDBLICNO")));
			    pledgeInfo_map.put("IMPAM",  "***");
			    pledgeInfo_map.put("IMPORGBLICNO",  StringUtil.replace((String)pledgeInfo_map.get("IMPORGBLICNO")));
			    pledgeInfo.set(i, pledgeInfo_map);
            }
			
			resultMap.put("pledgeInfo",pledgeInfo);
		}catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}
	
	
}
