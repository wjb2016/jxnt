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
    <title>客户之家</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="yes">

	<script src="${pageContext.request.contextPath}/source/bootstrap/js/jquery-1.9.1.js"></script>
	<script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/common.js"></script>   
    <script type="text/javascript" src="${pageContext.request.contextPath}/source/js/weixin/touchTouch.jquery.js"></script>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/common.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/css/styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/css/touchTouch.css">
    <link href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/css/cloud-admin.css" >
</head>
<style type="text/css">

.userInfo_font {
	font-family: "Open Sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size: 14px;
	color: black;
	background: white;
}
</style>
<script type="text/javascript">
    $(function(){
	$('#thumbs a').touchTouch();
});
</script>
<body class="userInfo_font">

<div class="container">
    <header id="JS_mll_header" class="mll-header borderOnePx">
        
        <div class="layout-middle">
            <div id="JS_header_page_title" class="text">客户之家</div>
        </div>
    </header>
    <c:forEach var="projectImage" items="${imageList}" varStatus="status">
	    <div id="thumbs">
		     <a id="photos" href='${projectImage.imagePath }' style="background-image:url('${projectImage.imagePath }');margin:auto;" title="照片${status.count}"></a>
	         <div style="margin-top: 10px;">
	             
	             <p style="width: 340px;" >${projectImage.userMobile}<span>:</span>${projectImage.message}</p>
	         </div>
	    </div>
    </c:forEach>
</div>
  </body>
</html>
