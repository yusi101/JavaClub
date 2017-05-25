package com.JavaClub.service.credit;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

@Service("IndustryType2Service")
public class IndustryType2Service {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 
	 * @descript 查询区县的行业分布情况
	 * @author 龚志强
	 * @since 2016年11月23日下午5:35:23
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryTypeEnt(PageData pd)throws Exception{

		return (List<Map<String, Object>>) dao.findForList("IndustryType2Mapper.queryTypeEnt", pd);
	}
	
	/**
	 * 查询南昌市的区县
	 * @descript (用一句话描述改方法的作用)
	 * @author 龚志强
	 * @since 2016年11月25日下午4:57:37
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryCounty(PageData pd)throws Exception{

		return (List<Map<String, Object>>) dao.findForList("IndustryType2Mapper.queryCounty", pd);
	}
	/**
	 * 
	 * @descript (查询人员)
	 * @author 魏旋
	 * @since 2017年1月9日上午8:56:18
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryEmpnum(PageData pd)throws Exception{

		return (List<Map<String, Object>>) dao.findForList("IndustryType2Mapper.queryEmpnum", pd);
	}
	
	/**
	 * 查询所有行业
	 * @descript (用一句话描述改方法的作用)
	 * @author 龚志强
	 * @since 2016年11月25日下午4:57:53
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryIndustry(PageData pd)throws Exception{

		return (List<Map<String, Object>>) dao.findForList("IndustryType2Mapper.queryIndustry", pd);
	}
	
	/**
	 * 查询外商来源
	 * @descript (用一句话描述改方法的作用)
	 * @author 龚志强
	 * @since 2016年12月19日上午10:57:29
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryForeignInvestment(PageData pd)throws Exception{

		return (List<Map<String, Object>>) dao.findForList("IndustryTypeMapper.queryForeignInvestment", pd);
	}
	

}
