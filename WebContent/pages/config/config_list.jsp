<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<%@ include file="../system/allresources.jsp" %>
	<title>配置项</title>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">配置项列表</h4>
			<form action="${pageContext.request.contextPath}/configController/queryConfiglistPage" method="post" id="queryConfig" class="form-horizontal" style="margin: 10px 0;height: 30px;">
				<div style="width: 60%; float: left;">
					<a class="btn btn-add" onclick="openadd();"><i class="fa fa-plus"></i>&nbsp;新增</a>
					<a class="btn btn-del" onclick="opendel();"><i class='fa fa-trash'></i>&nbsp;删除</a>
				</div>
				<div style="width: 40%; float: left; text-align: right;">
					配置名称：<input type="text" placeholder="请输入配置名称" name="configName" value="${pd.configName }" style="vertical-align: top;" />
					<button class="btn btn-search"  onclick="search();" ><i class="fa fa-search"></i>&nbsp;搜索</button>
				</div>
				<div class="clren"></div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="5%"><input type="checkbox" /></th>
						<th width="10%">配置名称</th>
						<th width="10%">配置项键</th>
						<th width="20%">配置项值</th>
						<th width="20%">配置项描述</th>
						<th width="15%">父节点名称</th>
						<th width="5%">排序号</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:forEach items="${configList }" var="list" varStatus="status">
						<tr>
							<td>
							     <input type="checkbox" name="ids" value="${list.ID}" />
							</td>
							<td>${list.NAME }</td>
							<td>${list.KEYNAME }</td>
							<td>${list.VALUE }</td>
							<td>${list.REMARK }</td>
							<td>
								<c:if test="${list.ID == '1' }">
									根
								</c:if>
								<c:if test="${list.ID != '1' }">
									${list.PARENT }
								</c:if>
							</td>
							<td>${list.ORDER_BY }</td>						
							<td>
								<a class="btn btn-edit" onclick="openupdate('${list.ID }');"><i class="fa fa-edit"></i>&nbsp;修改</a>
							</td>
						</tr>
					</c:forEach>
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
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/config.js"></script>
</body>
</html>