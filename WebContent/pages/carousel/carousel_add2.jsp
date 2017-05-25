<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>添加轮播图</title>
	<meta charset="utf-8" />
	<meta name="description" content="overview & stats" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/uploadify/uploadify.css">
</head>
<body>
<%@ include file="../system/allresources.jsp"%>
	<div style="margin: 10px;">
			<table id="table_bug_report" class="table table-striped table-bordered table-hover">
				<tr>
					<td style="text-align: right;width:60px;">轮播图名称</td>
					<td>
						<input type="text" name="name" id="name" placeholder="这里输入轮播图名称" maxlength="30"/>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;width:60px;">排序号</td>
					<td>
						<input type="number" name="orderBy" id="orderBy" placeholder="这里输入排序号" maxlength="3" onkeyup="value=value.replace(/[^1-9]/g,'')" onblur="value=value.replace(/[^1-9]/g,'')" />
					</td>
				</tr>
				<tr>
					<td style="text-align: right;width:60px;">链接地址</td>
					<td>
						<textarea rows="3" cols="3" style="width:98%;height:80px;" name="toUrl" id="toUrl" placeholder="这里输入链接地址" maxlength="255"></textarea>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;width:60px;margin-bottom: 10px;">图片</td>
					<td>
						<input type="file" name="uploadCarouseImg" id="uploadCarouseImg">
						<div id="CarouseImgList"></div>
					</td>
				</tr>
			</table>
			<div class="layui-layer-btn">
				<a class="btn btn-confirm" onclick="javascript:$('#uploadCarouseImg').uploadify('upload','*')">确定</a>
				<a class="btn btn-cancel" onclick="parent.layer.closeAll('iframe')">关闭</a>
		</div>
	</div>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/uploadify/jquery.uploadify.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/carousel.js"></script>
		<script type="text/javascript">
	$(function() {
		$("#uploadCarouseImg").uploadify({
			'auto' : false,//是否自动上传 true or false
			'successTimeout' : 99999, //超时时间上传成功后，将等待服务器的响应时间。在此时间后，若服务器未响应，则默认为成功(因为已经上传,等待服务器的响应) 单位：秒
			'removeCompleted' : false,
			'onUploadStart' : function(file) {
				$("#uploadCarouseImg").uploadify("settings",
					"formData", {
						'key2' : ''
					});
			},
			'buttonText' : '选择文件', //按钮上的文字
			'swf' : "${pageContext.request.contextPath}/static/plugins/uploadify/uploadify.swf", //flash
			'queueID' : 'CarouseImgList',//文件选择后的容器div的id值
			'fileObjName' : 'pic',//将要上传的文件对象的名称 必须与后台controller中抓取的文件名保持一致   
			'uploader' : '${pageContext.request.contextPath}/carouselController/FileUpload',//上传地址
			'width' : '100',//浏览按钮的宽度
			'height' : '25',//浏览按钮的高度
			'fileTypeDesc' : '支持的格式：',//在浏览窗口底部的文件类型下拉菜单中显示的文本
			'fileTypeExts' : '*.jpg;*.jpge;*.gif;*.png',//允许上传的文件后缀
			'fileSizeLimit' : '100KB',
			'queueSizeLimit' : 2, //允许上传的文件的最大数量。当达到或超过这个数字，onSelectError事件被触发。
			'onSelect' : function(file) { //选择上传文件后调用
				//alert("123");    
			},
			//返回一个错误，选择文件的时候触发
			'onSelectError' : function(file, errorCode,
					errorMsg) {
				switch (errorCode) {
				case -100:
					alert("上传的文件数量已经超出系统限制的"+ $('#uploadCarouseImg').uploadify('settings','queueSizeLimit') + "个文件！");
					break;
				case -110:
					alert("文件 ["+ file.name+ "] 大小超出系统限制的"+ $('#uploadCarouseImg').uploadify('settings','fileSizeLimit')+ "大小！");
					break;
				case -120:
					alert("文件 [" + file.name + "] 大小异常！");
					break;
				case -130:
					alert("文件 [" + file.name + "] 类型不正确！");
					break;
				}
			},
			'onUploadSuccess' : function(file, data, response) {//上传到服务器，服务器返回相应信息到data里
				alert("上传成功" + data);
			},
			'onUploadError' : function(file, errorCode,
					errorMsg, errorString) { //当单个文件上传出错时触发
				alert("上传失败" + errorMsg);
			},
			'onQueueComplete' : function(queueData){
				alert(queueData.uploadsSuccessful);
			}
			
		});
	});
</script>
</body>
</html>