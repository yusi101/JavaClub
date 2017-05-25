<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>后台首页</title>
	<%@ include file="../system/allresources.jsp"%>
	<style type="text/css">
		.qrcode{
			width: 95%;
			border-radius: 5px;
			margin: 20px auto;
			position: relative;
			padding: 5px;
			color: #999;
		}
		.qrcode_one{
			width:100%; 
			height: 230px;
		}
		.qrcode_two{
			width:100%; 
			height: 290px;
		}
		.qrcode_three{
			width: 100%; 
			height: 120px; 
			margin-top: 10px; 
			text-align: center;
		}
		.qrcode_one .qrcode_big{
			width: 40%; 
			height: 230px; 
			/*border: 1px solid #CCC;*/
			float: left; 
			text-align: center; 
			line-height: 200px;
			background: #FFF;
			border-radius: 5px;
		}
		.qrcode_two .qrcode_big{
			width: 40%; 
			height: 290px; 
			float: left; 
			line-height: 60px;
			background: #FFF;
			border-radius: 5px;
		}
		.qrcode_two .qrcode_count_top,.qrcode_two .qrcode_count_bottom{
		    height: 140px;
		    text-align: left;
		}
		.qrcode_two .qrcode_count_top h3,.qrcode_two .qrcode_count_bottom h3{
		    line-height: 30px !important;
		}
		.qrcode_big_desc{
			padding: 0px 0px 0px 10px; 
			height: 30px; 
			line-height: 30px; 
			text-align: left;
		}
		.qrcode_one .qrcode_count{
			width: 60%; 
			height: auto; 
			float: right; 
			text-align: center;
		}
		.qrcode_two .qrcode_count{
			width: 60%; 
			height: auto; 
			float: right; 
			text-align: center;
		}
		.qrcode_count_top{
			width: 31%; 
			height: 110px; 
			margin-top: 0px; 
			margin-left: 1.8%; 
			/* border: 1px solid #ccc; */ 
			float: left;
			border-radius: 5px;
			background: #FFF;
		}
		.qrcode_count_top h3{
			color: #EA3F3F;
		}
		.qrcode_count_bottom{
			width: 31%; 
			height: 110px; 
			margin-top: 10px; 
			margin-left: 1.8%;
			/* border: 1px solid #ccc; */ 
			float: left;
			border-radius: 5px;
			background: #FFF;
		}
		.qrcode_count_bottom h3{
			color: #65C3DF;
		}
		.qrcode_three_left{
			width: 49%; 
			height: 120px; 
			line-height: 120px;
			/* border: 1px solid #CCC;*/
			float: left;
			border-radius: 5px;
			background: #FFF;
		}
		.qrcode_three_right{
			width: 49%; 
			height: 120px; 
			line-height: 120px; 
			float: left; 
			margin-left: 1%;
			border-radius: 5px;
			background: #FFF;
		}
		.qrcode_three_left h2,.qrcode_three_right h2,.qrcode_big h1{
			display: inline-block;
		}
		.qrcode_three_right h2{
			color: #45B6B0;
		}
		.qrcode_big h1{
			color: #FF6B6B;
		}
		.qrcode_three_left h2{
			color: #FFA200;
		}
		
		h3{
			display: -webkit-inline-box;
		}
		h1, h2, h3, h4, h5, h6{
			margin: 10px;
			color: #6B8090;
		}
		
	</style>
		<SCRIPT language=javascript> 
<!-- 
window.onerror=function(){return true;} 
// --> 
</SCRIPT> 
</head>

<body style="background: rgb(245,245,245);">
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/plugins/echarts/echarts.min.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath }/static/scripts/homepage/gauge.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/scripts/homepage/qrcode_aweek_line.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/static/scripts/homepage/qrcode_industry_bar.js"></script>
	<div class="qrcode">
		<div class="qrcode_one">
			<div class="qrcode_big" id="apply_count">
				<h1>0</h1>&nbsp;<span>（次）</span>
				<div class="qrcode_big_desc">牌照申请总量</div>
			</div>
			<div class="qrcode_count">
				<div class="qrcode_count_top" id="apply_online">
					<h3>0</h3>（次）
					<h6>在线申请</h6>
				</div>
				<div class="qrcode_count_top" style="width:20%;" id="apply_online_pass">
					<h3>0</h3>（次）
					<h6>审核通过</h6>
				</div>
				<div class="qrcode_count_top" style="width:20%;" id="apply_online_nopass">
					<h3>0</h3>（次）
					<h6>未审核通过</h6>
				</div>
				<div class="qrcode_count_top" style="width:20%;" id="apply_online_nohandled">
					<h3>0</h3>（次）
					<h6>未处理</h6>
				</div>
				<div class="qrcode_count_bottom" id="apply_counter">
					<h3>0</h3>（次）
					<h6>柜台申请</h6>
				</div>
				<div class="qrcode_count_bottom" id="apply_counter_input">
					<h3>0</h3>（次）
					<h6>在线录入</h6>				
				</div>
				<div class="qrcode_count_bottom" id="apply_counter_import">
					<h3>0</h3>（次）
					<h6>批量导入</h6>				
				</div>
			</div>
		</div>
		<div class="qrcode_two" style="margin: 10px 0;">
			<div class="qrcode_big" id="qrcode_count">
				<h1 style="margin-top: 50px;">0</h1>&nbsp;<span>（次申请）</span><br>
				<h1>0</h1>&nbsp;<span>（块牌照）</span>
				<div class="qrcode_big_desc">共有<span>0</span>（家/次）企业申请牌照，总共制作牌照<span>0</span>（块），</div>
			</div>
			<div class="qrcode_count" >
				<div class="qrcode_count_top" id="qrcode_noauditing">
					<h3>0</h3>（次申请）<br>
					<h6>牌照未审核</h6>
				</div>
				<div class="qrcode_count_top" id="qrcode_noproduced">
					<h3>0</h3>（次申请）<br>
					<h3>0</h3>（块牌照）
					<h6>未制作的牌照数量</h6>
				</div>
				<div class="qrcode_count_top" id="qrcode_inproduced">
					<h3>0</h3>（次申请）<br>
					<h3>0</h3>（块牌照）
					<h6>制作中的牌照数量</h6>
				</div>
				<div class="qrcode_count_bottom" id="qrcode_alreadystorage">
					<h3>0</h3>（次申请）<br>
					<h3>0</h3>（块牌照）
					<h6>库存的牌照数量</h6>				
				</div>
				<div class="qrcode_count_bottom" id="qrcode_nonotice">
					<h3>0</h3>（次申请）<br>
					<h6>未通知的牌照数量</h6>
				</div>
				<div class="qrcode_count_bottom" id="qrcode_alreadynotice">
					<h3>0</h3>（次申请）<br>
					<h6>已通知的牌照数量</h6>				
				</div>
			</div>
		</div>
		<div class="qrcode_three">
			<div class="qrcode_three_left" id="qrcode_noreceive">
				未领取&nbsp;<h2>0</h2>&nbsp;个牌照
			</div>
			<div class="qrcode_three_right" id="qrcode_alreadyreceive">
				已领取了&nbsp;<h2>0</h2>&nbsp;个牌照
			</div>
		</div>
	</div>
	<!-- <div id="gauge_echarts" style="height:360px; width: 46%; float:left; margin: 20px 0px 0px 2.5%; border-radius: 5px; background-color: #FFF;"></div>
	<div id="qrcode_aweek_line" style="height:360px; width: 46%; float: left; margin: 20px 0px 0px 2.5%; border-radius: 5px; background: #FFF;"></div> -->
	<div id="qrcode_industry_bar" style="height:500px; width: 93%; float:right; margin: 20px 4%; border-radius: 5px; background: #FFF;"></div>
	<script type="text/javascript">
		$(document).ready(function() { 
			/* getgauge();
			getqrcode_aweek_line(); */
			queryApplyTypeAndStatusCount();
		});
		$(function(){ 
			setInterval("queryApplyTypeAndStatusCount()",30000);//1000为1秒钟
		});
		function queryApplyTypeAndStatusCount(){
			$.ajax({
				type :'post',
				url : contextPath+'/homePageController/queryApplyTypeAndStatusCount',
				data : {},
				dataType: "json",
				success : function(result){
					var applyTypeAndStatusCount=result[0];
					var qrcodeStatusCount=result[1];
					$("#apply_online h3").html("0");
					$("#apply_count h1").html("0");
					$("#apply_counter h3").html("0");
					for (var i = 0; i < applyTypeAndStatusCount.length; i++) {
						if(applyTypeAndStatusCount[i].APPLYWAY_CN=="在线申请"){
							if(applyTypeAndStatusCount[i].STATUS=="未处理"){
								$("#apply_online h3").html(parseInt(applyTypeAndStatusCount[i].COUNT)+parseInt($("#apply_online h3").html()));
								$("#apply_online_nohandled h3").html(applyTypeAndStatusCount[i].COUNT);
							}
							if(applyTypeAndStatusCount[i].STATUS=="审核通过"){
								$("#apply_online h3").html(parseInt(applyTypeAndStatusCount[i].COUNT)+parseInt($("#apply_online h3").html()));
								$("#apply_online_pass h3").html(applyTypeAndStatusCount[i].COUNT);
							}
							if(applyTypeAndStatusCount[i].STATUS=="审核不通过"){
								$("#apply_online h3").html(parseInt(applyTypeAndStatusCount[i].COUNT)+parseInt($("#apply_online h3").html()));
								$("#apply_online_nopass h3").html(applyTypeAndStatusCount[i].COUNT);
							}
						}else if(applyTypeAndStatusCount[i].APPLYWAY_CN=="柜台申请"){
							$("#apply_counter h3").html(parseInt(applyTypeAndStatusCount[i].COUNT)+parseInt($("#apply_counter h3").html()));
							$("#apply_counter_input h3").html(parseInt(applyTypeAndStatusCount[i].COUNT));
						}else if(applyTypeAndStatusCount[i].APPLYWAY_CN=="批量导入"){
							$("#apply_counter h3").html(parseInt(applyTypeAndStatusCount[i].COUNT)+parseInt($("#apply_counter h3").html()));
							$("#apply_counter_import h3").html(parseInt(applyTypeAndStatusCount[i].COUNT));
						}
						$("#apply_count h1").html(parseInt(applyTypeAndStatusCount[i].COUNT)+parseInt($("#apply_count h1").html()));
						
					}
					//qrcode_count h1
					for (var i = 0; i < qrcodeStatusCount.length; i++) {
						if(qrcodeStatusCount[i].STATUS=="0"){
							$("#qrcode_noauditing h3").html(qrcodeStatusCount[i].COUNT);
						}else if(qrcodeStatusCount[i].STATUS=="1"){
							$("#qrcode_noproduced h3").eq(0).html(qrcodeStatusCount[i].COUNT);
							$("#qrcode_noproduced h3").eq(1).html(qrcodeStatusCount[i].SUM);
						}else if(qrcodeStatusCount[i].STATUS=="3"){
							$("#qrcode_inproduced h3").eq(0).html(qrcodeStatusCount[i].COUNT);
							$("#qrcode_inproduced h3").eq(1).html(qrcodeStatusCount[i].SUM);
						}else if(qrcodeStatusCount[i].STATUS=="4"){
							$("#qrcode_nonotice h3").html(qrcodeStatusCount[i].COUNT);
							
							$("#qrcode_count h1").eq(0).html(parseInt($("#qrcode_count h1").eq(0).html())+parseInt(qrcodeStatusCount[i].COUNT));
							$("#qrcode_count h1").eq(1).html(parseInt($("#qrcode_count h1").eq(1).html())+parseInt(qrcodeStatusCount[i].SUM));
							$("#qrcode_count .qrcode_big_desc span").eq(0).html(parseInt($("#qrcode_count .qrcode_big_desc span").eq(0).html())+parseInt(qrcodeStatusCount[i].COUNT));
							$("#qrcode_count .qrcode_big_desc span").eq(1).html(parseInt($("#qrcode_count .qrcode_big_desc span").eq(1).html())+parseInt(qrcodeStatusCount[i].SUM));
							
							
							
							$("#qrcode_noreceive h2").html(parseInt($("#qrcode_noreceive h2").html())+parseInt(qrcodeStatusCount[i].COUNT));
							
							$("#qrcode_alreadystorage h3").eq(0).html(parseInt($("#qrcode_alreadystorage h3").eq(0).html())+parseInt(qrcodeStatusCount[i].COUNT));
							$("#qrcode_alreadystorage h3").eq(1).html(parseInt($("#qrcode_alreadystorage h3").eq(1).html())+parseInt(qrcodeStatusCount[i].SUM));
						}else if(qrcodeStatusCount[i].STATUS=="6"){
							//状态未6也是还在库存中
							$("#qrcode_alreadynotice h3").html(qrcodeStatusCount[i].COUNT);
							
							$("#qrcode_count h1").eq(0).html(parseInt($("#qrcode_count h1").eq(0).html())+parseInt(qrcodeStatusCount[i].COUNT));
							$("#qrcode_count h1").eq(1).html(parseInt($("#qrcode_count h1").eq(1).html())+parseInt(qrcodeStatusCount[i].SUM));
							$("#qrcode_count .qrcode_big_desc span").eq(0).html(parseInt($("#qrcode_count .qrcode_big_desc span").eq(0).html())+parseInt(qrcodeStatusCount[i].COUNT));
							$("#qrcode_count .qrcode_big_desc span").eq(1).html(parseInt($("#qrcode_count .qrcode_big_desc span").eq(1).html())+parseInt(qrcodeStatusCount[i].SUM));
							
							$("#qrcode_noreceive h2").html(parseInt($("#qrcode_noreceive h2").html())+parseInt(qrcodeStatusCount[i].COUNT));
							
							$("#qrcode_alreadystorage h3").eq(0).html(parseInt($("#qrcode_alreadystorage h3").eq(0).html())+parseInt(qrcodeStatusCount[i].COUNT));
							$("#qrcode_alreadystorage h3").eq(1).html(parseInt($("#qrcode_alreadystorage h3").eq(1).html())+parseInt(qrcodeStatusCount[i].SUM));
						}else if(qrcodeStatusCount[i].STATUS=="7"){
							$("#qrcode_count h1").eq(0).html(parseInt($("#qrcode_count h1").eq(0).html())+parseInt(qrcodeStatusCount[i].COUNT));
							$("#qrcode_count h1").eq(1).html(parseInt($("#qrcode_count h1").eq(1).html())+parseInt(qrcodeStatusCount[i].SUM));
							$("#qrcode_count .qrcode_big_desc span").eq(0).html(parseInt($("#qrcode_count .qrcode_big_desc span").eq(0).html())+parseInt(qrcodeStatusCount[i].COUNT));
							$("#qrcode_count .qrcode_big_desc span").eq(1).html(parseInt($("#qrcode_count .qrcode_big_desc span").eq(1).html())+parseInt(qrcodeStatusCount[i].SUM));
							
							$("#qrcode_alreadyreceive h2").html(qrcodeStatusCount[i].COUNT);
						}
						
					}
				}
			});
			getqrcode_industry_bar();
		}
	</script>              
</body>
</html>