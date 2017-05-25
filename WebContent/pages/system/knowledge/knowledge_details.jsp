<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/zTree/3.5/zTreeStyle.css" type="text/css">
<title>知识库管理</title>
<body>
	<%@ include file="../allresources.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid" style="float:left;">
			<table id="table_bug_report" class="table table-striped table-bordered table-hover" style="margin-top:30px;">
					<tr>
						<td class="center" width="25%">标题</td>
						<td class="center">${list.get(0).TITLE}</td>
					</tr>
					<tr >
						<td class="center" width="25%" >内容</td>
						<td class="center">${list.get(0).CONTENT}</td>
					</tr>
					<tr >
						<td class="center" width="25%" >创建人</td>
						<td class="center">${list.get(0).NAME}</td>
					</tr>
					<tr >
						<td class="center" width="25%" >时间</td>
						<td class="center">${list.get(0).TIME}</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;width:60px;">排序号</td>
						<th class="center">${list.get(0).ORDERBY}</th>
					</tr>
					<tr>
						<th class="center" colspan="2">
							<input type="button" class="btn btn-confirm" onclick="parent.layer.closeAll('iframe')" value="关闭"/>
						</th>
					</tr>
			</table>
			
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/knowledge.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>

	<script type="text/javascript">
	</script>
</head>
</body>
</html>