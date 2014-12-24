package controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import models.AddressDTO;
import models.AppointmentDTO;
import models.AppointmentGroupDTO;
import models.AppointmentMasterDTO;
import models.BreastCancerInfoDTO;
import models.BreastCancerStageDTO;
import models.CareTeamMasterDTO;
import models.CareTeamMemberDTO;
import models.ChemoScheduleDTO;
import models.DefaultTemplateDetailDTO;
import models.DefaultTemplateMasterDTO;
import models.DiseaseMasterDTO;
import models.ExpertDetailDTO;
import models.InputDefaultDTO;
import models.MedicineCatlogDTO;
import models.MedicineMasterDTO;
import models.NoteDTO;
import models.PatienCareTeamDTO;
import models.PatientChemoTreatmentDTO;
import models.PatientChromosomeDTO;
import models.PatientConcernDTO;
import models.PatientDetailDTO;
import models.PatientFollowUpCareItemDTO;
import models.PatientGoalDTO;
import models.PatientMedicationDTO;
import models.PatientMutationDTO;
import models.PatientRadiationTreatmentDTO;
import models.PatientSurgeryInfoDTO;
import models.RadiationScheduleDTO;
import models.RadiationTypeDTO;
import models.SideEffectDTO;
import models.SurgeryTypeDTO;
import models.TreatmentRegionDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import nav.dao.AppointmentDAO;
import nav.dao.AppointmentMasterDAO;
import nav.dao.BaseDAO;
import nav.dao.CareTeamDAO;
import nav.dao.DefaultTemplateDAO;
import nav.dao.Disease;
import nav.dao.DistressDAO;
import nav.dao.FollowUp;
import nav.dao.InputDefaultDAO;
import nav.dao.MedicationDAO;
import nav.dao.NotesDAO;
import nav.dao.PatientAlert;
import nav.dao.PatientDetailDAO;
import nav.dao.ProfileDAO;
import nav.dao.Treatment;
import nav.dao.UserDAO;
import nav.dto.CareMember;
import nav.dto.DistressBean;
import nav.dto.ExpertBean;
import nav.dto.UserBean;
import play.mvc.Controller;
import play.mvc.With;
import util.CommonUtil;
import util.ImageUtil;
import util.JPAUtil;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

@Check({"care","care"})
@With( { Secure.class } )
public class CarePatien  extends Controller {

	public static void appointment(int patientId) {
		Integer idField = new Integer(patientId);
		Date curreDate = new Date();
		int pageId = 0;
		List<AppointmentDTO> list = AppointmentDAO.getAppointmentListByField("patientid.id", idField, curreDate, "upcomming",pageId );
		ArrayList<Integer> totalUp = AppointmentDAO.getTotalappointments("patientid.id", idField, curreDate, "upcomming" );
		List<AppointmentDTO> expList = AppointmentDAO.getAppointmentListByField("patientid.id" , idField, curreDate, "past",pageId );
		ArrayList<Integer> totalPast = AppointmentDAO.getTotalappointments("patientid.id", idField, curreDate, "past" );
		Map <String, Object> ps = PatientDetailDAO.patientSummary(patientId);
		render(patientId,list,expList, ps,totalUp,totalPast);
	}
	
	public static void appointmentNextPage(int patientId, int pageId,String type) {
		Integer idField = new Integer(patientId);
		Date curreDate = new Date();
		pageId = (pageId -1)*10;
		List<AppointmentDTO> list = AppointmentDAO.getAppointmentListByField("patientid.id", idField, curreDate, type,pageId );
		render("tags/appointment.html",patientId,list);
	}
	
	public static void summary(Integer patientId) {
		UserBean user = CommonUtil.loadCachedUser(session);
		ExpertDetailDTO expertDetail = ProfileDAO.getExpertByField("id", user.getId());
		
		UserDetailsDTO patientDto = UserDAO.getDetailsById(patientId);
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", patientId);
		
		DistressBean distress = DistressDAO.getLastDistress(patientDto.getUser());
		
		//DistressBean lastDistress = DistressDAO.getLastDistress(patientDto.getUser(),1);
		
		List<NoteDTO> noteList = NotesDAO.getPatientNotesList(patientId.toString());
		List<DiseaseMasterDTO> diseases = Disease.allDiseases();
		List<BreastCancerStageDTO> stages = Disease.breastCancerStages();
		int breastCancerId = Disease.BREAST_CANCER_ID; 
		List<UserDTO> drList = UserDAO.getAll("5","");
		
		Map <String, Object> ps = PatientDetailDAO.patientSummary(Integer.valueOf(patientId));		
		//Appointment masterData
		List<AppointmentMasterDTO> appList = AppointmentMasterDAO.getAllAppointments();
		List<String> lastWeekProblems = DistressDAO.problemList(Integer.valueOf(patientId), 7);
		List<String> lastMonthProblems = DistressDAO.problemList(Integer.valueOf(patientId), 30);
		List<String> lastThreeMonthProblems = DistressDAO.problemList(Integer.valueOf(patientId), 90);
		List<String> lastSixMonthProblems = DistressDAO.problemList(Integer.valueOf(patientId), 180);
		List<String> lastYearProblems = DistressDAO.problemList(Integer.valueOf(patientId), 365);
		List<String> allProblems = DistressDAO.problemList(Integer.valueOf(patientId), 0);		
        render(user,expertDetail,patientId,patientDto,patientOtherDetails,distress,noteList, diseases, stages, breastCancerId, ps,appList,drList, lastWeekProblems, lastMonthProblems, lastThreeMonthProblems, lastSixMonthProblems, lastYearProblems, allProblems);
	}
	public static void distressValues(int patientId, int days) {
		Map<Long, Integer> values = DistressDAO.distressValues(patientId, days);
		renderJSON(values);
	}
	
	public static void careteam(int patientId) {
		UserDetailsDTO userDto = UserDAO.getDetailsById(patientId);
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", userDto.getId());
		List<PatienCareTeamDTO> careTeams = CareTeamDAO.getPatienCareTeamByField("patienid", userDto.getId());
		Map <String, Object> ps = PatientDetailDAO.patientSummary(patientId);
		render(patientId,userDto,patientOtherDetails,careTeams,ps);
	}

	public static void medication(int patientId) {
		UserDetailsDTO userDto = UserDAO.getDetailsById(patientId);
		List<PatientMedicationDTO> medicationList = MedicationDAO.getMedicine("patientid", userDto.getId());
		for (PatientMedicationDTO patientMedicationDTO : medicationList) {
			if(patientMedicationDTO.getCaremembername() == null) {
				patientMedicationDTO.setCaremembername(UserDAO.getUserName(patientMedicationDTO.getCaremember().getId()));
			}
		}
		Map <String, Object> ps = PatientDetailDAO.patientSummary(patientId);
		render(patientId,medicationList, ps);
	}
	public static void verify(Integer patientId, boolean isVerified) {
		PatientDetailDAO.patientVerify(patientId, isVerified);
		Map<String, String> jsonData = new HashMap<String, String>();
		jsonData.put("status", "success");
		renderJSON(jsonData);
	}

	public static void treatmentPlan(Integer patientId) {
		List<PatientRadiationTreatmentDTO> radiationTreatments = Treatment.getPatientRadiationTreatments(patientId);
		List<PatientChemoTreatmentDTO> chemoTreatments = Treatment.getPatientChemoTreatments(patientId);
		List<PatientSurgeryInfoDTO> surgeryInfo = Treatment.getPatientSurgeryInfo(patientId);
		Map <String, Object> ps = PatientDetailDAO.patientSummary(patientId);
		render(patientId, radiationTreatments, chemoTreatments, surgeryInfo, ps);
	}

	public static void followupPlan(int patientId) {
		List<NoteDTO> noteList = NotesDAO.getPatientNotesList(patientId+"");
		Map <String, Object> ps = PatientDetailDAO.patientSummary(patientId);
		PatientDetailDTO patientDetails = PatientDetailDAO.getDetailsByField("id", new Integer(patientId));

		List<PatientFollowUpCareItemDTO> careItemsOld = FollowUp.getPatientCareItems(patientId);
		List<PatientFollowUpCareItemDTO> careItems = null;
		if(careItemsOld != null && !careItemsOld.isEmpty()) {
			careItems = new ArrayList<PatientFollowUpCareItemDTO>();
			for (PatientFollowUpCareItemDTO patientFollowUpCareItemDTO : careItemsOld) {
				InputDefaultDTO tipText = InputDefaultDAO.getInputTipTextDefaultByFieldName(patientDetails.getDiseaseId(), "followupplan", patientFollowUpCareItemDTO.getActivity());
				if(tipText != null) {
					patientFollowUpCareItemDTO.setInfoText(tipText.getTiptext());
					patientFollowUpCareItemDTO.setTipType(tipText.getTiptype());
				}
				careItems.add(patientFollowUpCareItemDTO);
			}
		}

		List<PatientConcernDTO> concernsOld = FollowUp.getPatientConcerns(patientId);
		List<PatientConcernDTO> concerns = null;
		if(concernsOld != null && !concernsOld.isEmpty()) {
			concerns = new ArrayList<PatientConcernDTO>();
			for (PatientConcernDTO patientFollowUpCareItemDTO : concernsOld) {
				InputDefaultDTO tipText = InputDefaultDAO.getInputTipTextDefaultByFieldName(patientDetails.getDiseaseId(), "followupplan", patientFollowUpCareItemDTO.getConcern());
				if(tipText != null) {
					patientFollowUpCareItemDTO.setInfoText(tipText.getTiptext());
					patientFollowUpCareItemDTO.setTipType(tipText.getTiptype());
				}
				concerns.add(patientFollowUpCareItemDTO);
			}
		}

		List<PatientGoalDTO> goalsOld = FollowUp.getPatientGoals(patientId);
		List<PatientGoalDTO> goals = null;
		if(goalsOld != null && !goalsOld.isEmpty()) {
			goals = new ArrayList<PatientGoalDTO>();
			for (PatientGoalDTO patientFollowUpCareItemDTO : goalsOld) {
				InputDefaultDTO tipText = InputDefaultDAO.getInputTipTextDefaultByFieldName(patientDetails.getDiseaseId(), "followupplan", patientFollowUpCareItemDTO.getGoal());
				if(tipText != null) {
					patientFollowUpCareItemDTO.setInfoText(tipText.getTiptext());
					patientFollowUpCareItemDTO.setTipType(tipText.getTiptype());
				}
				goals.add(patientFollowUpCareItemDTO);
			}
		}
		boolean isTemplate = DefaultTemplateDAO.isTemplate(patientDetails.getDiseaseId());
		render(patientId,noteList, ps, concerns, goals, careItems,isTemplate);
	}
	public static void chemotherapyForm(Integer patientId, Integer treatmentId, Integer initFlag, String formType) {
		Map<String, Object> jsonData = new HashMap<String, Object>();
		if (initFlag.intValue() == 0) {
			List<MedicineCatlogDTO> medications = Treatment.getAllMedications();
			List<ChemoScheduleDTO> chemoSchedules = Treatment.allChemoSchedules();
			List<SideEffectDTO> sideEffects = Treatment.allSideEffects();
			
			List<CareMember> doctors = UserDAO.verifiedDoctors();
			Map<Integer, String> doctorNames = new HashMap<Integer, String>();
			for(CareMember doctor : doctors) {			
				StringBuilder name = new StringBuilder("");
				if (doctor.getFirstName() != null) {
					name.append(doctor.getFirstName());
				}				
				doctorNames.put(doctor.getId(), name.toString());
			}
			jsonData.put("doctors", doctorNames);
			
			jsonData.put("medications", medications);
			jsonData.put("chemoSchedules", chemoSchedules);
			jsonData.put("sideEffects", sideEffects);
		}
		if (formType.equalsIgnoreCase("edit") && treatmentId != null) {
			PatientChemoTreatmentDTO pctDto = Treatment.getChemotherapy(treatmentId);
			jsonData.put("pctDto", pctDto);
		}
		renderJSON(jsonData);
	}
	public static void saveChemotherapyData(Integer patientId, Integer treatmentId, Map<String, String> ctInfo, Map<Integer, String> sideEffects) {
		
		System.out.println("------------  Chemotherapy ------------------");
		System.out.println("Patient ID: " + patientId.toString());
		if (treatmentId != null) {
			System.out.println("Treatment ID: " + treatmentId.toString());
		}
		for (String key: ctInfo.keySet()) {
			System.out.println(key + ": " + ctInfo.get(key));
		}
		if (sideEffects != null) {
			System.out.print("Side Effects: ");
			for (Integer key: sideEffects.keySet()) {
				System.out.print(sideEffects.get(key) + ", ");
			}
		}
		System.out.println("\n-------------------------------------");
		
		Treatment.saveChemoTreatment(patientId, treatmentId, ctInfo, sideEffects);
		treatmentPlan(patientId);
	}
	public static void removeChemotherapyData(Integer patientId, Integer treatmentId) {
		Treatment.removeChemotherapyData(treatmentId);
		treatmentPlan(patientId);
	}
	public static void surgeryForm(Integer patientId, Integer treatmentId, Integer initFlag, String formType) {
		Map<String, Object> jsonData = new HashMap<String, Object>();
		if (initFlag.intValue() == 0) {
			UserDetailsDTO userDto = UserDAO.getDetailsById(patientId);
			PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", userDto.getId());
			
			List<SurgeryTypeDTO> surgeryTypes = Treatment.getSurgeryTypes(patientOtherDetails.getDiseaseId());
//			List<TreatmentRegionDTO> treatmentRegions = Treatment.allTreatementRegions();
			
			
			List<TreatmentRegionDTO> treatmentRegions = Treatment.getTreatementRegions(patientOtherDetails.getDiseaseId());
			List<SideEffectDTO> sideEffects = Treatment.allSideEffects();
			
			List<CareMember> doctors = UserDAO.verifiedDoctors();
			Map<Integer, String> doctorNames = new HashMap<Integer, String>();
			for(CareMember doctor : doctors) {			
				StringBuilder name = new StringBuilder("");
				if (doctor.getFirstName() != null) {
					name.append(doctor.getFirstName());
				}				
				doctorNames.put(doctor.getId(), name.toString());
			}
			jsonData.put("doctors", doctorNames);
			
			jsonData.put("surgeryTypes", surgeryTypes);
			jsonData.put("treatmentRegions", treatmentRegions);
			jsonData.put("sideEffects", sideEffects);
		}
		if (formType.equalsIgnoreCase("edit") && treatmentId != null) {
			PatientSurgeryInfoDTO psiDto = Treatment.getSurgeryInfo(treatmentId);
			jsonData.put("psiDto", psiDto);
		}
		renderJSON(jsonData);
	}
	
	public static void concernForm(Integer concernId) {
		Map<String, Object> jsonData = new HashMap<String, Object>();
		if (concernId != null) {
			PatientConcernDTO concern = FollowUp.getConcern(concernId);
			jsonData.put("concern", concern);
		}
		renderJSON(jsonData);
	}
	
	public static void fupTemplateData(Integer patientId,String formOf) {
		Map<String, Object> jsonData = new HashMap<String, Object>();
	
		if(formOf.equals("disease")) {
			UserDetailsDTO userDto = UserDAO.getDetailsById(patientId);
			PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", userDto.getId());

			List<DefaultTemplateMasterDTO> dis = DefaultTemplateDAO.getPatientTemplate(patientOtherDetails.getDiseaseId());
			jsonData.put("disease",dis);
		} else if(formOf.equals("doctors")) {
			List<CareMember> doctors = UserDAO.verifiedDoctors();
			Map<Integer, String> doctorNames = new HashMap<Integer, String>();
			for(CareMember doctor : doctors) {			
				StringBuilder name = new StringBuilder("");
				if (doctor.getFirstName() != null) {
					name.append(doctor.getFirstName());
				}				
				doctorNames.put(doctor.getId(), name.toString());
			}
			jsonData.put("doctors", doctorNames);
		} else if(formOf.equals("activity")) {
			UserDetailsDTO userDto = UserDAO.getDetailsById(patientId);
			PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", userDto.getId());

			List<InputDefaultDTO> inputList = InputDefaultDAO.getInputDefaultByPageField("followupplan",patientOtherDetails.getDiseaseId(),formOf);
			jsonData.put("inputlist", inputList);
				
			List<CareMember> doctors = UserDAO.verifiedDoctors();
			ArrayList<String> doctorNames = new ArrayList<String>();
			for(CareMember doctor : doctors) {			
				StringBuilder name = new StringBuilder("");
				if (doctor.getFirstName() != null) {
					name.append(doctor.getFirstName());
				}				
				doctorNames.add(name.toString());
			}
			jsonData.put("doctors", doctorNames);
			ArrayList<String> frequencies = new ArrayList<String>();
			frequencies.add("Every month");
			frequencies.add("Every 3 months");
			frequencies.add("Every 6 months");
			frequencies.add("Every year");
			jsonData.put("frequencies", frequencies);
		} else {
				List<InputDefaultDTO> inputList = InputDefaultDAO.getInputDefaultByPageField("followupplan",formOf);
				jsonData.put("inputlist", inputList);
		}
		renderJSON(jsonData);
	}

	public static void goalForm(Integer goalId) {
		Map<String, Object> jsonData = new HashMap<String, Object>();
		if (goalId != null) {
			PatientGoalDTO goal = FollowUp.getGoal(goalId);
			jsonData.put("goal", goal);
		}
		renderJSON(jsonData);
	}
	
	public static void careItemForm(Integer careItemId,int patientId) {
		
		Map<String, Object> jsonData = new HashMap<String, Object>();
		if (careItemId != null) {
			PatientFollowUpCareItemDTO careItem = FollowUp.getCareItem(careItemId);
			jsonData.put("careItem", careItem);
		}
		renderJSON(jsonData);
	}
	
	public static void saveSurgeryData(Integer patientId, Integer treatmentId, Map<String, String> siInfo, Map<Integer, String> sideEffects) {
		/*
		System.out.println("------------  Surgery ------------------");
		System.out.println("Patient ID: " + patientId.toString());
		if (treatmentId != null) {
			System.out.println("Treatment ID: " + treatmentId.toString());
		}
		for (String key: siInfo.keySet()) {
			System.out.println(key + ": " + siInfo.get(key));
		}
		if (sideEffects != null) {
			System.out.print("Side Effects: ");
			for (Integer key: sideEffects.keySet()) {
				System.out.print(sideEffects.get(key) + ", ");
			}
		}
		System.out.println("\n-------------------------------------");
		*/
		Treatment.saveSurgeryInfo(patientId, treatmentId, siInfo, sideEffects);
		treatmentPlan(patientId);
	}
	
	public static void saveConcern(Integer patientId, Integer concernId, Map<String, String> fupConcern) {
		System.out.println("------------  Concern ------------------");
		System.out.println("Patient ID: " + patientId.toString());
		if (concernId != null) {
			System.out.println("Concern ID: " + concernId.toString());
		}
		for (String key: fupConcern.keySet()) {
			System.out.println(key + ": " + fupConcern.get(key));
		}
		System.out.println("\n-------------------------------------");
		
		FollowUp.saveConcern(patientId, concernId, fupConcern);
		followupPlan(patientId.intValue());
	}
	
	public static void saveGoal(Integer patientId, Integer goalId, Map<String, String> fupGoal) {
		System.out.println("------------  Goal ------------------");
		System.out.println("Patient ID: " + patientId.toString());
		if (goalId != null) {
			System.out.println("Goal ID: " + goalId.toString());
		}
		for (String key: fupGoal.keySet()) {
			System.out.println(key + ": " + fupGoal.get(key));
		}
		System.out.println("\n-------------------------------------");
		
		FollowUp.saveGoal(patientId, goalId, fupGoal);
		followupPlan(patientId.intValue());
	}
	
	public static void saveCareItem(Integer patientId, Integer careItemId, Map<String, String> fupCareItem) {
		System.out.println("------------  CareItem ------------------");
		System.out.println("Patient ID: " + patientId.toString());
		if (careItemId != null) {
			System.out.println("CareItem ID: " + careItemId.toString());
		}
		for (String key: fupCareItem.keySet()) {
			System.out.println(key + ": " + fupCareItem.get(key));
		}
		System.out.println("\n-------------------------------------");
		
		FollowUp.saveCareItem(patientId, careItemId, fupCareItem);
		followupPlan(patientId.intValue());
	}
	
	public static void removeSurgeryData(Integer patientId, Integer treatmentId) {
		Treatment.removeSurgeryData(treatmentId);
		treatmentPlan(patientId);
	}
	
	public static void removeConcern(Integer patientId, Integer concernId) {
		FollowUp.removeConcern(concernId);
		followupPlan(patientId.intValue());
	}
	
	public static void removeGoal(Integer patientId, Integer goalId) {
		FollowUp.removeGoal(goalId);
		followupPlan(patientId.intValue());
	}
	
	public static void removeCareItem(Integer patientId, Integer careItemId) {
		FollowUp.removeCareItem(careItemId);
		followupPlan(patientId.intValue());
	}
	
	public static void radiationForm(Integer patientId, Integer treatmentId, Integer initFlag, String formType) {
		Map<String, Object> jsonData = new HashMap<String, Object>();
		if (initFlag.intValue() == 0) {
			UserDetailsDTO userDto = UserDAO.getDetailsById(patientId);
			PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", userDto.getId());
			
			List<RadiationTypeDTO> radiationTypes = Treatment.allRadiationTypes();
			List<RadiationScheduleDTO> radiationSchedules = Treatment.allRadiationSchedules();
//			List<TreatmentRegionDTO> treatmentRegions = Treatment.allTreatementRegions();
			List<TreatmentRegionDTO> treatmentRegions = Treatment.getTreatementRegions(patientOtherDetails.getDiseaseId());
			List<SideEffectDTO> sideEffects = Treatment.allSideEffects();
			List<CareMember> doctors = UserDAO.verifiedDoctors();
			Map<Integer, String> doctorNames = new HashMap<Integer, String>();
			for(CareMember doctor : doctors) {			
				StringBuilder name = new StringBuilder("");
				if (doctor.getFirstName() != null) {
					name.append(doctor.getFirstName());
				}				
				doctorNames.put(doctor.getId(), name.toString());
			}
			jsonData.put("doctors", doctorNames);
			jsonData.put("radiationTypes", radiationTypes);
			jsonData.put("radiationSchedules", radiationSchedules);
			jsonData.put("treatmentRegions", treatmentRegions);
			jsonData.put("sideEffects", sideEffects);
		}
		if (formType.equalsIgnoreCase("edit") && treatmentId != null) {
			PatientRadiationTreatmentDTO prtDto = Treatment.getRadiationTreatment(treatmentId);
			jsonData.put("prtDto", prtDto);
		}
		renderJSON(jsonData);
	}
	
	public static void saveRadiationData(Integer patientId, Integer treatmentId, Map<String, String> rtInfo, Map<Integer, String> sideEffects) {
		Treatment.saveRadiationTreatment(patientId, treatmentId, rtInfo, sideEffects);
		treatmentPlan(patientId);
	}
	public static void removeRadiationData(Integer patientId, Integer treatmentId) {
		Treatment.removeRadiationData(treatmentId);
		treatmentPlan(patientId);
	}
	public static void diagnosis(int patientId) {
		Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(patientId);
		UserDetailsDTO userDetails = (UserDetailsDTO) patientInfo.get("userDetails");
		PatientDetailDTO patientDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
		BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
		List<PatientMutationDTO> mutations = PatientDetailDAO.getMutations(new Integer(patientId));
		List<PatientChromosomeDTO> chromosomes =  PatientDetailDAO.getChromosome(new Integer(patientId));
		int breastCancerId = Disease.BREAST_CANCER_ID;
		Map <String, Object> ps = PatientDetailDAO.patientSummary(patientId);
		render(patientId, breastCancerId, userDetails, patientDetails, breastCancerInfo, ps,mutations,chromosomes);
	}
	public static void patientInfo(Integer patientId) {
		Map <String, Object> patientSummary = PatientDetailDAO.patientSummary(patientId);		
		renderArgs.put("ps", patientSummary);		
		render();
	}
	public static void patientInfoJSON(Integer patientId) {
		Map<String, Object> json = PatientDetailDAO.getInfo(patientId.intValue());
		renderJSON(json);
	}
	public static void savePatientInfo(Integer patientId,  Map<String, String> info) {
		PatientDetailDAO.saveInfo(patientId.intValue(), info);
		Map <String, Object> patientSummary = PatientDetailDAO.patientSummary(patientId);		
		renderArgs.put("ps", patientSummary);
		renderTemplate("CarePatien/patientInfo.html");
	}
	public static void diagnosisJSON(int patientId) {
		Map<String, Object> jsonData = PatientDetailDAO.getDiagnosisJSON(patientId);
		renderJSON(jsonData);
	}
	
	public static void updateDiagnosis(int patientId, Integer diseaseId, String dateOfDiagnosis, Map<String, String> diseaseInfo) {
		PatientDetailDAO.updateDiagnosis(patientId, diseaseId, dateOfDiagnosis, diseaseInfo);
		Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(patientId);
		UserDetailsDTO userDetails = (UserDetailsDTO) patientInfo.get("userDetails");
		PatientDetailDTO patientDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
		BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
		List<PatientMutationDTO> mutations = PatientDetailDAO.getMutations(new Integer(patientId));
		List<PatientChromosomeDTO> chromosomes =  PatientDetailDAO.getChromosome(new Integer(patientId));
		int breastCancerId = Disease.BREAST_CANCER_ID;
		Map <String, Object> ps = PatientDetailDAO.patientSummary(patientId);
		renderTemplate("CarePatien/diagnosis.html", patientId, breastCancerId, userDetails, patientDetails, breastCancerInfo, ps,mutations,chromosomes);
	}

	
	public static void noteOperation(String operation,int id,int patientId,String title,String description) {
		
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDTO noteBy = UserDAO.getUserBasicByField("id",user.getId());
		operation = operation==null?"add":operation;
		try {
			if("add".equalsIgnoreCase(operation)) {
				NoteDTO  noteDto = new NoteDTO();
				noteDto.setNoteBy(noteBy);
				noteDto.setNoteDate(new Date());
				noteDto.setNoteDesc(description);
				noteDto.setNoteEditDate(new Date());
				UserDTO noteFor = UserDAO.getUserBasicByField("id",patientId);
				noteDto.setNoteFor(noteFor);
				noteDto.setNoteTitle(title);
				BaseDAO.save(noteDto);
			} else if("edit".equalsIgnoreCase(operation)) {
				Integer idField = new Integer(id);
				NoteDTO  noteDto = NotesDAO.getByField("id", idField);
				if(noteDto != null) {
					noteDto.setNoteDesc(description);
					noteDto.setNoteTitle(title);
					noteDto.setNoteEditDate(new Date());
					BaseDAO.update(noteDto);
				}
			} else if("delete".equalsIgnoreCase(operation)) {
				Integer idField = new Integer(id);
				NoteDTO  noteDto = NotesDAO.getByField("id", idField);
				if(noteDto != null) {
					BaseDAO.remove(noteDto);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Care.patient(patientId+"");
	}
	
	public static void patientMedication(int id) {
		Integer idField = new Integer(id);
		PatientMedicationDTO dto = null;
		dto = MedicationDAO.getMedicineByField("id", idField);
		if(dto != null) {
			if(dto.getCaremembername() == null) {
				dto.setCaremembername(UserDAO.getUserName(dto.getCaremember().getId()));
			}
			renderJSON(dto);
		} else {
			renderText(".");
		}
	}
	
	public static void patientAppointment(int id) {
		Integer idField = new Integer(id);
		AppointmentDTO dto = AppointmentDAO.getAppointmentByField("id",idField);
		if(dto != null) {
			renderJSON(dto);
		} else {
			renderText(".");
		}
	}
	
	public static void medicineFileupload(int id, File image_file_input) {
		if (image_file_input != null) {
			String fileName = image_file_input.getName();
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (fileExt.equalsIgnoreCase("png") || fileExt.equalsIgnoreCase("jpg") || fileExt.equalsIgnoreCase("jpeg") || fileExt.equalsIgnoreCase("gif")) {
				if (image_file_input.length() > 2000000) {
					session.put("image_upload", "error");
					renderText("invalid file size"); 
				} else {
					try {
						BufferedImage bsrc = ImageIO.read(image_file_input);
						ByteArrayOutputStream baos = ImageUtil.getImageArray(bsrc,fileExt.toUpperCase());
						Integer idField = new Integer(id);
						PatientMedicationDTO  patientMedicationDTO = MedicationDAO.getMedicineByField("id", idField);
						if(patientMedicationDTO != null) {
							MedicineMasterDTO medicineMasterDTO = patientMedicationDTO.getMedicine();
							medicineMasterDTO.setImage(baos.toByteArray());
							BaseDAO.update(medicineMasterDTO);
						}
						renderText(id);
					} catch (IOException e) {
						renderText("error converting image"); 
					}
					renderText("image uploaded"); 
				}
			} else {
				renderText("invalid file type"); 
			}
		}
	}

	public static void autoCompleteMedic(String term) {
		List<MedicineCatlogDTO> list = MedicationDAO.searchMedicineCatlog("label","%"+term+"%");
		if(list != null) {
			renderJSON(list);
		} else {
			renderText(".");
		}
	}
	
	public static void medicationOperation(String operation,int id,int patientId,int catlogId,String genericname,String brandname,String frequency,String memberid,Integer careMembername,String startDate,String endDate, String otherdetails) {
		UserDTO patientBy = UserDAO.getUserBasicByField("id",patientId);
		UserDTO drUser = null;
		if(careMembername != null) {
			drUser = UserDAO.getUserBasicByField("id", careMembername);
		}
		operation = operation==null?"add":operation;
		System.out.println(startDate +" : "+endDate);
		//10/15/2014 : 10/15/2014
		try {
			if("add".equalsIgnoreCase(operation)) {
				Date startDt = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
				Date endDt = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
//				System.out.println("catlogId: " + catlogId);
				MedicineCatlogDTO catlog = MedicationDAO.getMedicineCatloagByField("id",new Integer(catlogId));
				MedicineMasterDTO medicineMasterDTO = new MedicineMasterDTO();
				medicineMasterDTO.setBrandname(brandname);
				medicineMasterDTO.setGenericname(genericname);
				if(catlog != null) {
					medicineMasterDTO.setMethod(catlog.getMethod());
					medicineMasterDTO.setImage(catlog.getImage());
				} else {
					medicineMasterDTO.setMethod("oral");
				}
				BaseDAO.save(medicineMasterDTO);
				PatientMedicationDTO patientMedicationDTO = new PatientMedicationDTO();
				if(catlog != null) {
					patientMedicationDTO.setMethod(catlog.getMethod());
				} else {
					patientMedicationDTO.setMethod("oral");
				}
				patientMedicationDTO.setActive(true);
				patientMedicationDTO.setAdddate(new Date());
				patientMedicationDTO.setCaremember(drUser);
				patientMedicationDTO.setCaremembername(memberid);
				patientMedicationDTO.setEnddate(new Date());
				patientMedicationDTO.setFrequency(frequency);
				patientMedicationDTO.setMedicine(medicineMasterDTO);
				
				patientMedicationDTO.setPatient(patientBy);
				patientMedicationDTO.setPatientid(patientId);
				patientMedicationDTO.setSpecialinstruction(otherdetails);
				
				patientMedicationDTO.setStartdate(startDt);
				patientMedicationDTO.setEnddate(endDt);
				
				BaseDAO.save(patientMedicationDTO);
			} else if("edit".equalsIgnoreCase(operation)) {
				Date startDt = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
				Date endDt = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
				Integer idField = new Integer(id);
				PatientMedicationDTO  patientMedicationDTO = MedicationDAO.getMedicineByField("id", idField);
				if(patientMedicationDTO != null) {
					MedicineMasterDTO medicineMasterDTO = patientMedicationDTO.getMedicine();
					medicineMasterDTO.setBrandname(brandname);
					medicineMasterDTO.setGenericname(genericname);
					BaseDAO.update(medicineMasterDTO);
					
					patientMedicationDTO.setCaremember(drUser);
					patientMedicationDTO.setCaremembername(memberid);
					patientMedicationDTO.setFrequency(frequency);
					patientMedicationDTO.setMedicine(medicineMasterDTO);
					patientMedicationDTO.setSpecialinstruction(otherdetails);

					patientMedicationDTO.setStartdate(startDt);
					patientMedicationDTO.setEnddate(endDt);

					BaseDAO.update(patientMedicationDTO);
					
				}
			} else if("delete".equalsIgnoreCase(operation)) {
				Integer idField = new Integer(id);
				PatientMedicationDTO  dto = MedicationDAO.getMedicineByField("id", idField);
				if(dto != null) {
					dto.setActive(false);
					BaseDAO.update(dto);
				}
			} else if("removeMethod".equalsIgnoreCase(operation)) {
				Integer idField = new Integer(id);
				PatientMedicationDTO  dto = MedicationDAO.getMedicineByField("id", idField);
				if(dto != null) {
					dto.setMethod(null);
					BaseDAO.update(dto);
				}
			} else if("addMethod".equalsIgnoreCase(operation)) {
				Integer idField = new Integer(id);
				PatientMedicationDTO  dto = MedicationDAO.getMedicineByField("id", idField);
				if(dto != null) {
					dto.setMethod("oral");
					BaseDAO.update(dto);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		JsonObject obj = new JsonObject();
		obj.add("status", new JsonPrimitive("200"));
		renderJSON(obj);
	}
	
	public static void careteamSpecific(int careTeamId) {
		System.out.println(careTeamId);
		CareTeamMasterDTO careteam = CareTeamDAO.getCareTeamByField("id", careTeamId);
		List<CareTeamMemberDTO>  memberList = CareTeamDAO.getCareTeamMembersByField("careteamid", careTeamId);
		UserDetailsDTO userDetails = null;
		ExpertDetailDTO expertDetail = null;
		ExpertBean expertBean =null;
		ExpertBean expertBeanHead = new ExpertBean();
		ArrayList<ExpertBean> otherExpert = new ArrayList<ExpertBean>(); 
		int iMemberCount = 0;
		if(memberList != null && memberList.size()>0) {
			for (CareTeamMemberDTO expertBean2 : memberList) {
				 expertBean = new ExpertBean();
				System.out.println(expertBean2.getMember().getEmail() );
				userDetails = UserDAO.getDetailsById(expertBean2.getMemberid());
				expertDetail = ProfileDAO.getExpertByField("id", expertBean2.getMemberid());
				expertBean.setUserDetails(userDetails);
				expertBean.setExpertDetail(expertDetail);
				if(iMemberCount==0) {
					expertBeanHead = expertBean;
					iMemberCount++;
				} else {
					otherExpert.add(expertBean);
				}	
			}
		}
		render("CarePatien/careteamblock.html",careteam,otherExpert,expertBeanHead);
	}
	
	public static void appointmentOperation(String operation,int patientId,int id,String purpose, String purposeText, String treatmentProcessStep, String time,String schDate, 
			boolean repeatWeeklyBtn, String startsOn, String endsOnCheck, int occurences, String endsOnDate, String editOccurencesAction, String center,
			int memberid, String membername, String address1,String city,String state,String zip) {
		
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDTO addedby = UserDAO.getUserBasicByField("id",user.getId());
		
//		System.out.println("repeatWeeklyBtn : " + repeatWeeklyBtn);
//		System.out.println("startsOn : " + startsOn);
//		System.out.println("endsOnCheck : " + endsOnCheck);
//		System.out.println("occurences : " + occurences);
//		System.out.println("endsOnDate: " + endsOnDate);

		try {
			if("add".equalsIgnoreCase(operation)) {
				
				AddressDTO address = new AddressDTO();
				address.setCity(city);
				address.setLine1(address1);
				address.setState(state);
				address.setZip(zip);
				BaseDAO.save(address);
				AppointmentGroupDTO appGroup = null;
				Date appStartsOn = null;
				if(startsOn != null && !startsOn.equals("")){
					appStartsOn = new SimpleDateFormat("MM/dd/yyyy").parse(startsOn);
				}
				Date appEndsOnDate = null;
				if(endsOnDate != null && !endsOnDate.equals("")){
					appEndsOnDate = new SimpleDateFormat("MM/dd/yyyy").parse(endsOnDate);
				}

				if(StringUtils.isBlank(endsOnCheck)) {
					endsOnCheck = "";
				}
				if(repeatWeeklyBtn) {
//					endsOn : Given Date
//					never : never
//					after : after some occurences
					appGroup = new AppointmentGroupDTO();
					appGroup.setStartson(appStartsOn);
					Calendar cal  = Calendar.getInstance();
					cal.setTime(appStartsOn);
					cal.add(Calendar.DATE, 7);
					appStartsOn = cal.getTime();

					if("endsOn".equalsIgnoreCase(endsOnCheck)) {
						appGroup.setEndsondate(appEndsOnDate);	
					} else if("never".equalsIgnoreCase(endsOnCheck)) {
						appGroup.setEndneverflag(true);	
					} else if("after".equalsIgnoreCase(endsOnCheck)) {
						appGroup.setOccurences(occurences);	
					}
					appGroup.setAddressid(address.getId());
					BaseDAO.save(appGroup);
				}
				UserDTO patient = UserDAO.getUserBasicByField("id", patientId);
				AppointmentDTO app = new AppointmentDTO();
				app.setAddedby(addedby);
				app.setAddedon(new Date());
				app.setAddressid(address);
				app.setAppointmentcenter(center);
				//08/19/2014
				//mm/dd/yyyy
				Date appointmentDate = new SimpleDateFormat("MM/dd/yyyy").parse(schDate);
				app.setAppointmentdate(appointmentDate);
				app.setAppointmenttime(time);
				UserDTO caremember = null;
				if (memberid > 0) {
					caremember = UserDAO.getUserBasicByField("id", memberid);
					app.setCaremember(caremember);
				}
				app.setCareMemberName(membername);
				Integer appIdInt = null;
				if (Integer.valueOf(purpose) > 0) {
					app.setPurpose(purpose);
					appIdInt = new Integer(purpose);
					app.setAppointmentid(AppointmentMasterDAO.getAppointmentByField("id", appIdInt));
				}
				app.setTreatementStep(treatmentProcessStep);
				app.setPurposeText(purposeText);
				
				app.setPatientid(patient);

				if(appGroup!= null)
					app.setAppointmentgroupid(new Integer(appGroup.getId()));

				BaseDAO.save(app);

				if (treatmentProcessStep.equalsIgnoreCase(PatientAlert.APPOINTMENT_STEP_FIRST_APPOINTMENT)) {
					PatientAlert.firstAppointmentScheduledAlert(patient, app);
				}
				
				if(appGroup != null){
					if("after".equalsIgnoreCase(endsOnCheck) && occurences > 0) {
						for(int i = 0; i < occurences-1; i++) {
							app = new AppointmentDTO();
							app.setAddedby(addedby);
							app.setAddedon(new Date());
							app.setAddressid(address);
							app.setAppointmentcenter(center);
							app.setAppointmentdate(appStartsOn);
							app.setAppointmenttime(time);
							app.setCaremember(caremember);
							app.setCareMemberName(membername);
							if (Integer.valueOf(purpose) > 0) {
								app.setPurpose(purpose);
								app.setAppointmentid(AppointmentMasterDAO.getAppointmentByField("id", appIdInt));
							}
							app.setTreatementStep(treatmentProcessStep);
							app.setPurposeText(purposeText);
							app.setPatientid(patient);
							app.setAppointmentgroupid(appGroup.getId());
							BaseDAO.save(app);
							if (treatmentProcessStep.equalsIgnoreCase(PatientAlert.APPOINTMENT_STEP_FIRST_APPOINTMENT)) {
								PatientAlert.firstAppointmentScheduledAlert(patient, app);
							}
							Calendar cal  = Calendar.getInstance();
							cal.setTime(appStartsOn);
							cal.add(Calendar.DATE, 7);
							appStartsOn = cal.getTime();
						}
					} else if("endsOn".equalsIgnoreCase(endsOnCheck) && endsOnDate != null && !endsOnDate.equals("")) {
						appEndsOnDate = new SimpleDateFormat("MM/dd/yyyy").parse(endsOnDate);
						while(appStartsOn.compareTo(appEndsOnDate) <= 0){
							app = new AppointmentDTO();
							app.setAddedby(addedby);
							app.setAddedon(new Date());
							app.setAddressid(address);
							app.setAppointmentcenter(center);
							app.setAppointmentdate(appStartsOn);
							app.setAppointmenttime(time);
							app.setCaremember(caremember);
							app.setCareMemberName(membername);
							if (Integer.valueOf(purpose) > 0) {
								app.setPurpose(purpose);
								app.setAppointmentid(AppointmentMasterDAO.getAppointmentByField("id", appIdInt));
							}
							app.setTreatementStep(treatmentProcessStep);
							app.setPurposeText(purposeText);
							app.setPatientid(patient);
							app.setAppointmentgroupid(appGroup.getId());
							BaseDAO.save(app);

							if (treatmentProcessStep.equalsIgnoreCase(PatientAlert.APPOINTMENT_STEP_FIRST_APPOINTMENT)) {
								PatientAlert.firstAppointmentScheduledAlert(patient, app);
							}
							Calendar cal  = Calendar.getInstance();
							cal.setTime(appStartsOn);
							cal.add(Calendar.DATE, 7);
							appStartsOn = cal.getTime();
						}
					} else if("never".equalsIgnoreCase(endsOnCheck)) {
						// Calculate endDate for 1 year
						Calendar cal  = Calendar.getInstance();
						cal.setTime(appointmentDate);
						cal.add(Calendar.YEAR , 1);
						appEndsOnDate = cal.getTime();
						while(appointmentDate.compareTo(appEndsOnDate) <= 0) {
							app = new AppointmentDTO();
							app.setAddedby(addedby);
							app.setAddedon(new Date());
							app.setAddressid(address);
							app.setAppointmentcenter(center);
							app.setAppointmentdate(appStartsOn);
							app.setAppointmenttime(time);
							app.setCaremember(caremember);
							app.setCareMemberName(membername);
							if (Integer.valueOf(purpose) > 0) {
								app.setPurpose(purpose);
								app.setAppointmentid(AppointmentMasterDAO.getAppointmentByField("id", appIdInt));
							}
							app.setTreatementStep(treatmentProcessStep);
							app.setPurposeText(purposeText);
							app.setPatientid(patient);
							app.setAppointmentgroupid(appGroup.getId());
							BaseDAO.save(app);
							cal  = Calendar.getInstance();
							cal.setTime(appStartsOn);
							cal.add(Calendar.DATE, 7);
							appStartsOn = cal.getTime();
						}
					}
				}
			} else if("edit".equalsIgnoreCase(operation)) {
				
				if(editOccurencesAction.equalsIgnoreCase("onlyThisEvent")){
					
					//Need to code this.
					Integer appId =  new Integer(id);
					AppointmentDTO app = AppointmentDAO.getAppointmentByField("id",appId);
					if (Integer.valueOf(purpose) > 0) {
						app.setPurpose(purpose);
						Integer appIdInt = new Integer(purpose);
						app.setAppointmentid(AppointmentMasterDAO.getAppointmentByField("id", appIdInt));
					} else {
						app.setPurpose(null);
						app.setAppointmentid(null);
					}
					app.setTreatementStep(treatmentProcessStep);
					app.setPurposeText(purposeText);
					
					app.setAppointmenttime(time);
					app.setAppointmentcenter(center);
					if (memberid > 0) {
						UserDTO caremember = UserDAO.getUserBasicByField("id", memberid);
						app.setCaremember(caremember);
					}
					app.setCareMemberName(membername);
					//08/19/2014
					//mm/dd/yyyy
					Date appointmentDate = new SimpleDateFormat("MM/dd/yyyy").parse(schDate);
					app.setAppointmentdate(appointmentDate);
					
					AppointmentGroupDTO group = AppointmentDAO.getAppointmentGroupByField("id",app.getAppointmentgroupid());
					if(app.getAppointmentgroupid() != null && group.getAddressid() != null && group.getAddressid().intValue() ==  app.getAddressid().getId()) {
						AddressDTO address = new AddressDTO();
						address.setCity(city);
						address.setLine1(address1);
						address.setState(state);
						address.setZip(zip);
						BaseDAO.save(address);
						app.setAddressid(address);
					} else {
						AddressDTO address = app.getAddressid();
						address.setCity(city);
						address.setLine1(address1);
						address.setState(state);
						address.setZip(zip);
						BaseDAO.update(address);
					}
					BaseDAO.update(app);

				} else if(editOccurencesAction.equalsIgnoreCase("allEvents")) {
					//Need to code this.
					Integer appId =  new Integer(id);
					AppointmentDTO app = AppointmentDAO.getAppointmentByField("id",appId);
					
					//Edit date of only current appointment.
					Date appointmentDate = new SimpleDateFormat("MM/dd/yyyy").parse(schDate);
					app.setAppointmentdate(appointmentDate);
					BaseDAO.update(app);
					
					
					Integer appointmentGroupId = app.getAppointmentgroupid();

					EntityManager em = JPAUtil.getEntityManager();
					String hql ="update AppointmentDTO set purpose = :fp1, appointmentid = :fp2, treatment_process_step = :fp4, purpose_text = :fp5, "
					+"appointmenttime = :fp6, appointmentcenter = :fp8, caremember = :fp9, caremember_name = :fp10 where appointmentgroupid = :f0";
					if(!em.getTransaction().isActive())
						em.getTransaction().begin();	
					
					Query query = em.createQuery(hql);
					
					if (Integer.valueOf(purpose) > 0) {
						query.setParameter("fp1", purpose);
						Integer appIdInt = new Integer(purpose);
						query.setParameter("fp2", AppointmentMasterDAO.getAppointmentByField("id", appIdInt));
					} else {
						query.setParameter("fp1", null);
						query.setParameter("fp2", null);
					}
					query.setParameter("fp4", treatmentProcessStep);
					query.setParameter("fp5", purposeText);
					query.setParameter("fp6", time);
					query.setParameter("fp8", center);
					
					if (memberid > 0) {
						UserDTO caremember = UserDAO.getUserBasicByField("id", memberid);
						query.setParameter("fp9", caremember);
					} else {
						query.setParameter("fp9", null);
					}
					query.setParameter("fp10", membername);
					
					query.setParameter("f0", appointmentGroupId);
					query.executeUpdate();
					em.getTransaction().commit();
					
					AddressDTO address = app.getAddressid();
					address.setCity(city);
					address.setLine1(address1);
					address.setState(state);
					address.setZip(zip);
					BaseDAO.update(address);
					
					List<AppointmentDTO> lst = AppointmentDAO.getAppointmentListByField("appointmentgroupid",appointmentGroupId);
					AppointmentGroupDTO group = AppointmentDAO.getAppointmentGroupByField("id",app.getAppointmentgroupid());
					for (AppointmentDTO appointmentDTO : lst) {
						if(appointmentDTO.getAppointmentgroupid() != null && group.getAddressid() != null && group.getAddressid().intValue() !=  app.getAddressid().getId()) {
							address = appointmentDTO.getAddressid();
							address.setCity(city);
							address.setLine1(address1);
							address.setState(state);
							address.setZip(zip);
							BaseDAO.update(address);
						}
					}
				}
			} else if("delete".equalsIgnoreCase(operation)) {
				//Need to code this.
				if(editOccurencesAction.equalsIgnoreCase("onlyThisEvent")){
					Integer appId =  new Integer(id);
					AppointmentDTO dto = AppointmentDAO.getAppointmentByField("id",appId);
					dto.setDeleteflag(true);
					BaseDAO.update(dto);
				}else if(editOccurencesAction.equalsIgnoreCase("allEvents")){
					Integer appId =  new Integer(id);
					AppointmentDTO dto = AppointmentDAO.getAppointmentByField("id",appId);
					
					int appointmentGroupId = dto.getAppointmentgroupid();
					
					EntityManager em = JPAUtil.getEntityManager();
					String hql ="update AppointmentDTO set deleteflag = :fp1 where appointmentgroupid = :f0";
					if(!em.getTransaction().isActive())
						em.getTransaction().begin();	
					
					Query query = em.createQuery(hql);
					query.setParameter("fp1", true);
					query.setParameter("f0", appointmentGroupId);
					
					query.executeUpdate();
					em.getTransaction().commit();
					
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		JsonObject obj = new JsonObject();
		obj.add("status", new JsonPrimitive("200"));
		renderJSON(obj);
	}
	
	public static void careitemTemplateData(Integer patientId, Integer diseaseId) {
		System.out.println("patientId: "+ patientId);
		System.out.println("diseaseId: "+ diseaseId);

		/*
		Activity: Medical history and physical exam	
		Frequency: Every 6 months

		Activity: Mammogram	
		Frequency: Every year

		Activity: Breast self-exam	
		Frequency: Monthly

		Activity: Pelvic exam	
		Frequency: Every year
		*/

//		List<InputDefaultDTO> defaults = InputDefaultDAO.getInputDefaultByPageField("followupplan",diseaseId,"activity");
		 List<DefaultTemplateDetailDTO> defaults = DefaultTemplateDAO.getInputDefaultByPageField(diseaseId); 
		if(defaults != null && !defaults.isEmpty()) {
			Integer careItemId = null;
			for (DefaultTemplateDetailDTO inputDefaultDTO : defaults) {
				Map<String, String> fupCareItem = new HashMap<String, String>();
				fupCareItem.put("activity",inputDefaultDTO.getFieldtext());
				fupCareItem.put("frequency",inputDefaultDTO.getFrequency());
				fupCareItem.put("purpose",inputDefaultDTO.getOtherfield());
				fupCareItem.put("endDate","");
				if(inputDefaultDTO.getEnddate() != null && inputDefaultDTO.getEnddate().equalsIgnoreCase("ongoing")) {
					fupCareItem.put("ongoing",inputDefaultDTO.getEnddate());
					fupCareItem.put("endDate",null);
				} else {
					fupCareItem.put("ongoing",null);
					fupCareItem.put("endDate",inputDefaultDTO.getEnddate());	
				}
				fupCareItem.put("doctor","");
				FollowUp.saveCareItem(patientId, careItemId, fupCareItem);		
			}
		}
		followupPlan(patientId.intValue());
	}
}
