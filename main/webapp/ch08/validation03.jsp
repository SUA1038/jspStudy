<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Validation</title>
<script type="text/javascript">
	function checkLogin(){ // 페이지에 입력 항목의 데이터를 검사하는 핸들러함수 checkLogin()작성
		var form=document.loginForm;
		
		if(form.id.value.length<4 || form.id.value.length>12){
			alert("아이디는 4~12자 이내로 입력 가능합니다.");
			form.id.selecet();
			return;
		}
		
		if(form.passwd.value.length<4 || form.passwd.value.length>25){
			alert("비밀번호는 4자~25자 이내로 입력 가능합니다.")
			form.passwd.select();
			return;
		}
		
		form.submit();
	}
</script>
</head>
<body>
	<form name="loginForm" action="validation03_process.jsp" method="post">
		<p> 아이디 : <input type="text" name="id">
		<p> 비밀번호 : <input type="password" name="passwd">
		<p> <input type="button" value="전송" onclick="checkLogin()">
	</form>
</body>
</html>