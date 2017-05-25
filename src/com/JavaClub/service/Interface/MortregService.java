package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;


@Service("mortregService")
public class MortregService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 
	 * @descript (获得动产抵押信息  分页)
	 * @author 李海涛
	 * @since 2016年9月19日下午2:08:47
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryMortregInfo(Page page) throws Exception {
		List<Map<String,Object>> list = (List<Map<String, Object>>) dao.findForList("MortregMapper.queryMortregInfolistPage", page);
		return list;
	}
	
	/**
     * 
     * @descript (获得动产抵押信息  不分页)
     * @author 李海涛
     * @since 2016年9月19日下午2:08:47
     * @param page
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryMortregInfo(PageData pd) throws Exception {
		List<Map<String,Object>> list = (List<Map<String, Object>>) dao.findForList("MortregMapper.queryMortregInfo", pd);
		return list;
	}
	
	/**
     * 
     * @descript (获得不动产抵押信息  分页)
     * @author 李海涛
     * @since 2016年9月19日下午2:08:47
     * @param page
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryChattelPledgeInfo(Page page) throws Exception {
		return (List<Map<String, Object>>) dao.findForList("MortregMapper.queryChattelPledgeInfolistPage", page);		
	}
	
	/**
     * 
     * @descript (获得不动产抵押信息  不分页)
     * @author 李海涛
     * @since 2016年9月19日下午2:08:47
     * @param page
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryChattelPledgeInfo(PageData pd) throws Exception {
        return (List<Map<String, Object>>) dao.findForList("MortregMapper.queryChattelPledgeInfo", pd);      
    }
}