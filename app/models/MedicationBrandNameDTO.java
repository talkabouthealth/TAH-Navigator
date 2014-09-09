package models;

import javax.persistence.*;

@Entity
@Table(name="medication_bn", schema="nav")
public class MedicationBrandNameDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="mbn_id")
	private Integer id;
	
	@Column(name="name")
	private String name;
}
