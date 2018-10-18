<%@page import="com.ch.BookInfo_DAO"%>
<%@page import="com.ch.BookInfo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>老徐网上书店^_^</title>
</head>
<body>
<%
	String memberID=(String)session.getAttribute("memberID");
	if(memberID==null){
%>
<p align="center"><font size="6"><strong>您还没有登录哟</strong></font>
</p>
<p align="center"><font size="6"><strong><a href="index.jsp">点击这里登录</a></strong></font></p>
<%} else{
	
	String spage =request.getParameter("cpage");
	int cpage = 1;
	if(spage!=null){
		cpage = Integer.parseInt(spage);
	}
	List<BookInfo> li = BookInfo_DAO.find(cpage);
	int ys =BookInfo_DAO.yeshu();
%>

<p align="center"><strong><font size="6">老徐网上书店</font></strong></p>
<div align="center">
	<table width="700" border="1" cellspacing="0" cellpadding="3">
		<tr>
			<th align="center" scope="col">书名</th>
			<th align="center" scope="col">作者</th>
			<th align="center" scope="col">出版社</th>
			<th align="center" scope="col">价格</th>
			<th align="center" scope="col">&nbsp;</th>
		</tr>	
		<%for(BookInfo b:li){
		%>
		<tr>
			<td align="center"><a href='bookinfo.jsp?Id=<%=b.getId()%>' target="_blank">
				<%=b.getBookName()%>
			</a></td>
			<td align="center"><%=b.getBookAuthor() %></td>
			<td align="center"><%=b.getPublisher() %></td>
			<td align="center"><%=b.getPrice() %></td>
			<td align="center">
			<a href="#" onclick='window.open("addcart.jsp?Id=<%=b.getId()%>","shoppingcart","width=600,height=400,top=200,left=500")'>加入购物车</a></td>
		
			</tr>
		<%} %>
	</table>
</div>
<p align="center">
	<%
	for(int i=1;i<=ys;i++){
		
	if(i==cpage){
		out.print(i+"&nbsp;&nbsp;");
		}
	else{
		out.print("<a href='booklist.jsp?cpage="+i+"'>"+i+"</a>&nbsp;&nbsp;");
		}
	}
	
	%>

</p>
<p align="center"><a href="shoppingcart.jsp">查看购物车</a></p>
<%} %>
</body>
</html>