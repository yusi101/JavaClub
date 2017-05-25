package com.JavaClub.service.Interface;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

@Service("infomessageService")
public class InfomessageService {
    
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 企业或个体基本工商信息及其他信息
	 * @author 李海涛
	 * @param pripid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List  queryAllBaseInfo(PageData pd) throws Exception{
		List list =(List) dao.findForList("InfoMessageMapper.queryAllBaseInfo", pd);
		return list;
	}
}
