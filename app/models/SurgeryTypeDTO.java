package models;

import javax.persistence.*;

@Entity
@Table(name="surgery_type", schema="nav")
public class SurgeryTypeDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="st_id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="days")
	private Integer days;

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

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

}
