package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="cancertypemaster", schema="nav")
public class CancerTypeDTO {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="cancertypemaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="cancertypemaster_id_seq", sequenceName = "nav.cancertypemaster_id_seq")
	private Integer id;
	
	@Column(name="roottype")
	private boolean roottype;

	@Column(name="name")
	private String name;
	
	@Column(name="diseaseid")
	private Integer diseaseid;

	@Column(name="user_defined")
	private boolean userDefined;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isRoottype() {
		return roottype;
	}

	public void setRoottype(boolean roottype) {
		this.roottype = roottype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDiseaseid() {
		return diseaseid;
	}

	public void setDiseaseid(Integer diseaseid) {
		this.diseaseid = diseaseid;
	}

	public boolean isUserDefined() {
		return userDefined;
	}

	public void setUserDefined(boolean userDefined) {
		this.userDefined = userDefined;
	}
}