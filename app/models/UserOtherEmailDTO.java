package models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "nav.userotheremail")
@javax.persistence.Entity
public class UserOtherEmailDTO {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@OneToOne
	@JoinColumn(name = "userid")
	private UserDTO user;

	@Column(name = "email")
	private String email;

	@Column(name = "verificationcode")
	@org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
	private UUID verificationcode;

	@Column(name = "adddate")
	private Date addDate;

	@Column(name = "active")
	private boolean active;

	@Column(name = "isprimary")
	private boolean primary;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UUID getVerificationcode() {
		return verificationcode;
	}

	public void setVerificationcode(UUID verificationcode) {
		this.verificationcode = verificationcode;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
}