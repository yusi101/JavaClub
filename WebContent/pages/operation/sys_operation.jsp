<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/jedate/skin/jedate.css">
	<%@ include file="../system/allresources.jsp"%>
	<title>系统流量统计</title>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">系统流量分析</h4>
			<form action="${pageContext.request.contextPath}/OperationController/querySystemFlowAnalysis" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 90%; float: left;">
					时间：
					<input style="height: 17px; width: 150px;" id="startTime" type="text" name="startTime" value="${pd.startTime }" placeholder="请选择开始时间" readonly="readonly" />
					<input style="height: 17px; display: none;" id="startDay" type="text" name="startDay" value="${pd.startTime }" placeholder="请选择开始日期" readonly="readonly" />
					<input style="height: 17px; display: none;" id="startDate" type="text" name="startDate" value="${pd.startDate }" placeholder="请选择开始日期" readonly="readonly" />
					至
					<input style="height: 17px; width: 150px;" id="endTime" type="text" name="endTime" value="${pd.endTime }" placeholder="请选择结束时间" readonly="readonly" />
					<input style="height: 17px; display: none;" id="endDay" type="text" name="endDay" value="${pd.endTime }" placeholder="请选择结束日期" readonly="readonly" />
					<input style="height: 17px; display: none;" id="endDate" type="text" name="endDate" value="${pd.endDate }" placeholder="请选择结束日期" readonly="readonly" />
					类型：<select style="width: 150px;" onchange='btnChange(this[selectedIndex].value);' id="select" name="select">
						<option value='1' <c:if test="${pd.select =='1' }">selected</c:if>>时间段</option>
						<option value='2' <c:if test="${pd.select =='2' }">selected</c:if>>天数</option>
						<option value='3' <c:if test="${pd.select =='3' }">selected</c:if>>月数</option>
					</select>
				</div>
				<div style="width: 10%; float: left; text-align: right;">
					<button type="button" class="btn btn-search"  onclick="query();" ><i class="fa fa-search"></i>查询</button>
				</div>
			</form>
			<div id="container" style="height: 500px; margin-bottom: 30px; width: 90%; margin: 30px auto;"></div>
		</div>
		<table id="table_bug_report" class="table table-striped table-bordered table-hover" style="margin: 30px auto;">
			<thead>
				<tr>
					<th class="center" width="5%">序号</th>
					<th class="center" width="35%">时间</th>
					<th class="center" width="10%">访问量</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${datelist}" var="list" varStatus="status">
					<tr>
						<td class="center">${status.index+1}</td>
						<td class="center">${list}</td>
						<td class="center">${countlist[status.index] }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table id="table_bug" class="table table-striped table-bordered table-hover" style="margin: 30px auto; display: none;">
			<thead>
				<tr>
					<th class="center" width="20%">企业名称</th>
					<th class="center" width="15%">用户名</th>
					<th class="center" width="20%">模板名称</th>
					<th class="center" width="20%">记录日志时间</th>
				</tr>
			</thead>
			<tbody id="table_bug_tab">

			</tbody>
		</table>
	</div>

	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/hicharts/highcharts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/hicharts/highcharts-3d.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/jedate/jedate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/scripts/util/json2.js"></script>
	<script type="text/javascript">
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
				//$("#startDay").val("");
				$("#endDay").show();
				$("#endDay").attr("name", "endTime");
				//$("#endDay").val("");
	
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
				//$("#startDate").val("");
				$("#endDate").show();
				$("#endDate").attr("name", "endTime");
				//$("#endDate").val("");
			} else {
				$("#startTime").show();
				$("#startTime").attr("name", "startTime");
				//$("#startTime").val("");
				$("#endTime").show();
				$("#endTime").attr("name", "endTime");
				//$("#endTime").val("");
	
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
			okfun : function(val) {}
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
			okfun : function(val) {}
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
			okfun : function(val) {}
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
			okfun : function(val) {}
		}) 
		
      	jeDate({
			dateCell : "#startDate",
			format : "YYYY-MM",
			isinitVal : false, //是否初始化时间
			isTime : false, //是否开启时间选择
			isClear : true, //是否显示清空
			festival : false, //是否显示节日
			minDate : "2014-09-19 00:00:00",
			okfun : function(val) {}
		})
		
		jeDate({
			dateCell : "#endDate",
			format : "YYYY-MM",
			isinitVal : false, //是否初始化时间
			isTime : false, //是否开启时间选择
			isClear : true, //是否显示清空
			festival : false, //是否显示节日
			minDate : "2014-09-19 00:00:00",
			okfun : function(val) {}
		})
		
 	var time = "";
    $(function () {
    	btnChange($("#select").val());
	    $('#container').highcharts({
	        title: {
	            text: '系统流量访问流量条形图',
	            x: -20 //center
	        },
	        subtitle: {
	            text: '系统流量访问',
	            x: -20
	        },
	        xAxis: {
	            categories: ${timelist}
	        },
	        yAxis: {
	            title: {
	                text: '系统访问次数'
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	            valueSuffix: '次'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
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
	       plotOptions: {
	           series: {
	               cursor: 'pointer',
	               events: {
	                   click: function(e) {
	                	   time = e.point.category;
	                       $.ajax({
	               			type : 'post',
	               			url : '${pageContext.request.contextPath }/OperationController/querySystemFlowAnalysislistPage',
	               			data : {
	               				logTime : e.point.category,
	               				select : $("#select").val(),
	               				funName : "toajax"
	               			},
	               			success : function(result) {
	               				var obj = JSON.parse(result).data;
	               				var page = JSON.parse(result).page;
	               					$("#table_bug_report").hide();
	               					$("#table_bug").show();
	               					var str = "";
	               					for(var i = 0; obj.length > i; i++){
	               						if(obj[i].ENTNAME == undefined){
	               							obj[i].ENTNAME = "无";
	               						}
	               						if(obj[i].username == undefined){
	               							obj[i].username = "无";
	               						}
	               						if(obj[i].MODULENAME == undefined){
	               							obj[i].MODULENAME = "无";
	               						}
	               						if(obj[i].LOGTIME == undefined){
	               							obj[i].LOGTIME = "无";
	               						}
	               					  str += "<tr>"+
	               								"<td class='center'>"+obj[i].ENTNAME+"</td>"+
	               								"<td class='center'>"+obj[i].username+"</td>"+
	               								"<td class='center'>"+obj[i].MODULENAME+"</td>"+
	               								"<td class='center'>"+obj[i].LOGTIME+"</td>"+
	               							 "</tr>";
	               							
	               					}
	               					$("#table_bug tbody").html(str);
	               					if($("#"+page.funName+"Peging").length && $("#"+page.funName+"Peging").length>0){   
	               						$("#"+page.funName+"Peging").html(page.pageStr); 
	               					} else {     
	               						$("#table_bug").after("<div id='"+page.funName+"Peging'>"+page.pageStr+"</div>"); 
	               					}
	               			}
	               		});
	                   }
	               }
	           }
	       },
	        series: [{
	            name: '访问次数',
	            data: ${countlist}
	        }]
	    });
	});
    
    HideLoading();
    
    function toajax(currentPage){
    	var select = $("#select").val();
       	$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath }/OperationController/querySystemFlowAnalysislistPage',
			data : {
				logTime : time,
   				funName : "toajax",
				select : select,
				currentPage : currentPage,
			},
			success : function(result) {
				var obj = JSON.parse(result).data;
				var page = JSON.parse(result).page;
				$("#table_bug_report").hide();
				$("#table_bug").show();
				var str = "";
				for(var i = 0; obj.length > i; i++){
					if(obj[i].ENTNAME == undefined){
						obj[i].ENTNAME = "无";
					}
					if(obj[i].username == undefined){
						obj[i].username = "无";
					}
					if(obj[i].MODULENAME == undefined){
						obj[i].MODULENAME = "无";
					}
					if(obj[i].LOGTIME == undefined){
						obj[i].LOGTIME = "无";
					}
				  str += "<tr>"+
							"<td class='center'>"+obj[i].ENTNAME+"</td>"+
							"<td class='center'>"+obj[i].username+"</td>"+
							"<td class='center'>"+obj[i].MODULENAME+"</td>"+
							"<td class='center'>"+obj[i].LOGTIME+"</td>"+
						 "</tr>";
						
				}
				$("#table_bug tbody").html(str);
				if($("#"+page.funName+"Peging").length && $("#"+page.funName+"Peging").length>0){   
					$("#"+page.funName+"Peging").html(page.pageStr); 
				} else {     
					$("#table_bug").after("<div id='"+page.funName+"Peging'>"+page.pageStr+"</div>"); 
				}
			}
		});
    }
  </script>
</body>
</html>