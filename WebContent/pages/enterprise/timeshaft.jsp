<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!--[if IE 7]><html class="ie7" lang="zh"><![endif]-->
<!--[if gt IE 7]><!-->
<html lang="zh">
<!--<![endif]-->
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>企业历程</title>
	<link href="${pageContext.request.contextPath }/static/plugins/timeshaft/css/style.css" rel="stylesheet" type="text/css" />
</head>

<body style="background-color: #fff;">	
	<div class="content">
		<div class="wrapper">
			<div class="light">
				<i></i>
			</div>
			<hr class="line-left">
			<hr class="line-right">
			<div class="main">
				<h1 class="title">${pd.entname }</h1>
				<c:forEach items="${entList }" var="entList">
					<div class="year">
						<h2>
							<a href="javascript:void(0)">${entList.year }年<i></i></a>
						</h2>
						<div class="list">
							<ul>
								<c:forEach items="${entList.yeardata }" var="yeardata">
									<li class="cls">
										<p class="date">${yeardata.date }</p>
										<c:if test="${yeardata.type == '个体变更' || yeardata.type == '企业变更'}">
											<p class="intro">变更：</p>
										</c:if>
										<c:if test="${yeardata.type != '个体变更' && yeardata.type != '企业变更'}">
											<p class="intro">${yeardata.type }：</p>
										</c:if>
										<p class="version">&nbsp;</p>
										<c:if test="${yeardata.type == '企业'}">
											<div class="more">
												<p>${yeardata.title }诞生了！</p>
											</div>										
										</c:if>
										<c:if test="${yeardata.type == '招聘'}">
											<div class="more">
												<p><font color="#ec6a13">招兵买马！干出一番事业：</font>${yeardata.title }</p>
											</div>
										</c:if>
										<c:if test="${yeardata.type == '专利'}">
											<div class="more">
												<p><font color="#ec6a13">华丽起步！申请到了一个专利：</font>${yeardata.title }</p>
											</div>
										</c:if>
										<c:if test="${yeardata.type == '中标'}">
											<div class="more">
												<p><font color="#ec6a13">时刻准备！我们得到一个中标：</font>${yeardata.title }</p>
											</div>
										</c:if>
										<c:if test="${yeardata.type == '新闻'}">
											<div class="more">
												<p><font color="#ec6a13">奇闻妙事！发布了一个新闻：</font>${yeardata.title }</p>
											</div>
										</c:if>	
										<c:if test="${yeardata.type == '商标'}">
											<div class="more">
												<p><font color="#ec6a13">再接再厉！注册了一个商标：</font>${yeardata.title }</p>
											</div>
										</c:if>	
										<c:if test="${yeardata.type == '著作权'}">
											<div class="more">
												<p><font color="#ec6a13">乘风破浪！申请到了一个著作权：</font>${yeardata.title }</p>
											</div>
										</c:if>
										<c:if test="${yeardata.type == '软件著作权'}">
											<div class="more">
												<p><font color="#ec6a13">迅速发展！申请到了一个软件著作权：</font>${yeardata.title }</p>
											</div>
										</c:if>	
										<c:if test="${yeardata.type == '荣誉'}">
											<div class="more">
												<p><font color="#ec6a13">光芒万丈！获得了第一个荣誉：</font>${yeardata.title }</p>
											</div>
										</c:if>																																										
										<c:if test="${yeardata.type == '企业变更'}">
											<div class="more">
												<p>${yeardata.title }</p>
											</div>
										</c:if>		
										<c:if test="${yeardata.type == '个体变更'}">
											<div class="more">
												<p>${yeardata.title }</p>
											</div>
										</c:if>												
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/plugins/timeshaft/js/jquery.min.js"></script>
	<script>
		$(".main .year .list").each(function (e, target) {
		    var $target=  $(target),
		        $ul = $target.find("ul");
		    $target.height($ul.outerHeight()), $ul.css("position", "absolute");
		}); 
		$(".main .year>h2>a").click(function (e) {
		    e.preventDefault();
		    $(this).parents(".year").toggleClass("close");
		});
	</script>
</body>
</html>
