<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>著作权页面</title>
	<%@ include file="../system/allresources.jsp" %>
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">著作权查询</h4>
			<form action="" method="post" name="queryCopyrightInfo" id="queryCopyrightInfo" class="form-horizontal" style="margin: 10px 0;height: 30px;">
				<div style="width: 100%; float: left; text-align: right;">
					著作权名称：<input type="text" placeholder="请输入著作权名称" id="copyrightName" name="copyrightName" value="${pd.copyrightName}" style="vertical-align: top;" maxlength="400" />
					<input type="hidden" id="workCopyrightName" name="workCopyrightName" value="${pd.copyrightName}"/>
					企业名称：<input type="text" placeholder="请输入企业名称" id="entName" name="entName" value="${pd.entName}" style="vertical-align: top;" maxlength="200"/>
					类型：<select  name="selectchoose" id="selectchoose" onchange="chooseType()">
						<option value="0" <c:if test="${pd.selectchoose == 0 }">selected</c:if>>著作权</option>
						<option value="1" <c:if test="${pd.selectchoose == 1 }">selected</c:if>>软件著作权</option>
					</select>
					<button class="btn btn-search"  onclick="queryCopyrightinfo();" ><i class="fa fa-search"></i>搜索</button>				
				</div>
				<div class="clren"></div>
			</form>		
			<div id="ruanjian">
				<table class="table table-condensed table-bordered table-hover tab">
						<thead>
							<tr>
								<th width="5%">序号</th>
								<th width="15%">登记号</th>
								<th width="20%">软件名称</th>
								<th width="10%">软件简称</th>
								<th width="15%">著权人</th>
								<th width="15%">企业名称</th>
								<th width="10%">首次发表时间</th>
								<th width="10%">登记日期</th>
							</tr>
						</thead>
						<c:choose>
							<c:when test="${not empty copyrightinfo }">
								<c:if test="${!empty copyrightinfo }">
									<tbody id="tbody">
										<c:forEach items="${copyrightinfo}" var="list" varStatus="status">
											<tr>
												<td>${status.index+1 }</td>
												<td>${list.REGISTERID}</td>
												<td>${list.SOFTWARENAME}</td>
												<td>${list.SOFTWARESHORT}</td>
												<td>${list.OWNERNAME}</td>
												<td>${list.ENTNAME}</td>
												<td>${list.STARTINGDATE}</td>
												<td>${list.REGISTERDATA}</td>
											</tr>								
										</c:forEach>
									</tbody>
								</c:if>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="8" style="color: red; text-align: center;">
										<div align="center">
											<img src="${pageContext.request.contextPath}/static/images/nodata.png" style="width: 100px; margin: 10px auto;">
										</div>
										<div style="margin-bottom: 10px;">小查没有查到数据哦~</div>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
				<div id="NOruanjian">
					<table class="table table-condensed table-bordered table-hover tab">
						<thead>
							<tr>
								<th width="5%">序号</th>
								<th width="15%">登记号</th>
								<th width="16%">作品名称</th>
								<th width="12%">创作完成日期</th>
								<th width="17%">著作权人</th>
								<th width="13%">登记日期</th>
								<th width="12%">首次发表时间</th>
								<th width="10%">作品类别</th>
							</tr>
						</thead>
						<c:choose>
							<c:when test="${not empty workCopyrightinfo }">
								<c:if test="${!empty workCopyrightinfo }">
									<tbody id="tbody">
										<c:forEach items="${workCopyrightinfo}" var="list" varStatus="status">
											<tr>
												<td>${status.index+1 }</td>
												<td>${list.REGISTERID}</td>
												<td>${list.WORKNAME}</td>
												<td>${list.FINISHDATE}</td>
												<td>${list.WORKOWNER}</td>
												<td>${list.REGISTERDATA}</td>
												<td>${list.FIRSTDATE}</td>
												<td>${list.WORKCLASS}</td>
											</tr>
										</c:forEach>
									</tbody>
								</c:if>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="8" style="color: red; text-align: center;">
										<div align="center">
											<img src="${pageContext.request.contextPath}/static/images/nodata.png" style="width: 100px; margin: 10px auto;">
										</div>
										<div style="margin-bottom: 10px;">小查没有查到数据哦~</div>
									</td>
								</tr>
							</c:otherwise>
						</c:choose>	
					</table>
				</div>
				<div class="position-relative">
					<table style="width: 100%;">
						<tr>
							<td style="vertical-align: top;"><div class="pagination" style="float: left; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<script src="${pageContext.request.contextPath}/static/scripts/myjs/copyright.js"></script>
</body>
</html>