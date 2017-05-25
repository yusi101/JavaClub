(function($){
$.fn.Huploadify = function(opts){
	var itemTemp = '<div id="${fileID}" class="uploadify-queue-item"><div class="uploadify-progress"><div class="uploadify-progress-bar"></div></div><span class="up_filename">${fileName}</span><div class="uploadify_tools"><div class="uploadifytoollist" ><ul><li class="uploadbtn"></li><li class="previewtn" ></li></ul></div><div class="delfilebtn"></div></div></div>';
	var defaults = {
		fileTypeExts:'*.*',//允许上传的文件类型，格式'*.jpg;*.doc'
		uploader:'',//文件提交的地址
		auto:false,//是否开启自动上传
		method:'post',//发送请求的方式，get或post
		multi:false,//是否允许选择多个文件
		formData:null,//发送给服务端的参数，格式：{key1:value1,key2:value2}
		fileObjName:'file',//在后端接受文件的参数名称，如PHP中的$_FILES['file']
		fileSizeLimit:2048,//允许上传的文件大小，单位KB
		showUploadedPercent:true,//是否实时显示上传的百分比，如20%
		showUploadedSize:false,//是否实时显示已上传的文件大小，如1M/2M
		buttonText:'选择文件',//上传按钮上的文字
		removeTimeout: 1000,//上传完成后进度条的消失时间
		itemTemplate:itemTemp,//上传队列显示的模板
		onUploadStart:null,//上传开始时的动作
		onUploadSuccess:null,//上传成功的动作
		onUploadComplete:null,//上传完成的动作
		onUploadError:null, //上传失败的动作
		onInit:null,//初始化时的动作
		onCancel:null//删除掉某个文件后的回调函数，可传入参数file
	}
		
	var option = $.extend(defaults,opts);
	
	//将文件的单位由bytes转换为KB或MB，若第二个参数指定为true，则永远转换为KB
	var formatFileSize = function(size,byKB){
		if (size> 1024 * 1024&&!byKB){
			size = (Math.round(size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
		}
		else{
			size = (Math.round(size * 100 / 1024) / 100).toString() + 'KB';
		}
		return size;
	}
	//根据文件序号获取文件
	var getFile = function(index,files){
		for(var i=0;i<files.length;i++){	   
		  if(files[i].index == index){
			  return files[i];
			}
		}
		return false;
	}
	
	//将输入的文件类型字符串转化为数组,原格式为*.jpg;*.png
	var getFileTypes = function(str){
		var result = [];
		var arr1 = str.split(";");
		for(var i=0,len=arr1.length;i<len;i++){
			result.push(arr1[i].split(".").pop());
		}
		return result;
	}
	var mimetypeMap = {
			zip : ['application/x-zip-compressed'],
			jpg : ['image/jpeg'],
			png : ['image/png'],
			gif : ['image/gif'],
			doc : ['application/msword'],
			xls : ['application/msexcel'],
			docx : ['application/vnd.openxmlformats-officedocument.wordprocessingml.document'],
			xlsx : ['application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'],
			ppt : ['application/vnd.ms-powerpoint '],
			pptx : ['application/vnd.openxmlformats-officedocument.presentationml.presentation'],
			mp3 : ['audio/mp3'],
			mp4 : ['video/mp4'],
			pdf : ['application/pdf']
		};

		//根据后缀名获得文件的mime类型
		var getMimetype = function(name){
			return mimetypeMap[name];
		}
		//根据配置的字符串，获得上传组件accept的值
		var getAcceptString = function(str){
			var types = getFileTypes(str);
			var result = [];
			for(var i=0,len=types.length;i<len;i++){
				var mime = getMimetype(types[i]);
				if(mime){
					result.push(mime);				
				}
			}
			return result.join(',');
		}
	var fileObj = null;
	
	this.each(function(){
		var _this = $(this);
		//先添加上file按钮和上传列表
		var instanceNumber = $('.uploadify').length+1;
		var inputStr = '<input id="select_btn_'+instanceNumber+'" class="selectbtn" style="display:none;" type="file" name="fileselect[]"';
		inputStr += option.multi ? ' multiple' : '';
		inputStr += ' accept="';
		inputStr += getAcceptString(option.fileTypeExts);
		inputStr += '"/>';
		inputStr += '<a id="file_upload_'+instanceNumber+'-button" href="javascript:void(0)" class="uploadify-button">';
		inputStr += option.buttonText;
		inputStr += '</a>';
		var uploadFileListStr = '<div id="file_upload_'+instanceNumber+'-queue" class="uploadify-queue"></div>';
		_this.append(inputStr+uploadFileListStr);	
		
		
		//创建文件对象
	   fileObj = {
		  fileInput: _this.find('.selectbtn'),				//html file控件
		  uploadFileList : _this.find('.uploadify-queue'),
		  url: option.uploader,						//ajax地址
		  fileFilter: [],					//过滤后的文件数组
		  filter: function(files) {		//选择文件组的过滤方法
			  var arr = [];
			  var typeArray = getFileTypes(option.fileTypeExts);
			  if(typeArray.length>0){
				  for(var i=0,len=files.length;i<len;i++){
				  	var thisFile = files[i];
				  		if(parseInt(formatFileSize(thisFile.size,true))>option.fileSizeLimit){
				  			alert('文件'+thisFile.name+'大小超出限制！');
				  			continue;
				  		}
						if($.inArray(thisFile.name.split('.').pop(),typeArray)>=0){
							arr.push(thisFile);	
						}
						else{
							alert('文件'+thisFile.name+'类型不允许！');
						}  	
					}	
				}
			  return arr;  	
		  },
		  //文件选择后
		  onSelect: function(files){
				for(var i=0,len=files.length;i<len;i++){
					var file = files[i];
					//处理模板中使用的变量
					var html="";
					var reader = new FileReader(); 
					
		            //监听文件读取结束后事件  
		            reader.onloadend = function (e) {
		            	 $('#fileupload_'+instanceNumber+'_'+file.index).css("background","url("+ e.target.result +") no-repeat center").css("background-size","100% 200px").css("width","250px").css("height","200px");
		            }; 
		            reader.readAsDataURL(file);  
		            var $html = $(option.itemTemplate.replace(/\${fileID}/g,'fileupload_'+instanceNumber+'_'+file.index).replace(/\${imageSrc}/g,html).replace(/\${fileName}/g,file.name).replace(/\${fileSize}/g,formatFileSize(file.size)).replace(/\${instanceID}/g,_this.attr('id')));
					//如果是自动上传，去掉上传按钮
					if(option.auto){
						$html.find('.uploadbtn').remove();
					}
					this.uploadFileList.append($html);
					
					//判断是否显示已上传文件大小
					if(option.showUploadedSize){
						var num = '<span class="progressnum"><span class="uploadedsize">0KB</span>/<span class="totalsize">${fileSize}</span></span>'.replace(/\${fileSize}/g,formatFileSize(file.size));
						$html.find('.uploadify-progress').after(num);
					}
					
					//判断是否显示上传百分比	
					if(option.showUploadedPercent){
						var percentText = '<span class="up_percent">0%</span>';
						$html.find('.uploadify-progress').after(percentText);
					}
					//判断是否是自动上传
					if(option.auto){
						this.funUploadFile(file);
					}
					else{
						//如果配置非自动上传，绑定上传事件
					 	$html.find('.uploadbtn').on('click',(function(file){
					 			return function(){fileObj.funUploadFile(file);}
					 		})(file));
					}
					//为删除文件按钮绑定删除文件事件
			 		$html.find('.delfilebtn').on('click',(function(file){
					 			return function(){fileObj.funDeleteFile(file.index);}
					 		})(file));
			 	}

			 
			},				
		  onProgress: function(file, loaded, total) {
				var eleProgress = _this.find('#fileupload_'+instanceNumber+'_'+file.index+' .uploadify-progress');
				var percent = (loaded / total * 100).toFixed(2) +'%';
				if(option.showUploadedSize){
					eleProgress.nextAll('.progressnum .uploadedsize').text(formatFileSize(loaded));
					eleProgress.nextAll('.progressnum .totalsize').text(formatFileSize(total));
				}
				if(option.showUploadedPercent){
					eleProgress.nextAll('.up_percent').text(percent);	
				}
				eleProgress.children('.uploadify-progress-bar').css('width',percent);
	  	},		//文件上传进度

		  /* 开发参数和内置方法分界线 */
		  
		  //获取选择文件，file控件
		  funGetFiles: function(e) {	  
			  // 获取文件列表对象
			  var files = e.target.files;
			  //继续添加文件
			  files = this.filter(files);
			  for(var i=0,len=files.length;i<len;i++){
			  	this.fileFilter.push(files[i]);	
			  }
			  this.funDealFiles(files);
			  return this;
		  },
		  
		  //选中文件的处理与回调
		  funDealFiles: function(files) {
			  var fileCount = _this.find('.uploadify-queue .uploadify-queue-item').length;//队列中已经有的文件个数
			  for(var i=0,len=files.length;i<len;i++){
				  files[i].index = ++fileCount;
				  files[i].id = files[i].index;
				  }
			  //执行选择回调
			  this.onSelect(files);
			  
			  return this;
		  },
		  
		  //删除对应的文件
		  funDeleteFile: function(index) {
			  for (var i = 0,len=this.fileFilter.length; i<len; i++) {
					  var file = this.fileFilter[i];
					  if (file.index == index) {
						  this.fileFilter.splice(i,1);
						  
						  _this.find('#fileupload_'+instanceNumber+'_'+index).removeAttr("style").fadeOut();
						  option.onCancel&&option.onCancel(file);	
						  break;
					  }
			  }
			  return this;
		  },
		  
		  //文件上传
		  funUploadFile: function(file) {
			  var xhr = false;
			  try{
				 xhr=new XMLHttpRequest();//尝试创建 XMLHttpRequest 对象，除 IE 外的浏览器都支持这个方法。
			  }catch(e){	  
				xhr=ActiveXobject("Msxml12.XMLHTTP");//使用较新版本的 IE 创建 IE 兼容的对象（Msxml2.XMLHTTP）。
			  }
			  
			  if (xhr.upload) {
				  // 上传中
				  xhr.upload.addEventListener("progress", function(e) {
					  fileObj.onProgress(file, e.loaded, e.total);
				  }, false);
	  
				  // 文件上传成功或是失败
				  xhr.onreadystatechange = function(e) {
					  if (xhr.readyState == 4) {
						  if (xhr.status == 200) {
							  //校正进度条和上传比例的误差
							  var thisfile = _this.find('#fileupload_'+instanceNumber+'_'+file.index);
							  thisfile.find('.uploadify-progress-bar').css('width','100%');
								option.showUploadedSize&&thisfile.find('.uploadedsize').text(thisfile.find('.totalsize').text());
								option.showUploadedPercent&&thisfile.find('.up_percent').text('100%');

							  option.onUploadSuccess&&option.onUploadSuccess(file, xhr.responseText);
							  //在指定的间隔时间后删掉进度条
							  setTimeout(function(){
							  	_this.find('#fileupload_'+instanceNumber+'_'+file.index).fadeOut();
							  },option.removeTimeout);
						  } else {
							  option.onUploadError&&option.onUploadError(file, xhr.responseText);		
						  }
						  option.onUploadComplete&&option.onUploadComplete(file,xhr.responseText);
						  //清除文件选择框中的已有值
						  fileObj.fileInput.val('');
					  }
				  };
	  
	  			option.onUploadStart&&option.onUploadStart();	
				  // 开始上传
				  xhr.open(option.method, this.url, true);
				  xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
				  var fd = new FormData();
				  fd.append(option.fileObjName,file);
				  if(option.formData){
				  	for(key in option.formData){
				  		fd.append(key,option.formData[key]);
				  	}
				  }
				  
				  xhr.send(fd);
			  }	
			  
				  
		  },
		  
		  init: function() {	  
			  //文件选择控件选择
			  if (this.fileInput.length>0) {
				  this.fileInput.change(function(e) { 
				  	fileObj.funGetFiles(e); 
				  });	
			  }
			  
			  //点击上传按钮时触发file的click事件
			  _this.find('.uploadify-button').on('click',function(){
				  _this.find('.selectbtn').trigger('click');
				});
			  
			  option.onInit&&option.onInit();
		  }
  	};

		//初始化文件对象
		fileObj.init();
	}); 
	
	var returnObj =  {
			stop : function(){
	  		fileObj.uploadOver = false;
				fileObj.uploadAllowed = false;
			},
			upload : function(fileIndex){
				if(fileIndex === '*'){
					for(var i=0,len=fileObj.fileFilter.length;i<len;i++){
						fileObj.funUploadFile(fileObj.fileFilter[i]);
					}
				}
				else{
					var file = getFile(fileIndex,fileObj.fileFilter);
					file && fileObj.funUploadFile(file);	
				}
			},
			cancel : function(fileIndex){
				if(fileIndex === '*'){
					for(var i=0,len=fileObj.fileFilter.length;i<len;i++){
						fileObj.funDeleteFile(++i);
					}
				}
				else{
					fileObj.funDeleteFile(fileIndex);	
				}
			},
			disable : function(instanceID){
				var parent = instanceID ? $('file_upload_'+instanceID+'-button') : $('body');
				parent.find('.uploadify-button').css('background-color','#888').off('click');
			},
			ennable : function(instanceID){
				//点击上传按钮时触发file的click事件
				var parent = instanceID ? $('file_upload_'+instanceID+'-button') : $('body');
			  	parent.find('.uploadify-button').css('background-color','#707070').on('click',function(){
				parent.find('.selectbtn').trigger('click');
				});
			},
			destroy : function(){
				fileObj.container.html('');
			},
			settings : function(name,value){
				if(arguments.length==1){
					return option[name];
				}
				else{
					if(name=='formData'){
						option.formData = $.extend(option.formData, value);
					}
					else{
						option[name] = value;
					}
				}
			},
			Huploadify : function(){
				var method = arguments[0];
				if(method in this){
					Array.prototype.splice.call(arguments, 0, 1);
					this[method].apply(this[method], arguments);
				}
			}
		};
	return returnObj;
}	

})(jQuery)