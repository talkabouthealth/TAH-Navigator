package models;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: InvitedDTO
 *
 */
@Table(name = "nav.invited")
@javax.persistence.Entity
public class InvitedDTO implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "password")
	private String password;

	@Column(name = "usertype")
	private char userType;

	@Column(name = "activateonsignup")
	private boolean activateOnSignup;

	@Column(name = "timestamp")
	private Date timestamp;

	@Column(name = "invitationsent")
	private boolean isInvitationSent;

	public InvitedDTO() {
	}

	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}   
	public char getUserType() {
		return this.userType;
	}

	public void setUserType(char userType) {
		this.userType = userType;
	}   
	public boolean getActivateOnSignup() {
		return this.activateOnSignup;
	}

	public void setActivateOnSignup(boolean activateOnSignup) {
		this.activateOnSignup = activateOnSignup;
	}   
	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}   
	public boolean getIsInvitationSent() {
		return this.isInvitationSent;
	}

	public void setIsInvitationSent(boolean isInvitationSent) {
		this.isInvitationSent = isInvitationSent;
	}
}