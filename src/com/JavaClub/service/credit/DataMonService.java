package com.JavaClub.service.credit;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (评论业务层)
 * @author 汤彬
 * @createTime 2016年9月22日下午4:10:39
 * @version 1.0
 */
@Service("dataMonService")
public class DataMonService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	static final String QUERYDATAMONQY = "DataMonMapper.queryDataMonqy"; //企业
	static final String QUERYDATAMONGT = "DataMonMapper.queryDataMongt";//个体
	static final String QUERYDATAMONNEW = "DataMonMapper.queryDataMonnew";//新表
	static final String QUERYENTSEVENDAYSDATA = "DataMonMapper.queryEntSevenDaysData"; //查询 企业 表7天数据更新情况
	static final String QUERYGTSEVENDAYSDATA = "DataMonMapper.queryGtSevenDaysData";//查询 个体 表7天数据更新情况
	static final String QUERYNEWSEVENDAYSDATA = "DataMonMapper.queryNewSevenDaysData";//查询 新企业 表7天数据更新情况
	/**
	 * 
	 * @descript 查询个体表数据更新情况
	 * @author 余思
	 * @since 2017年4月25日下午1:57:23
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryDataMonqy(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForObject(QUERYDATAMONQY,pd);
	}

	/**
	 * 报表
	 * @descript 查询 企业 表7天数据更新情况
	 * @author 余思
	 * @since 2017年5月15日下午5:22:35
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String queryEntSevenDaysData(PageData pd) throws Exception {
		List<Map<String, Object>> list=(List<Map<String, Object>>) dao.findForList(QUERYGTSEVENDAYSDATA,pd);
		String[] data=new String[list.size()];
		String[] nums=new String[list.size()];
		if(!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				data[i]="'"+list.get(i).get("days").toString()+"'";
				nums[i]=list.get(i).get("num").toString();
			}
		}
		return  Arrays.toString(data)+"@"+Arrays.toString(nums);
	}

	/**
	 * 
	 * @descript 查询 个体 表数据更新情况
	 * @author 余思
	 * @since 2017年4月25日下午1:57:23
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryDataMongt(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList(QUERYDATAMONGT,pd);
	}

	/**
	 * 报表
	 * @descript 查询 个体 表7天数据更新情况
	 * @author 余思
	 * @since 2017年5月15日下午5:22:35
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String queryGtSevenDaysData(PageData pd) throws Exception {
		List<Map<String, Object>> list=(List<Map<String, Object>>) dao.findForList(QUERYGTSEVENDAYSDATA,pd);
		String[] data=new String[list.size()];
		String[] nums=new String[list.size()];
		if(!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				data[i]="'"+list.get(i).get("days").toString()+"'";
				nums[i]=list.get(i).get("num").toString();
			}
		}
		return  Arrays.toString(data)+"@"+Arrays.toString(nums);
	}

	/**
	 * 
	 * @descript 查询 新企业 表数据更新情况
	 * @author 余思
	 * @since 2017年4月25日下午1:57:23
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryDataMonnew(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList(QUERYDATAMONNEW,pd);
	}

	/**
	 * 报表
	 * @descript 查询 新企业 表7天数据更新情况
	 * @author 余思
	 * @since 2017年5月15日下午5:22:35
	 * @param pd
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public String queryNewSevenDaysData(PageData pd) throws Exception {
		List<Map<String, Object>> list=(List<Map<String, Object>>) dao.findForList(QUERYNEWSEVENDAYSDATA,pd);
		String[] data=new String[list.size()];
		String[] nums=new String[list.size()];
		if(!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				data[i]="'"+list.get(i).get("days").toString()+"'";
				nums[i]=list.get(i).get("num").toString();
			}
		}
		return  Arrays.toString(data)+"@"+Arrays.toString(nums);
	}

}