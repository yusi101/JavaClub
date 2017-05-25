<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<%@ include file="../system/allresources.jsp"%> 
<title>年报定时推送</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">年报定时推送</h4>
			</div>
			 <form action="${pageContext.request.contextPath}/annualNotificationController/queryannualauto" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<div style="width: 100%; float:left ;">
						<button class="btn btn-search"  onclick="sendNotice();" >一键发送</button>
				</div>
				
				<div class="clren"></div>
			</form> 
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
						<th width="20%">名称</th>
						<th width="20%">已发送</th>
						<th width="20%">未发送</th>
						<th width="20%">需发送</th>
						<th width="20%">年度</th>
					</tr>
				</thead>
				<tbody id="tbody">
						<tr>
							<td>年报 </td>
							<td>${anList2 }</td>
							<td>${anList1 }</td>
							<td>${anList3 }</td>
							<td>${pd.year }</td>
						</tr>
				</tbody>				
			</table>
		</div>
	</div>
	<script type="text/javascript">
	function btnChange(value) {
		if ($("#select").val() == 2) {
			$("#startTime").hide();
			$("#startTime").attr("name", "startDay");
			$("#startTime").val("");
			$("#endTime").hide();
			$("#endTime").attr("name", "endDay");
			$("#endTime").val("");

			$("#startDay").show();
			$("#startDay").attr("name", "startTime");
			//$("#startDay").val("");
			$("#endDay").show();
			$("#endDay").attr("name", "endTime");
			//$("#endDay").val("");

			$("#startDate").hide();
			$("#startDate").attr("name", "startDate");
			$("#startDate").val("");
			$("#endDate").hide();
			$("#endDate").attr("name", "endDate");
			$("#endDate").val("");
		} else if ($("#select").val() == 3) {
			$("#startTime").hide();
			$("#startTime").attr("name", "startDate");
			$("#startTime").val("");
			$("#endTime").hide();
			$("#endTime").attr("name", "endDate");
			$("#endTime").val("");

			$("#startDay").hide();
			$("#startDay").attr("name", "startDate");
			$("#startDay").val("");
			$("#endDay").hide();
			$("#endDay").attr("name", "startDate");
			$("#endDay").val("");

			$("#startDate").show();
			$("#startDate").attr("name", "startTime");
			//$("#startDate").val("");
			$("#endDate").show();
			$("#endDate").attr("name", "endTime");
			//$("#endDate").val("");
		} else {
			$("#startTime").show();
			$("#startTime").attr("name", "startTime");
			//$("#startTime").val("");
			$("#endTime").show();
			$("#endTime").attr("name", "endTime");
			//$("#endTime").val("");

			$("#startDay").hide();
			$("#startDay").attr("name", "startDay");
			$("#startDay").val("");
			$("#endDay").hide();
			$("#endDay").attr("name", "endDay");
			$("#endDay").val("");

			$("#startDate").hide();
			$("#startDate").attr("name", "startDate");
			$("#startDate").val("");
			$("#endDate").hide();
			$("#endDate").attr("name", "endDate");
			$("#endDate").val("");
		}
	}

	function query() {
		//加载中
		ShowLoading();
		
		//获取当前选择的id名称
		var oneV,twoV;
		if($("#select").val() == 2){
			oneV = "startDay";
			twoV = "endDay";
		}else if($("#select").val() == 3){
			oneV = "startDate";
			twoV = "endDate";
		}else{
			oneV = "startTime";
			twoV = "endTime";				
		}
 		
	}
	
	//比较时间
	function compareTime(oneV,twoV) {
		//获取开始时间和结束时间
		var startTime = $("#" + oneV).val();
		var endTime = $("#" + twoV).val();
		
		
		if("" == startTime || "" == endTime || null == startTime 
				|| null == endTime || "undefined" == startTime || "undefined" == endTime){
			return true;
		}
		
		//转换为int的时间
		if($("#select").val() == 1){
			var newST = parseInt(startTime.replace(/-/g,"").replace(" ","").replace(/:/g,""));
			var newET = parseInt(endTime.replace(/-/g,"").replace(" ","").replace(/:/g,""));			
		}else{
			var newST = parseInt(startTime.replace(/-/g,""));
			var newET = parseInt(endTime.replace(/-/g,""));				
		}
		
		
		return true;
	}

  	jeDate({
		dateCell : "#startTime",
		format : "YYYY-MM-DD hh:mm:ss",
		isinitVal : false, //是否初始化时间
		isTime : true, //是否开启时间选择
		isClear : true, //是否显示清空
		festival : false, //是否显示节日
		minDate : "2014-09-19 00:00:00",
		okfun : function(val) {}
	})
	
	jeDate({
		dateCell : "#endTime",
		format : "YYYY-MM-DD hh:mm:ss",
		isinitVal : false, //是否初始化时间
		isTime : true, //是否开启时间选择
		isClear : true, //是否显示清空
		festival : false, //是否显示节日
		minDate : "2014-09-19 00:00:00",
		okfun : function(val) {}
	})  
	
   jeDate({
		dateCell : "#startDay",
		format : "YYYY-MM-DD",
		isinitVal : false, //是否初始化时间
		isTime : false, //是否开启时间选择
		isClear : true, //是否显示清空
		festival : false, //是否显示节日
		minDate : "2014-09-19 00:00:00",
		okfun : function(val) {}
	})
	jeDate({
		dateCell : "#endDay",
		format : "YYYY-MM-DD",
		isinitVal : false, //是否初始化时间
		isTime : false, //是否开启时间选择
		isClear : true, //是否显示清空
		festival : false, //是否显示节日
		minDate : "2014-09-19 00:00:00",
		okfun : function(val) {}
	}) 
	
  	jeDate({
		dateCell : "#startDate",
		format : "YYYY-MM",
		isinitVal : false, //是否初始化时间
		isTime : false, //是否开启时间选择
		isClear : true, //是否显示清空
		festival : false, //是否显示节日
		minDate : "2014-09-19 00:00:00",
		okfun : function(val) {}
	})
	
	jeDate({
		dateCell : "#endDate",
		format : "YYYY-MM",
		isinitVal : false, //是否初始化时间
		isTime : false, //是否开启时间选择
		isClear : true, //是否显示清空
		festival : false, //是否显示节日
		minDate : "2014-09-19 00:00:00",
		okfun : function(val) {}
	})
	
	
		//全选反选
		$(function() {
			//出库，制作全选反选
			$('table th input:checkbox').on('click', function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox').each(function(){
					this.checked = that.checked;
				});	
			});
		});
		
		function sendNotice() {		
			var noticeInfo = "";
			var selectOption = "";
			
			//获取每个选择的列表信息
			for(var i = 0; i < document.getElementsByName('ids').length; i++){
				if(document.getElementsByName('ids')[i].checked){
				  	if(noticeInfo == '') {
				  		noticeInfo += document.getElementsByName('ids')[i].value;
				  	}else {
				  		noticeInfo += '::' + document.getElementsByName('ids')[i].value;
				  	}
				}
			}
			
			for(var i = 0; i < document.getElementsByName('option').length; i++){
				if(document.getElementsByName('option')[i].checked){
				  	selectOption += document.getElementsByName('option')[i].value;
				}
			}
			
			
		    //获取的参数
		    var formdata = {
		    	selectOption: 1	//选择发送方式
		    }
		    
		    //确认框
		    layer.confirm('是否发送？', {icon: 3, title:'提示'}, function(index){
			    //开启正在加载
			    ShowLoading();
			    
			    //访问请求
			    $.ajax({
			        url: contextPath + '/annualNotificationController/autosendAnnualNotice',
			        type: "post",
				    timeout : 0,
			        data: formdata,
			        success: function (result) {
			        	//通知失败
			        	if("success" == result){
			         	   layer.alert('激活定时发送成功！', {icon: 1},function(){
			         		   //刷新父窗口信息
			         		   setTimeout("location.reload()",100); 
			         	   });
			        	}else if("noMod" == result){
				         	   layer.alert('请先选择一个模板！', {icon: 1},null);
				        }else{
			         	   layer.alert('激活定时发送失败！', {icon: 1},null);	        		
			        	}
			        }
			    })
			    layer.close(index);
		    });
		}
	</script>
</body>
</html>