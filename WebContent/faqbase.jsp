<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>常见问题FAQ</title>
<%@ include file="pages/system/allresources.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/styles/login/less.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/styles/login/basic.css">
<!--[if IE 8]>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/styles/login/ie8.css">
<![endif]-->
<!--[if gte IE 9]> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/styles/login/ie.css"> 
<![endif]-->
<style type="text/css">
.faq-topBg {
	background:
		url(${pageContext.request.contextPath}/static/images/Login/background-tops.png)
		center center no-repeat #fff;
}

.login-topStyle3 {
	background:
		url(${pageContext.request.contextPath}/static/images/Login/login_bg_white.png)
		center center repeat;
	width: 228px;
	height: auto;
	overflow: hidden;
	top: 34px;
	right: 50px;
	position: absolute;
	border-radius: 5px;
	padding: 22px 30px;
}

.login-center {
	background:
		url(${pageContext.request.contextPath}/static/images/Login/login-center-bg.png)
		center center no-repeat;
	width: 1190px;
	height: 120px;
	margin: 20px auto 0px auto;
	position: relative;
}
</style>
<script
	src="${pageContext.request.contextPath}/static/scripts/myjs/login.js"></script>
<script type="text/javascript">
	if (window != top) {
		top.location.href = location.href;
	}
</script>
<body>
	<div class="wrapper" style="background-color: white;">
		<div class="login-top">
			<div style="height: 60px; background-color: white;">
				<div style="width: 82%; margin: 0 auto; padding: 10px;">
					<img
						src="${pageContext.request.contextPath}/static/images/logo.png"
						style="width: 40px; height: 40px;" />
					<div class="title">${applicationScope.sysTitle }</div>
				</div>
				<div
					style="float: right; margin-top: -34px; width: 360px; font-size: 12px;">
					<span style="margin: 0 10px;"><a
						href="${pageContext.request.contextPath}/index.jsp"
						style="color: #2B2A2A;">系统首页</a></span> <span style="margin: 0 10px;">|</span>
					<span style="margin: 0 10px;">登录帮助</span> <span
						style="margin: 0 10px;">|</span> <span style="margin: 0 10px;"><a
						href="#">常见问题</a></span>
				</div>
			</div>
			<div class="faq-topBg"
				style="width:100%; margin-top: 20px; margin-bottom: 20px; ">
				<c:if test="${not empty know_list }">
					<ul style="margin-left:500px">
					<c:forEach items="${know_list }" var="list" varStatus="status">
						<li style="color: #6993DF;font-size:22px;padding-top:20px">Q:${list.TITLE}</li>
						<li style="color: #6993DF;font-size:22px;padding-top:20px">A:${list.CONTENT}</li>
					</c:forEach>
					</ul>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>