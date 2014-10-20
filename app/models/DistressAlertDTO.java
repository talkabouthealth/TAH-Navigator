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
@Table(name="distressalert", schema="nav")
public class DistressAlertDTO {
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="distressalert_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="distressalert_id_seq", sequenceName = "nav.distressalert_id_seq")
	@Column(name="id")
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
		
	@Column(name="alert_detail")
	private String alertDetail;
	
	@Column(name="alert_sent")
	private Date alertSent;
	
	@Column(name="alert_thru")
	private String alertThru;
	
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
	public String getAlertThru() {
		return alertThru;
	}
	public void setAlertThru(String alertThru) {
		this.alertThru = alertThru;
	}
	
	
}
