package com.JavaClub.controller.credit;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.Interface.PatentService;
import com.JavaClub.util.Connect;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;


/**
 * 
 * @descript (得到专利相关信息)
 * @author 汤彬
 * @createTime 2016年9月14日下午3:44:35
 * @version 1.0
 */
@Controller
@RequestMapping(value="/patentController")
public class PatentController  extends BaseController{

	@Autowired
	public PatentService patentService;
	
	/**
	 * 
	 * @descript (得到专利信息)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:44:49
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked" })
    @RequestMapping(value = "/queryPatentinfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryPatentinfo() throws Exception {
        ModelAndView mv=new ModelAndView("patent/patent_list");
        PageData pd = new PageData();
        pd = this.getPageData();

        //设置接口的加密
        Verification.EncodeKeyNo(pd, "pripid");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询专利接口
        Map<String, Object> queryPatentinfo = Connect.sendConnectByPdToMap("Interface/patentInterface/queryPatentinfo", pd, "POST");
        
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(queryPatentinfo)){
	        //得到专利信息JSON数据中的data数据
	        Map<String,Object> dataMap_Patent = (Map<String, Object>) queryPatentinfo.get("data");
	        List<Map<String, Object>> patentinfo = (List<Map<String, Object>>) dataMap_Patent.get("patentInfo");
	        mv.addObject("patentinfo",patentinfo);
	        //分页的拼接
	        Page page=Verification.getPage(dataMap_Patent);
	        mv.addObject("page", page);
        }       

        mv.addObject("pd", pd);
        return mv;
    }
	
	
	/**
	 * 
	 * @descript (得到专利详情信息)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:45:05
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked" })
    @RequestMapping(value = "/queryPatentDetail", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryPatentDetail() throws Exception {
        ModelAndView mv=new ModelAndView("patent/patent_det");
        PageData pd = new PageData();
        pd = this.getPageData();

        //设置接口的加密
        Verification.EncodeKeyNo(pd, "pripid");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询专利接口
        Map<String, Object> queryPatentinfo = Connect.sendConnectByPdToMap("Interface/patentInterface/queryPatentinfo", pd, "POST");
        
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(queryPatentinfo)){
	        //得到专利信息JSON数据中的data数据
	        Map<String,Object> dataMap_Patent = (Map<String, Object>) queryPatentinfo.get("data");
	        List<Map<String, Object>> patentinfo = (List<Map<String, Object>>) dataMap_Patent.get("patentInfo");
	        mv.addObject("patentinfo",patentinfo);
	        //分页的拼接
	        Page page=Verification.getPage(dataMap_Patent);
	        mv.addObject("page", page);
        }   

        mv.addObject("pd", pd);
        return mv;
    }
	
	
/**
 * 
 * @descript (得到专利信息)
 * @author 魏旋
 * @since 2016年12月19日上午9:27:08
 * @param page
 * @return
 * @throws Exception
 */
	@ResponseBody
	@RequestMapping(value = "/queryPatentInfo1", produces = "text/html;charset=UTF-8")
	public ModelAndView queryPatentInfo1(Page page) throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//设置分页
		page.setPd(pd);
		
		//获取所有用户数据
		List<Map<String, Object>> patent_list = patentService.queryPatentinfo1(page);

		//设置返回数据和视图
		mv.addObject("pd", pd);
		mv.addObject("patent_list", patent_list);
		mv.setViewName("/patent1/patent1_list");
		
		return mv;
	}
	
	/**
	 * 
	 * @descript (得到专利信息详情)
	 * @author 魏旋
	 * @since 2016年12月19日上午10:46:32
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPatentDetailPage1")
	public ModelAndView queryPatentDetailPage(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List<Map<String, Object>> patent_list =patentService.queryPatentinfoDetail1(pd);
		List<Map<String, Object>> patent_law =patentService.queryPatentinfoDetailLaw(pd);
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject("patent_law",patent_law);
		mv.addObject("patent_list",patent_list);
		mv.setViewName("/patent1/patent1_det");
		
		return mv;
	}
	

}
