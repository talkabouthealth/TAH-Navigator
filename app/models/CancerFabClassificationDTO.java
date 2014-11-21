package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="cancerfabclassificationmaster", schema="nav")
public class CancerFabClassificationDTO {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="cancerfabclassificationmaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="cancerfabclassificationmaster_id_seq", sequenceName = "nav.cancerfabclassificationmaster_id_seq")
	private Integer id;

	@Column(name="fabname")
	private String fabname;
	
	@Column(name="diseaseid")
	private Integer diseaseid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFabname() {
		return fabname;
	}

	public void setFabname(String fabname) {
		this.fabname = fabname;
	}

	public Integer getDiseaseid() {
		return diseaseid;
	}

	public void setDiseaseid(Integer diseaseid) {
		this.diseaseid = diseaseid;
	}
}