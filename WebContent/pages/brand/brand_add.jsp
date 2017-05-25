<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>添加页面</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
  	<%@ include file="../system/allresources.jsp" %>
  	<link href="${pageContext.request.contextPath}/static/styles/brand/brand.css" rel="stylesheet" />
</head>

<body>
	<div class="form-horizontal">
		<div style="height: 50px;"></div>
	    <div class="control-group">
	        <label class="control-label" for="userName">姓名</label>
	        <div class="controls">
	            <input type="text" id="userName" placeholder="姓名">
	        </div>
	    </div>
	    <div class="control-group">
	        <label class="control-label" for="Chinese">语文</label>
	        <div class="controls">
	            <input type="text" id="Chinese" placeholder="语文">
	        </div>
	    </div>
	    <div class="control-group">
	        <label class="control-label" for="Math">数学</label>
	        <div class="controls">
	            <input type="text" id="Math" placeholder="数学">
	        </div>
	    </div>
	    <div class="control-group">
	        <label class="control-label" for="English">英语</label>
	        <div class="controls">
	            <input type="text" id="English" placeholder="英语">
	        </div>
	    </div>    
	   	<div class="layui-layer-btn">
			<a class="layui-layer-btn0" onclick="add()">保存</a>
			<a class="layui-layer-btn1" onclick="parent.layer.closeAll('iframe')">关闭</a>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/scripts/brand/brand.js"></script>
</body>
</html>
