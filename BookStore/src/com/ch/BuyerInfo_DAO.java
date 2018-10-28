package com.ch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 2018-10-18
 * @author xiaolin
 *
 */
public class BuyerInfo_DAO {

	/**
	 * 创建一个会员对象
	 */
	public static BuyerInfo buyer = new BuyerInfo();
	
	/**
	 * 判断是否为成员
	 * @param ID
	 * @param pwd
	 * @return
	 */
	public static boolean isMember(String ID,String pwd) {
		//记录是否为会员
		boolean r=false;
		/**
		 * 查询用户
		 */
		String sql = "select * from buyerinfo where memberID=? and pwd=?";
		
		
		try {
			/**
			 * 调用Bookjdbc_Util的getConnection方法
			 * 返回cnn 对象
			 */
			Connection cnn = Bookjdbc_Util.getConnection();
			//创建查询
			PreparedStatement ps= cnn.prepareStatement(sql);
			ps.setString(1,ID);
			ps.setString(2, pwd);
			
			//执行查询并返回结果
			ResultSet rs= ps.executeQuery();
			
			//利用迭代
			if (rs.next()) {
				//有返回结果，说明为成员
				r=true;
				
				buyer.setAddress(rs.getString("address"));
				buyer.setEmail(rs.getString("email"));
				buyer.setId(rs.getInt("Id"));
				buyer.setLoginTimes(rs.getInt("loginTimes"));
				buyer.setMemberID(ID);
				buyer.setMembername(rs.getString("membername"));
				buyer.setPhoneCode(rs.getString("phoneCode"));
				buyer.setPwd(pwd);
				buyer.setZipcode(rs.getString("zipcode"));
				sql = "update buyerInfo set loginTimes = loginTimes+1 where memberID=?";
				ps = cnn.prepareStatement(sql);
				ps.setString(1, ID);
				ps.executeUpdate();
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
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}
	
	//public static void main(String[] args) {
		
	//	isMember("0000001", "123");
	//	System.out.println(buyer.getMembername());
	//}
	
	
}
