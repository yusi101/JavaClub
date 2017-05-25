<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>修改密码</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="../allresources.jsp"%>
</head>
<body>
	<table class="table table-striped table-bordered table-hover">
		<tr>
			<td width="40%">
				<span style="color: red">*</span>用户账号：
			</td>
			<td width="60%">
				${pd.USERNAME }
				<input type="hidden" id="USERNAME" value="${pd.USERNAME }"/>
			</td>
		</tr>	
		<tr>
			<td>
				<span style="color: red">*</span>用户名：
			</td>
			<td>
				${pd.NAME }
				<input type="hidden" id="NAME" value="${pd.NAME }"/>
			</td>
		</tr>	
		<tr>
			<td>
				<span style="color: red">*</span>请输入原密码：
			</td>
			<td>
				<input type="password" id="oldPASSWORD" placeholder="请输入原密码" maxlength="18">
			</td>
		</tr>
		<tr>
			<td>
				<span style="color: red">*</span>请输入密码：
			</td>
			<td>
				<input type="password" id="PASSWORD" placeholder="请输入新密码" maxlength="18">
			</td>
		</tr>
		<tr>
			<td>
				<span style="color: red">*</span>请再次输入密码：
			</td>
			<td>
				<input type="password" id="PASSWORDS" placeholder="请再次输入新密码" maxlength="18">
			</td>
		</tr>	
		<tr>
			<input type="hidden" id="ZD_ID" value="${pd.USER_ID }">
			<td colspan="2">
				<button type="button" class="btn btn-confirm" id="add" onclick="update('${pd.USER_ID }')" >保存</button>
				<button type="button" class="btn btn-cancel" id="close" onclick="parent.layer.closeAll('iframe')">关闭</button>
			</td>
		</tr>
	</table>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/user.js"></script>
	<script>
		$(document).keyup(function(event) {
			if (event.keyCode == 13) {
				update('${pd.USER_ID }');
			}
		});
	</script>
</body>
</html>
