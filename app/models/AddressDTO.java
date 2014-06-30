package models;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "nav.useraddress")
@javax.persistence.Entity
public class AddressDTO {

	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "line1")
	private String line1;
	
	@Column(name = "line2")
	private String line2;
	
	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

	@Column(name = "zip")
	private String zip;
	
	@Column(name = "lat")
	private double lat;
	
	@Column(name = "lang")
	private double lang;
	
	@Column(name = "phone")
	private String phone;
}