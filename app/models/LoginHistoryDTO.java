package models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "nav.loginhistory")
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LoginHistoryDTO implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="loginhistory_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="loginhistory_id_seq", sequenceName = "nav.loginhistory_id_seq")
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "userid")
	private UserDTO user;

	@Column(name = "logintime")
	private Date logintime;

	@Column(name = "sessionid")
	private String sessionid;

	@Column(name = "frommethod")
	private String from;

	@Column(name = "rememberflag")
	private boolean rememberChecked;

	@Column(name = "refrer")
	private String refrer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Date getLogintime() {
		return logintime;
	}

	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public boolean isRememberChecked() {
		return rememberChecked;
	}

	public void setRememberChecked(boolean rememberChecked) {
		this.rememberChecked = rememberChecked;
	}

	public String getRefrer() {
		return refrer;
	}

	public void setRefrer(String refrer) {
		this.refrer = refrer;
	}
}