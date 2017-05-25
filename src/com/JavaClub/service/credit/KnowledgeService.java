package com.JavaClub.service.credit;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (知识库操作)
 * @author 魏旋
 * @createTime 2016年12月7日上午8:56:20
 * @version 1.0
 */
@Service("KnowledgeService")
public class KnowledgeService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	final String insertKnowInfo = "KnowledgeMapper.insertKnow";			//添加知识库信息
	final String updateKnowInfo = "KnowledgeMapper.updateKnow";			//修改知识库信息
	final String deleteKnowInfo = "KnowledgeMapper.deleteKnow";			//删除知识库信息
	
	
	/**
	 * 
	 * @descript (查询知识库所有信息)
	 * @author 魏旋
	 * @since 2016年12月7日上午9:23:01
	 * @param page
	 * @return
	 * @throws Exception
	 */
   @SuppressWarnings("unchecked")
   public List<Map<String, Object>> queryKnowInfo(Page page) throws Exception {
       return  (List<Map<String, Object>>) dao.findForList("KnowledgeMapper.queryKnowlistPage", page);
   }
   
   /**
	 * 
	 * @descript (查询知识信息)
	 * @author 魏旋
	 * @since 2016年12月7日上午9:23:01
	 * @param page
	 * @return
	 * @throws Exception
	 */
@SuppressWarnings("unchecked")
public List<Map<String, Object>> queryKnowDetailsInfo(PageData page) throws Exception {
      return  (List<Map<String, Object>>) dao.findForList("KnowledgeMapper.queryKnowPage", page);
  }
   
   /**
    * 
    * @descript (添加知识库信息)
    * @author 魏旋
    * @since 2016年12月7日上午10:29:59
    * @param pd
    * @return
    * @throws Exception
    */
   public int insertKnowInfo(PageData pd) throws Exception{
	   return (int)dao.save(insertKnowInfo, pd);
	}
   
   /**
    * 
    * @descript (修改知识库信息)
    * @author 魏旋
    * @since 2016年12月7日上午10:29:59
    * @param pd
    * @return
    * @throws Exception
    */
   public int updateKnowInfo(PageData pd) throws Exception{
	   return (int)dao.save(updateKnowInfo, pd);
	}
   
   /**
    * 
    * @descript (删除知识)
    * @author 魏旋
    * @since 2016年12月7日下午3:46:00
    * @param pd
    * @return
    * @throws Exception
    */
   public int deleteKnow(PageData pd) throws Exception {
		return (int)dao.save(deleteKnowInfo, pd);
	}
   
   /**
    * 
    * @descript (判断)
    * @author 魏旋
    * @since 2017年2月13日上午9:27:34
    * @param pd
    * @return
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryKnow(PageData pd) throws Exception{
		return (List<Map<String, Object>>) dao.findForList("KnowledgeMapper.queryKnow", pd);
	}
	
}
