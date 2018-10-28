package com.ch.test;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ch.OrderInfo;
import com.ch.OrderInfo_BookInfo_Orderdetail;
import com.ch.OrderInfo_Orderdetail_DAO;
/**
 * 2018-10-18
 * @author xiaolin
 *
 */
class OrderInfo_Orderdetail_DAO_Test {

	@Test
	void testCreateOrderId() {
		String s = OrderInfo_Orderdetail_DAO.createOrderId();
		System.out.println(s);
	}

	@Test
	void testInsertOrderInfo() {
		java.util.Date date =new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.format(date);
		Date sDate = new Date(date.getTime());
		df.format(sDate);
	    OrderInfo oi=new OrderInfo();
	    oi.setOrderId(OrderInfo_Orderdetail_DAO.createOrderId());
	    oi.setUserId("0000001");
	    oi.setIsSale(0);
	    oi.setOrderDate(sDate);
	    oi.setOrderMemo("周末不发货");
	    oi.setOrderprice(123.7f);
	    oi.setReceiverName("陈增林");
	    oi.setReceiverAdd("二师");
	    oi.setReceiverZip("430070");
		OrderInfo_Orderdetail_DAO.insertOrderInfo(oi);
	}

	@Test
	void testInsertOrderdetail() {
		OrderInfo_Orderdetail_DAO.insertOrderdetail();
	}

	@Test
	void testFind() {
		List<OrderInfo_BookInfo_Orderdetail> li = new ArrayList<>();
		li = OrderInfo_Orderdetail_DAO.find("20181017215724");
		for(OrderInfo_BookInfo_Orderdetail c:li) {
			
			System.out.println(c.getOi().getReceiverName());
			
		}
		
	}

}
