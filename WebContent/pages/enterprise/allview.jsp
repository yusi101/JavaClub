<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="modo" uri="/WEB-INF/custom-tld/urltag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>全景视图</title>
	<%@ include file="../system/allresources.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/plugins/d3/d3.js"></script>
	<style>
		html,body{
			height: 100%;
		}
		.node circle {
			stroke: steelblue;
			stroke-width: 1px;
			cursor: pointer;
		}
		.node {
			font: 12px sans-serif;
			display: block;
		}
		.hidenode {
			font: 12px sans-serif;
			display: none;
		}
		.link {
			fill: none;
		 	stroke: #ccc; 
			stroke-width: 1px;
		}
		.show_content {
			width: 260px;
		    height: auto;
			background: #FFF;
			box-sizing: border-box;
		    position: absolute;
		    top: 20px;
		    right: 20px;
		    border: 1px solid #f8f8f8;
		    box-shadow: 0 3px 15px 1px rgba(0,0,0,.2);
		    border-radius: 3px;
		    display: none;
		    overflow: hidden!important;
		}
		.show_content table {
			width: 100%;
			padding: 10px 10px;
			font-size: 12px;
			border-collapse: inherit;
			border-spacing: inherit;
		}
		table tr td {
			height: 25px;
		}
		.ok {
			padding: 3px 10px;
			text-align: center;
			background: #65CEA7;
			color: #fff;
			border: none;
			font-size: 12px;
			margin-top: 3px;
		}
		.msg {
			width: 160px;
			height: 80px;
		    position: absolute;
		    margin-left: 15px;
		    font-size: 14px;
		    margin-top: 10px;
		}
		.back {
			position: absolute;
		    width: 130px;
		    height: 30px;
		    line-height: 30px;
		    bottom: 0px;
		    right: 10px;
		}
		.back a {
			font-size: 14px;
		}
	</style>
</head>

<body>
	<div class="msg">点击对外投资下的节点可以查看该企业全景视图</div>
	<div class="back">
		<a href="javascript:backNum('up')">返回上页</a>&nbsp;&nbsp;
		<a href="javascript:backNum('index')">返回首页</a>
	</div>
	<div id="mainContent" style="background-color:#fff !important; height:100%; width:100%;">
    	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
	    <div class="show_content" id="content"></div>
	</div>
	<script type="text/javascript">
		//正在加载中
		ShowLoading();
	
		//获取参数
		var pripid = "${param.pripid }";
		var priptype = "${param.priptype }";
		var regno = "${param.regno }";
		var entname =" ${param.entname }";
		var provinceCode = "${param.provinceCode }";
	
		//图像区域大小
		var R = 620;			
		var width = $(document).width();
   		var height = $(document).height();
		var index = 0;		//节点编号
		
		var tree;
	    var container;
	    var linkContainer;
	    var zoom;
	    var diagonal;
	    
	    //请求地址数组，包含每次请求的地址
	    var urlArr = new Array();
		
	    $(document).ready(function () {
	        getData();
	    });

	    //获取数据
        function getData(){
			var url = contextPath + "/overallView/overallView.do?" + "&pripid=" + pripid + "&regno=" + regno 
				+ "&entname=" + entname + "&provinceCode=" + provinceCode
			urlArr.push(url);
			
        	//访问请求
        	$.ajax({
        		url: contextPath + "/overallView/overallView.do",
        		type: "post",
        		data: {"pripid": pripid, "regno": regno, "entname": entname,"provinceCode": provinceCode},
        		success: function (result) {
        			//获取返回值处理业务
        			if("" != result){
        				drawTree(result);
        			}
        		}
        	})
        }

        //初始化树
        function drawTree(data){
    		//定义一个Tree对象,定义旋转角度和最大半径
    		tree = d3.layout.cluster()
    			.size([360, R / 2 - 120])
    			.separation(function(a, b) {
    				return(a.parent == b.parent ? 1 : 2) / a.depth;
    			});

    		//定义布局方向
    		diagonal = d3.svg.diagonal()
    			.projection(function(d) {
    				var r = d.y,
    					a = (d.x - 90) / 180 * Math.PI;
    				return [r * Math.cos(a), r * Math.sin(a)];
    			});

    		//绑定缩放
    		zoom = d3.behavior.zoom()
                   .scaleExtent([0.5, 3.2])
                   .on("zoom", zoomed);
	
    		//新建画布，移动到圆心位置
    		svg = d3.select("#mainContent").append("svg")
    			.attr("width", width)
    			.attr("height", height);
    		
    		container = svg.append("g")
    			.attr("transform", function(d) {
    				return "translate(" + width / 2 + "," + R / 2 + ")";
    			});
	
           	//调用放大移动
           	svg.call(zoom);
  		
           	//初始化位置
    		initLocation();
	
    		//缩放功能
            function zoomed() {
            	container.attr("transform",
    				"translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
            }

            //初始化位置
            function initLocation(){
                zoom.translate([width / 2, height / 2]);
                zoom.scale(0.8);
                container.attr("transform", "translate(" + (width / 2) + "," + (height / 2) + ")scale(" + zoom.scale() + ")");
           	}

            showTree(data);
        }       

		//根据JSON数据生成树
		function showTree (data) {
			var root = data;
			//根据数据生成nodes集合
			var nodes = tree.nodes(JSON.parse(data));
			//获取node集合的关系集合
			var links = tree.links(nodes);

			//计算节点间的间距，凤凰展翅
			nodes.forEach(function(d) {
		     	if(d.depth > 2){
		        	d.y = d.depth * (d.depth / 2) * 150;
		        } else {
		        	d.y = d.depth * 150;
		       	}
		    });

			//根据node集合生成节点,添加id是为了区分是否冗余的节点
			var node = container.selectAll(".node")
				.data(nodes, function(d) {
					return d.id || (d.id = ++index);
				});

			//为关系集合设置贝塞尔曲线连接
			var link = container.selectAll(".link")
				.data(links, function(d) {
					return d.target.id;
				})
				.enter()
				.append("path")
				.attr("class", "link")
				.attr("d", diagonal);
			node.enter()
				.append("g")
				.attr("class", "node")
				.attr("transform", function(d) {
					if(d.depth == 0){
						return "translate(" + project(0, 0) + ")"; 
					}
					return "rotate(" + (d.x - 90) + ")translate(" + d.y + ")";
				}); 

			//为节点添加圆形标记,设置节点颜色
			node.append("circle")
				.attr("fill", function(d) {
					return d.fill;
				})
				.attr("r", 6)
				.on("click", nodeClick);

			//为节点添加说明文字
			node.append("text")
 				.attr("dy", function(d){
					if(d.depth == 0){
						return "-1.5em";	
					}
					return ".4em";
				})
				.attr("x", function(d){
	                if(d.depth == 0){
	                    return d.name.length * 8
	                }
				}) 
				.attr("fill", function(d){
					if(d.depth > 1){
						return "#222";
					}
					return d.fill;
				})
				.text(function(d) {
					return d.name;
				})
               	.style("text-anchor", function(d) {
                    if(d.depth==0){
                        return "end";
                    }
                    return d.x < 180 ? "start" : "end";
                })
              	.attr("transform", function(d) {
	                if(d.depth > 0){
	                	return d.x < 180 ? "translate(8)" : "rotate(180)translate(-8)";
	                }else{
	                    return "rotate(0)";
	                }
              	})
				.on("click", textClick);

			//点击显示节点名称
			function textClick(d){
				if(d.depth > 1){
		 			$("#content").empty();
		 			$("#content").show();
					$("#content").append(d.content);
				}
			}
			
			//点击带外投资节点进入该企业的全景
			function nodeClick(d) {
				//判断是否有pripid属性，如果没有就提示只有对外投资节点才能查看该企业的全景视图
				if(!d.pripid){
					layer.msg('只有对外投资下的节点才能查看该企业的全景视图');
					return;
				}
				
				//判断区域编号是否为空或则是否为江西的，可以根据区域编号切换数据库连接，只做了河南和江西的判断，因为业务逻辑无法处理所以暂时不连接河南库，所以只限制江西
				if("" == d.provinceCode || "36" != d.provinceCode){
					layer.msg('该企业不能进入全景视图');
					return;
				}
				
				var url = contextPath + "/overallView/overallView.do?" + "&pripid=" + d.pripid + "&regno=" + d.regno 
					+ "&entname=" + d.name + "&provinceCode=" + d.provinceCode
				urlArr.push(url);
				
	        	//访问请求
	        	$.ajax({
	        		url: contextPath + "/overallView/overallView.do",
	        		type: "post",
	        		data: {"pripid": d.pripid, "regno": d.regno, "entname": d.name,"provinceCode": d.provinceCode},
	        		success: function (result) {
	        			//获取返回值处理业务
	        			if("" != result){
	        				svg.select("g").selectAll("path").remove()
	        				svg.select("g").selectAll("g").remove()
	        				showTree(result);
	        			}
	        		}
	        	})
			}

			//计算节点的位置
	        function project(x, y) {
	            var angle = (x - 90) / 180 * Math.PI, radius = y;
	            return [radius * Math.cos(angle), radius * Math.sin(angle)];
	        }

		    //删除正在加载中
		    HideLoading();
		}

		function backNum(flag) {
			var url = "";
		    var urlIndex = urlArr.length - 2;	//请求地址的位置
			
			if("index" == flag) {
				//if(0 == urlArr.length){
				if(0 > urlIndex){
					layer.msg("这已经是最早的全景视图了");
					return;
				}
				url = urlArr[0];
			} else {
				if(0 > urlIndex){
					layer.msg("这已经是最早的全景视图了");
					return;
				}
				
				url = urlArr[urlIndex];
				if(1 < urlArr.length){
					urlArr.pop();
				}
			}
			
        	//访问请求
        	$.ajax({
        		url: url,
        		type: "post",
        		data: {},
        		success: function (result) {
        			//获取返回值处理业务
        			if("" != result){
        				svg.select("g").selectAll("path").remove()
        				svg.select("g").selectAll("g").remove()
        				showTree(result);
        			}
        		}
        	})
		}
		
	    //确定关闭显示内容
	   	function ok(obj){
	   		$("#content").hide();
	   		$("#"+obj).remove();
	    };
	</script>
</body>
</html>