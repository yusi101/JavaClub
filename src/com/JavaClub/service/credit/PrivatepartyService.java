package com.JavaClub.service.credit;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (区域营业额分析)
 * @author 余思
 * @createTime 2017年1月6日下午4:39:10
 * @version 1.0
 */
@Service("privatepartyService")
public class PrivatepartyService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	final String queryPrivateparty = "PrivatepartyMapper.queryPrivateparty";				


	/**
	 * 
	 * @descript (非公党建人员分析)
	 * @author 余思
	 * @since 2017年1月13日下午5:02:48
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryPrivateparty(PageData pd) throws Exception {
		return (List<Map<String, Object>>)  dao.findForList(queryPrivateparty,pd);
	}
}
