<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>登陆失败</title>
<script type="text/javascript">
	var time = 4;
 
	function returnUrlByTime() {
 
		window.setTimeout('returnUrlByTime()', 1000);
 
		time = time - 1;
 
		document.getElementById("layer").innerHTML = time;
	}
</script>
</head>
<body onload="returnUrlByTime()">
<p>登陆失败，请检查账户密码！</p>
<b><span id="layer">3</span>秒后，转入输入界面。</b>
<%
		//转向语句
		response.setHeader("Refresh", "3;URL=index.html");
%>
</body>
</html>