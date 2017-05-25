HideLoading();

//删除用户
function deletereport(ID){
	layer.confirm('是否删除？', {icon: 3, title:'提示'}, function(index){ 
		//执行删除
		$.ajax({
			url : contextPath + '/Interface/MakePdfInterface/deleteReport',
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


//打开详情页面
	function query(id){
		layer.open({
			type: 2,
			title: '记录详情',
			scrollbar: false, //父页面是否有滚动条
			shadeClose: false,  //点击其他区域关闭弹窗
			shade: 0.5,  //笼罩层透明度
			maxmin: true,//是否显示最大化按钮
			area: ['700px', '70%'],  //大小
			content:  contextPath + '/Interface/MakePdfInterface/queryReportById?ID='+id
		}); 
}