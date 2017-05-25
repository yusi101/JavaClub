//搜索
function search(){
    ShowLoading();
	$("#queryConfig").submit();
}

//回车键触发搜索按钮
$(document).keyup(function(event){
	if(event.keyCode == 13){
	  $("#queryConfig").submit();
	}
});

//多选反选
$(function() {
	$('table th input:checkbox').on('click' , function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox').each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});	
	});
});

//打开添加配置项页面
function openadd(){
	layer.open({
	  	type: 2,
	  	title: '添加配置项',
	  	scrollbar: false, //父页面是否有滚动条
		maxmin: false,//是否显示最大化按钮
	  	shadeClose: false,  //点击其他区域关闭弹窗
	  	shade: 0.5,  //笼罩层透明度
	  	area: ['600px', '520px'],  //大小
	  	content: contextPath + '/configController/toCreateConfig'
	});  			
}

//添加配置项
function add() {
	var tree = $.fn.zTree.getZTreeObj("treeDemo");
	var obj = tree.getCheckedNodes(true);
	
	//检验
    if ($("#name").val() == "") {
        layer.tips('名称不能为空', '#name',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }	
    if ($("#keyname").val() == "") {
        layer.tips('配置项键不能为空', '#keyname',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }   
    if ($("#value").val() == "") {
        layer.tips('配置项值不能为空', '#value',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }
    if ($("#remark").val() == "") {
        layer.tips('配置项描述不能为空', '#remark',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }
    if ($("#order").val() == "") {
        layer.tips('排序号不能为空', '#order',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }
    if (obj.length == 0 || obj == null) {
        layer.tips('父节点不能为空', '#treeDemo',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }   
    
    //获取的参数
    var formdata = {
    	name: $("#name").val(),
    	keyname: $("#keyname").val(),
    	value: $("#value").val(),
    	remark: $("#remark").val(),
    	order: $("#order").val(),
    	parent: obj[0].id,
    }
    
    //开启正在加载
    ShowLoading();
   
    //访问请求
    $.ajax({
        url: contextPath + "/configController/createConfig",
        type: "post",
        data: formdata,
        success: function (result) {
           //获取返回值处理业务
           if("success" == result){
        	   layer.alert('添加成功！', {icon: 1,closeBtn: 0},function(){
            	   //父页面刷新 必须在关闭iframe之前
                   parent.setTimeout("location.reload()",100); 
                   //关闭弹窗
                   parent.layer.closeAll('iframe');
        	   });
           }else{
        	   layer.alert('添加失败！', {icon: 2,closeBtn: 0})
           }
        }
    })
}

//修改配置项
function openupdate(id) {
   layer.open({
	  	type: 2,
	  	title: '修改配置项',
	  	scrollbar: false, 			//父页面是否有滚动条
	  	shadeClose: false,  		//点击其他区域关闭弹窗
	  	shade: 0.5,  				//笼罩层透明度
	  	area: ['600px', '520px'],  	//大小
	  	maxmin: true,				//是否显示最大化按钮
	  	content: contextPath + '/configController/configUpUI?id=' + id,
	}); 
}

//修改配置项
function update() {
	var tree = $.fn.zTree.getZTreeObj("treeDemo");
	var obj = tree.getCheckedNodes(true);
	
	//检验
    if ($("#name").val() == "") {
        layer.tips('名称不能为空', '#name',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }	  
    if ($("#value").val() == "") {
        layer.tips('配置项值不能为空', '#value',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }
    if ($("#remark").val() == "") {
        layer.tips('配置项描述不能为空', '#remark',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }
    if ($("#order").val() == "") {
        layer.tips('排序号不能为空', '#order',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }
    if (obj.length == 0 || obj == null) {
        layer.tips('父节点不能为空', '#treeDemo',{tips:[2, '#78BA32'],tipsMore: true});
        return;
    }
    
    //获取的参数
    var formdata = {
    	name: $("#name").val(),
    	keyname:$("#keyname").val(),
    	value: $("#value").val(),
    	remark: $("#remark").val(),
    	id : $("#id").val(),
    	order: $("#order").val(),
    	parent: obj[0].id
    }
    
    //开启正在加载
    ShowLoading();
   
    //访问请求
	$.ajax({
		type : 'post',
		url : contextPath + '/configController/configUp',
		data : formdata,
		success : function(data){
			if(data == "success"){
				layer.alert('修改成功！', {icon: 1,closeBtn: 0},function(){
					//父页面刷新 必须在关闭iframe之前
					parent.setTimeout("location.reload()",100); 
					//关闭弹窗
					parent.layer.closeAll('iframe');
				});
			}else{
				layer.alert('修改失败！', {icon: 2,closeBtn: 0})
			}
		}
	})
}

//删除配置项
function opendel() {
	var str = "";
	for(var i = 0; i < document.getElementsByName('ids').length; i++){
		if(document.getElementsByName('ids')[i].checked){
		  	if(str == '') {
		  		str += document.getElementsByName('ids')[i].value;
		  	}else {
		  		str += '::' + document.getElementsByName('ids')[i].value;
		  	}
		}
	}
	
	if(str == "" || str.length == 0){	
		layer.alert('请先选择要删除的数据！', {icon: 6});
		return;
	}
	
	layer.confirm('是否删除这个配置项？', {icon: 2, title:'配置项'}, function(index){
		$.ajax({
			type : 'post',
			url : contextPath + '/configController/configDel',
			data : {
				ids : str,
			},
			success : function(data){
				if(data == "success"){
					layer.alert('删除成功！', {icon: 1,closeBtn: 0}, function(){
						//刷新窗口信息
						setTimeout("location.reload()",100); 
					});
				}else{
					layer.alert('删除失败！', {icon: 1,closeBtn: 0}, function(){
						//刷新窗口信息
						setTimeout("location.reload()",100); 
					});
				}
			}
		});
		layer.close(index);
	});
}