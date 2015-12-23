<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<table class="query_form_table" border="0" cellPadding="3" cellSpacing="0">
			<tr>
				<th>
					服务类型
				</th>
				<td>
					<input type="text" name="filter_LIKES_serviceType" />
				</td>
				<th>
					概要
				</th>
				<td>
					<input type="text" name="filter_LIKES_serviceTitle" />
				</td>
			</tr>
			<tr>
				<th>
					客户
				</th>
				<td>
					<input type="text" name="filter_EQS_customerName" />
				</td>
				<th>
					创建时间
				</th>
				<td>
					<input type="text" name="filter_GTED_createDateFrom" size="10" />
					-
					<input type="text" name="filter_LTED_createDateEnd" size="10" />
				</td>
			</tr>
		</table>
</body>
</html>