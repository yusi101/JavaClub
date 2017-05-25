package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.PageData;

@Service("memberService")
public class MemberService{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 主页页面从bl_member表中获取指定username字段
	 */
	@SuppressWarnings("rawtypes")
	public List  userRegister(PageData pd) throws Exception{
		List list =(List)dao.findForList("MemberMapper.userRegister", pd);
		return list;
	}
	/*
	 * 主页页面从bl_member表中获取指定username字段
	 */
	@SuppressWarnings("rawtypes")
	public List  userRegisteremail(PageData pd) throws Exception{
		List list =(List)dao.findForList("MemberMapper.userRegisteremail", pd);
		return list;
	}
	/*
	 * 注册页信息插入数据库bl_member中
	 */
	public int  saveUserRegister(PageData pd) throws Exception{
		int count =(int)dao.save("MemberMapper.saveUserRegister", pd);
		return count;
	}
	/*
	 * 修改用户数据
	 */
	public int userModify (PageData pd) throws Exception{
		int count=(int)dao.update("MemberMapper.modifyUserInfo", pd);
		return count;
	}
	/*
	 * 修改密码
	 */
	public int passwordModify (PageData pd) throws Exception{
		int count=(int)dao.update("MemberMapper.passwordModify", pd);
		return count;
	}
	/*
	 * 获取当前密码
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> passwordGet(PageData pd) throws Exception{
		List<Map<String, Object>> list =(List<Map<String, Object>>)dao.findForList("MemberMapper.passwordGet", pd);
		return list;
	}
	
	
	/*
	 * 登录成功后返回的值
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getMemberInfo(PageData pd) throws Exception{
		List<Map<String, Object>> list =(List<Map<String, Object>>)dao.findForList("MemberMapper.getMemberInfo", pd);
		return list;
	}
	
	/*
	 * 李海涛
	 * 添加邮箱记录
	 */
	public int saveEmail (PageData pd) throws Exception{
		int count=(int)dao.save("MemberMapper.saveEmail", pd);
		return count;
	}
	
	/*
	 * 李海涛
	 * 查找邮箱记录 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryEmailHistory(PageData pd) throws Exception{
		List<Map<String, Object>> list =(List<Map<String, Object>>)dao.findForList("MemberMapper.queryEmailHistory", pd);
		return ListUtils.getListMap(list, 0);
	}

	/*
	 * 龚志强
	 * 激活用户
	 */
	public int activatedUser(PageData pd) throws Exception{
		int count = (int) dao.save("MemberMapper.activatedUser", pd);
		return count;
	}
	/*
	 * 余思
	 * 判断用户是否存在  判断邮箱是否正确
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> usernameGet(PageData pd) throws Exception{
		return (List<Map<String, Object>>)dao.findForList("MemberMapper.queryUsername", pd);
	}
	/*
	 * 余思
	 * 判断邮箱，和 手机是否绑定账号
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryUseremailandphone(PageData pd) throws Exception{
		return (List<Map<String, Object>>)dao.findForList("MemberMapper.queryUseremailandphone", pd);
	}
	/*
	 * 余思
	 * 重置密码
	 */
	public int czpassword(PageData pd) throws Exception{
		return (int)dao.update("MemberMapper.updatapwd", pd);
	}
	/*
	 * 余思
	 * 添加支付记录
	 */
	public int savepay (PageData pd) throws Exception{
		return (int)dao.save("MemberMapper.savepay", pd);
	}
	
	/*
	 * 余思
	 * 查找支付记录 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> querypayHistory(PageData pd) throws Exception{
		return (List<Map<String, Object>>)dao.findForList("MemberMapper.querypayHistory", pd);
	}
}
