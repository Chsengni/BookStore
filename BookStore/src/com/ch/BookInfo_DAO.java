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
		String sql= "select * from bookinfo limit ?,?";
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
	
	public static BookInfo findId(int Id) {
		String sql = "select * from bookinfo where Id=?";
		BookInfo bi = new BookInfo();
		try {
			Connection cnn =Bookjdbc_Util.getConnection();
			PreparedStatement ps = cnn.prepareStatement(sql);
			ps.setInt(1, Id);
			ResultSet rs;
			rs = ps.executeQuery();
		    while(rs.next()) {
				bi.setBookAuthor(rs.getString("bookAuthor"));
				bi.setBookISBN(rs.getString("bookISBN"));
				bi.setBookName(rs.getString("bookName"));
				bi.setId(rs.getInt("Id"));
				bi.setIntroduce(rs.getString("introduce"));
				bi.setPrice(rs.getFloat("price"));
				bi.setPublisher(rs.getString("publisher"));
				
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
		
		return bi;
	}
	
	
}
