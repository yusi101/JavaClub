package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;


@Service("otCaseInfoService")
public class OtCaseInfoService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
     * 
     * @descript (查询行政处罚信息 分页)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param page
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryOtCaseInfo(Page page) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("OtCaseInfoMapper.queryOtCaseInfolistPage",page);
        return list;
    }
    
    /**
     * 
     * @descript (查询行政处罚信息 不分页)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryOtCaseInfo(PageData pd) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("OtCaseInfoMapper.queryOtCaseInfo",pd);
        return list;
    }
}
