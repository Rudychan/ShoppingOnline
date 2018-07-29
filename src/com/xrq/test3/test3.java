package com.xrq.test3;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
       
	private int PageNow =1;  //��ǰҳ��
	private int PageRow =5;  //ÿҳ��ʾ5��
	private int PageCount=1;  //��ҳ��
	private UserBeanSolve ubs= new UserBeanSolve();  //���ݿ⴦��ʵ��
	private int RowCount = ubs.getRowCount();//������ݿ��ܼ�¼��
	private String inputName ="";
	private String inputPassword ="";
	private ArrayList<UserBean> array = new ArrayList<UserBean>(); //���ڴ����Ʒ��Bean
	private UserBean productBean = new UserBean();  //��Ʒ��Bean
	private String [][] productData = new String[PageRow][4]; //���ڴ�Ŵ����ݿ��ȡ����Ʒ����
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
		
		/*
		 * ��ȡ���������Ҫ������
		 * RowCount -- �ܼ�¼����
		 * PageRow -- ÿҳ��ʾ����
		 * PageCount -- ��ҳ�������ڣ�������/ÿҳ������		
		 */
	
		if(RowCount%PageRow ==0)   //�ж��ܹ���ʾ����ҳ
				{
					PageCount = RowCount/PageRow;
				}else 
				{	PageCount =RowCount/PageRow+1;
				} 
		String SPageNow = request.getParameter("PageNow"); //����jsp�ĳ�������������Ҫ��ʾ��ҳ��
		if(SPageNow!=null)
		{
			PageNow = Integer.parseInt(SPageNow);
		}
		
		
		
		//������Ϣ��ȡ�Լ�Cookie��ȡ
		Cookie k1[] = request.getCookies();
		if(k1 != null)
		{
			for(Cookie i:k1)
			{
				if(i.getName().equals("name")) inputName = i.getValue();
				if(i.getName().equals("password")) inputPassword = i.getValue();
				System.out.println("ReadingCookie "+inputName+ " pass "+inputPassword);
			}
		}
		 if(inputName.equals("")|| inputPassword.equals("")) {
				inputName = request.getParameter("userName");  //��ȡ������û���
				inputPassword =  request.getParameter("userPassword"); //��ȡ���������
				System.out.println("There is no cookie"); 
		}
		
		/*
		 * ������ƣ�30s�޲���Ҫ���µ�½
		 */
		HttpSession hs = request.getSession();
		hs.setMaxInactiveInterval(30); //session��Ч��Ϊ30��
	
		
		//У��
		if(ubs.verify(inputName, inputPassword)|| SPageNow!=null)  
		{
				System.out.println("У��ɹ���");
				
				// ʹ��cookie���û��������뱣�浽�ͻ��ˣ�ʱ��Ϊһ��
				String checkBox = request.getParameter("keep");
				if(checkBox !=null)
				{
					Cookie ck1 = new Cookie("name",inputName);
					Cookie ck2 = new Cookie("password",inputPassword);
					ck1.setMaxAge(7*24*3600);
					ck2.setMaxAge(7*24*3600);
					response.addCookie(ck1);
					response.addCookie(ck2);
					System.out.print("Cookie Written!");
					checkBox=null; 
				}
		
			
			/*
			 * ��ArrayList��ȡ��UserBean������Ʒ��Ϣ
			 */
			
			array= ubs.getProduct(PageNow, PageRow); //ÿ�θ��µ�ǰҳ��
			for(int i =0 ;i<PageRow;i++) 
			{
				System.out.println("Size: "+array.size());
					productBean = (UserBean)array.get(i);
					productData[i][0]=productBean.getProductName();
					productData[i][1]=productBean.getPrice();
					productData[i][2]=productBean.getNumber();
					System.out.println("name "+productData[i][0] +" price "+productData[i][1]+" num: "+productData[i][2]+" i "+i);
			}
			hs.setAttribute("productData", productData); //���ݿ��ѯ��Ʒ���ݣ���ά����	
			hs.setAttribute("PageRow",PageRow);  //ÿ����ʾҳ�������ݸ�JSP
			hs.setAttribute("PageNow", PageNow);
			hs.setAttribute("PageCount", PageCount); 
			
			//
			if(inputName==null) inputName = (String)hs.getAttribute("name"); //�����ӻ�����ʱ��ÿ���û������������
			hs.setAttribute("name", inputName);   //����jsp��У�飬��Խ����½
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
