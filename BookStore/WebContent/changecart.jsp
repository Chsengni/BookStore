<%@page import="com.ch.CartInfo_DAO"%>
<%@page import="com.ch.CartInfo_BookInfo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改购物车数量————老徐网上书店</title>
</head>
<body>
<%
	String userId=(String)session.getAttribute("memberID");
	if(userId==null){
		response.sendRedirect("index.jsp");
	}
	List<CartInfo_BookInfo> li =CartInfo_DAO.find(userId);
	request.setAttribute("li", li);
%>

<p align="center"><strong><font size="5">购物车——老徐网上书店</font></strong></p>
<form name="form1" method="post" action="change.jsp">
	<table width="600" border="1" align="center" cellpadding="3" cellspacing="0">
		<tr>
			<th align="center" scope="col">ISBN</th>
			<th align="center" scope="col">书名</th>
			<th align="center" scope="col">单价</th>
			<th align="center" scope="col">数量</th>
		</tr>
		<%for(CartInfo_BookInfo cb:li){	
			%>
			<tr>
				<td align="center"><%=cb.getBi().getBookISBN()%></td>
				<td align="center"><%=cb.getBi().getBookName() %></td>
				<td align="center"><%=cb.getBi().getPrice() %></td>
				<td align="center">
				<input name="<%=cb.getCi().getBookId()%>" type="text" id="num" size="6" value="<%=cb.getCi().getBookCount()%>">
				</td>
			</tr>
		
		<%
		}%>
		
	</table>
	<input type="hidden">
	<p align="center"><input type="submit" value="确定"></p>
</form>
<p>&nbsp;</p>
</body>
</html>