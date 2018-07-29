/*
 * 数据库连接程序，返回数据库连接
 */
package com.xrq.test3;

import java.sql.*;

public class DBConnect {
	
	 	private static final String URL="jdbc:mysql://localhost:3306/testdb";//数据库连接字符串，这里的deom为数据库名
	    private static final String NAME="root";//登录名
	    private static final String PASSWORD="123456";//密码
	
	public Connection getConnect()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = null;
		try {
			conn =  DriverManager.getConnection(URL, NAME, PASSWORD);
			System.out.println("数据库连接成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}
