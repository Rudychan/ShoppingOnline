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
		String [] dbData = new String[2];  //���ڴ�Ŵ����ݿ��ȡ��ֵ
		String dbPassword =null;
		String inputName = request.getParameter("userName");
		String inputPassword =  request.getParameter("userPassword");
		String str = "SELECT * FROM manager WHERE user ="+"\""+ inputName +"\"";  //��������û��������ݿ����Ƿ����
		/*
		 * �˴�ʹ��sessionУ���ֹԽ����½
		 * Ҳ��Ϊ������ƣ�30s�޲���Ҫ���µ�½
		 */
		HttpSession hs = request.getSession(true);
		hs.setMaxInactiveInterval(30); //session��Ч��Ϊ30��
		hs.setAttribute("name", inputName);
		//ִ��sql��ѯ
		SqlConnection conn1 = new SqlConnection();
		dbData  = conn1.TheSqlConnection(str);  //�������ݿ��ѯ���
		dbPassword = dbData[1];  //���ҵڶ����ֶΣ�������
		if(inputPassword.equals(dbPassword))
		{
			//��֤ͨ��
			System.out.println("У��ɹ���");
			/*RequestDispatcher view = request.getRequestDispatcher("LoginResult.jsp");
			view.forward(request, response);*/
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
