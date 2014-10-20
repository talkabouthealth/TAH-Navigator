package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "nav.patientdistress")
@javax.persistence.Entity
public class PatientDistressDTO {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="patientdistress_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="patientdistress_id_seq", sequenceName = "nav.patientdistress_id_seq")
	@Column(name = "id")
	private int id;

	@OneToOne
	@JoinColumn(name = "patientid")
	private UserDTO user;
	
	@Column(name = "distressvalue")
	private int distressvalue;
	
	@Column(name = "daterecrded")
	private Date daterecrded;
	
	@Column(name = "through")
	private String through;
	
	@Column(name = "otherdetail")
	private String otherdetail;
	
	@Column(name="update_by")
	private Integer updateBy;

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

	public int getDistressvalue() {
		return distressvalue;
	}

	public void setDistressvalue(int distressvalue) {
		this.distressvalue = distressvalue;
	}

	public Date getDaterecrded() {
		return daterecrded;
	}

	public void setDaterecrded(Date daterecrded) {
		this.daterecrded = daterecrded;
	}

	public String getThrough() {
		return through;
	}

	public void setThrough(String through) {
		this.through = through;
	}

	public String getOtherdetail() {
		return otherdetail;
	}

	public void setOtherdetail(String otherdetail) {
		this.otherdetail = otherdetail;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
}