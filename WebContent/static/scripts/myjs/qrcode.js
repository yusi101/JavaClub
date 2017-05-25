//切换企业和法定代表人搜索名称显示
function select(){
	var obj = document.getElementById("selectType"); 
	if(obj){
		var text = obj.options[obj.selectedIndex].value; 
	}
	
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

//禁用回车键
document.onkeydown = function () {
    if (window.event && window.event.keyCode == 13) {
        window.event.returnValue = false;
    }
}

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

//打开申请牌照页面
function openApplyQRCode(pripid,regno,entname,uniscid,province){
	layer.open({
	  	type: 2,
	  	title: '申请牌照',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.6,  //笼罩层透明度
	  	area: ['900px', '85%'],  //大小
	  	content: contextPath + '/qrCodeController/toCreateQRCode?pripid=' + pripid + '&regno=' + regno
  			+ '&entname=' + entname + "&uniscid=" + uniscid + "&province=" + province
	});
}

//打开申请牌照编辑页面
function openApplyQRCodeEdit(aid){
	layer.open({
	  	type: 2,
	  	title: '申请牌照',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.6,  //笼罩层透明度
	  	area: ['800px', '80%'],  //大小
	  	content: contextPath + '/qrCodeController/toUpdateQRCode?aid=' + aid
	});
}

//打开申请记录页面
function openApplyRecord(pripid){
	layer.open({
	  	type: 2,
	  	title: '申请记录',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.6,  //笼罩层透明度
	  	area: ['900px', '90%'],  //大小
	  	content: contextPath + '/qrCodeController/queryQRCodeApplyDetail?pripid=' + pripid
	});
}

//打开申请牌照详情页面
function openApplyDet(aid, pid,pripid){
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
//打开牌照流水查询详情页面
function openApplyDets(aid, pid,pripid){
	layer.open({
	  	type: 2,
	  	title: '牌照流水查询',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.6,  //笼罩层透明度
	  	area: ['860px', '80%'],  //大小
	  	content: contextPath + '/qrCodeController/queryQRCodeById?aid=' + aid + "&pid=" + pid + "&pripid=" + pripid
	});
}
//下载导入模板
function uploadExcel(){
	window.open(contextPath + "/qrCodeController/downExcel");
}

//点击Excel上传
function fileOpen(){
	$('#upload').click();
}

//导入excel
function preview() { 
	/*var formData = new FormData($("#uploadForm")[0]);*/
    //开启正在加载
    ShowLoading();
    //兼容ie8文件上传
    $("#uploadForm").ajaxSubmit({
        
        url: contextPath + "/qrCodeController/batchQRCodeAtExcel", //设置post提交到的页面
        type: "post", //设置表单以post方法提交
        success: function (result) {
        	//拆分出失败的企业信息和成功导入的企业个数
	    	var data = result.split("::");
	    	//获取失败的企业信息
			var entinfo = JSON.parse(data[0]);
			//获取成功导入的企业个数
			var entCount = data[1];
			var str;
			
			//关闭加载中
			HideLoading();
			
			if(entinfo.length != 0){
				str = "<table class='table table-condensed table-bordered table-hover tab'>";
				str += "<thead style='background:#FFF;'><tr>" 
					+ "<th colspan='2' style='color:#000;'>一共成功导入<font color='red'>" + entCount + "</font>家企业，有<font color='red'>" + entinfo.length + "</font>家企业申请失败。</th>" 
					+ "</tr></thead>";
				str += "<tbody><tr><td>企业名称</td><td>注册号</td></tr>";
				for(var i = 0; i < entinfo.length; i++){
					str += "<tr><td>" + entinfo[i].entname + "</td><td>" + entinfo[i].regno + "</td></tr>";
				}
				str += "</tbody></table>";
				
				//提示
				layer.open({
					type: 1,
					skin: 'layui-layer-rim', //加上边框
					area: ['420px', '240px'], //宽高
					content: str,
					cancel: function(index){ 
						//刷新父窗口信息
						setTimeout("location.reload()",100); 
					} 
				});
			}else if(0 == entCount){
				//全部导入失败
				layer.alert('导入失败！', {icon: 1}, function(){
					//刷新父窗口信息
					setTimeout("location.reload()",100); 
				});
			}else{
				layer.alert('申请成功！', {icon: 1},function(){
					//刷新父窗口信息
					setTimeout("location.reload()",100); 
				});
			}
        }
    });
    /*	$.ajax({  
		url : contextPath + "/qrCodeController/batchQRCodeAtExcel",  
	    type : "post",  
	    data :  formData,  
	    timeout : 0,
	    contentType : false,  
        processData : false,  
	    success : function(result) {alert(result);
	    	//拆分出失败的企业信息和成功导入的企业个数
	    	var data = result.split("::");
	    	//获取失败的企业信息
			var entinfo = JSON.parse(data[0]);
			//获取成功导入的企业个数
			var entCount = data[1];
			var str;
			
			//关闭加载中
			HideLoading();
			
			if(entinfo.length != 0){
				str = "<table class='table table-condensed table-bordered table-hover tab'>";
				str += "<thead style='background:#FFF;'><tr>" 
					+ "<th colspan='2' style='color:#000;'>一共成功导入<font color='red'>" + entCount + "</font>家企业，有<font color='red'>" + entinfo.length + "</font>家企业申请失败。</th>" 
					+ "</tr></thead>";
				str += "<tbody><tr><td>企业名称</td><td>注册号</td></tr>";
				for(var i = 0; i < entinfo.length; i++){
					str += "<tr><td>" + entinfo[i].entname + "</td><td>" + entinfo[i].regno + "</td></tr>";
				}
				str += "</tbody></table>";
				
				//提示
				layer.open({
					type: 1,
					skin: 'layui-layer-rim', //加上边框
					area: ['420px', '240px'], //宽高
					content: str,
					cancel: function(index){ 
						//刷新父窗口信息
						setTimeout("location.reload()",100); 
					} 
				});
			}else if(0 == entCount){
				//全部导入失败
				layer.alert('导入失败！', {icon: 1}, function(){
					//刷新父窗口信息
					setTimeout("location.reload()",100); 
				});
			}else{
				layer.alert('申请成功！', {icon: 1},function(){
					//刷新父窗口信息
					setTimeout("location.reload()",100); 
				});
			}
	    } 			   
	}); */
}

//自动校验申请数量
function applyNumCheck(){
	//申请数量为数字
	var numRegex = /^[0-9]*$/;
	
	//如果申请数量不是数字的话，申请数量默认改为1
    if(!numRegex.test($("#applyNumber").val())){
    	$("#applyNumber").val("1");
    	layer.tips('申请数量只能输入正整数', '#applyNumber',{tips:[2, '#78BA32'],tipsMore: true});
    }
    //如果申请数量小于0的话，申请数量默认改为1
    if(0 >= $("#applyNumber").val()){
    	$("#applyNumber").val("1");
    }
}

//添加申请牌照(柜台资料保存)
function addguitai(){
	$("#applyNumber").val("1");
	$("#applyName").val("罗江浩（代办）");
	$("#cerno").val("罗江浩（代办）");
	$("#email").val("123@qq.com");
	$("#tel").val("13212345678");
	
	 //获取的参数
    var formdata = {
    	applyWay: 1,
    	applyWayCN: $("#applyWayCN").val(),
    	applyType: $("#applyTypeCN").val(),
    	applyTypeCN: $("#applyTypeCN").find("option:selected").text(),
    	applyNumber: $("#applyNumber").val(),
    	applyId: 0,
    	applyName: $("#applyName").val(),
    	cerType: $("#cerType").val(),
    	cerTypeCN: $("#cerType").find("option:selected").text(),
    	cerno: $("#cerno").val(),
    	position: $("#position").val(),
        address: $("#address").val(),
        tel: $("#tel").val(),
        email: $("#email").val(),
        sex: $("input[name='sex']:checked").val(),
        idcardCopyDis: $("input[name='idcardCopyDis']:checked").val(),
        businessLicenseDis: $("input[name='businessLicenseDis']:checked").val(),
        applicationsDis: $("input[name='applicationsDis']:checked").val(),
        entname: $("#entname").val(),
        pripid: $("#pripid").val(),
        regno: $("#regno").val(),
        uniscid: $("#uniscid").val(),
        provinceCode: $("#province").val(),
        remark: $("#remark").val(),
    }
    
    //开启正在加载
    ShowLoading();
   
    //访问请求
    $.ajax({
        url: contextPath + "/qrCodeController/createQRCode",
        type: "post",
        data: formdata,
        success: function (result) {
           //获取返回值处理业务
		   if("success" == result){
				layer.alert('申请成功！', {icon: 1}, function(){
					//关闭弹窗
					parent.layer.closeAll('iframe');
				});
           }else{
        	  layer.alert('申请失败！', {icon: 2}, function(index){
        		   layer.close(index);
        		   layer.closeAll('loading');
        	   });
           	}
        }
    })
	
}

//添加申请牌照
function add() {
	//邮箱正则
	var emailRegex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;	
	//手机正则
	var phoneRegex = /^[1][34578][0-9]{9}$/;	
	//身份证正则
	var idCardRegex = /(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
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
        layer.tips('电子邮箱不能为空', '#email',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }
    if (!emailRegex.test($("#email").val())) {
        layer.tips('电子邮箱格式不正确', '#email',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    } 
    if ($("#tel").val() == "") {
        layer.tips('手机号码不能为空', '#tel',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }
    if (!phoneRegex.test($("#tel").val())) {
        layer.tips('手机号码格式不正确', '#tel',{tips:[2, '#78BA32'],tipsMore: true});
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
    	applyWay: 1,
    	applyWayCN: $("#applyWayCN").val(),
    	applyType: $("#applyTypeCN").val(),
    	applyTypeCN: $("#applyTypeCN").find("option:selected").text(),
    	applyNumber: $("#applyNumber").val(),
    	applyId: 0,
    	applyName: $("#applyName").val(),
    	cerType: $("#cerType").val(),
    	cerTypeCN: $("#cerType").find("option:selected").text(),
    	cerno: $("#cerno").val(),
    	position: $("#position").val(),
        address: $("#address").val(),
        tel: $("#tel").val(),
        email: $("#email").val(),
        sex: $("input[name='sex']:checked").val(),
        idcardCopyDis: $("input[name='idcardCopyDis']:checked").val(),
        businessLicenseDis: $("input[name='businessLicenseDis']:checked").val(),
        applicationsDis: $("input[name='applicationsDis']:checked").val(),
        entname: $("#entname").val(),
        pripid: $("#pripid").val(),
        regno: $("#regno").val(),
        uniscid: $("#uniscid").val(),
        provinceCode: $("#province").val(),
        remark: $("#remark").val(),
    }
    
    //开启正在加载
    ShowLoading();
   
    //访问请求
    $.ajax({
        url: contextPath + "/qrCodeController/createQRCode",
        type: "post",
        data: formdata,
        success: function (result) {
           //获取返回值处理业务
		   if("success" == result){
				layer.alert('申请成功！', {icon: 1}, function(){
					//关闭弹窗
					parent.layer.closeAll('iframe');
				});
           }else{
        	  layer.alert('申请失败！', {icon: 2}, function(index){
        		   layer.close(index);
        		   layer.closeAll('loading');
        	   });
           	}
        }
    })
}

//编辑申请牌照
function edit() {
	//邮箱正则
	var emailRegex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;	
	//手机正则
	var phoneRegex = /^[1][34578][0-9]{9}$/;	
	//身份证正则
	var idCardRegex = /(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
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
        layer.tips('电子邮箱不能为空', '#email',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }
    if (!emailRegex.test($("#email").val())) {
        layer.tips('电子邮箱格式不正确', '#email',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }  
    if ($("#tel").val() == "") {
        layer.tips('手机号码不能为空', '#tel',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }
    if (!phoneRegex.test($("#tel").val())) {
        layer.tips('手机号码格式不正确', '#tel',{tips:[2, '#78BA32'],tipsMore: true});
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
    if(!numRegex.test($("#applyNumber").val())){
        layer.tips('申请数量只能是数字', '#applyNumber',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }
    if($("#applyNumber").val() <= 0){
        layer.tips('申请数量不能小于0', '#applyNumber',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }  
    
    //获取的参数
    var formdata = {
    	aid: $("#aid").val(),
    	applyType: $("#applyTypeCN").val(),
    	applyTypeCN: $("#applyTypeCN").find("option:selected").text(),
    	applyNumber: $("#applyNumber").val(),
    	applyName: $("#applyName").val(),
    	cerType: $("#cerType").val(),
    	cerTypeCN: $("#cerType").find("option:selected").text(),
    	cerno: $("#cerno").val(),
    	position: $("#position").val(),
        address: $("#address").val(),
        tel: $("#tel").val(),
        email: $("#email").val(),
        sex: $("input[name='sex']:checked").val(),
        idcardCopyDis: $("input[name='idcardCopyDis']:checked").val(),
        businessLicenseDis: $("input[name='businessLicenseDis']:checked").val(),
        applicationsDis: $("input[name='applicationsDis']:checked").val(),
        remark: $("#remark").val(),
    }
    
    //开启正在加载
    ShowLoading();
   
    //访问请求
    $.ajax({
        url: contextPath + "/qrCodeController/updateQRCodeApply",
        type: "post",
        data: formdata,
        success: function (result) {
           //获取返回值处理业务
           if("success" == result){
        	   layer.alert('编辑成功！', {icon: 1},function(){
        		   //刷新父窗口信息
         		   parent.setTimeout("location.reload()",100); 
                   //关闭弹窗
                   parent.layer.closeAll('iframe');
        	   });
           }else{
        	   layer.alert('编辑失败！', {icon: 2}, function(){
        		   layer.close(index);
        		   layer.closeAll('loading');
        	   })
           }
        }
    })
}

//***********************************牌照打印***********************************//

//选择反选
function selected(obj){
	if("展开" == obj.value){
		obj.value = "收缩";
		$("#selectedContent").show();
	}else{
		obj.value = "展开";
		$("#selectedContent").hide();
	}
}

//隐藏移除层
function selectedHide(){
	$("#selectedContent").hide();
	$("#showDown").val("展开");
}

//下载批次号
function uploadBatchNumber(batchNumber){
	window.open(contextPath + "/qrCodeController/downBatchNumber?batchNumber=" + batchNumber + ".zip");	
}

//***********************************牌照正常审核***********************************//

//打开牌照正常审核
function openQRCodeAudit(id){
	layer.open({
	  	type: 2,
	  	title: '牌照正常审核',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.6,  //笼罩层透明度
	  	area: ['700px', '80%'],  //大小
	  	content: contextPath + '/qrCodeController/queryQRCodeAuditById?id=' + id
	});
}

//编辑牌照正常审核
function edtQRCodeAudit(id,status) {
	$("#yessh").blur(); 
	$("#nosh").blur(); 
    //获取的参数
    var formdata = {
    	pid: id,
    	status: status,
    	code: $("#code").val() 
    }
    
    //开启正在加载
    ShowLoading();
    
    //访问请求
    $.ajax({
        url: contextPath + '/qrCodeController/updateAuditById',
        type: "post",
        data: formdata,
        success: function (result) {
        	//获取返回值处理业务
            if("success" == result){
         	   layer.alert('审核成功！', {icon: 1},function(){
         		   //刷新父窗口信息
         		   parent.setTimeout("location.reload()",100); 
                   //关闭弹窗
                   parent.layer.closeAll('iframe');
         	   });
            }else{
         	   layer.alert('审核失败！', {icon: 2})
            }
        }
    })
}

//***********************************牌照异常***********************************//

//打开牌照异常详情
function openAbnormalDetail(id){
	layer.open({
	  	type: 2,
	  	title: '牌照异常详情',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.6,  //笼罩层透明度
	  	area: ['800px', '80%'],  //大小
	  	content: contextPath + '/qrCodeController/queryQRCodeAbnormalByAid?aid=' + id + "&openType=" + 1
	});
}

//处理牌照异常
function openAbnormal(id){
	layer.open({
	  	type: 2,
	  	title: '牌照异常修复',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.6,  //笼罩层透明度
	  	area: ['800px', '80%'],  //大小
	  	content: contextPath + '/qrCodeController/queryQRCodeAbnormalByAid?aid=' + id + "&openType=" + 2
	});
}

//处理牌照异常
function edtQRCodeAbnormal(id) {
	//检查
	if(isEmpty($("#remark").val())){
        layer.tips('修复描述不能为空', '#remark',{tips:[3, '#78BA32'],tipsMore: true});
        return;
	}
    if ($("#uploadImgList").find(".uploadify-queue-item").length <= 0) {
        layer.tips('请上传图片', '#uploadImg',{tips:[3, '#78BA32'],tipsMore: true});
        return;
    }
	
    //获取的参数
    var formdata = {
    	remark: $("#remark").val(),
    	aid: id,
    	pid: $("#pid").val(),
    	pripid: $("#pripid").val(),
    	status: 0,
    	licensecode : $("#uploadImgList").find(".uploadify-queue-item").find("img").eq(0).attr("src").replace("data:image/png;base64,","")
    }
    
    //开启正在加载
    ShowLoading();
    
    //访问请求
    $.ajax({
        url: contextPath + '/qrCodeController/updateQRCodeAbnormal',
        type: "post",
        data: formdata,
        success: function (result) {
        	//获取返回值处理业务
            if("success" == result){
         	   layer.alert('处理成功！', {icon: 1},function(){
         		   //刷新父窗口信息
         		   parent.setTimeout("location.reload()",100); 
                   //关闭弹窗
                   parent.layer.closeAll('iframe');
         	   });
            }else{
         	   layer.alert('处理失败！', {icon: 2})
            }
        }
    })
}

//***********************************牌照入库***********************************//

//添加牌照入库信息
function addQRCodeStorage(id,num,status,stStatus) {
    //获取的参数
    var formdata = {
    	pid : id,			//牌照打印ID
    	number : num,		//打印数量
    	status : status,	//打印状态
    	stStatus : stStatus	//牌照入库确认状态
    }
    
    //是否处理
    layer.confirm('是否入库？', {icon: 3, title:'提示'}, function(index){
        //开启正在加载
        ShowLoading();
        
	    //访问请求
	    $.ajax({
	        url: contextPath + '/qrCodeController/insertQRCodeStorage',
	        type: "post",
	        data: formdata,
	        success: function (result) {
	        	//获取返回值处理业务
	            if("success" == result){
	         	   layer.alert('入库成功！', {icon: 1},function(){
	         		   //刷新父窗口信息
	         		   setTimeout("location.reload()",100); 
	                   //关闭弹窗
	         		   parent.layer.closeAll();
	         	   });
	            } else if("repeat" == result) {
	         	   layer.alert('牌子已经入库！入库前请刷新页面！', {icon: 2},function(){
	         		   //刷新父窗口信息
	         		   setTimeout("location.reload()",100);
	                   //关闭弹窗
	         		   parent.layer.closeAll();
	         	   });
	            } else {
	         	   layer.alert('入库失败！', {icon: 2},function(){
	         		   //刷新父窗口信息
	         		   setTimeout("location.reload()",100);
	                   //关闭弹窗
	         		   parent.layer.closeAll();
	         	   });
	            }
	        }
	    })
	    layer.close(index);
    });
}

//***********************************牌照领取通知***********************************//

//发送通知
function sendNotice() {
	var noticeInfo = "";
	for(var i = 0; i < document.getElementsByName('ids').length; i++){
		if(document.getElementsByName('ids')[i].checked){
		  	if(noticeInfo == '') {
		  		noticeInfo += document.getElementsByName('ids')[i].value;
		  	}else {
		  		noticeInfo += '::' + document.getElementsByName('ids')[i].value;
		  	}
		}
	}
	
	//判断是否选择了
	if(noticeInfo == "" || noticeInfo.length == 0){				
        layer.tips('请至少选择一个', '#zcheckbox',{tips:[3, '#78BA32'],tipsMore: true});
        return;
	}
	
    //获取的参数
    var formdata = {
    	noticeInfo: noticeInfo,		//打印编号ID,通知电话,通知邮箱,企业名称，注册号
    	status : 6					//打印状态
    }
    
    //确认框
    layer.confirm('是否发送通知？', {icon: 3, title:'提示'}, function(index){ 
	    //开启正在加载
	    ShowLoading();
	    
	    //访问请求
	    $.ajax({
	        url: contextPath + '/qrCodeController/sendNotice',
	        type: "post",
		    timeout : 0,
	        data: formdata,
	        success: function (result) {
	        	//通知失败
	        	if("fail" == result){
	         	   layer.alert('通知失败！', {icon: 1},function(){
	         		   //刷新父窗口信息
	         		   setTimeout("location.reload()",100); 
	         	   });
	        	}
	        	
		    	//获取数据结果集
				var data = JSON.parse(result);
				var str;
				
				//关闭加载中
				HideLoading();
				
				if(data.length != 0){
					str = "<table class='table table-condensed table-bordered table-hover tab'>";
					str += "<thead style='background:#FFF;'><tr><th colspan='2' style='color:#000;'>一共有<font color='red'>" + data.length + "</font>条通知失败记录</th></tr></thead>";
					str += "<tbody><tr><td>企业名称</td><td>注册号</td></tr>";
					for(var i = 0; i < data.length; i++){
						str += "<tr><td>" + data[i].entname + "</td><td>" + data[i].regno + "</td></tr>";
					}
					str += "</tbody></table>";
					
					//提示
					layer.open({
						type: 1,
						skin: 'layui-layer-rim', //加上边框
						area: ['420px', '240px'], //宽高
						content: str,
						cancel: function(index){ 
							//刷新父窗口信息
							setTimeout("location.reload()",100); 
						} 
					});
				}else{
	         	   layer.alert('通知成功！', {icon: 1},function(){
	         		   //刷新父窗口信息
	         		   setTimeout("location.reload()",100); 
	         	   });
				}
	        }
	    })
	    layer.close(index);
    });
}

//打开牌照通知详情页面
function openNoticeDet(aid, pid,pripid){
	layer.open({
	  	type: 2,
	  	title: '牌照领取通知',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: true,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.6,  //笼罩层透明度
	  	area: ['860px', '80%'],  //大小
	  	content: contextPath + '/qrCodeController/queryQRCodeById?aid=' + aid + "&pid=" + pid + "&pripid=" + pripid + "&openType=1"
	});
}

//添加领取记录
function receiveQRCode(id) {
	var receiveInfo = "";
	for(var i = 0; i < document.getElementsByName('ids').length; i++){
		if(document.getElementsByName('ids')[i].checked){
		  	if(receiveInfo == '') {
		  		receiveInfo += document.getElementsByName('ids')[i].value;
		  	}else {
		  		receiveInfo += '::' + document.getElementsByName('ids')[i].value;
		  	}
		}
	}
	
	//判断是否选择了
	if(receiveInfo == "" || receiveInfo.length == 0){				
        layer.tips('请至少选择一个', '#zcheckbox',{tips:[3, '#78BA32'],tipsMore: true});
        return;
	}
	
    //获取的参数
    var formdata = {
    	status : 7,			//打印状态
    	receiveInfo : receiveInfo	//打印编号，企业名称，注册号
    }
    
    //确认框
    layer.confirm('是否领取？', {icon: 3, title:'提示'}, function(index){ 
	    //开启正在加载
	    ShowLoading();
	    
	    //访问请求
	    $.ajax({
	        url: contextPath + '/qrCodeController/insertQRCodeReceive',
	        type: "post",
	        timeout : 0,
	        data: formdata,
	        success: function (result) {
	        	//获取数据结果集
	        	var data = JSON.parse(result);
				var str;
				
				//关闭加载中
				HideLoading();
				
				if(data.length != 0){
					str = "<table class='table table-condensed table-bordered table-hover tab'>";
					str += "<thead style='background:#FFF;'><tr><th colspan='2' style='color:#000;'>一共有<font color='red'>" + data.length + "</font>条领取失败记录</th></tr></thead>";
					str += "<tbody><tr><td>企业名称</td><td>注册号</td></tr>";
					for(var i = 0; i < data.length; i++){
						str += "<tr><td>" + data[i].entname + "</td><td>" + data[i].regno + "</td></tr>";
					}
					str += "</tbody></table>";
					
					//提示
					layer.open({
						type: 1,
						skin: 'layui-layer-rim', //加上边框
						area: ['420px', '240px'], //宽高
						content: str,
						cancel: function(index){ 
							//刷新父窗口信息
							setTimeout("location.reload()",100); 
						} 
					});
				}else{
	         	   layer.alert('领取成功！', {icon: 1},function(){
	         		   //刷新父窗口信息
	         		   setTimeout("location.reload()",100); 
	         	   });
				}	        	
	        }
	    })
	    layer.close(index);
    });
}

//全选反选
$(function() {
	//出库，制作全选反选
	$('table th input:checkbox').on('click', function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox').each(function(){
			this.checked = that.checked;
			//$(this).closest('tr').toggleClass('selected');
		});	
	});
	
	//牌照领取通知全选反选
//	$('#table2 th input:checkbox').on('click', function(){
//		var that = this;
//		$(this).closest('table').find('tr > td:first-child input:checkbox').each(function(){
//			var checkArr = $(this).val().split(",");
//			
//			//如果电话号码和邮箱为空就下一个。
//			if("" == checkArr[1] && "" == checkArr[2]){
//				return;
//			}
//			
//			this.checked = that.checked;
//			//$(this).closest('tr').toggleClass('selected');
//		});	
//		
//		if(this.checked){
//			layer.msg("没有电话号码和邮箱的无法选择");
//		}
//	});
	
	//检查领取通知的复选框是否有电话和邮箱
//	$('#table2 tbody td input:checkbox').click(function(){
//		var checkArr = $(this).val().split(",");
//		
//		//如果电话号码和邮箱为空就下一个。
//		if("" == checkArr[1] && "" == checkArr[2]){
//			layer.msg("没有电话号码和邮箱的无法选择");
//			return false;
//		}		
//	});
});