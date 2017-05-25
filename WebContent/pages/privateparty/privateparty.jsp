<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>非公党建分析</title>
<%@ include file="../system/allresources.jsp"%>
<style type="text/css">
#search{
	margin: 10px;
}
#search label{
	display: -webkit-inline-box;

}
</style>
</head>
<body >
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/plugins/echarts/echarts.min.js"></script>
   		<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/hicharts/highcharts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/hicharts/highcharts-3d.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/jedate/jedate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/scripts/util/json2.js"></script>
 <form action="${pageContext.request.contextPath }/privatepartyController/queryPrivateparty" method="POST" id="search">
   		<select name="year" id="year" style="margin: 0px;">
   			<option  value="">----请选择年份----</option>
   	 		<option value="2013" <c:if test="${pd.year == 2013}">selected</c:if>>2013年</option>
	   		<option value="2014" <c:if test="${pd.year == 2014}">selected</c:if>>2014年</option>
	   		<option value="2015" <c:if test="${pd.year == 2015}">selected</c:if>>2015年</option>
	   		<option value="2016" <c:if test="${pd.year == 2016}">selected</c:if>>2016年</option>
   		</select>
   		<button type="button" class="btn btn-primary" onclick="$('#search').submit();"><i class="fa fa-search"></i> 查询</button>
   	</form>
 	<div id="containers" style="height: 400px; margin-bottom: 30px; width: 50%; margin: 30px auto; float: left;"></div>
				
	<div id="containerling" style="height: 400px; margin-bottom: 30px; width: 50%; margin: 30px auto; float: right;"></div>
    
    <div id="privatepartyline" style="height: 500px; margin-bottom: 30px; width: 50%; margin: 30px auto; float: left;"></div>
    
     <div id="privatepartyzz" style="height: 500px; margin-bottom: 30px; width: 50%; margin: 30px auto; float: right;"></div>
   
   <div id="mainTable2" style="width: 96%; margin-right: 30px;margin-bottom: 20px; float: right;">
					<font size="3" color="red" ><b><c:choose><c:when test="${!empty pd.year}">${pd.year}年</c:when><c:otherwise>2013年-2016年</c:otherwise></c:choose>市场主体非公党建统计</b></font>
					<table class="table table-condensed table-bordered table-hover tab" style="margin-top:10px">
					<tr>
					<td>名称</td><td>主体类型</td><td>市场主体数量（家）</td>
					</tr>
					<c:forEach items="${listpageTa }" var="list" varStatus="status">
								<tr>
								<td rowspan="2">${list.name}</td>
									<td>企业</td>
									<td>${list.qy}</td>
								</tr>
								<tr>
									<td>个体</td>
									<td>${list.gts}</td>
								</tr>
					</c:forEach> 
					</table>
				</div>
    <script type="text/javascript">
    $(function(){ 
   	   $(window).resize(); 
    	}); 
    $(window).resize(function(){ 
        $(".mainTable2").css({ 
            position: "absolute", 
            left: ($(window).width() - $(".mydiv").outerWidth())/2, 
            top: ($(window).height() - $(".mydiv").outerHeight())/2 
        });        
    })
    
    	var myChart = echarts.init(document.getElementById('privatepartyline'));
    	/* var dataAxis = eval(${dataAxis});
    	var dataAxisjy = eval(${dataAxisjy});
    	var jylistsum = eval(${jylistsum});
    	var jylistqy = eval(${jylistqy});
    	var jylistgt = eval(${jylistgt});
    	var listqy = eval(${listqy});
    	var listgt = eval(${listgt});
    	var yuan = eval(${yuan}); */
    	
    	  option = {
    			title: {
    				text: '<c:choose><c:when test="${!empty pd.year}">${pd.year}年</c:when><c:otherwise>2013年-2016年</c:otherwise></c:choose>市场主体中的法定代表人/经营者党关系情况分析',
    		        x:'center',
    		        y:'0',
    		        textStyle: {
    		            fontSize: 18,
    		            fontWeight: 'bold',
    		            color:'#ff0000'
    		        }
    		    },
    		    tooltip: {
        		    trigger: 'axis',
        		    confine: true ,
                    formatter : function (params) {
                        if (params.length > 1) {
                        	   return params[0].name +'的市场主体<br>总共有'
                        	+ params[0].value+'(家) <br>其中为企业的有'+ params[1].value+'(家)，<br>为个体工商户的有'+ params[2].value+'(家)<br/>';
                        }
                        else {
                            return params.seriesName + ' :<br/>'
                               + params.name + ' : '
                               + params.value+'(家)';
                        }
                    },
    		    },
    		    grid: {
    		    	left: '2%',
    		        right: '2%',
    		        bottom: '1%',
    		        containLabel: true
    		    },
    		    xAxis: {
    		        type: 'category',
    		        boundaryGap: false,
    		        data: ['法定代表人/经营者为党组织书记', '法定代表人/经营者为党员', '法定代表人/经营者为群众', '其他'],
    		        axisTick :{
    		            show: false,
    		        },
    		        axisLabel:{  
    		        	interval:0 ,  
    	                formatter : function(params){
    	                    var newParamsName = "";
    	                    var paramsNameNumber = params.length;
    	                    var provideNumber = 4;
    	                    var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
    	                    if (paramsNameNumber > provideNumber) {
    	                        for (var p = 0; p < rowNumber; p++) {
    	                            var tempStr = "";
    	                            var start = p * provideNumber;
    	                            var end = start + provideNumber;
    	                            if (p == rowNumber - 1) {
    	                                tempStr = params.substring(start, paramsNameNumber);
    	                            } else {
    	                                tempStr = params.substring(start, end) + "\n\n";
    	                            }
    	                            newParamsName += tempStr;
    	                        }

    	                    } else {
    	                        newParamsName = params;
    	                    }
    	                    return newParamsName
    	                }
    	            } 
    		    },
    		    
    		    yAxis: {
    		    	 type: 'value',
    		         axisLabel: {
    		             formatter: '{value} (家)'
    		         }
    		    }, 
    		    series: [
    		        {
    		            name:'总数量',
    		            type:'line',
    		            data:${jylistsum},
    		            itemStyle : { normal: {label : {show: true}}},
    		        },	
//    		        ===============
    		        {name:'企业',
    		            type:'line',
    		            data:${jylistqy},
    		        },
    		        {name:'个体',
    		            type:'line',
    		            data:${jylistgt},
    		        }
    		    ]
    		};
    	myChart.setOption(option); 
    
    	//==============
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
		            text: '<c:choose><c:when test="${!empty pd.year}">${pd.year}年</c:when><c:otherwise>2013年-2016年</c:otherwise></c:choose>市场总体成立党委、党总支、党支部的占比统计图',
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
		            name: '市场主体数量所占百分比',
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
		            text: '<c:choose><c:when test="${!empty pd.year}">${pd.year}年</c:when><c:otherwise>2013年-2016年</c:otherwise></c:choose>市场主体成立党委、党总支、党支部的数量统计图',
		            style:{ "color": "#ff0000", "fontSize": "18px" ,fontWeight: 'bold'}
		        },
		        xAxis: {
		            categories: ['党委', '党总支', '党支部', '未成立', '未公布'],
		            crosshair: true
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '市场主体数量(家)'
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
		            headerFormat: '<span style="font-size:10px">{point.key}</span><br/>',
		            pointFormat:'其中{series.name}: {point.y} 家<br/>',
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
		                },
		                
		            }
		        },
		        series: [{
		            name: '企业',
		            data: ${listqy}
		        }, {
		            name: '个体',
		            data: ${listgt}
		        }]
		    });
		});  
    		 
		
		//=========
				//柱图
		
		$(function () {
		    $('#privatepartyzz').highcharts({
		        chart: {
		            type: 'column'
		        },
		        title: {
		            text: '<c:choose><c:when test="${!empty pd.year}">${pd.year}年</c:when><c:otherwise>2013年-2016年</c:otherwise></c:choose>市场主体中的法定代表人/经营者党关系情况分析',
		            style:{ "color": "#ff0000", "fontSize": "18px" ,fontWeight: 'bold'}
		        },
		        xAxis: {
		            categories:['法定代表人/经营者为党组织书记', '法定代表人/经营者为党员', '法定代表人/经营者为群众', '其他'],
		            crosshair: true
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '总数 (家)'
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
		            headerFormat: '<span style="font-size:10px">{point.key}</span><br/>',
		            pointFormat:'其中{series.name}: {point.y} 家<br/>',
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
		            name: '企业',
		            data: ${jylistqy}
		        }, {
		            name: '个体',
		            data: ${jylistgt}
		        }]
		    });
		});
    </script>
</body>
</html>