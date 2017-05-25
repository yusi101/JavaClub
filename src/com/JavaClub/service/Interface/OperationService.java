
package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

/**
 * @descript  
 * @author 李坡
 * @createTime 2016年9月20日下午4:06:51
 * @version 1.0
 */
@Service("OperationService")
public class OperationService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 
	 * @descript 查询每个公司的 访问量
	 * @author 李坡
	 * @since 2016年9月20日下午4:08:23
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> sysOperationlogDetailslistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OperationMapper.queryOperationlistPage", page);
	}
	
	/**
	 * 
	 * @descript 分类信息统计详情
	 * @author 李坡
	 * @since 2016年9月20日下午4:09:24
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryClassifyInfoXQ(PageData pd) throws Exception {
		return (List<Map<String, Object>>)  dao.findForList("OperationMapper.queryOperationInfoXQ",pd);
	}
	
	
	/**
	 * 
	 * @descript 查询分类信息统计
	 * @author 李坡
	 * @since 2016年9月20日下午4:29:03
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryClassifyInfo(PageData pd) throws Exception {
		return (List<Map<String, Object>>)  dao.findForList("OperationMapper.queryClassifyInfo",pd);
	}
	
	
	/**
	 * 
	 * @descript 点击系统流量分析详情
	 * @author 李坡
	 * @since 2016年9月21日上午11:00:40
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryOperationlistPage(Page page) throws Exception {
		return (List<Map<String, Object>>) dao.findForList("OperationMapper.querySystemFlowAnalysislistPage",page);
	}
	
	/**
	 * 
	 * @descript 系统流量分析
	 * @author 李坡
	 * @since 2016年9月21日上午11:07:40
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> querySystemFlowAnalysis(PageData pd) throws Exception {
		return (List<Map<String,Object>>) dao.findForList("OperationMapper.querySystemFlowAnalysis",pd);
	}
	
}
 