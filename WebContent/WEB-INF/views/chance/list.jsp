<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>销售机会管理</title>
	<script type="text/javascript">
		$(function(){
			$("#new").click(function(){
				window.location.href="${ctp}" + "/chance";
				return false;
			});
		});
		
		//删除
		$(function(){
			$("img[id^='delete-']").click(function(){
				var custName = $(this).next("input").attr("value");
				var flag = confirm("您确定要删除 "+custName+" 吗？");
				
				if(flag)
				{
				  	var url = "${ctp}/chance/"+$(this).prev("input").attr("value"); 
					$("#hiddenForm").attr("action",url).submit();
				}
				
			});
			
		});
		
		
	</script>
</head>

<body class="main">
		
	<form action="" method="POST" id="hiddenForm">
		<input  type="hidden" name="_method" value="DELETE" >
	</form>	


	<form id="command" action="${ctp}/chance/list" method="post">
		<div class="page_title">
			销售机会管理
		</div>
		<div class="button_bar">
			<button class="common_button" id="new">
				新建
			</button>
			<button class="common_button" onclick="document.forms[1].submit();">
				查询
			</button>
		</div>
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th class="input_title">
					客户名称
				</th>
				<td class="input_content">
					<input type="text" name="filter_LIKES_custName" />
				</td>
				<th class="input_title">
					概要
				</th>
				<td class="input_content">
					<input type="text" name="filter_LIKES_title" />
				</td>
				<th class="input_title">
					联系人
				</th>
				<td class="input_content">
					<input type="text" name="filter_LIKES_contact" />
				</td>
			</tr>
		</table>
		<!-- 列表数据 -->
		<br />
		<c:if test="${page.totalElements == 0 }">没有任何数据</c:if>
		<c:if test="${page.totalElements != 0 }">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th>
						编号
					</th>
					<th>
						客户名称
					</th>
					<th>
						概要
					</th>
					<th>
						联系人
					</th>
					<th>
						联系人电话
					</th>
					<th>
						创建时间
					</th>
					<th>
						操作
					</th>
				</tr>
				<c:forEach items="${page.content }" var="item">
					<tr>
						<td class="list_data_number">${item.id }</td>
						<td class="list_data_text">${item.custName }</td>
						<td class="list_data_text">${item.title }</td>
						<td class="list_data_text">${item.contact }</td>
						<td class="list_data_text">${item.contactTel }</td>
						<td class="list_data_text">
							<fmt:formatDate value="${item.createDate }" pattern="yyyy-MM-dd"/>
						</td>
						<td class="list_data_op">
							<img onclick="window.location.href='${ctp}/chance/dispatch?id=${item.id }'" 
								title="指派" src="${ctp}/static/images/bt_linkman.gif" class="op_button" />
							<img onclick="window.location.href='${ctp}/chance/${item.id }'" 
								title="编辑" src="${ctp}/static/images/bt_edit.gif"
								class="op_button" />
								
							<%-- <img onclick="window.location.href='${ctp}/chance/delete?id=7060'" 
								title="删除" src="${ctp}/static/images/bt_del.gif" class="op_button" /> --%>
								
								<span>
									<input type="hidden" value="${item.id }" >
									<img  id="delete-${item.id  }"
										title="删除" src="${ctp}/static/images/bt_del.gif" class="op_button" />
									<input type="hidden" value="${item.custName }" >
								</span>
								
						</td>
					</tr>
				</c:forEach>
			</table>

			<tags:pagination page="${page}" queryString="${queryString }"/>
		
	</c:if>
	</form>
	
</body>
</html>
