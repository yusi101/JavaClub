HideLoading();
function queryBrandinfo(){
	 ShowLoading();
	//检验
	if (isNotEmpty($("#brandName").val())) {
		$("#queryBrandInfo").submit();
    }
	
}


//打开添加Demo(Demo请修改为具体的模块名称)页面
function detail(brandId){
	layer.open({
	  	type: 2,
	  	title: '商标详情',
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	scrollbar: false,
	  	shade: 0.5,  //笼罩层透明度
	  	area: ['550px', '70%'],  //大小
	  	maxmin: true,
	  	content: contextPath + '/brandController/queryBrandDetail?brandId='+brandId,
	});  		
}

