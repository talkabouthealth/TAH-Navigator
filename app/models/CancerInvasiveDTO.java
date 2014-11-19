package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="cancerinvasivemaster", schema="nav")
public class CancerInvasiveDTO {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="cancerinvasivemaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="cancerinvasivemaster_id_seq", sequenceName = "nav.cancerinvasivemaster_id_seq")
	private Integer id;

	@Column(name="invname")
	private String invname;
	
	@Column(name="diseaseid")
	private Integer diseaseid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInvname() {
		return invname;
	}

	public void setInvname(String invname) {
		this.invname = invname;
	}

	public Integer getDiseaseid() {
		return diseaseid;
	}

	public void setDiseaseid(Integer diseaseid) {
		this.diseaseid = diseaseid;
	}
}