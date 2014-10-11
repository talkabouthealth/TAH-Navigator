package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.*;
import nav.dao.*;
import nav.dto.*;
import play.mvc.Controller;
import play.mvc.With;
import util.*;

@Check({"user","user"})
@With( { Secure.class } )
public class Patient extends Controller {
	public static void index() {
		UserBean user = CommonUtil.loadCachedUser(session);
		//UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		//PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		System.out.println("Session ID: " + session.getId());
		
		Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(user.getId());
		UserDetailsDTO userDto = (UserDetailsDTO) patientInfo.get("userDetails");
		PatientDetailDTO patientOtherDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
		BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
		int breastCancerId = Disease.BREAST_CANCER_ID;
		
		//Load appointment
		Date curreDate = new Date();
		AppointmentDTO apt = AppointmentDAO.getLatestAppointment("patientid.id", user.getId(), curreDate, "upcomming");
		List<AppointmentCheckListMasterDTO> checlist = null;
		if(apt != null) {
			if (apt.getCaremember() != null) {
				UserDetailsDTO userDetails = UserDAO.getDetailsById(apt.getCaremember().getId());
				apt.setExpertMobile(userDetails.getMobile());
				//Load the appointment check list
				if (apt.getAppointmentid() != null) {
					Integer intAppId = new Integer(apt.getAppointmentid().getId());
					checlist = AppointmentCheckListMasterDAO.getAppointmentCheckList("appointmentid.id", intAppId);
				} else {
					Integer intAppId = new Integer(1);
					checlist = AppointmentCheckListMasterDAO.getAppointmentCheckList("appointmentid.id", intAppId);
				}
			}
		}

		//Load care team
		List<PatienCareTeamDTO> careTeams = CareTeamDAO.getPatienCareTeamByField("patienid", user.getId());
		UserDetailsDTO userDetails = null;
		ExpertDetailDTO expertDetail = null;
		ExpertBean expertBean =null;
		ArrayList<ExpertBean> careExpert = new ArrayList<ExpertBean>(); 
		int maxUsers = 0;
		for (PatienCareTeamDTO patienCareTeamDTO : careTeams) {
			List<CareTeamMemberDTO>  memberList = CareTeamDAO.getCareTeamMembersByField("careteamid", patienCareTeamDTO.getCareteamid());
			if(memberList != null && memberList.size()>0) {
				for (CareTeamMemberDTO expertBean2 : memberList) {
					maxUsers++;
					if(maxUsers<5) {
						expertBean = new ExpertBean();
						userDetails = UserDAO.getDetailsById(expertBean2.getMemberid());
						expertDetail = ProfileDAO.getExpertByField("id", expertBean2.getMemberid());
						expertBean.setUserDetails(userDetails);
						expertBean.setExpertDetail(expertDetail);
						careExpert.add(expertBean);
					}
				}
			}
		}
		ApplicationSettingsDTO accesstoallpages = ApplicationSettingDAO.getDetailsByField("propertyname", "accesstoallpages");
		if(!user.isVerifiedFlag() && accesstoallpages != null) {
			user.setVerifiedFlag(Boolean.parseBoolean(accesstoallpages.getPropertyvalue()));
		}
        render(user,userDto,patientOtherDetails, breastCancerId, breastCancerInfo,apt,careExpert,maxUsers,checlist,accesstoallpages);
    }

	public static void appointment() {
		UserBean user = CommonUtil.loadCachedUser(session);
		
		ApplicationSettingsDTO accesstoallpages = ApplicationSettingDAO.getDetailsByField("propertyname", "accesstoallpages");
		if(!user.isVerifiedFlag() && accesstoallpages != null) {
			user.setVerifiedFlag(Boolean.parseBoolean(accesstoallpages.getPropertyvalue()));
		}
		
		if(user.isVerifiedFlag()) {
			//UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
			//PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
			
			Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(user.getId());
			UserDetailsDTO userDto = (UserDetailsDTO) patientInfo.get("userDetails");
			PatientDetailDTO patientOtherDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
			BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
			int breastCancerId = Disease.BREAST_CANCER_ID;
			
			List<AppointmentDTO> list = new ArrayList<AppointmentDTO>();
			UserDetailsDTO userDetails = null;
			Date curreDate = new Date();
			List<AppointmentDTO> listOther = AppointmentDAO.getAppointmentListByField("patientid.id", userDto.getId(), curreDate, "upcomming" );
			if(listOther != null) {
				for (AppointmentDTO appointmentDTO : listOther) {
					if (appointmentDTO.getCaremember() != null) {
						userDetails = UserDAO.getDetailsById(appointmentDTO.getCaremember().getId());
						appointmentDTO.setExpertMobile(userDetails.getMobile());
					}
					list.add(appointmentDTO);
				}
			} else {
				list = null;
			}
	
			List<AppointmentDTO> expListOther = AppointmentDAO.getAppointmentListByField("patientid.id" , userDto.getId(), curreDate, "past" );
			List<AppointmentDTO> listOld = new ArrayList<AppointmentDTO>();
			if(expListOther != null) {
				for (AppointmentDTO appointmentDTO : expListOther) {
					if (appointmentDTO.getCaremember() != null) {
						userDetails = UserDAO.getDetailsById(appointmentDTO.getCaremember().getId());
						appointmentDTO.setExpertMobile(userDetails.getMobile());
					}
					listOld.add(appointmentDTO);
				}
			} else {
				listOld = null;
			}
			
	        render(user,userDto,patientOtherDetails,list,listOld, breastCancerId, breastCancerInfo);
		} else {
			index();
		}
    }

	public static void careteam() {
		System.out.println(session.getId());
		UserBean user = CommonUtil.loadCachedUser(session);
		ApplicationSettingsDTO accesstoallpages = ApplicationSettingDAO.getDetailsByField("propertyname", "accesstoallpages");
		if(!user.isVerifiedFlag() && accesstoallpages != null) {
			user.setVerifiedFlag(Boolean.parseBoolean(accesstoallpages.getPropertyvalue()));
		}
		
		if(user.isVerifiedFlag()) {
			//UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
			//PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
			
			Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(user.getId());
			UserDetailsDTO userDto = (UserDetailsDTO) patientInfo.get("userDetails");
			PatientDetailDTO patientOtherDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
			BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
			int breastCancerId = Disease.BREAST_CANCER_ID;
			
			List<PatienCareTeamDTO> careTeams = CareTeamDAO.getPatienCareTeamByField("patienid", user.getId());
	        render(user,userDto,patientOtherDetails,careTeams, breastCancerId, breastCancerInfo);
		} else {
			index();
		}
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
		render("Patient/careteamblock.html",careteam,otherExpert,expertBeanHead);
	}
	
	public static void careMember(int memberId,int teamId) {
		System.out.println("memberId : " + memberId);
		UserBean user = CommonUtil.loadCachedUser(session);
		//UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		//PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		
		Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(user.getId());
		UserDetailsDTO userDto = (UserDetailsDTO) patientInfo.get("userDetails");
		PatientDetailDTO patientOtherDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
		BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
		int breastCancerId = Disease.BREAST_CANCER_ID;

		UserDetailsDTO expertDetails = UserDAO.getDetailsById(memberId);
		System.out.println(session.getId());
		ExpertDetailDTO expdetails = ProfileDAO.getExpertByField("id", memberId);
		CareTeamMasterDTO careteam = ProfileDAO.getCareTeamByField("id", teamId);
		List<UserEducationDTO> education = ProfileDAO.getEducationByField("userid", memberId);
		List<UserExpertiesDTO> experties = ProfileDAO.getExpertiesByField("userid", memberId);
		List<UserCertificateDTO> certificats = ProfileDAO.getCertificateByField("userid", memberId);
		
		
        render(user,userDto,expertDetails,expdetails,careteam,education,experties,certificats,patientOtherDetails, breastCancerId, breastCancerInfo);
	}
	
	public static void careMemberNew(int memberId) {
		System.out.println("memberId : " + memberId);
		UserBean user = CommonUtil.loadCachedUser(session);
		//UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		//PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		
		Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(user.getId());
		UserDetailsDTO userDto = (UserDetailsDTO) patientInfo.get("userDetails");
		PatientDetailDTO patientOtherDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
		BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
		int breastCancerId = Disease.BREAST_CANCER_ID;

		UserDetailsDTO expertDetails = UserDAO.getDetailsById(memberId);
		System.out.println(session.getId());
		ExpertDetailDTO expdetails = ProfileDAO.getExpertByField("id", memberId);
		CareTeamMasterDTO careteam = null;//ProfileDAO.getCareTeamByField("id", teamId);
		List<UserEducationDTO> education = ProfileDAO.getEducationByField("userid", memberId);
		List<UserExpertiesDTO> experties = ProfileDAO.getExpertiesByField("userid", memberId);
		List<UserCertificateDTO> certificats = ProfileDAO.getCertificateByField("userid", memberId);
		
		
        render("Patient/careMember.html",user,userDto,expertDetails,expdetails,careteam,education,experties,certificats,patientOtherDetails, breastCancerId, breastCancerInfo);
	}
	
	public static void diagnosis() {
		UserBean user = CommonUtil.loadCachedUser(session);
		/*
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		System.out.println(session.getId());
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		*/
		ApplicationSettingsDTO accesstoallpages = ApplicationSettingDAO.getDetailsByField("propertyname", "accesstoallpages");
		if(!user.isVerifiedFlag() && accesstoallpages != null) {
			user.setVerifiedFlag(Boolean.parseBoolean(accesstoallpages.getPropertyvalue()));
		}
		if(user.isVerifiedFlag()) {
			Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(user.getId());
			UserDetailsDTO userDto = (UserDetailsDTO) patientInfo.get("userDetails");
			PatientDetailDTO patientOtherDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
			BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
			int breastCancerId = Disease.BREAST_CANCER_ID;
			
	        render(user,breastCancerId, userDto,patientOtherDetails, breastCancerInfo);
		} else {
			index();
		}
    }
	
	public static void medication() {
		UserBean user = CommonUtil.loadCachedUser(session);
		//UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		//PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		ApplicationSettingsDTO accesstoallpages = ApplicationSettingDAO.getDetailsByField("propertyname", "accesstoallpages");
		if(!user.isVerifiedFlag() && accesstoallpages != null) {
			user.setVerifiedFlag(Boolean.parseBoolean(accesstoallpages.getPropertyvalue()));
		}
		if(user.isVerifiedFlag()) {
			Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(user.getId());
			UserDetailsDTO userDto = (UserDetailsDTO) patientInfo.get("userDetails");
			PatientDetailDTO patientOtherDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
			BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
			int breastCancerId = Disease.BREAST_CANCER_ID;
			
			System.out.println(session.getId());
			List<PatientMedicationDTO> medicationList = MedicationDAO.getMedicine("patientid", userDto.getId());
			List<PatientMedicationDTO> currentMedications = new ArrayList<PatientMedicationDTO>();
			List<PatientMedicationDTO> pastMedications = new ArrayList<PatientMedicationDTO>();
			Date today = new Date();
			for (PatientMedicationDTO mDto : medicationList) {
				if ((today.compareTo(mDto.getStartdate()) >= 0) && (today.compareTo(mDto.getEnddate()) <=0)) {
					currentMedications.add(mDto);
				}
				else {
					pastMedications.add(mDto);
				}
			}
			render(user,userDto,patientOtherDetails,medicationList, breastCancerId, breastCancerInfo, currentMedications, pastMedications);
		} else {
			index();
		}
    }

	public static void thrivercareplan() {
		UserBean user = CommonUtil.loadCachedUser(session);
		//UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		//PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		ApplicationSettingsDTO accesstoallpages = ApplicationSettingDAO.getDetailsByField("propertyname", "accesstoallpages");
		if(!user.isVerifiedFlag() && accesstoallpages != null) {
			user.setVerifiedFlag(Boolean.parseBoolean(accesstoallpages.getPropertyvalue()));
		}
		if(user.isVerifiedFlag()) {
			Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(user.getId());
			UserDetailsDTO userDto = (UserDetailsDTO) patientInfo.get("userDetails");
			PatientDetailDTO patientOtherDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
			BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
			int breastCancerId = Disease.BREAST_CANCER_ID;
			List<NoteDTO> noteList = NotesDAO.getPatientNotesList(String.valueOf(user.getId()));
			List<PatientConcernDTO> concerns = FollowUp.getPatientConcerns(user.getId());
			List<PatientGoalDTO> goals = FollowUp.getPatientGoals(user.getId());
			boolean noteLinkInactive = true;
			List<PatientFollowUpCareItemDTO> careItems = FollowUp.getPatientCareItems(user.getId());
			
			System.out.println(session.getId());
	        render(user,userDto,patientOtherDetails, breastCancerId, breastCancerInfo, noteList, concerns, goals, careItems, noteLinkInactive);
		} else {
			index();
		}
    }

	public static void treatmentplan() {
		UserBean user = CommonUtil.loadCachedUser(session);
		//UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		//PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		ApplicationSettingsDTO accesstoallpages = ApplicationSettingDAO.getDetailsByField("propertyname", "accesstoallpages");
		if(!user.isVerifiedFlag() && accesstoallpages != null) {
			user.setVerifiedFlag(Boolean.parseBoolean(accesstoallpages.getPropertyvalue()));
		}
		if(user.isVerifiedFlag()) {
			Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(user.getId());
			UserDetailsDTO userDto = (UserDetailsDTO) patientInfo.get("userDetails");
			PatientDetailDTO patientOtherDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
			BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
			int breastCancerId = Disease.BREAST_CANCER_ID;
			
			System.out.println(session.getId());
			Integer patientId = user.getId();
			List<PatientRadiationTreatmentDTO> radiationTreatments = Treatment.getPatientRadiationTreatments(patientId);
			List<PatientChemoTreatmentDTO> chemoTreatments = Treatment.getPatientChemoTreatments(patientId);
			List<PatientSurgeryInfoDTO> surgeryInfo = Treatment.getPatientSurgeryInfo(patientId);		
	        render(user,userDto,patientOtherDetails, patientId, radiationTreatments, chemoTreatments, surgeryInfo, breastCancerId, breastCancerInfo);
		} else {
			index();
		}
    }

	public static void setting() {
		UserBean user = CommonUtil.loadCachedUser(session);
		//UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		//PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(user.getId());
		UserDetailsDTO userDto = (UserDetailsDTO) patientInfo.get("userDetails");
		PatientDetailDTO patientOtherDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
		BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
		int breastCancerId = Disease.BREAST_CANCER_ID;
		System.out.println(session.getId());
		render(user,userDto,patientOtherDetails, breastCancerId, breastCancerInfo);
	}

	public static void password() {
//		params.flash();
		UserBean user = CommonUtil.loadCachedUser(session);
		//UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		//PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		
		Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(user.getId());
		UserDetailsDTO userDto = (UserDetailsDTO) patientInfo.get("userDetails");
		PatientDetailDTO patientOtherDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
		BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
		int breastCancerId = Disease.BREAST_CANCER_ID;
		
		System.out.println(session.getId());
		render(user,userDto,patientOtherDetails, breastCancerId, breastCancerInfo);
	}

	public static void profile() {
		UserBean user = CommonUtil.loadCachedUser(session);
		//UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		//PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		
		Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(user.getId());
		UserDetailsDTO userDto = (UserDetailsDTO) patientInfo.get("userDetails");
		PatientDetailDTO patientOtherDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
		BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
		int breastCancerId = Disease.BREAST_CANCER_ID;
		
		System.out.println(session.getId());
		List<ContactTypeDTO> contactTypes =  ContactTypeDAO.getContactTypeList();
		List<UserOtherEmailDTO> emaiList =  UserOtherEmailDAO.getAllByField("user.id", user.getId());
		render(user,userDto,patientOtherDetails,contactTypes,emaiList, breastCancerId, breastCancerInfo);
	}
	
	public static void editContact() {
		UserBean user = CommonUtil.loadCachedUser(session);
		//UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		//PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		
		Map<String, Object> patientInfo = PatientDetailDAO.getDiagnosis(user.getId());
		UserDetailsDTO userDto = (UserDetailsDTO) patientInfo.get("userDetails");
		PatientDetailDTO patientOtherDetails = (PatientDetailDTO) patientInfo.get("patientDetails");
		BreastCancerInfoDTO breastCancerInfo = (BreastCancerInfoDTO) patientInfo.get("breastCancerInfo");
		int breastCancerId = Disease.BREAST_CANCER_ID;
		
		System.out.println(session.getId());
		render(user,userDto,patientOtherDetails, breastCancerId, breastCancerInfo);
	}
}