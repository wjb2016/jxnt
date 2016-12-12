<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>品牌管理列表</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1  ,maximum-scale=1, user-scalable=no" />
<script	src="${pageContext.request.contextPath}/source/js/pager/jquery.pager.js"></script>
<link href="${pageContext.request.contextPath}/source/js/pager/Pager.css" rel="stylesheet"/>
<script type="text/javascript">
$(document).ready(function(){
	$("#pager").pager({
	    pagenumber:'${Type.pageNo}',                         /* 表示初始页数 */
	    pagecount:'${Type.pageCount}',                      /* 表示总页数 */
	    totalCount:'${Type.totalCount}',
	    buttonClickCallback:PageClick                     /* 表示点击分页数按钮调用的方法 */                  
	});
	//初始化品牌节点
	$("#treeList").tree({
		 url: '<%=basePath %>Order/jsonLoadItemTreeList.do',   
		 onClick:function(node){
		 	getItemListByParentId(node.id);
		 },
		 onBeforeExpand:function(node){
		 	$('#treeList').tree('options').url = '<%=basePath %>Order/jsonLoadItemTreeList.do?pid='+ node.id;
		 },
		 onLoadSuccess:function(){
		    var root = $("#treeList").tree("getRoot");
		 	if(root != null){
		 		$("#treeList").tree("expand",root.target);
		 	}
		 }
	}); 
});

//根据父节点加载子节点列表
function getItemListByParentId(pid){
	  var prepid = $("#parentId").val();
	  var pageNumber;
	  if(pid == prepid){
		pageNumber = $("#pageNumber").val();
	  }else{
	  	pageNumber = 1;
	  	$("#parentId").val(pid);
	  }
	  $.ajax({
		url:"<%=basePath %>Order/jsonLoadItemListByParentId.do?pid="+pid+"&pageNumber="+pageNumber,
		type:"post",  
		dataType:"json",
		success:function(data) { 
  			if(data.code == 0){ 
  				 $("#pager").pager({
				    pagenumber:data.obj.pageNo,                         /* 表示初始页数 */
				    pagecount:data.obj.pageCount,                      /* 表示总页数 */
				    totalCount:data.obj.totalCount,
				    buttonClickCallback:PageClick2                     /* 表示点击分页数按钮调用的方法 */                  
				});
				$("#typeList").html("");
				//$("#pageNumber").val("");
				$("#pagecount").val(data.obj.totalCount);
				fillItemList(data.list);
  			}else{
				$.messager.alert('错误信息',data.message,'error');
  			} 
		},
		error:function(data){
			alert(data.code);
		}
	});
};

//拼接html，展示品牌节点列表
function fillItemList(lst){
	var html = "";
	for(var i = 0; i<lst.length;i++){
		html += "<tr>";
		<%-- html += "<td align='center'>" + "<img alt='品牌照片' src='<%=basePath%>"+ lst[i].img1Path + "'"+" style='width:40px;height:50px;vertical-align: middle;'></td>"; --%>
		html += "<td>"+(lst[i].name == null ? "":lst[i].name)+"</td>";		
		html += "<td>"+(lst[i].parentName == null ? "":lst[i].parentName)+"</td>";
		html += "<td style='text-align:left;'>"+(lst[i].description == null ? "":lst[i].description)+"</td>";
		html += "<td>"+"<a href='javascript:void(0);' style='color:blue;' onclick=window.location.href='<%=basePath %>Order/typeInfo.do?typeId="+lst[i].id+"'>详情</a>"+(lst[i].flag == 0 ?"<a style='margin-left:15px;color:red;' href='javascript:void(0);' onclick='deleteItem("+lst[i].id+","+lst[i].childrenCount+","+lst[i].flag+");'>启用</a>":"<a style='margin-left:15px;color:red;' href='javascript:void(0);' onclick='deleteItem("+lst[i].id+","+lst[i].childrenCount+","+lst[i].flag+");'>停用</a>")+"</td>";
		html += "</tr>";
	}
	$("#typeList").html(html);
}


PageClick = function(pageclickednumber) {
	$("#pager").pager({
	    pagenumber:pageclickednumber,                 /* 表示启示页 */
	    pagecount:'${Type.pageCount}',                  /* 表示最大页数pagecount */
	    buttonClickCallback:PageClick                 /* 表示点击页数时的调用的方法就可实现javascript分页功能 */            
	});
	
	$("#pageNumber").val(pageclickednumber);          /* 给pageNumber从新赋值 */
	/* 执行Action */
	pagesearch();
}

PageClick2 = function(pageclickednumber) {
	var pagecount = $("#pagecount").val();
	$("#pager").pager({
	    pagenumber:pageclickednumber,                 /* 表示启示页 */
	    pagecount:pagecount,                  /* 表示最大页数pagecount */
	    buttonClickCallback:PageClick2                 /* 表示点击页数时的调用的方法就可实现javascript分页功能 */            
	});
	
	$("#pageNumber").val(pageclickednumber);          /* 给pageNumber从新赋值 */
	/* 执行Action */
	pagesearch2();
}

function search(){
	$("#pageNumber").val("1");
	pagesearch();
}

function pagesearch(){
	itemForm.submit();
}
function pagesearch2(){
	var pid = $("#parentId").val();
	getItemListByParentId(pid);
	//$("#parentId").val("");
}

//删除品牌
function deleteItem(id,count,flag){	
	if(count > 0){
		$.messager.alert("错误提示","品牌类型不能停用！","info",function(){
			return;
		});
	}else{
		$.ajax({
			url:"<%=basePath %>Order/jsonDeleteType.do?typeId="+id+"&flag="+flag,
			type:"post",
			dataType:"json",
			success:function(data){
				if(data.code == 0){
					$.messager.alert("提示信息",data.message,"info",function(){
						window.location.reload(true);
					})
				}else{
					$.messager.alert("错误信息",data.message,"error");
				}
			}
		});
	}
	
}
</script>
</head>
  
  <body>
    <div class="con-right" id="conRight">
		<div class="fl yw-lump">
			<div class="yw-lump-title">
				<i class="yw-icon icon-partner"></i><span>品牌管理列表</span>
				<div class="fr"> 
					<span class="yw-btn bg-green cur" onclick="window.location.href='<%=basePath %>Order/typeInfo.do?typeId=0'" style="margin-right: 10px;">新建品牌</span>
				</div>
			</div>
		</div>
		<div class="fl yw-lump mt10">
			<form id="itemForm" name="itemForm"	action="<%=basePath %>Order/orderTypeList.do" method="post">
				<div class="pd10">
					<div class="fr"> 
						<span class="ml26">品牌查询：</span>
						<input type="text" name="searchName" class="easyui-validatebox"	placeholder="品牌名搜索" value="${Type.searchName}" /> 
						<span class="yw-btn bg-blue ml30 cur" onclick="search();" style="display:inline-block;width: 65px;text-align: center;">搜索</span>
					</div>
					<div class="cl"></div>
				</div>
				<input type="hidden" id="pagecount"/>
				<input type="hidden" id="parentId"/>
				<input type="hidden" id="pageNumber" name="pageNo" value="${Type.pageNo}"/>
			</form>
		</div>
		<div class="fl">
			<div class="fl yw-lump wid250 mt10">
				<div class="yw-cm-title">
					<span class="ml26">品牌列表</span>
				</div>
				<div class="yw-tree-list" style="height:639px;">
					<ul id="treeList"></ul>
				</div>
			</div>
			<div class="yw-lump wid-atuo ml260 mt10">
				<div class="yw-cm-title">
					<span class="ml26">全部品牌</span>
				</div>
				<table class="yw-cm-table yw-center yw-bg-hover" id="typeInfoList">
					<thead>
					<tr style="background-color:#D6D3D3;font-weight: bold;">
						<!-- <th>照片</th>	 -->					
						<th>品牌名称</th>						
						<th>品牌类型</th> 
						<th  style="text-align: left!important;">品牌描述</th>
						<th>操作</th>
					</tr>
					</thead>
					<tbody id="typeList">
					<c:forEach var="item" items="${ItemList}">
						<tr>
							<%-- <td	align="left">
								<img alt="品牌照片" src="<%=basePath%>${item.imagePath}" style="width:40px;height:50px;vertical-align: middle;">
							</td> --%>							
							<td>${item.name}</td>							
							<td>${item.parentName}</td>
							<td  style="text-align: left!important;">${item.description}</td>
							<td>
								<a href="javascript:void(0);" style="color:blue;" onclick="window.location.href='<%=basePath %>Order/typeInfo.do?typeId=${item.id}'">详情</a>
								<c:if test="${item.flag == 0 }">
									<a style="margin-left:15px;color:red;" href="javascript:void(0);"  onclick="deleteItem(${item.id},${item.childrenCount},${item.flag })">启用</a> 
								</c:if>
								<c:if test="${item.flag == 1 }">
									<a style="margin-left:15px;color:red;" href="javascript:void(0);"  onclick="deleteItem(${item.id},${item.childrenCount},${item.flag })">停用</a> 
								</c:if>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<div class="page" id="pager"></div>
			</div>
		</div> 
  </body>
</html>
