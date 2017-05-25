<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>来访记录</title>
	<%@ include file="../system/allresources.jsp"%>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/jedate/skin/jedate.css">
</head>
<body> 
<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div style="margin: 0 auto;">
				<div  id="queryEnterprise" class="form-horizontal" style="margin: 10px 0;">
					<div style="text-align: right;">
						<div>
							<form action="${pageContext.request.contextPath}/visitorController/createVisitor" method="post" name="visitorInfo" id="visitorInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
								<c:forEach items="${visitorList}" var="list" varStatus="status">
									<table id="table_bug_report" class="table table-striped table-bordered table-hover">
										
										<tr>
											<td style="text-align:right;width:60px;">访客名称</td>
											<td><input type="text" id="visitorName" name="visitorName" style="width:50%;" maxlength="20" value="${list.VISITORNAME}"/></td>
											<td style="text-align:right;width:60px;">代表企业</td>
											<td><input type="text" id="entName" name="entName" style="width:50%;" maxlength="20" value="${list.ENTNAME }"/></td>
										</tr>
										<tr>
											<td style="text-align:right;width:60px;">接待人</td>
											<td><input type="text" id="receiverName" name="receiverName" style="width:50%;" maxlength="20" value="${list.RECEIVERNAME }"/></td>
											<td style="text-align:right;width:60px;">接待时间</td>
											<td><input type="text" id="receivrTime" name="receivrTime" style="width:50%;" maxlength="20" placeholder="请选择接待时间" readonly="readonly" value="<fmt:formatDate type="both" value="${list.RECEIVERTIME }" />"/></td>
										</tr>
										<tr>
											<td style="text-align:right;width:60px;">联系电话</td>
											<td colspan="3"><input type="text" id="tel" name="tel" style="width:50%;" maxlength="20" value="${list.TEL}"/></td>
										</tr>
										<tr>
											<td style="text-align:right;width:60px;">联系邮箱</td>
											<td colspan="3"><input type="text" id="email" name="email" style="width:50%;" maxlength="20" value="${list.EMAIL}"/></td>
										</tr>
										<tr>
											<td style="text-align:right;width:60px;">QQ</td>
											<td colspan="3"><input type="text" id="QQ" name="QQ" style="width:50%;" maxlength="20" value="${list.QQ}"/></td>
										</tr>
										<tr>
											<td colspan="4" style="text-align:right;"><input type="button" class="btn btn-confirm" onclick="upd('${list.ID}')" value="提交"/></td>
										</tr>
									</table>
								</c:forEach>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
		
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/visitor.js"></script>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/jedate/jedate.js"></script>
	<script type="text/javascript">
		jeDate({
			dateCell : "#receivrTime",
			format : "YYYY-MM-DD hh:mm:ss",
			isinitVal : false, //是否初始化时间
			isTime : true, //是否开启时间选择
			isClear : true, //是否显示清空
			festival : false, //是否显示节日
			maxDate : jeDate.now(0),
			minDate : "2014-09-19 00:00:00",
			okfun : function(val) {
			}
		})
	</script>	
</body>
</html>