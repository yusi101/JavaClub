<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html> 
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>牌照正常审核</title>
	<%@ include file="pages/system/allresources.jsp"%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/styles/mycss/printShou.css" type="text/css" media="print"/>
	<script language="javascript" src="${pageContext.request.contextPath}/static/plugins/lodop/LodopFuncs.js"></script>
</head>

<body>
	<div style="width: 100%; height: auto; text-align: center; margin-top: 20px;">
		<iframe id="QRBase64Code" style="page-break-before: always;border:0px;min-height: 500px;width:803px;" clear="all"  src="${pageContext.request.contextPath}/index4.jsp" scrolling="no"></iframe>
		<div style="margin: 20px auto;">
			<button class="btn btn-confirm " id="print">打印</button>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<button class="btn btn-cancel" onclick="parent.layer.closeAll('iframe')">关闭</button>		
		</div>		
	</div>
	<script type="text/javascript">
		$(function(){
            $("#print").click(function(){
	            $("#QRBase64Code").jqprint({
	          	  debug: false,//如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false  
	                importCSS: true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）  
	                printContainer: true,//表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。  
	                operaSupport: true//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
	            }); 
            });  
        });  
	</script>
</body>
</html>