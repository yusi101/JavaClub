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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/uploadify-img/uploadify.img.css">
</head>
<body>
<%@ include file="../system/allresources.jsp"%>
	<div style="margin: 10px;">
			<table id="table_bug_report" class="table table-striped table-bordered table-hover dialog">
				<tr>
					<td style="text-align: right;width:80px;">轮播图名称</td>
					<td>
						<input type="text" name="name" id="name" placeholder="这里输入轮播图名称" maxlength="10"/>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;width:80px;" >排序号</td>
					<td>
					 	 <input type="text" id="orderBy" style="width:80px;" name="orderBy" maxlength="3" onkeyup="value=value.replace(/[^1-9]/g,'')" onblur="value=value.replace(/[^1-9]/g,'')"  />
					     <label style="font-size:11px;display: initial;color: red;margin-left: 7px;" for="orderBy">值越大越排前面</label>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;width:80px;">链接地址</td>
					<td>
					   <textarea style="min-height: 60px;width:90%;" placeholder="请输入连接地址" type="text" name="toUrl" id="toUrl" maxlength="255" onkeyup="count();" oninput="this.style.height='0px';this.style.height=(this.scrollHeight+'px');"></textarea>
					   <div  class="textarea_status" id="stats"></div>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;width:80px;margin-bottom: 10px;">图片</td>
					<td>
						<input type="file" name="uploadImg" id="uploadImg" maxlength="5">
						<div id="uploadImgList"></div>
					</td>
				</tr>
				
			</table>
			<div style="text-align: center;margin-top:20px">
				<button class="btn btn-confirm" onclick="addcarousel()">确定</button>
				<button class="btn btn-cancel" onclick="parent.layer.closeAll('iframe')">关闭</button>
			</div>
	</div>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/uploadify-img/jquery.uploadify.img.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/util/upload_img.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/carousel.js"></script>
		
</body>
</html>