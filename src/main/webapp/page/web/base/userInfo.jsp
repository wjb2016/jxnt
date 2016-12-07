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
    
    <title>用户管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1  ,maximum-scale=1, user-scalable=no" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
$(document).ready(function(){	
    var id = $("#id_hid").val();
    var loginId = $("#loginId_hid").val();
    if(loginId != id){
        var utypeId = $("#utypeId").val();
	    $("#utype").combobox("setValue",utypeId);
    }
}); 
function saveUser(obj){
    if ($('#userForm').form('validate')) {
	$(obj).attr("onclick", "");	
	showProcess(true, '温馨提示', '正在提交数据...'); 
	 $('#userForm').form('submit',{
	  		success:function(data){ 
				showProcess(false);
	  			data = $.parseJSON(data);
	  			if(data.code==0){	  					
	  				$.messager.alert('保存信息',data.message,'info',function(){
	  					window.location.href="<%=basePath %>User/userList.do";
        			});
	  			}else{
					$.messager.alert('错误信息',data.message,'error',function(){
        			});
					$(obj).attr("onclick", "saveUser(this);"); 
	  			}
	  		}
	  	 });
	}
}	
function changePsd(){
   var wz = getDialogPosition($('#changePsdWindow').get(0),100);
	$('#changePsdWindow').window({
		  	top: 150,
		    left: wz[1],
		    onBeforeClose: function () {
		    },
		    onClose:function(){
		    }
	});
	$('#changePsdWindow').window('open');
}  
function savePsd(obj){
    var psd1 = $("#psd1").val();
    var psd2 = $("#psd2").val();
    var psd = $("#psd").val();
    var id = $("#id_hid").val();
   
   if(psd == null || psd1 == null || psd2 == null){
        $message.alert("密码不能为空!",error);
        return;
    } 
    $("#utype_psd").val(100);
    if ($('#psdForm').form('validate')) {
	$(obj).attr("onclick", "");	
	showProcess(true, '温馨提示', '正在提交数据...'); 
	 $('#psdForm').form('submit',{
	  		success:function(data){ 
				showProcess(false);
	  			data = $.parseJSON(data);
	  			if(data.code==0){
	  			$('#changePsdWindow').window('close');	  					
	  				$.messager.alert('保存信息',data.message,'info',function(){
	  					window.location.href="<%=basePath%>User/userInfo.do?id="+id;
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
	</script>
  </head>
  
  <body>
    <div class="con-right" id="conRight">
      <div class="fl yw-lump">
			<div class="yw-lump-title"> 
					<i id="i_back" class="yw-icon icon-back" onclick="window.location.href='<%=basePath %>User/userList.do'"></i><span>用户列表</span>
			</div>
		</div>
		<div class="fl yw-lump mt10">
			<div class="yw-bi-rows">
				<div class="yw-bi-tabs mt5" id="ywTabs">
					<span class="yw-bi-now">用户信息</span>
				</div>			
				<div class="fr">
				 <c:if test="${loginUser.utype == User.utype}">
					<span class="yw-btn bg-green mr10" onclick="changePsd()">修改密码</span>
				</c:if> 
				<span class="yw-btn bg-green mr10" id="saveBtn" onclick="saveUser(this);">保存</span> 
				    <span class="yw-btn bg-red mr10"  onclick="$('#i_back').click();">返回</span>
				</div>			
			</div>
			<div>
				<form id="userForm" name="userForm" action="<%=basePath %>User/jsonSaveOrUpdateUser.do" method="post">
					<div id="tab1" class="yw-tab ">
						<table class="yw-cm-table font16">
							<tr>
								<td  align="left">用户姓名：
								<input id="name"  name="name"  onblur="valueTrim(this);"    type="text" value="${User.name}" class="easyui-validatebox" required="true" validType="Length[1,20]" style="width:180px;height:28px;" />
								<span style="color:red">*</span>
								<input type="hidden" id="id_hid" name="id" value="${User.id }" />
								<input type="hidden" id="loginId_hid" value="${loginUser.id }" />
								</td>
							</tr>
						 <c:if test="${loginUser.id == User.id}">
						    <input type="hidden" id="utype_hid" name="utype" value="${User.utype }" />
						 </c:if>
						 <c:if test="${loginUser.id != User.id}">
						   <input type="hidden" id="utypeId" name="utype" value="${User.utype }" />	 															
							<tr>						 
								 <td  align="left">用户类型：
									 <select class="easyui-combobox"  id="utype" style="width:180px;height:28px;" 
									  data-options="editable:false,onSelect:function(record){ $('#utypeId').val(record.value) }">
					                 	<c:if test="${User.utype == 1}"><option  value="1" >普通用户</option></c:if>
					                 	<c:if test="${User.utype == 13}"><option  value="13" >超级管理员</option></c:if>
					                 	<c:if test="${User.utype == 10 || loginUser.utype == 13}"><option  value="10" >管理员</option></c:if>					                	
				                 		<c:if test="${User.utype == 11 || User.utype == 12}">
					                 		<option  value="11" >员工</option>	
						                 	<option  value="12" >项目经理</option>		
						                 </c:if>				                	
					                 </select>
								 </td>						    	
							</tr>	
						</c:if>
						<tr>						 							
				             <td align="left">用户性别：
				             <c:if test="${User.sex == 0 || User.sex == null}">
							     <label><input type="radio" name="sex" value="1"/>男</label> 
 								<label><input type="radio" name="sex" value="2" />女</label> 
 								<label><input type="radio" name="sex" value="0" checked="checked"/>保密</label> 
							     </c:if>
							     <c:if test="${User.sex== 1 }">
							     <label><input type="radio" name="sex" value="1" checked="checked" />男</label> 
 								<label><input type="radio" name="sex" value="2" />女</label> 
							     </c:if>	 
							      <c:if test="${User.sex == 2 }">
							      <label><input type="radio" name="sex" value="1" />男</label> 
 								<label><input type="radio" name="sex" value="2" checked="checked" />女</label> 
							      </c:if>													 						    						 	 	
					    	</td> 
						</tr>										  
						<tr>
							<td align="left">手机号码：
							<input  name="mobile"  onblur="valueTrim(this);"    type="text" value="${User.mobile}" class="easyui-validatebox" required="true" validType="mobile" style="width:180px;height:28px;"  />
							     <span style="color:red">*</span> 														
							</td>
						</tr>
						<tr>
							<td align="left">用户地址：
							<input  name="address"  onblur="valueTrim(this);"    type="text" value="${User.address}" class="easyui-validatebox" required="true" validType="Length[1,20]" style="width:180px;height:28px;"  />
							     <span style="color:red">*</span> 														
							</td>
						</tr>
							
					
						</table> 						
					</div>  
				</form>
			</div>
		</div>
		<div class="cl"></div>
	</div>
	<div class="cl"></div>
	<div id="changePsdWindow" class="easyui-window" title="修改密码" style="width:320px;height:310px;overflow:hidden;padding:10px;" iconCls="icon-info" closed="true" modal="true"   resizable="false" collapsible="false" minimizable="false" maximizable="false">
		<form id="psdForm" name="psdForm" action="<%=basePath %>User/jsonSaveOrUpdateUser.do" method="post" >
		
         <p >
        	
        	<input id="id_psd" name="id" type="hidden" value="${User.id }"/>
        	<input id="utype_psd" name="utype" type="hidden" value="${User.utype}"/>
        </p>
        <p >
        	<span >原始密码：</span><input id="psd" name="psd" type="password" class="easyui-validatebox" onblur="valueTrim(this);"  doc="manageInfo1" required="true"  validType="Length[6,18]" style="width:180px;height:28px;"/>
        	
        </p>
        <p >
        	<span >新&nbsp;&nbsp;密&nbsp;&nbsp;码：</span><input id="psd1" name="psd1" type="password" class="easyui-validatebox" onblur="valueTrim(this);"  doc="manageInfo1" value="${User.psd1}" required="true"  validType="Length[6,18]" style="width:180px;height:28px;"/>
        </p>
        <p >
        	<span >确认密码：</span><input id="psd2" name="psd2" type="password" class="easyui-validatebox" onblur="valueTrim(this);"  doc="manageInfo1" value="${User.psd2}" required="true"  validType="equalTo['#psd1']" invalidMessage="两次输入密码不匹配" style="width:180px;height:28px;"/> 
        </p>
         <div class="yw-window-footer txt-center">
         <span class="yw-btn bg-green mr10" id="saveBtn1" onclick="savePsd(this);">保存</span>
        	<span id="btnCancel" class="yw-btn bg-red mr10"   onclick="$('#changePsdWindow').window('close');">退出</span>
        	
        </div>
		</form>
	</div>
  </body>
</html> 
