<%@page import="com.ch.BuyerInfo_DAO"%>
<%@page import="com.ch.BuyerInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>老徐网上书店</title>
</head>
<body>
<%
	String memberID =request.getParameter("memberID");
	String pwd = request.getParameter("pwd");
	BuyerInfo buyer = new BuyerInfo();
	if(!BuyerInfo_DAO.isMember(memberID, pwd)){
		request.setAttribute("error", "会员代码或密码不正确");
		//重定向
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}else{
		
		buyer= BuyerInfo_DAO.buyer;
		session.setAttribute("memberID", buyer.getMemberID());
	}
%>
<p align="center"><strong><font size="7">老徐网上书店</font></strong></p>
<p align="center">&nbsp;</p>
<p align="center"><strong><font color="#FF0000" size="5"></font><%=buyer.getMembername()%></strong></p>
<p align="center"><strong><font size="5">欢迎您第<font color="#FF0000"><%=buyer.getLoginTimes()%></font>次光临小店</font></strong></p>
<p align="center">&nbsp;</p>
<p align="center"><a href="booklist.jsp"><font size="5">进入书店</font></a></p>
</body>
</html>