package com.JavaClub.controller.Interface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.Interface.EntCourseService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StrUtil;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript 企业发展历程控制器
 * @author 龚志强
 * @createTime 2016年9月14日下午2:26:04
 * @version 1.0
 */
@Controller
@RequestMapping(value="/Interface/entCourseInterface")
public class EntCourseInterface extends BaseController{

	//企业历程业务层
	@Resource(name = "entCourseService")
	private EntCourseService entCourseService;

	/**
	 * 
	 * @descript 企业发展历程
	 * @author 龚志强
	 * @since 2016年9月14日下午2:25:44
	 * @return 企业历程集合
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/queryEntCourse", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryEntCourse() throws Exception {
		PageData pd = new  PageData();
		pd = this.getPageData();	

		Map<String,Object> map = Verification.Success();
		Map<String,Object> resultdata = new HashMap<>();

		//初始化数据
		try {			
			String VerificationParameter = Verification.VerificationParameter(pd,"token@deviceId@KeyNo@regno@priptype");

			if(!"".equals(VerificationParameter)){
				return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
			}
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}
			pd.put("pripid", pd.getString("KeyNo"));

			List entList = entCourseService.queryEntCourse(pd);			
			MapReplaceUtils.handleLsitMapData(entList);
			resultdata.put("entList", entList);					

			map.put("data", resultdata);
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}

		return MyGson.toJson(map);
	}
	
	/**
	 * 
	 * @descript 历程详情
	 * @author 龚志强
	 * @since 2016年9月20日上午10:39:08
	 * @return 历程详情集合
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/queryEntInfo", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryEntInfo() throws Exception {
		PageData pd = new  PageData();
		pd = this.getPageData();	
		
		Map<String,Object> map = Verification.Success();
		Map<String,Object> resultdata = new HashMap<>();
		
		//初始化数据
		try {			
			String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@type");
			
			if(!VerificationParameter.equals("")){
				return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
			}
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}
			pd.put("id", pd.getString("KeyNo"));

			List detList = null;
			if(pd.getString("type").equals("招聘")){				
				detList = entCourseService.queryJobInfo(pd);
			}else if(pd.getString("type").equals("招投标")){				
				detList = entCourseService.queryBiddin(pd);
			}else if(pd.getString("type").equals("新闻")){				
				detList = entCourseService.queryEntnews(pd);	
			}else if(pd.getString("type").equals("专利")){				
				detList = entCourseService.queryPatent(pd);
			}else if(pd.getString("type").equals("商标")){				
				detList = entCourseService.queryBrand(pd);	
			}else if(pd.getString("type").equals("企业变更")){				
				detList = entCourseService.queryEntRecoder(pd);
				MapReplaceUtils.handleLsitMapData(detList);
				for (int i = 0; i < detList.size(); i++) {
				    Map<String, Object> listChangemap = (Map<String, Object>) detList.get(i);
				    listChangemap.put("ALTAF", StrUtil.replace(listChangemap.get("ALTAF").toString()));
				    listChangemap.put("ALTBE", StrUtil.replace(listChangemap.get("ALTBE").toString()));
				    detList.set(i, listChangemap);
	            }
			}else if(pd.getString("type").equals("个体变更")){				
				detList = entCourseService.querySingleRecoder(pd);
				MapReplaceUtils.handleLsitMapData(detList);
				for (int i = 0; i < detList.size(); i++) {
				    Map<String, Object> listChangemap = (Map<String, Object>) detList.get(i);
				    listChangemap.put("ALTAF", StrUtil.replace(listChangemap.get("ALTAF").toString()));
				    listChangemap.put("ALTBE", StrUtil.replace(listChangemap.get("ALTBE").toString()));
				    detList.set(i, listChangemap);
	            }
			}else if(pd.getString("type").equals("著作权")){
				detList = entCourseService.queryWorkcopyRight(pd);
			}else if(pd.getString("type").equals("软件著作权")){				
				detList = entCourseService.queryCopyright(pd);
			}else if(pd.getString("type").equals("荣誉")){
				detList = entCourseService.queryHonorinfo(pd);
			}
						
			MapReplaceUtils.handleLsitMapData(detList);
			resultdata.put("detList", detList);					
			
			map.put("data", resultdata);
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		
		return MyGson.toJson(map);
	}
}