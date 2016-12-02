<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% 
String uri = request.getRequestURI();
String  url  =  uri.substring(uri.lastIndexOf("/")+1);
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>暖通后台管理<sitemesh:write property='title'/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link type="text/css" href="${pageContext.request.contextPath}/source/css/base.css" rel="stylesheet"/>
<link type="text/css" href="${pageContext.request.contextPath}/source/css/global.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/js/easyUI/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/source/js/easyUI/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/source/js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/source/js/easyUI/jquery.easyui.min.js"></script>
<script src="${pageContext.request.contextPath}/source/js/easyUI/easyui-lang-zh_CN.js"></script>
<script src="${pageContext.request.contextPath}/source/js/common/validate.js"></script>
<script src="${pageContext.request.contextPath}/source/js/common/common.js"></script>
<script src="${pageContext.request.contextPath}/source/js/common/uiMessageHandler.js"></script>
<script	src="${pageContext.request.contextPath}/source/js/jquery.jplayer.min.js"></script>
<link href="${pageContext.request.contextPath}/source/css/jplayer.blue.monday.css" rel="stylesheet" />
<!-- 导入页面引用的特殊js和css文件 -->
<sitemesh:write property='head' />
<script type="text/javascript">
	<%-- $.yw={
			 currURL:'${pageContext.request.contextPath}',
			 sessionAccount:'${sessionScope.userInfo.account}',
			 sessionUserId:'${sessionScope.userInfo.id}',
			 pageURL:'<%=request.getAttribute("requestCurrURL")%>'
	};
	//这个方法用来启动该页面的ReverseAjax功能
	dwr.engine.setActiveReverseAjax( true);
	//设置在页面关闭时，通知服务端销毁会话
	dwr.engine.setNotifyServerOnPageUnload( true); --%>
	
	$(function(){
		//计算网页高度
		setHei();
		$(window).resize(function(){
			setHei();
		});
	});
	
	function setHei(){
		$("#content").removeAttr("style");
		var h = $(window).height();
		var hh = document.body.offsetHeight;
		var t = $("#content").offset().top;
		if(hh>h)h = hh+20;
		var v = h-t-1;
		var rh = $("#conRight").height();
		if(rh>v){
			v = rh+50;
		}
		$("#content").css("height",v-30);
	}
</script>
</head>

<body>
	<div id="main">
		<div id="header"><jsp:include page="/page/decorators/header.jsp"></jsp:include></div>
		<div id="content">
			<jsp:include page="/page/decorators/left.jsp"></jsp:include><sitemesh:write property='body'/>
			<div class="cl"></div>
		</div>
		<div id="footer"><jsp:include page="/page/decorators/footer.jsp"></jsp:include></div>
	</div>
	<div style="display:none;">
		<div id="jquery_jplayer_1" class="jp-jplayer"></div>
	
		<div id="jp_container_1" class="jp-audio">
			<div class="jp-type-single">
				<div class="jp-gui jp-interface">
					<ul class="jp-controls">
						<li><a href="javascript:;" class="jp-play" tabindex="1">play</a></li>
						<li><a href="javascript:;" class="jp-pause" tabindex="1">pause</a></li>
						<li><a href="javascript:;" class="jp-stop" tabindex="1">stop</a></li>
						<li><a href="javascript:;" class="jp-mute" tabindex="1" title="mute">mute</a></li>
						<li><a href="javascript:;" class="jp-unmute" tabindex="1" title="unmute">unmute</a></li>
						<li><a href="javascript:;" class="jp-volume-max" tabindex="1" title="max volume">max volume</a></li>
					</ul>
					<div class="jp-progress">
						<div class="jp-seek-bar">
							<div class="jp-play-bar"></div>
						</div>
					</div>
					<div class="jp-volume-bar">
						<div class="jp-volume-bar-value"></div>
					</div>
					<div class="jp-time-holder">
						<div class="jp-current-time"></div>
						<div class="jp-duration"></div>
	
						<ul class="jp-toggles">
							<li><a href="javascript:;" class="jp-repeat" tabindex="1" title="repeat">repeat</a></li>
							<li><a href="javascript:;" class="jp-repeat-off" tabindex="1" title="repeat off">repeat off</a></li>
						</ul>
					</div>
				</div>
				<div class="jp-title">
					<ul>
						<li>Cro Magnon Man</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		setInterval("checkOrder()",30000);
		$("#jquery_jplayer_1").jPlayer({
			ready: function () {
				$(this).jPlayer("setMedia", {
					mp3:"source/mp3/order.mp3"
				});
			},
			swfPath: "js",
			supplied: "mp3",
			wmode: "window",
			smoothPlayBar: true,
			keyEnabled: true
		});
	})
	function checkOrder(){
		$.ajax({
			url:"<%=basePath%>Order/checkOrder.do",
			type:"post",
			dataType:"json",
			success:function(data){
				if(data.code == 0){
					$(".jp-pause").click();
					$(".jp-play").click();
				}
			}
		});
	} 
</script>
</html>