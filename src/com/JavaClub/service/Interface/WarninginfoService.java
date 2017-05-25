package com.JavaClub.service.Interface;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

@Service("warninginfoService")
public class WarninginfoService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 *获取预警信息
	 * @author 李海涛
	 * @param pripid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List  queryWarningInfo(PageData pd) throws Exception{
		List list =(List) dao.findForList("WarningMapper.queryWarningInfo", pd);
		return list;
	}
}	
