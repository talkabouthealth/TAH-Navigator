package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import nav.dao.AppointmentMasterDAO;
@Table(name = "nav.appointment")
@javax.persistence.Entity
public class AppointmentDTO {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="appointment_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="appointment_id_seq", sequenceName = "nav.appointment_id_seq")
	@Column(name = "id")
	private int id;

	@Column(name = "purpose")
	private String purpose;

	@Column(name = "appointmenttime")
	private String appointmenttime;

	@Column(name = "appointmentdate")
	private Date appointmentdate;

	@Column(name = "appointmentcenter")
	private String appointmentcenter;

	@OneToOne
	@JoinColumn(name = "caremember")
	private UserDTO caremember;

	@OneToOne
	@JoinColumn(name = "addedby")
	private UserDTO addedby;

	@OneToOne
	@JoinColumn(name = "addressid")
	private AddressDTO addressid;

	@Column(name = "addedon")
	private Date addedon;

	@OneToOne
	@JoinColumn(name = "patientid")
	private UserDTO patientid;

	@Column(name = "deleteflag")
	private boolean deleteflag;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="appointmentid", insertable=true, updatable=true)
	private AppointmentMasterDTO appointmentid;
	
	@Transient
	private String expertMobile;
	
	@Column(name="caremember_name")
	private String careMemberName;
	
	@Column(name="purpose_text")
	private String purposeText;
	
	@Column(name="treatment_process_step")
	private String treatementStep;
	
	@Column(name="appointmentgroupid")
	private Integer appointmentgroupid;
	
	@Column(name="phone")
	private String phone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getAppointmenttime() {
		return appointmenttime;
	}

	public void setAppointmenttime(String appointmenttime) {
		this.appointmenttime = appointmenttime;
	}

	public Date getAppointmentdate() {
		return appointmentdate;
	}

	public void setAppointmentdate(Date appointmentdate) {
		this.appointmentdate = appointmentdate;
	}

	public String getAppointmentcenter() {
		return appointmentcenter;
	}

	public void setAppointmentcenter(String appointmentcenter) {
		this.appointmentcenter = appointmentcenter;
	}

	public UserDTO getCaremember() {
		return caremember;
	}

	public void setCaremember(UserDTO caremember) {
		this.caremember = caremember;
	}

	public UserDTO getAddedby() {
		return addedby;
	}

	public void setAddedby(UserDTO addedby) {
		this.addedby = addedby;
	}

	public AddressDTO getAddressid() {
		return addressid;
	}

	public void setAddressid(AddressDTO addressid) {
		this.addressid = addressid;
	}

	public Date getAddedon() {
		return addedon;
	}

	public void setAddedon(Date addedon) {
		this.addedon = addedon;
	}

	public UserDTO getPatientid() {
		return patientid;
	}

	public void setPatientid(UserDTO patientid) {
		this.patientid = patientid;
	}

	public boolean isDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(boolean deleteflag) {
		this.deleteflag = deleteflag;
	}

	public String getExpertMobile() {
		return expertMobile;
	}

	public void setExpertMobile(String expertMobile) {
		this.expertMobile = expertMobile;
	}

	public AppointmentMasterDTO getAppointmentid() {
		return appointmentid;
	}

	public void setAppointmentid(AppointmentMasterDTO appointmentid) {
		this.appointmentid = appointmentid;
	}

	public String getCareMemberName() {
		return careMemberName;
	}

	public void setCareMemberName(String careMemberName) {
		this.careMemberName = careMemberName;
	}

	public String getPurposeText() {
		return purposeText;
	}

	public void setPurposeText(String purposeText) {
		this.purposeText = purposeText;
	}

	public String getTreatementStep() {
		return treatementStep;
	}

	public void setTreatementStep(String treatementStep) {
		this.treatementStep = treatementStep;
	}

	public Integer getAppointmentgroupid() {
		return appointmentgroupid;
	}

	public void setAppointmentgroupid(Integer appointmentgroupid) {
		this.appointmentgroupid = appointmentgroupid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}