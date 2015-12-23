<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ include file="/commons/common.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>角色管理</title>
	<script type="text/javascript">
		$(function(){
			$("img[id^='delete-']").click(function(){
				var name = $(this).parents("tr").find("td:eq(2)").html().trim();
				
				var con = confirm("确定删除"+name+"吗？");
				if(con){
					var url = "${ctp}/role/delete";
					var id = $(this).attr("id").split("-")[1];
					var args = {"id":id,"_method":"DELETE"};
					$.post(url,args,function(data){
						if(data == "1"){
							$("#deleteTr-"+id).empty();
							alert("删除成功");
						}else{
							alert("删除失败");
						}
					})
					return false;
				}
			}); 
			
		})
	</script>
</head>

<body class="main">

	<div class="page_title">
		角色管理
	</div>
	
	<div class="button_bar">
		<button class="common_button" onclick="window.location.href='${ctp}/role/create'">
			新建
		</button>
	</div>
	
	<form action="role-list">

		<!-- 列表数据 -->
		<br />
		<c:if test="${page != null && page.totalElements > 0 }">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th class="data_title" >
						编号
					</th>
					<th class="data_title" >
						角色名
					</th>
					<th class="data_title" >
						角色描述
					</th>
					<th class="data_title">
						状态
					</th>
					<th class="data_title">
						操作
					</th>
				</tr>
				
				<c:forEach var="role" items="${page.content }">
					<tr id="deleteTr-${role.id}">
						<td class="data_cell" style="text-align:right;padding:0 10px;">${role.id}</td>
						<td class="data_cell" style="text-align:center;">${role.name}</td>
						<td class="data_cell" style="text-align:left;">${role.description}</td>
						<td class="data_cell" style="text-align:center;">${role.enabled ? "有效":"无效"}</td>
						<td class="data_cell">
							<img onclick="window.location.href='${ctp }/role/assign?id=${role.id}'" title="分配权限" src="${ctp }/static/images/bt_linkman.gif" class="op_button" />
							<img id="delete-${role.id}" title="删除" src="${ctp }/static/images/bt_del.gif" class="op_button" />
						</td>
					</tr>
				</c:forEach>
				
			</table>
			<tags:pagination2 page="${page}" queryString="${queryString }"/>
		</c:if>
		
		<c:if test="${page == null || page.totalElements == 0 }">
			没有任何数据
		</c:if>
		
	</form>
</body>
</html>

