package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="inputdefaultmaster", schema="nav")
public class InputDefaultDTO {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="inputdefaultmaster_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="inputdefaultmaster_id_seq", sequenceName = "nav.inputdefaultmaster_id_seq")
	private Integer id;

	@Column(name="diseaseid")
	private Integer diseaseid;

	@Column(name="page")
	private String page;

	@Column(name="field")
	private String field;

	@Column(name="fieldtext")
	private String fieldtext;

	@Column(name="tiptext")
	private String tiptext;

	@Column(name="otherfield")
	private String otherfield;

	@Column(name="frequency")
	private String frequency;

	@Column(name="tiptype")
	private String tiptype;

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

	public String getFieldtext() {
		return fieldtext;
	}

	public void setFieldtext(String fieldtext) {
		this.fieldtext = fieldtext;
	}

	public String getTiptext() {
		return tiptext;
	}

	public void setTiptext(String tiptext) {
		this.tiptext = tiptext;
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

	public String getTiptype() {
		return tiptype;
	}

	public void setTiptype(String tiptype) {
		this.tiptype = tiptype;
	}
}