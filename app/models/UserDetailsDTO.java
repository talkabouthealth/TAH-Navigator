package models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;

@Table(name = "nav.userdetails")
@javax.persistence.Entity
public class UserDetailsDTO {

	@Id
	@Column(name = "id")
	private int id;

	@OneToOne
	@JoinColumn(name = "id")
	private UserDTO user;

	@Column(name = "firstname")
	private String firstName;

	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "dob")
	private Date dob;
	
	@OneToOne
	@JoinColumn(name = "contactmethod")
	private ContactTypeDTO contactMethod;

	@Column(name = "email")
	private String email;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "homephone")
	private String homePhone;
	
	@ManyToOne
	@JoinColumn(name = "seq1")
	private SecurityQuestionDTO seq1;
	
	@Column(name = "sea1")
	private String sea1;
	
	@ManyToOne
	@JoinColumn(name = "seq2")
	private SecurityQuestionDTO seq2;
	
	@Column(name = "sea2")
	private String sea2;
	
	@Column(name = "tosflag")
	private boolean tosflag;
	
	@Column(name = "tocflag")
	private boolean tocflag;
	
	@Column(name = "createdate")
	private Date createdate;
	
	@Column(name = "editdate")
	private Date editdate;
	
	@Column(name = "verificationcode")
	@org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
	private UUID verificationcode;

	@OneToOne
	@JoinColumn(name = "editperson")
	private UserDTO editedBy;

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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public ContactTypeDTO getContactMethod() {
		return contactMethod;
	}

	public void setContactMethod(ContactTypeDTO contactMethod) {
		this.contactMethod = contactMethod;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public SecurityQuestionDTO getSeq1() {
		return seq1;
	}

	public void setSeq1(SecurityQuestionDTO seq1) {
		this.seq1 = seq1;
	}

	public String getSea1() {
		return sea1;
	}

	public void setSea1(String sea1) {
		this.sea1 = sea1;
	}

	public SecurityQuestionDTO getSeq2() {
		return seq2;
	}

	public void setSeq2(SecurityQuestionDTO seq2) {
		this.seq2 = seq2;
	}

	public String getSea2() {
		return sea2;
	}

	public void setSea2(String sea2) {
		this.sea2 = sea2;
	}

	public boolean isTosflag() {
		return tosflag;
	}

	public void setTosflag(boolean tosflag) {
		this.tosflag = tosflag;
	}

	public boolean isTocflag() {
		return tocflag;
	}

	public void setTocflag(boolean tocflag) {
		this.tocflag = tocflag;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getEditdate() {
		return editdate;
	}

	public void setEditdate(Date editdate) {
		this.editdate = editdate;
	}

	public UUID getVerificationcode() {
		return verificationcode;
	}

	public void setVerificationcode(UUID verificationcode) {
		this.verificationcode = verificationcode;
	}

	public UserDTO getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(UserDTO editedBy) {
		this.editedBy = editedBy;
	}

    @PostLoad
    protected void repair(){
        if(email!=null)email=email.trim();
        if(lastName!=null)lastName=lastName.trim();
        if(firstName!=null)firstName=firstName.trim();
        if(mobile!=null)mobile=mobile.trim(); 
        if(homePhone!=null)homePhone=homePhone.trim(); 
    }
}