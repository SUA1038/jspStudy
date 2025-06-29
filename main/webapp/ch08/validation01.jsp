<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Validation</title>
	<script type = "text/javascript">
		function checkForm(){ // 자바스크립트로 폼 페이지에 입력 항목의 데이터를 검사하는 핸들러함수 checkForm()을 작성
			alert("아이디 : " + document.loginForm.id.value + "\n" + "비밀번호 : " + document.loginForm.passwd.value);
		} // 폼 페이지에 입력한 아이디 패스워드 값을 출력
	</script>
</head>
<body>
	<form name="loginForm"> <!-- name속성 값이 loginForm문을 작성 -->
		<p> 아이디 : <input type="text" name="id"> <!-- id와 pw 입력받는 input 태그 작성 -->
		<p> 비밀번호 : <input type="password" name="passwd">
		<p> <input type="submit" value="전송" onlick="checkForm()"> <!-- 전송을 클릭하면 핸들러함수 checkForm()이 실행되도록 onclick 속성 작성 -->
	</form>
</body>
</html>