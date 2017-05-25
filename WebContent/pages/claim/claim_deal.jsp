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
	<input type="hidden" id="username" value="${claimMap.USERNAME }">
	<input type="hidden" id="password" value="${claimMap.PASSWORD }">
	<input type="hidden" id="email" value="${claimMap.EMAIL }">
	<input type="hidden" id="tel" value="${claimMap.TEL }">
	<input type="hidden" id="pripid" value="${claimMap.PRIPID }">
	<input type="hidden" id="memberId" value="${claimMap.MEMBER_ID }">
	<table class="table table-striped table-bordered table-hover" id="tabs">
		<tr>
			<td width="30%">企业名称：</td>
			<td width="70%">${claimMap.ENTNAME }</td>
		</tr>	
		<tr>
			<td>认领人：</td>
			<td>${claimMap.USERNAME }</td>
		</tr>
		<tr >
			<td>联系邮箱：</td>
			<td>${claimMap.EMAIL }</td>
		</tr>
		<tr >
			<td>联系电话：</td>
			<td>${claimMap.TEL }</td>
		</tr>		
		<tr >
			<td>认领描述：</td>
			<td>${claimMap.REMARK }</td>			
		</tr>
		<tr >
			<td>认领时间：</td>
			<td>${claimMap.CREATETIME }</td>
		</tr>
		<tr >
			<td>附件材料：</td>
			<td>
				<c:forEach items="${attachmentList }" var="list">
					<img class="imageView" src="data:image/jpg;base64,${list.ATTACHMENT_PATH }" style="width:100px; height:100px; float:left; margin-left:10px;">
				</c:forEach>
			</td>
		</tr>		
		<tr >
			<td><font color="red">*</font>审核描述：</td>
			<td>
				<textarea style="width:80%; height: 80px" placeholder="请输入您的审核描述..." id="remark" oninput="this.style.height='0px';this.style.height=(this.scrollHeight+'px');"></textarea>
			</td>
		</tr>	
		<tr>
			<td  style="text-align:right;" colspan="2">
				<button class="btn btn-through" onclick="edtClaim('${claimMap.ID }','1')">通过</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="btn btn-nothrough" onclick="edtClaim('${claimMap.ID }','2')">不通过</button>
			</td>	
		</tr>																															
	</table>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/claim.js"></script>
</body>
</html>
