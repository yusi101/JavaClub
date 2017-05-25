<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>专利信息详情页面</title>
	<link href="${pageContext.request.contextPath}/static/styles/patent/patent.css" rel="stylesheet" />
</head>
<body>
   <%@ include file="../system/allresources.jsp" %>
	<div class="container-fluid">
		<table class="table table-condensed table-bordered table-hover tab" style="text-align:  center;">	
			<c:forEach items="${patentinfo}" var="list" varStatus="status">
				<tr>
					<td colspan="2">
						<img alt="" src="${list.ABSTRACTGRAPH}"><br>
						${list.PATENTNAME}
					</td>
				</tr>
				<tr>
					<td width="10%">发明人</td>
					<td>${list.INVENTOR}</td>
				</tr>
				<tr>
					<td width="10%">申请日期</td>
					<td>${list.RDATE}</td>
				</tr>
				<tr>
					<td width="10%">专利类型</td>
					<td>${list.PATENTTYPE}</td>
				</tr>
				<tr>
					<td width="10%">企业名称</td>
					<td>${list.ENTNAME}</td>
				</tr>
				<tr>
					<td width="10%">专利详情</td>
					<td>${list.DETAILINFO}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/patent.js"></script>
</body>
</html>