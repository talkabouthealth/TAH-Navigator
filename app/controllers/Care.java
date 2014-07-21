package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.AddressDTO;
import models.CareTeamMemberDTO;
import models.DesignationMasterDTO;
import models.ExpertDetailDTO;
import models.PatienCareTeamDTO;
import models.PatientDetailDTO;
import models.UserCertificateDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import models.UserEducationDTO;
import models.UserExpertiesDTO;
import nav.dao.AddressDAO;
import nav.dao.BaseDAO;
import nav.dao.CareTeamDAO;
import nav.dao.DesignationMasterDAO;
import nav.dao.ExpertDetailDAO;
import nav.dao.ProfileDAO;
import nav.dao.UserDAO;
import nav.dto.PatientBean;
import nav.dto.UserBean;
import play.data.Upload;
import play.mvc.Controller;
import play.mvc.With;
import util.CommonUtil;

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
				patients.add(patient);
			}
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
	
	public static void updateCareProfile(String designation,String mobile,String homephone,String street1,String street2,String city,String state,String country,String zip,String statement) {
//		params.flash();
    	UserBean user = CommonUtil.loadCachedUser(session);
    	UserDTO userDto = UserDAO.getUserBasicByField("id", user.getId());
    	if(userDto != null) {
    		UserDetailsDTO detailsDTO = UserDAO.getDetailsByField("id", user.getId());
        	detailsDTO.setEditdate(new Date());
        	detailsDTO.setEditedBy(userDto);
        	detailsDTO.setHomePhone(homephone);
        	detailsDTO.setMobile(mobile);
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
	        	expdetails.setDesignation(DesignationMasterDAO.getEntityById(designation));
	    		expdetails.setId(user.getId());
	    		expdetails.setHomeAddr(addressDto);
	    		expdetails.setPracticeAddr(addressDto);
	    		expdetails.setStatement(statement);
	    		expdetails.setUser(userDto);
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
	    		expdetails.setDesignation(DesignationMasterDAO.getEntityById(designation));
	    		expdetails.setId(user.getId());
	    		expdetails.setHomeAddr(addressDto);
	    		expdetails.setPracticeAddr(addressDto);
	    		expdetails.setStatement(statement);
	    		expdetails.setUser(userDto);
        		ExpertDetailDAO.save(expdetails);
        		System.out.println("S3");
    		}
    	}
		renderText("Profile updated");
	}
	
	public static void saveEducation(String degree,String institute,String year, Upload photo) {
		UserBean user = CommonUtil.loadCachedUser(session);
		try {
			UserEducationDTO educationDTO = new UserEducationDTO();
			educationDTO.setDgree(degree);
			educationDTO.setLogopath("/public/images/blankseal.jpg");
			educationDTO.setSchool(institute);
			educationDTO.setUserid(user.getId());
			educationDTO.setYear(year);
			BaseDAO.save(educationDTO);
		} catch(Exception e) { 
			e.printStackTrace();
		}
		education();
	}
	
	public static void saveExperties(String name,String description) {
		UserBean user = CommonUtil.loadCachedUser(session);
		try {
			UserExpertiesDTO educationDTO = new UserExpertiesDTO();
			educationDTO.setName(name);
			educationDTO.setDescription(description);
			educationDTO.setUserid(user.getId());
			BaseDAO.save(educationDTO);
		} catch(Exception e) {
			e.printStackTrace();
		}
		experties();
	}
	
	public static void saveCertifications(String name,String description) {
		UserBean user = CommonUtil.loadCachedUser(session);
		try {
			UserCertificateDTO educationDTO = new UserCertificateDTO();
			educationDTO.setName(name);
			educationDTO.setDescription(description);
			educationDTO.setUserid(user.getId());
			BaseDAO.save(educationDTO);
		} catch(Exception e) {
			e.printStackTrace();
		}
		certifications();
	}
	
	public static void removeEducation(int id) {
//		url: "/care/removeEducation",
//		data: {"id":eduId,"data":"experties","op":"remove"},
	}
}