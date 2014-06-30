package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PostLoad;
import javax.persistence.Table;

@Table(name = "nav.usertypemaster")
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserTypeDTO implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "abbravation")
	private char abbravation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getAbbravation() {
		return abbravation;
	}

	public void setAbbravation(char abbravation) {
		this.abbravation = abbravation;
	}
	@PostLoad
    protected void repair(){
        if(name!=null)name=name.trim();
    }	
}