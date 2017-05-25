<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>企业投诉</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
  	<%@ include file="../system/allresources.jsp" %>
</head>

<body>
	<table class="table table-striped table-bordered table-hover dialog">
		<input type="hidden" id="memberId" value="${complainMap.MEMBER_ID }"/>
		<tr>
			<td width="20%">企业名称：</td>
			<td width="80%">${complainMap.ENTNAME }</td>
		</tr>	
		<tr>
			<td>投诉人：</td>
			<td>${complainMap.USERNAME }</td>
		</tr>
		<tr >
			<td>投诉标题：</td>
			<td>${complainMap.TITLE }</td>
		</tr>		
		<tr>
			<td>投诉类型：</td>
			<c:if test="${complainMap.TYPE_NAME==null or complainMap.TYPE_NAME=='' }">
				<td>其他</td>
			</c:if>
			<c:if test="${complainMap.TYPE_NAME!=null and complainMap.TYPE_NAME!='' }">
				<td>${complainMap.TYPE_NAME }</td>
			</c:if>
		</tr>
		<c:if test="${complainMap.TYPE_NAME==null or complainMap.TYPE_NAME=='' }">
		<tr>
			<td>投诉内容：</td>
			<td>${complainMap.REMARK }</td>			
		</tr>
		</c:if>
		<tr >
			<td>投诉时间：</td>
			<td>${complainMap.CREATETIME }</td>
		</tr>
		<tr >
			<td>附件材料：</td>
			<td>
				<c:forEach items="${attachmentList }" var="list">
					<img style="max-height: 200px;max-width: 200px;" src="data:image/jpg;base64,${list.ATTACHMENT_PATH }">
				</c:forEach>
			</td>
		</tr>		
		<tr >
			<td><font color="red">*</font>审核描述：</td>
			<td>
				<textarea style="width:80%;min-height: 80px" placeholder="请输入你的审核描述..."
				id="remark" maxlength="300" oninput="this.style.height='0px';this.style.height=(this.scrollHeight+'px');"></textarea>
			</td>
		</tr>	
		<tr>
			<td colspan="2">
				<button class="btn btn-through" onclick="edtComplain('${complainMap.ID }','1')">通过</button>
				<button class="btn btn-nothrough" onclick="edtComplain('${complainMap.ID }','2')">不通过</button>
			</td>		
		</tr>																															
	</table>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/complain.js"></script>
</body>
</html>
