package com.ch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 2018-10-18
 * @author xiaolin
 *
 */
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
	
	public static int change(String userId,String bookId, int bookCount) {
		String sql ="update cartinfo set bookCount=? where userId=? and bookId=?";
		int i =0;
		try {
			Connection cnn = Bookjdbc_Util.getConnection();
			PreparedStatement ps=cnn.prepareStatement(sql);
			ps.setInt(1,bookCount);
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
