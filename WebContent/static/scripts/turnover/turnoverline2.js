/**
 * 服务业2014-2016年度营业总额
 * @author 李海涛
 * @time 2016-10-12
 */

function getturnoverline(type,industryname,data){
	if(industryname=="----请选择行业----" ||industryname=="----请选择区域----"){
		industryname="";
	}
	if(type==0){
		industryname=industryname+"各区域的2013-2015年度人员总数";
	}else{
		industryname=industryname+"各行业的2013-2015年度人员总数";
	}
	var myChart = echarts.init(document.getElementById('turnoverline'));
	var dataAxis = eval(data.dataAxis);
	var data1 = eval(data.list_2014);
	var data2 = eval(data.list_2015);
	var data3 = eval(data.list_2016);
	var data4 = eval(data.list_2014_g);
	var data5 = eval(data.list_2015_n);
	var data6 = eval(data.list_2016_q);
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
                    	var arr = new Array();
                    	var arr2 = new Array();
                    	var arr3 = new Array();
                    	arr = params[3].value.split("|");
                    	arr2 = params[4].value.split("|");
                    	arr3 = params[5].value.split("|");
                        return params[0].name
                        +'<br/>'+params[2].seriesName+'：'
                        + ' <br/>&nbsp&nbsp&nbsp总人数有 '+ comdify(params[2].value)+'人，'
                        + '其中企业含有 '+ comdify(arr[0])+'人，'
                        + '农专含有 '+ comdify(arr[1])+'人，'
                        + '个体含有 '+ comdify(arr[2])+'人 。'
                        +'<br/>'+params[1].seriesName+ ' : '
                        + ' <br/>&nbsp&nbsp&nbsp总人数有 '+ comdify(params[1].value)+'人，'
                        + ' 其中企业含有 '+ comdify(arr2[0])+'人，'
                        + ' 农专含有 '+ comdify(arr2[1])+'人，'
                        + ' 个体含有'+ comdify(arr2[2])+'人。 '
                        +'<br/>'+params[0].seriesName+ ' : '
                        + ' <br/>&nbsp&nbsp&nbsp总人数有 '+ comdify(params[0].value)+'人，'
                        + ' 其中企业含有'+ comdify(arr3[0])+'人，'
                        + ' 农专含有 '+ comdify(arr3[1])+'人，'
                        + ' 个体含有 '+ comdify(arr3[2])+'人。';
                    }
                    else {
                        return params.seriesName + ' :<br/>'
                           + params.name + ' : '
                           + comdify(params.value) +'人';
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
		        data: dataAxis,
		        axisTick :{
		            show: false,
		        },
		        axisLine :{
		            lineStyle :{
		                color: '#fff'
		            }
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
		    	axisTick :{
		            show: false,
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
		    series: [
		        {
		            name:'2013年',
		            type:'line',
		            data:data1,
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
		            type:'line',
		            data:data2,
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
		            type:'line',
		            data:data3,
		            markPoint: {
		                data: [
		                    {type: 'max', name: '最高值'},
		                    {type: 'min', name: '最低值'}
		                ],
		                symbolSize : '70'
		            },
		        },
		        {
		            name:'2014年2',
		            type:'line',
		            data:data4,
		            
		        },
		        {
		            name:'2015年2',
		            type:'line',
		            data:data5,
		         
		        },
		        {
		            name:'2016年2',
		            type:'line',
		            data:data6,
		        },
		    ]
		};
	myChart.setOption(option);
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