<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
function gotoChildMenu(url,selectedChildMenu) {
	$.ajax({  
        type : "post",  
        url : "${pageContext.request.contextPath}/jsonLoadSession.do?selectedChildMenu="+selectedChildMenu,  
        async : false
        });  
	 var urlx = url;
	//alert(urlx.subString(urlx.length-3,3))
	if(url.lastIndexOf(".do")==url.length-3){
		urlx = urlx+"?s="+Math.random();
	}else{
		urlx = urlx+"&s="+Math.random();
		//setCount();
	} 
	
	window.location.href = urlx;
	
}
</script>
<div class="con-left" id="conLeft">
            	<div class="yw-user">
                	<div class="yw-userhead">
                    	<img src="${pageContext.request.contextPath}/source/images/userhaed.png"/>
                   	</div>
                    <p class="yw-usertxt">${sessionScope.userInfo.name}</p>
                    <%-- <p class="yw-usertxt font-size14">${sessionScope.userInfo.company.name}</p> --%>
                </div>
                <div class="yw-left-menu">
                	<ul>
                	<c:forEach var="mainItem" items="${sessionScope.userFunctions}">
                	    <c:if test="${mainItem.id == sessionScope.userInfo.selectedMainMemu}">
	                	    <c:forEach var="item" items="${mainItem.childFunctionlist}">
		                		<li <c:if test="${item.id == sessionScope.userInfo.selectedChildMenu}">class="yw-left-menu-now"</c:if>>
		                        	<em></em><span onclick="gotoChildMenu('${pageContext.request.contextPath}${item.url}','${item.id}')">
		                        	<i class="<c:if test="${item.cssCode != null}">${item.cssCode}</c:if><c:if test="${item.cssCode == null}">fl yw-icon icon-todayjob</c:if>"></i>${item.name}</span>
		                        </li>
	                        </c:forEach>
                        </c:if>
                	</c:forEach>
                    	<!-- <li class="yw-left-menu-now">
                        	<em></em><span><i class="fl yw-icon icon-todayjob"></i>合作伙伴</span>
                        </li> -->
                    </ul>
                </div>
            </div>