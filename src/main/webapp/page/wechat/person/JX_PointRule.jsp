<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>"> 
    <meta charset="UTF-8">
    <title>积分规则</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
	
	<script src="${pageContext.request.contextPath}/source/bootstrap/js/jquery-1.9.1.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/common.js"></script>

    <link href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/common.css" type="text/css">

</head>
<style type="text/css">
.rule{
   font-size: 16px;
}
</style>
<body style="font-weight: bold;">
<div class="container bg1">
    <header id="JS_mll_header" class="mll-header borderOnePx">
        <a class="layout-back" onclick="javascript:history.go(-1);">返回</a>
        <div class="layout-middle">
            <div id="JS_header_page_title" class="text">积分规则</div>
        </div>
    </header>
    <div>
	 <div style="margin-top: 10px;">
	 	<span class="rule">一、积分规则</span><br>
	 	    <span style="margin-top: 10px;"></span>
			1、积分不可以转赠他人；<br>
			2、积分不清零、不作废、不逾期；<br>
			3、消费10元积1分；<br>
			4、分享一次施工图片及评论奖励积分100分，分享完整施工日志奖励500分；<br>
			5、客户生日当天赠送888分（生日日期只能修改一次）；<br>
			6、客户生日当月（整月不限次数）消费可享受双倍积分（以系统登记生日日期为准）；<br>
			7、每介绍一位真实有效新客户信息积500分；每介绍一位新客户成交积10000分。<br>
	 </div>	
	 <div style="margin-top: 10px;">
	 
	 
		<span class="rule">二、积分兑换规则</span><br>
		 <span style="margin-top: 10px;"></span>
			1、1000分可兑换50元物品（可兑换等额现金）；<br>
			2、积分达到1000分后才可以进行兑换；<br>
			3、积分每次兑换最少100积分；<br>
			4、积分可参与我公司促销产品及积分活动；<br>
			5、积分兑换热线：4006-456-198、028-85554433；<br>
			6、我公司办公室设立积分兑换专区。<br>
	 </div>
    </div>
   
</div>
</body>
</html>
