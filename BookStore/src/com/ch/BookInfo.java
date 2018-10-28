package com.ch;
/**
 * 2018-10-18
 * @author xiaolin
 *
 */
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
