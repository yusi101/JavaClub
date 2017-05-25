HideLoading();
function queryBrandinfo(){
	ShowLoading();
	$("#queryBrandInfo").submit();
}


//app数据修改
function updateversion(id) {
	layer.confirm('您确定要删除？', {
		btn: ['确定', '取消'] //按钮
	}, function () {
		//执行删除
		var url =contextPath + '/VersionController/updateVersionById';
		$.ajax({
			url : url,
			timeout : 300000,
			dataType : "json",
			type : "post",
			data : {
				"id" : id
			},
			success : function(data) {
				if(data.status == "success"){
					layer.alert("删除成功",{icon: 1,closeBtn: 0},function(index){
						setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
						ShowLoading();
					});
				}else{
					layer.alert("删除失败",{icon: 5,closeBtn: 0},function(index){
						setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
						ShowLoading();
					});
				}
			}
		})
	}, function () {
	});
}

//跳转到数据字典新增页面
function openedit(){
	layer.open({
		type: 2,
		scrollbar: false, //父页面是否有滚动条
		title: '新增',
		maxmin: true,//是否显示最大化按钮
		shadeClose: false, //点击其他区域关闭弹窗
		shade: 0.8,
		area: ['650px', '70%'],
		content: contextPath + '/pages/version/app_add.jsp'
	});  			
}




//跳转到数据字典修改页面
function update(id){
	layer.open({
		type: 2,
		scrollbar: false, //父页面是否有滚动条
		title: '修改',
		maxmin: true,//是否显示最大化按钮
		shadeClose: false, //点击其他区域关闭弹窗
		shade: 0.8,
		area: ['650px', '70%'],
		content: contextPath + '/VersionController/queryVersionById?id='+id
	});  			
}