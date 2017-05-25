<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>企业自主更新</title>
	<%@ include file="../system/allresources.jsp"%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/uploadify-img/uploadify.img.css">
	<style type="text/css">
	.edui-editor,.edui-editor-iframeholder{
	  width: 100% !important;
	}
	</style>
</head>

<body> 
<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div style="margin: 0 auto;">
				<div  id="queryEnterprise" class="form-horizontal" style="margin: 10px 0;">
					<div style="text-align: right;">
						<div>
							<form action="${pageContext.request.contextPath}/selfUpdataController/createSelfUpdata" method="post" name="selfUpdataInfo" id="selfUpdataInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
								<c:forEach items="${selfUpdataInfo}" var="list" varStatus="status">
									<table id="table_bug_report" class="table table-bordered table-hover">
										<input type="hidden" id="TITLEIMG" name="TITLEIMG" />
										<tr>
											<td style="text-align:right;width:60px;">企业名称</td>
											<td style="text-align:left;">${list.ENTNAME}</td>
										</tr>
										<tr>
											<td style="text-align:right;width:60px;">标题</td>
											<td style="text-align:left;"><input type="text" id="title" name="title" style="width:50%;float:left;" value="${list.TITLE}" maxlength="20"/></td>
										</tr>
										<tr>
											<td style="text-align:right;width:60px;">主题</td>
											<td style="text-align:left;">
												<input type="file" name="uploadImg" id="uploadImg" maxlength="1">
												<div id="uploadImgList">
													<c:if test="${not empty list.TITLEIMG }">
														<div id="SWFUpload_1_0" class="uploadify-queue-item">
															<div class="cancel">
																<a href="javascript:$('#uploadImg').uploadify('cancel', 'SWFUpload_1_0');$('#uploadImg').uploadifySettings('uploadLimit',1)"></a>
															</div>
															<div class="data">
																<img src="data:image/png;base64,${list.TITLEIMG}">
															</div>
															<!-- <span class="fileName" title="头像.jpg">(42KB)头像.jpg</span> -->
														</div>
													</c:if>	
												</div>
											</td>
										</tr>
										<tr>
											<td style="text-align:right;">内容</td>
											<td id="nr">
												 <script id="editor" name="content" type="text/plain" style="width:90%; height:259px;">${list.CONTENT}</script>					
											</td>
										</tr>
										<tr>
											<td colspan="2" style="text-align:right;"><input type="button" class="btn btn-confirm" onclick="upd('${list.ID}')" value="确定"/></td>
										</tr>
									</table>
								</c:forEach>
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