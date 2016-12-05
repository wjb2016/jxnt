// 返回顶部
function gotoTop(f,d)
{
	
	f = f || 0.1;
	d = d || 10;
	var c = 0;
	var l = 0;
	var b = 0;
	var j = 0;
	var a = 0;
	var h = 0;
	if (document.documentElement) {
		c = document.documentElement.scrollLeft || 0;
		l = document.documentElement.scrollTop || 0
	}
	if (document.body) {
		b = document.body.scrollLeft || 0;
		j = document.body.scrollTop || 0
	}
	var a = window.scrollX || 0;
	var h = window.scrollY || 0;
	var k = Math.max(c, Math.max(b, a));
	var i = Math.max(l, Math.max(j, h));
	var g = 1 + f;
	window.scrollTo(Math.floor(k / g), Math.floor(i / g));
	if (k > 0 || i > 0) {
		var e = "gotoTop(" + f + ", " + d + ")";
		window.setTimeout(e, d)
	}
}

function F_Open_dialog()
{
    $('#btn_file').click();
}

//判断用户是否登录
function isLogin(){	
	 var userName = $('#username').text();
     if(userName !="未登录"){
         $('#exit').removeClass('exit');
         $('#fankui').css('display','none');
         $('#username').attr("href","PerCentral/jxPerson.do");
     }else{
     	 $('#username').attr("href","PerCentral/jxLogin.do");
     }
}
//解决alert有url地址的问题
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
//退出登录
function exitLogin(){
    
    window.location.href = "PerCentral/jxUserExitLogin.do";
} 
//解决安卓在微信页面刷新的问题
function reload(){
	  window.location.href=window.location.href+"?id="+10000*Math.random();
}
