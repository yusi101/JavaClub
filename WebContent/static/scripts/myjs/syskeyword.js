HideLoading();

//删除用户
function deletekeyword(ID){
	layer.confirm('是否删除？', {icon: 3, title:'提示'}, function(index){ 
		//执行删除
		$.ajax({
			url : contextPath + '/keywordController/deletekeyword',
			timeout : 300000,
			dataType : "json",
			type : "post",
			data : {
				"ID" : ID
			},
			success : function(data) {
				if(data.status == "success"){
					layer.alert("删除成功",{icon: 1,closeBtn: 0 },function(index){
						setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
						layer.closeAll('iframe');//关闭弹窗
					});
				}else{
					layer.alert("删除失败",{icon: 2,closeBtn: 0},function(index){
						setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
						layer.closeAll('iframe');//关闭弹窗
					});
				}
			}
		})
		layer.close(index);
	});
}

//打开添加用户页面
function openadd(){
	layer.open({
		type: 2,
		title: '添加关键词',
		scrollbar: false, 	//父页面是否有滚动条
		shadeClose: false,  //点击其他区域关闭弹窗
		shade: 0.5,  		//笼罩层透明度
		maxmin: true,		//是否显示最大化按钮
		area: ['500px', '50%'],  //大小
		content: contextPath + '/keywordController/toCreateSysKeyword'
	}); 
}

//添加用户
function add(){
	if(isEmpty($("#keyword").val())){
		layer.tips('关键词不能为空', '#keyword',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	//正在加载
	var index = layer.load(2); 
	var status = document.getElementsByName("status");
	var sta;
	for(var i=0;i<status.length;i++){
        if(status[i].checked==true){
       	 sta=status[i].value;
          }
	 }
	//获取的参数
	var formdata = {
		keyword: $("#keyword").val(),
		status: sta,
		type:$("#type").val(),
	}
	
	//访问请求
	$.ajax({
		url: contextPath + '/keywordController/insertkeyword',
		type: "post",
		dataType : "json",
		data: formdata,
		success : function(data) {
			if(data.status == "success"){
				layer.alert("添加成功",{icon: 1,closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();
				});
			}else{
				layer.alert("添加失败",{icon: 2,closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();
				});
			}
		},
		 error: function (){
	        	layer.alert('敏感词已存在',{icon: 2,closeBtn: 0},function(index){
	    			//父页面刷新
	            	parent.setTimeout("location.reload()",100);
	               //关闭加载
	            	parent.layer.closeAll('iframe');//关闭弹窗 
	    		});
	        	 
	        }
	})
}

//查询某个关键词
function query(Kid){
	layer.open({
		type: 2,
		title: '修改关键词',
		scrollbar: false, //父页面是否有滚动条
		shadeClose: false,  //点击其他区域关闭弹窗
		shade: 0.5,  //笼罩层透明度
		maxmin: true,//是否显示最大化按钮
		area: ['500px', '50%'],  //大小
		content:  contextPath + '/keywordController/querykeywordById?KID='+Kid
	}); 
}

//编辑用户
function edt(Kid){
	if(isEmpty($("#keyword").val())){
		layer.tips('关键词不能为空', '#keyword',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	//正在加载
	var index = layer.load(2); 
	var status = document.getElementsByName("status");
	var sta;
	 for(var i=0;i<status.length;i++){
         if(status[i].checked==true){
        	 sta=status[i].value;
           }
	 }
	//获取的参数
	var formdata = {
			keyword: $("#keyword").val(),
			status: sta,
			type:$("#type").val(),
			KID:Kid,
	}
	
	//访问请求
	$.ajax({
		url: contextPath + '/keywordController/updatekeyword',
		dataType : "json",
		type : "post",
		data: formdata,
		success: function (data) {
			if(data.status == "success"){
				layer.alert("修改成功",{icon: 1,closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();
				});
			}else{
				layer.alert("修改失败",{icon: 2,closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();
				});
			}
		},error: function (){
        	layer.alert('敏感词已存在',{icon: 2,closeBtn: 0},function(index){
    			//父页面刷新
            	parent.setTimeout("location.reload()",100);
               //关闭加载
            	parent.layer.closeAll('iframe');//关闭弹窗 
    		});
        	 
        }
	})
}