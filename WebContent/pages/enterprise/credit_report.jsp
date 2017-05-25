<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>信用报告</title>
	<%@ include file="../system/allresources.jsp"%>
	<style type="text/css">
	.part_item {
		display: inline-block !important;
		*display: inline; /* IE6、7 block 元素 */
		*zoom: 1;
		border-radius: 1.0em;
		font-size: 0.75em;
		padding: 0.5em 1em !important;
		margin: 0.5em 0.3em;
		border: 1px solid #ccc;
		cursor: pointer;
	}
	
	.part_item_Choice {
		background: rgb(118, 240, 240);
	}
	
	.ulev-app2 {
		display: inline-block !important;
		*display: inline; /* IE6、7 block 元素 */
		*zoom: 1;
		padding: 0.5em 1em;
		border: 1px solid #ccc;
		border-radius: 0.5em;
		background-color: #00A8EA;
		margin-left: 0.5em;
		color: #fff;
		font-size: 0.9em;
		cursor: pointer;
	}
	
	.umar-a {
		padding: 0 0.75em;
	}
	
	.button {
		margin: 1em;
		display: inherit !important;
		text-align: center;
	}
	
	.div_split {
		width: 100%;
		height: 30px;
		background: #F7F3F3;
		line-height: 30px;
		min-height: 30px;
		font-size: 13px;
		text-indent: 1em;
	}
	
	.div-line {
		display: -webkit-box;
		line-height: 35px;
		padding: 10px;
	}
	
	.sendPdf {
		border-radius: 15px;
		background-color: #2679b5;
		width: 100px;
		float: right;
		height: 60px;
		line-height: 60px;
		margin: 25px;
	}
	</style>
</head>

<body class="um-vp bc-bg ub-ver" style="background-color: #fff !important;">
	<div id="content" class="ub-f1 tx-l">
		<div class="div_split" style="clear: left; color: #2679b5; font-size: 15px;">选择模块</div>
		<div class="uinn bc-border">
			<div class="ulev-app2" style="background-color: #2679b5; margin-left: 12px;">工商信息</div>
			<div class="ub umar-a">
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">股东信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">主要人员信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">变更记录信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">分支机构信息</div>
			</div>
		</div>
		<div class="uinn bc-border">
			<div class="ulev-app2" style="background-color: #2679b5; margin-left: 12px;">对外投资信息</div>
			<div class="ub umar-a">
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">企业对外投资信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">法人对外投资信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">主要人员对外投资信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">股东对外投资信息</div>
			</div>
		</div>
		<div class="uinn bc-border">
			<div class="ulev-app2" style="background-color: #2679b5; margin-left: 12px;">司法信息</div>
			<div class="ub umar-a">
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">司法文书信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">失信被执行人信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">股权冻结信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">股权变更信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">失信被执行人信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">法院判决文书信息</div>
			</div>
		</div>
		<div class="uinn bc-border">
			<div class="ulev-app2" style="background-color: #2679b5; margin-left: 12px;">风险信息</div>
			<div class="ub umar-a">
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">荣誉信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">经营异常信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">股权出质信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">动产抵押信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">行政处罚信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">行政许可信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">预警信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">广告资质信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">守合同重信用信息</div>
			</div>
		</div>
		<div class="uinn bc-border">
			<div class="ulev-app2" style="background-color: #2679b5; margin-left: 12px;">年报信息</div>
			<div class="ub umar-a">
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">年报信息</div>
			</div>
		</div>
		<div class="uinn bc-border">
			<div class="ulev-app2"
				style="background-color: #2679b5; margin-left: 12px;">知识产权信息</div>
			<div class="ub umar-a">
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">著作权信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">软件著作权信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">专利信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">商标信息</div>
			</div>
		</div>
		<div class="uinn bc-border">
			<div class="ulev-app2" style="background-color: #2679b5; margin-left: 12px;">经营信息</div>
			<div class="ub umar-a">
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">招投标信息</div>
				<div class="part_item" onclick="if(this.className=='part_item'){this.className='part_item part_item_Choice';}else{this.className='part_item'};">招聘信息</div>
			</div>
		</div>
		<div style="width: 100%;">
			<input style="float: left; width: 60px; background-color: #2679b5" id="all" class="ulev-app2 button" value="全选" onclick="allCheck();" type="button" /> 
			<input style="float: left; width: 60px; background-color: #2679b5" id="del" value="取消" class="ulev-app2 button" onclick="allCache();" type="button" />
		</div>
		<div class="div_split" style="clear: left; color: #2679b5; font-size: 15px;">收件人信息</div>
		<div style="width: 45%; float: left;">
			<div class="div-line">
				<div class="ulev-app1 umw4" style="margin-left: 12px; font-size: 15px;">接收邮箱：</div>
				<div class="ub ub-ac umh5 ub-f1">
					<div id="title" class="uinput ub ub-f1" style="color: #a9a9a9; font-size: 14px; margin-left: 12px;">
						<input type="text" id="email" name="email" placeholder="请输入接收的邮箱" style="height: 35px;" maxlength="32" />
					</div>
				</div>
			</div>
			<div class="div-line">
				<div class="ulev-app1 umw4" style="margin-left: 12px; font-size: 15px;">联系电话：</div>
				<div class="ub ub-ac umh5 ub-f1">
					<div class="uinput ub ub-f1" style="color: #a9a9a9; font-size: 14px; margin-left: 12px;">
						<input type="text" id="telphone" name="telphone" placeholder="请输入您的手机号码" style="height: 35px;" maxlength="11" />
					</div>
				</div>
			</div>
		</div>
		<div class="ulev-app2 button sendPdf" onclick="sendPdf()">发送</div>
		<div style="display: none;">
			<input type="text" id="pripid" name="pripid" value="${pd.pripid }">
			<input type="text" id="entname" name="entname" value="${pd.entname }">
		</div>
	</div>
</body>
<script type="text/javascript">
	//全选
	function allCheck() {
		$(".part_item").addClass("part_item_Choice");
		$('#all').css("background-color", "gray");
		$('#del').css("background-color", "#00A8EA");
	}
	
	//取消
	function allCache() {
		if ($(".part_item").hasClass('part_item_Choice')) {
			$(".part_item").removeClass("part_item_Choice");
		}
		$('#del').css("background-color", "gray");
		$('#all').css("background-color", "#00A8EA");
	}
	
	function sendPdf() {
		var emailRegex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;	//邮箱正则
		var phoneRegex = /^[1][34578][0-9]{9}$/;		//手机正则
		var str = "";	//检验
		
		$(".part_item_Choice").each(function(index) {
			str += $.trim($(this).html()) + ",";
		});
		
		str = str.substring(0, str.length - 1);
		
		if (isEmpty(str)) {
			layer.alert("您没有选择模块！", {icon : 0});
			return;
		}
		
		if (isEmpty($("#email").val())) {
			layer.tips('请输入接收邮箱！', '#email', {tips : [ 2, '#78BA32' ],tipsMore : true});
			return;
		}
		
		if (!emailRegex.test($("#email").val())) {
			layer.tips('您填写的邮箱格式不对！请核对后重新填写！', '#email', {tips : [ 2, '#78BA32' ],tipsMore : true});
			return;
		}
		
		if (isEmpty($("#telphone").val())) {
			layer.tips('请输入手机号码！', '#telphone', {tips : [ 2, '#78BA32' ],tipsMore : true});
			return;
		}
		
		if (!phoneRegex.test($("#telphone").val())) {
			layer.tips('手机号码格式不正确', '#telphone', {tips : [ 2, '#78BA32' ],tipsMore : true});
			return;
		}
		
		if (str.length > 0) {
			ShowLoading("正在生成信用报告中，请稍等...");
			$.ajax({
				url : '${pageContext.request.contextPath }/Interface/MakePdfInterface/makePdf.do',
				type : 'post',
				cache : false,
				data : {
					select : str,
					email : $("#email").val(),
					tel : $("#telphone").val(),
					memberId : '${sessionScope.user.id}',
					pripid : '${pd.pripid }',
					entname : '${pd.entname }',
					priptype : '${pd.priptype }'
				},
				success : function(text) {
					ShowLoading("正在发送信用报告中，请稍等...");
					$.ajax({
						url : '${pageContext.request.contextPath }/Interface/MakePdfInterface/sendPdfByEmail.do',
						type : 'post',
						cache : false,
						data : {
							fileName : text,
							sendTo : $("#email").val()
						},
						success : function(text) {
							if (text == "success") {
								ShowLoading("信用报告发送成功~", 3000, "success");
							} else {
								ShowLoading("信用报告发送失败~", 3000, "error");
							}
						},
					});
				},
			});
		}
	}
</script>
</body>
</html>
