package models;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Table(name = "nav.medicinemaster")
@javax.persistence.Entity
public class MedicineMasterDTO {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="medicinemaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="medicinemaster_id_seq", sequenceName = "nav.medicinemaster_id_seq")
	@Column(name = "id")
	private int id;

	@Column(name = "genericname")
	private String genericname;

	@Column(name = "brandname")
	private String brandname;

	@Column(name = "method")
	private String method;

	@Column(name = "image")
	private byte [] image;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGenericname() {
		return genericname;
	}

	public void setGenericname(String genericname) {
		this.genericname = genericname;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}