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
   
    <title>自动应答信息</title>
    
	<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1  ,maximum-scale=1, user-scalable=no" /> 
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">	
function saveAuto(obj){
    if ($('#autoInfoForm').form('validate')) {
	$(obj).attr("onclick", "");	
	showProcess(true, '温馨提示', '正在提交数据...'); 
	 $('#autoInfoForm').form('submit',{
	  		success:function(data){ 
				showProcess(false);
	  			data = $.parseJSON(data);
	  			if(data.code==0){	  					
	  				$.messager.alert('保存信息',data.message,'info',function(){
	  					window.location.href="<%=basePath %>Auto/autoList.do";
        			});
	  			}else{
					$.messager.alert('错误信息',data.message,'error',function(){
        			});
					$(obj).attr("onclick", "saveAuto(this);"); 
	  			}
	  		}
	  	 });
	}
}
	 
	</script>
  </head> 
  <body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump">
			<div class="yw-lump-title"> 
					<i id="i_back" class="yw-icon icon-back" onclick="window.location.href='<%=basePath %>Auto/autoList.do'"></i><span>自动应答列表</span>
			</div>
		</div>
		<div class="fl yw-lump mt10">
			<div class="yw-bi-rows">
				<div class="yw-bi-tabs mt5" id="ywTabs">
				   <span class="yw-bi-now">自动应答设置</span>
				</div>
				<div class="fr">
					<span class="yw-btn bg-green mr10" id="saveBtn" onclick="saveAuto(this);">保存</span> 
					<span class="yw-btn bg-red mr10"  onclick="$('#i_back').click();">返回</span>
				</div>
			</div>
				<form id="autoInfoForm" name="autoInfoForm" action="<%=basePath %>Auto/jsonSaveOrUpdateAuto.do" method="post">
					<div id="tab1" class="yw-tab">
					<table class="yw-cm-table font16" id="autoTable">
						<tr>
							<td align="left">&nbsp;&nbsp;关&nbsp;键&nbsp;字&nbsp;:
							    <input id="keyword" class="easyui-validatebox" name="keyword" type="text" onblur="valueTrim(this);"  doc="autoInfo" value="${Auto.keyword}" required="true"  validType="Length[1,100]" style="width:354px;height:28px;"/>
								<input type="hidden" id="hid_autoId" name="id" doc="autoInfo" value="${Auto.id}"/>
								<input type="hidden" name="flag" value="${Auto.flag}"/>
								<span style="color:red">*</span>
							</td>
						</tr> 
				          <tr>
							<td  align="left">自动答复:
							     <textarea  placeholder="请输入自动回答信息" style="width:354px;height:100px;vertical-align: middle;"  doc="autoInfo" value="${Auto.answer}" required="true"  name="answer" class="textarea easyui-validatebox">${Auto.answer}</textarea>
								<span style="color:red">*</span>
							</td>
						</tr> 
						<c:if test="${Auto.id > 0}">
						    <tr>
						        <td align="left">操作时间:&nbsp;&nbsp;${Auto.createTimes}</td>
						    </tr>
						</c:if>
					</table>  
					</div>
				</form>
			</div> 
		
		<div class="cl"></div>
	</div>
	<div class="cl"></div>
	</div>
</body>
</html>  