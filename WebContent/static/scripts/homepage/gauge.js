function getgauge(){
	var myChart = echarts.init(document.getElementById('gauge_echarts')); 
	option = {
		    tooltip : {
		        formatter:  function(params) {  //proportion
		              var res = params.name+'<br/>';  
		             // var myseries = option.proportion;  
		             // res+= "myseries[i].name"+myseries.proportion;  
		             /*  for (var i = 0; i < myseries.length; i++) {  
		             	 res+= "myseries[i].name"+myseries[i].proportion;  
		              	for(var j=0;j<myseries[i].data.length;j++){  
		              		if(myseries[i].data[j].name==params.name){  
		             			 res+=' : '+myseries[i].data[j].proportion+'</br>';  
		              		}  
		              	}         
		              }  */ 
		              return res;  
		        }  
		    },
		    backgroundColor:'#fff',
		    toolbox: {
		        show : false,
		        
		    },
		    title : {
            	offsetCenter: ['0%', '100%'],       // x, y，单位px
            	text : '牌照等比统计',
                x:'center',
                y:20,
                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontWeight: 'bolder',
                    fontSize: 30,
                    fontStyle: 'normal',
                    color:'#4488BB'
                }
            },
		    series : [
		        {
		            name:'速度',
		            type:'gauge',
		            z: 3,
		            min:0,
		            radius : '60%',
		            max:220,
		            splitNumber:11,
		            axisLine: {            // 坐标轴线
		                lineStyle: {       // 属性lineStyle控制线条样式
		                    width: 10
		                }
		            },
		            axisTick: {            // 坐标轴小标记
		                length :15,        // 属性length控制线长
		                lineStyle: {       // 属性lineStyle控制线条样式
		                    color: 'auto'
		                }
		            },
		            axisLabel : {
		            	formatter :function (value){
		            		return value+"%";
		            	}
		            },
		            splitLine: {           // 分隔线
		                length :20,         // 属性length控制线长
		                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
		                    color: 'auto'
		                }
		            },
		            title : {
		            	offsetCenter: ['0%', '100%'],       // x, y，单位px
		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		                    fontWeight: 'bolder',
		                    fontSize: 20,
		                    fontStyle: 'italic'
		                }
		            },
		            detail : {
		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		                    fontWeight: 'bolder'
		                }
		            },
		            data:[{value: 40, name: '牌照申请比例（新建）'}]
		        },
		        {
		            name:'转速',
		            type:'gauge',
		            center : ['15%', '55%'],    // 默认全局居中
		            radius : '45%',
		            min:0,
		            max:7,
		            splitNumber:7,
		            axisLine: {            // 坐标轴线
		                lineStyle: {       // 属性lineStyle控制线条样式
		                    width: 8
		                }
		            },
		            axisTick: {            // 坐标轴小标记
		                length :12,        // 属性length控制线长
		                lineStyle: {       // 属性lineStyle控制线条样式
		                    color: 'auto'
		                }
		            },
		            splitLine: {           // 分隔线
		                length :20,         // 属性length控制线长
		                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
		                    color: 'auto'
		                }
		            },
		            pointer: {
		                width:5
		            },
		            title : {
		            	offsetCenter: ['0%', '110%'],       // x, y，单位px
		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		                    fontWeight: 'bolder',
		                    fontSize: 20,
		                    fontStyle: 'italic'
		                }
		            },
		            detail : {
		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		                    fontWeight: 'bolder'
		                }
		            },
		            data:[{value: 1.5, name: '牌照领取比例'}]
		        },
		        {
		            name:'转速',
		            type:'gauge',
		            center : ['85%', '55%'],    // 默认全局居中
		            radius : '45%',
		            min:0,
		            max:100,
		            splitNumber:10,
		            axisLine: {            // 坐标轴线
		                lineStyle: {       // 属性lineStyle控制线条样式
		                    width: 8
		                }
		            },
		            axisTick: {            // 坐标轴小标记
		                length :12,        // 属性length控制线长
		                lineStyle: {       // 属性lineStyle控制线条样式
		                    color: 'auto'
		                }
		            },
		            splitLine: {           // 分隔线
		                length :20,         // 属性length控制线长
		                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
		                    color: 'auto'
		                }
		            },
		            pointer: {
		                width:5
		            },
		            title : {
		            	offsetCenter: ['0%', '110%'],       // x, y，单位px
		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		                    fontWeight: 'bolder',
		                    fontSize: 20,
		                    fontStyle: 'italic'
		                }
		            },
		            detail : {
		            	
		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		                    fontWeight: 'bolder'
		                }
		            },
		            data:[{value: 15, name: '牌照未领取比例',proportion:'55%'}]
		        },
		    ]
		};

myChart.setOption(option);
}