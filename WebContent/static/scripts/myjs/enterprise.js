//打开全景视图页面
function openAllView(pripid,regno,entname,priptype){
	layer.open({
	  	type: 2,
	  	title: '全景视图',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: false,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	area: ['100%', '100%'],  //大小
	  	content: contextPath + '/pages/enterprise/allview.jsp?pripid=' + pripid + '&regno=' + regno
  			+ '&entname=' + entname + "&priptype=" + priptype
	});  			
}

//打开投资链图页面
function openInvestView(pripid,regno,entname,priptype){
	layer.open({
	  	type: 2,
	  	title: '投资链图',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: false,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	area: ['100%', '100%'],  //大小
	  	content: contextPath + '/pages/enterprise/investview.jsp?pripid=' + pripid + '&regno=' + regno
	  		+ '&entname=' + entname + "&priptype=" + priptype
	});  			
}


//打开投资全景页面
function openInvestAllView(pripid,regno,entname,priptype){
	layer.open({
	  	type: 2,
	  	title: '投资全景',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: false,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	area: ['100%', '100%'],  //大小
	  	content: contextPath + '/pages/enterprise/investallview.jsp?pripid=' + pripid + '&regno=' + regno
  			+ '&entname=' + entname + "&priptype=" + priptype
	});  			
}

//打开企业历程页面
function openEntCourseInter(pripid,regno,entname,priptype){
	layer.open({
	  	type: 2,
	  	title: '企业历程',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: false,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	area: ['100%', '100%'],  //大小
	  	content: contextPath + '/entCourseController/queryEntCourse?pripid=' + pripid + '&regno=' + regno
  			+ '&entname=' + entname + "&priptype=" + priptype
	});  			
}

function queryBrandinfo(){
	ShowLoading();
	$("#queryBrandInfo").submit();
}
//生成信用报告
function opencredit(pripid,entname,enttype) {
	layer.open({
	  	type: 2,
	  	title: '信用报告',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	area: ['850px', '90%'],
	  	content: contextPath + '/creditReportController/creditReport?pripid='+pripid+'&entname='+entname+'&priptype='+enttype
	});  
}

//选中切换标题
function select(){
	var obj = document.getElementById("selectType"); 
	var text = obj.options[obj.selectedIndex].value; 
	if(text=="0"){
		$("#entname").attr("placeholder","请输入企业名称");
		$("#searchDes").html("企业名称");
	}else if(text=="1") {
		$("#entname").attr("placeholder","请输入法定代表人");
		$("#searchDes").html("法定代表人");
	}else if(text=="2") {
		$("#entname").attr("placeholder","请输入品牌名称");
		$("#searchDes").html("品牌名称");
	}else if(text=="3") {
		$("#entname").attr("placeholder","请输入失信人");
		$("#searchDes").html("失信人");
	}
}

select();