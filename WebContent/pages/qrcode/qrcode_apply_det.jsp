<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>牌照打印</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
  	<%@ include file="../system/allresources.jsp" %>
  	<link href="${pageContext.request.contextPath}/static/styles/util/imgscale.css" rel="stylesheet" />
  	<style type="text/css">
  		.flow_img{
  			width: 12px; 
  			height: 12px; 
  			vertical-align: text-top;
  		}
  		.flow{
  			text-align: left !important; 
  			color:#FFA500
  		}
  	</style>
</head>

<body>
	<table class="table table-striped table-bordered table-hover">
	 	<!-- <input type="hidden" id="applyWayCN" placeholder="申请方式" value="柜台申请" maxlength="128" readonly="true"> -->
	 	<tr>
	 		<td colspan="4" class="flow">
	 			<img src="${pageContext.request.contextPath}/static/images/flowicon.png" class="flow_img">&nbsp;牌照申请
	 		</td>
	 	</tr>
		<tr>
			<td width="22%">牌照申请企业名称：</td>
			<td width="78%" colspan="3">${applyList[0].ENTNAME }</td>
		</tr>
		<tr>
			<td>牌照申请数量：</td>
			<td colspan="3">${applyList[0].APPLY_NUMBER }</td>
		</tr>	
		<tr>
			<td width="22%">牌照申请方式：</td>
			<td width="23%">${applyList[0].APPLYWAY_CN }</td>
			<td width="22%">牌照申请类型：</td>
			<td width="23%">${applyList[0].APPLYTYPE_CN }</td>	
		</tr>		
		<tr >
			<td>牌照申请人姓名：</td>
			<td><c:out value="${applyList[0].APPLY_NAME }" ></c:out></td>
			<td>牌照申请人职位：</td>
			<td><c:out value="${applyList[0].POSITION }" ></c:out></td>
		</tr>
		<tr >
			<td>牌照申请人证件类型：</td>
			<td>${applyList[0].CERTYPE_CN }</td>			
			<td>牌照申请人证件号码：</td>
			<td><c:out value="${applyList[0].CERNO }" ></c:out></td>
		</tr>
		<tr>
			<td>牌照申请人联系邮箱：</td>
			<td>${applyList[0].EMAIL }</td>
			<td>牌照申请人联系号码：</td>
			<td>${applyList[0].TEL }</td>
		</tr>
		<tr >
			<td>牌照申请人联系地址：</td>
			<td colspan="3"><c:out value="${applyList[0].ADDRESS }" ></c:out></td>
		</tr>
		<tr>
			<td>牌照申请人备注：</td>
			<td colspan="3"><c:out value="${applyList[0].REMARK }" ></c:out></td>			
		</tr>
		<tr>
			<td>牌照申请人性别：</td>
			<td>
				<c:if test="${applyList[0].SEX == 0 }">女</c:if>
				<c:if test="${applyList[0].SEX == 1 }">男</c:if>
			</td>				
			<td>牌照申请人申请书：</td>
			<td>
				<c:if test="${applyList[0].APPLICATIONS_DIS == 0 }">没有</c:if>
				<c:if test="${applyList[0].APPLICATIONS_DIS == 1 }">有</c:if>
			</td>
		</tr>		
		<tr>
			<td>牌照申请人身份证复印件：</td>
			<td>
				<c:if test="${applyList[0].IDCARD_COPY_DIS == 0 }">没有</c:if>
				<c:if test="${applyList[0].IDCARD_COPY_DIS == 1 }">有</c:if>
			</td>
			<td>牌照申请人企业营业执照：</td>
			<td>
				<c:if test="${applyList[0].BUSINESS_LICENSE_DIS == 0 }">没有</c:if>
				<c:if test="${applyList[0].BUSINESS_LICENSE_DIS == 1 }">有</c:if>
			</td>			
		</tr>	
		<tr>
			<td>牌照申请人时间：</td>
			<td colspan="3"><fmt:formatDate type="both" value="${applyList[0].CREATETIME }" /></td>		
		</tr>
		<tr>
			<td>牌照状态：</td>
			<td colspan="3">
				<c:if test="${applyList[0].STATUS == 0 }">
					申请未处理
				</c:if>
				<c:if test="${applyList[0].STATUS == 1 }">
					<c:if test="${printList[0].STATUS == 0 }">打印未审核</c:if>
					<c:if test="${printList[0].STATUS == 1 }">打印审核已通过</c:if>
					<c:if test="${printList[0].STATUS == 2 }">打印审核未通过</c:if>
					<c:if test="${printList[0].STATUS == 3 }">牌照送去制作</c:if>
					<c:if test="${printList[0].STATUS == 4 }">牌照已入库</c:if>
					<c:if test="${printList[0].STATUS == 5 }">牌照制作入库审核不通过</c:if>
					<c:if test="${printList[0].STATUS == 6 }">牌照领取通知已发送</c:if>
					<c:if test="${printList[0].STATUS == 7 }">牌照已领取</c:if>
				</c:if>
				<c:if test="${applyList[0].STATUS == 2 }">
					申请审核不通过
				</c:if>
			</td>		
		</tr>
		<c:if test="${applyList[0].STATUS == 1 }">
			<tr>
				<td>牌照编码：</td>
				<td colspan="3">${licenseRecordList[0].LICENSENUMBER }</td>		
			</tr>	
			<tr>
				<td>牌照：</td>
				<td colspan="3">
					<img src="data:image/jpg;base64,${printList[0].LICENSECODE }" onclick="imgScale(this)" width="100px" height="160px">
				</td>		
			</tr>	
	 		<tr>
				<td>牌照申请处理人：</td>
				<td>${printList[0].CREATEUSER_NAME }</td>
				<td>牌照申请处理时间：</td>
				<td><fmt:formatDate type="both" value="${printList[0].CREATETIME }" /></td>			
			</tr>
		</c:if>
		<c:if test="${!empty stateauditList[0] }">
			<tr>
		 		<td colspan="4" class="flow">
		 			<img src="${pageContext.request.contextPath}/static/images/flowicon.png" class="flow_img">&nbsp;牌照审核
		 		</td>
		 	</tr>	
			<tr>
				<td>牌照审核处理人：</td>
				<td>${stateauditList[0].APPROVEUSER_NAME }</td>
				<td>牌照审核处理时间：</td>
				<td><fmt:formatDate type="both" value="${stateauditList[0].APPROVE_TIME }" /></td>			
			</tr>	
		</c:if>			
		<c:if test="${!empty abnormalList }">	
			<tr>
		 		<td colspan="4" class="flow">
		 			<img src="${pageContext.request.contextPath}/static/images/flowicon.png" class="flow_img">&nbsp;牌照异常
		 		</td>
		 	</tr>	
			<c:forEach items="${abnormalList }" var="abnormalList">
				<tr >
					<td>异常牌照：</td>
					<td colspan="3"><img src="data:image/jpg;base64,${abnormalList.ABNORMAL_CODE }" onclick="imgScale(this)" width="60px" height="100px"></td>		
				</tr>
				<tr >
					<td>牌照异常处理人：</td>
					<td>${abnormalList.APPROVEUSER_NAME }</td>
					<td>牌照异常处理时间：</td>
					<td><fmt:formatDate type="both" value="${abnormalList.APPROVE_TIME }" /></td>			
				</tr>
				<tr >
					<td>牌照异常修复人：</td>
					<td>${abnormalList.REPAIRUSER_NAME }</td>
					<td>牌照异常修复时间：</td>
					<td><fmt:formatDate type="both" value="${abnormalList.REPAIR_TIME }" /></td>			
				</tr>	
				<tr >
					<td>牌照异常修复描述：</td>
					<td colspan="3"><c:out value="${abnormalList.REPAIR_REMARK }" ></c:out></td>		
				</tr>
			</c:forEach>
		</c:if>	
		<c:if test="${!empty makeList[0] }">
			<tr>
		 		<td colspan="4" class="flow">
		 			<img src="${pageContext.request.contextPath}/static/images/flowicon.png" class="flow_img">&nbsp;牌照制作
		 		</td>
		 	</tr>		
			<tr >
				<td>牌照制作批次号：</td>
				<td colspan="3">${makeList[0].AREACODE }${makeList[0].BATCHNUMBER }</td>		
			</tr>					
			<tr >
				<td>牌照制作处理人：</td>
				<td>${makeList[0].APPROVEUSER_NAME }</td>
				<td>牌照制作处理时间：</td>
				<td><fmt:formatDate type="both" value="${makeList[0].APPROVE_TIME }" /></td>			
			</tr>
		</c:if>
		<c:if test="${!empty storageList[0] }">
			<tr>
		 		<td colspan="4" class="flow">
		 			<img src="${pageContext.request.contextPath}/static/images/flowicon.png" class="flow_img">&nbsp;牌照入库
		 		</td>
		 	</tr>
			<tr >
				<td>入库的牌照是否正常：</td>
				<td colspan="3">
					<c:if test="${storageList[0].STATUS == 0 }">正常</c:if>
					<c:if test="${storageList[0].STATUS == 1 }">不正常</c:if>
				</td>		
			</tr>		
			<tr >
				<td>牌照入库处理人：</td>
				<td>${storageList[0].APPROVEUSER_NAME }</td>
				<td>牌照入库处理时间：</td>
				<td><fmt:formatDate type="both" value="${storageList[0].APPROVE_TIME }" /></td>			
			</tr>		
		</c:if>
		<c:if test="${!empty noticeList }">	
			<tr>
		 		<td colspan="4" class="flow">
		 			<img src="${pageContext.request.contextPath}/static/images/flowicon.png" class="flow_img">&nbsp;牌照领取通知
		 		</td>
		 	</tr>
			<c:forEach items="${noticeList }" var="noticeList" varStatus="status">
				<tr >
					<td>第${status.index + 1 }次牌照领取通知处理人：</td>
					<td>${noticeList.SENDUSER_NAME }</td>
					<td>第${status.index + 1 }次牌照领取通知处理时间：</td>
					<td><fmt:formatDate type="both" value="${noticeList.SEND_TIME }" /></td>			
				</tr>
			</c:forEach>
		</c:if>		
		<c:if test="${!empty recordList[0] }">
			<tr>
		 		<td colspan="4" class="flow">
		 			<img src="${pageContext.request.contextPath}/static/images/flowicon.png" class="flow_img">&nbsp;牌照领取
		 		</td>
		 	</tr>
			<tr >
				<td>牌照领取处理人：</td>
				<td>${recordList[0].APPROVEUSER_NAME }</td>
				<td>牌照领取处理时间：</td>
				<td><fmt:formatDate type="both" value="${recordList[0].RECEIVE_TIME }" /></td>			
			</tr>	
		</c:if>																
		<tr>
			<td colspan="4">
				 <div class="layui-layer-btn">
					<a class="btn btn-cancel" onclick="parent.layer.closeAll('iframe')">关闭</a>
				</div>
			</td>		
		</tr>																															
	</table>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcode.js"></script>
	<script src="${pageContext.request.contextPath}/static/scripts/util/imgscale.js"></script>
</body>
</html>
