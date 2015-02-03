package controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import models.AddressDTO;
import models.ApplicationSettingsDTO;
import models.AppointmentMasterDTO;
import models.CareTeamMasterDTO;
import models.CareTeamMemberDTO;
import models.InvitedDTO;
import models.PatienCareTeamDTO;
import models.PatientCareTeamMemberDTO;
import models.PatientContactMethodDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import models.UserTypeDTO;
import nav.dao.AdminDAO;
import nav.dao.ApplicationSettingDAO;
import nav.dao.AppointmentMasterDAO;
import nav.dao.BaseDAO;
import nav.dao.CareTeamDAO;
import nav.dao.ProfileDAO;
import nav.dao.UserDAO;
import nav.dao.UserTypeDAO;
import nav.dto.CareMember;
import nav.dto.UserBean;
import play.Play;
import play.data.FileUpload;
import play.data.Upload;
import play.exceptions.UnexpectedException;
import play.mvc.Controller;
import play.mvc.With;
import util.CommonUtil;
import util.JPAUtil;
import util.TemplateExtensions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

@Check({"user","admin"})
@With( { Secure.class } )
public class Admin extends Controller {

	public static void index() {
		UserBean user = CommonUtil.loadCachedUser(session);
		System.out.println(session.getId());
        render(user);
    }
	
	public static void addCareMember() {
		UserBean user = CommonUtil.loadCachedUser(session);
		System.out.println(session.getId());
		render(user);
	}
	
	public static void appSettings() {
		UserBean user = CommonUtil.loadCachedUser(session);
		render(user);
	}

	public static void createCareMember(String email,String name,String password, boolean isActive) {
//		UserDTO account = new UserDTO();
//		account.setName(name);
//		account.setPassword(CommonUtil.hashPassword(password));
//		account.setActive(isActive);
//		account.setUserType('c');
//		InvitedDTO invitedDTO = new  InvitedDTO();
//		invitedDTO.setName(name);
//		invitedDTO.setPassword(password);
//		invitedDTO.setActivateOnSignup(isActive);
//		invitedDTO.setUserType('c');
//		invitedDTO.setIsInvitationSent(false);
//		invitedDTO.setTimestamp(new Date());
//		EntityManager em = JPAUtil.getEntityManager();
//		em.getTransaction().begin();
//		em.persist(account);
//		em.persist(invitedDTO);
//		em.getTransaction().commit();
//		em.close();
		renderJSON("{\"ret\": \"true\"}");
	}
	

	public static void allMemberList() {
		List<UserTypeDTO> userTypelist = UserTypeDAO.getUserTypeList();
		UserBean user = CommonUtil.loadCachedUser(session);
		List<UserDTO> list = UserDAO.getAllForAdmin("0",null);
		flash("uname", "");
		render(user,list,userTypelist);
	}
	
	public static void passwordForm(String userId,String name) {
		Map<String, Object> jsonData = new HashMap<String, Object>();
		Integer intId = new Integer(userId);
		UserDTO patientDto = UserDAO.getUserBasicByField("id",intId);
		jsonData.put("id", userId);
		Integer id = new Integer(userId);
		jsonData.put("name", TemplateExtensions.usreName(userId, id));
		jsonData.put("email", patientDto.getEmail());
		String newPassword = CommonUtil.generateRandomPassword();
		jsonData.put("password", newPassword);
		renderJSON(jsonData);
	}
	
	public static void savePassword(String id,String name,String email,String password) {
		System.out.println("id: " + id);
		System.out.println("name: " + name);
		System.out.println("email: " + email);
		System.out.println("password: " + password);

		Integer intId = new Integer(id);
		UserDTO bean = UserDAO.getUserBasicByField("id", intId);
		if(password != null && !password.equals("") && bean!=null && password.length()>8) {
			String hashPassword = CommonUtil.hashPassword(password);
			bean.setPassword(hashPassword);
			BaseDAO.update(bean);
		}
		allMemberList();
	}

	public static void filterUsers(String userType,String uname) {
		System.out.println(userType);
		List<UserTypeDTO> userTypelist = UserTypeDAO.getUserTypeList();
		UserBean user = CommonUtil.loadCachedUser(session);
		List<UserDTO> list = UserDAO.getAllForAdmin(userType,uname);
		flash("userType", userType);
		flash("uname", uname);
		render("Admin/allMemberList.html",user,list,userTypelist);
	}
	
	public static void adminAjaxOperation(String userId,String op,String flag) {
		System.out.println("userId : " + userId);
		System.out.println("flag : " + Boolean.parseBoolean(flag));
//		Mail mail = new  Mail("admin@talkabouthealth.com", "aawte.umesh@s5infotech.com", "Test Mail", "This is email");
//		Mail.welcome("Test Mail",  "aawte.umesh@s5infotech.com","admin@talkabouthealth.com");

		UserDAO.updateUserActivationFlag(userId,Boolean.parseBoolean(flag),op);
		renderJSON("{\"status\":\"Done\",\"messages\": \"Updated user.\"}");
	}
	
	public static void adminSettingsAjaxOperation(String op,String settingname, String flag,String type) {
		System.out.println("Operation : " + op);
		System.out.println("flag : " + flag);
		System.out.println("settingname : " + settingname);
		System.out.println("type : " + type);
		ApplicationSettingsDTO settingDto = ApplicationSettingDAO.getDetailsByField("propertyname", settingname);
		boolean isNew = false;
		if(settingDto == null) {
			settingDto = new ApplicationSettingsDTO();
			isNew = true;
		}
		settingDto.setPropertytype(type);
		settingDto.setPropertyvalue(flag);
		settingDto.setPropertyname(settingname);

		if(isNew) {
			BaseDAO.save(settingDto);
		} else {
			BaseDAO.update(settingDto);
		}
		renderJSON("{\"status\":\"Done\",\"messages\": \"Updated setting.\"}");
	}
	
	public static void editUser(int userId) {
		UserBean user = CommonUtil.loadCachedUser(session);
		render(user);
	}
	
	public static void careTeam() {
		UserBean user = CommonUtil.loadCachedUser(session);
		List<CareTeamMasterDTO> list = CareTeamDAO.getAllActiveCareTeam();
		render(user,list);
	}
	
	public static void createCareTeam(String operation,int id,String teamtype,String center,String address1,String city,String state,String zip,String phone,Upload logo) {
		if(logo != null)
			System.out.println(logo.getFileName());
		
		
		CareTeamMasterDTO teamMasterDTO = null;
		if("create".equalsIgnoreCase(operation)) {
			AddressDTO address = new AddressDTO();
			address.setCity(city);
			address.setLine1(center);
			address.setLine2(address1); //+ " " + address2
			address.setState(state);
			address.setZip(zip);
			address.setPhone(phone);
			BaseDAO.save(address);

			teamMasterDTO = new CareTeamMasterDTO();
			teamMasterDTO.setAddress(address);
			teamMasterDTO.setName(teamtype);
			teamMasterDTO.setActive(true);
			teamMasterDTO.setAdminteam(true);
			if(logo != null)
				teamMasterDTO.setLogo(logo.asBytes());
			BaseDAO.save(teamMasterDTO);
//			renderText("<tr><td><a href='/admin/editcareteam?careTeamId="+teamMasterDTO.getId()+"'>"+teamtype+"</a></td></tr>");	
		} else if("edit".equalsIgnoreCase(operation)) {
			teamMasterDTO = CareTeamDAO.getCareTeamByField("id", id);
			teamMasterDTO.setName(teamtype);
			if(logo != null)
				teamMasterDTO.setLogo(logo.asBytes());
			BaseDAO.update(teamMasterDTO);

			AddressDTO address = teamMasterDTO.getAddress();
			address.setCity(city);
			address.setLine1(center);
			address.setLine2(address1);
			address.setState(state);
			address.setZip(zip);
			address.setPhone(phone);
			BaseDAO.update(address);

		} else if("delete".equalsIgnoreCase(operation)) {
			teamMasterDTO = CareTeamDAO.getCareTeamByField("id", id);
			teamMasterDTO.setActive(false);
			BaseDAO.update(teamMasterDTO);
		}
		/*
		JsonObject object = new JsonObject();
		object.add("id", new JsonPrimitive(teamMasterDTO.getId()));
		object.add("name", new JsonPrimitive(teamMasterDTO.getName()));
		renderText(object.toString());
		*/
		careTeam();
	}
	
	public static void createInvitedAccounts() {
		int accounts  = AdminDAO.createInvitedAccounts();
		JsonObject object = new JsonObject();
		object.add("code", new JsonPrimitive(200));
		object.add("message", new JsonPrimitive("Done with "+accounts+" records"));
		renderJSON(object.toString());
	}
	
	public static void getCareTeam(Integer careTeamId) {
		CareTeamMasterDTO careTeam = CareTeamDAO.getCareTeamByField("id", careTeamId);
		careTeam.setLogoString("/image/showClinic?careTeamId="+careTeam.getId());
		renderJSON(careTeam);
	}
	
	public static void editCareTeam(int careTeamId) {
		UserBean user = CommonUtil.loadCachedUser(session);
		CareTeamMasterDTO careTeam = CareTeamDAO.getCareTeamByField("id", careTeamId);
		List<CareTeamMemberDTO>  memberList = CareTeamDAO.getMasterCareTeamMembersByField("careteamid", careTeamId);
		render(user,careTeam,memberList);
	}
	
	public static void editCareTeamOperation(String operation,int careTeamId,int memberid,String memberName) {
		operation = operation==null?"":operation;
		if("makeprimary".equals(operation)) {
			CareTeamDAO.makePrimary(careTeamId,memberid);
		} else if("addMember".equals(operation)) {
			UserDetailsDTO usr = UserDAO.getDetailsByField("firstName", memberName);
			if(usr != null) {
				CareTeamMasterDTO careTeam = CareTeamDAO.getCareTeamByField("id", careTeamId);
				CareTeamDAO.addMember(careTeam,usr.getUser());
			} else {
				System.out.println("No member found: " + memberName);
			}
		} else if ("removeMember".equals(operation)) {
			CareTeamDAO.removeMember(careTeamId,memberid);
		}
		editCareTeam(careTeamId);
	}

	public static void correctCareTeamData() {
		List<UserDTO> list = UserDAO.getAllForAdmin("1",null);
		int userupdated = 0;
		for (UserDTO userDTO : list) {
			System.out.println(userDTO.getEmail());
			List<PatienCareTeamDTO> careTeams = CareTeamDAO.getPatienCareTeamByField("patienid", new Integer(userDTO.getId()));
			for (PatienCareTeamDTO patienCareTeamDTO : careTeams) {
				List<PatientCareTeamMemberDTO>  memberList = CareTeamDAO.getCareTeamMembersByPatient(new Integer(userDTO.getId()),new Integer(patienCareTeamDTO.getCareteamid()));
				if(memberList == null ) {
					System.out.println("YES: " +userDTO.getEmail());
					CareTeamDAO.migrateCareTeam(userDTO.getId(),patienCareTeamDTO.getCareteamid());
					userupdated = userupdated + 1;
				} else if(memberList.size() == 0) {
					System.out.println("YES: " +userDTO.getEmail());
					CareTeamDAO.migrateCareTeam(userDTO.getId(),patienCareTeamDTO.getCareteamid());
					userupdated = userupdated + 1;
				} else {
					System.out.println("NO: " +userDTO.getEmail() + " : " + memberList.size() );
				}
			}
		}
		renderText(userupdated);
	}

	public static void getExpertList() {
//		List<UserTypeDTO> userTypelist = UserTypeDAO.getUserTypeList();
//		UserBean user = CommonUtil.loadCachedUser(session);
//		List<UserDTO> list = UserDAO.getAll("5","");
//		list.addAll(UserDAO.getAll("4",""));
		List<CareMember> list = UserDAO.verifiedCareMembers();
		JsonArray data = new JsonArray();
		JsonObject object;
		for (CareMember userDTO : list) {
			object = new JsonObject();
			object.add("id", new JsonPrimitive(userDTO.getId()));
			object.add("name", new JsonPrimitive(userDTO.getFirstName()));
			data.add(object);
		}		
		renderJSON(data);
	}
	
	/**
	 * Use for login as another user from admin. 
	 * @param userName
	 * @throws Throwable
	 */
	public static void loginAsAnotherUser(String userId,boolean invitedFlag) throws Throwable{
		if(userId != null && !userId.equals("")) {
			Integer intId = new Integer(userId);
			UserDTO bean = UserDAO.getUserBasicByField("id", intId);
			if(bean != null){
				session.clear();
		        response.removeCookie("rememberme");
		        if(!invitedFlag) {
		        	Security.authenticate(bean.getEmail(), bean.getPassword(),true);
		        }
				session.put("username", bean.getEmail());
				String url = flash.get("url");
		        System.out.println("URL: "+ url);
		        if(url == null) {
		        	String callbackURL = "";
		            url = callbackURL+Play.ctxPath + "/";
		        }
				if('c' == bean.getUserType()) {
		        	 session.put("usertype", "care");
		        	url = url + "care/index";
		        } else if('p' == bean.getUserType()) {
		       	 	session.put("usertype", "user");
		       	 url = url + "patient/index";
		       }
			   redirect(url);
			}
		}
	}
	
	public static void migrateData(String type) {
		int userUpdated =  0;
		if(StringUtils.isNotBlank(type)) {
			if(type.equalsIgnoreCase("contact")) {
				System.out.println("Contact: ");
				List<UserDTO> list = UserDAO.getAllForAdmin("1",null);
				System.out.println("Contact: "+list.size());
				for (UserDTO userDto : list) {
					UserDetailsDTO detailsDTO = UserDAO.getDetailsByField("id", userDto.getId());
					System.out.println("User: " + userDto.getEmail());
					List<PatientContactMethodDTO> curList = ProfileDAO.getPatientContactMethodsByField("userid",userDto.getId());
					boolean isList = false;
					if(curList == null) 
						 isList = true;
					else if(curList.isEmpty())
						 isList = true;
					if(isList && detailsDTO != null && detailsDTO.getContactMethod() != null) {
						System.out.println("User: " + userDto.getEmail() + " : " + detailsDTO.getContactMethod());
						try{
							PatientContactMethodDTO pmDto = new PatientContactMethodDTO();
							pmDto.setContactmethod(new Integer(detailsDTO.getContactMethod().getId()));
							pmDto.setUserid(userDto.getId());
							BaseDAO.save(pmDto);
							
							userUpdated++;
						}catch(Exception e){}
						
						
					}
				}
			}
		}
		JsonObject object = new JsonObject();
		object.add("code", new JsonPrimitive(200));
		object.add("message", new JsonPrimitive("Done with "+userUpdated+" records"));
		renderJSON(object.toString());
	}
}
