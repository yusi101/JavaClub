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
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;


/**
 * 
 * @descript (得到著作权/软件著作权相关信息)
 * @author 汤彬
 * @createTime 2016年9月14日下午3:44:14
 * @version 1.0
 */
@Controller
@RequestMapping(value="/copyrightController")
public class CopyrightController  extends BaseController{
	
	/**
	 * 
	 * @descript (得到软件著作权信息)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:43:17
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked" })
    @RequestMapping(value = "/queryCopyrightinfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryCopyrightinfo() throws Exception {
        ModelAndView mv=new ModelAndView("copyright/copyright_list");
        PageData pd = new PageData();
        pd = this.getPageData();

        //设置接口的加密
        Verification.EncodeKeyNo(pd, "pripid");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询专利接口
        Map<String, Object> queryCopyrightinfo = Connect.sendConnectByPdToMap("Interface/copyrightInterface/queryCopyrightInfo", pd, "POST");
        
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(queryCopyrightinfo)){
	        //得到专利信息JSON数据中的data数据
	        Map<String,Object> dataMap_Copyright = (Map<String, Object>)  queryCopyrightinfo.get("data");
	        List<Map<String, Object>> copyrightinfo = (List<Map<String, Object>>) dataMap_Copyright.get("copyrightInfo");
	        mv.addObject("copyrightinfo",copyrightinfo);
	        
	        //分页的拼接
	        Page page=Verification.getPage(dataMap_Copyright);
	        mv.addObject("page", page);
        }
        
        mv.addObject("pd", pd);
        return mv;
    }
	
	/**
	 * 
	 * @descript (得到著作权信息)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:43:49
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked" })
    @RequestMapping(value = "/queryWorkCopyrightinfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryWorkCopyrightinfo() throws Exception {
        ModelAndView mv=new ModelAndView("copyright/copyright_list");
        PageData pd = new PageData();
        pd = this.getPageData();

        //设置接口的加密
        Verification.EncodeKeyNo(pd, "pripid");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询专利接口
        Map<String, Object> queryWorkCopyrightinfo = Connect.sendConnectByPdToMap("Interface/copyrightInterface/queryWorkCopyrightInfo", pd, "POST");
        
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(queryWorkCopyrightinfo)){
	        //得到专利信息JSON数据中的data数据
	        Map<String,Object> dataMap_WorkCopyright = (Map<String, Object>)  queryWorkCopyrightinfo.get("data");
	        List<Map<String, Object>> workCopyrightinfo = (List<Map<String, Object>>) dataMap_WorkCopyright.get("workCopyrightInfo");
	        mv.addObject("workCopyrightinfo",workCopyrightinfo);
	        
	        //分页的拼接
	        Page page=Verification.getPage(dataMap_WorkCopyright);
	        mv.addObject("page", page);
        }
        
        mv.addObject("pd", pd);
        return mv;
    }

}
