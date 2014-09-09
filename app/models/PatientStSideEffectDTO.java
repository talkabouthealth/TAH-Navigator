package models;

import javax.persistence.*;

@Entity
@Table(name="patientses", schema="nav")
public class PatientStSideEffectDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pse_id")
	private Integer id;
	
	@Column(name="psi_id")
	private Integer psiId;
	
	@Column(name="se_id")
	private Integer seId;
	
	@OneToOne
	@JoinColumn(name="se_id", insertable=false, updatable=false)
	private SideEffectDTO seDto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPsiId() {
		return psiId;
	}

	public void setPsiId(Integer psiId) {
		this.psiId = psiId;
	}

	public Integer getSeId() {
		return seId;
	}

	public void setSeId(Integer seId) {
		this.seId = seId;
	}

	public SideEffectDTO getSeDto() {
		return seDto;
	}

	public void setSeDto(SideEffectDTO seDto) {
		this.seDto = seDto;
	}
}
