<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>牌照异常处理</title>
	<%@ include file="../system/allresources.jsp"%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/uploadify-img/uploadify.img.css">
</head>

<body>
	<input type="hidden" id="pripid" value="${qrMap.PRIPID }" />
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
			<td><img src="data:image/jpg;base64,${qrMap.LICENSECODE }" style="width:30%;"></td>
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
			<td><fmt:formatDate type="both" value="${qrMap.APPROVE_TIME }" /></td>
		</tr>
		<tr>
			<td><font color="red">*</font>修复图片：</td>
			<td>
				<input type="hidden" id="pid" value="${qrMap.RELATION_ID }">
				<input type="file" name="uploadImg" id="uploadImg" multiple="true">
				<div id="uploadImgList"></div>
			</td>
		</tr>			
		<tr >
			<td><font color="red">*</font>修复描述：</td>
			<td>
				<textarea style="width:80%; height: 80px" placeholder="修复描述" id="remark"></textarea>
			</td>
		</tr>	
		<tr>
			<td colspan="2">
				 <div class="layui-layer-btn">
					<button class="btn btn-confirm " onclick="edtQRCodeAbnormal('${param.aid }')">确定</button>
					<a class="btn btn-cancel" onclick="parent.layer.closeAll('iframe')">关闭</a>
				</div>
			</td>		
		</tr>																															
	</table>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcode.js"></script>
	<script src="${pageContext.request.contextPath}/static/plugins/uploadify-img/jquery.uploadify.img.js"></script>
	<script src="${pageContext.request.contextPath}/static/scripts/util/upload_img.js"></script>
</body>
</html>