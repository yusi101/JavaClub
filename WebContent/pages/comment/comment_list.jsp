<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>评论管理</title>
	<%@ include file="../system/allresources.jsp"%>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">评论管理</h4>
			<form action="${pageContext.request.contextPath}/commentController/queryCommentinfo" method="post" name="queryCommentInfo" id="queryCommentInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 100%; float: left; text-align: right;">
					评论人：<input autocomplete="off" id="aliasname" type="text" name="aliasname" placeholder="请输入评论人" value="${pd.aliasname}" style="width: 150px;"/> 
					评论时间：
					<input style="height: 17px" id="startDay" type="text" name="startDay" value="${pd.startDay }" placeholder="请选择开始时间" readonly="readonly" />
					至 
					<input style="height: 17px" id="endDay" type="text" name="endDay" value="${pd.endDay }" placeholder="请选择结束时间" readonly="readonly" />
					状态：<select name="status" id="status" style="width: 120px; text-align: center;">
						<option value='3' <c:if test="${pd.status == null }">selected</c:if>>请选择状态</option>
						<option value='0' <c:if test="${pd.status == 0 }">selected</c:if>>未审核</option>
						<option value='1' <c:if test="${pd.status == 1 }">selected</c:if>>审核成功</option>
						<option value='2' <c:if test="${pd.status == 2 }">selected</c:if>>审核失败</option>
					</select>
					<button type="button" class="btn btn-search" onclick="mySearch()"><i class="fa fa-search"></i>搜索</button>
				</div>
			</form>
			<table id="table_bug_report" class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th class="center" width="5%">序号</th>
						<th class="center" width="20%">评论企业</th>
						<th class="center" width="10%">评论人</th>
						<th class="center" width="30%">评论内容</th>
						<th class="center" width="15%">评论时间</th>
						<th class="center" width="10%">审核状态</th>
						<th class="center" width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty commentInfo }">
							<c:forEach items="${commentInfo }" var="list" varStatus="status">
								<tr>
									<td class="center">${status.index+1 }</td>
									<td class="center">${list.ENTNAME}</td>
									<c:if test="${empty list.ALIASNAME}">
											<td class="center">${list.USERNAME}</td>
									</c:if>
									<c:if test="${not empty list.ALIASNAME}">
											<td class="center">${list.ALIASNAME}</td>
									</c:if>
									<td class="center">${list.REMARK}</td>
									<td class="center">${list.COMMENTTIME}</td>
									<td class="center">
										<c:if test="${list.STATUS == 0 }">
											未审核
										</c:if>
										<c:if test="${list.STATUS == 1 }">
											审核成功
										</c:if>
										<c:if test="${list.STATUS == 2 }">
											审核失败
										</c:if>
									</td>
									<td class="center">
										<c:if test="${list.STATUS == 0 }">
											<button class="btn btn-edit" onclick="openupd('${list.ID }')"><i class="fa fa-gavel"></i>&nbsp;审核</button>
										</c:if>
										<c:if test="${list.STATUS != 0 }">
											<button class="btn btn-detail" onclick="opendet('${list.ID }')"><i class="fa fa-navicon"></i>&nbsp;详情</button>
										</c:if>
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
							<div class="pagination" style="float: left; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/comment.js"></script>
	<script type="text/javascript">
		jeDate({
			dateCell : "#startDay",
			format : "YYYY-MM-DD",
			isinitVal : false, //是否初始化时间
			isTime : false, //是否开启时间选择
			isClear : true, //是否显示清空
			festival : false, //是否显示节日
			maxDate : jeDate.now(0),
			minDate : "2014-09-19",
			okfun : function(val) {
			}
		})
		
		jeDate({
			dateCell : "#endDay",
			format : "YYYY-MM-DD",
			isinitVal : false, //是否初始化时间
			isTime : false, //是否开启时间选择
			isClear : true, //是否显示清空
			festival : false, //是否显示节日
			maxDate : jeDate.now(0),
			minDate : "2014-09-19",
			okfun : function(val) {
			}
		})
	</script>
</head>
</body>
</html>