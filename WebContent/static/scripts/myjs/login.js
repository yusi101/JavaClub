/**
 * 
 */

$(function(){
	changeCode();
	document.getElementById("username").focus();
});
$(document).keyup(function(event) {
	if (event.keyCode == 13) {
		tologin();
	}
});
function reset(){
	$("#username").val("");
	$("#password").val("");
	$("#code").val("");
	changeCode();
}
function changeCode() {
	 $("#codeImg").attr("src", "code.do?t=" + genTimestamp());
}
function genTimestamp() {
	var time = new Date();
	return time.getTime();
}
function tologin(){
	if(check()){
		ShowLoading("正在登陆中，请稍后...");
		$.ajax({
			type : "POST",
			url:"user_Login",
			data : {
				userName : $("#username").val() ,
				password : $("#password").val(),
				code : $("#code").val()
			},
			success : function(result){
				HideLoading();
				if("success" == result){
					ShowLoading("正在进行系统中，请稍后...");
					window.location.href="main/index";
				}else if("usererror" == result){
					layer.tips('用户名或密码有误', '#username',{tips:[2, 'rgb(189, 27, 72)']});
					changeCode();
					$("#username").focus();
				}else if("codeerror" == result){
					layer.tips('验证码输入有误', '#codeImg',{tips:[2, 'rgb(189, 27, 72)']});
					changeCode();
					$("#code").focus();
				}else{
					layer.tips('系统异常', '#username',{tips:[2, 'rgb(189, 27, 72)']});
					changeCode();
					$("#username").focus();
				}
			}
			
		});
	}
}
//客户端校验
function check() {

	if ($("#username").val() == "") {
		layer.tips('用户名不能为空', '#username',{tips:[2, 'rgb(189, 27, 72)']});
		$("#username").focus();
		return false;
	}

	if ($("#password").val() == "") {
		layer.tips('密码不得为空', '#password',{tips:[2, 'rgb(189, 27, 72)']});
		$("#password").focus();
		return false;
	}
	if ($("#code").val() == "") {
		layer.tips('验证码不得为空', '#codeImg',{tips:[2, 'rgb(189, 27, 72)']});
		$("#code").focus();
		return false;
	}
	return true;
}