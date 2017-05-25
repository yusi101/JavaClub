package com.JavaClub.controller.credit;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.annotation.system.log.SysUserLog;
import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.util.Connect;
import com.JavaClub.util.Const;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;



/**
 * 
 * @descript 信用报告控制器
 * @author 汤彬
 * @since 2016年9月13日下午5:19:38
 */
@Controller
@RequestMapping(value = "/creditReportController")
public class CreditReportController extends BaseController {

	
	/**
	 * 
	 * @descript  跳转信用报告
	 * @author 汤彬
	 * @since 2016年9月13日下午5:20:38
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/creditReport", produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SysUserLog("跳转信用报告")
	public ModelAndView queryCreditReportinfo(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("pd", pd);
		mv.setViewName("enterprise/credit_report");
		
		return mv;
	}
	
	
	/**
	 * 
	 * @descript (信用报告企业列表)
	 * @author 汤彬
	 * @since 2016年10月12日上午10:21:37
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEnteraddition", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView queryEnteraddition() throws Exception{
		ModelAndView mv = new ModelAndView("creditreport/enterprise_list");;
		PageData pd = new PageData();
		pd = this.getPageData();
		
//		User user=this.getUser();
//		pd.put("countyCode", user.getAREA_CODE());
		
    	//设置接口的加密
        Verification.EncodesearchKey(pd, "entname");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter2(pd);  
        //调用企业信息查询接口
        Map<String, Object> quertenteraddition = Connect.sendConnectByPdToMap(Const.ENTERPRISEURLFOUR, pd, "POST");
        Verification.DecodesearchKey(pd, "entname");
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(quertenteraddition)){
            //得到商标信息JSON数据中的data数据
            @SuppressWarnings("unchecked")
			Map<String,Object> dataMap_enteraddition = (Map<String, Object>) quertenteraddition.get("data");
            @SuppressWarnings("unchecked")
			List<Map<String, Object>> enteraddition = (List<Map<String, Object>>) dataMap_enteraddition.get("Result");
            mv.addObject("Enteraddition",enteraddition);
            //分页的拼接
            Page page=Verification.getPage(dataMap_enteraddition);
            mv.addObject("page", page);
        }
        mv.addObject("pd", pd);
        return mv;
	}
}