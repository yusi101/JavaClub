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
 * @descript (自主业务)
 * @author 李海涛
 * @createTime 2016年9月22日下午6:35:46
 * @version 1.0
 */
@Service("activeService")
public class ActiveService {
    
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**
	 * 
	 * @descript (查询评论 分页)
	 * @author 李海涛
	 * @since 2016年9月22日下午6:34:41
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryCommentInfo(Page page) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("ActiveMapper.queryCommentInfolistPage",page);
        return list;
    }
	
	/**
     * 
     * @descript (查询评论 不分页)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return
     * @throws Exception
     */
	public List<Map<String, Object>> queryCommentInfo(PageData pd) throws Exception {
		List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("ActiveMapper.queryCommentInfo",pd);
		return list;
	}
	
	/**
     * 
     * @descript (查询评论 不分页)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return 操作条数
     * @throws Exception
     */
    public int insertCommentInfo(PageData pd) throws Exception {
        int result = (int) dao.save("ActiveMapper.insertCommentInfo",pd);
        return result;
    }
    
    /**
     * 
     * @descript (对点评进行点赞)
     * @author 李海涛
     * @since 2016年9月26日下午3:53:25
     * @param pd
     * @return
     * @throws Exception
     */
    public int savePraiseSuccessqty(PageData pd) throws Exception {
        int savePraiseSuccessqty = (int) dao.save("ActiveMapper.savePraiseSuccessqty", pd);
        int addSuccessqty = (int) dao.update("ActiveMapper.addSuccessqty", pd);
        return savePraiseSuccessqty*addSuccessqty;      
    }
    
    /**
     * 
     * @descript (对点评进行取消点赞)
     * @author 李海涛
     * @since 2016年9月26日下午3:52:59
     * @param pd
     * @return
     * @throws Exception
     */
    public int cancelPraiseSuccessqty(PageData pd) throws Exception {
        int savePraiseSuccessqty = (int) dao.delete("ActiveMapper.cancelPraiseSuccessqty", pd);
        int addSuccessqty = (int) dao.update("ActiveMapper.cancelSuccessqty", pd);
        return savePraiseSuccessqty*addSuccessqty;      
    }
    
    

    /**
     * 
     * @descript (对点评进行吐槽)
     * @author 李海涛
     * @since 2016年9月26日下午3:59:51
     * @param pd
     * @return
     * @throws Exception
     */
    public int savePraiseFailedqty(PageData pd) throws Exception {
        int savePraiseSuccessqty = (int) dao.save("ActiveMapper.savePraiseFailedqty", pd);
        int addSuccessqty = (int) dao.update("ActiveMapper.addFailedqty", pd);
        return savePraiseSuccessqty*addSuccessqty;      
    }
    
    /**
     * 
     * @descript (对点评进行取消吐槽)
     * @author 李海涛
     * @since 2016年9月26日下午3:59:40
     * @param pd
     * @return
     * @throws Exception
     */
    public int cancelPraiseFailedqty(PageData pd) throws Exception {
        int savePraiseSuccessqty = (int) dao.delete("ActiveMapper.cancelPraiseFailedqty", pd);
        int addSuccessqty = (int) dao.update("ActiveMapper.cancelFailedqty", pd);
        return savePraiseSuccessqty*addSuccessqty;      
    }
    
    
    /**
     * 
     * @descript (用户对企业进行关注)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return 操作条数
     * @throws Exception
     */
    public int insertFollowInfo(PageData pd) throws Exception {
        int result = (int) dao.save("ActiveMapper.insertFollowInfo",pd);
        return result;
    }
    
    /**
     * 
     * @descript (用户对企业进行取消关注)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return 操作条数
     * @throws Exception
     */
    public int cancelFollowInfo(PageData pd) throws Exception {
        int result = (int) dao.update("ActiveMapper.cancelFollowInfo",pd);
        return result;
    }
    
    
    
	/**
     * 
     * @descript (查看关注列表 分页)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param page
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryFollowInfo(Page page) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("ActiveMapper.queryFollowInfolistPage",page);
        return list;
    }
    
    /**
     * 
     * @descript (查看关注列表 不分页)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryFollowInfo(PageData pd) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("ActiveMapper.queryFollowInfo",pd);
        return list;
    }
    
    /**
     * 
     * @descript (查看投诉列表 分页)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param page
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryComplaintInfo(Page page) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("ActiveMapper.queryComplaintInfolistPage",page);
        return list;
    }
    
    /**
     * 
     * @descript (查看投诉列表 不分页)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryComplaintInfo(PageData pd) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("ActiveMapper.queryComplaintInfo",pd);
        return list;
    }
    
    /**
     * 
     * @descript (用户对企业进行投诉)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return 操作条数
     * @throws Exception
     */
    public int insertComplainInfo(PageData pd) throws Exception {
        int result = (int) dao.save("ActiveMapper.insertComplainInfo",pd);
        return result;
    }
    
    /**
     * 
     * @descript (用户对企业进行取消投诉)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return 操作条数
     * @throws Exception
     */
    public int cancelComplainInfo(PageData pd) throws Exception {
        int result = (int) dao.delete("ActiveMapper.cancelComplainInfo",pd);
        pd.put("relationId", pd.getString("id"));
        dao.delete("RespondedMapper.deleteRespondedInfoByRelationId",pd);
        return result;
    }
    
    
    
    /**
     * 
     * @descript (查看附件列表)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryAttachmentInfo(PageData pd) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("ActiveMapper.queryAttachmentInfo",pd);
        return list;
    }
    
    /**
     * 
     * @descript (删除附件信息)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return 操作条数
     * @throws Exception
     */
    public int deleteAttachment(PageData pd) throws Exception {
        int result = (int) dao.delete("ActiveMapper.deleteAttachment",pd);
        return result;
    }
    /**
     * 
     * @descript (添加附件信息)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return 操作条数
     * @throws Exception
     */
    public int insertAttachment(PageData pd) throws Exception {
        int result = (int) dao.save("ActiveMapper.insertAttachment",pd);
        return result;
    }
    
    
    /**
     * 
     * @descript (查看认领列表  分页)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryClaimInfo(Page page) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("ActiveMapper.queryClaimInfolistPage",page);
        return list;
    }
    
    /**
     * 
     * @descript (查看认领列表 不分页)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryClaimInfo(PageData pd) throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) dao.findForListMap("ActiveMapper.queryClaimInfo",pd);
        return list;
    }
    
    
    /**
     * 
     * @descript (增加企业认领信息)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return 操作条数
     * @throws Exception
     */
    public int insertClaimInfo(PageData pd) throws Exception {
        int result = (int) dao.save("ActiveMapper.insertClaimInfo",pd);
        return result;
    }
    /**
     * 
     * @descript (修改企业认领信息)
     * @author 李海涛
     * @since 2016年9月22日下午6:34:41
     * @param pd
     * @return 操作条数
     * @throws Exception
     */
    public int updateClaimInfo(PageData pd) throws Exception {
        int result = (int) dao.update("ActiveMapper.updateClaimInfo",pd);
        return result;
    }
    
    
    /**
     * 取消认领企业 
     * 胡华锋
     * @param pd
     * @return
     * @throws Exception
     */
    public int cancelClaimInfo(PageData pd) throws Exception {
        int result = (int) dao.delete("ActiveMapper.deleteClaimInfo", pd);
        pd.put("relationId", pd.getString("claimId"));
        dao.delete("ActiveMapper.deleteAttachment",pd);
        dao.delete("RespondedMapper.deleteRespondedInfoByRelationId",pd);
        dao.delete("ActiveMapper.deleteClaimUserInfo", pd);
        return result;
    }
    
    /**
     * 保存用户的反馈
     * 李海涛
     * @param page
     * @return
     * @throws Exception
     */
    public int saveOpinion(PageData pd) throws Exception {
        int result= (int) dao.save("ActiveMapper.saveOpinion",pd);
       
        return result;
    }
}