package com.JavaClub.service.Interface;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

@Service("annualreportService")
public class AnnualReportService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**
	 * 个体年报信息
	 * @author 龚志强
	 * @param pripid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List GetPBAnnualReport(PageData pd) throws Exception{
		List list =(List) dao.findForList("AnnualReportMapper.GetPBAnnualReport", pd);
		return list;
	}
	
	/**
	 * 农专年报信息
	 * @author 李龚志强
	 * @param pripid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List GetSFCAnnualReport(PageData pd) throws Exception{
		List list =(List) dao.findForList("AnnualReportMapper.GetSFCAnnualReport", pd);
		return list;
	}
	
	/**
	 * 企业年报信息
	 * @author 龚志强
	 * @param pripid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List GetENTAnnualReport(PageData pd) throws Exception{
		List list =(List) dao.findForList("AnnualReportMapper.GetENTAnnualReport", pd);
		return list;
	}
}