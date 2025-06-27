<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Implicit Objects</title>
</head>
<body>
	<p> 이 페이지는 5초마다 새로고침 됩니다.
	<%
		response.setIntHeader("Refresh", 5);	// 5초마다 jsp 페이지가 갱신되도록 response 내장 객체의 setIntGeader()메소드를 작성
	%>
	<p> <%=(new java.util.Date()) %>	<!-- 5초마다 jsp 페이지가 갱신된 시각을 출력하도록 표현문 태그를 작성 -->
</body>
</html>