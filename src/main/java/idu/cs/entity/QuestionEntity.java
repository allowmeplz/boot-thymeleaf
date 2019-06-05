package idu.cs.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import idu.cs.domain.Question;
import idu.cs.domain.User;


@Entity
@Table(name = "user_table") // table이름을 지정할 때 사용
public class QuestionEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	//  database에서 sequence number, auto increment => primary key 역할

	private String title;
	
	@ManyToOne
	@JoinColumn(name="fk_question_writer")
	private UserEntity writer;
	
	@Lob
	private String contents;
	private LocalDateTime createTime;
	
	public Question buildDomain() { // Domain 생성
		Question question = new Question();
		question.setId(id);
		question.setTitle(title);
		question.setContents(contents);
		question.setCreateTime(createTime);
		question.setWriter(writer.buildDomain());
		
		return question;
	}
	
	public void buildEntity(Question question) {
		
		id = question.getId();
		title = question.getTitle();
		
		UserEntity entity = new UserEntity();
		entity.buildEntity(question.getWriter());
		
		writer = entity;
		contents = question.getContents();
		createTime = question.getCreateTime();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public UserEntity getWriter() {
		return writer;
	}
	public void setWriter(UserEntity writer) {
		this.writer = writer;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public LocalDateTime getCreateTme() {
		return createTime;
	}
	public void setCreateTme(LocalDateTime createTme) {
		this.createTime = createTme;
	}
	
	
}
