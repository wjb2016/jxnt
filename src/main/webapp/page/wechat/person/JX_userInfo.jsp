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
    <title>个人资料</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/bootstrap/css/lyz.calendar.css" >
    <link href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/common.css" type="text/css">
    
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/jquery-1.9.1.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/common.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/lyz.calendar.min.js"></script>

</head>
<style type="text/css">
.jx_font {
	font-size: 15px;
	font-family: Microsoft YaHei;
}

.userInfo_font {
	font-family: "Open Sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size: 14px;
	color: black;
	background: white;
}

.jx_margin_left {
	margin-left: 25px;
}

</style>
<script type="text/javascript">
    $(function(){
       isLogin();   
       //日期控件

       $("#txtEndDate").calendar();
       var birthday = "${birthday}";
	   if(birthday != ""){
	      $('#exampleInputBirth').removeAttr('style','display');
	      $('#txtEndDate').css('display','none');
	   } 
	   var sex = "${user.sex}";
	   if(sex == "0"){
	      $('#inlineRadio3').attr('checked','checked');
	   }else if (sex == "1"){
	      $('#inlineRadio1').attr('checked','checked');
	   }else {
	      $('#inlineRadio2').attr('checked','checked');
	   }
    });
    function saveUserInfo(){
       var name = $('#exampleInputName2').val();
       var mobile = $("#phoneNo").val();
       var firstPsd = $('#first').val();
       var psd = $('#surePsd').val();
	   if(mobile == ""){
         $('#userErrInfo').text("*手机号不能为空，请输入！");	         
	     return false;
	   }
	   if(firstPsd != psd){
	     $('#psdErrInfo').text("*两次输入密码不相同，请重新输入！");
	   } 
	   if(firstPsd == ""){
	     $('#psdErrInfo').text("*密码不能为空！");
	   }
       var formData = $('#userInfo1').serialize();
       var btn = $("#btn_login");
       btn.button('loading');
       setTimeout(function () { btn.button('reset'); },2000);
     
       $.ajax({  
            type:"POST",    
            url:"PerCentral/jsonSaveUserInfo.do",           
            dataType:"JSON",
            data:formData,
            success: function(data){
            var info = null;
                if(data.code == 1){
                   info = "保存成功！";          
                   dealWithAlert(info);
                   window.location.href = "PerCentral/jxPerson.do";
                }else if(data.code == 2){
                   info = "验证码有误，请重新输入!";          
                   dealWithAlert(info);
                  
                }else if(data.code == 3){
                   info = "两次输入的密码不一致，请重新输入!";          
                   dealWithAlert(info);
                   
                }else if(data.code == 0){
                   info = "解绑新的手机号存在，不能解绑！";          
                   dealWithAlert(info);                 
                   alterPhone();
                }else{
                   alert("保存失败!");
                   return false;
                }
            }
       });
       
    }
    // 解绑手机
    function alterPhone(){
       var button = $('#unbundling').val();
       if(button == "返回"){
          location.reload(); 
       }
       $('#unbundling').val('返回');
       $('#phoneNo').removeAttr('value');
       $('#phoneNo').removeAttr('readOnly');
       $('#alter').removeAttr('style');
       $('#alterSurePsd').removeAttr('style');
       $('#alterPsd').removeAttr('style');
 
    }
    // 发送验证码计时器	
	 var InterValObj; //timer变量，控制时间
	 var count = 120; //间隔函数，1秒执行
	 var curCount; //当前剩余秒数
	
	 function sendMessage(id) {
	     var mobile = $("#phoneNo").val();
	     if(mobile == ""){
	         $('#userErrInfo').text("*手机号不能为空，请输入！");	         
		     return false;
	     }else if(!(/^1[3|4|5|7|8]\d{9}$/.test(mobile))){
	          $('#userErrInfo').text("*手机号有误，请输入正确的手机号！");
	          return false;
         }else{
		     curCount=count;
		    //设置button效果开始计时	     
		     $("#btnSendCode").val(curCount+"s");
		     
		     InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
			 $.ajax({//向后台发送处理数据
			     url: "PerCentral/sendCode.do?mobile="+mobile, //目标地址
			     type: "POST", //用POST方式传输
			     dataType: "JSON", //数据格式:JSON
			     success: function (data){   		
		     	     if(data.code == 1){
			           $('#codeErrInfo').text("获取验证码失败，请重新获取!");
			         }else if(data.code == 2){
			           $('#codeErrInfo').text("您今天的验证码已使用上限，请谨慎操作！");
			         }	
			     },
			     error: function () { },
		     }); 
	     }
     } 
     //timer处理函数
     function SetRemainTime() {
           if (curCount == 0) {                
               window.clearInterval(InterValObj);//停止计时器             
               $("#btnSendCode").val("获取");//启用按钮
           }
           else {
               curCount--;
               $("#btnSendCode").val(curCount+"s");
           }
       }
</script>
<body class="userInfo_font">
<header id="JS_mll_header" class="mll-header borderOnePx">
        <a class="layout-back" onclick="javascript:history.go(-1);">返回</a>
        <div class="layout-middle">
            <div id="JS_header_page_title" class="text">个人资料</div>
        </div>
</header>
<div class="container">
    
    <form role="form" id="userInfo1" style="text-align: center;">
      <div class="form-group jx_margin " >          
            <label class="jx_font jx_ntFount">姓名</label>
            <input type="text" class="form-control" placeholder="请输入您的姓名" id="exampleInputName2" name="name" value="${user.name}">
            <input type="hidden" name="id" id="userid" value="${user.id}"/>
      </div>
       <div class="form-group">
          <label class="jx_font jx_ntFount">性别</label>     
		  <input type="radio" name="sex" id="inlineRadio1" value="1"> 男	
		  <span style="margin-left: 10px;"></span>
		  <input type="radio" name="sex" id="inlineRadio2" value="2"> 女	
		  <span style="margin-left: 10px;"></span>	
		  <input type="radio" name="sex" id="inlineRadio3" value="0" style="width:20px;"> 保密	
          <div>                         
               <span id="sexErrInfo" class="errFont"></span>
          </div>
      </div>  
      <div class="form-group  " >          
            <label class="jx_font jx_ntFount">生日</label>
        <input type="text" class="form-control" id="txtEndDate" name="birthday" placeholder="请输入您的出生年月日"/>
        <div id="exampleInputBirth" style="display: none;">
			  <input type="text" class="form-control" readOnly="true" value="${birthday}">
		</div>
       </div>
       <div class="form-group">
            <label class="jx_font jx_ntFount">地址</label>
            <input type="text" class="form-control" placeholder="请输入您的地址" id="address" name="address" value="${user.address}">
        </div>
       
        <div class="form-group" >
            <label class="jx_font jx_ntFount" style="margin-left: 15px;">手机号</label>
            <input type="number" class="form-control" placeholder="请输入手机号" readOnly="true"  id="phoneNo" name="mobile"  value="${user.mobile}" style="border-radius:6px 0 0 6px;width: 146px;">
            <input type="button" id="unbundling" class="input-group-addon" style="border: 1px solid #e1891e;width: 55px;height: 30px;margin-bottom: 10px;border-radius: 0 6px 6px 0px;"
             onclick="alterPhone()" value="解绑"/>
            <div style="margin-right: 60px;">
                <span id="userErrInfo" class="errFont"></span>
            </div>
           
        </div>
         <div id="alter" class="form-group" style="display: none;">
	            <label class="jx_font jx_ntFount" style="margin-left: 15px;">验证码</label>
	            <input type="number" class="form-control" placeholder="请输入验证码" id="" name="sendCode" style="border-radius:6px 0 0 6px;width: 146px;">
	            <input type="button" id="btnSendCode" class="input-group-addon" style="border: 1px solid #e1891e;width: 54px;height: 30px;margin-bottom: 10px;border-radius: 0 6px 6px 0px;"
	             onclick="sendMessage()" value="获取"/>
	            <div style="margin-right: 60px;">
	              <span id="codeErrInfo" class="errFont"></span>
	            </div>
	            
        </div>
        <div id="alterPsd" class="form-group" style="display: none;">           
            <label class="jx_font jx_ntFount" style="margin-left: 15px;">新密码</label>
            <input type="password" class="form-control" placeholder="请输入新密码" id="first" name="firstPsd">
            <div style="margin-right: 60px;">
              <span id="firstPsdErrInfo" class="errFont"></span>
            </div>
        </div>
        
        <div id="alterSurePsd" class="form-group" style="display: none;">
            <label class="jx_font jx_ntFount" style="margin-left: 30px;">确认密码</label>
            <input type="password" class="form-control" placeholder="请输入再次输入新密码" id="surePsd" name="psd">
               <div style="margin-right: 60px;">
              <span id="psdErrInfo" class="errFont"></span>
            </div>
        </div>
        <div style="margin-top: 50px;margin-left:auto;margin-right:auto;text-align: center;">
             <input type="button" class="jx_userInfo_button btn-default " data-loading-text="Loading..." autocomplete="off" id="btn_login" onclick="saveUserInfo()" value="保存" style="width: 220px;background: #db891e;color: white;border: 1px solid #db891e;"/>
        </div>
    </form>
   <jsp:include page="/page/wechat/footer.jsp"></jsp:include>
</div>
  </body>
</html>
