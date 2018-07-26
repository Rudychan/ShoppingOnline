package com.xrq.test3;

/*
 * SQL���ӳ���
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class SqlConnection {

			/*
			*java����mysql���ݿ�
			*1��������������
			*2�����ݿ������ַ���"jdbc:mysql://localhost:3306/���ݿ���?"
			*3�����ݿ��¼��
			*3�����ݿ��¼����
			*/
	    private  String URL="jdbc:mysql://localhost:3306/testdb";//���ݿ������ַ����������deomΪ���ݿ���
	    private static final String NAME="root";//��¼��
	    private static final String PASSWORD="123456";//����
	    
	    /*
	     * ���Ӳ��Ҳ�ѯ���ݿ�
	     * ����num����ѯ��Ŀ��������Ĭ�϶���String
	     * ����sqlCommand��Ҫִ�е�sql�����ʱֻд�˲�ѯ������ɾ������д
	     */
	    public void SetURL(String URL)
	    {
	    	this.URL = URL;
	    }
		public String[][] TheSqlConnection(int row,int column, String sqlCommand)
		{
			String [][] parm= new String[row][column];
			
	        //1.��������
	        try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("δ�ܳɹ������������������Ƿ�����������");
	                        //���һ��println��������������쳣������Ƿ����������������������ַ����Ƿ����
				e.printStackTrace();
			}
			Connection conn = null;
			try {
				conn =  DriverManager.getConnection(URL, NAME, PASSWORD);
				 System.out.println("��ȡ���ݿ����ӳɹ���");
			        Statement stmt = conn.createStatement(); //����Statement����
			        ResultSet rs = stmt.executeQuery(sqlCommand);
			        int j=0;
			        while(rs.next())
			        {
			        	for(int i=0;i<column;i++)			   
			        	{
			        		parm[j][i]=rs.getString(i+1);
			        	}
			     		j++;
			     		if(j>=row) break;
			        }
			        j=0;
			       
			} catch (SQLException e) {
				System.out.println("��ȡ���ݿ�����ʧ�ܣ�");
	                        //���һ��println���������ʧ�ܣ���������ַ������ߵ�¼���Լ������Ƿ����
				e.printStackTrace();
			}
	               //���ݿ�򿪺��Ҫ�ر�
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
