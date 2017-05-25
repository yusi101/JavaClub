package com.JavaClub.util;

import javax.crypto.spec.SecurityHelper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class UrlEncrypt extends TagSupport {

	private String enCode; // 定义解密值属性，即输出参数，并getxxx(), setxxx();

	public String getEnCode() {

		return enCode;

	}

	public void setEnCode(String enCode) {

		this.enCode = enCode;

	}

	/**
	 * 
	 * 重写doEndTag() 方法
	 ***/
	public int doEndTag() throws JspException {

		JspWriter out = pageContext.getOut();

		try

		{
			enCode = SecurityHelper.getEncrypt(enCode); // 给字符串加密方法。
			out.print(enCode); // 页面中显示的内容
		} catch (Exception e){
		}

		return EVAL_PAGE; // 正常的流程继续执行jsp网页
	}

	public String getEnCode(String parm) {
		String oldparm=parm;
		try {
			parm = SecurityHelper.getDecrypt(parm);
		} catch (Exception e) {
			return oldparm;
		}
		return parm;

	}

}
