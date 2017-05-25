<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>申请牌照</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
  	<%@ include file="../system/allresources.jsp" %>
  	<link href="${pageContext.request.contextPath}/static/styles/brand/brand.css" rel="stylesheet" />
  	<style type="text/css">
  		input[type=radio]{
  			vertical-align: top;
  		}
  	</style>
  <!-- 
window.onerror=function(){return true;} 
// --> 
</head>

<body>
	<table class="table table-striped table-bordered table-hover">
		<input type="hidden" id="aid" value="${applyCodeMap.ID }">
		<tr>
			<td>
				<label for="entname"><font color="red">*</font>企业名称：</label>
			</td>
			<td colspan="3">
 				<input type="text" id="entname" placeholder="申请企业名称" value="${applyCodeMap.ENTNAME }" maxlength="30" readonly="true" style="width:93%">
			</td>
		</tr>	
		<tr>
			<td width="15%">
			 	<label for="applyTypeCN"><font color="red">*</font>申请类型：</label>
			</td>
			<td width="35%">
				<select id="applyTypeCN">
					<option value="0" <c:if test="${applyCodeMap.APPLYTYPE == 0 }">selected="selected"</c:if>>申请挂牌</option>
					<option value="1" <c:if test="${applyCodeMap.APPLYTYPE == 1 }">selected="selected"</c:if>>申请补办</option>	        	
	        	</select>
			</td>
			<td width="15%">
				<label for="applyNumber"><font color="red">*</font>申请数量：</label>
			</td>
			<td width="35%">
				<input type="text" value="${applyCodeMap.APPLY_NUMBER }" onblur="applyNumCheck()" id="applyNumber" placeholder="申请数量" maxlength="3">
			</td>	
		</tr>
		<tr >
			<td>
				<label for="applyName"><font color="red">*</font>姓名：</label>
			</td>
			<td>
				<input type="text" value="<c:out value="${applyCodeMap.APPLY_NAME }" ></c:out>" id="applyName" placeholder="申请人名称" maxlength="20">
			</td>
			<td>
				<label for="position">职位：</label>
			</td>
			<td>
				<input type="text" value="<c:out value="${applyCodeMap.POSITION }" ></c:out>" id="position" placeholder="申请人职位" maxlength="15">
			</td>
		</tr>
		<tr >
			<td>
				<label for="cerType"><font color="red">*</font>证件类型：</label>
			</td>
			<td>
				<select id="cerType">
	        		<c:forEach items="${sysCodeList }" var="sys">
	        			<option value="${sys.EC_ID }" <c:if test="${sys.EC_ID == applyCodeMap.CERTYPE }">selected="selected"</c:if>>${sys.EC_NAME }</option>
	        		</c:forEach>	
	        	</select>
			</td>			
			<td>
				<label for="cerno"><font color="red">*</font>证件号码：</label>
			</td>
			<td>
	            <input type="text" value="${applyCodeMap.CERNO }" id="cerno" placeholder="申请人证件号码" maxlength="25">
			</td>
		</tr>
		<tr >
			<td>
				<label for="email"><font color="red">*</font>电子邮箱：</label>
			</td>
			<td>
				<input type="text" value="${applyCodeMap.EMAIL }" id="email" placeholder="申请人电子邮箱" maxlength="30">
			</td>
			<td>
				<label for="tel"><font color="red">*</font>手机号码：</label>
			</td>
			<td>
				<input type="text" value="${applyCodeMap.TEL }" id="tel" placeholder="申请人手机号码" maxlength="11">
			</td>			
		</tr>
		<tr >
			<td>
				<label for="address">联系地址：</label>
			</td>
			<td>
				<textarea style="min-height:30px;width:90%;" placeholder="请输入联系地址" type="text" class="ub-f1" id="address" maxlength="300"  oninput="this.style.height='0px';this.style.height=(this.scrollHeight+'px');"><c:out value="${applyCodeMap.ADDRESS }" ></c:out></textarea>
			</td>
			<td>
				<label for="remark">备注：</label>
			</td>
			<td>
				<textarea style="min-height:30px;width:90%;" placeholder="请输入申请备注" type="text" class="ub-f1" id="remark" maxlength="300" oninput="this.style.height='0px';this.style.height=(this.scrollHeight+'px');"><c:out value="${applyCodeMap.REMARK }" ></c:out></textarea>
			</td>		
		</tr>
		<tr >
			<td>
				<label for="applicationsDis"><font color="red">*</font>申请书：</label>
			</td>
			<td>
	        	<input type="radio" value="1" name="applicationsDis" <c:if test="${applyCodeMap.APPLICATIONS_DIS == 1 }">checked="checked"</c:if>>&nbsp;有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        	<input type="radio" value="0" name="applicationsDis" <c:if test="${applyCodeMap.APPLICATIONS_DIS == 0 }">checked="checked"</c:if>>&nbsp;没有
			</td>
			<td>
				<label for="sex">性别：</label>
			</td>
			<td>
	        	<input type="radio" value="1" name="sex" <c:if test="${applyCodeMap.SEX == 1 }">checked="checked"</c:if>>&nbsp;男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        	<input type="radio" value="0" name="sex" <c:if test="${applyCodeMap.SEX == 0 }">checked="checked"</c:if>>&nbsp;女
			</td>			
		</tr>		
		<tr >
			<td>
				<label for="idcardCopyDis"><font color="red">*</font>身份证复印件：</label>
			</td>
			<td>
	        	<input type="radio" value="1" name="idcardCopyDis" <c:if test="${applyCodeMap.IDCARD_COPY_DIS == 1 }">checked="checked"</c:if>>&nbsp;有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        	<input type="radio" value="0" name="idcardCopyDis" <c:if test="${applyCodeMap.IDCARD_COPY_DIS == 0 }">checked="checked"</c:if>>&nbsp;没有
			</td>
			<td>
				<label for="businessLicenseDis"><font color="red">*</font>企业营业执照复印件：</label>
			</td>
			<td>
	        	<input type="radio" value="1" name="businessLicenseDis" <c:if test="${applyCodeMap.BUSINESS_LICENSE_DIS == 1 }">checked="checked"</c:if>>&nbsp;有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        	<input type="radio" value="0" name="businessLicenseDis" <c:if test="${applyCodeMap.BUSINESS_LICENSE_DIS == 0 }">checked="checked"</c:if>>&nbsp;没有
			</td>			
		</tr>	
		<tr>
			<td colspan="4">
				 <div class="layui-layer-btn">
					<a class="btn btn-confirm" onclick="edit()">确定</a>
					<a class="btn btn-cancel" onclick="parent.layer.closeAll('iframe')">关闭</a>
				</div>
			</td>		
		</tr>																															
	</table>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcode.js"></script>
</body>
</html>
