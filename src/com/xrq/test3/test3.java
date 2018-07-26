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
		int PageNow =1;  //当前页数
		int PageRow =5;  //每页显示5行
		int PageCount=1;  //总页数
		String SPageNow = request.getParameter("PageNow"); //接受jsp的超链接请求
		if(SPageNow!=null)
		{
			PageNow = Integer.parseInt(SPageNow);
		}
		String [] dbData = new String[3];  //用于存放从数据库获取的账号密码值,长度为字段数
		String [][] productData = new String[PageRow][4]; //用于存放从数据库获取的商品数据
		String dbPassword =null;
		String inputName = request.getParameter("userName");
		String inputPassword =  request.getParameter("userPassword");
		String strLogin = "SELECT * FROM manager WHERE user ="+"\""+ inputName +"\"";  //检查输入用户名在数据库中是否存在
		String strProduct = "SELECT * FROM product LIMIT "+(PageNow-1)*PageRow+","+PageRow*PageNow;
		String strRowCount = "select count(1) from product";
		/*
		 * 此处使用session校验防止越过登陆
		 * 也作为防呆设计，30s无操作要重新登陆
		 */
		HttpSession hs = request.getSession();
		hs.setMaxInactiveInterval(30); //session有效期为30秒
	
		//执行sql查询
		SqlConnection conn1 = new SqlConnection();
		conn1.SetURL("jdbc:mysql://localhost:3306/testdb");
		dbData  = conn1.TheSqlConnection(1,3,strLogin)[0];  //返回账户数据库查询结果
		dbPassword = dbData[2];  //查找第二个字段，即密码
	/*	String passKey = (String)hs.getAttribute("pass");*/
		if(inputPassword==null)
		{
			inputPassword=""; 
	/*		passKey=""; */
		}
		if(inputPassword.equals(dbPassword) || SPageNow!=null) 
		{
			//验证通过
			System.out.println("校验成功！");
			int RowCount = Integer.parseInt(conn1.TheSqlConnection(1,1, strRowCount)[0][0]);//获得数据库总记录数
			if(RowCount%PageRow ==0)   //判断总共显示多少页
					{
						PageCount = RowCount/PageRow;
					}else 
					{	PageCount =RowCount/PageRow+1;
					} 
			
			productData = (String[][])conn1.TheSqlConnection(PageRow,4,strProduct);
			hs.setAttribute("productData", productData); //数据库查询商品数据，二维数组
			hs.setAttribute("PageRow",PageRow);  //每行显示页数，传递给JSP
			hs.setAttribute("PageNow", PageNow);
			hs.setAttribute("PageCount", PageCount);
			if(inputName==null) inputName = (String)hs.getAttribute("name");
			hs.setAttribute("name", inputName);  
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
