package com.JavaClub.controller.credit;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.entity.system.User;
import com.JavaClub.service.credit.CommentService;
import com.JavaClub.util.PageData;

/**
 * 
 * @descript (评论控制器)
 * @author 汤彬
 * @createTime 2016年9月22日下午4:17:25
 * @version 1.0
 */
@Controller
@RequestMapping(value="/commentController")
public class CommentController  extends BaseController{
	
	final String type_Id = "评论";   //回复类型
	
	@Autowired
	public CommentService commentService;
	
	
	/**
	 * 
	 * @descript (所有评论)
	 * @author 汤彬
	 * @since 2016年9月22日下午5:53:18
	 * @param page
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/queryCommentinfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryCommentinfo(Page page) throws Exception {
        ModelAndView mv=new ModelAndView("comment/comment_list");
        PageData pd = new PageData();
        pd = this.getPageData();
		//设置分页
		page.setPd(pd);
		//获取所有配置项信息
		List<Map<String,Object>> commentInfo = commentService.queryCommentInfo(page);
		
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject("commentInfo", commentInfo);
		return mv;
	}
    
    
    /**
	 * 
	 * @descript (跳转到评论审核页面)
	 * @author 汤彬
	 * @since 2016年9月22日下午5:53:18
	 * @param page
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/queryAuditCommentinfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryAuditCommentinfo(Page page) throws Exception {
        ModelAndView mv=new ModelAndView("comment/comment_aud");
        PageData pd = new PageData();
        pd = this.getPageData();
		//设置分页
		page.setPd(pd);
		//获取所有配置项信息
		List<Map<String,Object>> commentInfo = commentService.queryCommentInfo(page);
		
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject("commentInfo", commentInfo);
		return mv;
	}
    
    
    /**
     * 
     * @descript (评论详情)
     * @author 汤彬
     * @since 2016年9月22日下午5:42:15
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryCommentDetailInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryCommentDetailInfo() throws Exception {
        ModelAndView mv=new ModelAndView("comment/comment_det");
        PageData pd = new PageData();
        pd = this.getPageData();
	
		//获取所有配置项信息
		List<Map<String,Object>> commentInfo = commentService.queryCommentDetailInfo(pd);
		
		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject("commentInfo", commentInfo);
		return mv;
        
	}
    
    /**
     * 
     * @descript (评论审核)
     * @author 汤彬
     * @since 2016年9月22日下午5:56:26
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateCommentInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateCommentInfo() throws Exception {
    	PageData  pd=this.getPageData();
    	
		User user=this.getUser();
		
		//添加UUID
		pd.put("respondedId", this.get32UUID());
		//添加回复类型
		pd.put("type_Id", type_Id);
		//添加审核用户
		pd.put("createUser_Id", user.getUSER_ID());
    	
		return commentService.updateComment(pd);
    }
}
