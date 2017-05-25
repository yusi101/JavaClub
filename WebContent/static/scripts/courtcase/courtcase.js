HideLoading();
function queryBrandinfo(){
	 ShowLoading();
	$("#queryBrandInfo").submit();
}

//打开添加Demo(Demo请修改为具体的模块名称)页面
function openadd(){
	layer.open({
	  	type: 2,
	  	title: '添加商标',
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	area: ['550px', '70%'],  //大小
	  	content: contextPath + '/pages/brand/brand_add.jsp'
	});  			
}

//添加Demo（Demo请修改具体模块名称）
function add() {
	//检验
    if ($("#userName").val() == "") {
        layer.tips('不能为空', '#userName');
        return;
    }
    if ($("#Chinese").val() == "") {
        layer.tips('不能为空', '#Chinese');
        return;
    }
    if ($("#Math").val() == "") {
        layer.tips('不能为空', '#Math');
        return;
    }
    if ($("#English").val() == "") {
        layer.tips('不能为空', '#English');
        return;
    }
    
    //正在加载
    var index = layer.load(2); 
    
    //获取的参数
    var formdata = {
        userName: $("#userName").val(),
        Chinese: $("#Chinese").val(),
        Math: $("#Math").val(),
        English: $("#English").val()
    }
   
    //访问请求
    $.ajax({
        url: "../Json/Index.aspx",
        timeout: 3000,
        dataType: "json",
        type: "post",
        data: formdata,
        success: function (data) {
           //获取返回值处理业务
           
           //关闭加载
           layer.close(index); 
        }
    })
}

//打开编辑Demo(Demo请修改为具体的模块名称)页面
function openedt(){
	layer.open({
	  	type: 2,
	  	title: '编辑Demo(Demo请修改为具体的模块名称)',
	  	shadeClose: false, //点击其他区域关闭弹窗
	  	shade: 0.8,
	  	area: ['550px', '70%'],
	  	content: contextPath + '/pages/brand/brand_edt.html'
	});  			
}

//编辑Demo（Demo请修改具体模块名称）
function edt() {
    if ($("#userName").val() == "") {
        layer.tips('不能为空', '#userName');
        return;
    }
    if ($("#Chinese").val() == "") {
        layer.tips('不能为空', '#Chinese');
        return;
    }
    if ($("#Math").val() == "") {
        layer.tips('不能为空', '#Math');
        return;
    }
    if ($("#English").val() == "") {
        layer.tips('不能为空', '#English');
        return;
    }
    //正在加载
    var index = layer.load(2); 
    
    //获取的参数
    var formdata = {
        flag: "edt",
        userName: $("#userName").val(),
        Chinese: $("#Chinese").val(),
        Math: $("#Math").val(),
        English: $("#English").val()
    }
    
    //访问请求
    $.ajax({
        url: "../Json/Index.aspx",
        timeout: 300000,
        dataType: "json",
        type: "post",
        data: formdata,
        success: function (data) {
           //获取返回值处理业务
           
           //关闭加载
           layer.close(index); 
        }
    })
}

//删除Demo（Demo请修改具体模块名称）
function del(userName) {
    //询问框
    layer.confirm('您确定要删除？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
    		//执行删除
        $.ajax({
            url: "../Json/Index.aspx",
            timeout: 300000,
            dataType: "json",
            type: "post",
            data: { "flag": "del", "userName": userName },
            success: function (data) {
                layer.alert(data.msg);
                load(curr);
            }
        })
    }, function () {
    		// 取消删除
        // layer.close();
    });

}

//Demo详情（Demo请修改具体模块名称）
function opendet(courtcaseId) {
	var str= "";
	layer.open({
	  	type: 2,
	  	title: '失信被执行人详情',
	  	shadeClose: true, //点击其他区域关闭弹窗
	  	shade: 0.8,
	  	area: ['100%', '100%'],
	  	content: contextPath + '/courtcaseController/findCourtinfo?courtcaseId='+courtcaseId+'&iname='+str
	});  
}

