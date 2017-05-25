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

//新增知识
function openadd(){
	layer.open({
	  	type: 2,
	  	title: ' ',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['400px', '70%'],  //大小
	  	content: contextPath + "/knowledgeController/addKnowPage"
	});  
}



//打开编辑页面
function openupd(ID){
	layer.open({
	  	type: 2,
	  	title: '  ',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['400px', '70%'],  //大小
	  	content: contextPath + "/knowledgeController/knowEditPage?ID="+ID
	});  
}

//打开详情页面
function ondetails(ID){
	layer.open({
	  	type: 2,
	  	title: '知识详情',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['400px', '70%'],  //大小
	  	content: contextPath + "/knowledgeController/knowDetailPage?ID="+ID
	});  
}




//添加知识
function add(){
   
	
	if (isEmpty($("#title").val())) {
		layer.tips('标题不能为空', '#title',{tips:[3, '#78BA32']});
		return;
	}
	if (isEmpty($("#content").val())) {
		layer.tips('内容不能为空', '#content',{tips:[3, '#78BA32']});
		return;
	}
	if (isEmpty($("#menuOrder").val())) {
		layer.tips('排序号不能为空', '#menuOrder',{tips:[3, '#78BA32']});
		return;
	}
	var num = /^[0][0-9]*$/;
	if (num.test($("#menuOrder").val())) {
		layer.tips('排序号只能为正整数', '#menuOrder',{tips:[3, '#78BA32']});
		return;
	}
	
	
    //正在加载
	ShowLoading(); 
    
    
    //获取的参数
    var formdata = {
    		TITLE: $("#title").val(),
    		CONTENT : $("#content").val(),
    		ORDERBY: $("#menuOrder").val(),
    }
   
    //访问请求
    $.ajax({
        url:  contextPath + "/knowledgeController/addKnowInfo",
        type: "post",
        data: formdata,
        dataType: "json",
        error: function (){
        	layer.alert('数据已存在',{icon: 2,closeBtn: 0},function(index){
    			//父页面刷新
            	parent.setTimeout("location.reload()",100);
               //关闭加载
            	parent.layer.closeAll('iframe');//关闭弹窗 
    		});
        	 
        },
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

//修改知识
function update(ID){
   
	if (isEmpty($("#title").val())) {
		layer.tips('标题不能为空', '#title',{tips:[3, '#78BA32']});
		return;
	}
	if (isEmpty($("#content").val())) {
		layer.tips('内容不能为空', '#content',{tips:[3, '#78BA32']});
		return;
	}
	if (isEmpty($("#menuOrder").val())) {
		layer.tips('排序号不能为空', '#menuOrder',{tips:[3, '#78BA32']});
		return;
	}
	var num = /^[0][0-9]*$/;
	if (num.test($("#menuOrder").val())) {
		layer.tips('排序号只能为正整数', '#menuOrder',{tips:[3, '#78BA32']});
		return;
	}
	
	
    //正在加载
	ShowLoading(); 
    
    
    //获取的参数
    var formdata = {
    		TITLE: $("#title").val(),
    		CONTENT : $("#content").val(),
    		ORDERBY: $("#menuOrder").val(),
    }
   
    //访问请求
    $.ajax({
        url:  contextPath + "/knowledgeController/updateKnowInfo?ID="+ID,
        type: "post",
        data: formdata,
        dataType: "json",
        error: function (){
        	layer.alert('数据已存在',{icon: 2,closeBtn: 0},function(index){
    			//父页面刷新
            	parent.setTimeout("location.reload()",100);
               //关闭加载
            	parent.layer.closeAll('iframe');//关闭弹窗 
    		});
        	 
        },
        success: function (result) {
        	if(result.status == "success"){
        		layer.alert('修改成功',{icon: 1,closeBtn: 0},function(index){
        			//父页面刷新
                	parent.setTimeout("location.reload()",100);
                   //关闭加载
                	parent.layer.closeAll('iframe');//关闭弹窗 
        		}
        		);
        	}else if(result.status == "error2"){
        		layer.alert('数据已存在',{icon: 2,closeBtn: 0});
        		parent.setTimeout("location.reload()",100);
                //关闭加载
             	parent.layer.closeAll('iframe');//关闭弹窗 
        	}
        	else{
        		layer.alert('修改失败',{icon: 2});
        		parent.setTimeout("location.reload()",100);
                //关闭加载
             	parent.layer.closeAll('iframe');//关闭弹窗 
        	}
        		
        }
    })
}




//删除角色
function del(ID){
	layer.confirm('是否确定删除成功？', {icon: 3, title:'提示'}, function(index){
	    //正在加载
		ShowLoading();
	    
	    
	    //获取的参数
	    var formdata = {
	    	ID:ID
	    }
	   
	    //访问请求
	    $.ajax({
	        url:  contextPath + "/knowledgeController/deleteKnowInfo",
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
}

