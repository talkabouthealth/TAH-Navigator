package models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Table(name = "nav.diseasemaster")
@javax.persistence.Entity
public class DiseaseMasterDTO {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="diseasemaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="diseasemaster_id_seq", sequenceName = "nav.diseasemaster_id_seq")
	private int id;

	@Column(name = "name")
	private String name;

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
}