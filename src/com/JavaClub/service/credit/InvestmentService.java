package com.JavaClub.service.credit;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * @descript (用一句话描述改方法的作用)
 * @author 李坡
 * @createTime 2016年9月28日上午9:00:59
 * @version 1.0
 */
@Service("InvestmentService")
public class InvestmentService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	final String queryPerhapsRelation = "InvestmentMapper.queryPerhapsRelation";	//查询对外投资
	
	/**
	 *
	 * @descript 查询对外投资
	 * @author 李坡
	 * @since 2016年9月28日上午9:21:45
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	public List<Map<String, Object>> queryPerhapsRelation(PageData pd) throws Exception {
		
		return (List) dao.findForList(queryPerhapsRelation, pd);
	}
}