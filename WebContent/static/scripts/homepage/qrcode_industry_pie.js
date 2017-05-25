/**
 * 牌照一周的申请情况
 * @author 李海涛
 * @time 2016-10-12
 */

function getqrcode_industry_pie(){
	var myChart = echarts.init(document.getElementById('qrcode_industry_pie')); 
	var labelTop = {
		    normal : {
		        label : {
		            show : true,
		            position : 'center',
		            formatter : '{b}',
		            textStyle: {
		                baseline : 'bottom'
		            }
		        },
		        labelLine : {
		            show : false
		        }
		    }
		};
		var labelFromatter = {
		    normal : {
		        label : {
		            formatter : function (params){
		                return 100 - params.value + '%'
		            },
		            textStyle: {
		                baseline : 'top'
		            }
		        }
		    },
		}
		var labelBottom = {
		    normal : {
		        color: '#ccc',
		        label : {
		            show : true,
		            position : 'center'
		        },
		        labelLine : {
		            show : false
		        }
		    },
		    emphasis: {
		        color: 'rgba(0,0,0,0)'
		    }
		};
		var radius = [40, 55];
		option = {
		    legend: {
		        x : 'center',
		        y : 'center',
		        data:[
		            'GoogleMaps','Facebook','Youtube','Google+','Weixin',
		            'Twitter', 'Skype', 'Messenger', 'Whatsapp', 'Instagram',
		            'Twitter', 'Skype', 'Messenger', 'Whatsapp', 'Instagram',
		            'Twitter', 'Skype', 'Messenger'
		        ]
		    },
		    backgroundColor:'#fff',
		    title : {
		        text: 'The App World',
		        subtext: 'from global web index',
		        x: 'center'
		    },
		    series : [
		        {
		            type : 'pie',
		            center : ['10%', '30%'],
		            radius : ['18%', '25%'],
		            x: '0%', // for funnel
		            itemStyle : labelFromatter,
		            data : [
		                {name:'未领取', value:46, itemStyle : labelBottom},
		                {name:'已经领取', value:54,itemStyle : labelTop}
		            ]
		        },
		        {
		            type : 'pie',
		            center : ['25%', '30%'],
		            radius : ['18%', '25%'],
		            x:'20%', // for funnel
		            itemStyle : labelFromatter,
		            data : [
		                {name:'other', value:56, itemStyle : labelBottom},
		                {name:'Facebook', value:44,itemStyle : labelTop}
		            ]
		        },
		        {
		            type : 'pie',
		            center : ['40%', '30%'],
		            radius : ['18%', '25%'],
		            x:'40%', // for funnel
		            itemStyle : labelFromatter,
		            data : [
		                {name:'other', value:65, itemStyle : labelBottom},
		                {name:'Youtube', value:35,itemStyle : labelTop}
		            ]
		        },
		        {
		            type : 'pie',
		            center : ['55%', '30%'],
		            radius : ['18%', '25%'],
		            x:'60%', // for funnel
		            itemStyle : labelFromatter,
		            data : [
		                {name:'other', value:70, itemStyle : labelBottom},
		                {name:'Google+', value:30,itemStyle : labelTop}
		            ]
		        },
		        {
		            type : 'pie',
		            center : ['70%', '30%'],
		            radius : ['18%', '25%'],
		            x:'80%', // for funnel
		            itemStyle : labelFromatter,
		            data : [
		                {name:'other', value:73, itemStyle : labelBottom},
		                {name:'Weixin', value:27,itemStyle : labelTop}
		            ]
		        },
		        {
		            type : 'pie',
		            center : ['85%', '30%'],
		            radius : ['18%', '25%'],
		            x:'80%', // for funnel
		            itemStyle : labelFromatter,
		            data : [
		                {name:'other', value:73, itemStyle : labelBottom},
		                {name:'Weixin', value:27,itemStyle : labelTop}
		            ]
		        },
		        {
		            type : 'pie',
		            center : ['10%', '60%'],
		            radius : ['18%', '25%'],
		            y:'35%',
		            x: '0%', // for funnel
		            itemStyle : labelFromatter,
		            data : [
		                {name:'other', value:46, itemStyle : labelBottom},
		                {name:'GoogleMaps', value:54,itemStyle : labelTop}
		            ]
		        },
		        {
		            type : 'pie',
		            center : ['25%', '100%'],
		            radius : ['18%', '25%'],
		            y:'35%',
		            x:'20%', // for funnel
		            itemStyle : labelFromatter,
		            data : [
		                {name:'other', value:56, itemStyle : labelBottom},
		                {name:'Facebook', value:44,itemStyle : labelTop}
		            ]
		        },
		        {
		            type : 'pie',
		            center : ['40%', '30%'],
		            radius : ['18%', '25%'],
		            y:'35%',
		            x:'40%', // for funnel
		            itemStyle : labelFromatter,
		            data : [
		                {name:'other', value:65, itemStyle : labelBottom},
		                {name:'Youtube', value:35,itemStyle : labelTop}
		            ]
		        },
		        {
		            type : 'pie',
		            center : ['55%', '30%'],
		            radius : ['18%', '25%'],
		            y:'35%',
		            x:'60%', // for funnel
		            itemStyle : labelFromatter,
		            data : [
		                {name:'other', value:70, itemStyle : labelBottom},
		                {name:'Google+', value:30,itemStyle : labelTop}
		            ]
		        },
		        {
		            type : 'pie',
		            center : ['70%', '30%'],
		            radius : ['18%', '25%'],
		            y:'35%',
		            x:'80%', // for funnel
		            itemStyle : labelFromatter,
		            data : [
		                {name:'other', value:73, itemStyle : labelBottom},
		                {name:'Weixin', value:27,itemStyle : labelTop}
		            ]
		        },
		        {
		            type : 'pie',
		            center : ['85%', '30%'],
		            radius : ['18%', '25%'],
		            y:'35%',
		            x:'80%', // for funnel
		            itemStyle : labelFromatter,
		            data : [
		                {name:'other', value:73, itemStyle : labelBottom},
		                {name:'Weixin', value:27,itemStyle : labelTop}
		            ]
		        }
		    ]
		};

	myChart.setOption(option);
}