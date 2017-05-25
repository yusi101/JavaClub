package com.JavaClub.service.system;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

@Service("logService")
public class LogService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/**
	 * 查询企业信息，分页
	 * @author 李海涛
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public int InsertLog(Map<String, Object> map){
		try {
			//dao.save("LogMapper.InsertLog", map);
		} catch (Exception e) {
			return 0;
		} 
		return 1;
	}
	public int InsertDBLog(Map<String, Object> map){
		try {
			dao.save("LogMapper.InsertDBLog", map);
		} catch (Exception e) {
			return 0;
		} 
		return 1;
	}
	
	/**
	 * 
	 * @descript (查询用户操作日志)
	 * @author 李文海
	 * @since 2016年10月26日上午9:31:55
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> userLoglistPage(Page page)throws Exception{
		return (List<PageData>) dao.findForList("LogMapper.userLoglistPage", page);
	}
	
	/**
	 * 
	 * @descript (根据id查看用户操作日志详情)
	 * @author 李文海
	 * @since 2016年10月26日上午9:54:16
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryUserLogById(PageData pd)throws Exception{
		return (PageData) dao.findForObject("LogMapper.queryUserLogById", pd);
	}
	
	/**
	 * 
	 * @descript (查询数据库操作日志)
	 * @author 吕永青
	 * @since 2016年10月26日上午10:37:06
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> queryDatabaseLoglistPage(Page page)throws Exception{
		return (List<PageData>) dao.findForList("LogMapper.queryDatabaseLoglistPage", page);
	}
	
	/**
	 * 
	 * @descript (查询数据库操作日志详情)
	 * @author 吕永青
	 * @since 2016年10月26日上午10:37:26
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData queryDatabaseLogById(PageData pd)throws Exception{
		return (PageData) dao.findForObject("LogMapper.queryDatabaseLogById", pd);
	}
}
