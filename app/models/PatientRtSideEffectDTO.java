package models;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="patientrtse", schema="nav")
public class PatientRtSideEffectDTO {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pse_id")
	private Integer id;
	
	@Column(name="prt_id")
	private Integer prtId;
	
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

	public Integer getPrtId() {
		return prtId;
	}

	public void setPrtId(Integer prtId) {
		this.prtId = prtId;
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
