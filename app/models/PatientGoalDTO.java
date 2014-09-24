package models;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="pgoal", schema="nav")
public class PatientGoalDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pgoal_id")
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
}
