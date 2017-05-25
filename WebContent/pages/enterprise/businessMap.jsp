<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>营业执照区域分布</title>
	<meta http-equiv="Content-Type" content="textml; charset=utf-8" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="../system/allresources.jsp"%>
	<style type="text/css">
		body, html {
			width: 100%;
			height: 100%;
			margin:0;
			font-family:"微软雅黑";
		}
		#allmap {
			width:100%;
			height:100%;
			z-index: 8;
		}
		p {
			margin-left:5px; 
			font-size:14px;
		}
		.find_btn {
		    background: #438EB9;
		    color: #fff;
		    cursor: pointer;
		    width:50px;
		    height:30px;
		    border: none;
		}
		.select_css {
   			border: 1px solid #ccc;
   			height: 30px;
   			line-height: 30px;
		}
		#mainEcharts {
			background: rgba(255, 255, 255, 0.68) !important;
		}
		#mainEcharts_div {	
			height:400px;
			width: 620px;
			z-index: 333;
			position:absolute;
			bottom:0px;
			text-align: left;
			overflow: hidden;
			/* padding: 0px 20px; */
			}
		.span {
			width:20px;
			height:50px;
			line-height:23px;
			background:#09C;
			float: left;
			color:#fff;
		}
	   	/*去掉百度地图图标*/
        .anchorBL{
           	display:none
         }
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=vXYOmfFNqXGrd1TISQgt62rw1RzNOgfw"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>
	<script src="${pageContext.request.contextPath}/static/plugins/echarts/echarts.min.js"></script>
</head>

<body style="background-color: #fff;">	
	<div style="height:30px; z-index: 999; position:absolute; left:65px; top:15px; ">
		<select class="select_css" id="province" name="province" onchange="onchangeAll(2,this,'city')">
			<option value="00" selected="selected">--请选择省--</option>
		</select>
		<select class="select_css"  id="city" name="city" onchange="onchangeAll(3,this,'county')">
			<option value="00" selected="selected">--请选择市--</option>
		</select>
		<select class="select_css"  id="county" name="county" >
			<option value="00" selected="selected">--请选择区/县--</option>
		</select>
		<button class="btn btn-search" onclick="find();" style="margin-bottom:10px"><i class="fa fa-search"></i> 查询</button>
	</div>
	<div id="allmap"></div>
	<!-- <div id="mainEcharts_div"  onmouseover="startmove(0,10)" onmouseout="startmove(-600,-10)">
		<div id="mainEcharts" style="height:400px;width: 620px;float: left; padding:0px 10px"></div>
		<span class="span">展开</span>
 	</div> -->	
</body>
</html>
<script type="text/javascript">
		// 百度地图API功能
		var map = new BMap.Map("allmap");
		var address="${C_COUNTY_CN}${C_CITY_CN}${C_C_CN}";
		var address_province='${C_COUNTY_CN}';
		var address_city='${C_CITY_CN}';
		var address_county='${C_C_CN}';
		map.centerAndZoom(address,13);
		
		//划分行政区域
		 function getBoundary(C_COUNTY_CN){
			var bdary = new BMap.Boundary();
			bdary.get(C_COUNTY_CN, function(rs){       //获取行政区域
				map.clearOverlays();        //清除地图覆盖物       
				var count = rs.boundaries.length; //行政区域的点有多少个
				if (count === 0) {
					layer.alert('未能获取当前输入行政区域！', {icon: 5});
					return ;
				}
	          	var pointArray = [];
				for (var i = 0; i < count; i++) {
					var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 3, strokeColor: "red",fillColor:"#fff",fillOpacity : 0.1}); //建立多边形覆盖物 背景色fillColor 背景透明度fillOpacity   线的背景色strokeColor
					map.addOverlay(ply);  //添加覆盖物
					pointArray = pointArray.concat(ply.getPath());
				}    
				map.setViewport(pointArray);    //调整视野  
				              
			});   
		}

		setTimeout(function(){
			 //加载所有企业
			getBaseInfo("","");
		}, 2000);

		
		 var opts = {
					width : 280,     // 信息窗口宽度
					height: 160,     // 信息窗口高度
					title : "企业详情" , // 信息窗口标题
					enableCloseOnClick:true
				   };
		
		  function addClickHandler(PRIPID,marker){
				marker.addEventListener("click",function(e){
					openInfo(PRIPID,e)}
				);
			}
		  //单击显示企业详情
		 function openInfo(PRIPID,e){
				var p = e.target;
				//alert("marker的位置是" + p.getPosition().lng + "," + p.getPosition().lat); 
				 $.ajax({
				url : '<%=request.getContextPath() %>/businessMapController/findBaseInfoById',
				type : 'post',
				cache : false,
				data:{PRIPID:PRIPID},
				success : function(text){
					if(text=="error"){
						layer.alert('请检查网络！', {icon: 5});
					}else{
						var data=JSON.parse(text);
						
						var str="<span style=\"display: none;\"></span></br><span>企业名称 ："+data[0].ENTNAME+"</span></br><span style=\"display: none;\">PRIPID："+data[0].PRIPID+"</span></br><span >地址："+data[0].DOM+"</span>";
						
						var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
						
						var infoWindow = new BMap.InfoWindow(str,opts);  // 创建信息窗口对象 
						
						map.openInfoWindow(infoWindow,point); //开启信息窗口
					}
					
					
				},
				error : function(jqXHR, textStatus, error) {
					layer.alert('请检查网络！', {icon: 5});
				}
			});
				
			} 
		 
		  window.onload=function(){
				 //获取省
				 geCity(1,"","province");
				 //柱状图
				 getCount(2,"");
			}
		//显示侧边柱状图
			var timer=null;
			function startmove(target,speed){
				if(navigator.userAgent.indexOf("MSIE")>0){
				    if(navigator.userAgent.indexOf("MSIE 6.0")>0){ 
				    	$('#mainEcharts').css("background","#fff !important;");
				    }
				    if(navigator.userAgent.indexOf("MSIE 7.0")>0){  
				    	$('#mainEcharts').css("background","#fff !important;");
				    }   
				    if(navigator.userAgent.indexOf("MSIE 8.0")>0) {  
				    	$('#mainEcharts').css("background","#fff !important;");
				    }   
				} 
			  var odiv=document.getElementById('mainEcharts_div');
			  clearInterval(timer);
			  timer=setInterval(function (){
			    if(odiv.offsetLeft==target){
			      clearInterval(timer);
			    }
			    else{    
			      odiv.style.left=odiv.offsetLeft+speed+'px';
			    }
			  },30) 
			}
			
		  //加载所有企业
		function  getBaseInfo(C_COUNTY,C_COUNTY_CN){
				
			  if(C_COUNTY_CN == "" || C_COUNTY_CN == null ){
				  C_COUNTY_CN = "${C_COUNTY_CN}${C_CITY_CN}${C_C_CN}";
			  }
				//划分行政区域
			  getBoundary(C_COUNTY_CN);
			  
				var markers =[];
			  map.enableScrollWheelZoom();
			  // 添加带有定位的导航控件
			  var navigationControl = new BMap.NavigationControl({
			    // 靠左上角位置
			    anchor: BMAP_ANCHOR_TOP_LEFT,
			    // LARGE类型
			    type: BMAP_NAVIGATION_CONTROL_LARGE,
			    // 启用显示定位
			    enableGeolocation: true
			  });
			  map.addControl(navigationControl);
			  // 添加定位控件
			   var geolocationControl = new BMap.GeolocationControl();
			  geolocationControl.addEventListener("locationSuccess", function(e){
			    // 定位成功事件
			    var address = '';
			    address += e.addressComponent.province;
			    address += e.addressComponent.city;
			    address += e.addressComponent.district;
			    address += e.addressComponent.street;
			    address += e.addressComponent.streetNumber;
			    //alert("当前定位地址为：" + address);
			  });
			  geolocationControl.addEventListener("locationError",function(e){
			   	 // 定位失败事件
				 //alert(e.message);
			  });
			  map.addControl(geolocationControl); 
			  
			 $.ajax({
					url : '<%=request.getContextPath() %>/businessMapController/queryBaseInfoList',
					type : 'post',
					cache : false,
					data : {C_COUNTY:C_COUNTY},
					success : function(text){
						var data=JSON.parse(text);
						$.each(data, function(i,u){
							if(data[i].MAPX == null || data[i].MAPX == "" || data[i].MAPY == null || data[i].MAPY == ""){
								// 创建地址解析器实例
								var myGeo = new BMap.Geocoder();
								// 将地址解析结果显示在地图上,并调整地图视野
								myGeo.getPoint(data[i].DOM, function(point){
									if (point) {
										var  m =  new BMap.Marker(point); 
										//点的单击事件
										 addClickHandler(data[i].PRIPID,m);
										markers.push(m);
										//最简单的用法，生成一个marker数组，然后调用markerClusterer类即可。
										var markerClusterer = new BMapLib.MarkerClusterer(map, {
											markers : markers
										});
									}
								}, C_COUNTY_CN);
							}else{
								var  m =  new BMap.Marker(new BMap.Point(data[i].MAPX,data[i].MAPY));  
								//点的单击事件
								 addClickHandler(data[i].PRIPID,m);
								markers.push(m);
							}

						 });
						 
						//最简单的用法，生成一个marker数组，然后调用markerClusterer类即可。
						var markerClusterer = new BMapLib.MarkerClusterer(map, {
							markers : markers
						});
					},
					error : function(jqXHR, textStatus, error) {
						layer.alert('请检查网络！', {icon: 5});
					}
				});
		}
		  
		  //查询
		 function find(){
				//编码
			  var C_COUNTY = "";
			 //显示的区域
			var C_COUNTY_CN = "";
			 
			 if($('#province').val() == "00" && $('#city').val() == "00" && $('#county').val() == "00"){
				 C_COUNTY = "";
				 C_COUNTY_CN = "${C_COUNTY_CN}";
			 }else if($('#city').val() == "00" && $('#county').val() == "00"){
				 C_COUNTY = $('#province').val();
				 C_COUNTY_CN = $('#province option:selected').text();
			 }else  if($('#county').val() == "00"){
				 C_COUNTY = $('#city').val();
				 C_COUNTY_CN = $('#province option:selected').text()+""+$('#city option:selected').text();
			 }else {
				 C_COUNTY = $('#county').val();
				 C_COUNTY_CN = $('#province option:selected').text()+""+$('#city option:selected').text()+""+$('#county option:selected').text();
			 }
			 
			 //重新new一下地图
			 //获取该行政区域的企业
			  map = new BMap.Map("allmap");
			  map.centerAndZoom(C_COUNTY_CN,13);
			 getBaseInfo(C_COUNTY,C_COUNTY_CN);
			 //柱状图
			 if(LEVEL==1){
				 LEVEL==2;
			 }
			 getCount(LEVEL,C_COUNTY);
		 }
		 
		  var LEVEL = 2;//级别
		  //三级联动
		 function onchangeAll(C_AREA_LEVEL,id,type){
			 if(C_AREA_LEVEL == 2){
				 //当该改变了省时，清空县
				 var osel = document.getElementById("county");
					//先清空所有值 select中的option
					for (var i = osel.options.length - 1; i > 0; i--) {
					osel.options.remove(i);
				}
			 }
			 
			 if(id.value == "00"){
				 var osel = document.getElementById(type);
					//先清空所有值 select中的option
					for (var i = osel.options.length - 1; i > 0; i--) {
					osel.options.remove(i);
				}
				LEVEL=2;
				return false;
			 }
			 LEVEL = C_AREA_LEVEL;
			 geCity(C_AREA_LEVEL,id.value,type);
		 }
		 
		//获取省市县
		function geCity(C_AREA_LEVEL,C_CODE,type){
			$.ajax({
				url : '${pageContext.request.contextPath}/businessMapController/queryCityList',
				type : 'post',
				cache : false,
				data : {C_AREA_LEVEL:C_AREA_LEVEL,C_CODE:C_CODE},
				success : function(text){
					//alert(text);
					var obj=JSON.parse(text);
					
						var osel = document.getElementById(type);
						//先清空所有值 select中的option
						for (var i = osel.options.length - 1; i > 0; i--) {
							osel.options.remove(i);
						}
						
						if(obj.length>0){
							for (var i = 0 ,len= obj.length; i<len;i++) {
								if(obj[i].C_NAME == address_province){
									 var   op= new Option(obj[i].C_NAME, obj[i].C_CODE);
									 op.selected = true;  
									osel.options.add(op);
									//默认选中省时 把该省下面所有的市也查询出来
									onchangeAll(2,osel,"city");
									 address_province='';
								}else if(obj[i].C_NAME == address_city){
									 var   op= new Option(obj[i].C_NAME, obj[i].C_CODE);
									 op.selected = true;  
									osel.options.add(op);
									//默认选中省时 把该省下面所有的市也查询出来
									onchangeAll(3,osel,"county");
									 address_city="";
								}else if(obj[i].C_NAME == address_county){
									 var   op= new Option(obj[i].C_NAME, obj[i].C_CODE);
									 op.selected = true;  
									osel.options.add(op);
									//默认选中省时 把该省下面所有的市也查询出来
									//onchangeAll(2,osel,"city");
									 address_county='';
								}else{
									osel.options.add(new Option(obj[i].C_NAME, obj[i].C_CODE));
								}
								
							}
						}else{
							osel.options.add(new Option("暂无数据", "00"));
						}
				},
				error : function(jqXHR,textStatus,error){
					//parent.layer.msg('网络超时!');
								}
			});
		}
		
		//柱状图统计
		function getCount(C_AREA_LEVEL,C_CODE){
			$.ajax({
				url : '${pageContext.request.contextPath}/businessMapController/queryCount',
				type : 'post',
				cache : false,
				data : {C_AREA_LEVEL:C_AREA_LEVEL,C_CODE:C_CODE},
				success : function(text){
					var obj=JSON.parse(text);
					
					//基于准备好的dom，初始化echarts图表
					var myChart = echarts.init(document.getElementById('mainEcharts')); 
					option = {
						    tooltip : {
						        trigger: 'axis'
						    },
						    grid: {
						        left: '0%',
						        right: '5%',
						        top: '1%',
						        bottom: '1%',
						        containLabel: true
						    },
						    xAxis : [
						        {
						            type : 'value',
						            boundaryGap : [0, 0.01]
						        }
						    ],
						    yAxis : [
						        {
						            type : 'category',
						            data : obj.name
						        }
						    ],
						    series : [
						        {
						            name:'牌照领取量',
						            type:'bar',
						            data:obj.count,
						       	 markPoint : {
						           		symbolRotate:-90,
						                data : [
						                {type : 'max', name: '最大值'},
						                   {type : 'min', name: '最小值'}
						                  
						                ],
						            },
						        }
						       
						    ]
						};
						                                   
						 // 为echarts对象加载数据   
					    myChart.setOption(option);  
						
				},
				error : function(jqXHR,textStatus,error){
					//parent.layer.msg('网络超时!');
								}
			});
		}
</script>