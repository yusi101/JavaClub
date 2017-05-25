<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>失信被执行人页面</title>
<link href="${pageContext.request.contextPath}/static/styles/brand/brand.css" rel="stylesheet" />
</head>
<body>
	<%@ include file="../system/allresources.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">失信被执行人查询</h4>
			<form action="${pageContext.request.contextPath}/courtcaseController/queryCourtinfo" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 100%; float: left; text-align: right;">
					失信被执行人：<input type="text" placeholder="请输入失信被执行人" name="iname" value="${pd.iname}" style="vertical-align: top;" maxlength="20px" />
					<button class="btn btn-search"  onclick="queryBrandinfo();" ><i class="fa fa-search"></i>&nbsp;搜索</button>
				</div>
				<div class="clren"></div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="10%">被执行人姓名/名称</th>
						<th width="15%">案号</th>
						<th width="15%">身份证号码 / 组织机构代码</th>
						<th width="10%">代表人</th>
						<th width="15%">执行法院</th>
						<th width="15%">发布时间</th>
						<th width="5%">类型</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:forEach items="${court}" var="list" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>${list.INAME}</td>
							<td>${list.CASE_CODE}</td>
							<td>${list.CARDNUM}</td>
							<td>${list.BUESINESSENTITY}</td>
							<td>${list.COURT_NAME }</td>
							<td>${list.PUBLISH_DATE}</td>
							<td>
								<c:if test="${list.CARDTYPE == 1}">有效</c:if>
								<c:if test="${list.CARDTYPE == 0}">无效</c:if>
							</td>
							<td class="dropdown">
							<button class="btn btn-detail" onclick="opendet('${list.COURTCASEID}')"><i class="fa fa-navicon"></i>&nbsp;详情</button>
							</td>
						</tr>
					</c:forEach>
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
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/courtcase.js"></script>
</body>
</html>
