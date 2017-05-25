<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>短信模板</title>
<body>
	<%@ include file="../system/allresources.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">短信模板</h4>
		<%-- 	<form action="${pageContext.request.contextPath}/knowledgeController/queryKnowInfo" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div class="clren"></div>
			</form> --%>
			<div style="margin: 10px 0;height: 30px">
				<div style="width: 60%; float: left;">
					<a class="btn btn-add" onclick="openadd();"><i class="fa fa-plus"></i>&nbsp;新增</a>
				</div>
			</div>
			<table id="table_bug_report" class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th class="center" width="20%">短信标题</th>
						<th class="center" width="22%">短信内容</th>
						<th class="center" width="15%">保存时间</th>
						<th class="center" width="10%">状态</th>
						<th class="center" width="10%">记录人</th>
						<th class="center" width="18%">操作</th>
					</tr>
				</thead>
				<tbody>
					
					<c:choose>
						<c:when test="${not empty templateList }">
							<c:forEach items="${templateList }" var="list" varStatus="status">
								<tr>
									<td class="center" width="20%">${list.SMS_TITLE }</td>
									<td class="center" width="22%">${list.SMS_CTX }</td>
									<td class="center" width="15%">${list.TIME }</td>
									<c:if test="${list.ACTIVE =='0' }"><td class="center" width="10%">未激活</td></c:if>
									<c:if test="${list.ACTIVE =='1' }"><td class="center" width="10%"><font color="#FF0000">已激活</font></td></c:if>
									<td class="center" width="10%">${list.NAME }</td>
									<td class="dropdown" width="18%">
										<button class="btn btn-edit" onclick="openupd('${list.ID }')"><i class="fa fa-edit"></i>&nbsp;编辑</button>
										<button class="btn btn-del" onclick="del('${list.ID }','${list.ACTIVE }')" ><i class='fa fa-trash'></i>&nbsp;删除</button>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<tr>
								<td colspan="7" style="color: red; text-align: center;">
									<div align="center">
										<img src="${pageContext.request.contextPath}/static/images/nodata.png" style="width: 100px; margin: 10px auto;">
									</div>
									<div style="margin-bottom: 10px;">小查没有查到数据哦~</div>
								</td>
								</tr>
							</tr>
						</c:otherwise>
					</c:choose> 

				</tbody>
			</table>
			<%-- <!-- 分页 -->
			<div class="position-relative">
				<table style="width: 100%;">
					<tr>
						<td style="vertical-align: top;">
							<div class="pagination" style="float: left; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
						</td>
					</tr>
				</table>
			</div> --%>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/myjs/smstemplate.js"></script>
</head>
</body>
</html>