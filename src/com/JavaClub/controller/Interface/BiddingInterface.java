
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
import com.JavaClub.service.Interface.BaseinfoService;
import com.JavaClub.service.Interface.BiddingService;
import com.JavaClub.util.EnumUtil;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**
 * @ClassName:     BiddingInterface
 * @Description: 查询招标信息
 * @author:    Android_Robot
 * @date:        2016年9月14日 上午9:22:34
 *
 */
@Controller
@RequestMapping(value="/Interface/biddingInterface")
public class BiddingInterface extends BaseController{

	@Autowired
	public BiddingService biddingService;
	@Autowired
	public BaseinfoService baseinfoService;
	
	@ResponseBody
	@RequestMapping(value = "/queryBinddingInfo", produces = "text/html;charset=UTF-8")
	public String queryBinddingInfo() throws Exception{
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
			List<Map<String, Object>> biddingInfo=new ArrayList<>();
			
			//如果pageSize存在，并且大于1，则该接口分页，否则不分页
	        if(StringUtil.isInt(pd.getString("pageSize"))){
	        	Page page = new Page();
	            Verification.SetPageList(pd, page);//设置page的分页
	            biddingInfo= biddingService.queryBiddingInfo(page);
	            Verification.getPageMessage(resultMap, page);
	        }else{
	            biddingInfo= biddingService.queryBiddingInfo(pd);
	        }
	        
			MapReplaceUtils.handleLsitMapData(biddingInfo);
			resultMap.put("biddingInfo",biddingInfo);
			
			//记录招标搜索关键词
			baseinfoService.saveKeywordLog(pd, pd.getString("entname"),new String[]{"biddingId"}, EnumUtil.SEARCH_BIDDING.getName());
		}catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}
}
 