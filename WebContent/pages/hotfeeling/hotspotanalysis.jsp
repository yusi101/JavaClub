<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/jedate/skin/jedate.css">
	<title>热搜榜</title>
	<%@ include file="../system/allresources.jsp" %>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div style="text-align: left;" class="page-header position-relative">
				<h4>热搜榜
					<small>
						<i class="icon-double-angle-right"></i>
					</small>
				</h4>
			</div>
			<div style="margin: 0 auto;">
				<form action="${pageContext.request.contextPath}/hotFeelingController/queryHotspotAnalysis" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0;margin-bottom:30px;height: 40px;line-height: 40px;">
					<div style="float: left; display: inline-block;">
						<div>
							时间：<input style="height:17px; width:150px;" id="startTime" type="text" name="startTime" value="${pd.startTime }" placeholder="请选择开始日期" readonly="readonly"/>
							至 <input style="height:17px; width:150px;" id="endTime" type="text" name="endTime" value="${pd.endTime }" placeholder="请选择结束日期" readonly="readonly"/>
							类型：<select id="form-field-select-3" data-placeholder="--请选择--" style="width: 150px;" name="select">
							  <option value="0" <c:if test="${pd.select =='0' }">selected</c:if>>-- 请选择 --</option>
							  <option value='1' <c:if test="${pd.select =='1' }">selected</c:if>>企业名称</option>
							  <option value='2' <c:if test="${pd.select =='2' }">selected</c:if>>关键词</option>
					        </select>
						</div>
					</div>
					<button type="button" class="btn btn-search btn-small tooltip-info" style="display: inline-block; float: right;" onclick="query();"><i class="fa fa-search"></i> 查询</button>
				</form>
				<div id="mainEcharts" style="width:100%;min-height:500px;margin-bottom:30px;width: 90%;margin:30px auto;"></div>
			<c:if test="${pd.select=='2'}">
                <table id="table_bug_report" class="table table-striped table-bordered table-hover table_bug_report" style="margin: 30px auto;">
					<thead>
						<tr>
							<th class="center" width="15%">序号</th>
							<th class="center" width="40%">关键词</th>
							<th class="center" width="10%">访问量</th>
							<th class="center" width="15%">访问时间</th>
							<th class="center" width="20%">备注</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${Hotlistpage}" var="list" varStatus="status">
							<tr>
							    <td class="center">${status.index+1}</td>
								<td>${list.KEYWORDS}</td>
								<td class="center">${list.COUNT }</td>
								<td class="center"><fmt:formatDate value="${list.LOGTIME}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td class="center">${list.REMARK}</td>
							</tr>
						</c:forEach>
					</tbody>
					</table>
					<table id="table_keywords" class="table table-striped table-bordered table-hover" style="margin: 30px auto; display: none;">
						<thead>
							<tr>
								<th class="center" width="20%">关键词</th>
								<th class="center" width="15%">用户名</th>
								<th class="center" width="20%">备注</th>
								<th class="center" width="20%">时间</th>
							</tr>
						</thead>
						<tbody id="keywords">
							
						</tbody>
					</table>
			</c:if>
			<c:if test="${pd.select !='2'}">
					<table class="table table-striped table-bordered table-hover table_bug_report" style="margin: 30px auto;">
						<thead>
							<tr>
						   		<th class="center" width="15%">序号</th>
								<th class="center" width="40%">企业名称</th>
								<th class="center" width="10%">访问量</th>
								<th class="center" width="15%">访问时间</th>
								<th class="center" width="20%">备注</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${Hotlistpage}" var="list" varStatus="status">
								<tr>
							 	   <td class="center">${status.index+1}</td>
									<td>${list.ENTNAME}</td>
									<td class="center">${list.COUNT }</td>
									<td class="center"><fmt:formatDate value="${list.LOGTIME}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td class="center">${list.REMARK}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table id="table_entname" class="table table-striped table-bordered table-hover" style="margin: 30px auto; display: none;">
						<thead>
							<tr>
								<th class="center" width="20%">企业名称</th>
								<th class="center" width="15%">用户名</th>
								<th class="center" width="20%">模板名称</th>
								<th class="center" width="20%">时间</th>
							</tr>
						</thead>
						<tbody id="entname">
							
						</tbody>
					</table>
					</c:if>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/echarts/echarts.min.js"></script>
	<script type="text/javascript">
		//搜索
		function query(){
		    ShowLoading();
		    
		    if(compareTime()){
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
		
		$(document).keyup(function(event){
			if(event.keyCode ==13){
			    $("#queryBrandInfo").submit();
			}
		});
		
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
	
		var select_param;
	    // 基于准备好的dom，初始化echarts图表
	    var myChart = echarts.init(document.getElementById('mainEcharts')); 
	    var titleText="${pd.select}"=="2" ? "搜索关键词热点分析":"搜索企业热点分析";
	    
	    option = {
	    	    title : {
	    	        text: titleText,
	    	        subtext: '',
	    	        x:'center'
	    	    },
	    	    tooltip : {
	    	        trigger: 'item',
	    	        formatter: "{b} : {c} ({d}%)"
	    	    },
	    	    legend: {
	    	        orient : 'horizontal',
	    	        x : 'center',
	    	        top:'40',
	    	        data: ${EcharsName}
	    	    },
	    	    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '10%',
			        containLabel: true
			    },
	    	    series : [
	    	        {
	    	            name:'',
	    	            type:'pie',
	    	            radius : '55%',
	    	            center: ['50%', '60%'],
	    	            data:${listEchars}
	    	        }
	    	    ]
	    	};
	
	
	    // 为echarts对象加载数据 
	    myChart.setOption(option);
	    myChart.on('click', eConsole);
	    
	    var select = "";
	    
	    function eConsole(param){
	    	select_param=param;
	    	toajax(1);
	    }
	    
	    function toajax(currentPage){
	    	/* $(top.jzts()); */
	    	var startTime = $("#startTime").val();
	    	var endTime = $("#endTime").val();
	    	select = $("#form-field-select-3").val();
	       $.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath }/hotFeelingController/queryHotspotAnalysisList.do',
				data : {
					keywords : select_param.data.name,
					pripid : select_param.data.pripid,
					select : select,
					endTime : endTime,
					startTime : startTime,
					currentPage : currentPage,
					funName : "toajax"
				},
				success : function(result) {
					var obj = JSON.parse(result).data;
					var page = JSON.parse(result).page;
					$(".table_bug_report").hide();
					var str = "";
					for(var i = 0; obj.length > i; i++){
						if(obj[i].ENTNAME == undefined){
							obj[i].ENTNAME = "无";
						}
						if(obj[i].USERNAME == undefined){
							obj[i].USERNAME = "无";
						}
						if(obj[i].MODULENAME == undefined){
							obj[i].MODULENAME = "无";
						}
						if(obj[i].LOGTIME == undefined){
							obj[i].LOGTIME = "无";
						}
						if(obj[i].KEYWORDS == undefined){
							obj[i].KEYWORDS = "无";
						}
						if(obj[i].REMARK == undefined){
							obj[i].REMARK = "无";
						}
						if(select == "2"){
							str += "<tr>"+
								"<td class='center'>"+obj[i].KEYWORDS+"</td>"+
								"<td class='center'>"+obj[i].USERNAME+"</td>"+
								"<td class='center'>"+obj[i].REMARK+"</td>"+
								"<td class='center'>"+obj[i].LOGTIME+"</td>"+
				 				"</tr>";
						}else{
							str += "<tr>"+
								"<td class='center'>"+obj[i].ENTNAME+"</td>"+
								"<td class='center'>"+obj[i].USERNAME+"</td>"+
								"<td class='center'>"+obj[i].MODULENAME+"</td>"+
								"<td class='center'>"+obj[i].LOGTIME+"</td>"+
						 		"</tr>";
						}
							
					}
					/* $(top.hangge()); */
					if(select == "2"){
						$("#table_keywords tbody").html(str);
						if($("#"+page.funName+"Peging").length && $("#"+page.funName+"Peging").length>0){   
							$("#"+page.funName+"Peging").html(page.pageStr); 
						} else {     
							$("#table_keywords").after("<div id='"+page.funName+"Peging'>"+page.pageStr+"</div>"); 
						}
						$("#table_keywords").show();
					}else{
						$("#table_entname tbody").html(str);
						if($("#"+page.funName+"Peging").length && $("#"+page.funName+"Peging").length>0){   
							$("#"+page.funName+"Peging").html(page.pageStr); 
						} else {     
							$("#table_entname").after("<div id='"+page.funName+"Peging'>"+page.pageStr+"</div>"); 
						}
						$("#table_entname").show();
					}
				}
			});
	    }
  </script>
</body>
</html>