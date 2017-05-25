<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>牌照领取通知</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
  	<%@ include file="../system/allresources.jsp" %>
  	<link href="${pageContext.request.contextPath}/static/styles/util/imgscale.css" rel="stylesheet" />
</head>

<body>
	<table class="table table-striped table-bordered table-hover">
	 	<input type="hidden" id="applyWayCN" placeholder="申请方式" value="柜台申请" maxlength="128" readonly="true">
		<tr>
			<td>牌照申请企业名称：</td>
			<td colspan="3">${applyList[0].ENTNAME }</td>
		</tr>
		<tr>
			<td>牌照申请数量：</td>
			<td colspan="3">${applyList[0].APPLY_NUMBER }</td>
		</tr>	
		<tr >
			<td>牌照申请人姓名：</td>
			<td><c:out value="${applyList[0].APPLY_NAME }" ></c:out></td>
			<td>牌照申请人职位：</td>
			<td><c:out value="${applyList[0].POSITION }" ></c:out></td>
		</tr>
		<tr>
			<td>牌照申请人联系邮箱：</td>
			<td>${applyList[0].EMAIL }</td>
			<td>牌照申请人联系号码：</td>
			<td>${applyList[0].TEL }</td>
		</tr>
		<tr >
			<td>牌照申请人联系地址：</td>
			<td colspan="3"><c:out value="${applyList[0].ADDRESS }" ></c:out></td>
		</tr>
		<tr>
			<td>牌照申请人备注：</td>
			<td colspan="3"><c:out value="${applyList[0].REMARK }" ></c:out></td>			
		</tr>
		<tr>
			<td>牌照申请人时间：</td>
			<td colspan="3"><fmt:formatDate type="both" value="${applyList[0].CREATETIME }" /></td>		
		</tr>
		<tr>
			<td>牌照状态：</td>
			<td colspan="3">
				<c:if test="${applyList[0].STATUS == 0 }">
					申请未处理
				</c:if>
				<c:if test="${applyList[0].STATUS == 1 }">
					<c:if test="${printList[0].STATUS == 0 }">打印未审核</c:if>
					<c:if test="${printList[0].STATUS == 1 }">打印审核已通过</c:if>
					<c:if test="${printList[0].STATUS == 2 }">打印审核未通过</c:if>
					<c:if test="${printList[0].STATUS == 3 }">牌照送去制作</c:if>
					<c:if test="${printList[0].STATUS == 4 }">牌照已入库</c:if>
					<c:if test="${printList[0].STATUS == 5 }">牌照制作入库审核不通过</c:if>
					<c:if test="${printList[0].STATUS == 6 }">牌照领取通知已发送</c:if>
					<c:if test="${printList[0].STATUS == 7 }">牌照已领取</c:if>
				</c:if>
				<c:if test="${applyList[0].STATUS == 2 }">
					申请审核不通过
				</c:if>
			</td>		
		</tr>
		<c:if test="${applyList[0].STATUS == 1 }">
			<tr>
				<td>牌照编码：</td>
				<td colspan="3">${licenseRecordList[0].LICENSENUMBER }</td>		
			</tr>	
			<tr>
				<td>牌照：</td>
				<td colspan="3">
					<img src="data:image/jpg;base64,${printList[0].LICENSECODE }" onclick="imgScale(this)" width="100px" height="160px">
				</td>		
			</tr>
		</c:if>
		<c:if test="${!empty noticeList }">	
			<c:forEach items="${noticeList }" var="noticeList" varStatus="status">
				<tr >
					<td>第${status.index + 1 }次牌照领取通知处理人：</td>
					<td>${noticeList.SENDUSER_NAME }</td>
					<td>第${status.index + 1 }次牌照领取通知处理时间：</td>
					<td><fmt:formatDate type="both" value="${noticeList.SEND_TIME }" /></td>			
				</tr>
			</c:forEach>
		</c:if>																
		<tr>
			<td colspan="4">
				 <div class="layui-layer-btn">
					<a class="btn btn-cancel" style="background:#fff" onclick="parent.layer.closeAll('iframe')">关闭</a>
				</div>
			</td>		
		</tr>																															
	</table>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcode.js"></script>
	<script src="${pageContext.request.contextPath}/static/scripts/util/imgscale.js"></script>
</body>
</html>
