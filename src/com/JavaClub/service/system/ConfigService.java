package com.JavaClub.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

/**
 * 配置项业务层
 * @author gongzhiqiang
 *
 */
@Service("configService")
public class ConfigService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 查询所有配置项
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryConfiglistPage(Page page) throws Exception {
		List configList = (List) dao.findForList("ConfigMapper.queryConfiglistPage", page);
		
		return configList;
	}
	
	/**
	 * 查询配置项信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryConfigInfo(PageData pd) throws Exception {
		List configList = (List) dao.findForList("ConfigMapper.queryConfigInfo", pd);
		
		return configList;
	}
	
	/**
	 * 查询配置项的父ID
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List querySysParnet() throws Exception {
		List parnetList = (List) dao.findForList("ConfigMapper.querySysParnet", "");
		
		return parnetList;
	}
	
	/**
	 * 增加配置项
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public String insertConfig(PageData pd) throws Exception {
		String flag = "fail";
		int num = 0;
		
		num = (int) dao.save("ConfigMapper.insertConfig", pd);
		
		if(0 < num){
			flag =  "success";
		}
		
		return flag;
	}
	
	
	/**
	 * 修改配置项
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public String updateConfig(PageData pd) throws Exception {
		String flag = "fail";
		int num = (int) dao.update("ConfigMapper.updateConfig", pd);
		
		if(0 < num){
			flag =  "success";
		}
		
		return flag;
	}
	
	/**
	 * 批量删除配置项
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String deleteConfigs(List<String> list) throws Exception {
		String flag = "fail";
		int num = (int) dao.batchDelete("ConfigMapper.deleteConfigs", list);
		
		if(0 < num){
			flag =  "success";
		}
		
		return flag;
	}
}
