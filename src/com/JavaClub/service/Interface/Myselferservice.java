package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

@Service("myselferservice")
public class Myselferservice {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 查询企业自主公示信息除股东出资人信息外
	 * @author 李海涛
	 * @param pripid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List  queryAllMyselferinfo(PageData pd) throws Exception{
		List list =(List) dao.findForList("MyselferMapper.queryAllMyselferinfo", pd);
		return list;
	}
	
	/**
	 * 股东出资人信息
	* @author 李海涛
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryShareholdersandInvestment(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForListMap("MyselferMapper.queryShareholdersandInvestment",pd);	
	}
	
	
	
	
	//<!-- 企业资产状况信息pdf 年报 -->
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> getAnBaseInfo(PageData pd)throws Exception{
		return  (List<Map<String, Object>>) dao.findForList("MyselferMapper.getAnBaseInfo",pd);
	}
	//<!-- 企业网站信息pdf  -->
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> getAnWebsiteInfo(String ANCHEID)throws Exception{
		return  (List<Map<String, Object>>) dao.findForList("MyselferMapper.getAnWebsiteInfo",ANCHEID);
	}
}

