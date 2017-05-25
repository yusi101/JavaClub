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
 * @descript (广告资质信息)
 * @author 李海涛
 * @createTime 2016年9月22日下午6:35:46
 * @version 1.0
 */
@Service("advertisingService")
public class AdvertisingService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**
	 * 
	 * @descript (查询广告资质 分页)
	 * @author 李海涛
	 * @since 2016年9月22日下午6:34:41
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryAdvertisingInfo(Page page) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("AdvertisingMapper.queryAdvertisingInfolistPage",page);
        return list;
    }
	
	/**
     * 
     * @descript (查询广告资质 不分页)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return
     * @throws Exception
     */
	public List<Map<String, Object>> queryAdvertisingInfo(PageData pd) throws Exception {
		List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("AdvertisingMapper.queryAdvertisingInfo",pd);
		return list;
	}
}