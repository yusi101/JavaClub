<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="../system/allresources.jsp"%>
<link href="${pageContext.request.contextPath}/static/styles/brand/brand.css" rel="stylesheet" />
</head>

<body>

	<div class="form-horizontal">
		<div style="height: 50px;"></div>
		<div class="control-group">
			<label class="control-label" for="NAME"><span style="color: red">*</span>名称</label>
			<div class="controls">
				<input type="text" id="NAME" placeholder="名称" maxlength="20">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="BIANMA"><span style="color: red">*</span>编码</label>
			<div class="controls">
				<input type="text" id="BIANMA" placeholder="编码" maxlength="30">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="ORDY_BY"><span style="color: red">*</span>序号</label>
			<div class="controls">
				<input onkeyup="value=value.replace(/^[0][0-9]*$/,'')" onblur="value=value.replace(/^[0][0-9]*$/,'')" type="number" id="ORDY_BY" placeholder="序号" maxlength="4">
			</div>
		</div>
		<input type="hidden" id="ZD_ID" value="<%=request.getParameter("ZD_ID")%>">
		<div class="control-group">
			<div class="controls">
				<input type="button" class="btn btn-primary" id="add" value="保存" onclick="add()" />
				<input type="button" class="btn btn-default"  value="关闭" onclick="parent.layer.closeAll('iframe')" />
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/dictionaries.js"></script>
</body>
</html>
