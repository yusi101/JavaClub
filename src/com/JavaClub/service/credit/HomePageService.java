package com.JavaClub.service.credit;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * @descript (用一句话描述改方法的作用)
 * @author 李坡
 * @createTime 2016年9月28日上午9:00:59
 * @version 1.0
 */
@Service("homePageService")
public class HomePageService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	final String queryIndustryphyCount = "HomePageMapper.queryIndustryphyCount";   //查询各行业的牌照数量
	final String queryQrcodeVariousStatusCount = "HomePageMapper.queryQrcodeVariousStatusCount";   //查询牌照各类型和状态下的数量
	
	/**
	 *
	 * @descript 查询在线申请牌照
	 * @author 李坡
	 * @since 2016年9月28日上午9:21:45
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
    public List queryIndustryphyCount(PageData pd) throws Exception {
		return (List) dao.findForList("HomePageMapper.queryIndustryphyCount", pd);
	}
	
	/**
	 * 
	 * @descript (查询牌照各类型和状态下的数量)
	 * @author 李海涛
	 * @since 2016年10月19日下午4:47:22
	 * @param pd
	 * @return
	 * @throws Exception
	 */
    @SuppressWarnings("rawtypes")
    public List queryQrcodeVariousStatusCount(PageData pd) throws Exception {
        List qrCodeList = (List) dao.findForList(queryQrcodeVariousStatusCount, pd);
        return qrCodeList;
    }
}