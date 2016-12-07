<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<title>团队管理</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1  ,maximum-scale=1, user-scalable=no" />
<script
	src="${pageContext.request.contextPath}/source/js/pager/jquery.pager.js"></script>
<link
	href="${pageContext.request.contextPath}/source/js/pager/Pager.css"
	rel="stylesheet" />
<script type="text/javascript">		
function DeleteGroupItem(id){
    var message = "是否删除该团队成员?";
	$.messager.confirm("操作确认",message,function(r){  
		    if (r){   
			$.ajax({
				url : "<%=basePath %>Group/jsonDeleteGroupItemById.do?id="+id,
				type : "post",  
		    	dataType : "json",								
				success : function(data) { 	
				    //alert(data.code == 0);								
		  			if(data.code == 0){ 
		  			   // alert(data.message);
		  				$.messager.alert('操作信息',data.message,'info',function(){ 		  				   
		  					window.location.href="<%=basePath %>Group/groupInfo.do?id="+data.obj; 
		      			});
		  			}else{		  			    
						$.messager.alert('错误信息',data.message,'error');
		  			}  
			    } 
			});
	    }  
	}); 
}

function addMember(){
   var id = $("#id_hid").val();
   if(id == 0){
      $.messager.alert("提示","请先保存团队信息!",'error');
      return ;
   }
   var wz = getDialogPosition($('#addMemberWindow').get(0),100);
	$('#addMemberWindow').window({
		  	top: 150,
		    left: wz[1],
		    onBeforeClose: function () {
		    },
		    onClose:function(){
		    }
	});
	$('#addMemberWindow').window('open');
} 
function saveMember(obj){   
    if ($('#memberForm').form('validate')) {
	$(obj).attr("onclick", "");	
	showProcess(true, '温馨提示', '正在提交数据...'); 
	 $('#memberForm').form('submit',{
	  		success:function(data){ 
				showProcess(false);
	  			data = $.parseJSON(data);
	  			if(data.code==0){
	  			$('#addMemberWindow').window('close');	  					
	  				$.messager.alert('保存信息',data.message,'info',function(){
		  				window.location.href="<%=basePath %>Group/groupInfo.do?id="+data.obj;
        			});
	  			}else{
					$.messager.alert('错误信息',data.message,'error',function(){
        			});
					$(obj).attr("onclick", "savePsd(this);"); 
	  			}
	  		}
	  	 });
	}
} 	  
function saveGroup(obj){
    if ($('#groupInfoForm').form('validate')) {
	$(obj).attr("onclick", "");	
	showProcess(true, '温馨提示', '正在提交数据...'); 
	 $('#groupInfoForm').form('submit',{
	  		success:function(data){ 
				showProcess(false);
	  			data = $.parseJSON(data);
	  			if(data.code==0){	  					
	  				$.messager.alert('保存信息',data.message,'info',function(){
	  					window.location.href="<%=basePath %>Group/groupInfo.do?id="+data.obj;
        			});
	  			}else{
					$.messager.alert('错误信息',data.message,'error',function(){
        			});
					$(obj).attr("onclick", "saveGroup(this);"); 
	  			}
	  		}
	  	 });
	}
}


function ChangeLeader(){
   var wz = getDialogPosition($('#addLeaderWindow').get(0),100);
	$('#addLeaderWindow').window({
		  	top: 150,
		    left: wz[1],
		    onBeforeClose: function () {
		    },
		    onClose:function(){
		    }
	});
	$('#addLeaderWindow').window('open');
} 
function saveLeader(obj){   
    if ($('#leaderForm').form('validate')) {
	$(obj).attr("onclick", "");	
	showProcess(true, '温馨提示', '正在提交数据...'); 
	 $('#leaderForm').form('submit',{
	  		success:function(data){ 
				showProcess(false);
	  			data = $.parseJSON(data);
	  			if(data.code==0){
	  			$('#addLeaderWindow').window('close');	  					
	  				$.messager.alert('保存信息',data.message,'info',function(){
		  				window.location.href="<%=basePath %>Group/groupInfo.do?id="+data.obj;
        			});
	  			}else{
					$.messager.alert('错误信息',data.message,'error',function(){
        			});
					$(obj).attr("onclick", "saveLeader(this);"); 
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
				<i id="i_back" class="yw-icon icon-back" onclick="window.location.href='<%=basePath %>Group/groupList.do'"></i><span>团队列表</span>
		</div>
    </div>
    <div class="fl yw-lump mt10">
		<div class="yw-bi-rows">
			<div class="yw-bi-tabs mt5" id="ywTabs">
			   <span class="yw-bi-now">团队详情</span>
			</div>
		   <div class="fr">
		   <c:if test="${Group.id != null}">
		        <span class="yw-btn bg-green mr10" onclick="addMember()" >添加成员</span>
		   </c:if>
				<span class="yw-btn bg-green mr10" id="saveBtn" onclick="saveGroup(this);">保存</span> 
				<span class="yw-btn bg-red mr10"  onclick="$('#i_back').click();">返回</span>
				<input id="count2" name="count2" type="hidden" value="${Group.count2 }"/>  
		   </div>
		 </div>
		  <div id="tab1" class="yw-tab">
			  <form id="groupInfoForm" name="groupInfoForm" action="<%=basePath %>Group/jsonSaveOrUpdateGroup.do" method="post">						   
				<table class="yw-cm-table font16">				
					<tr>
						<td align="left" width="25%">
							团队名称：<input id="name" class="easyui-validatebox" name="name" style="width:150px;height:32px;" value="${Group.name}"/>
						    <input id="id_hid" name="id" type="hidden" value="${Group.id}">
						</td>
						<c:if test="${Group.id == null}">
							<td align="left"  width="75%">
								团队描述：<input id="description" class="easyui-validatebox" name="description" style="width:85%;height:32px;" value="${Group.description}"/>
							</td>
						</c:if>
					    <c:if test="${Group.id > 0}">
					    	<td align="left"  width="50%">
								团队描述：<input id="description" class="easyui-validatebox" name="description" style="width:85%;height:32px;" value="${Group.description}"/>
							</td>
					    	<td align="center"  width="25%">
					    		创建时间：<input readonly="readonly" class="easyui-validatebox" value="${Group.createTimes}"/>
					    	</td>
					    </c:if>
					</tr>																		 	
				</table>
			   </form>
			   <c:if test="${Group.count != 0}">
				<table  class="yw-cm-table yw-center yw-bg-hover" id="groupItemList">
					<tr style="background-color:#D6D3D3;font-weight: bold;">
						<th style="display:none">&nbsp;</th>				
						<th>姓名</th>						
						<th>职务</th>
						<th>电话</th>					
						<th>操作</th>
					</tr>
					<c:forEach var="item" items="${groupItemList}">
					<tr>
						<td  style="display:none">${item.id}</td>	
						<td>${item.name}</td>							
						<td>${item.duty}</td>
						<td>${item.mobile}</td>
						<td>
						  <c:if test="${item.isLeader == 2}">
						      <a style="color:blue"  onclick="ChangeLeader()">替换</a>
						  </c:if>
						   <c:if test="${item.isLeader != 2}">
						      <a style="color:red"  onclick="DeleteGroupItem(${item.id})">删除</a>
						  </c:if>   						
						</td>	
					</tr>				
					</c:forEach>													
			    </table> 	
			   </c:if>					
		</div>							
	  </div> 
	 </div>	
 
 
 
 <div id="addMemberWindow" class="easyui-window" title="添加成员" style="width:320px;height:310px;overflow:hidden;padding:10px;" iconCls="icon-info" closed="true" modal="true"   resizable="false" collapsible="false" minimizable="false" maximizable="false">
		<form id="memberForm" name="memberForm" action="<%=basePath %>Group/jsonSaveOrUpdateMenber.do" method="post" >
		
         <p >        	
        	<input id="id_hid1" name="id" type="hidden" value="${GroupItem.id }"/>  
        	<input id="GroupId_hid" name="groupId" type="hidden" value="${GroupItem.groupId }"/>       	
        </p>
          <p >
        	<span >成员职务：</span>
        	<c:if test="${Group.count2 == 0}">
		     <label><input type="radio" name="isLeader" value="2" checked="checked"/>队长</label> 
		    </c:if>	
		    <c:if test="${Group.count2 > 0}">
			        <c:if test="${Group.count1 == 0}">
			             <label><input type="radio" name="isLeader" value="1" checked="checked"/>副队长</label>
			             <label><input type="radio" name="isLeader" value="0" />队员</label>
			        </c:if>
			         <c:if test="${Group.count1 > 0}">
			             <label><input type="radio" name="isLeader" value="0" checked="checked"/>队员</label>
			        </c:if>
			   </c:if>	 		   		      
         </p>
        <p >
        	<span >成员姓名：</span>
        	 <select class="easyui-combobox"  id="userId"  name="userId" value="${GroupItem.userId}"
				style="width:120px;height:32px;" data-options="editable:false">
				<!--  <option name="userId" value="0">-请选择团队成员-</option> -->
				<c:forEach var="item" items="${chooselist}">
				   <option name="userId" value="${item.id}">${item.name}</option>
				</c:forEach>
			</select>
        </p>
      
         <div class="yw-window-footer txt-center">
         <span class="yw-btn bg-green mr10" id="saveBtn1" onclick="saveMember(this);">保存</span>
        	<span id="btnCancel" class="yw-btn bg-red mr10"   onclick="$('#addMemberWindow').window('close');">退出</span>
        	
        </div>
		</form>
	</div>	
	
	
	<div id="addLeaderWindow" class="easyui-window" title="替换队长" style="width:320px;height:310px;overflow:hidden;padding:10px;" iconCls="icon-info" closed="true" modal="true"   resizable="false" collapsible="false" minimizable="false" maximizable="false">
		<form id="leaderForm" name="leaderForm" action="<%=basePath %>Group/jsonSaveOrUpdateMenber.do" method="post" >
		
         <p >        	
        	<input id="id_hid1" name="id" type="hidden" value="${GroupItem.id }"/>  
        	<input id="GroupId_hid" name="groupId" type="hidden" value="${GroupItem.groupId }"/>       	
        </p>
          <p >
        	<span >成员职务：</span>
		     <label><input type="radio" name="isLeader" value="2" checked="checked"/>队长</label> 		   	   		      
         </p>
        <p >
        	<span >成员姓名：</span>
        	 <select class="easyui-combobox"  id="userId"  name="userId" value="${GroupItem.userId}"
				style="width:120px;height:32px;" data-options="editable:false">
				<!--  <option name="userId" value="0">-请选择团队成员-</option> -->
				<c:forEach var="item" items="${leaderlist}">
				   <option name="userId" value="${item.id}">${item.name}</option>
				</c:forEach>
			</select>
        </p>
      
         <div class="yw-window-footer txt-center">
         <span class="yw-btn bg-green mr10" id="saveBtn1" onclick="saveLeader(this);">保存</span>
        <span id="btnCancel" class="yw-btn bg-red mr10"   onclick="$('#addLeaderWindow').window('close');">退出</span>
        	
        </div>
		</form>
	</div>								
  </body>
</html>




	