/*
 * ���ݿ����ӳ��򣬷������ݿ�����
 */
package com.xrq.test3;

import java.sql.*;

public class DBConnect {
	
	 	private static final String URL="jdbc:mysql://localhost:3306/testdb";//���ݿ������ַ����������deomΪ���ݿ���
	    private static final String NAME="root";//��¼��
	    private static final String PASSWORD="123456";//����
	
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
			System.out.println("���ݿ����ӳɹ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}
