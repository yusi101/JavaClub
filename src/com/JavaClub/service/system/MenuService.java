/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年9月16日下午4:31:17
 * @version 1.0
 */
package com.JavaClub.service.system;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;

/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年9月16日下午4:31:17
 * @version 1.0
 */
@Service(value="menuService")
public class MenuService {
    
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    
    /**
     * 
     * @descript (得到所有的顶级菜单)
     * @author 李海涛
     * @since 2016年9月16日下午5:04:06
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryAllParentMenu() throws Exception {
        return (List<Map<String, Object>>) dao.findForList("MenuMapper.queryAllParentMenu", null);
        
    }
    
    /**
     * 
     * @descript (根据父节点得到子节点)
     * @author 李海涛
     * @since 2016年9月16日下午5:04:25
     * @param pd
     * @return
     * @throws Exception
     */
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> querySubMenuByParentId(PageData pd) throws Exception {
        return (List<Map<String, Object>>) dao.findForList("MenuMapper.querySubMenuByParentId", pd);
        
    }
    
    /**
     * 
     * @descript (根据MENU_ID得到菜单信息)
     * @author 李海涛
     * @since 2016年9月18日下午3:36:34
     * @param pd
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryMenuByMenuId(PageData pd) throws Exception {
        return (List<Map<String, Object>>) dao.findForList("MenuMapper.queryMenuByMenuId", pd);
        
    }
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryAllMenu(PageData pd) throws Exception {
        return (List<Map<String, Object>>) dao.findForList("MenuMapper.queryAllMenu", pd);
    }
    
    public int insertMenu(PageData pd) throws Exception {
        return (int) dao.save("MenuMapper.insertMenu", pd);
    }
    /**
     * 
     * @descript (根据MENU_ID去修改菜单信息)
     * @author 李海涛
     * @since 2016年9月18日下午5:13:10
     * @param pd
     * @return
     * @throws Exception
     */
    public int updateMenuByMenuId(PageData pd) throws Exception {
         return (int) dao.update("MenuMapper.updateMenuByMenuId", pd);
    }
    /**
     * 
     * @descript (根据MENU_ID去删除菜单信息)
     * @author 李海涛
     * @since 2016年9月18日下午5:13:10
     * @param pd
     * @return
     * @throws Exception
     */
    public String deleteMenuByMenuId(PageData pd) throws Exception {
        int result=(int) dao.update("MenuMapper.deleteMenuByMenuId", pd);
        if(result>0){
           return "success"; 
        }else{
           return "false";
        }
        
    }
}
