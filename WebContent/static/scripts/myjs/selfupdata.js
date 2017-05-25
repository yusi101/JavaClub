HideLoading();
function querySelfUpdataInfo(){
	ShowLoading();
	$("#selfUpdataInfo").submit();
}

function selfupdata(pripid,entName){
	window.location.href = contextPath + "/selfUpdataController/querySelfUpdatainfo?pripid="+pripid+"&entName="+entName;
}

//打开添加企业自主更新页面
function openadd(pripid,entName){
	layer.open({
	  	type: 2,
	  	title: '添加企业自主更新',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['700px', '70%'],  //大小
	  	content: contextPath + "/selfUpdataController/toSelfUpdata?pripid="+pripid+"&entName="+entName
	});  
}


//打开企业自主更新审核页面
function openaud(ID){
	layer.open({
	  	type: 2,
	  	title: '企业自主更新审核',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['700px', '70%'],  //大小
	  	content: contextPath + "/selfUpdataController/toSelfUpdataAuditing?ID="+ID
	});  
}

//打开企业自主更新拒审页面
function openden(ID){
	layer.open({
	  	type: 2,
	  	title: '企业自主更新拒审',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['700px', '70%'],  //大小
	  	content: contextPath + "/selfUpdataController/toSelfUpdataDenied?ID="+ID
	});  
}

//打开企业自主更新审核详情页面
function auddet(ID){
	layer.open({
	  	type: 2,
	  	title: '企业自主更新审核详情',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['700px', '70%'],  //大小
	  	content: contextPath + "/selfUpdataController/querySelfUpdataAuditingDetailInfo?ID="+ID
	});  
}

//打开企业自主更新页面
function openupd(ID){
	layer.open({
	  	type: 2,
	  	title: '企业自主更新',
	  	scrollbar: false, //父页面是否有滚动条
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	maxmin: true,//是否显示最大化按钮
	  	area: ['700px', '70%'],  //大小
	  	content: contextPath + "/selfUpdataController/toSelfUpdataUpdate?ID="+ID
	});  
}

//添加企业自主更新
function add() {
	 var content = UE.getEditor('editor').getContentTxt();
	
	//检验
    if (isEmpty($("#title").val())) {
        layer.tips('标题不能为空', '#title',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if (isEmpty(content)) {
        layer.tips('内容不能为空', '#nr',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if ($("#uploadImgList").find(".uploadify-queue-item").length <= 0) {
        layer.tips('请上传图片', '#uploadImg',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    
    //正在加载
    ShowLoading(); 
    
    //获取的参数
    var formdata = {
		title: $("#title").val(),
		content: content,
		pripid: $("#pripid").val(),
		entName: $("#entName").val(),
		titleimg: $("#uploadImgList").find(".uploadify-queue-item").find("img").eq(0).attr("src").replace("data:image/png;base64,","")
    }
   
    //访问请求
    $.ajax({
        url:  contextPath + "/selfUpdataController/createSelfUpdata",
        type: "post",
        data: formdata,
        success: function (msg) {
        	//父页面刷新
        	parent.setTimeout("location.reload()",100);
        	layer.msg('添加成功！') 
           //关闭加载
        	parent.layer.closeAll('iframe');//关闭弹窗 
        }
    })
}

//删除企业自主更新
function opendel(ID){
	layer.confirm('是否确定删除？', {icon: 3, title:'提示'}, function(index){
		 //正在加载
		ShowLoading();
		    
		//获取的参数
		var formdata = {
			ID: ID
	    }
		
		//访问请求
	    $.ajax({
	        url:  contextPath + "/selfUpdataController/deleteSelfUpdata",
	        type: "post",
	        data: formdata,
	        success: function (msg) {
	        	//刷新当前页
	        	location.reload();
	    		layer.msg('删除成功！') 
	        }
	    }) 
	});
}

//企业自主更新
function upd(ID) {

	 var content = UE.getEditor('editor').getContentTxt();
	
	//检验
    if (isEmpty($("#title").val())) {
        layer.tips('标题不能为空', '#title',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if (isEmpty(content)) {
        layer.tips('内容不能为空', '#content',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    if ($("#uploadImgList").find(".uploadify-queue-item").length <= 0) {
        layer.tips('请上传图片', '#uploadImg',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
    
    //正在加载
    ShowLoading(); 
    
    //获取的参数
    var formdata = {
		title: $("#title").val(),
		content: content,
		ID: ID,
		titleimg: $("#uploadImgList").find(".uploadify-queue-item").find("img").eq(0).attr("src").replace("data:image/png;base64,","")
    }
   
    //访问请求
    $.ajax({
        url:  contextPath + "/selfUpdataController/updateSelfUpdata",
        type: "post",
        data: formdata,
        success: function (msg) {
        	//父页面刷新
        	parent.setTimeout("location.reload()",100);
        	layer.msg('更新成功！') 
           //关闭加载
        	parent.layer.closeAll('iframe');//关闭弹窗 
        }
    })
}

//企业自主更新审核通过
function aud(ID) {
   
    //正在加载
	ShowLoading(); 
    
    //获取的参数
    var formdata = {
    	ID : ID,
    	respondedId : ID,
		resultRemark : $("#resultremark").val(),
		memberId : $("#memberId").val()
    }
    //访问请求
    $.ajax({
        url:  contextPath + "/selfUpdataController/updateSelfUpdataAuditing",
        type: "post",
        data: formdata,
        success: function (result) {
        	if("success" == result){
        		layer.alert('审核通过',{icon: 1},function(index){
        			//父页面刷新
                	parent.setTimeout("location.reload()",100);
                	//关闭加载
                	parent.layer.closeAll('iframe');//关闭弹窗 
        		});
        	}else{
        		layer.alert('审核失败',{icon: 2});
        		parent.setTimeout("location.reload()",100);
                //关闭加载
             	parent.layer.closeAll('iframe');//关闭弹窗 
        	}
        }
    })
}

//企业自主更新拒审
function den(ID) {
	//检验
    if ($("#resultremark").val() == "") {
        layer.tips('拒审理由不能为空', '#resultremark');
        return;
    }
    
    //正在加载
    ShowLoading(); 
    
    //获取的参数
    var formdata = {
		ID : ID,
		respondedId : ID,
		resultRemark : $("#resultremark").val(),
		memberId : $("#memberId").val()
    }
    //访问请求
    $.ajax({
        url:  contextPath + "/selfUpdataController/updateSelfUpdataDenied",
        type: "post",
        data: formdata,
        success: function (result) {
        	if(result == "success"){
        		layer.alert('拒审成功',{icon: 1},function(index){
        			//父页面刷新
                	parent.setTimeout("location.reload()",100);
                   //关闭加载
                	parent.layer.closeAll('iframe');//关闭弹窗 
        		}
        		);
        	}else{
        		layer.alert('拒审失败',{icon: 2});
        		parent.setTimeout("location.reload()",100);
                //关闭加载
             	parent.layer.closeAll('iframe');//关闭弹窗 
        	}
        }
    })
}

//选中切换标题
function select(){
	var obj = document.getElementById("selectType"); 
	var text = obj.options[obj.selectedIndex].value; 
	if(text=="1"){
		$("#entname").attr("placeholder","请输入法定代表人名称");
	}else if(text=="2") {
		$("#entname").attr("placeholder","请输入企业名称");
	}else if(text=="3") {
		$("#entname").attr("placeholder","请输入品牌名称");
	}else if(text=="4") {
		$("#entname").attr("placeholder","请输入失信人名称");
	}
}