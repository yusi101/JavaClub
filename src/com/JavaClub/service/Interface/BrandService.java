package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

@Service("brandService")
public class BrandService {
    // 旧的商标 查询
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
     * 
     * @descript 得到商标信息，分页
     * @author 李海涛
     * @since 2016年9月9日上午9:04:38
     * @param pd
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryBrandInfo(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("BrandMapper.queryBrandInfolistPage",page);
	}
	
	/**
	 * 
	 * @descript 得到商标信息，不分页
	 * @author 李海涛
	 * @since 2016年9月9日上午9:04:38
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryBrandInfo(PageData pd) throws Exception {
        return  (List<Map<String, Object>>) dao.findForList("BrandMapper.queryBrandInfo",pd);
    }
	
	/**
	 * 
	 * @descript 得到商标信息，不分页
	 * @author 李海涛
	 * @since 2016年9月9日上午9:04:38
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryBrandInfolimit() throws Exception {
        return  (List<Map<String, Object>>) dao.findForList("BrandMapper.queryBrandInfolimit",null);
    }
	
	
	/**
	 * 
	 * @descript 得到商标类别信息  
	 * @author 李海涛
	 * @since 2016年9月9日上午9:05:15
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryBrandclassInfo(PageData pd) throws Exception {
        List<Map<String, Object>> Brandclassinfo = (List<Map<String, Object>>) dao.findForList("BrandMapper.queryBrandclassInfo", pd);
        return Brandclassinfo;
    }
}
