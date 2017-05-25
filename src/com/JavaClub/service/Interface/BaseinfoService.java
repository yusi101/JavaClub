package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;
import com.JavaClub.util.UuidUtil;

@Service("baseinfoService")
public class BaseinfoService{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 
	 * @descript (根据商标查询企业信息)
	 * @author 李海涛
	 * @since 2016年8月12日上午9:57:37
	 * @param page
	 * @return
	 * @throws Exception
	 */
    public List<Map<String,Object>> getEntByBrandNameinfolistPage (Page page) throws Exception{
    	return (List<Map<String,Object>>)dao.findForListMap("BaseinfoMapper.queryEnterpriseByBrandNamelistPage", page);
    }
    
    /**
     * 
     * @descript (根据失信人查询企业信息)
     * @author 李海涛
     * @since 2016年8月12日上午9:57:37
     * @param page
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> getEntByCourtcaseinfolistPage (Page page) throws Exception{
    	return (List<Map<String,Object>>)dao.findForListMap("BaseinfoMapper.queryEnterpriseByCourtcaselistPage", page);
    }
    
    /**
     * 
     * @descript (根据新企业查询企业信息)
     * @author 余思
     * @since 2017年4月21日上午11:36:26
     * @param page
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> queryEnterpriseByNewlistPage (Page page) throws Exception{
    	return (List<Map<String,Object>>)dao.findForListMap("BaseinfoMapper.queryEnterpriseByNewlistPage", page);
    }
    /**
     * 
     * @descript (根据企业查询企业信息)
     * @author 余思
     * @since 2017年4月21日上午11:36:31
     * @param page
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> queryEnterpriseEntNamelistPage (Page page) throws Exception{
    	return (List<Map<String,Object>>)dao.findForListMap("BaseinfoMapper.queryEnterpriseEntNamelistPage", page);
    }
    /**
     * 
     * @descript (根据法人查询企业信息)
     * @author 余思
     * @since 2017年4月21日上午11:36:31
     * @param page
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> queryEnterpriseNamelistPage (Page page) throws Exception{
    	return (List<Map<String,Object>>)dao.findForListMap("BaseinfoMapper.queryEnterpriseNamelistPage", page);
    }
	//行业门类
	public List<Map<String,Object>> getindustry() throws Exception{
		return (List<Map<String, Object>>) dao.findForListMap("BaseinfoMapper.getindustry", null);
	}
	
	/**
	 * @Title: getArea 
	 * @Description: 得到所有省市信息
	 * @author xsj
	 * @param @return
	 * @param @throws Exception     参数说明
	 * @return Map<String,Object>    返回类型
	 */
	public List<Map<String, Object>> getArea() throws Exception {
		return (List<Map<String, Object>>) dao.findForListMap("BaseinfoMapper.getAreaInfo", null);
	}
	
	
	/*选择地方省级*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> getcityjx() throws Exception {
		return (Map<String, Object>) dao.findForObject("BaseinfoMapper.getcityjx", null);		
	}
	
	/*选择地方省级*/
	@SuppressWarnings("unchecked")
	public Map<String, Object> getcityhn() throws Exception {
		return (Map<String, Object>) dao.findForObject("BaseinfoMapper.getcityhn", null);		
	}
	
	
	/*选择地方江西省市*/
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getcitycodejx() throws Exception {
		List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForList("BaseinfoMapper.getcitycodejx", null);
		return list;	
	}
	
	/*选择地河南省市*/
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getcitycodehn() throws Exception {
		return (List<Map<String, Object>>) dao.findForList("BaseinfoMapper.getcitycodehn", null);
	}
		
	
	
	/*首页 根据pritype，,pripid，查询企业基本信息和16个模块的数量*/
	@SuppressWarnings("rawtypes")
	public List queryAllCountForEnterprise(PageData pd) throws Exception {
		return (List) dao.findForList("BaseinfoMapper.queryAllCountForEnterprise",pd);
	}
	//查  zr_qydjxx 新企业表
	public List<Map<String, Object>> getEnterprise(Page page) throws Exception {
		return (List<Map<String, Object>>) dao.findForListMap("BaseinfoMapper.queryEnterprise", page);
	}
	
	/**
	 * 九宫格进入日志
	 * 李海涛
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int saveOperationLog(PageData pd) throws Exception {
			return (int) dao.save("BaseinfoMapper.saveOperationLog", pd);
	}
	
	/**
	 * 插入关键词搜索
	 * 李海涛
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int saveKeywordLog(PageData pd) throws Exception {
	    if(pd.getString("searchKey")!="" && !"".equals(pd.getString("searchKey")) && (pd.getString("pageIndex")==null || "1".equals(pd.getString("pageIndex")))){
	        return (int) dao.save("BaseinfoMapper.saveKeywordLog", pd);
	    }else{
	        return 0;
	    }
	}
	
	/**
     * 
     * @descript (记录搜索关键词)
     * @author 李海涛
     * @since 2016年11月3日上午9:00:05
     * @param pd
     * @param searchKey 关键词
     * @param logType  搜索类型  为企业、商标、专利
     * @throws Exception
     */
	public void saveKeywordLog(PageData pd,String searchKey,String[] nosave,String logType) throws Exception {
	  int status=0;//如果为0则记录
	    //记录商标查询
	    for (int i = 0; i < nosave.length; i++) {
            if(pd.getString(nosave[i])!=null && !"".equals(pd.getString(nosave[i]))){
                status=1;
            }
        }
        if((pd.getString("pripid") ==null || "".equals(pd.getString("pripid")) )&& status==0){
            pd.put("searchKey", searchKey);
            pd.put("logType",logType);
            pd.put("LogID", UuidUtil.get32UUID());
            if(pd.getString("searchKey")!="" && !"".equals(pd.getString("searchKey")) && (pd.getString("pageIndex")==null || "1".equals(pd.getString("pageIndex")))){
    	        dao.save("BaseinfoMapper.saveKeywordLog", pd);
    	    }
        }
	}
	/**
	 * 
	 * @descript 查询企业信用等级
	 * @author 余思
	 * @since 2017年5月4日下午5:33:08
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map getgradename(PageData pd) throws Exception {
		return (Map) dao.findForObject("BaseinfoMapper.getgradename", pd);
	}
}
