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
<title>销售统计</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1  ,maximum-scale=1, user-scalable=no" />
<script
	src="${pageContext.request.contextPath}/source/js/pager/jquery.pager.js"></script>
<link
	href="${pageContext.request.contextPath}/source/js/pager/Pager.css"
	rel="stylesheet" />
<script type="text/javascript">
$(document).ready(function(){
	var flag = $("#hid_flag").val();
	var filePath = $("#hid_path").val();
	if(flag == 2){
	//   alert("导出销售统计成功!");
	   window.location.href = "data/tempFile"+filePath;
	   $.messager.alert('错误信息',"导出销售统计成功!",'info');
	}
    if(flag == 4){
    //	alert("导出销售统计失败!");
    	$.messager.alert('错误信息',"导出销售统计失败!",'error');
	}
}); 
function search(){
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
    search();
}
function exportSale(){
    $("#hid_flag").val(1);
   search();
}
</script>
</head>
<body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump">
		<div class="yw-lump-title">	 
		<i class="yw-icon icon-partner"></i><span>销售统计</span>		
		<div class="fr">
		     <span class="yw-btn bg-red cur" style="margin-right:13px;" onclick="exportSale();">导出</span>  
		</div>
	    </div>
	    </div>	 	 	
		  
	
		<div class="fl yw-lump mt10">
			<form id="PayForm" name="PayForm"
				action="<%=basePath %>Statistic/saleList.do" method="get">
				<div class=pd10>							
		        	 <div class="fl">  				
				     </div>
				     <div class="fr">					     
				     <table>
				     <tr><td>
				      <span>时间查询：</span>
				         <input id="hid_flag" name="flag" type="hidden" value="${Pay.flag}"/>
				         <input id="hid_path" name="filePath" type="hidden" value="${Pay.filePath}"/>
						<input id="startTimes" name="startTimes" value="${Pay.startTimes}" style="width:180px;height:32px;"
						  data-options="editable:false,onSelect:getStartTimes" type="text"  class="easyui-datebox" /> 
					    <span>~</span>
						<input id="endTimes" name="endTimes" value="${Pay.endTimes}" style="width:180px;height:32px;"
						  data-options="editable:false,onSelect:getEndTimes" type="text"  class="easyui-datebox" />  						               
					 <%--  </td></tr>
					  <tr><td align="right">			     
						<span>内容查询：</span><input type="text" id="searchName" name="searchName"   validType="SpecialWord" style="width:177px;height:32px;"
						 class="easyui-validatebox" placeholder="搜索" value="${Pay.searchName}" type="hidden"/> 						 --%>
						<span class="yw-btn bg-green cur" onclick="refresh();" style="margin-left: 15px;">刷新</span>							 						 
						<span class="yw-btn bg-blue  cur" onclick="search();" style="margin-left: 15px;">搜索</span>
					   </td></tr>
					</table>
					</div>
					<div class="cl"></div> 
             
                </div>    
             </form>
             </div>                          	             				
           <div class="fl yw-lump"> 
				<table class="yw-cm-table yw-center yw-bg-hover">
					<tr style="background-color:#D6D3D3;font-weight: bold;">
						<th >统计类型</th>	
						<th >有效订单数</th>	 				
						<th >成交订单数</th>						 												
						<th >成交订单额</th> 		
						<!-- <th >付款订单数</th> -->  		 	
						<th >付款订单额</th>  								
						<th >评价人次</th>  
						<th >总体评价</th>  
								
					</tr>
					<c:forEach var="item" items="${salelist}">
						<tr>
							<td ><span >${item.name}</span></td> 
							<td ><span>${item.allCount}</span></td>  				
							<td ><span >${item.orderCount}</span></td> 							
							<td ><span >${item.orderAmount}</span></td> 
							<%-- <td ><span>${item.payCount}</span></td> --%>  
							<td ><span>${item.amount}</span></td>  
							<td ><span >${item.assessCount}</span></td> 
							<td ><span>${item.assess}</span></td>  
								
						</tr>
					</c:forEach>
				</table>
				
				</div>
			 </div>     
  </body>
</html>