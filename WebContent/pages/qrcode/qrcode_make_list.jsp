<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>牌照选择制作</title>
	<%@ include file="../system/allresources.jsp"%>
	<link href="${pageContext.request.contextPath}/static/styles/util/imgscale.css" rel="stylesheet" />
	<style type="text/css">
		.select{
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
			<h4 class="title">牌照选择制作</h4>
			<div style="width: 15%; float: left;">
				<a class="btn btn-add" onclick="selectAll();">移入</a>
				<a class="btn btn-del" onclick="disSelectAll();">移出</a>
			</div>
			<div style="width: 85%; float: right; text-align: right;">
				时间：<input id="startTime" type="text" name="startTime" value="${pd.startTime }" placeholder="请选择开始时间" readonly="readonly" />
				至&nbsp;<input id="endTime" type="text" name="endTime" value="${pd.endTime }" placeholder="请选择结束时间" readonly="readonly" />
				企业名称：<input type="text" placeholder="请输入企业名称" name="entname" id="entname" maxlength="20" style="vertical-align: top;" />
				<button type="button" class="btn btn-search"  onclick="getqrCodeList(1);" ><i class="fa fa-search"></i> 搜索</button>
				<input class="btn btn-add" id="showDown" value="展开" type="button" onclick="selected(this);"/>
				<span id="count" class="count">0</span>
			</div>
			<div style="height: auto; width: 100%; border: none; margin-top: 50px;" onmouseout="selectedHide()">
				<table id="qrcodeList" class="table table-condensed table-bordered table-hover tab">
					<thead>
						<tr>
							<th width="5%">
						    	<input type="checkbox" id="zcheckbox" style="vertical-align: text-top"/>
						   	</th>
							<th width="25%">企业名称</th>
							<th width="15%">牌照编码</th>
							<th width="10%">牌照</th>
							<th width="10%">打印数量</th>
							<th width="10%">审核处理人</th>
							<th width="20%">处理时间</th>
							<th width="10%">操作</th>
						</tr>
					</thead>
					<tbody>
					
					</tbody>
				</table>
			</div>
			<div id="selectedContent" class="content">
				<div class="select">
					<table id="selected" class="table table-condensed table-bordered table-hover tab" style="margin-bottom: 0px;">
						<thead>
							<tr>
								<th width="5%" style="display: none;">
							    	<input type="checkbox" id="zcheckbox" style="vertical-align: text-top"/>
							   	</th> 
								<th width="30%">企业名称</th>
								<th width="15%">牌照编码</th>
								<th width="15%" style="display: none;">牌照</th>
								<th width="10%">打印数量</th>
 								<th width="15%" style="display: none;">审核处理人</th>
								<th width="10%" style="display: none;">审核时间</th>
								<th width="10%">操作</th>
							</tr>
						</thead>
						<tbody>
	
						</tbody>
					</table>
				</div>
				<div style="width:100%; text-align: center; height: 40px; margin-top: 10px;">
					<button class="btn btn-through" onclick="addMake()">制作牌照</button>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcode.js"></script>
	<script src="${pageContext.request.contextPath}/static/scripts/util/imgscale.js"></script>
	<script type="text/javascript">
		jeDate({
			dateCell : "#startTime",
			format : "YYYY-MM-DD",
			isinitVal : false, //是否初始化时间
			isTime : false, //是否开启时间选择
			isClear : true, //是否显示清空
			festival : false, //是否显示节日
			maxDate : jeDate.now(0),
			minDate : "2014-09-19",
			okfun : function(val) {
			}
		})
		
		jeDate({
			dateCell : "#endTime",
			format : "YYYY-MM-DD",
			isinitVal : false, //是否初始化时间
			isTime : false, //是否开启时间选择
			isClear : true, //是否显示清空
			festival : false, //是否显示节日
			maxDate : jeDate.now(0),
			minDate : "2014-09-19",
			okfun : function(val) {
			}
		})
	
		//调用ajax请求第一页数据
		getqrCodeList(1);
	
		//ajax请求数据
		function getqrCodeList(currentPage){
		    //开启正在加载
		    ShowLoading();
		    
			$.ajax({
				type : "post",
				url : contextPath + "/qrCodeController/queryQRCodeMakeAjax",
				data : {
					currentPage : currentPage,			//当前页
					funName : "getqrCodeList",			//分页方法
					entname : $("#entname").val(),		//搜索条件
					endTime : $("#endTime").val(),		//结束时间
					startTime :	$("#startTime").val()	//开始时间
				},
				success : function(result) {
					//获取数据结果集
					var data = JSON.parse(result).data;
					//获取分页信息
					var page = JSON.parse(result).page;
			        var str = "";
			        
			        //判断是否有数据
			        if(data.length > 0){
				       //拼接标签，显示结果集内容
				       for (var i = 0; i < data.length; i++) {
				        	str += '<tr id=' + data[i].ID + '>'
					        	+ '<td>'
					        	+ '<input type="checkbox" name="ids"  style="vertical-align: text-top"/>'
					        	+ '</td>' 
								+ '<td>' + data[i].ENTNAME 
								+ '<span class="MAKEID" style="display:none;">' + data[i].ID + '</span>'
								+ '<span style="display:none;">' + data[i].RELATION_ID + '</span>' 
								+ '<span style="display:none;">' + data[i].ENTNAME + '</span>' 
								+ '<span style="display:none;">' + data[i].LICENSENUMBER + '</span>' 
								+ '<span style="display:none;">' + data[i].PRIPID + '</span>' 
								+ '<span style="display:none;">' + data[i].APPLY_NUMBER + '</span>' 
							+ '</td>'
							+ '<td>' + data[i].LICENSENUMBER + '</td>'
							+ '<td><img src="data:image/jpg;base64,' + data[i].LICENSECODE + '" onclick="imgScale(this)" class="code_img"/></td>'
							+ '<td>' + data[i].APPLY_NUMBER + '</td>'
							+ '<td>' + data[i].APPROVEUSER_NAME + '</td>'
							+ '<td>' + data[i].APPROVE_TIME + '</td>';	        	
							if(selectedIds.indexOf(data[i].ID) >= 0) {
								str += '<td><a href="javascript:void(0)" onclick="moveInData(this,2)" >移出</a></td>';
							} else {
								str += '<td><a href="javascript:void(0)" onclick="moveInData(this,1)" >移入</a></td>';
							}
							str += "</tr>";
				        }
			        } else {
			        	str += '<tr>'
			        	+ '<td colspan="8" style="color: red; text-align: center;">'
			        		+ '<div align="center">'
			        			+ '<img src="' + contextPath + '/static/images/nodata.png" style="width: 100px; margin: 10px auto;">'
			        		+ '</div>'
			        		+ '<div style="margin-bottom: 10px;">小查没有查到数据哦~</div>'
			        	+ '</td>'
			        	+ '</tr>';
			        }
			        
					$("#qrcodeList tbody").html(str);
					
					//判断分页
					if($("#" + page.funName + "Peging").length && $("#"+page.funName+"Peging").length > 0){   
						$("#" + page.funName + "Peging").html(page.pageStr); 
					} else {     
						$("#qrcodeList").after("<div id='" + page.funName + "Peging'>" + page.pageStr + "</div>"); 
					}
					
					HideLoading();
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
				selectedIds += moveInDocument.find(".MAKEID").html() + "::";
				//修改子节点文字和点击事件
				moveInDocument.find("a").html("移出");
				moveInDocument.find("a").attr("onclick","moveInData(this,2)");
				//添加到移入表中
				$("#selected").find("tbody").append(moveInDocument.prop('outerHTML'));
				hideSelected();
				//显示移入个数
				var count = $("#selected tbody").children("tr").length;
				$("#count").html(count);
			}else{
				//获取移出的子节点
				var moveOutDocument = $(obj).parent().parent();
				//获取移出的子节点ID
				var cancalId = moveOutDocument.find(".MAKEID").html();
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
		function addMake(){
			var printInfo = "";
	
			//获取参数
			$("#selected tbody tr").each(function(){
				printInfo += $(this).find("td").eq(1).find("span").eq(1).text() + ",";		//打印编号
				printInfo += $(this).find("td").eq(1).find("span").eq(2).text() + ",";		//企业名称
				printInfo += $(this).find("td").eq(1).find("span").eq(3).text() + ",";		//牌照编码
				printInfo += $(this).find("td").eq(1).find("span").eq(4).text() + ",";		//企业身份主体ID
				printInfo += $(this).find("td").eq(1).find("span").eq(5).text() + "::";		//牌照数量
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
		        url: contextPath + '/qrCodeController/createQRCodeMake',
		        type: "post",
		        timeout : 0,
		        data: formdata,
		        success: function (result) {
		        	//如果result为fail，则是后台没有获取到前台传来的牌照信息，制作失败，直接返回失败！
		        	if("fail" == result){
		        	   layer.alert('制作失败！请检查网路！', {icon: 2},function(){
		        		   //刷新页面
		        		   setTimeout("location.reload()",100); 
		                   //关闭弹窗
		                   parent.layer.closeAll('iframe');
		        	   })
		        	   
		        	  return;
		        	}
		        	
					var data = result.split("::");	
					var dataMsg = JSON.parse(data[0]);
					var flag = dataMsg.flag;			//状态
					var entNum = dataMsg.entNum;		//所有制作企业的数量
					var errList = dataMsg.errList;		//重复企业
					var codePath = data[1];				//zip下载地址
					var fileName = data[2];				//zip名称
					var str = "";
					var num = entNum - errList.length;	//制作成功的企业
					
		           //获取返回值处理业务
		           if("success" == flag){
		        	   	if(0 != errList.length){
							str = "<table class='table table-condensed table-bordered table-hover tab'>";
							str += "<thead style='background:#FFF;'><tr><th colspan='2' style='color:#000;'>一共有<font color='green'>" + entNum + "</font>家企业制作牌照，有<font color='green'>" + num + "</font>家制作成功，有<font color='red'>" + errList.length + "</font>家重复制作</th></tr></thead>";
							str += "<tbody><tr><td>企业名称</td></tr>";
							for(var i = 0; i < errList.length; i++){
								str += "<tr><td>" + errList[i].entname + "</td></tr>";
							}
							str += "</tbody></table>";
							
							//提示
							layer.open({
								type: 1,
								skin: 'layui-layer-rim', 	//加上边框
								area: ['420px', '240px'], 	//宽高
								content: str,
								cancel: function(index){ 
									layer.confirm('是否下载制作成功的牌照？', {icon: 3, title:'提示'}, function(index){
										//下载zip
					        		   	window.open("${pageContext.request.contextPath }/qrCodeController/downImg?codePath=" + codePath + "&fileName=" + fileName)
					        		   	//刷新页面
					        		   	setTimeout("location.reload()",100); 
					        			//关闭弹窗
										layer.close(index);
									});   
								} 
							});
		        	   	} else {
		 	         	   layer.alert('制作成功！', {icon: 1}, function(){
								//下载zip
			        		   	window.open("${pageContext.request.contextPath }/qrCodeController/downImg?codePath=" + codePath + "&fileName=" + fileName)
			         		   	//刷新父窗口信息
			         		   	setTimeout("location.reload()",100); 
			        		 	//关闭弹窗
				                parent.layer.closeAll();
			         	   });
		        	   	}
		           }else{
		        	   layer.alert('制作失败！您选择的所有牌照已经制作了', {icon: 2},function(){
		        		   //刷新页面
		        		   setTimeout("location.reload()",100); 
		                   //关闭弹窗
		                   parent.layer.closeAll();
		        	   });
		           }
		        }
		    })
		}
		
		//隐藏移入移除的列项
		function hideSelected(){
			$("#selected").find("tbody").find("tr").each(function(index){
				$("#selected").find("tbody").find("tr").eq(index).find("td").eq(0).hide();
				$("#selected").find("tbody").find("tr").eq(index).find("td").eq(3).hide();
				$("#selected").find("tbody").find("tr").eq(index).find("td").eq(5).hide();
				$("#selected").find("tbody").find("tr").eq(index).find("td").eq(6).hide();
			});
		}
		
		//全部移入
		function selectAll(){
			for(var i = 0; i < $("input[name=ids]").length; i++){
				if(document.getElementsByName('ids')[i].checked){
					if($("#qrcodeList").find("tbody").find("tr").eq(i).find("td").find("a").html()=="移入"){
						$("#qrcodeList").find("tbody").find("tr").eq(i).find("td").find("a").click();
					}
				}
			}
		}
		
		//全部移除
		function disSelectAll(){
			for(var i = 0; i < $("input[name=ids]").length; i++){
				if(document.getElementsByName('ids')[i].checked){
					if($("#qrcodeList").find("tbody").find("tr").eq(i).find("td").find("a").html()=="移出"){
						$("#qrcodeList").find("tbody").find("tr").eq(i).find("td").find("a").click();
					}
				}
			}
		}
	</script>
</body>
</html>