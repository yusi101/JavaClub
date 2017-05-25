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
import com.JavaClub.service.Interface.PatentService;
import com.JavaClub.util.EnumUtil;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript (得到专利相关信息)
 * @author 汤彬
 * @createTime 2016年9月14日下午3:46:57
 * @version 1.0
 */
@Controller
@RequestMapping(value="/Interface/patentInterface")
public class PatentInterface extends BaseController{
	
	@Autowired
	public PatentService patentService;
	@Autowired
	public BaseinfoService baseinfoService;
	
	/**
	 * 
	 * @descript (得到专利信息，参数中有pageSize则代表分页)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:47:08
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPatentinfo", produces = "text/html;charset=UTF-8")
	public String queryPatentinfo() throws Exception{
	    Page page = new Page();
	    PageData pd = new PageData();
		pd = this.getPageData();

		Map<String,Object> map=Verification.Success();
		Map<String,Object> resultMap=new HashMap<>();
		map.put("data",resultMap);

		try {
		    // 选择调用该接口必须传入的参数
			String VerificationParameter=Verification.VerificationParameter(pd,"token@KeyNo@deviceId");
			 //如果上面的必要参数没有，则返回异常
			if(!"".equals(VerificationParameter)){
				return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
			}
			
			// 验证加密是否比配，不匹配则返回加密错误
			if(!Verification.VerificationMd5(pd)){
				return  MyGson.toJson(Verification.Md5Match());
			}

			Verification.DecodeKeyNo(pd, "pripid");
			List<Map<String, Object>> patentinfo=new ArrayList<>();
			
			//如果pageSize存在，并且大于1，则该接口分页，否则不分页
	        if(StringUtil.isInt(pd.getString("pageSize"))){
	            Verification.SetPageList(pd, page);//设置page的分页
	           patentinfo= patentService.queryPatentinfo(page);
	            Verification.getPageMessage(resultMap, page);
	        }else{
	        	patentinfo= patentService.queryPatentinfo(pd);
	        }
	        
			MapReplaceUtils.handleLsitMapData(patentinfo);
			resultMap.put("patentInfo",patentinfo);
			//记录专利搜索关键词
			baseinfoService.saveKeywordLog(pd, pd.getString("patentName"),new String[]{"patentId","entname"}, EnumUtil.SEARCH_PATENT.getName());
		}catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}
	
	/**
	 * 
	 * @descript 查询专利类型
	 * @author 吕永青
	 * @since 2016年10月26日下午3:27:11
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
    @RequestMapping(value = "/queryPatentClass", produces = "text/html;charset=UTF-8")
    public String queryPatentClass() throws Exception{
        PageData pd = new PageData();
        pd = this.getPageData();

        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);

        try {
            //选择调用该接口必须传入的参数
            String VerificationParameter=Verification.VerificationParameter(pd,"token@KeyNo@deviceId");
             //如果上面的必要参数没有，则返回异常
            if(!VerificationParameter.equals("")){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }

            //验证加密是否比配，不匹配则返回加密错误
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "");
            List<Map<String, Object>> PatentClass=patentService.queryPatentClass(pd);

            MapReplaceUtils.handleLsitMapData(PatentClass);
            resultMap.put("PatentClass",PatentClass);
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
}
