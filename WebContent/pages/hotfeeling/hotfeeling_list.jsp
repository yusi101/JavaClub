<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/jedate/skin/jedate.css">
	<title>舆情热度分析</title>
	<%@ include file="../system/allresources.jsp" %>
</head>

<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div style="text-align: left;" class="page-header position-relative">
				<h4>
					舆情热度分析
					<small>
						<i class="icon-double-angle-right"></i>
					</small>
				</h4>
			</div>
			<div style="margin: 0 auto;">
				<form action="${pageContext.request.contextPath}/hotFeelingController/queryHotFeeling" method="post" name="queryHotFeelingInfo" id="queryHotFeelingInfo" class="form-horizontal" style="margin: 10px 0;margin-bottom:30px;height: 40px;line-height: 40px;">
					<div style="float: left; display: inline-block;">
						<div>
							时间：<input style="height:17px; width:150px;" id="startTime" type="text"  name="startTime" value="${pd.startTime }" placeholder="请选择开始日期" readonly="readonly"/>
							至 <input style="height:17px; width:150px;" id="endTime" type="text"  name="endTime"  value="${pd.endTime }" placeholder="请选择结束日期" readonly="readonly"/>
							类型：<select id="select" data-placeholder="--请选择--" style="width: 150px;" name="select">
							  	<option value='1' <c:if test="${pd.select =='1' }">selected</c:if>>企业关注</option>
							  	<option value='2' <c:if test="${pd.select =='2' }">selected</c:if>>企业投诉</option>
							  	<option value='3' <c:if test="${pd.select =='3' }">selected</c:if>>企业评论</option>
					        </select>
						</div>
					</div>
					<button type="button" class="btn btn-search btn-small tooltip-info" style="display:inline-block; float: right;" onclick="query();"><i class="fa fa-search"></i> 查询</button>
				</form>
				<div id="containers" style="height: 350px; margin-bottom: 30px; width: 100%; float: right;"></div>
				<div id="tiaoxinqu" style="height: 350px; width: 100%; float: left ;margin-bottom: 30px"></div>
                <table id="table_bug_report" class="table table-striped table-bordered table-hover" style="width:80%;margin: 30px auto;">
					<thead>
						<tr>
							 <th class="center" width="10%">序号</th>
							<th class="center" width="30%">企业名称</th>
							<th class="center" width="30%">企业类型</th>
							<th class="center" width="10%">访问量(单位：次)</th>
							<th class="center" width="20%">访问时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${hotlistList}" var="list" varStatus="status">
							<tr>
							    <td class="center">${status.index+1}</td>
								<td>${list.ENTNAME}</td>
								<td class="center">${list.ENTTYPE_CN}</td>
								<td class="center">${list.COUNT }</td>
								<td class="center"><fmt:formatDate value="${list.CREATETIME}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<table id="table_bug" class="table table-striped table-bordered table-hover" style="width:80%;margin: 30px auto;display: none;">
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/hicharts/highcharts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/hicharts/highcharts-3d.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/jedate/jedate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/scripts/util/json2.js"></script>
	<script type="text/javascript">
		//搜索
		function query(){
		    ShowLoading();
		    
		    if(compareTime()){
		    	$("#queryHotFeelingInfo").submit();
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
		
		$(function () {
			Highcharts.setOptions({
		        colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4']
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
		            text: '舆情热度分析'
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
		       		 	events:{
		            		click:  function (e){
		            	    	  toajax(e.point.name);
		            	    }
		            	}
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
		            name: '访问量占百分比',
		            data: [${queryll}],
		            dataLabels: {
		                enabled: true,
		                rotation: 0,
		                color: '#000000',
		                align: 'right',
		                format: '{point.name}({point.y:.1f}%)', // one decimal
		                style: {
		                    fontSize: '13px',
		                    fontFamily: 'Verdana, sans-serif'
		                }
		            }
		        }]
		    });

		    $('#tiaoxinqu').highcharts({
		        chart: {
		            type: 'bar'
		        },
		        title: {
		            text: '舆情热度分析'
		        },
		        subtitle: {
		            text: '访问量'
		        },
		        xAxis: {
					show:false,
		            categories: [${entName}],
		            title: {
		                text: null
		            }
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '',
		                align: 'high'
		            },
		            labels: {
		                overflow: 'justify'
		            }
		        },
		        tooltip: {
		            pointFormat: '{series.name}: <b>{point.y}次</b>'
		        },
		        plotOptions: {
		            bar: {
		            	 allowPointSelect: true,
			            cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    format: '{point.name}'
		                },
		                events:{ 
		            		click:  function (e){
		            	    	  toajax(e.point.category);
		            	    }
		            	}
		            }
		        },
		        credits: {
		            enabled: false
		        },
		        series: [{
		        	name: '访问量',
		            data: [${count}],
		            dataLabels: {
		                enabled: true,
		                rotation: 0,
		                color: '#FFFFFF',
		                align: 'right',
		                format: '{point.y:.1f}', // one decimal
		                style: {
		                    fontSize: '13px',
		                    fontFamily: 'Verdana, sans-serif'
		                }
		            }
		        }]
		    });
		});
		
		function toajax(entname){
		   	var startTime = $("#startTime").val();
		   	var endTime = $("#endTime").val();
		   	var select = $("#select").val();
		      $.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath }/hotFeelingController/queryHotFeelingDetail.do',
				data : {
					name : entname,
					select : select,
					endTime : endTime,
					startTime : startTime
				},
				success : function(result) {
					var obj = JSON.parse(result);
						$("#table_bug_report").hide();
						$("#table_bug").show();
						var str = "";
						if(select == "3"){
							str = "<thead>"+
									"<tr>"+
										"<th class='center' width='20%'>企业名称</th>"+
										"<th class='center' width='15%'>用户名</th>"+
										"<th class='center' width='20%'>评论内容</th>"+
										"<th class='center' width='20%'>时间</th>"+
							 		"</tr>"+
				 		 		 "</thead>";
							for(var i = 0; obj.length > i; i++){
								if(obj[i].ENTNAME == undefined){
									obj[i].ENTNAME = "无";
								}
								if(obj[i].USERNAME == undefined){
									obj[i].USERNAME = "无";
								}
								if(obj[i].REMARK == undefined){
									obj[i].REMARK = "无";
								}
								if(obj[i].CREATETIME == undefined){
									obj[i].CREATETIME = "无";
								}
						str += "<tbody id='table_bug_tab'>"+
							  	 "<tr>"+
									"<td class='center'>"+obj[i].ENTNAME+"</td>"+
									"<td class='center'>"+obj[i].USERNAME+"</td>"+
									"<td class='center'>"+obj[i].REMARK+"</td>"+
									"<td class='center'>"+obj[i].CREATETIME+"</td>"+
							 	 "</tr>"
							  "</tbody>";
							}
						}else if(select == "2"){
							str = "<thead>"+
							 		"<tr>"+
										"<th class='center' width='20%'>投诉标题</th>"+
										"<th class='center' width='15%'>用户名</th>"+
										"<th class='center' width='20%'>投诉内容</th>"+
										"<th class='center' width='20%'>时间</th>"+
						 			"</tr>"+
						 		  "</thead>";
							for(var i = 0; obj.length > i; i++){
								if(obj[i].TITLE == undefined){
									obj[i].TITLE = "无";
								}
								if(obj[i].USERNAME == undefined){
									obj[i].USERNAME = "无";
								}
								if(obj[i].REMARK == undefined){
									obj[i].REMARK = "无";
								}
								if(obj[i].CREATETIME == undefined){
									obj[i].CREATETIME = "无";
								}
						str += "<tbody id='table_bug_tab'>"+
								"<tr>"+
									"<td class='center'>"+obj[i].TITLE+"</td>"+
									"<td class='center'>"+obj[i].USERNAME+"</td>"+
									"<td class='center'>"+obj[i].REMARK+"</td>"+
									"<td class='center'>"+obj[i].CREATETIME+"</td>"+
								"</tr>"+
							  "</tbody>";
							}
						}else{
							str = "<thead>"+
									"<tr>"+
										"<th class='center' width='20%'>企业名称</th>"+
										"<th class='center' width='15%'>用户名</th>"+
										"<th class='center' width='20%'>企业类型</th>"+
										"<th class='center' width='20%'>时间</th>"+
									"</tr>"+
								  "</thead>";
							for(var i = 0; obj.length > i; i++){
								if(obj[i].ENTNAME == undefined){
									obj[i].ENTNAME = "无";
								}
								if(obj[i].USERNAME == undefined){
									obj[i].USERNAME = "无";
								}
								if(obj[i].ENTTYPE_CN == undefined){
									obj[i].ENTTYPE_CN = "无";
								}
								if(obj[i].CREATETIME == undefined){
									obj[i].CREATETIME = "无";
								}
						str += "<tbody id='table_bug_tab'>"+
									"<tr>"+
										"<td class='center'>"+obj[i].ENTNAME+"</td>"+
										"<td class='center'>"+obj[i].USERNAME+"</td>"+
										"<td class='center'>"+obj[i].ENTTYPE_CN+"</td>"+
										"<td class='center'>"+obj[i].CREATETIME+"</td>"+
									"</tr>"+
								"</tbody>";
							}
						}
		
						$("#table_bug").html(str);
				}
		      });
		}
	</script>
</body>
</body>
</html>