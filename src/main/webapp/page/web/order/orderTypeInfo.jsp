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
<title>品牌管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1  ,maximum-scale=1, user-scalable=no" />
<script type="text/javascript">
$(function(){
	//品牌类型
	var parentId = $("#parentId").val();
	if(parentId){
		$("#ordertype").combobox("setValue",parentId);
	}else{
		$("#ordertype").combobox("setValue",0);
	}

	//加载品牌节点树
	<%-- $("#cmbParentItem").combotree({
		 url: '<%=basePath %>Order/jsonLoadItemTreeList.do',  
		 required: false,
		 onBeforeExpand:function(node){
		 	$('#cmbParentItem').combotree('tree').tree('options').url = '<%=basePath %>Order/jsonLoadItemTreeList.do?pid='+ node.id;
		 },
		 onSelect:function(record){
		 	 //获取品牌id
		 	 var typeId = $("#typeId").val();
		 	 if(typeId){
		 	 	if(record != null ){
				 	 if(typeId == record.id){
				 	 	$.messager.alert('错误信息',"不能选择当前品牌为所属品牌",'error',function(){
				 	 		return;
      					});
				 	 	$("#cmbParentItem").combotree("clear");
			 	 		$("#cmbParentItem").combotree("reload",'<%=basePath %>Order/jsonLoadItemTreeList.do?pid='+0);
				 	 	$("#cmbParentItem").combotree("setText","=请选择品牌类型=");
				 	 }else{
				 	 	$("#cmbParentItem").combotree("setText",record.name);
				 	 	$("#parentId").val(record.id);
				 	 }
			 	}
			 }else{
			 	if(record != null){
			 		$("#cmbParentItem").combotree("setText",record.name);
				 	$("#parentId").val(record.id);
			 	}
			}
 			},
 			onLoadSuccess:function(){
				var parentName = $("#parentName").val();
				if(parentName){
					$("#cmbParentItem").combotree("setText",parentName);
				}else{
					$("#cmbParentItem").combotree("setText","=请选择品牌类型=");
				}
 			}
	}); --%>
})

function chooseFile() {
	return $("#file").click();
}

//检查文件格式
function showName(obj) {
	if (!(/(?:jpg)$/i.test(obj.value))
			&& !(/(?:jpeg)$/i.test(obj.value))
			&& !(/(?:png)$/i.test(obj.value))) {
		$.messager.alert('错误', "选择图片文件格式错误", 'error');
		if (window.ActiveXObject) {//for IE
			obj.select();//lect the file ,and clear selection
			document.selection.clear();
		} else if (window.opera) {//for opera
			obj.type = "text";
			obj.type = "file";
		} else
			obj.value = "";//for FF,Chrome,Safari
	} else {
		//$("#img1Path").val(obj.value);
		$("#imagetd").hide();
		$("#pathtd input").val(obj.value);
		$("#pathtd").show();
	}
}

//保存品牌
function saveType(obj){
	if ($('#typeForm').form('validate')) {
		$(obj).attr("onclick", "");	
		showProcess(true, '温馨提示', '正在提交数据...'); 
	 	$('#typeForm').form('submit',{
	  		success:function(data){ 
				showProcess(false);
	  			data = $.parseJSON(data);
	  			if(data.code==0){	  					
	  				$.messager.alert('保存信息',data.message,'info',function(){
	  					window.location.href="<%=basePath %>Order/typeInfo.do?typeId=" + data.obj.id;
        			});
	  			}else{
					$.messager.alert('错误信息',data.message,'error',function(){
						$(obj).attr("onclick", "saveType(this);"); 
        			});
	  			}
	  		}
	  	});
	}
}
</script>
</head>
<body>
    <div class="con-right" id="conRight">
      <div class="fl yw-lump">
			<div class="yw-lump-title"> 
				<i id="i_back" class="yw-icon icon-back" onclick="window.location.href='<%=basePath %>Order/orderTypeList.do'"></i>
				<c:if test="${typeId == 0}">
					<span>新建品牌</span>
				</c:if>
				<c:if test="${typeId != 0}">
					<span>${orderType.name}</span>
				</c:if>
			</div>
		</div>
		<div class="fl yw-lump mt10">
			<div class="yw-bi-rows">
				<div class="yw-bi-tabs mt5" id="ywTabs">
					<span class="yw-bi-now">品牌详情</span>
				</div>			
				<div class="fr">
					<span class="yw-btn bg-green mr10" id="saveBtn" onclick="saveType(this);">保存</span> 
				    <span class="yw-btn bg-red mr10"  onclick="$('#i_back').click();">返回</span>
				</div>			
			</div>
			<form id="typeForm" name="typeForm" action="<%=basePath %>Order/jsonSaveOrUpdateType.do" method="post" enctype="multipart/form-data">
				<div id="tab1" class="yw-tab ">
					<table class="yw-cm-table font16">
						<tr>
							<td width="8%" align="right">
								<span>品牌名称：</span>
							</td>
							<td align="left">
								<input id="name" name="name" onblur="valueTrim(this);" type="text" value="${orderType.name}" class="easyui-validatebox" required="true" validType="Length[1,20]" style="width:254px;height:28px;" />
								<span style="color:red">*</span>
							</td>
						</tr>
						<tr>						 
							 <td width="8%" align="right">品牌类型：</td>
							 <td>
							 	 <input type="hidden" id="typeId" name="id" value="${orderType.id }" />
							 	 <input type="hidden" id="parentId" name="parentId" value="${orderType.parentId }" />
							 	 <input type="hidden" id="parentName" name="parentName" value="${orderType.parentName }" />
							 	 <!-- <input id ="cmbParentItem" type="text"  class="easyui-combotree" required="false" style="width:254px;height:28px;" /> -->
							 	 <select id="ordertype" class="easyui-combobox" data-options="editable:false,required:true,onSelect:function(record){ $('#parentId').val(record.value) }" style="width:254px;height:28px;">
									 <option value="0">=请选择品牌类型=</option>
									 <option value="1">地暖</option>
									 <option value="2">中央空调</option>
									 <option value="3">净水系统</option>
								</select>
							 	 <span style="color:red">*</span>
							 </td>
						</tr>	
						<tr>						 
							 <td width="8%" align="right">品牌描述：</td>
						     <td>
						     	<textarea name="description" onblur="valueTrim(this);" type="text" style="width:250px;height:100px;margin-top:15px;margin-bottom:15px;vertical-align: middle;" class="textarea easyui-validatebox">${orderType.description}</textarea>
							 </td>
						</tr>										  
						<tr>
							<td align="right">品牌照片：</td>	
							<td>
								<div id="imagetd">
									<c:if test="${orderType.imagePath != null && orderType.imagePath != ''}">		
								  		<img alt="品牌照片" src="${orderType.imagePath}" style="width:250px;height:300px;vertical-align: middle;">
								  		<span class="yw-btnbyme bg-blue" onclick="chooseFile();">重新上传</span>
							        </c:if>	
							        <c:if test="${orderType.imagePath == null || orderType.imagePath == ''}">		
								  		<span class="yw-btnbyme bg-blue" onclick="chooseFile();">上传照片</span>
							        </c:if>	
								</div>
								<div id="pathtd" style="display: none;">
									<input type="text" class="easyui-validatebox" style="width:254px;height:28px;" />
								</div>
						        <div style="display:none;">
									<input type="file" name="file" id="file" onchange="showName(this)"/>
								</div>	
							</td>
						</tr>
					</table> 						
				</div>  
			</form>
		</div>
		<div class="cl"></div>
	</div>
	<div class="cl"></div>
  </body>
</html> 
