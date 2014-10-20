package models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "nav.designationmaster")
@javax.persistence.Entity
public class DesignationMasterDTO {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="designationmaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="designationmaster_id_seq", sequenceName = "nav.designationmaster_id_seq")
	private int id;
	
	@Column(name = "abbr")
	private String abbr;
	
	@Column(name = "designation")
	private String designation;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
}