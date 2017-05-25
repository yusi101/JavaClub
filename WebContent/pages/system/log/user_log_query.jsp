<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="../allresources.jsp"%>
<title>日志管理</title>
</head>
<body>
	<div class="container-fluid">
		<table class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:120px;">功能模块</td>
				<td>${userLogPd.MODULENAME }</td>
			</tr>
			<tr>
				<td>链接地址</td>
				<td>${userLogPd.MODULEURL }</td>
			</tr>
			<tr>
				<td>IP地址</td>
				<td>${userLogPd.IP }</td>
			</tr>
			<tr>
				<td>操作类型</td>
				<td>${userLogPd.LOGTYPE }</td>
			</tr>
			<tr>
				<td>操作时间</td>
				<td><fmt:formatDate value="${userLogPd.CREATEDTIME}" pattern="yyyy年MM月dd日  HH时mm分ss秒" /></td>
			</tr>
			<tr>
				<td>操作人</td>
				<td>${userLogPd.CREATEUSERNAME }</td>
			</tr>
			<tr>
				<td>备注</td>
				<td>${userLogPd.REMARK }</td>
			</tr>
		</table>
	</div>
</body>
</html>