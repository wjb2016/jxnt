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
    <title>我爱我家-客户</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <script src="${pageContext.request.contextPath}/source/bootstrap/js/jquery-1.9.1.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap-tab.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap-select.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap-select.min.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/common.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/common.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap-select.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap-select.css" type="text/css">
</head>
<style type="text/css">
    .jx_sure{
        margin-left: auto;
        margin-right: auto;
        text-align: center;
    }
   
    #star {
        position: relative;
        width: 600px;
        margin: 20px auto;
        height: 24px;
    }

    #star ul,#star span {
        float: left;
        display: inline;
        height: 19px;
        line-height: 19px;
    }

    #star ul {
        margin: 0 10px;
    }

    #star li {
        float: left;
        width: 24px;
        cursor: pointer;
        text-indent: -9999px;
        background: url(source/images/star.png) no-repeat;
    }

    #star strong {
        color: #f60;
        padding-left: 10px;
    }

    #star li.on {
        background-position: 0 -28px;
    }

    #star p {
        position: absolute;
        top: 20px;
        width: 159px;
        height: 60px;
        display: none;
        background: url(source/images/icon.gif) no-repeat;
        padding: 7px 10px 0;
    }

    #star p em {
        color: #f60;
        display: block;
        font-style: normal;
    }
    
    .jx_ntLove_Fount {
		color: #db891e;
		font-family: "Open Sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
		font-size: 14px;
		font-weight: bold;
	}
	.jx_margin table tr td {
		text-align: center;
    }
    
    .div_overlay{
            display: none;
            position: fixed;
            top: 0%;
            left: 0%;
            width: 100%;
            height: 100%;
            background-color: black;
            z-index:1010;
            -moz-opacity: 0.8;
            opacity:.80;
            filter: alpha(opacity=80);
	}
	.div_content {
           display: none;
           position: fixed;
           top: 50%;
           left: 50%;
           width: 280px;
		   margin-left:-140px;
           height: 150px;
		   margin-top:-80px;
           padding: 0;
           border: 5px solid 5c2905;
           background-color: #F0F5F8;
           _position:absolute;
           z-index:1011;
           overflow: hidden;
	}
	
	.div_messageshow
	{
		height:80px;
		width:280px;
		line-height:30px;
		text-align:center;
		margin:0 auto;
		color:#000;
		font-size:13px;
		font-weight:bold;
		overflow:hidden;
		margin-top:10px;
	}
	
	.div_main
	{
		width:60px;
		margin:0 auto;
		text-align:center;
		line-height:30px;
		height:30px;
		margin-top:30px;
		padding:0;
	}
	.div_li
	{
		background:#c87509;
		margin:0 auto;
		height:20px;
		width:60px;
		border:#c87509 1px solid;
		color:#FFF;
		font-size:12px;
		list-style-type:none;
		line-height:20px;
		text-align:center;
		cursor:pointer;
		float:left;
	}
</style>
<script type="text/javascript">

    $(function(){
    	isLogin();
        var oStar = document.getElementById("star");
        var aLi = oStar.getElementsByTagName("li");
        var oUl = oStar.getElementsByTagName("ul")[0];
        var oSpan = oStar.getElementsByTagName("span")[1];
        var oP = oStar.getElementsByTagName("p")[0];
        var i = iScore = iStar = 0;
        var aMsg = [ "工程施工过程很不满意", "工程施工过程合格","工程施工过程大体满意", "工程施工过程满意","工程施工过程非常满意" ]

        for (i = 1; i <= aLi.length; i++) {
            aLi[i - 1].index = i;

            //鼠标移过显示分数
            aLi[i - 1].onmouseover = function() {
                fnPoint(this.index);
                //浮动层显示
                oP.style.display = "block";
                //计算浮动层位置
                oP.style.left = oUl.offsetLeft + this.index * this.offsetWidth
                        - 104 + "px";
                //匹配浮动层文字内容
                oP.innerHTML = "<em><b>" + this.index + "</b> 分 "
                        + aMsg[this.index - 1].match(/(.+)\|/)[1] + "</em>"
                        + aMsg[this.index - 1].match(/\|(.+)/)[1]
            };

            //鼠标离开后恢复上次评分
            aLi[i - 1].onmouseout = function() {
                fnPoint();
                //关闭浮动层
                oP.style.display = "none"
            };

            //点击后进行评分处理
            aLi[i - 1].onclick = function() {
                iStar = this.index;
                $("#grade").val(this.index);
                oP.style.display = "none";
                oSpan.innerHTML = "<strong>" + (this.index) + " 分</strong> ("
                        + aMsg[this.index - 1]/* .match(/\|(.+)/)[1] */ + ")"
            }
        }

    //评分处理
    function fnPoint(iArg) {
         //分数赋值
         iScore = iArg || iStar;
         for (i = 0; i < aLi.length; i++)
             aLi[i].className = i < iScore ? "on" : "";
     }
    });
	        
	//选择合同号
	function selectContract(){
	   $("#erropMsg").text("");
	   $("#proList").html("<option>请先选择合同编号</option>");
	   $("#proList").selectpicker('refresh');
	   $("#proInfo").hide();
	   $("#orderInfo").hide();
	   // 选中的合同编号
	   var contractNum = $('#contract').val();
	   $.ajax({
	        url:"<%=basePath%>Project/jsonLoadProList.do",
	        type:"POST",
	        dataType:"JSON",
	        data:{contractNum:contractNum},
	        success: function (data){
	        	$("#proList").find("option").remove();
	        	var html = "";
	        	if(data.code == 0){
	        		html += "<option value='0'>请选择工程</option>"
	        		for(var i=0;i<data.list.length;i++){
	        			html += "<option value='"+data.list[i].id+"'>"+data.list[i].name+"</option>" 
	        		}
	        	}else{
	        		html += "<option>无工程列表</option>";
	        	}
	        	if(data.obj){
		        	//项目类型
		        	if(data.obj.orderTypeId == 1){
		        		$("#orderType").text("地暖")
		        	}else if(data.obj.orderTypeId == 2){
		        		$("#orderType").text("中央空调")
		        	}else if(data.obj.orderTypeId == 3){
		        		$("#orderType").text("净水系统")
		        	}else{
		        	}
		        	
		        	//项目状态
		        	if(data.obj.status == 0 || data.obj.status == 1){
		        		$("#orderStatus").text("待确认");
		        	}else if(data.obj.status == 2){
		        		$("#orderStatus").text("已确认");
		        	}else if(data.obj.status == 4){
		        		$("#orderStatus").text("施工中");
		        	}else if(data.obj.status == 6){
		        		$("#orderStatus").text("已完成");
		        	}else if(data.obj.status == 8){
		        		$("#orderStatus").text("已中断");
		        	}else if(data.obj.status == 10){
		        		$("#orderStatus").text("已作废");
		        	}else{}
		        	
		        	$("#serviceStarts").text(data.obj.serviceStarts);
		        	$("#serviceEnds").text(data.obj.serviceEnds);
		        	
		        	$("#orderInfo").show();
	        	}
	        	
        		$("#proList").html(html);
        		$("#proList").selectpicker('refresh');
	        }
	   });
	}
	
	//选择工程
	function selectPro(){
	   $("#erropMsg").text("");
	   $("#showStar").hide();
	   $("#tr_assessValue").hide();
	   $("#tr_assessMessage").hide();
	   // 选中的工程id
	   var proId = $("#proList").val();
	   $.ajax({
	        url:"<%=basePath%>Project/jsonLoadPro.do",
	        type:"POST",
	        dataType:"JSON",
	        data:{proId:proId},
	        success: function (data){
	        	if(data.code == 0){
	        		$("#typeName").text(data.obj.typeName);
	        		$("#startTimes").text(data.obj.startTimes);
	        		$("#endTimes").text(data.obj.endTimes);
	        		$("#tr_leader").text(data.obj.leaderPhone);
	        		$("#id").val(data.obj.id);
	        		if(data.obj.beganTimes){
		        		$("#beganTimes").text(data.obj.beganTimes);
	        		}else{
		        		$("#beganTimes").text("尚未开工");
	        		}
	        		if(data.obj.finishTimes){
		        		$("#finishTimes").text(data.obj.finishTimes);
	        		}else{
		        		$("#finishTimes").text("尚未完工");
	        		}
	        		
	        		if(data.obj.description){
		        		$("#description").text(data.obj.description);
	        		}else{
		        		$("#description").text("暂无描述");
	        		}
	        		
	        		if(data.obj.status == 0){
	        			$("#status").text("未执行");
	        		}else if(data.obj.status == 1){
	        			$("#status").text("施工中");
	        		}else if(data.obj.status == 2){
	        			if(data.obj.assessMessage){
		        			$("#assessValue").text(data.obj.assessValue);
		        			$("#assessMessage").text(data.obj.assessMessage);
		        			$("#tr_assessValue").show();
		   					$("#tr_assessMessage").show();
		        		}else{
		        			$("#btn_saveMessage").attr("onclick","saveProMessage("+data.obj.id+")");
		        			$("#showStar").show();
		        		}
	        			$("#status").text("已完成");
	        		}else{
	        			$("#status").text("已中断");
	        		}
	        		var imgList = "";
	        		for(var i=0;i<data.list.length;i++){
	        			imgList += "<div style='position: relative;display: block;margin:2px auto;width:300px;height:300px;' onclick='openWindow(this,"+data.list[i].id+");'><image style='display:block;width:300px;height:300px;' src='<%=basePath %>"+ data.list[i].imagePath +"'></image></div>";
	        		}
	        		$("#imgList").html(imgList);
	        		$("#proInfo").show();
	        	}else{
	        		$("#proInfo").hide();
					$("#erropMsg").text(data.message);
	        	}
	        }
	   });
	}
	
	function openWindow(obj,id){
		$.ajax({
			url:"<%=basePath%>Project/jsonLoadProImg.do?id="+id,
			type:"post",
			dataType:"json",
			success:function(data){
				//如留言过，则只能查看之前留言信息，反之可以提交评价
				if(data.obj.message){
					$("#messagebox").val(data.obj.message).attr("readonly","readonly");
					if(data.obj.permission == 1 || data.obj.permission == 2){
						$("#imgInput").text("已公开").show();
					}else{
						$("#imgInput").text("未公开").show();
					}
					$(".div_li").text("关闭").attr("onclick","closeWindow();");
					$("#lightshow").show();
					$("#fadeshow").show();
				}else{
					$(".div_li").text("提交评价").attr("onclick","saveMessage("+id+");");
					$($("[name=openimg]:radio").get(0)).prop("checked",true); 
					$("#imgRadio").show();
					$("#lightshow").show();
					$("#fadeshow").show();
				}
			}
		})
	}
	
	function closeWindow(){
		$("#messagebox").val("").attr("readonly",false);
		$("#imgRadio").hide();
		$("#imgInput").text("").hide();
		$("#lightshow").hide();
		$("#fadeshow").hide();
	}
	
	//保存工程图片评价
	function saveMessage(id){
		var message = $("#messagebox").val();
		if(!message){
			alert("留言评价不能为空！");
			return;
		}
		var open = $("input:radio[name='openimg']:checked").val();
		$.ajax({
			url:"<%=basePath%>Project/jsonSaveProImg.do?id="+id+"&message="+message+"&open="+open,
			type:"post",
			dataType:"json",
			success:function(data){
				closeWindow();
				alert(data.message);
			}
		})
	}

	//保存工程评价
	function saveProMessage(id){
		var grade = $("#grade").val();
		if(!grade){
			alert("请评价分值！");
			return;
		}
		var promessage = $("#promessage").val();
		if(!promessage){
			alert("请输入评价内容！")
			return;
		}
		var permission = $("input:radio[name='permission']:checked").val();
		$.ajax({
			url:"<%=basePath%>Project/jsonSaveProMsg.do?id="+id+"&grade="+grade+"&message="+promessage+"&permission="+permission,
			type:"post",
			dataType:"json",
			success:function(data){
				alert(data.message);
				$("#showStar").hide();
				$("#assessValue").text(data.obj.assessValue);
       			$("#assessMessage").text(data.obj.assessMessage);
       			$("#tr_assessValue").show();
  				$("#tr_assessMessage").show();
			}
		})
	}
</script>

<body class="">

<div class="container bg1">
    <header id="JS_mll_header" class="mll-header borderOnePx">
        <div class="layout-middle">
            <div id="JS_header_page_title" class="text">我爱我家</div>
        </div>
    </header>
    <div class="banner" style="text-align:center;">
        <img src="source/images/logo1.png" alt="我要预约" class="img-rounded">
    </div>
    <div class="img">
    <form class="form-inline">
       <div class="form-group jx_margin">
            <label class="jx_ntLove_Fount">合同编号</label>
            <select class="selectpicker" data-live-search="true" id="contract" onchange="selectContract()">
              <option value="0">请选择合同编号</option>
              <c:forEach var="project" items="${projects}">
              	<option value="${project}">${project}</option>
              </c:forEach>
            </select> 
        </div> 
        <div class="form-group jx_margin">
            <label class="jx_ntLove_Fount">工程列表</label>
            <select class="selectpicker" data-live-search="true" id="proList" onchange="selectPro()">
            	<option>请先选择合同编号</option>
            </select>                      
        </div>
        <div id="orderInfo" style="display: none;">
	        <div class="jx_margin">
		        <table class="table table-striped jx_ntFount" style="border-bottom:1px solid #db891e;">
		            <tr>
		            	<td colspan="2">项目详情</td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">项目类型</td>
		                <td id="orderType" style="color: #1e1107;"></td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">项目状态</td>
		                <td id="orderStatus" style="color: #1e1107;"></td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">维修起始时间</td>
		                <td id="serviceStarts" style="color: #1e1107;"></td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">维修截止时间</td>
		                <td id="serviceEnds" style="color: #1e1107;"></td>
		            </tr>
		        </table>
	        </div>
        </div>
        <div id="proInfo" style="display: none;">
	        <div class="jx_margin">
		        <table class="table table-striped jx_ntFount" style="border-bottom:1px solid #db891e;">
		            <tr>
		            	<td colspan="2">工程详情</td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">品牌名称</td>
		                <td id="typeName" style="color: #1e1107;"></td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">工程状态</td>
		                <td id="status" style="color: #1e1107;"></td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">工程描述</td>
		                <td id="description" style="color: #1e1107;">
		                </td>
		            </tr>
		            <tr id="tr_assessValue" style="display: none;">
		                <td style="text-align: right;width:100px;">评价分值</td>
		                <td id="assessValue" style="color: #1e1107;">
		                </td>
		            </tr>
		            <tr id="tr_assessMessage" style="display: none;">
		                <td style="text-align: right;width:100px;">评价内容</td>
		                <td id="assessMessage" style="color: #1e1107;">
		                </td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">负责人电话</td>
		                <td id="tr_leader" style="color: #1e1107;">
		                </td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">规划起始时间</td>
		                <td id="startTimes" style="color: #1e1107;"></td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">规划结束时间</td>
		                <td id="endTimes" style="color: #1e1107;"></td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">施工开始时间</td>
		                <td id="beganTimes" style="color: #1e1107;"></td>
		            </tr>
		            <tr>
		                <td style="text-align: right;width:100px;">施工完工时间</td>
		                <td id="finishTimes" style="color: #1e1107;"></td>
		            </tr>
		        </table>
	        </div>
	        <div class="form-group" id="imgList">
	        </div>
	        <div style="display: none;" id="showStar">
		        <div id="star" style="text-align: center;width:360px;margin:10px auto;">
		            <span style="color: #db891e;font-family: Open Sans, Helvetica Neue, Helvetica, Arial, sans-serif;font-weight: bold;font-size: 14px;">评分：</span>
		            <ul>
		                <li>
		                    <a href="javascript:;">1</a>
		                </li>
		                <li>
		                    <a href="javascript:;">2</a>
		                </li>
		                <li>
		                    <a href="javascript:;">3</a>
		                </li>
		                <li>
		                    <a href="javascript:;">4</a>
		                </li>
		                <li>
		                    <a href="javascript:;">5</a>
		                </li>
		            </ul>
		            <div><br><span></span></div>
		            <p></p>
		        </div>
	        	<input type="hidden" id="grade"/>
		        <div class="form-group">
		       		<textarea class="form-control" style="width: 340px;" rows="3" id="promessage" placeholder="请输入工程评价"></textarea>
		        </div>
		        <div style="text-align: center;width:360px;margin:10px auto;"> 
					<input type="radio" name="permission" value="0" style="vertical-align:text-bottom; margin-bottom:2px;" checked="checked"/> 不公开
					<input type="radio" name="permission" value="1" style="vertical-align:text-bottom; margin-bottom:2px;"/> 公开
				</div>
		        <div style="margin-top: 10px;text-align: center;">
		             <input class="btn btn-success fileinput-button jx_userInfo_button" readonly="readonly" id="btn_saveMessage" value="保存评价"/>
		        </div>
	        </div>
        </div>
    </form>
    <div>
    	<span id="erropMsg" style="color: red;"></span>
    </div>
    </div>
   <div style="height: 80px;"></div>
    <div id="lightshow" class="div_content">
		<div class="div_messageshow">
			<textarea style="width: 250px;" rows="3" id="messagebox" placeholder="请输入图片评价"></textarea>
		</div>
		<div>
			<div style="margin-right: 10px;float:right;display: none" id="imgRadio"> 
				<input type="radio" name="openimg" value="0" style="vertical-align:text-bottom; margin-bottom:2px;" checked="checked"/> 不公开
				<input type="radio" name="openimg" value="1" style="vertical-align:text-bottom; margin-bottom:2px;"/> 公开
			</div>
			<div style="margin-right: 10px;float:right;display: none;color:#8d8585" id="imgInput"> 
			</div>
		</div>
		<div class="div_main">
			<a id="goback"><li class="div_li"></li></a>
		</div>
	</div>
	<div id="fadeshow" class="div_overlay" onclick="closeWindow();"></div>
  <jsp:include page="/page/wechat/footer.jsp"></jsp:include>
</div>
</body>
</html>
