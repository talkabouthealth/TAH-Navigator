package models;

import javax.persistence.*;

@Entity
@Table(name="patientcttse", schema="nav")
public class PatientCttSideEffectDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pse_id")
	private Integer id;
	
	@Column(name="pct_id")
	private Integer pctId;
	
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

	public Integer getPctId() {
		return pctId;
	}

	public void setPctId(Integer pctId) {
		this.pctId = pctId;
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
