<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>企业信息</title>
<%@ include file="../system/allresources.jsp"%>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">企业信息查询</h4>
			<form action="${pageContext.request.contextPath}/enteradditioncontroller/queryEnteraddition" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 100%; float: left; text-align: right;">
					<span id="searchDes">企业名称</span>：<input type="text" placeholder="请输入企业名称" id="entname" name="entname" maxlength="20" value="${pd.entname}" style="vertical-align: top;" />
					类型：<select placeholder="--请选择类型--" name="searchType" onchange="select()" id="selectType" style="width: 120px; text-align: center;">
						<option value='0' <c:if test="${pd.searchType == 0}">selected</c:if>>企业名称</option>
						<option value='1' <c:if test="${pd.searchType == 1}">selected</c:if>>法定代表人</option>
						<%-- <option value='2' <c:if test="${pd.searchType == 2}">selected</c:if>>品牌名称</option> --%>
						<option value='3' <c:if test="${pd.searchType == 3}">selected</c:if>>失信人</option>
					</select>
					<button class="btn btn-search" onclick="queryBrandinfo();"><i class="fa fa-search"></i> 搜索</button>
				</div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="20%">企业名称</th>
						<th width="10%">法定代表人</th>
						<th width="15%">登记机关</th>
						<th width="15%">企业类型</th>
						<th width="10%">登记状态</th>
						<th width="10%">登记时间</th>
						<th width="15%">操作</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:forEach items="${Enteraddition}" var="list" varStatus="status">
						<tr>
							<td>${status.index + 1 }</td>
							<td>${list.ENTNAME }</td>
							<td>${list.NAME }</td>
							<td>${list.REGORG_CN }</td>
							<td>${list.ENTTYPE_CN }</td>
							<td>${list.REGSTATE_CN }</td>
							<td>${list.ESTDATE }</td>
							<td class="dropdown">
								<a class="dropdown-toggle mymsg" data-toggle="dropdown" href="#">
									<img src="${pageContext.request.contextPath}/static/images/32/166.png" />
									&nbsp;&nbsp;更多
									<b class="caret"></b>
								</a>
								<ul class="dropdown-menu">
									<li>
										<a href="javascript:openAllView('${list.PRIPID }','${list.REGNO }','${list.ENTNAME }','${list.ENTTYPE }')">全景视图</a>
									</li>
									<li>
										<a href="javascript:openInvestView('${list.PRIPID }','${list.REGNO }','${list.ENTNAME }','${list.ENTTYPE }')">投资链图</a>
									</li>
									<li>
										<a href="javascript:openInvestAllView('${list.PRIPID }','${list.REGNO }','${list.ENTNAME }','${list.ENTTYPE }')">投资全景</a>
									</li>
									<li>
										<a href="javascript:openEntCourseInter('${list.PRIPID }','${list.REGNO }','${list.ENTNAME }','${list.ENTTYPE }')">企业历程</a>
									</li>
									<li>
										<a href="javascript:opencredit('${list.PRIPID }','${list.ENTNAME }')">生成信用报告</a>
									</li>
								</ul>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/enterprise.js"></script>
</body>
</html>