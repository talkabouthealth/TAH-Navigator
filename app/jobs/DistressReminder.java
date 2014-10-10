package jobs;

import java.util.List;
import java.util.Map;

import nav.dao.PatientAlert;
import models.UserDetailsDTO;
import play.jobs.Every;
import play.jobs.Job;


@Every("8h")
public class DistressReminder extends Job{
	public void doJob() {
		List<UserDetailsDTO> patients = PatientAlert.getAllPatient();
		for (UserDetailsDTO patient: patients) {
			int patientId = patient.getUser().getId();
			System.out.println("\n\n");
			String alertDetail = PatientAlert.distressAlertInfo(patientId);
			
			if (alertDetail != null) {
				PatientAlert.emailDistressAlert(patient, alertDetail);
				System.out.println(patient.getFirstName() + " Mail Sent **************** ");
				System.out.println("\n\n");
			}
			else {
				System.out.println(patient.getFirstName() + " No Mail -----------");
				System.out.println("\n\n");
			}			
		}
	}
}
