<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>征信报告发送记录</title>
</head>
<body>
	<%@ include file="../system/allresources.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">征信报告发送记录</h4>
			<form action="${pageContext.request.contextPath}/Interface/MakePdfInterface/queryReportName" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 100%; float: left; text-align: right;">
					征信报告名称：<input type="text" placeholder="请输入征信报告名称" name="NAME" value="${pd.NAME}" style="vertical-align: top;" />
					<button class="btn btn-search"  onclick="queryBrandinfo();" ><i class="fa fa-search"></i> 搜索</button>
				</div>
				<div class="clren"></div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="10%">用户名</th>
						<th width="20%">征信报告</th>
						<th width="10%">发送邮箱</th>
						<th width="10%">手机号码</th>
						<th width="10%">IP地址</th>
						<th width="13%">发送时间</th>
						<th width="15%">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty report_list }">
						<c:forEach items="${report_list }" var="list" varStatus="status">
							<tr>
								<td>${status.index+1 }</td>
								<td>${list.CREATEUSER_ID}</td>
								<td>${list.ENTNAME}</td>
								<td>${list.EMAIL}</td>
								<td>${list.TEL}</td>
								<td>${list.IP}</td>
								<td><fmt:formatDate type="both" value="${list.CREATETIME }" /></td>
								<td>
									<button class="btn btn-detail" onclick="query('${list.ID }')"><i class="fa fa-navicon"></i>&nbsp;详情</button>
									<button class="btn btn-del" onclick="deletereport('${list.ID }')"><i class='fa fa-trash'></i>&nbsp;删除</button>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty report_list }">
						<tr>
							<td colspan="9" style="color: red; text-align: center;">
								<div align="center">
									<img src="${pageContext.request.contextPath}/static/images/nodata.png" style="width: 100px; margin: 10px auto;">
								</div>
								<div style="margin-bottom: 10px;">小查没有查到数据哦~</div>
							</td>
						</tr>
					</c:if>
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/sysreport.js"></script>
</body>
</html>