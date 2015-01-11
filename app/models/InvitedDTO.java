package models;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: InvitedDTO
 *
 */
@Table(name = "nav.invited")
@javax.persistence.Entity
public class InvitedDTO implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="invited_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="invited_id_seq", sequenceName = "nav.invited_id_seq")
	@Column(name = "id")
	private int id;

	@Column(name = "email")
	private String email;

	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;

	@Column(name="purpose_text")
	private String purposeText;
	
	@Column(name="treatment_process_step")
	private String treatementStep;
	
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

	@Column(name = "activateonsignup")
	private boolean activateOnSignup;

	@Column(name = "invitationsent")
	private boolean isInvitationSent;

	@Column(name = "addedon")
	private Date addedon;
	
	@Column(name="caremember_name")
	private String careMemberName;
	
	@Column(name = "purpose")
	private String purpose;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="appointmentid", insertable=true, updatable=true)
	private AppointmentMasterDTO appointmentid;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "communication_method")
	private String communicationType;

	public InvitedDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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

	public boolean isActivateOnSignup() {
		return activateOnSignup;
	}

	public void setActivateOnSignup(boolean activateOnSignup) {
		this.activateOnSignup = activateOnSignup;
	}

	public boolean isInvitationSent() {
		return isInvitationSent;
	}

	public void setInvitationSent(boolean isInvitationSent) {
		this.isInvitationSent = isInvitationSent;
	}

	public Date getAddedon() {
		return addedon;
	}

	public void setAddedon(Date addedon) {
		this.addedon = addedon;
	}

	public String getCareMemberName() {
		return careMemberName;
	}

	public void setCareMemberName(String careMemberName) {
		this.careMemberName = careMemberName;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public AppointmentMasterDTO getAppointmentid() {
		return appointmentid;
	}

	public void setAppointmentid(AppointmentMasterDTO appointmentid) {
		this.appointmentid = appointmentid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(String communicationType) {
		this.communicationType = communicationType;
	}
	
}