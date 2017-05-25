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
 * @descript (舆情热度业务层)
 * @author 汤彬
 * @createTime 2016年9月30日下午2:18:45
 * @version 1.0
 */
@Service("hotFeelingService")
public class HotFeelingService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	final String queryFollow = "HotFeelingMapper.queryFollow"; //关注热度
	final String queryComplatnt = "HotFeelingMapper.queryComplatnt"; //投诉热度
	final String queryComment ="HotFeelingMapper.queryComment"; //评论热度
	final String queryFollowDetail = "HotFeelingMapper.queryFollowDetail";//关注热度详情
	final String queryComplatntDetail = "HotFeelingMapper.queryComplatntDetail";//投诉热度详情
	final String queryCommentDetail = "HotFeelingMapper.queryCommentDetail";//投诉热度详情
	
	final String queryHotspotAnalysis="HotFeelingMapper.queryHotspotAnalysis";//查询热词热点分析
	final String queryHotEnterprise="HotFeelingMapper.queryHotEnterprise";//查询企业热度分析
	final String queryHotspotAnalysislistPage="HotFeelingMapper.queryHotspotAnalysislistPage";//点击查询热点分析详情
	final String queryHotEnterpriselistPage="HotFeelingMapper.queryHotEnterpriselistPage";//查询企业热度分析详情
	/**
	 * 
	 * @descript (关注热度)
	 * @author 汤彬
	 * @since 2016年9月30日下午2:23:23
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryFollowInfo(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList(queryFollow,pd);
	}
	
	/**
	 * 
	 * @descript (投诉热度)
	 * @author 汤彬
	 * @since 2016年9月30日下午2:24:18
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryComplatntInfo(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList(queryComplatnt,pd);
	}
	
	/**
	 * 
	 * @descript (评论热度)
	 * @author 汤彬
	 * @since 2016年9月30日下午2:25:47
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryCommentInfo(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList(queryComment,pd);
	}
	
	/**
	 * 
	 * @descript (关注热度详情)
	 * @author 汤彬
	 * @since 2016年9月30日下午4:48:57
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryFollowDetailInfo(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList(queryFollowDetail,page);
	}
	
	/**
	 * 
	 * @descript (投诉热度详情)
	 * @author 汤彬
	 * @since 2016年9月30日下午4:49:47
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryComplatntDetailInfo(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList(queryComplatntDetail,page);
	}
	
	/**
	 * 
	 * @descript (评论热度详情)
	 * @author 汤彬
	 * @since 2016年9月30日下午4:50:35
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryCommentDetailInfo(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList(queryCommentDetail,page);
	}
	
	/**
	 * 
	 * @descript (查询热词热点分析)
	 * @author 李海涛
	 * @since 2016年12月8日下午12:07:17
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryHotspotAnalysis(PageData pd) throws Exception {
		List<Map<String,Object>> HotList = (List<Map<String,Object>>) dao.findForList(queryHotspotAnalysis,pd);
		return HotList;
	}
	
	/**
	 * 
	 * @descript (查询企业热度分析)
	 * @author 李海涛
	 * @since 2016年12月8日下午12:07:02
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryHotEnterprise(PageData pd) throws Exception {
		List<Map<String,Object>> HotList = (List<Map<String,Object>>) dao.findForList(queryHotEnterprise,pd);
		return HotList;
	}
	
	/**
	 * 
	 * @descript (点击查询热点分析详情)
	 * @author 李海涛
	 * @since 2016年12月8日下午12:06:44
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryHotspotAnalysislistPage(Page page) throws Exception {
		List<Map<String,Object>> HotList = (List<Map<String,Object>>) dao.findForList(queryHotspotAnalysislistPage,page);
		return HotList;
	}
	
	/**
	 * 
	 * @descript (点击查询企业热度分析详情)
	 * @author 李海涛
	 * @since 2016年12月8日下午12:06:33
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryHotEnterpriselistPage(Page page) throws Exception {
		List<Map<String,Object>> HotList = (List<Map<String,Object>>) dao.findForList(queryHotEnterpriselistPage,page);
		return HotList;
	}
}