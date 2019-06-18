package idu.cs.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import idu.cs.domain.Answer;
import idu.cs.domain.Question;
import idu.cs.domain.User;


@Entity
@Table(name = "answer") // table이름을 지정할 때 사용
public class AnswerEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	//  database에서 sequence number, auto increment => primary key 역할


	@ManyToOne
	@JoinColumn(name="fk_anwer_writer")
	private UserEntity writer;
	
	@OneToMany
	@JoinColumn(name="fk_anwer_writer")
	private QuestionEntity question;
	
	@Lob
	private String contents;
	private LocalDateTime createTime;
	
	public Answer buildDomain() { // Domain 생성
		Answer answer = new Answer();
		answer.setId(id);
		answer.setContents(contents);
		answer.setCreateTime(createTime);
		answer.setWriter(writer.buildDomain());
		answer.setQuestion(question.buildDomain());

		return answer;
	}
	
	public void buildEntity(Question question) {
		
		id = question.getId();
		
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
