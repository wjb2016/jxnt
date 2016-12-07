<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <footer class="jx-footer">
        <div class="jx-footer-link">
            <div>
                <ul style="margin:0 0 0 10px;">
                    <li>
                        <a href="PerCentral/jxLogin.do" id="username">${sessionScope.userName==''?'未登录':sessionScope.userName}</a>
                    </li>
                    <li id="exit" class="exit">
                        <a href="javascript:void(0);" onclick="exitLogin()">退出登录</a>
                    </li>
                    <li id="fankui">
                        <a href="PerCentral/jxCustomService.do" onclick="">联系我们</a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" id="point">积分规则</a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" onclick="gotoTop();return false;">返回顶部</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="jx-footer-icp">ICP备案证书号：蜀ICP备xxxx号</div>
 </footer>
<script>
/* 	function MyPoint(userId){
	     var userName = $('#username').text();
	     if(userName !="未登录"){
	         window.location.href = "PerCentral/jxUserPoints.do?userId="+userId;
	     }else{
	     	 $('#point').attr("href","PerCentral/jxLogin.do");
	     }
	} */
</script>
