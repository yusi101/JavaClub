<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/jedate/skin/jedate.css">
	<%@ include file="../system/allresources.jsp"%>
	<title>企业登记状态分析</title>
</head>

<body >
	<div class="container-fluid" >
		<div class="row-fluid" >
			<h4 class="title">企业登记状态分析</h4>
			<form action="${pageContext.request.contextPath}/registrationController/regstateinfo" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0; margin-bottom: 30px; height: 40px; line-height: 40px;">
				<div style="width: 90%; float:left ;">
					<div>
						时间：
						<input style="height: 17px; width: 150px;" id="startTime" type="text" name="startTime" value="${pd.startTime }" placeholder="请选择开始时间" readonly="readonly" />
						<input style="height: 17px; display: none;" id="startDay" type="text" name="startDay" value="${pd.startTime }" placeholder="请选择开始日期" readonly="readonly" />
						<input style="height: 17px; display: none;" id="startDate" type="text" name="startDate" value="${pd.startTime }" placeholder="请选择开始日期" readonly="readonly" />
						至
						<input style="height: 17px; width: 150px;" id="endTime" type="text" name="endTime" value="${pd.endTime }" placeholder="请选择结束时间" readonly="readonly" />
						<input style="height: 17px; display: none;" id="endDay" type="text" name="endDay" value="${pd.endTime }" placeholder="请选择结束日期" readonly="readonly" />
						<input style="height: 17px; display: none;" id="endDate" type="text" name="endDate" value="${pd.endTime }" placeholder="请选择结束日期" readonly="readonly" />
						地区：<select name="county" id="county" style="margin: 0px;">
   									<option  value="">----请选择地区----</option>
   	 							<c:forEach items="${countylist }" var="list">
   									<option value="${list.C_CODE }" <c:if test="${list.C_CODE == pd.county}"> selected = "selected"</c:if>>${list.C_NAME }</option>
   								</c:forEach> 
   							</select>
						<button class="btn btn-search"  onclick="query();" ><i class="fa fa-search"></i>查询</button>
					</div>
					
				</div>
			</form>
			<div>
			<div id="containers" style="height: 400px; margin-bottom: 30px; width: 100%; margin: 0px auto; float: left;"></div>
			<%-- <c:if test="${empty queryll} "><div id="container" style="height: 700px; margin-bottom: 30px; width: 100%; margin: 30px auto; float: left;"><p>暂无数据</p></div></c:if> --%>
			</div>
			
			
		</div>
		
	</div>
			
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/hicharts/highcharts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/hicharts/highcharts-3d.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/jedate/jedate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/scripts/util/json2.js"></script>
	<script type="text/javascript">
		
$(function () {
	Highcharts.setOptions({
        colors: [ '#058DC7','#DDDF00','#ED561B', '#6AF9C4','#FFF263','#FF9655', '#24CBE5','#64E572',  '#50B432' ]
    });
	$('#containers').highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0
            }
        },
        title: {
            text: '企登记状态分析'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                },
                showInLegend: true,
            }
        },
        credits: {
            enabled: true,
            text: "",
            href: "",
            style: {
                cursor: "pointer",
                color: "#909090",
                fontSize: "15px"
            }
        },
        series: [{
            type: 'pie',
            name: '各状态占百分比',
            data: [${queryll}]
        }]
    });
    
    
});


function btnChange(value) {
	if ($("#select").val() == 2) {
		$("#startTime").hide();
		$("#startTime").attr("name", "startDay");
		$("#startTime").val("");
		$("#endTime").hide();
		$("#endTime").attr("name", "endDay");
		$("#endTime").val("");

		$("#startDay").show();
		$("#startDay").attr("name", "startTime");
		$("#startDay").val("");
		$("#endDay").show();
		$("#endDay").attr("name", "endTime");
		$("#endDay").val("");

		$("#startDate").hide();
		$("#startDate").attr("name", "startDate");
		$("#startDate").val("");
		$("#endDate").hide();
		$("#endDate").attr("name", "endDate");
		$("#endDate").val("");
	} else if ($("#select").val() == 3) {
		$("#startTime").hide();
		$("#startTime").attr("name", "startDate");
		$("#startTime").val("");
		$("#endTime").hide();
		$("#endTime").attr("name", "endDate");
		$("#endTime").val("");

		$("#startDay").hide();
		$("#startDay").attr("name", "startDate");
		$("#startDay").val("");
		$("#endDay").hide();
		$("#endDay").attr("name", "startDate");
		$("#endDay").val("");

		$("#startDate").show();
		$("#startDate").attr("name", "startTime");
		$("#startDate").val("");
		$("#endDate").show();
		$("#endDate").attr("name", "endTime");
		$("#endDate").val("");
	} else {
		$("#startTime").show();
		$("#startTime").attr("name", "startTime");
		$("#startTime").val("");
		$("#endTime").show();
		$("#endTime").attr("name", "endTime");
		$("#endTime").val("");

		$("#startDay").hide();
		$("#startDay").attr("name", "startDay");
		$("#startDay").val("");
		$("#endDay").hide();
		$("#endDay").attr("name", "endDay");
		$("#endDay").val("");

		$("#startDate").hide();
		$("#startDate").attr("name", "startDate");
		$("#startDate").val("");
		$("#endDate").hide();
		$("#endDate").attr("name", "endDate");
		$("#endDate").val("");
	}
}

function query() {
	//加载中
	ShowLoading();
	
	//获取当前选择的id名称
	var oneV,twoV;
	if($("#select").val() == 2){
		oneV = "startDay";
		twoV = "endDay";
	}else if($("#select").val() == 3){
		oneV = "startDate";
		twoV = "endDate";
	}else{
		oneV = "startTime";
		twoV = "endTime";				
	}
		
	//判断开始时间是否大于结束时间
		if(compareTime(oneV,twoV)){
			$("#queryBrandInfo").submit();
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
	if($("#select").val() == 1){
		var newST = parseInt(startTime.replace(/-/g,"").replace(" ","").replace(/:/g,""));
		var newET = parseInt(endTime.replace(/-/g,"").replace(" ","").replace(/:/g,""));			
	}else{
		var newST = parseInt(startTime.replace(/-/g,""));
		var newET = parseInt(endTime.replace(/-/g,""));				
	}
	
	//判断开始时间是否大于结束时间
	if(newST > newET){
		layer.msg("开始时间不能大于结束时间");
		return false;
	}
	if(newST == newET){
		layer.msg("开始日期不能等于结束日期");
		return false;
	}
	
	return true;
}

jeDate({
	dateCell : "#startTime",
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
	dateCell : "#endTime",
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
	dateCell : "#startDay",
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
	</script>
</body>
</html>