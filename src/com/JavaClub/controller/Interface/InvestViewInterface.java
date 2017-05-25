package com.JavaClub.controller.Interface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.Interface.InvestViewService;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript 投资链图
 * @author 龚志强
 * @createTime 2016年9月14日下午2:56:17
 * @version 1.0
 */
@Controller
@RequestMapping(value="/Interface/investViewInterface")
public class InvestViewInterface extends BaseController{
	
	//投资链图业务层
	@Resource(name = "investViewService")
	private InvestViewService investViewService;
	
	/**
	 * 
	 * @descript 投资链图
	 * @author 龚志强
	 * @since 2016年9月14日下午2:56:27
	 * @return 投资链图集合
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/queryInvestView", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryInvestView() throws Exception {
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
			List investViewList = investViewService.queryInvestView(pd);
			
			//自然人
			List<Map<String,Object>> npList = ListUtils.getListIndex(investViewList, 0);
			MapReplaceUtils.handleLsitMapData(npList);
			resultdata.put("npList", npList);		
			
			//自然人第二层
			Map<String,Object> npMap2 = null;
			List<Map<String,Object>> npList2 = null;
			if(null != npList && !npList.isEmpty()){
				npMap2 = new HashMap<>();
				for(int i = 0; i < npList.size(); i++){
					//如果身份证号码为空就跳过，身份证号码为查询条件
					if(null == npList.get(i).get("CERNO")){
						continue;
					}
					npList2 = investViewService.queryInvAllEnt(npList.get(i).get("CERNO") + "");
					npMap2.put(npList.get(i).get("CERNO").toString(), npList2);				
				}			
			}
			MapReplaceUtils.handleMapData(npMap2);	
			resultdata.put("npMap2", npMap2);

			//非自然人 
			List<Map<String,Object>> nnpList = ListUtils.getListIndex(investViewList, 1);
			MapReplaceUtils.handleLsitMapData(nnpList);
			resultdata.put("nnpList", nnpList);	
			
			//非自然人对外投资
			Map<String,Object> nnpMap2 = null;
			List<Map<String,Object>> nnpList2 = null;
			if(null != nnpList && !nnpList.isEmpty()){
				nnpMap2 = new HashMap<>();
				for(int i = 0; i < nnpList.size(); i++){
					//如果证照编号为空就跳过，证照编号为查询条件
					if(null == nnpList.get(i).get("BLICNO")){
						continue;
					}
					nnpList2 = investViewService.queryInvestViewTwo(nnpList.get(i).get("BLICNO") + "");
					nnpMap2.put(nnpList.get(i).get("BLICNO").toString(), nnpList2);				
				}		
			}	
			MapReplaceUtils.handleMapData(nnpMap2);	
			resultdata.put("nnpMap2", nnpMap2);
			
			//对外投资
			List<Map<String,Object>> fiList = ListUtils.getListIndex(investViewList, 2);
			MapReplaceUtils.handleLsitMapData(fiList);
			resultdata.put("fiList", fiList);
			
			//分支机构 
			List<Map<String,Object>> ebList = ListUtils.getListIndex(investViewList, 3);
			MapReplaceUtils.handleLsitMapData(ebList);
			resultdata.put("ebList", ebList);	
			
			//对外投资第二层
			Map<String,Object> fiMap2 = null;
			List<Map<String,Object>> fiList2 = null;
			if(null != fiList && !fiList.isEmpty()){
				fiMap2 = new HashMap<>();
				for(int i = 0; i < fiList.size(); i++){
					//如果企业注册号为空就跳过，企业注册号为查询条件
					if(null == fiList.get(i).get("REGNO")){
						continue;
					}
					fiList2 = investViewService.queryInvestViewTwo(fiList.get(i).get("REGNO") + "");
					fiMap2.put(fiList.get(i).get("REGNO").toString(), fiList2);				
				}		
			}	
			MapReplaceUtils.handleMapData(fiMap2);	
			resultdata.put("fiMap2", fiMap2);
			
			//对外投资第三层
			Map<String,Object> fiMap3 = new HashMap<>();
			List<Map<String,Object>> fiList3 = null;
			List<Map<String,Object>> fiList22 = null;
			//判断对外投资第二层是否有数据
			if(null != fiMap2 && !fiMap2.isEmpty()){		
				Set set = fiMap2.keySet();
				//循环第二层所有的键
				for(Object key : set){
					//获取第二层每个企业的对外投资
					fiList22 = (List<Map<String, Object>>) fiMap2.get(key);
					//如果第二层没有对外投资的企业就跳过
					if(null == fiList22 || fiList22.isEmpty()){
						continue;
					}
					
					//查询第二层对外投资的企业
					for(int i = 0; i < fiList22.size(); i++){
						//如果企业注册号为空就跳过，企业注册号为查询条件
						if(null == fiList22.get(i).get("REGNO")){
							continue;
						}
						fiList3 = investViewService.queryInvestViewTwo(fiList22.get(i).get("REGNO") + "");
						fiMap3.put(fiList22.get(i).get("REGNO").toString(), fiList3);				
					}
				}		
			}
			//保存第三层对外投资的企业
			MapReplaceUtils.handleMapData(fiMap3);	
			resultdata.put("fiMap3", fiMap3);
			
			map.put("data", resultdata);			
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		
		return MyGson.toJson(map);
	}
}
