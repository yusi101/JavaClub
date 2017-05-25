<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/jedate/skin/jedate.css">
	<%@ include file="../system/allresources.jsp"%>
	<title>从业人员分析</title>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">从业人员分析</h4>
			 <form action="${pageContext.request.contextPath }/privatepartyController/queryEmployee" method="POST" id="search">
	   		<select name="year" id="year" style="margin: 0px;">
	   			<option  value="" >----请选择年份----</option>
	   				<option value="2013" <c:if test="${pd.year == 2013}">selected</c:if>>2013年</option>
	   				<option value="2014" <c:if test="${pd.year == 2014}">selected</c:if>>2014年</option>
	   				<option value="2015" <c:if test="${pd.year == 2015}">selected</c:if>>2015年</option>
	   				<option value="2016" <c:if test="${pd.year == 2016}">selected</c:if>>2016年</option>
	   		</select>
	   		<button type="button" class="btn btn-primary" onclick="$('#search').submit();">查询</button>
				</form>
			<div>
				<div id="containers" style="height: 400px; margin-bottom: 30px; width: 50%; margin: 30px auto; float: left;"></div>
				
				<div id="containerling" style="height: 400px; margin-bottom: 30px; width: 50%; margin: 30px auto; float: right;"></div>
			
				<div id="mainEchart2" style="width: 100%; margin: 10px auto; float: right;">
					<font size="3" color="red" ><b><c:choose><c:when test="${!empty pd.year}">${pd.year}年</c:when><c:otherwise>2013年-2016年</c:otherwise></c:choose>从业人员信息表格统计表</b></font>
					<table class="table table-condensed table-bordered table-hover tab" style="margin-top:10px">
					<tr>
					<td>人员类别</td><td>工作类别</td><td>人数</td><td>其中隶属企业的人数</td><td>其中隶属农专的人数</td><td>其中隶属个体的人数</td>
					</tr>
					<c:forEach items="${listpages1 }" var="list" varStatus="status">
								<tr>
								<td rowspan="2">${list.name}</td>
									<td>经营者</td>
									<td>${list.sum1}</td>
									<td>${list.qiye1}</td>
									<td>${list.nz1}</td>
									<td>${list.gts1}</td>
								</tr>
								<tr>
									<td>雇工</td>
									<td>${list.sum2}</td>
									<td>${list.qiye2}</td>
									<td>${list.nz2}</td>
									<td>${list.gts2}</td>
								</tr>
					</c:forEach>
					</table>
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
		    $('#containers1').highcharts({
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
		
		//柱图
		
		$(function () {
		    $('#containerling').highcharts({
		        chart: {
		            type: 'column'
		        },
		        title: {
		            text: '<c:choose><c:when test="${!empty pd.year}">${pd.year}年</c:when><c:otherwise>2013年-2016年</c:otherwise></c:choose>从业人员信息柱状统计图',
		            style:{ "color": "#ff0000", "fontSize": "18px" ,fontWeight: 'bold'}
		        },
		        xAxis: {
		        	 categories: [
			                '高校毕业生',
			                '退役士兵',
			                '残疾人',
			                '再就业',
			                '党员/预备党员',
			                '农民人数'
			            ],
		            crosshair: true
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '人数 (个)'
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
		            pointFormat:'{series.name}: {point.y}个人<br/>',
		            footerFormat: '',
		            shared: true,
		            useHTML: true
		        },
		        plotOptions: {
		            series: {
		                borderWidth: 0,
		                dataLabels: {
		                    enabled: true,
		                    format: '{point.y}'
		                }
		            }
		        },
		        series: [{
		            name: '经营者（个）',
		            data: ${jingy}
		        }, {
		            name: '雇工（个）',
		            data: ${guz}
		        }]
		    });
		});
	    
		//折线
		
		$(function () {
	    	$('#containers').highcharts({
	        title: {
	            text: '<c:choose><c:when test="${!empty pd.year}">${pd.year}年</c:when><c:otherwise>2013年-2016年</c:otherwise></c:choose>从业人员信息折线统计图',
	            style:{ "color": "#ff0000", "fontSize": "18px" ,fontWeight: 'bold'}
	        },
	        credits: { 
	        	enabled: false //不显示LOGO 
	        	},
	        xAxis: {
	            categories: [
	                '高校毕业生',
	                '退役士兵',
	                '残疾人',
	                '再就业',
	                '党员/预备党员',
	                '农民人数'
	            ]
	        },
	        yAxis: {
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
	        	  },
	        	  title: {
		                text: '总人数 (个)'
		            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        plotOptions: {
	            series: {
	                borderWidth: 0,
	                dataLabels: {
	                    enabled: true,
	                    format: '{point.y}'
	                }
	            }
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	       
	        series: [{
	            name: '总人数',
	            data: ${sum}
	        },{
	            name: '经营者',
	            data: ${jingy},
	            visible:false
	        },{
	            name: '雇工',
	            data: ${guz},
	            visible:false
	        }
	        ]
	    });
	});

	</script>
</body>
</html>