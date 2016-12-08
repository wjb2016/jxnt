<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String url = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<META http-equiv="X-UA-Compatible" content="IE=edge" />
<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport' />
<title>暖通后台管理——登录</title>
<link type="text/css" href="<%=basePath%>source/css/main.css" rel="stylesheet"/>
<script src="${pageContext.request.contextPath}/source/js/jquery-2.0.3.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/source/js/easyUI/jquery.easyui.min.js"></script>
<script src="${pageContext.request.contextPath}/source/js/easyUI/easyui-lang-zh_CN.js"></script>
<script src="${pageContext.request.contextPath}/source/js/common/validate.js"></script> 

<script>
var baseurl = '<%=url%>';
	$(function(){
		var Ht=$(window).height();
		$('#login-head').css('margin-top',Ht/2-276);
		 // 在这里写你的代码...
		$("#name").keypress(function(e){ 
		  $("#login-alert").hide();
			if(e.keyCode == 13){
				$("#psd").focus();
			}
		 });
		$("#psd").keydown(function(e){ 
		  $("#login-alert").hide();
			if(e.keyCode == 13){
				login();
			}
		});
	});
	function login(){ 
	    $('#loginForm').form('submit', { 
	    	url:"login.do",
	        success:function(data){
	        	var item = eval("(" + data + ")");
	        	if(item.code == 0){
	        		window.location.href =baseurl+item.gotoUrl;
	        		$("#login-alert").html("");
	        		$("#login-alert").hide();
	        	}
	        	else{
	        		$("#login-alert").html("<span style='color:red;'>"+item.message+"</span>");
	        		$("#login-alert").show();
	        	}
	        }
        });  
	}
</script>
</head>

<style>
	body{ /* background:url(source/images/logn-bg.jpg) no-repeat; */font-family: "Arial","Microsoft YaHei","黑体","宋体",sans-serif; font-size:14px; color:#222;margin-top:150px;}
</style>
<body>

	<div class="logn-logo" id="login-head">
		<img style="max-width: 100%;height: auto;border:10% solid #F2F2F2;border-radius:10px;" src="source/images/jxltlogin.jpg" alt="">
	</div>
	<form id="loginForm" method="post"  >
	    <div class="login-panel">
	    	<div class="User-ID">
	        	<span>用户名：</span><input type="text" name="name" style="width:300px;height:28px;"/>
	        </div>
	        <div class="Password">
	        	<span>密&nbsp;&nbsp;&nbsp;码：</span><input type="password" id="psd" name="psd" style="width:300px;height:28px;" onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"/>
	        </div>
	        <div id="login-alert" class="login-rows" style="display:none;"></div>
	        <a href="#"><div class="login-Btn"  onclick="javascript:login()">登录</div></a>
	    </div>
	</form>
</body>
</html>
