package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="defaultemplatetdetail", schema="nav")
public class DefaultTemplateDetailDTO {

	/*  id serial NOT NULL,
	  templateid integer,
	  fieldtext character varying(500),
	  otherfield character varying(500),
	  frequency character varying(100),
	  enddate character varying(50),*/

	@Id
	@Column(name="id")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="defaultemplatetdetail_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="defaultemplatetdetail_id_seq", sequenceName = "nav.defaultemplatetdetail_id_seq")
	private Integer id;

	@Column(name="templateid")
	private Integer templateid;

	@Column(name="fieldtext")
	private String fieldtext;

	@Column(name="otherfield")
	private String otherfield;

	@Column(name="frequency")
	private String frequency;

	@Column(name="enddate")
	private String enddate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTemplateid() {
		return templateid;
	}

	public void setTemplateid(Integer templateid) {
		this.templateid = templateid;
	}

	public String getFieldtext() {
		return fieldtext;
	}

	public void setFieldtext(String fieldtext) {
		this.fieldtext = fieldtext;
	}

	public String getOtherfield() {
		return otherfield;
	}

	public void setOtherfield(String otherfield) {
		this.otherfield = otherfield;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
}