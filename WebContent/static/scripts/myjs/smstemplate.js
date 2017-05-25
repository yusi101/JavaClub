HideLoading();
function collapseOne(){
	if($("#collapseOne").css("height")=="0px"){
		$("#collapseOne").css("height","auto");
	}
}
//字数
function count() {
    $('#stats').html("您还可以输入 " + ($('#content').attr("maxlength") - $('#content').val().length) + "个字");
  }

//新增短信模板
function openadd(){
	layer.open({
	  	type: 2,
	  	title: ' ',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['600px', '70%'],  //大小
	  	content: contextPath + "/smsTemplateController/addSmsTemplate"
	});  
}



//编辑短信模板
function openupd(ID){
	layer.open({
	  	type: 2,
	  	title: ' ',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['600px', '70%'],  //大小
	  	content: contextPath + "/smsTemplateController/updataSmsTemplate?ID="+ID
	});  
}




//添加短信模板
function add(){
   
	if (isEmpty($("#title").val())) {
		layer.tips('短信标题不能为空', '#title',{tips:[3, '#78BA32']});
		return;
	}
	if (isEmpty($("#content").val())) {
		layer.tips('短信内容不能为空', '#content',{tips:[3, '#78BA32']});
		return;
	}
	 var checkbox = document.getElementById('status');
	 var cb 
	if(checkbox.checked){
		cb = 1    //选中了
	}else{
		cb = 0    //没选中
	}
	
    //正在加载
	ShowLoading(); 
    
    
    //获取的参数
    var formdata = {
    		SMS_TITLE: $("#title").val(),
    		SMS_CTX : $("#content").val(),
    		STATUS : cb,
    }
   
    //访问请求
    $.ajax({
        url:  contextPath + "/smsTemplateController/addSmsTemplateInfo",
        type: "post",
        data: formdata,
        dataType: "json",
        success: function (result) {
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

//修改短信模板
function update(ID){
   
	if (isEmpty($("#title").val())) {
		layer.tips('标题不能为空', '#title',{tips:[3, '#78BA32']});
		return;
	}
	if (isEmpty($("#content").val())) {
		layer.tips('内容不能为空', '#content',{tips:[3, '#78BA32']});
		return;
	}
	 var checkbox = document.getElementById('status');
	 var cb 
	if(checkbox.checked){
		cb = 1    //选中了
	}else{
		cb = 0    //没选中
	}
	
    //正在加载
	ShowLoading(); 
    
    
    //获取的参数
    var formdata = {
    		SMS_TITLE: $("#title").val(),
    		SMS_CTX : $("#content").val(),
    		STATUS : cb,
    }
   
    //访问请求
    $.ajax({
        url:  contextPath + "/smsTemplateController/updataSmsTemplateInfo?ID="+ID,
        type: "post",
        data: formdata,
        dataType: "json",
        success: function (result) {
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




//删除角色
function del(ID,ACTIVE){
	
	if(ACTIVE == 0){
		layer.confirm('是否删除？', {icon: 3, title:'提示'}, function(index){
		    //正在加载
			ShowLoading();
		    
		    
		    //获取的参数
		    var formdata = {
		    	ID:ID
		    }
		   
		    //访问请求
		    $.ajax({
		        url:  contextPath + "/smsTemplateController/delSmsTemplateInfo",
		        type: "post",
		        data: formdata,
		        dataType: "json",
		        success: function (result) {
		        	if(result.status == "success"){
		        		layer.alert('删除成功',{icon: 1,closeBtn: 0},function(index){
		        			//刷新当前页
		    	        	location.reload();
		                   //关闭加载
		                	parent.layer.closeAll('iframe');//关闭弹窗 
		        		}
		        		);
		        	}else{
		        		layer.alert('删除失败',{icon: 2,closeBtn: 0},function(index){
			        		location.reload();
			                //关闭加载
			             	parent.layer.closeAll('iframe');//关闭弹窗
		        		});
		        	}
		        		
		        }
		    })
		});
	} else{
		layer.alert('状态已激活，不能删除！',{icon: 2,closeBtn: 0});
	}
	
	
}

