<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>老徐网上书店</title>
</head>
<body>
	<%
		String error = (String) request.getAttribute("error");
	%>
	
	<p align="center"><strong><font size="7">老徐网上书店</font></strong></p>
	<p align="center"><strong><font size="5">会员登录页</font></strong></p>
	<form name="form1" method="post" action="checklogin.jsp"> 
		<p>&nbsp;</p>
		<table width="500" border="0" align="center" cellpadding="8" cellspacing="0">
			<tr>
				<th colspan="2" scope="col"><font color="#0000FF">请输入会员代码和密码</font></th>
			</tr>
			<tr>
				<td align="right"><font color="#0000FF"><strong>会员代码:</strong></font></td>
				<td><input type="text" name="memberID" id="memberID"></td>
			</tr>
			<tr>
				<td align="right"><font color="#0000FF"><strong>密码:</strong></font></td>
				<td><input type="text" name="pwd" id="pwd"></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" name="Submit" value="登录"></td>
			</tr>
		</table>
		<p align="center"><font color="red"><%=error==null?"":error%></font></p>
	</form>
	<p>&nbsp;</p>
</body>
</html>