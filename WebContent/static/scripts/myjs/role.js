HideLoading();
function collapseOne(){
	if($("#collapseOne").css("height")=="0px"){
		$("#collapseOne").css("height","auto");
	}
}

//打开权限添加页面
function openadd(){
	layer.open({
	  	type: 2,
	  	title: '角色-权限添加',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['700px', '70%'],  //大小
	  	content: contextPath + "/roleController/queryMenu"
	});  
}



//打开角色编辑页面
function openupd(ID){
	layer.open({
	  	type: 2,
	  	title: '编辑角色',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['700px', '70%'],  //大小
	  	content: contextPath + "/roleController/toqueryRole?ID="+ID
	});  
}




//添加角色-权限
function add(){
   
	if (isEmpty($("#role_Name").val())) {
		layer.tips('角色名称不能为空', '#role_Name',{tips:[3, '#78BA32']});
		return;
	}
	if (isEmpty($("#role_Code").val())) {
		layer.tips('角色编码不能为空', '#role_Code',{tips:[3, '#78BA32']});
		return;
	}
	if (isEmpty($("#menu_Name").html())) {
		layer.tips('请添加权限', '#menu_Name',{tips:[3, '#78BA32']});
		return;
	}
	
    //正在加载
	ShowLoading(); 
    
    
    //获取的参数
    var formdata = {
    	role_Name: $("#role_Name").val(),
    	role_Code : $("#role_Code").val(),
    	rights : $("#menu_Id").val(),
    	parent_Id : $("#parent_Id").val(),
    }
   
    //访问请求
    $.ajax({
        url:  contextPath + "/roleController/createRole",
        type: "post",
        data: formdata,
        dataType: "json",
        success: function (result) {
        	if(result.status == "success"){
        		layer.alert('添加成功',{icon: 1},function(index){
        			//父页面刷新
                	parent.setTimeout("location.reload()",100);
                   //关闭加载
                	parent.layer.closeAll('iframe');//关闭弹窗 
        		}
        		);
        	}else{
        		layer.alert('添加失败',{icon: 2});
        		parent.setTimeout("location.reload()",100);
                //关闭加载
             	parent.layer.closeAll('iframe');//关闭弹窗 
        	}
        		
        }
    })
}


//删除角色
function del(ID){
	layer.confirm('确定删除(请确认是否有用户使用此角色)？', {icon: 3, title:'提示'}, function(index){
	    //正在加载
		ShowLoading();
	    
	    
	    //获取的参数
	    var formdata = {
	    	ID:ID
	    }
	   
	    //访问请求
	    $.ajax({
	        url:  contextPath + "/roleController/deleteRole",
	        type: "post",
	        data: formdata,
	        dataType: "json",
	        success: function (result) {
	        	if(result.status == "success"){
	        		layer.alert('删除成功',{icon: 1},function(index){
	        			//刷新当前页
	    	        	location.reload();
	                   //关闭加载
	                	parent.layer.closeAll('iframe');//关闭弹窗 
	        		}
	        		);
	        	}else{
	        		layer.alert('删除失败',{icon: 2},function(index){
		        		location.reload();
		                //关闭加载
		             	parent.layer.closeAll('iframe');//关闭弹窗
	        		});
	        	}
	        		
	        }
	    })
	});
}


//修改角色-权限
function upd(ID){
   
	if (isEmpty($("#role_Name").val())) {
		layer.tips('角色名称不能为空', '#role_Name',{tips:[3, '#78BA32']});
		return;
	}
	if (isEmpty($("#role_Code").val())) {
		layer.tips('角色编码不能为空', '#role_Code',{tips:[3, '#78BA32']});
		return;
	}
	if (isEmpty($("#menu_Id").val())) {
		layer.tips('请添加权限', '#menu_Id',{tips:[3, '#78BA32']});
		return;
	}
	
    //正在加载
	ShowLoading();
    
    
    //获取的参数
    var formdata = {
    	role_Name: $("#role_Name").val(),
    	role_Code : $("#role_Code").val(),
    	rights : $("#menu_Id").val(),
    	parent_Id : $("#parent_Id").val(),
    	ID:ID
    }
   
    //访问请求
    $.ajax({
        url:  contextPath + "/roleController/updateRole",
        type: "post",
        data: formdata,
        dataType: "json",
        success: function (result) {
        	if(result.status == "success"){
        		layer.alert('修改成功',{icon: 1},function(index){
        			//父页面刷新
                	parent.setTimeout("location.reload()",100);
                   //关闭加载
                	parent.layer.closeAll('iframe');//关闭弹窗 
        		}
        		);
        	}else{
        		layer.alert('修改失败',{icon: 2});
        		parent.setTimeout("location.reload()",100);
                //关闭加载
             	parent.layer.closeAll('iframe');//关闭弹窗 
        	}
        		
        }
    })
}
