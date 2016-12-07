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
    <title>注册-登录-忘记密码</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/common.js"></script>
    
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/css/cloud-admin.css" >
    <link href="${pageContext.request.contextPath}/source/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- DATE RANGE PICKER -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/bootstrap/js/daterangepicker-bs3.css" />
    <!-- UNIFORM -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/bootstrap/js/uniform/css/uniform.default.min.css" />
    <!-- ANIMATE -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/css/animatecss/animate.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/common.css" type="text/css">
    <!-- FONTS -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700' rel='stylesheet' type='text/css'>
    <!-- JQUERY -->
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/jquery-2.0.3.min.js"></script>
  </head>
<style type="text/css">

body {  
    overflow-x:hidden;  
    
}  
</style>

<script type="text/javascript">
    // 解决alert有url地址的问题
    function dealWithAlert(info){
    	 window.alert = function(name){
	     var iframe = document.createElement("IFRAME");
	     iframe.style.display="none";
	     iframe.setAttribute("src", 'data:text/plain,');
	     document.documentElement.appendChild(iframe);
	     window.frames[0].window.alert(name);
	     iframe.parentNode.removeChild(iframe);
	     }
    	 alert(info);
     }
    // 登录验证手机号和密码
    function checkUser(){
       var mobile = $('#mobile').val();
       var psd = $('#psd').val();
       if(mobile == ""){
          $('#errInfoPhone').text("*手机号不能为空，请输入手机号！");
          return false;
       }
       if(!(/^1[3|4|5|7|8]\d{9}$/.test(mobile))){
          $('#errInfoPhone').text("*手机号有误，请输入正确的手机号！");
          return false;
       }
       if(psd == ""){
           $('#errInfoPsd').text("*密码不能为空,请输入密码！");
          return false;
       }
       if(psd.length < 6 || psd.length > 18){
          $('#errInfoPsd').text("*密码不能小于6位或大于18位！");
          return false;
       }
       var form = $('#userInfo');
       var formData = form.serialize();
       $.ajax({
          url:"PerCentral/login.do",
          dataType:"json",
          type:"post",
          data:formData,
          success:function (data){
              if(data.code == 1){
                 $('#Alert').removeAttr('style');
                 var info = "登录成功！";
                 dealWithAlert(info);
                 var ref = '';  
				 if (document.referrer.length > 0) {  
				  	ref = document.referrer;  
				 }  
				 try {  
				  	if (ref.length == 0 && opener.location.href.length > 0) {  
				   		ref = opener.location.href;  
				  	}  
				 } catch (e) {
				 } 
				 if(ref.indexOf("PerCentral") != -1){
				    window.location.href = "PerCentral/jxPerson.do";
				 } else {						    
				    // 返回上一级并刷新
				 	location.replace(document.referrer);	
	
				 } 
              }else if(data.code == 2){
                  $('#errInfoPsd').text("*密码错误，请重新输入！");
                 //location.reload();    // 刷新当前页面
              }else if(data.code == 3){
                  $('#errInfoPhone').text("*该用户未注册！");
                  
              }else if(data.code == 4){
                  $('#errInfoPhone').text("*该用户登录受限制！");
                  
              }else{
                 $('#errInfoPsd').text("*用户登录失败,请重新登录！");
              }
          }
       });
    }
    
    // 注册
    function register(){
        // 用户信息验证
       // var username = $('#username').val();             // 用户名
        var mobile = $('#phoneNo').val();                  // 手机号
        var sendCode = $('#sendCode').val();             // 验证码
        var firstPsd = $('#exampleInputPassword1').val();// 第一次密码
	    var psd = $('#exampleInputPassword2').val();     // 确认密码
	   /*  if(username == ""){
	          $('#usernameErrInfo').text("*用户名不能为空，请输入用户名！");
	          return false;
	    } */
	    if(mobile == ""){
	          $('#phoneErrInfo').text("*手机号不能为空，请输入手机号！");
	          return false;
	    }
	    if(!(/^1[3|4|5|7|8]\d{9}$/.test(mobile))){
	          $('#phoneErrInfo').text("*手机号有误，请输入正确的手机号！");
	          return false;
	    }
	    if(sendCode == ""){
	          $('#sendCodeErrInfo').text("*验证码不能为空，请输入验证码！");
	          return false;     
	    }
	    if(firstPsd == ""){
	          $('#firstPsdErrInfo').text("*密码不能为空,请输入密码！");
	          return false;
	    }
	    if(firstPsd.length < 6 || firstPsd.length > 18){
	          $('#firstPsdErrInfo').text("*密码不能小于6位或大于18位！");
	          return false;
	    }
	    if(psd == ""){
	          $('#psdErrInfo').text("*确认密码不能为空,请输入确认密码！");
	          return false;
	    }
	    if(psd.length < 6 || psd.length > 18){
	          $('#psdErrInfo').text("*确认密码不能小于6位或大于18位！");
	          return false;
	    }
	    if(psd != firstPsd){
	          $('#psdErrInfo').text("*确认密码与密码不一致,请重新输入！");
	          return false;
	    }
	    if($("input[type='checkbox']").is(':checked') == false){
	          $('#checkboxErrInfo').text("*必须选中用户注册协议才可以注册哦！");
	          return false;
	    }
	    var form = $('#rigisterForm');
        var formData = form.serialize();//用jquery 获取你的form对象  后边是序化
      
		  $.ajax({
		      type: "POST",
		      url: "PerCentral/register.do",
		      dataType:"JSON",
		      data:formData,
		      success: function(data){
		         var info = null;
		         if(data.code == 1){
		            $('#myAlert').removeAttr('style');
		              info = "注册成功！";
		              dealWithAlert(info);          					  
					  window.location.href = "PerCentral/jxPerson.do";	            
		         }else if(data.code == 2){
		              info = "该用户已经注册，请直接登录！";
		              dealWithAlert(info);
		              reload(); 
		         }else if(data.code == 3){
		              info = "验证码有误，请重新输入！";
		              dealWithAlert(info);
		              
		         }else{
		              info = "注册失败！";
		              dealWithAlert(info);
		         }
		      }
		   });
    }
     // 发送验证码计时器	
	 var InterValObj; //timer变量，控制时间
	 var count = 120; //间隔函数，1秒执行
	 var curCount; //当前剩余秒数
	
	 function sendMessage1() {
	     var mobile = $("#phoneNo").val();;	       
           if(mobile == ""){
              $('#phoneErrInfo').text("*手机号不能为空，请输入！");
              return false;
           }
	     		    
		     curCount=count;
		    //设置button效果开始计时	     
		     $("#btnSendCode1").text("获取"+"("+curCount+"s)");
		     
		     InterValObj = window.setInterval(SetRemainTime1, 1000); //启动计时器，1秒执行一次
			 $.ajax({//向后台发送处理数据
			     url: "PerCentral/sendCode.do?mobile="+mobile, //目标地址
			     type: "POST", //用POST方式传输
			     dataType: "JSON", //数据格式:JSON
			     success: function (data){   		
		     	     if(data.code == 1){
			           $('#sendCodeErrInfo').text("获取验证码失败，请重新获取!");
			         }else if(data.code == 2){
			           $('#sendCodeErrInfo').text("您今天的验证码已使用上限，请谨慎操作！");
			         }	
			     },
			     error: function () { },
		     }); 
	     } 
     //timer处理函数
     function SetRemainTime1() {
           if (curCount == 0) {                
               window.clearInterval(InterValObj);//停止计时器             
               $("#btnSendCode1").text("重新获取");//启用按钮
           }
           else {
               curCount--;
               $("#btnSendCode1").text("获取"+"("+curCount+"s)");
           }
       }
</script>
<body class="login" style=" background: white;">
<div style="overflow-x: hidden;">
<!-- PAGE -->
<section id="page">
    <!-- HEADER -->
    <header>
        <!-- <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div id="logo">
                        <a href="javascript:void(0);">
                           <img src="source/images/logo1.png" width="346" height="160" alt="logo name" />
                        </a>
                    </div>
                </div>
            </div>
        </div> -->
        <div class="banner" style="text-align:center;">
            <img src="source/images/logo1.png" alt="我要预约" class="img-rounded">
        </div>
    </header>
    <!--/HEADER -->
    <!-- LOGIN -->
   
    <section id="login_bg" class="visible">
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="login-box">
                        <h2 class="bigintro" style="color:#db891e;">登录</h2>
                       <!--  <div class="divide-40"></div> -->
                        <form id="userInfo" role="form" method="post">
                            <div class="form-group">
                                <label for="exampleInputEmail1">手机号</label>
                                <i class="fa fa-phone"></i>
                                <input type="text" class="form-control" id="mobile" name="mobile" />
                                <span id="errInfoPhone" class="errFont"></span>
                            </div>
                            <div class="form-group">
                                <label for="exampleInputPassword1">密码</label>
                                <i class="fa fa-lock"></i>
                                <input type="password" class="form-control" id="psd" name="psd"/>
                                <span id="errInfoPsd" class="errFont"></span>
                            </div>
                            <div style="margin-top: 35px;">
                                <!-- <label class="checkbox"><input type="checkbox" class="uniform" value="">记住账号？</label> -->
                                <button type="button" class="btn btn-danger" onclick="checkUser()">登录</button>
                            </div>
                            <div id="Alert" class="alert alert-success" style="display:none;">
						        <a href="../jxlt/PerCentral/jxPerson.do" class="close" data-dismiss="alert">&times;</a>
						        <strong>成功：</strong>登录成功！
						    </div>
                        </form>
                    
                        <div class="login-helpers" style="font-size: 15px;">
                            <a href="#" onclick="swapScreen('forgot_bg');return false;" style="color:#db891e">忘记密码?</a> <br> 还没有账号？
                            <a href="#" onclick="swapScreen('register_bg');return false;" style="color:#db891e">去注册吧!</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    
    <!--/LOGIN -->
    <!-- REGISTER -->
    
    <section id="register_bg" class="font-400">
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="login-box">
                        <h2 class="bigintro">注册</h2>
                       <!--  <div class="divide-40"></div> -->
                        <form id="rigisterForm" role="form">
                          <!--   <div class="form-group">
                                <label for="exampleInputUsername">姓名</label>
                                <i class="fa fa-user"></i>
                                <input type="text" class="form-control" id="username" name="name"/>
                                <span id="usernameErrInfo" class="errFont"></span>
                            </div> -->
                           
                            <div class="form-group">
                                <label>手机号</label>
                                <i class="fa fa-phone"></i>
                                <input type="text" class="form-control" id="phoneNo" name="mobile"/>
                                <span id="phoneErrInfo" class="errFont"></span>
                            </div>
                            <label>验证码</label>
                            <div class="input-group">
                                <i class="fa fa-envelope"></i>
                                <input type="text" class="form-control" id="sendCode" name="sendCode" style="border-radius:6px 0 0 6px;">
                                <span id="btnSendCode1" class="input-group-addon" style="border-radius:0 6px 6px 0;" onclick="sendMessage1()">获取验证码</span>
                                
                            </div>
                            <div><span id="sendCodeErrInfo" class="errFont"></span></div>
                            <div style="margin-bottom:15px;"></div>
                            <div class="form-group">
                                <label for="exampleInputPassword1">密码</label>
                                <i class="fa fa-lock"></i>
                                <input type="password" class="form-control" id="exampleInputPassword1" name="firstPsd">
                                <span id="firstPsdErrInfo" class="errFont"></span>
                            </div>
                            <div class="form-group">
                                <label for="exampleInputPassword2">再次确认密码</label>
                                <i class="fa fa-check-square-o"></i>
                                <input type="password" class="form-control" id="exampleInputPassword2" name="psd">
                                <span id="psdErrInfo" class="errFont"></span>
                            </div>
                            <div>
                                <label class="checkbox"><input type="checkbox" checked="checked" id="choice" class="uniform">我同意并已阅读
                                     <div style="margin-left: 5px;"><a href="PerCentral/jxUserRegisterAgreement.do" >精欣暖通用户注册协议</a></div>
                                </label>
                                <span id="checkboxErrInfo" class="errFont"></span>
                                <button type="button" class="btn btn-success" onclick="register()">注册</button>
                            </div>
                            <div id="myAlert" class="alert alert-success" style="display:none;">
						        <a href="../jxlt/PerCentral/jxPerson.do" class="close" data-dismiss="alert">&times;</a>
						        <strong>成功：</strong>注册成功！
						    </div>
                        </form>
                        
                        <div class="login-helpers" style="font-size: 15px;">
                            <a href="#" onclick="swapScreen('login_bg');return false;" style="color:#db891e"> 返回登录</a> <br>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    
    <!--/REGISTER -->
    <!-- FORGOT PASSWORD -->
   
    <section id="forgot_bg">
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="login-box">
                        <h2 class="bigintro">忘记密码</h2>
                        <!-- <div class="divide-40"></div> -->
                        <form id="forgetPassword" class="form-inline" role="form">
                            <div class="form-group">
                                <label for="">手机号</label>
                                <i class="fa fa-phone"></i>
                                <input type="text" class="form-control" name="mobile" id="phone" />
                                <span id="forgetErrInfo" class="errFont"></span>
                            </div>
                            <label>验证码</label>
                            <div class="input-group">
                                <i class="fa fa-envelope"></i>
                                <input type="text" id="code" class="form-control" name="sendCode" style="border-radius:6px 0 0 6px;">
                                <span class="input-group-addon" style="border-radius:0 6px 6px 0;" id="btnSendCode" onclick="sendMessage()">获取验证码</span>
                            </div>
                            <div><span id="codeErrInfo" class="errFont"></span></div>
                            <div style="margin-bottom:15px;"></div>
                            <div class="form-group">
                                <label for="">新密码</label>
                                <i class="fa fa-lock"></i>
                                <input type="password" id="newPsd" class="form-control" name="firstPsd"/>
                                <span id="newPsdErrInfo" class="errFont"></span>
                            </div>
                            <div class="form-group">
                                <label for="exampleInputPassword2">再次确认密码</label>
                                <i class="fa fa-check-square-o"></i>
                                <input type="password" id="surePsd" class="form-control" name="psd"/>
                                <span id="twoPsdErrInfo" class="errFont"></span>
                            </div>
                            <div class="form-group" style="margin-top: 36px;">
                                <button type="submit" class="btn btn-info" onclick="swapScreen1('forgot_bg2');return false;">提交</button>
                            </div>
                        </form>
                        <div class="login-helpers" style="font-size: 15px;">
                            <a href="#" onclick="swapScreen('login_bg');return false;" style="color:#db891e">返回登录</a> <br>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    
    <!-- FORGOT PASSWORD -->
</section>
</div>
<!--/PAGE -->

<!-- JQUERY UI-->
<script src="${pageContext.request.contextPath}/source/bootstrap/js/jquery-ui-1.10.3.custom.min.js"></script>
<!-- BOOTSTRAP -->
<script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap.min.js"></script>
<!-- UNIFORM -->
<script type="text/javascript" src="${pageContext.request.contextPath}/source/bootstrap/js/uniform/jquery.uniform.min.js"></script>
<!-- BACKSTRETCH -->
<script type="text/javascript" src="${pageContext.request.contextPath}/source/bootstrap/js/backstretch/jquery.backstretch.min.js"></script>
<!-- CUSTOM SCRIPT -->
<script src="${pageContext.request.contextPath}/source/bootstrap/js/script.js"></script>
<script>
    jQuery(document).ready(function() {
        App.setPage("login_bg");  //Set current page
        App.init(); //Initialise plugins and elements
    }); 
</script>
<script type="text/javascript">
     // 发送验证码计时器	
	 var InterValObj; //timer变量，控制时间
	 var count = 120; //间隔函数，1秒执行
	 var curCount; //当前剩余秒数
	
	 function sendMessage(id) {
	      var mobile = $("#phone").val();
         
          if(mobile == ""){
             $('#forgetErrInfo').text("*手机号不能为空，请输入！");
             return false;
          }
	    
	     curCount=count;
	     //设置button效果开始计时	     
	     $("#btnSendCode").text("获取"+"("+curCount+"s)");
	     
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
     //timer处理函数
     function SetRemainTime() {
           if (curCount == 0) {                
               window.clearInterval(InterValObj);//停止计时器             
               $("#btnSendCode").text("重新获取");//启用按钮
           }
           else {
               curCount--;
               $("#btnSendCode").text("获取"+"("+curCount+"s)");
           }
       }
    // 点击事件处理
    function swapScreen(id) {
        jQuery('.visible').removeClass('visible animated fadeInUp');
        jQuery('#'+id).addClass('visible animated fadeInUp');
        
    }
    function swapScreen1(id) {
        var sendCode = $('#code').val();
        var mobile = $('#phone').val();
        var firstPsd = $('#newPsd').val();
        var psd = $('#surePsd').val();
	    if(mobile == ""){	     
		     $('#forgetErrInfo').text("*手机号不能为空，请输入！");
		     return false;
	    }
	    if(!(/^1[3|4|5|7|8]\d{9}$/.test(mobile))){
	         $('#forgetErrInfo').text("*手机号有误，请输入正确的手机号！");
	         return false;
        }
	    if(sendCode == ""){
	         $('#codeErrInfo').text("*短信验证码不能为空,请输入！");
	         return false;
	    }
	    if(firstPsd == ""){
	          $('#newPsdErrInfo').text("*密码不能为空,请输入密码！");
	          return false;
	    }
	    if(firstPsd.length < 6 || firstPsd.length > 18){
	          $('#newPsdErrInfo').text("*密码不能小于6位或大于18位！");
	          return false;
	    }
        if(psd == ""){
              $('#twoPsdErrInfo').text("*密码不能为空,请输入密码！");
              return false;
        }
        if(psd.length < 6 || psd.length > 18){
              $('#twoPsdErrInfo').text("*密码不能小于6位或大于18位！");
              return false;
        }
        var formData = $('#forgetPassword').serialize(); 
	    $.ajax({
             url: "PerCentral/forgetPsd.do",
		     type: "POST", //用POST方式传输
		     dataType: "JSON", //数据格式:JSON
		     data:formData,
		     success: function (data){
		         var info = null; 		
	     	     if(data.code == 1){
	     	        info = "新密码修改成功！";
                    dealWithAlert(info);
		            reload();
		         }else if(data.code == 2){
		            info = "两次密码不一致，请重新输入！";
                    dealWithAlert(info);
		           
		         }else if(data.code == 3){
		            info = "该用户受限制！";
                    dealWithAlert(info);
		            
		         }else if(data.code == 4){
		            info = "该用户未注册！";
                    dealWithAlert(info);
		            
		         }else if(data.code == 5){
		            info = "验证码错误！请重新输入";
                    dealWithAlert(info);
		            
		         }else {
		            info = "修改密码失败！请重新修改密码";
                    dealWithAlert(info);
		            
		         }		
		     },
		     error: function () { },
         });
    }
</script>
  </body>
</html>
