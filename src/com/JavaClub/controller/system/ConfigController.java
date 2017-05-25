package com.JavaClub.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.annotation.system.log.SysUserLog;
import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.system.ConfigService;
import com.JavaClub.util.Const;
import com.JavaClub.util.JsonUtils;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StrUtil;
import com.JavaClub.util.UuidUtil;

/**
 * 系统配置项
 * @author gongzhiqiang
 *
 */
@Controller
@RequestMapping(value="/configController")
public class ConfigController extends BaseController {
	
	public static final String KEYNAME = "keyname";
	public static final String VALUE = "value";
	
	@Resource(name = "configService")
	private ConfigService configService;
	
	/**
	 * 配置项信息查询展示
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryConfiglistPage")
	@SuppressWarnings("unchecked")
	public ModelAndView queryConfiglistPage(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//设置分页
		page.setPd(pd);
		//获取所有配置项信息
		List<Map<String,Object>> configList = configService.queryConfiglistPage(page);
		
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject("configList", configList);
		mv.setViewName("config/config_list");
		
		return mv;
	}
	
	/**
	 * 进入添加配置项页面
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toCreateConfig")
	@SuppressWarnings("unchecked")
	public ModelAndView toCreateConfig()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//获取父节点JSON数据
		List<Map<String,Object>> parentList = new ArrayList<Map<String,Object>>();
		getParentZTree(configService.querySysParnet(), parentList, "");
		
		mv.addObject("pd", pd);
		mv.addObject("parentList", JsonUtils.toJson(parentList));
		mv.setViewName("config/config_add");
		
		return mv;
	}
	
	/**
	 * 添加配置项
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createConfig", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String createConfig()throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();	
		
		//如果排序为空，默认为0
		if("".equals(pd.getString("order"))){
			pd.put("order", "0");
		}	
		pd.put("id", UuidUtil.get32UUID());

		String flag = configService.insertConfig(pd);
			
		return flag;
	}
	
	/**
	 * 进入修改配置项页面
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/configUpUI")
	@SuppressWarnings("unchecked")
	public ModelAndView configUpUI()throws Exception{
		ModelAndView mv = this.getModelAndView();
		Page page = new Page();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//获取修改的配置项数据
		page.setPd(pd);
		Map<String,Object> config =  (Map<String, Object>) configService.queryConfiglistPage(page).get(0);
		
		//获取父节点JSON数据
		List<Map<String,Object>> parentList = new ArrayList<Map<String,Object>>();
		getParentZTree(configService.querySysParnet(), parentList,config.get("PARENT_ID").toString());
		
		mv.addObject("pd", pd);
		mv.addObject("config", config);
		mv.addObject("parentList", JsonUtils.toJson(parentList));
		mv.setViewName("config/config_up");
		
		return mv;
	}
	
	/**
	 * 修改配置项
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/configUp")
	@ResponseBody
	@SysUserLog("配置项 - 修改配置项")
	public String configUp(HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();	

		String flag = configService.updateConfig(pd);
		//修改成功开启读取配置项 
		if("success".equals(flag)){
			if("BACKGROUND".equals(pd.getString(KEYNAME))){
				request.getServletContext().setAttribute("sysTitle", pd.getString(VALUE));
			}
			//获取邮箱发送者的名称
			if("emailName".equals(pd.getString(KEYNAME))){
				Const.EMAIL_NAME = pd.getString(VALUE);
			}
			//获取邮箱协议
			if("emailProtocol".equals(pd.getString(KEYNAME))){
				Const.EMAILPROTOCOL = pd.getString(VALUE);
			}
			//获取邮箱端口
			if("emailPort".equals(pd.getString(KEYNAME))){
				Const.EMAILPORT = Integer.valueOf(pd.getString(VALUE));
			}
			//获取邮箱用户
			if("emailUser".equals(pd.getString(KEYNAME))){
				Const.EMAILUSER = pd.getString(VALUE);
			}			
			//获取邮箱密码
			if("emailPassword".equals(pd.getString(KEYNAME))){
				Const.EMAILPASSWORD = pd.getString(VALUE);
			}		
		}
			
		return flag;
	}
	
	/**
	 * 批量删除配置项
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/configDel")
	@ResponseBody
	public String configDel()throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		List<String> list = new ArrayList<String>();
		
		String delStr = pd.getString("ids");
		String delArr[] = delStr.split("::");
		
		for(int i = 0; i < delArr.length; i++){
			list.add(delArr[i]);
		}

		String flag = configService.deleteConfigs(list);
			
		return flag;
	}
	
	/**
	 * 设置父配置数据
	 * @param list
	 * @param parentList
	 */
	public void getParentZTree(List<Map<String,Object>> list, List<Map<String,Object>> parentList, String pid){
		Map<String,Object> mapdata = null;
		
		for(int i = 0; i < list.size(); i++){
			//修改
			if(!"".equals(pid) && null != pid){
				if(pid.equals(list.get(i).get("ID"))){
					mapdata = setNode(list.get(i).get("ID"), list.get(i).get("PARENT_ID"), list.get(i).get("NAME"), false, true);	
				}else{
					mapdata = setNode(list.get(i).get("ID"), list.get(i).get("PARENT_ID"), list.get(i).get("NAME"), false, false);	
				}
			//添加
			}else{
				mapdata = setNode(list.get(i).get("ID"), list.get(i).get("PARENT_ID"), list.get(i).get("NAME"), false, false);	
			}
			parentList.add(mapdata);
		}
	}

	/**
	 * 
	 * @descript 设置树节点
	 * @author 龚志强
	 * @since 2016年9月20日上午11:36:44
	 * @param id 节点ID
	 * @param pId 父节点ID
	 * @param name 节点名称
	 * @param open 节点是否打开
	 * @param checked 节点是否选中
	 * @return
	 */
	public Map<String,Object> setNode(Object id, Object pId, Object name, boolean open, boolean checked){
		Map<String,Object> pd = new HashMap<String,Object>();
		
		pd.put("id", StrUtil.delNull(id));
		pd.put("pId", StrUtil.delNull(pId));
		pd.put("name", StrUtil.delNull(name));
		pd.put("open", open);
		pd.put("checked", checked);
		
		return pd;
	}
	
}
