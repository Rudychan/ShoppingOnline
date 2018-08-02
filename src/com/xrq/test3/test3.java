package com.xrq.test3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletContext;
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
	private String inputName =null;
	private String inputPassword =null;
	private ArrayList<UserBean> array = new ArrayList<UserBean>(); //���ڴ����Ʒ��Bean
	private UserBean productBean = new UserBean();  //��Ʒ��Bean
	private String [][] productData = new String[PageRow][4]; //���ڴ�Ŵ����ݿ��ȡ����Ʒ����
    private int VisitCount=0;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public test3() {
        super();
        // TODO Auto-generated constructor stub
    }

    //�ڳ�ʼ��ʱ��ȡ��վ�������ı��ļ�������ʱд���ļ�
    //����ʹ��ServletContext���д����ļ�¼
	@Override
	public void init()
	{
		try {
			FileReader fw = new FileReader("E:\\eclipse_javaee_workspace\\test3\\WebContent\\VisitCounter.txt");
			BufferedReader fb = new BufferedReader(fw);
			try {
					VisitCount = Integer.parseInt(fb.readLine().toString());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 
				}finally{
							try {
								fb.close();
								fw.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
		} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("�Ҳ��������ļ�");
		}
	}
	
	/*
	 * Servlet���ٵ�ʱ���servletContxt�ķ��ʼ�����д���ļ���ȥ
	 */
	@Override
	public void destroy() 
	{
				FileWriter fw1 =null; 
				BufferedWriter fb1=null;
			try {
					 fw1 = new FileWriter("E:\\eclipse_javaee_workspace\\test3\\WebContent\\VisitCounter.txt");
					 fb1 = new BufferedWriter(fw1);
						fb1.write(VisitCount+"");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block 
					e.printStackTrace();
				}finally{
							try {
								fb1.close();
								fw1.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
	}
	
	
	//��������������
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
			SPageNow=null;
		}
		
		//�޸���Ʒ����
		UserBean b1= new UserBean();
		String modifyID = (String)request.getParameter("ModifyID");
		String modifyName = (String)request.getParameter("ModifyName");
		String modifyPrice = (String)request.getParameter("ModifyPrice");
		String modifyNum = (String)request.getParameter("ModifyNum");
		if(modifyName!=null)
		{
			b1.setID(modifyID);
			b1.setNumber(modifyNum);
			b1.setPrice(modifyPrice);
			b1.setProductName(modifyName);
			new UserBeanSolve().modify(b1);
			System.out.print("�������޸�");
		}
		

		
		//ɾ����Ʒ����
		String deleteItem = (String)request.getParameter("delID");
		if (deleteItem!=null)
		{
			UserBean u2= new UserBean();
			UserBeanSolve ub2 = new UserBeanSolve();
			u2.setID(deleteItem);
			ub2.delete(u2); 
		}
		
		
		inputName = request.getParameter("userName");  //��ȡ������û���
		inputPassword =  request.getParameter("userPassword"); //��ȡ���������
		if(inputName!=null)
		{
			 VisitCount++; 
		}
		ServletContext ctx = this.getServletContext();
		ctx.setAttribute("VisitCount", VisitCount);
		//������Ϣ��ȡ�Լ�Cookie��ȡ
		Cookie k1[] = request.getCookies();
			//��ȡcookie������˺�����
		if(k1!=null)
			for(Cookie i:k1)
			{
				//�˳���½�ж�
				String logout = request.getParameter("Logout"); 
				System.out.println("logout: "+logout);
				if( logout!=null)
				{
					i.setMaxAge(0);
					i.setPath("/test3");
					response.addCookie(i);
				}
				if(i.getName().equals("name")) inputName = i.getValue();
				if(i.getName().equals("password")) inputPassword = i.getValue();
				System.out.println("ReadingCookie "+inputName+ " pass "+inputPassword);
			}
			 
		/*
		 * ������ƣ�30s�޲���Ҫ���µ�½
		 */
		HttpSession hs = request.getSession();
		hs.setMaxInactiveInterval(30); //session��Ч��Ϊ30��
		if(inputName==null) inputName = (String)hs.getAttribute("name"); //�����ӻ�����ʱ��ÿ���û������������
		if(inputPassword==null) inputPassword = (String)hs.getAttribute("password"); //��JSP��servlet��У��
		
		//У��
		System.out.println("name: "+inputName +"Spage: "+SPageNow);
		if(ubs.verify(inputName, inputPassword))  
		{
				System.out.println("У��ɹ���"); 	
				// ʹ��cookie���û��������뱣�浽�ͻ��ˣ�ʱ��Ϊһ��
				String checkBox = request.getParameter("keep");
				if(checkBox !=null)
				{
					Cookie ck1 = new Cookie("name",inputName);
					Cookie ck2 = new Cookie("password",inputPassword);
					ck1.setPath("/test3");
					ck2.setPath("/test3");
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
				
					productBean = (UserBean)array.get(i);
					productData[i][1]=productBean.getProductName();
					productData[i][2]=productBean.getPrice();
					productData[i][3]=productBean.getNumber();
					productData[i][0]=productBean.getID();
					/*System.out.println("name "+productData[i][0] +" price "+productData[i][1]+" num: "+productData[i][2]+" i "+i);*/
			}
			hs.setAttribute("productData", productData); //���ݿ��ѯ��Ʒ���ݣ���ά����	
			hs.setAttribute("PageRow",PageRow);  //ÿ����ʾҳ�������ݸ�JSP
			hs.setAttribute("PageNow", PageNow);
			hs.setAttribute("PageCount", PageCount); 
			hs.setAttribute("name", inputName);   //����jsp��У�飬��Խ����½
			hs.setAttribute("password", inputPassword);   //����jsp��ת��servletʱ��У��
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
