<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>图片轮播管理</title>
</head>
<body>
	<%@ include file="../system/allresources.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">图片轮播管理</h4>
			<form action="${pageContext.request.contextPath}/carouselController/queryCarouselInfo" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 10%; float: left;">
					<a class="btn btn-add" onclick="openadd();"><i class="fa fa-plus"></i>&nbsp;新增</a>
				</div>
				<div style="width: 90%; float: left; text-align: right;">
					图片名称：<input type="text" placeholder="请输入图片名称" name="brandName" value="${pd.brandName}" style="vertical-align: top;" />
					<button class="btn btn-search"  onclick="queryBrandinfo();" ><i class="fa fa-search"></i> 搜索</button>
				</div>
				<div class="clren"></div>
			</form>
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th class="center" width="5%">序号</th>
						<th class="center" width="10%">名称</th>
						<th class="center" width="15%">图片</th>
						<th class="center" width="15%">链接地址</th>
						<th class="center" width="15%">创建时间</th>
						<th class="center" width="10%">创建人</th>
						<th class="center" width="5%">排序号</th>
						<th class="center" width="5%">区域</th>
						<th class="center" width="20%">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty carouselInfo }">
							<c:forEach items="${carouselInfo }" var="list" varStatus="status">
								<tr>
									<td class="center" >${status.index+1 }</td>
									<td class="center" >${list.NAME}</td>
									<td class="center" >
									   <img
										src="data:image/jpg;base64,${list.PATH}"
										style="width: 80% !important;" width="100px" height="100px">
									</td>
									<td class="center" >${list.TOURL}</td>
									<td class="center">${list.CREATETIME}</td>
									<td class="center" >${list.CREATEUSER_NAME}</td>
									<td class="center" >${list.ORDERBY}</td>
									<td class="center" >${list.AREA_CODE}</td>
									<td class="dropdown" >
										<button class="btn btn-edit" onclick="openedit('${list.ID }')"><i class="fa fa-edit"></i>&nbsp;编辑</button>
										<button class="btn btn-del" onclick="deletecarousel('${list.ID }')"><i class='fa fa-trash'></i>&nbsp;删除</button>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="9" style="color: red; text-align: center;">
									<div align="center">
										<img
											src="${pageContext.request.contextPath}/static/images/nodata.png"
											style="width: 100px; margin: 10px auto;">
									</div>
									<div style="margin-bottom: 10px;">小查没有查到数据哦~</div>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<div class="position-relative">
				<table style="width: 100%;">
					<tr>
						<td style="vertical-align: top;">
						<div class="pagination" style="float: left; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/static/scripts/myjs/carousel.js"></script>
</body>
</html>