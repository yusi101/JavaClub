<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域营业额营业总额</title>
<%@ include file="../system/allresources.jsp"%>
<style type="text/css">
#search{
	margin: 10px;
}
#search label{
	display: -webkit-inline-box;

}
.title{
	color: #fff;
    border-left: 5px solid #3AA8FB;
    margin-left: 20px;
    font-size: 20px;
    height: 25px;
    line-height: 25px;
    padding-left: 11px;
    font-weight: bold;
    font-family: Microsoft YaHei;
    word-spacing: 8px;
    letter-spacing: 2px;
    margin-top: 50px;
}
</style>
</head>
<body style="background-color: #333334">
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/plugins/echarts/echarts.min.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath }/static/scripts/turnover/turnoverline.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath }/static/scripts/turnover/turnoverpyramid.js"></script>
    <form action="${pageContext.request.contextPath}/annualReportAnalysisController/areaTurnoverAnalysis" method="POST" id="search">
    	<label for="industry" style="color: #fff;">行业：</label>
   		<select name="industry" id="industry" style="margin: 0px;">
   			<option  value="">----请选择行业----</option>
   	 		<c:forEach items="${industryList }" var="list">
   				<option value="${list.EC_VALUE }" <c:if test="${list.EC_VALUE == pd.industry}"> selected = "selected"</c:if>>${list.EC_NAME }</option>
   			</c:forEach> 
   		</select>
   		<button type="button" class="btn btn-primary" onclick="$('#search').submit();">查询</button>
   	</form>
   	<div class="title">南昌市各区域的2013-2015年度营业总额</div>
   	<div id="turnoverline" style="height:500px; width: 100%; background: #FFF;"></div>
   	<div class="title">南昌市各区域的2013-2015年度营业总额</div>
   	<div id="turnoverpyramid" style="height:500px; width: 100%; background: #FFF;"></div>
    <script type="text/javascript">
    $(function(){
    	var industryname=$("#industry option:selected").text();
    	if(industryname=="----请选择行业----" ||industryname=="----请选择区域----"){
    		industryname="";
    	}
    	industryname=industryname+"南昌市各区域的2013-2015年度营业总额";
    	$(".title").html(industryname);
    	getturnoverline(0,$("#industry option:selected").text(),${data});
    	getturnoverpyramid(0,$("#industry option:selected").text(),${data});
    });
    
    </script>
</body>
</html>