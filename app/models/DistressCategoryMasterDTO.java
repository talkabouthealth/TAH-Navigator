package models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "nav.distrsscategorymaster")
@javax.persistence.Entity
public class DistressCategoryMasterDTO {

	/*	CREATE TABLE nav.distrsscategorymaster(id serial NOT NULL,name character varying(250),active boolean, CONSTRAINT "distrsscategorymaster_PK" PRIMARY KEY (id) )	*/

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="distrsscategorymaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="distrsscategorymaster_id_seq", sequenceName = "nav.distrsscategorymaster_id_seq")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "active")
	private boolean active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}