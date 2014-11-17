package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="cancermutationmaster", schema="nav")
public class CancerMutationDTO {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="cancermutationmaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="cancermutationmaster_id_seq", sequenceName = "nav.cancermutationmaster_id_seq")
	private Integer id;
	
	@Column(name="diseaseid")
	private Integer diseaseid;

	@Column(name="mutation")
	private String mutation;

	public String getMutation() {
		return mutation;
	}

	public void setMutation(String mutation) {
		this.mutation = mutation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDiseaseid() {
		return diseaseid;
	}

	public void setDiseaseid(Integer diseaseid) {
		this.diseaseid = diseaseid;
	}
}