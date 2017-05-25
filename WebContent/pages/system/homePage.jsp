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
		.qrcode {
			width: 95%;
			border-radius: 5px;
			margin: 20px auto;
			position: relative;
			padding: 5px;
			color: #FFF;
			border-bottom: 1px dashed #CCC;
		}
		.qrcode span {
			display: inline-block;
		}
		.qrcode_one {
			width: 100%; 
			height: 220px;
		}
		.qrcode_two {
			width: 100%; 
			height: 220px;
		}
		.qrcode_three {
			width: 100%; 
			height: 120px;  
			text-align: center;
		}
		.qrcode_one .qrcode_big {
			width: 36%;
		    height: 210px;
		    float: left;
		    text-align: center;
		    background: #FFF;
		    background: rgb(3,110,184);
		}
		.qrcode_big_count{
			width: 100%; 
			height: 130px; 
			font-size: 70px; 
			line-height: 160px;
		}
		.qrcode_two .qrcode_big {
			width: 36%;
		    height: 210px;
		    float: left;
		    text-align: center;
		    background: #FFF;
		    background: rgb(19,174,103);
		}
		.qrcode_count {
			width: 64%; 
			height: auto; 
			float: right; 
			text-align: center;
		}
		.qrcode_count_top {
			width: 100%; 
			height: 50px;
			border-bottom: 1px solid rgb(74,193,241);
		}
		.qrcode_count1 {
			width: 32%; 
			height: 100px; 
			margin-top: 0px; 
			margin-left: 2%; 
			float: left; 
			background: rgb(143,195,31);
		}
		.qrcode_count1 span,.qrcode_count5 span{
			margin-top: 28px;
    		font-size: 38px;
		}
		.qrcode_count2{
			width: 20%; 
			height: 100px; 
			margin-left: 2%; 
			float: left; 
			background: rgb(74,193,241);
		}
		.qrcode_count2 span,.qrcode_count3 span,.qrcode_count4 span{
			font-size: 28px;
			margin-top: 30px;
		}
		.qrcode_count3{
			width: 20%; 
			height: 100px; 
			margin-left: 2%; 
			float: left; 
			background: rgb(0,160,233);
		}
		.qrcode_count4{
			width: 20%; 
			height: 100px; 
			margin-left: 2%; 
			float: left; 
			background: rgb(0,143,206);
		}
		.qrcode_count_bottom{
			width: 100%; 
			height: 110px;
			border-bottom: 1px solid red; 
		}
		.qrcode_count5{
			width: 32%; 
			height: 100px; 
			margin-top: 10px; 
			margin-left: 2%; 
			float: left; 
			background: rgb(0,162,154);
		}
		.qrcode_count6 {
			width: 31%; 
			height: 100px; 
			margin-left: 2%; 
			margin-top: 10px; 
			float: left; 
			background: rgb(23,139,132);
		}
		.qrcode_count6 span,.qrcode_count7 span{
			font-size: 28px;
			margin-top: 30px;
		}
		.qrcode_count7{
			width: 31%; 
			height: 100px; 
			margin-left: 2%; 
			margin-top: 10px; 
			float: left; 
			background: rgb(0,113,108);
		}
		.qrcode_count_top2{
			width: 100%; 
			height: 50px;
			border-bottom: 1px solid green; 
		}
		.qrcode_count8{
			width: 31%; 
			height: 100px; 
			margin-left: 2%; 
			float: left;  
			background: rgb(201,160,99);
		}
		.qrcode_count9{
			width: 31%; 
			height: 100px; 
			margin-left: 2%; 
			float: left; 
			background: rgb(178,130,71);
		}
		.qrcode_count8 span,.qrcode_count9 span,.qrcode_count11 span,.qrcode_count12 span{
			font-size: 28px;
			margin-top: 25px;
		}
		.qrcode_count10{
			width: 32%; 
			height: 100px; 
			margin-left: 2%; 
			float: left; 
			background: rgb(149,97,52);
		}
		.qrcode_count10 span,.qrcode_count13 span{
			font-size: 35px;
			margin-top: 28px;
		}
		.qrcode_count_bottom2{
			width: 100%; 
			height: 110px;
			border-bottom: 1px solid rgb(235,92,1); 
		}
		.qrcode_count11{
			width: 31%; 
			height: 100px; 
			margin-left: 2%; 
			margin-top: 10px; 
			float: left; 
			background: rgb(243,150,45);
		}
		.qrcode_count12{
			width: 31%; 
			height: 100px; 
			margin-left: 2%;  
			margin-top: 10px; 
			float: left; 
			background: rgb(238,120,21);
		}
		.qrcode_count13{
			width: 32%; 
			height: 100px; 
			margin-top: 10px; 
			margin-left: 2%; 
			float: left; 
			background: rgb(235,92,1);
		}
		.qrcode_three_left{
			width: 49%; 
			height: 100px; 
			line-height: 100px;
			float: left;
			background: rgb(3,110,184);
		}
		.qrcode_three_right{
			width: 49%; 
			height: 100px; 
			line-height: 100px; 
			float: left; 
			margin-left: 2%;
			background: rgb(3,110,184);
		}
		.qrcode_three_left span,.qrcode_three_right span{
			font-size: 52px;
			color: yellow;
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
				<div class="qrcode_big_count" id="bigcount1">0</div>
				<div style="font-size: 22px;">申请企业总数</div>
			</div>
			<div class="qrcode_count">
				<div class="qrcode_count_top">
					<div class="qrcode_count1" id="apply_online">
						<span>0</span>（次）
						<p>在线申请</p>
					</div>
					<div class="qrcode_count2" id="apply_online_pass">
						<span>0</span>（次）
						<p>审核通过</p>
					</div>
					<div class="qrcode_count3" id="apply_online_nopass">
						<span>0</span>（次）
						<p>未审核通过</p>
					</div>			
					<div class="qrcode_count4" style="" id="apply_online_nohandled">
						<span>0</span>（次）
						<p>未处理</p>
					</div>
				</div>
				<div class="qrcode_count_bottom">
					<div class="qrcode_count5" id="apply_counter">
						<span>0</span>（次）
						<p>柜台申请</p>
					</div>
					<div class="qrcode_count6" id="apply_counter_input">
						<span>0</span>（次）
						<p>在线录入</p>				
					</div>
					<div class="qrcode_count7" id="apply_counter_import">
						<span>0</span>（次）
						<p>批量导入</p>				
					</div>
				</div>
			</div>
		</div>
		<div class="qrcode_two">
			<div class="qrcode_big" id="qrcode_count">
				<div class="qrcode_big_count" style="color: yellow;" id="bigcount2">0</div>
				<div style="font-size: 22px;">申请制作牌照总数</div>
			</div>
			<div class="qrcode_count" >
				<div class="qrcode_count_top2">
					<div class="qrcode_count8" id="qrcode_noauditing">
						<span>0</span>（块）
						<p>未审核的牌照数</p>
					</div>
					<div class="qrcode_count9" id="qrcode_noproduced">
						<span>0</span>（块）
						<p>未制作的牌照数</p>
					</div>
					<div class="qrcode_count10" id="qrcode_inproduced">
						<span>0</span>（块）
						<p>制作中的牌照数</p>
					</div>
				</div>
				<div class="qrcode_count_bottom2">
					<div class="qrcode_count11" id="qrcode_alreadystorage">
						<span>0</span>（块）
						<p>库存的牌照数</p>				
					</div>
					<div class="qrcode_count12" id="qrcode_nonotice">
						<span>0</span>（块）
						<p>未通知的牌照数</p>
					</div>
					<div class="qrcode_count13" id="qrcode_alreadynotice">
						<span>0</span>（块）
						<p>已通知的牌照数</p>				
					</div>
				</div>
			</div>
		</div>
		<div class="qrcode_three">
			<div class="qrcode_three_left" id="qrcode_noreceive">
				未领取&nbsp;<span>0</span>&nbsp;个牌照
			</div>
			<div class="qrcode_three_right" id="qrcode_alreadyreceive">
				已领取了&nbsp;<span>0</span>&nbsp;个牌照
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
					$("#apply_online span").html("0");
					$("#bigcount1").html("0");
					$("#apply_counter span").html("0");
					for (var i = 0; i < applyTypeAndStatusCount.length; i++) {
						if(applyTypeAndStatusCount[i].APPLYWAY_CN=="在线申请"){
							if(applyTypeAndStatusCount[i].STATUS=="未处理"){
								$("#apply_online span").html(parseInt(applyTypeAndStatusCount[i].COUNT)+parseInt($("#apply_online span").html()));
								$("#apply_online_nohandled span").html(applyTypeAndStatusCount[i].COUNT);
							}
							if(applyTypeAndStatusCount[i].STATUS=="审核通过"){
								$("#apply_online span").html(parseInt(applyTypeAndStatusCount[i].COUNT)+parseInt($("#apply_online span").html()));
								$("#apply_online_pass span").html(applyTypeAndStatusCount[i].COUNT);
							}
							if(applyTypeAndStatusCount[i].STATUS=="审核不通过"){
								$("#apply_online span").html(parseInt(applyTypeAndStatusCount[i].COUNT)+parseInt($("#apply_online span").html()));
								$("#apply_online_nopass span").html(applyTypeAndStatusCount[i].COUNT);
							}
						}else if(applyTypeAndStatusCount[i].APPLYWAY_CN=="柜台申请"){
							$("#apply_counter span").html(parseInt(applyTypeAndStatusCount[i].COUNT)+parseInt($("#apply_counter span").html()));
							$("#apply_counter_input span").html(parseInt(applyTypeAndStatusCount[i].COUNT));
						}else if(applyTypeAndStatusCount[i].APPLYWAY_CN=="批量导入"){
							$("#apply_counter span").html(parseInt(applyTypeAndStatusCount[i].COUNT)+parseInt($("#apply_counter span").html()));
							$("#apply_counter_import span").html(parseInt(applyTypeAndStatusCount[i].COUNT));
						}
						$("#bigcount1").html(parseInt(applyTypeAndStatusCount[i].COUNT)+parseInt($("#bigcount1").html()));
						
					}
					//qrcode_count h1
					$("#qrcode_noreceive span").html("0");
					$("#qrcode_alreadystorage span").html("0");
					for (var i = 0; i < qrcodeStatusCount.length; i++) {
						if(qrcodeStatusCount[i].STATUS=="0"){
							$("#qrcode_noauditing span").html(qrcodeStatusCount[i].COUNT);
							$("#apply_online_nohandled span").html(qrcodeStatusCount[i].COUNT);
						}else if(qrcodeStatusCount[i].STATUS=="1"){
							$("#qrcode_noproduced span").html(qrcodeStatusCount[i].SUM);
							
						}else if(qrcodeStatusCount[i].STATUS=="2"){
							$("#apply_online_nopass span").html(qrcodeStatusCount[i].SUM);
						}else if(qrcodeStatusCount[i].STATUS=="3"){
							$("#qrcode_inproduced span").html(qrcodeStatusCount[i].SUM);
						}else if(qrcodeStatusCount[i].STATUS=="4"){
							$("#qrcode_nonotice span").html(qrcodeStatusCount[i].COUNT);
							$("#bigcount2").html(parseInt($("#bigcount1").html())+parseInt(qrcodeStatusCount[i].SUM));
							$("#qrcode_noreceive span").html(parseInt($("#qrcode_noreceive span").html())+parseInt(qrcodeStatusCount[i].COUNT));
							$("#qrcode_alreadystorage span").html(parseInt($("#qrcode_alreadystorage span").html())+parseInt(qrcodeStatusCount[i].SUM));
						}else if(qrcodeStatusCount[i].STATUS=="6"){
							//状态未6也是还在库存中
							$("#qrcode_alreadynotice span").html(qrcodeStatusCount[i].COUNT);
							$("#bigcount2").html(parseInt($("#bigcount1").html())+parseInt(qrcodeStatusCount[i].SUM));
							//$("#qrcode_noreceive span").html(parseInt($("#qrcode_noreceive span").html())+parseInt(qrcodeStatusCount[i].COUNT));
							//$("#qrcode_alreadystorage span").html(parseInt($("#qrcode_alreadystorage span").html())+parseInt(qrcodeStatusCount[i].SUM));
						}else if(qrcodeStatusCount[i].STATUS=="7"){
							$("#bigcount2").html(parseInt($("#bigcount1").html())+parseInt(qrcodeStatusCount[i].SUM));
							$("#qrcode_alreadyreceive span").html(qrcodeStatusCount[i].COUNT);
						}
					}
					$("#apply_online_pass span").html((parseInt($("#bigcount1").html())-parseInt($("#apply_online_nohandled span").html())-parseInt($("#apply_online_nopass span").html()))+"");
				}
			});
			getqrcode_industry_bar();
		}
	</script>              
</body>
</html>