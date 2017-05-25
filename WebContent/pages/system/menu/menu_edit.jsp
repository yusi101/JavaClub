<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>菜单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/zTree/3.5/zTreeStyle.css" type="text/css">
	 <!--common-->
	<link href="http://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
	<style type="text/css">
		#menuIconList img {
			width: 32px;
			height: 32px;
			margin: 5px;
		}
		#menuIconView{
		    width: 24px;
		    display: -webkit-inline-box;
		    vertical-align: middle;
		}
		input{
		    width: 100% !important;
		    box-sizing: border-box !important;
		    height: 30px !important;
		
		}
		.fa{
		  	font-size: 32px;
		    vertical-align: middle;
		    width: 32px;
		    text-align: center;
		    margin: 5px;
		    color: #7A7676;
		}
		.accordion-heading .fa{
			font-size: 24px;
		}
	</style>
	<%@ include file="../allresources.jsp"%>
</head>

<body>
	<div class="form-horizontal" style="margin: 10px;">
		<table id="table_bug_report" class="table table-striped table-bordered table-hover">
			<tr>
			    <td style="vertical-align: middle;width:60px;">父级菜单</td>
				<td style="text-align: left !important;">
					<div class="accordion-group" style="border: 0;">
						<div class="accordion-heading">
							<input type="text" name="PARENT_NAME" id="parentName" placeholder="请选择父级菜单" value="${queryMenu.PARENT_NAME}" title="请选择父级菜单" readonly="readonly" onclick="collapseOne();" />
							<input type="text" name="PARENT_ID" id="parentId" readonly="readonly" style="display: none;" value="${queryMenu.PARENT_ID}"/>
							<input type="text" name="PARENT_TYPE" id="parentType" readonly="readonly" style="display: none;" value="${queryMenu.MENU_TYPE-1}"/>
						</div>
						<div class="accordion-body collapse" id="collapseOne" style="position: inherit; z-index: 9999; width: 98%;">
							<ul id="menuList" class="ztree"></ul>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;width:60px;">菜单图标</td>
				<td style="text-align: left !important;">
					<div class="accordion-group" style="border: 0;width: 100%;">
						<div class="accordion-heading">
							<input type="text" name="MENU_ICON" id="menuIcon" placeholder="请选择菜单图标" value="${queryMenu.MENU_ICON}" style="width: 80% !important;" title="请选择菜单图标" readonly="readonly" onclick="chooseIcon();" />
							<c:if test="${fn:contains(queryMenu.MENU_ICON,'zhirong')}">
								<img id="menuIconView"
									<c:if test="${queryMenu.MENU_ICON ==null}">style="display: none;"</c:if>
									<c:if test="${queryMenu.MENU_ICON !=null}">src="${queryMenu.MENU_ICON}"</c:if>
								>
							</c:if>
							<c:if test="${!fn:contains(queryMenu.MENU_ICON,'zhirong')}">
								<img id="menuIconView" style="display: none;">
								<i class="fa ${queryMenu.MENU_ICON}"></i>
							</c:if>
						</div>
						<div class="accordion-body collapse " id="menuIconList" style="position: inherit; z-index: 9999; width: 98%;">
							<c:choose>
								<c:when test="${not empty menuIconList}">
									<c:forEach items="${menuIconList}" var="menu" varStatus="vs">
										<img alt="" src="${pageContext.request.contextPath}/static/images/menuIcon/${menu}">
									</c:forEach>
								</c:when>
								<c:otherwise>
										没有相关数据
								</c:otherwise>
							</c:choose>
								<i class="fa fa-home"></i>
								<i class="fa fa-desktop"></i>
								<i class="fa fa-dashboard"></i>
								<i class="fa fa-gears"></i>
								<i class="fa fa-laptop"></i>
								<i class="fa fa-handshake-o"></i>
								<i class="fa fa-balance-scale"></i>
								<i class="fa fa-sitemap"></i>
								<i class="fa fa-bank"></i>
								<i class="fa fa-bar-chart-o"></i>
								<i class="fa fa-signal"></i>
								<i class="fa fa-sort-amount-asc"></i>
								<i class="fa fa-pie-chart"></i>
								<i class="fa fa-line-chart"></i>
								<i class="fa fa-area-chart"></i>
								<i class="fa fa-location-arrow"></i>
								<i class="fa fa-map"></i>
								<i class="fa fa-paper-plane"></i>
								<i class="fa fa-sellsy"></i>
								<i class="fa fa-sliders"></i>
								<i class="fa fa-tags"></i>
								<i class="fa fa-gavel"></i>&nbsp;
								<i class="fa fa-flag-o"></i>
								<i class="fa fa-book"></i>
								<i class="fa fa-anchor"></i>
								<i class="fa fa-fighter-jet"></i>
								<i class="fa fa-shield"></i>
								<i class="fa fa-user-md"></i>
								<i class="fa fa-user-circle"></i>
								<i class="fa fa-google-plus"></i>
								<i class="fa fa-cog" ></i>
								<i class="fa fa-rub"></i>
								<i class="fa fa-leaf"></i>
								<i class="fa fa-key"></i>
								<i class="fa fa-filter"></i>
								<i class="fa fa-coffee"></i>
								<i class="fa fa-code-fork"></i>
								<i class="fa fa-book"></i>
								<i class="fa fa-bars"></i>
								<i class="fa fa-bar-chart-o"></i>
								<i class="fa fa-download"></i>
								<i class="fa fa-bar-chart-o"></i>
								<i class="fa fa-th-list"></i>
								<i class="fa fa-file-text"></i>
								<i class="fa fa-map-marker"></i>
								<i class="fa fa-windows"></i>
								<i class="fa fa-trello"></i>
								<i class="fa fa-dribbble"></i>
								<i class="fa fa-file-text-o"></i>
								<i class="fa fa-text-width"></i>
								<i class="fa fa-text-height"></i>
								<i class="fa fa-underline"></i>
								<i class="fa fa-puzzle-piece"></i>
								<i class="fa fa-plane"></i>
								<i class="fa fa-picture-o"></i>
								<i class="fa fa-registered"></i>
								<i class="fa fa-search"></i>
								<i class="fa fa-safari"></i>
								<i class="fa fa-stack-exchange"></i>
								<i class="fa fa-mobile-phone"></i>
								<i class="fa fa-globe"></i>
								<i class="fa fa-print"></i>
								<i class="fa fa-exclamation-circle"></i>
								<i class="fa fa-eraser"></i>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;width:60px;">菜单名称</td>
				<td style="text-align: left !important;">
					<input type="text" name="MENU_NAME" id="menuName" placeholder="这里输入菜单名称" value="${queryMenu.MENU_NAME}" title="名称" />
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;width:60px;">链接地址</td>
				<td style="text-align: left !important;">
					<input type="text" name="MENU_URL" id="menuUrl" placeholder="这里输入链接地址" value="${queryMenu.MENU_URL}" title="地址" />
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;width:60px;">排序号</td>
				<td style="text-align: left !important;">
					<input type="number" name="MENU_ORDER" id="menuOrder" onkeyup="value=value.replace(/^[0][0-9]*$/,'')" onblur="value=value.replace(/^[0][0-9]*$/,'')" placeholder="这里输入序号" value="${queryMenu.MENU_ORDER}" title="序号" />
				</td>
			</tr>
		</table>
	</div>
	<div class="layui-layer-btn">
		<a class="layui-layer-btn0" onclick="eidtMenu('${queryMenu.MENU_ID}')">保存</a>
		<a class="layui-layer-btn1" onclick="parent.layer.closeAll('iframe')">关闭</a>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/menu.js"></script>
	<script type="text/javascript">
		var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "level",
				autoCheckTrigger: true
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
			$("#parentName").val(treeNode.MENU_NAME);
			$("#parentId").val(treeNode.MENU_ID);
			$("#parentType").val(treeNode.MENU_TYPE);
			collapseOne();
		};

		var code;
		function setCheck() {
			var type = $("#level").attr("checked")? "level":"all";
			setting.check.radioType = type;
			showCode('setting.check.radioType = "' + type + '";');
			$.fn.zTree.init($("#menuList"), setting, ${menuTree});
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
	</form>
</body>
</html>