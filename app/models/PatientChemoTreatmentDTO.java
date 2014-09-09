package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="patientctt", schema="nav")
public class PatientChemoTreatmentDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pct_id")
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="medication_id")
	private Integer medicationId;
	
	@Column(name="cycle_no")
	private Integer cycleNo;
	
	@Column(name="cs_id")
	private Integer csId;
	
	@Column(name="dose_reduction")
	private Integer doseReduction;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="notes")
	private String notes;
	
	
	@OneToOne
	@JoinColumn(name="medication_id", insertable=false, updatable=false)
	private MedicationDTO medDto;
	
	@OneToOne
	@JoinColumn(name="cs_id", insertable=false, updatable=false)
	private ChemoScheduleDTO csDto;
	
	@OneToMany
	@JoinColumn(name="pct_id")
	private List<PatientCttSideEffectDTO> pctSeDtos;

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

	public Integer getMedicationId() {
		return medicationId;
	}

	public void setMedicationId(Integer medicationId) {
		this.medicationId = medicationId;
	}

	public Integer getCycleNo() {
		return cycleNo;
	}

	public void setCycleNo(Integer cycleNo) {
		this.cycleNo = cycleNo;
	}

	public Integer getCsId() {
		return csId;
	}

	public void setCsId(Integer csId) {
		this.csId = csId;
	}

	public Integer getDoseReduction() {
		return doseReduction;
	}

	public void setDoseReduction(Integer doseReduction) {
		this.doseReduction = doseReduction;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public MedicationDTO getMedDto() {
		return medDto;
	}

	public void setMedDto(MedicationDTO medDto) {
		this.medDto = medDto;
	}

	public ChemoScheduleDTO getCsDto() {
		return csDto;
	}

	public void setCsDto(ChemoScheduleDTO csDto) {
		this.csDto = csDto;
	}

	public List<PatientCttSideEffectDTO> getPctSeDtos() {
		return pctSeDtos;
	}

	public void setPctSeDtos(List<PatientCttSideEffectDTO> pctSeDtos) {
		this.pctSeDtos = pctSeDtos;
	}
}
