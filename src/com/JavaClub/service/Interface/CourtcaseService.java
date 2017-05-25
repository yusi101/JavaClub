package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

@Service("courtcaseService")
public class CourtcaseService {
    
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/**
     * 
     * @descript 得到失信被执行人分页
     * @author 李坡
     * @since 2016年9月12日上午11:14:38
     * @param pd
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryCourtcaseInfo(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("CourtcaseMapper.queryCourtcaseInfolistPage",page);
	}
	
	/**
     * 
     * @descript 得到失信被执行人不分页
     * @author 李坡
     * @since 2016年9月12日上午11:14:38
     * @param pd
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryCourtcaseInfo(PageData pd) throws Exception {
        return  (List<Map<String, Object>>) dao.findForList("CourtcaseMapper.queryCourtcaseInfo",pd);
    }
	
	/**
	 * 
	 * @descript (得到司法信息)
	 * @author 李海涛
	 * @since 2016年9月19日下午1:55:13
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
    public List queryJusticeInfo(PageData pd) throws Exception {
        List list =(List) dao.findForList("CourtcaseMapper.queryJusticeInfo",pd);
        return list;
    }
	
	/**
	 * 
	 * @descript (用一句话描述改方法的作用)
	 * @author 李海涛
	 * @since 2016年9月22日下午8:59:16
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
    public List queryJusticeCount(PageData pd) throws Exception {
        List list =(List) dao.findForList("CourtcaseMapper.queryJusticeCount",pd);
        return list;
    }
}