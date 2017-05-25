<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/jedate/skin/jedate.css">
	<%@ include file="../system/allresources.jsp"%>
	<title>社保信息分析</title>
</head>

<body >
	<div class="container-fluid" >
		<div class="row-fluid" >
			<h4 class="title">社保信息分析</h4>
			<form action="${pageContext.request.contextPath}/socialInsuranceController/queryInsuranceInfo" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0; margin-bottom: 30px; height: 40px; line-height: 40px;">
				<div style="width: 20%; float:left ;">
					<div>
						年份：<select style="width: 150px;" onchange='btnChange(this[selectedIndex].value);' id="select" name="select">
							<option value='1' <c:if test="${pd.select =='1' }">selected</c:if>>--年份--</option>
							<option value='2' <c:if test="${pd.select =='2' }">selected</c:if>>2016年</option>
						</select>
					</div>
				</div>
				<button class="btn btn-search"  onclick="query();" ><i class="fa fa-search"></i>查询</button>
			</form>
			<div>
			<div id="mainEchart" style="height: 350px; margin-bottom: 30px; width: 100%; margin: 0px auto; float: right;"></div>
			<div id="containers" style="height: 350px; margin-bottom: 30px; width: 100%; margin: 0px auto; float: left;"></div>
			<div id="containers2" style="height: 350px; margin-bottom: 30px; width: 100%; margin: 0px auto; float: left;"></div>
			<div id="containers5" style="height: 350px; margin-bottom: 30px; width: 100%; margin: 0px auto; float: left;"></div>
			<div id="containers3" style="height: 800px; margin-bottom: 30px; width: 50%; margin: 0px auto;">
			<table id="table_bug_report" class="table table-striped table-bordered table-hover" style="width:100%;margin: 00px auto;">
				<h4 align="center">城镇职工各保险单位表格分析</h4>
				<tr>
					<td rowspan="5">基本保险参保人数</td>
					<td >养老保险参保人数</td>
					<td colspan="2">${map[0].SO110} 人</td>
					
				</tr>
				<tr>
					<td >失业保险参保人数</td>
					<td colspan="2">${map[0].SO210} 人</td>
				</tr>
				<tr>
					<td >医疗保险参保人数</td>
					<td colspan="2">${map[0].SO310} 人</td>
				</tr>
				<tr>
					<td >工伤保险参保人数</td>
					<td colspan="2">${map[0].SO410} 人</td>
				</tr>
				<tr>
					<td >生育保险参保人数</td>
					<td colspan="2">${map[0].SO510} 人</td>
				</tr>
				<tr>
					<td rowspan="5">基本保险缴费基数</td>
					<td >养老保险缴费基数</td>
					<td colspan="2">${map[1].SO110} 万元</td>
					
				</tr>
				<tr>
					<td >失业保险缴费基数</td>
					<td colspan="2">${map[1].SO210} 万元</td>
				</tr>
				<tr>
					<td >医疗保险缴费基数</td>
					<td colspan="2">${map[1].SO310} 万元</td>
				</tr>
				<tr>
					<td >工伤保险缴费基数</td>
					<td colspan="2">${map[1].SO410} 万元</td>
				</tr>
				<tr>
					<td >生育保险缴费基数</td>
					<td colspan="2">${map[1].SO510} 万元</td>
				</tr>
				<tr>
					<td rowspan="5">基本保险本期实际缴费金额</td>
					<td >养老保险实缴金额</td>
					<td colspan="2">${map[2].SO110} 万元</td>
					
				</tr>
				<tr>
					<td >失业保险实缴金额</td>
					<td colspan="2">${map[2].SO210} 万元</td>
				</tr>
				<tr>
					<td >医疗保险实缴金额</td>
					<td colspan="2">${map[2].SO310} 万元</td>
				</tr>
				<tr>
					<td >工伤保险实缴金额</td>
					<td colspan="2">${map[2].SO410} 万元</td>
				</tr>
				<tr>
					<td >生育保险实缴金额</td>
					<td colspan="2">${map[2].SO510} 万元</td>
				</tr>
				<tr>
					<td rowspan="5">基本保险累计欠缴金额</td>
					<td >养老保险欠缴金额</td>
					<td colspan="2">${map[3].SO110} 万元</td>
					
				</tr>
				<tr>
					<td >失业保险欠缴金额</td>
					<td colspan="2">${map[3].SO210} 万元</td>
				</tr>
				<tr>
					<td >医疗保险欠缴金额</td>
					<td colspan="2">${map[3].SO310} 万元</td>
				</tr>
				<tr>
					<td >工伤保险欠缴金额</td>
					<td colspan="2">${map[3].SO410} 万元</td>
				</tr>
				<tr>
					<td >生育保险欠缴金额</td>
					<td colspan="2">${map[3].SO510} 万元</td>
				</tr>
				</table>
			</div>
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
            text: '城镇职工各保险单位缴费基数分析'
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
            name: '各保险基数占百分比',
            data: [${queryl2}]
        }]
    });

    $('#mainEchart').highcharts({
        chart: {
            type: 'pie',
            options3d: {
            	 enabled: true,
                 alpha: 45,
                 beta: 0
            }
        },
        title: {
            text: '城镇各保险总人数分析'
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
            name: '人员总数占百分比',
            data: [${queryll}]
        }]
    });
    $('#containers2').highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0
            }
        },
        title: {
            text: '城镇职工各保险缴费金额分析'
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
            name: '各保险缴费金额占百分比',
            data: [${queryl3}]
        }]
    });
    $('#containers5').highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0
            }
        },
        title: {
            text: '城镇职工各保险欠缴金额分析'
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
            name: '各保险欠缴金额占百分比',
            data: [${queryl4}]
        }]
    });
    
});
	</script>
</body>
</html>