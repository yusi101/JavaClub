HideLoading();
function queryBrandinfo(){
	ShowLoading();
	$("#queryBrandInfo").submit();
}


//失信被执行人详情
function opendet(courtcaseId) {
	var str= "";
	layer.open({
		type: 2,
		scrollbar: false,
		maxmin: true,//是否显示最大化按钮
		title: '失信被执行人详情',
		shadeClose: true, //点击其他区域关闭弹窗
		shade: 0.8,
		area: ['75%', '85%'],
		content: contextPath + '/courtcaseController/queryCourtById?courtcaseId='+courtcaseId+'&iname='+str
	});  
}