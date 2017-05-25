package com.JavaClub.service.system;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;


/**
 * 
 * @descript (访客记录)
 * @author 汤彬
 * @createTime 2016年10月11日下午1:44:19
 * @version 1.0
 */
@Service("visitorService")
public class VisitorService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	final String queryVisitorlistPage = "VisitorMapper.queryVisitorlistPage"; //查询访客记录分页
	final String createVisitor = "VisitorMapper.createVisitor";//添加访客记录
	final String deleteVisitor = "VisitorMapper.deleteVisitor";//删除访客记录
	final String updateVisitor = "VisitorMapper.updateVisitor";//修改访客记录
	
	/**
	 * 
	 * @descript (查询访客记录)
	 * @author 汤彬
	 * @since 2016年10月11日下午1:48:56
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryVisitorlistPage(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList(queryVisitorlistPage,page);
	}
	
	
	/**
	 * 
	 * @descript (添加访客记录)
	 * @author 汤彬
	 * @since 2016年10月11日下午1:49:17
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int createVisitor(PageData pd) throws Exception {
		return (int)dao.save(createVisitor, pd);
	} 
	
	/**
	 * 
	 * @descript (删除访客记录)
	 * @author 汤彬
	 * @since 2016年10月11日下午1:49:36
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int deleteVisitor(PageData pd) throws Exception {
		return (int)dao.save(deleteVisitor, pd);
	} 
	
	/**
	 * 
	 * @descript (修改访客记录)
	 * @author 汤彬
	 * @since 2016年10月11日下午1:49:59
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int updateVisitor(PageData pd) throws Exception {
		return (int)dao.save(updateVisitor, pd);
	} 
}
