/**
 * 服务业2014-2016年度营业总额
 * @author 李海涛
 * @time 2016-10-12
 */

function getturnoverpyramid(type,industryname,data){
	if(industryname=="----请选择行业----" ||industryname=="----请选择区域----"){
		industryname="";
	}
	if(type==0){
		industryname=industryname+"各区域的2013-2015年度营业总额";
	}else{
		industryname=industryname+"各行业的2013-2015年度营业总额";
	}
	var myChart = echarts.init(document.getElementById('turnoverpyramid')); 
	var dataAxis = eval(data.dataAxis);
	var data1 = eval(data.list_2013);
	var data2 = eval(data.list_2014);
	var data3 = eval(data.list_2015);
	
	var data1s = eval(data.list_2013s);
	var data2s = eval(data.list_2014s);
	var data3s = eval(data.list_2015s);
	option = {
		color: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
		title: {
			show : false,
			text: industryname,
	        x:'center',
	        y:'0',
            textStyle: {
                color: '#fff'
            }
	        
	    },
	    tooltip: {
		    trigger: 'axis',
            formatter : function (params) {
                if (params.length > 1) {
                    return params[0].name
                    +'<br/>'+params[2].seriesName +'<br/>'
                	+ ' 总额: '+ comdify(params[5].value.split(",")[0]) +'万元人民币<br/>'
                	+ ' 企业: '+ comdify(params[5].value.split(",")[1]) +'万元人民币<br/>'
                	+ ' 个体: '+ comdify(params[5].value.split(",")[2]) +'万元人民币<br/>'
                	+ ' 农专: '+ comdify(params[5].value.split(",")[3]) +'万元人民币<br/>'
                +'<br/>'+params[1].seriesName+'<br/>'
                    + ' 总额: '+ comdify(params[4].value.split(",")[0]) +'万元人民币<br/>'
                	+ ' 企业: '+ comdify(params[4].value.split(",")[1]) +'万元人民币<br/>'
                	+ ' 个体: '+ comdify(params[4].value.split(",")[2]) +'万元人民币<br/>'
                	+ ' 农专: '+ comdify(params[4].value.split(",")[3]) +'万元人民币<br/>'
                +'<br/>'+params[0].seriesName+'<br/>'
                    + ' 总额: '+ comdify(params[3].value.split(",")[0]) +'万元人民币<br/>'
                	+ ' 企业: '+ comdify(params[3].value.split(",")[1]) +'万元人民币<br/>'
                	+ ' 个体: '+ comdify(params[3].value.split(",")[2]) +'万元人民币<br/>'
                	+ ' 农专: '+ comdify(params[3].value.split(",")[3]) +'万元人民币<br/>';
                }
                else {
                    return params.seriesName + ' :<br/>'
                       + params.name + ' : '
                       + comdify(params.value) +'万元人民币';
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
	        data: dataAxis,
	        axisLine :{
	            lineStyle :{
	                color: '#fff'
	            }
	        },
	        axisTick :{
	            show: false,
	        },
	        axisLabel:{
	        	textStyle: {
	                color: '#fff'
	            },
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
	        axisTick: {
	            show: false
	        },
	        axisLabel: {
	            textStyle: {
	                color: '#fff'
	            }
	        },
	        axisLine :{
	            lineStyle :{
	                color: '#fff'
	            }
	        },
	    },
	    dataZoom: [
	        {
	            type: 'inside'
	        }
	    ],
	    series: [
	        {	
	        	name:'2013年',
	        	type: 'bar',
	            itemStyle: {
	                normal: {
	                    color: new echarts.graphic.LinearGradient(
	                        0, 0, 0, 1,
	                        [
	                            {offset: 0, color: '#83bff6'},
	                            {offset: 0.5, color: '#188df0'},
	                            {offset: 1, color: '#188df0'}
	                        ]
	                    )
	                },
	                emphasis: {
	                    color: new echarts.graphic.LinearGradient(
	                        0, 0, 0, 1,
	                        [
	                            {offset: 0, color: '#2378f7'},
	                            {offset: 0.7, color: '#2378f7'},
	                            {offset: 1, color: '#83bff6'}
	                        ]
	                    )
	                }
	            },
	            data: data1,
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最高值'},
	                    {type: 'min', name: '最低值'}
	                ],
	                symbolSize : '70'
	            },
	        },
	        {
	        	name:'2014年',
	        	type: 'bar',
	            itemStyle: {
	                normal: {
	                    color: new echarts.graphic.LinearGradient(
	                        0, 0, 0, 1,
	                        [
	                            {offset: 0, color: '#C17876'},
	                            {offset: 0.5, color: '#C23531'},
	                            {offset: 1, color: '#C23531'}
	                        ]
	                    )
	                },
	                emphasis: {
	                    color: new echarts.graphic.LinearGradient(
	                        0, 0, 0, 1,
	                        [
	                            {offset: 0, color: '#C30C07'},
	                            {offset: 0.7, color: '#C30C07'},
	                            {offset: 1, color: '#C17876'}
	                        ]
	                    )
	                }
	            },
	            data: data2,
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最高值'},
	                    {type: 'min', name: '最低值'}
	                ],
	                symbolSize : '70'
	            },
	        },
	        {
	        	name:'2015年',
	        	type: 'bar',
	            itemStyle: {
	                normal: {
	                    color: new echarts.graphic.LinearGradient(
	                        0, 0, 0, 1,
	                        [
	                            {offset: 0, color: '#20C7AE'},
	                            {offset: 0.5, color: '#40C7AE'},
	                            {offset: 1, color: '#40C7AE'}
	                        ]
	                    )
	                },
	                emphasis: {
	                    color: new echarts.graphic.LinearGradient(
	                        0, 0, 0, 1,
	                        [
	                            {offset: 0, color: '#07C3A0'},
	                            {offset: 0.7, color: '#07C3A0'},
	                            {offset: 1, color: '#6ACAB8'}
	                        ]
	                    )
	                }
	            },
	            data: data3,
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最高值'},
	                    {type: 'min', name: '最低值'}
	                ],
	                symbolSize : '70'
	            },
	            
	        }
//	        ================================
	        ,{
	        	name:'2017年1',
	        	type: 'bar',
	            data: data3s,
	        },
	        {
	        	name:'2017年2',
	        	type: 'bar',
	            data: data2s,
	        },
	        {
	        	name:'2017年3',
	        	type: 'bar',
	            data: data3s,
	        }
	        
	    ]
	};
	myChart.setOption(option);
	// Enable data zoom when user click bar.
	var zoomSize = 6;
	myChart.on('click', function (params) {
	    console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
	    myChart.dispatchAction({
	        type: 'dataZoom',
	        startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
	        endValue: dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
	    });
	});
	
}
function comdify(num) 
{
    if(num != 0)
    {
        num = num.toString().replace(/\$|\,/g,'');
        if(''==num || isNaN(num)){return 'Not a Number ! ';}
        var sign = num.indexOf("-")> 0 ? '-' : '';
        var cents = num.indexOf(".")> 0 ? num.substr(num.indexOf(".")) : '';
        cents = cents.length>1 ? cents : '' ;//注意：这里如果是使用change方法不断的调用，小数是输入不了的
        num = num.indexOf(".")>0 ? num.substring(0,(num.indexOf("."))) : num ;
        if('' == cents){ if(num.length>1 && '0' == num.substr(0,1)){return 'Not a Number ! ';}}
        else{if(num.length>1 && '0' == num.substr(0,1)){return 'Not a Number ! ';}}
        for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
        {
            num = num.substring(0,num.length-(4*i+3))+','+num.substring(num.length-(4*i+3));
        }
        return (sign + num + cents);    
    }else{
    	return num;    
    }
}