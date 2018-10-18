package com.ch.test;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import com.ch.Bookjdbc_Util;
/**
 * 2018-10-18
 * @author xiaolin
 *
 */
class Bookjdbc_Util_Test {

	@Test
	void testGetConnection() {
	
		Connection cnn = Bookjdbc_Util.getConnection();
		
		System.out.println(cnn);
	}

}
