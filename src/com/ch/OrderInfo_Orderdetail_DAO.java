package com.ch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 2018-10-18
 * @author xiaolin
 *
 */
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
