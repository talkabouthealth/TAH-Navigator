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


@Table(name = "nav.medicinecatlog")
@javax.persistence.Entity
public class MedicineCatlogDTO {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="medicinecatlog_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="medicinecatlog_id_seq", sequenceName = "nav.medicinecatlog_id_seq")
	@Column(name = "id")
	private int id;

	@Column(name = "genericname")
	private String label;

	@Column(name = "brandname")
	private String brandname;

	@Column(name = "method")
	private String method;

	@Column(name = "frequency")
	private String frequency;

	@Column(name = "specialinstruction")
	private String specialinstruction;

	@Column(name = "image")
	private byte [] image;

	public MedicineCatlogDTO() {
	}

	public MedicineCatlogDTO(int id,String label,String brandname,String frequency,String specialinstruction) {
		this.id = id;
		this.label = label;
		this.brandname = brandname;
		this.frequency = frequency;
		this.specialinstruction = specialinstruction; 
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getSpecialinstruction() {
		return specialinstruction;
	}

	public void setSpecialinstruction(String specialinstruction) {
		this.specialinstruction = specialinstruction;
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