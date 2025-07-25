package dao;

import java.util.ArrayList;

import dto.Book;

public class BookRepository {
	// 원래는 db에 저장된 내용을 가져와 출력해야 되지만 간단하게 구현

	// 프론트에서 넘어오는 더미데이터 리스트
	private static BookRepository instance = new BookRepository();

	public static BookRepository getInstance() {
		return instance;
	} // 자신 객체를 생성하는 메스드

	// 생성자를 통한 더미데이터
	private ArrayList<Book> listOfBooks = new ArrayList<Book>();
	// ArrayList는 배열과 비슷한 구조지만 추가삭제가 자유롭다.
	// <타입> -> Book타입만 ArrayList에 들어가게 셋팅

	public BookRepository() { // 생성자로 더미데이터 3개를 리스트에 저장

		Book book1 = new Book("ISBN1234", "C# 프로그래밍", 27000);
		book1.setAuthor("우재남");
		book1.setDescription(
				"C#을 처음 접하는 독자를 대상으로 일대일 수업처럼 자세히 설명한 책이다. 꼭 알아야 할 핵심 개념은 기본 예제로 최대한 쉽게 설명했으며, 중요한 내용은 응용 예제, 퀴즈, 셀프 스터디, 예제 모음으로 한번 더 복습할 수 있다.");
		book1.setPublisher("한빛아카데미");
		book1.setCategory("IT모바일");
		book1.setUnitsInStock(1000);
		book1.setReleaseDate("2022/10/06");
		book1.setFilename("ISBN1234.jpg");

		Book book2 = new Book("ISBN1235", "자바마스터", 30000);
		book2.setAuthor("송미영");
		book2.setDescription(
				"자바를 처음 배우는 학생을 위해 자바의 기본 개념과 실습 예제를 그림을 이용하여 쉽게 설명합니다. 자바의 이론적 개념→기본 예제→프로젝트 순으로 단계별 학습이 가능하며, 각 챕터의 프로젝트를 실습하면서 온라인 서점을 완성할 수 있도록 구성하였습니다.");
		book2.setPublisher("한빛아카데미");
		book2.setCategory("IT모바일");
		book2.setUnitsInStock(1000);
		book2.setReleaseDate("2023/01/01");
		book2.setFilename("ISBN1235.jpg");
		
		Book book3 = new Book("ISBN1236", "파이썬 프로그래밍", 30000);
		book3.setAuthor("최성철");
		book3.setDescription(
				" 파이썬으로 프로그래밍을 시작하는 입문자가 쉽게 이해할 수 있도록 기본 개념을 상세하게 설명하며, 다양한 예제를 제시합니다. 또한 프로그래밍의 기초 원리를 이해하면서 파이썬으로 데이터를 처리하는 기법도 배웁니다.");
		book3.setPublisher("한빛아카데미");
		book3.setCategory("IT모바일");
		book3.setUnitsInStock(1000);
		book3.setReleaseDate("2023/01/01");
		book3.setFilename("ISBN1236.jpg");
		
		listOfBooks.add(book1); // 객체를 배열에 넣는 방법
		listOfBooks.add(book2);
		listOfBooks.add(book3); // ArrayList 배열에 3개의 boot 객체가 삽입됨.

	}

	public ArrayList<Book> getAllBooks() { // 전체 리스트를 전달함.
		return listOfBooks;
	}

	// 책의 id가 들어오면 책 객체를 리턴한다.
	public Book getBookById(String bookId) {

		Book bookById = null; // 찾은 책 초기값은 null

		for (int i = 0; i < listOfBooks.size(); i++) {
			// 위에서 만든 더미데이터 리스트 검색용
			Book book = listOfBooks.get(i); // 인덱스를 이용해 리스트 값을 가져옴

			// 리스트에 있는 책과 전달받은 책과 비교
			if (book != null && book.getBookId() != null && book.getBookId().equals(bookId)) {
				// 리스트가 널이아니고 받은값도 널이 아니고 이 두값이 일치???
				bookById = book;
				break;
			} // 비교문 종료
		} // 반복문 종료

		return bookById; // 찾은 책 객체를 리턴
	} // 책 찾기 메서드 종료

	public void addBook(Book book) {
		listOfBooks.add(book); // 더미데이터 리스트에 추가용
	} // 책 추가 메서드 종료

}
