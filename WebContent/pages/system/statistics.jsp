<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>数据统计</title>
	<%@ include file="../system/allresources.jsp"%>
	<style type="text/css">
	html,body{
		height:100%;
	}
	.content{
	  height:100%;
	  background: url("${pageContext.request.contextPath }/static/images/statistics_bg.jpg") no-repeat center;
	  
	}
	.div_part{
		height: 100%;
	    width: 33%;
	    float: left;
	}
	.div_part .echars_div{
		background: rgba(206, 200, 200, 0.1) !important;
		margin-top:2px;
	}
	
	.div_part p{
	    font-size: 20px;
	    color: #fff;
	    font-weight: bolder;
	    font-family: 微软雅黑;
	    padding:5px 20px;
    	margin: 0px;
	}
	</style>]
		<SCRIPT language=javascript> 
<!-- 
window.onerror=function(){return true;} 
// --> 
</SCRIPT> 
</head>

<body style="height: 100%;">
   <script type="text/javascript" src="${pageContext.request.contextPath }/static/plugins/echarts/echarts.min.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath }/static/scripts/statistics/qrcodeapplycountbyenttype.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath }/static/scripts/statistics/qrcodeapplycountbyenttype2.js"></script>
   <div class="content">
	   <div class="div_part">
	   <!-- 各市场主体的牌照申请状况 -->
	     <div id="qrcodeapplycountbyenttype1" style="height:250px;" class="echars_div"></div>
	     <div id="qrcodeapplycountbyenttype2" style="height:250px;"  class="echars_div"></div>
	     <div id="qrcodeapplycountbyenttype3" style="height:210px;"  class="echars_div">
	     <p>近7个工作日的牌照申请量走势图</p>
	     	<div id="qrcodeapplycountbyenttype2_div" style="height:190px;"></div>
	     </div>
	   </div>
	   <div style="width: 34%;" class="div_part"></div>
	   <div  class="div_part"></div>
   </div>
  
	<script type="text/javascript">
		$(document).ready(function() { 
			getqrcodeapplycountbyenttype();
			getqrcodeapplycountbyenttype2();
			/* getgauge();
			getqrcode_aweek_line();
			queryApplyTypeAndStatusCount(); */
		});
	</script>              
</body>
</html>