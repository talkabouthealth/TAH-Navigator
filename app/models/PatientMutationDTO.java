package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="patientmutation", schema="nav")
@IdClass(PatientMutationPK.class)
public class PatientMutationDTO {

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="patientid", insertable=false, updatable=false)
	private UserDTO pDto;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="mutationid", insertable=false, updatable=false)
	private CancerMutationDTO pmDto;
	
	@Id
	private Integer patientid;

	@Id
	private Integer mutationid;
	
	public Integer getPatientid() {
		return patientid;
	}

	public void setPatientid(Integer patientid) {
		this.patientid = patientid;
	}

	public Integer getMutationid() {
		return mutationid;
	}

	public void setMutationid(Integer mutationid) {
		this.mutationid = mutationid;
	}


	public UserDTO getpDto() {
		return pDto;
	}

	public void setpDto(UserDTO pDto) {
		this.pDto = pDto;
	}

	public CancerMutationDTO getPmDto() {
		return pmDto;
	}

	public void setPmDto(CancerMutationDTO pmDto) {
		this.pmDto = pmDto;
	}

	
}