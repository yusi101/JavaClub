<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>牌照异常</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
  	<%@ include file="../system/allresources.jsp" %>
</head>

<body>
	<table class="table table-striped table-bordered table-hover">
		<tr>
			<td width="30%">企业名称：</td>
			<td width="70%">${qrMap.ENTNAME }</td>
		</tr>	
		<tr>
			<td>牌照编码：</td>
			<td>${qrMap.LICENSENUMBER }</td>
		</tr>
		<tr >
			<td>牌照：</td>
			<td><img src="data:image/jpg;base64,${qrMap.ABNORMAL_CODE }" style="max-width:30%;"></td>
		</tr>
		<tr >
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
		</tr>		
		<tr >
			<td>审核人：</td>
			<td>${qrMap.APPROVEUSER_NAME }</td>			
		</tr>
		<tr >
			<td>审核时间：</td>
			<td>${qrMap.APPROVE_TIME }</td>
		</tr>
		<tr >
			<td>修复人：</td>
			<td>${qrMap.REPAIRUSER_NAME }</td>
		</tr>
		<tr >
			<td>修复时间：</td>
			<td><fmt:formatDate type="both" value="${qrMap.REPAIR_TIME }" /></td>
		</tr>		
		<tr >
			<td>修复描述：</td>
			<td><c:out value="${qrMap.REPAIR_REMARK }" ></c:out></td>
		</tr>	
		<tr>
			<td colspan="2">
				 <div class="layui-layer-btn">
					<a class="btn btn-cancel" onclick="parent.layer.closeAll('iframe')">关闭</a>
				</div>
			</td>		
		</tr>																															
	</table>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcode.js"></script>
</body>
</html>
