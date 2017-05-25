<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>牌照申请</title>
	<%@ include file="../system/allresources.jsp"%>
</head>

<body style="background: #FFF">
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">牌照申请</h4>
			<form action="${pageContext.request.contextPath}/qrCodeController/queryEnteraddition" method="post" name="queryBrandInfo" id="queryEntnameInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 40%; float: left;">
					<input type="button" class="btn btn-add" onclick="fileOpen();"  value="导入EXCEL">
					<div id="uploadFile" name="uploadFile" style="display:none;"></div> 
					<input type="button" class="btn btn-confirm" onclick="uploadExcel();" value="下载导入模板">
				</div>
				<div style="width: 60%; float: left; text-align: right;">
					<span id="searchDes">企业名称</span>：<input type="text" placeholder="请输入企业名称" id="entname" name="entname" maxlength="30" value="${pd.entname}" style="vertical-align: top;" />
					类型：<select placeholder="--请选择类型--" name="searchType" onchange="select()" id="selectType" style="width: 120px; text-align: center;">
						<option value='0' <c:if test="${pd.searchType == 0}">selected</c:if>>企业名称</option>
						<option value='4' <c:if test="${pd.searchType == 4}">selected</c:if>>新企业</option>						
						<option value='1' <c:if test="${pd.searchType == 1}">selected</c:if>>法定代表人</option>
						<option value='2' <c:if test="${pd.searchType == 2}">selected</c:if>>品牌名称</option>
						<option value='3' <c:if test="${pd.searchType == 3}">selected</c:if>>失信人</option>
					</select>
					<button type="button" class="btn btn-search" onclick="mySearch();" ><i class="fa fa-search"></i> 搜索</button>
				</div>
			</form>
			<!-- 导入Excel -->
			<form id="uploadForm" method="post" enctype="multipart/form-data">  
				<input type="file" id="upload" name="upload" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="preview()" 
				style="position:absolute;left:-130px;top:50px;opacity: 0;
				background-color: #000000; /* background color for IE */filter: alpha(opacity=0); /* opacity filter for IE */background-color: rgba(0, 0, 0, 0.7);"/>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="15%">企业名称</th>
						<th width="10%">法定代表人</th>
						<th width="15%">登记机关</th>
						<th width="15%">企业类型</th>
						<th width="10%">登记状态</th>
						<th width="10%">登记时间</th>
						<th width="20%">操作</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:if test="${!empty Enteraddition }">
						<c:forEach items="${Enteraddition }" var="list" varStatus="status">
							<tr>
								<td>${status.index + 1 }</td>
								<td>${list.ENTNAME }</td>
								<td>${list.NAME }</td>
								<td>${list.REGORG_CN }</td>
								<td>${list.ENTTYPE_CN }</td>
								<td>${list.REGSTATE_CN }</td>
								<td>${list.ESTDATE }</td>
								<td>
									<c:if test="${list.REGSTATE_CN != '注销' }">
										<button class="btn btn-apply" onclick="openApplyQRCode('${list.PRIPID }','${list.REGNO }','${list.ENTNAME }','${list.UNISCID }','${list.C_PROVINCE }')">申请牌照</button>
									</c:if>
									<c:if test="${list.REGSTATE_CN == '注销' }">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</c:if>
									<button class="btn btn-detail" onclick="openApplyRecord('${list.PRIPID }')">申请记录</button>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty Enteraddition }">
						<tr>
							<td colspan="8" style="color: red; text-align: center;">
								<div align="center">
									<img src="${pageContext.request.contextPath}/static/images/nodata.png" style="width: 100px; margin: 10px auto;">
								</div>
								<div style="margin-bottom: 10px;">小查没有查到数据哦~</div>
							</td>
						</tr>
					</c:if>						
				</tbody>
			</table>
			<div class="position-relative">
				<table style="width: 100%;">
					<tr>
						<td style="vertical-align: top;">
							<div class="pagination" style="float: left; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/uploadify/jquery.uploadify.js"></script>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcode.js"></script>
	<!-- <script type="text/javascript">
	$(function() {
		$("#uploadFile")
				.uploadify(
						{
							'auto' : true,//是否自动上传 true or false
							'successTimeout' : 99999, //超时时间上传成功后，将等待服务器的响应时间。在此时间后，若服务器未响应，则默认为成功(因为已经上传,等待服务器的响应) 单位：秒
							'removeCompleted' : false,
							'swf' : "${pageContext.request.contextPath}/static/plugins/uploadify/uploadify.swf", //flash
							'fileObjName' : 'pic',//将要上传的文件对象的名称 必须与后台controller中抓取的文件名保持一致   
							'uploader' : '${pageContext.request.contextPath}/filesController/FileUpload',//上传地址
							'fileTypeDesc' : '支持的格式：',//在浏览窗口底部的文件类型下拉菜单中显示的文本
							'fileTypeExts' : '*.xls;*.xlsx',//允许上传的文件后缀
							'fileSizeLimit' : '20MB',
							'queueSizeLimit' : 1, //允许上传的文件的最大数量。当达到或超过这个数字，onSelectError事件被触发。
							'uploadLimit' : 1,
							'onSelect' : function(file) {
								if ($("#" + file.id).parent().find(
										".uploadify-queue-item").length > 1) {
									$("#" + file.id).parent().find(
											".uploadify-queue-item").eq(0)
											.remove();
								}
							},
							//返回一个错误，选择文件的时候触发
							'onSelectError' : function(file, errorCode,
									errorMsg) {
								switch (errorCode) {
								case -100:
									alert("上传的文件数量已经超出系统限制的"
											+ $('#uploadFile').uploadify(
													'settings',
													'queueSizeLimit')
											+ "个文件！");
									break;
								case -110:
									alert("文件 ["
											+ file.name
											+ "] 大小超出系统限制的"
											+ $('#uploadCarouseImg')
													.uploadify('settings',
															'fileSizeLimit')
											+ "大小！");
									break;
								case -120:
									alert("文件 [" + file.name + "] 大小异常！");
									break;
								case -130:
									alert("文件 [" + file.name + "] 类型不正确！");
									break;
								}
							},
							'onUploadSuccess' : function(file, data,
									response) {//上传到服务器，服务器返回相应信息到data里
								var filepath = JSON.parse(data);
								var formdata = {
									PATH:  filepath.filePath+"\\"+filepath.fileName,
								}
								$
										.ajax({
											url : contextPath
													+ '/qrCodeController/batchQRCodeAtExcel',
											timeout : 300000,
											dataType : "json",
											type : "post",
											data : formdata,
											success : function(result) {
												//拆分出失败的企业信息和成功导入的企业个数
										    	var data = result.split("::");
										    	//获取失败的企业信息
												var entinfo = JSON.parse(data[0]);
												//获取成功导入的企业个数
												var entCount = data[1];
												var str;
												
												//关闭加载中
												HideLoading();
												
												if(entinfo.length != 0){
													str = "<table class='table table-condensed table-bordered table-hover tab'>";
													str += "<thead style='background:#FFF;'><tr>" 
														+ "<th colspan='2' style='color:#000;'>一共成功导入<font color='red'>" + entCount + "</font>家企业，有<font color='red'>" + entinfo.length + "</font>家企业申请失败。</th>" 
														+ "</tr></thead>";
													str += "<tbody><tr><td>企业名称</td><td>注册号</td></tr>";
													for(var i = 0; i < entinfo.length; i++){
														str += "<tr><td>" + entinfo[i].entname + "</td><td>" + entinfo[i].regno + "</td></tr>";
													}
													str += "</tbody></table>";
													
													//提示
													layer.open({
														type: 1,
														skin: 'layui-layer-rim', //加上边框
														area: ['420px', '240px'], //宽高
														content: str,
														cancel: function(index){ 
															//刷新父窗口信息
															setTimeout("location.reload()",100); 
														} 
													});
												}else if(0 == entCount){
													//全部导入失败
													layer.alert('导入失败！', {icon: 1}, function(){
														//刷新父窗口信息
														setTimeout("location.reload()",100); 
													});
												}else{
													layer.alert('申请成功！', {icon: 1},function(){
														//刷新父窗口信息
														setTimeout("location.reload()",100); 
													});
												}
											}
										})
							},
							'onUploadError' : function(file, errorCode,
									errorMsg, errorString) { //当单个文件上传出错时触发
							},
							'onQueueComplete' : function(queueData) {
							}

						});

	});
	//点击Excel上传
	function fileOpen(){
		$('#uploadFile').uploadify('upload', '*');
	}
	</script> -->
</body>
</html>