<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>企业投诉</title>
	<%@ include file="../system/allresources.jsp"%>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">投诉审核查询</h4>
			<form action="${pageContext.request.contextPath }/complainController/queryComplainApplylistPage" method="post" id="queryComplainInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 100%; float: left; text-align: right;">
					企业名称：<input type="text" placeholder="请输入企业名称" id="entname" name="entname" maxlength="30" value="${pd.entname}" style="vertical-align: top;" />
					投诉时间：
					<input style="height: 17px" id="startDay" type="text" name="startDay" value="${pd.startDay }" placeholder="请选择开始时间" readonly="readonly" />
					至 
					<input style="height: 17px" id="endDay" type="text" name="endDay" value="${pd.endDay }" placeholder="请选择结束时间" readonly="readonly" />
					状态：<select name="status" id="status" style="width: 120px; text-align: center;">
						<option value='3' <c:if test="${pd.status == null }">selected</c:if>>请选择状态</option>
						<option value='0' <c:if test="${pd.status == 0 }">selected</c:if>>未审核</option>
						<option value='1' <c:if test="${pd.status == 1 }">selected</c:if>>已审核通过</option>
						<option value='2' <c:if test="${pd.status == 2 }">selected</c:if>>审核不通过</option>
					</select>
					<button type="button" class="btn btn-search" onclick="mySearch()"><i class="fa fa-search"></i> 搜索</button>
				</div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="25%">企业名称</th>
						<th width="10%">投诉人</th>
						<th width="15%">投诉类型</th>
						<th width="15%">投诉标题</th>
						<th width="10%">投诉状态</th>
						<th width="10%">投诉时间</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:if test="${!empty complainList }">
						<c:forEach items="${complainList }" var="list" varStatus="status">
							<tr>
								<td>${status.index + 1 }</td>
								<td>${list.ENTNAME }</td>
								<td>${list.USERNAME }</td>
								<c:if test="${list.TYPE_NAME==null or list.TYPE_NAME=='' }">
									<td>其他</td>
								</c:if>
								<c:if test="${list.TYPE_NAME!=null and list.TYPE_NAME!='' }">
									<td>${list.TYPE_NAME }</td>
								</c:if>
								<td>${list.TITLE }</td>
								<td>
									<c:if test="${list.STATUS == 0}">未审核</c:if>
									<c:if test="${list.STATUS == 1}">已审核通过</c:if>
									<c:if test="${list.STATUS == 2}">审核不通过</c:if>
								</td>
								<td>${list.COMMENTTIME }</td>
								<td>
									<c:if test="${list.STATUS == 0}">
										<button class="btn btn-edit" onclick="openApplyComplain('${list.ID }','0')"><i class="fa fa-gavel"></i>&nbsp;审核</button>
									</c:if>
									<c:if test="${list.STATUS != 0}">
										<button class="btn btn-detail" onclick="openApplyComplainDet('${list.ID }','1')"><i class="fa fa-navicon"></i>&nbsp;详情</button>
									</c:if>					
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty complainList }">
						<tr>
							<td colspan="8" style="color: red; text-align: center;">
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
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/complain.js"></script>
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
</body>
</html>