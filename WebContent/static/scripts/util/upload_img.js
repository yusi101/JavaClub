/**
 * 
 * 上传图片的js
 * @author 李海涛
 * @version 1.0
 * @time 2016-09-21 15:35
 * 
 */
var locat = (window.location+'').split('/'); 

var hasFlash = function(){
	var isIE = !-[1,];
	if(isIE){
	    try{
	        var swf1 = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
	       /* layer.alert('安装了Flash');*/
	    }
	    catch(e){
	    	layer.alert('请确保浏览器已安装Adobe Flash Player插件，否则无法完成文件上传！');
	    }
	}
	else {
	    try{
	        var swf2 = navigator.plugins['Shockwave Flash'];
	        if(swf2){
	          /*  layer.alert('安装Flash');*/
	        }
	        else {
	        	layer.alert('请确保浏览器已安装Adobe Flash Player插件，否则无法完成文件上传！');
	        }
	    }
	    catch(e){
	    	layer.alert('请确保浏览器已安装Adobe Flash Player插件，否则无法完成文件上传！');
	    }
	}
}

$(function() {
	
	hasFlash();
	
	if('main'== locat[3]){
		locat =  locat[0]+'//'+locat[2];
	}else{
		locat =  locat[0]+'//'+locat[2]+'/'+locat[3];
	};
		$("#uploadImg").uploadify({
			'swf' : locat+'/static/plugins/uploadify-img/uploadify.swf', //flash
			'uploader' : locat+'/filesController/convertBase64',//上传地址
			'auto' : true,//是否自动上传 true or false
			'successTimeout' : 99999, //超时时间上传成功后，将等待服务器的响应时间。在此时间后，若服务器未响应，则默认为成功(因为已经上传,等待服务器的响应) 单位：秒
			'removeCompleted' : false,
			'buttonText' : '选择文件', //按钮上的文字
			'queueID' : 'uploadImgList',//文件选择后的容器div的id值
			'fileObjName' : 'pic',//将要上传的文件对象的名称 必须与后台controller中抓取的文件名保持一致   
			'uploader' : locat+'/filesController/convertBase64',//上传地址
			'width' : '100',//浏览按钮的宽度
			'height' : '25',//浏览按钮的高度
			'fileTypeDesc' : '支持的格式：',//在浏览窗口底部的文件类型下拉菜单中显示的文本
			'fileTypeExts' : '*.jpg;*.jpge;*.gif;*.png;*.bmp',//允许上传的文件后缀
			'fileSizeLimit' : '200KB',
			'queueSizeLimit' : 1, //允许上传的文件的最大数量。当达到或超过这个数字，onSelectError事件被触发。
			//返回一个错误，选择文件的时候触发
			'onSelectError' : function(file, errorCode,errorMsg) {
				switch (errorCode) {
				case -100:
					alert("上传的文件数量已经超出系统限制的"+ $('#uploadImg').uploadify('settings','queueSizeLimit') + "个文件！");
					break;
				case -110:
					layer.tips('文件 ['+ file.name+ '] 大小超出系统限制的'+ $('#uploadImg').uploadify('settings','fileSizeLimit')+ '大小！',
							  '#uploadImg',{tips:[3, '#78BA32'],tipsMore: true});
					break;
				case -120:
					layer.tips("文件 [" + file.name + "] 大小异常！",'#uploadImg',{tips:[3, '#78BA32'],tipsMore: true});
					break;
				case -130:
					layer.tips("文件 [" + file.name + "] 类型不正确！",'#uploadImg',{tips:[3, '#78BA32'],tipsMore: true});
					break;
				}
			},
			'onUploadSuccess' : function(file, data, response) {//上传到服务器，服务器返回相应信息到data里
				if($("#"+file.id).parent().find(".uploadify-queue-item").length>1){
					$("#"+file.id).parent().find(".uploadify-queue-item").eq(0).remove();
				}
				 $("#"+file.id).find(".data").html("<img src='data:image/png;base64,"+data+"'>");
			},
			'onUploadError' : function(file, errorCode,errorMsg, errorString) { //当单个文件上传出错时触发
				alert("上传失败" + errorMsg);
			}
		});
	});