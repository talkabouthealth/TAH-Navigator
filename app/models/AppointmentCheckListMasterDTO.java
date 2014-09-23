package models;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "nav.appointmentchecklist")
@javax.persistence.Entity

public class AppointmentCheckListMasterDTO {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="appointmentchecklist_id_seq")
	@SequenceGenerator(allocationSize=1, schema="nav",  name="appointmentchecklist_id_seq", sequenceName = "nav.appointmentchecklist_id_seq")
	@Column(name = "id")
	private int id;

	@Column(name = "checkitem")
	private String checkitem;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="appointmentid", insertable=true, updatable=true)
	private AppointmentMasterDTO appointmentid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCheckitem() {
		return checkitem;
	}

	public void setCheckitem(String checkitem) {
		this.checkitem = checkitem;
	}

	public AppointmentMasterDTO getAppointmentid() {
		return appointmentid;
	}

	public void setAppointmentid(AppointmentMasterDTO appointmentid) {
		this.appointmentid = appointmentid;
	}
}