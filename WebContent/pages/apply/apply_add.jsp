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
		<tr>
			<td>
				<label for="applyTypeCN">
					<font color="red">*</font>申请类型：
				</label>
			</td>
			<td>
				<select id="applyTypeCN">
					<option value="0" selected="selected">申请挂牌</option>
					<option value="1">申请补办</option>
				</select>
			</td>
			<td>
				<label for="applyNumber">
					<font color="red">*</font>申请数量：
				</label>
			</td>
			<td>
				<input type="text" value="1" onblur="applyNumCheck()" id="applyNumber" placeholder="申请数量" maxlength="3">
			</td>
		</tr>
		<tr>
			<td>
				<label for="applyName">
					<font color="red">*</font>姓名：
				</label>
			</td>
			<td>
				<input type="text" id="applyName" placeholder="申请人名称" maxlength="20">
			</td>
			<td>
				<label for="position">职位：</label>
			</td>
			<td>
				<input type="text" id="position" placeholder="申请人职位" maxlength="10">
			</td>
		</tr>
		<tr>
			<td>
				<label for="cerType">
					<font color="red">*</font>证件类型：
				</label>
			</td>
			<td>
				<select id="cerType">
	        		<c:forEach items="${sysCodeList }" var="sys">
	        			<option value="${sys.EC_ID }" <c:if test="${sys.EC_ID == '968ee786b9124054b56ee7112d0a9b07' }">selected="selected"</c:if>>${sys.EC_NAME }</option>
	        		</c:forEach>	
	        	</select>
			</td>
			<td>
				<label for="cerno">
					<font color="red">*</font>证件号码：
				</label>
			</td>
			<td>
				<input type="text" id="cerno" placeholder="申请人证件号码" maxlength="50">
			</td>
		</tr>
		<tr>
			<td>
				<label for="email">
					<font color="red">*</font>联系邮箱：
				</label>
			</td>
			<td>
				<input type="text" id="email" placeholder="申请人联系邮箱" maxlength="30">
			</td>
			<td>
				<label for="tel">
					<font color="red">*</font>联系号码：
				</label>
			</td>
			<td>
				<input type="text" id="tel" placeholder="申请人联系号码" maxlength="11">
			</td>
		</tr>
		<tr>
			<td>
				<label for="sex">性别：</label>
			</td>
			<td colspan="3">
				<input type="radio" value="0" name="sex" checked="checked">&nbsp;女&nbsp;&nbsp;&nbsp;
				<input type="radio" value="1" name="sex">&nbsp;男
			</td>
		</tr>
		<tr>
			<td>
				<label for="address">联系地址：</label>
			</td>
			<td colspan="3">
				<textarea id="address" placeholder="申请人联系地址" maxlength="300" style="width: 90%;" ></textarea>
			</td>
		</tr>
		<tr>
			<td>
				<label for="remark">备注：</label>
			</td>
			<td colspan="3">
				<textarea id="remark" placeholder="申请备注" maxlength="300" style="width: 90%;" ></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<button class="btn btn-confirm" onclick="add()">保存</button>
				<button class="btn btn-cancel" onclick="parent.layer.closeAll('iframe')">关闭</button>
			</td>
		</tr>
	</table>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/apply.js"></script> 
</body>
</html>
