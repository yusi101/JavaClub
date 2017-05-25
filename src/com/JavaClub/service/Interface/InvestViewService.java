package com.JavaClub.service.Interface;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * 投资链图业务层
 * @author 龚志强
 *
 */
@Service("investViewService")
public class InvestViewService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//投资链图
	final String queryInvestView = "InvestViewMapper.queryInvestView";
	//自然人对外投资
	final String queryInvAllEnt = "InvestViewMapper.queryInvAllEnt";
	//非自然对外投资
	final String queryInvestViewTwo = "InvestViewMapper.queryInvestViewTwo";
	
	/**
	 * 投资链图
	 * @param pd 参数集合
	 * @return 投资链图结果集
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryInvestView(PageData pd) throws Exception{
		List list =(List) dao.findForList(queryInvestView, pd);
		return list;
	}

	/**
	 * 自然人对外投资
	 * @param cerno 身份证号
	 * @return 返回对外投资结果集
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryInvAllEnt(String cerno) throws Exception {
		List aeList = (List) dao.findForList(queryInvAllEnt, cerno);
		return aeList;
	}

	/**
	 * 非自然对外投资
	 * @param regno 注册号
	 * @return 返回对外投资结果集
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryInvestViewTwo(String regno) throws Exception {
		List epList = (List) dao.findForList(queryInvestViewTwo, regno);
		return epList;
	}
}