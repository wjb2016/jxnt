<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>客服</title>
    
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    
	<script src="${pageContext.request.contextPath}/source/bootstrap/js/jquery-1.9.1.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap-tab.js"></script>
    <%-- <script src="${pageContext.request.contextPath}/source/bootstrap/js/common.js"></script> --%>

    <link href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/common.css" type="text/css">

<style type="text/css">
html, body {
	margin: 0;
	padding: 0;
	height: 100%;
}

#container {
	/* min-height: 100%; */
	height: auto !important;
	height: 100%; /*IE6不识别min-height*/
	position: relative;
}

#header {
	background: #ff0;
	padding: 10px;
}

#page {
	width: 960px;
	/* margin: 0 auto; */
	padding-bottom: 40px; /*等于footer的高度*/
}

#footer {
	position: fixed;
	bottom: 0;
	width: 100%;
	height: 40px; /*脚部的高度*/
	clear: both;
}

.footer {
	clear: both;
	position: relative;
	z-index: 10;
	height: 60px;
	margin-top: -86px;
}

.custom {
	background: #e1891e;
	border: 1px solid #e1891e;
	display: inline-block;
	width: 18%;
	height: 40px;
	color: white;
	
	text-align: center;
	padding-top: 4px;
	line-height: 40px;
}

#message tr td {
	padding: 10px;
}

body {
	overflow-x: hidden;
}
</style>
<script type="text/javascript">
    function showSendMessage(question){
       var question = $('#question').val();
       if(question != '' && question != null){
          $.ajax({
             url:"PerCentral/CustomServiceAnswer.do?question="+question,
             type:"POST",
             dataType:"JSON",
             success:function(data){
               
                if(data.code == 1){
					sendMessages(question,data.obj.answer);
				}else{
					sendMessages(question,"亲,请点击<span style='color:red;font-weight: bold;font-size：20px;'onclick='show()'>我需要帮助</span>查看信息关键词!");
				}
                
             },
          });
       }else{
           alert("请先输入内容");
       }
    }
    function show(){
        var question = "我需要帮助";
        $.ajax({
             url:"PerCentral/CustomServiceAnswer.do?question="+question,
             type:"POST",
             dataType:"JSON",
             success:function(data){
               
                if(data.code == 1){
					sendMessages(question,data.obj.answer);
				}else{
					sendMessages(question,"亲,请输入<span style='color:red;font-weight: bold;font-size：20px;'onclick='show()'>我需要帮助</span>查看信息关键词!");
				}
                
             },
          });
    }
    function sendMessages(question,answer){
  			var html = "";
			html = "<tr><td style='text-align:right;'>"+"我："+question+"</td></tr>";
			html += "<tr><td>"+"客服妹妹："+answer+"</td></tr>";
			$("#message").append(html);
			$("#question").val("");	
  		}
    function eKeyup(event){
		if((event.ctrlKey&&window.event.keyCode==13)||window.event.keyCode==13){
			showSendMessage();
			return false;
		} else {
			return true;
		}
    }
</script>
</head> 
<body>
  <div id="container"> 
    <header id="JS_mll_header" class="mll-header borderOnePx" style="background: #e1891e;">
        <a class="layout-back" onclick="javascript:history.go(-1);" style="color: white;">返回</a>
        <div class="layout-middle">
            <div id="JS_header_page_title" class="text"style="color: white;">客服小妹</div>
        </div>
    </header>

    <table style="margin: 10px 10px;font-size: 14px;" id="message">
         <tr>
            <td>客服小妹：亲，欢迎您的光临，有什么问题需要咨询我的吗？</td>
         </tr>
         <!-- <tr>
            <td><div style="margin-top:10px;"></div></td>
         </tr>  -->
         <tr>           
            <td>客服小妹：亲，如果我没能帮上您的忙，请拨打公司电话(028)85554462</td>
         </tr>             
    </table>
	<div style="height: 70px;"> 
	 
	</div> 
    <div id="footer">
      <input type="text" id="question" placeholder="请选择怎么勾搭客服小妹" onKeyDown="eKeyup(event)" style="width: 80%;line-height: 40px;height: 40px;border-radius: 0px;"/>
      <a href="javascript:void(0);"></a><span class="custom" style=""
       onclick="showSendMessage()">发送</span>
      
      <!-- <table>
        <tr>
           <td width = "85%;">
              <input type="text" id="question" placeholder="请选择怎么勾搭客服小妹" onKeyDown="eKeyup(event)" style="width: 100%;line-height: 40px;height: 40px;border-radius: 0px;"/>
           </td>
           <td width = "15%;">
              <a href="javascript:void(0);"></a><span class="custom" style="display:inline-block;width: 100%;height: 40px;color:white;margin-top:-10px;" onclick="showSendMessage()">发送</span>
           </td>
        </tr>
      </table> -->
     </div> 
    </div> 
  </body>
</html>
