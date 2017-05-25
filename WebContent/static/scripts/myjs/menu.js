/**
 * @descript (菜单)
 * @author 李海涛
 * @since 2016年9月18日下午3:03:35
 * @version 1.0
 * 
 */
$(document).ready(function(){
	$("#menuIconList").find("img").click(function(){
		$("#menuIconView").show();
		$("#menuIconView").parent().find(".fa").remove();
		$("#menuIconView").attr("src",$(this).attr("src"));
		$("#menuIcon").val($(this).attr("src"));
		$("#menuIcon").attr("title",$(this).attr("src"));
		chooseIcon();
	});
	$("#menuIconList > .fa").click(function(){
		$("#menuIconView").parent().find(".fa").remove();
		$("#menuIconView").hide();
		$("#menuIconView").parent().append('<i class="'+$(this).attr("class")+'"></i>');
		$("#menuIcon").val($(this).attr("class").replace("fa ",""));
		$("#menuIcon").attr("title",$(this).attr("class").replace("fa ",""));
		chooseIcon();
	});
	HideLoading();
});	

function collapseOne(){
	if($("#collapseOne").css("height")=="0px"){
		$("#collapseOne").css("height","auto");
	}else{
		$("#collapseOne").css("height","0px");
	}
}

function chooseIcon(){
	if($("#menuIconList").css("height")=="0px"){
		$("#menuIconList").css("height","auto");
	}else{
		$("#menuIconList").css("height","0px");
	}
}
HideLoading();

function createMenu(){
	//检验
	if (isEmpty($("#parentId").val())) {
		layer.tips('父级菜单不能为空', '#parentName',{tips:[3, '#78BA32']});
		return;
	}
	if (isEmpty($("#menuIconView").attr("src")) && $("#menuIconView").parent().find(".fa").length <= 0) {
		layer.tips('菜单图标不能为空', '#menuIcon',{tips:[3, '#78BA32']});
		return;
	}
	if (isEmpty($("#menuName").val())) {
		layer.tips('菜单名称不能为空', '#menuName',{tips:[3, '#78BA32']});
		return;
	}
	if (isEmpty($("#menuUrl").val())) {
		layer.tips('菜单路径不能为空', '#menuUrl',{tips:[3, '#78BA32']});
		return;
	}
	if (isEmpty($("#menuOrder").val())) {
		layer.tips('排序号不能为空', '#menuOrder',{tips:[3, '#78BA32']});
		return;
	}
	var num = /^[0][0-9]*$/;
	if (num.test($("#menuOrder").val())) {
		layer.tips('排序号只能为正整数', '#menuOrder',{tips:[3, '#78BA32']});
		return;
	}
	
	ShowLoading();
	
	$.ajax({
		type : "post",
		url : contextPath+"/menuController/createMenu",
		data : {
			"PARENT_ID" : $("#parentId").val(),
			"PARENT_NAME": $("#parentName").val(),
			"MENU_URL" : $("#menuUrl").val(),
			"MENU_ICON" : $("#menuIconView").parent().find(".fa").length >0 ? $("#menuIconView").parent().find(".fa").attr("class").replace("fa ","") : $("#menuIconView").attr("src"),
			"MENU_NAME" : $("#menuName").val(),
			"MENU_ORDER" : $("#menuOrder").val(),
			"PARENT_TYPE" : $("#parentType").val(),
		},
		dataType : "json",
		success : function(result){
			HideLoading();
			if(result.status=="success"){
				layer.alert('添加成功！', {icon: 1,closeBtn: 0},function(index){
					parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
					parent.layer.closeAll('iframe');//关闭弹窗
					ShowLoading();
				});
			}else{
				layer.alert('添加失败！', {icon: 2,closeBtn: 0});
			}
		}
	});
}

function eidtMenu(MENU_ID){
	//检验
    if (isEmpty($("#parentId").val())) {
        layer.tips('父级菜单不能为空', '#parentName',{tips:[3, '#78BA32']});
        return;
    }
    if (isEmpty($("#menuIconView").attr("src"))  && $("#menuIconView").parent().find(".fa").length <= 0) {
        layer.tips('菜单图标不能为空', '#menuIcon',{tips:[3, '#78BA32']});
        return;
    }
    if (isEmpty($("#menuName").val())) {
        layer.tips('菜单名称不能为空', '#menuName',{tips:[3, '#78BA32']});
        return;
    }
    if (isEmpty($("#menuUrl").val())) {
        layer.tips('菜单路径不能为空', '#menuUrl',{tips:[3, '#78BA32']});
        return;
    }
    if (isEmpty($("#menuOrder").val())) {
		layer.tips('排序号不能为空', '#menuOrder',{tips:[3, '#78BA32']});
		return;
	}
    var num = /^[0][0-9]*$/;
	if (num.test($("#menuOrder").val())) {
		layer.tips('排序号只能为正整数', '#menuOrder',{tips:[3, '#78BA32']});
		return;
	}
    
    ShowLoading();
    
    $.ajax({
    	type : "post",
    	url : contextPath+"/menuController/updateMenu",
    	data : {
    		"MENU_ID" : MENU_ID,
    		"PARENT_ID" : $("#parentId").val(),
    		"PARENT_NAME": $("#parentName").val(),
    		"MENU_URL" : $("#menuUrl").val(),
    		"MENU_ICON" : $("#menuIconView").parent().find(".fa").length >0 ? $("#menuIconView").parent().find(".fa").attr("class").replace("fa ","") : $("#menuIconView").attr("src"),
    		"MENU_NAME" : $("#menuName").val(),
    		"MENU_ORDER" : $("#menuOrder").val(),
    		"PARENT_TYPE" : $("#parentType").val(),
    	},
    	dataType : "json",
    	success : function(result){
    		HideLoading();
    		if(result.status=="success"){
    			layer.alert('修改成功！', {icon: 1,closeBtn: 0},function(index){
    				parent.setTimeout("location.reload()",100); //父页面刷新 必须在关闭iframe之前
    				parent.layer.closeAll('iframe');//关闭弹窗
    				ShowLoading();
    			});
    		}else{
    			layer.alert('修改失败！', {icon: 2,closeBtn: 0});
    		}
    	}
    });
}