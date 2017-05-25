<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>配置项</title>
	<%@ include file="../system/allresources.jsp" %>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/static/plugins/zTree/3.5/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript">
		var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "level"
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		var zNodes = ${parentList };
		
		var code;		
		function setCheck() {
			var type = $("#level").attr("checked")? "level":"all";
			setting.check.radioType = type;
			showCode('setting.check.radioType = "' + type + '";');
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		}
		function showCode(str) {
			if (!code) code = $("#code");
			code.empty();
			code.append("<li>"+str+"</li>");
		}
		
		$(document).ready(function(){
			setCheck();			
			$("#level").bind("change", setCheck);
			$("#all").bind("change", setCheck);
		});
	</script>
</head>

<body>
	<table id="table_bug_report" class="table table-striped table-bordered table-hover">	
		<tr>
			<td>名称：</td>
			<td>
				<input type="text" name="name" id="name" placeholder="输入名称" maxlength="20"/>
			</td>
		</tr>
		<tr>
			<td>配置项键：<br/><font color="red" size="2px">注意：添加后不能修改</font></td>
			<td>
				<input type="text" name="keyname" id="keyname" placeholder="输入配置项键" maxlength="20"/>
			</td>
		</tr>
		<tr>
			<td>配置项值：</td>
			<td>
				<input type="text" name="value" id="value" placeholder="输入配置项值" maxlength="300"/>
			</td>
		</tr>
		<tr>
			<td>配置项描述：</td>
			<td>
				<textarea style="height: 60px;" name="remark" id="remark" placeholder="输入配置项描述" maxlength="300"></textarea>
			</td>
		</tr>
		<tr>
			<td>排序号：<br/><font color="red" size="2px">注意：越大越前</font></td>
			<td>
				<input type="text" name="order" id="order" placeholder="输入排序号" maxlength="10"/>
			</td>
		</tr>
		<tr>
			<td>父节点ID：</td>
			<td>					
				<ul id="treeDemo" class="ztree"></ul>		
			</td>	
		</tr>
		<tr>
			<td colspan="2">
				 <div class="layui-layer-btn">
					<button class="btn btn-confirm" onclick="add()">确定</button>
					<button class="btn btn-cancel" onclick="parent.layer.closeAll('iframe')">关闭</button>
				</div>
			</td>
		</tr>
	</table>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/config.js"></script>
</body>
</html>