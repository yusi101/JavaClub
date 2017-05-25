<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>牌照批次管理</title>
	<%@ include file="../system/allresources.jsp"%>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">牌照批次管理</h4>
			<form action="${pageContext.request.contextPath}/qrCodeController/queryBarchNumberlistPage" method="post" name="queryEntnameInfo" id="queryEntnameInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 100%; float: left; text-align: right;">
					批次号：<input type="text" placeholder="请输入批次号" name="barchNumber" id="barchNumber" value="${pd.barchNumber }" maxlength="30" style="width: 200px;"/>
					<button type="button" class="btn btn-search" onclick="mySearch();" ><i class="fa fa-search"></i> 搜索</button>
				</div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="15%">序号</th>
						<th width="20%">批次号</th>
						<th width="20%">批次处理人</th>
						<th width="20%">处理时间</th>
						<th width="15%">牌照数量</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${!empty bnList }">
						<c:forEach items="${bnList }" var="list" varStatus="status">
							<tr>
								<td>${status.index + 1 }</td>
								<td>${list.AREACODE }${list.BATCHNUMBER }</td>
								<td>${list.APPROVEUSER_NAME }</td>
								<td>
									<fmt:parseDate value="${list.APPROVE_TIME }" var="APPROVETIME"></fmt:parseDate>
									<fmt:formatDate value="${list.APPROVE_TIME }" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>${list.NUM }</td>
								<td>
									<button class="btn btn-add" onclick="uploadBatchNumber('${list.AREACODE }${list.BATCHNUMBER }')">下载</button>
								</td>
							</tr>
						</c:forEach>	
					</c:if>
					<c:if test="${empty bnList }">
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
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcode.js"></script>	
</body>
</html>