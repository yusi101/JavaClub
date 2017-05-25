
package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

/**
 * @ClassName:     BiddingService
 * @Description:TODO( 获取招标信息)
 * @author:    Android_Robot
 * @date:        2016年9月14日 上午9:25:57
 *
 */
@Service("biddingService")
public class BiddingService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/**
     * 
     * @descript 得到招标信息分页
     * @author 李坡
     * @since 2016年9月12日上午11:14:38
     * @param pd
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryBiddingInfo(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("BiddingMapper.queryBiddingInfolistPage",page);
	}
	
	/**
     * 
     * @descript 得到招标信息不分页
     * @author 李坡
     * @since 2016年9月12日上午11:14:38
     * @param pd
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryBiddingInfo(PageData pd) throws Exception {
        return  (List<Map<String, Object>>) dao.findForList("BiddingMapper.queryBiddingInfo",pd);
    }
}
 