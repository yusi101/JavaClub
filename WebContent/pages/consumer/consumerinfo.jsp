<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/jedate/skin/jedate.css">
	<%@ include file="../system/allresources.jsp"%>
	<title>消费者投诉举报分析</title>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">消费者投诉举报分析</h4>
			<form action="${pageContext.request.contextPath}/consumerController/queryConsumerinfo" method="post" name="queryConsumerinfo" id="queryConsumerinfo" class="form-horizontal" style="margin: 10px 0; margin-bottom: 30px; height: 40px; line-height: 40px;">
				<div style="width: 90%; float: left;">
					<div>
						时间：
						<input style="height: 17px; width: 150px;" id="startTime" type="text" name="startTime" value="${pd.startTime }" placeholder="请选择开始时间" readonly="readonly" />
						至
						<input style="height: 17px; width: 150px;" id="endTime" type="text" name="endTime" value="${pd.endTime }" placeholder="请选择结束时间" readonly="readonly" />
					</div>
				</div>
				<div style="width: 10%; float: left; text-align: right;">
					<button type="button" class="btn btn-search"  onclick="query();" ><i class="fa fa-search"></i>查询</button>
				</div>
			</form>
		</div>
	</div>
	<div id="mainEcharts" style="height: 800px; width: 100%; margin: 0px auto; float: left;"></div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/echarts/echarts.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/jedate/jedate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/scripts/util/json2.js"></script>
	<script type="text/javascript">

	$(document).ready(function(){
			$("#startTime").show();
			$("#startTime").attr("name", "startTime");
			//$("#startTime").val("");
			$("#endTime").show();
			$("#endTime").attr("name", "endTime");
			//$("#endTime").val("");

			$("#startDay").hide();
			$("#startDay").attr("name", "startDay");
			//$("#startDay").val("");
			$("#endDay").hide();
			$("#endDay").attr("name", "endDay");
			//$("#endDay").val("");

			$("#startDate").hide();
			$("#startDate").attr("name", "startDate");
			//$("#startDate").val("");
			$("#endDate").hide();
			$("#endDate").attr("name", "endDate");
			//$("#endDate").val("");
		});
		
		function query() {
			//加载中
			ShowLoading();
			
			//获取当前选择的id名称
			var	oneV = "startTime";
			var	twoV = "endTime";				
	 		
			//判断开始时间是否大于结束时间
	 		if(compareTime(oneV,twoV)){
	 			$("#queryConsumerinfo").submit();
	 		}
			
		}
		
		//比较时间
		function compareTime(oneV,twoV) {
			//获取开始时间和结束时间
			var startTime = $("#" + oneV).val();
			var endTime = $("#" + twoV).val();
			
			
			if("" == startTime || "" == endTime || null == startTime 
					|| null == endTime || "undefined" == startTime || "undefined" == endTime){
				return true;
			}
			
			//转换为int的时间
				var newST = parseInt(startTime.replace(/-/g,"").replace(" ","").replace(/:/g,""));
				var newET = parseInt(endTime.replace(/-/g,"").replace(" ","").replace(/:/g,""));			
			
			//判断开始时间是否大于结束时间
			if(newST > newET){
				layer.msg("开始时间不能大于结束时间");
				return false;
			}
			if(newST == newET){
				layer.msg("开始时间不能等于结束时间");
				return false;
			}
			
			return true;
		}
		
		jeDate({
			dateCell : "#startTime",
			format : "YYYY-MM-DD hh:mm:ss",
			isinitVal : false, //是否初始化时间
			isTime : true, //是否开启时间选择
			isClear : true, //是否显示清空
			festival : false, //是否显示节日
			maxDate : jeDate.now(0),
			minDate : "2014-09-19 00:00:00",
			okfun : function(val) {
			}
		})
		
		jeDate({
			dateCell : "#endTime",
			format : "YYYY-MM-DD hh:mm:ss",
			isinitVal : false, //是否初始化时间
			isTime : true, //是否开启时间选择
			isClear : true, //是否显示清空
			festival : false, //是否显示节日
			maxDate : jeDate.now(0),
			minDate : "2014-09-19 00:00:00",
			okfun : function(val) {
			}
		})
		
		jeDate({
			dateCell : "#startDay",
			format : "YYYY-MM-DD",
			isinitVal : true, //是否初始化时间
			isTime : false, //是否开启时间选择
			isClear : true, //是否显示清空
			festival : false, //是否显示节日
			maxDate : jeDate.now(0),
			minDate : "2014-09-19 00:00:00",
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
			minDate : "2014-09-19 00:00:00",
			okfun : function(val) {
			}
		})
		
		jeDate({
			dateCell : "#startDate",
			format : "YYYY-MM",
			isinitVal : false, //是否初始化时间
			isTime : false, //是否开启时间选择
			isClear : true, //是否显示清空
			festival : false, //是否显示节日
			minDate : "2014-09-19 00:00:00",
			okfun : function(val) {
			}
		})
		
		jeDate({
			dateCell : "#endDate",
			format : "YYYY-MM",
			isinitVal : false, //是否初始化时间
			isTime : false, //是否开启时间选择
			isClear : true, //是否显示清空
			festival : false, //是否显示节日
			minDate : "2014-09-19 00:00:00",
			okfun : function(val) {
			}
		})
		//饼图
  var myChart = echarts.init(document.getElementById('mainEcharts')); 
	option = {
		    title : {
		        text: '消费者投诉类型统计分析',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'left',
		        data: [${yuanw}]
		    },
		    series : [
		        {
		            name: '消费者投诉类型',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '40%'],
		            data:[${yuan}],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};

	   // 为echarts对象加载数据 
    myChart.setOption(option);
	</script>
</body>
</html>