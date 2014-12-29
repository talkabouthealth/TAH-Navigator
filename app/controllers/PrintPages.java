package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nav.dao.AppointmentDAO;
import nav.dao.CancerDAO;
import nav.dao.CareTeamDAO;
import nav.dao.DistressDAO;
import nav.dao.FollowUp;
import nav.dao.MedicationDAO;
import nav.dao.PatientDetailDAO;
import nav.dao.Treatment;
import nav.dao.UserDAO;
import nav.dto.CareTeam;
import nav.dto.DistressBean;
import nav.dto.ExpertBean;
import models.AppointmentDTO;
import models.BreastCancerInfoDTO;
import models.PatienCareTeamDTO;
import models.PatientChemoTreatmentDTO;
import models.PatientConcernDTO;
import models.PatientDetailDTO;
import models.PatientFollowUpCareItemDTO;
import models.PatientGoalDTO;
import models.PatientMedicationDTO;
import models.PatientRadiationTreatmentDTO;
import models.PatientSurgeryInfoDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import play.mvc.Controller;
import play.mvc.With;

@With({Secure.class})
public class PrintPages extends Controller {
	public static void patient(Integer patientId, String[] filter) {
		UserDTO user = UserDAO.getUserBasicByField("id", patientId);
		UserDetailsDTO userDetails = UserDAO.getDetailsByField("id", patientId);
		PatientDetailDTO patientDetails = PatientDetailDAO.getDetailsByField("id", patientId);
		Map<String, String> cancerInfo = CancerDAO.cancerInfo(patientId);
		// treatment plan
		List<PatientRadiationTreatmentDTO> radiationTreatments = Treatment.getPatientRadiationTreatments(patientId);
		List<PatientChemoTreatmentDTO> chemoTreatments = Treatment.getPatientChemoTreatments(patientId);
		List<PatientSurgeryInfoDTO> surgeryInfo = Treatment.getPatientSurgeryInfo(patientId);
		// care plan
		List<PatientConcernDTO> concerns = FollowUp.getPatientConcerns(user.getId());			
		List<PatientGoalDTO> goals = FollowUp.getPatientGoals(user.getId());			
		List<PatientFollowUpCareItemDTO> careItems = FollowUp.getPatientCareItems(user.getId());							
		// care team		
		List<CareTeam> careTeams = CareTeamDAO.patientTeams(patientId);		
		// appointments
		List<AppointmentDTO> futureAppointments = AppointmentDAO.futureAppointments(patientId);
		List<AppointmentDTO> pastAppointments = AppointmentDAO.pastAppointments(patientId);
		// medication
		List<PatientMedicationDTO> currentMedications = MedicationDAO.currentMedications(patientId);
		List<PatientMedicationDTO> pastMedications = MedicationDAO.pastMedications(patientId);
		// distress data
		DistressBean lastDistress = DistressDAO.getLastDistress(user);
		List<String> lastWeekProblems = DistressDAO.problemList(patientId, 7);
		List<String> lastMonthProblems = DistressDAO.problemList(patientId, 30);
		List<String> lastThreeMonthProblems = DistressDAO.problemList(patientId, 90);
		List<String> lastSixMonthProblems = DistressDAO.problemList(patientId, 180);
		List<String> lastYearProblems = DistressDAO.problemList(patientId, 365);
		List<String> allProblems = DistressDAO.problemList(patientId, 0);
		
		for(String str: filter) {
			renderArgs.put(str, true);
		}	
		
		// cover page & diagnosis
		renderArgs.put("user", user);
		renderArgs.put("userDetails", userDetails);
		renderArgs.put("patientDetails", patientDetails);
		renderArgs.put("cancerInfo", cancerInfo);
		// treatment
		renderArgs.put("radiationTreatments", radiationTreatments);
		renderArgs.put("chemoTreatments", chemoTreatments);		
		renderArgs.put("surgeryInfo", surgeryInfo);
		// follow up
		renderArgs.put("concerns", concerns);
		renderArgs.put("goals", goals);		
		renderArgs.put("careItems", careItems);
		// care team
		renderArgs.put("careTeams", careTeams);
		// appointments
		renderArgs.put("futureAppointments", futureAppointments);
		renderArgs.put("pastAppointments", pastAppointments);
		// medication
		renderArgs.put("currentMedications", currentMedications);
		renderArgs.put("pastMedications", pastMedications);
		// distress data
		renderArgs.put("lastDistress", lastDistress);		
		renderArgs.put("lastWeekProblems", lastWeekProblems);
		renderArgs.put("lastMonthProblems", lastMonthProblems);
		renderArgs.put("lastThreeMonthProblems", lastThreeMonthProblems);
		renderArgs.put("lastSixMonthProblems", lastSixMonthProblems);
		renderArgs.put("lastYearProblems", lastYearProblems);
		renderArgs.put("allProblems", allProblems);
		render();
	}
	
	public static void care() {
		render();
	}
}
