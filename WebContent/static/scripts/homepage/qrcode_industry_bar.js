/**
 * 牌照一周的申请情况
 * @author 李海涛
 * @time 2016-10-12
 */

function getqrcode_industry_bar(){
	$.ajax({
		type :'post',
		url : contextPath+'/homePageController/queryIndustryphyCount',
		data : {},
		dataType: "json",
		success : function(result){
	var myChart = echarts.init(document.getElementById('qrcode_industry_bar')); 
	option = {
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    backgroundColor:'#fff',
		    legend: {
		        data:result[0]
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : result[0]
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'牌照申请数量',
		            type:'bar',
		            data:result[1],
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        },
		        {
		            name:'牌照领取',
		            type:'bar',
		            data:result[2],
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        },
		        {
		            name:'牌照未领取',
		            type:'bar',
		            data:result[3],
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		            
		        },
		       
		    ]
		};

	   myChart.setOption(option);
	}
		
 });
}