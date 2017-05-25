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
import com.JavaClub.service.credit.EmailsModsService;
import com.JavaClub.service.system.UserService;
import com.JavaClub.util.PageData;
import com.JavaClub.util.UuidUtil;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript (邮件模板管理)
 * @author 余思
 * @createTime 2017年2月6日下午4:02:49
 * @version 1.0
 */

@Controller
@RequestMapping(value = "/emailsModsController")
public class EmailsModsController extends BaseController{


	@Autowired
	public EmailsModsService emailsModsService;

	@Autowired
	private UserService userService;
	/**
	 * 
	 * @descript (所有邮件模板列表
	 * @author 余思
	 * @since 2017年2月7日下午5:16:07
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEmailsModslist", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView querykeywordinfo(Page page) throws Exception {
		ModelAndView mv=new ModelAndView("system/emailsmods_list");
		PageData pd = new PageData();
		List<Map<String, Object>> emailsModslist = emailsModsService.queryEmailsMods(pd);
		for(int i=0;i<emailsModslist.size();i++){
			//获取角色信息
			pd.put("USERID", emailsModslist.get(i).get("USER_ID"));
			List<Map<String,Object>> userList = userService.queryUserById(pd);
			emailsModslist.get(i).put("USER_ID", userList.get(0).get("NAME"));
		}
		mv.addObject("pd", pd);
		mv.addObject("emailsModslist", emailsModslist);
		return mv;
	}

	/**
	 * 
	 * @descript (跳转新增邮件模板界面)
	 * @author 余思
	 * @since 2017年2月6日下午4:30:29
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toCreateemailsMods")
	public ModelAndView toCreateSysKeyword()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("pd", pd);
		pd.put("STATUS", "1");
		List<Map<String, Object>> emailsModslist = emailsModsService.queryEmailsMods(pd);
		if(emailsModslist.isEmpty()){
			mv.addObject("stu","1");
		}else{
			mv.addObject("stu","0");
		}
		mv.setViewName("system/emailsmods_add1");
		return mv;
	}

	/**
	 * 
	 * @descript (新增邮件模板)
	 * @author 余思
	 * @since 2017年2月7日上午10:11:19
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertEmailsMods", produces = "text/html;charset=UTF-8")
	public String insertkeyword() throws Exception{
		PageData pd = this.getPageData();
		//获取当前用户ID
		pd.put("uId", this.getUser().getUSER_ID());
		pd.put("uuId", UuidUtil.get32UUID());
		List<Map<String, Object>> emailsModslist = emailsModsService.queryEmailsModsIDflag(pd);
		if(emailsModslist.isEmpty()){
			if(pd.get("mods") !=null){
				emailsModsService.insertEmailsMods(pd);
				emailsModslist = emailsModsService.queryEmailsModsIDflag(pd);
				pd.put("uuId", emailsModslist.get(0).get("ID"));

				if("1".equals(pd.get("status"))){
					emailsModsService.updateemailsModsstatus(pd);
				}
				pd.put("modsId", UuidUtil.get32UUID());
				int t= emailsModsService.insertEmailsModsAttr(pd);
				return Verification.getResultString(t);
			}else{
				int t=emailsModsService.insertEmailsMods(pd);
				if("1".equals(pd.get("status"))){
					emailsModsService.updateemailsModsstatus(pd);
				}
				return Verification.getResultString(t);
			}
		}else{
			return Verification.getResultString(10);
		}

	}
	/**
	 * 
	 * @descript (删除邮件模板)
	 * @author 余思
	 * @since 2017年2月7日上午10:11:39
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteEmailsMods", produces = "text/html;charset=UTF-8")
	public String deletekeyword() throws Exception{
		PageData pd = this.getPageData();
		int num = emailsModsService.delemailsMods(pd);
		emailsModsService.delemailsModsAttr(pd);
		return Verification.getResultString(num);
	}

	/**
	 * 
	 * @descript (进入修改)
	 * @author 余思
	 * @since 2017年2月7日上午10:12:08
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryEmailsModsById", produces = "text/html;charset=UTF-8")
	public ModelAndView querykeywordById() throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();

		//查询用户数据
		List<Map<String,Object>> emailsMods_list = emailsModsService.queryEmailsModsByIdInfo(pd);
		List<Map<String,Object>> emailsModsAttr_list = emailsModsService.queryEmailsModsByIdAttrInfo(pd);
		String strID="";
		for(int i=0;i<emailsModsAttr_list.size();i++){
			strID += emailsModsAttr_list.get(i).get("ID")+"@";
		}
		mv.addObject("EmailsMods_list", emailsMods_list.get(0));
		mv.addObject("EmailsModsAttr_list", emailsModsAttr_list);
		mv.addObject("strID", strID);
		pd.put("STATUS", "1");
		List<Map<String, Object>> emailsModslist = emailsModsService.queryEmailsMods(pd);
		if("0".equals(emailsMods_list.get(0).get("ACTIVE"))){
			if(emailsModslist.isEmpty()){
				mv.addObject("stu","1");
			}else{
				mv.addObject("stu","0");
			}
		}else{
			mv.addObject("stu","1");
		}


		mv.setViewName("system/emailsmods_eat2");

		return mv;
	}
	/**
	 * 
	 * @descript (邮件模板信息修改
	 * @author 余思
	 * @since 2017年2月7日下午5:16:26
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateEmailsModsById", produces = "text/html;charset=UTF-8")
	public String updatekeyword() throws Exception{
		PageData pd = this.getPageData();
		//获取当前用户ID
		pd.put("uId", this.getUser().getUSER_ID());
		if("1".equals(pd.get("status"))){
			emailsModsService.updateemailsModsstatus(pd);
		}
		int num = emailsModsService.updateemailsModsById(pd);
		if(pd.get("mods") !=null){
			String[] StridArray = pd.get("hID").toString().split("@");
			for(int i=0;i<StridArray.length;i++){
				pd.put("EId", StridArray[i]);
				emailsModsService.delemailsModsIDAttr(pd);
			}
			pd.put("modsId", UuidUtil.get32UUID());
			pd.put("uuId", pd.get("ID"));
			emailsModsService.insertEmailsModsAttr(pd);
		}
		return Verification.getResultString(num);
	}
}
