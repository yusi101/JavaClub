package com.JavaClub.service.credit;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.Const;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (评论业务层)
 * @author 汤彬
 * @createTime 2016年9月22日下午4:10:39
 * @version 1.0
 */
@Service("commentService")
public class CommentService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	final String queryCommentlistPage = "CommentMapper.queryCommentlistPage"; //查询所有评论
	final String queryCommentDetails = "CommentMapper.queryCommentDetails";//查询单个评论详情
	final String updateComment = "CommentMapper.updateComment";//修改审核状态
	final String createResponded = "CommentMapper.createResponded";//添加审核记录
	
	/**
	 * 
	 * @descript (查询所有评论，分页)
	 * @author 汤彬
	 * @since 2016年9月22日下午4:13:25
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryCommentInfo(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList(queryCommentlistPage,page);
	}
	
	/**
	 * 
	 * @descript (查询单个评论详情)
	 * @author 汤彬
	 * @since 2016年9月22日下午5:20:15
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryCommentDetailInfo(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList(queryCommentDetails,pd);
	}
	
	/**
	 * 
	 * @descript (修改评论审核状态)
	 * @author 汤彬
	 * @since 2016年9月22日下午5:36:41
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String updateComment(PageData pd) throws Exception {
		int num = 0;
		String flag = Const.FAIL;
		
		num += (int) dao.save(updateComment, pd);
		if(num >= 1){
			num += (int) dao.save(createResponded, pd);
			if(num >= 2){
				flag = Const.SUCCESS;
			}
		}
		
		return flag;
	}
}