package models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "nav.othercaremember")
@javax.persistence.Entity
public class OtherCareMemberDTO {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="othercaremember_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="othercaremember_id_seq", sequenceName = "nav.othercaremember_id_seq")
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "title")
	private String title;

	@Column(name = "telephone")
	private String telephone;

	@Column(name = "careteamid")
	private int careteamid;

	@Column(name = "patientid")
	private int patientid;

	@Column(name = "isprimary")
	private boolean primary;
	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getCareteamid() {
		return careteamid;
	}

	public void setCareteamid(int careteamid) {
		this.careteamid = careteamid;
	}

	public int getPatientid() {
		return patientid;
	}

	public void setPatientid(int patientid) {
		this.patientid = patientid;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
}