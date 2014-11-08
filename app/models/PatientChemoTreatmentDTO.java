package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="patientctt", schema="nav")
public class PatientChemoTreatmentDTO {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="patientctt_pct_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="patientctt_pct_id_seq", sequenceName = "nav.patientctt_pct_id_seq")	
	@Column(name="pct_id")
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="mgn")
	private String genericName;
	
	@Column(name="mbn")
	private String brandName;
	
	@Column(name="cycle_no")
	private Integer cycleNo;
	
	@Column(name="cs_id")
	private Integer csId;
	
	@Column(name="dose_reduction")
	private String doseReduction;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="doctor")
	private String doctor;
	
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

	public String getGenericName() {
		return genericName;
	}

	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getDoseReduction() {
		return doseReduction;
	}

	public void setDoseReduction(String doseReduction) {
		this.doseReduction = doseReduction;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
}
