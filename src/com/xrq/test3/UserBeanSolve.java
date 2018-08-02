package com.xrq.test3;

import java.sql.*;
import java.util.ArrayList;

public class UserBeanSolve {
	
	private DBConnect dbc;
	private Connection conn;
	private Statement stat;
	private ResultSet rs;
	private ArrayList<UserBean> al =new ArrayList<>();
	private int count =0;
	
	
	/*
	 * 用户登陆校验，返回成功和失败的boolean值
	 */
	public boolean verify(String user,String password)
	{
		String dbPassword="";
		dbc = new DBConnect();
		conn = dbc.getConnect();
		
		try {
				stat = conn.createStatement();
				 rs = stat.executeQuery("SELECT * FROM manager WHERE user ="+"\""+ user +"\"");
				if(rs.next())
				{
					dbPassword =rs.getString(3);
					System.out.println("databse password: "+dbPassword);
				}else return false;
				if(dbPassword.equals(password))
					return true; 
				else return false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("verify program error!");
				return false;
			} finally {
				if (conn != null)
					{
					try {
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					conn=null;
					}
						}
		}
	
	
	/*
	 * 获取商品信息，以UserBean的方式放在ArrayList中
	 * 返回值为ArrayList
	 */
	public ArrayList<UserBean> getProduct(int PageNow,int PageRow)
	{
		al.clear(); 
		dbc = new DBConnect();
		conn = dbc.getConnect();
		try {
			System.out.println("Inital count: "+count);
			stat = conn.createStatement();
			rs = stat.executeQuery("SELECT * FROM product LIMIT "+(PageNow-1)*PageRow+","+PageRow*PageNow);
			while(rs.next())
			{
				UserBean usb = new UserBean();
				usb.setID(rs.getString(1));
				usb.setproductName(rs.getString(2));
				usb.setPrice(rs.getString(3));
				usb.setNumber(rs.getString(4));
				al.add(usb);
				count++;
				if(count == PageRow) break;
			}
			//以下两条为调试之用
			System.out.println("al Size: "+al.size()+" count "+count);
			System.out.println("sqlString: "+"SELECT * FROM product LIMIT "+(PageNow-1)*PageRow+","+PageRow);
			count=0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			try {
				rs.close();
				stat.close();
				conn.close();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 return al;
	}
	
	
	
	/*
	 * 获取数据库商品总记录数，用于后面分页使用
	 */
	public int getRowCount()
	{
		int RowCount=0;
		dbc = new DBConnect();
		conn = dbc.getConnect();
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery("select count(1) from product");
			if(rs.next())
			{
				RowCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 return RowCount;
	}
	
/*
 * 修改商品数据
 */
	public void modify(UserBean b1)
	{	
			dbc = new DBConnect();
			conn = dbc.getConnect();
			String MID = b1.getID();
			String MPrice = b1.getPrice();
			String MNumber = b1.getNumber();
			String MName = b1.getProductName();
			try {
				stat = conn.createStatement();
				stat.executeUpdate("UPDATE product SET price='"+MPrice+"',number='"+MNumber+ "' ,name='"+MName+ "' WHERE id= '"+MID+"'");
				System.out.println("UPDATE product SET price='"+MPrice+"',number='"+MNumber+  "' ,name='"+MName+ "' WHERE id= '"+MID+"'");
				//以下两条为调试之用
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally
			{
				try {
					stat.close();
					conn.close();
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
	}
	
	/*
	 * 删除商品数据
	 */
		public void delete(UserBean b1)
		{	
				dbc = new DBConnect();
				conn = dbc.getConnect();
				String MID = b1.getID();
				try {
					stat = conn.createStatement();
					stat.executeUpdate("DELETE FROM product WHERE id= '"+MID+"'");
					//以下两条为调试之用
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally
				{
					try {
						stat.close();
						conn.close();
					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
		}
	
}
