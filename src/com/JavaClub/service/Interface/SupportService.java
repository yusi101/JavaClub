package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

@Service("supportService")
public class SupportService {
    
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**
	 * 
	 * @descript 得到扶持信息，分页
	 * @author 李海涛
	 * @since 2016年9月12日下午1:12:18
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> querySupportinfo(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("SupportMapper.querySupportinfolistPage",page);
	}
	
	
	/**
	 * 
	 * @descript 得到扶持信息，不分页
	 * @author 李海涛
	 * @since 2016年9月12日下午1:12:38
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> querySupportinfo(PageData pd) throws Exception {
        return  (List<Map<String, Object>>) dao.findForList("SupportMapper.querySupportinfo",pd);
    }
	
}
