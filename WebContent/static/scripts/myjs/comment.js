//搜索
function mySearch(){
    ShowLoading();
    
    if(compareTime()){
    	$("#queryCommentInfo").submit();
    }
}

//比较时间
function compareTime() {
	//获取开始时间和结束时间
	var startTime = $("#startDay").val();
	var endTime = $("#endDay").val();
	
	
	if("" == startTime || "" == endTime || null == startTime 
			|| null == endTime || "undefined" == startTime || "undefined" == endTime){
		return true;
	}
	
	//转换为int的时间
	var newST = parseInt(startTime.replace(/-/g,""));
	var newET = parseInt(endTime.replace(/-/g,""));
	
	//判断开始时间是否大于结束时间
	if(newST > newET){
		layer.msg("开始时间不能大于结束时间");
		return false;
	}
	
	return true;
}

//打开评论审核页面
function openupd(ID){
	layer.open({
	  	type: 2,
	  	title: '评论审核',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['700px', '70%'],  //大小
	  	content: contextPath + "/commentController/queryAuditCommentinfo?ID="+ID
	});  
}

//打开评论审核详情页面
function opendet(ID){
	layer.open({
	  	type: 2,
	  	title: '评论审核详情',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['700px', '70%'],  //大小
	  	content: contextPath + "/commentController/queryCommentDetailInfo?ID="+ID
	});  
}

//企业自主更新审核通过
function aud(ID) {
	//检验
    if ($("#resultremark").val() == "") {
        layer.tips('审核通过理由不能为空', '#resultremark');
        return;
    }
    //正在加载
	ShowLoading(); 
    
    //获取的参数
    var formdata = {
		ID: ID,
		resultreMark : $("#resultremark").val(),
		status : 1
    }
   
    //访问请求
    $.ajax({
        url:  contextPath + "/commentController/updateCommentInfo",
        type: "post",
        data: formdata,
        success: function (result) {
        	if(result == "success"){
        		layer.alert('审核通过',{icon: 1},function(index){
        			//父页面刷新
                	parent.setTimeout("location.reload()",100);
                   //关闭加载
                	parent.layer.closeAll('iframe');//关闭弹窗 
        		});
        	}else{
        		layer.alert('审核失败',{icon: 2});
        		parent.setTimeout("location.reload()",100);
                //关闭加载
             	parent.layer.closeAll('iframe');//关闭弹窗 
        	}
        }
    })
}

//企业自主更新拒审
function den(ID) {
	//检验
    if ($("#resultremark").val() == "") {
        layer.tips('拒审理由不能为空', '#resultremark');
        return;
    }
    
    //正在加载
    ShowLoading();
    
    //获取的参数
    var formdata = {
		ID: ID,
		resultreMark: $("#resultremark").val(),
		status : 2
    }
   
    //访问请求
    $.ajax({
    	url:  contextPath + "/commentController/updateCommentInfo",
        type: "post",
        data: formdata,
        success: function (result) {
        	if(result == "success"){
        		layer.alert('拒审成功',{icon: 1},function(index){
        			//父页面刷新
                	parent.setTimeout("location.reload()",100);
                   //关闭加载
                	parent.layer.closeAll('iframe');//关闭弹窗 
        		});
        	}else{
        		layer.alert('拒审失败',{icon: 2});
        		parent.setTimeout("location.reload()",100);
                //关闭加载
             	parent.layer.closeAll('iframe');//关闭弹窗 
        	}
        }
    })
}