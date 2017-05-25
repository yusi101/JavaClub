
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
 * @descript 获取app上传数据
 * @author 李坡
 * @createTime 2016年9月18日下午5:26:16
 * @version 1.0
 */
@Service("VersionService")
public class VersionService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**
	 * 
	 * @descript 获取app上传数据分页
	 * @author 李坡
	 * @since 2016年9月18日下午5:26:55
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryVersionInfo(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("VersionMapper.queryVersioninfolistPage",page);
	}
	
	/**
	 * 
	 * @descript  获取app上传数据不分页
	 * @author 李坡
	 * @since 2016年9月18日下午5:27:14
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryVersionInfo(PageData pd) throws Exception {
        return  (List<Map<String, Object>>) dao.findForList("VersionMapper.queryVersioninfo",pd);
    }
	
	
	/**
	 * 
	 * @descript 修改app状态
	 * @author 李坡
	 * @since 2016年9月19日上午9:26:26
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int updateVersionById(PageData pd) throws Exception{
		return (int) dao.update("VersionMapper.updateVersionById", pd);
	}
	/**
	 * 
	 * @descript 添加app信息
	 * @author 李坡
	 * @since 2016年9月19日上午11:05:28
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int insertVersion(PageData pd) throws Exception{
		return (int) dao.save("VersionMapper.insertVersion", pd);
	}
	/**
	 * 
	 * @descript 带地址修改数据
	 * @author 李坡
	 * @since 2016年9月19日下午5:03:14
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int updatesVersionById(PageData pd) throws Exception{
		return (int) dao.update("VersionMapper.updateAppPath", pd);
	}
	
	/**
	 * 
	 * @descript  单条查询
	 * @author 李坡
	 * @since 2016年9月19日下午5:04:21
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>>  queryVersionById(PageData pd) throws Exception{
		return (List<Map<String, Object>> ) dao.findForList("VersionMapper.queryVersionById", pd);
	}
}
 