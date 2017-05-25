
//邮箱正则
var emailRegex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;	
//手机正则
var phoneRegex = /^[1][358][0-9]{9}$/;	
//不准输入数字
var isChinese = /^[A-z]+$|^[\u4E00-\u9FA5]+$/;

var locat = (window.location+'').split('/'); 

$(function () {
	$(".content-left,.content-right").height($(window).height() - 80);
	if('main'== locat[3]){
		locat =  locat[0]+'//'+locat[2];
	}else{
		locat =  locat[0]+'//'+locat[2]+'/'+locat[3];
	};
})
$(".accordion-inner").click(function () {
	$(".active").html($(this).find(".left-body").text());
	$("#mainIframe").attr("src",$(this).attr("href"));
	$(".accordion-inner").removeClass("accordion-inner-enable");
	$(this).addClass("accordion-inner-enable")
})

$(window).resize(function () {
	$(".content-left,.content-right").height($(window).height() - 80);
})

//退出
function logout(){
	window.document.location.href=locat+'/user_Logout';
}
//跳转到修改用户信息页面
function openedt() {
	layer.open({
		type : 2,
		scrollbar : false,
		maxmin : true,//是否显示最大化按钮
		scrollbar: false, //父页面是否有滚动条
		title : '修改密码',
		shadeClose : true, //点击其他区域关闭弹窗
		shade : 0.8,
		area : [ '520px', '350px' ],
		content :locat+'/user_ToUpdate'
	});
}

//查询某个用户
function openquery(){
	layer.open({
		type: 2,
		title: '修改用户信息',
		scrollbar: false, //父页面是否有滚动条
		shadeClose: false,  //点击其他区域关闭弹窗
		shade: 0.5,  //笼罩层透明度
		maxmin: true,//是否显示最大化按钮
		area: ['600px', '60%'],  //大小
		content: locat+'/queryUserById'
	}); 
}

//编辑用户
function edt(userid){
	if (!isChinese.test($("#NAME").val())) {
		layer.tips('您输入的真实姓名格式不对，只能全部中文或者英文', '#NAME', {tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if (!emailRegex.test($("#EMAIL").val())) {
		layer.tips('您输入的邮箱格式不对', '#EMAIL', {tips:[2, '#78BA32'],tipsMore: true});
		return;
	}	
	if (!phoneRegex.test($("#PHONE").val())) {
		layer.tips('您输入的手机号格式不对', '#PHONE', {tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	
	//开启正在加载
	ShowLoading();

	//获取的参数
	var formdata = {
		USERID :userid,
		NAME: $("#NAME").val(),
		USERNAME: $("#USERNAME").val(),
		PASSWORD: $("#PASSWORD").val(),
		REMARK: $("#REMARK").val(),
		SEX: $("input[name='SEX']:checked").val(),
		EMAIL: $("#EMAIL").val(),
		PHONE: $("#PHONE").val(),
	}
	
	//访问请求
	$.ajax({
		url: locat+'/updateUserInfo',
		type: "post",
		data: formdata,
		success: function (data) {
			if(data == "success"){
				layer.alert("修改成功",{icon: 1,closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
				});
			}else{
				layer.alert("修改失败",{icon: 2,closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
				});
			}
		}
	})
}