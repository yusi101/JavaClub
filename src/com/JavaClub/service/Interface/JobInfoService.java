package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

@Service("jobInfoService")
public class JobInfoService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 
	 * @descript (得到招聘信息  分页)
	 * @author 李海涛
	 * @since 2016年9月19日下午12:50:38
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryJobInfo(Page page) throws Exception {
		List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("JobInfoMapper.queryJobInfolistPage",page);
		return list;
	}
	/**
	 * 
	 * @descript (得到招聘信息  不分页)
	 * @author 李海涛
	 * @since 2016年9月19日下午12:49:43
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryJobInfo(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("JobInfoMapper.queryJobInfo",pd);
	}
}