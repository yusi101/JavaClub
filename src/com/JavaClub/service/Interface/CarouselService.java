package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

@Service("carouselService")
public class CarouselService {
    
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 
	 * @descript (获取轮播图列表分页)
	 * @author 汤彬
	 * @since 2016年9月19日下午3:58:22
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryCarouseInfoListPage(Page page) throws Exception {
        return  (List<Map<String, Object>>) dao.findForList("CarouselMapper.queryCarouseInfoListPage",page);
    }
	
	
	/**
	 * 
	 * @descript (获取轮播图列表不分页)
	 * @author 汤彬
	 * @since 2016年9月19日下午3:58:22
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryCarouseInfo(PageData pd) throws Exception {
        return  (List<Map<String, Object>>) dao.findForList("CarouselMapper.queryCarouseInfo",pd);
    }
	
	/**
	 * 
	 * @descript (添加轮播图)
	 * @author 汤彬
	 * @since 2016年9月19日下午5:31:59
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int insertCarouse(PageData pd) throws Exception {
		return  (int) dao.save("CarouselMapper.insertCarouse", pd);
	}
	
	
	/**
	 * 
	 * @descript (编辑轮播图)
	 * @author 汤彬
	 * @since 2016年9月20日下午2:34:12
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int updateCarouse(PageData pd) throws Exception {
		return  (int) dao.save("CarouselMapper.updateCarouse", pd);
	}
	
	/**
	 * 
	 * @descript (删除轮播图)
	 * @author 汤彬
	 * @since 2016年9月20日下午3:13:15
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int deleteCarouse(PageData pd) throws Exception {
		return  (int) dao.delete("CarouselMapper.deleteCarouse", pd);
	}
}
