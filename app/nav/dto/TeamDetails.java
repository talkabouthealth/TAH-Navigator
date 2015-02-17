package nav.dto;

import java.util.ArrayList;
import java.util.List;

import models.CareTeamMasterDTO;
import models.OtherCareMemberDTO;

public class TeamDetails {
	private CareTeamMasterDTO careTeam;
	private List<ExpertBean> experts;
	private ExpertBean primaryExpert;
	private OtherCareMemberDTO otherPrimaryCareMember;
	private List<OtherCareMemberDTO> otherCareMembers;
	public CareTeamMasterDTO getCareTeam() {
		return careTeam;
	}
	public void setCareTeam(CareTeamMasterDTO careTeam) {
		this.careTeam = careTeam;
	}
	public List<ExpertBean> getExperts() {
		return experts;
	}
	public void setExperts(List<ExpertBean> experts) {
		this.experts = experts;
	}
	public ExpertBean getPrimaryExpert() {
		return primaryExpert;
	}
	public void setPrimaryExpert(ExpertBean primaryExpert) {
		this.primaryExpert = primaryExpert;
	}
	public OtherCareMemberDTO getOtherPrimaryCareMember() {
		return otherPrimaryCareMember;
	}
	public void setOtherPrimaryCareMember(OtherCareMemberDTO otherPrimaryCareMember) {
		this.otherPrimaryCareMember = otherPrimaryCareMember;
	}
	public List<OtherCareMemberDTO> getOtherCareMembers() {
		return otherCareMembers;
	}
	public void setOtherCareMembers(List<OtherCareMemberDTO> otherCareMembers) {
		this.otherCareMembers = otherCareMembers;
	}
}
