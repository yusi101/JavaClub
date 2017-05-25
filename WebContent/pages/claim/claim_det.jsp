<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>认领企业</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
  	<%@ include file="../system/allresources.jsp" %>
</head>

<body>
	<table id="tabs" class="table table-striped table-bordered table-hover">
		<tr>
			<td width="30%">企业名称：</td>
			<td width="70%">${claimMap.ENTNAME }</td>
		</tr>	
		<tr>
			<td>认领人：</td>
			<td>${claimMap.USERNAME }</td>
		</tr>
		<tr>
			<td>联系邮箱：</td>
			<td>${claimMap.EMAIL }</td>
		</tr>
		<tr>
			<td>联系电话：</td>
			<td>${claimMap.TEL }</td>
		</tr>		
		<tr>
			<td>认领描述：</td>
			<td>${claimMap.REMARK }</td>			
		</tr>
		<tr>
			<td>认领时间：</td>
			<td>${claimMap.CREATETIME }</td>
		</tr>	
		<tr>
			<td>审核人名称：</td>
			<td>${claimMap.CREATEUSER_NAME }</td>
		</tr>	
		<tr>
			<td>审核状态：</td>
			<td>
				<c:if test="${claimMap.STATUS == 0}">未处理</c:if>
				<c:if test="${claimMap.STATUS == 1}">已审核通过</c:if>
				<c:if test="${claimMap.STATUS == 2}">审核不通过</c:if>		
			</td>
		</tr>		
		<tr>
			<td>审核描述：</td>
			<td>${claimMap.RESULTREMARK }</td>
		</tr>	
		<tr>
			<td>审核时间：</td>
			<td>${claimMap.CREATETIME }</td>
		</tr>					
		<tr>
			<td>附件材料：</td>
			<td>
				<c:forEach items="${attachmentList }" var="list">
					<img class="imageView" src="data:image/jpg;base64,${list.ATTACHMENT_PATH }" style="width:100px; height:100px; float:left; margin-left:10px;">
				</c:forEach>
			</td>
		</tr>		
		<tr>
			<td colspan="2">
				 <div class="layui-layer-btn">
					<a class="btn btn-cancel" onclick="parent.layer.closeAll('iframe')">关闭</a>
				</div>
			</td>		
		</tr>																															
	</table>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/claim.js"></script>
</body>
</html>
