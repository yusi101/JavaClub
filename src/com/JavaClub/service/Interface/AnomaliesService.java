package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;


/**
 * 
 * @descript (经营异常信息)
 * @author 李海涛
 * @createTime 2016年9月19日下午4:05:43
 * @version 1.0
 */
@Service("anomaliesService")
public class AnomaliesService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
     * 
     * @descript 得到经营异常信息分页
     * @author 李海涛
     * @since 2016年9月12日上午11:14:38
     * @param pd
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryAnomaliesInfo(Page page) throws Exception {
        return  (List<Map<String, Object>>) dao.findForList("AnomaliesMapper.queryAnomaliesInfolistPage",page);
    }
    
    /**
     * 
     * @descript 得到经营异常信息不分页
     * @author 李海涛
     * @since 2016年9月12日上午11:14:38
     * @param pd
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryAnomaliesInfo(PageData pd) throws Exception {
        return  (List<Map<String, Object>>) dao.findForList("AnomaliesMapper.queryAnomaliesInfo",pd);
    }
    
    
}