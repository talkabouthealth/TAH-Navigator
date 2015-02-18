package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import nav.dao.AppointmentDAO;
import nav.dao.BaseDAO;
import nav.dao.CancerDAO;
import nav.dao.CareTeamDAO;
import nav.dao.DistressDAO;
import nav.dao.FollowUp;
import nav.dao.MedicationDAO;
import nav.dao.NotesDAO;
import nav.dao.PatientDetailDAO;
import nav.dao.Treatment;
import nav.dao.UserDAO;
import nav.dto.CareTeam;
import nav.dto.DistressBean;
import nav.dto.ExpertBean;
import nav.dto.TeamDetails;
import models.AppointmentDTO;
import models.BreastCancerInfoDTO;
import models.NoteDTO;
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
		List<NoteDTO> noteList = NotesDAO.getPatientNotesList(String.valueOf(user.getId()));
		// care team				
		List<TeamDetails> careTeams = CareTeamDAO.getTeamDetails(user.getId());				
		// appointments
		List<AppointmentDTO> futureAppointments = AppointmentDAO.futureAppointments(patientId);
		List<AppointmentDTO> pastAppointments = AppointmentDAO.pastAppointments(patientId);
		// Medication
		List<PatientMedicationDTO> medicationList = MedicationDAO.getMedicine("patientid", patientId);
		List<PatientMedicationDTO> currentMedications = new ArrayList<PatientMedicationDTO>();
		List<PatientMedicationDTO> pastMedications = new ArrayList<PatientMedicationDTO>();		
		Date now = new Date();		
		
		
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");		
		DateFormat dfDisplay = new SimpleDateFormat("mm/dd/yyyy");
		
		
		for (PatientMedicationDTO mDto : medicationList) {
			Date startDt = null;
			Date endDt = null;	
			Date startDt1 = null;
			Date endDt1 = null;
			if(mDto.getCaremembername() == null) {
				mDto.setCaremembername(UserDAO.getUserName(mDto.getCaremember().getId()));
			}
			try {
				Calendar cal = Calendar.getInstance();
				String date = mDto.getStartdate().trim();
				String[] tokens = date.split("-");
				cal.set(Calendar.YEAR, Integer.parseInt(tokens[0]));
				cal.set(Calendar.MONTH, Integer.parseInt(tokens[1]) - 1);
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tokens[2]));
				startDt = cal.getTime();
				
				date = mDto.getEnddate().trim();
				tokens = date.split("-");
				cal.set(Calendar.YEAR, Integer.parseInt(tokens[0]));
				cal.set(Calendar.MONTH, Integer.parseInt(tokens[1]) - 1);
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tokens[2]));
				endDt = cal.getTime();
				
				startDt1 = df.parse(mDto.getStartdate().trim());							
				endDt1 = df.parse(mDto.getEnddate().trim());
				mDto.setStartdate(dfDisplay.format(startDt1));
				mDto.setEnddate(dfDisplay.format(endDt1));
			} catch(Exception e) { }
			
			if(startDt != null && endDt != null) {				
				if (startDt.before(now) && endDt.after(now)) {
					currentMedications.add(mDto);					
				} 
				else if(endDt.after(now)) {
					currentMedications.add(mDto);
				} 
				else {
					pastMedications.add(mDto);					
				}
			} 
			else {
				currentMedications.add(mDto);
			}			
		}
		
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
		renderArgs.put("noteList", noteList);
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
