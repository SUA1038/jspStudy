CREATE TABLE Board  (
       num int not null auto_increment,		-- 게시글 순번
       id varchar(10) not null,				-- 회원 아이디
       name varchar(10) not null,			-- 회원 이름
       subject varchar(100) not null,		-- 게시글 제목
       content text not null,				-- 게시글 내용
       regist_day varchar(30),				-- 게시글 등록 일자
       hit int,								-- 게시글 조회 수
       likebutton int,						-- 게시글 공감 버튼
       PRIMARY KEY (num)					-- 게시글 순번을 고유 키로 설정
)default CHARSET=utf8; 

select * from Board;
desc Board;
drop table Board;


------------------------------------------------------------------------
-- 수정 후

USE coffee;


CREATE TABLE IF NOT EXISTS BOARD (
       NUM int not null auto_increment,   
       ID varchar(10) not null,   
       SUBJECT varchar(100) not null,
       CONTENT text not null,
       REGIST_DAY varchar(30),
       HIT int,   
       LIKEBUTTON int,
       PRIMARY KEY (num),
       FOREIGN KEY(ID) REFERENCES MEMBER(ID)
)default CHARSET=utf8;




--name varchar(10) not null, 이름은 MEMBER 테이블에서 불러오는 걸로
select * from Board;
desc Board;
drop table Board;

--멤버 테이블
create table member (
      id varchar(10) not null, -- 회원 아이디
      password varchar(10) not null, -- 비밀번호
      name varchar(10) not null, -- 이름
      gender varchar(4) not null, -- 성별
      birth varchar(10) not null, -- 생일
      mail varchar(30) not null,  -- 메일
      phone varchar(20) not null, -- 전화번호
      address varchar(100) not null, -- 주소
      regist_day varchar(50) not null, -- 가입 일자
      primary key(id)                  -- 회원 아이디를 고유 키로 설정 
) default CHARSET=utf8;
drop table member;
select * from member;

-----------------------------------------------------------
--더미데이터

-- 회원 더미 데이터 3명 추가
INSERT INTO member (id, password, name, gender, birth, mail, phone, address, regist_day)
VALUES 
('user1', 'pass1', '홍길동', '남', '1990-01-01', 'user1@example.com', '010-1234-5678', '서울시 강남구', '2025-07-03'),
('user2', 'pass2', '김영희', '여', '1992-02-02', 'user2@example.com', '010-2345-6789', '서울시 서초구', '2025-07-03'),
('user3', 'pass3', '이철수', '남', '1991-03-03', 'user3@example.com', '010-3456-7890', '서울시 마포구', '2025-07-03');

-- 게시글 더미 데이터 5개 추가
INSERT INTO board (id, subject, content, regist_day, hit, likebutton)
VALUES
('user1', '첫 번째 게시글입니다', '안녕하세요. 첫 번째 글입니다.', '2025/07/03(10:00:00)', 10, 2),
('user2', '두 번째 글', '두 번째 내용입니다.', '2025/07/03(11:00:00)', 5, 0),
('user3', '세 번째 게시물', '세 번째 내용. 테스트용입니다.', '2025/07/03(11:30:00)', 2, 1),
('user1', '네 번째 글입니다', '다시 user1이 작성한 글입니다.', '2025/07/03(12:00:00)', 15, 3),
('user2', '다섯 번째 테스트', '게시판 테스트용 글입니다.', '2025/07/03(12:30:00)', 0, 0);

