<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>Menu</title>
</head>
   <meta charset="UTF-8">
    <title>게시판 기능 메뉴</title>
    <!-- 부트스트랩 CSS -->
    <link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
    <!-- 부트스트랩 아이콘 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <style>
      body {
        background-color: #f7f9f9;
        padding: 40px;
      }
      h2 {
        color: #4C5317;
        margin-bottom: 30px;
        text-align: center;
        font-weight: bold;
      }
      .menu-container {
        display: flex;
        justify-content: center;
        gap: 40px;
        flex-wrap: wrap;
      }
      .menu-item {
        background: white;
        border-radius: 10px;
        padding: 25px 20px;
        width: 140px;
        text-align: center;
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        color: #4C5317;
        text-decoration: none;
        transition: all 0.3s ease;
        cursor: pointer;
      }
      .menu-item i {
        font-size: 48px;
        margin-bottom: 15px;
        display: block;
      }
      .menu-item:hover {
        color: #2c3e50;
        box-shadow: 0 8px 15px rgba(44, 62, 80, 0.3);
        transform: translateY(-5px);
        text-decoration: none;
      }
    </style>
<body>
   <h2>게시판</h2>
  <div class="menu-container">
    <a href="./BoardListAction.do" class="menu-item">
      <i class="bi bi-card-list"></i>
      게시글 목록
    </a>
    <a href="./BoardWriteForm.do" class="menu-item">
      <i class="bi bi-pencil-square"></i>
      글 작성
    </a>
    <a href="./BoardViewAction.do?num=1&pageNum=1" class="menu-item">
      <i class="bi bi-eye"></i>
      글 상세 보기
    </a>
    <a href="./BoardUpdateAction.do?num=1&pageNum=1" class="menu-item">
      <i class="bi bi-pencil"></i>
      글 수정
    </a>
    <a href="./BoardDeleteAction.do?num=1&pageNum=1" class="menu-item">
      <i class="bi bi-trash"></i>
      글 삭제
    </a>
  </div>
</body>
</html>