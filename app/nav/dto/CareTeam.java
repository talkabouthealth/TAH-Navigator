package nav.dto;

import java.util.List;

import models.CareTeamMasterDTO;
import models.PatienCareTeamDTO;

public class CareTeam {
	private PatienCareTeamDTO careTeamDto;
	private List<ExpertBean> experts;
	private ExpertBean leadExpert;
	
	
	public List<ExpertBean> getExperts() {
		return experts;
	}
	public void setExperts(List<ExpertBean> experts) {
		this.experts = experts;
	}
	public PatienCareTeamDTO getCareTeamDto() {
		return careTeamDto;
	}
	public void setCareTeamDto(PatienCareTeamDTO careTeamDto) {
		this.careTeamDto = careTeamDto;
	}
	public ExpertBean getLeadExpert() {
		return leadExpert;
	}
	public void setLeadExpert(ExpertBean leadExpert) {
		this.leadExpert = leadExpert;
	}
}
