package models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "nav.careplanprint")
@javax.persistence.Entity
public class CarePlanPrintDTO {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="careplanprint_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="careplanprint_id_seq", sequenceName = "nav.careplanprint_id_seq")
	@Column(name = "id")
	private int id;
	
	@Column(name = "expertid")
	private int expertId;
	
	@Column(name = "patientid")
	private int patientId;

	@Column(name = "issuedate")
	private Date issueDate;
	
	@Column(name = "note")
	private String note ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getExpertId() {
		return expertId;
	}

	public void setExpertId(int expertId) {
		this.expertId = expertId;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public void setIssueDate(Date issuedDate) {
		this.issueDate = issuedDate;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
