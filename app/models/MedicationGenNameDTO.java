package models;

import javax.persistence.*;

@Entity
@Table(name="medication_gn", schema="nav")
public class MedicationGenNameDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="mgn_id")
	private Integer id;
	
	@Column(name="name")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
