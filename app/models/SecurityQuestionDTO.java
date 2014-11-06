package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "nav.securityquestionmaster")
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SecurityQuestionDTO implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="securityquestionmaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="securityquestionmaster_id_seq", sequenceName = "nav.securityquestionmaster_id_seq")
	@Column(name = "id")
	private Long id;

	@Column(name = "question")
	private String question;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
}