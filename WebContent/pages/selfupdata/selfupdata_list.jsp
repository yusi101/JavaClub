<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>企业自主更新</title>
	<%@ include file="../system/allresources.jsp"%>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<c:if test="${empty pd.entName}">
				<h4 class="title"> &nbsp;&nbsp;自主更新</h4>
			</c:if>
			<c:if test="${not empty pd.entName}">
				<h4 class="title" onclick="javascript:history.go(-1);"><i class="fa fa-reply-all"></i> &nbsp;&nbsp;${pd.entName}(自主更新)</h4>
			</c:if>
			
			<form action="${pageContext.request.contextPath}/selfUpdataController/querySelfUpdatainfo" method="post" name="selfUpdataInfo" id="selfUpdataInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="display: none;">
					<input name="KeyNo" value="${pd.KeyNo }">
					<input name="entName" value="${pd.entName }">
				</div>
				<div style="width: 60%; float: left;">
					<a class="btn btn-add" onclick="openadd('${pd.KeyNo}','${pd.entName}');"><i class="fa fa-plus"></i>&nbsp;新增</a>
				</div>
				<div style="width: 40%; float: left; text-align: right;">
					标题：<input type="text" placeholder="请输入标题" name="title" maxlength="20" value="${pd.title}" style="vertical-align: top;" />
					<button class="btn btn-search"  onclick="querySelfUpdataInfo();" ><i class="fa fa-search"></i>搜索</button>
				</div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="20%">企业名称</th>
						<th width="15%">标题</th>
						<th width="10%">创建人</th>
						<th width="15%">创建时间</th>
						<th width="12%">审核状态</th>
						<th width="23%">操作</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:choose>
						<c:when test="${not empty selfUpdataInfo }">
							<c:forEach items="${selfUpdataInfo}" var="list" varStatus="status">
								<tr>
									<td>${status.index+1 }</td>
									<td>${list.ENTNAME}</td>
									<td>${list.TITLE}</td>
									<td>${list.FOUNDNAME}</td>
									<td>${list.CREATE_TIME}</td>
									<td>
										<c:if test="${list.STATUS == 0}">
											未审核
										</c:if>
										<c:if test="${list.STATUS == 1}">
											审核通过
										</c:if>
										<c:if test="${list.STATUS == 2}">
											审核失败
										</c:if>
									</td>
									<td class="dropdown">
										<a class="btn btn-edit" href="javascript:openupd('${list.ID }')">更新</a>
										<a class="btn btn-del" href="javascript:opendel('${list.ID }')"><i class='fa fa-trash'></i>&nbsp;删除</a>
										<a class="btn btn-detail" href="javascript:auddet('${list.ID }')"><i class="fa fa-navicon"></i>&nbsp;详情</a>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="7" style="color: red; text-align: center;">
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
						<td style="vertical-align: top;">
						<div class="pagination" style="float: left; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/selfupdata.js"></script>
</body>
</html>