package com.JavaClub.service.credit;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (经营年限分析)
 * @author 余思
 * @createTime 2017年2月15日下午3:54:37
 * @version 1.0
 */
@Service("operatingyearsService")
public class OperatingyearsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	final String queryOperatingyears = "OperatingyearsMapper.queryOperatingyears";				


	/**
	 * 
	 * @descript (经营年限分析)
	 * @author 余思
	 * @since 2017年2月15日下午3:55:06
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryOperatingyears(PageData pd) throws Exception {
		return (List<Map<String, Object>>)  dao.findForList(queryOperatingyears,pd);
	}
}
