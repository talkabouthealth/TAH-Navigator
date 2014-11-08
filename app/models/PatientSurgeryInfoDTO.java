package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="patientsi", schema="nav")
public class PatientSurgeryInfoDTO {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="patientsi_psi_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="patientsi_psi_id_seq", sequenceName = "nav.patientsi_psi_id_seq")
	@Column(name="psi_id")
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="st_id")
	private Integer stId;
	
	@Column(name="surgery_date")
	private Date surgeryDate;
	
	@Column(name="tr_id")
	private Integer trId;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="doctor")
	private String doctor;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="st_id", insertable=false, updatable=false)
	private SurgeryTypeDTO stDto;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tr_id", insertable=false, updatable=false)
	private TreatmentRegionDTO trDto;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="psi_id")
	private List<PatientStSideEffectDTO> pstSeDtos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStId() {
		return stId;
	}

	public void setStId(Integer stId) {
		this.stId = stId;
	}

	public Date getSurgeryDate() {
		return surgeryDate;
	}

	public void setSurgeryDate(Date surgeryDate) {
		this.surgeryDate = surgeryDate;
	}

	public Integer getTrId() {
		return trId;
	}

	public void setTrId(Integer trId) {
		this.trId = trId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public SurgeryTypeDTO getStDto() {
		return stDto;
	}

	public void setStDto(SurgeryTypeDTO stDto) {
		this.stDto = stDto;
	}

	public TreatmentRegionDTO getTrDto() {
		return trDto;
	}

	public void setTrDto(TreatmentRegionDTO trDto) {
		this.trDto = trDto;
	}

	public List<PatientStSideEffectDTO> getPstSeDtos() {
		return pstSeDtos;
	}

	public void setPstSeDtos(List<PatientStSideEffectDTO> pstSeDtos) {
		this.pstSeDtos = pstSeDtos;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	
	
	
}
