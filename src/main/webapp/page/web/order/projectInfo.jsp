<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>工程管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1  ,maximum-scale=1, user-scalable=no" />
<script type="text/javascript">
$(function(){
	//订单类型
	var orderTypeId =$("#orderTypeId").val();
	if(orderTypeId){
		$("#ordertype").combobox("setValue",orderTypeId);
	}
	
	//施工团队
	var groupId = $("#groupId").val();
	if(groupId){
		$("#group").combobox("setValue",groupId);
	}
})

//保存项目工程
function savePro(obj,id,orderTypeId,orderId){
	var status = $("#status").val();
	if(status != 0){
		$.messager.alert("错误提示","只能修改未执行的工程！","error");
    	return;
	}
	
	var startTimes =$("#startTimes").datebox("getValue");  
    var endTimes =$("#endTimes").datebox("getValue");
    //检查开始结束时间
    if(startTimes && endTimes){
    	if(!checkTime(startTimes,endTimes)){
    		$.messager.alert("操作提示","开始时间不能大于结束时间！","error");
    		return;
    	}
    }
	if ($('#proForm').form('validate')) {
		$(obj).attr("onclick", "");	
		showProcess(true, '温馨提示', '正在提交数据...'); 
	 	$('#proForm').form('submit',{
	  		success:function(data){ 
				showProcess(false);
	  			data = $.parseJSON(data);
	  			if(data.code==0){	  					
	  				$.messager.alert('保存信息',data.message,'info',function(){
        				window.location.href="<%=basePath %>Order/proInfo.do?id="+id+"&orderTypeId="+orderTypeId+"&orderId="+orderId;
        			});
	  			}else{
					$.messager.alert('错误信息',data.message,'error',function(){
						$(obj).attr("onclick", "savePro(this,"+id+","+orderTypeId+","+orderId+");"); 
        			});
	  			}
	  		}
	  	});
	}
}

//检查开始结束时间
function checkTime(sta,end){  
    var start=new Date(sta.replace("-", "/").replace("-", "/"));  
    var end=new Date(end.replace("-", "/").replace("-", "/"));  
    if(end < start){  
        return false;  
    }  
    return true;  
}

function savePhoto(obj,id,orderTypeId,orderId) {
	if ($('#photoForm').form('validate')) {
		$(obj).attr("onclick", "");	
		showProcess(true, '温馨提示', '正在提交数据...'); 
		 $('#photoForm').form('submit',{
		  		success:function(data){ 
					showProcess(false);
		  			data = $.parseJSON(data);
		  			if(data.code==0){
		  		    	$('#uploadPhotoWindow').window('close');	  					
		  				$.messager.alert('保存信息',data.message,'info',function(){
		  				    window.location.href="<%=basePath %>Order/proInfo.do?id="+id+"&orderTypeId="+orderTypeId+"&orderId="+orderId;
	        			});
		  			}else{
						$.messager.alert('错误信息',data.message,'error',function(){
	        			});
						$(obj).attr("onclick", "savePhoto(this,"+id+","+orderTypeId+","+orderId+");"); 
		  			}
		  		}
		  	 }); 
		}
}

function showName1(obj) {
	if (!(/(?:jpg)$/i.test(obj.value))
			&& !(/(?:jpeg)$/i.test(obj.value))
			&& !(/(?:png)$/i.test(obj.value))) {
		$.messager.alert('错误', "选择上传图片文件格式错误", 'error');
		if (window.ActiveXObject) {//for IE
			obj.select();//lect the file ,and clear selection
			document.selection.clear();
		} else if (window.opera) {//for opera
			obj.type = "text";
			obj.type = "file";
		} else
			obj.value = "";//for FF,Chrome,Safari
	} else {
		$("#photoUrl1").val(obj.value);
	}
}
function showName2(obj) {
	if (!(/(?:jpg)$/i.test(obj.value))
			&& !(/(?:jpeg)$/i.test(obj.value))
			&& !(/(?:png)$/i.test(obj.value))) {
		$.messager.alert('错误', "选择上传图片文件格式错误", 'error');
		if (window.ActiveXObject) {//for IE
			obj.select();//lect the file ,and clear selection
			document.selection.clear();
		} else if (window.opera) {//for opera
			obj.type = "text";
			obj.type = "file";
		} else
			obj.value = "";//for FF,Chrome,Safari
	} else {
		$("#photoUrl2").val(obj.value);
	}
}
function showName3(obj) {
	if (!(/(?:jpg)$/i.test(obj.value))
			&& !(/(?:jpeg)$/i.test(obj.value))
			&& !(/(?:png)$/i.test(obj.value))) {
		$.messager.alert('错误', "选择上传图片文件格式错误", 'error');
		if (window.ActiveXObject) {//for IE
			obj.select();//lect the file ,and clear selection
			document.selection.clear();
		} else if (window.opera) {//for opera
			obj.type = "text";
			obj.type = "file";
		} else
			obj.value = "";//for FF,Chrome,Safari
	} else {
		$("#photoUrl3").val(obj.value);
	}
}
function showName4(obj) {
	if (!(/(?:jpg)$/i.test(obj.value))
			&& !(/(?:jpeg)$/i.test(obj.value))
			&& !(/(?:png)$/i.test(obj.value))) {
		$.messager.alert('错误', "选择上传图片文件格式错误", 'error');
		if (window.ActiveXObject) {//for IE
			obj.select();//lect the file ,and clear selection
			document.selection.clear();
		} else if (window.opera) {//for opera
			obj.type = "text";
			obj.type = "file";
		} else
			obj.value = "";//for FF,Chrome,Safari
	} else {
		$("#photoUrl4").val(obj.value);
	}
}
function chooseFile1() {
	return $("#file1").click();
}
function chooseFile2() {
	return $("#file2").click();
}
function chooseFile3() {
	return $("#file3").click();
}
function chooseFile4() {
	return $("#file4").click();
}

function deleteById(id,proId,orderTypeId,orderId){
	$.messager.confirm("删除确认","是否删除该工程照片？",function(r){  
	    if (r){   
			$.ajax({
				url : "<%=basePath %>Order/jsonDeletePhotoById.do?id="+id,
				type : "post",  
		    	dataType : "json",								
				success : function(data) { 									
		  			if(data.code == 0){ 
		  				$.messager.alert('操作信息',data.message,'info',function(){ 
		  				    window.location.href="<%=basePath %>Order/proInfo.do?id="+proId+"&orderTypeId="+orderTypeId+"&orderId="+orderId;
		      			});
		  			}else{		
		  		    	$.messager.alert('错误信息',data.message,'error');
		  			    if(data.obj != null){
		  			        window.location.href="<%=basePath %>Order/proInfo.do?id="+proId+"&orderTypeId="+orderTypeId+"&orderId="+orderId;		      			
		  			    }
		  			}  
			    } 
			});
    	}  
	});
}
</script>
</head>
<body>
    <div class="con-right" id="conRight">
      <div class="fl yw-lump">
			<div class="yw-lump-title"> 
				<i id="i_back" class="yw-icon icon-back" onclick="window.location.href='<%=basePath %>Order/orderInfo.do?id=${order.id }'"></i>
				<span>工程详情</span>
			</div>
		</div>
		<div class="fl yw-lump mt10">
			<div class="yw-bi-rows">
				<div class="yw-bi-tabs mt5" id="ywTabs">
					<span class="yw-bi-now">${project.name }</span>
				</div>			
				<div class="fr">
					<c:if test="${project.status == 1 }">
						<span class="yw-btn bg-blue mr10" id="addBtn" onclick="$('#uploadPhotoWindow').window('open');">上传工程图片</span> 
					</c:if>
					<c:if test="${project.status == 0 }">
						<span class="yw-btn bg-green mr10" id="sureBtn" onclick="savePro(this,${project.id},${order.orderTypeId },${project.orderId })">保存</span> 
					</c:if>
				    <span class="yw-btn bg-red mr10"  onclick="$('#i_back').click();">返回</span>
				</div>			
			</div>
			<div id="tab1" class="yw-tab ">
				<form id="proForm" name="proForm" action="<%=basePath %>Order/jsonSaveOrUpdatePro.do" method="post">
			        <table class="yw-cm-table font16">
						<tr>
							<td width="8%" align="right">
								<span>工程名称：</span>
							</td>
							<td align="left">
								<input id="name" name="name" value="${project.name }" type="text" class="easyui-validatebox" required="true" validType="Length[1,20]" style="width:254px;height:28px;" />
							</td>
							<td width="8%" align="right">
								<span>工程状态：</span>
							</td>
							<td align="left">
								<c:if test="${project.status == 0}">
									<input readonly="readonly" type="text" value="未执行" class="easyui-validatebox" style="width:254px;height:28px;" />
								</c:if>
								<c:if test="${project.status == 1}">
									<input readonly="readonly" type="text" value="进行中" class="easyui-validatebox" style="width:254px;height:28px;" />
								</c:if>
								<c:if test="${project.status == 2}">
									<input readonly="readonly" type="text" value="已完成" class="easyui-validatebox" style="width:254px;height:28px;" />
								</c:if>
								<c:if test="${project.status == 3}">
									<input readonly="readonly" type="text" value="已中断" class="easyui-validatebox" style="width:254px;height:28px;" />
								</c:if>
							</td>
						</tr>
						<tr>
							<td align="right">
								<span>施工团队：</span>
							</td>
							<td align="left">
								<select id="group" class="easyui-combobox" required="true" data-options="editable:false,onSelect:function(record){ $('#groupId').val(record.value) }" style="width:254px;height:28px;">
									 <option value="0">=请选择施工团队=</option>
									 <c:forEach var="item" items="${listGroup}">
										 <option value="${item.id }">${item.name }</option>
									 </c:forEach>
								</select>
								<input type="hidden" id="groupId" name="groupId" value="${project.groupId }"/>
								<input type="hidden" id="status" name="status" value="${project.status }"/>
								<input type="hidden" id="id" name="id" value="${project.id }"/>
								<input type="hidden" id="orderId" name="orderId" value="${order.id }"/>
							</td>
							<td align="right">
								<span>品牌类型：</span>
							</td>
							<td align="left">
								<select id="ordertype" class="easyui-combobox" required="true" data-options="editable:false,onSelect:function(record){ $('#orderTypeId').val(record.value) }" style="width:254px;height:28px;">
									 <option value="0">=请选择品牌类型=</option>
									 <c:forEach var="item" items="${listOrderType}">
										 <option value="${item.id }">${item.name }</option>
									 </c:forEach>
								</select>
								<input type="hidden" id="orderTypeId" name="orderTypeId" value="${project.orderTypeId }"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								<span>起始时间：</span>
							</td>
							<td align="left">
								<input id="startTimes" name="startTimes"  value="${project.startTimes }" style="width:254px;height:28px;" type="text" required="true" class="easyui-datebox" data-options="editable:false"/>
							</td>
							<td width="20%" align="right">
								<span>截止时间：</span>
							</td>
							<td align="left">
								<input id="endTimes" name="endTimes" value="${project.endTimes }" style="width:254px;height:28px;" type="text" required="true" class="easyui-datebox" data-options="editable:false"/>
							</td>
						</tr>
						<c:if test="${project.status != 0 }">
							<tr>
								<td align="right">
									<span>开工时间：</span>
								</td>
								<td align="left">
									<input readonly="readonly" type="text" value="${project.beganTimes }" class="easyui-validatebox" style="width:254px;height:28px;" />
								</td>
								<td width="20%" align="right">
									<span>完工时间：</span>
								</td>
								<td align="left">
									<input readonly="readonly" type="text" value="${project.finishTimes }" class="easyui-validatebox" style="width:254px;height:28px;" />
								</td>
							</tr>
						</c:if>
						<c:if test="${project.status == 2 }">
							<c:if test="${project.assessValue != null}">
								<tr>
									<td width="8%" align="right">
										<span>评&nbsp;&nbsp;价&nbsp;&nbsp;人：</span>
									</td>
									<td align="left">
										<input readonly="readonly" value="${project.assessUserName }" type="text" class="easyui-validatebox" style="width:254px;height:28px;" />
									</td>
									<td width="8%" align="right">
										<span>评价分值：</span>
									</td>
									<td align="left">
										<input readonly="readonly" value="${project.assessValue }" type="text" class="easyui-validatebox" style="width:254px;height:28px;" />
									</td>
								</tr>
							</c:if>
							<c:if test="${project.assessMessage != null }">
								<tr>
									<td width="8%" align="right">
										<span>评价内容：</span>
									</td>
									<td align="left">
										<textarea rows="5" style="vertical-align: middle;width: 248px;resize:none;">${project.assessMessage }</textarea>
									</td>
							</c:if>
						</c:if>
						<c:if test="${project.assessMessage != null }">
								<td align="right">
									<span>工程描述：</span>
								</td>
								<td align="left">
									<textarea rows="5" style="vertical-align: middle;width: 248px;resize:none;" id="description" name="description">${project.description }</textarea>
								</td>
							</tr>
						</c:if>
						<c:if test="${project.assessMessage == null }">
							<tr>
								<td align="right">
									<span>工程描述：</span>
								</td>
								<td colspan="3">
									<textarea rows="5" style="vertical-align: middle;width: 248px;resize:none;" id="description" name="description">${project.description }</textarea>
								</td>
							</tr>
						</c:if>
					</table>
					<c:if test="${project.status != 0}">
						<c:forEach var="item" items="${listImage }">
							<div style="position: relative;display: inline-block;margin-right: 8px;">
								<img alt="客户照片" src="<%=basePath %>${item.imagePath}" style="width:250px;height:250px;display: block;">
								<c:if test="${project.status == 1 }">
									<span style="position:absolute;width:40px;height: 25px;background-color: #FFAA71;color: white;top:0;right:0;text-align: center;line-height: 25px;cursor: pointer;" onclick="deleteById(${item.id},${project.id},${order.orderTypeId },${project.orderId })">X</span>
								</c:if>
							</div>
						</c:forEach>
					</c:if>
				</form>
			</div>
			<div id="uploadPhotoWindow" class="easyui-window" title="上传照片" style="width:560px;height:350px;overflow:hidden;padding:10px;text-align:center;" iconCls="icon-info" closed="true" modal="true" resizable="false" collapsible="false" minimizable="false" maximizable="false" closable="false" center="true">
				<form id="photoForm" name="photoForm"
					action="<%=basePath%>Order/jsonSaveOrUpdatePhoto.do" method="post"
					enctype="multipart/form-data">
					<table class="yw-cm-table font16">
						<tr>
							<td align="center">
								<input type="hidden" id="projectId" name="projectId" value="${project.id }"/>
								<input type="text" class="easyui-validatebox" 
								style="width:254px;height:32px;" readonly="readonly"
								id="photoUrl1"/> 
								<span class="yw-btnbyme bg-blue ml10" onclick="chooseFile1();">...</span>
							</td>
							<div style="display:none;">
								<input type="file" name="file1" id="file1" onchange="showName1(this)" />
							</div>
						</tr>
						<tr>
							<td align="center">
								<input type="text"
								class="easyui-validatebox" doc="photoInfo"
								style="width:254px;height:32px;" readonly="readonly"
								id="photoUrl2"/> 
								<span class="yw-btnbyme bg-blue ml10" onclick="chooseFile2();">...</span>
							</td>
							<div style="display:none;">
								<input type="file" name="file2" id="file2" onchange="showName2(this)" />
							</div>
						</tr>
						<tr>
							<td align="center">
								<input type="text"
								class="easyui-validatebox" doc="photoInfo"
								style="width:254px;height:32px;" readonly="readonly"
								id="photoUrl3"/> 
								<span class="yw-btnbyme bg-blue ml10" onclick="chooseFile3();">...</span>
							</td>
							<div style="display:none;">
								<input type="file" name="file3" id="file3" onchange="showName3(this)" />
							</div>
						</tr>
						<tr>
							<td align="center">
								<input type="text"
								class="easyui-validatebox" doc="photoInfo"
								style="width:254px;height:32px;" readonly="readonly"
								id="photoUrl4"/> 
								<span class="yw-btnbyme bg-blue ml10" onclick="chooseFile4();">...</span>
							</td>
							<div style="display:none;">
								<input type="file" name="file4" id="file4" onchange="showName4(this)" />
							</div>
						</tr>
					</table>
				</form>
				<div class="yw-window-footer txt-right">
		        	<span id="btnCancel" class="yw-window-btn bg-lightgray mt12"  onclick="$('#uploadPhotoWindow').window('close');">取消</span>
		        	<span class="yw-window-btn bg-blue mt12" onclick="savePhoto(this,${project.id},${order.orderTypeId },${project.orderId });">确定</span>
		        </div>
			</div>
		</div>
		<div class="cl"></div>
	</div>
	<div class="cl"></div>
  </body>
</html> 
