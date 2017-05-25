<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>申请牌照</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<%@ include file="../system/allresources.jsp"%>
	<link href="${pageContext.request.contextPath}/static/styles/brand/brand.css" rel="stylesheet" />
	<style type="text/css">
		input[type=radio] {
			vertical-align: top;
		}
	</style>
</head>

<body>
	<table class="table table-striped table-bordered table-hover">
		<input type="hidden" id="ID" value ="${map.ID }" />
		<input type="hidden" id="APPLY_NUMBER" value ="${map.APPLY_NUMBER }" />
		<input type="hidden" id="ENTNAME" value ="${map.ENTNAME }" />
		<input type="hidden" id="PRIPID" value ="${map.PRIPID }" />
		<tr>
			<td width="30%">申请企业名称：</td>
			<td width="70%">${map.ENTNAME }</td>
		</tr>
		<tr>
			<td>申请企业注册号：</td>
			<td>${map.REGNO }</td>
		</tr>
		<tr>
			<td>申请方式：</td>
			<td>${map.APPLYWAY_CN }</td>
		</tr>
		<tr>
			<td>申请人：</td>
			<td>${map.APPLY_NAME }</td>
		</tr>
		<tr>
			<td>申请类型：</td>
			<td>${map.APPLYTYPE_CN }</td>
		</tr>
		<tr>
			<td>身份证类型：</td>
			<td>${map.CERTYPE_CN }</td>
		</tr>
		<tr>
			<td>身份证号码：</td>
			<td>${map.CERNO }</td>
		</tr>
		<tr>
			<td>申请牌照数量：</td>
			<td>${map.APPLY_NUMBER }</td>
		</tr>
		<tr>
			<td>审核意见：</td>
			<td>
				<textarea maxlength="300" id="RESULTREMARK" style="width:80%;" placeholder="请输入审核意见"></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div class="layui-layer-btn">
					<button class="btn btn-through" onclick="openOk()">通过</button>&nbsp;&nbsp;&nbsp;
					<button class="btn btn-nothrough" onclick="openNo()">不通过</button>
				</div>
			</td>
		</tr>
	</table>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/apply.js"></script>
</body>
</html>
