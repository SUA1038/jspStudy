<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File Upload</title>
</head>
<body>
	<form name = "fileForm" method = "post" enctype = "multipart/form-data" action = "fileupload01_process.jsp"> 
	<!-- 파일 업로드를 위한 폼 태그에 method 속성 값은 post, enctype은 multipart/form-data, 업로드된 파일을 처리하기 위한 속성 action 작성 -->
		<p> 이름 : <input type="text" name="name"> <!-- 이름과 제목 값을 입력받도록 input 태그의 type 속성값을 text로 작성하고 name속성을 작성 -->
		<p> 제목 : <input type="text" name="subject">
		<p> 파일 : <input type="file" name="filename">
		<p> <input type="submit" value="파일 올리기"> <!-- 입력된 데이터를 서버로 전송하도록 input 태그의 type 속성 값을 submit로 작성 -->
	</form>
</body>
</html>