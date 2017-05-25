<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>外商投资来源</title>
	<%@ include file="../system/allresources.jsp"%>
   	<style type="text/css">
           body {
               margin: 0;
           }
           #main {
               	height: 100%;
           }
           .ranking {
           		position: absolute; 
           		width: 260px; 
           		height:300px; 
           		bottom: 0px; 
           		right: 0px; 
           		background: #F8F8F8; 
           		border:1px solid #ddd;
           		overflow: auto;
           		padding-left: 10px;
           		z-index: 600;
           }
           .ranking ul {
           		width: 100%;
           		list-style: none;
           		margin: 0px;
           }
           .ranking ul li{
           		height: 30px;
           		line-height: 30px;
           }
           .ranking ul li span {
           		padding: 2px 6px;
           		background-color: #ccc;	
           		color: #fff;
           }
	       .ranking ul li:nth-child(1) span{background-color: #FF0000;} 
	       .ranking ul li:nth-child(2) span{background-color: #FF590B;}
	       .ranking ul li:nth-child(3) span{background-color: #FF973B;}
	       .ranking ul li:nth-child(4) span{background-color: #FFE200;}
	       .ranking ul li:nth-child(5) span{background-color: #DCCC9A;}
           /*去掉百度地图图标*/
           .anchorBL{
            	display:none
            }
       </style>
</head>

<body>
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main"></div>
    <div class="ranking">
    	<ul>
    		<c:forEach items="${fiList }" var="list" varStatus="index">
    			<li><span>${index.index+1 }</span>&nbsp;&nbsp;&nbsp;${list.name }：${list.value }</li>
    		</c:forEach>
    	</ul>
    </div>
	<script src="${pageContext.request.contextPath }/static/plugins/echarts_light/js/echarts.js" ></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=vXYOmfFNqXGrd1TISQgt62rw1RzNOgfw"></script>
	<script type="text/javascript">
	    require.config({
	        paths: {
	            echarts: '${pageContext.request.contextPath }/static/plugins/echarts_light/js'
	        },
	        packages: [
	            {
	                name: 'BMap',
	                location: '${pageContext.request.contextPath }/static/plugins/echarts_light/src',
	                main: 'main'
	            }
	        ]
	    });
	
	    require(
	    [
	        'echarts',
	        'BMap',
	        'echarts/chart/map'
	    ],
	    function (echarts, BMapExtension) {
	        $('#main').css({
	            height:$('body').height(),
	            width: $('body').width()
	        });
	        
			// 初始化地图
			var BMapExt = new BMapExtension($('#main')[0], BMap, require('echarts'), require('zrender'));
			var map = BMapExt.getMap();
			var container = BMapExt.getEchartsContainer();
			var startPoint = {
	            x: 104.114129,
	            y: 37.550339
	       	};
			var point = new BMap.Point(startPoint.x, startPoint.y);
			map.centerAndZoom(point, 5);
			map.enableScrollWheelZoom(true);
			
			//左上角，添加默认缩放平移控件
			var top_left_navigation = new BMap.NavigationControl();
			map.addControl(top_left_navigation); 
			
			//2D图，卫星图
			var mapType1 = new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]});
			map.addControl(mapType1); 
			
			option = {
			    tooltip : {
			        trigger: 'item',
			        formatter: function (v) {
			            return v[1].replace(':', ' > ');
			        }
			    },
			    toolbox: {
			        show : true,
			        orient : 'horizontal',
			        x: 'center',
			        y: 'top',
			        feature : {
			            restore : {show: true},
			            saveAsImage : {show: false}
			        }
			    },
			    dataRange: {
			        min : 0,
			        max : 2000,
			        y: '60%',
			        calculable : 3,
			        //color: ['red','#ff3333']
			        color: ['#ff3333', 'orange', 'yellow','lime','aqua']
			    },
			    series : [
			        {
			            name:'江西省',
			            type:'map',
			            mapType: 'none',
			            data:[],
			            geoCoord: {
			            	'江西省': [115.91646,28.68047],
			                '河南省': [113.759604,34.772024],
			                '上海市': [121.480237,31.236305],
			                '天津市': [117.205914,39.090908],
			                '北京市': [116.413554,39.911013],
			                '澳门特别行政区': [113.549403,22.192961],
			                '重庆市': [106.557165,29.570997],
			                '河北省': [114.476188,38.042284],
			                '山西省': [112.569095,37.87976],
			                '辽宁省': [123.435847,41.841317],
			                '吉林省': [125.333383,43.901864],
			                '黑龙江省': [126.669503,45.747709],
			                '江苏省': [118.769502,32.066586],
			                '浙江省': [120.160136,30.271487],
			                '甘肃省': [103.83282,36.065479],
			                '安徽省': [117.292218,31.867312],
			                '福建省': [119.30304,26.106051],
			                '山东省': [117.02702,36.674414],
			                '湖北省': [114.348779,30.551595],
			                '湖南省': [112.989982,28.118301],
			                '广东省': [113.272698,23.137962],
			                '广西壮族自治区': [108.334063,22.821171],
			                '海南省': [110.355167,20.025669],
			                '四川省': [104.082256,30.65679],
			                '贵州省': [106.713693,26.604242],
			                '云南省': [102.716147,25.05156],
			                '陕西省': [108.960637,34.271171],
			                '台湾省': [120.961454,23.80406],
			                '内蒙古自治区': [111.771822,40.823096],
			                '西藏自治区': [91.124021,29.653148],
			                '宁夏回族自治区': [106.265275,38.476848],
			                '新疆维吾尔族自治区': [87.633574,43.799467],
			                '香港特别行政区': [114.171994,22.281089],
			                '青海省': [101.786572,36.627027]
			            },
			            markLine : {
			                smooth:true,
			                effect : {
			                    show: true,
			                    scaleSize: 1,
			                    period: 30,
			                    color: '#fff',
			                    shadowBlur: 10
			                },
			                itemStyle : {
			                    normal: {
			                        borderWidth:1,
			                        lineStyle: {
			                            type: 'solid',
			                          /*   shadowBlur: 10 */
			                        }
			                    }
			                },
			                data : ${fiLink }
			            },
			            markPoint : {
			                symbol:'emptyCircle',
			                symbolSize : function (v){
			                    //return 10 + v/10
			                    return 0;
			                },
			                effect : {
			                    show: false,
			                   /*  shadowBlur : 0 */
			                },
			                itemStyle:{
			                    normal:{
			                        label:{show:false}
			                    }
			                },
			                data : ${fiPoint }
			            }
			        },
			    ]
			};
			
			if (myChart && myChart.dispose) {
			    myChart.dispose();
			}
			var myChart = BMapExt.initECharts(container);
			window.onresize = myChart.resize;
			BMapExt.setOption(option, true)
	    });		
	</script>
</body>
</html>