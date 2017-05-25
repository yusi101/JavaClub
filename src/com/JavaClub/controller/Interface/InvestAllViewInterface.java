package com.JavaClub.controller.Interface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.Interface.InvestAllViewService;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript 投资链图
 * @author 龚志强
 * @createTime 2016年9月14日下午3:01:38
 * @version 1.0
 */
@Controller
@RequestMapping(value="/Interface/investAllViewInterface")
public class InvestAllViewInterface extends BaseController{
	
	//投资全景业务层
	@Resource(name = "investAllViewService")
	private InvestAllViewService investAllViewService;
	
	/**
	 * 
	 * @descript 投资链图
	 * @author 龚志强
	 * @since 2016年9月14日下午3:01:48
	 * @return 投资链图集合
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/queryInvestAllView", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryInvestAllView() throws Exception {
		PageData pd = new  PageData();
		pd = this.getPageData();	
		
		Map<String,Object> map = Verification.Success();
		Map<String,Object> resultdata = new HashMap<>();
		
		//初始化数据
		try {			
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@regno");

			if(!"".equals(VerificationParameter)){
				return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
			}
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}
			pd.put("pripid", pd.getString("KeyNo"));
			List investAllViewList = investAllViewService.queryInvestAllView(pd);

			//自然人
			List<Map<String,Object>> npList = ListUtils.getListIndex(investAllViewList, 0);
			MapReplaceUtils.handleLsitMapData(npList);
			resultdata.put("npList", npList);		

			//自然人对外投资
			Map<String,Object> npMap2 = null;
			List<Map<String,Object>> npList2 = null;
			if(null != npList && !npList.isEmpty()){
				npMap2 = new HashMap<>();
				for(int i = 0; i < npList.size(); i++){
					if(null == npList.get(i).get("CERNO")){
						continue;
					}
					npList2 = investAllViewService.queryNameAllEnt(npList.get(i).get("CERNO") + "");
					npMap2.put(npList.get(i).get("CERNO") + "", npList2);				
				}			
			}
			MapReplaceUtils.handleMapData(npMap2);	
			resultdata.put("npMap2", npMap2);

			//非自然人 
			List<Map<String,Object>> nnpList = ListUtils.getListIndex(investAllViewList, 1);
			MapReplaceUtils.handleLsitMapData(nnpList);
			resultdata.put("nnpList", nnpList);	
		
			//非自然人对外投资
			Map<String,Object> nnpMap2 = null;
			List<Map<String,Object>> nnpList2 = null;
			if(null != nnpList && !nnpList.isEmpty()){
				nnpMap2 = new HashMap<>();
				for(int i = 0; i < nnpList.size(); i++){
					if(null == nnpList.get(i).get("BLICNO")){
						continue;
					}
					nnpList2 = investAllViewService.queryEntAllEnt(nnpList.get(i).get("BLICNO") + "");
					nnpMap2.put(nnpList.get(i).get("BLICNO") + "", nnpList2);				
				}		
			}	
			MapReplaceUtils.handleMapData(nnpMap2);	
			resultdata.put("nnpMap2", nnpMap2);

			//主要人员
			List<Map<String,Object>> kyList = ListUtils.getListIndex(investAllViewList, 2);
			MapReplaceUtils.handleLsitMapData(kyList);
			resultdata.put("kyList", kyList);

			//主要人员对外投资
			Map<String,Object> kyMap2 = null;
			List<Map<String,Object>> kyList2 = null;
			if(null != kyList && !kyList.isEmpty()){
				kyMap2 = new HashMap<>();
				for(int i = 0; i < kyList.size(); i++){
					if(null == kyList.get(i).get("CERNO")){
						continue;
					}
					kyList2 = investAllViewService.queryNameAllEnt(kyList.get(i).get("CERNO") + "");
					kyMap2.put(kyList.get(i).get("CERNO") + "", kyList2);				
				}			
			}
			MapReplaceUtils.handleMapData(kyMap2);	
			resultdata.put("kyMap2", kyMap2);

			//法定代表人
			List<Map<String,Object>> lrList = ListUtils.getListIndex(investAllViewList, 3);
			MapReplaceUtils.handleLsitMapData(lrList);
			resultdata.put("lrList", lrList);

			//法定代表人对外投资
			Map<String,Object> lrMap2 = null;
			List<Map<String,Object>> lrList2 = null;
			if(null != lrList && !lrList.isEmpty()){
				lrMap2 = new HashMap<>();
				for(int i = 0; i < lrList.size(); i++){
					if(null == lrList.get(i).get("CERNO")){
						continue;
					}
					lrList2 = investAllViewService.queryNameAllEnt(lrList.get(i).get("CERNO") + "");
					lrMap2.put(lrList.get(i).get("CERNO") + "", lrList2);				
				}			
			}
			MapReplaceUtils.handleMapData(lrMap2);	
			resultdata.put("lrMap2", lrMap2);

			//分支机构 
			List<Map<String,Object>> ebList = ListUtils.getListIndex(investAllViewList, 4);
			MapReplaceUtils.handleLsitMapData(ebList);
			resultdata.put("ebList", ebList);	

			//分支机构对外投资第二层
			Map<String,Object> ebMap2 = null;
			List<Map<String,Object>> ebList2 = null;
			if(null != ebList && !ebList.isEmpty()){
				ebMap2 = new HashMap<>();
				for(int i = 0; i < ebList.size(); i++){
					if(null == ebList.get(i).get("REGNO")){
						continue;
					}
					ebList2 = investAllViewService.queryEntAllEnt(ebList.get(i).get("REGNO") + "");
					ebMap2.put(ebList.get(i).get("REGNO") + "", ebList2);				
				}		
			}	
			MapReplaceUtils.handleMapData(ebMap2);	
			resultdata.put("ebMap2", ebMap2);	
			
			//企业对外投资
			List<Map<String,Object>> invList = ListUtils.getListIndex(investAllViewList, 5);
			MapReplaceUtils.handleLsitMapData(invList);
			resultdata.put("invList", invList);	

			map.put("data", resultdata);			
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}

		return MyGson.toJson(map);
	}
}
