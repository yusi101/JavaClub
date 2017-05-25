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
		<div class="row-fluid" float:left;">
			<h4 class="title">新增知识</h4>
			<table id="table_bug_report" class="table table-striped table-bordered table-hover">
					<tr>
						<th class="center" width="25%">标题</th>
						<th class="center">
							<input type="text" id="title" name="title"  maxlength="20"/>
						</th>
					</tr>
					<tr >
						<th class="center" width="25%" >内容</th>
						<th class="center">
							<textarea style="min-height: 60px;" placeholder="请输入内容" type="text" name="content" id="content" maxlength="255" onkeyup="count();" oninput="this.style.height='0px';this.style.height=(this.scrollHeight+'px');"></textarea>
							<div  class="textarea_status" id="stats"></div>
						</th>
					</tr>
					<tr>
						<td class="center">排序号</td>
						<td class="center">
						<input  type="number" name="MENU_ORDER" id="menuOrder" onkeyup="value=value.replace(/^[0][0-9]*$/,'')" onblur="value=value.replace(/^[0][0-9]*$/,'')" placeholder="这里输入序号" value="1" title="序号" />
						</td>
					</tr>
					<tr>
						<th class="center" colspan="2">
							<input type="button" class="btn btn-confirm" onclick="add()" value="保存"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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