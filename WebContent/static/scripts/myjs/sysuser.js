HideLoading();
//邮箱正则
var emailRegex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;	
//手机正则
var phoneRegex = /^[1][34578][0-9]{9}$/;	
//不准输入数字
var isChinese = /^\d*([\u4e00-\u9fa5]|[a-zA-Z])+\d*$/;

//删除用户
function deleteuser(USERID){
	layer.confirm('是否删除？', {icon: 3, title:'提示'}, function(index){ 
		//执行删除
		$.ajax({
			url : contextPath + '/userController/updateUserByid',
			timeout : 300000,
			dataType : "json",
			type : "post",
			data : {
				"USERID" : USERID
			},
			success : function(data) {
				if(data.status == "success"){
					layer.alert("删除成功",{icon: 1,closeBtn: 0},function(index){
						setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
						layer.closeAll('iframe');//关闭弹窗
					});
				}else{
					layer.alert("删除失败",{icon: 2,closeBtn: 0},function(index){
						setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
						layer.closeAll('iframe');//关闭弹窗
					});
				}
			}
		})
		layer.close(index);
	});
}

//打开添加用户页面
function openadd(){
	layer.open({
		type: 2,
		title: '添加用户信息',
		scrollbar: false, 	//父页面是否有滚动条
		shadeClose: false,  //点击其他区域关闭弹窗
		shade: 0.5,  		//笼罩层透明度
		maxmin: true,		//是否显示最大化按钮
		area: ['700px', '70%'],  //大小
		content: contextPath + '/userController/toCreateSysUser'
	}); 
}

//添加用户
function add(){
	var tree = $.fn.zTree.getZTreeObj("treeDemo");
	var obj = tree.getCheckedNodes(true);
	var sysRoleId = "";
	
	for(var i = 0; i < obj.length; i++){
		sysRoleId += obj[i].id + "::";
	}
	
	if (!isChinese.test($("#USERNAME").val())) {
		layer.tips('输入有误', '#USERNAME',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if (!isChinese.test($("#NAME").val())) {
		layer.tips('输入有误', '#NAME',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if (!emailRegex.test($("#EMAIL").val())) {
		layer.tips('邮箱的格式有误！', '#EMAIL',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if (!phoneRegex.test($("#PHONE").val())) {
		layer.tips('手机号码输入有误！', '#PHONE',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if (isEmpty($("#PASSWORD").val())) {
		layer.tips('密码不能为空', '#PASSWORD',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if(isEmpty($("#RPASSWORD").val())){
		layer.tips('确认密码不能为空', '#RPASSWORD',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if($("#RPASSWORD").val() != $("#PASSWORD").val()){
		layer.tips('密码和确认密码不符合', '#RPASSWORD',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
    if (isEmpty(sysRoleId)) {
        layer.tips('角色不能为空', '#treeDemo',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }  
	
	//正在加载
	var index = layer.load(2); 

	//获取的参数
	var formdata = {
		SYSROLEID: sysRoleId,
		NAME: $("#NAME").val(),
		USERNAME: $("#USERNAME").val(),
		PASSWORD: $("#PASSWORD").val(),
		REMARK: $("#REMARK").val(),
		SEX: $("input[name='SEX']:checked").val(),
		EMAIL: $("#EMAIL").val(),
		PHONE: $("#PHONE").val(),
		STATUS: 0
	}
	
	//访问请求
	$.ajax({
		url: contextPath + '/userController/insertUser',
		type: "post",
		data: formdata,
		success: function (data) {
			if(data == "success"){
				layer.alert("添加成功",{icon: 1,closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();
				});
			}else if(data == "message"){
				layer.alert("此用户已经被注册！",{icon: 2,closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();
				});
			}else{
				layer.alert("添加失败",{icon: 2,closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();
				});
			}
		}
	})
}

//查询某个用户
function query(userid){
	layer.open({
		type: 2,
		title: '修改用户信息',
		scrollbar: false, //父页面是否有滚动条
		shadeClose: false,  //点击其他区域关闭弹窗
		shade: 0.5,  //笼罩层透明度
		maxmin: true,//是否显示最大化按钮
		area: ['700px', '70%'],  //大小
		content:  contextPath + '/userController/queryUserById?USERID='+userid
	}); 
}

//编辑用户
function edt(userid){
	var tree = $.fn.zTree.getZTreeObj("treeDemo");
	var obj = tree.getCheckedNodes(true);
	var sysRoleId = "";
	
	for(var i = 0; i < obj.length; i++){
		sysRoleId += obj[i].id + "::";
	}
	
	if (!isChinese.test($("#NAME").val())) {
		layer.tips('输入有误', '#NAME',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if (!phoneRegex.test($("#PHONE").val())) {
		layer.tips('手机号码输入有误！', '#PHONE',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
	if (!emailRegex.test($("#EMAIL").val())) {
		layer.tips('邮箱输入有误！', '#EMAIL',{tips:[2, '#78BA32'],tipsMore: true});
		return;
	}
    if (isEmpty(sysRoleId)) {
        layer.tips('父节点不能为空', '#treeDemo',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    } 	

	//获取的参数
	var formdata = {
			SYSROLEID: sysRoleId,
			USERID :userid,
			NAME: $("#NAME").val(),
			USERNAME: $("#USERNAME").val(),
			PASSWORD: $("#PASSWORD").val(),
			REMARK: $("#REMARK").val(),
			SEX: $("input[name='SEX']:checked").val(),
			EMAIL: $("#EMAIL").val(),
			PHONE: $("#PHONE").val(),
	}
	
	//开启正在加载
	ShowLoading();
	
	//访问请求
	$.ajax({
		url: contextPath + '/userController/updateUserById',
		type: "post",
		data: formdata,
		success: function (data) {
			if(data == "success"){
				layer.alert("修改成功！",{icon: 1,closeBtn: 0},function(){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
				});
			}else{
				layer.alert("修改失败！",{icon: 2,closeBtn: 0},function(){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
				});
			}
		}
	})
}