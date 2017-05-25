

package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript  查询行业跟企业信息相关联总数)
 * @author 余思
 * @createTime 2017年2月14日上午9:39:54
 * @version 1.0
 */
@Service("enterinfoService")
public class EnterinfoService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 
	 * @descript (查询行业跟企业信息相关联总数 )
	 * @author 余思
	 * @since 2017年2月14日上午9:38:52
	 * @param pd
	 * @return 报表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryEnterInfo(PageData pd) throws Exception {
		return (List<Map<String, Object>>)  dao.findForList("enterinfoMapper.queryEnterinfo",pd);
	}
	
}
