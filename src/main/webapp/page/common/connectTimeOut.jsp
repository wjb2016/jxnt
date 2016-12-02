<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'connectTimeOut.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="format-detection" content="telephone=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <link type="text/css" href="${pageContext.request.contextPath}/source/css/global.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/css/mob.common.min.css">
  </head>
  <body>
  	<header id="JS_mll_header" class="mll-header borderOnePx">
		<a class="layout-back" onclick="history.back(-1);"></a>
		<div class="layout-middle">
			<div id="JS_header_page_title" class="text">连接超时</div>
		</div>
	</header>
    <span style="letter-spacing: 2px;font:30px/30px bold '微软雅黑';">网络繁忙，请稍后重试</span>
  </body>
</html>
