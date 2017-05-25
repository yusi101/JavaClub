<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>近7天数据更新情况</title>
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
 	<div id="containers" style="height: 500px; margin-bottom: 30px; width: 100%; margin: 30px auto; float: left;"></div>
   
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
    
    	var myChart = echarts.init(document.getElementById('containers'));
    	
    option = {
    	    title: {
    	        text: '7天数据更新情况分析',
    	        subtext: '来自表  zr_qydjxx 的数据',
    	    },
    	    tooltip: {
    	        trigger: 'axis'
    	    },
    	    legend: {
    	        data:['数据更新情况']
    	    },
    	    toolbox: {
    	        show: true,
    	        feature: {
    	            dataZoom: {
    	                yAxisIndex: 'none'
    	            },
    	            dataView: {readOnly: false},
    	            magicType: {type: ['line', 'bar']},
    	            restore: {},
    	            saveAsImage: {}
    	        }
    	    },
    	    xAxis:  {
    	        type: 'category',
    	        boundaryGap: false,
    	        data: ${data}
    	    },
    	    yAxis: {
    	        type: 'value',
    	        axisLabel: {
    	            formatter: '{value}条 '
    	        }
    	    },
    	    series: [
    	        {
    	            name:'数据更新情况',
    	            type:'line',
    	            data:${nums},
    	            itemStyle : { normal: {label : {show: true}}},
    	            /* markPoint: {
    	                data: [
    	                    {type: 'max', name: '最大值'},
    	                    {type: 'min', name: '最小值'}
    	                ]
    	            }, */
    	            markLine: {
    	                data: [
    	                    {type: 'average', name: '平均值'}
    	                ]
    	            }
    	        }
    	    ]
    	};

    	myChart.setOption(option); 
    
    </script>
</body>
</html>