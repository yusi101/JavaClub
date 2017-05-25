<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>牌照申请记录</title>
	<%@ include file="../system/allresources.jsp"%>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">牌照在线申请</h4>
			<div style="width: 40%; float: left; margin-bottom: 10px">
				<button class="btn btn-add" onclick="openadd()">申请牌照</button>
			</div>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="20%">申请企业</th>
						<th width="10%">申请方式</th>
						<th width="10%">申请类型</th>
						<th width="10%">申请人姓名</th>
						<th width="10%">申请数量</th>
						<th width="15%">申请时间</th>
						<th width="10%">申请状态</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${!empty qrList }">
						<c:forEach items="${qrList }" var="list" varStatus="status">
							<tr>
								<td>${status.index + 1 }</td>
								<td>${list.ENTNAME }</td>
								<td>${list.APPLYWAY_CN }</td>
								<td>${list.APPLYTYPE_CN }</td>
								<td>${list.APPLY_NAME }</td>
								<td>${list.APPLY_NUMBER }</td>
								<td><fmt:formatDate type="both" value="${list.CREATETIME }" /></td>
								<td>
									<c:if test="${list.STATUS == 0 }">
										申请未处理
									</c:if>
									<c:if test="${list.STATUS == 1 }">
										<c:if test="${list.STATUS == 1 && list.QPSTATUS == null }">申请审核通过</c:if>
										<c:if test="${list.QPSTATUS == 0 }">打印未审核</c:if>
										<c:if test="${list.QPSTATUS == 1 }">打印审核已通过</c:if>
										<c:if test="${list.QPSTATUS == 2 }">打印审核未通过</c:if>
										<c:if test="${list.QPSTATUS == 3 }">牌照送去制作</c:if>
										<c:if test="${list.QPSTATUS == 4 }">牌照已入库</c:if>
										<c:if test="${list.QPSTATUS == 5 }">牌照制作入库审核不通过</c:if>
										<c:if test="${list.QPSTATUS == 6 }">牌照领取通知已发送</c:if>
										<c:if test="${list.QPSTATUS == 7 }">牌照已领取</c:if>
									</c:if>
									<c:if test="${list.STATUS == 2 }">
										申请审核不通过
									</c:if>
								</td>
								<td>
									<button class="btn btn-detail" onclick="openApplyDet('${list.ID }','${list.PID }','${list.PRIPID }')"><i class="fa fa-navicon"></i>&nbsp;详情</button>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty qrList }">
						<tr>
							<td colspan="9" style="color: red; text-align: center;">
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
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/apply.js"></script>
</body>
</html>