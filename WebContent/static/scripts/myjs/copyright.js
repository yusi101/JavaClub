HideLoading();
function queryCopyrightinfo(){
	ShowLoading();
	$("#workCopyrightName").val($("#copyrightName").val());
	$("#queryCopyrightInfo").submit();
}

//没有加载时根据不同的选择内容，有不同的请求
window.onload=function(){
	var ok = $("#selectchoose option:selected").val();
	if(ok == 0){
		$("#ruanjian").attr("style","display:none;");
		$("#NOruanjian").attr("style","display:block;");
		$("#queryCopyrightInfo").attr("action", contextPath + "/copyrightController/queryWorkCopyrightinfo");
	
	}else if(ok == 1){
		$("#NOruanjian").attr("style","display:none;");
		$("#ruanjian").attr("style","display:block;");
		$("#queryCopyrightInfo").attr("action",contextPath + "/copyrightController/queryCopyrightinfo");
		
	}
}

//根据选择不同，input框的意义不同
function chooseType(){
	var ok = $("#selectchoose option:selected").val();
	if(ok == 0){
		$("#queryCopyrightInfo").attr("action", contextPath + "/copyrightController/queryWorkCopyrightinfo");
	}else if(ok == 1){
		$("#queryCopyrightInfo").attr("action",contextPath + "/copyrightController/queryCopyrightinfo");
	}
}