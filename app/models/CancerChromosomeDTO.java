package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="cancerchromosomemaster", schema="nav")
public class CancerChromosomeDTO {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="cancerchromosomemaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="cancerchromosomemaster_id_seq", sequenceName = "nav.cancerchromosomemaster_id_seq")
	private Integer id;

	@Column(name="chromosomename")
	private String chromosomename;
	
	@Column(name="diseaseid")
	private Integer diseaseid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChromosomename() {
		return chromosomename;
	}

	public void setChromosomename(String chromosomename) {
		this.chromosomename = chromosomename;
	}

	public Integer getDiseaseid() {
		return diseaseid;
	}

	public void setDiseaseid(Integer diseaseid) {
		this.diseaseid = diseaseid;
	}
}