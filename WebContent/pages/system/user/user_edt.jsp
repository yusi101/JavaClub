<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>添加页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<%@ include file="../allresources.jsp"%>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/plugins/zTree/3.5/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript">
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		var zNodes = ${parentList }
		
		var code;
		
		function setCheck() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			py = $("#py").attr("checked")? "p":"",
			sy = $("#sy").attr("checked")? "s":"",
			pn = $("#pn").attr("checked")? "p":"",
			sn = $("#sn").attr("checked")? "s":"",
			type = { "Y":py + sy, "N":pn + sn};
			zTree.setting.check.chkboxType = type;
			showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
		}
		function showCode(str) {
			if (!code) code = $("#code");
			code.empty();
			code.append("<li>"+str+"</li>");
		}
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			setCheck();
			$("#py").bind("change", setCheck);
			$("#sy").bind("change", setCheck);
			$("#pn").bind("change", setCheck);
			$("#sn").bind("change", setCheck);
		});
	</script>
  	<style type="text/css">
  		input[type=radio]{
  			vertical-align: top;
  		}
  	</style>
</head>

<body>
	<table class="table table-striped table-bordered table-hover">
		<tr>
			<td width="30%">
				<span style="color: red">*</span>用户名：
			</td>
			<td width="70%">
				${pd.USERNAME }
			</td>
		</tr>	
		<tr>
			<td>
				<span style="color: red">*</span>真实姓名：
			</td>
			<td>
				<input type="text" id="NAME" placeholder="请输入真实姓名" maxlength="32" value="${pd.NAME }">
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
				<textarea id="REMARK" placeholder="请输入备注" maxlength="50">${pd.REMARK }</textarea>
			</td>
		</tr>
		<tr >
			<td>
				<span style="color: red">*</span>选择角色：
			</td>
			<td align="text">
				<ul id="treeDemo" class="ztree" style="margin-left: 22%;"></ul>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="layui-layer-btn">
				<a class="layui-layer-btn0" onclick="edt('${pd.USER_ID }')">保存</a>
				<a class="layui-layer-btn1" onclick="parent.layer.closeAll('iframe')">关闭</a>
			</td>
		</tr>
	</table>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/sysuser.js"></script>
</body>
</html>
