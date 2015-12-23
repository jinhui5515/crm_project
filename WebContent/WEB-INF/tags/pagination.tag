<%@tag pageEncoding="UTF-8"%>

<%@ attribute name="page" type="com.atguigu.crm.orm.Page" required="true"%>
<%@ attribute name="queryString" type="java.lang.String" required="false"%>
<%@ attribute name="id" type="java.lang.String" required="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set value="${pageContext.request.contextPath }" var="ctp"></c:set>
<div style="text-align:right; padding:6px 6px 0 0;">
				共 ${page.totalElements }条记录 
				&nbsp;&nbsp;
				
				当前第 ${page.pageNo } 页/共 ${page.totalPages } 页
				&nbsp;&nbsp;
				
				<c:if test="${page.hasPrev }">
				<a href='?pageNo=1&${queryString}&id=${id}'>首页</a>
				&nbsp;&nbsp;
				<a href='?pageNo=${page.pageNo - 1 }&${queryString}&id=${id}'>上一页</a>
				&nbsp;&nbsp;
				</c:if>
				<c:if test="${page.hasNext }">
				<a href='?pageNo=${page.pageNo + 1 }&${queryString}&id=${id}'>下一页</a>
				&nbsp;&nbsp;
				<a href='?pageNo=${page.totalPages }&${queryString}&id=${id}'>末页</a>
				&nbsp;&nbsp;
				</c:if>
				
				转到 <input id="pageNo" size='1'/> 页
				&nbsp;&nbsp;
			
			</div>
			
			<script type="text/javascript" src="${ctp}/static/jquery/jquery-1.9.1.min.js"></script>
			<script type="text/javascript">
			
				$(function(){
					
					$("#pageNo").change(function(){
						
						var pageNo = $(this).val();
						var reg = /^\d+$/;
						if(!reg.test(pageNo)){
							$(this).val("");
							alert("输入的页码不合法");
							return;
						}
						
						var pageNo2 = parseInt(pageNo);
						if(pageNo2 < 1 || pageNo2 > parseInt("${page.totalPages}")){
							$(this).val("");
							alert("输入的页码不合法");
							return;
						}
						
						//查询条件需要放入到 class='condition' 的隐藏域中. 
						window.location.href = "?pageNo=" + pageNo2 +"&${queryString}&id=${id}";
						
					});
				})
			</script>
</script>