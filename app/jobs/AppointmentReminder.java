package jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.AppointmentDTO;
import models.UserDetailsDTO;
import nav.dao.AppointmentDAO;
import nav.dao.PatientAlert;
import play.jobs.Every;
import play.jobs.Job;

@Every("30mn")
public class AppointmentReminder extends Job {
	public void doJob() {
		List<UserDetailsDTO> patients = PatientAlert.getAllPatient();
		for (UserDetailsDTO patient: patients) {
			int patientId = patient.getUser().getId();
			List<AppointmentDTO> appointments = AppointmentDAO.getAppointmentListByField("patientid.id", patientId, new Date(), "upcomming" );
			for (AppointmentDTO appointment : appointments) {
				String alertDetail = PatientAlert.appointmentAlertInfo(appointment);
				if (alertDetail != null) {
					if (alertDetail.equalsIgnoreCase(PatientAlert.APPOINTMENT_ALERT_BEFORE_DAY)) {
						Calendar t7am = Calendar.getInstance();
						t7am.set(Calendar.HOUR_OF_DAY, 7);
						Date t7Date = t7am.getTime();
						long diff = new Date().getTime() - t7Date.getTime();
						if (diff >= 0) {
							PatientAlert.emailAppointmentAlert(patient, appointment, alertDetail);
							System.out.println(patientId + " :" + alertDetail);
						}
					}
					else {
						PatientAlert.emailAppointmentAlert(patient, appointment, alertDetail);
						System.out.println(patientId + " :" + alertDetail);
					}
				}
				else {
					System.out.println(patientId + " : " + "No mail for Appointment : " + appointment.getId());
				}
			}
			
		}
	}
}
