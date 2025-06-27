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
		request.setCharacterEncoding("UTF-8");	// 한글 처리하도록 문자 인코딩 UTF-8 작성
		String userid = request.getParameter("id"); // 입력된 아이디 패스워드를 폼 문으로부터 전송 받도록 request 내장 객체의 getParameter() 메소드를 작성
		String password = request.getParameter("passwd");
		
		if(userid.equals("관리자") && password.equals("1234")){
			response.sendRedirect("response01_success.jsp");
		}else{
			response.sendRedirect("response01_failed.jsp");
		}
	%>
	<p> 아이디 : <%=userid %>
	<p> 비밀번호 : <%=password %>
	
</body>
</html>