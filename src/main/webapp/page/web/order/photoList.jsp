<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<title>照片管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1  ,maximum-scale=1, user-scalable=no" />
<script	src="${pageContext.request.contextPath}/source/js/pager/jquery.pager.js"></script>
<link href="${pageContext.request.contextPath}/source/js/pager/Pager.css" rel="stylesheet" />
<script type="text/javascript">
$(document).ready(function() {
	$("#pager").pager({
		pagenumber : '${ProjectImage.pageNo}', /* 表示初始页数 */
		pagecount : '${ProjectImage.pageCount}', /* 表示总页数 */
		totalCount : '${ProjectImage.totalCount}',
		buttonClickCallback : PageClick
	/* 表示点击分页数按钮调用的方法 */
	});
});

PageClick = function(pageclickednumber) {
	$("#pager").pager({
		pagenumber : pageclickednumber, /* 表示启示页 */
		pagecount : '${ProjectImage.pageCount}', /* 表示最大页数pagecount */
		buttonClickCallback : PageClick
	/* 表示点击页数时的调用的方法就可实现javascript分页功能 */
	});

	$("#pageNumber").val(pageclickednumber); /* 给pageNumber从新赋值 */
	/* 执行Action */
	pagesearch();
}
function search() {
	$("#pageNumber").val("1");
	pagesearch();
}
function pagesearch() {
	PhotoForm.submit();
}

//取消公开
function canOpen(id){
	$.ajax({
		url:"<%=basePath %>Order/jsonUpdateOpenStatus.do?status=0&imgId="+id,
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.code == 0){
				$.messager.alert("操作提示","图片取消公开成功！","info",function(){
					window.location.href = "<%=basePath %>Order/photoList.do";
				});
			}else{
				$.messager.alert("错误提示","图片取消公开失败！","error");
			}
		}
	})
}

//公开
function openimg(id){
	$.ajax({
		url:"<%=basePath %>Order/jsonUpdateOpenStatus.do?status=1&imgId="+id,
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.code == 0){
				$.messager.alert("操作提示","图片公开成功！","info",function(){
					window.location.href = "<%=basePath %>Order/photoList.do";
				});
			}else{
				$.messager.alert("错误提示","图片公开失败！","error");
			}
		}
	})
}
</script>
</head>
<body>
	<div class="con-right" id="conRight">
		<div class="fl yw-lump">
			<div class="yw-lump-title">
				<i class="yw-icon icon-partner"></i>
				<span>
					照片管理
				</span>
			</div>
		</div>
		<form id="PhotoForm" name="PhotoForm" action="<%=basePath%>Order/photoList.do" method="post">
			<div class="pd10">
				<input type="hidden" id="pageNumber" name="pageNo" value="${ProjectImage.pageNo}" />
			</div>
		</form>
		<div class="fl yw-lump" style="margin-top: 8px;">
			<span style="display: block;width: 100%;height: 28px;margin: 10px 0px;">已公开照片</span>
			<c:forEach var="item" items="${openList }">
				<div style="margin-right:10px;width: 200px;display: inline-block;margin-bottom: 3px;">
					<img alt="公开照片" src="<%=basePath%>${item.imagePath }" style="width: 200px;height: 200px;border-radius:5px;display: block;">
					<c:if test="${item.message == null || item.message == '' }">
						<textarea readonly="readonly" rows="5" style="width: 195px;">无评价</textarea>
					</c:if>
					<c:if test="${item.message != null && item.message != '' }">
						<textarea readonly="readonly" rows="5" style="width: 195px;">${item.message }</textarea>
					</c:if>
					<input type="button" value="取消公开" onclick="canOpen(${item.id})" style="float:right;background-color: #D9534F;color: white;border:none;height: 28px;cursor: pointer;"/>
				</div>
			</c:forEach>
		</div>
		<div class="fl yw-lump" style="margin-top: 8px;">
			<span style="display: block;width: 100%;height: 28px;margin: 10px 0px;">未公开照片</span>
			<c:forEach var="image" items="${unOpenList }">
				<div style="margin-right:10px;width: 200px;display: inline-block;margin-bottom: 3px;">
					<img alt="公开照片" src="<%=basePath%>${image.imagePath }" style="width: 200px;height: 200px;border-radius:5px;display: block;">
					<c:if test="${image.message == null || image.message == '' }">
						<textarea readonly="readonly" rows="5" style="width:195px;">无评价</textarea>
					</c:if>
					<c:if test="${image.message != null && image.message != '' }">
						<textarea readonly="readonly" rows="5" style="width:195px;">${image.message }</textarea>
					</c:if>
					<input type="button" value="公开" onclick="openimg(${image.id})" style="float:right;background-color: #75B74B;color: white;border:none;height: 28px;cursor: pointer;"/>
				</div>
			</c:forEach>
			<div class="page" id="pager"></div>
		</div>
	</div>
</body>
</html>
