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
<title>用户管理</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1  ,maximum-scale=1, user-scalable=no" />
<script
	src="${pageContext.request.contextPath}/source/js/pager/jquery.pager.js"></script>
<link
	href="${pageContext.request.contextPath}/source/js/pager/Pager.css"
	rel="stylesheet" />
<script type="text/javascript">
$(document).ready(function(){
	$("#pager").pager({
	    pagenumber:'${User.pageNo}',                         /* 表示初始页数 */
	    pagecount:'${User.pageCount}',                      /* 表示总页数 */
	    totalCount:'${User.totalCount}',
	    buttonClickCallback:PageClick                     /* 表示点击分页数按钮调用的方法 */                  
	});
	var searchUtype = $("#searchUtype").val();
	$("#searchUtypes").combobox("setValue",searchUtype);
}); 
		
PageClick = function(pageclickednumber) {
	$("#pager").pager({
	    pagenumber:pageclickednumber,                 /* 表示启示页 */
	    pagecount:'${User.pageCount}',                  /* 表示最大页数pagecount */
	    buttonClickCallback:PageClick                 /* 表示点击页数时的调用的方法就可实现javascript分页功能 */            
	});
	
	$("#pageNumber").val(pageclickednumber);          /* 给pageNumber从新赋值 */
	/* 执行Action */
	pagesearch();
}
function search(){
	$("#pageNumber").val("1");
	pagesearch(); 
} 
function pagesearch(){
	if ($('#UserForm').form('validate')) {
		UserForm.submit();
	}
}
function DeleteUser(id){
    var message = "是否删除该用户(删除将彻底清空用户积分)?";
   
	$.messager.confirm("操作确认",message,function(r){  
		    if (r){   
			$.ajax({
				url : "<%=basePath %>User/jsonDeleteUserById.do?id="+id,
				type : "post",  
		    	dataType : "json",								
				success : function(data) { 	
				    //alert(data.code == 0);								
		  			if(data.code == 0){ 
		  			   // alert(data.message);
		  				$.messager.alert('操作信息',data.message,'info',function(){ 
		  					search();
		      			});
		  			}else{		  			    
						$.messager.alert('错误信息',data.message,'error');
		  			}  
			    } 
			});
	    }  
	}); 
}

function ChangeFlag(id,flag){
     var message = "是否禁止该用户登录！";
     if(flag == 2){
         message = "是否允许该用户登录!";
     }     
	$.messager.confirm("操作确认",message,function(r){  
		    if (r){   
			$.ajax({
				url : "<%=basePath %>User/jsonChangeFlagById.do?id="+id,
				type : "post",  
		    	dataType : "json",								
				success : function(data) { 	
				    //alert(data.code == 0);								
		  			if(data.code == 0){ 
		  			   // alert(data.message);
		  				$.messager.alert('操作信息',data.message,'info',function(){ 
		  					search();
		      			});
		  			}else{		  			    
						$.messager.alert('错误信息',data.message,'error');
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
		<i class="yw-icon icon-partner"></i><span>用户列表</span>		
		<!-- <span class="fr yw-btn bg-orange line-hei22 mr10 mt9 cur" onclick="exportLog();">导出日志</span>  -->
         <div class="fr" > 
	        <span class="yw-btn bg-yellow mr10" onclick="window.location.href='<%=basePath %>User/userInfo.do?id=${loginUser.id}'">个人资料</span>								    
	        <span class="yw-btn bg-green cur" style="margin-left:19px;margin-right:11px;" onclick="window.location.href='<%=basePath %>User/userInfo.do?id=0';">新建用户</span> 
	    </div>
	    </div>	    
	    </div>	 	 	
		  
	
		<div class="fl yw-lump mt10">
			<form id="UserForm" name="UserForm"
				action="<%=basePath %>User/userList.do" method="get">
				<div class=pd10>							
		        	 <div class="fl">  
		        <!-- 	 <span class="yw-btn bg-green mr10" onclick="changePsd()">修改密码</span> -->
		        	  </div>
				     <div class="fr">				     
						<span>查询：</span><input type="text" name="searchName"   validType="SpecialWord"  style="width:150px;height:30px;"
						 class="easyui-validatebox" placeholder="姓名/手机号/地址搜索" value="${User.searchName}" type="hidden"/> 						
						<select class="easyui-combobox"  id="searchUtypes" style="width:150px;height:32px;"  
		                 	data-options="editable:false,onSelect:function(record){ $('#searchUtype').val(record.value) }">
		                 	 <option  value="0" >=请选择用户类型=</option>	
		                 	 <option  value="1" >普通用户</option>				                   	
		                 	 <option  value="13" >超级管理员</option>	
		                 	 <option  value="10" >管理员</option>	
		                 	 <option value="11" >员工</option>	
		                 	 <option  value="12" >项目经理</option>
		                 </select>
						<span class="yw-btn bg-blue ml30 cur" onclick="search();" style="display: inline-block;width: 65px;text-align: center">搜索</span>
					
					</div>
					<div class="cl"></div> 
				<input type="hidden" id="searchUtype" name="searchUtype" value="${User.searchUtype}" /> 
                <input type="hidden" id="pageNumber" name="pageNo" value="${User.pageNo}" />    
                </div>    
             </form>
             </div>                          	             				
           <div class="fl yw-lump"> 
				<table class="yw-cm-table yw-center yw-bg-hover">
					<tr style="background-color:#D6D3D3;font-weight: bold;">
						<th  style="display:none">&nbsp;</th>
						<th width="10%">姓名</th>  
						<th width="10%">性别</th>  
						<th width="10%">类型</th>  
						<th width="15%">手机号</th>  
						<th style="text-align: left;">地址</th> 
						<!-- <th width="10%">项目总数</th>  --> 
						<th width="8%">积分</th>  
						<th width="20%" style="text-align: left !important;">操作</th>				
					</tr>
					<c:forEach var="item" items="${Userlist}">
						<tr>
							<td  style="display:none">${item.id}</td>
							<td ><span>${item.name}</span></td>  
							<td ><span>${item.sexs}</span></td>  
							<td ><span>${item.utypeName}</span></td>  
							<td ><span>${item.mobile}</span></td>  
							<td style="text-align: left;"><span >${item.address}</span></td> 
							<%-- <td ><span>${item.flag}</span></td>   --%>
							<td ><span>${item.grade}</span></td>  
							<td style="text-align: left !important;">
							<!-- 详情 -->
							    <c:if test="${(item.utype == 10 && loginUser.utype == 13) || item.utype == 11 || item.utype == 12}">								 
								    <a href="javascript:void(0);" style="color:blue;margin-right:22px;" onclick="window.location.href='<%=basePath%>User/userInfo.do?id=${item.id}'">详情</a>	
								</c:if>
								 <c:if test="${!(item.utype == 10 && loginUser.utype == 13) && item.utype != 11 && item.utype != 12}">								 
								    <a href="javascript:void(0);" style="color:#D3D3D3;margin-right:22px;" >详情</a>	
								</c:if>   
							<!-- 删除 -->  
								<c:if test="${loginUser.utype == 13 && item.utype != 13}">								 
								    <a href="javascript:void(0);" style="color:red;margin-right:22px;" onclick="DeleteUser(${item.id})">删除</a>	 												
							     </c:if>
							    <c:if test="${loginUser.utype == 13 && item.utype == 13}">								 
								    <a href="javascript:void(0);" style="color:#D3D3D3;margin-right:22px;" >删除</a>	 												
							     </c:if> 
							 <!-- 登录权限 -->
							     <c:if test="${(item.utype == 1 ||( item.utype == 10 && loginUser.utype == 13))&& item.flag == 2}">
								    <a href="javascript:void(0);" style="color:green;margin-right:22px;" onclick="ChangeFlag(${item.id},${item.flag})">允许登陆</a>			
								</c:if>
								<c:if test="${!(item.utype == 1 ||( item.utype == 10 && loginUser.utype == 13))&& item.flag == 2}">
								    <a href="javascript:void(0);" style="color:#D3D3D3;margin-right:22px;")">允许登陆</a>			
								</c:if>
								<c:if test="${(item.utype == 1 ||( item.utype == 10 && loginUser.utype == 13))&& item.flag == 1}">
								    <a href="javascript:void(0);" style="color:#FBAF43;margin-right:22px;" onclick="ChangeFlag(${item.id},${item.flag})">禁止登陆</a>							
								</c:if>
								<c:if test="${!(item.utype == 1 ||( item.utype == 10 && loginUser.utype == 13))&& item.flag == 1}">
								    <a href="javascript:void(0);" style="color:#D3D3D3;margin-right:22px;" >禁止登陆</a>							
								</c:if>
							</td>							
						</tr>
					</c:forEach>
				</table>
				<div class="page" id="pager"></div>
				</div>
			 </div>   
			 
		
		

  </body>
</html>