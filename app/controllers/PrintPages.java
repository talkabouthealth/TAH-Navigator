package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nav.dao.CancerDAO;
import nav.dao.CareTeamDAO;
import nav.dao.FollowUp;
import nav.dao.PatientDetailDAO;
import nav.dao.Treatment;
import nav.dao.UserDAO;
import nav.dto.CareTeam;
import nav.dto.ExpertBean;
import models.BreastCancerInfoDTO;
import models.PatienCareTeamDTO;
import models.PatientChemoTreatmentDTO;
import models.PatientConcernDTO;
import models.PatientDetailDTO;
import models.PatientFollowUpCareItemDTO;
import models.PatientGoalDTO;
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
		
		for(String str: filter) {
			renderArgs.put(str, true);
		}	
		
		renderArgs.put("user", user);
		renderArgs.put("userDetails", userDetails);
		renderArgs.put("patientDetails", patientDetails);
		renderArgs.put("cancerInfo", cancerInfo);
		renderArgs.put("radiationTreatments", radiationTreatments);
		renderArgs.put("chemoTreatments", chemoTreatments);
		renderArgs.put("surgeryInfo", surgeryInfo);
		renderArgs.put("concerns", concerns);
		renderArgs.put("goals", goals);
		renderArgs.put("careItems", careItems);
		renderArgs.put("careTeams", careTeams);
		render();
	}
	
	public static void care() {
		render();
	}
}
