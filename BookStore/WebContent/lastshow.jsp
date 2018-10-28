<%@page import="com.ch.OrderInfo_Orderdetail_DAO"%>
<%@page import="com.ch.OrderInfo_BookInfo_Orderdetail"%>
<%@page import="java.util.List"%>
<%@page import="com.ch.OrderInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>老徐——网上书店</title>
</head>
<body>
<%
request.setCharacterEncoding("utf-8");
String userId =(String)session.getAttribute("memberID");
String orderId =(String) request.getAttribute("orderId");
OrderInfo oi =(OrderInfo)request.getAttribute("oi");
if(userId==null||orderId==null||oi==null){response.sendRedirect("index.jsp");}
List<OrderInfo_BookInfo_Orderdetail> li=OrderInfo_Orderdetail_DAO.find(orderId);
%>
<p align="center"><strong><font size="5">老徐网上书店</font></strong></p>
<p align="center"><strong>会员:<font color="#FF0000"><%=userId%></font>&nbsp;&nbsp;订购成功</strong></p>
<p align="center"><strong>订单号:</strong><font color="$FF0000"><%=orderId%></font></p>
<div align="center">
	<table width="600" border="1" cellpadding="3" cellspacing="0">
		<tr>
			<th scope="col">ISBN</th>
			<th scope="col">书名</th>
			<th scope="col">单价</th>
			<th scope="col">数量</th>
			<th scope="col">价格</th>
		</tr>
		<%
		float tprice=1;
		for(OrderInfo_BookInfo_Orderdetail obo :li){
			tprice = obo.getBi().getPrice()*obo.getOd().getBookCount();
		%>
		<tr>
			<td align="center"><%=obo.getBi().getBookISBN() %></td>
			<td align="center"><%=obo.getBi().getBookName() %></td>
			<td align="center"><%=obo.getBi().getPrice() %></td>
			<td align="center"><%=obo.getOd().getBookCount() %></td>
			<td align="center"><%=tprice %></td>
		</tr>
		
		<%} %>
	</table>
<p>&nbsp;</p>
</div>
<div align="center">
	<table width="600" border="0" cellspacing="0" cellpadding="3">
		<tr>
			<td width="110" align="right"><strong>收书人姓名:</strong></td>
			<td width="478"><%=oi.getReceiverName()%></td>
		</tr>
		<tr>
			<td align="right"><strong>收货地址:</strong></td>
			<td><%=oi.getReceiverAdd()%></td>
		</tr>
		<tr>
			<td align="right"><strong>邮编:</strong></td>
			<td><%=oi.getReceiverZip()%></td>
		</tr>
		<tr>
			<td align="right"><strong>备注:</strong></td>
			<td><%=oi.getOrderMemo()%></td>
		</tr>
		<tr>
			<td align="right"><strong>发货否:</strong></td>
			<td><%=oi.getIsSale()==0? "否":"是"%></td>
		</tr>

	</table>
</div>
<p>&nbsp;</p>
<p align="center"><a href="booklist.jsp">返回</a></p>
</body>
</html>