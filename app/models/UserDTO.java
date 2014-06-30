package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;

@Table(name = "nav.user")
@javax.persistence.Entity
public class UserDTO implements Serializable {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "usertype")
	private char userType;
	
	@Column(name = "isactive")
	private boolean isActive;

	@Column(name = "email")
	private String email;
	
	@Column(name = "isverified")
	private boolean isverified;
	
	@OneToOne
	@JoinColumn(name = "usertypeid")
	public UserTypeDTO usertypeid;

	public UserDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getUserType() {
		return userType;
	}

	public void setUserType(char userType) {
		this.userType = userType;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isIsverified() {
		return isverified;
	}

	public void setIsverified(boolean isverified) {
		this.isverified = isverified;
	}

	public UserTypeDTO getUsertypeid() {
		return usertypeid;
	}

	public void setUsertypeid(UserTypeDTO usertypeid) {
		this.usertypeid = usertypeid;
	}
	
	@PostLoad
    protected void repair(){
        if(email!=null)email=email.trim();
        if(name!=null)name=name.trim();
    }
}