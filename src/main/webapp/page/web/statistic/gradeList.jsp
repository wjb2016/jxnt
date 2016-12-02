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
<title>积分管理</title>
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
	    pagenumber:'${Grade.pageNo}',                         /* 表示初始页数 */
	    pagecount:'${Grade.pageCount}',                      /* 表示总页数 */
	    totalCount:'${Grade.totalCount}',
	    buttonClickCallback:PageClick                     /* 表示点击分页数按钮调用的方法 */                  
	});
}); 
		
PageClick = function(pageclickednumber) {
	$("#pager").pager({
	    pagenumber:pageclickednumber,                 /* 表示启示页 */
	    pagecount:'${Grade.pageCount}',                  /* 表示最大页数pagecount */
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
	if ($('#GradeForm').form('validate')) {
		GradeForm.submit();
	}
}
function getDateModel(date){
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	if(month <10){
		month = "0"+month;
	}
	var day = date.getDate();
	if(day <10){
		day = "0"+day;
	}
	var dates = year+"-"+month+"-"+day+" 00:00:00";
	return dates;
}

function getStartTimes(date){
	var dates = getDateModel(date);
	$("#searchTimes").val(dates);
}  
 function getEndTimes(date){
	var dates = getDateModel(date);
	$("#endTimes").val(dates);
} 
function refresh(){
    $("#startTimes").datebox('setValue', '');
    $("#endTimes").datebox('setValue', ''); 
    $("#searchName").val("");
    search();
}
function SureGrade(id){
    var message = "是否确认此次积分兑换?";
    CheckGrade(id,message,0);
}
function ReturnGrade(id){
    var message = "是否取消本次积分兑换?";
    CheckGrade(id,message,1);
}
function CheckGrade(id,message,flag){  
	$.messager.confirm("操作确认",message,function(r){  
		    if (r){   
			$.ajax({
				url : "<%=basePath %>Statistic/jsonChangeGradeById.do?id="+id+"&flag="+flag,
				type : "post",  
		    	dataType : "json",								
				success : function(data) { 	
				    //alert(data.code == 0);								
		  			if(data.code == 0){ 
		  			   // alert(data.message);
		  				$.messager.alert('操作信息',data.message,'info',function(){ 
		  					window.location.href="<%=basePath %>Statistic/gradeList.do"; 
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
		<i class="yw-icon icon-partner"></i><span>积分列表</span>		
		<!-- <span class="fr yw-btn bg-orange line-hei22 mr10 mt9 cur" onclick="exportLog();">导出日志</span>  -->
	    </div>
	    </div>	 	 	
		  
	
		<div class="fl yw-lump mt10">
			<form id="GradeForm" name="GradeForm"
				action="<%=basePath %>Statistic/gradeList.do" method="get">
				<div class=pd10>							
		        	 <div class="fl">  				
				     </div>
				     <div class="fr">	
				     <table>
				     <tr>
					     <td>
					      <span>时间查询：</span>
							<input id="startTimes" name="startTimes" value="${Grade.startTimes}" style="width:180px;height:32px;"
							  data-options="editable:false,onSelect:getStartTimes" type="text"  class="easyui-datebox" /> 
						    <span>~</span>
							<input id="endTimes" name="endTimes" value="${Grade.endTimes}" style="width:180px;height:32px;"
							  data-options="editable:false,onSelect:getEndTimes" type="text"  class="easyui-datebox" />  						               
						  </td>
					  </tr>
					  <tr>
					  <td>			     
						<span>内容查询：</span>
						<input type="text" id="searchName" name="searchName" style="width:177px;height:32px;"
						 class="easyui-validatebox" placeholder="用户名搜索" value="${Grade.searchName}" type="hidden"/> 						
						<span class="yw-btn bg-green cur" onclick="refresh();" style="margin-left: 15px;">刷新</span>							 						 
						<span class="yw-btn bg-blue  cur" onclick="search();" style="margin-left: 15px;">搜索</span>
					   </td>
					   </tr>
					</table>
					</div>
					<div class="cl"></div> 
                <input type="hidden" id="pageNumber" name="pageNo" value="${Grade.pageNo}" />    
                </div>    
             </form>
             </div>                          	             				
           <div class="fl yw-lump"> 
				<table class="yw-cm-table yw-center yw-bg-hover">
					<tr style="background-color:#D6D3D3;font-weight: bold;">
						<th  style="display:none">&nbsp;</th>
						<th >用户姓名</th>  
						<th >积分</th> 
						<th >创建时间</th>  
						<th style="text-align: left;">描述</th>		
						<th >操作</th>  		
					</tr>
					<c:forEach var="item" items="${gradelist}">
						<tr>
							<td  style="display:none">${item.id}</td>
							<td ><span>${item.userName}</span></td>  
							<td ><span >${item.grade}</span></td> 
							<td ><span>${item.createTimes}</span></td> 
							<td style="text-align: left;"><span >${item.description}</span></td>  
							 <td>
								<c:if test="${item.grade < 0 && item.operId == 0}">
								   <a style="margin-left:15px;color:blue"  onclick="SureGrade(${item.id})">兑换</a> 
								   <a style="margin-left:15px;color:red"  onclick="ReturnGrade(${item.id})">取消</a> 								
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
