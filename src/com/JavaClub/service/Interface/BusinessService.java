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
 * @descript (自主业务接口服务)
 * @author 李海涛
 * @createTime 2016年9月22日下午6:35:46
 * @version 1.0
 */
@Service("businessService")
public class BusinessService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**
     * 
     * @descript (获取我的历史查询列表  分页)
     * @author 李海涛
     * @since 2016年9月28日下午4:33:36
     * @param page
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>>queryHistoryInfo(Page page) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForList("BusinessMapper.queryHistoryInfolistPage", page);
        return list;
    }
   
    /**
     * 
     * @descript (获取我的历史查询列表  不分页)
     * @author 李海涛
     * @since 2016年9月28日下午4:33:36
     * @param page
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>>queryHistoryInfo(PageData pd) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForList("BusinessMapper.queryHistoryInfo", pd);
        return list;
    }
    
    /**
     * 得到搜索历史
     * 李海涛
     * @param page
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> querySearchHistoryInfo(Page page) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForList("BusinessMapper.querySearchHistoryInfolistPage", page);
        return list;
    }
    
    /**
     * 得到搜索历史
     * 李海涛
     * @param page
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> querySearchHistoryInfo(PageData pd) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForList("BusinessMapper.querySearchHistoryInfo", pd);
        return list;
    }
    
    /**
     * 点击查询热点分析详情
     * @author 胡华锋
     * @param page
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> queryHotspotAnalysislistPage(Page page) throws Exception {
        List<Map<String,Object>> HotList = (List<Map<String,Object>>) dao.findForList("BusinessMapper.queryHotspotAnalysislistPage",page);
        return HotList;
    }
    
    /**
     * 保存用户的反馈
     * 李海涛
     * @param page
     * @return
     * @throws Exception
     */
    public String saveOpinion(PageData pd) throws Exception {
        int result= (int) dao.save("BusinessMapper.saveOpinion",pd);
        if(result>0){
            return "success";
        }
        return "fail";
    }
}