<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>记录详情</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<%@ include file="../system/allresources.jsp"%>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/plugins/zTree/3.5/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>
  	<style type="text/css">
  		input[type=radio]{
  			vertical-align: top;
  		}
  	</style>
</head>

<body>
	<table class="table table-striped table-bordered table-hover">
		<tr>
			<td width="40%"><span style="color: red">*</span>序号：</td>
			<td width="60%">${status.index+1 }</td>
		</tr>	
		<tr>
			<td><span style="color: red">*</span>用户名：</td>
			<td>${report_list.CREATEUSER_ID}</td>
		</tr>	
		<tr>
			<td><span style="color: red">*</span>征信报告：</td>
			<td>${report_list.ENTNAME}</td>
		</tr>
		<tr>
			<td><span style="color: red">*</span>邮件：</td>
			<td>${report_list.EMAIL}</td>
		</tr>
		<tr>
			<td><span style="color: red">*</span>手机号码：</td>
			<td>${report_list.TEL}</td>
		</tr>	
		<tr>
			<td><span style="color: red">*</span>报告详情：</td>
			<td>${report_list.SELECTS}</td>
		</tr>
		<tr>
			<td><span style="color: red">*</span>用户IP：</td>
			<td>${report_list.IP}</td>
		</tr>
		<tr>
			<td><span style="color: red">*</span>发送时间：</td>
			<td>${report_list.CREATETIME}</td>
		</tr>
	</table>
</body>
</html>
