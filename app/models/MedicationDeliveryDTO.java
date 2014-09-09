package models;

import javax.persistence.*;

@Entity
@Table(name="medication_delivery", schema="nav")
public class MedicationDeliveryDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="md_id")
	private Integer id;
	
	@Column(name="delivery")
	private String delivery;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
}
