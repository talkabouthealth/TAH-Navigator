package models;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "nav.distresstypemaster")
@javax.persistence.Entity
public class DistressTypeMasterDTO {

	@Id
	@Column(name = "id")
	private int id;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "categoryid")
	private DistressCategoryMasterDTO category;
	
	@Column(name = "name")
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DistressCategoryMasterDTO getCategory() {
		return category;
	}

	public void setCategory(DistressCategoryMasterDTO category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}