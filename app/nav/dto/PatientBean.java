package nav.dto;

import models.PatientDetailDTO;
import models.PatientDistressDTO;
import models.UserDetailsDTO;

public class PatientBean {
	
	private UserDetailsDTO userDetails;
	private PatientDetailDTO patientOtherDetails;
	private DistressBean distress;

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
}