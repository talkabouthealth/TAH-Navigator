package models;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="pfupci", schema="nav")
public class PatientFollowUpCareItemDTO {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="pfupci_pfup_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="pfupci_pfup_id_seq", sequenceName = "nav.pfupci_pfup_id_seq")	
	@Column(name="pfup_id")
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="activity")
	private String activity;
	
	@Column(name="frequency")
	private String frequency;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="ongoing")
	private String ongoing;
	
	@Column(name="purpose")
	private String purpose;
	
	@Column(name="doctor")
	private String doctor;
	
	private String infoText;

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

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getOngoing() {
		return ongoing;
	}

	public void setOngoing(String ongoing) {
		this.ongoing = ongoing;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getInfoText() {
		return infoText;
	}

	public void setInfoText(String infoText) {
		this.infoText = infoText;
	}
}
