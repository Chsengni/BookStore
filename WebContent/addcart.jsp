<%@page import="com.ch.CartInfo_DAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>购物车——老徐网上书店</title>
</head>
<body onload='setTimeout("close()",10000);return false;'>
	<%
	
	String bookId =request.getParameter("Id");
	String userId =(String)session.getAttribute("memberID");
	if(bookId!=null&&userId!=null){
		CartInfo_DAO.addcart(userId, bookId);
	}
	%>
	<table width="400" height="300" border="0" align="center" cellpadding="3" cellspacing="0">
		<tr>
			<td align="center"><strong>图书已经成功放入购物车</strong></td>
		</tr>
		<tr>
			<td align="center"><strong><input type="button" name="B3" value="继续购买" onclick="window.close()" style="border :#006699 solid 1px;background:#ccCCcc"></strong></td>
		</tr>
		<tr>
			<td align="center"><strong>(此窗口将为您在10秒内自动关闭</strong></td>
		</tr>
		<tr>
			<td align="center"><strong>商品已经安全地保存在购物车中)</strong></td>
		</tr>
	</table>	
</body>
</html>