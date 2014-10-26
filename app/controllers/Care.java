package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.AddressDTO;
import models.AppointmentDTO;
import models.AppointmentMasterDTO;
import models.BreastCancerInfoDTO;
import models.BreastCancerStageDTO;
import models.CareTeamMemberDTO;
import models.DesignationMasterDTO;
import models.DiseaseMasterDTO;
import models.ExpertDetailDTO;
import models.InvitedDTO;
import models.NoteDTO;
import models.PatienCareTeamDTO;
import models.PatientDetailDTO;
import models.UserCertificateDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import models.UserEducationDTO;
import models.UserExpertiesDTO;
import models.UserOtherEmailDTO;
import models.UserTypeDTO;
import nav.dao.AddressDAO;
import nav.dao.AppointmentDAO;
import nav.dao.AppointmentMasterDAO;
import nav.dao.BaseDAO;
import nav.dao.CareTeamDAO;
import nav.dao.DesignationMasterDAO;
import nav.dao.Disease;
import nav.dao.DistressDAO;
import nav.dao.ExpertDetailDAO;
import nav.dao.NotesDAO;
import nav.dao.PatientDetailDAO;
import nav.dao.ProfileDAO;
import nav.dao.UserDAO;
import nav.dao.UserOtherEmailDAO;
import nav.dao.UserTypeDAO;
import nav.dto.CareMember;
import nav.dto.DistressBean;
import nav.dto.PatientBean;
import nav.dto.SignUpMemberBean;
import nav.dto.UserBean;
import notifiers.Mail;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import play.mvc.Controller;
import play.mvc.With;
import util.CommonUtil;
import util.EmailUtil;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

@Check({"care","care"})
@With( { Secure.class } )
public class Care extends Controller {

	public static void index() {
		
		UserBean user = CommonUtil.loadCachedUser(session);
		ExpertDetailDTO expertDetail = ProfileDAO.getExpertByField("id", user.getId());
		CareTeamMemberDTO careTeam =  CareTeamDAO.getCareTeamMembersByMemberId(user.getId()); // memberid
//		System.out.println(careTeam.getCareteamid());
		List<PatienCareTeamDTO> patientList = null;
		if(careTeam != null) {
			patientList = CareTeamDAO.getPatienCareTeamByField("careteamid",careTeam.getCareteamid());
		}
		ArrayList<PatientBean> patients = null;
		if(patientList != null) {
			patients = new ArrayList<PatientBean>(); 
			PatientBean patient =null;
			UserDetailsDTO userDetails = null;
			PatientDetailDTO patientDetail = null;
			
			for (PatienCareTeamDTO patienCareTeamDTO : patientList) {
				patient = new PatientBean();
				userDetails = UserDAO.getDetailsById(patienCareTeamDTO.getPatienid());
				patient.setUserDetails(userDetails);
	
				patientDetail  = ProfileDAO.getPatientByField("id", patienCareTeamDTO.getPatienid());
				patient.setPatientOtherDetails(patientDetail);
				
				/* need to refactor - getDiagnosis method performing unnecessary queries for this call. This method is good for the MyDiagnosis page though - Murray */
				Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(patienCareTeamDTO.getPatienid());
				BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
//				int breastCancerId = Disease.BREAST_CANCER_ID;
				patient.setBreastCancerInfo(breastCancerInfo);
				
				DistressBean distress = DistressDAO.getLastDistress(patienCareTeamDTO.getPatien());
				if(distress !=null) {
					patient.setDistress(distress);
				}
				AppointmentDTO appointment=AppointmentDAO.getLastAppointment(patienCareTeamDTO.getPatien(),new Date());				
				AppointmentDTO nextAppointment = AppointmentDAO.nextAppointment(patienCareTeamDTO.getPatienid());
				if (nextAppointment != null) {
					patient.setNextAppointment(nextAppointment);
				}
				
				if(appointment !=null) {
					patient.setAppointmentInfo(appointment);
				}
				
				NoteDTO noteFor = NotesDAO.getLastNoteFor(patienCareTeamDTO.getPatien());
				if(noteFor!=null){
					patient.setNote(noteFor);
				}
				patients.add(patient);
			}
			// Descending Order of Last Distress check Date
			Collections.sort(patients, new Comparator<PatientBean>() {
				@Override
				public int compare(PatientBean o1, PatientBean o2) {
					Date o1Date = null;
					Date o2Date = null;
					if (o1.getDistress() != null) {
						o1Date = o1.getDistress().getDistressDate();
					}
					if (o2.getDistress() != null) {
						o2Date = o2.getDistress().getDistressDate();
					}
					if (o1Date != null && o2Date != null) {
						return o2Date.compareTo(o1Date);
					}
					else if (o2Date != null) {
						return 1;
					}
					else if (o1Date != null) {
						return -1;
					}				
					else {
						return 0;
					}				
				}
			});
		}
        render(user,expertDetail,patients);
    }
	
	public static void invite() {
		UserBean user = CommonUtil.loadCachedUser(session);
		System.out.println(session.getId());
		ExpertDetailDTO expertDetail = ProfileDAO.getExpertByField("id", user.getId());
		List<UserDTO> drList = UserDAO.getAll("5","");
        render(user,expertDetail,drList);
	}
	
	public static void sendInvitation(String email,String firstname,String lastname,String purposeText, String treatmentProcessStep, String time,String schDate,String center,int memberid, 
			String address1,String city,String state,String zip,String membername,String purpose) {
		 validation.clear();
		System.out.println("email: "+ email);
		System.out.println("firstname: "+ firstname);
		System.out.println("lastname: "+ lastname);
		System.out.println("purposeText: "+ purposeText);
		System.out.println("treatmentProcessStep: "+ treatmentProcessStep);
		System.out.println("time: "+ time);
		System.out.println("schDate: "+ schDate);
		System.out.println("center: "+ center);
		System.out.println("memberid: "+ memberid);
		
		System.out.println("address1: "+ address1);
		System.out.println("city: "+ city);
		System.out.println("state: "+ state);
		System.out.println("zip: "+ zip);
	   	 validateMember(email);
	   	 System.out.println(validation.hasErrors());
	   	 if (validation.hasErrors()) {
	            params.flash();
	            validation.keep();
	      
	            JsonObject obj = new JsonObject();
				obj.add("status", new JsonPrimitive("300"));
				renderJSON(obj);
	        } 
		
		try {
			AddressDTO address = new AddressDTO();
			address.setCity(city);
			address.setLine1(address1);
			address.setState(state);
			address.setZip(zip);

			BaseDAO.save(address);

			InvitedDTO app = new InvitedDTO();
			app.setEmail(email);
			app.setFirstname(firstname);
			app.setLastname(lastname);
			
			if (Integer.valueOf(purpose) > 0) {
				app.setPurpose(purpose);
				Integer appIdInt = new Integer(purpose);
				app.setAppointmentid(AppointmentMasterDAO.getAppointmentByField("id", appIdInt));
			} else {
				app.setTreatementStep(treatmentProcessStep);
			}

			app.setPurposeText(purposeText);

//			if(StringUtils.isNotBlank(treatmentProcessStep))
//				app.setTreatementStep(treatmentProcessStep);

			app.setAppointmenttime(time);

			Date appointmentDate = new SimpleDateFormat("MM/dd/yyyy").parse(schDate);
			app.setAppointmentdate(appointmentDate);

			app.setAppointmentcenter(center);

			if (memberid > 0) {
				UserDTO caremember = UserDAO.getUserBasicByField("id", memberid);
				app.setCaremember(caremember);
			}
			app.setCareMemberName(membername);

			UserBean user = CommonUtil.loadCachedUser(session);
			UserDTO addedby = UserDAO.getUserBasicByField("id",user.getId());
			app.setAddedby(addedby);

			app.setAddressid(address);

			app.setActivateOnSignup(false);
			app.setInvitationSent(false);

			app.setAddedon(new Date());

			BaseDAO.save(app);
   		 	String url = "http://"+request.host;
//   		 	Mail.invitation(firstname,lastname,email,app.getId(),url);

   		 	Map<String, Object> vars = new HashMap<String, Object>();
   		 	vars.put("username", firstname + " " + lastname);
   		 	vars.put("signupurl", url + "/createinvited/"+app.getId());
//   		 	EmailUtil.sendEmail(EmailUtil.MOFFITT_WELCOME,vars,"aawte.umesh@s5infotech.com");
   		 	EmailUtil.sendEmail(EmailUtil.MOFFITT_WELCOME,vars,email);

		} catch(Exception e) {
			e.printStackTrace();
			JsonObject obj = new JsonObject();
			obj.add("status", new JsonPrimitive("100"));
			renderJSON(obj);
		}

		JsonObject obj = new JsonObject();
		obj.add("status", new JsonPrimitive("200"));
		renderJSON(obj);
	}
    private static void validateMember(String member) {
    	if(member != null) {
    		System.out.println("Not null");
    		if (!validation.hasError("member.email")) {
    			System.out.println("Not null email");
    			System.out.println(member);
    			UserBean user = UserDAO.getByUserEmail(member);
    			if(user != null) {
    				validation.addError("member.email", "email.exists", "");
    			}
    		} else {
    			System.out.println("Not null email");
    		}
    	}
	}
    
	public static void report(String patientId) {
		UserBean user = CommonUtil.loadCachedUser(session);
		System.out.println(session.getId());
		ExpertDetailDTO expertDetail = ProfileDAO.getExpertByField("id", user.getId());
        render(user,expertDetail);
    }

	public static void patient(String patientId) {
		UserBean user = CommonUtil.loadCachedUser(session);
		ExpertDetailDTO expertDetail = ProfileDAO.getExpertByField("id", user.getId());
		
		UserDetailsDTO patientDto = UserDAO.getDetailsById(patientId);
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", patientId);
		
		DistressBean distress = DistressDAO.getLastDistress(patientDto.getUser());
		
		//DistressBean lastDistress = DistressDAO.getLastDistress(patientDto.getUser(),1);
		
		List<NoteDTO> noteList = NotesDAO.getPatientNotesList(patientId);
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
	
	public static void appointmentForm() {
		Map<String, Object> jsonData = new HashMap<String, Object>();
		List<CareMember> members = UserDAO.verifiedCareMembers();
		List<AppointmentMasterDTO> appList = AppointmentMasterDAO.getAllAppointments();
		Map<Integer, String> memberNames = new HashMap<Integer, String>();
		for(CareMember cm : members) {			
			StringBuilder name = new StringBuilder("");
			if (cm.getFirstName() != null) {
				name.append(cm.getFirstName());
			}
			/*
			if (cm.getLastName() != null) {
				if (name.length() > 0) {
					name.append(" " + cm.getLastName());
				}
				else {
					name.append(cm.getLastName());
				}
			}
			
			if (cm.getDesignation() != null) {
				if (name.length() > 0) {
					name.append(", " + cm.getDesignation());
				}
				else {
					name.append(cm.getDesignation());
				}
			}
			*/
			memberNames.put(cm.getId(), name.toString());
		}
		jsonData.put("members", memberNames);
		jsonData.put("purposes", appList);
		renderJSON(jsonData);
	}
	
	public static void setting() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		ExpertDetailDTO expertDetail = ProfileDAO.getExpertByField("id", user.getId());
		render(user,userDto,expertDetail);
	}
	
	public static void profile() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		List<DesignationMasterDTO> designations = DesignationMasterDAO.getUserTypeList();  
		ExpertDetailDTO expertDetail = ExpertDetailDAO.getDetailsByField("id", user.getId());
		render(user,userDto,expertDetail,designations);
	}

	public static void education() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		ExpertDetailDTO expertDetail = ExpertDetailDAO.getDetailsByField("id", user.getId());
		List<UserEducationDTO> education = ProfileDAO.getEducationByField("userid", user.getId());
		render(user,userDto,expertDetail,education);
	}
	
	public static void experties() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		ExpertDetailDTO expertDetail = ExpertDetailDAO.getDetailsByField("id", user.getId());
		List<UserExpertiesDTO> experties = ProfileDAO.getExpertiesByField("userid", user.getId());
		render(user,userDto,expertDetail,experties);
	}

	public static void certifications() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		ExpertDetailDTO expertDetail = ExpertDetailDAO.getDetailsByField("id", user.getId());
		List<UserCertificateDTO> experties = ProfileDAO.getCertificateByField("userid", user.getId());
		render(user,userDto,expertDetail,experties);
	}
	
	public static void password() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		ExpertDetailDTO expertDetail = ExpertDetailDAO.getDetailsByField("id", user.getId());
		render(user,userDto,expertDetail);
	}
	
	public static void contact() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		ExpertDetailDTO expertDetail = ExpertDetailDAO.getDetailsByField("id", user.getId());
		List<UserOtherEmailDTO> emailList =  UserOtherEmailDAO.getAllByField("user.id", user.getId());
		render(user,userDto,expertDetail, emailList);
	}

	public static void updateCareProfile(String userName,String firstName,String userType,String designation,String mobile,String street1,String street2,String city,String state,String country,String zip,String statement) { //String designation,String homephone,
//		params.flash();
    	UserBean user = CommonUtil.loadCachedUser(session);
    	UserDTO userDto = UserDAO.getUserBasicByField("id", user.getId());
    	userDto.setName(userName);
    	UserTypeDTO userTypedto = UserTypeDAO.getEntityById(userType);
    	userDto.setUserType(userTypedto.getAbbravation());
    	userDto.setUsertypeid(userTypedto);
    	

    	UserDAO.updateUserBasic(userDto);
    	if(userDto != null) {
    		UserDetailsDTO detailsDTO = UserDAO.getDetailsByField("id", user.getId());
        	detailsDTO.setEditdate(new Date());
        	detailsDTO.setEditedBy(userDto);
//        	detailsDTO.setHomePhone(homephone);
        	detailsDTO.setMobile(mobile);
        	detailsDTO.setFirstName(firstName);
        	
        	UserDAO.updateUserDetails(detailsDTO);

        	ExpertDetailDTO expdetails = ExpertDetailDAO.getDetailsByField("id", user.getId());
    		if(expdetails != null) {
    			AddressDTO addressDto = expdetails.getPracticeAddr();
    			boolean newAddress = false;
	        	if(addressDto ==null) {
	        		newAddress = true;
	        		addressDto = new AddressDTO();
	        	}
	        	addressDto.setLine1(street1);
	    		addressDto.setLine2(street2);
	    		addressDto.setCity(city);
	    		addressDto.setState(state);
	    		addressDto.setCountry(country);
	    		addressDto.setZip(zip);
	        	if(newAddress) {
	        		addressDto = AddressDAO.save(addressDto);
	        	} else {
	        		addressDto = AddressDAO.update(addressDto);
	        	}
//	        	expdetails.setDesignation(DesignationMasterDAO.getEntityById(designation));
	    		expdetails.setId(user.getId());
	    		expdetails.setHomeAddr(addressDto);
	    		expdetails.setPracticeAddr(addressDto);
	    		expdetails.setStatement(statement.trim());
	    		expdetails.setUser(userDto);
//	    		if("5".equalsIgnoreCase(userType)) { //DR
//					expdetails.setDesignation(DesignationMasterDAO.getEntityById("1"));	
//				} else { //RN
//					expdetails.setDesignation(DesignationMasterDAO.getEntityById("2"));
//				}
	    		DesignationMasterDTO dto = DesignationMasterDAO.getEntityById(designation);
	    		if(dto!=null)
	    			expdetails.setDesignation(dto);
	    		
	    		ExpertDetailDAO.update(expdetails);
    		} else {
    			System.out.println("S1");
    			expdetails = new ExpertDetailDTO();
    			AddressDTO addressDto =  new AddressDTO();
        		addressDto.setLine1(street1);
	    		addressDto.setLine2(street2);
	    		addressDto.setCity(city);
	    		addressDto.setState(state);
	    		addressDto.setCountry(country);
	    		addressDto.setZip(zip);
	    		addressDto = AddressDAO.save(addressDto);
	    		System.out.println("S2");
//	    		expdetails.setDesignation(DesignationMasterDAO.getEntityById(designation));
	    		expdetails.setId(user.getId());
	    		expdetails.setHomeAddr(addressDto);
	    		expdetails.setPracticeAddr(addressDto);
	    		expdetails.setStatement(statement);
	    		expdetails.setUser(userDto);
//	    		if("5".equalsIgnoreCase(userType)) { //DR
//					expdetails.setDesignation(DesignationMasterDAO.getEntityById("1"));	
//				} else { //RN
//					expdetails.setDesignation(DesignationMasterDAO.getEntityById("2"));
//				}
	    		DesignationMasterDTO dto = DesignationMasterDAO.getEntityById(designation);
	    		if(dto!=null)
	    			expdetails.setDesignation(dto);
        		ExpertDetailDAO.save(expdetails);
        		System.out.println("S3");
    		}
    	}
		renderText("Profile updated");
	}
	
	public static void saveEducation(String operation,String id,String degree,String institute,String year, File photo)  throws	FileNotFoundException, IOException{
		UserBean user = CommonUtil.loadCachedUser(session);
		operation = operation==null?"add":operation;
		try {
			if("add".equalsIgnoreCase(operation)) {
				UserEducationDTO educationDTO = new UserEducationDTO();
				if(photo!= null) {
					System.out.println("Form file is not null");
					educationDTO.setLogopath("/public/upload/"+photo.getName());
					InputStream in = new FileInputStream(photo);
					OutputStream out = new FileOutputStream(new File("/opt/navigator-public/TAH-Navigator/trunk/public/upload/"+photo.getName()));
					IOUtils.copy(in,out);
					out.close();
					in.close();
				} else {
					System.out.println("Form file is null");
					educationDTO.setLogopath("/public/images/blankseal.jpg");	
				}

				educationDTO.setDgree(degree);
				educationDTO.setSchool(institute);
				educationDTO.setUserid(user.getId());
				educationDTO.setYear(year);
				BaseDAO.save(educationDTO);
			} else if("edit".equalsIgnoreCase(operation)) {
				List<UserEducationDTO> education = ProfileDAO.getEducationByField("id", new Integer(id));
				if(education != null) {
					UserEducationDTO educationDTO = education.get(0);
					educationDTO.setDgree(degree);
					if(photo!= null) {
						System.out.println("Form file is not null");
						educationDTO.setLogopath("/public/upload/"+photo.getName());
						InputStream in = new FileInputStream(photo);
						OutputStream out = new FileOutputStream(new File("/opt/navigator-public/TAH-Navigator/trunk/public/upload/"+photo.getName()));
						IOUtils.copy(in,out);
						out.close();
						in.close();
//					} else {
//						System.out.println("Form file is null");
//						educationDTO.setLogopath("/public/images/blankseal.jpg");	
					}
					educationDTO.setSchool(institute);
					educationDTO.setUserid(user.getId());
					educationDTO.setYear(year);
					BaseDAO.update(educationDTO);
				}
			} else if("remove".equalsIgnoreCase(operation)) {
				List<UserEducationDTO> education = ProfileDAO.getEducationByField("id", new Integer(id));
				if(education != null) {
					UserEducationDTO cert = education.get(0);
					BaseDAO.remove(cert);
				}
			}
		} catch(Exception e) { 
			e.printStackTrace();
		}
		education();
	}
	
	public static void saveExperties(String operation,String id,String name,String description) {
		UserBean user = CommonUtil.loadCachedUser(session);
		operation = operation==null?"add":operation;
		try {
			if("add".equalsIgnoreCase(operation)) {
				UserExpertiesDTO educationDTO = new UserExpertiesDTO();
				educationDTO.setName(name);
				educationDTO.setDescription(description);
				educationDTO.setUserid(user.getId());
				BaseDAO.save(educationDTO);
			} else if("edit".equalsIgnoreCase(operation)) {
				List<UserExpertiesDTO> experties = ProfileDAO.getExpertiesByField("id",  new Integer(id));
				if(experties != null) {
					UserExpertiesDTO cert = experties.get(0);
					cert.setDescription(description);
					cert.setName(name);
					BaseDAO.update(cert);
				}
			} else if("remove".equalsIgnoreCase(operation)) {
				List<UserExpertiesDTO> experties = ProfileDAO.getExpertiesByField("id",  new Integer(id));
				if(experties != null) {
					UserExpertiesDTO cert = experties.get(0);
					BaseDAO.remove(cert);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		experties();
	}
	
	public static void saveCertifications(String operation,String id,String name,String description) {
		UserBean user = CommonUtil.loadCachedUser(session);
		operation = operation==null?"add":operation;
		try {
			if("add".equalsIgnoreCase(operation)) {
				UserCertificateDTO educationDTO = new UserCertificateDTO();
				educationDTO.setName(name);
				educationDTO.setDescription(description);
				educationDTO.setUserid(user.getId());
				BaseDAO.save(educationDTO);
			} else if("edit".equalsIgnoreCase(operation)) {
				List<UserCertificateDTO> experties = ProfileDAO.getCertificateByField("id", new Integer(id));
				if(experties != null) {
					UserCertificateDTO cert = experties.get(0);
					cert.setDescription(description);
					cert.setName(name);
					BaseDAO.update(cert);
				}
			} else if("remove".equalsIgnoreCase(operation)) {
				List<UserCertificateDTO> experties = ProfileDAO.getCertificateByField("id", new Integer(id));
				if(experties != null) {
					UserCertificateDTO cert = experties.get(0);
					BaseDAO.remove(cert);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		certifications();
	}
	
	public static void distressData(String patientId) {
			JsonObject json = DistressDAO.getLastDistressData(patientId);
			renderJSON(json.toString());
	}
	
	public static void removeEducation(int id) {
//		url: "/care/removeEducation",
//		data: {"id":eduId,"data":"experties","op":"remove"},
	}
}