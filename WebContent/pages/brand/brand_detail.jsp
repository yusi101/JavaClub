<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="modo" uri="/WEB-INF/custom-tld/urltag.tld"%>
<!DOCTYPE html>
<html class="um landscape min-width-240px min-width-320px min-width-480px min-width-768px min-width-1024px">
<head>
	<title>商标详情</title>
	<meta charset="utf-8">
	<%@ include file="../system/allresources.jsp" %>
	<style type="text/css">
		.div_baseinfo {
			margin-top: 10px;
		}
		.div_baseinfo ul{
			margin: 0px;
		}
		.div_baseinfo li {
			margin: 0.5em 2%;
			display: inline-block;
			border-bottom: 1px solid #ccc;
			width: 96%;
			margin-bottom: 0px;
		}
		
		.div_baseinfo li div {
			margin: 0.5em 0;
			min-height: 25px;
			line-height: 25px;
			font-size: 13px;
			float: right;
			margin-right: 0.5em;
		}
		
		.div_baseinfo li div:first-child {
			float: left;
			margin-left: 0.5em;
		}
		
		.div_split {
			width: 100%;
			height: 30px;
			background: #F7F3F3;
			line-height: 30px;
			min-height: 30px;
			font-size: 13px;
			text-indent: 1em;
		}
		
		.uinn-m {
			padding: 0.4em 0.7em 0 0.7em;
			margin:0.5em;
		}
		.lcontent {
			border: 1px solid #d4cfca;
			margin:5px;
		}
	</style>
</head>

<body style="background-color: #fff !important;">
	<div id="content">
		<c:if test="${!empty brandInfo }">
			<img src="${brandInfo.BRANDIMG}" style="background: #fff; height: 10em; margin: 0 auto; display: -webkit-box;">
			<div style="width: 100%;" class="div_baseinfo">
				<ul>
					<li>
						<div>商标名称</div>
						<div>${brandInfo.BRANDNAME}</div>
					</li>
					<li>
						<div>商标类型</div>
						<div>${brandInfo.CLASSIFYID}</div>
					</li>
					<li>
						<div>注册号</div>
						<div>${brandInfo.REGCORE}</div>
					</li>
					<li>
						<div>状态</div>
						<div>${brandInfo.BRANDSTAUTS}</div>
					</li>
					<li>
						<div>申请时间</div>
						<div>${brandInfo.APPLICATIONDATE}</div>
					</li>
					<li>
						<div>使用期限</div>
						<div>${brandInfo.LIFESPAN}</div>
					</li>
				</ul>
			</div>
			<div class="div_split">申请进度</div>
			<c:set value="${ fn:split(brandInfo.PROCEDURE, '@') }" var="procedures" />
			<div class="ub bc-bg uinn-m">
				<c:forEach items="${procedures}" var="procedure">
					<div class="c-wh lcontent uinn ub-f1 uc-a1">
						<div class="ulev-1">
							<c:set value="${ fn:split(procedure, ' ') }" var="pds" />
							<c:forEach items="${pds}" var="p">
								<p style="margin: 0.5em;">${p}</p>
							</c:forEach>
						</div>
					</div>
				</c:forEach>
			</div>
			<div class="div_split">商品/服务项目</div>
			<c:set value="${ fn:split(brandInfo.BRANDLIST, ' ') }" var="brandlists" />
			<div style="width: 100%;" class="div_baseinfo">
				<ul>
					<c:forEach items="${brandlists}" var="brandlist">
						<li>
							<div>${brandlist}</div>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="div_split">代理机构</div>
			<div class="div_split" style="background: #fff;">${brandInfo.AGENCY}</div>
			<div class="div_split"></div>
		</c:if>
		<c:if test="${empty brandInfo }">
			<div align="center">
				<img src="${pageContext.request.contextPath }/images/nodata.png" style="width: 6em; margin-top: 4em;">
				<div style="line-height: 30px; color: #908888; font-size: 14px;">这里空空如也~~</div>
			</div>
		</c:if>
	</div>
</body>
</html>