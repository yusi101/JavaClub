<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>回执单打印</title>
	<%@ include file="../system/allresources.jsp"%>
</head>

<body style="background: #FFF">
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">回执单打印</h4>
			<form action="${pageContext.request.contextPath}/qrCodePrintController/printReceipts" method="post" name="queryEntnameInfo" id="queryEntnameInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 100%; float: left; text-align: right;">
					
					<c:if test="${pd.searchType !=1}">
					<span id="searchDes">企业名称</span>：<input type="text" placeholder="请输入企业名称" id="entname" name="entname" maxlength="30" value="${pd.entname}" style="vertical-align: top;" />
					</c:if>
					
					
					<c:if test="${pd.searchType == 1}">
					<span id="searchDes">法定代表人</span>：<input type="text" placeholder="请输入法定代表人" id="entname" name="entname" maxlength="30" value="${pd.entname}" style="vertical-align: top;" />
					</c:if>
					类型：<select placeholder="--请选择类型--" name="searchType" onchange="select(this)" id="selectType" style="width: 120px; text-align: center;">
						<option value='0' <c:if test="${pd.searchType == 0}">selected</c:if>>企业</option>
						<option value='1' <c:if test="${pd.searchType ==1}">selected</c:if>>法定代表人</option>
					</select>
					<button type="button" class="btn btn-search" onclick="mySearch();" ><i class="fa fa-search"></i> 搜索</button>
				</div>
			</form>
			<!-- 导入Excel -->
			<form id="uploadForm" method="post" enctype="multipart/form-data">  
				<input type="file" id="upload" name="upload" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="preview()" style="display:none;"/>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="20%">企业名称</th>
						<th width="10%">申请人</th>
						<th width="10%">申请人职位</th>
						<th width="15%">申请方式</th>
						<th width="15%">申请类型</th>
						<th width="10%">申请时间</th>
						<th width="15%">操作</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:if test="${!empty qrList }">
						<c:forEach items="${qrList }" var="list" varStatus="status">
							<tr>
								<td>${status.index + 1 }</td>
								<td>${list.ENTNAME }</td>
								<td><c:out value="${list.APPLY_NAME }" ></c:out></td>
								<td><c:out value="${list.POSITION }" ></c:out></td>
								<td>${list.APPLYWAY_CN }</td>
								<td>${list.APPLYTYPE_CN }</td>
								<td><fmt:formatDate type="both" value="${list.CREATETIME }" /></td>
								<td>
									<button class="btn btn-detail" onclick="printBusinessLicense('${list.PRIPID }')">打印回执单</button>							
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty qrList }">
						<tr>
							<td colspan="8" style="color: red; text-align: center;">
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
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcodeprint.js"></script>
</body>
</html>