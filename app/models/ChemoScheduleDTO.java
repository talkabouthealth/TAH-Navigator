package models;

import javax.persistence.*;

@Entity
@Table(name="chemo_schedule", schema="nav")
public class ChemoScheduleDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="cs_id")
	private Integer id;
	
	@Column(name="time_period")
	private String timePeriod;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}
}
