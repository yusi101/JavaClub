<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>专利信息页面</title>
	<link href="${pageContext.request.contextPath}/static/styles/patent/patent.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/static/styles/util/imgscale.css" rel="stylesheet" />
	<%@ include file="../system/allresources.jsp"%>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">专利查询</h4>
			<form action="${pageContext.request.contextPath}/patentController/queryPatentinfo" method="post" name="queryPatentInfo" id="queryPatentInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 100%; float: left; text-align: right;">
					企业名称：<input type="text" placeholder="请输入企业名称" name="entName" value="${pd.entName}" style="vertical-align: top;" maxlength="200" />
					专利名称：<input type="text" placeholder="请输入专利名称" name="patentName" value="${pd.patentName}" style="vertical-align: top;" maxlength="100" /> 
					<button class="btn btn-search"  onclick="queryPatentinfo();" ><i class="fa fa-search"></i> 搜索</button>
				</div>
				<div class="clren"></div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="10%">专利图标</th>
						<th width="15%">专利名称</th>
						<th width="20%">企业名称</th>
						<th width="10%">申请日期</th>
						<th width="20%">发明人</th>
						<th width="10%">专利类型</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<c:choose>
					<c:when test="${not empty patentinfo }">
						<tbody id="tbody">
							<c:forEach items="${patentinfo}" var="list" varStatus="status">
								<tr>
									<td>${status.index+1 }</td>
									<td><img src="${list.ABSTRACTGRAPH}" onclick="imgScale(this)" class="code_img" style="height: 50px; width: 50px;"></td>
									<td>${list.PATENTNAME}</td>
									<td>${list.ENTNAME}</td>
									<td>${list.RDATE}</td>
									<td>${list.INVENTOR}</td>
									<td>${list.PATENTTYPE}</td>
									<td class="dropdown"><a class="btn btn-detail" href="javascript:opendet('${list.ID}')"><i class="fa fa-navicon"></i>&nbsp;详情</a></td>
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
						<td style="vertical-align: top;"><div class="pagination" style="float: left; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/scripts/util/imgscale.js"></script>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/patent.js"></script>
</body>
</html>