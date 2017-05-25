package com.JavaClub.service.credit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.service.system.ConfigService;
import com.JavaClub.util.Const;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.PageData;
import com.JavaClub.util.SendMailUtil;
import com.JavaClub.util.StrUtil;
import com.JavaClub.util.UuidUtil;

/**
 * 年报人工推送
 * @descript 
 * @author 龚志强
 * @createTime 2017年2月7日下午2:11:57
 * @version 1.0
 */
@Service("annualNotificationService")
public class AnnualNotificationService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name = "configService")
	private ConfigService configService;
	@Autowired
	public EmailsModsService emailsModsService;
	/**
	 * 查询没有过填写年报的企业和没有推送过填写年报的企业
	 * @descript 
	 * @author 龚志强
	 * @since 2016年11月23日下午5:35:23
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryAnnualNotificationlistPage(Page page)throws Exception{

		return (List<Map<String, Object>>) dao.findForList("AnnualNotificationMapper.queryAnnualNotificationlistPage", page);
	}

	/**
	 * 查询年报推送历史
	 * @descript
	 * @author 龚志强
	 * @since 2017年2月9日上午9:12:35
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> annualHistorylistPage(Page page)throws Exception{

		return (List<Map<String, Object>>) dao.findForList("AnnualNotificationMapper.annualHistorylistPage", page);
	}

	/**
	 * 添加到年报发送历史查询
	 * @descript 
	 * @author 龚志强
	 * @since 2017年2月7日下午4:41:10
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String insertAnnualNotification(PageData pd) throws Exception {
		//		List<String> sendTo = new ArrayList();
		//		sendTo.add("1461855809@qq.com");
		//		String title = "你好";
		//		String content = "这是一个测试。";
		//		List<String> attachments = new ArrayList();
		////		attachments.add("D:\\testback\\360随身WiFi");
		////		attachments.add("D:\\testback\\瑞臻征信.png");
		//		SendMailUtil.sendHtmlEmailPaths(sendTo, title, content, null);

		String flag = Const.FAIL;
		int num = 0;

		//获取未处理的通知企业ID，企业名称，邮箱，电话
		String noticeArr[] = pd.getString("noticeInfo").split("::");
		for(int i = 0; i < noticeArr.length; i++){
			String noticeInfoArr[] = noticeArr[i].split(",");
			//设置牌照通知主键
			pd.put("aid", UuidUtil.get32UUID());
			//获取企业ID，企业名称，邮箱，电话
			pd.put("pripid", StrUtil.getArrValue(noticeInfoArr, 0));
			pd.put("entname", StrUtil.getArrValue(noticeInfoArr, 1));

			//1是邮件，2是手机，12是邮箱和手机
			//			if("1".equals(pd.getString("selectOption"))){
			pd.put("email", 1);
			pd.put("tel", 0);

			//查询邮箱模板
			List<Map<String, Object>> eaList = (List<Map<String, Object>>) dao.findForList("EmailsModsMapper.queryEmailActive", null);

			//设置收件人
			List<String> sendTo = new ArrayList<>();
			if(i == 0){
				sendTo.add("1461855809@qq.com");
			} else {
				sendTo.add("1903258687@qq.com");
			}
			//设置附件
			List<String> attachPath = new ArrayList<>();
			for(int n = 0; n < eaList.size(); n++){
				if(null != eaList.get(n).get("FILEURL") && !"".equals(eaList.get(n).get("FILEURL") + "")){
					attachPath.add(eaList.get(n).get("FILEURL") + "");
				}
			}
			//发送邮件
			SendMailUtil.sendHtmlEmailPaths(sendTo, eaList.get(0).get("EMAIL_TITLE") + "", eaList.get(0).get("EMAIL_CTX") + "", attachPath);
			//			}
			/*else if("2".equals(pd.getString("selectOption"))){
				pd.put("email", 0);
				pd.put("tel", 1);
			}else{
				pd.put("email", 1);
				pd.put("tel", 1);

				//查询邮箱模板
				List<Map<String, Object>> eaList = (List<Map<String, Object>>) dao.findForList("EmailsModsMapper.queryEmailActive", null);

				//设置收件人
				List<String> sendTo = new ArrayList<String>();
	            //sendTo.add(StrUtil.getArrValue(noticeInfoArr, 2));
				sendTo.add("1461855809@qq.com");
				//设置附件
				List<String> attachPath = new ArrayList<String>();
				for(int n = 0; n < eaList.size(); n++){
					attachPath.add(eaList.get(n).get("FILEURL") + "");
				}
				//发送邮件
	            SendMailUtil.sendHtmlEmailPaths(sendTo, eaList.get(0).get("EMAIL_TITLE") + "", eaList.get(0).get("EMAIL_CTX") + "", attachPath);
			}*/

			//添加到年报发送历史查询
			num += (int) dao.update("AnnualNotificationMapper.insertAnnualNotification", pd);
		}

		if(0 < num){
			flag =  Const.SUCCESS;
		}

		return flag;
	}
	/**
	 * 查询没有过填写年报的企业和没有推送过填写年报的企业 {不分页}
	 * @descript 
	 * @author 余思
	 * @since 2016年11月23日下午5:35:23
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryAnnualNotificationlist(Page page)throws Exception{

		return (List<Map<String, Object>>) dao.findForList("AnnualNotificationMapper.queryAnnualNotificationlist", page);
	}
	/**
	 * 查询年报推送历史  {不分页}
	 * @descript
	 * @author 余思
	 * @since 2017年2月9日上午9:12:35
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> annualHistorylist(Page page)throws Exception{

		return (List<Map<String, Object>>) dao.findForList("AnnualNotificationMapper.annualHistorylist", page);
	}
	/**
	 * 年报定时发送
	 * @descript
	 * @author 余思
	 * @since 2017年2月9日上午9:12:35
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void autosendAnnualNotice()throws Exception{
		Page page=new Page();
		PageData pd = new PageData();
		String FLAG="";
		pd.put("configName", "年报邮件定时发送设置");
		page.setPd(pd);
		pd.put("STATUS", "1");
		List<Map<String, Object>> emailsModslist = emailsModsService.queryEmailsMods(pd);
		if(ListUtils.isNotEmpty(emailsModslist)){
			List<Map<String,Object>> configList = configService.queryConfiglistPage(page);
			if(configList != null && configList.get(0).get("VALUE").equals("1")){
				List<Map<String,Object>> anList= queryAnnualNotificationlist(page);
				//获取当前用户ID和用户名
				//			if(null != getUser()){
				//				pd.put("uid", getUser().getUSER_ID());
				//				pd.put("uname", getUser().getNAME());
				pd.put("uid", "管理员");
				pd.put("uname", "管理员");
				//			}
				if(ListUtils.isNotEmpty(anList)){
					for (Map<String, Object> map : anList) {
						FLAG=FLAG+map.get("ENTNAME");
					
						List<Map<String,Object>> config= configService.queryConfiglistPage(page);
						if(config != null && !"1".equals(config.get(0).get("VALUE"))){
							System.out.println("=====================邮件发送定时任务已被终止======================");
							break;
						}else{
							pd.put("noticeInfo", map.get("PRIPID")+","+map.get("ENTNAME")+","+map.get("EMAIL")+","+map.get("TEL"));
							//发送通知
							FLAG=FLAG+insertAnnualNotification(pd);
							System.out.println("=====================邮件发送定时任务正在进行中======================"+FLAG);
						}
					}
				}else{
					if(ListUtils.isNotEmpty(configList)){
						pd.put("id", configList.get(0).get("ID"));
					}
					pd.put("value", "0");
					configService.updateConfig(pd);
					System.out.println("=====================邮件已全部发送完成，定时任务已停止======================");
				}
			}else{
				System.out.println("======================邮件发送定时任务未开启======================");
			}
		}else{
			System.out.println("======================未激活模板，邮件发送定时任务无法开启======================");
		}
	}
	/**
	 * 
	 * @descript 获取短信发送企业数据
	 * @author 余思
	 * @since 2017年3月17日上午10:45:18
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectsmsinfo(Page page)throws Exception{
		return (List<Map<String, Object>>) dao.findForList("AnnualNotificationMapper.selectsmsinfo", page);
	}
	/**
	 * 
	 * @descript 添加年报短信推送历史
	 * @author 余思
	 * @since 2017年3月17日上午10:45:14
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int insertsmshistory(PageData pd)throws Exception{

		return (int) dao.update("AnnualNotificationMapper.insertsmshistory", pd);
	}

}