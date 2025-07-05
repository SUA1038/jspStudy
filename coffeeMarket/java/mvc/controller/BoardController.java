package mvc.controller;

import java.io.IOException;
import java.util.ArrayList;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import mvc.model.BoardDAO;
import mvc.model.BoardDTO;

public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final int LISTCOUNT = 5; // 한 페이지에 보여줄 게시글 개수 제한.

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 모든 요청을 doPost에서 처리하도록 함, URI을 분석하여 어떤 작업을 할지 결정.
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		//클라이언트 요청 URI에서 컨텍스트 패스 길이만큼 빼서 실제 명령어(command)를 얻음.
		// 예: /BoardListAction.do 같은 값이 됨.
	
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
	
		if (command.equals("/BoardListAction.do")) {//등록된 글 목록 페이지 출력하기
			requestBoardList(request);
			RequestDispatcher rd = request.getRequestDispatcher("./board/list.jsp");
			rd.forward(request, response);
		} else if (command.equals("/BoardWriteForm.do")) { //글 등록 페이지 출력
				requestLoginName(request);
				RequestDispatcher rd = request.getRequestDispatcher("./board/writeForm.jsp");
				rd.forward(request, response);				
		} else if (command.equals("/BoardWriteAction.do")) {//새로운 글 등록
		    requestBoardWrite(request);
		    response.sendRedirect("BoardListAction.do");  // 리다이렉트로 변경				
		} else if (command.equals("/BoardViewAction.do")) {//선택된 글 상자 페이지 가져오기
		    requestBoardView(request);
		    RequestDispatcher rd = request.getRequestDispatcher("./board/view.jsp");
		    rd.forward(request, response);											
		} else if (command.equals("/BoardView.do")) {  //글 상세 페이지 출력
				RequestDispatcher rd = request.getRequestDispatcher("./board/view.jsp");
				rd.forward(request, response);	
		} else if (command.equals("/BoardUpdateAction.do")) { //선택된 글 수정하기
				requestBoardUpdate(request);
				RequestDispatcher rd = request.getRequestDispatcher("/BoardListAction.do");
				rd.forward(request, response);
		}else if (command.equals("/BoardDeleteAction.do")) { //선택된 글 삭제하기
				requestBoardDelete(request);
				RequestDispatcher rd = request.getRequestDispatcher("/BoardListAction.do");
				rd.forward(request, response);				
		} else if (command.equals("/BoardLikeAction.do")) { // 공감 버튼
		    	requestLikeBoard(request);
		    	RequestDispatcher rd = request.getRequestDispatcher("/BoardViewAction.do?num=" + request.getParameter("num") + "&pageNum=" + request.getParameter("pageNum"));
		    	rd.forward(request, response);
		} // 요청 주소에 따라 처리 메서드를 호출하고 JSP 페이지로 포워딩하거나 리다이렉트함. 각 기능별로 아래에서 설명할 메서드를 호출.
	}
	
	// 게시글 공감 수 증가
	public void requestLikeBoard(HttpServletRequest request) {
	    int num = Integer.parseInt(request.getParameter("num"));
	    BoardDAO dao = BoardDAO.getInstance();
	    dao.increaseLike(num);
	}

	//등록된 글 목록 가져오기 (게시글 목록을 가져와서 request 객체에 저장하는 메서드)
	public void requestBoardList(HttpServletRequest request) {
	   
		// DAO 인스턴스를 가져온다 (싱글톤 패턴)
	    BoardDAO dao = BoardDAO.getInstance();
	    
	    // 게시글 목록을 저장할 리스트 생성
	    ArrayList<BoardDTO> boardlist = new ArrayList<BoardDTO>();
	    
	    // 기본 페이지 번호는 1로 설정
	    int pageNum = 1;

	    // 한 페이지당 출력할 게시글 수
	    int limit = LISTCOUNT;

	    // 클라이언트가 보낸 페이지 번호가 있다면 그 값을 사용
	    if (request.getParameter("pageNum") != null)
	        pageNum = Integer.parseInt(request.getParameter("pageNum"));
	    
	    // 검색 조건과 검색어를 가져온다 (예: items = "subject", text = "공지")
	    String items = request.getParameter("items");
	    String text = request.getParameter("text");

	    // 전체 게시글 수를 DB에서 가져온다 (검색 조건 포함 가능)
	    int total_record = dao.getListCount(items, text);

	    // 현재 페이지에 해당하는 게시글 목록을 가져온다
	    boardlist = dao.getBoardList(pageNum, limit, items, text);

	    // 전체 페이지 수 계산 (올림 처리)
	    int total_page = (total_record + limit - 1) / limit;

	    // JSP에서 사용할 정보를 request 객체에 저장
	    request.setAttribute("pageNum", pageNum);               // 현재 페이지 번호
	    request.setAttribute("total_page", total_page);         // 전체 페이지 수
	    request.setAttribute("total_record", total_record);     // 전체 게시글 수
	    request.setAttribute("boardlist", boardlist);           // 현재 페이지의 게시글 목록
	}
	
	//인증된 사용자명 가져오기(로그인된 사용자의 이름을 가져와 request 객체에 저장하는 메서드)
	public void requestLoginName(HttpServletRequest request) {
	    
	    // 클라이언트로부터 전달받은 id 값을 가져온다 (파라미터로 전달된 사용자 ID)
	    String id = request.getParameter("id");

	    // BoardDAO 인스턴스를 얻는다 (싱글톤 패턴)
	    BoardDAO dao = BoardDAO.getInstance();

	    // 해당 ID에 해당하는 사용자의 이름(name)을 DB에서 가져온다
	    String name = dao.getLoginNameById(id);

	    // JSP에서 출력하거나 사용할 수 있도록 이름을 request에 저장한다
	    request.setAttribute("name", name);
	}
  
	
	//새로운 글 등록하기(클라이언트로부터 전달받은 게시글 데이터를 DB에 저장하는 메서드)
	public void requestBoardWrite(HttpServletRequest request){
					
		  // BoardDAO 인스턴스 가져오기 (싱글톤 패턴)
	    BoardDAO dao = BoardDAO.getInstance();		

	    // 게시글 정보를 담을 DTO 객체 생성
	    BoardDTO board = new BoardDTO();

	    // 사용자 입력값을 DTO에 설정
	    board.setId(request.getParameter("id"));           // 작성자 ID
	    board.setName(request.getParameter("name"));       // 작성자 이름
	    board.setSubject(request.getParameter("subject")); // 게시글 제목
	    board.setContent(request.getParameter("content")); // 게시글 내용	

	    // 작성자 정보 콘솔 출력 (디버깅용)
	    System.out.println(request.getParameter("name"));
	    System.out.println(request.getParameter("subject"));
	    System.out.println(request.getParameter("content"));

	    // 작성 날짜(시간 포함)를 현재 시각으로 설정
	    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd(HH:mm:ss)");
	    String regist_day = formatter.format(new java.util.Date()); 

	    // 초기값 설정: 조회수(hit), 작성일, 공감 수(likebutton)
	    board.setHit(0);
	    board.setRegist_day(regist_day);
	    board.setLikebutton(0);			

	    // 게시글을 DB에 저장
	    dao.insertBoard(board);	
	}
	
	
	//선택된 글 상세 페이지 가져오기(사용자가 선택한 게시글의 상세 내용을 가져와 request에 저장하는 메서드)
	public void requestBoardView(HttpServletRequest request) {

	    // BoardDAO 객체 생성 (싱글톤으로 관리되는 DAO)
	    BoardDAO dao = BoardDAO.getInstance();

	    // 요청 파라미터에서 게시글 번호(num)와 현재 페이지 번호(pageNum)를 가져옴
	    int num = Integer.parseInt(request.getParameter("num"));       // 게시글 고유 번호
	    int pageNum = Integer.parseInt(request.getParameter("pageNum")); // 현재 페이지 번호

	    // 게시글 정보를 담을 DTO 객체 생성
	    BoardDTO board = new BoardDTO();

	    // 게시글 번호에 해당하는 상세 정보 조회 (조회수 증가도 포함됨)
	    board = dao.getBoardByNum(num, pageNum);

	    // JSP에서 사용할 수 있도록 request에 필요한 데이터를 저장
	    request.setAttribute("num", num);           // 글 번호 전달
	    request.setAttribute("page", pageNum);      // 현재 페이지 번호 전달
	    request.setAttribute("board", board);       // 글 상세 내용 전달 (view.jsp에서 출력됨)
	}
	
	 //선택된 글 내용 수정하기
	public void requestBoardUpdate(HttpServletRequest request){
					
	    // 게시글 번호와 페이지 번호를 파라미터에서 추출
	    int num = Integer.parseInt(request.getParameter("num"));
	    int pageNum = Integer.parseInt(request.getParameter("pageNum"));	

	    // DAO 인스턴스 생성 (싱글톤)
	    BoardDAO dao = BoardDAO.getInstance();		

	    // 새로운 BoardDTO 객체 생성 및 수정할 데이터 설정
	    BoardDTO board = new BoardDTO();		
	    board.setNum(num); // 수정할 게시글 번호 설정
	    board.setName(request.getParameter("name")); // 작성자 이름
	    board.setSubject(request.getParameter("subject")); // 게시글 제목
	    board.setContent(request.getParameter("content")); // 게시글 내용

	    // 현재 시각을 등록일(regist_day)로 설정
	    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd(HH:mm:ss)");
	    String regist_day = formatter.format(new java.util.Date()); 

	    board.setRegist_day(regist_day); // 수정 시간 설정

	    // DAO를 통해 데이터베이스에 업데이트 요청
	    dao.updateBoard(board);								
	}
	//선택된 글 삭제하기
	//클라이언트로부터 삭제할 게시글 번호(num)와 현재 페이지 번호(pageNum)를 받아서 해당 게시글을 삭제하는 메서드
	public void requestBoardDelete(HttpServletRequest request){
					
		  
	    // 삭제할 게시글 번호를 파라미터에서 받아 정수로 변환
	    int num = Integer.parseInt(request.getParameter("num"));
	    
	    // 현재 페이지 번호를 파라미터에서 받아 정수로 변환 (삭제 처리에는 직접 사용하지 않음)
	    int pageNum = Integer.parseInt(request.getParameter("pageNum"));
	    
	    // DAO 인스턴스 얻기
	    BoardDAO dao = BoardDAO.getInstance();
	    
	    // DAO의 deleteBoard 메서드를 호출하여 num에 해당하는 게시글 삭제
	    dao.deleteBoard(num);
	}

}