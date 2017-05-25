<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>新增敏感词</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<%@ include file="../system/allresources.jsp"%>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/plugins/zTree/3.5/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>
  	<style type="text/css">
  		input[type=radio]{
  			vertical-align: top;
  		}
  	</style>
</head>

<body>
	<table class="table table-striped table-bordered table-hover">
		<tr>
			<td width="40%"><span style="color: red">*</span>敏感词：</td>
			<td width="60%"><input type="text" id="keyword" placeholder="请输入关键词" maxlength="32"></td>
		</tr>	
		<tr>
			<td>状态：</td>
			<td>
			<input type="radio" name="status" id="status" value="0" />&nbsp;禁用
				<input type="radio" name="status" id="status" value="1" checked="checked"/>&nbsp;启用&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td>出现该敏感词的操作：</td>
			<td><select id="type" name="type">
						<option value="0">--不执行任何操作--</option>
						<option value="1">--关键词为空--</option>
						<option value="2">--整条内容变成空--</option>
					</select></td>
		</tr>	
		<tr>
			<td colspan="2" class="layui-layer-btn">
				<a class="layui-layer-btn0" onclick="add()">保存</a>
				<a class="layui-layer-btn1" onclick="parent.layer.closeAll('iframe')">关闭</a>
			</td>
		</tr>
	</table>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/syskeyword.js"></script>
</body>
</html>
