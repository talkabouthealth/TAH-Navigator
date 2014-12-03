package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="defaultemplatetmaster", schema="nav")
public class DefaultTemplateMasterDTO {

	 /* id serial NOT NULL,
	  templatename character varying(250),
	  diseaseid integer,
	  page character varying(100),
	  field character varying(100),
	  */
	  
	@Id
	@Column(name="id")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="defaultemplatetmaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="defaultemplatetmaster_id_seq", sequenceName = "nav.defaultemplatetmaster_id_seq")
	private Integer id;

	@Column(name="templatename")
	private String templatename;

	@Column(name="diseaseid")
	private Integer diseaseid;

	@Column(name="page")
	private String page;

	@Column(name="field")
	private String field;

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

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getTemplatename() {
		return templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}
}