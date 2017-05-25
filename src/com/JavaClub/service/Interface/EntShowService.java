package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

@Service("entShowService")
public class EntShowService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 企业展示
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryEntShowInfo(PageData pd) throws Exception{
	    List<Map<String, Object>> list =(List<Map<String, Object>>) dao.findForList("EntShowMapper.queryEntShowInfo", pd);
		return list;
	}
	
}
