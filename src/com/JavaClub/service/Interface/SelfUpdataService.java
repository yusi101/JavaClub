package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.Const;
import com.JavaClub.util.PageData;
import com.JavaClub.util.UuidUtil;

@Service("selfUpdataService")
public class SelfUpdataService {
    
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 
	 * @descript (自主更新列表（分页）)
	 * @author 汤彬
	 * @since 2016年9月14日上午11:29:01
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> querySelfUpdatainfo(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("SelfUpdataMapper.querySelfUpdatalistPage",page);
	}
	
	
	/**
	 * 
	 * @descript (自主更新列表（不分页）)
	 * @author 汤彬
	 * @since 2016年9月14日上午11:29:01
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> querySelfUpdatainfo(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("SelfUpdataMapper.querySelfUpdata",pd);
	}
	
	/**
	 * 通过企业主体ID查询企业信息
	 * @descript 用来企业自主更新的添加功能，查询其中的企业名称和身份主体ID
	 * @author 龚志强
	 * @since 2017年1月11日下午1:57:49
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryEntnameByPripid(PageData pd) throws Exception {
		List<Map<String, Object>> entList = (List<Map<String, Object>>) dao.findForList("SelfUpdataMapper.queryEntnameByPripid",pd);
		if(entList.isEmpty()){
			return null;
		}
		return entList.get(0);
	}
	
	/**
	 * 
	 * @descript (自主更新审核详情)
	 * @author 汤彬
	 * @since 2016年9月19日下午2:30:48
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> querySelfUpdataAuditingDetailInfo(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("SelfUpdataMapper.querySelfUpdataAuditingInfo",pd);
	}
	
	
	/**
	 * 
	 * @descript (添加自主更新)
	 * @author 汤彬
	 * @since 2016年9月14日下午4:17:26
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int createSelfUpdata(PageData pd) throws Exception {
		return  (int) dao.save("SelfUpdataMapper.insertSelfUpdata", pd);
	}
	
	/**
	 * 
	 * @descript (删除自主更新)
	 * @author 汤彬
	 * @since 2016年9月18日下午3:39:44
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int deleteSelfUpdata(PageData pd) throws Exception {
		//删除自主更新表中的数据
		 int deleteSelfUpdata=(int)dao.delete("SelfUpdataMapper.deleteSelfUpdata", pd);
		 //删除管理员审核表中的数据
		 pd.put("relationId", pd.getString("ID"));
		 int deleteRespondedInfoByRelationId=(int)dao.delete("RespondedMapper.deleteRespondedInfoByRelationId", pd);
		return  deleteSelfUpdata*deleteRespondedInfoByRelationId;
	}
	
	/**
	 * 
	 * @descript (企业自主更新)
	 * @author 汤彬
	 * @since 2016年9月18日下午4:27:29
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int updateSelfUpdata(PageData pd) throws Exception {
		return  (int) dao.save("SelfUpdataMapper.updateSelfUpdata", pd);
	}
	
	/**
	 * 
	 * @descript (企业自主更新审核)
	 * @author 汤彬
	 * @since 2016年9月19日上午11:30:54
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String updateSelfUpdataAuditing(PageData pd) throws Exception {
		String flag = Const.FAIL;
		int num = 0;
		
		//通过审核
		num += (int) dao.save("SelfUpdataMapper.updateSelfUpdataAuditing", pd);
		
		if(num > 0){
			//添加管理员回复记录
			pd.put("id", UuidUtil.get32UUID());
			pd.put("typeId", "自主更新审核");
			num += (int) dao.save("RespondedMapper.insertRespondedInfo", pd);
			if(num >= 2){
				flag = Const.SUCCESS;
			}
		}
		return flag;
	}

}
