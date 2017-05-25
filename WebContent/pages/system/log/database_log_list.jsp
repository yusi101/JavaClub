<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="../allresources.jsp"%>
<title>数据库操作日志</title>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">数据库操作日志</h4>
		</div>
		<form action="${pageContext.request.contextPath}/userLogController/queryDatabaseLoglistPage" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
			<div style="width: 100%; text-align: right;">
				时间：<input type="text" placeholder="请选择开始时间" name="start_time" id="start_time" value="${pd.start_time}" style="vertical-align: top;" readonly="readonly" /> 
				至 <input type="text" placeholder="请选择结束时间" name="end_time" id="end_time" value="${pd.end_time}" style="vertical-align: top;" readonly="readonly" />
				<button type="button" class="btn btn-search" onclick="mySearch()"><i class="fa fa-search"></i>&nbsp;搜索</button>
			</div>
			<div class="clren"></div>
		</form>
		<table class="table table-condensed table-bordered table-hover tab">
			<thead>
				<tr>
					<th class="center" width="5%">序号</th>
					<th class="center" width="10%">姓名</th>
					<th class="center" width="15%">IP地址</th>
					<th class="center" width="10%">操作类型</th>
					<th class="center" width="25%">操作时间</th>
					<th class="center" width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty DatabaseLogList }">
					<c:forEach items="${DatabaseLogList }" var="list"
						varStatus="status">
						<tr>
							<td class="center">${status.index+1 }</td>
							<td class="center">${list.CREATEUSERNAME}</td>
							<td class="center">${list.IP}</td>
							<td class="center">${list.LOGTYPE}</td>
							<td class="center"><fmt:formatDate
									value="${list.CREATETIME}" pattern="yyyy年MM月dd日  HH时mm分ss秒" /></td>
							<td class="dropdown">
								<button class="btn btn-detail" onclick="query('${list.ID }')">
									<i class="fa fa-navicon"></i>&nbsp;详情
								</button>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty DatabaseLogList }">
					<tr>
						<td colspan="6" style="color: red; text-align: center;">
							<div align="center">
								<img
									src="${pageContext.request.contextPath}/static/images/nodata.png"
									style="width: 100px; margin: 10px auto;">
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
						<div class="pagination"
							style="float: left; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	</div>
	<script type="text/javascript">
		jeDate({
			dateCell : "#start_time",
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
			dateCell : "#end_time",
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
		
		function find() {
			$("#queryBrandInfo").submit();
		}

		function query(ID) {
			layer.open({
				type : 2,
				title : '数据库操作日志详情',
				scrollbar : false, //父页面是否有滚动条
				shadeClose : false, //点击其他区域关闭弹窗
				shade : 0.5, //笼罩层透明度
				area : [ '700px', '400px' ], //大小
				maxmin : true,//是否显示最大化按钮
				content : '${pageContext.request.contextPath}/userLogController/queryDatabaseLogById?ID=' + ID,
			});
		}
		
		//搜索
		function mySearch(){
		    ShowLoading();
		    
		    if(compareTime()){
		    	$("#queryBrandInfo").submit();
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
</body>
</html>