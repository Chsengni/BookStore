<%@page import="com.ch.CartInfo_DAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String bookId=request.getParameter("Id");
	if(bookId!=null){
		
		CartInfo_DAO.del(bookId);
	}
	response.sendRedirect("shoppingcart.jsp");
%>
</body>
</html>