<%@page import="java.io.PrintWriter"%>
<%@ page contentType = "text/html; charset = UTF-8" %>
<%@ page isErrorPage="true" %>
<html><head>
<title>Directives Tag</title></head><body>
	<h4>에러가 발생했습니다.</h4>
	<h5>exception 내장 객체 변수</h5>
	<%
		exception.printStackTrace(new PrintWriter(out));
	%>
</body>
</html>