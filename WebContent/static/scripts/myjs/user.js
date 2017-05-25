HideLoading();
var locat = (window.location + '').split('/');

$(function() {
	$(".content-left,.content-right").height($(window).height() - 80);
	if ('main' == locat[3]) {
		locat = locat[0] + '//' + locat[2];
	} else {
		locat = locat[0] + '//' + locat[2] + '/' + locat[3];
	}
	;
})
$(".accordion-inner").click(function() {
	$(".active").html($(this).find(".left-body").text());
	$("#mainIframe").attr("src", $(this).attr("href"));
	$(".accordion-inner").removeClass("accordion-inner-enable");
	$(this).addClass("accordion-inner-enable")
})

$(window).resize(function() {
	$(".content-left,.content-right").height($(window).height() - 80);
})

function update(ID) {
	var pwdReg = /^[a-zA-Z0-9]{6,18}$/;
	
	if ($("#oldPASSWORD").val() == "") {
		layer.tips('原密码不能为空', '#oldPASSWORD',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if (!pwdReg.test($("#oldPASSWORD").val())) {
		layer.tips('原密码的格式不对！只能输入6-18位英文加数字字符', '#oldPASSWORD', {tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if ($("#PASSWORD").val() == "") {
		layer.tips('新密码不能为空', '#PASSWORD',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if (!pwdReg.test($("#PASSWORD").val())) {
		layer.tips('新密码的格式不对！只能输入6-18位英文加数字字符', '#PASSWORD', {tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if ($("#PASSWORDS").val() == "") {
		layer.tips('确认密码不能为空', '#PASSWORDS',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if (!pwdReg.test($("#PASSWORDS").val())) {
		layer.tips('确认密码的格式不对！只能输入6-18位英文加数字字符', '#PASSWORDS', {tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if ($("#PASSWORDS").val() != $("#PASSWORD").val()) {
		layer.tips('两次密码不一致', '#PASSWORDS', {tips : [ 2, '#78BA32' ],tipsMore : true});
		return;
	}

	// 获取的参数
	var formdata = {
		flag : "edt",
		ID : ID,
		NAME : $("#NAME").val(),
		userName : $("#USERNAME").val(),
		oldPASSWORD : $("#oldPASSWORD").val(),
		PASSWORD : $("#PASSWORD").val(),
	}
	
	// 访问请求
	$.ajax({
		url : 'user_UpdateUser',
		timeout : 300000,
		dataType : "json",
		type : "post",
		data : formdata,
		success : function(message) {
			if (message.status == "success") {
				layer.alert("修改成功",{closeBtn: 0}, {
					icon : 1
				}, function(index) {
					parent.setTimeout("location.reload()", 100); // 父页面刷新
																	// 必须在关闭iframe之前
					parent.layer.closeAll('iframe');// 关闭弹窗
					ShowLoading();
				});
			}
		},
		error : function(error) {
			layer.alert("原密码错误", {icon : 2,closeBtn: 0}, null
			// function(index){
			// parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
			// parent.layer.closeAll('iframe');//关闭弹窗
			//				ShowLoading();
			//			}
			);
		}
	})
}
