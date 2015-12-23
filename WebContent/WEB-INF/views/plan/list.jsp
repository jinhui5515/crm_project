<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>	
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>客户开发计划</title>
</head>
<!-- 绑定开发成功按键 单击函数 -->
<script type="text/javascript">
	$(function(){
		$("#success").click(function(){
			var id = $("#itemId").val();
			var action  = "${ctp}/chance/finish/"+id
			$("#successForm").attr("action",action).submit();
		})
	})
</script>
<body class="main">
	<!-- 开发成功的PUT提交 -->
	<form id="successForm" action="" method="post">
		<input type="hidden" name="_method" value="PUT" />
	</form>
	<form action="${ctp }/plan/chance/list" method="post">
		<div class="page_title">客户开发计划</div>
		<div class="button_bar">
			<button class="common_button" onclick="document.forms[0].submit();">
				查询</button>
		</div>
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th class="input_title">客户名称</th>
				<td class="input_content"><input type="text"
					name="filter_LIKES_custName" /></td>
				<th class="input_title">概要</th>
				<td class="input_content"><input type="text"
					name="filter_LIKES_title" /></td>
				<th class="input_title">联系人</th>
				<td class="input_content"><input type="text"
					name="filter_LIKES_contact" /></td>
			</tr>
		</table>
		<br />
		
		<c:if test="${page.totalElements == 0 }">没有任何数据</c:if>
		<c:if test="${page.totalElements != 0 }">
				<table class="data_list_table" border="0" cellPadding="3"
					cellSpacing="0">
					<tr>
						<th>编号</th>
						<th>客户名称</th>
						<th>概要</th>
						<th>联系人</th>
						<th>联系人电话</th>
						<th>创建时间</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${page.content }" var="item">
						<tr>
							<td class="list_data_number">${item.id }</td>
							<td class="list_data_text">${item.custName }</td>
							<td class="list_data_text">${item.title }</td>
							<td class="list_data_text">${item.contact }</td>
							<td class="list_data_text">${item.contactTel }</td>
							<td class="list_data_text">
								<fmt:formatDate value="${item.designeeDate }" pattern="yyyy-MM-dd"/>
							</td>
							<td class="list_data_text"><script type="text/javascript">
											switch('${item.status}')
											{
												case '0':
													document.write('status为0');
												break;
												case '2':
													document.write('开发中');
													break;
												case '3':
													document.write('开发成功');
													break;
												case '4':
												    document.write('开发失败');
												   	break;
											}
										</script></td>
							<td class="list_data_op">
							<c:if test="${item.status == 2  }">
							
								<img onclick="window.location.href='${ctp }/plan/chance/make?id=${item.id}'"
							 title="制定计划" src="${ctp }/static/images/bt_plan.gif" class="op_button" />
							 
								<img onclick="window.location.href='${ctp }/plan/chance/execution/${item.id }'"
								title="执行计划" src="${ctp }/static/images/bt_feedback.gif" class="op_button" />
								
								<img id="success"
								title="开发成功" src="${ctp }/static/images/bt_yes.gif" class="op_button" />
								<input type="hidden" id="itemId" value="${item.id}" />
							</c:if>
							<c:if test="${item.status != 2  }">
							
								<img onclick="window.location.href='${ctp }/plan/chance/detail?id=${item.id}'"
									title="查看" src="${ctp }/static/images/bt_detail.gif" class="op_button" />
									
							</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>

		<tags:pagination page="${page}" queryString="${queryString }"/>

	</c:if>


	</form>
</body>
</html>
