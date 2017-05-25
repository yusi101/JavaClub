package com.JavaClub.service.Interface;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript 企业历程业务层
 * @author 龚志强
 * @createTime 2016年9月14日下午2:45:49
 * @version 1.0
 */
@Service("entCourseService")
public class EntCourseService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	final String queryEntCourse = "EntCourseMapper.queryEntCourse";			//企业历程
	final String queryJobInfo = "EntCourseMapper.queryJobInfo";				//招聘详情
	final String queryBiddin = "EntCourseMapper.queryBiddin";				//招标详情
	final String queryEntnews = "EntCourseMapper.queryEntnews"; 			//新闻详情
	final String queryPatent = "EntCourseMapper.queryPatent";				//专利详情
	final String queryBrand = "EntCourseMapper.queryBrand";					//商标详情
	final String queryEntRecoder = "EntCourseMapper.queryEntRecoder";		//企业变更详情
	final String querySingleRecoder = "EntCourseMapper.querySingleRecoder";	//个体变更详情
	final String queryWorkcopyRight = "EntCourseMapper.queryWorkcopyRight";	//著作权详情
	final String queryCopyright = "EntCourseMapper.queryCopyright";			//软件著作权详情
	final String queryHonorinfo = "EntCourseMapper.queryHonorinfo";			//荣誉信息详情
	
	/**
	 * 
	 * @descript 企业历程
	 * @author 龚志强
	 * @since 2016年9月14日下午2:46:09
	 * @param pd 参数集合
	 * @return 企业历程集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryEntCourse(PageData pd) throws Exception{
		List list =(List) dao.findForList(queryEntCourse, pd);
		return list;
	}

	/**
	 * 
	 * @descript 招聘详情
	 * @author 龚志强
	 * @since 2016年9月20日上午10:34:58
	 * @param pd 参数集合
	 * @return 招聘集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryJobInfo(PageData pd) throws Exception{
		List list =(List) dao.findForList(queryJobInfo, pd);
		return list;
	}

	/**
	 * 
	 * @descript 招标详情
	 * @author 龚志强
	 * @since 2016年9月20日上午10:35:14
	 * @param pd 参数集合
	 * @return 招标集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryBiddin(PageData pd) throws Exception{
		List list =(List) dao.findForList(queryBiddin, pd);
		return list;
	}

	/**
	 * 
	 * @descript 新闻详情
	 * @author 龚志强
	 * @since 2016年9月20日上午10:35:28
	 * @param pd 参数集合
	 * @return 新闻集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryEntnews(PageData pd) throws Exception{
		List list =(List) dao.findForList(queryEntnews, pd);
		return list;
	}

	/**
	 * 
	 * @descript 专利详情
	 * @author 龚志强
	 * @since 2016年9月20日上午10:36:16
	 * @param pd 参数集合
	 * @return 专利集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryPatent(PageData pd) throws Exception{
		List list =(List) dao.findForList(queryPatent, pd);
		return list;
	}

	/**
	 * 
	 * @descript 商标详情
	 * @author 龚志强
	 * @since 2016年9月20日上午10:36:28
	 * @param pd 参数集合
	 * @return 商标集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryBrand(PageData pd) throws Exception{
		List list =(List) dao.findForList(queryBrand, pd);
		return list;
	}

	/**
	 * 
	 * @descript 企业变更详情
	 * @author 龚志强
	 * @since 2016年9月20日上午10:36:42
	 * @param pd 参数集合
	 * @return 企业变更集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryEntRecoder(PageData pd) throws Exception{
		List list =(List) dao.findForList(queryEntRecoder, pd);
		return list;
	}

	/**
	 * 
	 * @descript 个体变更详情
	 * @author 龚志强
	 * @since 2016年9月20日上午10:36:58
	 * @param pd 参数集合
	 * @return 个体变更集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List querySingleRecoder(PageData pd) throws Exception{
		List list =(List) dao.findForList(querySingleRecoder, pd);
		return list;
	}

	/**
	 * 
	 * @descript 著作权详情
	 * @author 龚志强
	 * @since 2016年9月20日上午10:37:18
	 * @param pd 参数集合
	 * @return 著作权集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryWorkcopyRight(PageData pd) throws Exception{
		List list =(List) dao.findForList(queryWorkcopyRight, pd);
		return list;
	}
	
	/**
	 * 
	 * @descript 软件著作权详情
	 * @author 龚志强
	 * @since 2016年9月20日上午10:37:32
	 * @param pd 参数集合
	 * @return 软件著作权集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryCopyright(PageData pd) throws Exception{
		List list =(List) dao.findForList(queryCopyright, pd);
		return list;
	}

	/**
	 * 
	 * @descript 荣誉信息详情
	 * @author 龚志强
	 * @since 2016年9月20日上午10:37:45
	 * @param pd 参数集合
	 * @return 荣誉信息集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryHonorinfo(PageData pd) throws Exception{
		List list =(List) dao.findForList(queryHonorinfo, pd);
		return list;
	}
}
