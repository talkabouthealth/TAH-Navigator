package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import nav.dao.CareTeamDAO;
import nav.dao.ProfileDAO;
import nav.dao.UserDAO;
import nav.dto.ExpertBean;
import nav.dto.PatientBean;
import nav.dto.UserBean;

import models.CareTeamMemberDTO;
import models.ExpertDetailDTO;
import models.PatienCareTeamDTO;
import models.PatientDetailDTO;
import models.UserDTO;
import models.UserDetailsDTO;

import com.ning.http.client.Response;

import play.mvc.Controller;
import play.mvc.With;
import util.CommonUtil;
import util.JPAUtil;

@Check({"care","care"})
@With( { Secure.class } )
public class Care extends Controller {

	public static void index() {
		
		UserBean user = CommonUtil.loadCachedUser(session);
		ExpertDetailDTO expertDetail = ProfileDAO.getExpertByField("id", user.getId());
		CareTeamMemberDTO careTeam =  CareTeamDAO.getCareTeamMembersByMemberId(user.getId()); // memberid
		System.out.println(careTeam.getCareteamid());
		
		List<PatienCareTeamDTO> patientList = CareTeamDAO.getPatienCareTeamByField("careteamid",careTeam.getCareteamid());
		ArrayList<PatientBean> patients = new ArrayList<PatientBean>(); 
		PatientBean patient =null;
		UserDetailsDTO userDetails = null;
		PatientDetailDTO patientDetail = null;
		
		for (PatienCareTeamDTO patienCareTeamDTO : patientList) {
			patient = new PatientBean();
			userDetails = UserDAO.getDetailsById(patienCareTeamDTO.getPatienid());
			patient.setUserDetails(userDetails);

			patientDetail  = ProfileDAO.getPatientByField("id", patienCareTeamDTO.getPatienid());
			patient.setPatientOtherDetails(patientDetail);
			patients.add(patient);
		}
		
        render(user,expertDetail,patients);
    }
	
	public static void report(String patientId) {
		UserBean user = CommonUtil.loadCachedUser(session);
		System.out.println(session.getId());
		ExpertDetailDTO expertDetail = ProfileDAO.getExpertByField("id", user.getId());
        render(user,expertDetail);
    }

	public static void patient(String patientId) {
		UserBean user = CommonUtil.loadCachedUser(session);
		System.out.println(session.getId());
		ExpertDetailDTO expertDetail = ProfileDAO.getExpertByField("id", user.getId());
        render(user,expertDetail);
    }
	
	public static void setting() {
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
//		System.out.println(session.getId());
		ExpertDetailDTO expertDetail = ProfileDAO.getExpertByField("id", user.getId());
		render(user,userDto,expertDetail);
	}
}