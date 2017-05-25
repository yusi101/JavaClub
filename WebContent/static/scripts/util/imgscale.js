function imgScale(obj){
	//var url = obj.src;
	var url = $(obj).attr("src");
	
	//获取页面的高度和宽度
	var sWidth = document.body.scrollWidth;
	//获取整个页面高度，包括滚动的页面
	var sHeight = Math.max(document.body.scrollHeight,document.documentElement.scrollHeight);
	
	//获取页面的可视区域高度和宽度
	var wHeight = document.documentElement.clientHeight;
	var wWidth = document.documentElement.clientWidth;
	
	var oMask = document.createElement("div");	//createElement创建一个元素
		oMask.id = "mask";
		oMask.style.height = sHeight + "px";
		oMask.style.width = sWidth + "px";
		document.body.appendChild(oMask);		//添加节点方法
	var oImg = document.createElement("img");
		oImg.id = "ImageScale";
		oImg.src = url;
		oImg.style.display = "none";
		document.body.appendChild(oImg);
	
	//设置图片淡出效果
	$("#ImageScale").fadeIn("slow");
	
	//获取登陆框的宽和高
	var dHeight = oImg.offsetHeight;
	var dWidth = oImg.offsetWidth;
	//设置登陆框的left和top
	oImg.style.left = (sWidth-dWidth)/2 + "px";
	oImg.style.top = (wHeight-dHeight)/2 + "px";
	
	//点击关闭按钮
	var oClose = document.getElementById("ImageScale");
		//点击登陆框以外的区域也可以关闭登陆框
		oClose.onclick = oMask.onclick = function(){
			document.body.removeChild(oImg);	//移除子节点	
			document.body.removeChild(oMask);	//移除子节点
		};
};