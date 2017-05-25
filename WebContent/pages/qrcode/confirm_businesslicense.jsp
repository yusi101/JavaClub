﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html> 
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>营业执照打印</title>
	<%@ include file="../system/allresources.jsp"%>
	<script language="javascript" src="${pageContext.request.contextPath}/static/plugins/lodop/LodopFuncs.js"></script>
	<object id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0" height="0"> 
		<param name="Border" value="0">
    	<embed id="LODOP_EM" type="application/x-print-lodop" width="0" height="0" pluginspage="${pageContext.request.contextPath}/static/plugins/lodop/install_lodop.exe"></embed>
	</object> 
	<style type="text/css" id="style">
		body{
		  margin:0px;
		  padding:0px;
		}
		#QRBase64Code img {
			width:350px;
			height:350px;
		}
	</style>
</head>

<body style="zoom:1;font-family:'adobe-clean',sans-serif">
	<div style="width: 100%; height: auto; text-align: center; margin-top: 20px;">
		<input type="hidden" id="pripid" value="${pd.pripid }">
		<input type="hidden" id="entname" value="${pd.entname }">
		<div id="QRBase64Code" style="margin-top:20px;margin-right:20px;">
			<img src="data:image/jpg;base64,${QRBase64Code }">
		</div>
		<div style="margin: 20px auto;" id="btn">
			<button class="btn btn-confirm " onclick="javascript:CreatePrintPage1();">营业执照正本【A3】打印</button>
			<button class="btn btn-confirm " onclick="javascript:CreatePrintPage2();">营业执照副本【A4】打印</button>
			<button class="btn btn-cancel" style="border:1px solid #ccc;" onclick="parent.layer.closeAll('iframe')">关闭</button>
		</div>		
	</div>
	<script type="text/javascript">
		var LODOP; //声明为全局变量       
		function CreatePrintPage1() {//A3 纸打印
			$("#btn").hide();
			LODOP=getLodop(document.getElementById('LODOP'),document.getElementById('LODOP_EM'));
	   		LODOP.PRINT_INIT("");
	   	 	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A3"); //规定纸张大小
	   	 	//LODOP.SET_PREVIEW_WINDOW(0, 1, 1,540,760, "营业执照打印预览.开始打印");//设置预览窗口
	   	 	LODOP.SET_SHOW_MODE("HIDE_PAPER_BOARD",1);//隐藏线条
			LODOP.SET_PRINT_MODE("POS_BASEON_PAPER","1");//设置输出位置以纸张边缘为基点
			LODOP.SET_PRINT_MODE("AUTO_CLOSE_PREWINDOW","1");//设置打印完毕是否自动关闭预览窗口
			LODOP.ADD_PRINT_TEXT("92mm","229.5mm","35mm","35mm","企业信用信息二维码");
			LODOP.SET_PRINT_STYLEA(0,"Bold",1);
			LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
			LODOP.ADD_PRINT_IMAGE("62mm","232mm","30mm","30mm","data:image/jpg;base64,${QRBase64Code }");
			//第一个属性越大距离顶部越大，第二次属性越小距离右边越大
			LODOP.SET_PRINT_STYLEA(0,"Stretch",1);//(可变形)扩展缩放模式
			//打印关闭后执行的动作
			if (LODOP.CVERSION) {
				CLODOP.On_Return=function(TaskID,Value){
					$("#btn").show();
					if(Value > 0){
						//添加营业执照打印数据
						$.ajax({
					        url: "${pageContext.request.contextPath}/qrCodePrintController/insertBusinessLicense",
					        type: "POST",
					        data: {
						    	pripid: $("#pripid").val(),
						        entname: $("#entname").val()
						    },
					        success: function (result) {}
					    })
					}
				};
			}else{
				$("#btn").show();
			}
			
			LODOP.PREVIEW();
		};
		function CreatePrintPage2() {//A4 纸打印
			$("#btn").hide();
			LODOP=getLodop(document.getElementById('LODOP'),document.getElementById('LODOP_EM'));
	   		LODOP.PRINT_INIT("");
	   	 	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A3"); //规定纸张大小
	   	 	//LODOP.SET_PREVIEW_WINDOW(0, 1, 1,540,760, "营业执照打印预览.开始打印");//设置预览窗口
	   	 	LODOP.SET_SHOW_MODE("HIDE_PAPER_BOARD",1);//隐藏线条
			LODOP.SET_PRINT_MODE("POS_BASEON_PAPER","1");//设置输出位置以纸张边缘为基点
			LODOP.SET_PRINT_MODE("AUTO_CLOSE_PREWINDOW","1");//设置打印完毕是否自动关闭预览窗口
			LODOP.ADD_PRINT_TEXT("60.5mm","198mm","35mm","35mm","企业信用信息二维码");
			LODOP.SET_PRINT_STYLEA(0,"Bold",1);
			LODOP.SET_PRINT_STYLEA(0,"Alignment",2);
			LODOP.ADD_PRINT_IMAGE("25.5mm","198mm","35mm","35mm","data:image/jpg;base64,${QRBase64Code }");
			//第一个属性越大距离顶部越大，第二次属性越小距离右边越大
			LODOP.SET_PRINT_STYLEA(0,"Stretch",1);//(可变形)扩展缩放模式
			//打印关闭后执行的动作
			if (LODOP.CVERSION) {
				CLODOP.On_Return=function(TaskID,Value){
					$("#btn").show();
					if(Value > 0){
						//添加营业执照打印数据
						$.ajax({
					        url: "${pageContext.request.contextPath}/qrCodePrintController/insertBusinessLicense",
					        type: "POST",
					        data: {
						    	pripid: $("#pripid").val(),
						        entname: $("#entname").val()
						    },
					        success: function (result) {}
					    })
					}
				};
			}else{
				$("#btn").show();
			}
			
			LODOP.PREVIEW();
		};
	</script>
</body>
</html>