package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="bcsinfo", schema="nav")
public class BreastCancerStageDTO {
	@Id
	@Column(name="stage_id")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="bcsinfo_stage_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="bcsinfo_stage_id_seq", sequenceName = "nav.bcsinfo_stage_id_seq")
	private Integer id;
	@Column(name="diseaseid")
	private Integer diseaseid;

	@Column(name="stage")
	private String name;

	@Column(name="user_defined")
	private boolean userDefined;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDiseaseid() {
		return diseaseid;
	}
	public void setDiseaseid(Integer diseaseid) {
		this.diseaseid = diseaseid;
	}

	public boolean isUserDefined() {
		return userDefined;
	}

	public void setUserDefined(boolean userDefined) {
		this.userDefined = userDefined;
	}
}
