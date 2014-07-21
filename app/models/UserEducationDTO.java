package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "nav.usereducation")
@javax.persistence.Entity
public class UserEducationDTO implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Id
	@Column(name = "userid")
	private int userid;
	
	@Column(name = "dgree")
	private String dgree;
	
	@Column(name = "school")
	private String school;
	
	@Column(name = "year")
	private String year;

	@Column(name = "logopath")
	private String logopath;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getDgree() {
		return dgree;
	}

	public void setDgree(String dgree) {
		this.dgree = dgree;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getLogopath() {
		return logopath;
	}

	public void setLogopath(String logopath) {
		this.logopath = logopath;
	}
/*
	 id serial NOT NULL,
	  userid integer NOT NULL,
	  dgree character varying(200),
	  school character varying(250),
	  year character varying(50),
	  logopath character varying(250),
	  CONSTRAINT "education_PK" PRIMARY KEY (id, userid),
	  CONSTRAINT "userid_FK" FOREIGN KEY (userid)
	      REFERENCES nav."user" (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION*/
}
