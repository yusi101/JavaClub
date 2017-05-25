HideLoading();
function queryBrandinfo(){
	ShowLoading();
	$("#queryBrandInfo").submit();
}

//数据字典添加
function add() {
	var reg= /^\d+$/;
	if ($("#NAME").val() == "") {
		layer.tips('不能为空', '#NAME');
		return;
	}
	if ($("#BIANMA").val() == "") {
		layer.tips('不能为空', '#BIANMA');
		return;
	}
	if ($("#ORDY_BY").val() == "") {
		layer.tips('不能为空', '#ORDY_BY');
		return;
	}
	if (!reg.test($("#ORDY_BY").val())){
		layer.tips('只能为纯数字', '#ORDY_BY');
		return;
	}
	//正在加载
	var index = layer.load(2); 

	//获取的参数
	var formdata = {
			flag: "edt",
			NAME: $("#NAME").val(),
			BIANMA: $("#BIANMA").val(),
			ORDY_BY: $("#ORDY_BY").val(),
			PARENT_ID: $("#ZD_ID").val(),
	}
	//访问请求
	$.ajax({
		url: contextPath + '/dictionariesController/createDictionaries',
		timeout: 300000,
		dataType: "json",
		type: "post",
		data: formdata,
		success: function (data) {
			if(data == true){
				layer.alert("添加成功！",{closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();
				});
			}else if(data == false){
				layer.alert("修改失败,内容已存在",{closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();});
			}
			else{
				layer.alert("添加失败！",{closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();
				});
			}

		}
	})
}

//跳转到数据字典新增页面
function openedt(ZD_ID){
	layer.open({
		type: 2,
		scrollbar: false, //父页面是否有滚动条
		title: '新增',
		maxmin: true,//是否显示最大化按钮
		shadeClose: false, //点击其他区域关闭弹窗
		shade: 0.8,
		area: ['550px', '70%'],
		content: contextPath + '/pages/system/zd_add.jsp?ZD_ID='+ZD_ID
	});  			
}
//跳转到数据字典修改页面
function opens(id){
	layer.open({
		type: 2,
		scrollbar: false, //父页面是否有滚动条
		title: '修改',
		maxmin: true,//是否显示最大化按钮
		shadeClose: true, //点击其他区域关闭弹窗
		shade: 0.8,
		area: ['550px', '70%'],  //大小
		content: contextPath + '/dictionariesController/queryDictionariesById?ZD_ID='+id+'&keyNo='
	});  
}



//跳转到数据字典修改页面
function update() {
	var reg= /^\d+$/;
	if ($("#NAME").val() == "") {
		layer.tips('不能为空', '#NAME');
		return;
	}
	if ($("#BIANMA").val() == "") {
		layer.tips('不能为空', '#BIANMA');
		return;
	}
	if ($("#ORDY_BY").val() == "") {
		layer.tips('不能为空', '#ORDY_BY');
		return;
	}
	if (!reg.test($("#ORDY_BY").val())){
		layer.tips('只能为纯数字', '#ORDY_BY');
		return;
	}
	//正在加载
	var index = layer.load(2); 

	//获取的参数
	var formdata = {
			flag: "edt",
			NAME: $("#NAME").val(),
			BIANMA: $("#BIANMA").val(),
			ORDY_BY: $("#ORDY_BY").val(),
			ZD_ID: $("#ZD_ID").val(),
			P_BM: $("#P_BM").val(),
	}
	//访问请求
	$.ajax({
		url: contextPath + '/dictionariesController/updateDictionariesModify',
		timeout: 300000,
		dataType: "json",
		type: "post",
		data: formdata,
		success: function (data) {
			if(data==true){
				layer.alert("修改成功",{closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();
				});
			}
			else if(data == false){
				layer.alert("修改失败,内容已存在",{closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();});
			}
			else{
				layer.alert("修改失败",{closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();
				});
			}
		}
	})
}


//数据字典单挑数据删除
function del(ZD_ID) {
	layer.confirm('您确定要删除？', {icon: 3, title:'提示'}, function () {
		//执行删除
		var url =contextPath + '/dictionariesController/deleteDictionariesById';
		$.ajax({
			url : url,
			timeout : 300000,
			dataType : "json",
			type : "post",
			data : {
				"ZD_ID" : ZD_ID
			},
			success : function(data) {
				if(data == false){
					layer.alert("删除失败！请先删除其下级信息",{closeBtn: 0},function(index){
						setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
						ShowLoading();
					});
				}else if(data == true ){
					layer.alert("删除成功",{closeBtn: 0},function(index){
						setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
						ShowLoading();
					});
				}
			}
		})
	}, function () {
		// 取消删除

	});

}

