<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="modo" uri="/WEB-INF/custom-tld/urltag.tld"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="renderer" content="webkit" /> 
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
	<title>${applicationScope.sysTitle }</title>
	<%@ include file="system/allresources.jsp"%>
	<!--common-->
	<link href="${pageContext.request.contextPath}/static/plugins/assets/css/style.css" rel="stylesheet">
	<link href="http://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
		<script src="${pageContext.request.contextPath}/static/plugins/assets/js/html5shiv.js"></script>
		<script src="${pageContext.request.contextPath}/static/plugins/assets/js/respond.min.js"></script>
	<![endif]-->
	<SCRIPT language=javascript> 
<!-- 
window.onerror=function(){return true;} 
// --> 
</SCRIPT> 

</head>

<body >
<section class="sticky-header">
    <!-- left side start-->
    <div class="left-side sticky-left-side">

        <!--logo and iconic logo start-->
        <div class="logo">
            <a href="javascript:void(0);">
           		<div style="height:100%;margin-right: 8px;float: left;">
           			<img src="${pageContext.request.contextPath}/static/images/logo.png" alt="" onclick="javascript:void(0);" style="width: 35px;height: 35px;">
           		</div>
            	<span>${applicationScope.sysTitle }</span>
            </a>
        </div>
        <div class="logo-icon text-center">
            <a href="javascript:void(0);">
				<img src="${pageContext.request.contextPath}/static/images/logo.png" alt="" onclick="javascript:void(0);" style="width: 35px;">
			</a>
        </div>
        <!--logo and iconic logo end-->

        <div class="left-side-inner">
           <!--sidebar nav start-->
           <ul class="sidebar-menu">
                <c:forEach items="${authList }" var="authList">
               	 	<!-- 显示一级菜单 -->
	               	<c:if test="${!empty authList.sonMenuList }"> 
		               <li class="treeview">
		               	 <a href="javascript:void(0);">
			          		<c:if test="${fn:contains(authList.MENU_ICON,'zhirong')}">
		               	 		<img class="left-icon-child"  src="${authList.MENU_ICON }" /> 
		               	 	</c:if>
			          		<c:if test="${!fn:contains(authList.MENU_ICON,'zhirong')}">
		               	 		<i class="fa ${authList.MENU_ICON }"></i>
		               	 	</c:if>
			          		 <span>${authList.MENU_NAME } <i class="fa fa-angle-left pull-right"></i></span>
			        	</a>
			        	<ul class="treeview-menu">
			        		<c:forEach items="${authList.sonMenuList }" var="sonMenuList">
		          			   <c:if test="${!empty sonMenuList.sonMenuList }"> 
					             <li>
					               	 <a href="javascript:void(0);">
					               	 	<c:if test="${fn:contains(sonMenuList.MENU_ICON,'zhirong')}">
					               	 		<img class="left-icon-child" src="${sonMenuList.MENU_ICON }" /> 
					               	 	</c:if>
						          		<c:if test="${!fn:contains(sonMenuList.MENU_ICON,'zhirong')}">
					               	 		<i class="fa ${sonMenuList.MENU_ICON }"></i>
					               	 	</c:if>
						          		${sonMenuList.MENU_NAME } <i class="fa fa-angle-left pull-right"></i>
						        	</a>
						        	<ul class="treeview-menu">
						        		<c:forEach items="${sonMenuList.sonMenuList }" var="threeMenuList">
					          			   <c:if test="${!empty threeMenuList.sonMenuList }"> 
								             <li>
								               	 <a href="javascript:void(0);">
									          		<c:if test="${fn:contains(threeMenuList.MENU_ICON,'zhirong')}">
								               	 		<img class="left-icon-child"  src="${threeMenuList.MENU_ICON }" /> 
								               	 	</c:if>
									          		<c:if test="${!fn:contains(threeMenuList.MENU_ICON,'zhirong')}">
								               	 		<i class="fa ${threeMenuList.MENU_ICON }"></i>
								               	 	</c:if>
									          		 ${threeMenuList.MENU_NAME } <i class="fa fa-angle-left pull-right"></i>
									        	</a>
							              	 </li>
							               </c:if>
							               <c:if test="${empty threeMenuList.sonMenuList }">
							              	 <li>
								               	 <a href="${pageContext.request.contextPath }${threeMenuList.MENU_URL }" target="iframeContent">
									          		<c:if test="${fn:contains(threeMenuList.MENU_ICON,'zhirong')}">
								               	 		<img class="left-icon-child"  src="${threeMenuList.MENU_ICON }" /> 
								               	 	</c:if>
									          		<c:if test="${!fn:contains(threeMenuList.MENU_ICON,'zhirong')}">
								               	 		<i class="fa ${threeMenuList.MENU_ICON }"></i>
								               	 	</c:if>
									          		 ${threeMenuList.MENU_NAME }
									        	</a>
							              	 </li>
							               </c:if>
					          			</c:forEach> 
					        		</ul>
				              	 </li>
				               </c:if>
				               <c:if test="${empty sonMenuList.sonMenuList }">
				              	 <li>
					               	 <a href="${pageContext.request.contextPath }${sonMenuList.MENU_URL }" target="iframeContent">
						          		<c:if test="${fn:contains(sonMenuList.MENU_ICON,'zhirong')}">
					               	 		<img class="left-icon-child"  src="${sonMenuList.MENU_ICON }" /> 
					               	 	</c:if>
						          		<c:if test="${!fn:contains(sonMenuList.MENU_ICON,'zhirong')}">
					               	 		<i class="fa ${sonMenuList.MENU_ICON }"></i>
					               	 	</c:if>
						          		${sonMenuList.MENU_NAME }
						        	</a>
				              	 </li>
				               </c:if>
		          			</c:forEach> 
		        		</ul>
	              	 </li>
	               </c:if>
	               <c:if test="${empty authList.sonMenuList }">
	              	 <li>
		               	 <a href="${pageContext.request.contextPath }${authList.MENU_URL }" target="iframeContent">
			          		<c:if test="${fn:contains(authList.MENU_ICON,'zhirong')}">
		               	 		<img class="left-icon-child"  src="${authList.MENU_ICON }" /> 
		               	 	</c:if>
			          		<c:if test="${!fn:contains(authList.MENU_ICON,'zhirong')}">
		               	 		<i class="fa ${authList.MENU_ICON }"></i>
		               	 	</c:if>
			          		<span>${authList.MENU_NAME }</span>
			        	</a>
	              	 </li>
	               </c:if>
	        	</c:forEach> 
		    </ul>
            <!--sidebar nav end-->

        </div>
    </div>
    <!-- left side end-->
    
    <!-- main content start-->
    <div class="main-content" >

        <!-- header section start-->
        <div class="header-section">

            <!--toggle button start-->
            <a class="toggle-btn"><i class="fa fa-bars"></i></a>
            <!--toggle button end-->
			<!-- page heading start-->
	        <div class="page-heading" style="display: -webkit-inline-box;float: left;">
	            <ul class="breadcrumb">
	                <li>当前位置：</li>

	                <li style="content:'';">
	                    <a href="${pageContext.request.contextPath}/main/index"><i class="fa fa-home"></i><span>首页</span></a>
	                </li>
	                <i class="fa fa-angle-double-right" style="margin: 0px 10px;display: none;"></i>
	                <li class="active"></li>
	            </ul>
	        </div>
	        <!-- page heading end-->

            <!--notification menu start -->
            <div class="menu-right">
                <ul class="notification-menu">
                    <li>
                        <a href="#" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                            <img src="${pageContext.request.contextPath}/static/images/top/user.jpg" alt="" />
                            ${sessionScope.sessionUser.NAME }
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
                            <li><a href="javascript:openquery()"><i class="fa fa-user"></i>  &nbsp;修改资料</a></li>
                            <li><a href="javascript:openedt()"><i class="fa fa-gears"></i> 修改密码</a></li>
                            <li><a href="javascript:logout();"><i class="fa fa-sign-out"></i>  &nbsp;安全退出</a></li>
                        </ul>
                    </li>

                </ul>
            </div>
            <!--notification menu end -->

        </div>
        <!-- header section end-->

        

        <!--body wrapper start-->
      	 <iframe src="${pageContext.request.contextPath}/homePageController/toHomePage" id="iframeContent" name="iframeContent" frameBorder=0 width="100%"></iframe> 
        <!--body wrapper end-->

        <!--footer section start-->
        <footer>
            Copyright © 2017 ZHIRONG .All rights reserved. 版权所有 智容科技有限公司赣ICP备05002520号
        </footer>
        <!--footer section end-->
    </div>
    <!-- main content end-->
</section>
<!-- 滚动条 -->
<!-- 滚动条插件jquery.nicescroll.js与火狐和ie11冲突-->
<script type="text/javascript">
	var explorer = navigator.userAgent;
	if(explorer.indexOf('Firefox') >= 0 || !!window.ActiveXObject || "ActiveXObject" in window){
	}else{
		$('section').after('<script src="${pageContext.request.contextPath}/static/plugins/assets/js/jquery.nicescroll.js"/>');
	}
</script>
<!--菜单-->

<script src="${pageContext.request.contextPath}/static/plugins/assets/dist/sidebar-menu.js"></script>
<%-- <script src="${pageContext.request.contextPath}/static/plugins/assets/js/jquery.nicescroll.js"></script> --%>
<script src="${pageContext.request.contextPath}/static/plugins/assets/js/scripts.js"></script>

</body>
</html>