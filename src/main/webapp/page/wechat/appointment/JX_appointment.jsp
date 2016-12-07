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
    <title>我要预约</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <script src="${pageContext.request.contextPath}/source/bootstrap/js/jquery-1.9.1.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap-select.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap-select.min.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/common.js"></script>

    <link href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/source/bootstrap/css/common.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap-select.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap-select.css" type="text/css">
   
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=qjCeGQbBaaMPncjx3tFijBhbTqvGAelE"></script>   
	<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  </head>
  <script type="text/javascript">
       // 预约
       function saveInfo(){
          var username = $('#username').val();      // 姓名
          var mobile = $('#phone').val();           // 电话
          var refmobile = $('#refphone').val();     // 推荐人电话
          if(username == ""){
              $('#usernameErrInfo').text('*姓名不能为空，请输入您的姓名！');
              return false;
          }
          if(mobile == ""){
              $('#phoneErrInfo').text('*电话不能为空，请输入您的联系电话！');
              return false;
          }
          if(!(/^1[3|4|5|7|8]\d{9}$/.test(mobile))){
	          $('#phoneErrInfo').text("*手机号有误，请输入正确的手机号！");
	          return false;
          }
          if(refmobile != ""){
            if(!(/^1[3|4|5|7|8]\d{9}$/.test(refmobile))){
	          $('#ref_phoneErrInfo').text("*手机号有误，请输入正确的手机号！");
	          return false;
            }
            if(refmobile == mobile){
              $('#ref_phoneErrInfo').text("*推荐人的电话不能和本次预约的电话号码相同！");
              return falase;
            }
          }
          var btn = $("#btn_login");
          btn.button('loading');
          setTimeout(function () { btn.button('reset'); },2000);
          var formData = $('#appointmentInfo').serialize();
          $.ajax({
          		url:"Appointment/appointtemtInfo.do",
          		type:"post",
          		dataType:"json",
          		data:formData,
          		success: function(data){         		
          		   if(data.code == 1){
          		      var info = "提交成功!";
          		      dealWithAlert(info)
          		      //location.reload();    // 刷新当前页面
          		      reload();
          		   }
          		},
          });
                   
       } 

       // 推荐人
       function referee(){
          var checked = $("input[type='checkbox']").is(':checked');
          if(checked == true){
             $('#refereeTel').removeAttr('style');
          }else{
             $('#refereeTel').css('display','none');
          }
       }
  </script>

<style type="text/css">
html {
	height: 100%
}

body {
	height: 100%;
	margin: 0px;
	padding: 0px
}

#milkMap {
	height: 400px;
	width: 400px;
	border: 1px solid blue;
	margin-top: -30px;
}

.zhanshi {
	background: #db891e;
	font-size: 15px;
	font-weight: bold;;
	color: white;
	border: 1px solid transparent;
	border-color: #db891e;
	height: 30px;
	border-radius: 4px;
}
.custom{
    width: 60px;
    height: 100%;
    border-radius: 100px;
    background-color: #fff;
    border: 0;
    vertical-align: middle;
}
</style>
<body>
<div class="container bg1">
    <header id="JS_mll_header" class="mll-header borderOnePx">
        <div class="layout-middle">
            <div id="JS_header_page_title" class="text">我要预约</div>
        </div>
    </header>
    <div class="banner" style="text-align:center;">
         <img src="source/images/logo1.png" alt="我要预约" class="img-rounded">
    </div>
    <div id="closeMap" style="display: none;">
	      <input type="button" class="zhanshi" onclick="closeBaiduMap()" value="关闭地图"/>
	</div>
    <div style="margin-top: 30px;"></div>
    	
    <div class="img">
    <form role="form" id="appointmentInfo">
        <div class="form-group " >
            <label class="jx_ntFount">姓名<span>*</span></label>
            <input type="text" class="form-control" id="username" name="name" placeholder="请输入您的姓名" >           
            <div class="form-group"><span id="usernameErrInfo" class="errFont"></span></div>
        </div>
        <div class="form-group ">
            <label class="jx_ntFount">电话<span>*</span></label>
            <input type="number" class="form-control" id="phone" name="mobile" placeholder="请输入您的电话" >
            <div class="form-group"><span id="phoneErrInfo" class="errFont"></span></div>
        </div>
        
        <div class="form-group ">
            <label class="jx_ntFount">地址</label>
            <input type="text" class="form-control" id="" name="homeAddress" placeholder="请输入您的地址" >
        </div>
        <div class="form-group">
            <label class="jx_ntFount">户型</label>
            <select class="selectpicker" data-live-search="true" name="houseType">
              <option value="0">请选择</option>
              <option value="1">平层一套一</option>
              <option value="2">平层一套二</option>
              <option value="3">平层一套三</option>
              <option value="4">平层一套四</option>
              <option value="5">跃层/别墅</option>
            </select>
                      
        </div> 
        <div style="margin-top: 10px;"></div>
        <div class="form-group" style="margin-left: 18px;">         
            <input type="checkbox" class="" id="refPerson" onclick="referee()" style="margin-top: -4px;"/>
            <span class="jx_ntFount">推荐人</span>    
        </div>
        <div class="form-group" id="refereeTel" style="display:none;">
            <label class="jx_ntFount" style="margin-left:41px;margin-top: 0px;">推荐人电话</label>
            <input type="number" class="form-control" id="refphone" name="refMobile" placeholder="请输入推荐人的电话" >
            <div class="form-group"><span id="ref_phoneErrInfo" class="errFont"></span></div>
        </div>
        <div class="form-group " style="float: right;">
	        <a href="PerCentral/jxCustomService.do"><img alt="客服" src="source/images/custom.jpg" class="custom"></a>
	    </div>
		<div class="" style="margin-top: 60px;margin-left:auto;margin-right:auto;text-align: center;">
              <input style="width:220px;" type="button" class="jx_userInfo_button btn-default" data-loading-text="Loading..." autocomplete="off" id="btn_login" onclick="saveInfo()"value="提交"/>
        </div>
        
	    <div style="margin-top: 30px;">
	        <span style="display: block;">咨询热线：(028)85554462,13689004567 </span>
            <span style="display: block;">公司名称：成都市精欣暖通工程有限公司 </span>
            <span style="display: block;">公司地址：四川省成都市武侯区金履一路 </span>                                         
            <span style="display: block; margin-left: 63px;">218号优博广场2栋4层2-403</span>
            
            <div id="car" style="display:none;">
                 <span style="display: block; margin-right: 183px;">参考路线：</span>	                   
                 <table>                       
                       <tr>
                          <td align="left"><span style="margin-left: 60px;">57路到川藏路武侯大道口下车步行760米</span></td>         
                       </tr>
                       <tr>
                          <td align="left"><span style="margin-left: 60px;">806b路到成双大道中段下车步行540米</span></td>
                       </tr>                          
                       <tr> 
                           <td align="left"><span style="margin-left: 60px;">318路到川藏路武侯大道口下车步行760米</span></td>
                       </tr>
                 </table>
                 <div style="margin-top: 10px;"></div>
            </div>  
            <input type="button" class="zhanshi" onclick="baiduMap()" value="百度地图展示"/>
            <span style="margin-left: 10px;"></span>
            <input type="button" class="zhanshi" id="bus" value="公交路线展示" onclick="Bus()"/>
            
            
	    </div>
	    
    </form>
    </div>
    <div id="milkMap" style="display:none;">	    
	</div>
</div>

</body>
<script type="text/javascript">
    // 打开地图
    function baiduMap(){
        $('#milkMap').removeAttr('style');
        $('.img').css('display','none');
        $('#closeMap').removeAttr('style');
        var map = new BMap.Map("milkMap");                 // 创建地图实例
	    //map.addControl(new BMap.ZoomControl());          // 添加缩放控件
	    //map.addControl(new BMap.ScaleControl());         // 添加比例尺控件          
	    var point = new BMap.Point(103.990148,30.608872);  // 创建点坐标
	    var marker = new BMap.Marker(point);               // 创建标注
	    map.addOverlay(marker);                            // 将标注添加到地图中
	    map.centerAndZoom(point, 15);                      // 初始化地图，设置中心点坐标和地图级别
	    map.setCurrentCity("成都市");                       // 设置当前城市   
	    map.enableScrollWheelZoom();                       // 开启滚轮缩放功能
	    map.disableDoubleClickZoom();                      // 关闭双击放大功能  
	    map.enableKeyboard();                              // 开启键盘操作功能
    }
    // 关闭地图    
    function closeBaiduMap(){
        $('#milkMap').css('display','none');
        $('.img').removeAttr('style');
        $('#closeMap').css('display','none');
    } 
    // 公交线路
    function Bus(){
       var bus = $('#bus').val();
       if(bus == "公共路线隐藏"){
         $('#car').css('display','none');
         $('#bus').attr('value','公共路线展示');
         return false;
       }
       //$('#car').removeAttr('style','display');
       
       $('#car').slideToggle();
       $('#bus').attr('value','公共路线隐藏');
   
   
    }                       
</script>
</html>
