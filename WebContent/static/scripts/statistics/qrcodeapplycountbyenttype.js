/**
 * 牌照一周的申请情况
 * @author 李海涛
 * @time 2016-10-12
 */

function getqrcodeapplycountbyenttype(){
	var myChart = echarts.init(document.getElementById('qrcodeapplycountbyenttype2')); 

	option = {
	    tooltip: {
	        trigger: 'axis'
	    },
	    color: ['#3398DB'],
	    toolbox: {
	       
	    },
	    legend: {
	    	show : false,
	        data:['蒸发量','降水量','平均温度']
	    },
	    xAxis: [
	        {
	            show : false,
	            type: 'category',
	            data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
	        }
	    ],
	    yAxis: [
	        {
	        	show : false,
	            type: 'value',
	            name: '水量',
	            min: 0,
	            max: 250,
	            interval: 50,
	            axisLabel: {
	                formatter: '{value} ml'
	            }
	        },
	        {
	        	show : false,
	            type: 'value',
	            name: '温度',
	            min: 0,
	            max: 25,
	            interval: 5,
	            axisLabel: {
	                formatter: '{value} °C'
	            }
	        }
	    ],
	    series: [
	        {
	            name:'蒸发量',
	            type:'bar',
	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
	        },
	        {
	            name:'平均温度',
	            yAxisIndex: 1,
	            type: 'line',
	            symbol:'circle', //图标形状
	            showAllSymbol: true,
	            symbolSize: function (){
	                return 20;
	            },
	            itemStyle:{
	                normal:{
	                    color: "#FFFF00", //图标颜色
	                }
	            },
	            data:[7.0, 9.9, 8.0, 6.2, 9.6, 14.7, 16.6, 18.2, 11.6, 9.0, 6.4, 3.3]
	        }
	    ]
	};

	myChart.setOption(option);
}