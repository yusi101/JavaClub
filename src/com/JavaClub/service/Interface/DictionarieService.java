package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

@Service("dictionarieService")
public class DictionarieService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	

	//<!-- 查询字典表中的数据-->
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryDictionarieInfo(PageData pd) throws Exception {
		return (List<Map<String,Object>>) dao.findForList("DictionariesMapper.findDictionaries", pd);
	}
	
	
	
	
}
