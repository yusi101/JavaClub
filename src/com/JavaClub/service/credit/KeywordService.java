package com.JavaClub.service.credit;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (关键词管理)
 * @author 余思
 * @createTime 2016年12月7日上午10:10:27
 * @version 1.0
 */
@Service("keywordService")
public class KeywordService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	final String insertKeyword = "KeywordMapper.insertKeyword";						//新增关键词
	final String queryKeywordlistPage = "KeywordMapper.queryKeywordlistPage";		//查询全部关键词
	final String queryKeyword = "KeywordMapper.queryKeyword";						//查询全部关键词  不分页
	final String deletekeyword = "KeywordMapper.deletekeyword";						//删除关键词
	final String querykeywordByIdInfo = "KeywordMapper.querykeywordByIdInfo";		//查询单个关键词
	final String updatekeywordById = "KeywordMapper.updatekeywordById";				// 修改关键词 

	/**
	 * 
	 * @descript (查询全部关键词)
	 * @author 余思
	 * @since 2016年12月7日上午10:10:43
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Cacheable(value = "SensitiveWordsCache",key="#page.pd")  
	public List<Map<String, Object>> queryKeywordlistPage(Page page) throws Exception {
		return (List<Map<String, Object>>) dao.findForList(queryKeywordlistPage, page);
	}
	
	/**
	 * 
	 * @descript (查询全部关键词  不分页)
	 * @author 李海涛
	 * @since 2016年12月7日上午10:10:43
	 * @param PageData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Cacheable(value = "SensitiveWordsCache",key="#pd")  
	public List<Map<String, Object>> queryKeyword(PageData pd) throws Exception {
		return (List<Map<String, Object>>) dao.findForList(queryKeyword, pd);
	}
	
	/**
	 * 
	 * @descript (新增关键词)
	 * @author 余思
	 * @since 2016年12月7日上午10:10:43
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@CacheEvict(value="SensitiveWordsCache",key="#pd",allEntries=true)  
	public int insertKeywords(PageData pd) throws Exception {
		return (int) dao.save(insertKeyword, pd);
	}
	/**
	 * 
	 * @descript (删除单条信息)
	 * @author 余思
	 * @since 2016年12月7日上午11:15:25
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@CacheEvict(value="SensitiveWordsCache",key="#pd",allEntries=true)  
	public int deletekeyword(PageData pd) throws Exception {
		return (int) dao.delete(deletekeyword, pd);
	}
	
	/**
	 * 
	 * @descript (查询单个关键词信息)
	 * @author 余思
	 * @since 2016年12月7日上午11:25:39
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> querykeywordByIdInfo (PageData pd) throws Exception{
		return  (List<Map<String,Object>>)dao.findForListMap(querykeywordByIdInfo, pd);
	}
	/**
	 * 
	 * @descript (修改关键词)
	 * @author 余思
	 * @since 2016年12月7日上午11:30:58
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@CacheEvict(value="SensitiveWordsCache",key="#pd",allEntries=true)  
	public int updatekeywordById(PageData pd) throws Exception {
		return (int) dao.update(updatekeywordById, pd);
	}
	
	/**
	 * 判断重复
	 * @descript (用一句话描述改方法的作用)
	 * @author 魏旋
	 * @since 2017年3月3日下午4:56:04
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryName(PageData pd) throws Exception{
		return (List<Map<String, Object>>) dao.findForList("KeywordMapper.queryName", pd);
	}
	
}