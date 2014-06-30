package controllers;

import java.util.ArrayList;
import java.util.List;

import models.CareTeamMasterDTO;
import models.CareTeamMemberDTO;
import models.ContactTypeDTO;
import models.ExpertDetailDTO;
import models.PatienCareTeamDTO;
import models.PatientDetailDTO;
import models.PatientMedicationDTO;
import models.UserCertificateDTO;
import models.UserDetailsDTO;
import models.UserEducationDTO;
import models.UserExpertiesDTO;
import nav.dao.CareTeamDAO;
import nav.dao.ContactTypeDAO;
import nav.dao.MedicationDAO;
import nav.dao.ProfileDAO;
import nav.dao.UserDAO;
import nav.dto.ExpertBean;
import nav.dto.UserBean;
import play.mvc.Controller;
import play.mvc.With;
import util.CommonUtil;

@Check({"user","user"})
@With( { Secure.class } )
public class Patient extends Controller {
	public static void index() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		System.out.println(session.getId());
        render(user,userDto,patientOtherDetails);
    }
	
	public static void appointment() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		System.out.println(session.getId());
        render(user,userDto,patientOtherDetails);
    }

	public static void careteam() {
		System.out.println(session.getId());
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		List<PatienCareTeamDTO> careTeams = CareTeamDAO.getPatienCareTeamByField("patienid", user.getId());
        render(user,userDto,patientOtherDetails,careTeams);
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
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());

		UserDetailsDTO expertDetails = UserDAO.getDetailsById(memberId);
		System.out.println(session.getId());
		ExpertDetailDTO expdetails = ProfileDAO.getExpertByField("id", memberId);
		CareTeamMasterDTO careteam = ProfileDAO.getCareTeamByField("id", teamId);
		List<UserEducationDTO> education = ProfileDAO.getEducationByField("userid", memberId);
		List<UserExpertiesDTO> experties = ProfileDAO.getExpertiesByField("userid", memberId);
		List<UserCertificateDTO> certificats = ProfileDAO.getCertificateByField("userid", memberId);
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		
        render(user,userDto,expertDetails,expdetails,careteam,education,experties,certificats,patientOtherDetails);
	}
	
	public static void diagnosis() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		System.out.println(session.getId());
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
        render(user,userDto,patientOtherDetails);
    }
	
	public static void medication() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		System.out.println(session.getId());
		List<PatientMedicationDTO> medicationList = MedicationDAO.getMedicine("patientid", userDto.getId());
		
		
		
        render(user,userDto,patientOtherDetails,medicationList);
    }

	public static void thrivercareplan() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		System.out.println(session.getId());
        render(user,userDto,patientOtherDetails);
    }
	
	public static void treatmentplan() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		System.out.println(session.getId());
        render(user,userDto,patientOtherDetails);
    }

	public static void setting() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		System.out.println(session.getId());
		render(user,userDto,patientOtherDetails);
	}
	
	public static void password() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		System.out.println(session.getId());
		render(user,userDto,patientOtherDetails);
	}
	
	
	public static void profile() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		System.out.println(session.getId());
		List<ContactTypeDTO> contactTypes =  ContactTypeDAO.getContactTypeList();
		render(user,userDto,patientOtherDetails,contactTypes);
	}
}