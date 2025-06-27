<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Implicit Objects</title>
</head>
<body>
	<%
		response.sendError(404, "요청 페이지를 찾을 수 없습니다."); // response 내장 객체의 sendError()메소드를 작성하여 오류 메시지를 넣음
	%>
</body>
</html>