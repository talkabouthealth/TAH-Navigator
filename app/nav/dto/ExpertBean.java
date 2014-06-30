package nav.dto;

import models.CareTeamMemberDTO;
import models.ExpertDetailDTO;
import models.UserDetailsDTO;

public class ExpertBean {

	private UserDetailsDTO userDetails;
	private ExpertDetailDTO expertDetail;

	public UserDetailsDTO getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetailsDTO userDetails) {
		this.userDetails = userDetails;
	}
	public ExpertDetailDTO getExpertDetail() {
		return expertDetail;
	}
	public void setExpertDetail(ExpertDetailDTO expertDetail) {
		this.expertDetail = expertDetail;
	}
}