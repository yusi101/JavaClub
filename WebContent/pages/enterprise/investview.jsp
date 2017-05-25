<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="modo" uri="/WEB-INF/custom-tld/urltag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>投资链图</title>
	<%@ include file="../system/allresources.jsp" %>
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
	</style>
</head>

<body>
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
		var entname = "${param.entname }";
		var provinceCode = "${param.provinceCode }";
	
		//图像区域大小
		var R = 620;			
		var width = $(document).width();
   		var height = $(document).height();	
		var duration = 600;		//动画持续时间
		var index = 0;			//节点编号
		var rootData;			//数据源
		var id = 0;
		
		//定义一个Tree对象,定义旋转角度和最大半径
		var tree = d3.layout.cluster()
			.size([360, R / 2 - 120])
			.separation(function(a, b) {
				return(a.parent == b.parent ? 1 : 2) / a.depth;
			});
			
		//定义布局方向
		var diagonal = d3.svg.diagonal()
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
		var svg = d3.select("#mainContent").append("svg")
			.attr("width", width)
			.attr("height", height);
			
		var container = svg.append("g")
			.attr("transform", function(d) {
				return "translate(" + width / 2 + "," + R / 2 + ")";	
			});
		var linkContainer = container.append("g");
      		
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
        
        //隐藏节点
        function draw(root){
       		nodes = tree.nodes(root);
            links = tree.links(nodes);

			//隐藏所有子节点
			nodes.forEach(function(d) {
	            if(d.depth > 0){
	                if(d.children){
	                    d._children = d.children;
	                    d.children = null;
	                }
	            }
	        });

            root.x0 = 0;
            root.y0 = 0;
            drawTree(root);
        }
        
        //添加节点和连线
        function drawTree(data) {
            nodes = tree.nodes(rootData);
            links = tree.links(nodes);

			//计算节点间的间距，凤凰展翅
			nodes.forEach(function(d) {
		     	if(d.depth > 2){
		        	d.y = d.depth * (d.depth / 2) * 120;
		        } else {
		        	d.y = d.depth * 120;
		       	}
		    });
	        
			//声明连线
            var linkUpdate = linkContainer.selectAll(".link")
                    .data(links,function(d){ return d.target.id; });
            var linkEnter = linkUpdate.enter();
            var linkExit = linkUpdate.exit();
            
            linkEnter.append("path")
				.attr("class", "link")
				.attr("d", function(d) {
				    var o = {x: data.x0, y: data.y0};
				    return diagonal({source: o, target: o});
				})
				.transition()
				.duration(duration)
				.attr("d", diagonal);
            
            //修改连线
            linkUpdate.transition()
	            .duration(duration)
	            .attr("d", diagonal);

            //删除连线
            linkExit.transition()
	           .duration(duration)
	           .attr("d", function(d) {
	               var o = {x: data.x, y: data.y};
	               return diagonal({source: o, target: o});
	           })
	           .remove();

            //声明节点
            var nodeUpdate = container.selectAll(".node")
	        	.data(nodes,function(d){ 
	        		return d.id; 
	        	});
	    	var nodeEnter = nodeUpdate.enter();
	   	 	var nodeExit = nodeUpdate.exit();
	   	 	
	   		//添加节点
            var enterNodes = nodeEnter.append("g")
	            .attr("class", "node")
	            .attr("transform", function(d) { 
	            	return "translate(" + project(data.x0, data.y0) + ")"; 
	            });
            enterNodes.append("circle")
			    .attr("r", 0)
			    .attr("fill",function(d){
			    	return d.fill;
			    }) 
			    .attr("stroke-opacity",0.5)
			    .attr("stroke-width", function (d) {
			        if(d.depth==0){
			            return 10;
			        }
			        
			        if(d.depth==1){
			            return 6;
			        }
			        
			        return 0;
			    })
			    .on("click", function (d) {
			        if(d.depth > 0){
			            toggle(d);
			            drawTree(d);
			        }
			    });

            //添加+，-
            enterNodes.append("path")
            .attr("d", function (d) {
                if(d.depth>0 && d._children){
                    return "M-4 -1 H-1 V-4 H1 V-1 H4 V1 H1 V4 H-1 V1 H-4 Z"
                }else if(d.depth>0 && d.children){
                    return "M-4 -1 H4 V1 H-4 Z"
                }
            })
            .attr("fill","#fff")
            .attr("stroke","#fff")
            .attr("stroke-width","0.2")
            .on("click", function (d) {
                if(d.depth > 0){
                    toggle(d);
                    drawTree(d);
                }
            });
            
            //添加文本
            enterNodes.append("text")
	            .attr("dy", function(d){
	                if(d.depth == 0){
	                    return "-1.5em";
	                }
	                return "0.31em";
	            })
               	.attr("x", function(d) {
	                if(d.depth == 0){
	                    return d.name.length * 8
	                }
	                return d.x < 180 ? 15 : -15;
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
              	.style("fill-opacity", 0)
              	.attr("transform", function(d) {
	                if(d.depth > 0){
	                    return "rotate(" + (d.x < 180 ? d.x - 90 : d.x + 90) + ")";
	                }else{
	                    return "rotate(0)";
	                }
              	})
                .style("font-size", function (d) {
                    if(d.depth == 0){
                        return "15px";
                    }
                    return "12px";
                })
               .attr("fill", function (d) {
                   	if(d.depth > 1){
                   		return "#222";
                   	}
                   	return d.fill
               	})
               .on("click", textClick);

            //修改节点
            var updateNodes = nodeUpdate.transition()
	            .duration(duration)
	            .attr("transform", function(d) { return "translate(" + project(d.x, d.y) + ")"; });
            updateNodes.select("circle")
            	.attr("r", function (d) {
	                if(d.depth == 0){
	                    return 9;
	                }
	
	                if(d.depth == 1){
	                    return 8;
	                }
	
	                return 6;
            	});
            
            //修改文本
            updateNodes.select("text")
                    .style("fill-opacity", 1)
                    .attr("transform", function(d) {
                        if(d.depth>0){
                            return "rotate(" + (d.x < 180 ? d.x - 90 : d.x + 90) + ")";
                        }else{
                            return "rotate(0)";
                        }
                    })
                    .attr("x", function(d) {
                        if(d.depth == 0){
                            return d.name.length * 8
                        }
                        return d.x < 180 ? 15 : -15;
                    })
                    .attr("fill", function (d) {
                    	if(d.depth > 1){
                    		return "#222";
                    	}
                    	return d.fill;
                    })
                    .style("text-anchor", function(d) {
                        if(d.depth == 0){
                            return "end";
                        }
                        return d.x < 180 ? "start" : "end";
                    });
            
            //修改+,-
            updateNodes.select("path")
                    .attr("d", function (d) {
                        if(d.depth>0 && d._children){
                            return "M-4 -1 H-1 V-4 H1 V-1 H4 V1 H1 V4 H-1 V1 H-4 Z"
                        }else if(d.depth>0 && d.children){
                            return "M-4 -1 H4 V1 H-4 Z"
                        }
                    });
			
            //删除节点
            var exitNodes = nodeExit.transition()
                    .duration(duration)
                    .attr("transform", function(d) { return "translate(" + project(data.x, data.y) + ")"; })
                    .remove();
            
            //设置节点大小和透明度
            exitNodes.select("circle")
                    .attr("r", 0);
            exitNodes.select("text")
                    .style("fill-opacity", 0);

          	//记录现在的位置
            nodes.forEach(function(d) {
                d.x0 = d.x;
                d.y0 = d.y;
            });

        }
        
        //设置节点ID，用于连线
        function traverseTreeId(node){
            if(!node.id){
                node.id = id;
                id++;
            }

            if(node.children){
                for(var i=0; i<node.children.length; i++){
                    traverseTreeId(node.children[i]);
                }
            }
        }

        //显示隐藏节点
        function toggle(d){
            if (d.children) {
                d._children = d.children;
                d.children = null;
            } else {
                d.children = d._children;
                d._children = null;
            }
        }
        
		//点击显示节点详情
		function textClick(d){
			if(d.depth > 1){
 	 			$("#content").empty();	
 	 			$("#content").show();
 				$("#content").append(d.content);
			}
		} 
        
		//计算节点的位置
        function project(x, y) {
            var angle = (x - 90) / 180 * Math.PI, radius = y;
            return [radius * Math.cos(angle), radius * Math.sin(angle)];
        }

		//根据JSON数据生成树
		d3.json("${pageContext.request.contextPath }/investViewController/investView.do?pripid=" + pripid + "&priptype=" 
				+ priptype + "&regno=" + regno + "&entname=" + entname + "&provinceCode=" + provinceCode, function(error, data) {
			rootData = data;
			
			traverseTreeId(rootData)
			draw(rootData);
			
		    //删除正在加载中
		    HideLoading();
		});
		
	    //确定关闭显示内容
	   	function ok(obj){
	   		$("#content").hide();
	   		$("#"+obj).remove();
	    };
	</script>
</body>
</html>