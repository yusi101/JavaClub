package com.JavaClub.service.Interface;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * 系统业务层
 * @author gongzhiqiang
 *
 */
@Service("sysService")
public class SysService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 获取h5配置信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List querySysMobileTitle(PageData pd) throws Exception {
		List syslist = (List) dao.findForList("ConfigMapper.querySysMobileTitle", pd);
		return syslist;
	}
	
}