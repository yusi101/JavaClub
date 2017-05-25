<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/zTree/3.5/zTreeStyle.css" type="text/css">
<title>角色管理</title>
<body>
	<%@ include file="../system/allresources.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid"  style="width:60%;float:left">
			<h4 class="title">角色管理</h4>
				<table id="table_bug_report" class="table table-striped table-bordered table-hover">
						<tr>
							<th class="center" width="25%">角色名称</th>
							<th class="center">
								<input type="text" id="role_Name" name="role_Name" style="width:50%;" maxlength="20" value="${role.ROLE_NAME}"/>
							</th>
						</tr>
						<tr>
							<th class="center" width="25%">角色编码</th>
							<th class="center">
								<input type="text" id="role_Code" name="role_Code" style="width:50%;" maxlength="20" value="${role.ROLE_CODE}"/>
							</th>
						</tr>
						<tr>
							<th class="center" width="25%">所属权限</th>
							<th class="center">
								<div class="accordion-heading">
									<div name="menu_Name" id="menu_Name" style="font-weight: normal;text-align: left;">${role.RIGHTSNAME}</div>
									<input type="text" name="menu_Id" id="menu_Id" readonly="readonly" style="display: none;" value="${role.RIGHTS}"/>
									<input type="text" name="parent_Id" id="parent_Id" readonly="readonly" style="display: none;" />
								</div>
							</th>
						</tr>
						<tr>
							<th class="center" colspan="2">
								<input type="button" class="btn btn-confirm" onclick="upd('${role.ROLE_ID}')" value="确定"/>
							</th>
						</tr>
				</table>
				
		</div>
		<div class="row-fluid"  style="width:40%;float:left;">
			<h4 class="title">菜单选择</h4>
			<ul id="menuList" class="ztree" style="argin-left: 40px;"></ul>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/role.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>

	<script type="text/javascript">
		var setting = {
				check: {
					enable: true,
					chkStyle: "checkbox",
					checkboxType: "level"
				},
				data: {
					 key:{
					      name:"MENU_NAME" //节点显示的值
					 },
					simpleData: {
						enable: true,
						 idKey: "MENU_ID", // id编号命名 默认  
		                 pIdKey: "PARENT_ID", // 父id编号命名 默认  
		                 rootPId: 0 // 用于修正根节点父节点数据，即 pIdKey 指定的属性值  
					}
				},callback: {
					onCheck: zTreeOnCheck
				}			
			};
		 	function zTreeOnCheck(event, treeId, treeNode) {
		 		var treeObj = $.fn.zTree.getZTreeObj("menuList");
		        var nodes = treeObj.getCheckedNodes(true);
		        var msg = "";
		        var mId = "";
		        for (var i = 0; i < nodes.length; i++) {
		            msg += nodes[i].MENU_NAME+",";
		            mId += nodes[i].MENU_ID+"@";
		        }
		        msg=msg.substring(0, msg.length-1);
		        mId=mId.substring(0, mId.length-1);
		        $("#menu_Name").html(msg);
		        $("#menu_Id").val(mId);
				$("#parent_Id").val(treeNode.PARENT_ID);
				collapseOne();
			}; 
			
			var code;		
			function setCheck() {
				var type = $("#level").attr("checked")? "level":"all";
				setting.check.checkboxType = type;
				showCode('setting.check.checkboxType = "' + type + '";');
				$.fn.zTree.init($("#menuList"), setting, ${roleMenu});
				
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
				if($("#menuId").val()!=""){
					var parentId = "${menu.parentId}";
					if(parentId==""){
						$("#parentId").attr("disabled",true);
					}else{
						$("#parentId").val(parentId);
					}
				}
			});
	</script>
</head>
</body>
</html>