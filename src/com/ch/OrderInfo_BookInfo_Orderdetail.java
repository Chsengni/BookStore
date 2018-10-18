package com.ch;
/**
 * 2018-10-18
 * @author xiaolin
 *
 */
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
