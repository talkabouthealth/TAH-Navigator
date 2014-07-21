package models;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import models.CareTeamMasterDTO;
import models.UserDTO;

@Table(name = "nav.careteammember")
@javax.persistence.Entity
public class CareTeamMemberDTO implements Serializable{

	@Id
	@Column(name = "careteamid")
	private int careteamid;
	
	@Id
	@Column(name = "memberid")
	private int memberid;
	
	@OneToOne
	@JoinColumn(name = "memberid", insertable=false, updatable=false)
	private UserDTO member;
	
	@OneToOne
	@JoinColumn(name = "careteamid", insertable=false, updatable=false)
	private CareTeamMasterDTO careteam;
	
	@Column(name = "isprimary")
	private boolean primary;

	public int getCareteamid() {
		return careteamid;
	}

	public void setCareteamid(int careteamid) {
		this.careteamid = careteamid;
	}

	public int getMemberid() {
		return memberid;
	}

	public void setMemberid(int memberid) {
		this.memberid = memberid;
	}

	public UserDTO getMember() {
		return member;
	}

	public void setMember(UserDTO member) {
		this.member = member;
	}

	public CareTeamMasterDTO getCareteam() {
		return careteam;
	}

	public void setCareteam(CareTeamMasterDTO careteam) {
		this.careteam = careteam;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

}