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
import com.JavaClub.service.Interface.PhotoService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript (轮播)
 * @author 汤彬
 * @createTime 2016年9月19日下午4:07:41
 * @version 1.0
 */
@Controller
@RequestMapping(value="/Interface/photoInterface")
public class PhotoInterface extends BaseController{
	
	@Autowired
	public PhotoService photoService;

	
	/**
	 * 
	 * @descript (得到轮播图列表，参数中有pageSize则代表分页)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:45:54
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPhotoInfo", produces = "text/html;charset=UTF-8")
	public String queryPhotoInfo() throws Exception{
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

			Verification.DecodeKeyNo(pd, "attachMent_Name");
			List<Map<String, Object>> photoInfo=new ArrayList<>();
			
			//如果pageSize存在，并且大于1，则该接口分页，否则不分页
	        if(StringUtil.isInt(pd.getString("pageSize"))){
	            Verification.SetPageList(pd, page);//设置page的分页
	            photoInfo= photoService.queryPhotoInfoListPage(page);
	            Verification.getPageMessage(resultMap, page);
	        }else{
	        	photoInfo= photoService.queryPhotoInfo(pd);
	        }
	        
			MapReplaceUtils.handleLsitMapData(photoInfo);
			resultMap.put("photoInfo",photoInfo);
		}catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return MyGson.toJson(map);
	}
	
}
