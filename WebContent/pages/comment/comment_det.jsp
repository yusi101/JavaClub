<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>评论审核</title>
	<%@ include file="../system/allresources.jsp"%>
</head>
<body> 
<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div style="margin: 0 auto;">
				<div class="form-horizontal" style="margin: 10px 0;">
					<div style="text-align: right;">
						<div>
							<span class="tabletitle">评论详情</span>
							<c:forEach items="${commentInfo}" var="list" varStatus="status">
								<table id="table_bug_report" class="table table-striped table-bordered table-hover">
									<tr>
										<td style="text-align:right;width:20%;" >评论企业</td>
										<td colspan="3">${list.ENTNAME}</td>
									</tr>
									<tr>
										<td style="text-align:right;width:20%;" >评论内容</td>
										<td colspan="3">${list.REMARK}</td>
									</tr>
									<tr>
										<td style="text-align:right;width:20%;">评论人</td>
										<c:if test="${empty list.ALIASNAME}">
											<td>${list.USERNAME}</td>
										</c:if>
										<c:if test="${not empty list.ALIASNAME}">
											<td>${list.ALIASNAME}</td>
										</c:if>
										<td style="text-align:right;width:20%;">审核人</td>
										<td >${list.NAME}</td>
									</tr>
									<tr>
										<td style="text-align:right;width:20%;">评论时间</td>
										<td >${list.COMMENTTIME}</td>
										<td style="text-align:right;width:20%;">审核时间</td>
										<td >${list.AUDITTIME}</td>
									</tr>
									<tr>
										<td style="text-align:right;width:20%;">审核通过/不通过理由</td>
										<td colspan="3">${list.RESULTREMARK}</td>
									</tr>
								</table>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 编辑框-->
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/comment.js"></script>
</body>
</html>