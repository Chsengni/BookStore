package com.ch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * 2018-10-18
 * @author xiaolin
 *
 */
public class Bookjdbc_Util {
		
	public static Connection getConnection() {
		
		Connection cnn =null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			String url="jdbc:mysql://localhost:3306/bookstore?"+"useUnicode=true&characterEncoding=UTF-8"; 
			String user="root"; //填上你自己数据库的账号
			String password="root";// 密码
			cnn =DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return cnn;
	}

}
