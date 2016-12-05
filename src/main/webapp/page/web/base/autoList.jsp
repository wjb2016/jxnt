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
	    pagenumber:'${Auto.pageNo}',                         /* 表示初始页数 */
	    pagecount:'${Auto.pageCount}',                      /* 表示总页数 */
	    totalCount:'${Auto.totalCount}',
	    buttonClickCallback:PageClick                     /* 表示点击分页数按钮调用的方法 */                  
	});
}); 
		
PageClick = function(pageclickednumber) {
	$("#pager").pager({
	    pagenumber:pageclickednumber,                 /* 表示启示页 */
	    pagecount:'${Auto.pageCount}',                  /* 表示最大页数pagecount */
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
	if ($('#AutoForm').form('validate')) {
		AutoForm.submit();
	}
}
function ChangeAuto(id,flag){
    var message = "是否重新启用这条自动应答?";
    if(flag == 1){
        message = "是否立即停用这条自动应答?";
    }
	$.messager.confirm("操作确认",message,function(r){  
		    if (r){   
			$.ajax({
				url : "<%=basePath %>Auto/jsonChangeAutoById.do?id="+id,
				type : "post",  
		    	dataType : "json",								
				success : function(data) { 	
				    //alert(data.code == 0);								
		  			if(data.code == 0){ 
		  			   // alert(data.message);
		  				$.messager.alert('操作信息',data.message,'info',function(){ 
		  					window.location.href="<%=basePath %>Auto/autoList.do"; 
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
		<i class="yw-icon icon-partner"></i><span>自动应答列表</span>		
		<!-- <span class="fr yw-btn bg-orange line-hei22 mr10 mt9 cur" onclick="exportLog();">导出日志</span>  -->
	    <div class="fr" >
	        <span class="yw-btn bg-green cur" style="margin-right:11px;" onclick="window.location.href='<%=basePath %>Auto/autoInfo.do?id=0';">新建应答</span> 
		</div>
	    </div>
	    </div>	 	 	
		  
	
		<div class="fl yw-lump mt10">
			<form id="AutoForm" name="AutoForm"
				action="<%=basePath %>Auto/autoList.do" method="get">
				<div class=pd10>							
		        	 <div class="fl">  				
				     </div>
				     <div class="fr">				     
						<span>查询：</span><input type="text" name="searchName"  validType="SpecialWord"
						 class="easyui-validatebox" placeholder="关键字/答复搜索" value="${Auto.searchName}" type="hidden"/> 						
						<span class="yw-btn bg-blue ml30 cur" onclick="search();" style="display: inline-block;width: 65px;text-align: center">搜索</span>
					</div>
					<div class="cl"></div> 
                <input type="hidden" id="pageNumber" name="pageNo" value="${Auto.pageNo}" />    
                </div>    
             </form>
             </div>                          	             				
           <div class="fl yw-lump"> 
				<table class="yw-cm-table yw-center yw-bg-hover">
					<tr style="background-color:#D6D3D3;font-weight: bold;">
						<th  style="display:none">&nbsp;</th>
						<th width="15%">关键字</th>  
						<th style="text-align: left;">自动答复</th> 
						<th width="15%">创建时间</th>  
						<th width="12%">操作</th>				
					</tr>
					<c:forEach var="item" items="${Autolist}">
						<tr>
							<td  style="display:none">${item.id}</td>
							<td ><span>${item.keyword}</span></td>  
							<td style="text-align: left;"><span >${item.answer}</span></td> 
							<td ><span>${item.createTimes}</span></td>  
							<td>
								<a href="javascript:void(0);" style="color:blue;" onclick="window.location.href='<%=basePath %>Auto/autoInfo.do?id=${item.id}'">详情</a>
								<c:if test="${item.flag == 0}">
								   <a style="margin-left:15px;color:red"  onclick="ChangeAuto(${item.id},${item.flag})">重新启用</a> 
								</c:if>
								<c:if test="${item.flag == 1}">
								   <a style="margin-left:15px;color:green"  onclick="ChangeAuto(${item.id},${item.flag})">立即停用</a> 
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
