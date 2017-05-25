<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<title>菜单列表</title>
	<meta name="description" content="overview & stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<%@ include file="../allresources.jsp"%>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">菜单管理</h4>
			<div style="margin: 10px 0;height: 30px;">
				<a class="btn btn-success" onclick="addMenu();"><i class="fa fa-plus"></i>&nbsp;新增</a>
			</div>
			<table id="table_report" class="table table-striped table-bordered table-hover tab">
				<thead>
					<tr>
						<th class="center" style="width:10%;">序号</th>
						<th class='center' style="width:35%;">名称</th>
						<th class='center' style="width:25%;">资源路径</th>
						<th class='center' style="width:5%;">排序</th>
						<th class='center' style="width:25%;">操作</th>
					</tr>
				</thead>
				<c:choose>
					<c:when test="${not empty menuList}">
						<c:forEach items="${menuList}" var="menu" varStatus="vs">
							<tr id="menu${menu.MENU_ID }">
								<td class="center" >${vs.index+1}</td>
								<td style="text-align: left !important;">
									<c:if test="${fn:contains(menu.MENU_ICON,'zhirong')}">
		               	 				<img class="left-icon-child"  src="${menu.MENU_ICON }" /> 
		               	 			</c:if>
					          		<c:if test="${!fn:contains(menu.MENU_ICON,'zhirong')}">
				               	 		<i class="fa ${menu.MENU_ICON }"></i>
				               	 	</c:if>
									
									</i>${menu.MENU_NAME }&nbsp;
									<c:if test="${menu.MENU_TYPE == '1' }">
										<span class="label label-success arrowed">菜单栏</span>
									</c:if>
									<c:if test="${menu.MENU_TYPE == '2' }">
										<span class="label label-important arrowed-in">菜单</span>
									</c:if>
									<c:if test="${menu.MENU_TYPE == '3' }">
										<span class="label label-important arrowed-in">菜单项</span>
									</c:if>
								</td>
								<td>${menu.MENU_URL == '#'? '': menu.MENU_URL}</td>
								<td class='center'>${menu.MENU_ORDER }</td>
								<td>
									<a class='btn btn-warning' onclick="openClose('${menu.MENU_ID }',this,${vs.index })">展开</a>
									<a class='btn btn-info' title="编辑" onclick="editMenu('${menu.MENU_ID }','${menu.PARENT_ID }')">
										<i class="fa fa-edit"></i>&nbsp; 编辑
									</a>
									<a class='btn btn-danger' title="删除" onclick="delMenu('${menu.MENU_ID }',true)">
										<i class='fa fa-trash'></i>&nbsp; 删除
									</a>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="100">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		HideLoading();
		
		function openClose(parentId,curObj){
			var txt = $(curObj).text();
			if(txt=="展开"){
				if($(".submenu"+parentId).length>0){
					$(".submenu"+parentId).show();
					$(curObj).text("折叠");
				}else{
					$.ajax({
						type : "POST",
						url: "${pageContext.request.contextPath}/menuController/querySubMenuByParentId",
						data : {
							parentId : 	parentId
						},
						success : function(result){
							var data=JSON.parse(result);
							var str="";
							for (var i = 0; i < data.length; i++) {
								str+='<tr id="menu'+data[i].MENU_ID+'" class="submenu'+parentId+'">'
								+'<td class="center" style="text-indent: '+(data[i].MENU_TYPE*20)+'px;">'+(i+1)+'</td>'
								+'<td style="text-align: left !important;    width: 100px;">'
								+'	<img src="${pageContext.request.contextPath}/static/images/join.gif" class="'+data[i].MENU_ICON+'" style="margin-left: '+(data[i].MENU_TYPE*50)+'px;">&nbsp;</img>';
								if(data[i].MENU_ICON.indexOf("zhirong") >= 0){
									str+='	<img class="left-icon-child"  src="'+data[i].MENU_ICON+'" /> '+data[i].MENU_NAME+'&nbsp;';	
								}else{
									str+='	<i class="fa '+data[i].MENU_ICON+'"></i> '+data[i].MENU_NAME+'&nbsp;';		
								}
								if(data[i].MENU_TYPE==0){
									str+='	<span class="label label-success arrowed">菜单栏</span>';	
								}else if(data[i].MENU_TYPE==1){
									str+='	<span class="label label-important arrowed-in">菜单</span>';	
								}else{
									str+='	<span class="label label-warning arrowed-in">菜单项</span>';		
								}
								str+='</td>'
								+'<td style="text-align: left !important;">'+(data[i].MENU_URL=="#" ? "" : data[i].MENU_URL)+'</td>'
								+'<td class="center">'+data[i].MENU_ORDER+'</td>'
								+'<td style="width: 25%;">'
								+'	<a class="btn btn-warning" onclick="openClose(\''+data[i].MENU_ID+'\',this)">展开</a>'
								+'	<a class="btn btn-info" title="编辑" onclick="editMenu(\''+data[i].MENU_ID+'\',\''+data[i].PARENT_ID+'\')">'
								+'		<i class="fa fa-edit"></i>&nbsp; 编辑'
								+'	</a>'
								+'	<a class="btn btn-danger" title="删除" onclick="delMenu(\''+data[i].MENU_ID+'\')">'
								+'		<i class="fa fa-trash"></i> 删除'
								+'	</a>'
								+'</td>';
								+'</tr>';
							}
						  $("#menu"+parentId).after(str);
						}
					});
					$(curObj).text("折叠");
				}
			}else{
				$(".submenu"+parentId).hide();
				$('.sub'+$(".submenu"+parentId).attr("id")).hide();
				$(curObj).text("展开");
			}
		}
		
		function addMenu(){
			layer.open({
			  	type: 2,
			  	title: '添加菜单',
			  	scrollbar: false, //父页面是否有滚动条
			  	shadeClose: false,  //点击其他区域关闭弹窗
			  	shade: 0.5,  //笼罩层透明度
			  	area: ['400px', '70%'],  //大小
			  	maxmin: true,//是否显示最大化按钮
			  	content: contextPath + '/menuController/toCreateMenu',
			});
		}
		
		function delMenu(MENU_ID){
			layer.confirm('此操作会将其子节点一并删除，是否确定删除(请确认是否有角色选中了此菜单)？', {icon: 3, title:'删除提示'}, function(index){
				ShowLoading();
				$.ajax({
					type : "POST",
					url: "${pageContext.request.contextPath}/menuController/deleteMenu",
					data : {
						MENU_ID : 	MENU_ID
					},
					success : function(result){
						HideLoading();
			    		if(result=="success"){
			    			layer.alert('删除成功!', {icon: 1},function(index){
			    				setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
			    				parent.layer.closeAll('iframe');//关闭弹窗
			    				ShowLoading();
			    			});
			    		}else{
			    			layer.alert('删除失败!', {icon: 2});
			    		}
					}
				});
				layer.close(index);
			});
		}
		
		function editMenu(MENU_ID,PARENT_ID){
			layer.open({
				type: 2,
			  	title: '修改菜单',
			  	scrollbar: false, //父页面是否有滚动条
			  	shadeClose: false,  //点击其他区域关闭弹窗
			  	shade: 0.5,  //笼罩层透明度
			  	area: ['400px', '70%'],  //大小
			  	maxmin: true,//是否显示最大化按钮
			  	content: contextPath + '/menuController/toUpdateMenu?menuId='+MENU_ID+'&parentId='+PARENT_ID,
			});
		}
	</script>
</body>
</html>