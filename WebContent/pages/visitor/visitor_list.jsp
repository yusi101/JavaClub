<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>访客记录</title>
	<%@ include file="../system/allresources.jsp"%>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">访客记录</h4>
			<form action="${pageContext.request.contextPath}/visitorController/queryVisitorlistPage" method="post" name="visitorInfo" id="visitorInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 60%; float: left;">
					<a class="btn btn-add" onclick="openadd();"><i class="fa fa-plus"></i>&nbsp;新增</a>
				</div>
				<div style="width: 40%; float: left; text-align: right;">
					企业名称：<input type="text" placeholder="请输入企业名称" name="entName" maxlength="20" value="${pd.entName}" style="vertical-align: top;" />
					<button class="btn btn-search"  onclick="queryVisitorInfo();" ><i class="fa fa-search"></i>&nbsp;搜索</button>
				</div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="10%">企业名称</th>
						<th width="10%">访客姓名</th>
						<th width="10%">联系电话</th>
						<th width="10%">联系邮箱</th>
						<th width="10%">QQ</th>
						<th width="10%">接待人</th>
						<th width="10%">接待时间</th>
						<th width="10%">记录时间</th>
						<th width="15%">操作</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:choose>
						<c:when test="${not empty visitorList }">
							<c:forEach items="${visitorList}" var="list" varStatus="status">
								<tr>
									<td>${status.index+1 }</td>
									<td>${list.ENTNAME }</td>
									<td>${list.VISITORNAME}</td>
									<td>${list.TEL}</td>
									<td>${list.EMAIL}</td>
									<td>${list.QQ}</td>
									<td>${list.RECEIVERNAME }</td>
									<td><fmt:formatDate type="both" value="${list.RECEIVERTIME }" /></td>
									<td><fmt:formatDate type="both" value="${list.CREATETIME }" /></td>
									<td class="dropdown">
										<a class="btn btn-edit"  href="javascript:openupd('${list.ID }')"><i class="fa fa-edit"></i>&nbsp;编辑</a>
										<a class="btn btn-del"  href="javascript:opendel('${list.ID }')"><i class='fa fa-trash'></i>&nbsp;删除</a>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="10" style="color: red; text-align: center;">
									<div align="center">
										<img src="${pageContext.request.contextPath}/static/images/nodata.png" style="width: 100px; margin: 10px auto;">
									</div>
									<div style="margin-bottom: 10px;">小查没有查到数据哦~</div>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>	
				</tbody>
			</table>
			<div class="position-relative">
				<table style="width: 100%;">
					<tr>
						<td style="vertical-align: top;"><div class="pagination"
								style="float: left; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
					</tr>
				</table>
			</div>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/static/scripts/myjs/visitor.js"></script>
</body>
</html>