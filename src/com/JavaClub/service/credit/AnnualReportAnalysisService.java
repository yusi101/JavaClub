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
@Service("annualReportAnalysisService")
public class AnnualReportAnalysisService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	final String queryAnnualrlinfoss = "AnnualReportAnalysisMapper.queryAnnualrlinfo";				

	final String queryAnnualrindustryss = "AnnualReportAnalysisMapper.queryAnnualrindustry";

	/**
	 * 
	 * @descript (区域营业总额查询)
	 * @author 余思
	 * @since 2017年1月9日下午2:41:14
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryAnnualrlinfo(PageData pd) throws Exception {
		return (List<Map<String, Object>>)  dao.findForList(queryAnnualrlinfoss,pd);
	}
	/**
	 * 
	 * @descript (区域营业行业总额查询)
	 * @author 余思
	 * @since 2017年1月9日下午2:41:14
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryAnnualrindustry(PageData pd) throws Exception {
		return (List<Map<String, Object>>)  dao.findForList(queryAnnualrindustryss,pd);
	}
}
