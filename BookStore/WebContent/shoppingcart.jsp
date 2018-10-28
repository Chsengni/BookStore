<%@page import="com.ch.CartInfo_DAO"%>
<%@page import="com.ch.CartInfo_BookInfo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>购物车--老徐网上书店</title>
</head>
<body>
<%
	String userId=(String)session.getAttribute("memberID");
	if(userId==null){
		response.sendRedirect("index.jsp");	
	}else{
	List<CartInfo_BookInfo> li =CartInfo_DAO.find(userId);
	
%>
<p align="center"><strong><font size="5">购物车 -- 老徐网上书店</font></strong></p>
<form name="form1" method="post" action="">
	<table width="600" border="1" align="center" cellpadding="3" cellspacing="0">
		<tr>
			<th align="center" scope="col">ISBN</th>
			<th align="center" scope="col">书名</th>
			<th align="center" scope="col">单价</th>
			<th align="center" scope="col">数量</th>
			<th align="center" scope="col">&nbsp;</th>
		</tr>
		<%
		for(CartInfo_BookInfo cb:li){
		%>
		<tr>
			<td align="center"><%=cb.getBi().getBookISBN()%></td>
			<td align="center"><%=cb.getBi().getBookName() %></td>
			<td align="center"><%=cb.getBi().getPrice() %></td>
			<td align="center">
			<input name="num" type="text" id="num" size="6" readonly="true" value="<%=cb.getCi().getBookCount()%>"> 
			</td>
			<td align="center"><a href="delbook.jsp?Id=<%=cb.getBi().getId()%>">删除</a></td>
		</tr>
		<%} }%>
	</table>
	<p align="center"><strong><a href="booklist.jsp">返回</a>&nbsp;&nbsp;&nbsp;
	<a href="emptycart.jsp">清空购物车</a>&nbsp;&nbsp;&nbsp;
	<a href="changecart.jsp">修改数量</a>&nbsp;&nbsp;
	<a href="order.jsp">提交订单</a>
	</strong></p>
	<p>&nbsp;</p>
</form>
</body>
</html>