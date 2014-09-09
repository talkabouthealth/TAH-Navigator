package models;

import javax.persistence.*;

@Entity
@Table(name="side_effect", schema="nav")
public class SideEffectDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="se_id")
	private Integer id;
	
	@Column(name="description")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
