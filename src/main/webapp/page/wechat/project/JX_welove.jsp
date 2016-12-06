<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <base href="<%=basePath%>">    
    <meta charset="UTF-8">
    <title>我爱我家</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/jquery-1.9.1.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap-tab.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap-select.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap-select.min.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/common.js"></script>
	<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/common.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap-select.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap-select.css" type="text/css">
    <!-- FILE UPLOAD -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap-fileupload/bootstrap-fileupload.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/js/jquery-upload/css/jquery.fileupload.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/js/jquery-upload/css/jquery.fileupload-ui.css">
  </head>
<style type="text/css">
.jx_sure {
	margin-left: auto;
	margin-right: auto;
	text-align: center;
}

.jx_ntLove_Fount {
	color: #db891e;
	font-family: "Open Sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size: 14px;
	font-weight: bold;
}

.jx_margin table tr td {
	text-align: center;
}

#loading_center {
	margin: 50px auto;
	width: 250px;
}

#loading {
	width: 250px;
	height: 20px;
	background: url(source/img/bak.jpg) no-repeat;
	background-size: 250px 20px;
}

#loading div {
	width: 0px;
	height: 20px;
	background: url(source/img/pro.jpg) no-repeat;
	background-size: 250px 20px;
	color: #edab4b;
	text-align: center;
	font-family: Tahoma;
	font-size: 12px;
	line-height: 48px;
	padding-top: 8px;
}

.div_overlay {
	display: none;
	position: fixed;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	background-color: black;
	z-index: 1010;
	-moz-opacity: 0.8;
	opacity: .80;
	filter: alpha(opacity = 80);
}

.div_content {
	display: none;
	position: fixed;
	top: 50%;
	left: 50%;
	width: 280px;
	margin-left: -140px;
	height: 150px;
	margin-top: -80px;
	padding: 0;
	border: 5px solid 5c2905;
	background-color: #F0F5F8;
	_position: absolute;
	z-index: 1011;
	overflow: hidden;
}
</style>
<script type="text/javascript">
	$(function() {
		isLogin();
	});

	//选择合同号
	function selectContract() {
		$("#erropMsg").text("");
		$("#proList").html("<option>请先选择合同编号</option>");
		$("#proList").selectpicker('refresh');
		$("#proInfo").hide();
		// 选中的合同编号
		var contractNum = $('#contract').val();
		$.ajax({
			url : "<%=basePath%>Project/jsonLoadProList.do",
	        type:"POST",
	        dataType:"JSON",
	        data:{contractNum:contractNum},
	        success: function (data){
	        	$("#proList").find("option").remove();
	        	var html = "";
	        	if(data.code == 0){
	        		html += "<option value='0'>请选择工程</option>"
	        		for(var i=0;i<data.list.length;i++){
	        			html += "<option value='"+data.list[i].id+"'>"+data.list[i].name+"</option>" 
	        		}
	        	}else{
	        		html += "<option>无工程列表</option>";
	        	}
        		$("#proList").html(html);
        		$("#proList").selectpicker('refresh');
	        }
	   });
	}
	
	//选择工程
	function selectPro(){
	   $("#erropMsg").text("");
	   // 选中的工程编号
	   var proId = $("#proList").val();
	   $.ajax({
	        url:"<%=basePath%>Project/jsonLoadPro.do",
	        type:"POST",
	        dataType:"JSON",
	        data:{proId:proId},
	        success: function (data){
	        	if(data.code == 0){
	        		$("#typeName").text(data.obj.typeName);
	        		$("#startTimes").text(data.obj.startTimes);
	        		$("#endTimes").text(data.obj.endTimes);
	        		$("#id").val(data.obj.id);
	        		if(data.obj.beganTimes){
		        		$("#beganTimes").text(data.obj.beganTimes);
	        		}else{
		        		$("#beganTimes").text("尚未开工");
	        		}
	        		if(data.obj.finishTimes){
		        		$("#finishTimes").text(data.obj.finishTimes);
	        		}else{
		        		$("#finishTimes").text("尚未完工");
	        		}
	        		
	        		if(data.obj.description){
		        		$("#description").text(data.obj.description);
	        		}else{
		        		$("#description").text("暂无描述");
	        		}
	        		
	        		if(data.obj.status == 0){
	        			$("#status").text("未执行");
	        			$("#btn_pro").val("开始施工");
	        			$("#btn_pro").attr("onclick","savePro("+ data.obj.id +","+ data.obj.status +")");
	        			//显示开始施工按钮
	        			$("#pro_btn").show();
	        			$("#img_btn").hide();
	        		}else if(data.obj.status == 1){
	        			$("#status").text("施工中");
	        			$("#btn_pro").val("完成施工");
	        			$("#btn_pro").attr("onclick","savePro("+ data.obj.id +","+ data.obj.status +")");
	        			//显示上传图片及完成施工按钮
	        			$("#img_btn").show();
	        			$("#pro_btn").show();
	        		}else if(data.obj.status == 2){
	        			$("#status").text("已完成");
	        			$("#img_btn").hide();
	        			$("#pro_btn").hide();
	        		}else{
	        			$("#status").text("已中断");
	        		}
	        		
	        		var imgList = "";
	        		for(var i=0;i<data.list.length;i++){
	        			imgList += "<div style='position: relative;display: block;margin:2px auto;width:300px;height:300px;'><image style='display:block;width:300px;height:300px;' src='<%=basePath %>"+ data.list[i].imagePath +"'></image><span style='position:absolute;width:40px;height: 25px;background-color: #e1891e;color: white;top:0;right:0;text-align: center;line-height: 25px;' onclick='delImg(this,"+ data.list[i].id +")'>X</span></div>";
	        		}
	        		$("#imgList").html(imgList);
	        		$("#proInfo").show();
	        	}else{
	        		$("#proInfo").hide();
					$("#erropMsg").text(data.message);
	        	}
	        }
	   });
	}
	
	function delImg(obj,id){
		$(obj).parent().hide();
		$.ajax({
			url:"<%=basePath%>Project/jsonDeletePhotoById.do?id="+id,
			type:"post",
			dataType:"json",
			success:function(data){
				dealWithAlert(data.message);
			}
		});
	}
	
	//开始，结束工程
	function savePro(id,status){
		$.ajax({
			url:"<%=basePath%>Project/jsonSavePro.do?proId="+id+"&status="+status,
			type:"post",
			dataType:"json",
			success:function(data){
				$("#proInfo").hide();
				$("#erropMsg").text(data.message).show();
				setTimeout("refreshPage();",3000);
			}
		})
	}
	
	function refreshPage(){
		$('#contract').selectpicker('val', "0");
		var html = "<option>请先选择合同编号</option>";
    	$("#proList").html(html);
    	$("#proList").selectpicker('refresh');
    	$("#erropMsg").text("").hide();
	}
	
	function showName(obj) {
		if (!(/(?:jpg)$/i.test(obj.value))
				&& !(/(?:jpeg)$/i.test(obj.value))
				&& !(/(?:png)$/i.test(obj.value))) {
			dealWithAlert("选择上传图片文件格式错误！");
			if (window.ActiveXObject) {//for IE
				obj.select();//lect the file ,and clear selection
				document.selection.clear();
			} else if (window.opera) {//for opera
				obj.type = "text";
				obj.type = "file";
			} else
				obj.value = "";//for FF,Chrome,Safari
			return;
		} else {
			 doProgress();
			 var formData = new FormData($("#proform")[0]);  
		     $.ajax({  
		          url: '<%=basePath%>Project/jsonLoadFile.do',  
		          type: 'post',  
		          data: formData,  
		          async: false,  
		          cache: false,  
		          contentType: false,  
		          processData: false,  
		          success: function (data) {
		          	  var jsondata = eval("("+data+")");    
		              if(jsondata.code == 0){
			              var img = "<div style='position: relative;display: block;margin:2px auto;width:300px;height:300px;'><image style='display:block;width:300px;height:300px;' src='<%=basePath %>"+ jsondata.obj.imagePath +"'></image><span style='position:absolute;width:40px;height: 25px;background-color: #e1891e;color: white;top:0;right:0;text-align: center;line-height: 25px;' onclick='delImg(this,"+ jsondata.obj.id +")'>X</span></div>";
		        		  $("#imgList").append(img);
		        		  clearProgress();
		              }else{
		              	  clearProgress();
		              	  dealWithAlert(jsondata.message);
		              }
		          } 
		     });  
		}
	}
	
	function SetProgress(progress) {
		if (progress) {
			$("#loading > div").css("width", String(progress) + "%"); //控制#loading div宽度 
			$("#loading > div").html(String(progress) + "%"); //显示百分比 
		}
	}
	var index = 0;
	function doProgress() {
		$("#lightshow").show();
		$("#fadeshow").show();
		if(index == 100){
			clearProgress();
		}
		setTimeout("doProgress()",200);
		SetProgress(index);
		index++;
	}
	
	function clearProgress(){
		$("#lightshow").hide();
		$("#fadeshow").hide();
		clearTimeout("doProgress()");
		index = 0;
		return;
	}
	
</script>
<body>
<div class="container bg1">
    <header id="JS_mll_header" class="mll-header borderOnePx">
        <div class="layout-middle">
            <div id="JS_header_page_title" class="text">我爱我家</div>
        </div>
    </header>
    <div class="banner" style="text-align:center;">
        <img src="source/images/logo1.png" alt="我要预约" class="img-rounded">
    </div>
    <div class="img">
    <form id="proform" class="form-inline" enctype="multipart/form-data">
         <div class="form-group jx_margin">
            <label class="jx_ntLove_Fount">合同编号</label>
            <select class="selectpicker" data-live-search="true" id="contract" onchange="selectContract()">
              <option value="0">请选择合同编号</option>
              <c:forEach var="project" items="${projects}">
              	<option value="${project}">${project}</option>
              </c:forEach>
            </select> 
        </div> 
        <div class="form-group jx_margin">
            <label class="jx_ntLove_Fount">工程列表</label>
            <select class="selectpicker" data-live-search="true" id="proList" onchange="selectPro()">
            	<option>请先选择合同编号</option>
            </select>                      
        </div> 
        <div id="proInfo" style="display: none;">
	        <div class="jx_margin">
		        <table class="table table-striped jx_ntFount" style="border-bottom:1px solid #db891e;">
		            <tr>
		            	<td colspan="2">工程详情</td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">品牌名称</td>
		                <td id="typeName" style="color: #1e1107;"></td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">工程状态</td>
		                <td id="status" style="color: #1e1107;"></td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">工程描述</td>
		                <td id="description" style="color: #1e1107;">
		                </td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">规划起始时间</td>
		                <td id="startTimes" style="color: #1e1107;"></td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">规划结束时间</td>
		                <td id="endTimes" style="color: #1e1107;"></td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">施工开始时间</td>
		                <td id="beganTimes" style="color: #1e1107;"></td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">施工完工时间</td>
		                <td id="finishTimes" style="color: #1e1107;"></td>
		            </tr>
		        </table>
	        </div>
	        <div class="form-group" id="imgList">
	        </div>
	        <div style="margin-top:10px;text-align: center;display: none;" id="img_btn">
	        	<input type="hidden" id="id" name="id"/>
	            <input class="btn btn-success fileinput-button jx_userInfo_button" value="工程图上传" onclick="$('#file').click();" readonly="readonly">
		         </input>
	        </div>
	        <div style="display: none;">
	            <input type="file" name="file" id="file" onchange="showName(this)">
	        </div>
	        <div style="margin-top:10px;text-align: center;display:none;" id="pro_btn">
	            <input class="btn btn-success fileinput-button jx_userInfo_button" id="btn_pro" readonly="readonly"></input>
	        </div>
        </div>
    </form>
    <div>
    	<span id="erropMsg" style="color: red;"></span>
    </div>
    </div>
    <div style="height: 80px;"></div>
	<div id="lightshow" class="div_content">
		<div id="loading_center">
			<div id="loading">
				<div></div>
			</div>
		</div>
	</div>
	<div id="fadeshow" class="div_overlay"></div>
	<jsp:include page="/page/wechat/footer.jsp"></jsp:include>
</div>
</body>
</html>
