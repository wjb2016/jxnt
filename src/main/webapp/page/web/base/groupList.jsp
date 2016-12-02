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
<title>自动应答管理</title>
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
	    pagenumber:'${Group.pageNo}',                         /* 表示初始页数 */
	    pagecount:'${Group.pageCount}',                      /* 表示总页数 */
	    totalCount:'${Group.totalCount}',
	    buttonClickCallback:PageClick                     /* 表示点击分页数按钮调用的方法 */                  
	});
}); 
		
PageClick = function(pageclickednumber) {
	$("#pager").pager({
	    pagenumber:pageclickednumber,                 /* 表示启示页 */
	    pagecount:'${Group.pageCount}',                  /* 表示最大页数pagecount */
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
	if ($('#GroupForm').form('validate')) {
		GroupForm.submit();
	}
}
function DeleteGroup(id){
    var message = "是否删除该团队?";
	$.messager.confirm("操作确认",message,function(r){  
		    if (r){   
			$.ajax({
				url : "<%=basePath %>Group/jsonDeleteGroupById.do?id="+id,
				type : "post",  
		    	dataType : "json",								
				success : function(data) { 	
				    //alert(data.code == 0);								
		  			if(data.code == 0){ 
		  			   // alert(data.message);
		  				$.messager.alert('操作信息',data.message,'info',function(){ 
		  					window.location.href="<%=basePath %>Group/groupList.do"; 
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
		<i class="yw-icon icon-partner"></i><span>团队列表</span>		
		<!-- <span class="fr yw-btn bg-orange line-hei22 mr10 mt9 cur" onclick="exportLog();">导出日志</span>  -->
	     <div class="fr" > 
	         <span class="yw-btn bg-green cur" style="margin-right:11px;" onclick="window.location.href='<%=basePath %>Group/groupInfo.do?id=0';">新建团队</span> 
		 </div>
	    </div>
	    </div>	 	 	
		  
	
		<div class="fl yw-lump mt10">
			<form id="GroupForm" name="GroupForm"
				action="<%=basePath %>Group/groupList.do" method="get">
				<div class=pd10>							
		        	 <div class="fl">  				
				     </div>
				     <div class="fr">				     
						<span>查询：</span><input type="text" name="searchName"   validType="SpecialWord"
						 class="easyui-validatebox" placeholder="团队名或描述搜索" value="${Group.searchName}" type="hidden"/> 						
						<span class="yw-btn bg-blue ml30 cur" onclick="search();" style="display: inline-block;width: 65px;text-align: center">搜索</span>
					</div>
					<div class="cl"></div> 
                <input type="hidden" id="pageNumber" name="pageNo" value="${Group.pageNo}" />    
                </div>    
             </form>
             </div>                          	             				
           <div class="fl yw-lump"> 
				<table class="yw-cm-table yw-center yw-bg-hover">
					<tr style="background-color:#D6D3D3;font-weight: bold;">
						<th  style="display:none">&nbsp;</th>
						<th width="15%">团队名称</th> 
						<th width="10%">项目经理</th>  
						<th style="text-align: left;">描述</th> 
						<th width="10%">人数</th> 
						<th width="15%">创建时间</th>  
						<th width="12%">操作</th>				
					</tr>
					<c:forEach var="item" items="${groupList}">
						<tr>
							<td  style="display:none">${item.id}</td>
							<td ><span>${item.name}</span></td>  
							<td ><span>${item.leader}</span></td>
							<td style="text-align: left;"><span >${item.description}</span></td>
							<td ><span>${item.count}</span></td> 
							<td ><span>${item.createTimes}</span></td>  
							<td>
								<a href="javascript:void(0);" style="color:blue;" onclick="window.location.href='<%=basePath %>Group/groupInfo.do?id=${item.id}'">详情</a>
								
								<a style="margin-left:15px;color:red"  onclick="DeleteGroup(${item.id})">删除</a> 
								
							</td>							
						</tr>
					</c:forEach>
				</table>
				<div class="page" id="pager"></div>
				</div>
			 </div>     
  </body>
</html>
