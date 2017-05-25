<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>添加页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/uploadify/uploadify.css">
</head>

<body>
	<%@ include file="../system/allresources.jsp"%>
	<div class="form-horizontal">
		<table id="table_bug_report" class="table table-striped table-bordered table-hover" style="width: 580px; text-align: center; margin: 10px auto 15px;">
			<tr>
				<td style="text-align: right; width: 60px;">
					<span style="color: red">*</span>
					类型
				</td>
				<td>

					<select id="TYPE" name="TYPE" readonly="readonly">
						<option value="0">请选择</option>
						<option value="1" selected="selected">江西企业客户端</option>
						<option value="2">IOS客户端</option>
						<option value="3">警示系统客户端</option>
						<option value="4">全国企业客户端</option>
						<option value="5">新建区系统客户端</option>
						<option value="6">其他</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">
					<span style="color: red">*</span>
					名称
				</td>
				<td style="text-align: right;">
					<input type="text" id="NAME" name="NAME" style="width: 63%;" maxlength="30" value="${pd.NAME }" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">
					<span style="color: red">*</span>
					版本
				</td>
				<td style="text-align: right;">
					<input type="text" id="VERSION" value="${pd.VERSION }" name="VERSION" style="width: 63%;" maxlength="100" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')" />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">
					<span style="color: red">*</span>
					描述
				</td>
				<td style="text-align: right;">
					<textarea rows="3" cols="3" style="width: 63%;" maxlength="2000" id="DESCS" name="DESCS">${pd.REMARK }</textarea>
				</td>
			</tr>
			<tr>
				<td>
					<span style="color: red">*</span>
					上传文件
				</td>
				<td>
					<input type="file" name="uploadFile" id="uploadFile">
					<div id="uploadFileList">
						<c:if test="${pd.PATH !='' }">
							<div id="SWFUpload_1_0" class="uploadify-queue-item">
								<div class="cancel">
									<a href="javascript:$('#uploadFile').uploadify('cancel', 'SWFUpload_1_0')">X</a>
								</div>
								<span class="fileName">${pd.NAME}</span>
								<span class="data"></span>
								<div class="uploadify-progress">
									<div class="uploadify-progress-bar" style="width: 100%;">
										<!--Progress Bar-->
									</div>
								</div>
							</div>
						</c:if>
					</div>
				</td>
			</tr>
		</table>
		<input type="hidden" id="ID" name="ID" style="width: 63%;" maxlength="30" value="${pd.ID }" />
		<div class="layui-layer-btn" style="margin: 30px auto;">
			<a class="btn btn-confirm" onclick="submit()">确定</a>
			<a class="btn btn-cancel" onclick="parent.layer.closeAll('iframe')">关闭</a>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/uploadify/jquery.uploadify.js"></script>
	<script>
		var FileCounnt = 0;
		var filename = "";
		$(function() {
			$("#uploadFile")
					.uploadify(
							{
								'auto' : true,//是否自动上传 true or false
								'successTimeout' : 99999, //超时时间上传成功后，将等待服务器的响应时间。在此时间后，若服务器未响应，则默认为成功(因为已经上传,等待服务器的响应) 单位：秒
								'removeCompleted' : false,
								'buttonText' : '选择文件', //按钮上的文字
								'swf' : "${pageContext.request.contextPath}/static/plugins/uploadify/uploadify.swf", //flash
								'queueID' : 'uploadFileList',//文件选择后的容器div的id值
								'fileObjName' : 'pic',//将要上传的文件对象的名称 必须与后台controller中抓取的文件名保持一致   
								'uploader' : '${pageContext.request.contextPath}/filesController/FileUpload',//上传地址
								'width' : '100',//浏览按钮的宽度
								'height' : '25',//浏览按钮的高度
								'fileTypeDesc' : '支持的格式：',//在浏览窗口底部的文件类型下拉菜单中显示的文本
								'fileTypeExts' : '*.apk',//允许上传的文件后缀
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
												+ "] 大小超出系统限制的20MB大小！");
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
										NAME : $('#NAME').val(),
										VERSION : $('#VERSION').val(),
										REMARK : $('#DESCS').val(),
										TYPE : $('#TYPE').val(),
										ID : $('#ID').val(),
										PATH:  filepath.filePath+"\\"+filepath.fileName,
									}
									$
											.ajax({
												url : contextPath
														+ '/VersionController/updatesVersionById',
												timeout : 300000,
												dataType : "json",
												type : "post",
												data : formdata,
												success : function(data) {
													if (data.status == "success") {
														layer
																.alert(
																		"修改成功",
																		{
																			icon : 1
																		},
																		function(
																				index) {
																			parent
																					.setTimeout(
																							"location.reload()",
																							100); //父页面刷新 必须在关闭iframe之前
																			parent.layer
																					.closeAll('iframe');//关闭弹窗
																			ShowLoading();
																		});
													} else {
														layer
																.alert(
																		"修改失败",
																		{
																			icon : 2
																		},
																		function(
																				index) {
																			parent
																					.setTimeout(
																							"location.reload()",
																							100); //父页面刷新 必须在关闭iframe之前
																			parent.layer
																					.closeAll('iframe');//关闭弹窗
																			ShowLoading();
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
			if ($('#TYPE').val() == 0) {
				layer.tips('不能为空', '#TYPE', {
					tips : [ 2, '#78BA32' ],
					tipsMore : true
				});
				return;
			}
			if ($('#NAME').val() == "" || $('#NAME').val() == null) {
				layer.tips('不能为空', '#NAME', {
					tips : [ 2, '#78BA32' ],
					tipsMore : true
				});
				return;
			}
			if ($('#VERSION').val() == "" || $('#VERSION').val() == null) {
				layer.tips('不能为空', '#VERSION', {
					tips : [ 2, '#78BA32' ],
					tipsMore : true
				});
				return;
			}
			if ($('#DESCS').val() == "" || $('#DESCS').val() == null) {
				layer.tips('不能为空', '#DESCS', {
					tips : [ 2, '#78BA32' ],
					tipsMore : true
				});
				return;
			}
			if ($("#uploadFileList").find(".uploadify-queue-item").length <= 0) {
				layer.tips('请上传apk', '#uploadFile', {
					tips : [ 3, '#78BA32' ],
					tipsMore : true
				});
				return;
			}
			if (FileCounnt < 1) {
				var formdata = {
					NAME : $('#NAME').val(),
					VERSION : $('#VERSION').val(),
					REMARK : $('#DESCS').val(),
					TYPE : $('#TYPE').val(),
					ID : $('#ID').val()
				}
				$
						.ajax({
							url : contextPath
									+ '/VersionController/updatesVersionById',
							timeout : 300000,
							dataType : "json",
							type : "post",
							data : formdata,
							success : function(data) {
								if (data.status == "success") {
									layer.alert("修改成功", {
										icon : 1,closeBtn: 0
									}, function(index) {
										parent.setTimeout("location.reload()",
												100); //父页面刷新 必须在关闭iframe之前
										parent.layer.closeAll('iframe');//关闭弹窗
										ShowLoading();
									});
								} else {
									layer.alert("修改失败", {
										icon : 2,closeBtn: 0
									}, function(index) {
										parent.setTimeout("location.reload()",
												100); //父页面刷新 必须在关闭iframe之前
										parent.layer.closeAll('iframe');//关闭弹窗
										ShowLoading();
									});
								}
							}
						})
			} else {
				$('#uploadFile').uploadify('upload', '*');
			}

		}
	</script>
</body>
</html>
