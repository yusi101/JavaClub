package com.JavaClub.entity;

import java.util.HashMap;
import java.util.Map;

import com.JavaClub.util.PageData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AjaxPage {
	
	public static String toJson(Object object,Page page){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("data", object);
		map.put("page", getAjaxPageStr(page,page.getFunName()));
		Gson gson =MyGson();
		String result= gson.toJson(map);
		return result;
	}
	
	
	public static Gson MyGson(){
		Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson;
	}
	
	
	public static Page setAjaxPage(PageData pd,Page page){
		page.setCurrentPage(pd.getString("currentPage")==null ? 1 : Integer.parseInt(pd.getString("currentPage")));//搜索请求的的页数 	默认为1
		if(pd.getString("showCount")!=null){
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}
		page.setFunName(pd.getString("funName"));
		
		pd.remove("currentPage");
		pd.remove("showCount");
		pd.remove("funName");
		page.setPd(pd);
		return page;
    }
	
	/**
	 * 得到分页栏
	 * @author 李海涛
	 * @param page  分页实体
	 * @param funName 点击页码时调用的js方法
	 * @return
	 */
	public static Page getAjaxPageStr(Page page,String funName) {
		StringBuffer sb = new StringBuffer();
		if(page.getTotalResult()>0){
			sb.append("	<div class=\"position-relative\">\n");
			sb.append("	<table style=\"width: 100%;\">\n");
			sb.append("	<tbody>\n");
			sb.append("	<tr>\n");
			sb.append("	<td style=\"vertical-align: top;\">\n");
			sb.append("	<div class=\"pagination\" style=\"float: left; padding-top: 0px; margin-top: 0px;\">\n");
			sb.append("	<ul>\n");
			if(page.getCurrentPage()==1){
				sb.append("	<li><a>共<font color=red>"+page.getTotalResult()+"</font>条</a></li>\n");
				sb.append("	<li><a>首页</a></li>\n");
				sb.append("	<li><a>上页</a></li>\n");
			}else{
				sb.append("	<li><a>共<font color=red>"+page.getTotalResult()+"</font>条</a></li>\n");
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\""+funName+"(1)\">首页</a></li>\n");
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\""+funName+"("+(page.getCurrentPage()-1)+")\">上页</a></li>\n");
			}
			int showTag = 10;//分页标签显示数量
			int startTag = 1;
			if(page.getCurrentPage()>showTag){
				startTag = page.getCurrentPage()-1;
			}
			int endTag = startTag+showTag-1;
			for(int i=startTag; i<=page.getTotalPage() && i<=endTag; i++){
				if(page.getCurrentPage()==i)
					sb.append("<li><a style='background-color: #2283c5;'><font color='#fff'>"+i+"</font></a></li>\n");
				else
					sb.append("	<li style=\"cursor:pointer;\"><a onclick=\""+funName+"("+i+")\">"+i+"</a></li>\n");
			}
			if(page.getCurrentPage()==page.getTotalPage()){
				sb.append("	<li><a>下页</a></li>\n");
				sb.append("	<li><a>尾页</a></li>\n");
			}else{
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\""+funName+"("+(page.getCurrentPage()+1)+")\">下页</a></li>\n");
				sb.append("	<li style=\"cursor:pointer;\"><a onclick=\""+funName+"("+page.getTotalPage()+")\">尾页</a></li>\n");
			}
			sb.append("	<li><a>第"+page.getCurrentPage()+"页</a></li>\n");
			sb.append("	<li><a>共"+page.getTotalPage()+"页</a></li>\n");
			sb.append("</ul>\n");
			
			sb.append("</div>\n");
			sb.append("</td>\n");
			sb.append("</tr>\n");
			sb.append("</tbody>\n");
			sb.append("</table>\n");
			sb.append("</div>\n");
		}
		page.setPageStr(sb.toString());
		return page;
	}
	
}
