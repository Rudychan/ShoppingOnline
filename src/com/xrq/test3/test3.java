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
	private int PageNow =1;  //当前页数
	private int PageRow =5;  //每页显示5行
	private int PageCount=1;  //总页数
	private UserBeanSolve ubs= new UserBeanSolve();  //数据库处理实例
	private int RowCount = ubs.getRowCount();//获得数据库总记录数
	private String inputName =null;
	private String inputPassword =null;
	private ArrayList<UserBean> array = new ArrayList<UserBean>(); //用于存放商品的Bean
	private UserBean productBean = new UserBean();  //商品的Bean
	private String [][] productData = new String[PageRow][4]; //用于存放从数据库获取的商品数据
    private int VisitCount=0;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public test3() {
        super();
        // TODO Auto-generated constructor stub
    }

    //在初始化时读取网站计数的文本文件，销毁时写入文件
    //过程使用ServletContext进行次数的记录
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
				System.out.println("找不到计数文件");
		}
	}
	
	/*
	 * Servlet销毁的时候把servletContxt的访问计数回写到文件中去
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
	
	
	//以下是请求处理部分
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	

		/*
		 * 获取构建表格需要的数据
		 * RowCount -- 总记录条数
		 * PageRow -- 每页显示条数
		 * PageCount -- 总页数，等于（总条数/每页条数）		
		 */
		if(RowCount%PageRow ==0)   //判断总共显示多少页
				{
					PageCount = RowCount/PageRow;
				}else 
				{	PageCount =RowCount/PageRow+1;
				} 
		String SPageNow = request.getParameter("PageNow"); //接受jsp的超链接请求，设置要显示的页码
		if(SPageNow!=null)
		{
			PageNow = Integer.parseInt(SPageNow);
			SPageNow=null;
		}
		
		//修改商品数据
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
			System.out.print("数据已修改");
		}
		

		
		//删除商品数据
		String deleteItem = (String)request.getParameter("delID");
		if (deleteItem!=null)
		{
			UserBean u2= new UserBean();
			UserBeanSolve ub2 = new UserBeanSolve();
			u2.setID(deleteItem);
			ub2.delete(u2); 
		}
		
		
		inputName = request.getParameter("userName");  //获取输入的用户名
		inputPassword =  request.getParameter("userPassword"); //获取输入的密码
		if(inputName!=null)
		{
			 VisitCount++; 
		}
		ServletContext ctx = this.getServletContext();
		ctx.setAttribute("VisitCount", VisitCount);
		//输入信息获取以及Cookie读取
		Cookie k1[] = request.getCookies();
			//读取cookie里面的账号密码
		if(k1!=null)
			for(Cookie i:k1)
			{
				//退出登陆判断
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
		 * 防呆设计，30s无操作要重新登陆
		 */
		HttpSession hs = request.getSession();
		hs.setMaxInactiveInterval(30); //session有效期为30秒
		if(inputName==null) inputName = (String)hs.getAttribute("name"); //超链接回跳的时候每有用户名和密码参数
		if(inputPassword==null) inputPassword = (String)hs.getAttribute("password"); //从JSP到servlet免校验
		
		//校验
		System.out.println("name: "+inputName +"Spage: "+SPageNow);
		if(ubs.verify(inputName, inputPassword))  
		{
				System.out.println("校验成功！"); 	
				// 使用cookie讲用户名和密码保存到客户端，时长为一周
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
			 * 从ArrayList中取出UserBean，即商品信息
			 */
			array= ubs.getProduct(PageNow, PageRow); //每次更新当前页码
			for(int i =0 ;i<PageRow;i++) 
			{
				
					productBean = (UserBean)array.get(i);
					productData[i][1]=productBean.getProductName();
					productData[i][2]=productBean.getPrice();
					productData[i][3]=productBean.getNumber();
					productData[i][0]=productBean.getID();
					/*System.out.println("name "+productData[i][0] +" price "+productData[i][1]+" num: "+productData[i][2]+" i "+i);*/
			}
			hs.setAttribute("productData", productData); //数据库查询商品数据，二维数组	
			hs.setAttribute("PageRow",PageRow);  //每行显示页数，传递给JSP
			hs.setAttribute("PageNow", PageNow);
			hs.setAttribute("PageCount", PageCount); 
			hs.setAttribute("name", inputName);   //用于jsp中校验，防越过登陆
			hs.setAttribute("password", inputPassword);   //用于jsp跳转回servlet时免校验
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
