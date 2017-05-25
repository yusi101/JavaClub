<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>打印回执单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<%@ include file="../system/allresources.jsp"%>
</head>

<body>
	<div id="apply_receipt" style="width: 80%; margin: 100px auto; text-align: center;">
		<h1 style="text-align: center; color: red">该企业没有回执单可打印！</h1>
	</div>
	<div style="text-align: center;">
		<button class="btn btn-cancel" onclick="parent.layer.closeAll('iframe')">关闭</button>
	</div>
</body>
</html>
