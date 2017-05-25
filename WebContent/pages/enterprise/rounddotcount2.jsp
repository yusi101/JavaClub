<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<script src="http://cdn.bootcss.com/json2/20160511/json2.js"></script>
   	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=vXYOmfFNqXGrd1TISQgt62rw1RzNOgfw"></script>
    <script type="text/javascript" src="http://mapv.baidu.com/build/mapv.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/scripts/util/Base64.js"></script>
    <%@ include file="../system/allresources.jsp"%>
    <title>营业执照分布圆点图</title>
    <style type="text/css">
        html, body {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }
        #map {
            width: 100%;
            height: 100%;
        }
        .btn-chakan {
        	background: skyblue; 
        	padding: 5px 12px; 
        	color:#FFF; 
        	margin:-10px 0px 0px 10px;
        }
		.anchorBL{
			display:none
		}
    </style>
</head>
<body>
    <div id="map">
    	<div id="search" style="display:none">
    		&emsp;&emsp;&emsp;地区：<select name="county" id="county"  >
    			<option value="" name="南昌市">南昌市</option>
    			<c:forEach items="${countyList }" var="list">
    				<option value="${list.C_CODE}" name="${list.C_NAME}">${list.C_NAME}</option>
    			</c:forEach> 
    		</select>&nbsp;&nbsp;&nbsp;
    	企业名：<input type="text" placeholder="请输入查询的企业名" id="entname" name="entname" style="margin-top:-10px">					
    	<button class="btn-chakan" onclick="getData()">查询</button>
    	</div>
    
    </div>
    <canvas id="canvas"></canvas>
    <script type="text/javascript">
    getData();
		// 添加文字说明
		var search = document.getElementById("search").innerHTML;
		
	  	//回车提交事件
	    document.onkeydown = function(event) {
	    	e = event ? event :(window.event ? window.event : null); 
		    if(e.keyCode == 13){ 
			    //执行的方法 
		    	getData();
		    }
	    }
	  	
		// 创建Map实例
        var map = new BMap.Map("map", {
            enableMapClick: false
        });
        
        // 创建点坐标
        var point = new BMap.Point(115.864528,28.687675);
        
     	// 初始化地图,设置中心点坐标和地图级别
        map.centerAndZoom(point, 15);  
        map.enableScrollWheelZoom(true); 	// 禁用滚轮放大缩小。
		map.disableDoubleClickZoom();		// 禁用双击放大。
		//map.disableKeyboard();				// 禁用键盘操作。
		//map.disableDragging();				// 禁用地图拖拽。
		map.disablePinchToZoom();			// 禁用双指操作缩放。
		map.setMinZoom(12);					// 设置地图允许的最小级别
		map.setMaxZoom(18);					// 设置地图允许的最大级别。
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
		    alert("当前定位地址为：" + address);
		});
		  	
		geolocationControl.addEventListener("locationError",function(e){
		    // 定位失败事件
		    alert(e.message);
		});
		map.addControl(geolocationControl);
		
		//右下角，打开
		var overViewOpen = new BMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT});
		map.addControl(overViewOpen); 
		
		//2D图，卫星图
		var mapType1 = new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]});
		map.addControl(mapType1);          
		
 		// 定义一个控件类,即function
		function SearchControl(){
		  // 默认停靠位置和偏移量
		  this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
		  this.defaultOffset = new BMap.Size(15, 10);
		}
		
		// 通过JavaScript的prototype属性继承于BMap.Control
		SearchControl.prototype = new BMap.Control();

		// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
		// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
		SearchControl.prototype.initialize = function(map){
			var div = document.createElement("div");
			div.style.cursor = "pointer";
			div.innerHTML = search;
			map.getContainer().appendChild(div);
			   
			return div;
		}
		
		// 创建控件
		var mySCtrl = new SearchControl();
		map.addControl(mySCtrl); 
		
		//获取化区
		function getBoundary(county){
			var countyName = county == "南昌市" ? "南昌市" : "南昌市" + county;
			var bdary = new BMap.Boundary();
			bdary.get(countyName, function(rs){       	//获取行政区域
				//map.clearOverlays();        		//清除地图覆盖物
				var count = rs.boundaries.length; 	//行政区域的点有多少个
				if (count === 0) {
					layer.msg("未能获取当前输入行政区域");
					return ;
				}
	          	var pointArray = [];
				for (var i = 0; i < count; i++) {
					var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 3, strokeColor: "red",fillColor:"#fff",fillOpacity : 0.1}); //建立多边形覆盖物
					map.addOverlay(ply);  		//添加覆盖物
					pointArray = pointArray.concat(ply.getPath());
				}    
				map.setViewport(pointArray);    //调整视野  
			});   
		}
		//获取坐标
		function getAddressxy(county){
			var countyName = county;
			var bdary = new BMap.Boundary();
			bdary.get(countyName, function(rs){       	//获取行政区域
				//map.clearOverlays();        		//清除地图覆盖物
				var count = rs.boundaries.length; 	//行政区域的点有多少个
				if (count === 0) {
					layer.msg("未能获取当前输入行政区域");
					return ;
				}
	          	var pointArray = [];
				for (var i = 0; i < count; i++) {
					var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 3, strokeColor: "red",fillColor:"#fff",fillOpacity : 0.1}); //建立多边形覆盖物
					map.addOverlay(ply);  		//添加覆盖物
					pointArray = pointArray.concat(ply.getPath());
				}    
				map.setViewport(pointArray);    //调整视野  
			});   
		}
        
		//获取选中的坐标
		function getCoordinate(lng,lat){
    		point = new BMap.Point(lng, lat);
        	map.centerAndZoom(point, 15);  
        	//getBoundary(countyText);
		}
		
		// 获取数据
		function getData(){
			var countyName = $('#county option:selected').attr("name");
			var entname = $('#entname').val();
			
			var county = $("#county option:selected").val();
			//正在加载中
			ShowLoading();
			
    	    //获取的参数
    	    var formdata = {
    	    		C_COUNTY: county,
    	    		entname:entname
    	    }
    		
    	    //访问请求
    	    $.ajax({
    	        url: contextPath + '/businessMapController/queryRoundEntactive',
    	        type: "post",
    	        data: formdata,
    	        success: function (result) {
    				//清除地图覆盖物
    				map.clearOverlays();
    				getBoundary(countyName);
    				
     	        	if("" != result){
     	        		var resultData = result.split("::");
							//设置圆点数据
						 	var data = JSON.parse(resultData[0]);
						 	if(data.length > 0 && data.length < 2){
						 		getCoordinate(data[0].geometry.coordinates[0],data[0].geometry.coordinates[1]);
						 	}
					        var dataSet = new mapv.DataSet(data);
					        var opts = {
									width : 280,     // 信息窗口宽度
									height: 160,     // 信息窗口高度
									title : "企业详情" , // 信息窗口标题
									enableCloseOnClick:true
								   };
					        var options = {
					            fillStyle: '#ff0000',
					            shadowBlur: 30,
					            globalCompositeOperation: 'lighter',
					            methods: {
					                click: function (item) {
						                	if(null != item){
						                		$.ajax({
							              	        url: contextPath + '/businessMapController/findBasexyInfoById',
							              	        type: "post",
							              	        data: {
							                	    	map: item.geometry.coordinates,
							                	    },
							              	        success: function (result) {
							              	        	if(result=="error"){
							        						layer.alert('请检查网络！', {icon: 5});
							        					}else{
							        						var data=JSON.parse(result);
							        						
							        						var str="<span style=\"display: none;\"></span></br>企业名称 ：<span onclick=\"onData('"+data[0].REGNO+"','"+data[0].PRIPID+"')\" style=\"color:red\">"+data[0].ENTNAME+"</span></br><span style=\"display: none;\">PRIPID："+data[0].PRIPID+"</span></br><span >地址："+data[0].DOM+"</span>";

							        					 	var point = new BMap.Point(data[0].MAPX, data[0].MAPY);
							        						
							        						var infoWindow = new BMap.InfoWindow(str,opts);  // 创建信息窗口对象 
							        						
							        						map.openInfoWindow(infoWindow,point); //开启信息窗口
							        					}
							              	        	 /* layer.alert(str, {
		        					                		  skin: 'layui-layer-molv' //样式类名
		        					                		  ,closeBtn: 0
		        					                		});     */           	
							              	        }
							              	    }); 
						                	}else{
						                		/* layer.alert("无法获取相关信息!", {
	  					                		  skin: 'layui-layer-molv' //样式类名
	  					                		  ,closeBtn: 0
	  					                		}); */
						                	}
					                }
					            },
					            size: 10,
					            draw: 'simple'
					        }
					        var mapvLayer = new mapv.baiduMapLayer(map, dataSet, options);
    	        	} else {
    	        		layer.msg("暂时没有数据");
    	        	}
    	        	
     	       		//隐藏正在加载中
    	        	HideLoading();
    	        }
    	    });
    	}
		 function showInfo(){
				window.location.href="http://www.baidu.com";
			}
		function onData(regnore,pripid){
			var base64 = new Base64();
			var rg = new String(regnore);
			var re = base64.encode(rg);
			var pi = new String(pripid);
			var pd = base64.encode(pi);
			var url= "http://192.168.10.156:8080/ECPS/qyxxgsAction_initQyxyxxMains.do";
			window.open (url+'?pripid='+pi+'&regnore='+rg );
		}
        //mapvLayer.show(); // 显示图层
        // mapvLayer.hide(); // 删除图层
      	// dataSet.set(data);// 修改数据
    </script>
</body>
</html>