HideLoading();
function queryBrandinfo(){
	 ShowLoading();
	$("#queryBrandInfo").submit();
}


//失信被执行人详情
function opendet(biddingId) {
	layer.open({
	  	type: 2,
	  	title: '招标信息详情',
	  	shadeClose: true, //点击其他区域关闭弹窗
	  	shade: 0.8,
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['70%', '80%'],
	  	content: contextPath + '/biddingController/queryBiddingDetail?biddingId='+biddingId+'&keyNo='
	});  
}