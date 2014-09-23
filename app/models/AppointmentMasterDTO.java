package models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "nav.appointmentmaster")
@javax.persistence.Entity

public class AppointmentMasterDTO {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="appointmentmaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="appointmentmaster_id_seq", sequenceName = "nav.appointmentmaster_id_seq")
	@Column(name = "id")
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