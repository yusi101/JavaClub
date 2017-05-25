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
    <%@ include file="../system/allresources.jsp"%>
    <title>行业分布圆点图</title>
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
        	border:none; 
        	margin:-10px 0px 0px 10px;
        }
		.anchorBL{
			display:none
		}
    </style>
</head>
<body>
    <div id="map">
    	<div id="search" style="display: none;">
    		区县：<select name="county" id="county" onchange="getCoordinate()">
    			<option>区县</option>
    			<c:forEach items="${countyList }" var="list">
    				<option value="${list.C_CODE }" name="${list.C_REMARKS }">${list.C_NAME }</option>
    			</c:forEach> 
    		</select>&nbsp;&nbsp;&nbsp;
    		行业：<select name="industry" id="industry">
    			<option>行业</option>
    	 		<c:forEach items="${industryList }" var="list">
    				<option value="${list.EC_VALUE }">${list.EC_NAME }</option>
    			</c:forEach> 
    		</select>
    		<button class="btn-chakan" onclick="getData()">查看</button>
    	</div>
    </div>
    <canvas id="canvas"></canvas>
    <script type="text/javascript">
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
        
		//获取选中的坐标
		function getCoordinate(){
    		var countyName = $('#county option:selected').attr("name");
    		var countyText =  $('#county option:selected').text();
    		var coordinate = countyName.split("@");
    		var lng = eval(coordinate[0]);
    		var lat = eval(coordinate[1]);
    		
    		point = new BMap.Point(lng, lat);
        	map.centerAndZoom(point, 15);  
        	//getBoundary(countyText);
		}
		
		// 获取数据
		function getData(){
			var county = $("#county option:selected").val() == '区县' ? "" : $("#county option:selected").val();
			var industry = $("#industry option:selected").val() == '行业' ? "" : $("#industry option:selected").val();
			var countyName = $("#county option:selected").text();
			var industryName = $("#industry option:selected").text();
			
			if("" == county){
				layer.msg("区县不能为空");
				return;
			}
			
			if("" == industry){
				layer.msg("行业不能为空");
				return;
			}
			
			//正在加载中
			ShowLoading();
			
    	    //获取的参数
    	    var formdata = {
    	    	county: county,
    	    	industry: industry
    	    }
    		
    	    //访问请求
    	    $.ajax({
    	        url: contextPath + '/industryTypeController/queryRoundDotCount',
    	        type: "post",
    	        data: formdata,
    	        success: function (result) {
    				//清除地图覆盖物
    				map.clearOverlays();
    				getBoundary(countyName);
    				
     	        	if("" != result){
     	        		var resultData = result.split("::");
     	        		
     	        		//设置文本显示数量
     	        		var num = industryName + ": " + resultData[1];
     	        		var opts = {
							position : point,    // 指定文本标注所在的地理位置
							offset : new BMap.Size(30, -30)    //设置文本偏移量
						}
						var label = new BMap.Label(num, opts);  // 创建文本标注对象
						label.setStyle({
						 	color : "#333",
						 	fontSize : "12px",
						 	height : "30px",
						 	lineHeight : "30px",
						 	fontFamily: "微软雅黑",
						 	padding: "0px 10px"
					 	});
						map.addOverlay(label);  
						
						//设置圆点数据
				        var data = JSON.parse(resultData[0]);
				        var dataSet = new mapv.DataSet(data);

				        var options = {
				            fillStyle: 'rgba(255, 50, 50, 0.6)',
				            shadowColor: 'rgba(255, 50, 50, 1)',
				            shadowBlur: 30,
				            globalCompositeOperation: 'lighter',
				            methods: {
				                click: function (item) {
				                    //console.log(item);
				                }
				            },
				            size: 5,
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
		
        // mapvLayer.show(); // 显示图层
        // mapvLayer.hide(); // 删除图层
      	// dataSet.set(data);// 修改数据
    </script>
</body>
</html>