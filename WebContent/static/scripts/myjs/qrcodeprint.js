//切换企业和法定代表人搜索名称显示
$("#selectType").change(function(){
	if($(this).val() == 0){
		$("#searchDes").text("企业名称");
		$("#entname").attr("placeholder","请输入企业名称");
	}else{
		$("#searchDes").text("法定代表人");
		$("#entname").attr("placeholder","请输入法定代表人");
	}
});




//搜索
function mySearch(){
    ShowLoading();
    
    if(compareTime()){
    	$("#queryEntnameInfo").submit();
    }
}

//比较时间
function compareTime() {
	//获取开始时间和结束时间
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	
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

//打印营业执照
function confirm_businesslicense(pripid,entname){
	layer.open({
	  	type: 2,
	  	title: '打印营业执照',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.6,  //笼罩层透明度
	  	area: ['500px', '500px'],  //大小
	  	content: contextPath + '/qrCodePrintController/confirm_businesslicense?pripid=' + pripid + "&entname=" + entname
	});
}

//打印牌照申请回执单
function printBusinessLicense(pripid){
	layer.open({
	  	type: 2,
	  	title: '打印回执单',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.6,  //笼罩层透明度
	  	area: ['780px', '80%'],  //大小
	  	content: contextPath + '/qrCodePrintController/printApplyReceipt?pripid=' + pripid
	});
}