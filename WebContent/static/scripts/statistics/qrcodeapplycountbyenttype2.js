/**
 * 牌照一周的申请情况
 * @author 李海涛
 * @time 2016-10-12
 */

function getqrcodeapplycountbyenttype2(){
	var myChart = echarts.init(document.getElementById('qrcodeapplycountbyenttype2_div')); 

	option = {
		    tooltip : {
		        trigger: 'axis'
		    },
		    grid: {
		        left: '-1%',
		        right: '2%',
		        top: '1%',
		        bottom: '5%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		        	show : false,
		            type : 'category',
		            boundaryGap : false,
		            data : ['周一','周二','周三','周四','周五','周六','周日']
		        }
		    ],
		    yAxis : [
		        {
		        	show : false,
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'邮件营销',
		            type:'line',
		            smooth :true,
		            stack: '总量',
		            label: {
		                normal: {
		                    show: true,
		                    position: 'top'
		                }
		            },
		            itemStyle: {
		                normal: {
		                    areaStyle: {
		                        // 区域图，纵向渐变填充
		                        color : "#445394",
		                    }
		                }
		            },
		            data:[
		                120, 132, 301, 134,90,230, 210
		            ]
		        }
		    ]
		};
	myChart.setOption(option);
}