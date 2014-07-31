package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.AddressDTO;
import models.CareTeamMemberDTO;
import models.DesignationMasterDTO;
import models.ExpertDetailDTO;
import models.NoteDTO;
import models.PatienCareTeamDTO;
import models.PatientDetailDTO;
import models.PatientDistressDTO;
import models.UserCertificateDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import models.UserEducationDTO;
import models.UserExpertiesDTO;
import models.UserTypeDTO;
import nav.dao.AddressDAO;
import nav.dao.BaseDAO;
import nav.dao.CareTeamDAO;
import nav.dao.DesignationMasterDAO;
import nav.dao.DistressDAO;
import nav.dao.ExpertDetailDAO;
import nav.dao.NotesDAO;
import nav.dao.ProfileDAO;
import nav.dao.UserDAO;
import nav.dao.UserTypeDAO;
import nav.dto.DistressBean;
import nav.dto.PatientBean;
import nav.dto.UserBean;

import org.apache.commons.io.IOUtils;

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
				
				DistressBean distress = DistressDAO.getLastDistress(patienCareTeamDTO.getPatien());
				if(distress !=null) {
					patient.setDistress(distress);
				}
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
		ExpertDetailDTO expertDetail = ProfileDAO.getExpertByField("id", user.getId());
		
		UserDetailsDTO patientDto = UserDAO.getDetailsById(patientId);
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", patientId);
		
		DistressBean distress = DistressDAO.getLastDistress(patientDto.getUser());
		
		List<NoteDTO> noteList = NotesDAO.getPatientNotesList(patientId);
		
        render(user,expertDetail,patientId,patientDto,patientOtherDetails,distress,noteList);
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
		render(user,userDto,expertDetail);
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
					OutputStream out = new FileOutputStream(new File("/opt/sayli/navigator/public/upload/"+photo.getName()));
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
						OutputStream out = new FileOutputStream(new File("/opt/sayli/navigator/public/upload/"+photo.getName()));
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
	
	public static void removeEducation(int id) {
//		url: "/care/removeEducation",
//		data: {"id":eduId,"data":"experties","op":"remove"},
	}
}