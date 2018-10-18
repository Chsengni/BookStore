<%@page import="com.ch.CartInfo_DAO"%>
<%@page import="com.ch.CartInfo_BookInfo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>订单确认——老徐网上书店</title>
</head>
<body>
<%
String userId =(String)session.getAttribute("memberID");
if(userId==null){
	response.sendRedirect("index.jsp");
}
List<CartInfo_BookInfo> li =CartInfo_DAO.find(userId);
float totalprice = 0;
%>
<p align="center"><strong><font size="5">订单确认——老徐网上书店</font></strong></p>
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
		for(CartInfo_BookInfo cb:li){
			float f = cb.getBi().getPrice()*cb.getCi().getBookCount();
			totalprice+=f;
		%>
		<tr>
			<td align="center"><%=cb.getBi().getBookISBN() %></td>
			<td align="center"><%=cb.getBi().getBookName() %></td>
			<td align="center"><%=cb.getBi().getPrice() %></td>
			<td align="center"><%=cb.getCi().getBookCount() %></td>
			<td align="center"><%=f %></td>
		</tr>
		
		<%} %>
		
	</table>
</div>
<p align="center"><a href="shoppingcart.jsp"><strong>修改图书订单</strong></a></p>
<p align="center"><strong>如以上信息无误,请您填写以下信息并提交订单,完成网上订书</strong></p>
<form name="form1" method="post" action="last.jsp"> 
	<div align="center">
		<table width="600" border="0" cellspacing="0" cellpadding="3">
			<tr>
				<td width="110"><strong>收书人姓名</strong></td>
				<td width="478"><input type="text" name="name" size="10"></td>
			</tr>
			<tr>
				<td><strong>订单总金额</strong></td>
				<td><input type="text" name="totalprice" size="10" value="<%=totalprice%>" readonly="true"></td>
			</tr>
			<tr>
				<td><strong>收货地址</strong></td>
				<td><textarea name="add" cols="50"></textarea></td>
			</tr>

			<tr>
				<td><strong>邮编</strong></td>
				<td><input type="text" name="zip" size="10"></td>
			</tr>
			<tr>
				<td><strong>备注</strong></td>
				<td><textarea name="memo"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" name="Submit" value="提交订单"> 
				</td>

			</tr>

		</table>
	</div>
</form>
<p>&nbsp;</p>
</body>
</html>