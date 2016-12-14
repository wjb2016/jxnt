<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<title>地暖</title>
<script src="${pageContext.request.contextPath}/source/bootstrap/js/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/source/bootstrap/js/common.js"></script>

<link href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/common.css" type="text/css">
<style>
.pinch-zoom,.pinch-zoom img{
	width: 100%;
	-webkit-user-drag: none;
	-moz-user-drag: none;
	-ms-user-drag: none;
	user-drag: none;
}

body,
html{
    text-align: center;
    color: white;
}

div.page{
    margin: 5px auto 10px auto;
    max-width: 500px;
    position: relative;
    text-align: left;
}

div.pinch-zoom {
    position: relative;
}
div.pinch-zoom a{
    color: white;
    position: absolute;
    bottom: 10px;
    right: 10px;
    text-decoration: none;
    background: #333;
    padding: 3px;
    font-size: 11px;
}

ul{
    margin: 0;
    padding: 0;
}
</style>
</head>
<body>

	<!-- 代码部分begin -->
	<header id="JS_mll_header" class="mll-header borderOnePx">
        <a class="layout-back" onclick="javascript:history.go(-1);">返回</a>
        <div class="layout-middle">
            <div id="JS_header_page_title" class="text">地暖</div>
        </div>
    </header>
	<div class="page">
        <div class="pinch-zoom">           
            <img src="<%=basePath%>source/images/din.png">
        </div>
    </div>

	<script src="${pageContext.request.contextPath}/source/bootstrap/js/pinchzoom.js"></script>
	<script type="text/javascript">
	    $(function () {
	        $('div.pinch-zoom').each(function () {
	            new RTP.PinchZoom($(this), {});
	        });
	    })
	</script>
	<!-- 代码部分end -->
</body>
</html>
