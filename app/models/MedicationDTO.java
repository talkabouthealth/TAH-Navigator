package models;

import javax.persistence.*;

@Entity
@Table(name="medication", schema="nav")
public class MedicationDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="medication_id")
	private Integer id;
	
	@Column(name="mgn_id")
	private Integer mgnId;
	
	@Column(name="mbn_id")
	private Integer mbnId;
	
	@Column(name="disease_id")
	private Integer diseaseId;
	
	@OneToOne
	@JoinColumn(name="mgn_id", insertable=false, updatable=false)
	private MedicationGenNameDTO mgnDto;
	
	@OneToOne
	@JoinColumn(name="mbn_id", insertable=false, updatable=false)
	private MedicationBrandNameDTO mbnDto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMgnId() {
		return mgnId;
	}

	public void setMgnId(Integer mgnId) {
		this.mgnId = mgnId;
	}

	public Integer getMbnId() {
		return mbnId;
	}

	public void setMbnId(Integer mbnId) {
		this.mbnId = mbnId;
	}

	public Integer getDiseaseId() {
		return diseaseId;
	}

	public void setDiseaseId(Integer diseaseId) {
		this.diseaseId = diseaseId;
	}

	public MedicationGenNameDTO getMgnDto() {
		return mgnDto;
	}

	public void setMgnDto(MedicationGenNameDTO mgnDto) {
		this.mgnDto = mgnDto;
	}

	public MedicationBrandNameDTO getMbnDto() {
		return mbnDto;
	}

	public void setMbnDto(MedicationBrandNameDTO mbnDto) {
		this.mbnDto = mbnDto;
	}

}
