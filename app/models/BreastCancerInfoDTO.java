package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="stage_id", insertable=false, updatable=false)
	private BreastCancerStageDTO bcStage;

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

}
