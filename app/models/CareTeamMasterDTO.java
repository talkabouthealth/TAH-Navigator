package models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Table(name = "nav.careteammaster")
@javax.persistence.Entity
public class CareTeamMasterDTO {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="careteammaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="careteammaster_id_seq", sequenceName = "nav.careteammaster_id_seq")
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@OneToOne
	@JoinColumn(name = "address")
	private AddressDTO address;

	@Column(name = "logo")
	private byte [] logo;

	@Column(name = "active")
	private boolean active;
	
	@Column(name = "adminteam")
	private boolean adminteam;

	@Transient
	private String logoString;

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

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLogoString() {
		return logoString;
	}

	public void setLogoString(String logoString) {
		this.logoString = logoString;
	}

	public boolean isAdminteam() {
		return adminteam;
	}

	public void setAdminteam(boolean adminteam) {
		this.adminteam = adminteam;
	}
}