<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>企业自主更新审核详情</title>
	<%@ include file="../system/allresources.jsp"%>
</head>

<body> 
<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div style="margin: 0 auto;">
				<div  id="queryEnterprise" class="form-horizontal" style="margin: 10px 0;">
					<div style="text-align: right;">
					<c:choose>
						<c:when test="${not empty selfUpdataInfo }">
							<span class="tabletitle">自主更新详情</span>
							<c:forEach items="${selfUpdataInfo}" var="list" varStatus="status">
								<div>
									<table id="table_bug_report" class="table table-striped table-bordered table-hover">
										<tr>
											<td style="text-align:right;width:60px;">主题</td>
											<td colspan="3"><img src="data:image/jpg;base64,${list.TITLEIMG }" style="width:80px;height:80px;border:1px solid black;" /></td>
										</tr>
										<tr>
											<td style="text-align:right;width:60px;">标题</td>
											<td colspan="3">${list.TITLE}</td>
										</tr>
										<tr>
											<td style="text-align:right;width:60px;">企业名称</td>
											<td colspan="3">${list.ENTNAME}</td>
										</tr>
										<tr>
											<td style="text-align:right;width:60px;">创建人</td>
											<td>${list.FOUNDNAME}</td>
											<td style="text-align:right;width:60px;">创建时间</td>
											<td><fmt:formatDate type="both" value="${list.CREATE_TIME }" /></td>
										</tr>
										<tr>
											<td style="text-align:right;">内容</td>
											<td colspan="3">${list.CONTENT}</td>
										</tr>
									</table>
								</div>
							</c:forEach>
						</c:when>
						</c:choose>	
						<c:choose>	
						<c:when test="${not empty respondedInfo }">
							<span class="tabletitle">审核记录</span>
							<c:forEach items="${respondedInfo}" var="respondedInfo" varStatus="status">
								<div style="margin-bottom: 10px;">
									<table class="table table-striped table-bordered table-hover">
										<tr>
											<td style="text-align:right;width:60px;">审核人</td>
											<td>${respondedInfo.RESULTNAME}</td>
											<td style="text-align:right;width:60px;">审核时间</td>
											<td>${respondedInfo.RESULTTIME}</td>
										</tr>
										<tr>
											<td style="text-align:right;">审核描述</td>
											<td colspan="3">${respondedInfo.RESULTREMARK}</td>
										</tr>
									</table>
								</div>
							</c:forEach>
						</c:when>
					</c:choose>		
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>