HideLoading();
function querySelfUpdataInfo(){
	ShowLoading();
	$("#selfUpdataInfo").submit();
}

function count() {
    $('#stats').html("您还可以输入 " + ($('#toUrl').attr("maxlength") - $('#toUrl').val().length) + "个字");
  }
//打开添加图片页面
function openadd(){
	layer.open({
	  	type: 2,
	  	title: '添加图片',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['450px', '60%'],  //大小
		content: contextPath + '/pages/carousel/carousel_add.jsp'
	});  
}


//打开图片编辑页面
function openedit(ID){
	layer.open({
	  	type: 2,
	  	title: '编辑',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['600px', '70%'],  //大小
		content: contextPath + '/carouselController/toUpdateCarousel?id='+ID
	});  
}

//验证网址
function IsURL(str) {
    var Expression="^((https|http|ftp|rtsp|mms)?://)"      
        + "?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?" //ftp的user@     
        + "(([0-9]{1,3}\\.){3}[0-9]{1,3}"                                 // IP形式的URL- 199.194.52.184     
        + "|"                                                         // 允许IP和DOMAIN（域名）     
        + "([0-9a-zA-Z_!~*'()-]+\\.)*"                                 // 域名- www.     
        + "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\\."                     // 二级域名     
        + "[a-zA-Z]{2,6})"                                         // first level domain- .com or .museum     
        + "(:[0-9]{1,4})?"                                                     // 端口- :80     
        + "((/?)|"      
        + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$"; 
    var objExp=new RegExp(Expression);
    if(objExp.test(str)==false){
        return "no";
    }else{
    	return "yes"
    }
}

//添加轮播图
function addcarousel() {
	//检验
	if (isEmpty($("#name").val())) {
        layer.tips('名称不能为空', '#name',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
	if (isEmpty($("#orderBy").val())) {
        layer.tips('输入序号不能为空', '#orderBy',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    } 
	if ($("#orderBy").val().length > $("#orderBy").attr("maxlength")) {
        layer.tips('排序号超长', '#orderBy',{tips:[3, '#78BA32'],tipsMore: true});
        return;
	}
	if (isEmpty($("#toUrl").val())) {
        layer.tips('链接地址不能为空', '#toUrl',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }else if(IsURL($("#toUrl").val()) == "no"){
    	 layer.tips('链接地址格式不正确', '#toUrl',{tips:[3, '#78BA32'],tipsMore: true});
         return;
    }
    if ($("#uploadImgList").find(".uploadify-queue-item").length <= 0) {
        layer.tips('请上传图片', '#uploadImg',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    ShowLoading("正在提交请求中...");
    //访问请求
    var src = $("#uploadImgList").find(".uploadify-queue-item").find("img").eq(0).attr("src");
    var path = src.substr(src.indexOf(",")+1);
    $.ajax({
        url: contextPath + "/carouselController/createCarousel",
        type: "post",
        data: {
    		name: $("#name").val(),
    		orderBy: $("#orderBy").val(),
    		toUrl: $("#toUrl").val(),
    		path : path
        },
        dataType: "json",
        success: function (result) {
        	HideLoading();
        	if(result.status == "success"){
        		layer.alert('添加成功',{icon: 1,closeBtn: 0},function(index){
        			//父页面刷新
                	parent.setTimeout("location.reload()",100);
                   //关闭加载
                	parent.layer.closeAll('iframe');//关闭弹窗 
        		}
        		);
        	}else{
        		layer.alert('添加失败',{icon: 2,closeBtn: 0});
        		parent.setTimeout("location.reload()",100);
                //关闭加载
             	parent.layer.closeAll('iframe');//关闭弹窗 
        	}
        		
        }
    })
}


//编辑轮播图
function updatecarousel(ID) {
	//检验
	if (isEmpty($("#name").val())) {
        layer.tips('名称不能为空', '#name',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
	if (isEmpty($("#orderBy").val())) {
        layer.tips('输入序号不能为空', '#orderBy',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    } 
	if ($("#orderBy").val().length > $("#orderBy").attr("maxlength")) {
        layer.tips('排序号超长', '#orderBy',{tips:[3, '#78BA32'],tipsMore: true});
        return;
	}
	if (isEmpty($("#toUrl").val())) {
        layer.tips('链接地址不能为空', '#toUrl',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }else if(IsURL($("#toUrl").val()) == "no"){
    	 layer.tips('链接地址格式不正确', '#toUrl',{tips:[3, '#78BA32'],tipsMore: true});
         return;
    }
    if ($("#uploadImgList").find(".uploadify-queue-item").length <= 0) {
        layer.tips('请上传图片', '#uploadImg',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    ShowLoading("正在提交请求中...");
    //访问请求
    $.ajax({
        url: contextPath + "/carouselController/updateCarousel",
        type: "post",
        data: {
        	id: $("#id").val(),
    		name: $("#name").val(),
    		orderBy: $("#orderBy").val(),
    		toUrl: $("#toUrl").val(),
    		path : $("#uploadImgList").find(".uploadify-queue-item").find("img").eq(0).attr("src").replace("data:image/png;base64,","")
        },
        dataType: "json",
        success: function (result) {
        	HideLoading();
        	if(result.status == "success"){
        		layer.alert('修改成功',{icon: 1,closeBtn: 0},function(index){
        			//父页面刷新
                	parent.setTimeout("location.reload()",100);
                   //关闭加载
                	parent.layer.closeAll('iframe');//关闭弹窗 
        		}
        		);
        	}else{
        		layer.alert('修改失败',{icon: 2,closeBtn: 0});
        		parent.setTimeout("location.reload()",100);
                //关闭加载
             	parent.layer.closeAll('iframe');//关闭弹窗 
        	}
        		
        }
    })
}


//编辑轮播图
function deletecarousel(ID) {
	layer.confirm('确定删除？', {icon: 3, title:'提示'}, function(index){
	    //访问请求
		ShowLoading("正在提交请求中...");
		$.ajax({
	        url: contextPath + "/carouselController/deleteCarousel",
	        type: "post",
	        data: {
		    	id:ID
		    },
	        dataType: "json",
	        success: function (result) {
	        	HideLoading();
	        	if(result.status == "success"){
	        		layer.alert('删除成功',{icon: 1,closeBtn: 0},function(index){
	        			//刷新当前页
	    	        	location.reload();
	                   //关闭加载
	                	parent.layer.closeAll('iframe');//关闭弹窗 
	        		}
	        		);
	        	}else{
	        		layer.alert('删除失败',{icon: 2,closeBtn: 0});
	        		//刷新当前页
		        	location.reload();
	                //关闭加载
	             	parent.layer.closeAll('iframe');//关闭弹窗 
	        	}
	        		
	        }
	    })
	});
}