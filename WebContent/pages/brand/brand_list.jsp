<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>商标信息页面</title>
	<%@ include file="../system/allresources.jsp"%>
	<link href="${pageContext.request.contextPath}/static/styles/util/imgscale.css" rel="stylesheet" />
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">商标信息</h4>
			<form
				action="${pageContext.request.contextPath}/brandController/queryBrandInfo"
				method="post" name="queryBrandInfo" id="queryBrandInfo"
				class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 100%; float: right; text-align: right;">
					商标名称：<input type="text" placeholder="请输入商标名称" name="brandName" value="${pd.brandName}" style="vertical-align: top;" /> 
					类型：<select id="form-field-select-3" placeholder="--请选择类别--" style="width: 150px;" name="classifyId">
						<option value=""></option>
						<c:forEach items="${brandClass}" var="brandClass" varStatus="status">
							<option value='${brandClass.ID}' <c:if test="${pd.classifyId ==brandClass.ID }">selected</c:if>>${brandClass.CLASSNAME}</option>
						</c:forEach>
					</select>
					<button class="btn btn-search" onclick="queryBrandinfo();" ><i class="fa fa-search"></i>搜索</button>
				</div>
				<div class="clren"></div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="10%">商标图标</th>
						<th width="15%">商标名称</th>
						<th width="15%">申请日期</th>
						<th width="10%">申请人</th>
						<th width="15%">商标状态</th>
						<th width="10%">使用期限</th>
						<th width="10%">商标分类名称</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<c:choose>
					<c:when test="${not empty brandInfo }">
						<tbody id="tbody">
							<c:forEach items="${brandInfo}" var="list" varStatus="status">
								<tr>
									<td>${status.index+1 }</td>
									<td>
										<img src="${list.BRANDIMG}" onclick="imgScale(this)" class="code_img" style="height: 50px; width: 50px;">
									</td>
									<td>${list.BRANDNAME}</td>
									<td>${list.APPLICATIONDATE}</td>
									<td>${list.APPLICANT}</td>
									<td>${list.BRANDSTAUTS}</td>
									<td>${list.LIFESPAN}</td>
									<td>${list.CLASSNAME}</td>
									<td>
										<button class="btn btn-detail" onclick="detail('${list.ID}');"><i class="fa fa-navicon"></i>&nbsp;详情</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="8" style="color: red; text-align: center;">
								<div align="center">
									<img
										src="${pageContext.request.contextPath}/static/images/nodata.png"
										style="width: 100px; margin: 10px auto;">
								</div>
								<div style="margin-bottom: 10px;">小查没有查到数据哦~</div>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
			<div class="position-relative">
				<table style="width: 100%;">
					<tr>
						<td style="vertical-align: top;">
						<div class="pagination" style="float: left; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/brand.js"></script>
	<script src="${pageContext.request.contextPath}/static/scripts/util/imgscale.js"></script>
	<script type="text/javascript">
		function reload() {
			parent.location.reload()
		}
	</script>
</body>
</html>