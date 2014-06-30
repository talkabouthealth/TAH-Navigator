package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "nav.mailtemplate")
@javax.persistence.Entity
public class MailTemplateDTO implements Serializable {

	/*
	 id serial NOT NULL,
	  mailtemplate character varying(50) DEFAULT NULL::character varying,
	  mailsubject character varying(150) DEFAULT NULL::character varying,
	  type character varying(50) DEFAULT NULL::character varying,
	  fromfield character varying(200) DEFAULT NULL::character varying,
	  ccfield character varying(200) DEFAULT NULL::character varying,
	  bccfield character varying(200) DEFAULT NULL::character varying,
	  companyid integer NOT NULL,
	  CONSTRAINT mailtemplate_pkey PRIMARY KEY (id)
	 */
	@Id 
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "mailtemplate")
	private String template;
	
	@Column(name = "mailsubject")
	private String subject;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "fromfield")
	private String form;

	@Column(name = "ccfield")
	private String cc;
	
	@Column(name = "bccfield")
	private String bcc;
	
	@Column(name = "companyid")
	private int companyid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public int getCompanyid() {
		return companyid;
	}

	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}
}