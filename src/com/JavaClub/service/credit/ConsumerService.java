package com.JavaClub.service.credit;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript 消费者投诉举报统计
 * @author 余思
 * @createTime 2017年3月16日下午1:47:12
 * @version 1.0
 */
@Service("consumerService")
public class ConsumerService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	final String queryConsumerinfo = "ConsumerMapper.queryConsumerinfo";				


	/**
	 * 
	 * @descript 消费者投诉举报统计
	 * @author 余思
	 * @since 2017年3月16日下午1:47:33
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryConsumerinfo(PageData pd) throws Exception {
		return (List<Map<String, Object>>)  dao.findForList(queryConsumerinfo,pd);
	}
}
