<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/zTree/3.5/zTreeStyle.css" type="text/css">
<title>短信模板</title>
<style type="text/css"> 
body 
{ 
font-family: tahoma; 
font-size: 12px; 
} 
input[type=checkbox] 
{ 
vertical-align: middle; 
padding: 2px; 
} 
label 
{ 
vertical-align: middle; 
} 
</style> 
<body>
	<%@ include file="../system/allresources.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid" float:left;">
			<h4 class="title">编辑短信模板</h4>
			<table id="table_bug_report" class="table table-striped table-bordered table-hover">
					<tr>
						<td class="center" width="25%">短信标题</th>
						<td class="center">
							<input type="text" id="title" name="title"  maxlength="20" value="${templateList.get(0).SMS_TITLE }"/>
						</td>
					</tr>
					<tr >
						<td class="center" width="25%" >短信内容</th>
						<td class="center">
							<textarea style="min-height: 60px;" placeholder="请输入内容" type="text" name="content" id="content" maxlength="255" onkeyup="count();" oninput="this.style.height='0px';this.style.height=(this.scrollHeight+'px');">${templateList.get(0).SMS_CTX }</textarea>
							<div  class="textarea_status" id="stats"></div>
						</td>
					</tr>
					<tr>
						<td class="center" colspan=2>
						<c:if test="${templateList.get(0).ACTIVE =='0' }"><input id="status" type="checkbox" /></c:if>
						<c:if test="${templateList.get(0).ACTIVE =='1' }"><input id="status" type="checkbox" checked="checked"/></c:if>	
							<label for="Checkbox1">激活</label> 
						</td>
					</tr>
					<tr>
						<td class="center" colspan="2">
							<input type="button" class="btn btn-confirm" onclick="update('${templateList.get(0).ID }')" value="保存"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn btn-confirm" onclick="parent.layer.closeAll('iframe')" value="关闭"/>
						</td>
					</tr>
			</table>
			
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/smstemplate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>

	<script type="text/javascript">
	</script>
</head>
</body>
</html>