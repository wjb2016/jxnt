<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

//取消订单中断状态
function cancelBreak(orderId){
	$.ajax({
		url:"<%=basePath %>/Order/jsonUpdateBreakPro.do?orderId="+orderId,
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.code == 0){
				$.messager.alert('操作信息',data.message,'info',function(){
  					window.location.href="<%=basePath %>Order/orderInfo.do?id="+orderId;
       			});
			}else{
				$.messager.alert('错误信息',data.message,'error');
			}
		}
	})
}

//作废订单
function savePro(orderId){
	var reason = $("#reason").val();
	var amount = $("#amount").val();
	var preAmount = $("#preAmount").val();
	if(!isNaN(amount)){
	   if(amount - preAmount > 0){
		$.messager.alert('错误提示',"退款金额不能超过总金额",'error');
		return;
	   }	
	}else{
	   $.messager.alert('错误提示',"退款金额必须为数字",'error');
	   return;
	}
	$.ajax({
		url:"<%=basePath %>/Order/jsonUpdateCancelOrder.do?orderId="+orderId+"&amount="+amount+"&reason="+reason,
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.code == 0){
				$.messager.alert('操作信息',data.message,'info',function(){
  					window.location.href="<%=basePath %>Order/orderInfo.do?id="+orderId;
       			});
			}else{
				$.messager.alert('错误信息',data.message,'error');
			}	
			
		}
	})
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
					<c:if test="${order.status == 8}">
						<span class="yw-btn bg-blue mr10" id="sureBtn" onclick="cancelBreak(${order.id});">取消中断</span> 
						<span class="yw-btn bg-green mr10" id="addBtn" onclick="$('#proWindow').window('open')">作废项目</span>
					</c:if>
				    <span class="yw-btn bg-red mr10"  onclick="$('#i_back').click();">返回</span>
				</div>			
			</div>
			<div id="tab1" class="yw-tab ">
				<table class="yw-cm-table font16">
					<tr>
						<td width="120px;" align="right">
							<span>客户名称：</span>
						</td>
						<td>
							<input readonly="readonly" type="text" value="${order.name}" class="easyui-validatebox" style="width:254px;height:28px;" />
						</td>
						<td width="120px;" align="right">客户电话：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.mobile}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>
					</tr>
					<tr>						 
						<td width="120px;" align="right">
						 	<span>订单状态：</span>
						</td>
						<td>
							<c:if test="${order.status == 8 }">
								<input value="已中断" type="text" class="easyui-validatebox" readonly="readonly" style="width:254px;height:28px;"/>
							</c:if>
							<c:if test="${order.status == 10 }">
								<input value="已作废" type="text" class="easyui-validatebox" readonly="readonly" style="width:254px;height:28px;"/>
							</c:if>
						</td>
						<td width="120px;" align="right">
							<span>施工类型：</span>
						</td>
						<td>
							<c:if test="${order.orderTypeId == 0 || order.orderTypeId == null}">
								<input readonly="readonly" type="text" value="其他" class="easyui-validatebox" style="width:254px;height:28px;" />
							</c:if>
							<c:if test="${order.orderTypeId == 1 }">
								<input readonly="readonly" type="text" value="地暖" class="easyui-validatebox" style="width:254px;height:28px;" />
							</c:if>
							<c:if test="${order.orderTypeId == 2 }">
								<input readonly="readonly" type="text" value="中央空调" class="easyui-validatebox" style="width:254px;height:28px;" />
							</c:if>
							<c:if test="${order.orderTypeId == 3 }">
								<input readonly="readonly" type="text" value="净水系统" class="easyui-validatebox" style="width:254px;height:28px;" />
							</c:if>
						</td>
					</tr>	
					<tr>
						<td width="120px;" align="right">
							<span>下单时间：</span>
						</td>
						<td>
							<input type="text" readonly="readonly" value="${order.createDate}" class="easyui-validatebox" style="width:254px;height:28px;" />
						</td>
						<td width="120px;" align="right">交付时间：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.appointTime}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>
					</tr>
					<tr>						 
						 <td width="120px;" align="right">房屋地址：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.homeAddress}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>
						 <td width="120px;" align="right">
							<span>房屋户型：</span>
						 </td>
						 <td>
						 	<c:if test="${order.houseType == 0}">
								<input readonly="readonly" type="text" value="其他" class="easyui-validatebox" style="width:254px;height:28px;" />
							</c:if>
						 	<c:if test="${order.houseType == 1}">
								<input readonly="readonly" type="text" value="平层一套一" class="easyui-validatebox" style="width:254px;height:28px;" />
							</c:if>
							<c:if test="${order.houseType == 2}">
								<input readonly="readonly" type="text" value="平层一套二" class="easyui-validatebox" style="width:254px;height:28px;" />
							</c:if>
							<c:if test="${order.houseType == 3}">
								<input readonly="readonly" type="text" value="平层一套三" class="easyui-validatebox" style="width:254px;height:28px;" />
							</c:if>
							<c:if test="${order.houseType == 4}">
								<input readonly="readonly" type="text" value="平层一套四" class="easyui-validatebox" style="width:254px;height:28px;" />
							</c:if>
							<c:if test="${order.houseType == 5}">
								<input readonly="readonly" type="text" value="跃层/别墅" class="easyui-validatebox" style="width:254px;height:28px;" />
							</c:if>
						 </td>
					</tr>	
					<tr>
						 <td width="120px;" align="right">订单金额：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.amount}" class="easyui-numberbox" data-options="min:0,precision:2" style="width:254px;height:28px;" id="preAmount"/>
						 </td>
						 <td width="120px;" align="right">合同编号：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.contractNumber}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>					 
					</tr>
				<c:if test="${order.serviceStart != null && order.serviceEnd != null}">
					<tr>						 
						 <td width="120px;" align="right">维修起始：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.serviceStarts}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>
						 <td width="120px;" align="right">维修截止：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.serviceEnds}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>
					</tr>
				</c:if>
					<tr>						 
						 <td width="120px;" align="right">留言备注：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.message}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>
						 <td width="120px;" align="right">接单人员：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.operName}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>
					</tr>	
					<tr>
						<td width="120px;" align="right"><span>上门测量：</span></td>
						<td>
						 	<c:if test="${order.measureFlag == 0}">
								<input type="radio" name="measure" value="0" checked="checked" disabled="disabled">否
								<input type="radio" name="measure" value="1" disabled="disabled">是
							</c:if>
							<c:if test="${order.measureFlag == 1}">
								<input type="radio" name="measure" value="0" disabled="disabled">否
								<input type="radio" name="measure" value="1" checked="checked" disabled="disabled">是
							</c:if>
						</td>
						<td width="120px;" align="right">是否交房：</td>
					    <td>
					 		<c:if test="${order.depostFlag == 0}">
								<input type="radio" name="depost" value="0" checked="checked" disabled="disabled">否
								<input type="radio" name="depost" value="1" disabled="disabled">是
							</c:if>
							<c:if test="${order.depostFlag == 1}">
								<input type="radio" name="depost" value="0" disabled="disabled">否
								<input type="radio" name="depost" value="1" checked="checked" disabled="disabled">是
							</c:if>
					    </td>
					</tr>
					<tr>
						<td width="120px;" align="right">是否新房：</td>
					    <td>
					 		<c:if test="${order.newFlag == 0}">
								<input type="radio" name="new" value="0" checked="checked" disabled="disabled">否
								<input type="radio" name="new" value="1" disabled="disabled">是
							</c:if>
							<c:if test="${order.newFlag == 1}">
								<input type="radio" name="new" value="0" disabled="disabled">否
								<input type="radio" name="new" value="1" checked="checked" disabled="disabled">是
							</c:if>
						</td>
						<c:if test="${order.status == 8}">
							<td width="120px;" align="right">中断原因：</td>
						</c:if>
						<c:if test="${order.status == 10}">
							<td width="120px;" align="right">作废原因：</td>
						</c:if>
					    <td>
					    	<textarea rows="5" style="width:254px;vertical-align: middle;border:1px solid #ddd;border-radius:5px;margin:8px 0px;" readonly="readonly">${order.operMessage }</textarea>
						</td>
					</tr>
				</table> 
			</div>
			<c:if test="${fn:length(listProject) > 0}">
				<div class="fl yw-lump">
					<table class="yw-cm-table yw-center yw-bg-hover">
						<tr style="background-color:#D6D3D3;font-weight: bold;">
							<td>工程名称</td>
							<td>施工团队</td>
							<td>品牌类型</td>
							<td>工程状态</td>
							<td>开始时间</td>
							<td>结束时间</td>
						</tr>
						<c:forEach var="item" items="${listProject }">
							<tr>
								<td>${item.name }</td>
								<td>${item.groupName }</td>
								<td>${item.typeName }</td>
								<td>
									<c:if test="${item.status == 0}">
										未执行
									</c:if>
									<c:if test="${item.status == 1}">
										进行中
									</c:if>
									<c:if test="${item.status == 2}">
										已完成
									</c:if>
									<c:if test="${item.status == 3}">
										已中断
									</c:if>
								</td>
								<td>${item.startTimes }</td>
								<td>${item.endTimes }</td>
							</tr>
						</c:forEach>
					</table>
				</div>						
			</c:if> 
		</div>
		<div id="proWindow" class="easyui-window" title="作废原因" style="width:360px;height:250px;overflow:hidden;padding:10px;text-align:center;" iconCls="icon-info" closed="true" modal="true" resizable="false" collapsible="false" minimizable="false" maximizable="false" closable="false" center="true">
				<div>
					<span>作废原因：</span>
					<input type="hidden" id="proId"/> 
					<textarea id="reason" rows="5" style="width:250px;vertical-align: middle;border-radius:5px;resize:none;"></textarea>
				</div>
				<div style="margin-top: 10px;">
					<span>退款金额：</span>
					<input id="amount" type="text" class="easyui-validatebox" class="easyui-numberbox" data-options="min:0,precision:2" style="width:255px;height:28px;"/>
				</div>
		        <div class="yw-window-footer txt-right">
		        	<span id="btnCancel" class="yw-window-btn bg-lightgray mt12"  onclick="$('#reason').val('');$('#proWindow').window('close')">取消</span>
		        	<span class="yw-window-btn bg-blue mt12" onclick="savePro(${order.id});">确定</span>
		        </div> 
			</div>
		</div>
		<div class="cl"></div>
	</div>
	<div class="cl"></div>
  </body>
</html> 
