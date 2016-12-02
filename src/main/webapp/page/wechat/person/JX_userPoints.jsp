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
    <title>积分记录</title>
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
table tr td{
    padding: 10px;
    border-top: solid 1px #d8d8d8;
}
.jx_margin p{
    padding: 0 10px;

}
.userPoint_font {
	font-family: "Open Sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size: 15px;
	color: #db891e;
	background: white;
	font-weight: bold;
}
.message-box .message div {
   font-size: 15px;
}
.message-box .button {
    height: 42px;
    border-top: solid 1px #d8d8d8;
    display: -webkit-box;
    display: -moz-box;
    display: -o-box;
    display: -ms-box;
    width: -moz-available;
    margin-top: 10px;
}
.button li {
    height: 42px;
    line-height: 42px;
    text-align: center;
    color: #da0000;
    font-size: 17px;
    font-weight: bold;
    -webkit-box-flex: 1;
    -moz-box-flex: 1;
    -ms-flex: 1;
    -o-box-flex: 1;
    box-flex: 1;
}
.assess tr td{
    padding-left: 30px;
    text-align: center;
}
i, ol, ul {
    list-style: none;
}
.point{
    width: 50px;
    border-radius: 0px;
    
}
</style>
<script type="text/javascript">
$(function(){      
   isLogin();
});
   

// 兑换积分
function cashing(){
   //$('#win').removeAttr('style','display');
   document.getElementById("win").style.display="";
   document.getElementById("JS_mask").style.display="";
}
// 取消
function cancelAssess(){
     $('#win').css('display','none');
     $('#JS_mask').css('display','none');
}
// 确定兑换积分
function submitCashing(id){
     var point = $('#cash').val();
     var allPoint = ${user.grade};
     if(point == ""){
        $('#pointErrorInfo').text('*兑换的积分不能为空');
        return false;
     }
     if(point > allPoint){
        $('#pointErrorInfo').text('*兑换的积分不能大于用户的积分，请重新输入！');
        return false;
     }
     if(point < 100){
        $('#pointErrorInfo').text('*兑换的积分不能低于100积分，请重新输入！');
        return false;
     }
   
     $.ajax({
        url:"PerCentral/cashingPoint.do",
        type:"POST",
        dataType:"JSON",
        data:{grade:point,id:id},
        success: function(data){
           if(data.code == 1){
           
               var info = "兑换积分申请已成功提交！相关人员审核通过即可生效";
               dealWithAlert(info);
               // 返回上一级并刷新
			   location.replace(document.referrer);
           }else{               
               alert("兑换失败！请重新操作");
           }
        },
     });
}
</script>
<body>
<div class="container bg1">
    <header id="JS_mll_header" class="mll-header borderOnePx">
        <a class="layout-back" onclick="javascript:history.go(-1);"></a>
        <div class="layout-middle">
            <div id="JS_header_page_title" class="text">积分详情</div>
        </div>
    </header>
    
    <div class="jx_margin userPoint_font">
        <span style="margin-left: 10px;">积分: ${user.grade}</span>
        <a href="javascript:void(0)" style="float: right;margin-right: 30px;"><span onclick="cashing()">兑换</span></a>
    </div>
   
    <div class="jx_margin userPoint_font">
        <p>积分明细</p>
    </div>
    <c:forEach var="grade" items="${gradeObj}">
    <table style="width: 99%;font-size:16px;height:auto;">
        <tr>
            <td align="left" style="width: 50%;">${grade.description}</td>
             <td align="center" style="color: red">${grade.grade}</td>
            <td align="right">${grade.createTimes}</td>
        </tr>        
    </table> 

    </c:forEach>
    
    <div id="win" style="width: 100%; position: fixed; left: 50%; top: 50%; z-index: 500; display:none;margin: -50%;">
        <div class="mob-layer-content" style="background-color: #f5eaea;margin-left:auto;margin-right:auto;width:80%">
        <div id="JS_message_box" class="message-box">
        <div class="message">
	        <div style="padding-left:-20px;">
		        <div style="font-size:16px;padding:5px 0;text-align:center;font-weight:bold;color:black;">兑换积分</div>
			        <div style="margin-left: 30px;">
				        <div style="margin-top: 10px;">可用积分： ${user.grade}</div> 
				        <div style="margin-top: 10px;">本次兑换：<input type="text" id="cash" class="point" style="border: 1px solid #cec0c0;"/></div>
				        <span id="pointErrorInfo" class="errFont"></span>
				    </div> 
			    </div>
	        </div>
	     <ul class="button">
	          <li id="JS_msgbox_btn_0" style="font-weight:normal" onclick="cancelAssess()">取消</li>
	          <li id="JS_msgbox_btn_1" style="border-left:1px solid #d8d8d8;" onclick="submitCashing(${sessionScope.ID})">确定</li>
	     </ul>
       </div>
       </div>
       
</div>
<div id="JS_mask" style="height: 820px; width: 100%; position: absolute; top: 0px; left: 0px; z-index: 499; background: rgba(0, 0, 0, 0.498039);display:none;"></div>

   <jsp:include page="/page/wechat/footer.jsp"></jsp:include>
</div>
</body>
</html>