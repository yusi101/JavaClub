var locat = (window.location+'').split('/');
var emailRegex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;	
//手机正则
var phoneRegex = /^[1][358][0-9]{9}$/;	
//不准输入数字
var isChinese = /^\d*([\u4e00-\u9fa5]|[a-zA-Z])+\d*$/;
(function() {
    "use strict";
    reinitIframe();
    // custom scrollbar
    $.sidebarMenu($('.sidebar-menu'))

    //判断是否是IE浏览器
	if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){
		//alert('你是使用IE');
	}else{
		$("#iframeContent").niceScroll({styler:"fb",cursorcolor:"#65cea7", cursorwidth: '6', cursorborderradius: '0px', background: '#424f63', spacebarenabled:false, cursorborder: '0',  zindex: '1000'});
	}
   
    $(".left-side").niceScroll({styler:"fb",cursorcolor:"#65cea7", cursorwidth: '3', cursorborderradius: '0px', background: '#424f63', spacebarenabled:false, cursorborder: '0'});

    $(".left-side").getNiceScroll();
    if ($('body').hasClass('left-side-collapsed')) {
        $(".left-side").getNiceScroll().hide();
    }

   // 菜单的隐藏和显示
   jQuery('.toggle-btn').click(function(){
       $(".left-side").getNiceScroll().hide();
       
       if ($('body').hasClass('left-side-collapsed')) {
           $(".left-side").getNiceScroll().hide();
       }
      var body = jQuery('body');
      var bodyposition = body.css('position');

      if(bodyposition != 'relative') {
         if(!body.hasClass('left-side-collapsed')) {
            body.addClass('left-side-collapsed');
         } else {
            body.removeClass('left-side-collapsed');
         }
      }
      reinitIframe();
   });

   //  class add mouse hover
   jQuery('.sidebar-menu > li').hover(function(){
      jQuery(this).addClass('nav-hover');
   }, function(){
      jQuery(this).removeClass('nav-hover');
   });
   
   
   jQuery(window).resize(function(){

      if(jQuery('body').css('position') == 'relative') {
         jQuery('body').removeClass('left-side-collapsed');
      } else {
         jQuery('body').css({left: '', marginRight: ''});
      }
      reinitIframe();
   });


    $(".sidebar-menu li a").each(function(){
		if($(this).attr("href")!="javascript:void(0);"){
			$(this).click(function(){
				ShowLoading();
				$("#iframeContent").attr("src",$(this).attr("href"));
				$(".breadcrumb .fa-angle-double-right").show();
				$(".breadcrumb").find(".active").html($(this).text());
				$(".sidebar-menu li").removeClass("isclick");
				$(this).parent().addClass("isclick");
				return false;
			});
		}
	});
    if('main'== locat[3]){
		locat =  locat[0]+'//'+locat[2];
	}else{
		locat =  locat[0]+'//'+locat[2]+'/'+locat[3];
	};
})(jQuery);
//退出
 function getBasePath(){
	var basePath = (window.location+'').split('/');
	if('main'== basePath[3]){
		basePath =  basePath[0]+'//'+basePath[2];
		}else{
			basePath =  basePath[0]+'//'+basePath[2]+'/'+basePath[3];
		};
	return basePath;
}
function logout(){
	var basePath = getBasePath();
	window.document.location.href=basePath+'/user_Logout';
}
//跳转到修改用户信息页面
function openedt() {
	var basePath = getBasePath();
	layer.open({
		type : 2,
		scrollbar : false,
		maxmin : true,//是否显示最大化按钮
		scrollbar: false, //父页面是否有滚动条
		title : '修改密码',
		shadeClose : true, //点击其他区域关闭弹窗
		shade : 0.8,
		area : [ '520px', '350px' ],
		content :basePath+'/user_ToUpdate'
	});
}
//查询某个用户
function openquery(){
	var basePath = getBasePath();
	layer.open({
		type: 2,
		title: '修改用户信息',
		scrollbar: false, //父页面是否有滚动条
		shadeClose: false,  //点击其他区域关闭弹窗
		shade: 0.5,  //笼罩层透明度
		maxmin: true,//是否显示最大化按钮
		area: ['600px', '60%'],  //大小
		content: basePath+'/queryUserById'
	}); 
}

//编辑用户
function edt(userid){
	var basePath = getBasePath();
	if (!isChinese.test($("#USERNAME").val())) {
		layer.tips('输入有误', '#USERNAME');
		return;
	}
	if (!isChinese.test($("#NAME").val())) {
		layer.tips('输入有误', '#NAME');
		return;
	}
	if (!emailRegex.test($("#EMAIL").val())) {
		layer.tips('邮箱输入有误！', '#EMAIL');
		return;
	}	
	if (!phoneRegex.test($("#PHONE").val())) {
		layer.tips('手机号输入有误！', '#PHONE');
		return;
	}
	//正在加载
	var index = layer.load(2); 

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
		url: basePath+'/updateUserInfo',
		type: "post",
		data: formdata,
		success: function (data) {
			if(data == "success"){
				layer.alert("修改成功",{icon: 1},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();
				});
			}else{
				layer.alert("修改失败",{icon: 2},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();
				});
			}
		}
	})
}

function reinitIframe(){ 
	try{ 
		var contentHeight=$(".main-content").height()-$("footer").height();
		$(".main-content").css("height",($(".sticky-left-side").height())+"px");
	    $("#iframeContent").css("height",($(".main-content").height()-$("footer").height()-7)+"px");
	    $("#iframeContent").css("width",$(".main-content").width());
	}catch (ex){}  
}  
