package mvc.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import mvc.database.DBConnection;

public class BoardDAO {

	private static BoardDAO instance;

	private BoardDAO() {

	}

	public static BoardDAO getInstance() {
		if (instance == null)
			instance = new BoardDAO();
		return instance;
	}

	// 게시글 공감 수(likebutton) 1 증가
	// 게시글 번호(num)를 받아 해당 게시글의 공감(likebutton) 수를 1 증가시키는 메서드
	public void increaseLike(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			// DB 연결 얻기
			conn = DBConnection.getConnection();

			// likebutton 컬럼을 1 증가시키는 SQL문 작성
			String sql = "UPDATE board SET likebutton = likebutton + 1 WHERE num = ?";

			// SQL문 실행 준비
			pstmt = conn.prepareStatement(sql);

			// ?에 게시글 번호(num) 바인딩
			pstmt.setInt(1, num);

			// 쿼리 실행 (업데이트)
			pstmt.executeUpdate();

		} catch (Exception e) {
			// 예외 발생 시 스택 트레이스 출력
			e.printStackTrace();

		} finally {
			// 사용한 자원들 닫기 (예외 발생 가능성 있어서 try-catch로 감싸줌)
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
	}

	// board 테이블의 레코드 개수
	public int getListCount(String items, String text) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int x = 0; // 레코드 수를 저장할 변수

		String sql;

		// 검색 조건이 없으면 전체 게시글 개수 조회 쿼리 작성
		if (items == null && text == null)
			sql = "select count(*) from board";
		else
			// 특정 컬럼(items)에서 text를 포함하는 게시글 개수 조회 (like 연산자 사용)
			sql = "SELECT count(*) FROM board where " + items + " like '%" + text + "%'";

		try {
			// DB 연결 얻기
			conn = DBConnection.getConnection();

			// SQL문 실행 준비
			pstmt = conn.prepareStatement(sql);

			// 쿼리 실행 후 결과(ResultSet) 얻기
			rs = pstmt.executeQuery();

			// 결과가 있으면 첫 번째 컬럼 값(개수)를 x에 저장
			if (rs.next())
				x = rs.getInt(1);

		} catch (Exception ex) {
			// 에러 발생 시 에러 메시지 출력
			System.out.println("getListCount() 레코드수 : " + ex);
		} finally {
			// 사용한 자원들 반드시 닫기 (예외 발생 가능성 대비 try-catch 사용)
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}

		// 최종 게시글 개수 반환
		return x;
	}

	// board 테이블의 레코드 가져오기
	// 게시글 목록을 페이징 처리하여 검색 조건에 맞게 가져오는 메서드
	public ArrayList<BoardDTO> getBoardList(int page, int limit, String items, String text) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// 검색 조건에 맞는 전체 게시글 수를 가져옴
		int total_record = getListCount(items, text);

		// 현재 페이지의 시작 인덱스 계산
		int start = (page - 1) * limit;
		// 결과셋에서 이동할 실제 위치 (absolute는 1부터 시작)
		int index = start + 1;

		String sql;

		// 검색 조건이 없을 경우 전체 게시글 가져오기
		if (items == null && text == null)
			sql = "SELECT board.*, member.name FROM board JOIN member ON board.id = member.id ORDER BY board.num DESC";
		else
			// 검색 조건이 있을 경우 해당 조건으로 검색
			sql = "SELECT * FROM board WHERE " + items + " LIKE '%" + text + "%' ORDER BY num DESC";

		// 결과를 담을 리스트 생성
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();

		try {
			conn = DBConnection.getConnection();

			// 스크롤이 가능한 결과셋 생성
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();

			// 결과셋에서 해당 인덱스로 이동하며 게시글 객체 생성 후 리스트에 추가
			while (rs.absolute(index)) {
				BoardDTO board = new BoardDTO();
				board.setNum(rs.getInt("num"));
				board.setId(rs.getString("id"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setRegist_day(rs.getString("regist_day"));
				board.setHit(rs.getInt("hit"));
				board.setLikebutton(rs.getInt("likebutton"));
				list.add(board);

				// limit만큼 반복되도록 인덱스 증가
				if (index < (start + limit) && index <= total_record)
					index++;
				else
					break; // limit 도달 시 종료
			}
			return list;
		} catch (Exception ex) {
			System.out.println("getBoardList() ???? : " + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}

		// 오류 발생 시 빈 리스트 반환
		return new ArrayList<>();
	}

	// member 테이블에서 인증된 id의 사용자명 가져오기
	// (member 테이블에서 로그인한 사용자의 id를 통해 이름(name)을 조회하는 메서드)
	public String getLoginNameById(String id) {
		Connection conn = null; // DB 연결을 위한 Connection 객체
		PreparedStatement pstmt = null; // SQL 실행을 위한 PreparedStatement 객체
		ResultSet rs = null; // SQL 결과를 담을 ResultSet 객체

		String name = null; // 사용자 이름을 저장할 변수

		// 사용자 ID에 해당하는 레코드를 조회하는 SQL 문
		String sql = "select * from member where id = ? ";

		try {
			// DB 연결
			conn = DBConnection.getConnection();

			// SQL 준비 및 파라미터 바인딩
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id); // 첫 번째 ?에 전달받은 id 값 설정

			// SQL 실행
			rs = pstmt.executeQuery();

			// 결과가 존재하면 name 필드 값 가져오기
			if (rs.next())
				name = rs.getString("name"); // member 테이블의 name 컬럼 값

			return name; // name 반환
		} catch (Exception ex) {
			// 예외 발생 시 콘솔에 출력
			System.out.println("() ???? : " + ex);
		} finally {
			// 사용한 자원 해제 (역순으로)
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage()); // 자원 해제 중 예외 발생 시 런타임 예외 던짐
			}
		}

		// 조회 실패 시 null 반환
		return null;
	}

	// board 테이블에 새로운 글 삽입하기
	public void insertBoard(BoardDTO board) {
		  Connection conn = null;                 // DB 연결 객체 선언
		    PreparedStatement pstmt = null;         // SQL 실행 준비 객체 선언

		    try {
		        conn = DBConnection.getConnection();   // DB 연결 얻기

		        // name 컬럼 제외, 게시글 정보만 INSERT 하는 SQL 작성
		        String sql = "INSERT INTO board (id, subject, content, regist_day, hit, likebutton) VALUES (?, ?, ?, ?, ?, ?)";

		        pstmt = conn.prepareStatement(sql);    // SQL 실행 준비

		        pstmt.setString(1, board.getId());         // 1번째 물음표에 작성자 ID 세팅
		        pstmt.setString(2, board.getSubject());    // 2번째 물음표에 게시글 제목 세팅
		        pstmt.setString(3, board.getContent());    // 3번째 물음표에 게시글 내용 세팅
		        pstmt.setString(4, board.getRegist_day()); // 4번째 물음표에 등록일 세팅
		        pstmt.setInt(5, board.getHit());           // 5번째 물음표에 조회수 세팅 (보통 0)
		        pstmt.setInt(6, board.getLikebutton());    // 6번째 물음표에 좋아요 수 세팅 (보통 0)

		        pstmt.executeUpdate();                     // SQL 실행 (INSERT)

		    } catch (Exception ex) {
		        System.out.println("insertBoard() 에러 : " + ex);  // 예외 발생 시 에러 메시지 출력
		    } finally {
		        try {
		            if (pstmt != null) pstmt.close();   // PreparedStatement 닫기
		            if (conn != null) conn.close();     // DB 연결 닫기
		        } catch (Exception ex) {
		            throw new RuntimeException(ex.getMessage());  // 자원 닫기 실패 시 런타임 예외 발생
		        }
		    }
		}
	

	// 선택된 글(num)의 조회 수(hit)를 1 증가시키는 메서드
	public void updateHit(int num) {

		Connection conn = null; // DB 연결 객체
		PreparedStatement pstmt = null; // SQL 실행 객체
		ResultSet rs = null; // 쿼리 결과 저장 객체

		try {
			// DB 연결
			conn = DBConnection.getConnection();

			// 현재 조회수(hit)를 가져오는 SQL 문
			String sql = "SELECT hit FROM board WHERE num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

			// 쿼리 실행 및 결과 저장
			rs = pstmt.executeQuery();
			int hit = 0;

			// 결과가 있으면 현재 조회수를 가져와서 1 증가시킴
			if (rs.next())
				hit = rs.getInt("hit") + 1;

			// 조회수를 1 증가시킨 값을 다시 DB에 업데이트
			sql = "UPDATE board SET hit = ? WHERE num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, hit);
			pstmt.setInt(2, num);
			pstmt.executeUpdate();

		} catch (Exception ex) {
			// 예외 발생 시 에러 출력
			System.out.println("updateHit() 에러 발생 : " + ex);

		} finally {
			// 자원 해제 (결과셋, PreparedStatement, Connection 순서)
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}

	// 선택된 글 상세 내용 가져오기
	// 선택된 글 번호(num)에 해당하는 게시글의 상세 내용을 가져오는 메서드
	public BoardDTO getBoardByNum(int num, int page) {
		Connection conn = null; // DB 연결 객체
		PreparedStatement pstmt = null; // SQL 실행 객체
		ResultSet rs = null; // 쿼리 결과 저장 객체
		BoardDTO board = null; // 게시글 정보를 담을 DTO 객체

		// 조회수(hit)를 1 증가시킴 (글 상세보기 시 조회수 증가)
		updateHit(num);

		// 선택한 글 번호에 해당하는 게시글을 조회하는 SQL 문
		String sql = "SELECT board.*, member.name FROM board JOIN member ON board.id = member.id WHERE board.num = ?";

		try {
			// DB 연결
			conn = DBConnection.getConnection();

			// SQL 준비 및 파라미터 세팅
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

			// 쿼리 실행
			rs = pstmt.executeQuery();

			// 결과가 있으면 게시글 데이터를 BoardDTO 객체에 세팅
			if (rs.next()) {
				board = new BoardDTO();
				board.setNum(rs.getInt("num")); // 글 번호
				board.setId(rs.getString("id")); // 작성자 아이디
				board.setName(rs.getString("name")); // 작성자 이름
				board.setSubject(rs.getString("subject")); // 글 제목
				board.setContent(rs.getString("content")); // 글 내용
				board.setRegist_day(rs.getString("regist_day"));// 작성일
				board.setHit(rs.getInt("hit")); // 조회수
				board.setLikebutton(rs.getInt("likebutton")); // 좋아요 수
			}

			// 완성된 게시글 객체 반환
			return board;

		} catch (Exception ex) {
			// 예외 발생 시 에러 메시지 출력
			System.out.println("getBoardByNum() 에러 발생 : " + ex);

		} finally {
			// DB 자원 해제
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}

		// 예외 등으로 게시글을 가져오지 못했을 경우 null 반환
		return null;
	}

	// 선택된 글 내용 수정하기
	public void updateBoard(BoardDTO board) {
	    Connection conn = null; // DB 연결 객체
	    PreparedStatement pstmt = null; // SQL 실행 객체

	    try {
	        // DB 연결
	        conn = DBConnection.getConnection();

	        // 게시글 수정 SQL 문: 제목(subject), 내용(content)을 num 기준으로 수정
	        String sql = "UPDATE board SET subject = ?, content = ? WHERE num = ?";

	        // SQL 준비
	        pstmt = conn.prepareStatement(sql);

	        // 파라미터 세팅
	        pstmt.setString(1, board.getSubject());
	        pstmt.setString(2, board.getContent());
	        pstmt.setInt(3, board.getNum());

	        // SQL 실행 (업데이트)
	        pstmt.executeUpdate();

	    } catch (Exception ex) {
	        // 예외 발생 시 에러 메시지 출력
	        System.out.println("updateBoard() 에러 발생 : " + ex);

	    } finally {
	        // DB 자원 해제
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception ex) {
	            throw new RuntimeException(ex.getMessage());
	        }
	    }
	}
	
	
	// 선택된 글 삭제하기
	public void deleteBoard(int num) {
		Connection conn = null; // DB 연결 객체
		PreparedStatement pstmt = null; // SQL 실행 객체

		// 게시글 삭제 SQL 문: num(게시글 번호) 기준으로 해당 레코드 삭제
		String sql = "delete from board where num=?";

		try {
			// DB 연결
			conn = DBConnection.getConnection();

			// SQL 준비
			pstmt = conn.prepareStatement(sql);

			// SQL 파라미터 세팅: 삭제할 게시글 번호
			pstmt.setInt(1, num);

			// SQL 실행: 게시글 삭제
			pstmt.executeUpdate();

		} catch (Exception ex) {
			// 예외 발생 시 에러 메시지 출력
			System.out.println("deleteBoard() 에러 발생 : " + ex);

		} finally {
			// DB 자원 해제
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}
}