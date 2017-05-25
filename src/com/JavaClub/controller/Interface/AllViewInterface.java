package com.JavaClub.controller.Interface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.Interface.AllViewService;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript 全景视图控制器 
 * @author 龚志强
 * @createTime 2016年9月14日下午2:39:02
 * @version 1.0
 */
@Controller
@RequestMapping(value="/Interface/allViewInterface")
public class AllViewInterface extends BaseController{
	
	//全景视图业务层
	@Resource(name = "allViewService")
	private AllViewService allViewService;
	
	/**
	 * 
	 * @descript 全景视图
	 * @author 龚志强
	 * @since 2016年9月14日下午2:39:11
	 * @return 返回全景视图json数据
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/queryAllView", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryAllView() throws Exception {
		PageData pd = new  PageData();
		pd = this.getPageData();	
		
		Map<String,Object> map = Verification.Success();
		Map<String,Object> resultdata = new HashMap<>();
		
		try {			
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@regno");
			
			if(!"".equals(VerificationParameter)){
				return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
			}
			
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}
			
			pd.put("pripid", pd.getString("KeyNo"));
			List allViewList = allViewService.queryAllView(pd);
			
			//自然人 
			List<Map<String,Object>> invPersonList = ListUtils.getListIndex(allViewList, 0);
			MapReplaceUtils.handleLsitMapData(invPersonList);
			resultdata.put("invPersonList", invPersonList);		

			//非自然人
			List<Map<String,Object>> invEstmentList = ListUtils.getListIndex(allViewList, 1);
			MapReplaceUtils.handleLsitMapData(invEstmentList);
			resultdata.put("invEstmentList", invEstmentList);	
			
			//法定代表人 
			List<Map<String,Object>> priPersonList = ListUtils.getListIndex(allViewList, 2);
			MapReplaceUtils.handleLsitMapData(priPersonList);
			resultdata.put("priPersonList", priPersonList);		
			
			//主要人员
			List<Map<String,Object>> priPersonList2 = ListUtils.getListIndex(allViewList, 3);
			MapReplaceUtils.handleLsitMapData(priPersonList2);
			resultdata.put("priPersonList2", priPersonList2);		
			
			//对外投资
			List<Map<String,Object>> invEstmentList2 = ListUtils.getListIndex(allViewList, 4);
			MapReplaceUtils.handleLsitMapData(invEstmentList2);
			resultdata.put("invEstmentList2", invEstmentList2);	
			
			//判决文书 
			List<Map<String,Object>> judgmentList = ListUtils.getListIndex(allViewList, 5);
			MapReplaceUtils.handleLsitMapData(judgmentList);
			resultdata.put("judgmentList", judgmentList);	

			//失信被执行人 
			List<Map<String,Object>> courtcaseList = ListUtils.getListIndex(allViewList, 6);
			MapReplaceUtils.handleLsitMapData(courtcaseList);
			resultdata.put("courtcaseList", courtcaseList);	
			
			//行政许可 
			List<Map<String,Object>> certificateList = ListUtils.getListIndex(allViewList, 7);
			MapReplaceUtils.handleLsitMapData(certificateList);
			resultdata.put("certificateList", certificateList);	
			
			//其他行政许可  
			List<Map<String,Object>> permitList = ListUtils.getListIndex(allViewList, 10);
			MapReplaceUtils.handleLsitMapData(permitList);
			resultdata.put("permitList", permitList);	
			
			//行政处罚 
			List<Map<String,Object>> caseList = ListUtils.getListIndex(allViewList, 11);
			MapReplaceUtils.handleLsitMapData(caseList);
			resultdata.put("caseList", caseList);
		
			//分支机构
			List<Map<String,Object>> brchinfoList = ListUtils.getListIndex(allViewList, 8);
			MapReplaceUtils.handleLsitMapData(brchinfoList);
			resultdata.put("brchinfoList", brchinfoList);	
			
			//疑似关系
			List<Map<String,Object>> priPersonList3 = ListUtils.getListIndex(allViewList, 9);
			MapReplaceUtils.handleLsitMapData(priPersonList3);
			resultdata.put("priPersonList3", priPersonList3);	
			
			map.put("data", resultdata);
			
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		
		return MyGson.toJson(map);
	}

}