package com.JavaClub.controller.Interface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.credit.InvestmentService;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript 对外投资
 * @author 龚志强
 * @createTime 2016年9月14日下午3:01:38
 * @version 1.0
 */
@Controller
@RequestMapping(value="/Interface/investmentInterface")
public class investmentInterface extends BaseController{
	
	//投资全景业务层
	@Resource(name = "InvestmentService")
	private InvestmentService investmentService;
	
	/**
	 * 
	 * @descript 对外投资
	 * @author 龚志强
	 * @since 2016年9月14日下午3:01:48
	 * @return 对外投资集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryInvestment", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryInvestment() throws Exception {
		PageData pd = new  PageData();
		pd = this.getPageData();	
		
		Map<String,Object> map = Verification.Success();
		Map<String,Object> resultdata = new HashMap<>();
		
		//初始化数据
		try {			
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");

			if(!"".equals(VerificationParameter)){
				return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
			}
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}
			pd.put("pripid", pd.getString("KeyNo"));
			List investmentList = investmentService.queryPerhapsRelation(pd);

			//企业担任其他企业法定代表人的企业信息
			List<Map<String,Object>> invList1 = ListUtils.getListIndex(investmentList, 0);
			MapReplaceUtils.handleLsitMapData(invList1);
			resultdata.put("invList1", invList1);		

			//企业任职的企业信息
			List<Map<String,Object>> invList2 = ListUtils.getListIndex(investmentList, 1);
			MapReplaceUtils.handleLsitMapData(invList2);
			resultdata.put("invList2", invList2);

			//企业对外投资的企业信息
			List<Map<String,Object>> invList3 = ListUtils.getListIndex(investmentList, 2);
			MapReplaceUtils.handleLsitMapData(invList3);
			resultdata.put("invList3", invList3);
			
			//法定代表人担任其他企业法定代表人的企业信息
			List<Map<String,Object>> invList4 = ListUtils.getListIndex(investmentList, 3);
			MapReplaceUtils.handleLsitMapData(invList4);
			resultdata.put("invList4", invList4);
			
			//法定代表人任职的企业信息
			List<Map<String,Object>> invList5 = ListUtils.getListIndex(investmentList, 4);
			MapReplaceUtils.handleLsitMapData(invList5);
			resultdata.put("invList5", invList5);
			
			//法定代表人对外投资的企业信息
			List<Map<String,Object>> invList6 = ListUtils.getListIndex(investmentList, 5);
			MapReplaceUtils.handleLsitMapData(invList6);
			resultdata.put("invList6", invList6);
			
			//主要人员担任法定代表人的企业信息
			List<Map<String,Object>> invList7 = ListUtils.getListIndex(investmentList, 6);
			MapReplaceUtils.handleLsitMapData(invList7);
			resultdata.put("invList7", invList7);
			
			//主要人员任职的企业信息
			List<Map<String,Object>> invList8 = ListUtils.getListIndex(investmentList, 7);
			MapReplaceUtils.handleLsitMapData(invList8);
			resultdata.put("invList8", invList8);
			
			//主要人员对外投资的企业信息
			List<Map<String,Object>> invList9 = ListUtils.getListIndex(investmentList, 8);
			MapReplaceUtils.handleLsitMapData(invList9);
			resultdata.put("invList9", invList9);
			
			//股东担任法定代表人的企业信息
			List<Map<String,Object>> invList10 = ListUtils.getListIndex(investmentList, 9);
			MapReplaceUtils.handleLsitMapData(invList10);
			resultdata.put("invList10", invList10);
			
			//股东任职的企业信息
			List<Map<String,Object>> invList11 = ListUtils.getListIndex(investmentList, 10);
			MapReplaceUtils.handleLsitMapData(invList11);
			resultdata.put("invList11", invList11);
			
			//股东对外投资的企业信息
			List<Map<String,Object>> invList12 = ListUtils.getListIndex(investmentList, 11);
			MapReplaceUtils.handleLsitMapData(invList12);
			resultdata.put("invList12", invList12);

			map.put("data", resultdata);			
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}

		return MyGson.toJson(map);
	}
}