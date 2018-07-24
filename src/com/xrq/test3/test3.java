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
		String [] dbData = new String[2];  //用于存放从数据库获取的值
		String dbPassword =null;
		String inputName = request.getParameter("userName");
		String inputPassword =  request.getParameter("userPassword");
		String str = "SELECT * FROM manager WHERE user ="+"\""+ inputName +"\"";  //检查输入用户名在数据库中是否存在
		/*
		 * 此处使用session校验防止越过登陆
		 * 也作为防呆设计，30s无操作要重新登陆
		 */
		HttpSession hs = request.getSession(true);
		hs.setMaxInactiveInterval(30); //session有效期为30秒
		hs.setAttribute("name", inputName);
		//执行sql查询
		SqlConnection conn1 = new SqlConnection();
		dbData  = conn1.TheSqlConnection(str);  //返回数据库查询结果
		dbPassword = dbData[1];  //查找第二个字段，即密码
		if(inputPassword.equals(dbPassword))
		{
			//验证通过
			System.out.println("校验成功！");
			/*RequestDispatcher view = request.getRequestDispatcher("LoginResult.jsp");
			view.forward(request, response);*/
			response.sendRedirect("LoginResult.jsp");
		}
		else {
			System.out.println("校验失败");
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
