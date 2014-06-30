package models;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "nav.expertdetails")
@javax.persistence.Entity
public class ExpertDetailDTO {

	@Id
	@Column(name = "id")
	private int id;

	@OneToOne
	@JoinColumn(name = "id")
	private UserDTO user;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "designation")
	private DesignationMasterDTO designation;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "practiceaddress")
	private AddressDTO practiceAddr;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "homeaddress")
	private AddressDTO homeAddr;

	@Column(name = "statement")
	private String statement;

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

	public AddressDTO getPracticeAddr() {
		return practiceAddr;
	}

	public void setPracticeAddr(AddressDTO practiceAddr) {
		this.practiceAddr = practiceAddr;
	}

	public AddressDTO getHomeAddr() {
		return homeAddr;
	}

	public void setHomeAddr(AddressDTO homeAddr) {
		this.homeAddr = homeAddr;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public DesignationMasterDTO getDesignation() {
		return designation;
	}

	public void setDesignation(DesignationMasterDTO designation) {
		this.designation = designation;
	}
}