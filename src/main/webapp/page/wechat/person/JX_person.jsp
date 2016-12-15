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
    <title>精欣暖通-个人中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
	
	<script src="${pageContext.request.contextPath}/source/bootstrap/js/jquery-1.9.1.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap-tab.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/common.js"></script>

    <link href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/common.css" type="text/css">

  </head>
<style type="text/css">
   .heardImg{
       box-sizing: border-box;
       width: 100px;
       height: 100px;
       padding: 3px;
       border-radius: 50%;
       text-align: center;
       margin: 0 auto;
   }
    .heardImg img{
        width: 100%;
        height: 100%;
        border-radius: 100px;
        background-color: #fff;
        margin-top: 70px;
        vertical-align: middle;
    }
    .jx_center{
        height: 34px;
        overflow: hidden;
        margin: 10px 0 0 0;
        font-size: 15px;
        display: block;
        display: inline-block;
    }
    .jx_center li{
        float: left;
        width: 65px;
        text-align: center;
        font-weight: bold;
        border-right-width: 2px;
        border-right-style: solid;
        border-color: #db891e;
    }
    .jx_center_margin{
        margin-top: 100px;
	    margin-left: auto;
	    margin-right: auto;
	    text-align: center;
    }
    .jx_person_top{
        margin-top: 10px;
    }
    .jx_margin a{
        padding: 0 10px;
    }
</style>
<script type="text/javascript">
    $(function(){      
        isLogin();
       /*  var sex = '${User.sex}'
        if(sex == 1){
          $('#baomi').css('display','none');
          $('#man').removeAttr('style');
        }else if(sex == 2){
          $('#baomi').css('display','none');
          $('#meinv').removeAttr('style');
        }else{
        } */
    });
    // 个人资料
    function userInfo(userId){
        var username = $('#username').text();
	    if(username =="未登录"){
	        window.location.href = "PerCentral/jxLogin.do";
	    }else{
	    	window.location.href = "PerCentral/jxUserInfo.do?userId="+userId;
	    }
    }
    
    // 积分记录
    function userPoints(userId){
        var username = $('#username').text();
	    if(username =="未登录"){
	        window.location.href = "PerCentral/jxLogin.do";
	    }else{
	    	window.location.href = "PerCentral/jxUserPoints.do?userId="+userId;
	    }
    }
</script>
<body>
<div class="container bg1">
    
    <header id="JS_mll_header" class="mll-header borderOnePx">
        <div class="layout-middle">
            <div id="JS_header_page_title" class="text">个人中心</div>
        </div>
    </header>
    <div class="banner heardImg">
        <a href="javascript:void(0);" onclick="userInfo(${sessionScope.ID})">
        <img src="source/images/touxiang1.jpg" alt="我要预约" class="img-rounded" id="baomi">
     <!--    <img src="source/images/man.jpg" alt="我要预约" class="img-rounded" id="man" style="display: none;">
        <img src="source/images/meinv.jpg" alt="我要预约" class="img-rounded" id="meinv" style="display: none;"> -->
        <p style="color: #db891e; font-size: 16px; font-weight: bold;margin-top: 10px;">${sessionScope.userName}</p>
        </a>
    </div>
    <div class="jx_center_margin">
         <ul class="jx_center">
            <li><a href="javascript:void(0);" style="color:#db891e;" onclick="userInfo(${sessionScope.ID})">个人资料</a></li>
            <span style="font-weight: bold;margin-left: 3px;"><a href="javascript:void(0);" onclick="userPoints(${sessionScope.ID})" style="color:#db891e;">积分查询</a></span>
         </ul>
    </div>
    <div class="jx_margin" style="padding: 0 20px;">
        <label style="color:#db891e;font-size: 16px;">客户须知：</label>
        <div style="margin-top: 15px;"></div>
        <a href="PerCentral/jxFloor_heating.do"><span>1.地暖</span></a><br>       
        <div class="jx_person_top"></div>
        <a href="PerCentral/jxAirConditioning.do"><span>2.中央空调</span></a><br>
        <div class="jx_person_top"></div>
        <a href="PerCentral/jxWater.do"><span>3.净水系统</span></a>
    </div>

    <jsp:include page="/page/wechat/footer.jsp"></jsp:include>
</div>
</body>
</html>
