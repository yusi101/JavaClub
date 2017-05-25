package com.JavaClub.controller.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.system.DictionariesService;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;
/**
 * 
 * @ClassName: DictionariesController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author XWang
 * @date 2016年3月24日 下午2:59:10 
 *
 */
@Controller
@RequestMapping(value="/dictionariesController")
public class DictionariesController extends BaseController {

	@Autowired
	private DictionariesService dictionariesService;
	List<PageData> szdList=new ArrayList<>();

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDictionariesInfo", produces = "text/html;charset=UTF-8")
	public ModelAndView queryDictionariesInfo(Page page)throws Exception{

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String PARENT_ID = pd.getString("PARENT_ID");
		if(null != PARENT_ID && !"".equals(PARENT_ID) && !"0".equals(PARENT_ID)){
			//返回按钮用
			PageData pdp = new PageData();
			pdp = this.getPageData();
			pdp.put("ZD_ID", PARENT_ID);
			pdp = dictionariesService.queryDictionariesById(pdp);
			mv.addObject("pdp", pdp);
			//头部导航
			szdList = new ArrayList<PageData>();
			this.getZDname(PARENT_ID);	//	逆序
			Collections.reverse(szdList);

		}


		String NAME = pd.getString("NAME");
		if(null != NAME && !"".equals(NAME)){
			NAME = NAME.trim();
			pd.put("NAME", NAME);
		}
		page.setPd(pd);				
		List<PageData> varList = dictionariesService.dictlistPage(page);

		mv.setViewName("system/zd_list");
		mv.addObject("varList", varList);
		mv.addObject("varsList", szdList);
		mv.addObject("pd", pd);

		return mv;
	}

	//递归
	public void getZDname(String PARENT_ID) {
		logBefore(logger, "递归");
		try {
			PageData pdps = new PageData();;
			pdps.put("ZD_ID", PARENT_ID);
			pdps = dictionariesService.queryDictionariesById(pdps);
			if(pdps != null){
				szdList.add(pdps);
				String PARENT_IDs = pdps.getString("PARENT_ID");
				this.getZDname(PARENT_IDs);
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
	}
	/**
	 * 
	 * @descript (数据字典删除)
	 * @author 李坡
	 * @since 2016年9月14日下午1:58:41
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteDictionariesById", produces = "text/html;charset=UTF-8")
	public String deleteDictionariesById() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "";
		try {
			pd = this.getPageData();
			if(Integer.parseInt(dictionariesService.findCount(pd).get("ZS").toString()) != 0){
				errInfo = "false";
			}else{
				dictionariesService.delete(pd);
				errInfo = "true";
			}
		}catch (Exception e) {
			return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
		}
		return errInfo;
	}
	/**
	 * 
	 * @descript 新增数据字典信息
	 * @author 李坡
	 * @since 2016年9月14日下午3:37:14
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/createDictionaries", produces = "text/html;charset=UTF-8")
	public String createDictionaries() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData pdp = new PageData();
		pdp = this.getPageData();
		String flag="";
		String PARENT_ID = pd.getString("PARENT_ID");
		pdp.put("ZD_ID", PARENT_ID);
		
		List<Map<String, Object>> list = dictionariesService.queryDict(pd);
		if (!list.isEmpty()) {
			flag = "false";
			return flag;
		}
		else{
			if(null == pd.getString("ZD_ID") || "".equals(pd.getString("ZD_ID"))){

				if(null != PARENT_ID && "0".equals(PARENT_ID)){
					pd.put("JB", 1);
					pd.put("P_BM", pd.getString("BIANMA"));
				}else{
					pdp = dictionariesService.queryDictionariesById(pdp);
					pd.put("JB", Integer.parseInt(pdp.get("JB").toString()) + 1);
					pd.put("P_BM", pdp.getString("BIANMA") + "_" + pd.getString("BIANMA"));
				}

				pd.put("ZD_ID", this.get32UUID());	//ID
				dictionariesService.insertDictionaries(pd);
				flag = "true";
			}else{

				pdp = dictionariesService.queryDictionariesById(pdp);
				if(null != PARENT_ID && "0".equals(PARENT_ID)){
					pd.put("P_BM",  pd.getString("BIANMA"));
				}else{
					pd.put("P_BM", pdp.getString("BIANMA") + "_" + pd.getString("BIANMA"));
				}
				dictionariesService.updateDictionariesById(pd);
				flag = "true";
			}
			return flag;
		}

		
	}
	/**
	 * 
	 * @descript  单个数据字典查询
	 * @author 李坡
	 * @since 2016年9月18日上午9:42:16
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDictionariesById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView queryDictionariesDetail() throws Exception{
		ModelAndView mv =  new ModelAndView("system/zd_edt");
		PageData pd = new PageData();
		pd=this.getPageData();
		PageData dictionaries_list=dictionariesService.queryDictionariesById(pd);
		mv.addObject("pd", dictionaries_list);
		return mv;
	}

	/**
	 * 
	 * @descript 修改数据字典查询
	 * @author 李坡
	 * @since 2016年9月18日上午10:51:09
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateDictionariesModify", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateDictionariesModify() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String falg= "";
		
		List<Map<String, Object>> list = dictionariesService.queryDict(pd);
		if (!list.isEmpty()) {
			falg = "false";
			return falg;
		}
		else{
		int num = 	dictionariesService.updateDictionariesById(pd);
		if(num == 1){
			falg="true"; 
		}else if(num==0){
			falg="false"; 
		}
		return falg;
		}
	}

}
