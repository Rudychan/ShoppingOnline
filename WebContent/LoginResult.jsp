<%@ page language="java" contentType="text/html; charset=utf-8"
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