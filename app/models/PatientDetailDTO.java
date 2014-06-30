package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "nav.patientdetails")
@javax.persistence.Entity
public class PatientDetailDTO {

	@Id
	@Column(name = "id")
	private int id;

	@OneToOne
	@JoinColumn(name = "id")
	private UserDTO user;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "disease")
	private DiseaseMasterDTO disease;

	@Column(name = "dateofdiagnosis")
	private Date dateofdiagnosis;

	@Column(name = "stage")
	private String stage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public DiseaseMasterDTO getDisease() {
		return disease;
	}

	public void setDisease(DiseaseMasterDTO disease) {
		this.disease = disease;
	}

	public Date getDateofdiagnosis() {
		return dateofdiagnosis;
	}

	public void setDateofdiagnosis(Date dateofdiagnosis) {
		this.dateofdiagnosis = dateofdiagnosis;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}
}