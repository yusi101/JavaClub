<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>企业自主更新</title>
	<%@ include file="../system/allresources.jsp"%>
</head>
<body> 
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div style="margin: 0 auto;">
				<div  id="queryEnterprise" class="form-horizontal" style="margin: 10px 0;">
					<div style="text-align: right;">
						<c:forEach items="${selfUpdataInfo}" var="list" varStatus="status">
							<table id="table_bug_report" class="table table-striped table-bordered table-hover">
								<input type="hidden" id="memberId" value="${list.CREATEUSER_ID }"/>
								<tr>
									<td style="text-align:right;width:20%;">企业名称</td>
									<td >${list.ENTNAME}</td>
								</tr>
								<tr>
									<td style="text-align:right;width:20%;">标题</td>
									<td >${list.TITLE}</td>
								</tr>
								<tr>
									<td style="text-align:right;width:20%;">主题</td>
									<td >
										<c:if test="${not empty list.TITLEIMG }">
											<div ><img src="data:image/jpg;base64,${list.TITLEIMG}" style="width:80px;height:80px;border:1px solid black;" /></div>
										</c:if>
									</td>
								</tr>
								<tr>
									<td style="text-align:right;width:20%;">内容</td>
									<td >${list.CONTENT}</td>
								</tr>
								<tr>
									<td style="text-align:right;width:20%;">审核通过理由</td>
									<td ><textarea style="float:left; overflow:hidden;min-height: 80px; width:98%;" placeholder="请输入您的通过理由..." id="resultremark" 
										name="resultremark"maxlength="300" oninput="this.style.height='0px';this.style.height=(this.scrollHeight+'px');"></textarea></td>
								</tr>
								<tr>
									<td colspan="2" style="text-align:right;"><input type="button" class="btn btn-through"  onclick="aud('${list.ID}')" value="通过"/></td>
								</tr>
							</table>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 编辑框-->
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/selfupdata.js"></script>
</body>
</html>