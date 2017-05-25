//搜索
function search(){
    ShowLoading();
	$("#queryClaimInfo").submit();
}

//打开认领企业审核页面
function openApplyClaim(id,openType){
	layer.open({
	  	type: 2,
	  	title: '认领企业',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.6,  //笼罩层透明度
	  	area: ['860px', '80%'],  //大小
	  	content: contextPath + '/claimController/queryClaimById?id=' + id + "&openType=" + openType
	});
}

//打开认领企业详情页面
function openApplyClaimDet(id,openType){
	layer.open({
	  	type: 2,
	  	title: '认领企业',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.6,  //笼罩层透明度
	  	area: ['860px', '80%'],  //大小
	  	content: contextPath + '/claimController/queryClaimById?id=' + id + "&openType=" + openType
	});
}

//编辑认领企业审核状态
function edtClaim(id, status){
    //获取的参数
    var formdata = {
    	cid: id,
    	status: status,
    	remark: $("#remark").val(),
    	username: $("#username").val(),
    	password: $("#password").val(),
    	email: $("#email").val(),
    	tel: $("#tel").val(),
    	memberId: $("#memberId").val(),
    	pripid: $("#pripid").val(),
    	sysStatus: 0
    }
    
    //开启正在加载
    ShowLoading();
    
    //访问请求
    $.ajax({
        url: contextPath + '/claimController/updateClaim',
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
