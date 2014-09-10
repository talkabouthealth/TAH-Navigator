package controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import models.AddressDTO;
import models.AppointmentDTO;
import models.BreastCancerInfoDTO;
import models.CareTeamMasterDTO;
import models.CareTeamMemberDTO;
import models.ChemoScheduleDTO;
import models.DiseaseMasterDTO;
import models.ExpertDetailDTO;
import models.MedicationDTO;
import models.MedicineCatlogDTO;
import models.MedicineMasterDTO;
import models.NoteDTO;
import models.PatienCareTeamDTO;
import models.PatientChemoTreatmentDTO;
import models.PatientDetailDTO;
import models.PatientMedicationDTO;
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
import nav.dao.BaseDAO;
import nav.dao.CareTeamDAO;
import nav.dao.Disease;
import nav.dao.MedicationDAO;
import nav.dao.NotesDAO;
import nav.dao.PatientDetailDAO;
import nav.dao.ProfileDAO;
import nav.dao.Treatment;
import nav.dao.UserDAO;
import nav.dto.ExpertBean;
import nav.dto.UserBean;
import play.mvc.Controller;
import play.mvc.With;
import util.CommonUtil;
import util.ImageUtil;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

@Check({"care","care"})
@With( { Secure.class } )
public class CarePatien  extends Controller {

	public static void appointment(int patientId) {
		Integer idField = new Integer(patientId);
		//List<AppointmentDTO> list = AppointmentDAO.getAppointmentListByField("patientid.id",idField);
		Date curreDate = new Date();
		List<AppointmentDTO> list = AppointmentDAO.getAppointmentListByField("patientid.id", idField, curreDate, "upcomming" );
		List<AppointmentDTO> expList = AppointmentDAO.getAppointmentListByField("patientid.id" , idField, curreDate, "past" );
		render(patientId,list,expList);
	}

	public static void careteam(int patientId) {
		UserDetailsDTO userDto = UserDAO.getDetailsById(patientId);
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", userDto.getId());
		List<PatienCareTeamDTO> careTeams = CareTeamDAO.getPatienCareTeamByField("patienid", userDto.getId());
		
		render(patientId,userDto,patientOtherDetails,careTeams);
	}

	public static void medication(int patientId) {
		UserDetailsDTO userDto = UserDAO.getDetailsById(patientId);
		List<PatientMedicationDTO> medicationList = MedicationDAO.getMedicine("patientid", userDto.getId());
		render(patientId,medicationList);
	}

	public static void treatmentPlan(Integer patientId) {
		List<PatientRadiationTreatmentDTO> radiationTreatments = Treatment.getPatientRadiationTreatments(patientId);
		List<PatientChemoTreatmentDTO> chemoTreatments = Treatment.getPatientChemoTreatments(patientId);
		List<PatientSurgeryInfoDTO> surgeryInfo = Treatment.getPatientSurgeryInfo(patientId);
		render(patientId, radiationTreatments, chemoTreatments, surgeryInfo);
	}

	public static void followupPlan(int patientId) {
		List<NoteDTO> noteList = NotesDAO.getPatientNotesList(patientId+"");
		render(patientId,noteList);
	}
	public static void chemotherapyForm(Integer patientId, String formType) {
		Map<String, Object> jsonData = new HashMap<String, Object>();
		List<MedicationDTO> medications = Treatment.getAllMedications();
		List<ChemoScheduleDTO> chemoSchedules = Treatment.allChemoSchedules();
		List<SideEffectDTO> sideEffects = Treatment.allSideEffects();
		jsonData.put("medications", medications);
		jsonData.put("chemoSchedules", chemoSchedules);
		jsonData.put("sideEffects", sideEffects);
		renderJSON(jsonData);
	}
	public static void saveChemotherapyData(Integer patientId, Map<String, String> cttInfo, Map<Integer, Integer> sideEffect) {
		/*
		System.out.println("-------------------------------------");
		System.out.println("Patient ID: " + patientId.toString());
		for (String key: cttInfo.keySet()) {
			System.out.println(key + ": " + cttInfo.get(key));
		}
		if (sideEffect != null) {
			System.out.print("Side Effects: ");
			for (Integer key: sideEffect.keySet()) {
				System.out.print(sideEffect.get(key) + ", ");
			}
		}
		System.out.println("\n-------------------------------------");
		*/
		Treatment.saveChemoTreatment(patientId, cttInfo, sideEffect);
		treatmentPlan(patientId);
	}
	public static void surgeryForm(Integer patientId, String formType) {
		Map<String, Object> jsonData = new HashMap<String, Object>();
		List<SurgeryTypeDTO> surgeryTypes = Treatment.allSurgeryTypes();
		List<TreatmentRegionDTO> treatmentRegions = Treatment.allTreatementRegions();
		List<SideEffectDTO> sideEffects = Treatment.allSideEffects();
		jsonData.put("surgeryTypes", surgeryTypes);
		jsonData.put("treatmentRegions", treatmentRegions);
		jsonData.put("sideEffects", sideEffects);
		renderJSON(jsonData);
	}
	public static void saveSurgeryData(Integer patientId, Map<String, String> siInfo, Map<Integer, Integer> sideEffect) {
		/*
		System.out.println("-------------------------------------");
		System.out.println("Patient ID: " + patientId.toString());
		for (String key: siInfo.keySet()) {
			System.out.println(key + ": " + siInfo.get(key));
		}
		if (sideEffect != null) {
			System.out.print("Side Effects: ");
			for (Integer key: sideEffect.keySet()) {
				System.out.print(sideEffect.get(key) + ", ");
			}
		}
		System.out.println("\n-------------------------------------");
		*/
		Treatment.saveSurgeryInfo(patientId, siInfo, sideEffect);
		treatmentPlan(patientId);
	}
	public static void radiationForm(Integer patientId, String formType) {
		Map<String, Object> jsonData = new HashMap<String, Object>();
		List<RadiationTypeDTO> radiationTypes = Treatment.allRadiationTypes();
		List<RadiationScheduleDTO> radiationSchedules = Treatment.allRadiationSchedules();
		List<TreatmentRegionDTO> treatmentRegions = Treatment.allTreatementRegions();
		List<SideEffectDTO> sideEffects = Treatment.allSideEffects();
		jsonData.put("radiationTypes", radiationTypes);
		jsonData.put("radiationSchedules", radiationSchedules);
		jsonData.put("treatmentRegions", treatmentRegions);
		jsonData.put("sideEffects", sideEffects);
		renderJSON(jsonData);
	}
	public static void saveRadiationData(Integer patientId, Map<String, String> rtInfo, Map<Integer, Integer> sideEffect) {
		/*
		System.out.println("-------------------------------------");
		System.out.println("Patient ID: " + patientId.toString());
		for (String key: rtInfo.keySet()) {
			System.out.println(key + ": " + rtInfo.get(key));
		}
		if (sideEffect != null) {
			System.out.print("Side Effects: ");
			for (Integer key: sideEffect.keySet()) {
				System.out.print(sideEffect.get(key) + ", ");
			}
		}
		System.out.println("\n-------------------------------------");
		*/
		Treatment.saveRadiationTreatment(patientId, rtInfo, sideEffect);
		treatmentPlan(patientId);
	}
	public static void diagnosis(int patientId) {
		Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(patientId);
		UserDetailsDTO userDetails = (UserDetailsDTO) patientInfo.get("userDetails");
		PatientDetailDTO patientDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
		BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
		int breastCancerId = Disease.BREAST_CANCER_ID;
		render(patientId, breastCancerId, userDetails, patientDetails, breastCancerInfo);
	}
	
	public static void diagnosisJSON(int patientId) {
		Map<String, Object> jsonData = PatientDetailDAO.getDiagnosisJSON(patientId);
		renderJSON(jsonData);
	}
	
	public static void updateDiagnosis(int patientId, Integer diseaseId, Date dateOfDiagnosis, Date dob, String phone, String supportName, String supportNumber, Map<String, String> diseaseInfo) {
		/*
		System.out.println("-------------------------------------");
		System.out.println("Pateint ID: " + patientId);
		System.out.println("Disease ID: " + diseaseId);
		System.out.println("First Diagnosed:"  + new SimpleDateFormat("YYYY-MM-dd").format(dateOfDiagnosis));
		System.out.println("DOB: " + new SimpleDateFormat("YYYY-MM-dd").format(dob));
		System.out.println("Phone: " + phone);
		System.out.println("Support Name: " + supportName);
		System.out.println("Support Number: " + supportNumber);
		if (diseaseId != null && diseaseId == Disease.BREAST_CANCER_ID) {
			System.out.println("*************************************");
			for (String key : diseaseInfo.keySet()) {
				System.out.println(key + ": " + diseaseInfo.get(key));
			}
		}
		System.out.println("-------------------------------------");
		*/
		PatientDetailDAO.updateDiagnosis(patientId, diseaseId, dateOfDiagnosis, dob, phone, supportName, supportNumber, diseaseInfo);
		Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(patientId);
		UserDetailsDTO userDetails = (UserDetailsDTO) patientInfo.get("userDetails");
		PatientDetailDTO patientDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
		BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
		int breastCancerId = Disease.BREAST_CANCER_ID;
		renderTemplate("CarePatien/diagnosis.html", patientId, breastCancerId, userDetails, patientDetails, breastCancerInfo);
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
		PatientMedicationDTO  dto = MedicationDAO.getMedicineByField("id", idField);
		if(dto != null) {
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
	
	public static void medicationOperation(String operation,int id,int patientId,int catlogId,String genericname,String brandname,String frequency,int memberid,String startDate,String endDate, String otherdetails) {
		UserDTO patientBy = UserDAO.getUserBasicByField("id",patientId);
		
		UserDTO drUser = UserDAO.getUserBasicByField("id", memberid);
		operation = operation==null?"add":operation;
		System.out.println(startDate +" : "+endDate);
		//10/15/2014 : 10/15/2014
		

		try {
			Date startDt = new SimpleDateFormat("mm/dd/yyyy").parse(startDate);
			Date endDt = new SimpleDateFormat("mm/dd/yyyy").parse(endDate);
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
				patientMedicationDTO.setActive(true);
				patientMedicationDTO.setAdddate(new Date());
				patientMedicationDTO.setCaremember(drUser);
				patientMedicationDTO.setEnddate(new Date());
				patientMedicationDTO.setFrequency(frequency);
				patientMedicationDTO.setMedicine(medicineMasterDTO);
				patientMedicationDTO.setMethod("oral");
				patientMedicationDTO.setPatient(patientBy);
				patientMedicationDTO.setPatientid(patientId);
				patientMedicationDTO.setSpecialinstruction(otherdetails);
				
				patientMedicationDTO.setStartdate(startDt);
				patientMedicationDTO.setEnddate(endDt);
				
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
	
	public static void appointmentOperation(String operation,int patientId,int id,String purpose,String time,String schDate,String center,int memberid,String address1,String city,String state,String zip) {
		
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDTO addedby = UserDAO.getUserBasicByField("id",user.getId());
		
		System.out.println("operation : " + operation);
		System.out.println("patientId : " + patientId);
		System.out.println("id : " + id);
		System.out.println("purpose : " + purpose);
		System.out.println("Other : time: " + time + " : "+schDate+center+memberid+address1+city+state+zip);
		try {
			if("add".equalsIgnoreCase(operation)) {
				AddressDTO address = new AddressDTO();
				address.setCity(city);
				address.setLine1(address1);
				address.setState(state);
				address.setZip(zip);

				BaseDAO.save(address);
				UserDTO patient = UserDAO.getUserBasicByField("id", patientId);
				UserDTO caremember = UserDAO.getUserBasicByField("id", memberid);

				//08/19/2014
				//mm/dd/yyyy
				Date appointmentDate = new SimpleDateFormat("MM/dd/yyyy").parse(schDate);

				AppointmentDTO app = new AppointmentDTO();
				app.setAddedby(addedby);
				app.setAddedon(new Date());
				app.setAddressid(address);
				app.setAppointmentcenter(center);
				app.setAppointmentdate(appointmentDate);
				app.setAppointmenttime(time);
				app.setCaremember(caremember);
				app.setPurpose(purpose);
				app.setPatientid(patient);
				
				BaseDAO.save(app);
			} else if("edit".equalsIgnoreCase(operation)) {
				//Need to code this.
				Integer appId =  new Integer(id);
				AppointmentDTO app = AppointmentDAO.getAppointmentByField("id",appId);
				app.setPurpose(purpose);
				app.setAppointmenttime(time);
				Date appointmentDate = new SimpleDateFormat("MM/dd/yyyy").parse(schDate);
				app.setAppointmentdate(appointmentDate);
				app.setAppointmentcenter(center);
				UserDTO caremember = UserDAO.getUserBasicByField("id", memberid);
				app.setCaremember(caremember);
				
				AddressDTO address = app.getAddressid();
				address.setCity(city);
				address.setLine1(address1);
				address.setState(state);
				address.setZip(zip);

				BaseDAO.update(address);
				BaseDAO.update(app);

			} else if("delete".equalsIgnoreCase(operation)) {
				//Need to code this.
				Integer appId =  new Integer(id);
				AppointmentDTO dto = AppointmentDAO.getAppointmentByField("id",appId);
				dto.setDeleteflag(true);
				BaseDAO.update(dto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		JsonObject obj = new JsonObject();
		obj.add("status", new JsonPrimitive("200"));
		renderJSON(obj);
	}
}