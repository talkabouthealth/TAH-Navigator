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
@Table(name="patientchromosome", schema="nav")
@IdClass(PatientChromosomePK.class)
public class PatientChromosomeDTO {

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="patientid", insertable=false, updatable=false)
	private UserDTO pDto;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="chromosomeid", insertable=false, updatable=false)
	private CancerChromosomeDTO pmDto;
	
	@Id
	private Integer patientid;

	@Id
	private Integer chromosomeid;
	
	public Integer getPatientid() {
		return patientid;
	}

	public void setPatientid(Integer patientid) {
		this.patientid = patientid;
	}

	public UserDTO getpDto() {
		return pDto;
	}

	public void setpDto(UserDTO pDto) {
		this.pDto = pDto;
	}

	public CancerChromosomeDTO getPmDto() {
		return pmDto;
	}

	public void setPmDto(CancerChromosomeDTO pmDto) {
		this.pmDto = pmDto;
	}
	public Integer getChromosomeid() {
		return chromosomeid;
	}

	public void setChromosomeid(Integer chromosomeid) {
		this.chromosomeid = chromosomeid;
	}
}