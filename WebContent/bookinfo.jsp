<%@page import="com.ch.BookInfo_DAO"%>
<%@page import="com.ch.BookInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>老徐网上书店^_^</title>
</head>
<body>
<%
	String userId = (String)session.getAttribute("memberID");
    String Id = request.getParameter("Id");
   	if(Id==null||userId==null){
   		response.sendRedirect("index.jsp");
   	}else{
    BookInfo b =BookInfo_DAO.findId(Integer.parseInt(Id));
%>
<p align="center"><strong><font size="5">图书信息</font></strong></p>
<table width="600" border="0" align="center" cellpadding="3" cellspacing="0">
	<tr>
		<td width="137" align="left"><font size="4">ISBN</font></td>
		<td width="451" align="left"><font size="4"><%=b.getBookISBN()%></font></td>
	</tr>
	<tr>
		<td align="left"><font size="4">书名</font></td>
		<td align="left"><font size="4"><%=b.getBookName() %></font></td>
	</tr>
	<tr>
		<td align="left"><font size="4">出版社</font></td>
		<td align="left"><font size="4"><%=b.getPublisher()%></font></td>
	</tr>
	<tr>
		<td align="left"><font size="4">作者/译者</font></td>
		<td align="left"><font size="4"><%=b.getBookAuthor()%></font></td>
	</tr>
	<tr>
		<td align="left"><font size="4">图书价格</font></td>
		<td align="left"><font size="4"><%=b.getPrice()%></font></td>
	</tr>
	<tr>
		<td align="center" colspan="2"><font size="4">内容简介</font></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<font size="4">
				<textarea name="textarea" cols="70" rows="6"><%=b.getIntroduce()%>
				</textarea>
			</font>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center"><font size="4"><a href="addcart.jsp?Id=<%=b.getId()%>">加入购物车</a>&nbsp;&nbsp;
		<a href="shoppingcart.jsp">查看购物车</a>&nbsp;
		</font></td>
	</tr>
</table>
<p>&nbsp;</p>
<%} %>
</body>
</html>