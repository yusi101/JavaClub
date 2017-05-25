HideLoading();
function queryPatentinfo(){
	 ShowLoading();
	$("#queryPatentInfo1").submit();
}

//专利详情页面
function opendet1(ID){
	layer.open({
	  	type: 2,
	  	title: '专利详情',
	  	shadeClose: false, //点击其他区域关闭弹窗
	  	scrollbar: false, //父页面是否有滚动条
	  	shade: 0.5,
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['800px', '80%'],
	  	content: contextPath + "/patentController/queryPatentDetailPage1?ID="+ID,
	});  			
}