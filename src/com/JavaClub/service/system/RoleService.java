package com.JavaClub.service.system;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;


/**
 * 
 * @descript (权限)
 * @author 汤彬
 * @createTime 2016年9月23日上午11:13:18
 * @version 1.0
 */
@Service("roleService")
public class RoleService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	final String queryRolelistPage = "RoleMapper.queryRolelistPage"; //角色查询分页
	final String queryMenu = "RoleMapper.queryMenu";//查询权限
	final String queryRoleMenu = "RoleMapper.queryRoleMenu";//查询权限
	final String createRole = "RoleMapper.createRole";//添加角色
	final String deleteRole = "RoleMapper.deleteRole";//删除角色
	final String updateRole = "RoleMapper.updateRole";//修改角色
	
	/**
	 * 
	 * @descript (按条件查询角色)
	 * @author 汤彬
	 * @since 2016年9月23日上午11:15:22
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryRolelistPage(Page page) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList(queryRolelistPage,page);
	}
	
	/**
	 * 
	 * @descript (查询权限)
	 * @author 汤彬
	 * @since 2016年9月23日下午2:25:12
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryMenu(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList(queryMenu,pd);
	}
	
	/**
     * 
     * @descript (查询指定角色的权限)
     * @author 汤彬
     * @since 2016年9月23日下午2:25:12
     * @param pd
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryRoleMenu(PageData pd) throws Exception {
        return  (List<Map<String, Object>>) dao.findForList(queryRoleMenu,pd);
    }
	
	
	/**
	 * 
	 * @descript (添加角色)
	 * @author 汤彬
	 * @since 2016年9月23日下午4:15:37
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int createRole(PageData pd) throws Exception {
		return (int)dao.save(createRole, pd);
	} 
	
	/**
	 * 
	 * @descript (删除角色)
	 * @author 汤彬
	 * @since 2016年9月23日下午5:19:28
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int deleteRole(PageData pd) throws Exception {
		return (int)dao.save(deleteRole, pd);
	} 
	
	/**
	 * 
	 * @descript (修改角色)
	 * @author 汤彬
	 * @since 2016年9月27日上午11:24:16
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int updateRole(PageData pd) throws Exception {
		return (int)dao.save(updateRole, pd);
	} 
}
