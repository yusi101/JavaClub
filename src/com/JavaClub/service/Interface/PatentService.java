package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

@Service("patentService")
public class PatentService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 
	 * @descript (得到专利信息，分页)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:47:52
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryPatentinfo(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("PatentMapper.queryPatentinfolistPage",page);
	}

	/**
	 * 
	 * @descript (得到专利信息，不分页)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:48:03
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryPatentinfo(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("PatentMapper.queryPatentinfo",pd);
	}


	/**
	 * 
	 * @descript 查询专利分类信息
	 * @author 吕永青
	 * @since 2016年10月26日下午3:28:56
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryPatentClass(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("PatentMapper.queryPatentClass",pd);
	}

	/**
	 * 
	 * @descript (得到专利信息，分页)
	 * @author 魏旋
	 * @since 2016年12月19日上午9:21:34
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryPatentinfo1(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("PatentMapper.queryPatentinfolistPage1",page);
	}

	/**
	 * 	
	 * @descript (专利信息详情)
	 * @author 魏旋
	 * @since 2016年12月19日上午10:03:18
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryPatentinfoDetail1(PageData page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("PatentMapper.queryPatentinfoDetail",page);
	}

	/**
	 * 	
	 * @descript (法律信息)
	 * @author 魏旋
	 * @since 2016年12月19日上午10:03:18
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryPatentinfoDetailLaw(PageData page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("PatentMapper.queryPatentinfoDetailLaw",page);
	}
}
