<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/jedate/skin/jedate.css">
	<%@ include file="../system/allresources.jsp"%>
	<title>纳税信息总量统计</title>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">纳税信息总量统计</h4>
			<div>
				 <div id="containers" style="height: 400px; margin-bottom: 30px; width: 50%; margin: 30px auto; float: left;"></div>
				<div id="containerling" style="height: 400px; margin-bottom: 30px; width: 50%; margin: 30px auto; float: right;"></div> 
			
				<div style="display:none;">
				<div id="mainEchart2" style="width: 100%; margin: 10px auto; float: right">
					<font size="3" color="red" ><b>企业纳税信息总量统计表</b></font>
					<table class="table table-condensed table-bordered table-hover tab" style="margin-top:10px">
					<tr>
					<td>序号</td><td>年度(年)</td><td>总额(万元)</td><td>单位数量(家)</td><td>增长</td>
					</tr>
					<c:forEach items="${listpages }" var="list" varStatus="status">
						<c:if test="${list.NAME == 1}">
							<tr>
								<td>${list.NO}</td>
								<td>${list.YEAR}</td>
								<td>${list.VALUE}</td>
								<td>${list.COUNT}</td>
								<td>2.31%</td>
							</tr>
						</c:if>
					</c:forEach>
					</table>
				</div>
				
					<div id="mainEchart2" style="width: 100%; margin: 10px auto; float: right;">
					<font size="3" color="red" style="margin:0px auto"><b>农专纳税信息总量统计表</b></font>
					<table class="table table-condensed table-bordered table-hover tab" style="margin-top:10px">
					<tr>
					<td>序号</td><td>年度(年)</td><td>总额(万元)</td><td>单位数量(家)</td><td>增长</td>
					</tr>
					<c:forEach items="${listpages }" var="list" varStatus="status">
						<c:if test="${list.NAME == 2}">
							<tr>
								<td>${list.NO}</td>
								<td>${list.YEAR}</td>
								<td>${list.VALUE}</td>
								<td>${list.COUNT}</td>
								<td>1.63%</td>
							</tr>
						</c:if>
					</c:forEach>
					</table>
				</div>
				
					<div id="mainEchart2" style="width: 100%; margin: 10px auto; float: right;">
					<font size="3" color="red" style="margin:0px auto"><b>个体纳税信息总量统计表</b></font>
					<table class="table table-condensed table-bordered table-hover tab" style="margin-top:10px">
					<tr>
					<td>序号</td><td>年度(年)</td><td>总额(万元)</td><td>单位数量(家)</td><td>增长</td>
					</tr>
					<c:forEach items="${listpages }" var="list" varStatus="status">
						<c:if test="${list.NAME == 3}">
							<tr>
								<td>${list.NO}</td>
								<td>${list.YEAR}</td>
								<td>${list.VALUE}</td>
								<td>${list.COUNT}</td>
								<td>0.85%</td>
							</tr>
						</c:if>
					</c:forEach>
					</table>
				</div>
				</div>
			</div>
		</div>
		<div id="classifyiInfo1" style="display: none;">
			<div id="page-content" class="clearfix">
				<div style="text-align: left;" class="page-header position-relative">
					<h1>
						<img style="width: 45px" src="${pageContext.request.contextPath }/static/images/jiantou.jpg" onclick="changes()">
						分类统计的详情
						<small>
							<i class="icon-double-angle-right"></i>
						</small>
					</h1>
				</div>

			</div>
		</div>
	</div>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/hicharts/highcharts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/hicharts/highcharts-3d.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/jedate/jedate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/scripts/util/json2.js"></script>
	<script type="text/javascript">
	
		//饼图
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
		            text: '纳税总额饼状统计图',
		            style:{ "color": "#ff0000", "fontSize": "18px" ,fontWeight: 'bold'}
		        },
		        tooltip: {
		            pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                depth: 35,
		                dataLabels: {
		                    enabled: true,
		                    format: '{point.name}'+'({point.percentage:.2f}%)'
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
		            name: '纳税额所占百分比',
		            data: [${yuan}]
		        }]
		    });
		});
		$(function () {
		    $('#containerling').highcharts({
		        chart: {
		            type: 'column'
		        },
		        title: {
		            text: '纳税总人数柱状统计图',
		            style:{ "color": "#ff0000", "fontSize": "18px" ,fontWeight: 'bold'}
		        },
		        xAxis: {
		            categories: [
		                '企业',
		                '个体',
		                '农专'
		            ],
		            crosshair: true
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '纳税人数 (人)'
		            },
		        labels: {
        		    formatter:function(){
        		      if(this.value <=100) { 
        		        return this.value;
        		      }else if(this.value >100 && this.value <=200) { 
        		        return this.value; 
        		      }else { 
        		        return this.value;
        		      }
        		    }
        		  }
		        },
		        credits: { 
		        	enabled: false //不显示LOGO 
		        	},
		        tooltip: {
		            headerFormat: '<span style="font-size:10px">{point.key}</span><br/><br/>',
		            pointFormat:'{series.name}: {point.y:.1f}人<br/>',
		            footerFormat: '',
		            shared: true,
		            useHTML: true
		        },
		        plotOptions: {
		            column: {
		                pointPadding: 0.2,
		                borderWidth: 0
		            }
		        },
		        series: [{
		            name: '2016年',
		            data: ${list2016}
		        }, {
		            name: '2015年',
		            data: ${list2015}
		        }, {
		            name: '2014年',
		            data: ${list2014}
		        }, {
		            name: '2013年',
		            data: ${list2013}
		        }]
		    });
		});
	    
	</script>
</body>
</html>