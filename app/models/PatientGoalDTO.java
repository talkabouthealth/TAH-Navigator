package models;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="pgoal", schema="nav")
public class PatientGoalDTO {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="pgoal_pgoal_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="pgoal_pgoal_id_seq", sequenceName = "nav.pgoal_pgoal_id_seq")
	@Column(name = "pgoal_id")
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="goal")
	private String goal;
	
	@Column(name="next_step")
	private String nextStep;
	
	@Column(name="goal_deadline")
	private Date goalDeadline;
	
	@Column(name="notes")
	private String notes;

	private String infoText;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public Date getGoalDeadline() {
		return goalDeadline;
	}

	public void setGoalDeadline(Date goalDeadline) {
		this.goalDeadline = goalDeadline;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getInfoText() {
		return infoText;
	}

	public void setInfoText(String infoText) {
		this.infoText = infoText;
	}
	
}
