
package com.JavaClub.controller.Interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.Interface.BaseinfoService;
import com.JavaClub.service.Interface.CourtcaseService;
import com.JavaClub.util.EnumUtil;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**

 * @ClassName:  CourtcaseInterface
 * @Description: 得到失信被执行人相关数据
 * @author:    李坡
 * @date:        2016年9月12日 上午11:19:31
 *
 */
@Controller
@RequestMapping(value="/Interface/courtcaseInterface")
public class CourtcaseInterface extends BaseController{

	@Autowired
	public CourtcaseService courtcaseService;
	@Autowired
	public BaseinfoService baseinfoService;
	/**
	 * @Title: queryCourt
	 * @Description: 得到失信被执行人信息
	 * @param: @return
	 * @return: String   
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCourtcaseInfo", produces = "text/html;charset=UTF-8")
	public String queryCourtcaseInfo() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		
		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultMap=new HashMap<>(2);
		
		map.put("data",resultMap);
		try {
			String VerificationParameter = Verification.VerificationParameter(pd, "token@KeyNo@deviceId");
			if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }

			//验证加密是否比配，不匹配则返回加密错误
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}

			Verification.DecodeKeyNo(pd, "iname");
			List<Map<String, Object>> courtcaseInfo=new ArrayList<>();

			//如果pageSize存在，并且大于1，则该接口分页，否则不分页
			if(StringUtil.isInt(pd.getString("pageSize"))){
				Page page = new Page();
				Verification.SetPageList(pd, page);//设置page的分页
				courtcaseInfo= courtcaseService.queryCourtcaseInfo(page);
				Verification.getPageMessage(resultMap, page);
			}else{
			    courtcaseInfo= courtcaseService.queryCourtcaseInfo(pd);
			}

			MapReplaceUtils.handleLsitMapData(courtcaseInfo);
			resultMap.put("courtcaseInfo",courtcaseInfo);
			//记录失信搜索关键词
			baseinfoService.saveKeywordLog(pd, pd.getString("iname"),new String[]{"courtcaseid"}, EnumUtil.SEARCH_COURTCASE.getName());
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}
	
	/**
	 * 
	 * @descript (得到司法信息)
	 * @author 李海涛
	 * @since 2016年9月19日下午1:50:51
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
    @ResponseBody
    @RequestMapping(value = "/queryJusticeInfo", produces = "text/html;charset=UTF-8")
    public String queryJusticeInfo() throws Exception{
        PageData pd = new PageData();
        pd = this.getPageData();
        
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>(2);
        
        map.put("data",resultMap);
        try {
            String VerificationParameter = Verification.VerificationParameter(pd, "token@KeyNo@deviceId");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }

            //验证加密是否比配，不匹配则返回加密错误
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }

            Verification.DecodeKeyNo(pd, "pripid");
            //得到司法信息
            List JusticeInfo= courtcaseService.queryJusticeInfo(pd);
            //得到判决文书信息
            List<Map<String,Object>> judgmentInfo=(List<Map<String, Object>>) JusticeInfo.get(0);
            //得到失信被执行人信息
            List<Map<String,Object>> courtcaseInfo=(List<Map<String, Object>>) JusticeInfo.get(1);
            //得到股权变更信息
            List<Map<String,Object>> sfAlter=(List<Map<String, Object>>) JusticeInfo.get(2);
            //得到股权冻结信息
            List<Map<String,Object>> sfInfo=(List<Map<String, Object>>) JusticeInfo.get(3);
           // 判决文书信息表
            MapReplaceUtils.handleLsitMapData(judgmentInfo);
            resultMap.put("judgmentInfo",judgmentInfo);
            //失信被执行人信息表
            MapReplaceUtils.handleLsitMapData(courtcaseInfo);
            resultMap.put("courtcaseInfo",courtcaseInfo);
            //股权变更信息
            MapReplaceUtils.handleLsitMapData(sfAlter);
            resultMap.put("sfAlter",sfAlter);
            //股权冻结信息
            MapReplaceUtils.handleLsitMapData(sfInfo);
            resultMap.put("sfInfo",sfInfo);

        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
	/**
	 * 
	 * @descript (得到司法信息数量)
	 * @author 李海涛
	 * @since 2016年9月22日下午9:03:11
	 * @param request
	 * @return
	 */
    @SuppressWarnings("unchecked")
    @ResponseBody   
    @RequestMapping(value="/queryJusticeCount",produces ="text/html;charset=UTF-8")
    public String queryJusticeCount(HttpServletRequest request){
	    PageData pd = new PageData();
        pd = this.getPageData();
        
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>(2);
        try{
            String VerificationParameter = Verification.VerificationParameter(pd, "token@KeyNo@deviceId");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }

            //验证加密是否比配，不匹配则返回加密错误
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "pripid");
            
            Map<String,Object> justiceCount = ListUtils.getListMap(courtcaseService.queryJusticeCount(pd), 0);
            MapReplaceUtils.handleMapData(justiceCount);
            resultMap.put("justiceCount",justiceCount);
            return MyGson.toJson(map);
        }catch(Exception e){
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
    }
}
