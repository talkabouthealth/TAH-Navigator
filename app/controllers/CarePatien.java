package controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
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
import org.joda.time.DateTime;

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
import models.OtherCareMemberDTO;
import models.PatienCareTeamDTO;
import models.PatientCareTeamMemberDTO;
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
import nav.dao.CarePlanPrintDAO;
import nav.dao.CareTeamDAO;
import nav.dao.DefaultTemplateDAO;
import nav.dao.Disease;
import nav.dao.DistressDAO;
import nav.dao.FollowUp;
import nav.dao.InputDefaultDAO;
import nav.dao.MedicationDAO;
import nav.dao.NotesDAO;
import nav.dao.NotificationDAO;
import nav.dao.PatientDetailDAO;
import nav.dao.ProfileDAO;
import nav.dao.Treatment;
import nav.dao.UserDAO;
import nav.dto.CareMember;
import models.CarePlanPrintDTO;
import nav.dto.DistressBean;
import nav.dto.ExpertBean;
import nav.dto.TAHConstants;
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
	
	public static void saveCarePlanPrint(String patientId,String note)
	{
		UserBean user = CommonUtil.loadCachedUser(session);
		CarePlanPrintDTO carePlanPrintDTO = new CarePlanPrintDTO();
		carePlanPrintDTO.setExpertId(user.getId());
		int pid = Integer.parseInt(patientId);
		carePlanPrintDTO.setIssueDate(new Date());
		carePlanPrintDTO.setPatientId(pid);
		carePlanPrintDTO.setNote(note);
		BaseDAO.save(carePlanPrintDTO);
		System.out.println(user.getId()+" print plan saved");
		Date curDate = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyyy hh:mm a");

		UserDTO noteBy = UserDAO.getUserBasicByField("id",user.getId());
		String title= "Patient given printed care plan on "+ft.format(curDate); 
		if(note.equals(""))
		{
			note = "given on "+ft.format(curDate);
		}
		
		NoteDTO  noteDto = new NoteDTO();
		noteDto.setNoteBy(noteBy);
		noteDto.setNoteDate(curDate);
		noteDto.setNoteDesc(note);
		noteDto.setNoteEditDate(curDate);
		UserDTO noteFor = UserDAO.getUserBasicByField("id",new Integer(patientId));
		noteDto.setNoteFor(noteFor);
		noteDto.setNoteTitle(title);
		noteDto.setNoteSection("summary");
		BaseDAO.save(noteDto);
		System.out.println(noteFor.getId()+" note saved");
		
		renderText(CarePlanPrintDAO.getPrintCount(pid));
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
	
	public static void getNumberOfPrints(int patientId)
	{
		renderText(CarePlanPrintDAO.getPrintCount(patientId));
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
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		DateFormat dfDisplay = new SimpleDateFormat("mm/dd/yyyy");
		Date startDt = null;
		Date endDt = null;
		for (PatientMedicationDTO patientMedicationDTO : medicationList) {
			if(patientMedicationDTO.getCaremembername() == null) {
				patientMedicationDTO.setCaremembername(UserDAO.getUserName(patientMedicationDTO.getCaremember().getId()));
			}
			try {
				startDt = df.parse(patientMedicationDTO.getStartdate());
				patientMedicationDTO.setStartdate(dfDisplay.format(startDt));
			} catch(Exception e) {
//				e.printStackTrace();
			}
			try {
				endDt = df.parse(patientMedicationDTO.getEnddate());
				patientMedicationDTO.setEnddate(dfDisplay.format(endDt));
			} catch(Exception e) {
//				e.printStackTrace();
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
		List<NoteDTO> noteList = NotesDAO.getPatientNotesList(patientId+"");
		List<PatientRadiationTreatmentDTO> radiationTreatments = Treatment.getPatientRadiationTreatments(patientId);
		List<PatientChemoTreatmentDTO> chemoTreatments = Treatment.getPatientChemoTreatments(patientId);
		List<PatientSurgeryInfoDTO> surgeryInfo = Treatment.getPatientSurgeryInfo(patientId);
		Map <String, Object> ps = PatientDetailDAO.patientSummary(patientId);
		render(patientId, radiationTreatments, chemoTreatments, surgeryInfo, ps,noteList);
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
		renderText("OK");
//		treatmentPlan(patientId);
	}
	public static void removeChemotherapyData(Integer patientId, Integer treatmentId) {
		Treatment.removeChemotherapyData(treatmentId);
		renderText("OK");
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
		renderText("OK");
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
//		followupPlan(patientId.intValue());
		renderText("OK");
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
//		followupPlan(patientId.intValue());
		renderText("OK");
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
//		followupPlan(patientId.intValue());
		renderText("OK");
	}
	
	public static void removeSurgeryData(Integer patientId, Integer treatmentId) {
		Treatment.removeSurgeryData(treatmentId);
//		treatmentPlan(patientId);
		renderText("OK");
	}
	
	public static void removeConcern(Integer patientId, Integer concernId) {
		FollowUp.removeConcern(concernId);
//		followupPlan(patientId.intValue());
		renderText("OK");
	}
	
	public static void removeGoal(Integer patientId, Integer goalId) {
		FollowUp.removeGoal(goalId);
//		followupPlan(patientId.intValue());
		renderText("OK");
	}
	
	public static void removeCareItem(Integer patientId, Integer careItemId) {
		FollowUp.removeCareItem(careItemId);
//		followupPlan(patientId.intValue());
		renderText("OK");
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
//		treatmentPlan(patientId);
		renderText("OK");
	}
	public static void removeRadiationData(Integer patientId, Integer treatmentId) {
		Treatment.removeRadiationData(treatmentId);
//		treatmentPlan(patientId);
		renderText("OK");
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
		boolean isSaved = PatientDetailDAO.saveInfo(patientId.intValue(), info);
//		if(isSaved) { }
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

		List<DefaultTemplateMasterDTO> defaultTemplates = DefaultTemplateDAO.getPatientTemplate(diseaseId);
		Integer templateId = 0;
		if(defaultTemplates != null) {
			for (DefaultTemplateMasterDTO defaultTemplateMasterDTO : defaultTemplates) {
				if(templateId.intValue() == 0) {
					templateId = defaultTemplateMasterDTO.getId();
				}
				if(Disease.ENDOMETRIAL_CANCER_ID == diseaseId.intValue() && "Endometrial Cancer Medium Risk Template".equalsIgnoreCase(defaultTemplateMasterDTO.getTemplatename())) {
					templateId = defaultTemplateMasterDTO.getId();
				}
			}
		}
		if(templateId.intValue()!= 0) {
			Treatment.populatePatientFolloupplan(patientId, templateId);
		}
		renderTemplate("CarePatien/diagnosis.html", patientId, breastCancerId, userDetails, patientDetails, breastCancerInfo, ps,mutations,chromosomes);
	}

	
	public static void noteOperation(String operation,int id,int patientId,String title,String description,String noteSection) {
		
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
				noteDto.setNoteSection(noteSection);
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
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		DateFormat dfDisplay = new SimpleDateFormat("mm/dd/yyyy");
		Date startDt = null;
		Date endDt = null;

		if(dto != null) {
			if(dto.getCaremembername() == null) {
				dto.setCaremembername(UserDAO.getUserName(dto.getCaremember().getId()));
			}
			
			if(dto.getCaremembername() == null) {
				dto.setCaremembername(UserDAO.getUserName(dto.getCaremember().getId()));
			}
			try {
				startDt = df.parse(dto.getStartdate());
				dto.setStartdate(dfDisplay.format(startDt));
			} catch(Exception e) {
			}
			try {
				endDt = df.parse(dto.getEnddate());
				dto.setEnddate(dfDisplay.format(endDt));
			} catch(Exception e) {
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
				//patientMedicationDTO.setEnddate(new Date());
				patientMedicationDTO.setFrequency(frequency);
				patientMedicationDTO.setMedicine(medicineMasterDTO);
				
				patientMedicationDTO.setPatient(patientBy);
				patientMedicationDTO.setPatientid(patientId);
				patientMedicationDTO.setSpecialinstruction(otherdetails);
				
				Date startDt = null, endDt = null;
				
				try{
					startDt = new SimpleDateFormat("mm/dd/yyyy").parse(startDate);
					patientMedicationDTO.setStartdate(new SimpleDateFormat("yyyy-mm-dd").format(startDt));
				}catch(Exception e){
					patientMedicationDTO.setStartdate(startDate);
				}
				
				try{
					endDt = new SimpleDateFormat("mm/dd/yyyy").parse(endDate);
					patientMedicationDTO.setEnddate(new SimpleDateFormat("yyyy-mm-dd").format(endDt));
				}catch(Exception e){
					patientMedicationDTO.setEnddate(endDate);
				}
				
				BaseDAO.save(patientMedicationDTO);
			} else if("edit".equalsIgnoreCase(operation)) {
				
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

					Date startDt = null, endDt = null;
					try{
						startDt = new SimpleDateFormat("mm/dd/yyyy").parse(startDate);
						patientMedicationDTO.setStartdate(new SimpleDateFormat("yyyy-mm-dd").format(startDt));
					}catch(Exception e){
						patientMedicationDTO.setStartdate(startDate);
					}
					
					try{
						endDt = new SimpleDateFormat("mm/dd/yyyy").parse(endDate);
						patientMedicationDTO.setEnddate(new SimpleDateFormat("yyyy-mm-dd").format(endDt));
					}catch(Exception e){
						patientMedicationDTO.setEnddate(endDate);
					}

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
	
	public static void careteamSpecific(Integer careTeamId,Integer patientId) {
		CareTeamMasterDTO careteam = CareTeamDAO.getCareTeamByField("id", careTeamId);
		List<PatientCareTeamMemberDTO>  memberList = CareTeamDAO.getCareTeamMembersByPatient(patientId,careTeamId);
		UserDetailsDTO userDetails = null;
		ExpertDetailDTO expertDetail = null;
		ExpertBean expertBean =null;
		ExpertBean expertBeanHead = new ExpertBean();
		ArrayList<ExpertBean> otherExpert = new ArrayList<ExpertBean>(); 
		int iMemberCount = 0;
		boolean ismasterPrimary = false;
		EntityManager em = JPAUtil.getEntityManager();
		if(memberList != null && memberList.size()>0) {
			for (PatientCareTeamMemberDTO expertBean2 : memberList) {
				em.refresh(expertBean2);
				expertBean = new ExpertBean();
				userDetails = UserDAO.getDetailsById(expertBean2.getMemberid());
				expertDetail = ProfileDAO.getExpertByField("id", expertBean2.getMemberid());
				expertBean.setUserDetails(userDetails);
				expertBean.setExpertDetail(expertDetail);
				if(expertBean2.isPrimary()) {
					ismasterPrimary = true;
				}
				if(iMemberCount==0) {
					expertBeanHead = expertBean;
					iMemberCount++;
				} else {
					otherExpert.add(expertBean);
				}
			}
		}
		OtherCareMemberDTO otherPrimary = null;
		List<OtherCareMemberDTO> othersOld = CareTeamDAO.getOtherCareTeamMembersByPatient(patientId,careTeamId);
		List<OtherCareMemberDTO> others = null; 
		if(othersOld != null) {
			
			others = new ArrayList<OtherCareMemberDTO>();
			for (OtherCareMemberDTO otherCareMemberDTO : othersOld) {
				em.refresh(otherCareMemberDTO);
				if(!ismasterPrimary && otherCareMemberDTO.isPrimary()) {
					otherPrimary = 	otherCareMemberDTO;
					otherExpert.add(expertBeanHead);
					expertBeanHead = null;
				} else {
					others.add(otherCareMemberDTO);
				}
			}
		}
		render("CarePatien/careteamblock.html",careteam,otherExpert,expertBeanHead,otherPrimary,others);
	}
	
	public static void careteamOperation() {
		String operation = params.get("operation");
		int memberId = params.get("memberid",Integer.class);
		int patientId = params.get("patientid",Integer.class);
		int teamId  = params.get("teamid",Integer.class);
		boolean isTemplateRender = false;
		if("makeprimary".equalsIgnoreCase(operation)) {
			boolean other =  params.get("other",Boolean.class);
			 
			CareTeamDAO.makePatientPrimary(teamId,memberId,patientId,other);
//			careteamSpecific(teamId,patientId);	
			isTemplateRender = true;
		} else if("deletemember".equalsIgnoreCase(operation)) {
			boolean other =  params.get("other",Boolean.class);
			System.out.println("Other Flag: " + other);
			System.out.println("Other memberId: " + memberId);
			CareTeamDAO.deleteCareMember(teamId,memberId,patientId,other);
			isTemplateRender = true;
		} else if("addCareMember".equalsIgnoreCase(operation)) {
			boolean primary =  params.get("primary",Boolean.class);
			String memberName = params.get("name",String.class);
			String memberTitle = params.get("title",String.class);
			String memberTelephone = params.get("telephone",String.class);
			
			System.out.println(memberName);
			System.out.println(memberTitle);
			System.out.println(memberTelephone);
			if(memberId != 0) {
				CareTeamDAO.addCareMember(teamId,memberId,patientId,primary);
			} else {
				CareTeamDAO.addOtherCareMember(teamId,patientId,memberName,memberTitle,memberTelephone,primary);
			}
			System.out.println("Adding care member");
//			careteamSpecific(teamId,patientId);	
			isTemplateRender = true;
		} else if("addCareTeam".equalsIgnoreCase(operation)) {
			String type = params.get("type",String.class);
			String center = params.get("center",String.class);
			System.out.println(patientId);
			System.out.println(type);
			System.out.println(center);
			CareTeamMasterDTO careTeam = null;
			careTeam = CareTeamDAO.getCareTeamByTypeAndCenter(type, center);
			if(careTeam == null) {
				String address = params.get("address",String.class);
				String phone = params.get("phone",String.class);
				String city = params.get("city",String.class);
				String state = params.get("state",String.class);
				String zip = params.get("zip",String.class);
				//Create care team code
				System.out.println("There is no care team");
				careTeam  = CareTeamDAO.createMasterCareTeam(type,center,address,city,state,zip,phone);
			}
			UserDTO patient = UserDAO.getUserBasicByField("id",patientId);
			PatienCareTeamDTO pCareTeam = CareTeamDAO.addCareTeam(patient,careTeam);
			renderJSON(pCareTeam);
		} else if ("removeTeam".equalsIgnoreCase(operation)) {
			PatienCareTeamDTO careTeam = null;
			careTeam = CareTeamDAO.getCareTeamByPatientAndTeamid(patientId, teamId);
			if(careTeam != null) {
				careTeam.setDeleted(true);
				BaseDAO.update(careTeam);
			}
		}

		if(isTemplateRender) {
			CareTeamMasterDTO careteam = CareTeamDAO.getCareTeamByField("id", teamId);
			List<PatientCareTeamMemberDTO>  memberList = CareTeamDAO.getCareTeamMembersByPatient(new Integer(patientId),teamId);
			UserDetailsDTO userDetails = null;
			ExpertDetailDTO expertDetail = null;
			ExpertBean expertBean =null;
			ExpertBean expertBeanHead = new ExpertBean();
			ArrayList<ExpertBean> otherExpert = new ArrayList<ExpertBean>(); 
			int iMemberCount = 0;
			boolean ismasterPrimary = false;
			EntityManager em = JPAUtil.getEntityManager();
			if(memberList != null && memberList.size()>0) {
				for (PatientCareTeamMemberDTO expertBean2 : memberList) {
					em.refresh(expertBean2);
					expertBean = new ExpertBean();
					userDetails = UserDAO.getDetailsById(expertBean2.getMemberid());
					expertDetail = ProfileDAO.getExpertByField("id", expertBean2.getMemberid());
					expertBean.setUserDetails(userDetails);
					expertBean.setExpertDetail(expertDetail);
					if(expertBean2.isPrimary()) {
						ismasterPrimary = true;
					}
					if(iMemberCount==0) {
						expertBeanHead = expertBean;
						iMemberCount++;
					} else {
						otherExpert.add(expertBean);
					}
				}
			}
			OtherCareMemberDTO otherPrimary = null;
			List<OtherCareMemberDTO> othersOld = CareTeamDAO.getOtherCareTeamMembersByPatient(patientId,teamId);
			List<OtherCareMemberDTO> others = null; 
			
			if(othersOld != null) {
				others = new ArrayList<OtherCareMemberDTO>();
				for (OtherCareMemberDTO otherCareMemberDTO : othersOld) {
					em.refresh(otherCareMemberDTO);
					if(!ismasterPrimary && otherCareMemberDTO.isPrimary()) {
						otherPrimary = 	otherCareMemberDTO;
						otherExpert.add(expertBeanHead);
						expertBeanHead = null;
					} else {
						others.add(otherCareMemberDTO);
					}
				}
			}
			render("CarePatien/careteamblock.html",careteam,otherExpert,expertBeanHead,otherPrimary,others);
		}
	}

	public static void appointmentOperation() {
		String operation = params.get("operation");
		int patientId = 0;
		try {
			patientId = params.get("patientId", Integer.class);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int id = 0;
		try {
			id = params.get("id", Integer.class);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String purpose = params.get("purpose");
		String purposeText = params.get("purposeText"); 
		String treatmentProcessStep = params.get("treatmentProcessStep");
	    String time = params.get("time");
		String schDate = params.get("schDate"); 		
		boolean repeatWeeklyBtn = false;
		try {
			repeatWeeklyBtn = params.get("repeatWeeklyBtn", Boolean.class);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		 
		String startsOn = params.get("startsOn");		
		String endsOnCheck = params.get("endsOnCheck"); 
		int occurences = 0;
		try {
			occurences = params.get("occurences", Integer.class);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
		String endsOnDate = params.get("endsOnDate"); 
		String editOccurencesAction = params.get("editOccurencesAction"); 
		String center = params.get("center");
		int memberid = 0;
		try {
			memberid = params.get("memberid", Integer.class);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		String membername = params.get("membername"); 
		String address1 = params.get("address1");
		String city = params.get("city");
		String state = params.get("state");
		String zip = params.get("zip");
		String phone = params.get("phone");
		
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
				app.setPhone(phone);
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
				//Integer appIdInt = null;
				/*
				if (Integer.valueOf(purpose) > 0) {
					app.setPurpose(purpose);
					appIdInt = new Integer(purpose);
					app.setAppointmentid(AppointmentMasterDAO.getAppointmentByField("id", appIdInt));
				}
				*/
				app.setTreatementStep(treatmentProcessStep);
				app.setPurposeText(purposeText);
				
				app.setPatientid(patient);

				if(appGroup!= null)
					app.setAppointmentgroupid(new Integer(appGroup.getId()));

				BaseDAO.save(app);

				NotificationDAO.scheduleAppointmentEmails(app, "add");
				
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
							app.setPhone(phone);
							/*
							if (Integer.valueOf(purpose) > 0) {
								app.setPurpose(purpose);
								app.setAppointmentid(AppointmentMasterDAO.getAppointmentByField("id", appIdInt));
							}
							*/
							app.setTreatementStep(treatmentProcessStep);
							app.setPurposeText(purposeText);
							app.setPatientid(patient);
							app.setAppointmentgroupid(appGroup.getId());
							BaseDAO.save(app);
							NotificationDAO.scheduleAppointmentEmails(app, "add");
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
							app.setPhone(phone);
							/*
							if (Integer.valueOf(purpose) > 0) {
								app.setPurpose(purpose);
								app.setAppointmentid(AppointmentMasterDAO.getAppointmentByField("id", appIdInt));
							}
							*/
							app.setTreatementStep(treatmentProcessStep);
							app.setPurposeText(purposeText);
							app.setPatientid(patient);
							app.setAppointmentgroupid(appGroup.getId());
							BaseDAO.save(app);
							NotificationDAO.scheduleAppointmentEmails(app, "add");
							
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
						while(appStartsOn.compareTo(appEndsOnDate) <= 0) {
							app = new AppointmentDTO();
							app.setAddedby(addedby);
							app.setAddedon(new Date());
							app.setAddressid(address);
							app.setAppointmentcenter(center);
							app.setAppointmentdate(appStartsOn);
							app.setAppointmenttime(time);
							app.setCaremember(caremember);
							app.setPhone(phone);
							app.setCareMemberName(membername);
							/*
							if (Integer.valueOf(purpose) > 0) {
								app.setPurpose(purpose);
								app.setAppointmentid(AppointmentMasterDAO.getAppointmentByField("id", appIdInt));
							}
							*/
							app.setTreatementStep(treatmentProcessStep);
							app.setPurposeText(purposeText);
							app.setPatientid(patient);
							app.setAppointmentgroupid(appGroup.getId());							
							
							BaseDAO.save(app);
							NotificationDAO.scheduleAppointmentEmails(app, "add");
							
							Calendar cal2  = Calendar.getInstance();
							cal2.setTime(appStartsOn);
							cal2.add(Calendar.DATE, 7);
							appStartsOn = cal2.getTime();
						}
					}
				}
			  
			} else if("edit".equalsIgnoreCase(operation)) {
				
				if(editOccurencesAction.equalsIgnoreCase("onlyThisEvent")){
					
					//Need to code this.
					Integer appId =  new Integer(id);
					AppointmentDTO app = AppointmentDAO.getAppointmentByField("id",appId);
					/*
					if (Integer.valueOf(purpose) > 0) {
						app.setPurpose(purpose);
						Integer appIdInt = new Integer(purpose);
						app.setAppointmentid(AppointmentMasterDAO.getAppointmentByField("id", appIdInt));
					} else {
						app.setPurpose(null);
						app.setAppointmentid(null);
					}
					*/
					app.setPurpose(null);
					app.setAppointmentid(null);
					
					app.setTreatementStep(treatmentProcessStep);
					app.setPurposeText(purposeText);
					app.setPhone(phone);
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
					// Notification
					NotificationDAO.scheduleAppointmentEmails(app, "edit");
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
					+"appointmenttime = :fp6, appointmentcenter = :fp8, caremember = :fp9, caremember_name = :fp10, phone = :fp11  where appointmentgroupid = :f0";
					if(!em.getTransaction().isActive())
						em.getTransaction().begin();	
					
					Query query = em.createQuery(hql);
					/*
					if (Integer.valueOf(purpose) > 0) {
						query.setParameter("fp1", purpose);
						Integer appIdInt = new Integer(purpose);
						query.setParameter("fp2", AppointmentMasterDAO.getAppointmentByField("id", appIdInt));
					} else {
						query.setParameter("fp1", null);
						query.setParameter("fp2", null);
					}
					*/
					query.setParameter("fp1", null);
					query.setParameter("fp2", null);
					
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
					query.setParameter("fp11", phone);
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
						// Notification
						NotificationDAO.scheduleAppointmentEmails(appointmentDTO, "edit");
					}
				}
			} else if("delete".equalsIgnoreCase(operation)) {
				//Need to code this.
				if(editOccurencesAction.equalsIgnoreCase("onlyThisEvent")){
					Integer appId =  new Integer(id);
					AppointmentDTO dto = AppointmentDAO.getAppointmentByField("id",appId);					
					dto.setDeleteflag(true);
					BaseDAO.update(dto);
					// Notification
					NotificationDAO.scheduleAppointmentEmails(dto, "remove");
				}else if(editOccurencesAction.equalsIgnoreCase("allEvents")){
					Integer appId =  new Integer(id);
					AppointmentDTO dto = AppointmentDAO.getAppointmentByField("id",appId);					
					int appointmentGroupId = dto.getAppointmentgroupid();					

					// Notification Start 
					List<AppointmentDTO> groupAppointments = AppointmentDAO.getAppointmentListByField("appointmentgroupid", appointmentGroupId);
					if (groupAppointments != null) {
						for (AppointmentDTO appointment: groupAppointments) {
							NotificationDAO.scheduleAppointmentEmails(appointment, "remove");
						}
					}
					// Notification End
					
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
		/*System.out.println("patientId: "+ patientId);
		System.out.println("diseaseId: "+ diseaseId);*/

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
		Treatment.populatePatientFolloupplan(patientId, diseaseId);
/*		Treatment.addDefaultNotes(patientId, diseaseId);
*/		/*if(defaults != null && !defaults.isEmpty()) {
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
		}*/
//		followupPlan(patientId.intValue());
		renderText("OK");
	}
}
