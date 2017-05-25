package com.JavaClub.service.credit;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (管理员审核/回复)
 * @author 余思
 * @createTime 2016年11月29日下午2:06:06
 * @version 1.0
 */

@Service("respondedService")
public class RespondedService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	final String insertRespondedInfo = "RespondedMapper.insertRespondedInfo";											//插入管理员审核/回复
	
	final String queryRespondedInfoByRelationId = "RespondedMapper.queryRespondedInfoByRelationId";						//根据关联ID查询管理员回复
	
	final String deleteRespondedInfoByRelationId = "RespondedMapper.deleteRespondedInfoByRelationId";					//根据关联ID删除管理员回复
	
	
	
	/**
	 * 
	 * @descript (插入管理员审核/回复)
	 * @author 李海涛
	 * @since 2016年12月16日下午5:05:21
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int insertRespondedInfo(PageData pd) throws Exception{
		return (int) dao.save(insertRespondedInfo, pd);
	}
	
	/**
	 * 
	 * @descript (根据关联ID查询管理员回复)
	 * @author 李海涛
	 * @since 2016年12月16日下午5:05:50
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryRespondedInfoByRelationId(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList(queryRespondedInfoByRelationId,pd);
	}
	
	/**
	 * 
	 * @descript (根据关联ID删除管理员回复)
	 * @author 李海涛
	 * @since 2016年12月16日下午5:06:26
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int deleteRespondedInfoByRelationId(PageData pd) throws Exception {
		return (int) dao.delete(deleteRespondedInfoByRelationId, pd);
	}
	
}
