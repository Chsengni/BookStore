package com.ch.test;

import org.junit.jupiter.api.Test;

import com.ch.BuyerInfo_DAO;
/**
 * 2018-10-18
 * @author xiaolin
 *
 */
class BuyerInfo_DAO_Test {

	@Test
	void testIsMember() {
		String ID ="0000001";
		String pwd = "123";
	    boolean r=BuyerInfo_DAO.isMember(ID, pwd);
	    System.out.println(r);
	}

}
