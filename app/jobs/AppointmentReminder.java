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

@Every("3h")
public class AppointmentReminder extends Job {
	public void doJob() {
		PatientAlert.firstAppointmentAlerts();
		PatientAlert.treatmentDecisionAlerts();
		PatientAlert.simulationAlerts();
		PatientAlert.duringTreatmentAlerts();
		PatientAlert.afterTreatmentBeforeOneDayAlerts();
		PatientAlert.afterTreatmentBeforeOneWeekAlerts();
		PatientAlert.treatmentDecision_beforeThreeDayAlerts();
		PatientAlert.simulation_beforeThreeDayAlerts();
		PatientAlert.duringTreatment_beforeThreeDayAlerts();
	}
}
