package com.JavaClub.service.credit;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (征信报告)
 * @author 余思
 * @createTime 2016年11月29日下午2:06:06
 * @version 1.0
 */

@Service("ReportService")
public class ReportService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	final String insertReport = "ReportMapper.insertReport";					//插入征信报告发送历史
	
	final String queryReportCount = "ReportMapper.queryReportCount";					//查询全部征信报告发送历史
	
	final String deleteReport = "ReportMapper.deleteReport";					//删除征信报告发送历史
	
	final String queryReportById = "ReportMapper.queryReportById";					//查询单条征信报告发送历史
	
	final String queryReportCountUser = "ReportMapper.queryReportCountUser";	
	
	final String queryReportName = "ReportMapper.queryReportName";	      //根据企业 查询征信报告发送历史
	
	/**
	 * 
	 * @descript (插入征信报告发送历史)
	 * @author 余思
	 * @since 2016年11月29日下午2:09:53
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int insertReporthis(PageData pd) throws Exception{
		return (int) dao.save(insertReport, pd);
	}
	/**
	 * 
	 * @descript (查询全部的征信报告发送历史)
	 * @author 余思
	 * @since 2016年11月30日上午9:03:09
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryReportCount(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList(queryReportCount,page);
	}
	
	/**
	 * 
	 * @descript (删除单条信息)
	 * @author 余思
	 * @since 2016年11月30日上午9:37:22
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public int deleteReport(PageData page) throws Exception {
		return (int) dao.delete(deleteReport, page);
	}
	/**
	 * 
	 * @descript ( 查询单个记录信息)
	 * @author 余思
	 * @since 2016年11月30日上午9:54:52
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map queryReportById(PageData pd) throws Exception {
		return (Map) dao.findForObject(queryReportById, pd);
	}
	/**
	 * 
	 * @descript (根据ID查询用户账号)
	 * @author 余思
	 * @since 2016年11月30日上午9:54:52
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public  List<Map<String, Object>> queryReportCountUser (PageData pd) throws Exception{
		return  dao.findForListMap(queryReportCountUser, pd);
	}
	
	/**
	 * 
	 * @descript (查询记录信息)
	 * @author 余思
	 * @since 2016年12月1日下午6:20:11
	 * @param page
	 * @return
	 * @throws Exception
	 */
   @SuppressWarnings("unchecked")
   public List<Map<String, Object>> queryReportName(Page page) throws Exception {
       return  (List<Map<String, Object>>) dao.findForList(queryReportName, page);
   }
}
