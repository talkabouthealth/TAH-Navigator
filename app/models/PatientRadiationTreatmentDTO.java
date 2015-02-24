package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="patientrt", schema="nav")
public class PatientRadiationTreatmentDTO {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="patientrt_prt_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="patientrt_prt_id_seq", sequenceName = "nav.patientrt_prt_id_seq")
	@Column(name="prt_id")
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="rt_id")
	private Integer rtId;
	
	@Column(name="dose")
	private String dose;
	
	@Column(name="total_dose")
	private String totalDose;
	
	@Column(name="rs_id")
	private Integer rsId;
	
	@Column(name="tr_id")
	private Integer trId;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="doctor")
	private String doctor;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "rt_id", insertable=false, updatable=false)
	private RadiationTypeDTO rtDto;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "rs_id", insertable=false, updatable=false)
	private RadiationScheduleDTO rsDto;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "tr_id", insertable=false, updatable=false)
	private TreatmentRegionDTO trDto;
	
	@OneToMany
	@JoinColumn(name="prt_id")
	private List<PatientRtSideEffectDTO> prtSeDtos;

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

	public Integer getRtId() {
		return rtId;
	}

	public void setRtId(Integer rtId) {
		this.rtId = rtId;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getTotalDose() {
		return totalDose;
	}

	public void setTotalDose(String totalDose) {
		this.totalDose = totalDose;
	}
	
	public Integer getRsId() {
		return rsId;
	}

	public void setRsId(Integer rsId) {
		this.rsId = rsId;
	}

	public Integer getTrId() {
		return trId;
	}

	public void setTrId(Integer trId) {
		this.trId = trId;
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

	public RadiationTypeDTO getRtDto() {
		return rtDto;
	}

	public void setRtDto(RadiationTypeDTO rtDto) {
		this.rtDto = rtDto;
	}

	public RadiationScheduleDTO getRsDto() {
		return rsDto;
	}

	public void setRsDto(RadiationScheduleDTO rsDto) {
		this.rsDto = rsDto;
	}

	public TreatmentRegionDTO getTrDto() {
		return trDto;
	}

	public void setTrDto(TreatmentRegionDTO trDto) {
		this.trDto = trDto;
	}

	public List<PatientRtSideEffectDTO> getPrtSeDtos() {
		return prtSeDtos;
	}

	public void setPrtSeDtos(List<PatientRtSideEffectDTO> prtSeDtos) {
		this.prtSeDtos = prtSeDtos;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

}
