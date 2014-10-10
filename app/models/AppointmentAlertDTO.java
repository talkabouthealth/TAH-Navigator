package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="appointmentalert", schema="nav")
public class AppointmentAlertDTO {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="appointment_id")
	private Integer appointmentId;
	
	@Column(name="alert_thru")
	private String alertThru;
	
	@Column(name="alert_detail")
	private String alertDetail;
	
	@Column(name="alert_sent")
	private Date alertSent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getAlertThru() {
		return alertThru;
	}

	public void setAlertThru(String alertThru) {
		this.alertThru = alertThru;
	}

	public String getAlertDetail() {
		return alertDetail;
	}

	public void setAlertDetail(String alertDetail) {
		this.alertDetail = alertDetail;
	}

	public Date getAlertSent() {
		return alertSent;
	}

	public void setAlertSent(Date alertSent) {
		this.alertSent = alertSent;
	}
	
	
}
