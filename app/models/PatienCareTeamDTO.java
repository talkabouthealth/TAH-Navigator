package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import models.CareTeamMasterDTO;
import models.UserDTO;

@Table(name = "nav.patiencareteam")
@javax.persistence.Entity
public class PatienCareTeamDTO implements Serializable {

	@Id
	@Column(name = "careteamid")
	private int careteamid;
	
	@Id
	@Column(name = "patienid")
	private int patienid;
	
	@OneToOne
	@JoinColumn(name = "patienid")
	private UserDTO patien;
	
	@OneToOne
	@JoinColumn(name = "careteamid")
	private CareTeamMasterDTO careteam;
	
	@Column(name = "isdeleted")
	private boolean deleted;

	public int getCareteamid() {
		return careteamid;
	}

	public void setCareteamid(int careteamid) {
		this.careteamid = careteamid;
	}

	public int getPatienid() {
		return patienid;
	}

	public void setPatienid(int patienid) {
		this.patienid = patienid;
	}

	public UserDTO getPatien() {
		return patien;
	}

	public void setPatien(UserDTO patien) {
		this.patien = patien;
	}

	public CareTeamMasterDTO getCareteam() {
		return careteam;
	}

	public void setCareteam(CareTeamMasterDTO careteam) {
		this.careteam = careteam;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}