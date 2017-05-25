package com.JavaClub.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeUtility;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;

public class SendMailUtil {
	
	 protected static Logger logger = Logger.getLogger(SendMailUtil.class);
	public static String sendHtmlEmail(List<String> sendTo, String title, String htmlMessage, String attachPath) {
		
		try {
			HtmlEmail email = new HtmlEmail();
			//email.setSSL(true);
			//email.setSslSmtpPort(Const.EMAILPORT);
			email.setSmtpPort(Const.EMAILPORT);//邮箱端口
			email.setSubject(title);//标题
			email.setCharset("GB2312");
			email.setHostName(Const.EMAILPROTOCOL);//邮箱协议
			email.setAuthentication(Const.EMAILUSER, Const.EMAILPASSWORD);//发送邮箱的账号 发送邮箱的密码
			email.setFrom(Const.EMAIL_NAME+"<"+Const.EMAILUSER+">");//子标题

			EmailAttachment attachment = null;
			if ((attachPath != null) && (attachPath != "")) {
				String attachName = attachPath.substring(attachPath.lastIndexOf(File.separator) + 1,
						attachPath.length());
				attachment = new EmailAttachment();
				try {
					attachment.setPath(attachPath);
					attachment.setName(MimeUtility.encodeText(attachName));
					attachment.setDisposition("attachment");
					attachment.setDescription(attachName);
					email.attach(attachment);
				} catch (UnsupportedEncodingException e) {
					logger.error(e.toString(),e);
				}
			}

			for (String aTo : sendTo) {
				email.addTo(aTo);
			}

			email.setHtmlMsg(htmlMessage);
			email.send();

			return "success";
		} catch (Exception ee) {
			logger.error(ee.toString(),ee);
		}

		return "error";
	}

	public static String sendHtmlEmail(List<String> sendTo, String title, String htmlMessage) {
		return sendHtmlEmail(sendTo, title, htmlMessage, null);
	}

	/**
	 * 发送多个文件附件
	 * @descript
	 * @author 龚志强
	 * @since 2017年2月8日下午6:00:04
	 * @param sendTo
	 * @param title
	 * @param htmlMessage
	 * @param attachPaths
	 * @return
	 */
	public static String sendHtmlEmailPaths(List<String> sendTo, String title, String htmlMessage, List<String> attachPaths) {
		try {
			HtmlEmail email = new HtmlEmail();
			//email.setSSL(true);
			//email.setSslSmtpPort(Const.EMAILPORT);
			email.setSmtpPort(Const.EMAILPORT);
			email.setSubject(title);
			email.setCharset("GB2312");
			email.setHostName(Const.EMAILPROTOCOL);
			email.setAuthentication(Const.EMAILUSER, Const.EMAILPASSWORD);
			email.setFrom(Const.EMAIL_NAME+"<"+Const.EMAILUSER+">");
			System.out.println("email============="+email.getSslSmtpPort()+"=="+email.getSmtpPort()+"=="+email.getHostName());	
			EmailAttachment attachment = null;
			if (null != attachPaths && !attachPaths.isEmpty()) {
				for(String aPs : attachPaths){
					if ((aPs != null) && (aPs != "")) {
						String attachName = aPs.substring(aPs.lastIndexOf(File.separator) + 1,
								aPs.length());
						attachment = new EmailAttachment();
						try {
							attachment.setPath(aPs);
							attachment.setName(MimeUtility.encodeText(attachName));
							attachment.setDisposition("attachment");
							attachment.setDescription(attachName);
							email.attach(attachment);
						} catch (UnsupportedEncodingException e) {
							logger.error(e.toString(),e);
						}
					}				
				}
			}

			for (String aTo : sendTo) {
				email.addTo(aTo);
			}



			email.setHtmlMsg(htmlMessage);
			email.send();

			return "success";
		} catch (Exception ee) {
			logger.error(ee.toString(),ee);
		}

		return "error";
	}

	public static void main(String[] args) {
		//		String title = "江西智容科技有限公司征信报告";
		//		List sendTo = new ArrayList();
		//		sendTo.add("914497544@qq.com");
		//
		//		String htmlMessage = "<html><body><font size='15' color='red'>欢迎光临我之家</font><a href=\"http://www.baidu.com\" target=\"_blank\">百度</a></body></html>";
		//		System.out.println(sendHtmlEmail(sendTo, title, htmlMessage, null));

		List<String> sendTo = new ArrayList<>();
		sendTo.add("1461855809@qq.com");
		String title = "你好";
		String content = "这是一个测试。";
		List<String> attachments = new ArrayList<>();
		//		attachments.add("D:\\testback\\360随身WiFi");
		//		attachments.add("D:\\testback\\瑞臻征信.png");
		System.out.println(""+attachments);
		sendHtmlEmail(sendTo, title, content, null);

	}
}