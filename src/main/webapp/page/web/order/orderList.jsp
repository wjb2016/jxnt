<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<title>订单管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1  ,maximum-scale=1, user-scalable=no" />
<meta http-equiv="refresh" content="60">
<script	src="${pageContext.request.contextPath}/source/js/pager/jquery.pager.js"></script>
<link href="${pageContext.request.contextPath}/source/js/pager/Pager.css" rel="stylesheet" />
<script type="text/javascript">
$(document).ready(function() {
	$("#pager").pager({
		pagenumber : '${Order.pageNo}', /* 表示初始页数 */
		pagecount : '${Order.pageCount}', /* 表示总页数 */
		totalCount : '${Order.totalCount}',
		buttonClickCallback : PageClick
	/* 表示点击分页数按钮调用的方法 */
	});
	
	//订单类型
	var orderTypeId =$("#orderTypeId").val();
	if(orderTypeId){
		$("#ordertype").combobox("setValue",orderTypeId);
	}
	
	//订单状态
	var orderStatus =$("#orderStatus").val();
	if(orderStatus != null && orderStatus != ""){
		$("#status").combobox("setValue",orderStatus);
	}
});

PageClick = function(pageclickednumber) {
	$("#pager").pager({
		pagenumber : pageclickednumber, /* 表示启示页 */
		pagecount : '${Order.pageCount}', /* 表示最大页数pagecount */
		buttonClickCallback : PageClick
	/* 表示点击页数时的调用的方法就可实现javascript分页功能 */
	});

	$("#pageNumber").val(pageclickednumber); /* 给pageNumber从新赋值 */
	/* 执行Action */
	pagesearch();
}
function search() {
	$("#pageNumber").val("1");
	pagesearch();
}
function pagesearch() {
	var startTimes =$("#startTimes").val();  
    var endTimes =$("#endTimes").val();
    //检查开始结束时间
    if(startTimes && endTimes){
    	if(!checkTime(startTimes,endTimes)){
    		$.messager.alert("操作提示","开始时间不能大于结束时间！","error");
    		return;
    	}
    }
	if ($('#OrderForm').form('validate')) {
		OrderForm.submit();
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

//导出订单
function exportOrder(){
	showProcess(true, '温馨提示', '正在导出订单数据...'); 
     $.ajax({
          url : "<%=basePath %>Order/exportExcel.do",
          type : "post",
          dataType:"json", 
          success : function(data) { 
	           showProcess(false); 
               if(data.code == 0){
                   $.messager.alert('获取信息', data.message, 'info',function() {
                       window.location.href="<%=basePath %>Order/downOrderExcel.do";
                   });
	           }else{
                   $.messager.alert('操作信息', data.message, 'error');
               }
         }
     });
}
function refresh(){
    $("#startTimes").val("");
    $("#endTimes").val("");
    $("#searchName").val("");
    $("#orderTypeId").val("0");
    $("#orderStatus").val("-1");
    search();
}
function deleteOrder(id,oName){
$.messager.confirm("操作确认","确认删除客户："+oName+" 的订单？",function(r){  
		    if (r){   
			  	$.ajax({
			  		url:'<%=basePath%>Order/jsonDeleteOrder.do?id='+id,
			  		type : "post",
			          dataType:"json", 
			          success : function(data) { 
				           showProcess(false); 
			               if(data.code == 0){
			                   $.messager.alert('获取信息', data.message, 'info',function() {
			                       window.location.href="<%=basePath %>Order/orderList.do?pageNo="+$("#pageNumber").val();
			                   });
				           }else{
			                   $.messager.alert('操作信息', data.message, 'error');
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
				<i class="yw-icon icon-partner"></i>
				<span>
					订单管理
				</span>
				<div class="fr" style="margin-right: 15px;"> 
					<span class="yw-btn bg-green cur" style="margin-right: 10px;" onclick="window.location.href='<%=basePath%>Order/orderInfo.do?id=0'">新增订单</span> 
					<span class="yw-btn bg-red cur" onclick="exportOrder();">导出</span> 
				</div>
			</div>
		</div>
		<div class="fr yw-lump mt10" style="padding-bottom: 10px;">
			<form id="OrderForm" name="OrderForm" action="<%=basePath%>Order/orderList.do" method="post">
				<div class="pd10">
					<table class="fr">
						<tr>
							<td width="80px" height="32px">
								<span>订单查询：</span>
							</td>
							<td width="200px" height="32px">
								<input id="searchName" type="text" onblur="valueTrim(this);" name="searchName" class="easyui-validatebox"	placeholder="用户名/手机号/合同号搜索" value="${Order.searchName}" style="width: 178px;height:28px;"/>
							</td>
							<td width="200px" height="32px">
								<select id="ordertype" class="easyui-combobox" data-options="editable:false,onSelect:function(record){ $('#orderTypeId').val(record.value) }" style="width:177px;height: 32px;">
									 <option value="0">=请选择订单类型=</option>
									 <option value="1">地暖</option>
									 <option value="2">中央空调</option>
									 <option value="3">净水系统</option>
								</select>
							</td>
							<td width="150px" height="32px">
								<select id="status" class="easyui-combobox" data-options="editable:false,onSelect:function(record){ $('#orderStatus').val(record.value) }" style="width: 177px;height: 32px;">
									 <option value="-1">=请选择订单状态=</option>
									 <option value="0">待确认</option>
									 <option value="2">已确认</option>
									 <option value="4">施工中</option>
									 <option value="6">已完成</option>
									 <option value="8">已中断</option>
									 <option value="10">已作废</option> 
								</select>
							</td>
						</tr>
						<tr>
							<td width="80px" height="32px">
								<span>日期查询：</span>
							</td>
							<td colspan="2">
								<input id="startTime" value="${Order.startTimes}" style="width:180px;height:32px;" type="text" class="easyui-datebox" data-options="editable:false,onSelect:function(date){ $('#startTimes').val(date.getFullYear()+ '-' +(date.getMonth()+1)+ '-' + date.getDate()) }"/>
								<span style="padding:0 2px;">~</span>
								<input id="endTime" value="${Order.endTimes}" style="width:180px;height:32px;" type="text" class="easyui-datebox" data-options="editable:false,onSelect:function(date){ $('#endTimes').val(date.getFullYear()+ '-' +(date.getMonth()+1)+ '-' + date.getDate()) }"/>
							</td>
							<td width="150px" height="32px" >
							<span class="yw-btn bg-green cur" onclick="refresh();">刷新</span>							 						 
							<span class="yw-btn bg-blue  cur" onclick="search();" style="margin-left:8px;">搜索</span>
							</td>
						</tr>
					</table>
					<input type="hidden" id="pageNumber" name="pageNo" value="${Order.pageNo}" />
					<input type="hidden" id="orderTypeId" name="orderTypeId" value="${Order.orderTypeId}" />
					<input type="hidden" id="orderStatus" name="status" value="${Order.status}" />
					<input type="hidden" id="startTimes" name="startTimes" value="${Order.startTimes}"/>
					<input type="hidden" id="endTimes" name="endTimes" value="${Order.endTimes}"/>
				</div>
			</form>
		</div>
		<div class="fl yw-lump">
			<table class="yw-cm-table yw-center yw-bg-hover">
				<tr style="background-color:#D6D3D3;font-weight: bold;">
					<th width="5%">用户名</th>
					<th width="10%">手机号</th>
					<th width="10%">合同号</th>
					<th width="15%">下单时间</th>
					<th width="10%">房屋户型</th>
					<th width="20%" style="text-align: left!important;">地址</th>
					<th width="10%">订单类型</th>
					<th width="10%">订单状态</th>
					<th width="10%">操作</th>
				</tr>
				<c:forEach var="item" items="${OrderList}">
					<tr>
						<td style="text-align: center;">${item.name}</td>
						<td style="text-align: center;">${item.mobile}</td>
						<td style="text-align: center;">${item.contractNumber}</td>
						<td style="text-align: center;">${item.createDate}</td>
						<td style="text-align: center;">
							<c:if test="${item.houseType == 1}">
								平层一套一
							</c:if>
							<c:if test="${item.houseType == 2}">
								平层一套二
							</c:if>
							<c:if test="${item.houseType == 3}">
								平层一套三
							</c:if>
							<c:if test="${item.houseType == 4}">
								平层一套四
							</c:if>
							<c:if test="${item.houseType == 5}">
								跃层/别墅
							</c:if>
						</td>
						<td style="text-align: left;">${item.homeAddress}</td>
						<td style="text-align: center;">
							<c:if test="${item.orderTypeId == 1}">
								地暖
							</c:if>
							<c:if test="${item.orderTypeId == 2}">
								中央空调
							</c:if>
							<c:if test="${item.orderTypeId == 3}">
								净水系统
							</c:if>
						</td>
						<td style="text-align: center;">
							<c:if test="${item.status == 0 || item.status == 1}">
								待确认
							</c:if>
							<c:if test="${item.status == 2}">
								已确认
							</c:if>
							<c:if test="${item.status == 4}">
								施工中
							</c:if>
							<c:if test="${item.status == 6}">
								已完成
							</c:if>
							<c:if test="${item.status == 8}">
								已中断
							</c:if>
							<c:if test="${item.status == 10}">
								已作废
							</c:if>
						</td>
						<td style="text-align: center;">
							<a href="javascript:void(0);" style="color:blue;" onclick="window.location.href='<%=basePath%>Order/orderInfo.do?id=${item.id}'">详情</a>
						    <a href="javascript:void(0);" style="color:red;margin-left:15px;" onclick="deleteOrder(${item.id},'${item.name}');">删除</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<div class="page" id="pager"></div>
		</div>
	</div>
</body>
</html>
