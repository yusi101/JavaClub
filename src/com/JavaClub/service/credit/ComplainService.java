package com.JavaClub.service.credit;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.Const;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript 企业投诉
 * @author 龚志强
 * @createTime 2016年9月26日上午9:22:28
 * @version 1.0
 */
@Service("complainService")
public class ComplainService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//查询全部的投诉审核
	String queryComplainApplylistPage = "ComplainMapper.queryComplainApplylistPage";
	//查询某个投诉审核详情
	String queryComplainById = "ComplainMapper.queryComplainById";
	//查询某个类型的附件材料
	String queryAttachmentById = "ClaimMapper.queryAttachmentById";
	//修改投诉审核信息
	String updateComplain = "ComplainMapper.updateComplain";
	//添加管理员审核信息
	String insertResponded = "ClaimMapper.insertResponded";
	
	/**
	 * 
	 * @descript 查询全部的投诉审核
	 * @author 龚志强
	 * @since 2016年9月26日上午9:30:43
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryComplainApplylistPage(Page page) throws Exception {
		List complaintList = (List) dao.findForList(queryComplainApplylistPage, page);
		
		return complaintList;
	}
	
	/**
	 * 
	 * @descript 查询某个投诉审核信息
	 * @author 龚志强
	 * @since 2016年9月26日上午10:49:58
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map queryComplainById(PageData pd) throws Exception {
		Map complaintMap = (Map) dao.findForObject(queryComplainById, pd);
		
		return complaintMap;
	}
	
	/**
	 * 
	 * @descript 查询某个类型的附件材料信息
	 * @author 龚志强
	 * @since 2016年9月26日上午11:54:16
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryAttachmentById(PageData pd) throws Exception {
		List attachmentList = (List) dao.findForList(queryAttachmentById, pd);
		
		return attachmentList;
	}
	
	
	/**
	 * 
	 * @descript 修改投诉审核信息
	 * @author 龚志强
	 * @since 2016年9月26日上午11:23:51
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String updateComplain(PageData pd) throws Exception {
		String flag = Const.FAIL;
		int num = 0;

		//修改投诉状态和投诉描述等信息
		num += (int) dao.update(updateComplain, pd);
		
		if(0 < num){
			//添加管理回复表
			num += (int) dao.save(insertResponded, pd);
			flag =  Const.SUCCESS;
		}
		
		return flag;
	}
}