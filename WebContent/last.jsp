<%@page import="com.ch.CartInfo_DAO"%>
<%@page import="com.ch.OrderInfo"%>
<%@page import="java.sql.Date"%>
<%@page import="com.ch.OrderInfo_Orderdetail_DAO"%>
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
request.setCharacterEncoding("utf-8");
String userId = (String)session.getAttribute("memberID");
String name = request.getParameter("name");
if(userId==null||name==null){response.sendRedirect("index.jsp");}
String totalprice = request.getParameter("totalprice");
String add = request.getParameter("add");
String memo =request.getParameter("memo");
String zip = request.getParameter("zip");
String orderId=OrderInfo_Orderdetail_DAO.createOrderId();
java.util.Date date =new java.util.Date();
Date date2 =new Date(date.getTime());
OrderInfo oi = new OrderInfo();
oi.setIsSale(0);
oi.setOrderDate(date2);
oi.setOrderId(orderId);
oi.setOrderMemo(memo);
oi.setOrderprice(Float.parseFloat(totalprice));
oi.setReceiverAdd(add);
oi.setReceiverName(name);
oi.setReceiverZip(zip);
oi.setUserId(userId);
OrderInfo_Orderdetail_DAO.insertOrderInfo(oi);
OrderInfo_Orderdetail_DAO.insertOrderdetail();
CartInfo_DAO.empty(userId);
request.setAttribute("orderId", orderId);
request.setAttribute("oi", oi);
request.getRequestDispatcher("lastshow.jsp").forward(request, response);

%>
</body>
</html>