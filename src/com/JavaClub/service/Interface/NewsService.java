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
 * @descript (新闻和资讯的接口)
 * @author 李海涛
 * @createTime 2016年9月23日上午9:23:21
 * @version 1.0
 */
@Service("newsService")
public class NewsService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**
	 * 
	 * @descript (得到最新资讯  分页)
	 * @author 李海涛
	 * @since 2016年9月23日上午9:24:15
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryNewInformation(Page page) throws Exception {
		List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("NewsMapper.queryNewInformationlistPage",page);
		return list;
	}
	

	/**
	 * 
	 * @descript (得到最新资讯 不分页)
	 * @author 李海涛
	 * @since 2016年9月23日上午9:24:05
	 * @param pd
	 * @return
	 * @throws Exception
	 */
    public List<Map<String, Object>> queryNewInformation(PageData pd) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("NewsMapper.queryNewInformation",pd);
        return list;
    }
	

    /**
     * 
     * @descript (得到企业新闻  分页)
     * @author 李海涛
     * @since 2016年9月23日上午9:25:12
     * @param page
     * @return
     * @throws Exception
     */
	public List<Map<String, Object>> queryEntNewsInfo(Page page) throws Exception {
		List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("NewsMapper.queryEntNewsInfolistPage",page);
		return list;
	}
	
	
	/**
     * 
     * @descript (得到企业新闻 不分页)
     * @author 李海涛
     * @since 2016年9月23日上午9:24:05
     * @param pd
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryEntNewsInfo(PageData pd) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("NewsMapper.queryEntNewsInfo",pd);
        return list;
    }
}