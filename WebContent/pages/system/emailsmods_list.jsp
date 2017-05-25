<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>邮件模板管理</title>
</head>
<body>
	<%@ include file="../system/allresources.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">邮件模板管理</h4>
			<div style="width: 10%; float: left;">
				<a class="btn btn-add" onclick="openadd();"><i class="fa fa-plus"></i>&nbsp;新增</a>
			</div>
			<form action="${pageContext.request.contextPath}/keywordController/querykeywordinfo" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<%-- <div style="width: 90%; float: left; text-align: right;">
					<input type="text" placeholder="请输入用户名称" name="NAME" value="${pd.NAME}" style="vertical-align: top;" />
					<button class="btn btn—search" onclick="queryBrandinfo();">搜索</button>
				</div>
				<div class="clren"></div> --%>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th class="center" width="5%">序号</th>
						<th class="center" width="10%">邮件标题</th>
						<th class="center" width="10%">邮件内容</th>
						<th class="center" width="20%">保存时间</th>
						<th class="center" width="10%">状态</th>
						<th class="center" width="15%">记录人</th>
						<th class="center" width="20%">操作</th>
					</tr>
				</thead>
				<tbody>
					 <c:if test="${not empty emailsModslist }">
						<c:forEach items="${emailsModslist }" var="list" varStatus="status">
							<tr>
								<td class="center" width="5%">${status.index+1 }</td>
								<td class="center" width="15%">${list.EMAIL_TITLE}</td>
								<td class="center" width="20%">${list.EMAIL_CTX}</td>
								<td class="center" width="10%"><fmt:formatDate type="both" value="${list.CURRENT_TIMESTAMP}" /></td>
								<td class="center" width="5%">
									<c:if test="${list.ACTIVE == 0 }"><span style="color: red">未激活</span></c:if>
									<c:if test="${list.ACTIVE == 1 }">已激活</c:if>
									
								</td>
								<td class="center" width="5%">${list.USER_ID}</td>
								<td class="dropdown" width="20%">
									<button class="btn btn-edit" onclick="query('${list.ID }')"><i class="fa fa-edit"></i>&nbsp;编辑</button>
									<button class="btn btn-del" onclick="deleteEmailsMods('${list.ID }')"><i class='fa fa-trash'></i>&nbsp;删除</button>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty emailsModslist }">
						<tr>
							<td colspan="9" style="color: red; text-align: center;">
								<div align="center">
									<img src="${pageContext.request.contextPath}/static/images/nodata.png" style="width: 100px; margin: 10px auto;">
								</div>
								<div style="margin-bottom: 10px;">小查没有查到数据哦~</div>
							</td>
						</tr>
					</c:if> 
				</tbody>
			</table>
			<div class="position-relative">
				<table style="width: 100%;">
					<tr>
						<td style="vertical-align: top;">
							<div class="pagination" style="float: left; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/sysemailsmods.js"></script>
</body>
</html>