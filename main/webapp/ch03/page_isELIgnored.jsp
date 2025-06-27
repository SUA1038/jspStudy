<%@page contentType = "text/html; charset=UTF-8" %>
<%@page isELIgnored = "true" %>
<html><head>
<title>Directives Tag</title></head>
<body>
	<%
		request.setAttribute("RequestAttribute", "request 내장 객체");
	%>
	${requeScope.RequestAttribute}
</body>
</html>