package mvc.model;

public class BoardDTO {
	private int num;
	private String id;			// member테이블에서 불러오기
	private String name;
	private String subject;
	private String content;
	private String regist_day;
	private int hit;
	private int likebutton;
	
	
	// 기본 생성자
	public BoardDTO() {
		super();		
	}
	
	// 게시글 작성용 생성자
    public BoardDTO(String id, String name, String subject, String content, int num) {
        this.num = num;
    	this.id = id;
        this.name = name;
        this.subject = subject;
        this.content = content;
        this.regist_day = java.time.LocalDate.now().toString();
        this.hit = 0;
        this.likebutton = 0;
    }
	

	public int getNum() {
		return num;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}

	public String getRegist_day() {
		return regist_day;
	}

	public int getHit() {
		return hit;
	}

	public int getLikebutton() {
		return likebutton;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setRegist_day(String regist_day) {
		this.regist_day = regist_day;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public void setLikebutton(int likebutton) {
		this.likebutton = likebutton;
	}



}