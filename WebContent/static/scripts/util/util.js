//选项卡函数
$(".option").click(function(){
	$(".option").css("border-bottom","2px solid #CCC");
	$(".option").css("color","#666");
	$(".option").css("font-weight","300");
	$(".optionContent").hide();
	
	$(".option").eq($(this).index()).css("border-bottom","2px solid #00a8ea");
	$(".option").eq($(this).index()).css("color","#00a8ea");
	$(".option").eq($(this).index()).css("font-weight","bold");
	$(".optionContent").eq($(this).index()).show();	
});

//选项卡函数2
$(".option2").click(function(){
	$(".option2").css("border-bottom","2px solid #CCC");
	$(".option2").css("color","#666");
	$(".option2").css("font-weight","300");
	$(".optionContent").hide();
	
	$(".option2").eq($(this).index()).css("border-bottom","2px solid #00a8ea");
	$(".option2").eq($(this).index()).css("color","#00a8ea");
	$(".option2").eq($(this).index()).css("font-weight","bold");
	$(".optionContent").eq($(this).index()).show();	
});

//当前时间
function currentTime(){
	var d = new Date(),
	str = '';
    str += d.getFullYear()+'-';
    str += d.getMonth() + 1+'-';
    str += d.getDate()+' ';
    str += d.getHours()+':'; 
    str += d.getMinutes()+':';  
    str += d.getSeconds()+'';  
    return str; 
}

//根据参数名称得到地址栏上的参数 
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(decodeURI(r[2])); return null; //返回参数值
}


//截取前6位，然后后面的变成*代替
function encryptionStr(str, num){
   	var pledblicno = str.substr(0, num);
   	
   	for(var i=0; i<str.length-num; i++){
	   	pledblicno += "*";
  	}
   	
   	return pledblicno;
}

//数组去重复
function unique(arr) {
    var result = [], hash = {};
    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
        if (!hash[elem]) {
            result.push(elem);
            hash[elem] = true;
        }
    }
    
    return result;
}


function isEmpty(value) {
    if (value == null || value == "" || value == "undefined" || value == undefined || value == "null" || value == "(null)" || value == 'NULL' || typeof (value) == 'undefined') {
        return true;
    } else {
        value = value + "";
        value = value.replace(/\s/g, "");
        if (value == "") {
            return true;
        }
        return false;
    }
}
function isNotEmpty(value) {
    if (value == null || value == "" || value == "undefined" || value == undefined || value == "null" || value == "(null)" || value == 'NULL' || typeof (value) == 'undefined') {
        return false;
    } else {
        value = value + "";
        value = value.replace(/\s/g, "");
        if (value == "") {
            return false;
        }
        return true;
    }
}
