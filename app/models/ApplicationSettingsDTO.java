package models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "nav.applicationsettings")
@javax.persistence.Entity
public class ApplicationSettingsDTO {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="applicationsettings_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="applicationsettings_id_seq", sequenceName = "nav.applicationsettings_id_seq")
	@Column(name = "id")
	private int id;
	
	@Column(name = "propertyname")
	private String propertyname;
	
	@Column(name = "propertyvalue")
	private String propertyvalue;
	
	@Column(name = "propertytype")
	private String propertytype;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPropertyname() {
		return propertyname;
	}

	public void setPropertyname(String propertyname) {
		this.propertyname = propertyname;
	}

	public String getPropertyvalue() {
		return propertyvalue;
	}

	public void setPropertyvalue(String propertyvalue) {
		this.propertyvalue = propertyvalue;
	}

	public String getPropertytype() {
		return propertytype;
	}

	public void setPropertytype(String propertytype) {
		this.propertytype = propertytype;
	}
}