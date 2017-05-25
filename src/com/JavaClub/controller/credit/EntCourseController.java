package com.JavaClub.controller.credit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * @descript 企业历程控制器
 * @author 龚志强
 * @createTime 2016年9月14日下午2:27:53
 * @version 1.0
 */
@Controller
@RequestMapping("/entCourseController")
public class EntCourseController extends BaseController{

	/**
	 * 
	 * @descript 查看历程展示
	 * @author 龚志强
	 * @since 2016年9月14日下午2:27:36
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryEntCourse", produces = "text/html;charset=UTF-8")
	public ModelAndView queryEntCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();	
		pd = this.getPageData();

    	//设置接口的加密
        Verification.EncodeKeyNo(pd, "pripid");;

		//获取数据
		Map<String,Object> resultMap = Connect.sendConnectByPdToMap("/Interface/entCourseInterface/queryEntCourse.do", pd,"post");	

		//判断查询的结果集是否为空
		if(Verification.StatusIsSuccess(resultMap)){		
			//处理数据 
			Map<String,Object> dataMap = (Map<String, Object>) resultMap.get("data");
			List<Map<String,Object>> entList = getData((List<Map<String,Object>>) dataMap.get("entList"));

			mv.addObject("entList", entList);
		}

		mv.addObject("pd", pd);
		mv.setViewName("/enterprise/timeshaft");

		return mv;
	}

	/**
	 * 
	 * @descript 处理企业历程的数据结构
	 * @author 龚志强
	 * @since 2016年9月14日下午2:28:24
	 * @param enList 企业历程集合
	 * @return
	 */
	public List<Map<String,Object>> getData(List<Map<String,Object>> enList){
		//处理后的企业历程集合
		List<Map<String,Object>> entdata = new ArrayList<Map<String,Object>>();
		Set<String> yearSet = new TreeSet<String>();	//年份集合
		List<Map<String,Object>> yeardata = null;		//年份对应的月份数据
		Map<String,Object> map = null;					//临时的月份数据
		Map<String,Object> mapdata = null;				//临时的年份数据
		String year = "";

		//获取所有的年份
		for(int i = 0; i < enList.size(); i++){
			String date = enList.get(i).get("ecdate").toString();
			//如果日期为空就下一个
			if(date.equals("") || date == null){
				continue;
			}
			year = date.substring(0, 4);
			yearSet.add(year);
		}

		//遍历所有
		for(String value : yearSet){
			mapdata = new HashMap<String,Object>();
			yeardata = new ArrayList<Map<String,Object>>();

			//获取每个年份里的月份数据
			for(int i = 0; i < enList.size(); i++){
				String date = enList.get(i).get("ecdate").toString();
				if(date.equals("") || date == null){
					continue;
				}
				year = date.substring(0, 4);
				//获取年份对应的月份数据
				if(value.equals(year)){
					map = new HashMap<String,Object>();
					map.put("id", enList.get(i).get("ecid"));
					map.put("title", enList.get(i).get("ectitle"));
					map.put("desc", enList.get(i).get("ecdesc"));
					map.put("type", enList.get(i).get("ectype"));
					map.put("date", date.substring(5));
					yeardata.add(map);
				}		
			}	

			mapdata.put("year", value);
			mapdata.put("yeardata", yeardata);
			entdata.add(mapdata);
		}		

		return entdata;
	}
	
	
	/**
	 * 
	 * @descript (历程企业列表)
	 * @author 汤彬
	 * @since 2016年10月12日上午10:13:47
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryEnteraddition", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView queryEnteraddition() throws Exception{
		ModelAndView mv = new ModelAndView("timeshaft/enterprise_list");;
		PageData pd = new PageData();
		pd = this.getPageData();
		
//		User user=this.getUser();
//		pd.put("countyCode", user.getAREA_CODE());
		
    	//设置接口的加密
        Verification.EncodesearchKey(pd, "entname");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用企业信息查询接口
        Map<String, Object> quertenteraddition = Connect.sendConnectByPdToMap("/Interface/baseinfoInterface/queryEnterpriseInfo", pd, "POST");
        Verification.DecodesearchKey(pd, "entname");
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(quertenteraddition)){
            //得到商标信息JSON数据中的data数据
			Map<String,Object> dataMap_enteraddition = (Map<String, Object>) quertenteraddition.get("data");
			List<Map<String, Object>> enteraddition = (List<Map<String, Object>>) dataMap_enteraddition.get("Result");
            mv.addObject("Enteraddition",enteraddition);
            //分页的拼接
            Page page = Verification.getPage(dataMap_enteraddition);
            mv.addObject("page", page);
        }
        mv.addObject("pd", pd);
        return mv;
	}
	
}