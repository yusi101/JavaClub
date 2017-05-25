package com.JavaClub.controller.Interface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.Interface.SysService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * 系统控制器
 * @author gongzhiqiang
 *
 */
@Controller
@RequestMapping(value="/Interface/sysInterface")
public class SysInterface extends BaseController{

	@Resource(name = "sysService")
	private SysService sysService;
	
	
	/**
	 * 
	 * @descript (获取h5配置信息)
	 * @author 李海涛
	 * @since 2016年9月22日下午8:21:02
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/querySysMobileTitle", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String querySysMobileTitle() throws Exception {
	    PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
		//初始化数据
		try {	
		    String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "pid");
			List sysMobileTitle = sysService.querySysMobileTitle(pd);
			MapReplaceUtils.handleLsitMapData(sysMobileTitle);
			resultMap.put("sysMobileTitle", sysMobileTitle);
		} catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}
}
