package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "nav.usercertificate")
@javax.persistence.Entity
public class UserCertificateDTO implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="usercertificate_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="usercertificate_id_seq", sequenceName = "nav.usercertificate_id_seq")
	@Column(name = "id")
	private int id;
	
	@Id
	@Column(name = "userid")
	private int userid;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
