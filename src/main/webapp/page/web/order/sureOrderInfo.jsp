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
//保存项目工程
function savePro(obj){
	var status = $("#status").val();
	if(status != 0){
		$.messager.alert("错误提示","只能修改未执行的工程！","error");
    	return;
	}
	
	var startTimes =$("#startTimes").datebox("getValue");  
    var endTimes =$("#endTimes").datebox("getValue");
    //检查开始结束时间
    if(startTimes && endTimes){
    	if(!checkTime(startTimes,endTimes)){
    		$.messager.alert("操作提示","开始时间不能大于结束时间！","error");
    		return;
    	}
    }
	if ($('#proForm').form('validate')) {
		$(obj).attr("onclick", "");	
		showProcess(true, '温馨提示', '正在提交数据...'); 
	 	$('#proForm').form('submit',{
	  		success:function(data){ 
				showProcess(false);
	  			data = $.parseJSON(data);
	  			if(data.code==0){	  					
	  				$.messager.alert('保存信息',data.message,'info',function(){
	  					window.location.href="<%=basePath %>Order/orderInfo.do?id="+data.obj.orderId;
        			});
	  			}else{
					$.messager.alert('错误信息',data.message,'error',function(){
						$(obj).attr("onclick", "savePro(this);"); 
        			});
	  			}
	  		}
	  	});
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

//删除工程
function delPro(status,id,orderId){
	if(status != 0){
		$.messager.alert("错误提示","只能删除未执行状态的工程！","error");
		return;
	}
	$.ajax({
		url:"<%=basePath %>Order/jsonDeletePro.do?id="+id,
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.code == 0){
				$.messager.alert("操作提示",data.message,"info",function(){
					window.location.href="<%=basePath %>Order/orderInfo.do?id="+orderId;
				});
			}else{
				$.messager.alert('错误信息',data.message,'error');
  			}
		}
	});
}

//中断工程
function breakPro(status,id,orderId){
	if(status == 3){
		$.messager.alert("错误提示","工程已中断！","error");
		return;
	}else if(status == 0){
		$.messager.alert("错误提示","工程未执行，不能中断！","error");
		return;
	}else if(status == 2){
		$.messager.alert("错误提示","工程已完成，不能中断！","error");
		return;
	}else{}
	
	$.ajax({
		url:"<%=basePath %>Order/jsonUpdatePro.do?id="+id,
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.code == 0){
				$.messager.alert("操作提示",data.message,"info",function(){
					window.location.href="<%=basePath %>Order/orderInfo.do?id="+orderId;
				});
			}else{
				$.messager.alert('错误信息',data.message,'error');
  			}
		}
	});
}

//查看订单
function openPro(id,orderTypeId){
	$.ajax({
		url:"<%=basePath %>Order/jsonLoadPro.do?id="+id+"&orderTypeId="+orderTypeId,
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.code == 0){
				$("#name").val(data.obj.name);
				$("#ordertype").combobox("setValue",data.obj.orderTypeId);
				$("#orderTypeId").val(data.obj.orderTypeId);
				$("#group").combobox("setValue",data.obj.groupId);
				$("#groupId").val(data.obj.groupId);
				$("#startTimes").datebox("setValue",data.obj.startTimes);
				$("#endTimes").datebox("setValue",data.obj.endTimes);
				$("#status").val(data.obj.status);
				$("#id").val(data.obj.id);
				$('#proWindow').window('open');
			}else{
				$.messager.alert('错误信息',data.message,'error');
  			}
		}
	});
}

//工程划分确定
function surePro(obj,id){
	$.messager.confirm('操作提示','工程划分完毕确认，确认之后不可再次划分，是否继续？',function(r){    
	    if(r){    
	        $(obj).attr("onclick","");
	        showProcess(true, '温馨提示', '正在提交数据...'); 
	        $.ajax({
	        	url:"<%=basePath %>Order/jsonSavePro.do?id="+id,
	        	type:"post",
	        	dataType:"json",
	        	success:function(data){ 
					showProcess(false);
		  			if(data.code==0){	  					
		  				$.messager.alert('操作信息',data.message,'info',function(){
		  					window.location.href="<%=basePath %>Order/orderInfo.do?id="+id;
	        			});
		  			}else{
						$.messager.alert('错误信息',data.message,'error',function(){
						    window.location.href="<%=basePath %>Order/orderInfo.do?id="+id;
	        			});
		  			}
		  		}
	        })
	    }    
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
					<span class="yw-btn bg-blue mr10" id="sureBtn" onclick="surePro(this,${order.id});">工程划分完毕</span> 
					<span class="yw-btn bg-green mr10" id="addBtn" onclick="$('#proWindow').window('open')">项目工程划分</span> 
				    <span class="yw-btn bg-red mr10"  onclick="$('#i_back').click();">返回</span>
				</div>			
			</div>
			<div id="tab1" class="yw-tab ">
				<table class="yw-cm-table font16">
					<tr>
						<td width="8%" align="right">
							<span>客户名称：</span>
						</td>
						<td>
							<input readonly="readonly" type="text" value="${order.name}" class="easyui-validatebox" style="width:254px;height:28px;" />
						</td>
						<td width="8%" align="right">客户电话：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.mobile}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>
					</tr>
					<tr>						 
						<td width="8%" align="right">
						 	<span>订单状态：</span>
						</td>
						<td>
							<input value="已确认" type="text" class="easyui-validatebox" readonly="readonly" style="width:254px;height:28px;"/>
						</td>
						<td width="8%" align="right">
							<span>下单时间：</span>
						</td>
						<td>
							<input type="text" readonly="readonly" value="${order.createDate}" class="easyui-validatebox" style="width:254px;height:28px;" />
						</td>
					</tr>	
					<tr>
						<td width="8%" align="right">
							<span>施工类型：</span>
						</td>
						<td>
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
						<td width="8%" align="right">预约时间：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.appointTime}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>
					</tr>
					<tr>						 
						 <td width="8%" align="right">房屋地址：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.homeAddress}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>
						 <td width="8%" align="right">
							<span>房屋户型：</span>
						 </td>
						 <td>
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
						 <td width="8%" align="right">订单金额：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.amount}" class="easyui-numberbox" data-options="min:0,precision:2" style="width:254px;height:28px;" />
						 </td>
						 <td width="8%" align="right">合同编号：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.contractNumber}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>					 
					</tr>
				<c:if test="${order.serviceStart != null && order.serviceEnd != null}">
					<tr>						 
						 <td width="8%" align="right">维修起始：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.serviceStarts}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>
						 <td width="8%" align="right">维修截止：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.serviceEnds}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>
					</tr>
				</c:if>
					<tr>						 
						 <td width="8%" align="right">留言备注：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.message}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>
						 <td width="8%" align="right">接单人员：</td>
						 <td>
						 	<input readonly="readonly" type="text" value="${order.operName}" class="easyui-validatebox" style="width:254px;height:28px;" />
						 </td>
					</tr>	
					<tr>
						<td width="8%" align="right"><span>上门测量：</span></td>
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
						<td width="8%" align="right">是否交房：</td>
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
						<td width="8%" align="right">是否新房：</td>
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
							<td>操作</td>
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
								<td>
									<c:if test="${order.status == 2}">
										<a onclick="delPro(${item.status},${item.id },${item.orderId })" style="margin-right: 10px;color:red">删除</a>
									</c:if>
									<c:if test="${item.status == 1 }">
										<a onclick="breakPro(${item.status},${item.id },${item.orderId })" style="margin-right: 10px;color:#FF8D41">中断</a>
									</c:if>
									<a onclick="window.location.href='<%=basePath %>Order/proInfo.do?id=${item.id }'+'&orderTypeId=${order.orderTypeId}'+'&orderId=${order.id }'" style="color:blue">查看</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>						
			</c:if> 
			<div id="proWindow" class="easyui-window" title="工程信息" style="width:560px;height:560px;overflow:hidden;padding:10px;text-align:center;" iconCls="icon-info" closed="true" modal="true" resizable="false" collapsible="false" minimizable="false" maximizable="false" closable="false" center="true">
				<form id="proForm" name="proForm" action="<%=basePath %>Order/jsonSaveOrUpdatePro.do" method="post">
			        <table class="yw-cm-table font16">
						<tr>
							<td width="20%" align="right">
								<span>工程名称：</span>
							</td>
							<td align="left">
								<input id="name" name="name" type="text" class="easyui-validatebox" required="true" validType="Length[1,20]" style="width:254px;height:28px;" />
							</td>
						</tr>
						<tr>
							<td width="20%" align="right">
								<span>施工团队：</span>
							</td>
							<td align="left">
								<select id="group" class="easyui-combobox" required="true" data-options="editable:false,onSelect:function(record){ $('#groupId').val(record.value) }" style="width:254px;height:28px;">
									 <option value="0">=请选择施工团队=</option>
									 <c:forEach var="item" items="${listGroup}">
										 <option value="${item.id }">${item.name }</option>
									 </c:forEach>
								</select>
								<input type="hidden" id="groupId" name="groupId"/>
								<input type="hidden" id="status"/>
								<input type="hidden" id="id" name="id"/>
								<input type="hidden" id="orderId" name="orderId" value="${order.id }"/>
							</td>
						</tr>
						<tr>
							<td width="20%" align="right">
								<span>品牌类型：</span>
							</td>
							<td align="left">
								<select id="ordertype" class="easyui-combobox" required="true" data-options="editable:false,onSelect:function(record){ $('#orderTypeId').val(record.value) }" style="width:254px;height:28px;">
									 <option value="0">=请选择品牌类型=</option>
									 <c:forEach var="item" items="${listOrderType}">
										 <option value="${item.id }">${item.name }</option>
									 </c:forEach>
								</select>
								<input type="hidden" id="orderTypeId" name="orderTypeId"/>
							</td>
						</tr>
						<tr>
							<td width="20%" align="right">
								<span>起始时间：</span>
							</td>
							<td align="left">
								<input id="startTimes" name="startTimes" style="width:254px;height:28px;" type="text" required="true" class="easyui-datebox" data-options="editable:false"/>
							</td>
						</tr>
						<tr>
							<td width="20%" align="right">
								<span>截止时间：</span>
							</td>
							<td align="left">
								<input id="endTimes" name="endTimes" style="width:254px;height:28px;" type="text" required="true" class="easyui-datebox" data-options="editable:false"/>
							</td>
						</tr>
						<tr style="padding-top: 10px;">
							<td width="20%" align="right">
								<span>工程描述：</span>
							</td>
							<td align="left">
								<textarea id="description" name="description" rows="5" style="width:248px;vertical-align: middle;"></textarea>
							</td>
						</tr>
					</table>
				</form>
		        <div class="yw-window-footer txt-right">
		        	<span id="btnCancel" class="yw-window-btn bg-lightgray mt12"  onclick="location.reload(true)">取消</span>
		        	<span class="yw-window-btn bg-blue mt12" onclick="savePro(this);">确定</span>
		        </div> 
			</div>
		</div>
		<div class="cl"></div>
	</div>
	<div class="cl"></div>
  </body>
</html> 
