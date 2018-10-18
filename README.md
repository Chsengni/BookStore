# BookStore
期末Jsp项目设计


1.构建数据库(使用SQLyog)

数据库命令

//创建数据库
CREATE DATABASE bookstore;

//使用数据库
USE bookstore;

//创建会员表
CREATE TABLE buyerinfo(
	Id INT(11) UNIQUE KEY AUTO_INCREMENT,
	memberID VARCHAR(15) PRIMARY KEY  DEFAULT "000000",
	membername VARCHAR(15) ,
	loginTimes INT(5) NOT NULL DEFAULT 0,
	pwd VARCHAR(10),
	phoneCode VARCHAR(11),
	zipcode VARCHAR(6),
	address VARCHAR(50),
	email VARCHAR(20)
);


 


文档上说memberID是主键（primary key）。 







2.打开eclipse创建项目 
1）new ----> Dynamic Web Project

 


2）src 创建一个class 

 
3）JavaBean


package com.ch;

public class BuyerInfo {

	private int Id;
	private String memberID;
	private String membername;
	private int loginTimes;
	private String pwd;
	private String phoneCode;
	private String zipcode;
	private String address;
	private String email;
}

 


4）点右键  Source  生成setter 与 getter 方法


 


生成后

package com.ch;

public class BuyerInfo {

	private int Id;
	private String memberID;
	private String membername;
	private int loginTimes;
	private String pwd;
	private String phoneCode;
	private String zipcode;
	private String address;
	private String email;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public int getLoginTimes() {
		return loginTimes;
	}
	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPhoneCode() {
		return phoneCode;
	}
	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}


5）点右键  Source 生成构造方法from Superclass(父类)  

	public BuyerInfo() {
		super();
		// TODO Auto-generated constructor stub
	}


 

 
3.连接数据库
1）下载一个驱动mysql的jar文件
mysql-connector-java-5.1.39-bin.jar（版本随意）
下载页
放到lib下（如图）
 

2）鼠标放在mysql-connector-java-5.1.39-bin.jar 点击右键 构建路径（Build path  Add to Build path）后多了 Reference Libraries
 



3）创建class
 

package com.ch;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Bookjdbc_Util {
	public static Connection getConnection() {
		Connection cnn =null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			String url="jdbc:mysql://localhost:3306/bookstore?"+"useUnicode=true&characterEncoding=UTF-8"; 
			String user="root"; //填上你自己数据库的账号
			String password="root";// 密码
			cnn =DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return cnn;
	}
}


4.创建BuyerInfo_DAO.java

package com.ch;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class BuyerInfo_DAO {
	/**
	 * 创建一个会员对象
	 */
	public static BuyerInfo buyer = new BuyerInfo();
	
	/**
	 * 判断是否为成员
	 * @param ID
	 * @param pwd
	 * @return
	 */
	public static boolean isMember(String ID,String pwd) {
		//记录是否为会员
		boolean r=false;
		/**
		 * 查询用户
		 */
		String sql = "select * from buyerinfo where memberID=? and pwd=?";
		try {
			/**
			 * 调用Bookjdbc_Util的getConnection方法
			 * 返回cnn 对象
			 */
			Connection cnn = Bookjdbc_Util.getConnection();
			//创建查询
			PreparedStatement ps= cnn.prepareStatement(sql);
			ps.setString(1,ID);
			ps.setString(2, pwd);
			//执行查询并返回结果
			ResultSet rs= ps.executeQuery();
			//利用迭代
			if (rs.next()) {
				//有返回结果，说明为成员
				r=true;
				buyer.setAddress(rs.getString("address"));
				buyer.setEmail(rs.getString("email"));
				buyer.setId(rs.getInt("Id"));
				buyer.setLoginTimes(rs.getInt("loginTimes"));
				buyer.setMemberID(ID);
				buyer.setMembername(rs.getString("membername"));
				buyer.setPhoneCode(rs.getString("phoneCode"));
				buyer.setPwd(pwd);
				buyer.setZipcode(rs.getString("zipcode"));
				sql = "update buyerInfo set loginTimes = loginTimes+1 where memberID=?";
				ps = cnn.prepareStatement(sql);
				ps.setString(1, ID);
				ps.executeUpdate();
			}	
			if (rs!=null) {
				rs.close();
			}
			if (ps!=null) {
				ps.close();
			}
			if (cnn!=null) {
				cnn.close();
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}	
}



5.创建index.jsp

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
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

6.创建checklogin.jsp;

 

<%@page import="com.ch.BuyerInfo_DAO"%>
<%@page import="com.ch.BuyerInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
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



7.创建 booklist.jsp
暂时放着。

8.打开SQLyog
//创建bookinfo
CREATE TABLE bookinfo(
Id INT(11) UNIQUE KEY AUTO_INCREMENT,
bookISBN VARCHAR(20) PRIMARY KEY,
bookName VARCHAR(40),
bookAuthor VARCHAR(20),
publisher VARCHAR(20),
price FLOAT(3,1),
introduce VARCHAR(150)
);

9.创建BookInfo.java

package com.ch;

public class BookInfo {
	
		private int Id;
		private String bookISBN;
		private String bookName;
		private String bookAuthor;
		private String publisher;
		private float price;
		private String introduce;
		public BookInfo() {
			super();
			// TODO Auto-generated constructor stub
		}
		public int getId() {
			return Id;
		}
		public void setId(int id) {
			Id = id;
		}
		public String getBookISBN() {
			return bookISBN;
		}
		public void setBookISBN(String bookISBN) {
			this.bookISBN = bookISBN;
		}
		public String getBookName() {
			return bookName;
		}
		public void setBookName(String bookName) {
			this.bookName = bookName;
		}
		public String getBookAuthor() {
			return bookAuthor;
		}
		public void setBookAuthor(String bookAuthor) {
			this.bookAuthor = bookAuthor;
		}
		public String getPublisher() {
			return publisher;
		}
		public void setPublisher(String publisher) {
			this.publisher = publisher;
		}
		public float getPrice() {
			return price;
		}
		public void setPrice(float price) {
			this.price = price;
		}
		public String getIntroduce() {
			return introduce;
		}
		public void setIntroduce(String introduce) {
			this.introduce = introduce;
		}
		
}


10.创建BookInfo_DAO.java

package com.ch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookInfo_DAO {
	final static int PAGESIZE=3;
	/**
	 * 根据page值查找书籍
	 * 放到li
	 * @param page
	 * @return
	 */
	public static List<BookInfo> find(int page) {
		
		/**
		 * 实例List对象li
		 */
		List<BookInfo> li = new ArrayList<>();
		String sql= "select * from BookInfo limit?,?";
		try {
			Connection cnn =Bookjdbc_Util.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
			ps.setInt(1, (page-1)*PAGESIZE);
			ps.setInt(2,PAGESIZE);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
			
				/**
				 * 实例一个BookInfo
				 * 将每一条结果封装到bi对象
				 * 加入到li对象
				 */
				BookInfo bi=new BookInfo(); 
				bi.setBookAuthor(rs.getString("bookAuthor"));
				bi.setBookISBN(rs.getString("bookISBN"));
				bi.setBookName(rs.getString("bookName"));
				bi.setId(rs.getInt("Id"));
				bi.setIntroduce(rs.getString("introduce"));
				bi.setPrice(rs.getFloat("price"));
				bi.setPublisher(rs.getString("publisher"));
				li.add(bi);
			}
			
			if (rs!=null) {
				rs.close();
			}
			if (ps!=null) {
				ps.close();
			}
			if (cnn!=null) {
				cnn.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return li;
	}
	
	/**
	 * 获取总共多少本书籍
	 * @return
	 */
	public static int getRecordCount() {
		int c=0;
		String sql="select count(*) from BookInfo";
		try {
			
			Connection cnn =Bookjdbc_Util.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
			ResultSet rs;
			rs = ps.executeQuery();
			if (rs.next()) {
				c = rs.getInt(1);
			}
			if (rs!=null) {
				rs.close();
			}
			if (ps!=null) {
				ps.close();
			}
			if (cnn!=null) {
				cnn.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return c;
	}
	
	
	/**
	 * 把c条记录分成多少页
	 * @return
	 */
	public static int yeshu() {
		int c = getRecordCount();
		return c%PAGESIZE==0?c/PAGESIZE:(c/PAGESIZE+1);
	}
	
	public static BookInfo findId(String Id) {
		String sql = "select * from BookInfo where Id=?";
		BookInfo bi = new BookInfo();
		try {
			Connection cnn =Bookjdbc_Util.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
			ps.setString(1, Id);
			ResultSet rs;
			rs = ps.executeQuery();
			bi.setBookAuthor(rs.getString("bookAuthor"));
			bi.setBookISBN(rs.getString("bookISBN"));
			bi.setBookName(rs.getString("bookName"));
			bi.setId(rs.getInt("Id"));
			bi.setIntroduce(rs.getString("introduce"));
			bi.setPrice(rs.getFloat("price"));
			bi.setPublisher(rs.getString("publisher"));
			if (rs!=null) {
				rs.close();
			}
			if (ps!=null) {
				ps.close();
			}
			if (cnn!=null) {
				cnn.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return bi;
	}
	
}


11.booklist.jsp

<%@page import="com.ch.BookInfo_DAO"%>
<%@page import="com.ch.BookInfo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
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
		<%for(BookInfo b:li){%>
		<tr>
			<td align="center"><a href='bookinfo.jsp?Id=<%=b.getId()%>' target="_blank">
				<%=b.getBookName()%>
			</a></td>
			<td align="center"><%=b.getBookAuthor() %></td>
			<td align="center"><%=b.getPublisher() %></td>
			<td align="center"><%=b.getPrice() %></td>
			<td align="center"><a href="#" onclick='window.open("addcart.jsp?Id<%=b.getId()%>","shoppingcart","width=300,height=300,top=200,left=300")'>加入购物车</a></td>
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

12.创建addcart.jsp 和  shoppingcart.jsp 和 bookinfo.jsp


13.创建数据表 cartinfo

CREATE TABLE cartinfo(
id INT(11) UNIQUE KEY AUTO_INCREMENT,
userId VARCHAR(15),
bookId VARCHAR(20),
bookCount INT(4) UNSIGNED ZEROFILL,
PRIMARY KEY(userId,bookId)
);


14.创建 CartInfo.java

package com.ch;

public class CartInfo {

	private int Id;
	/**
	 * 对应buyerinfo表中的memberID
	 */
	private String userId;
	
	private String bookId;
	private int bookCount;
	public CartInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public int getBookCount() {
		return bookCount;
	}
	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}
	
	
}
15.创建CartInfo_BookInfo.java
package com.ch;
public class  CartInfo_BookInfo {
	private CartInfo ci;
	private BookInfo bi;
	public CartInfo getCi() {
		return ci;
	}
	public void setCi(CartInfo ci) {
		this.ci = ci;
	}
	public BookInfo getBi() {
		return bi;
	}
public void setBi(BookInfo bi) {
		this.bi = bi;
	}
	public CartInfo_BookInfo(){
			super();
}
	public CartInfo_BookInfo(CartInfo ci, BookInfo bi) {
		this.ci = ci;
		this.bi = bi;
	}
}



16.创建CartInfo_DAO.java


package com.ch;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class CartInfo_DAO {
	public static int addcart(String userId,String bookId) {
		int i=0;	
		String sql="update cartinfo set bookCount=bookCount + 1 where userId=? and bookId=?";
		try {		
			Connection cnn = Bookjdbc_Util.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, bookId);
			i=ps.executeUpdate();
			if (i==0) {
				sql ="insert into cartinfo(userId,bookId,bookCount)values(?,?,?)";
				ps = cnn.prepareStatement(sql);
				ps.setString(1, userId);
				ps.setString(2, bookId);
				ps.setInt(3, 1);
				i = ps.executeUpdate();
			}	
			if(ps!=null) {
				ps.close();
			}
			if (cnn!=null) {
				cnn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return i;
	}
	
	public static List<CartInfo_BookInfo> find(String userId) {
		List<CartInfo_BookInfo> li = new ArrayList<>();
		String sql="select * from bookinfo b,cartinfo c where b.Id=c.bookId and userId=?";
		
		try {
			Connection cnn = Bookjdbc_Util.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {	
				CartInfo ci = new CartInfo();
				BookInfo bi =new BookInfo();
				CartInfo_BookInfo c =new CartInfo_BookInfo(ci,bi);
				bi.setBookAuthor(rs.getString("bookAuthor"));
				bi.setBookISBN(rs.getString("bookISBN"));
				bi.setBookName(rs.getString("bookName"));
				bi.setId(rs.getInt("b.Id"));
				bi.setIntroduce(rs.getString("introduce"));
				bi.setPrice(rs.getFloat("price"));
				bi.setPublisher(rs.getString("publisher"));
				ci.setUserId(rs.getString("userId"));
				ci.setBookCount(rs.getInt("bookCount"));
				ci.setBookId(rs.getString("bookId"));
				ci.setUserId(rs.getString("userId"));
				li.add(c);
			}
			if (rs!=null) {
				rs.close();
			}
			if (ps!=null) {
				ps.close();
			}
			if (cnn!=null) {
				cnn.close();
			}
 		} catch (Exception e) {
			e.printStackTrace();
		}
		return li;
	}
	public static int del(String bookId) {
		String sql ="delete from cartinfo where bookId=?";
		int i=0;
		try {
			Connection cnn = Bookjdbc_Util.getConnection();
			PreparedStatement ps=cnn.prepareStatement(sql);
			ps.setString(1, bookId);
			i=ps.executeUpdate();
			if (ps!=null) {
				ps.close();
			}
			if (cnn!=null) {
				cnn.close();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;	
	}
	
	public static int empty(String userId) {
		String sql="delete from cartinfo where userId=?";
		int i=0;
		try {
			Connection cnn = Bookjdbc_Util.getConnection();
			PreparedStatement ps=cnn.prepareStatement(sql);
			ps.setString(1, userId);
			i=ps.executeUpdate();
			
			if (ps!=null) {
				ps.close();
			}
			if (cnn!=null) {
				cnn.close();
			}	
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		return i;
	}
	
	public static int change(String userId,String bookId, String bookCount) {
		String sql ="update cartinfo set bookCount=? where userId=? and bookId=?";
		int i =0;
		try {
			Connection cnn = Bookjdbc_Util.getConnection();
			PreparedStatement ps=cnn.prepareStatement(sql);
			ps.setString(1,bookCount);
			ps.setString(2, userId);
			ps.setString(3, bookId);
			i=ps.executeUpdate();	
			if (ps!=null) {
				ps.close();
			}
			if (cnn!=null) {
				cnn.close();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}	
}


17.编写addcart.jsp

<%@page import="com.ch.CartInfo_DAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>购物车--老徐网上书店</title>
</head>
<body onload='setTimeout("close()",10000);return false;'>
	<%
	
	String bookId =request.getParameter("Id");
	String userId =(String)session.getAttribute("memberID");
	if(bookId!=null&&userId!=null){
		CartInfo_DAO.addcart(userId, bookId);
	}
	%>
	<table width="280" height="200" border="0" align="center" cellpadding="3" cellspacing="0">
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


18.编写shoppingcart.jsp
<%@page import="com.ch.CartInfo_DAO"%>
<%@page import="com.ch.CartInfo_BookInfo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>购物车--老徐网上书店</title>
</head>
<body>
<%
	String userId=(String)session.getAttribute("memberID");
	if(userId==null){
		response.sendRedirect("index.jsp");	
	}else{
	List<CartInfo_BookInfo> li =CartInfo_DAO.find(userId);
	
%>
<p align="center"><strong><font size="5">购物车 -- 老徐网上书店</font></strong></p>
<form name="form1" method="post" action="">
	<table width="600" border="1" align="center" cellpadding="3" cellspacing="0">
		<tr>
			<th align="center" scope="col">ISBN</th>
			<th align="center" scope="col">书名</th>
			<th align="center" scope="col">单价</th>
			<th align="center" scope="col">数量</th>
			<th align="center" scope="col">&nbsp;</th>
		</tr>
		<%
		for(CartInfo_BookInfo cb:li){
		%>
		<tr>
			<td align="center"><%=cb.getBi().getBookISBN()%></td>
			<td align="center"><%=cb.getBi().getBookName() %></td>
			<td align="center"><%=cb.getBi().getPrice() %></td>
			<td align="center">
			<input name="num" type="text" id="num" size="6" readonly="true" value="<%=cb.getCi().getBookCount()%>"> 
			</td>
			<td align="center"><a href="delbook.jsp?Id=<%=cb.getBi().getId()%>">删除</a></td>
		</tr>
		<%} }%>
	</table>
	<p align="center"><strong><a href="booklist.jsp">返回</a>&nbsp;&nbsp;&nbsp;
	<a href="emptycart.jsp">清空购物车</a>&nbsp;&nbsp;&nbsp;
	<a href="changecart.jsp">修改数量</a>&nbsp;&nbsp;
	<a href="order.jsp">提交订单</a>
	</strong></p>
	<p>&nbsp;</p>
</form>
</body>
</html>


19.创建emptycart.jsp和changecart.jsp和order.jsp和 delbook.jsp

20.编写delbook.jsp

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

21.编写emptycart.jsp
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

22.编写changecart.jsp

<%@page import="com.ch.CartInfo_DAO"%>
<%@page import="com.ch.CartInfo_BookInfo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>修改购物车数量————老徐网上书店</title>
</head>
<body>
<%
	String userId=(String)session.getAttribute("memberID");
	if(userId==null){
		response.sendRedirect("index.jsp");
	}
	List<CartInfo_BookInfo> li =CartInfo_DAO.find(userId);
	request.setAttribute("li", li);
%>

<p align="center"><strong><font size="5">购物车——老徐网上书店</font></strong></p>
<form name="form1" method="post" action="change.jsp">
	<table width="600" border="1" align="center" cellpadding="3" cellspacing="0">
		<tr>
			<th align="center" scope="col">ISBN</th>
			<th align="center" scope="col">书名</th>
			<th align="center" scope="col">单价</th>
			<th align="center" scope="col">数量</th>
		</tr>
		<%for(CartInfo_BookInfo cb:li){	
			%>
			<tr>
				<td align="center"><%=cb.getBi().getBookISBN()%></td>
				<td align="center"><%=cb.getBi().getBookName() %></td>
				<td align="center"><%=cb.getBi().getPrice() %></td>
				<td align="center">
				<input name="<%=cb.getCi().getBookId()%>" type="text" id="num" size="6" value="<%=cb.getCi().getBookCount()%>">
				</td>
			</tr>
		
		<%
		}%>
		
	</table>
	<input type="hidden">
	<p align="center"><input type="submit" value="确定"></p>
</form>
<p>&nbsp;</p>
</body>
</html>

23.编写change.jsp

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

24.创建数据表 orderinfo
CREATE TABLE orderinfo(
id INT(11) UNIQUE KEY AUTO_INCREMENT,
orderId VARCHAR(15),
userId VARCHAR(15),
receiverName VARCHAR(10),
receiverAdd VARCHAR(50),
receiverZip VARCHAR(10),
orderMemo VARCHAR(50),
orderPrice FLOAT(6,2),
orderDate DATE,
isSale INT(1) UNSIGNED ZEROFILL  DEFAULT '0'

);

25.创建数据表 orderdetail
CREATE TABLE orderdetail(
id INT(11) UNIQUE KEY AUTO_INCREMENT,
orderId VARCHAR(15),
bookId VARCHAR(20),
bookCount INT(4)
);

26.创建OrderInfo.java

package com.ch;

import java.util.Date;

public class OrderInfo {
	private int Id;
	private String userId;
	private String receiverName;
	private String receiverAdd;
	private String receiverZip;
	private String orderMemo;
	private float orderprice;
	private Date orderDate;
	private int isSale;
	public OrderInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverAdd() {
		return receiverAdd;
	}
	public void setReceiverAdd(String receiverAdd) {
		this.receiverAdd = receiverAdd;
	}
	public String getReceiverZip() {
		return receiverZip;
	}
	public void setReceiverZip(String receiverZip) {
		this.receiverZip = receiverZip;
	}
	public String getOrderMemo() {
		return orderMemo;
	}
	public void setOrderMemo(String orderMemo) {
		this.orderMemo = orderMemo;
	}
	public float getOrderprice() {
		return orderprice;
	}
	public void setOrderprice(float orderprice) {
		this.orderprice = orderprice;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public int getIsSale() {
		return isSale;
	}
	public void setIsSale(int isSale) {
		this.isSale = isSale;
	}

}

27.创建Orderdetail.java
package com.ch;

public class Orderdetail {
	
	private int Id;
	private String orderId;
	private String bookId;
	private int bookCount;
	public Orderdetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public int getBookCount() {
		return bookCount;
	}
	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}
	
}

28.创建OrderInfo_BookInfo_Orderdetail.java

package com.ch;

public class OrderInfo_BookInfo_Orderdetail {
	private OrderInfo oi;
	private BookInfo bi;
	private Orderdetail od;

	public OrderInfo_BookInfo_Orderdetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderInfo_BookInfo_Orderdetail(OrderInfo oi, BookInfo bi, Orderdetail od) {
		super();
		this.oi = oi;
		this.bi = bi;
		this.od = od;
	}

	public OrderInfo getOi() {
		return oi;
	}

	public void setOi(OrderInfo oi) {
		this.oi = oi;
	}

	public BookInfo getBi() {
		return bi;
	}

	public void setBi(BookInfo bi) {
		this.bi = bi;
	}

	public Orderdetail getOd() {
		return od;
	}
	public void setOd(Orderdetail od) {
		this.od = od;
	}
}
29.编写OrderInfo_Orderdetail_DAO.java
package com.ch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderInfo_Orderdetail_DAO {
	
		/**
		 * 创建一个OrderId
		 * @return
		 */
		public static String createOrderId() {
			String s="";
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			s = df.format(new Date());
			return s;
		}
		
		public static int insertOrderInfo(OrderInfo oi) {
			String sql ="insert into orderinfo (orderId,userId,receiverName,"
					+ "receiverAdd,receiverZip,orderMemo,orderPrice,orderDate,isSale) "
					+ "values(?,?,?,?,?,?,?,?,?)";
			int i =0;
			try {
				Connection cnn = Bookjdbc_Util.getConnection();
				PreparedStatement ps = cnn.prepareStatement(sql);
				ps.setString(1, oi.getOrderId());
				ps.setString(2, oi.getUserId());
				ps.setString(3, oi.getReceiverName());
				ps.setString(4, oi.getReceiverAdd());
				ps.setString(5, oi.getReceiverZip());
				ps.setString(6, oi.getOrderMemo());
				ps.setFloat(7, oi.getOrderprice());
				ps.setDate(8,  oi.getOrderDate());
				ps.setInt(9, oi.getIsSale());
				i = ps.executeUpdate();
				if (ps!=null) {
					ps.close();
				}
				if(cnn!=null) {
					cnn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return i;
		}
		
		
		
		public static int insertOrderdetail() {
			
			String sql="insert into orderdetail(orderId,bookId,bookCount) "
					+ "select orderinfo.orderId,cartinfo.bookId,cartinfo.bookCount"
					+ " from cartinfo,orderinfo where cartinfo.userId=orderinfo.userId";
			int i=0;
			try {
				
				Connection cnn = Bookjdbc_Util.getConnection();
				PreparedStatement ps =cnn.prepareStatement(sql);
				i =ps.executeUpdate();
				if (ps!=null) {
					ps.close();
				}
				if(cnn!=null) {
					cnn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return i;
			
		}
		
		public static List<OrderInfo_BookInfo_Orderdetail> find(String orderId) {
			List<OrderInfo_BookInfo_Orderdetail> li = new ArrayList<>();
			
			String sql = "select * from orderinfo,bookinfo,orderdetail where"
					+ " bookinfo.Id=orderdetail.bookId and orderdetail.orderId=orderinfo.orderId"
					+ " and orderinfo.orderId=?";
			
			try {
				Connection cnn = Bookjdbc_Util.getConnection();
				PreparedStatement ps =cnn.prepareStatement(sql);
				ps.setString(1, orderId);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					OrderInfo oi=new OrderInfo();
					BookInfo bi =new BookInfo();
					Orderdetail od =new Orderdetail();
					OrderInfo_BookInfo_Orderdetail c =new OrderInfo_BookInfo_Orderdetail(oi,bi,od);
					bi.setBookAuthor(rs.getString("bookAuthor"));
					bi.setBookISBN(rs.getString("bookISBN"));
					bi.setBookName(rs.getString("bookName"));
					bi.setId(rs.getInt("id"));
					bi.setIntroduce(rs.getString("introduce"));
					bi.setPrice(rs.getFloat("price"));
					bi.setPublisher(rs.getString("publisher"));
					oi.setId(rs.getInt("id"));
					oi.setIsSale(rs.getInt("isSale"));
					oi.setOrderDate(rs.getDate("orderDate"));
					oi.setOrderId(rs.getString("orderId"));
					oi.setOrderMemo(rs.getString("orderMemo"));
					oi.setOrderprice(rs.getFloat("orderprice"));
					oi.setReceiverAdd(rs.getString("receiverAdd"));
					oi.setReceiverName(rs.getString("receiverName"));
					oi.setReceiverZip(rs.getString("receiverZip"));
					oi.setUserId(rs.getString("userId"));
					od.setBookCount(rs.getInt("bookCount"));
					od.setBookId(rs.getString("bookId"));
					od.setId(rs.getInt("orderdetail.Id"));
					od.setOrderId(rs.getString("orderdetail.orderId"));
					li.add(c);
				}
				if (rs!=null) {
					rs.close();
				}
				if (ps!=null) {
					ps.close();
				}
				if (cnn!=null) {
					cnn.close();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return li;
		}
		
}

30. 编写order.jsp

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


31.编写last.jsp
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


32.编写lastshow.jsp
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


最终结构







 






 

 

 

 

 

 






我的MySQL里的bookstore数据库

将以下复制文件复制为 sql语句 执行一下。

/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.5.27 : Database - bookstore
*********************************************************************
*/ 

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bookstore` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bookstore`;

/*Table structure for table `bookinfo` */

DROP TABLE IF EXISTS `bookinfo`;

CREATE TABLE `bookinfo` (
  `Id` INT(11) NOT NULL AUTO_INCREMENT,
  `bookISBN` VARCHAR(20) NOT NULL,
  `bookName` VARCHAR(40) DEFAULT NULL,
  `bookAuthor` VARCHAR(20) DEFAULT NULL,
  `publisher` VARCHAR(20) DEFAULT NULL,
  `price` FLOAT(3,1) DEFAULT NULL,
  `introduce` VARCHAR(150) DEFAULT NULL,
  PRIMARY KEY (`bookISBN`),
  UNIQUE KEY `Id` (`Id`)
) ENGINE=INNODB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `bookinfo` */

INSERT  INTO `bookinfo`(`Id`,`bookISBN`,`bookName`,`bookAuthor`,`publisher`,`price`,`introduce`) VALUES (1,'001','三体','刘慈欣','清华出版社',38.5,'非常的好看'),(2,'002','哈利波特之剑圣 ','千山尽 ','无',15.0,'（本文哈利波特开篇，前期世界为哈利波特跟美漫混合的世界）'),(3,'003','守望先锋入侵美漫','东方星尘','无',45.0,'　富人靠科技，穷人靠变异。'),(4,'004','文化苦旅','余秋雨','长江文艺出版社',21.7,'新版出版近1年重掀文化热，深思中国历史之力作《文化之痛》全新收录！影响三代华人的文化价值观，值得全家人一读再读的经典之作。'),(5,'005','朝花夕拾','鲁迅','天津人民出版社',12.5,'中国鲁迅研究会常务副会长、鲁迅博物馆原馆长孙郁倾力推荐。精选48篇散文，口碑典藏版。');

/*Table structure for table `buyerinfo` */

DROP TABLE IF EXISTS `buyerinfo`;

CREATE TABLE `buyerinfo` (
  `Id` INT(11) NOT NULL AUTO_INCREMENT,
  `memberID` VARCHAR(15) NOT NULL DEFAULT '000000',
  `membername` VARCHAR(15) DEFAULT NULL,
  `loginTimes` INT(5) NOT NULL DEFAULT '0',
  `pwd` VARCHAR(10) DEFAULT NULL,
  `phoneCode` VARCHAR(11) DEFAULT NULL,
  `zipcode` VARCHAR(6) DEFAULT NULL,
  `address` VARCHAR(50) DEFAULT NULL,
  `email` VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (`memberID`),
  UNIQUE KEY `Id` (`Id`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `buyerinfo` */

INSERT  INTO `buyerinfo`(`Id`,`memberID`,`membername`,`loginTimes`,`pwd`,`phoneCode`,`zipcode`,`address`,`email`) VALUES (1,'0000001','123',24,'123',NULL,NULL,NULL,NULL),(2,'0000002',NULL,0,NULL,NULL,NULL,NULL,NULL),(3,'0000003',NULL,0,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `cartinfo` */

DROP TABLE IF EXISTS `cartinfo`;

CREATE TABLE `cartinfo` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(15) NOT NULL DEFAULT '',
  `bookId` VARCHAR(20) NOT NULL DEFAULT '',
  `bookCount` INT(4) UNSIGNED ZEROFILL DEFAULT NULL,
  PRIMARY KEY (`userId`,`bookId`),
  UNIQUE KEY `id` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

/*Data for the table `cartinfo` */

INSERT  INTO `cartinfo`(`id`,`userId`,`bookId`,`bookCount`) VALUES (6,'0000002','1',0011),(2,'0000002','3',0004);

/*Table structure for table `orderdetail` */

DROP TABLE IF EXISTS `orderdetail`;

CREATE TABLE `orderdetail` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `orderId` VARCHAR(15) DEFAULT NULL,
  `bookId` VARCHAR(20) DEFAULT NULL,
  `bookCount` INT(4) DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=669 DEFAULT CHARSET=utf8;

/*Data for the table `orderdetail` */

INSERT  INTO `orderdetail`(`id`,`orderId`,`bookId`,`bookCount`) VALUES (1,'20181017215724','1',2),(2,'20181017215724','2',4),(3,'20181017215724','3',3),(4,'20181017215724','4',5),(5,'20181017215724','5',2),(8,'20181017215724','1',2),(9,'20181017215724','2',4),(10,'20181017215724','3',3),(11,'20181017215724','4',5),(12,'20181017215724','5',2),(13,'20181017215958','1',2),(14,'20181017215958','2',4),(15,'20181017215958','3',3),(16,'20181017215958','4',5),(17,'20181017215958','5',2),(23,'20181017215724','1',2),(24,'20181017215724','2',4),(25,'20181017215724','3',3),(26,'20181017215724','4',5),(27,'20181017215724','5',2),(28,'20181017215958','1',2),(29,'20181017215958','2',4),(30,'20181017215958','3',3),(31,'20181017215958','4',5),(32,'20181017215958','5',2),(33,'20181017220539','1',2),(34,'20181017220539','2',4),(35,'20181017220539','3',3),(36,'20181017220539','4',5),(37,'20181017220539','5',2),(38,'20181017215724','1',2),(39,'20181017215724','2',4),(40,'20181017215724','3',3),(41,'20181017215724','4',5),(42,'20181017215724','5',2),(43,'20181017215958','1',2),(44,'20181017215958','2',4),(45,'20181017215958','3',3),(46,'20181017215958','4',5),(47,'20181017215958','5',2),(48,'20181017220539','1',2),(49,'20181017220539','2',4),(50,'20181017220539','3',3),(51,'20181017220539','4',5),(52,'20181017220539','5',2),(53,'20181017220631','1',2),(54,'20181017220631','2',4),(55,'20181017220631','3',3),(56,'20181017220631','4',5),(57,'20181017220631','5',2),(58,'20181017230222','1',2),(59,'20181017230222','2',4),(60,'20181017230222','3',3),(61,'20181017230222','4',5),(62,'20181017230222','5',2),(69,'20181017215724','2',1),(70,'20181017215958','2',1),(71,'20181017220539','2',1),(72,'20181017220631','2',1),(73,'20181017230222','2',1),(74,'20181018130641','2',1),(75,'20181017215724','3',2),(76,'20181017215958','3',2),(77,'20181017220539','3',2),(78,'20181017220631','3',2),(79,'20181017230222','3',2),(80,'20181018130641','3',2),(84,'20181017215724','2',2),(85,'20181017215958','2',2),(86,'20181017220539','2',2),(87,'20181017220631','2',2),(88,'20181017230222','2',2),(89,'20181018130641','2',2),(90,'20181018130807','2',2),(91,'20181018130817','2',2),(92,'20181018131003','2',2),(93,'20181018131027','2',2),(94,'20181018131053','2',2),(95,'20181018131149','2',2),(99,'20181017215724','1',1),(100,'20181017215958','1',1),(101,'20181017220539','1',1),(102,'20181017220631','1',1),(103,'20181017230222','1',1),(104,'20181018130641','1',1),(105,'20181018130807','1',1),(106,'20181018130817','1',1),(107,'20181018131003','1',1),(108,'20181018131027','1',1),(109,'20181018131053','1',1),(110,'20181018131149','1',1),(111,'20181018131204','1',1),(112,'20181018131306','1',1),(113,'20181017215724','2',1),(114,'20181017215958','2',1),(115,'20181017220539','2',1),(116,'20181017220631','2',1),(117,'20181017230222','2',1),(118,'20181018130641','2',1),(119,'20181018130807','2',1),(120,'20181018130817','2',1),(121,'20181018131003','2',1),(122,'20181018131027','2',1),(123,'20181018131053','2',1),(124,'20181018131149','2',1),(125,'20181018131204','2',1),(126,'20181018131306','2',1),(127,'20181017215724','3',2),(128,'20181017215958','3',2),(129,'20181017220539','3',2),(130,'20181017220631','3',2),(131,'20181017230222','3',2),(132,'20181018130641','3',2),(133,'20181018130807','3',2),(134,'20181018130817','3',2),(135,'20181018131003','3',2),(136,'20181018131027','3',2),(137,'20181018131053','3',2),(138,'20181018131149','3',2),(139,'20181018131204','3',2),(140,'20181018131306','3',2),(162,'20181017215724','1',5),(163,'20181017215724','2',8),(164,'20181017215724','3',10),(165,'20181017215724','4',7),(166,'20181017215724','5',6),(167,'20181017215958','1',5),(168,'20181017215958','2',8),(169,'20181017215958','3',10),(170,'20181017215958','4',7),(171,'20181017215958','5',6),(172,'20181017220539','1',5),(173,'20181017220539','2',8),(174,'20181017220539','3',10),(175,'20181017220539','4',7),(176,'20181017220539','5',6),(177,'20181017220631','1',5),(178,'20181017220631','2',8),(179,'20181017220631','3',10),(180,'20181017220631','4',7),(181,'20181017220631','5',6),(182,'20181017230222','1',5),(183,'20181017230222','2',8),(184,'20181017230222','3',10),(185,'20181017230222','4',7),(186,'20181017230222','5',6),(187,'20181018130641','1',5),(188,'20181018130641','2',8),(189,'20181018130641','3',10),(190,'20181018130641','4',7),(191,'20181018130641','5',6),(192,'20181018130807','1',5),(193,'20181018130807','2',8),(194,'20181018130807','3',10),(195,'20181018130807','4',7),(196,'20181018130807','5',6),(197,'20181018130817','1',5),(198,'20181018130817','2',8),(199,'20181018130817','3',10),(200,'20181018130817','4',7),(201,'20181018130817','5',6),(202,'20181018131003','1',5),(203,'20181018131003','2',8),(204,'20181018131003','3',10),(205,'20181018131003','4',7),(206,'20181018131003','5',6),(207,'20181018131027','1',5),(208,'20181018131027','2',8),(209,'20181018131027','3',10),(210,'20181018131027','4',7),(211,'20181018131027','5',6),(212,'20181018131053','1',5),(213,'20181018131053','2',8),(214,'20181018131053','3',10),(215,'20181018131053','4',7),(216,'20181018131053','5',6),(217,'20181018131149','1',5),(218,'20181018131149','2',8),(219,'20181018131149','3',10),(220,'20181018131149','4',7),(221,'20181018131149','5',6),(222,'20181018131204','1',5),(223,'20181018131204','2',8),(224,'20181018131204','3',10),(225,'20181018131204','4',7),(226,'20181018131204','5',6),(227,'20181018131306','1',5),(228,'20181018131306','2',8),(229,'20181018131306','3',10),(230,'20181018131306','4',7),(231,'20181018131306','5',6),(232,'20181018132035','1',5),(233,'20181018132035','2',8),(234,'20181018132035','3',10),(235,'20181018132035','4',7),(236,'20181018132035','5',6),(289,'20181017215724','1',1),(290,'20181017215958','1',1),(291,'20181017220539','1',1),(292,'20181017220631','1',1),(293,'20181017230222','1',1),(294,'20181018130641','1',1),(295,'20181018130807','1',1),(296,'20181018130817','1',1),(297,'20181018131003','1',1),(298,'20181018131027','1',1),(299,'20181018131053','1',1),(300,'20181018131149','1',1),(301,'20181018131204','1',1),(302,'20181018131306','1',1),(303,'20181018132035','1',1),(304,'20181018132125','1',1),(305,'20181018132148','1',1),(306,'20181018132158','1',1),(307,'20181018132219','1',1),(308,'20181017215724','2',2),(309,'20181017215958','2',2),(310,'20181017220539','2',2),(311,'20181017220631','2',2),(312,'20181017230222','2',2),(313,'20181018130641','2',2),(314,'20181018130807','2',2),(315,'20181018130817','2',2),(316,'20181018131003','2',2),(317,'20181018131027','2',2),(318,'20181018131053','2',2),(319,'20181018131149','2',2),(320,'20181018131204','2',2),(321,'20181018131306','2',2),(322,'20181018132035','2',2),(323,'20181018132125','2',2),(324,'20181018132148','2',2),(325,'20181018132158','2',2),(326,'20181018132219','2',2),(352,'20181017215724','1',1),(353,'20181017215958','1',1),(354,'20181017220539','1',1),(355,'20181017220631','1',1),(356,'20181017230222','1',1),(357,'20181018130641','1',1),(358,'20181018130807','1',1),(359,'20181018130817','1',1),(360,'20181018131003','1',1),(361,'20181018131027','1',1),(362,'20181018131053','1',1),(363,'20181018131149','1',1),(364,'20181018131204','1',1),(365,'20181018131306','1',1),(366,'20181018132035','1',1),(367,'20181018132125','1',1),(368,'20181018132148','1',1),(369,'20181018132158','1',1),(370,'20181018132219','1',1),(371,'20181018132246','1',1),(372,'20181017215724','2',1),(373,'20181017215958','2',1),(374,'20181017220539','2',1),(375,'20181017220631','2',1),(376,'20181017230222','2',1),(377,'20181018130641','2',1),(378,'20181018130807','2',1),(379,'20181018130817','2',1),(380,'20181018131003','2',1),(381,'20181018131027','2',1),(382,'20181018131053','2',1),(383,'20181018131149','2',1),(384,'20181018131204','2',1),(385,'20181018131306','2',1),(386,'20181018132035','2',1),(387,'20181018132125','2',1),(388,'20181018132148','2',1),(389,'20181018132158','2',1),(390,'20181018132219','2',1),(391,'20181018132246','2',1),(392,'20181017215724','3',1),(393,'20181017215958','3',1),(394,'20181017220539','3',1),(395,'20181017220631','3',1),(396,'20181017230222','3',1),(397,'20181018130641','3',1),(398,'20181018130807','3',1),(399,'20181018130817','3',1),(400,'20181018131003','3',1),(401,'20181018131027','3',1),(402,'20181018131053','3',1),(403,'20181018131149','3',1),(404,'20181018131204','3',1),(405,'20181018131306','3',1),(406,'20181018132035','3',1),(407,'20181018132125','3',1),(408,'20181018132148','3',1),(409,'20181018132158','3',1),(410,'20181018132219','3',1),(411,'20181018132246','3',1),(415,'20181017215724','1',3),(416,'20181017215724','2',2),(417,'20181017215724','3',1),(418,'20181017215724','4',3),(419,'20181017215724','5',1),(420,'20181017215958','1',3),(421,'20181017215958','2',2),(422,'20181017215958','3',1),(423,'20181017215958','4',3),(424,'20181017215958','5',1),(425,'20181017220539','1',3),(426,'20181017220539','2',2),(427,'20181017220539','3',1),(428,'20181017220539','4',3),(429,'20181017220539','5',1),(430,'20181017220631','1',3),(431,'20181017220631','2',2),(432,'20181017220631','3',1),(433,'20181017220631','4',3),(434,'20181017220631','5',1),(435,'20181017230222','1',3),(436,'20181017230222','2',2),(437,'20181017230222','3',1),(438,'20181017230222','4',3),(439,'20181017230222','5',1),(440,'20181018130641','1',3),(441,'20181018130641','2',2),(442,'20181018130641','3',1),(443,'20181018130641','4',3),(444,'20181018130641','5',1),(445,'20181018130807','1',3),(446,'20181018130807','2',2),(447,'20181018130807','3',1),(448,'20181018130807','4',3),(449,'20181018130807','5',1),(450,'20181018130817','1',3),(451,'20181018130817','2',2),(452,'20181018130817','3',1),(453,'20181018130817','4',3),(454,'20181018130817','5',1),(455,'20181018131003','1',3),(456,'20181018131003','2',2),(457,'20181018131003','3',1),(458,'20181018131003','4',3),(459,'20181018131003','5',1),(460,'20181018131027','1',3),(461,'20181018131027','2',2),(462,'20181018131027','3',1),(463,'20181018131027','4',3),(464,'20181018131027','5',1),(465,'20181018131053','1',3),(466,'20181018131053','2',2),(467,'20181018131053','3',1),(468,'20181018131053','4',3),(469,'20181018131053','5',1),(470,'20181018131149','1',3),(471,'20181018131149','2',2),(472,'20181018131149','3',1),(473,'20181018131149','4',3),(474,'20181018131149','5',1),(475,'20181018131204','1',3),(476,'20181018131204','2',2),(477,'20181018131204','3',1),(478,'20181018131204','4',3),(479,'20181018131204','5',1),(480,'20181018131306','1',3),(481,'20181018131306','2',2),(482,'20181018131306','3',1),(483,'20181018131306','4',3),(484,'20181018131306','5',1),(485,'20181018132035','1',3),(486,'20181018132035','2',2),(487,'20181018132035','3',1),(488,'20181018132035','4',3),(489,'20181018132035','5',1),(490,'20181018132125','1',3),(491,'20181018132125','2',2),(492,'20181018132125','3',1),(493,'20181018132125','4',3),(494,'20181018132125','5',1),(495,'20181018132148','1',3),(496,'20181018132148','2',2),(497,'20181018132148','3',1),(498,'20181018132148','4',3),(499,'20181018132148','5',1),(500,'20181018132158','1',3),(501,'20181018132158','2',2),(502,'20181018132158','3',1),(503,'20181018132158','4',3),(504,'20181018132158','5',1),(505,'20181018132219','1',3),(506,'20181018132219','2',2),(507,'20181018132219','3',1),(508,'20181018132219','4',3),(509,'20181018132219','5',1),(510,'20181018132246','1',3),(511,'20181018132246','2',2),(512,'20181018132246','3',1),(513,'20181018132246','4',3),(514,'20181018132246','5',1),(515,'20181018132449','1',3),(516,'20181018132449','2',2),(517,'20181018132449','3',1),(518,'20181018132449','4',3),(519,'20181018132449','5',1),(520,'20181018223020','1',3),(521,'20181018223020','2',2),(522,'20181018223020','3',1),(523,'20181018223020','4',3),(524,'20181018223020','5',1),(542,'20181017215724','1',1),(543,'20181017215724','2',1),(544,'20181017215724','3',2),(545,'20181017215958','1',1),(546,'20181017215958','2',1),(547,'20181017215958','3',2),(548,'20181017220539','1',1),(549,'20181017220539','2',1),(550,'20181017220539','3',2),(551,'20181017220631','1',1),(552,'20181017220631','2',1),(553,'20181017220631','3',2),(554,'20181017230222','1',1),(555,'20181017230222','2',1),(556,'20181017230222','3',2),(557,'20181018130641','1',1),(558,'20181018130641','2',1),(559,'20181018130641','3',2),(560,'20181018130807','1',1),(561,'20181018130807','2',1),(562,'20181018130807','3',2),(563,'20181018130817','1',1),(564,'20181018130817','2',1),(565,'20181018130817','3',2),(566,'20181018131003','1',1),(567,'20181018131003','2',1),(568,'20181018131003','3',2),(569,'20181018131027','1',1),(570,'20181018131027','2',1),(571,'20181018131027','3',2),(572,'20181018131053','1',1),(573,'20181018131053','2',1),(574,'20181018131053','3',2),(575,'20181018131149','1',1),(576,'20181018131149','2',1),(577,'20181018131149','3',2),(578,'20181018131204','1',1),(579,'20181018131204','2',1),(580,'20181018131204','3',2),(581,'20181018131306','1',1),(582,'20181018131306','2',1),(583,'20181018131306','3',2),(584,'20181018132035','1',1),(585,'20181018132035','2',1),(586,'20181018132035','3',2),(587,'20181018132125','1',1),(588,'20181018132125','2',1),(589,'20181018132125','3',2),(590,'20181018132148','1',1),(591,'20181018132148','2',1),(592,'20181018132148','3',2),(593,'20181018132158','1',1),(594,'20181018132158','2',1),(595,'20181018132158','3',2),(596,'20181018132219','1',1),(597,'20181018132219','2',1),(598,'20181018132219','3',2),(599,'20181018132246','1',1),(600,'20181018132246','2',1),(601,'20181018132246','3',2),(602,'20181018132449','1',1),(603,'20181018132449','2',1),(604,'20181018132449','3',2),(605,'20181018223020','1',1),(606,'20181018223020','2',1),(607,'20181018223020','3',2),(608,'20181018224017','1',1),(609,'20181018224017','2',1),(610,'20181018224017','3',2);

/*Table structure for table `orderinfo` */

DROP TABLE IF EXISTS `orderinfo`;

CREATE TABLE `orderinfo` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `orderId` VARCHAR(15) DEFAULT NULL,
  `userId` VARCHAR(15) DEFAULT NULL,
  `receiverName` VARCHAR(10) DEFAULT NULL,
  `receiverAdd` VARCHAR(50) DEFAULT NULL,
  `receiverZip` VARCHAR(10) DEFAULT NULL,
  `orderMemo` VARCHAR(50) DEFAULT NULL,
  `orderPrice` FLOAT(6,2) DEFAULT NULL,
  `orderDate` DATE DEFAULT NULL,
  `isSale` INT(1) UNSIGNED ZEROFILL DEFAULT '0',
  UNIQUE KEY `id` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `orderinfo` */

INSERT  INTO `orderinfo`(`id`,`orderId`,`userId`,`receiverName`,`receiverAdd`,`receiverZip`,`orderMemo`,`orderPrice`,`orderDate`,`isSale`) VALUES (1,'20181017215724','0000001','陈增林','二师','430070','周末不发货',123.70,'2018-10-17',0),(2,'20181017215958','0000001','陈增林','二师','430070','周末不发货',123.70,'2018-10-17',0),(3,'20181017220539','0000001','陈增林','二师','430070','周末不发货',123.70,'2018-10-17',0),(4,'20181017220631','0000001','陈增林','二师','430070','周末不发货',123.70,'2018-10-17',0),(5,'20181017230222','0000001','czl','wu','441326','hjg',405.50,'2018-10-17',0),(6,'20181018130641','0000001','陈增林','二师','440000','无',105.00,'2018-10-18',0),(7,'20181018130807','0000001','陈增林','二师','440000','无',105.00,'2018-10-18',0),(8,'20181018130817','0000001','陈增林','二师','440000','无',105.00,'2018-10-18',0),(9,'20181018131003','0000001','陈增林','二师','440000','无',105.00,'2018-10-18',0),(10,'20181018131027','0000001','陈增林','二师','440000','无',105.00,'2018-10-18',0),(11,'20181018131053','0000001','陈增林','二师','440000','无',105.00,'2018-10-18',0),(12,'20181018131149','0000001','','','','',30.00,'2018-10-18',0),(13,'20181018131204','0000001','','','','',0.00,'2018-10-18',0),(14,'20181018131306','0000001','czl','','','',143.50,'2018-10-18',0),(15,'20181018132035','0000001','陈增林','湖北第二师范学院','000000','无',989.40,'2018-10-18',0),(16,'20181018132125','0000001','陈增林','湖北第二师范学院','000000','无',989.40,'2018-10-18',0),(17,'20181018132148','0000001','陈增林','湖北第二师范学院','000000','无',989.40,'2018-10-18',0),(18,'20181018132158','0000001','','','','',0.00,'2018-10-18',0),(19,'20181018132219','0000001','撒','的','','',68.50,'2018-10-18',0),(20,'20181018132246','0000001','','','','',98.50,'2018-10-18',0),(21,'20181018132449','0000001','陈增林','湖北第二师范学院','430060','顺丰',98.50,'2018-10-18',0),(22,'20181018223020','0000001','','','','',268.10,'2018-10-18',0),(23,'20181018224017','0000001','','','','',143.50,'2018-10-18',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */; 
