<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>牌照申请记录</title>
	<%@ include file="../system/allresources.jsp"%>
	<style type="text/css">
		.selected{
			height: 300px; 
			width: 100%; 
			border:none;
			overflow: auto;
		}
		.content{
			height: 350px; 
			width: 50%; 
			margin-top:20px; 
			border:1px solid #CCC; 
			position: absolute; 
			z-index:20; 
			right:18px; 
			top:70px; 
			background: #FFF; 
			display: none;
		}
		.count{
			padding: 0px 3px; 
			text-align: cneter !important; 
			background: red; 
			color: #FFF; 
			line-height: 20px;
			position:absolute; 
			margin-left: -14px;
		}
	</style>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">牌照申请记录查询</h4>
			<div style="width: 100%; float: right; text-align: right;">
				<input type="text" placeholder="请输入企业名称" name="entname" id="entname" maxlength="20" style="vertical-align: top;" />
				<button class="btn btn-search"  onclick="getqrCodeList(1);" ><i class="fa fa-search"></i> 搜索</button>
				<input class="btn btn-add" id="showDown" value="展开" type="button" onclick="selected(this);"/>
				<span id="count" class="count">0</span>
			</div>
			<div style="height: auto; width: 100%; border: none; margin-top:50px;" onmouseout="selectedHide()">
				<table id="qrcodeList" class="table table-condensed table-bordered table-hover tab">
					<thead>
						<tr>
							<th width="25%">企业名称</th>
							<th width="15%">申请方式</th>
							<th width="15%">申请类型</th>
							<th width="15%">申请人姓名</th>
							<th width="10%">申请数量</th>
							<th width="10%">申请时间</th>
							<th width="10%">操作</th>
						</tr>
					</thead>
					<tbody>
					
					</tbody>
				</table>
			</div>
			<div id="selectedContent" class="content">
				<div class="selected">
					<table id="selected" class="table table-condensed table-bordered table-hover tab" style="margin-bottom: 0px;">
						<thead>
							<tr>
								<th width="25%">企业名称</th>
								<th width="15%">申请方式</th>
								<th width="15%">申请类型</th>
								<th width="15%">申请人姓名</th>
								<th width="10%">申请数量</th>
								<th width="10%">申请时间</th>
								<th width="10%">操作</th>
							</tr>
						</thead>
						<tbody>
	
						</tbody>
					</table>
				</div>
				<div style="width:100%; text-align: center; height: 40px; margin-top: 10px;">
					<button class="btn btn-through" onclick="addPrint()">打印</button>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcode.js"></script>	
	<script type="text/javascript">
		//调用ajax请求第一页数据
		getqrCodeList(1);
	
		//ajax请求数据
		function getqrCodeList(currentPage){
			$.ajax({
				type : "post",
				url : contextPath + "/qrCodeController/queryQRCodeInfoAjax",
				data : {
					currentPage : currentPage,		//当前页
					funName : "getqrCodeList",		//分页方法
					entname : $("#entname").val(),	//搜索条件
					status : 1						//审核通过
				},
				success : function(result) {
					//获取数据结果集
					var data = JSON.parse(result).data;
					//获取分页信息
					var page = JSON.parse(result).page;
			        var str = "";
			        //拼接标签，显示结果集内容
			        for (var i = 0; i < data.length; i++) {
			        	str += '<tr id=' + data[i].ID + '>'
						+ '<td>' + data[i].ENTNAME 
							+ '<span class="APPLYID" style="display:none;">' + data[i].ID + '</span>' 
							+ '<span style="display:none;">' + data[i].PRIPID + '</span>' 
							+ '<span style="display:none;">' + data[i].ENTNAME + '</span>' 
						+ '</td>'
						+ '<td>' + data[i].APPLYWAY_CN + '</td>'
						+ '<td>' + data[i].APPLYTYPE_CN + '</td>'
						+ '<td><c:out value="' + data[i].APPLY_NAME + '" ></c:out></td>'
						+ '<td>' + data[i].APPLY_NUMBER + '</td>'
						+ '<td>' + data[i].CREATETIME + '</td>';	        	
						if(selectedIds.indexOf(data[i].ID) >= 0) {
							str += '<td><a href="javascript:void(0)" onclick="moveInData(this,2)" >移出</a></td>';
						} else {
							str += '<td><a href="javascript:void(0)" onclick="moveInData(this,1)" >移入</a></td>';
						}
						str += "</tr>";
			         }
			       
					$("#qrcodeList tbody").html(str);
					
					//判断分页
					if($("#" + page.funName + "Peging").length && $("#"+page.funName+"Peging").length > 0){   
						$("#" + page.funName + "Peging").html(page.pageStr); 
					} else {     
						$("#qrcodeList").after("<div id='" + page.funName + "Peging'>" + page.pageStr + "</div>"); 
					}
				}
			});
		}
		
		//选择的移除ID
		var selectedIds = "";
			
		//移入移出
		function moveInData(obj,status){
			//1为移入，2为移出
			if(status == 1){
				//获取移入的子节点
				var moveInDocument = $(obj).parent().parent();
				//获取移入的子节点ID
				selectedIds += moveInDocument.find(".APPLYID").html() + "::";
				//修改子节点文字和点击事件
				moveInDocument.find("a").html("移出");
				moveInDocument.find("a").attr("onclick","moveInData(this,2)");
				//添加到移入表中
				$("#selected").find("tbody").append(moveInDocument.prop('outerHTML'));
				
				//显示移入个数
				var count = $("#selected tbody").children("tr").length;
				$("#count").html(count);
			}else{
				//获取移出的子节点
				var moveOutDocument = $(obj).parent().parent();
				//获取移出的子节点ID
				var cancalId = moveOutDocument.find(".APPLYID").html();
				//修改移出的子节点名称和点击事件
				$("#qrcodeList tbody").find("tr[id="+cancalId+"]").find("a").html("移入");
				$("#qrcodeList tbody").find("tr[id="+cancalId+"]").find("a").attr("onclick","moveInData(this,1)");
				//移出掉选择的子节点
				$("#selected tbody tr").remove("#" + cancalId); 
				//删除选择移出的子节点ID
				selectedIds = selectedIds.replace(cancalId,"");
				
				//显示移出个数
				var count = $("#selected tbody").children("tr").length;
				$("#count").html(count);
			}
		}
		
		//添加牌照打印信息
		function addPrint(){
			var printInfo = "";
			
			//获取参数
			$("#selected tbody tr").each(function(){
				printInfo += $(this).find("td").eq(0).find("span").eq(0).text() + ","
				+ $(this).find("td").eq(0).find("span").eq(1).text() + ","
				+ $(this).find("td").eq(0).find("span").eq(2).text() + ","
				+ $(this).find("td").eq(4).text() + "::";
			});
			
			//判断如果没有移入的值就停止
			if("" == printInfo || null == printInfo){
				return;
			}

		    //开启正在加载
		    ShowLoading();
		    //获取的参数
		    var formdata = {
		    	printInfo: printInfo
		    }
		    
		    //访问请求
		    $.ajax({
		        url: contextPath + '/qrCodeController/createQRCodePrint',
		        type: "post",
		        data: formdata,
		        success: function (result) {
		           //获取返回值处理业务
		           if("success" == result){
		        	   layer.alert('添加成功！', {icon: 1},function(){
		        		   setTimeout("location.reload()",100); 
		                   //关闭弹窗
		                   parent.layer.closeAll('iframe');
		        	   });
		           }else{
		        	   layer.alert('添加失败！', {icon: 2})
		           }
		        }
		    })
		}				
	</script>
</body>
</html>