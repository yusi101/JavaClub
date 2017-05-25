
package com.JavaClub.controller.Interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.SecurityHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.Interface.VersionService;
import com.JavaClub.util.Const;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**
 * @descript 获取app上传数据
 * @author 李坡
 * @createTime 2016年9月18日下午5:28:06
 * @version 1.0
 */ 
@Controller
@RequestMapping(value="/Interface/VersionInterface")
public class VersionInterface extends BaseController{
	
	@Autowired
	public VersionService versionService;
	
	/**
	 * 
	 * @descript 查询app上传数据接口
	 * @author 李坡
	 * @since 2016年9月18日下午5:29:45
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryVersionInfo", produces = "text/html;charset=UTF-8")
	public String queryVersionInfo() throws Exception{
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

				Verification.DecodeKeyNo(pd, "name");
				List<Map<String, Object>> Version=new ArrayList<>();
				if(pd.get("appname") == null ){
					pd.put("appname","瑞臻征信2");
				}
				//如果pageSize存在，并且大于1，则该接口分页，否则不分页
		        if(StringUtil.isInt(pd.getString("pageSize"))){
		            Page page = new Page();
		            Verification.SetPageList(pd, page);//设置page的分页
		            Version= versionService.queryVersionInfo(page);
		            Verification.getPageMessage(resultMap, page);
		        }else{
		        	Version= versionService.queryVersionInfo(pd);
		        }
		        for (int i = 0; i < Version.size(); i++) {
                    Map<String, Object> Version_map=Version.get(i);
                    Version_map.put("TYPE", "1");
                    Version_map.put("PATH", Const.CLIENT_PATH+"baseinfo/downLoadBusinessCard.do?imagename="+SecurityHelper.getEncrypt(Version_map.get("PATH").toString())+"&type=downloadApp");
                    Version.set(i, Version_map);
                }
				MapReplaceUtils.handleLsitMapData(Version);
				resultMap.put("VersionInfo",Version);
			}catch (Exception e) {
				return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
			}
			return MyGson.toJson(map);
	}
	
}
 