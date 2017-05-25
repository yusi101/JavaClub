<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>角色管理</title>
	<%@ include file="../system/allresources.jsp"%>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">角色管理</h4>
			<div style="margin: 10px 0;height: 30px">
				<div style="width: 60%; float: left;">
					<a class="btn btn-add" onclick="openadd();"><i class="fa fa-plus"></i>&nbsp;新增</a>
				</div>
				<form action="${pageContext.request.contextPath}/roleController/queryRolelistPage" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<%-- <div style="width: 90%; float: left; text-align: right;">
					<input type="text" placeholder="请输入用户名称" name="NAME" value="${pd.NAME}" style="vertical-align: top;" />
					<button class="btn btn—search" onclick="queryBrandinfo();">搜索</button>
				</div>
				<div class="clren"></div> --%>
			</form>
			</div>
			<table id="table_bug_report" class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th class="center" width="5%">序号</th>
						<th class="center" width="15%">角色名称</th>
						<th class="center" width="30%">所属权限</th>
						<th class="center" width="15%">创建人</th>
						<th class="center" width="10%">创建时间</th>
						<th class="center" width="15%">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty roleList }">
							<c:forEach items="${roleList }" var="list" varStatus="status">
								<tr>
									<td class="center" width="5%">${status.index+1 }</td>
									<td class="center" width="15%">${list.ROLE_NAME}</td>
									<td class="center" width="30%">${list.RIGHTS}</td>
									<td class="center" width="15%">${list.CREATEUSER_NAME}</td>
									<td class="center" width="10%">${list.TIME}</td>
									<td class="dropdown" width="15%">
										<button class="btn btn-edit" onclick="openupd('${list.ROLE_ID }')"><i class="fa fa-edit"></i>&nbsp;编辑</button>
										<button class="btn btn-del" onclick="del('${list.ROLE_ID }')"><i class='fa fa-trash'></i>&nbsp;删除</button>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<tr>
								<td colspan="7" style="color: red; text-align: center;">
									<div align="center">
										<img src="${pageContext.request.contextPath}/static/images/nodata.png" style="width: 100px; margin: 10px auto;">
									</div>
									<div style="margin-bottom: 10px;">小查没有查到数据哦~</div>
								</td>
							</tr>
							</tr>
						</c:otherwise>
					</c:choose>

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
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/role.js"></script>
</head>
</body>
</html>