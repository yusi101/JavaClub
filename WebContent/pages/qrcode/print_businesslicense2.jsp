<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>营业执照打印</title>
 	<%@ include file="../system/allresources.jsp"%>
</head>

<body style="background: #FFF">
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">营业执照打印</h4>
			<form action="${pageContext.request.contextPath}/qrCodePrintController/printBusinessLicense" method="post" name="queryEntnameInfo" id="queryEntnameInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 100%; float: right; text-align: right;">
					时间：<input id="startTime" type="text" name="startTime" value="${pd.startTime }" placeholder="请选择开始时间" readonly="readonly" />
					至&nbsp;<input id="endTime" type="text" name="endTime" value="${pd.endTime }" placeholder="请选择结束时间" readonly="readonly" />
					企业名称：<input type="text" placeholder="请输入企业名称" name="entname" id="entname" value="${pd.entname }" maxlength="30" style="vertical-align: top;" />
					<button type="button" class="btn btn-search" onclick="mySearch();" ><i class="fa fa-search"></i> 搜索</button>
				</div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="5%">序号</th>
						<th width="20%">申请企业</th>
						<th width="10%">申请方式</th>
						<th width="10%">申请类型</th>
						<th width="10%">申请处理人</th>
						<th width="15%">处理时间</th>
						<th width="15%">申请状态</th>
						<th width="15%">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${!empty qrList }">
						<c:forEach items="${qrList }" var="list" varStatus="status">
							<tr>
								<td>${status.index + 1 }</td>
								<td>${list.ENTNAME }</td>
								<td>${list.APPLYWAY_CN }</td>
								<td>${list.APPLYTYPE_CN }</td>
								<td>${list.PNAME }</td>
								<td><fmt:formatDate type="both" value="${list.CREATETIME }" /></td>
								<td>
									<c:if test="${list.STATUS == 0 }">
										申请未处理
									</c:if>
									<c:if test="${list.STATUS == 1 }">
										<c:if test="${list.STATUS == 1 && list.QPSTATUS == null }">申请审核通过</c:if>
										<c:if test="${list.QPSTATUS == 0 }">打印未审核</c:if>
										<c:if test="${list.QPSTATUS == 1 }">打印审核已通过</c:if>
										<c:if test="${list.QPSTATUS == 2 }">打印审核未通过</c:if>
										<c:if test="${list.QPSTATUS == 3 }">牌照送去制作</c:if>
										<c:if test="${list.QPSTATUS == 4 }">牌照已入库</c:if>
										<c:if test="${list.QPSTATUS == 5 }">牌照制作入库审核不通过</c:if>
										<c:if test="${list.QPSTATUS == 6 }">牌照领取通知已发送</c:if>
										<c:if test="${list.QPSTATUS == 7 }">牌照已领取</c:if>
									</c:if>
									<c:if test="${list.STATUS == 2 }">
										申请审核不通过
									</c:if>
								</td>
								<td>
									<button class="btn btn-detail" onclick="confirm_businesslicense('${list.PRIPID }','${list.ENTNAME }')">打印营业执照</button>
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
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcodeprint.js"></script>
<%-- 	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcode.js"></script> --%>
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