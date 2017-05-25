package com.JavaClub.controller.credit;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.util.Connect;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;


/**
 * 
 * @descript (得到商标相关信息)
 * @author 李海涛
 * @createTime 2016年9月14日上午10:57:41
 * @version 1.0
 */
@Controller
@RequestMapping(value="/brandController")
public class BrandController  extends BaseController{
	
    
    /**
     * 
     * @descript (得到商标信息)
     * @author 李海涛
     * @since 2016年9月14日上午10:57:11
     * @return
     * @throws Exception
     */
	@SuppressWarnings({"unchecked" })
    @RequestMapping(value = "/queryBrandInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryBrandInfo() throws Exception {
        ModelAndView mv=new ModelAndView("brand/brand_list");
        PageData pd = new PageData();
        pd = this.getPageData();

        //设置接口的加密
        Verification.EncodeKeyNo(pd, "pripid");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询商标接口接口
        Map<String, Object> queryBrandInfo = Connect.sendConnectByPdToMap("Interface/brandInterface/queryBrandInfo", pd, "POST");
        //调用查询商标分类信息接口
        Map<String, Object> queryBrandClass = Connect.sendConnectByPdToMap("Interface/brandInterface/queryBrandClass", pd, "POST");
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(queryBrandInfo) && Verification.StatusIsSuccess(queryBrandClass)){
            //得到商标信息JSON数据中的data数据
            Map<String,Object> dataMap_Brand = (Map<String, Object>) queryBrandInfo.get("data");
            List<Map<String, Object>> brandInfo = (List<Map<String, Object>>) dataMap_Brand.get("brandInfo");
            mv.addObject("brandInfo",brandInfo);
            //分页的拼接
            Page page=Verification.getPage(dataMap_Brand);
            mv.addObject("page", page);
            
            Map<String,Object> dataMap_brandClass = (Map<String, Object>) queryBrandClass.get("data");
            List<Map<String, Object>> brandClass = (List<Map<String, Object>>) dataMap_brandClass.get("brandClass");
            mv.addObject("brandClass",brandClass);
        }
        

        mv.addObject("pd", pd);
        return mv;
    }

	/**
     * 
     * @descript (得到详细商标信息)
     * @author 李海涛
     * @since 2016年9月14日上午10:57:11
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked" })
    @RequestMapping(value = "/queryBrandDetail", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryBrandDetail() throws Exception {
        ModelAndView mv=new ModelAndView("brand/brand_detail");
        PageData pd = new PageData();
        pd = this.getPageData();

        //设置接口的加密
        Verification.EncodeKeyNo(pd, "pripid");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询商标接口接口
        Map<String, Object> queryBrandInfo = Connect.sendConnectByPdToMap("Interface/brandInterface/queryBrandInfo", pd, "POST");
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(queryBrandInfo)){
            //得到商标信息JSON数据中的data数据
            Map<String,Object> dataMap_Brand = (Map<String, Object>) queryBrandInfo.get("data");
            Map<String, Object> brandInfo = ListUtils.getListMap((List<Map<String, Object>>) dataMap_Brand.get("brandInfo"), 0);
            mv.addObject("brandInfo",brandInfo);
           
        }
        mv.addObject("pd", pd);
        return mv;
    }
}
