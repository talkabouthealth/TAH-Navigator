package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="pbcsvinfo", schema="nav")
public class BreastCancerInfoDTO {
	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="stage_id")
	private Integer stageId;
	
	@Column(name="brca")
	private Character brca;
	
	@Column(name="er")
	private Character er;
	
	@Column(name="pr")
	private Character pr;
	
	@Column(name="her2")
	private Character her2;
	
	@Column(name="typeid")
	private Integer typeid;	
	
	@Column(name="subtypeid")
	private Integer subtypeid;	
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="stage_id", insertable=false, updatable=false)
	private BreastCancerStageDTO bcStage;

	@OneToMany
	@JoinColumn(name="pct_id")
	private List<PatientCttSideEffectDTO> pctSeDtos;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="typeid", insertable=false, updatable=false)
	private CancerTypeDTO type;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="subtypeid", insertable=false, updatable=false)
	private CancerTypeDTO subtype;
	
	@Column(name="risklevel")
	private String risklevel;
	
	@Column(name="psascore")
	private String psascore;
	
	@Column(name="gleasonscore")
	private String gleasonscore;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="invasion", insertable=true, updatable=true)
	private CancerInvasiveDTO invasion;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="grade", insertable=true, updatable=true)
	private CancerGradeDTO grade;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="phaseid", insertable=true, updatable=true)
	private CancerPhaseDTO phase;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="fabid", insertable=true, updatable=true)
	private CancerFabClassificationDTO fabclass;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="whoid", insertable=true, updatable=true)
	private CancerWhoClassificationDTO whoclass;
	
	
	public CancerTypeDTO getType() {
		return type;
	}

	public void setType(CancerTypeDTO type) {
		this.type = type;
	}

	public CancerTypeDTO getSubtype() {
		return subtype;
	}

	public void setSubtype(CancerTypeDTO subtype) {
		this.subtype = subtype;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStageId() {
		return stageId;
	}

	public void setStageId(Integer stageId) {
		this.stageId = stageId;
	}

	public Character getBrca() {
		return brca;
	}

	public void setBrca(Character brca) {
		this.brca = brca;
	}

	public Character getEr() {
		return er;
	}

	public void setEr(Character er) {
		this.er = er;
	}

	public Character getPr() {
		return pr;
	}

	public void setPr(Character pr) {
		this.pr = pr;
	}

	public Character getHer2() {
		return her2;
	}

	public void setHer2(Character her2) {
		this.her2 = her2;
	}

	public BreastCancerStageDTO getBcStage() {
		return bcStage;
	}

	public void setBcStage(BreastCancerStageDTO bcStage) {
		this.bcStage = bcStage;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public Integer getSubtypeid() {
		return subtypeid;
	}

	public void setSubtypeid(Integer subtypeid) {
		this.subtypeid = subtypeid;
	}

	public String getRisklevel() {
		return risklevel;
	}

	public void setRisklevel(String risklevel) {
		this.risklevel = risklevel;
	}

	public String getPsascore() {
		return psascore;
	}

	public void setPsascore(String psascore) {
		this.psascore = psascore;
	}

	public String getGleasonscore() {
		return gleasonscore;
	}

	public void setGleasonscore(String gleasonscore) {
		this.gleasonscore = gleasonscore;
	}

	public CancerInvasiveDTO getInvasion() {
		return invasion;
	}

	public void setInvasion(CancerInvasiveDTO invasion) {
		this.invasion = invasion;
	}

	public CancerGradeDTO getGrade() {
		return grade;
	}

	public void setGrade(CancerGradeDTO grade) {
		this.grade = grade;
	}

	public CancerPhaseDTO getPhase() {
		return phase;
	}

	public void setPhase(CancerPhaseDTO phase) {
		this.phase = phase;
	}

	public CancerFabClassificationDTO getFabclass() {
		return fabclass;
	}

	public void setFabclass(CancerFabClassificationDTO fabclass) {
		this.fabclass = fabclass;
	}

	public CancerWhoClassificationDTO getWhoclass() {
		return whoclass;
	}

	public void setWhoclass(CancerWhoClassificationDTO whoclass) {
		this.whoclass = whoclass;
	}
}
