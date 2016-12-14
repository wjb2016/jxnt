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
                        <a href="PerCentral/jxPointRule.do">积分规则</a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" onclick="gotoTop();return false;">返回顶部</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="jx-footer-icp">ICP备案证书号：蜀ICP备14016313号-1</div>
        <div style="text-align: center;font-size: 12px;line-height: 12px;color: #db891e;">
              <span style="font-size: 8px;">@优创引力团队提供技术支持</span>
        </div>
                      
 </footer>
<script>
</script>
