package com.JavaClub.service.credit;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (关键词管理)
 * @author 余思
 * @createTime 2016年12月7日上午10:10:27
 * @version 1.0
 */
@Service("emailsModsService")
public class EmailsModsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	final String insertEmailsMods = "EmailsModsMapper.insertEmailsMods";						
	final String insertEmailsModsAttr = "EmailsModsMapper.insertEmailsModsAttr";						
	final String queryEmailsMods = "EmailsModsMapper.queryEmailsMods";						
	final String delemailsMods = "EmailsModsMapper.delemailsMods";		
	final String delemailsModsAttr = "EmailsModsMapper.delemailsModsAttr";	
	final String updateemailsModsById = "EmailsModsMapper.updateemailsModsById";				
	final String updateemailsModsAttrById = "EmailsModsMapper.updateemailsModsAttrById";	
	final String queryEmailsModsID = "EmailsModsMapper.queryEmailsModsID";	
	final String queryEmailsModsIDflag = "EmailsModsMapper.queryEmailsModsIDflag";		
	final String queryEmailsModsByIdAttrInfo = "EmailsModsMapper.queryEmailsModsByIdAttrInfo";
	final String updateemailsModsstatus = "EmailsModsMapper.updateemailsModsstatus";
	final String delemailsModsIDAttr = "EmailsModsMapper.delemailsModsIDAttr";
	
	/**
	 * 
	 * @descript (新增邮件模板)
	 * @author 余思
	 * @since 2017年2月7日下午1:48:10
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int insertEmailsMods(PageData pd) throws Exception {
		return (int) dao.save(insertEmailsMods, pd);
	}
	/**
	 * 
	 * @descript (新增邮件模板附件)
	 * @author 余思
	 * @since 2017年2月7日下午3:19:19
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int insertEmailsModsAttr(PageData pd) throws Exception {
		return (int) dao.save(insertEmailsModsAttr, pd);
	}
/*	*//**
	 * 
	 * @descript (查询全部邮件模板)
	 * @author 余思
	 * @since 2017年2月7日下午2:09:07
	 * @param page
	 * @return
	 * @throws Exception
	 *//*
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryKeywordlistPage(Page page) throws Exception {
		return (List<Map<String, Object>>) dao.findForList(queryKeywordlistPage, page);
	}*/
	/**
	 * 
	 * @descript (查询单个邮件模板)
	 * @author 余思
	 * @since 2016年12月7日上午11:25:39
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryEmailsModsByIdInfo (PageData pd) throws Exception{
		return  (List<Map<String,Object>>)dao.findForListMap(queryEmailsModsID, pd);
	}
	/**
	 * 
	 * @descript (查询单个邮件模板ID的有所附件)
	 * @author 余思
	 * @since 2016年12月7日上午11:25:39
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryEmailsModsByIdAttrInfo (PageData pd) throws Exception{
		return  (List<Map<String,Object>>)dao.findForListMap(queryEmailsModsByIdAttrInfo, pd);
	}
	/**
	 * 
	 * @descript (判断存在)
	 * @author 余思
	 * @since 2017年2月8日上午9:57:30
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryEmailsModsIDflag (PageData pd) throws Exception{
		return  (List<Map<String,Object>>)dao.findForListMap(queryEmailsModsIDflag, pd);
	}
	/**
	 * 
	 * @descript 查询全部邮件模板  不分页)
	 * @author 余思
	 * @since 2017年2月7日下午5:08:00
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryEmailsMods(PageData pd) throws Exception {
		return (List<Map<String, Object>>) dao.findForList(queryEmailsMods, pd);
	}
	
	/**
	 * 
	 * @descript (删除单条信息)
	 * @author 余思
	 * @since 2017年2月7日下午5:07:54
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int delemailsMods(PageData pd) throws Exception {
		return (int) dao.delete(delemailsMods, pd);
	}
	/**
	 * 
	 * @descript (删除单条信息附件)
	 * @author 余思
	 * @since 2017年2月7日下午5:07:48
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int delemailsModsAttr(PageData pd) throws Exception {
		return (int) dao.delete(delemailsModsAttr, pd);
	}
		
	/**
	 * 
	 * @descript (修改邮件模板)
	 * @author 余思
	 * @since 2016年12月7日上午11:30:58
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int updateemailsModsById(PageData pd) throws Exception {
		return (int) dao.update(updateemailsModsById, pd);
	}
	/**
	 * 
	 * @descript (修改邮件模板附件)
	 * @author 余思
	 * @since 2016年12月7日上午11:30:58
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int updateemailsModsAttrById(PageData pd) throws Exception {
		return (int) dao.update(updateemailsModsAttrById, pd);
	}
	/**
	 * 
	 * @descript (强制更改状态为未激活)
	 * @author 余思
	 * @since 2016年12月7日上午11:30:58
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int updateemailsModsstatus(PageData pd) throws Exception {
		return (int) dao.update(updateemailsModsstatus, pd);
	}
	/**
	 * 
	 * @descript (清除附件)
	 * @author 余思
	 * @since 2017年2月7日下午5:07:48
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int delemailsModsIDAttr(PageData pd) throws Exception {
		return (int) dao.delete(delemailsModsIDAttr, pd);
	}
}