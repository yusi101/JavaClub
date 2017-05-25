<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/jedate/skin/jedate.css">
	<%@ include file="../system/allresources.jsp"%>
	<title>行业门类信息总量统计</title>
	<style type="text/css">
		table{
		  border-collapse:collapse;
		  border:0px solid #999;
		  font-size:12px;
		  margin:50px;
		}
		table td{
		  border-top:0;
		  border-right:1px solid #999;
		  border-bottom:1px solid #999;
		  border-left:0;
		}
		table 
		table tr.lastrow td{
		  border-bottom:0;
		}
		table tr td.lastCol{
		  border-right:0;
		}
	</style>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">行业门类信息总量统计</h4>
			 <form action="${pageContext.request.contextPath }/enterinfoController/queryEnterinfo" method="POST" id="search">
	   		<select name="type" id="type" style="margin: 0px;">
	   			<option  value="" >----请选择类型----</option>
	   				<option value="" <c:if test="${pd.type == ''}">selected</c:if>>全部</option>
	   				<option value="0" <c:if test="${pd.type == 0}">selected</c:if>>企业</option>
	   				<option value="1" <c:if test="${pd.type == 1}">selected</c:if>>个体</option>
	   		</select>
	   		<button type="button" class="btn btn-primary" onclick="$('#search').submit();">查询</button>
				</form>
			<div>
				<div id="mainEchart" style="height: 400px; margin-bottom: 30px; width: 50%; margin: 30px auto; float: left;"></div>
				<div id="mainEchart2" style="width: 50%; margin: 10px auto; float: right;">
				<p align=center style=" width: 100%; "><font size="3" color="red"><b>行业门类信息总量统计表</b></font></p>
				<table border="1" style="height: 400px; margin-bottom: 30px;width: 90%;margin-left:20px;margin-right:20px ">
				<tr>
				<td>行业门类</td><td>市场主体数量（个）</td><td>市场主体数量所占总体比例(百分比（%）)</td>
				</tr>
				<c:forEach items="${listpages }" var="list" varStatus="status">
					<tr>
						<td>${list.NAME}</td><td>${list.COUNT}</td>
						<td>
						<fmt:formatNumber type="number" value="${(list.COUNT/pd.sum)*100}" pattern="#.##" maxFractionDigits="2"/>
						</td>
					</tr>
				</c:forEach>
				</table>
				</div>
				<div id="containers" style="height: 400px; margin-bottom: 30px; width: 100%; margin: 30px auto; float: left;"></div>
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
		            text: '行业门类信息总量饼状统计图',
		            style:{ "color": "#ff0000", "fontSize": "18px" ,fontWeight: 'bold'}
		        },
		        tooltip: {
		            pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b><br/>市场主体数量： <b>{point.y}</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                depth: 35,
		                dataLabels: {
		                    enabled: true,
		                    format: '{point.name}'+'({point.percentage:.2f}%)'
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
		            name: '门类占百分比',
		            data: [${yuan}]
		        }]
		    });
		});
		
		//条形图
		$(function () {
		    $('#mainEchart').highcharts({
		        chart: {
		            type: 'column',
		            marginTop: 65,
		            marginBottom: 115,
		            marginLeft: 25,
		            marginRight: 25,
		            options3d: {
		                enabled: true,
		                alpha: 10,
		                beta: 25,
		                depth: 70
		            }
		        },
		        title: {
		            text: '行业门类信息总量柱状统计图',
		            style:{ "color": "#ff0000", "fontSize": "18px" ,fontWeight: 'bold'}
		        },
		        subtitle: {
		            text: ''
		        }, 
		        legend: {
		            verticalAlign: 'top',
		            x: 90,
		            y: 25,
		        },
		        plotOptions: {
		            column: {
		                depth: 25
		            },
		            series: {
		                events: {
		                    legendItemClick: function(e) {
		                        return false; // 直接 return false 即可禁用图例点击事件
		                    }
		                }
		              },
		        },
		        xAxis: {
		        	categories:[ ${modulename}]
		        },
		        yAxis: {
		        	title:{
		        	       text:'市场主体数量(个)'
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
		        	name: '市场主体数量',
		        	 data: [${classifyCount}],
		            dataLabels: {
		                enabled: true,
		                rotation: 0,
		                color: '#FFFFFF',
		                align: 'left',
		                format: '{point.y:.0f}', // one decimal
		                y: 10, // 10 pixels down from the top
		            }
		        }], 
		    });
		});
	</script>
</body>
</html>