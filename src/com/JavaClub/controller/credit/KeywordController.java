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
import com.JavaClub.service.credit.KeywordService;
import com.JavaClub.service.system.UserService;
import com.JavaClub.util.PageData;
import com.JavaClub.util.UuidUtil;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript 敏感词管理
 * @author 余思
 * @createTime 2016年12月6日下午5:46:58
 * @version 1.0
 */

@Controller
@RequestMapping(value = "/keywordController")
public class KeywordController extends BaseController{

	@Autowired
	public KeywordService keywordService;

	@Autowired
	private UserService userService;
	/**
	 * 
	 * @descript (查询所有关键词)
	 * @author 余思
	 * @since 2016年12月7日上午10:42:08
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querykeywordinfo", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView querykeywordinfo(Page page) throws Exception {
		ModelAndView mv=new ModelAndView("system/keyword_list");
		PageData pd = new PageData();
		pd = this.getPageData();
		//设置分页
		page.setPd(pd);

		List<Map<String,Object>> keywordlist = keywordService.queryKeywordlistPage(page);
		for(int i=0;i<keywordlist.size();i++){
			//获取角色信息
			pd.put("USERID", keywordlist.get(i).get("CREATEUSER_ID"));
			List<Map<String,Object>> userList = userService.queryUserById(pd);
			keywordlist.get(i).put("CREATEUSER_ID", userList.get(0).get("NAME"));
		}
		mv.addObject("pd", pd);
		mv.addObject("keywordlist", keywordlist);
		return mv;
	}

	/**
	 * 
	 * @descript 进入新增关键词界面
	 * @author 余思
	 * @since 2016年12月7日上午10:40:47
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toCreateSysKeyword")
	public ModelAndView toCreateSysKeyword()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.addObject("pd", pd);
		mv.setViewName("system/keyword_add");

		return mv;
	}

	/**
	 * 
	 * @descript (新增关键词)
	 * @author 余思
	 * @since 2016年12月7日上午11:06:50
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertkeyword", produces = "text/html;charset=UTF-8")
	public String insertkeyword() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		//获取当前用户ID
		pd.put("uId", this.getUser().getUSER_ID());
		pd.put("uuId", UuidUtil.get32UUID());
		List<Map<String, Object>> list = keywordService.queryName(pd);
		if (!list.isEmpty()) {
			return "error";
		}else{
			int i= keywordService.insertKeywords(pd);
			return Verification.getResultString(i);
		}
		
		
		
	}
	/**
	 * 
	 * @descript (删除关键词)
	 * @author 余思
	 * @since 2016年12月7日上午11:14:26
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deletekeyword", produces = "text/html;charset=UTF-8")
	public String deletekeyword() throws Exception{
		PageData pd = new PageData();
		pd=this.getPageData();
		int num = keywordService.deletekeyword(pd);
		return Verification.getResultString(num);
	}

	/**
	 * 
	 * @descript  进入修改用户页面
	 * @author 余思
	 * @since 2016年12月7日下午1:45:52
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/querykeywordById", produces = "text/html;charset=UTF-8")
	public ModelAndView querykeywordById() throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		//查询用户数据
		List<Map<String,Object>> keyword_list = keywordService.querykeywordByIdInfo(pd);
		mv.addObject("keyword_list", keyword_list.get(0));
		mv.setViewName("system/keyword_eat");

		return mv;
	}
	/**
	 * 
	 * @descript 关键字信息修改
	 * @author 余思
	 * @since 2016年12月7日下午1:48:30
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updatekeyword", produces = "text/html;charset=UTF-8")
	public String updatekeyword() throws Exception{
		PageData pd = new PageData();
		pd=this.getPageData();
		//获取当前用户ID
		pd.put("uId", this.getUser().getUSER_ID());
		List<Map<String, Object>> list = keywordService.queryName(pd);
		if (!list.isEmpty()) {
			return "error";
		}else{
			int num = keywordService.updatekeywordById(pd);
			return Verification.getResultString(num);
		}
	}
}
