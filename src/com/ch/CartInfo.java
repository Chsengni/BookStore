package com.ch;

/**
 * 2018-10-18
 * @author xiaolin
 *
 */
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
