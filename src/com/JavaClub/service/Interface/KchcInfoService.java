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
 * @descript (获取重用守信合同信息)
 * @author 李海涛
 * @createTime 2016年9月22日下午7:48:34
 * @version 1.0
 */

@Service("kchcInfoService")
public class KchcInfoService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**
     * 
     * @descript (获取重用守信合同信息 分页)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param page
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryKchcInfo(Page page) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("KchcInfoMapper.queryKchcInfolistPage",page);
        return list;
    }
    
    /**
     * 
     * @descript (获取重用守信合同信息 不分页)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryKchcInfo(PageData pd) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("KchcInfoMapper.queryKchcInfo",pd);
        return list;
    }
}	
