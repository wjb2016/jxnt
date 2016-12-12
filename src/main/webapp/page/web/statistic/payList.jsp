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
<title>支付管理</title>
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
	    pagenumber:'${Pay.pageNo}',                         /* 表示初始页数 */
	    pagecount:'${Pay.pageCount}',                      /* 表示总页数 */
	    totalCount:'${Pay.totalCount}',
	    buttonClickCallback:PageClick                     /* 表示点击分页数按钮调用的方法 */                  
	});
}); 
		
PageClick = function(pageclickednumber) {
	$("#pager").pager({
	    pagenumber:pageclickednumber,                 /* 表示启示页 */
	    pagecount:'${Pay.pageCount}',                  /* 表示最大页数pagecount */
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
	if ($('#PayForm').form('validate')) {
		PayForm.submit();
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
</script>
</head>
<body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump">
		<div class="yw-lump-title">	 
		<i class="yw-icon icon-partner"></i><span>支付列表</span>		
		<!-- <span class="fr yw-btn bg-orange line-hei22 mr10 mt9 cur" onclick="exportLog();">导出日志</span>  -->
	    </div>
	    </div>	 	 	
		  
	
		<div class="fl yw-lump mt10">
			<form id="PayForm" name="PayForm"
				action="<%=basePath %>Statistic/payList.do" method="get">
				<div class=pd10>							
		        	 <div class="fl">  				
				     </div>
				     <div class="fr">					     
				     <table>
				     <tr><td>
				      <span>时间查询：</span>
						<input id="startTimes" name="startTimes" value="${Pay.startTimes}" style="width:180px;height:32px;"
						  data-options="editable:false,onSelect:getStartTimes" type="text"  class="easyui-datebox" /> 
					    <span>~</span>
						<input id="endTimes" name="endTimes" value="${Pay.endTimes}" style="width:180px;height:32px;"
						  data-options="editable:false,onSelect:getEndTimes" type="text"  class="easyui-datebox" />  						               
					  </td></tr>
					  <tr><td>			     
						<span>内容查询：</span>
						<input type="text" onblur="valueTrim(this);" id="searchName" name="searchName"  style="width:177px;height:32px;"
						 class="easyui-validatebox" placeholder="合同号搜索" value="${Pay.searchName}" type="hidden"/> 						
						<span class="yw-btn bg-green cur" onclick="refresh();" style="margin-left: 15px;">刷新</span>							 						 
						<span class="yw-btn bg-blue  cur" onclick="search();" style="margin-left: 15px;">搜索</span>
					   </td></tr>
					</table>
					</div>
					<div class="cl"></div> 
                <input type="hidden" id="pageNumber" name="pageNo" value="${Pay.pageNo}" />    
                </div>    
             </form>
             </div>                          	             				
           <div class="fl yw-lump"> 
				<table class="yw-cm-table yw-center yw-bg-hover">
					<tr style="background-color:#D6D3D3;font-weight: bold;">
						<th  style="display:none">&nbsp;</th>
						<th >合同号</th> 
						<th >交易类型</th>		 
						<th >支付金额</th>  
						<th >支付时间</th>  
						<!-- <th >退款金额</th>  
						<th >退款时间</th>   -->
								
					</tr>
					<c:forEach var="item" items="${paylist}">
						<tr>
							<td  style="display:none">${item.id}</td>							
							<td ><span >${item.contract}</span></td> 
							<td ><span>${item.payAccount}</span></td>  
							<td ><span >${item.payPrice}</span></td> 
							<td ><span>${item.payTimes}</span></td>  
							<%-- <td ><span >${item.payBackPrice}</span></td> 
							<td ><span>${item.payBackTimes}</span></td>   --%>
							<%-- <td>
								<a href="javascript:void(0);" style="color:blue;" onclick="window.location.href='<%=basePath %>Pay/autoInfo.do?id=${item.id}'">详情</a>
								<c:if test="${item.flag == 0}">
								   <a style="margin-left:15px;color:red"  onclick="ChangeAuto(${item.id},${item.flag})">重新启用</a> 
								</c:if>
								<c:if test="${item.flag == 1}">
								   <a style="margin-left:15px;color:green"  onclick="ChangeAuto(${item.id},${item.flag})">立即停用</a> 
								</c:if>
							</td>		 --%>					
						</tr>
					</c:forEach>
				</table>
				<div class="page" id="pager"></div>
				</div>
			 </div>     
  </body>
</html>