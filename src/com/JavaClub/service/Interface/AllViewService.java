package com.JavaClub.service.Interface;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript 全景视图业务层
 * @author 龚志强
 * @createTime 2016年9月14日下午2:44:45
 * @version 1.0
 */
@Service("allViewService")
public class AllViewService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//全景视图
	final String queryAllView = "AllViewMapper.queryAllView";

	/**
	 * 
	 * @descript 全景视图
	 * @author 龚志强
	 * @since 2016年9月14日下午2:44:55
	 * @param pd 参数集合
	 * @return 返回查询结果集
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryAllView(PageData pd) throws Exception{
		List list =(List) dao.findForList(queryAllView, pd);
		return list;
	}	
}