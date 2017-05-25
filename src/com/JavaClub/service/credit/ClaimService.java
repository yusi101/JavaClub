package com.JavaClub.service.credit;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.Const;
import com.JavaClub.util.PageData;
import com.JavaClub.util.UuidUtil;
import com.util.SendMailUtil;

/**
 * 
 * @descript 认领审核
 * @author 龚志强
 * @createTime 2016年9月26日上午9:22:28
 * @version 1.0
 */
@Service("claimService")
public class ClaimService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	final String queryClaimApplylistPage = "ClaimMapper.queryClaimApplylistPage";	//查询全部的认领审核
	final String queryClaimById = "ClaimMapper.queryClaimById";						//查询某个认领审核详情
	final String queryAttachmentById = "ClaimMapper.queryAttachmentById";			//查询某个类型的附件材料
	final String updateClaim = "ClaimMapper.updateClaim";							//修改认领审核信息
	final String insertResponded = "ClaimMapper.insertResponded";					//添加管理员审核信息
	final String insertSysUser = "ClaimMapper.insertSysUser";						//添加后台管理员信息
	final String insertUserRole = "ClaimMapper.insertUserRole";						//添加管理员角色信息
	final String queryClaimByMid = "ClaimMapper.queryClaimByMid";					//查询是否有过成功认领记录
	final String insertMessageData = "MessageMapper.insertMessageData";						//查询添加用户消息记录
	
	/**
	 * 
	 * @descript 查询全部的认领审核
	 * @author 龚志强
	 * @since 2016年9月26日上午9:30:43
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryClaimApplylistPage(Page page) throws Exception {
		
		return (List) dao.findForList(queryClaimApplylistPage, page);
	}
	
	/**
	 * 
	 * @descript 查询某条认领审核信息
	 * @author 龚志强
	 * @since 2016年9月26日上午10:49:58
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryClaimById(PageData pd) throws Exception {
		
		return (List) dao.findForList(queryClaimById, pd);
	}
	
	/**
	 * 
	 * @descript 查询某个认领企业的附件材料信息
	 * @author 龚志强
	 * @since 2016年9月26日上午11:54:16
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryAttachmentById(PageData pd) throws Exception {
		List attachmentList = (List) dao.findForList(queryAttachmentById, pd);
		
		return attachmentList;
	}
	
	
	/**
	 * 
	 * @descript 修改认领审核信息
	 * @author 龚志强
	 * @since 2016年9月26日上午11:23:51
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String updateClaim(PageData pd) throws Exception {
		String flag = Const.FAIL;
		int num = 0;

		//2为认领审核未通过，1为认领审核已通过
		if("2".equals(pd.getString("status"))){
			//修改认领状态和认领描述等信息
			num += (int) dao.update(updateClaim, pd);
			
			if(0 < num){
				//添加管理回复表
				num += (int) dao.save(insertResponded, pd);
				pd.put("mid", UuidUtil.get32UUID());					//设置用户消息主键ID
				pd.put("mtitle", "企业认领");								//设置用户消息标题
				pd.put("mcontent", "抱歉！您认领的企业审核失败！如有疑问请拨打服务热线电话：" + Const.TELEPHONE);	//设置用户消息内容
				//添加用户消息记录
				num += (int) dao.save(insertMessageData, pd);
				flag =  Const.SUCCESS;
			}
		}else{
			//查询是否有过成功认领记录
			List cliaimList = (List) dao.findForList(queryClaimByMid, pd);
			if(!cliaimList.isEmpty()){
				return flag;
			}
			
			pd.put("sysId", UuidUtil.get32UUID());					//设置管理员主键ID
			pd.put("urId", UuidUtil.get32UUID());					//设置管理角色主键ID
			pd.put("sysRemark", "这是企业用户管理员。");					//设置管理员和管理员角色备注
			pd.put("roleId", "f22d7ef0944c46f5888c8b8d5f111491");	//设置企业管理员角色ID
			pd.put("mid", UuidUtil.get32UUID());					//设置用户消息主键ID
			pd.put("mtitle", "企业认领");								//设置用户消息标题
			String mcontent = "恭喜您！认领审核通过。您可以通过电脑登录" 
                    + Const.SYSNAME + "后台管理自己的企业信息。账号密码是您之前在我们平台申请过的账号密码。如有疑问请拨打热线电话：" + Const.TELEPHONE + "。以下是" 
                    + Const.SYSNAME + "后台地址：<br/><a href='" + Const.CONNECT_PATH_JX + "'>" + Const.CONNECT_PATH_JX  +"</a>";
			pd.put("mcontent", mcontent);							//设置用户消息内容
			
			//修改认领状态和认领描述等信息
			num += (int) dao.update(updateClaim, pd);
			
			//如果修改失败则返回失败！考虑幻读的情况。
			if(0 == num){
				return flag;
			}
			
			//添加用户消息记录
			num += (int) dao.save(insertMessageData, pd);
			//添加管理回复表
			num += (int) dao.save(insertResponded, pd);
			//添加后台管理员信息 
			num += (int) dao.save(insertSysUser, pd);
			//添加管理员角色信息
			num += (int) dao.save(insertUserRole, pd);
			
			//判断是否以上数据是否添加成功
			if(0 < num){
				flag =  Const.SUCCESS;
				//发送邮件
				List<String> sendTo =new ArrayList<String>();
		        sendTo.add(pd.getString("email"));
		        String content="恭喜您！认领审核通过。您可以登录" 
	                    + Const.SYSNAME + "后台管理自己的企业信息。账号密码是您之前在我们平台申请过的账号密码。如有疑问请拨打热线电话：" + Const.TELEPHONE + "。以下是" 
	                    + Const.SYSNAME + "后台地址：<br/><a href='" + Const.CONNECT_PATH_JX + "'>" + Const.CONNECT_PATH_JX  +"</a>";
		        SendMailUtil.sendHtmlEmail(sendTo,"【" + Const.SYSNAME + "】企业认领",content);
			}
		}
		
		return flag;
	}
}