package com.JavaClub.service.credit;

import java.util.List;
import java.util.Map;

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
@Service("businessMapService")
public class BusinessMapService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	private static final String QUERYBASEINFOLIST="BusinessMapMapper.queryBaseInfoList";
	private static final String QUERYBASEINFOLISTPD="BusinessMapMapper.queryBaseInfoListpd";
	
	private static final String FINDBASEINFOBYID="BusinessMapMapper.findBaseInfoById";
	private static final String FINDBASEXYINFOBYID="BusinessMapMapper.findBasexyInfoById";
	private static final String QUERYCITYLIST="BusinessMapMapper.queryCityList";
	
	private static final String QUERYADDRESSCODE="BusinessMapMapper.queryAddressCode";
	/**
	 * 
	 * @descript 查询地图坐标
	 * @author 余思
	 * @since 2017年5月19日下午2:58:45
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryBaseInfoList(PageData pd)throws Exception{
		return (List<Map<String, Object>>) dao.findForList(QUERYBASEINFOLIST, pd);
	}
	/**
	 * 
	 * @descript 查询地图坐标
	 * @author 余思
	 * @since 2017年5月19日下午2:58:45
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> queryBaseInfoListpd(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList(QUERYBASEINFOLISTPD, pd);
	}
	/**
	 * 
	 * @descript 根据pripid查询企业详情
	 * @author 余思
	 * @since 2017年5月19日下午2:58:51
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findBaseInfoById(PageData pd)throws Exception{
		return (PageData) dao.findForObject(FINDBASEINFOBYID, pd);
	}
	/**
	 * 
	 * @descript 根据x y查询企业详情
	 * @author 余思
	 * @since 2017年5月19日下午2:58:51
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findBasexyInfoById(PageData pd)throws Exception{
		return (PageData) dao.findForObject(FINDBASEXYINFOBYID, pd);
	}
	/**
	 * 
	 * @descript (查询省市县)
	 * @author 余思
	 * @since 2017年5月19日下午2:58:57
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> queryCityList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList(QUERYCITYLIST, pd);
	}
	
	/**
	 * 
	 * @descript (查詢地區編碼
	 * @author 余思
	 * @since 2017年5月19日下午2:58:57
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryaddresscode(PageData pd)throws Exception{
		return (List<Map<String, Object>>) dao.findForList(QUERYADDRESSCODE, pd);
	}
}
