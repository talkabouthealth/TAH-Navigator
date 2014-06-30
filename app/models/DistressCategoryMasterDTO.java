package models;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "nav.distrsscategorymaster")
@javax.persistence.Entity
public class DistressCategoryMasterDTO {

	/*	CREATE TABLE nav.distrsscategorymaster(id serial NOT NULL,name character varying(250),active boolean, CONSTRAINT "distrsscategorymaster_PK" PRIMARY KEY (id) )	*/

	@Id
	@Column(name = "id")
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