package models;

import javax.persistence.*;

@Entity
@Table(name="radiation_schedule", schema="nav")
public class RadiationScheduleDTO {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="radiation_schedule_rs_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="radiation_schedule_rs_id_seq", sequenceName = "nav.radiation_schedule_rs_id_seq")
	@Column(name="rs_id")
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
