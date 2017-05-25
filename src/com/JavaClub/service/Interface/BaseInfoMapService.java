package com.JavaClub.service.Interface;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;


/**
 * @Service("baseInfoMapService")
 * @descript 地图
 * @author 李坡
 * @createTime 2016年9月22日上午10:31:35
 * @version 1.0
 */
@Service("BaseInfoMapService")
public class BaseInfoMapService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 
	 * @descript 查询地图坐标
	 * @author 李坡
	 * @since 2016年9月22日上午10:31:24
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> queryBaseInfoList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("BaseInfoMapMapper.queryBaseInfoList", pd);
	}
	/**
	 * 
	 * @descript 根据pripid查询企业详情
	 * @author 李坡
	 * @since 2016年9月22日上午10:31:50
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findBaseInfoById(PageData pd)throws Exception{
		return (PageData) dao.findForObject("BaseInfoMapMapper.findBaseInfoById", pd);
	}
	
	/**
	 * 
	 * @descript (查询省市县)
	 * @author 李文海
	 * @since 2016年10月20日上午9:24:35
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> queryCityList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("BaseInfoMapMapper.queryCityList", pd);
	}
	
	/**
	 * 
	 * @descript (统计某个区域有多少牌照未领取)
	 * @author 李文海
	 * @since 2016年10月25日上午10:24:13
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int queryCount(PageData pd)throws Exception{
		return (int) dao.findForObject("BaseInfoMapMapper.queryCount", pd);
	}
	
}
