package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "nav.appointmentgroup")
@javax.persistence.Entity
public class AppointmentGroupDTO {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="appointmentgroup_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="appointmentgroup_id_seq", sequenceName = "nav.appointmentgroup_id_seq")
	@Column(name = "id")
	private int id;

	@Column(name = "startson")
	private Date startson;
	
	@Column(name = "endcheckflag")
	private boolean endcheckflag;
	
	@Column(name = "endneverflag")
	private boolean endneverflag;
	
	@Column(name = "occurences")
	private int occurences;
	
	@Column(name = "endsondate")
	private Date endsondate;
	
	@Column(name = "addressid")
	private Integer addressid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartson() {
		return startson;
	}

	public void setStartson(Date startson) {
		this.startson = startson;
	}

	public boolean isEndcheckflag() {
		return endcheckflag;
	}

	public void setEndcheckflag(boolean endcheckflag) {
		this.endcheckflag = endcheckflag;
	}

	public boolean isEndneverflag() {
		return endneverflag;
	}

	public void setEndneverflag(boolean endneverflag) {
		this.endneverflag = endneverflag;
	}

	public int getOccurences() {
		return occurences;
	}

	public void setOccurences(int occurences) {
		this.occurences = occurences;
	}

	public Date getEndsondate() {
		return endsondate;
	}

	public void setEndsondate(Date endsondate) {
		this.endsondate = endsondate;
	}

	public Integer getAddressid() {
		return addressid;
	}

	public void setAddressid(Integer addressid) {
		this.addressid = addressid;
	}
}