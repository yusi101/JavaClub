package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

@Service("honorService")
public class HonorService {
    
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
     * 
     * @descript 得到荣誉信息，分页
     * @author 李海涛
     * @since 2016年9月9日上午9:04:38
     * @param pd
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getHonorinfo(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("HonorMapper.getHonorinfolistPage",page);
	}
	
	/**
	 * 
	 * @descript 得到荣誉信息，不分页
	 * @author 李海涛
	 * @since 2016年9月9日上午9:04:38
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> getHonorinfo(PageData pd) throws Exception {
        return  (List<Map<String, Object>>) dao.findForList("HonorMapper.getHonorinfo",pd);
    }
	
	
	
	
	
}
