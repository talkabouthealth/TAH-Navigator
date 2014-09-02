package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "nav.patientdetails")
@javax.persistence.Entity
public class PatientDetailDTO {

	@Id
	@Column(name = "id")
	private int id;

	@OneToOne
	@JoinColumn(name = "id")
	private UserDTO user;

	@Column(name="disease")
	private Integer diseaseId;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "disease", insertable=false, updatable=false)
	private DiseaseMasterDTO disease;
	
	@Column(name = "dateofdiagnosis")
	private Date dateofdiagnosis;

	@Column(name = "stage")
	private String stage;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "address")
	private AddressDTO address;
	
	@Column(name = "ec1name")
	private String ec1name;
	
	@Column(name = "ec1number")
	private String ec1number;

	@Column(name = "ec2name")
	private String ec2name;

	@Column(name = "ec2number")
	private String ec2number;

	@Column(name = "kinname")
	private String kinname;

	@Column(name = "kinnumber")
	private String kinnumber;
	
	@Column(name = "proxyname")
	private String proxyname;

	@Column(name = "proxynumber")
	private String proxynumber;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public DiseaseMasterDTO getDisease() {
		return disease;
	}

	public void setDisease(DiseaseMasterDTO disease) {
		this.disease = disease;
	}

	public Date getDateofdiagnosis() {
		return dateofdiagnosis;
	}

	public void setDateofdiagnosis(Date dateofdiagnosis) {
		this.dateofdiagnosis = dateofdiagnosis;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public String getEc1name() {
		return ec1name;
	}

	public void setEc1name(String ec1name) {
		this.ec1name = ec1name;
	}

	public String getEc1number() {
		return ec1number;
	}

	public void setEc1number(String ec1number) {
		this.ec1number = ec1number;
	}

	public String getEc2name() {
		return ec2name;
	}

	public void setEc2name(String ec2name) {
		this.ec2name = ec2name;
	}

	public String getEc2number() {
		return ec2number;
	}

	public void setEc2number(String ec2number) {
		this.ec2number = ec2number;
	}

	public String getKinname() {
		return kinname;
	}

	public void setKinname(String kinname) {
		this.kinname = kinname;
	}

	public String getKinnumber() {
		return kinnumber;
	}

	public void setKinnumber(String kinnumber) {
		this.kinnumber = kinnumber;
	}

	public String getProxyname() {
		return proxyname;
	}

	public void setProxyname(String proxyname) {
		this.proxyname = proxyname;
	}

	public String getProxynumber() {
		return proxynumber;
	}

	public void setProxynumber(String proxynumber) {
		this.proxynumber = proxynumber;
	}

	public Integer getDiseaseId() {
		return diseaseId;
	}

	public void setDiseaseId(Integer diseaseId) {
		this.diseaseId = diseaseId;
	}

	
}