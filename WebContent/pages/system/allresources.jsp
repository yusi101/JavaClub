<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="modo" uri="/WEB-INF/custom-tld/urltag.tld"%>

<!-- 添加头元素 -->
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"> 



<!-- 设置绝对位置 -->
<c:set var="ctp" value="${pageContext.request.contextPath }"/>

<!-- 引用css -->
<link href="${ctp}/static/plugins/bootstrap2.3.2/css/bootstrap.min.css" rel="stylesheet" />			<!-- bootstrap的css -->
<link href="${ctp}/static/styles/util/common.css" rel="stylesheet" rel="stylesheet" />
<link href="${ctp}/static/styles/util/imgscale.css" rel="stylesheet" rel="stylesheet" />
<link href="${ctp}/static/styles/util/skin.css" rel="stylesheet" rel="stylesheet" />
<link href="${ctp}/static/plugins/jedate/skin/jedate.css" rel="stylesheet" >						<!-- 时间插件css -->
<link href="http://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">	<!-- 文字图标css -->

<!-- 引用js -->
<script src="${ctp}/static/scripts/util/jquery-1.9.1.min.js"></script>				<!-- jquery插件 -->
<script src="${ctp}/static/scripts/util/util.js"></script>
<script src="${ctp}/static/scripts/util/imgscale.js"></script>
<script src="${ctp}/static/plugins/bootstrap2.3.2/js/bootstrap.min.js"></script>	<!-- bootstrap插件 -->
<script src="${ctp}/static/plugins/layer-v2.4/layer.js"></script>					<!-- layer弹层 -->
<script src="${ctp}/static/plugins/jedate/jedate.js"></script>						<!-- 时间插件 -->
<script src="${ctp}/static/scripts/util/jquery.placeholder.min.js"></script>		<!-- 输入框文字提示插件 -->
<script src="${ctp}/static/plugins/assets/js/jquery-form.js"></script>
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/static/plugins/assets/js/html5shiv.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/assets/js/respond.min.js"></script>
<![endif]-->

<!-- 内部js -->
<script type="text/javascript">
	//设置项目绝对路径
	var contextPath ="${ctp}";
	  
	$(function(){
		//兼容IE8textarea
	    $("textarea[maxlength]").on('keyup',function(){  
	        var area=$(this);  
	        var max=parseInt(area.attr("maxlength"),10); //获取maxlength的值  
	        if(max>0){  
	            if(area.val().length>max){ //textarea的文本长度大于maxlength  
	                area.val(area.val().substr(0,max)); //截断textarea的文本重新赋值  
	            }  
	        }  
		}) 
		
		//兼容IE8textarea
		$("textarea[maxlength]").on('blur',function(){  
		    //复制的字符处理问题  
		    var area=$(this);  
		    var max=parseInt(area.attr("maxlength"),10); //获取maxlength的值  
		    if(max>0){  
		        if(area.val().length>max){ //textarea的文本长度大于maxlength  
		            area.val(area.val().substr(0,max)); //截断textarea的文本重新赋值  
		        }  
		    }  
		}); 
		//设置所有图片点击放大
		$("img").each(function(){
			if($(this).attr("onclick")==null){
				$(this).click(function(){
					imgScale(this);
				});
			}
		});
		
		//ajax全局设置属性
		$.ajaxSetup({
	        type: "POST",
	        timeout : 60000, 
	        error: function(jqXHR, textStatus, errorThrown){  
	            switch (jqXHR.status){  
	                case(500):  
	                	Showtoast("服务器系统内部错误");  
	                    break;  
	                case(401):  
	                	Showtoast("未登录");  
	                    break;  
	                case(403):  
	                	Showtoast("无权限执行此操作");  
	                    break;  
	                case(408):  
	                	Showtoast("会话过期，退出到登录界面");
	                    break;  
	                default:  
	                	Showtoast("连接服务器失败");  
	            }  
	        }
    	});  
	});
	
    //页面加载完后自动调用关闭加载中
    $(document).ready(function(){ 
    	parent.layer.closeAll('dialog');
    }); 
    
	//弹窗提示
	function Showtoast(message, time, type) {
		HideLoading();
		$("#Myalert_Toast").remove();
		$("body").append('<div id="Myalert_Toast" style="color:black;">' + message+ '</div>');
		var Myalert = $("#Myalert_Toast");
		if (type == "success") {
			Myalert.css("background","#fff url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAACZklEQVRYR8WX33HiMBDGv5ULOKeCJBUkjHkP6YB0EN5DztcBHYTBeU+oIFwFIe9h4DpwOtAVYO2NDCbGlmRhuIEZHhgj7W+//WvCiT90YvvYCyD8fOiD6AaMayLqFfAMSDCvQFhBBFPZGa98HfMCCBfDe4CeCAh9LmYgBWMku5Np0/+dAOEyvoZSbwRcNF1kes6MFQIxcCliBVjLLV58vbYB5uER6k52nuem/xgBtHEi8dbGayuIEB2TEjWAjezvh3peBVkrkUOk5Wd1gMUwJdD5Mb3fVgvzXHaTWyuAznYCvfwP498Q6k52n2fF7x0FwiN7z+Cvqpq6RGU0uawB6NiTUstjec/gAUQwg8pWNQghLotc2CoQLh7HBPw8BoA2LqPk1RZSBv+SUTLWtkoAwzmBbg4FaDKu72fwh4ySvJWXAQ7Ofh/jVoCzxSNb2ukUgRhTls1B9MPe8dyyl8/pniCjydmOAlYA8KuMkkGepBYIX8+/pyd/ySjJ50s5BDpbr4wqOCD2Ne7KAWcSsgGijfEcgPFbdif9qgIxgZ5cVVCGgFJ9GU1GbbqnuQw9G1EBoUHbGM8VMDWizYVepaghAHy0mRvlHrATghxg+dAjJd4PbUbOMAp1W15OTOP4KB3R0lO2yWechmsV4pCyLHU1nTYKMfgPRNCTnbEsnzevZI6m08Y4mP9ykBuvrev2pTTfiLPZodvRxvN722bctJaHG4hWU5IZeo7EVdkbQ1CVWVcHFI18x7UuNQge2VbxvQGKA+EyvoDKemDqg1i/JenvOqmYUhDP9Rbk8rjqnNerWavE8zx0coB/nQdcMLn1F8YAAAAASUVORK5CYII=) no-repeat 5px").css("text-indent", "40px");
		} else if (type == "warn") {
			Myalert.css("background","#fff url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAACU0lEQVRYR82XQXLTQBBFf8dxtogbjNcUUnICnBNgTgBr5CqSEyQ5AaYqgi2cAHOCmBPEchXFMnMEZWtjNdWjSMjBkWdGKoIXXvVMv/nd6vlDcPgtPqoRGC/BUEwICHQoyxk8J0YGggZjGo71N9ttaVfgz09KrXKcMWNERMGueAPEnBFhul7i9OhUZ01rGgHSRJ2D8c428f1EArJHmDyP9cVDEFsBrt+rYO8AV6XENqduipES5Uscb1PjL4AfiTr8xbjyPfVDIKLGPuH4Wazn9ZgNAHPyPm66Tl4mFIh8hUFdiQrAS3bm1GxOFNmWScoRxfqojK8ApOEIdGa7EZjTcKzNZ7i4VHNHiIso1ueGXf7kU1uuce0mPX8PYz00AImaAfTCFr5eCgOwuFSfQfTadoMizh+gWM5fwrF+YwDSZJAR8ORfAogK0Vg/pWK80le35B0oYBqAX5Gf/N0AMPgDuTbQH6Va9sBdH4kCTp9QlwDmFl0kA3avfzclMG3w+ACPWAKZpv9FE3pMwcL1HPRgLhX3MX43S81n6D2I/Fp3Y5UMIv9R3A6AgdsovglaXEbmQnH2AxV2/TIqzAhppwuplR/Abb5kJc7I25DUnY3rNGXwpiEpZfHYyBhMJ/dcU65yRCWAVykcelEar5S+XLbdloNmTv1gASHJ98HDRlteV6LXx8zFaDYyMKfrFYZWD5P6RuZpBjrxVUNODfCkdMDbIHc+TqUven1MmGhkCyKJiXm6XuGk1eP0PrEZ2zlGICgwgqpEMpAIGQNzIszCt3pq0RYm5DfOzWpTYxRxLAAAAABJRU5ErkJggg==) no-repeat 5px").css("text-indent", "40px");
		} else if (type == "none") {
			Myalert.css("text-indent", "10px").css("background-color","#ECE7E7");
		}else {
			Myalert.css("background","#fff url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAACXUlEQVRYR8VXS3LaQBB9LUiFXaAqyhqfIGSRwC7mALaVEwTfID5ByAmSG9g5gSX7ABE7UVmYnCCwNamylnbZ1kuN+FiANIxkKGYnzee97n7d0yPY8ZAd4yMXgRunVb+PeCSCfUCqBOoCVgEZAAxJ+C8s8WpuMDQ1zIjA2GntkzgVoG54sA/BN9sN/HXrtQRunEb1gZVzQFlcZNAty91xzR2EWbszCVw77xtg6TyH1RkYDClR+437e5C2IJWAAheWrorYnL4nm8QKAeX2e1aunm/5MhWGZbnbWw7HCoHxYUsJ6OPmrE+eRNf2+p+SfxYIKLWD+LUd8OmpgnYyOxYJaKwn+UeIM1jyXUsw4gkFHRF5m7HOt72gPZubE1grvIgn9mX/x7+DVocWTlMVHeH49WVwNj5oftERLQv2ZsVqTmDdJgUoU4A0Erq5FbJTY+IzZ5Om4ksDygWuAEnPvug7hQgse0J9K7frQrPiAaJnXwRxdX3ywFEzBOSVaQbMrFbrc4HHAAxtr1/bIQGMbC+IL7anLDhsDjSps+CYZMw3FwLDCrhNEXYh+KrTwFbTUHU7D8TfTALbLkQK+FqjA4IDifDTqBRb+CyQRoYxcwEuiHCSTh8cWpbqgLY3dJeRQjWtiIUYJgrQymU0+zHpA18O8xQlQzKjstw21jYksRbilszyN0mC8vgurS/UNqUbIjGiPDq5mtKFcEQVt3CLRvTK1q1TqC1PxlVlRyTSNS3VIHqw0H32w2RZXHGxitBRDxWCVZHJS4nEUCAhSJelyM9yd5pYjZ5mhiovtGznBP4DcARrMJRZd1wAAAAASUVORK5CYII=) no-repeat 5px").css("text-indent", "40px");
		}
		Myalert.css("height", "40px").css("background-size", "24px").css("margin", "20px auto").css("border-radius", "10px")
		.css("line-height", "40px").css("position", "fixed").css("left", "50%").css("bottom", "10px")
		.css("margin-top", "0px").css("border", "1px solid #ccc").css("font-size", "13px").css("background-position-x","12px").css("padding-right", "10px").css("display","inline-table");
		Myalert.css("margin-left", "-" + (Myalert.width() / 2) + "px");
		if (time != null) {
			setTimeout(function() {
				Myalert.remove();
			}, time);
		}
	}
	
	//关闭正在加载
	function HideLoading() {
		/* $("#MyShowLoading").remove(); */
		layer.closeAll('dialog');
	}
	
	//显示正在加载
	function ShowLoading(message, time, type) {
		if (type == "success") {
			type=1;
		}else if(type == "warn"){
			type=0;
		}else if(type == "error"){
			type=2;
		}else{
			type=16;
		}
		
		if (time == null) {
			time= false;
		}
		
		if (message == null) {
			message= "正在加载中...";
		}
		
		layer.msg(message, {
			 shade: [0.3, '#65cea7'],icon: type,
			  time: time //2秒关闭（如果不配置，默认是3秒）
		});   
	}
</script>