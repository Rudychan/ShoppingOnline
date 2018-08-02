<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%  HttpSession hs = request.getSession();
	String name =(String)hs.getAttribute("name");
	if (name!=null)
	{%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改商品参数</title>
</head>
<body>
 欢迎访问，当前用户：
			<!-- 暂时用scriptlet写，后面换成EL -->
			<%
				out.print(name);
				String ID = (String)request.getParameter("modifyID");
				String Name = (String)request.getParameter("modifyName");
				String Price = (String)request.getParameter("modifyPrice");
				String Num = (String)request.getParameter("modifyNum");
			%>
			<div style="text-align:center">
			<h1>商品信息修改</h1>
			<form action="testapp" method="post">
			<table border ="1"  style="width: 60%;margin:auto">
			<tr>
			<td>商品ID</td><td><input readonly type ="text" name = "ModifyID" value =<%out.print(ID);%>></td>
			</tr>
			<tr>
			<td>商品名称</td><td><input type ="text" name = "ModifyName" value =<%out.print(Name);%>></td>
			</tr>
			<tr>
			<td>商品价格</td><td><input type ="text" name = "ModifyPrice" value =<%out.print(Price);%>></td>
			</tr>
			<tr>
			<td>商品数量</td><td><input type ="text" name = "ModifyNum" value =<%out.print(Num);%>></td>
			</tr>
			</table>
			<input type = submit value = "提交" >
			</form>	
			</div>		
</body>
</html>
<% 	}
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
