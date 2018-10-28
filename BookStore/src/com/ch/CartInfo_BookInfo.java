package com.ch;

/**
 * 2018-10-18
 * @author xiaolin
 *
 */
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



	public CartInfo_BookInfo(CartInfo ci, BookInfo bi) {
		this.ci = ci;
		this.bi = bi;
	}



	public CartInfo_BookInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	
	
}
