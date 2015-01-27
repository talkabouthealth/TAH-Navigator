package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="notifications", schema="nav")
public class NotificationDTO {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="appointmentalert_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="appointmentalert_id_seq", sequenceName = "nav.appointmentalert_id_seq")
	@Column(name="id")
	private Integer id;
	
	@Column(name="category")
	private String category;
	
	@Column(name="related_id")
	private Integer relatedId;
	
	@Column(name="communication")
	private Integer communication;
	
	@Column(name="description")
	private String description;
	
	@Column(name="scheduled_time")
	private Date scheduledTime;
	
	@Column(name="notified_to")
	private Integer notifiedTo;
	
	@Column(name="notified")
	private Boolean notified;
	
	@Column(name="discard")
	private Boolean discard;
	
	@Column(name="priority")
	private Integer priority;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public Integer getCommunication() {
		return communication;
	}

	public void setCommunication(Integer communication) {
		this.communication = communication;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public Integer getNotifiedTo() {
		return notifiedTo;
	}

	public void setNotifiedTo(Integer notifiedTo) {
		this.notifiedTo = notifiedTo;
	}

	public Boolean getNotified() {
		return notified;
	}

	public void setNotified(Boolean notified) {
		this.notified = notified;
	}

	public Boolean getDiscard() {
		return discard;
	}

	public void setDiscard(Boolean discard) {
		this.discard = discard;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	
}
