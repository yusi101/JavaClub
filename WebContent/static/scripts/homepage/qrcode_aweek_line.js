/**
 * 牌照一周的申请情况
 * @author 李海涛
 * @time 2016-10-12
 */

function getqrcode_aweek_line(){
	var myChart = echarts.init(document.getElementById('qrcode_aweek_line')); 
	option = {
		    title: {
		        text: '近七天的牌照申请、领取、未领取状况',
		        	x:'center',
		        	y: '330'
		    },
		    backgroundColor:'#fff',
		    tooltip: {
		       trigger: "item",
		       formatter: "{a} <br/>{b} : {c}"
		   },
		    legend: {
		        data:['牌照领取','牌照申请','牌照未领取']
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '10%',
		        containLabel: true
		    },
		    toolbox: {
		    },
		    xAxis: {
		        type: 'category',
		        boundaryGap: false,
		        data: ['周一','周二','周三','周四','周五','周六','周日']
		    },
		    yAxis: {
		        type: 'value'
		    },
		    series: [
		        {
		            name:'牌照领取',
		            type:'line',
		            stack: '总量',
		            data:[120, 132, 101, 134, 90, 230, 210]
		        },
		        {
		            name:'牌照申请',
		            type:'line',
		            stack: '总量',
		            data:[220, 182, 191, 234, 290, 330, 310]
		        },
		        {
		            name:'牌照未领取',
		            type:'line',
		            stack: '总量',
		            data:[150, 232, 201, 154, 190, 330, 410]
		        }
		    ]
		};

	myChart.setOption(option);
}