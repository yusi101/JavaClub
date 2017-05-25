//搜索
function mySearch(){
    ShowLoading();
    
    if(compareTime()){
    	$("#queryComplainInfo").submit();
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

//打开企业投诉审核页面
function openApplyComplain(id,openType){
	layer.open({
	  	type: 2,
	  	title: '企业投诉',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.6,  //笼罩层透明度
	  	area: ['700px', '80%'],  //大小
	  	content: contextPath + '/complainController/queryComplainById?id=' + id + "&openType=" + openType
	});
}

//打开企业投诉详情页面
function openApplyComplainDet(id,openType){
	layer.open({
	  	type: 2,
	  	title: '企业投诉',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.6,  //笼罩层透明度
	  	area: ['700px', '80%'],  //大小
	  	content: contextPath + '/complainController/queryComplainById?id=' + id + "&openType=" + openType
	});
}

//编辑企业投诉审核状态
function edtComplain(id, status){
	if($("#remark").val() == ""){
		layer.tips('描述不能为空', '#remark',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if($("#remark").length > 300){
		layer.tips('描述最多不能超过三百个字', '#remark',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	
    //获取的参数
    var formdata = {
    	cid: id,
    	status: status,
    	remark: $("#remark").val(),
    	memberId: $("#memberId").val()
    }
    
    //开启正在加载
    ShowLoading();
    
    //访问请求
    $.ajax({
        url: contextPath + '/complainController/updateComplain',
        type: "post",
        data: formdata,
        success: function (result) {
        	//获取返回值处理业务
            if("success" == result){
         	   layer.alert('审核成功！', {icon: 1},function(){
         		   //刷新父窗口信息
         		   parent.setTimeout("location.reload()",100); 
                   //关闭弹窗
                   parent.layer.closeAll('iframe');
         	   });
            }else{
         	   layer.alert('审核失败！', {icon: 2})
            }
        }
    })
}
