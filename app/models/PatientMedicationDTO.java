package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "nav.patientmedication")
@javax.persistence.Entity
public class PatientMedicationDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

//	@Insert(insert="false" update="false")
	@Column(name = "patientid", insertable = false, updatable = false)
	private int patientid;
	
	@OneToOne
	@JoinColumn(name = "patientid")
	private UserDTO patient;

	@OneToOne
	@JoinColumn(name = "medicineid")
	private MedicineMasterDTO medicine;

	@OneToOne
	@JoinColumn(name = "carememberid")
	private UserDTO caremember;

	@Column(name = "frequency")
	private String frequency;

	@Column(name = "specialinstruction")
	private String specialinstruction;

	@Column(name = "method")
	private String method;

	@Column(name = "adddate")
	private Date adddate;	

	@Column(name = "startdate")
	private Date startdate;		

	@Column(name = "enddate")
	private Date enddate;		

	@Column(name = "active")
	private boolean active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserDTO getPatient() {
		return patient;
	}

	public void setPatient(UserDTO patient) {
		this.patient = patient;
	}

	public MedicineMasterDTO getMedicine() {
		return medicine;
	}

	public void setMedicine(MedicineMasterDTO medicine) {
		this.medicine = medicine;
	}

	public UserDTO getCaremember() {
		return caremember;
	}

	public void setCaremember(UserDTO caremember) {
		this.caremember = caremember;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getSpecialinstruction() {
		return specialinstruction;
	}

	public void setSpecialinstruction(String specialinstruction) {
		this.specialinstruction = specialinstruction;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Date getAdddate() {
		return adddate;
	}

	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getPatientid() {
		return patientid;
	}

	public void setPatientid(int patientid) {
		this.patientid = patientid;
	}
}