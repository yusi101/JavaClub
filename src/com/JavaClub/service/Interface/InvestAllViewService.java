package com.JavaClub.service.Interface;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript 投资链图业务层
 * @author 龚志强
 * @createTime 2016年9月14日下午3:04:14
 * @version 1.0
 */
@Service("investAllViewService")
public class InvestAllViewService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//投资全景
	final String queryInvestAllView = "InvestAllViewMapper.queryInvestAllView";
	//查询人名对外投资
	final String queryNameAllEnt = "InvestAllViewMapper.queryNameAllEnt";
	//查询企业对外投资
	final String queryEntAllEnt = "InvestAllViewMapper.queryEntAllEnt";

	/**
	 * 
	 * @descript 投资全景
	 * @author 龚志强
	 * @since 2016年9月14日下午3:04:24
	 * @param pd 参数集合
	 * @return 投资全景List集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryInvestAllView(PageData pd) throws Exception{
		List list =(List) dao.findForList(queryInvestAllView, pd);
		return list;
	}

	/**
	 * 
	 * @descript 查询人名对外投资
	 * @author 龚志强
	 * @since 2016年9月14日下午3:04:58
	 * @param cerno 身份证号
	 * @return 人名对外投资List集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryNameAllEnt(String cerno) throws Exception {
		List aeList = (List) dao.findForList(queryNameAllEnt, cerno);
		return aeList;
	}

	/**
	 * 
	 * @descript 查询企业对外投资
	 * @author 龚志强
	 * @since 2016年9月14日下午3:05:39
	 * @param regno 注册号
	 * @return 企业对外投资的List集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryEntAllEnt(String regno) throws Exception {
		List epList = (List) dao.findForList(queryEntAllEnt, regno);
		return epList;
	}
}