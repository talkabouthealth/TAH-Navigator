package models;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="mgn_delivery", schema="nav")
public class MGNDeliveryDTO implements Serializable{
	
	@Id
	@Column(name="mgn_id")
	private Integer mgnId;
	
	@Id
	@Column(name="md_id")
	private Integer mdId;

	public Integer getMgnId() {
		return mgnId;
	}

	public void setMgnId(Integer mgnId) {
		this.mgnId = mgnId;
	}

	public Integer getMdId() {
		return mdId;
	}

	public void setMdId(Integer mdId) {
		this.mdId = mdId;
	}

}
