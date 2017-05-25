<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<%@ include file="../system/allresources.jsp"%> 
	<title>年报发送历史查询</title>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">年报发送历史查询</h4>
		</div>
		<form action="${pageContext.request.contextPath}/annualNotificationController/annualHistoryListPage" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
			<div style="width: 100%; text-align: right;">
				时间：<input type="text" placeholder="请选择开始时间" name="startTime" id="startTime" value="${pd.startTime}" style="vertical-align: top;" readonly="readonly" /> 
				至 <input type="text" placeholder="请选择结束时间" name="endTime" id="endTime" value="${pd.endTime}" style="vertical-align: top;" readonly="readonly" />
				企业名称：<input type="text" placeholder="请输入企业名称" id="entname" name="entname" maxlength="30" value="${pd.entname}" style="vertical-align: top;" />
				<button type="button" class="btn btn-search" onclick="mySearch()"><i class="fa fa-search"></i>&nbsp;搜索</button>
			</div>
			<div class="clren"></div>
		</form>
		<table class="table table-condensed table-bordered table-hover tab">
			<thead>
				<tr>
					<th>序号</th>
					<th>企业名称</th>
					<th>推送人</th>
					<th>推送时间</th>
					<th>邮箱推送</th>
					<th>手机推送</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty anList }">
					<c:forEach items="${anList }" var="list" varStatus="status">
						<tr>
							<td class="center">${status.index+1 }</td>
							<td class="center">${list.ENTNAME }</td>
							<td class="center">${list.USER_ID }</td>
							<td class="center">
								<fmt:formatDate value="${list.CURIMESTAMP }" pattern="yyyy-MM-dd" />
							</td>
							<td class="center">
								<c:if test="${list.EMAIL_FLAG == '0' }">否</c:if>
								<c:if test="${list.EMAIL_FLAG == '1' }">是</c:if>
							</td>
							<td class="center">
								<c:if test="${list.TEL_FLAG == '0' }">否</c:if>
								<c:if test="${list.TEL_FLAG == '1' }">是</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty anList }">
					<tr>
						<td colspan="6" style="color: red; text-align: center;">
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
	<script type="text/javascript">
		jeDate({
			dateCell : "#startTime",
			format : "YYYY-MM-DD",
			isinitVal : '${DatabaserLogPd.SQLINFO }', //是否初始化时间
			isTime : true, //是否开启时间选择
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
			isTime : true, //是否开启时间选择
			isClear : true, //是否显示清空
			festival : false, //是否显示节日
			maxDate : jeDate.now(0),
			minDate : "2014-09-19",
			okfun : function(val) {
			}
		})
		
		//搜索
		function mySearch(){
		    if(compareTime()){
		    	 ShowLoading();
		    	$("#queryBrandInfo").submit();
		    }
		}
		
		//比较时间
		function compareTime() {
			//获取开始时间和结束时间
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			
			
			if("" == startTime || "" == endTime || null == startTime 
					|| null == endTime || "undefined" == startTime || "undefined" == endTime){
				return true;
			}
			
			//转换为int的时间
			var newST = parseInt(startTime.replace(/-/g,""));
			var newET = parseInt(endTime.replace(/-/g,""));
			
			//判断开始时间是否大于结束时间
			if(newST > newET){
				layer.msg("开始时间不能大于结束时间");
				return false;
			}
			
			return true;
		}
	</script>
</body>
</html>