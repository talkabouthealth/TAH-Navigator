package nav.dto;

import java.io.Serializable;
import java.util.Date;


import org.apache.commons.lang.StringUtils;


import play.data.validation.Email;
import play.data.validation.Required;
import util.CommonUtil;

public class SignUpMemberBean {

	private int id;
	
	@Required private String userType;
	
	@Required private String userName;
	
	@Required private String password;
	
	@Required private String repassword;

	private String firstName;
	
	private String lastName;
	
	private String dob;
	
	private int ssn1;
	
	private int ssn2;
	
	private int ssn3;
	
	private String contactMethod;
	
	@Required @Email private String email;
	
	@Required @Email private String verifyemail;
	
	private String mobile;
	
	private String homePhone;
	
	private String seq1;
	
	private String sea1;
	
	private String seq2;
	
	private String sea2;
	
	private boolean tosFlag =false;
	
	private boolean smtFlag =false;

	public SignUpMemberBean() {
	}
	
	public SignUpMemberBean(int id) {
		this.id = id;
	}
	public SignUpMemberBean(int id, String userName) {
		this.id = id;
		this.userName = userName;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SignUpMemberBean)) {
			return false;
		}
		SignUpMemberBean other = (SignUpMemberBean)obj;
		return id == other.id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public int getSsn1() {
		return ssn1;
	}

	public void setSsn1(int ssn1) {
		this.ssn1 = ssn1;
	}

	public int getSsn2() {
		return ssn2;
	}

	public void setSsn2(int ssn2) {
		this.ssn2 = ssn2;
	}

	public int getSsn3() {
		return ssn3;
	}

	public void setSsn3(int ssn3) {
		this.ssn3 = ssn3;
	}

	public String getContactMethod() {
		return contactMethod;
	}

	public void setContactMethod(String contactMethod) {
		this.contactMethod = contactMethod;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVerifyemail() {
		return verifyemail;
	}

	public void setVerifyemail(String verifyemail) {
		this.verifyemail = verifyemail;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getSeq1() {
		return seq1;
	}

	public void setSeq1(String seq1) {
		this.seq1 = seq1;
	}

	public String getSea1() {
		return sea1;
	}

	public void setSea1(String sea1) {
		this.sea1 = sea1;
	}

	public String getSeq2() {
		return seq2;
	}

	public void setSeq2(String seq2) {
		this.seq2 = seq2;
	}

	public String getSea2() {
		return sea2;
	}

	public void setSea2(String sea2) {
		this.sea2 = sea2;
	}

	public boolean isTosFlag() {
		return tosFlag;
	}

	public void setTosFlag(boolean tosFlag) {
		this.tosFlag = tosFlag;
	}

	public boolean isSmtFlag() {
		return smtFlag;
	}

	public void setSmtFlag(boolean smtFlag) {
		this.smtFlag = smtFlag;
	}
}