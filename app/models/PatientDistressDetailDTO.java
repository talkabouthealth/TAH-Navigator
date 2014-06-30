package models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "nav.patientdistressdetail")
@javax.persistence.Entity
public class PatientDistressDetailDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@OneToOne
	@JoinColumn(name = "patiendistressid")
	private PatientDistressDTO patiendistress;

	@Column(name = "distresstypeid")
	private int distresstypeid;

	@Column(name = "distressvalue")
	private boolean distressvalue;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PatientDistressDTO getPatiendistress() {
		return patiendistress;
	}

	public void setPatiendistress(PatientDistressDTO patiendistress) {
		this.patiendistress = patiendistress;
	}

	public int getDistresstypeid() {
		return distresstypeid;
	}

	public void setDistresstypeid(int distresstypeid) {
		this.distresstypeid = distresstypeid;
	}

	public boolean isDistressvalue() {
		return distressvalue;
	}

	public void setDistressvalue(boolean distressvalue) {
		this.distressvalue = distressvalue;
	}
}