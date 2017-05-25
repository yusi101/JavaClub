<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>数据字典</title>
	<meta charset="utf-8" />
	<meta name="description" content="overview & stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link href="${pageContext.request.contextPath}/static/styles/brand/brand.css" rel="stylesheet" />
	<%@ include file="../system/allresources.jsp"%>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">字典管理</h4>
				<div style="width: 10%; float: left;">
					<a class="btn btn-success" onclick="openedt('${pd.PARENT_ID }');" style="margin-right: 25px"><i class="fa fa-plus"></i>&nbsp;   新增</a>
				</div>
				<div style="width: 60%; float: left; text-align: right;">
					<table>
							<tr>
								<c:if test="${pd.PARENT_ID != '0'}">
									<c:choose>
										<c:when test="${not empty varsList}">
											<td style="vertical-align: top;">
												<a href="${pageContext.request.contextPath}/dictionariesController/queryDictionariesInfo?PARENT_ID=0" class="btn  btn-purple" title="查看">
													顶级
													<i class="fa fa-hand-o-right"></i>
												</a>
											</td>
											<c:forEach items="${varsList}" var="var" varStatus="vsd">
												<td style="vertical-align: top;">
													<a href="${pageContext.request.contextPath}/dictionariesController/queryDictionariesInfo?PARENT_ID=${var.ZD_ID }" class="btn  btn-purple" title="查看">${var.NAME }<i class="fa fa-hand-o-right"></i></i>
													</a>
												</td>
											</c:forEach>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
								</c:if>
		
							</tr>
					</table>
				</div>
				<div style="clear: both;"></div>
			<!-- 检索  -->
			<form action="${pageContext.request.contextPath}/dictionariesController/queryDictionariesInfo" method="post" name="userForm" id="userForm" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<input name="PARENT_ID" id="PARENT_ID" type="hidden" value="${pd.PARENT_ID }" />
				<table class="table table-condensed table-bordered table-hover tab">
					<thead>
						<tr style="height: 10px">
							<th class="center" style="width: 5%;">序号</th>
							<th class='center' style="width: 35%;">名称</th>
							<th class='center' style="width: 20%;">编码</th>
							<th class='center' style="width: 20%;">级别</th>
							<th class='center' style="width: 20%;">操作</th>
						</tr>
					</thead>
					<c:choose>
						<c:when test="${not empty varList}">
							<c:forEach items="${varList}" var="var" varStatus="vs">
								<tr>
									<td class="center">${vs.index + 1  }</td>
									<td class='center'>
										<a href="${pageContext.request.contextPath}/dictionariesController/queryDictionariesInfo?PARENT_ID=${var.ZD_ID }" title="查看下级">
											${var.NAME }&nbsp;<i class="fa fa-hand-o-right"></i>
										</a>
									</td>
									<td class='center'>${var.BIANMA }</td>
									<td class='center' style="width: 35px;">
										<b class="green">${var.JB }</b>
									</td>
									<td style="width: 68px;">
										<a class='btn  btn-info' title="编辑" onclick="javascript:opens('${var.ZD_ID }')">
											<i class="fa fa-edit"></i>&nbsp; 编辑
										</a>
										<a class='btn  btn-danger' title="删除" onclick="del('${var.ZD_ID }')">
											<i class='fa fa-trash'></i>&nbsp; 删除
										</a>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="100" class="center">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
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
			</form>

		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/dictionaries.js"></script>
</body>
</html>

<script>
	
</script>
