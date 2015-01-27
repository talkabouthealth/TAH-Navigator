package nav.dto;

public class TemplateVars {	
	private String email;
	private String userName;
	private String signupURL;
	private String distressCheckInURL;
	private String clinicPhone;
	private String clinicAddress;
	private String doctorName;
	private String appointmentDate;
	private String appointmentTime;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSignupURL() {
		return signupURL;
	}
	public void setSignupURL(String signupURL) {
		this.signupURL = signupURL;
	}
	public String getClinicPhone() {
		return clinicPhone;
	}
	public void setClinicPhone(String clinicPhone) {
		this.clinicPhone = clinicPhone;
	}
	public String getClinicAddress() {
		return clinicAddress;
	}
	public void setClinicAddress(String clinicAddress) {
		this.clinicAddress = clinicAddress;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public String getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDistressCheckInURL() {
		return distressCheckInURL;
	}
	public void setDistressCheckInURL(String distressCheckInURL) {
		this.distressCheckInURL = distressCheckInURL;
	}		
}
