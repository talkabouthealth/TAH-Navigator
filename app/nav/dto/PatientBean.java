package nav.dto;

import models.PatientDetailDTO;
import models.UserDetailsDTO;

public class PatientBean {
	
	private UserDetailsDTO userDetails;
	private PatientDetailDTO patientOtherDetails;

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
}