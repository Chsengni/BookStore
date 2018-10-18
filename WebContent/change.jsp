<%@page import="com.ch.CartInfo_DAO"%>
<%@page import="com.ch.CartInfo_BookInfo"%>
<%@page import="java.util.List"%>
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
		List<CartInfo_BookInfo> li =CartInfo_DAO.find(userId);
		for(CartInfo_BookInfo cb:li){
			CartInfo_DAO.change(userId, cb.getCi().getBookId(),Integer.parseInt(request.getParameter(cb.getCi().getBookId())));
		}
	}
	response.sendRedirect("shoppingcart.jsp");
%>
</body>
</html>