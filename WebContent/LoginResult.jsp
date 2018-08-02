<%@ page language="java"  contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
 	HttpSession hs = request.getSession();
	String name =(String)hs.getAttribute("name");
	if (name!=null)
	{%>
		<html>
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
			<title>Login success!</title>
		</head>
		<body> 欢迎访问，当前用户：
			<!-- 暂时用scriptlet写，后面换成EL -->
			<%
				out.print(name);
			%>
			<div style="text-align:center">
			<h1>商品信息表</h1>
		<table border ="1"  style="width: 60%;margin:auto">
			<tr>
			<td>商品ID</td>
			<td>商品名称</td>
			<td>价格</td>
			<td>数量</td>
			<td>修改</td>
			<td>删除</td>
			</tr>
			<%
			int PageRow = (int)hs.getAttribute("PageRow");
			int PageNow = (int)hs.getAttribute("PageNow");
			int PageCount = (int)hs.getAttribute("PageCount");
			int visit = Integer.parseInt(application.getAttribute("VisitCount").toString());
		/* 	hs.setAttribute("pass", "pass"); //用于解决超链接跳转servlet密码校验不通过 */
			String [][] productData = (String[][])hs.getAttribute("productData"); 
			for(int i=0;i<PageRow;i++)
			{%>
				<tr>
				<td><% out.print(productData[i][0]);%></td>
				<td><% out.print(productData[i][1]);%></td>
				<td><% out.print(productData[i][2]);%></td>
				<td><% out.print(productData[i][3]);%></td>
				<td><a href=Modify.jsp?modifyID=<% out.print(productData[i][0]);%>&modifyName=<% out.print(productData[i][1]);%>&modifyPrice=<% out.print(productData[i][2]);%>&modifyNum=<% out.print(productData[i][3]);%>>修改</a></td>
				<td><a href=testapp?delID=<% out.print(productData[i][0]);%>>删除</a></td>
				</tr>
			<% }
			 %>
		</table>
		<p style= "text-align =center">
		<% if(PageNow>1)
		{%>
		<a href =testapp?PageNow=<%out.print(PageNow-1);%>>上一页  </a>
		<% }%>
			<%
			for(int i=PageNow;i<PageNow+5;i++)
			{%>
				<a href=testapp?PageNow=<%out.print(i);%>><%out.print(i);%></a>
			<%}%>
		<a href =testapp?PageNow=<%out.print(PageNow+1);%>> 下一页  </a>
		</p>
		<form action="testapp" method="post">
		<p style ="text-align:center"/>跳转到：  <input type ="text" name="PageNow" >
		<input type="submit" value="确定" />
		</form>
		<p><a href =testapp?Logout=1>退出登陆 </a> 网站访问次数： <%out.print(visit); %></p>
		</div>	
			
		
		</body>
		</html>
	<% }
	else
	{%>
		<html>
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
			<title>Illegal page!</title>
		</head>
		<body> 
			<p>访问超时！请重新登陆！</p>
		</body>
		</html>
	 
	<%}%>