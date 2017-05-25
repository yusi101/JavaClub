package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

@Service("copyrightService")
public class CopyrightService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;


	/**
	 * 
	 * @descript (得到获取软件著作权信息，不分页)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:48:26
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryCopyrightInfo(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("CopyrightMapper.queryCopyrightInfo",pd);
	}

	/**
	 * 
	 * @descript (得到获取软件著作权信息，分页)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:48:39
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryCopyrightInfo(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("CopyrightMapper.queryCopyrightInfolistPage",page);
	}

	/**
	 * 
	 * @descript (得到获取著作权信息，不分页)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:48:51
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryWorkCopyrightInfo(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("CopyrightMapper.queryWorkCopyrightInfo",pd);
	}

	/**
	 * 
	 * @descript (得到获取著作权信息，分页)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:49:08
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryWorkCopyrightInfo(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("CopyrightMapper.queryWorkCopyrightInfolistPage",page);
	}

	/**
	 * 
	 * @descript 查询著作权类型
	 * @author 吕永青
	 * @since 2016年10月27日上午8:57:27
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryWorkCopyrightClass(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("CopyrightMapper.queryWorkCopyrightClasslistPage",page);
	}


}
