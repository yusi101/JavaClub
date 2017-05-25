package com.JavaClub.service.system;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.system.User;
import com.JavaClub.util.PageData;

/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年9月14日上午11:17:42
 * @version 1.0
 */
@Service("loginService")
public class LoginService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 
	 * @descript 登录判断
	 * @author 
	 * @since 2016年10月9日上午9:23:13
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public User getUserByNameAndPwd(PageData pd) throws Exception {
		List<User> userlist=(List<User>) dao.findForList("LoginMapper.queryUserinfo", pd);
		if(!userlist.isEmpty()){
			return userlist.get(0);
		}else{
			return null;
		}
		
	}
	
	/**
	 * 
	 * @descript 查询用户拥有的角色
	 * @author 龚志强
	 * @since 2016年10月9日上午9:25:36
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryRoleByUid(PageData pd) throws Exception {
		List roleList = (List) dao.findForList("LoginMapper.queryRoleByUid", pd);
		return roleList;
	}
	
	/**
	 * 
	 * @descript 查询角色拥有的权限
	 * @author 龚志强
	 * @since 2016年10月9日上午10:30:54
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryAuthByRid(String auth) throws Exception {
		PageData pd = new PageData();
		pd.put("auth", auth);
		
		List roleList = (List) dao.findForList("LoginMapper.queryAuthByRid", pd);
		
		return roleList;
	}
	
	/**
	 * 查询所有权限
	 * @descript 用于超级管理员
	 * @author 龚志强
	 * @since 2016年10月14日下午3:53:18
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryAuth() throws Exception {
		
		return (List) dao.findForList("LoginMapper.queryAuth", null);
	}

	/**
	 * 
	 * @descript 查询单个用户信息
	 * @author 李坡
	 * @since 2016年9月21日下午7:33:11
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryUser(PageData pd) throws Exception {
		return (List<Map<String, Object>>) dao.findForList("LoginMapper.queryUser", pd);
	}

	/**
	 * 
	 * @descript 修改用户资料
	 * @author 李坡
	 * @since 2016年9月21日下午8:09:17
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int updateUserInfo(PageData pd) throws Exception {
		return (int) dao.update("LoginMapper.updateUserInfo", pd);
	}
	
	/**
	 * 
	 * @descript 修改用户资料
	 * @author 李坡
	 * @since 2016年9月21日下午8:09:17
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int updateUser(PageData pd) throws Exception {
		return (int) dao.update("LoginMapper.updateUser", pd);
	}
}
