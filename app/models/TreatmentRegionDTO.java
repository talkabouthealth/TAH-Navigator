package models;

import javax.persistence.*;

@Entity
@Table(name="treatment_region", schema="nav")
public class TreatmentRegionDTO {
	
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="treatment_region_tr_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="treatment_region_tr_id_seq", sequenceName = "nav.treatment_region_tr_id_seq")
	@Column(name="tr_id")
	private Integer id;
	
	@Column(name="region")
	private String region;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
