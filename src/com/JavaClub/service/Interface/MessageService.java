package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

@Service("messageService")
public class MessageService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 
	 * @descript (根据用户ID查询个人未读消息记录)
	 * @author 李海涛
	 * @since 2016年8月12日上午9:57:37
	 * @param page
	 * @return
	 * @throws Exception
	 */
    public int queryMessageCount (PageData pd) throws Exception{
    	int MyMessageCount=(int) dao.findForObject("MessageMapper.queryMessageCount", pd);
        return  MyMessageCount;
    }
	
	
	/**
	 * 
	 * @descript (根据用户ID查询个人消息)
	 * @author 李海涛
	 * @since 2016年8月12日上午9:57:37
	 * @param page
	 * @return
	 * @throws Exception
	 */
    public List<Map<String,Object>> queryMessageInfo(Page page) throws Exception{
        List<Map<String,Object>> list=(List<Map<String,Object>>)dao.findForListMap("MessageMapper.queryMessageInfolistPage", page);
        return  list;
    }
    
    /**
     * 
     * @descript (根据用户ID查询个人消息)
     * @author 李海涛
     * @since 2016年8月12日上午9:57:37
     * @param page
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> queryMessageInfo(PageData pd) throws Exception{
        List<Map<String,Object>> list=(List<Map<String,Object>>)dao.findForListMap("MessageMapper.queryMessageInfo", pd);
        return  list;
    }
    
    
    
	/**
	 * 
	 * @descript (根据ID查询个人消息)
	 * @author 李海涛
	 * @since 2016年8月12日上午9:57:37
	 * @param page
	 * @return
	 * @throws Exception
	 */
    public List<Map<String,Object>> queryMessageById (PageData pd) throws Exception{
        List<Map<String,Object>> list=(List<Map<String,Object>>)dao.findForListMap("MessageMapper.queryMessageById", pd);
        return  list;
    }
    
	/**
	 * 
	 * @descript (根据ID修改状态)
	 * @author 李海涛
	 * @since 2016年8月12日上午9:57:37
	 * @param page
	 * @return
	 * @throws Exception
	 */
    public int updateMessageById (PageData pd) throws Exception{
    	int updateMyMessage = (int) dao.update("MessageMapper.updateMessageById", pd);
        return  updateMyMessage;
    }
    
    
	/**
	 * 
	 * @descript (根据ID删除个人消息)
	 * @author 李海涛
	 * @since 2016年8月12日上午9:57:37
	 * @param page
	 * @return
	 * @throws Exception
	 */
    public int deleteMessageById (PageData pd) throws Exception{
    	int result = (int) dao.delete("MessageMapper.deleteMessageById", pd);
        return  result;
    }
    
}
