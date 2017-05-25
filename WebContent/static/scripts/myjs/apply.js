//搜索
function search(){
    ShowLoading();
	$("#queryEntnameInfo").submit();
}

//禁用回车键
document.onkeydown = function () {
    if (window.event && window.event.keyCode == 13) {
        window.event.returnValue = false;
    }
}

//打开申请牌照页面
function openadd() {
	layer.open({
		type : 2,
		scrollbar : false, 	//父页面是否有滚动条
		title : '申请牌照',
		maxmin : true,		//是否显示最大化按钮
		shade : 0.8,
		area : [ '780px', '80%' ],
		content : contextPath + '/ApplyController/toCreateQRCode'
	});
}

//自动校验申请数量
function applyNumCheck(){
	//申请数量为数字
	var numRegex = /^[0-9]*$/;
	
	//如果申请数量不是数字的话，申请数量默认改为1
    if(!numRegex.test($("#applyNumber").val())){
    	$("#applyNumber").val("1");
    }
    //如果申请数量小于0的话，申请数量默认改为1
    if(0 >= $("#applyNumber").val()){
    	$("#applyNumber").val("1");
    }
}

//在线添加申请牌照
function add() {
	//邮箱正则
	var emailRegex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;	
	//手机正则
	var phoneRegex = /^[1][358][0-9]{9}$/;	
	//身份证正则
	var idCardRegex = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
	//申请数量为数字
	var numRegex = /^[0-9]*$/;

	//检验
	if ($("#applyNumber").val() == "") {
		layer.tips('申请数量不能为空', '#applyNumber',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}	
	if ($("#applyNumber").val() > 100) {
		layer.tips('申请数量不能超过100个', '#applyNumber',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
    if(!numRegex.test($("#applyNumber").val())){
        layer.tips('申请数量只能是数字', '#applyNumber',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }
    if($("#applyNumber").val() <= 0){
        layer.tips('申请数量不能小于0', '#applyNumber',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    } 
	if ($("#applyName").val() == "") {
		layer.tips('姓名不能为空', '#applyName',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}	  
	if ($("#cerno").val() == "") {
		layer.tips('证件号码不能为空', '#cerno',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if($("#cerType").find("option:selected").text() == "中华人民共和国居民身份证"){
		if (!idCardRegex.test($("#cerno").val())) {
			layer.tips('证件号码格式不正确', '#cerno',{tips:[2, '#78BA32'],tipsMore: true});
			return;
		} 
	}
	if ($("#email").val() == "") {
		layer.tips('联系邮箱不能为空', '#email',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if (!emailRegex.test($("#email").val())) {
		layer.tips('联系邮箱格式不正确', '#email',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}  
	if ($("#tel").val() == "") {
		layer.tips('联系号码不能为空', '#tel',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if (!phoneRegex.test($("#tel").val())) {
		layer.tips('联系号码格式不正确', '#tel',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}    
	if ($("#address").length > 300) {
		layer.tips('联系地址长度不能超过300个字符', '#address',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}      
	if ($("#remark").length > 300) {
		layer.tips('备注长度不能超过300个字符', '#remark',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	} 

	//获取的参数
	var formdata = {
		sex: $("input[name='sex']:checked").val(),
		remark: $("#remark").val(),
		address: $("#address").val(),
		tel: $("#tel").val(),
		email: $("#email").val(),
		cerno: $("#cerno").val(),
		applyType: $("#applyTypeCN").val(),
		applyTypeCN: $("#applyTypeCN").find("option:selected").text(),
		applyNumber: $("#applyNumber").val(),
		applyName: $("#applyName").val(),
		position: $("#position").val(),
		cerType: $("#cerType").val(),
		cerTypeCN: $("#cerType").find("option:selected").text()
	}

	//开启正在加载
	ShowLoading();

	//访问请求
	$.ajax({
		url: contextPath + "/ApplyController/insertApply",
		type: "post",
		data: formdata,
		success: function (result) {
			//获取返回值处理业务
			if("success" == result){
				layer.alert('申请成功！', {icon: 1}, function(){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					layer.closeAll('iframe');//关闭弹窗
				});
			} else {
				layer.alert('申请失败！', {icon: 2}, function(index){
					layer.close(index);
	        		layer.closeAll('loading');
				})
			}
		}
	})
}

//打开申请牌照详情页面
function openApplyDet(aid, pid, pripid){
	layer.open({
	  	type: 2,
	  	title: '申请牌照',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.6,  //笼罩层透明度
	  	area: ['860px', '80%'],  //大小
	  	content: contextPath + '/qrCodeController/queryQRCodeById?aid=' + aid + "&pid=" + pid + "&pripid=" + pripid
	});
}

//新开审核页面
function openAudit(id,openType){
	layer.open({
		type : 2,
		scrollbar : false, //父页面是否有滚动条
		title : '牌照在线审核',
		maxmin : true,//是否显示最大化按钮
		shade : 0.8,
		area : [ '680px', '80%' ],
		content : contextPath + '/ApplyController/queryApplyById?id=' + id + "&openType=" + openType 
	});
}

//审核通过
function openOk(){
	if($("#RESULTREMARK").val() == ""){
		layer.tips('不能为空', '#RESULTREMARK',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	
	//获取的参数
	var formdata = {
		aid: $("#ID").val(),
		resultremark : $("#RESULTREMARK").val(),
		applyNumber : $("#APPLY_NUMBER").val(),
		entname : $("#ENTNAME").val(),
		pripid : $("#PRIPID").val(),
		pstatus: 0,
		astatus: 1,
		typeId: "牌照在线审核"
	}
	
	//开启正在加载
	ShowLoading();

	//访问请求
	$.ajax({
		url: contextPath + "/ApplyController/okPass",
		type: "post",
		data: formdata,
		success: function (result) {
			//获取返回值处理业务
			if(result == "success" ){
				layer.alert('审核成功！', {icon: 1},function(){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					layer.closeAll('iframe');//关闭弹窗
				});
			}else{
				layer.alert('审核失败！', {icon: 2},function(){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					layer.closeAll('iframe');//关闭弹窗
				})
			}
		}
	})
}

//审核不通过
function openNo(){
	if($("#RESULTREMARK").val() == ""){
		layer.tips('不能为空', '#RESULTREMARK',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	
	//获取的参数
	var formdata = {
		aid: $("#ID").val(),
		resultremark :  $("#RESULTREMARK").val(),
		astatus: 2,
		typeId: "牌照在线审核"
	}
	
	//开启正在加载
	ShowLoading();

	//访问请求
	$.ajax({
		url: contextPath + "/ApplyController/noPass",
		type: "post",
		data: formdata,
		success: function (result) {
			//获取返回值处理业务
			if(result == "success" ){
				layer.alert('审核成功！', {icon: 1},function(){
					//关闭弹窗
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					layer.closeAll('iframe');//关闭弹窗
				});
			}else{
				layer.alert('审核失败！', {icon: 2},function(){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					layer.closeAll('iframe');//关闭弹窗
				})
			}
		}
	})
}