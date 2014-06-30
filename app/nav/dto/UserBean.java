package nav.dto;

import java.io.Serializable;

public class UserBean implements Serializable {

	private int id;

	private String name;

	private String password;

	private char userType;

	private boolean isActive;
	
	private boolean verifiedFlag;

	private String email;

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

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isVerifiedFlag() {
		return verifiedFlag;
	}

	public void setVerifiedFlag(boolean verifiedFlag) {
		this.verifiedFlag = verifiedFlag;
	}

	public String getEmail() {
		return email;
	}
	
}