HideLoading();
function queryVisitorInfo(){
	ShowLoading();
	$("#visitorInfo").submit();
}

//邮箱正则
var emailRegex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;	
//手机正则
var phoneRegex = /^[1][34578][0-9]{9}$/;	
//不准输入数字
var isChinese = /^\d*([\u4e00-\u9fa5]|[a-zA-Z])+\d*$/;
//qq正则
var qq=/^\d{5,10}$/;



//打开添加来访记录页面
function openadd(){
	layer.open({
	  	type: 2,
	  	title: '添加来访记录',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['800px', '70%'],  //大小
	  	content: contextPath + "/pages/visitor/visitor_add.jsp"
	});  
}


//打开来访记录编辑页面
function openupd(ID){
	layer.open({
	  	type: 2,
	  	title: '编辑来访记录',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['800px', '70%'],  //大小
	  	content: contextPath + "/visitorController/toqueryVisitor?ID="+ID
	});  
}


//添加来访记录
function add() {
	
	//检验
    if (isEmpty($("#visitorName").val())) {
        layer.tips('访客名称不能为空', '#visitorName',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if(!isChinese.test($("#visitorName").val())){
    	 layer.tips('请输入正确的名称', '#visitorName',{tips:[3, '#78BA32'],tipsMore: true});
         return;
    }
    if (isEmpty($("#entName").val())) {
        layer.tips('代表企业不能为空', '#entName',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if (isEmpty($("#receiverName").val())) {
        layer.tips('接待人不能为空', '#receiverName',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if(!isChinese.test($("#receiverName").val())){
   	 	layer.tips('请输入正确的名称', '#receiverName',{tips:[3, '#78BA32'],tipsMore: true});
        return;
   }
    if (isEmpty($("#receivrTime").val())) {
        layer.tips('接待时间不能为空', '#receivrTime',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if (isEmpty($("#tel").val())) {
        layer.tips('联系电话不能为空', '#tel',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if(!phoneRegex.test($("#tel").val())){
   	 layer.tips('电话格式不正确', '#tel',{tips:[3, '#78BA32'],tipsMore: true});
        return;
   }
    if (isEmpty($("#email").val())) {
        layer.tips('联系邮箱不能为空', '#email',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if(!emailRegex.test($("#email").val())){
    	 layer.tips('邮箱格式不正确', '#email',{tips:[3, '#78BA32'],tipsMore: true});
         return;
    }
    if (isEmpty($("#QQ").val())) {
        layer.tips('QQ不能为空', '#QQ',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if(!qq.test($("#QQ").val())){
      	 layer.tips('QQ格式不正确', '#QQ',{tips:[3, '#78BA32'],tipsMore: true});
           return;
      }
    
    //正在加载
    ShowLoading(); 
    
    //获取的参数
    var formdata = {
    	visitorName: $("#visitorName").val(),
    	entName: $("#entName").val(),
    	receiverName: $("#receiverName").val(),
    	receivrTime: $("#receivrTime").val(),
    	tel: $("#tel").val(),
    	email: $("#email").val(),
    	QQ: $("#QQ").val(),
    }
   
    //访问请求
    $.ajax({
        url:  contextPath + "/visitorController/createVisitor",
        type: "post",
        data: formdata,
        success: function (msg) {
        	layer.alert("添加成功",{icon: 1,closeBtn: 0},function(index){
				parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
				parent.layer.closeAll('iframe');//关闭弹窗
				ShowLoading();
			}); 
        },
        error : function(msg){
        	layer.alert("添加失败",{icon: 2,closeBtn: 0},function(index){
				parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
				parent.layer.closeAll('iframe');//关闭弹窗
				ShowLoading();
			});
        }
    })
}


//删除来访记录
function opendel(ID){
	layer.confirm('是否确定删除？', {icon: 3, title:'提示'}, function(index){
		
		 //正在加载
		ShowLoading();
	    
	    //获取的参数
	    var formdata = {
			ID: ID
	    }
		//访问请求
	    $.ajax({
	        url:  contextPath + "/visitorController/deleteVisitor",
	        type: "post",
	        data: formdata,
	        success: function (msg) {
	        	layer.alert('删除成功！', {icon: 1,closeBtn: 0}, function(){
					//刷新窗口信息
					setTimeout("location.reload()",100); 
				});
	        }
	    }) 

	});
}


//编辑来访记录
function upd(ID) {
	
	//检验
    if (isEmpty($("#visitorName").val())) {
        layer.tips('访客名称不能为空', '#visitorName',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if(!isChinese.test($("#visitorName").val())){
    	 layer.tips('请输入正确的名称', '#visitorName',{tips:[3, '#78BA32'],tipsMore: true});
         return;
    }
    if (isEmpty($("#entName").val())) {
        layer.tips('代表企业不能为空', '#entName',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if (isEmpty($("#receiverName").val())) {
        layer.tips('接待人不能为空', '#receiverName',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if(!isChinese.test($("#receiverName").val())){
   	 	layer.tips('请输入正确的名称', '#receiverName',{tips:[3, '#78BA32'],tipsMore: true});
        return;
   }
    if (isEmpty($("#receivrTime").val())) {
        layer.tips('接待时间不能为空', '#receivrTime',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if (isEmpty($("#tel").val())) {
        layer.tips('联系电话不能为空', '#tel',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if(!phoneRegex.test($("#tel").val())){
   	 layer.tips('电话格式不正确', '#tel',{tips:[3, '#78BA32'],tipsMore: true});
        return;
   }
    if (isEmpty($("#email").val())) {
        layer.tips('联系邮箱不能为空', '#email',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if(!emailRegex.test($("#email").val())){
    	 layer.tips('邮箱格式不正确', '#email',{tips:[3, '#78BA32'],tipsMore: true});
         return;
    }
    if (isEmpty($("#QQ").val())) {
        layer.tips('QQ不能为空', '#QQ',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if(!qq.test($("#QQ").val())){
      	 layer.tips('QQ格式不正确', '#QQ',{tips:[3, '#78BA32'],tipsMore: true});
           return;
      }
    
    //正在加载
    ShowLoading(); 
    
    //获取的参数
    var formdata = {
    	visitorName: $("#visitorName").val(),
    	entName: $("#entName").val(),
    	receiverName: $("#receiverName").val(),
    	receivrTime: $("#receivrTime").val(),
    	tel: $("#tel").val(),
    	email: $("#email").val(),
    	QQ: $("#QQ").val(),
    	ID:ID
    }
   
    //访问请求
    $.ajax({
        url:  contextPath + "/visitorController/updateVisitor",
        type: "post",
        data: formdata,
        success: function (msg) {
        	layer.alert("修改成功",{icon: 1,closeBtn: 0},function(index){
				parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
				parent.layer.closeAll('iframe');//关闭弹窗
				ShowLoading();
			}); 
        },
        error : function(msg){
        	layer.alert("修改失败",{icon: 2,closeBtn: 0},function(index){
				parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
				parent.layer.closeAll('iframe');//关闭弹窗
				ShowLoading();
			});
        }
    })
}




