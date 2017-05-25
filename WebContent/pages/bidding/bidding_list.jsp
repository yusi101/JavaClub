<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>招标信息页面</title>
<link href="${pageContext.request.contextPath}/static/styles/brand/brand.css" rel="stylesheet" />
</head>
<body>
	<%@ include file="../system/allresources.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">招标信息查询</h4>
			<form action="${pageContext.request.contextPath}/biddingController/queryBiddingInfo" method="post" name="queryBrandInfo" id="queryBrandInfo" lass="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 100%; float: left; text-align: right;">
					招标名称：<input type="text" placeholder="请输入招标名称" name="entname" value="${pd.entname}" style="vertical-align: top;" maxlength="20px" /> 
					<button class="btn btn-search"  onclick="queryBrandinfo();" ><i class="fa fa-search"></i> 搜索</button>
				</div>
				<div class="clren"></div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="10%">序号</th>
						<th width="20%">企业名称</th>
						<th width="20%">招标标题</th>
						<th width="20%">招标时间</th>
						<th width="20%">行业</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<c:choose>
					<c:when test="${not empty bidding }">
						<tbody id="tbody">
							<c:forEach items="${bidding}" var="list" varStatus="status">
								<tr>
									<td>${status.index + 1}</td>
									<td>${list.ENTNAME}</td>
									<td>${list.TITLE}</td>
									<td>${list.RELEASE_DATE}</td>
									<td>${list.INDUSTRY}</td>
									<td class="dropdown">
										<button class="btn btn-detail" onclick="opendet('${list.ID}')"><i class="fa fa-navicon"></i>&nbsp;详情</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="8" style="color: red; text-align: center;">
								<div align="center">
									<img src="${pageContext.request.contextPath}/static/images/nodata.png" style="width: 100px; margin: 10px auto;">
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
							<div class="pagination" style="float: left; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/bidding.js"></script>
</body>
</html>