package models;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="pconcern", schema="nav")
public class PatientConcernDTO {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="pconcern_pconcern_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="pconcern_pconcern_id_seq", sequenceName = "nav.pconcern_pconcern_id_seq")	
	@Column(name="pconcern_id")
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="concern")
	private String concern;
	
	@Column(name="next_step")
	private String nextStep;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="concern_date")
	private Date concernDate;

	@Transient
	private String infoText;
	
	@Transient
	private String tipType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getConcern() {
		return concern;
	}

	public void setConcern(String concern) {
		this.concern = concern;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getConcernDate() {
		return concernDate;
	}

	public void setConcernDate(Date concernDate) {
		this.concernDate = concernDate;
	}

	public String getInfoText() {
		return infoText;
	}

	public void setInfoText(String infoText) {
		this.infoText = infoText;
	}
	public String getTipType() {
		return tipType;
	}

	public void setTipType(String tipType) {
		this.tipType = tipType;
	}
}
