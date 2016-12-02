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
<title>订单管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1  ,maximum-scale=1, user-scalable=no" />
<script type="text/javascript">
$(function(){
	//订单类型
	var orderTypeId =$("#orderTypeId").val();
	if(orderTypeId){
		$("#ordertype").combobox("setValue",orderTypeId);
	}
	
	//户型
	var houseType =$("#houseType").val();
	if(houseType){
		$("#housetypecom").combobox("setValue",houseType);
	}
	
	//创建时间
	var createDate =  $('#create').val();
	if (createDate) {
		$('#createDate').val(createDate);
	} else {
		var d = new Date();
		var vYear = d.getFullYear();
		var vMon = d.getMonth() + 1;
		var vDay = d.getDate();
		var h = d.getHours();
		var m = d.getMinutes();
		var nowTime = vYear + "-" + (vMon < 10 ? "0" + vMon : vMon)
				+ "-" + (vDay < 10 ? "0" + vDay : vDay) + " " + (h < 10 ? "0" + h : h)
				+ ":" + (m < 10 ? "0" + m : m);
		$('#createDate').datetimebox('setValue', nowTime);
	}
	
	//预约时间
	var appointTime =  $("#appoint").val();
	if (appointTime) {
		$('#appointTime').datetimebox('setValue', appointTime);
	} else {
		var d = new Date();
		var vYear = d.getFullYear();
		var vMon = d.getMonth() + 1;
		var vDay = d.getDate();
		var h = d.getHours();
		var m = d.getMinutes();
		var nowTime = vYear + "-" + (vMon < 10 ? "0" + vMon : vMon)
				+ "-" + (vDay < 10 ? "0" + vDay : vDay) + " " + (h < 10 ? "0" + h : h)
				+ ":" + (m < 10 ? "0" + m : m);
		$('#appointTime').datetimebox('setValue', nowTime);
	}
	
})

//保存订单
function saveOrder(obj){
	//获取单选按钮的值并赋值给隐藏文本域
	var measure = $("input[name='measure']:checked").val();
	$("#measureFlag").val(measure);
	var depost = $("input[name='depost']:checked").val();
	$("#depostFlag").val(depost);
	var newflag = $("input[name='new']:checked").val();
	$("#newFlag").val(newflag);
	if ($('#orderForm').form('validate')) {
		$(obj).attr("onclick", "");	
		showProcess(true, '温馨提示', '正在提交数据...'); 
	 	$('#orderForm').form('submit',{
	  		success:function(data){ 
				showProcess(false);
	  			data = $.parseJSON(data);
	  			if(data.code==0){	  					
	  				$.messager.alert('保存信息',data.message,'info',function(){
	  					window.location.href="<%=basePath %>Order/orderInfo.do?id=" + data.obj.id;
        			});
	  			}else{
					$.messager.alert('错误信息',data.message,'error',function(){
					   if(data.obj){
					       window.location.href="<%=basePath %>Order/orderInfo.do?id=" + data.obj.id;
					   }else{
					       $(obj).attr("onclick", "saveOrder(this);");
					   }
        			});
	  			}
	  		}
	  	});
	}
}

function falseOrder(){
   var id= $("#hid_id").val();
   var flag = 0;
   $.messager.sureCheck('取消订单判断','该用户信息是否真实有效？',function(r){    
	    if(r){  
	        flag = 1;  
	    }
	    showProcess(true, '温馨提示', '正在提交数据...');
	    $.ajax({
	        	url:"<%=basePath %>Order/jsonSaveFalseOrder.do?id="+id+"&flag="+flag,
	        	type:"post",
	        	dataType:"json",
	        	success:function(data){
	        		showProcess(false);
	        		if(data.code == 0){
	        			$.messager.alert("操作提示",data.message,"info",function(){
					        window.location.href="<%=basePath %>Order/orderInfo.do?id="+id; 
	        			})
	        		}else{
	        			$.messager.alert("操作提示",data.message,"error");
	        		}
	        	}
	        });  
   }); 
   
}
</script>
</head>
<body>
    <div class="con-right" id="conRight">
      <div class="fl yw-lump">
			<div class="yw-lump-title"> 
				<i id="i_back" class="yw-icon icon-back" onclick="window.location.href='<%=basePath %>Order/orderList.do'"></i>
				<span>订单详情</span>
			</div>
		</div>
		<div class="fl yw-lump mt10">
			<div class="yw-bi-rows">
				<div class="yw-bi-tabs mt5" id="ywTabs">
					<span class="yw-bi-now">订单详情</span>
				</div>			
				<div class="fr">
				    <span class="yw-btn bg-yellow mr10" id="saveBtn" onclick="falseOrder();">取消订单</span> 				    
				    <span class="yw-btn bg-green mr10" id="saveBtn" onclick="saveOrder(this);">确认订单</span> 
				    <span class="yw-btn bg-red mr10"  onclick="$('#i_back').click();">返回</span>
				</div>			
			</div>
			<form id="orderForm" name="orderForm" action="<%=basePath %>Order/jsonSaveOrUpdateOrder.do" method="post">
				<div id="tab1" class="yw-tab ">
					<table class="yw-cm-table font16">
						<tr>
							<td width="8%" align="right">
								<span>客户名称：</span>
							</td>
							<td>
								<input id="name" name="name" type="text" value="${order.name}" class="easyui-validatebox" onblur="valueTrim(this);" required="true" validType="Length[1,20]" style="width:254px;height:28px;" />
							</td>
							<td width="8%" align="right">客户电话：</td>
							 <td>
							 	<input id="mobile" name="mobile" type="text" value="${order.mobile}" class="easyui-validatebox" onblur="valueTrim(this);" required="true" validType="mobile" style="width:254px;height:28px;" />
							 </td>
						</tr>
						<tr>						 
							<td width="8%" align="right">
							 	<span>订单状态：</span>
							</td>
							<td>
								<input value="待确认" type="text" class="easyui-validatebox" readonly="readonly" style="width:254px;height:28px;"/>
								<input type="hidden" name="status" value="0"/>
							</td>
							<td width="8%" align="right">
								<span>下单时间：</span>
							</td>
							<td>
								<c:if test="${order.createDate == null}">
									<input id="createDate" name="createDate" onblur="valueTrim(this);" type="text" class="easyui-datetimebox" data-options="showSeconds:false,editable:false" validType="Length[1,20]" style="width:254px;height:28px;" />
								</c:if>
								<c:if test="${order.createDate != null}">
									<input id="createDate" type="text" readonly="readonly" value="${order.createDate}" class="easyui-validatebox" style="width:254px;height:28px;" />
								</c:if>
								<input id="create" type="hidden" value="${order.createDate}"/>
							</td>
						</tr>	
						<tr>
							<td width="8%" align="right">
								<span>施工类型：</span>
							</td>
							<td>
								<select id="ordertype" class="easyui-combobox" data-options="editable:false,onSelect:function(record){ $('#orderTypeId').val(record.value) }" style="width:254px;height:28px;">
									 <option value="0">=请选择订单类型=</option>
									 <option value="1">地暖</option>
									 <option value="2">中央空调</option>
									 <option value="3">净水系统</option>
								</select>
								<input id="orderTypeId" name="orderTypeId" type="hidden" value="${order.orderTypeId}"/>
							</td>
							<td width="8%" align="right">交付时间：</td>
							 <td>
							 	<input id="appointTime" name="appointTime" onblur="valueTrim(this);" type="text" value="${order.appointTime}" class="easyui-datetimebox" data-options="showSeconds:false,editable:false" validType="Length[1,20]" style="width:254px;height:28px;" />
							 	<input id="appoint" type="hidden" value="${order.appointTime}"/>
							 </td>
						</tr>
						<tr>						 
							 <td width="8%" align="right">房屋地址：</td>
							 <td>
							 	<input id="homeAddress" name="homeAddress" placeholder="请输入房屋地址" type="text" value="${order.homeAddress}" class="easyui-validatebox" onblur="valueTrim(this);" required="true" validType="Length[1,20]" style="width:254px;height:28px;" />
							 </td>
							 <td width="8%" align="right">
								<span>房屋户型：</span>
							 </td>
							 <td>
								<select id="housetypecom" class="easyui-combobox" data-options="editable:false,onSelect:function(record){ $('#houseType').val(record.value) }" style="width:254px;height:28px;">
									 <option value="0">=请选择房屋户型=</option>
									 <option value="1">平层一套一</option>
									 <option value="2">平层一套二</option>
									 <option value="3">平层一套三</option>
									 <option value="4">平层一套四</option>
									 <option value="5">跃层/别墅</option>
								</select>
								<input id="houseType" name="houseType" type="hidden" value="${order.houseType}"/>
							 </td>
						</tr>	
						<tr>
							 <td width="8%" align="right">订单金额：</td>
							 <td>
							 	<c:if test="${order.amount == 0 }">
								 	<input id="amount" name="amount" placeholder="请输入订单金额" onblur="valueTrim(this);" type="text" class="easyui-numberbox" data-options="min:0,precision:2" required="true" validType="Length[1,20]" style="width:254px;height:28px;" />
							 	</c:if>
							 	<c:if test="${order.amount != 0 }">
								 	<input id="amount" name="amount" placeholder="请输入订单金额" onblur="valueTrim(this);" type="text" value="${order.amount}" class="easyui-numberbox" data-options="min:0,precision:2" required="true" validType="Length[1,20]" style="width:254px;height:28px;" />
							 	</c:if>
							 </td>
							 <td width="8%" align="right">合同编号：</td>
							 <td>
							 	<input id="contractNumber" name="contractNumber" placeholder="请输入合同编号" onblur="valueTrim(this);" type="text" value="${order.contractNumber}" class="easyui-validatebox" required="true" validType="Length[1,20]" style="width:254px;height:28px;" />
							 </td>					 
						</tr>
						<tr>
							<td width="8%" align="right">
								<span>保修起始：</span>
							</td>
							<td>
							 	<input id="serviceStarts" name="serviceStarts" onblur="valueTrim(this);" type="text" value="${order.serviceStarts}" class="easyui-datebox" data-options="showSeconds:false,editable:false" validType="Length[1,20]" style="width:254px;height:28px;" />
							 	<input id="starts" type="hidden" value="${order.serviceStarts}"/>
							 </td>
							<td width="8%" align="right">保修截止：</td>
							 <td>
							 	<input id="serviceEnds" name="serviceEnds" onblur="valueTrim(this);" type="text" value="${order.serviceEnds}" class="easyui-datebox" data-options="showSeconds:false,editable:false" validType="Length[1,20]" style="width:254px;height:28px;" />
							 	<input id="ends" type="hidden" value="${order.serviceEnds}"/>
							 </td>
						</tr>
						<tr>						 
							 <td width="8%" align="right">留言备注：</td>
							 <td>
							 	<input id="message" name="message" placeholder="请输入留言备注" onblur="valueTrim(this);" type="text" value="${order.message}" class="easyui-validatebox" style="width:254px;height:28px;" />
							 </td>
							 <td width="8%" align="right"><span>上门测量：</span></td>
							 <td>
							 	<c:if test="${order.measureFlag == 0}">
									<input type="radio" name="measure" value="0" checked="checked">否
									<input type="radio" name="measure" value="1">是
								</c:if>
								<c:if test="${order.measureFlag == 1}">
									<input type="radio" name="measure" value="0">否
									<input type="radio" name="measure" value="1" checked="checked">是
								</c:if>
								<c:if test="${order.measureFlag == null}">
									<input type="radio" name="measure" value="0" checked="checked">否
									<input type="radio" name="measure" value="1">是
								</c:if>
							 </td>
						</tr>	
						<tr>
							<td width="8%" align="right">是否交房：</td>
						    <td>
						 		<c:if test="${order.depostFlag == 0}">
									<input type="radio" name="depost" value="0" checked="checked">否
									<input type="radio" name="depost" value="1">是
								</c:if>
								<c:if test="${order.depostFlag == 1}">
									<input type="radio" name="depost" value="0">否
									<input type="radio" name="depost" value="1" checked="checked">是
								</c:if>
								<c:if test="${order.depostFlag == null}">
									<input type="radio" name="depost" value="0" checked="checked">否
									<input type="radio" name="depost" value="1">是
								</c:if>
						    </td>
							<td width="8%" align="right">是否新房：</td>
						    <td>
						 		<c:if test="${order.newFlag == 0}">
									<input type="radio" name="new" value="0" checked="checked">否
									<input type="radio" name="new" value="1">是
								</c:if>
								<c:if test="${order.newFlag == 1}">
									<input type="radio" name="new" value="0">否
									<input type="radio" name="new" value="1" checked="checked">是
								</c:if>
								<c:if test="${order.newFlag == null}">
									<input type="radio" name="new" value="0" checked="checked">否
									<input type="radio" name="new" value="1">是
								</c:if>
								<input id="depostFlag" name="depostFlag" type="hidden" value="${order.depostFlag}"/>
								<input id="newFlag" name="newFlag" type="hidden" value="${order.newFlag}"/>
								<input id="measureFlag" name="measureFlag" type="hidden" value="${order.measureFlag}"/>
								<input id="refMobile" name="refMobile" type="hidden" value="${order.refMobile}"/>
								<%--<input id="status" name="status" type="hidden" value="${order.status}"/>--%>
								<input id="hid_id" name="id" type="hidden" value="${order.id}"/>
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
