<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>添加页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<%@ include file="../allresources.jsp"%>
	
  	<style type="text/css">
  		input[type=radio]{
  			vertical-align: top;
  		}
  	</style>
</head>

<body>
	<table class="table table-striped table-bordered table-hover">
		<tr>
			<td width="40%">
				<span style="color: red">*</span>用户名：
			</td>
			<td width="60%">
				<input type="text" id="USERNAME" placeholder="请输入用户名" maxlength="12" value="${pd.USERNAME }" readonly="readonly" >
			</td>
		</tr>	
		<tr>
			<td>
				<span style="color: red">*</span>真实姓名：
			</td>
			<td>
				<input type="text" id="NAME" placeholder="请输入真实姓名" maxlength="12" value="${pd.NAME }">
			</td>
		</tr>	
		<tr>
			<td>
				<span style="color: red">*</span>性别：
			</td>
			<td>
				<input type="radio" name="SEX" value="1" <c:if test="${pd.SEX == '1'}">checked</c:if>/>&nbsp;男&nbsp;&nbsp;
				<input type="radio" name="SEX" value="0" <c:if test="${pd.SEX == '0'}">checked</c:if>/>&nbsp;女
			</td>
		</tr>
		<tr>
			<td>
				<span style="color: red">*</span>邮件：
			</td>
			<td>
				<input type="text" id="EMAIL" placeholder="请输入邮件号码" maxlength="32" value="${pd.EMAIL }">
			</td>
		</tr>
		<tr>
			<td>
				<span style="color: red">*</span>手机号码：
			</td>
			<td>
				<input type="text" id="PHONE" placeholder="请输入手机号码" maxlength="32" value="${pd.PHONE }">
			</td>
		</tr>	
		<tr>
			<td>备注：</td>
			<td>
				<textarea id="REMARK" placeholder="请输入备注" maxlength="255">${pd.REMARK }</textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<button class="btn btn-confirm" onclick="edt('${pd.USER_ID }')">保存</button>
				<a class="btn btn-cancel" onclick="parent.layer.closeAll('iframe')">关闭</a>
			</td>
		</tr>
	</table>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/index.js"></script>
	<script>
	$(document).keyup(function(event) {
		if (event.keyCode == 13) {
			edt('${pd.USER_ID }');
		}
	});
	</script>
</body>
</html>
