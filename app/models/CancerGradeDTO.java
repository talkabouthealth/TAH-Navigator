package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="cancergrademaster", schema="nav")
public class CancerGradeDTO {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="cancergrademaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="cancergrademaster_id_seq", sequenceName = "nav.cancergrademaster_id_seq")
	private Integer id;

	@Column(name="gradename")
	private String gradename;
	
	@Column(name="diseaseid")
	private Integer diseaseid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGradename() {
		return gradename;
	}

	public void setGradename(String gradename) {
		this.gradename = gradename;
	}

	public Integer getDiseaseid() {
		return diseaseid;
	}

	public void setDiseaseid(Integer diseaseid) {
		this.diseaseid = diseaseid;
	}
}