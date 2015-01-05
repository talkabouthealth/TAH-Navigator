package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="cancerwhoclassificationmaster", schema="nav")
public class CancerWhoClassificationDTO {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="cancerwhoclassificationmaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="cancerwhoclassificationmaster_id_seq", sequenceName = "nav.cancerwhoclassificationmaster_id_seq")
	private Integer id;

	@Column(name="whoname")
	private String whoname;
	
	@Column(name="diseaseid")
	private Integer diseaseid;

	@Column(name="user_defined")
	private boolean userDefined;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWhoname() {
		return whoname;
	}

	public void setWhoname(String whoname) {
		this.whoname = whoname;
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