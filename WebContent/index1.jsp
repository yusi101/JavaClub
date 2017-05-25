<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<title>${applicationScope.sysTitle }-登录</title>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<script type="text/javascript">
		if (window != top) {
			top.location.href = location.href;
		}
	</script>
	<script src="${pageContext.request.contextPath}/static/scripts/util/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/login.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/styles/mycss/login.css" type="text/css"/>
</head>

<body>
<%-- 	<div style="background: url(${pageContext.request.contextPath}/static/images/Login/background-top.png); width: 100%; height: 30px; position: absolute; z-index: 1;"></div>
	<div style="background: url(${pageContext.request.contextPath}/static/images/Login/蓝天.png); width: 100%; height: 450px; position: absolute; z-index: 1; margin-top: 30px;"></div>
	<div style="background: rgb(239,241,241); width: 100%; height: 140px; position: absolute; z-index: 1; margin-top: 480px;"></div> --%>
<!-- 	<div class="main" id="main"> -->
		<img src="${pageContext.request.contextPath}/static/images/Login/background.png" style="width: 100%; height: 80%; position: absolute; z-index: -1; background-repeat: no-repeat"/>
		<img src="${pageContext.request.contextPath}/static/images/Login/top.png" style="width: 100%; position: absolute; z-index: -1; background-repeat: no-repeat"/>
		<div class="top">
			<a class="entname">${applicationScope.sysTitle }</a>
		</div>
		<div class="middel">
			<div class="login" style="opacity: 0.7; filter:alpha(opacity:70); background: #FFF;"></div>
			<div class="login">
				<div class="login_name">
					<div class="name">用户登录</div>
				</div>
				<div class="from_content">
					<div class="from_img">
						<img src="${pageContext.request.contextPath}/static/images/Login/username2.png" style="width: 16px; height: 16px; margin: 5px 10px;" />
					</div>
					<div class="from_text">
						<input type="text" class="username" name="username" id="username" placeholder="用户名"/>
					</div>
				</div>
				<div class="from_content">
					<div class="from_img">
						<img src="${pageContext.request.contextPath}/static/images/Login/password.png" style="width: 16px; height: 16px; margin: 5px 10px;" />
					</div>
					<div class="from_text">
						<input type="password" class="password" name="password" id="password" placeholder="密码" />
					</div>
				</div>
				<div class="from_code">
					<div class="from_content" style="width: 102px; margin: 0px; float: left;">
						<div class="from_img">
							<img src="${pageContext.request.contextPath}/static/images/Login/code.png" style="width: 16px; height: 16px; margin: 5px 10px;" />
						</div>
						<div class="from_text" style="width: 60px;">
							<input type="text" class="code" name="code" id="code" placeholder="验证码" maxlength="4" />
						</div>
					</div>
					<img class="checkcode" id="codeImg" alt="点击更换" title="点击更换" src="" />
				</div>
				<div class="from_content" style="border: none; margin-top: 30px;">
					<input type="button" class="submit" value="登录" id="submit" onclick="tologin();"/>
				</div>
			</div>
		</div>		
		<div class="bottom">
			<ul>
				<li>
					<div class="bottom_left"><img src="${pageContext.request.contextPath}/static/images/Login/企业.png" /></div>
					<div class="bottom_right">
						<h4>工商查询</h4>
						<p>企业股东，法人，法人对外投资，企业对外投资信息咨询</p>
					</div>
				</li>	
				<li>
					<div class="bottom_left"><img src="${pageContext.request.contextPath}/static/images/Login/商标.png" /></div>
					<div class="bottom_right">
						<h4>商标查询</h4>
						<p>商标信息变更，商标使用，为你轻松开启创业之路</p>
					</div>
				</li>					
				<li>
					<div class="bottom_left"><img src="${pageContext.request.contextPath}/static/images/Login/专利.png" /></div>
					<div class="bottom_right">
						<h4>专利查询</h4>
						<p>更深入全面的了解企业，为企业之间合作提供全力保障</p>
					</div>
				</li>
				<li>
					<div class="bottom_left"><img src="${pageContext.request.contextPath}/static/images/Login/失信人.png" /></div>
					<div class="bottom_right">
						<h4>失信人/被执行人查询</h4>
						<p>全国法院失信人执行名单，为您保驾护航</p>
					</div>
				</li>					
			</ul>
		</div>
<!-- 	</div> -->
</body>
<!-- 	<script type="text/javascript">
		//居中mian主要数据层
		window.onload = function(){
			var sWidth = document.body.scrollWidth;
			var m = document.getElementById("main");
			var mWidth = document.getElementById("main").offsetWidth;
			m.style.left = (sWidth-mWidth)/2 + "px";
		}
	</script> -->
	<script src="${pageContext.request.contextPath}/static/scripts/util/jquery.tips.js"></script>
</body>
</html>