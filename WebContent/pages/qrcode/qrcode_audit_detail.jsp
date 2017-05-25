<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>牌照审核</title>
	<%@ include file="../system/allresources.jsp"%>
	<link href="${pageContext.request.contextPath}/static/styles/util/imgscale.css" rel="stylesheet" />
</head>

<body>
	<table class="table table-striped table-bordered table-hover">
		<tr>
			<td>企业名称：</td>
			<td>${qrMap.ENTNAME }</td>
			<td rowspan="6" colspan="2">
				<img src="data:image/jpg;base64,${qrMap.LICENSECODE }" onclick="imgScale(this)" style="width:200px; height:200px;" />
				<input type="hidden" id="code" value="${qrMap.LICENSECODE }" />
			</td>			
		</tr>
		<tr>
			<td>注册号：</td>
			<td>${qrMap.REGNORE }</td>
		</tr>
		<tr>
			<td>登记状态：</td>
			<td>${qrMap.REGSTATE_CN }</td>		
		</tr>
		<tr>
			<td>注册资金：</td>
			<td>${qrMap.REGCAP }万${qrMap.REGCAPCUR_CN }</td>		
		</tr>
		<tr>
			<td>法定代表人：</td>
			<td>${qrMap.NAME }</td>

		</tr>
		<tr>
			<td>成立日期：</td>
			<td><fmt:formatDate type="both" value="${qrMap.ESTDATE }" /></td>			
		</tr>
		<tr>
			<td>状态：</td>
			<td>
				<c:if test="${qrMap.STATUS == 0 }">打印未审核</c:if>
				<c:if test="${qrMap.STATUS == 1 }">打印审核已通过</c:if>
				<c:if test="${qrMap.STATUS == 2 }">打印审核未通过</c:if>
				<c:if test="${qrMap.STATUS == 3 }">牌照送去制作</c:if>
				<c:if test="${qrMap.STATUS == 4 }">牌照已入库</c:if>
				<c:if test="${qrMap.STATUS == 5 }">牌照制作入库审核不通过</c:if>
				<c:if test="${qrMap.STATUS == 6 }">牌照领取通知已发送</c:if>
				<c:if test="${qrMap.STATUS == 7 }">牌照已领取</c:if>
			</td>
			<td>申请数量：</td>
			<td>${qrMap.NUMBER }</td>
		</tr>
		<tr>
			<td colspan="4">
				<c:if test="${qrMap.STATUS == 0 }">
					<button class="btn btn-through" id="yessh" onclick="edtQRCodeAudit('${qrMap.ID }',1)">通过</button>
					&nbsp;&nbsp;&nbsp;
					<button class="btn btn-nothrough" id="nosh" onclick="edtQRCodeAudit('${qrMap.ID }', 2)">不通过</button>		
				</c:if>
			</td>
		</tr>
	</table>
	<script src="${pageContext.request.contextPath}/static/scripts/util/imgscale.js"></script>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcode.js"></script>	
</body>
</html>