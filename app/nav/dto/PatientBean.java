package nav.dto;

import models.AppointmentDTO;
import models.BreastCancerInfoDTO;
import models.NoteDTO;
import models.PatientDetailDTO;
import models.PatientDistressDTO;
import models.UserDetailsDTO;

public class PatientBean {
	
	private UserDetailsDTO userDetails;
	private PatientDetailDTO patientOtherDetails;
	private DistressBean distress;
	private BreastCancerInfoDTO breastCancerInfo;
	private AppointmentDTO appointmentInfo;
	private NoteDTO note;
	
	public UserDetailsDTO getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetailsDTO userDetails) {
		this.userDetails = userDetails;
	}
	public PatientDetailDTO getPatientOtherDetails() {
		return patientOtherDetails;
	}
	public void setPatientOtherDetails(PatientDetailDTO patientOtherDetails) {
		this.patientOtherDetails = patientOtherDetails;
	}
	public DistressBean getDistress() {
		return distress;
	}
	public void setDistress(DistressBean distress) {
		this.distress = distress;
	}
	public BreastCancerInfoDTO getBreastCancerInfo() {
		return breastCancerInfo;
	}
	public void setBreastCancerInfo(BreastCancerInfoDTO breastCancerInfo) {
		this.breastCancerInfo = breastCancerInfo;
	}
	public AppointmentDTO getAppointmentInfo() {
		return appointmentInfo;
	}
	public void setAppointmentInfo(AppointmentDTO appointmentInfo) {
		this.appointmentInfo = appointmentInfo;
	}
	public NoteDTO getNote() {
		return note;
	}
	public void setNote(NoteDTO note) {
		this.note = note;
	}
}