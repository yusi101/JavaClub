package com.JavaClub.service.credit;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (用一句话描述改方法的作用)
 * @author 魏旋
 * @createTime 2017年2月15日上午10:36:05
 * @version 1.0
 */
@Service("registrationService")
public class RegistrationService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 
	 * @descript (查询南昌市的区县)
	 * @author 魏旋
	 * @since 2017年2月15日上午10:38:02
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryCounty(PageData pd)throws Exception{

		return (List<Map<String, Object>>) dao.findForList("RegistrationMapper.queryCounty", pd);
	}
	
	/**
	 * 查询南昌市开业的公司个数
	 * @descript (用一句话描述改方法的作用)
	 * @author 魏旋
	 * @since 2017年2月15日上午10:39:05
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryRegstate(PageData pd)throws Exception{

		return (List<Map<String, Object>>) dao.findForList("RegistrationMapper.queryRegstate", pd);
	}

}
