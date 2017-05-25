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
import com.JavaClub.service.Interface.BaseinfoService;
import com.JavaClub.service.Interface.CopyrightService;
import com.JavaClub.util.EnumUtil;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript (得到著作权/软件著作权相关信息)
 * @author 汤彬
 * @createTime 2016年9月14日下午3:45:39
 * @version 1.0
 */
@Controller
@RequestMapping(value="/Interface/copyrightInterface")
public class CopyrightInterface extends BaseController{

	@Autowired
	public CopyrightService copyrightService;
	@Autowired
	public BaseinfoService baseinfoService;

	/**
	 * 
	 * @descript (得到软件著作权信息，参数中有pageSize则代表分页)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:45:54
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCopyrightInfo", produces = "text/html;charset=UTF-8")
	public String queryCopyrightinfo() throws Exception{
		Page page = new Page();
		PageData pd = new PageData();
		pd = this.getPageData();

		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultMap=new HashMap<>();
		map.put("data",resultMap);

		try {
			//选择调用该接口必须传入的参数
			String VerificationParameter=Verification.VerificationParameter(pd,"token@KeyNo@deviceId");
			//如果上面的必要参数没有，则返回异常
			if(!"".equals(VerificationParameter)){
				return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
			}

			//验证加密是否比配，不匹配则返回加密错误
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}

			Verification.DecodeKeyNo(pd, "pripid");
			List<Map<String, Object>> copyrightInfo=new ArrayList<>();

			//如果pageSize存在，并且大于1，则该接口分页，否则不分页
			if(StringUtil.isInt(pd.getString("pageSize"))){
				Verification.SetPageList(pd, page);//设置page的分页
				copyrightInfo= copyrightService.queryCopyrightInfo(page);
				Verification.getPageMessage(resultMap, page);
			}else{
				copyrightInfo= copyrightService.queryCopyrightInfo(pd);
			}

			MapReplaceUtils.handleLsitMapData(copyrightInfo);
			resultMap.put("copyrightInfo",copyrightInfo);
			//记录软件著作权搜索关键词
			baseinfoService.saveKeywordLog(pd, pd.getString("copyrightName"),new String[]{"copyrightId","entName"}, EnumUtil.SEARCH_COPYRIGHT.getName());
		}catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}


	/**
	 * 
	 * @descript (得到著作权信息，参数中有pageSize则代表分页)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:46:14
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryWorkCopyrightInfo", produces = "text/html;charset=UTF-8")
	public String queryWorkCopyrightinfo() throws Exception{
		Page page = new Page();
		PageData pd = new PageData();
		pd = this.getPageData();

		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultMap=new HashMap<>();
		map.put("data",resultMap);

		try {
			//选择调用该接口必须传入的参数
			String VerificationParameter=Verification.VerificationParameter(pd,"token@KeyNo@deviceId");
			//如果上面的必要参数没有，则返回异常
			if(!"".equals(VerificationParameter)){
				return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
			}

			//验证加密是否比配，不匹配则返回加密错误
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}

			Verification.DecodeKeyNo(pd, "pripid");
			List<Map<String, Object>> workCopyrightInfo=new ArrayList<>();

			//如果pageSize存在，并且大于1，则该接口分页，否则不分页
			if(StringUtil.isInt(pd.getString("pageSize"))){
				Verification.SetPageList(pd, page);//设置page的分页
				workCopyrightInfo= copyrightService.queryWorkCopyrightInfo(page);
				Verification.getPageMessage(resultMap, page);
			}else{
				workCopyrightInfo= copyrightService.queryWorkCopyrightInfo(pd);
			}

			MapReplaceUtils.handleLsitMapData(workCopyrightInfo);
			resultMap.put("workCopyrightInfo",workCopyrightInfo);
			//记录著作权搜索关键词
			baseinfoService.saveKeywordLog(pd, pd.getString("workCopyrightName"),new String[]{"workCopyrightId","entName"}, EnumUtil.SEARCH_WORKCOPYRIGHT.getName());
		}catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}

	/**
	 * 
	 * @descript (得到著作权/软件著作权信息（前台接口），)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:46:39
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCopyright", produces = "text/html;charset=UTF-8")
	public String queryCopyright(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,Object> map=Verification.Success();
		Map<String,Object> mapdata=new HashMap<String,Object>();
		PageData pd=new PageData();
		pd=this.getPageData();

		try {
			//选择调用该接口必须传入的参数
			String VerificationParameter=Verification.VerificationParameter(pd,"token@KeyNo@deviceId");
			//如果上面的必要参数没有，则返回异常
			if(!"".equals(VerificationParameter)){
				return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
			}
			//验证加密是否比配，不匹配则返回加密错误
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}
			//KeyNo转译为pripid
			Verification.DecodeKeyNo(pd, "pripid");
			//获取软件著作权的信息
			List<Map<String,Object>> copyrightInfo=copyrightService.queryCopyrightInfo(pd);
			MapReplaceUtils.handleLsitMapData(copyrightInfo);
			//获取著作权的信息
			List<Map<String,Object>> workCopyrightInfo = copyrightService.queryWorkCopyrightInfo(pd);
			MapReplaceUtils.handleLsitMapData(workCopyrightInfo);

			mapdata.put("copyrightInfo",copyrightInfo);
			mapdata.put("workCopyrightInfo", workCopyrightInfo);
			map.put("data", mapdata);
		}catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}

	/**
	 * 
	 * @descript 查询著作权类型
	 * @author 吕永青
	 * @since 2016年10月27日上午8:58:12
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryWorkCopyrightClass", produces = "text/html;charset=UTF-8")
	public String queryWorkCopyrightClass() throws Exception{
		Page page = new Page();
		PageData pd = new PageData();
		pd = this.getPageData();

		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultMap=new HashMap<>();
		map.put("data",resultMap);

		try {
			//选择调用该接口必须传入的参数
			String VerificationParameter=Verification.VerificationParameter(pd,"token@KeyNo@deviceId");
			//如果上面的必要参数没有，则返回异常
			if(!"".equals(VerificationParameter)){
				return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
			}

			//验证加密是否比配，不匹配则返回加密错误
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}

			Verification.DecodeKeyNo(pd, "pripid");
			List<Map<String, Object>> workCopyrightInfClass=new ArrayList<>();

				workCopyrightInfClass= copyrightService.queryWorkCopyrightClass(page);
				Verification.getPageMessage(resultMap, page);

			MapReplaceUtils.handleLsitMapData(workCopyrightInfClass);
			resultMap.put("workCopyrightInfClass",workCopyrightInfClass);
		}catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}

}
