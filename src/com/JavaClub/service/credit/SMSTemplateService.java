package com.JavaClub.service.credit;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (短信模板)
 * @author 魏旋
 * @createTime 2017年2月7日上午10:47:34
 * @version 1.0
 */
@Service("SMSTemplateService")
public class SMSTemplateService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	final String INSERT_SMS_INFO = "SMSTemplateMapper.insertSMS";			//添加短信模板
	
	/**
	 * 
	 * @descript (添加短信模板)
	 * @author 魏旋
	 * @since 2017年2月7日上午10:48:26
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int insertSMSInfo(PageData pd) throws Exception{
		   return (int)dao.save(INSERT_SMS_INFO, pd);
	}
	
	/**
	 * 
	 * @descript (查询短信模板列表)
	 * @author 魏旋
	 * @since 2017年2月7日下午2:25:37
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> querySMSlist(PageData pd) throws Exception {
	      return  (List<Map<String, Object>>) dao.findForList("SMSTemplateMapper.querySMSlist", pd);
	}
	
	/**
	 * 
	 * @descript (删除短信模板)
	 * @author 魏旋
	 * @since 2017年2月7日下午2:55:21
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	 public int deleteSms(PageData pd) throws Exception {
		return (int)dao.save("SMSTemplateMapper.deleteSms", pd);
	}
	 
	 /**
	  * 
	  * @descript (修改短信模板)
	  * @author 魏旋
	  * @since 2017年2月7日下午2:57:12
	  * @param pd
	  * @return
	  * @throws Exception
	  */
	 public int updateSms(PageData pd) throws Exception{
		 return (int)dao.save("SMSTemplateMapper.updateSms", pd);
	}
	 
	 /**
	  * 
	  * @descript (重置激活状态)
	  * @author 魏旋
	  * @since 2017年2月7日下午3:58:33
	  * @param pd
	  * @return
	  * @throws Exception
	  */
	 public int updateActive(PageData pd) throws Exception{
		 return (int)dao.save("SMSTemplateMapper.updateActive", pd);
	}
}
