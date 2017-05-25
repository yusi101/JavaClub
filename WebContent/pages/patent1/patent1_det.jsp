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
		<table class="table table-condensed table-bordered table-hover tab  dialog" style="text-align:  center;">	
			<c:forEach items="${patent_list}" var="list" varStatus="status">
				<tr>
					<td  width="20%" >名称</td>
                    <td  width="20%" colspan="3">${list.PATENT_NAME}</td>
				</tr>
				<tr>
					<td width="20%">申请（专利）号</td>
					<td>${list.RCODE }</td>
                    <td width="20%">申请日</td>
					<td>${list.RDATE }</td>
				</tr>
				<tr>
					<td width="20%">授权（公告）号</td>
					<td>${list.ACODE}</td>
                    <td width="20%">授权（公告）日</td>
					<td>${list.ADATE}</td>
				</tr>
				<tr>
					<td width="20%">主分类号:</td>
					<td>${list.MAINIPCCODE}</td>
                    <td width="20%">范畴分类</td>
					<td>${list.CATEGORY}</td>
				</tr>
				<tr>
					<td width="20%">分类号</td>
					<td  colspan="3">${list.IPCCODE}</td>
				</tr>
				<tr>
					<td width="20%">优先权</td>
					<td colspan="3">${list.PRIORITY}</td>
				</tr>
                <tr>
					<td width="20%">申请（专利权）人</td>
					<td  colspan="3">${list.APPLICANT}</td>
				</tr>
				<tr>
					<td width="20%">地址</td>
					<td colspan="3">${list.ADDRESS}</td>
				</tr>
                <tr>
					<td width="20%">国省代码</td>
					<td  colspan="3">${list.ENTNAME}</td>
				</tr>
				<tr>
					<td width="20%">发明（设计）人</td>
					<td colspan="3">${list.INVENTOR}</td>
				</tr>
                <tr>
					<td width="20%">国际申请</td>
					<td  colspan="3">${list.IRCODE}</td>
				</tr>
				<tr>
					<td width="20%">国际公布</td>
					<td colspan="3">${list.IACODE}</td>
				</tr>
                <tr>
					<td width="20%">进入国家日期</td>
					<td  colspan="3">${list.IIRDATE}</td>
				</tr>
				<tr>
					<td width="20%">专利代理机构</td>
					<td colspan="3">${list.AGENCY}</td>
				</tr>
                <tr>
					<td width="20%">代理人</td>
					<td  colspan="3">${list.AGENT}</td>
				</tr>
				<tr>
					<td width="20%">分案申请号</td>
					<td colspan="3">${list.DRCODE}</td>
				</tr>
                <tr>
					<td width="20%">颁证日</td>
					<td colspan="3">${list.TDAY}</td>
				</tr>
                <tr>
					<td width="20%">摘要</td>
					<td colspan="3">${list.ABSTRACT}</td>
				</tr>
                <tr>
					<td width="20%">图形</td>
					<td colspan="3"></td>
				</tr>
                <tr>
					<td width="20%">主权项</td>
					<td colspan="3">${list.PRINCIPALCLAIM}</td>
				</tr>
				<c:if test="${not empty patent_law}">
                <tr>
					<td width="20%">法律状态</td>
					<td colspan="3">${patent_law.get(0).LEGALSTATUS}</td>
				</tr>
                <tr>
					<td width="20%">法律状态信息</td>
					<td colspan="3">${patent_law.get(0).LEGALSTATUSTRMARK}</td>
				</tr>
                <tr>
					<td width="20%">法律状态公告日</td>
					<td colspan="3">${patent_law.get(0).LEGALSTATUSDATA}</td>
				</tr>
				</c:if>
			</c:forEach>
		</table>
	</div>
	
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/patent.js"></script>
</body>
</html>