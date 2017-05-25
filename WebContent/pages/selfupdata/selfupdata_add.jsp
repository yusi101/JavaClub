<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>企业自主更新</title>
	<%@ include file="../system/allresources.jsp"%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/uploadify-img/uploadify.img.css">
</head>

<body> 
<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div style="margin: 0 auto;">
				<div  id="queryEnterprise" class="form-horizontal" style="margin: 10px 0;">
					<div style="text-align: right;">
						<div>
							<form action="${pageContext.request.contextPath}/selfUpdataController/createSelfUpdata" method="post" name="selfUpdataInfo" id="selfUpdataInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
								<table id="table_bug_report" class="table table-striped table-bordered table-hover">
									<input type="hidden" id="entName" name="entName" value="${entMap.entname }"/>
									<input type="hidden" id="pripid" name="pripid" value="${entMap.pripid }"/>
									<input type="hidden" id="TITLEIMG" name="TITLEIMG" />
									<tr>
										<td style="text-align:right;width:60px;">企业名称</td>
										<td>${entMap.entname }</td>
									</tr>
									<tr>
										<td style="text-align:right;width:60px;">标题</td>
										<td><input type="text" id="title" name="title" style="width:50%;" maxlength="20"/></td>
									</tr>
									<tr>
										<td style="text-align:right;width:60px;">主题</td>
										<td>
											<input type="file" name="uploadImg" id="uploadImg" maxlength="5">
											<div id="uploadImgList"></div>
										</td>
									</tr>
									<tr>
										<td style="text-align:right;">内容</td>
										<td id="nr">
											 <script id="editor" name="content" type="text/plain" style="width:80%; height:259px;"></script>					
										</td>
									</tr>
									<tr>
										<td colspan="2" style="text-align:right;">
											<input type="button" class="btn btn-confirm" onclick="add()" value="确定"/>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 编辑框-->
	<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "${pageContext.request.contextPath}/static/plugins/ueditor/";</script>  
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/ueditor/ueditor.all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/uploadify-img/jquery.uploadify.img.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/util/upload_img.js"></script>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/selfupdata.js"></script>
	<script type="text/javascript">
		setTimeout("ueditor()",500);
		function ueditor(){
			var ue = UE.getEditor('editor');
		}
	</script>		
</body>
</html>