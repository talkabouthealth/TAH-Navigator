package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "nav.contactmethodmaster")
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ContactTypeDTO implements Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="contactmethodmaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="contactmethodmaster_id_seq", sequenceName = "nav.contactmethodmaster_id_seq")
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