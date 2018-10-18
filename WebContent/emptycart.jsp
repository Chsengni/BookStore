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
	String userId=(String)session.getAttribute("memberID");
	if(userId!=null){
		CartInfo_DAO.empty(userId);
	}
	response.sendRedirect("shoppingcart.jsp");
%>
</body>
</html>