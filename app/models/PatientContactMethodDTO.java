package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="usercontactmethod", schema="nav")
@IdClass(PatientContactMethodPK.class)
public class PatientContactMethodDTO {

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userid", insertable=false, updatable=false)
	private UserDTO pDto;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="contactmethod", insertable=false, updatable=false)
	private ContactTypeDTO ctypeDto;

	@Id
	private Integer userid;

	@Id
	private Integer contactmethod;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getContactmethod() {
		return contactmethod;
	}

	public void setContactmethod(Integer contactmethod) {
		this.contactmethod = contactmethod;
	}

	public UserDTO getpDto() {
		return pDto;
	}

	public void setpDto(UserDTO pDto) {
		this.pDto = pDto;
	}

	public ContactTypeDTO getCtypeDto() {
		return ctypeDto;
	}

	public void setCtypeDto(ContactTypeDTO ctypeDto) {
		this.ctypeDto = ctypeDto;
	}
}