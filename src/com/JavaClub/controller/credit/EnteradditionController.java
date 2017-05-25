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
import com.JavaClub.util.Const;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript 企业信息查询
 * @author 李坡
 * @createTime 2016年9月18日上午11:57:18
 * @version 1.0
 */
@Controller
@RequestMapping(value="/enteradditioncontroller")
public class EnteradditionController extends BaseController{

	/**
	 * @Title: queryEnteraddition
	 * @Description: TODO 企业信息查询
	 * @param: @return 李坡
	 * @param: @throws Exception   
	 * @return: ModelAndView   
	 * @throws
	 */
	@RequestMapping(value = "/queryEnteraddition", produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ModelAndView queryEnteraddition() throws Exception{
		ModelAndView mv = new ModelAndView("enterprise/enterprise_list");;
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
			Map<String,Object> dataMap_enteraddition = (Map<String, Object>) quertenteraddition.get("data");
			List<Map<String, Object>> enteraddition = (List<Map<String, Object>>) dataMap_enteraddition.get("Result");
			for(int i=0;i<enteraddition.size();i++){
				String str1=enteraddition.get(i).get("ENTTYPE_CN").toString();
				if(str1.indexOf("(") > 0){  
					String[] s=str1.split("\\(");
					String	sr=s[0]+"<br/>("+s[1];
					enteraddition.get(i).put("ENTTYPE_CN", sr);
				}
				String str2=enteraddition.get(i).get("REGSTATE_CN").toString();
				if(str2.indexOf("(") > 0){  
					String[] s=str2.split("\\(");
					String	sr=s[0]+"<br/>("+s[1];
					enteraddition.get(i).put("REGSTATE_CN", sr);
				}
			}
			
			mv.addObject("Enteraddition",enteraddition);
            //分页的拼接
            Page page=Verification.getPage(dataMap_enteraddition);
            mv.addObject("page", page);
        }
        mv.addObject("pd", pd);
        return mv;
	}
	
	
}
