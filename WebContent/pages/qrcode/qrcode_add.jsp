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
</head>

<body>
	<table class="table table-striped table-bordered table-hover">
	 	<input type="hidden" id="applyWayCN" placeholder="申请方式" value="柜台申请" maxlength="128" readonly="true">
	 	<input type="hidden" id="pripid" placeholder="申请企业主体身份代码" value="${pd.pripid }" maxlength="64" readonly="true">
		<input type="hidden" id="regno" placeholder="申请企业注册号" value="${pd.regno }" maxlength="64" readonly="true">
		<input type="hidden" id="uniscid" placeholder="申请企业统一社会信用代码" value="${pd.uniscid }" maxlength="64" readonly="true">
 		<input type="hidden" id="entname" placeholder="申请企业名称" value="${pd.entname }" maxlength="30" readonly="true">
		<input type="hidden" id="province" placeholder="申请企业名称" value="${pd.province }" maxlength="30" readonly="true">
		<tr>
			<td>
				<label for="entname"><font color="red">*</font>企业名称：</label>
			</td>
			<td colspan="3">
			    <label style="width:90%">${pd.entname }</label>
			</td>
		</tr>	
		<tr>
			<td width="15%">
			 	<label for="applyTypeCN"><font color="red">*</font>申请类型：</label>
			</td>
			<td width="35%">
				<select id="applyTypeCN">
					<option value="0" selected="selected">申请挂牌</option>
					<option value="1">申请补办</option>	        	
	        	</select>
			</td>
			<td width="15%">
				<label for="applyNumber"><font color="red">*</font>申请数量：</label>
			</td>
			<td width="35%">
				<input type="text" value="1" id="applyNumber" onblur="applyNumCheck()" placeholder="申请数量" maxlength="3">
			</td>	
		</tr>
		<tr >
			<td>
				<label for="applyName"><font color="red">*</font>姓名：</label>
			</td>
			<td>
				<input type="text" id="applyName" placeholder="申请人名称" maxlength="20">
			</td>
			<td>
				<label for="position">职位：</label>
			</td>
			<td>
				<input type="text" id="position" placeholder="申请人职位" maxlength="10">
			</td>
		</tr>
		<tr >
			<td>
				<label for="cerType"><font color="red">*</font>证件类型：</label>
			</td>
			<td>
				<select id="cerType">
	        		<c:forEach items="${sysCodeList }" var="sys">
	        			<option value="${sys.EC_ID }" <c:if test="${sys.EC_ID == '968ee786b9124054b56ee7112d0a9b07' }">selected="selected"</c:if>>${sys.EC_NAME }</option>
	        		</c:forEach>	
	        	</select>
			</td>			
			<td>
				<label for="cerno"><font color="red">*</font>证件号码：</label>
			</td>
			<td>
	            <input type="text" id="cerno" placeholder="申请人证件号码" maxlength="25">
			</td>
		</tr>
		<tr >
			<td>
				<label for="email"><font color="red">*</font>电子邮箱：</label>
			</td>
			<td>
				<input type="text" id="email" placeholder="申请人电子邮箱" maxlength="30">
			</td>
			<td>
				<label for="tel"><font color="red">*</font>手机号码：</label>
			</td>
			<td>
				<input type="text" id="tel" placeholder="申请人手机号码" maxlength="11">
			</td>			
		</tr>
		<tr >
			<td>
				<label for="address">联系地址：</label>
			</td>
			<td>
				<textarea style="min-height:30px;width:90%;" placeholder="请输入联系地址" type="text" class="ub-f1" id="address" maxlength="300" oninput="this.style.height='0px';this.style.height=(this.scrollHeight+'px');"></textarea>
			</td>
			<td>
				<label for="remark">备注：</label>
			</td>
			<td>
				<textarea style="min-height:30px;width:90%;" placeholder="请输入备注" type="text" class="ub-f1" id="remark" maxlength="300" oninput="this.style.height='0px';this.style.height=(this.scrollHeight+'px');"></textarea>
			</td>		
		</tr>
		<tr >
			<td>
				<label for="applicationsDis"><font color="red">*</font>申请书：</label>
			</td>
			<td>
	        	<input type="radio" value="1" name="applicationsDis" checked="checked">&nbsp;有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        	<input type="radio" value="0" name="applicationsDis">&nbsp;没有
			</td>
			<td>
				<label for="sex">性别：</label>
			</td>
			<td>
	        	<input type="radio" value="1" name="sex" checked="checked">&nbsp;男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        	<input type="radio" value="0" name="sex" >&nbsp;女
			</td>			
		</tr>		
		<tr >
			<td>
				<label for="idcardCopyDis"><font color="red">*</font>身份证复印件：</label>
			</td>
			<td>
	        	<input type="radio" value="1" name="idcardCopyDis" checked="checked">&nbsp;有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        	<input type="radio" value="0" name="idcardCopyDis">&nbsp;没有
			</td>
			<td>
				<label for="businessLicenseDis"><font color="red">*</font>企业营业执照<br>（复印件）：</label>
			</td>
			<td>
	        	<input type="radio" value="1" name="businessLicenseDis" checked="checked">&nbsp;有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        	<input type="radio" value="0" name="businessLicenseDis">&nbsp;没有
			</td>			
		</tr>	
		<tr>
			<td colspan="4">
				<button class="btn btn-confirm" onclick="addguitai()" style="margin-right:20px;">柜台资料保存</button>
				<button class="btn btn-confirm" style="margin-right:20px;" onclick="add()">保存</button>
				<button class="btn btn-cancel" onclick="parent.layer.closeAll('iframe')">关闭</button>
			</td>		
		</tr>																															
	</table>
	<script src="${pageContext.request.contextPath}/static/scripts/myjs/qrcode.js"></script>
</body>
</html>
