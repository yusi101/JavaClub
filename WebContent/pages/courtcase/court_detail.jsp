<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>详情页面</title>

</head>
<body>
<%@ include file="../system/allresources.jsp"%>
	<div class="form-horizontal">
		<table id="table_bug_report" class="table table-striped table-bordered table-hover dialog">
			<tr>
				<td colspan="2"><b>失信被执行人</b></td>
			</tr>
			<tr>
				<td colspan="2"><b>详情摘要</b></td>
			</tr>
			<tr>
				<td><b>案号：</b></td>
				<td>${court.CASE_CODE }</td>
			</tr>
			<tr>
				<td><b>名称：</b></td>
				<td>${court.INAME }</td>
			</tr>
			<tr>
				<td><b>注册号： </b></td>
				<td>${court.REGNORE }</td>
			</tr>
			<tr>
				<td><b>法定代表人（负责人）姓名：</b></td>
				<td>${court.BUESINESSENTITY }</td>
			</tr>
			<tr>
				<td><b>失信被执行人具体情形：</b></td>
				<td>${court.DISREPUT_TYPE_NAME }</td>
			</tr>
			<tr>
				<td><b>执行依据文号：</b></td>
				<td>${court.GIST_CID }</td>
			</tr>
			<tr>
				<td><b>执行法院：</b></td>
				<td>${court.COURT_NAME }</td>
			</tr>
			<tr>
				<td><b>立案时间：</b></td>
				<td>${court.REG_DATE }</td>
			</tr>
			<tr>
				<td colspan="2"><b>被执行人的义务与行为的具体情形</b></td>
			</tr>
			
		</table>
	</div>
	<div>
		<table id="table_bug_report" class="table table-striped table-bordered table-hover dialog">
			<tr>
				<td><b>生效法律文书确定的义务：</b></td>
				<td>${court.DISREPUT_TYPE_NAME }</td>
			</tr>
		</table>
		<table id="table_bug_report" class="table table-striped table-bordered table-hover dialog">
			<tr>
				<td><b>立案时间失信被执行人行为具体情形</b></td>
			</tr>
			<tr>
				<td
					style="text-indent: 2em; white-space: normal; text-align: left; line-height: 30px; font-size: 18px;">
					${court.DUTY }
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
