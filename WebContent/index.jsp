<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" /> 
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
<title>${applicationScope.sysTitle }</title>
	<%@ include file="pages/system/allresources.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/styles/login/less.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/styles/login/basic.css">
<!--[if IE 8]>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/styles/login/ie8.css">
<![endif]-->
<!--[if gte IE 9]> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/styles/login/ie.css"> 
<![endif]-->
<style type="text/css">
.login-topBg {
	background: url(${pageContext.request.contextPath}/static/images/Login/login_img.jpg) center center no-repeat #fff;
	height: 443px;
	min-width: 1190px;
}
.login-topStyle3 {
	background: url(${pageContext.request.contextPath}/static/images/Login/login_bg_white.png) center center repeat;
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
	background: url(${pageContext.request.contextPath}/static/images/Login/login-center-bg.png) center center no-repeat;
	width: 1190px;
	height: 120px;
	margin: 20px auto 0px auto;
	position: relative;
}
</style>
<script src="${pageContext.request.contextPath}/static/scripts/myjs/login.js"></script>
<script type="text/javascript">
		if (window != top) {
			top.location.href = location.href;
		}
	</script>
</head>
<body>
	<div class="wrapper" style="background-color: white;">
		<div class="login-top">
			<div style="height: 60px; background-color: white;">
				<div style="width: 82%; margin: 0 auto; padding: 10px;">
					<img src="${pageContext.request.contextPath}/static/images/logo.png" style="width: 40px; height: 40px;" />
					<div class="title">${applicationScope.sysTitle }</div>
				</div>
				<div style="float:right;margin-top: -34px;width: 360px;font-size: 12px;">
					<span style="margin: 0 10px;" >系统首页</span>
					<span style="margin: 0 10px;">|</span>
					<span style="margin: 0 10px;">登录帮助</span>
					<span style="margin: 0 10px;">|</span>
					<span style="margin: 0 10px;"><a href="${pageContext.request.contextPath }/knowledgeController/queryKnowfaqInfo" style="color:#2B2A2A;">常见问题</a></span>
				</div>
			</div>
			<div class="login-topBg">
				<div class="login-topBg1">
					<div class="login-topStyle">
						<!--在点击注册时出现样式login-topStyle3登录框，而login-topStyle2则消失-->
						<div class="login-topStyle3" id="loginStyle" style="margin-top: 40px;">
							<h3>系统登录</h3>
							<!--输入错误提示信息，默认是隐藏的，把display:none，变成block可看到-->
							<div class="error-information" style="display: none;">您输入的密码不正确，请重新输入</div>
							<div class="ui-form-item loginUsername">
								<input type="text" placeholder="用户名/手机号" id="username">
							</div>
							<div class="ui-form-item loginCode">
								<input type="password" placeholder="请输入密码" id="password">
							</div>
							<div class="ui-form-item loginPassword">
								<input type="text" placeholder="请输入验证码" style="width: 100px;" id="code">
								<img class="checkcode" id="codeImg" alt="点击更换" title="点击更换" src="" style="height: 35px;vertical-align: top;float: right;" onclick="changeCode();"/>
							</div>
							<!-- <div class="login_reme">
								<input type="checkbox" style="margin: 0px;">
								<a class="reme1">记住账号</a>
							</div> -->
							<span class="error_xinxi" style="display: none;">您输入的密码不正确，请重新输入</span>
							<a class="btnStyle btn-register" href="javascript:tologin();"> 立即登录</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="loginCen" style="margin-top: 35px;">
			<div class="login-center">
				<div class="loginCenter-moudle">
					<div class="loginCenter-moudleRight" style="width: 1067px;">
						<!--第一个-->
						<a class="loginCenter-mStyle loginCenter-moudle1" id="moudleRemove" style="display: block; width: 340px;">
							<span class="moudle-img">
								<img src="${pageContext.request.contextPath}/static/images/Login/企业.png">
							</span>
							<span class="moudle-text">
								<div class="moudle-text1" style="font-family: '微软雅黑'">工商查询</div>
								<div class="moudle-text2" style="font-family: '微软雅黑'">企业股东，法人，法人对外投资，企业对外投资信息咨询</div>
							</span>
						</a>
						<!--第二个-->
						<a class="loginCenter-mStyle loginCenter-moudle2" style="display: block; width: 357px;" id="moudleRemove2">
							<span class="moudle-img">
								<img src="${pageContext.request.contextPath}/static/images/Login/商标.png">
							</span>
							<span class="moudle-text">
								<div class="moudle-text1" style="font-family: '微软雅黑'"> 商标查询 </div>
								<div class="moudle-text2" style="font-family: '微软雅黑'">商标信息变更，商标使用，为你轻松开启创业之路</div>
							</span>
						</a>
						<!--第三个-->
						<a class="loginCenter-mStyle loginCenter-moudle3" style="display: block;" id="moudleRemove3">
							<span class="moudle-img">
								<img src="${pageContext.request.contextPath}/static/images/Login/专利.png">
							</span>
							<span class="moudle-text">
								<div class="moudle-text1" style="font-family: '微软雅黑'"> 专利查询</div>
								<div class="moudle-text2" style="font-family: '微软雅黑'">更深入全面的了解企业，为企业之间合作提供全力保障</div>
							</span>
						</a>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">
			<span class="footerText">Copyright © 2017 ZHIRONG .All rights reserved. 版权所有 智容科技有限公司赣ICP备05002520号</span>
		</div>
	</div>
</body>
</html>