package com.xrq.test3;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class test3
 */
@WebServlet("/test3")
public class test3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test3() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int PageNow =1;  //��ǰҳ��
		int PageRow =5;  //ÿҳ��ʾ5��
		int PageCount=1;  //��ҳ��
		String SPageNow = request.getParameter("PageNow"); //����jsp�ĳ���������
		if(SPageNow!=null)
		{
			PageNow = Integer.parseInt(SPageNow);
		}
		String [] dbData = new String[3];  //���ڴ�Ŵ����ݿ��ȡ���˺�����ֵ,����Ϊ�ֶ���
		String [][] productData = new String[PageRow][4]; //���ڴ�Ŵ����ݿ��ȡ����Ʒ����
		String dbPassword =null;
		String inputName = request.getParameter("userName");
		String inputPassword =  request.getParameter("userPassword");
		String strLogin = "SELECT * FROM manager WHERE user ="+"\""+ inputName +"\"";  //��������û��������ݿ����Ƿ����
		String strProduct = "SELECT * FROM product LIMIT "+(PageNow-1)*PageRow+","+PageRow*PageNow;
		String strRowCount = "select count(1) from product";
		/*
		 * �˴�ʹ��sessionУ���ֹԽ����½
		 * Ҳ��Ϊ������ƣ�30s�޲���Ҫ���µ�½
		 */
		HttpSession hs = request.getSession();
		hs.setMaxInactiveInterval(30); //session��Ч��Ϊ30��
	
		//ִ��sql��ѯ
		SqlConnection conn1 = new SqlConnection();
		conn1.SetURL("jdbc:mysql://localhost:3306/testdb");
		dbData  = conn1.TheSqlConnection(1,3,strLogin)[0];  //�����˻����ݿ��ѯ���
		dbPassword = dbData[2];  //���ҵڶ����ֶΣ�������
	/*	String passKey = (String)hs.getAttribute("pass");*/
		if(inputPassword==null)
		{
			inputPassword=""; 
	/*		passKey=""; */
		}
		if(inputPassword.equals(dbPassword) || SPageNow!=null) 
		{
			//��֤ͨ��
			System.out.println("У��ɹ���");
			int RowCount = Integer.parseInt(conn1.TheSqlConnection(1,1, strRowCount)[0][0]);//������ݿ��ܼ�¼��
			if(RowCount%PageRow ==0)   //�ж��ܹ���ʾ����ҳ
					{
						PageCount = RowCount/PageRow;
					}else 
					{	PageCount =RowCount/PageRow+1;
					} 
			
			productData = (String[][])conn1.TheSqlConnection(PageRow,4,strProduct);
			hs.setAttribute("productData", productData); //���ݿ��ѯ��Ʒ���ݣ���ά����
			hs.setAttribute("PageRow",PageRow);  //ÿ����ʾҳ�������ݸ�JSP
			hs.setAttribute("PageNow", PageNow);
			hs.setAttribute("PageCount", PageCount);
			if(inputName==null) inputName = (String)hs.getAttribute("name");
			hs.setAttribute("name", inputName);  
			response.sendRedirect("LoginResult.jsp");
		}
		else {
			System.out.println("У��ʧ��");
			response.sendRedirect("ErrorPage.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
