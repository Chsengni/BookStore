package com.ch.test;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.ch.CartInfo_BookInfo;
import com.ch.CartInfo_DAO;
/**
 * 2018-10-18
 * @author xiaolin
 *
 */
class CartInfo_DAO_Test {

	@Test
	void testAddcart() {
		String userId ="0000002";
		String bookId ="1";
		CartInfo_DAO.addcart(userId, bookId);
	    List<CartInfo_BookInfo> li = CartInfo_DAO.find("0000002");
		for(CartInfo_BookInfo cb:li) {
			System.out.println("0000002 顾客 购物车有"+cb.getCi().getBookCount()+"本《"+cb.getBi().getBookName()+"》");
		}
	}

	@Test
	void testFind() {
		List<CartInfo_BookInfo> li = CartInfo_DAO.find("0000002");
		for(CartInfo_BookInfo cb:li) {
			System.out.println("0000002 顾客  购物车有"+cb.getCi().getBookCount()+"本《"+cb.getBi().getBookName()+"》");
		}
	}

	@Test
	void testDel() {
		//根据bookId删除书籍
		CartInfo_DAO.del("4");
		List<CartInfo_BookInfo> li = CartInfo_DAO.find("0000001");
		for(CartInfo_BookInfo cb:li) {
			System.out.println("0000001 顾客  购物车有"+cb.getCi().getBookCount()+"本《"+cb.getBi().getBookName()+"》");
		}
	}

	@Test
	void testEmpty() {
		//清空购物车
		CartInfo_DAO.empty("0000003");
	}

	@Test
	void testChange() {
		//更改购书的数量
		CartInfo_DAO.change("0000001", "3", 4);
	}

}
