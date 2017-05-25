
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
import com.JavaClub.service.Interface.PackageService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

@Controller
@RequestMapping(value="/Interface/packageInterface")
public class PackageInterface extends BaseController{
	/**
	 * 得到所有套餐
	 * @author 孔钊
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Autowired
	private PackageService packageService;
	
	@ResponseBody
	@RequestMapping(value = "/queryPackageList",produces = "text/html;charset=UTF-8")
	public String getPackageListPage(Page page) throws Exception{
	    PageData pd = new PageData();
        pd = this.getPageData();
      /*//设置分页
      	page.setPd(pd);
      	pd.put("pageSize", "2");*/
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            List<Map<String, Object>> packageInfo=new ArrayList<>();
            
            //如果pageSize存在，并且大于1，则该接口分页，否则不分页
            if(StringUtil.isInt(pd.getString("pageSize"))){
                Verification.SetPageList(pd, page);//设置page的分页
                packageInfo= packageService.queryPkglistPage(page);
                Verification.getPageMessage(resultMap, page);
            }else{
            	packageInfo= packageService.packageInfo(pd);
            }
            MapReplaceUtils.handleLsitMapData(packageInfo);
            resultMap.put("packageInfo",packageInfo);
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
	}
	
	/**
     * 
     * @descript (保存套餐)
     * @author 孔钊
     * @since 2017年04月28日下午4:28:54
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/savePackage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String savePackage() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            pd.put("pkgId", get32UUID());
            pd.put("status", 1);
            int save_Result = packageService.savePackage(pd);
            //套餐配置明细表
            
            //插入查询功能次数
            pd.put("wayId", get32UUID());
            pd.put("way", "visit");
            pd.put("remark", "访问公司名");
            pd.put("user_time", pd.get("visit_user_time"));
            packageService.savePkgWay(pd);
            //插入下载征信次数
            pd.put("wayId", get32UUID());
            pd.put("way", "report");
            pd.put("remark", "信用报告下载");
            pd.put("user_time", pd.get("report_user_time"));
            packageService.savePkgWay(pd);
            
            resultMap.put("Result", save_Result+"");
        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
    
    /**
     * 
     * @descript (查看套餐详细)
     * @author 孔钊
     * @since 2017年05月2日下午2:28:54
     * @return
     * @throws Exception
     */
    @ResponseBody
	@RequestMapping(value = "/packageInfo",produces = "text/html;charset=UTF-8")
	public String packageInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
	    PageData pd = new PageData();
        pd = this.getPageData();
      //设置分页
        Page page = new Page();
      	/*page.setPd(pd);*/
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            List<Map<String, Object>> packageInfo=new ArrayList<>();
            
            //如果pageSize存在，并且大于1，则该接口分页，否则不分页
            if(StringUtil.isInt(pd.getString("pageSize"))){
                Verification.SetPageList(pd, page);//设置page的分页
              /*  packageInfo= packageService.queryPkgInfo(page);*/
                Verification.getPageMessage(resultMap, page);
            }else{
            	packageInfo= packageService.queryPkgInfo(pd);
            }
            MapReplaceUtils.handleLsitMapData(packageInfo);
            resultMap.put("packageInfo",packageInfo);
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
	}
    
    /**
     * 
     * @descript (更新套餐)
     * @author 孔钊
     * @since 2017年05月2日下午2:28:54
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delPackage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String delPackage() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            pd.put("status", 0);
            int update_Result = packageService.updatePackage(pd);
            //套餐配置明细表
            resultMap.put("Result", update_Result+"");
        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
}
