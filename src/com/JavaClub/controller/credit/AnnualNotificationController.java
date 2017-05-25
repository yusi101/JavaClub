package com.JavaClub.controller.credit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.credit.AnnualNotificationService;
import com.JavaClub.service.credit.EmailsModsService;
import com.JavaClub.service.system.ConfigService;
import com.JavaClub.util.Const;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.PageData;
import com.JavaClub.util.SmsUtil;
import com.JavaClub.util.UuidUtil;

/**
 * 年报维护
 * @descript (用一句话描述改方法的作用)
 * @author 龚志强
 * @createTime 2017年2月7日下午2:09:26
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/annualNotificationController")
public class AnnualNotificationController extends BaseController{

	/*年报人工推送业务层*/
	@Resource(name = "annualNotificationService")
	private AnnualNotificationService anService;
	@Resource(name = "configService")
	private ConfigService configService;
	@Autowired
	public EmailsModsService emailsModsService;
	/**
	 * 年报人工推送列表
	 * @descript 
	 * @author 龚志强
	 * @since 2017年2月7日上午9:37:44
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryannualReport", produces = "text/html;charset=UTF-8")
	public ModelAndView queryannualReport(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = getPageData();
		
		List<Map<String,Object>> anList = anService.queryAnnualNotificationlistPage(page);
		
		/*设置返回数据和视图*/
		mv.addObject("pd", pd);
		mv.addObject("anList", anList);
		mv.setViewName("nbts/push");

		return mv;
	}
	
	/**
	 * 添加到年报发送历史查询
	 * @descript 
	 * @author 龚志强
	 * @since 2017年2月7日下午4:42:36
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sendAnnualNotice", produces = "text/html;charset=UTF-8")
	public String sendNotice() throws Exception{
		PageData pd = getPageData();

		/*获取当前用户ID和用户名*/
		if(null != getUser()){
			pd.put("uid", getUser().getUSER_ID());
			pd.put("uname", getUser().getNAME());
		}

		/*如果获取不到通知数据，就返回失败结果*/
		if("".equals(pd.getString("noticeInfo") + "")){
			return Const.FAIL;
		}
		
		if("".equals(pd.getString("selectOption") + "")){
			return Const.FAIL;
		}
		Page page=new Page();
		pd.put("configName", "年报邮件定时发送设置");
		page.setPd(pd);
		List<Map<String,Object>> configList = configService.queryConfiglistPage(page);
		if(null != configList && "0".equals(configList.get(0).get("VALUE"))){
			pd.put("STATUS", "1");
			List<Map<String, Object>> emailsModslist = emailsModsService.queryEmailsMods(pd);
			if(ListUtils.isNotEmpty(emailsModslist)){
				/*发送通知*/
				return anService.insertAnnualNotification(pd);
			}else{
				return "noMod";
			}
		}else{
			return "noet";
		}
		
	}
	
	/**
	 * 查询年报推送历史
	 * @descript
	 * @author 龚志强
	 * @since 2017年2月9日上午9:14:26
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "annualHistoryListPage", produces = "text/html;charset=UTF-8")
	public ModelAndView yearHistoryListPage(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = getPageData();
		page.setPd(pd);
		
		List<Map<String,Object>> anList = anService.annualHistorylistPage(page);
		
		mv.addObject("pd", pd);
		mv.addObject("anList", anList);
		mv.setViewName("nbts/yearHistory_list");

		return mv;
	}
	/**
	 * 
	 * @descript 年报定时推送列表
	 * @author 余思
	 * @since 2017年2月16日下午3:47:13
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryannualauto", produces = "text/html;charset=UTF-8")
	public ModelAndView queryannualauto(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = getPageData();
		List<Map<String,Object>> anList1 = anService.queryAnnualNotificationlist(page);
		List<Map<String,Object>> anList2 = anService.annualHistorylist(page);
		Date day=new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		pd.put("year", df.format(day)+"年");
		mv.addObject("pd", pd);
		mv.addObject("anList1", anList1.size());
		mv.addObject("anList2", anList2.size());
		mv.addObject("anList3", anList1.size()+anList2.size());
		mv.setViewName("nbts/auto_push");

		return mv;
	}
	/**
	 * 
	 * @descript 年报定时发送
	 * @author 余思
	 * @since 2017年2月20日下午4:32:29
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/autosendAnnualNotice", produces = "text/html;charset=UTF-8")
	public String autosendAnnualNotice() throws Exception {
		PageData pd = getPageData();
		pd.put("configName", "年报邮件定时发送设置");
		Page page=new Page();
		page.setPd(pd);
		pd.put("STATUS", "1");
		List<Map<String, Object>> emailsModslist = emailsModsService.queryEmailsMods(pd);
		if(ListUtils.isNotEmpty(emailsModslist)){
			List<Map<String,Object>> configList = configService.queryConfiglistPage(page);
			if(ListUtils.isNotEmpty(configList)){
				pd.put("id", configList.get(0).get("ID"));
			}
			pd.put("value", "1");
			configService.updateConfig(pd);
			return Const.SUCCESS;
		}else{
			return "noMod";
		}
	
	}
	
	/**
	 * 添加到年报发送历史查询
	 * @descript 
	 * @author 龚志强
	 * @since 2017年2月7日下午4:42:36
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertsmshistory", produces = "text/html;charset=UTF-8")
	public String insertsmshistory(Page page) throws Exception{
		PageData pd = getPageData();
		page.setPd(pd);
		List<Map<String, Object>> smsinfo = anService.selectsmsinfo(page);
		int num=0;
		if(ListUtils.isNotEmpty(smsinfo)){
			for(Map<String, Object> map:smsinfo){
				   	pd.put("sid", UuidUtil.get32UUID());
			        String msg=SmsUtil.sendSmsReport(map.get("PHONE").toString());
			        pd.put("results", msg);
			        pd.put("pripid", map.get("PRIPID"));
			        pd.put("entname", map.get("ENTNAME"));
			        pd.put("name", map.get("NAME"));
			        pd.put("phone", map.get("PHONE"));
			        anService.insertsmshistory(pd);
			        num++;
			}
		}
		return num+"条 success";		
	}
}