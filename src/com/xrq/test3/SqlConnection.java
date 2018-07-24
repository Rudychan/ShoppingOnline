package com.xrq.test3;

/*
 * SQL连接程序
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class SqlConnection {

			/*
			*java连接mysql数据库
			*1、加载驱动程序
			*2、数据库连接字符串"jdbc:mysql://localhost:3306/数据库名?"
			*3、数据库登录名
			*3、数据库登录密码
			*/
	    private static final String URL="jdbc:mysql://localhost:3306/testdb";//数据库连接字符串，这里的deom为数据库名
	    private static final String NAME="root";//登录名
	    private static final String PASSWORD="123456";//密码
	    
	    /*
	     * 连接并且查询数据库
	     */
	    
		public String[] TheSqlConnection(String sqlCommand)
		{
			String [] parm= new String[2];
			
	        //1.加载驱动
	        try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("未能成功加载驱动程序，请检查是否导入驱动程序！");
	                        //添加一个println，如果加载驱动异常，检查是否添加驱动，或者添加驱动字符串是否错误
				e.printStackTrace();
			}
			Connection conn = null;
			try {
				conn =  DriverManager.getConnection(URL, NAME, PASSWORD);
				 System.out.println("获取数据库连接成功！");
			        Statement stmt = conn.createStatement(); //创建Statement对象
			        ResultSet rs = stmt.executeQuery(sqlCommand);
			        while(rs.next())
			        {
			        	
			        	parm[0]=rs.getString(2);
			        	parm[1] = rs.getString(3);
		
			        }
			           
			} catch (SQLException e) {
				System.out.println("获取数据库连接失败！");
	                        //添加一个println，如果连接失败，检查连接字符串或者登录名以及密码是否错误
				e.printStackTrace();
			}
	               //数据库打开后就要关闭
			if(conn!=null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					conn=null;
				}
					 	
			}
			return parm;
			
		}

}
