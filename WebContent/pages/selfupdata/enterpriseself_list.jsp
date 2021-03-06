﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>企业自主更新</title>
	<%@ include file="../system/allresources.jsp"%>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">企业自主更新</h4>
			<form action="${pageContext.request.contextPath}/selfUpdataController/querySelfUpdata" method="post" name="selfUpdataInfo" id="selfUpdataInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 100%; float: left; text-align: right;">
					<span id="searchDes">企业名称</span>：<input type="text" placeholder="请输入企业名称" id="entname" name="entname" maxlength="20" value="${pd.entname}" style="vertical-align: top;" />
					类型：<select placeholder="--请选择类型--" name="searchType" onchange="select(this)" id="selectType" style="width: 120px; text-align: center;">
						<option value='0' <c:if test="${pd.searchType == 0}">selected</c:if>>企业名称</option>
						<option value='1' <c:if test="${pd.searchType ==1}">selected</c:if>>法定代表人</option>
					</select>
					<button class="btn btn-search"  onclick="querySelfUpdataInfo();" ><i class="fa fa-search"></i> 搜索</button>
				</div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="20%">企业名称</th>
						<th width="15%">企业类型</th>
						<th width="15%">登记机关</th>
						<th width="7%">登记状态</th>
						<th width="13%">法定代表人/负责人</th>
						<th width="15%">注册资本</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:choose>
						<c:when test="${not empty Enteraddition }">
							<c:forEach items="${Enteraddition}" var="list" varStatus="status">
								<tr>
									<td>${status.index+1 }</td>
									<td>${list.ENTNAME }</td>
									<td>${list.ENTTYPE_CN }</td>
									<td>${list.REGORG_CN }</td>
									<td>${list.REGSTATE_CN }</td>
									<td>${list.NAME }</td>
									<c:if test="${fn:contains(list.ENTTYPE_CN,'分公司')}">
										<td>无</td>
									</c:if>
									<c:if test="${!fn:contains(list.ENTTYPE_CN,'分公司')}">
										<td>${list.REGCAP }万${list.REGCAPCUR_CN }</td>
									</c:if>
									<td class="dropdown">
										<a class="btn btn-edit" href="javascript:selfupdata('${list.PRIPID }','${list.ENTNAME }')">自主更新</a>
									</td>
								</tr>
							</c:forEach>
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
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/selfupdata.js"></script>
</body>
</html>