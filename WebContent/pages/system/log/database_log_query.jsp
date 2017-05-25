<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="../allresources.jsp"%>
<style type="text/css">
	.wrap { table-layout:fixed; word-break:break-all; overflow:hidden; }
</style>
<title>数据库操作详情</title>
</head>
<body>
	<div class="container-fluid">
			<h4 class="title">数据库操作详情</h4>
			<table class="table table-striped table-bordered table-hover">

				<tr>
					<td style="width: 50px;">IP地址</td>
					<td style="">${DatabaserLogPd.IP }</td>
				</tr>
				<tr>
					<td>操作类型</td>
					<td>${DatabaserLogPd.LOGTYPE }</td>
				</tr>
				<tr>
					<td>操作参数</td>
					<td>${DatabaserLogPd.PARAMETERINFO }</td>
				</tr>
				<tr>
					<td>sql语句</td>
					<td class="wrap"> ${DatabaserLogPd.SQLINFO }</td>
				</tr>
				<tr>
					<td>操作时间</td>
					<td><fmt:formatDate value="${DatabaserLogPd.CREATETIME}"
							pattern="yyyy年MM月dd日  HH时mm分ss秒" /></td>
				</tr>
				<tr>
					<td>操作人</td>
					<td>${DatabaserLogPd.CREATEUSERNAME }</td>
				</tr>
				<tr>
					<td>备注</td>
					<td>${userLogPd.REMARK }</td>
				</tr>
			</table>
	</div>
</body>
</html>