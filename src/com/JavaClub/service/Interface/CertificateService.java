package com.JavaClub.service.Interface;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;


/**
 * 
 * @descript (查看许可信息)
 * @author 李海涛
 * @createTime 2016年9月19日下午1:38:49
 * @version 1.0
 */
@Service("certificateService")
public class CertificateService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 
	 * @descript 获取行政审批  包括行政许可和其他部门行政许可
	 * @author 李海涛
	 * @since 2016年9月12日下午12:15:36
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List  queryCertificateInfo(PageData pd) throws Exception{
		List list =(List) dao.findForList("CertificateMapper.queryCertificateInfo", pd);
		return list;
	}
}
