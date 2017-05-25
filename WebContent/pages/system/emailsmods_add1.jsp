<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加邮件模板</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/plugins/uploadify/uploadify.css" />
</head>

<body>
	<%@ include file="../system/allresources.jsp"%>
	<div class="form-horizontal">
		<table id="table_bug_report" class="table table-striped table-bordered table-hover dialog">
			<tr>
				<td style="text-align: right;">
					<span style="color: red">*</span>
					邮件模板名
				</td>
				<td>
					<input type="text" placeholder="请输入邮件模板名" id="modsname" name="modsname" style="width: 80%;" maxlength="30" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">
					<span style="color: red">*</span>
					邮件内容：
				</td>
				<td>
					<textarea placeholder="请输入邮件内容" rows="3" cols="3" style="width: 80%;" maxlength="200" id="remark" name="remark"></textarea>
				</td>
			</tr>
			<tr>
				<td>
					上传文件
				</td>
				<td>
					<input type="file" name="uploadFile" id="uploadFile">
					<div id="uploadFileList"></div>
				</td>
			</tr>
			<tr>
			<td><span style="color: red">*</span>状态：</td>
			<td>
				<input type="hidden" id="stu" name="stu" value="${stu }" />
			<input type="radio" name="type" id="type" value="0"<c:if test="${stu =='0' }">checked="checked"</c:if>/>&nbsp;不激活
				<input type="radio" name="type" id="type" value="1" <c:if test="${stu =='1' }">checked="checked"</c:if>/>&nbsp;激活&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
		</table>
		<div class="layui-layer-btn" style="margin-top:20px">
			<button class="btn btn-confirm" onclick="submit()">确定</button>
			<button class="btn btn-cancel" onclick="parent.layer.closeAll('iframe')">关闭</button>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/uploadify/jquery.uploadify.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#uploadFile")
					.uploadify(
							{
								'auto' : false,//是否自动上传 true or false
								'successTimeout' : 99999, //超时时间上传成功后，将等待服务器的响应时间。在此时间后，若服务器未响应，则默认为成功(因为已经上传,等待服务器的响应) 单位：秒
								'removeCompleted' : false,
								'buttonText' : '选择文件', //按钮上的文字
								'swf' : "${pageContext.request.contextPath}/static/plugins/uploadify/uploadify.swf", //flash
								'queueID' : 'uploadFileList',//文件选择后的容器div的id值
								'fileObjName' : 'pic',//将要上传的文件对象的名称 必须与后台controller中抓取的文件名保持一致   
								'uploader' : '${pageContext.request.contextPath}/filesController/emailFileUpload',//上传地址
								'width' : '100',//浏览按钮的宽度
								'height' : '25',//浏览按钮的高度
								'fileTypeDesc' : '支持的格式：',//在浏览窗口底部的文件类型下拉菜单中显示的文本
								'fileTypeExts' : '*',//允许上传的文件后缀
								'fileSizeLimit' : '20MB',
								'queueSizeLimit' : 3, //允许上传的文件的最大数量。当达到或超过这个数字，onSelectError事件被触发。
								'uploadLimit' : 3,
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
									$.ajax({
										url : contextPath + '/emailsModsController/insertEmailsMods',
										timeout : 300000,
										dataType : "json",
										type : "post",
										data : {
											modsname: $("#modsname").val(),
											remark: $("#remark").val(),
											status: $("input[name='type']:checked").val(),
											mods: filepath.fileName,
											PATH:  filepath.filePath+"\\"+filepath.fileName,
										},
										success : function(data) {
											if(data.status=="success"){
												layer.alert("添加成功",{icon: 1},function(index){
													parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
													parent.layer.closeAll('iframe');//关闭弹窗
													//ShowLoading();
												});
											}else if(data.status=="repeat"){
												layer.alert("添加失败,模板名不能重复",{icon: 2},function(index){
													parent.layer.closeAll('iframe');//关闭弹窗
													//ShowLoading();
												});
											}else{
												layer.alert("添加失败",{icon: 2},function(index){
													parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
													parent.layer.closeAll('iframe');//关闭弹窗
													//ShowLoading();
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
		function submit() {
			if(isEmpty($("#modsname").val())){
				layer.tips('邮件模板名不能为空', '#modsname',{tips:[2, '#78BA32'],tipsMore: true});
				return;
			}
			if(isEmpty($("#remark").val())){
				layer.tips('邮件内容不能为空', '#remark',{tips:[2, '#78BA32'],tipsMore: true});
				return;
			}
			if($("#stu").val() == '1' && $("input[name='type']:checked").val() != '1'){
				layer.tips('首个模板必须为激活状态', '#type',{tips:[1, '#78BA32'],tipsMore: true});
				return;
			}
			ShowLoading();
			if($("#uploadFileList").find(".uploadify-queue-item").length <= 0){
				$.ajax({
					url : contextPath + '/emailsModsController/insertEmailsMods',
					timeout : 300000,
					dataType : "json",
					type : "post",
					data : {
						modsname: $("#modsname").val(),
						remark: $("#remark").val(),
						status: $("input[name='type']:checked").val(),
					},
					success : function(data) {
						if(data.status=="success"){
							layer.alert("添加成功",{icon: 1},function(index){
								parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
								parent.layer.closeAll('iframe');//关闭弹窗
								//ShowLoading();
							});
						}else if(data.status=="repeat"){
							layer.alert("添加失败,模板名不能重复",{icon: 2},function(index){
								parent.layer.closeAll('iframe');//关闭弹窗
								//ShowLoading();
							});
						}else{
							layer.alert("添加失败",{icon: 2},function(index){
								parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
								parent.layer.closeAll('iframe');//关闭弹窗
								//ShowLoading();
							});
						}
					}
				})
			}else{
				$('#uploadFile').uploadify('upload', '*');
			}
		}
	</script>


</body>
</html>
