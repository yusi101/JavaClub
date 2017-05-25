<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<%@ include file="../system/allresources.jsp"%> 
<title>年报人工推送</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<h4 class="title">年报人工推送</h4>
			</div>
			 <form action="${pageContext.request.contextPath}/annualNotificationController/queryannualReport" method="post" name="queryBrandInfo" id="queryBrandInfo" class="form-horizontal" style="margin: 10px 0; height: 30px;">
				<!-- <div style="width: 20%; float: left;">
					<input type="checkbox" name="option" id="email" value="1"/>&nbsp;<label for="email" style="display:inline; vertical-align:middle;">邮件</label>
				    <input type="checkbox" name="option" id="phone" value="2"/>&nbsp;<label for="phone" style="display:inline; vertical-align:middle;">电话</label>
				</div> -->
				<div style="width: 100%; float: left; text-align: right;">
				    <input type="button" class="btn btn-add" onclick="sendNotice();" value="发送"/>
				</div>
				<div class="clren"></div>
			</form> 
			<table class="table table-condensed table-bordered table-hover tab">
				<thead>
					<tr>
					    <th width="5%">
					    	<input type="checkbox" id="zcheckbox" style="vertical-align: text-top"/>
					    </th>
						<th width="35%">企业名</th>
						<th width="30%">邮件地址</th>
						<th width="30%">电话号码</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<c:if test="${!empty anList }">
						<c:forEach items="${anList }" var="list" varStatus="status">
							<tr>
								<td>
									<c:if test="${list.TEL != '' or list.EMAIL != '' }">
										<input type="checkbox" name="ids" value="${list.PRIPID },${list.ENTNAME },${list.EMAIL },${list.TEL }" style="vertical-align: text-top"/>
									</c:if>
								</td>
								<td>${list.ENTNAME }</td>
								<td>${list.EMAIL }</td>
								<td>${list.TEL }</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty anList }">
						<tr>
							<td colspan="4" style="color: red; text-align: center;">
								<div align="center">
									<img src="${pageContext.request.contextPath}/static/images/nodata.png" style="width: 100px; margin: 10px auto;">
								</div>
								<div style="margin-bottom: 10px;">小查没有查到数据哦~</div>
							</td>
						</tr>
					</c:if>	
				</tbody>				
			</table>
			<div class="position-relative">
				<table style="width: 100%;">
					<tr>
						<td style="vertical-align: top;">
							<div class="pagination" style="float: left; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
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
			
			//判断是否选择了
			if(noticeInfo == "" || noticeInfo.length == 0){				
		        layer.tips('请至少选择一家企业', '#zcheckbox',{tips:[3, '#78BA32'],tipsMore: true});
		        return;
			}
			
			/* //判断是否选择了
			if(selectOption == "" || selectOption.length == 0){				
		        layer.tips('请选择发送方式', '#email',{tips:[3, '#78BA32'],tipsMore: true});
		        return;
			} */
			
		    //获取的参数
		    var formdata = {
		    	noticeInfo: noticeInfo,		//企业ID，企业名称，邮箱，电话
		    	selectOption: 1	//选择发送方式
		    }
		    
		    //确认框
		    layer.confirm('是否发送通知？', {icon: 3, title:'提示'}, function(index){
			    //开启正在加载
			    ShowLoading();
			    
			    //访问请求
			    $.ajax({
			        url: contextPath + '/annualNotificationController/sendAnnualNotice',
			        type: "post",
				    timeout : 0,
			        data: formdata,
			        success: function (result) {
			        	//通知失败
			        	if("success" == result){
			         	   layer.alert('通知成功！', {icon: 1},function(){
			         		   //刷新父窗口信息
			         		   setTimeout("location.reload()",100); 
			         	   });
			        	}else if("noet" == result){
				         	   layer.alert('请先去配置项关闭定时发送任务！', {icon: 1},null);
				        }else if("noMod" == result){
				         	   layer.alert('请先选择一个模板！', {icon: 1},null);
				        }else{
			         	   layer.alert('通知失败！', {icon: 1},null);	        		
			        	}
			        }
			    })
			    layer.close(index);
		    });
		}
	</script>
</body>
</html>