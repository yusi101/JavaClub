<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>数据监控</title>
	<%@ include file="../system/allresources.jsp"%>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">数据监控</h4>
			<form action="${pageContext.request.contextPath}/dataMonController/querydataMon" method="post" name="querydataMon" id="querydataMon" class="form-horizontal" style="margin: 10px 0; height: 30px;">
			<div style="width: 100%; text-align: right;">
				更新时间：<input type="text" placeholder="请选择开始时间" name="startDay" id="startDay" value="${pd.startDay}" style="vertical-align: top;" readonly="readonly" /> 
				至 <input type="text" placeholder="请选择结束时间" name="endDay" id="endDay" value="${pd.endDay}" style="vertical-align: top;" readonly="readonly" />
				<button type="button" class="btn btn-search" onclick="mySearch()"><i class="fa fa-search"></i>&nbsp;搜索</button>
			</div>
			<div class="clren"></div>
		</form>
			<table id="table_bug_report" class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th class="center" width="5%">序号</th>
						<th class="center" width="20%">当日更新的数据量</th>
						<th class="center" width="20%">当日最后更新时间</th>
						<th class="center" width="15%">备注</th>
						<th class="center" width="20%">总更新数据量</th>
						<th class="center" width="20%">最后更新时间</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty enterpriseInfo }">
					<c:forEach items="${enterpriseInfo}" var="list" varStatus="status">
						<tr>
							<th class="center" width="10%">1</th>
							<th class="center" width="35%">E_BASEINFO</th>
							<th class="center" width="10%">${list.NUM }</th>
							<th class="center" width="35%">${list.D_ADDTIME }</th>
							<th class="center" width="10%">企业表</th>
						</tr>
					</c:forEach>
					</c:if>
					<c:if test="${not empty individualInfo }">
					<c:forEach items="${individualInfo}" var="list" varStatus="status">
						<tr>
							<th class="center" width="10%">1</th>
							<th class="center" width="35%">E_PB_BASEINFO</th>
							<th class="center" width="10%">${list.NUM }</th>
							<th class="center" width="35%">${list.D_ADDTIME }</th>
							<th class="center" width="10%">个体农专表</th>
						</tr>
					</c:forEach>
					</c:if>
					<c:if test="${not empty newentInfo }">
					<c:forEach items="${newentInfo}" var="list" varStatus="status">
						<tr>
							<th class="center" width="5%">1</th>
							<th class="center" width="20%">${list.NUM }</th>
							<th class="center" width="20%">
							<c:if test="${empty list.D_ADDTIME}">
							暂无更新
							</c:if>
							<c:if test="${not empty list.D_ADDTIME}">
							<fmt:formatDate value="${list.D_ADDTIME}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</c:if>
							</th>
							<th class="center" width="15%">zr_qydjxx</th>
							<th class="center" width="20%">${list.SUMS }</th>
							<th class="center" width="20%"><fmt:formatDate value="${list.MAXTIME }" pattern="yyyy-MM-dd HH:mm:ss"/></th>
						</tr>
					</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/comment.js"></script>
	<script type="text/javascript">
		jeDate({
			dateCell : "#startDay",
			format : "YYYY-MM-DD hh:mm:ss",
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
			dateCell : "#endDay",
			format : "YYYY-MM-DD hh:mm:ss",
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
		    ShowLoading();
		    
		    if(compareTime()){
		    	$("#querydataMon").submit();
		    }
		}
		//比较时间
		function compareTime() {
			//获取开始时间和结束时间
			var startTime = $("#start_time").val();
			var endTime = $("#end_time").val();
			
			
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
</head>
</body>
</html>