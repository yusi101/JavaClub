<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>牌照领取通知</title>
	<%@ include file="../system/allresources.jsp"%>
	<link href="${pageContext.request.contextPath}/static/styles/util/imgscale.css" rel="stylesheet" />
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcode.js"></script>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">牌照领取通知</h4>
			<form action="${pageContext.request.contextPath}/qrCodeController/queryQRCodeNoticelistPage" method="post" name="queryEntnameInfo" id="queryEntnameInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 10%; float: left;">
					<input type="button" class="btn btn-add" onclick="sendNotice();" value="通知">
				</div>
				<div style="width: 90%; float: left; text-align: right;">
					时间：<input id="startTime" type="text" name="startTime" value="${pd.startTime }" placeholder="请选择开始时间" readonly="readonly" />
					至&nbsp;<input id="endTime" type="text" name="endTime" value="${pd.endTime }" placeholder="请选择结束时间" readonly="readonly" />
					批次号：<input type="text" placeholder="请输入批次号" name="barchNumber" id="barchNumber" value="${pd.barchNumber }" maxlength="30" />
					企业名称：<input type="text" placeholder="请输入企业名称" name="entname" id="entname" value="${pd.entname }" maxlength="30" style="width: 150px;" />
					<button type="button" class="btn btn-search" onclick="mySearch();" ><i class="fa fa-search"></i> 搜索</button>
				</div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
					    <th width="5%">
					    	<input type="checkbox" id="zcheckbox" style="vertical-align: text-top"/>
					    </th>
						<th width="20%">企业名称</th>
						<th width="15%">牌照编码</th>
						<th width="10%">牌照</th>
						<th width="10%">批次号</th>
						<th width="10%">通知次数</th>
						<th width="10%">入库处理人</th>
						<th width="10%">处理时间</th>
						<th width="15%">操作</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:if test="${!empty qrList }">
						<c:forEach items="${qrList }" var="list" varStatus="status">
							<tr>
								<td>
									<c:if test="${list.TEL != '' or list.EMAIL != '' }">
										<input type="checkbox" name="ids" value="${list.RELATION_ID },${list.TEL },${list.EMAIL },${list.ENTNAME },${list.REGNO },${list.APPLY_NAME }" style="vertical-align: text-top"/>
									</c:if>
								</td>
								<td>${list.ENTNAME }</td>
								<td>${list.LICENSENUMBER }</td>
								<td><img src="data:image/jpg;base64,${list.LICENSECODE }" onclick="imgScale(this)" class="code_img"/></td>
								<td>${list.BATCHNO }</td>
								<td>
									<c:if test="${empty list.noticeCount }">0次</c:if>
									<c:if test="${!empty list.noticeCount }">${list.noticeCount }次</c:if>
								</td>
								<td>${list.APPROVEUSER_NAME }</td>
								<td><fmt:formatDate type="both" value="${list.APPROVE_TIME }" /></td>
								<td>
									<button class="btn btn-detail" onclick="openNoticeDet('${list.AID }','${list.PID }','${list.PRIPID }')"><i class="fa fa-navicon"></i>&nbsp;详情</button>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty qrList }">
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
	<script src="${pageContext.request.contextPath}/static/scripts/util/imgscale.js"></script>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcode.js"></script>
	<script type="text/javascript">
		jeDate({
			dateCell : "#startTime",
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
			dateCell : "#endTime",
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