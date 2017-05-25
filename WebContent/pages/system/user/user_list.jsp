<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>用户管理</title>
	<%@ include file="../allresources.jsp"%>
</head>
<body>
	
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">用户管理</h4>
			<div style="width: 10%; float: left;">
				<a class="btn btn-add" onclick="openadd();"><i class="fa fa-plus"></i>&nbsp;新增</a>
			</div>
			<form action="${pageContext.request.contextPath}/userController/queryUserInfo" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 90%; float: left; text-align: right;">
					用户名/真实姓名：<input type="text" placeholder="请输入用户名/真实姓名" name="NAME" value="${pd.NAME}" style="vertical-align: top;" />
					<button class="btn btn-search" onclick="queryBrandinfo();"><i class="fa fa-search"></i>&nbsp;搜索</button>
				</div>
				<div class="clren"></div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="20%">用户名</th>
						<th width="15%">真实姓名</th>
						<th width="5%">性别</th>
						<th width="20%">邮件</th>
						<th width="15%">手机号码</th>
						<th width="20%">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty user_list }">
						<c:forEach items="${user_list }" var="list" varStatus="status">
							<tr>
								<td>${status.index+1 }</td>
								<td>${list.USERNAME}</td>
								<td>${list.NAME}</td>
								<td>
									<c:if test="${list.SEX == 1 }">男</c:if>
									<c:if test="${list.SEX == 0 }">女</c:if>
								</td>
								<td>${list.EMAIL}</td>
								<td>${list.PHONE}</td>
								<td>
									<button class="btn btn-edit" onclick="query('${list.USER_ID }')"><i class="fa fa-edit"></i>&nbsp;编辑</button>
									<button class="btn btn-del" onclick="deleteuser('${list.USER_ID }')"><i class='fa fa-trash'></i>&nbsp;删除</button>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty user_list }">
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/sysuser.js"></script>
</body>
</html>