package controllers;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import models.CareTeamMasterDTO;
import models.CareTeamMemberDTO;
import models.InvitedDTO;
import models.UserDTO;
import models.UserTypeDTO;
import nav.dao.CareTeamDAO;
import nav.dao.UserDAO;
import nav.dao.UserTypeDAO;
import nav.dto.UserBean;
import play.mvc.Controller;
import play.mvc.With;
import util.CommonUtil;
import util.JPAUtil;

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
		render();
	}
	
	public static void createCareMember(String email,String name,String password, boolean isActive) {
		UserDTO account = new UserDTO();
		account.setName(name);
		account.setPassword(CommonUtil.hashPassword(password));
		account.setActive(isActive);
		account.setUserType('c');

		InvitedDTO invitedDTO = new  InvitedDTO();
		invitedDTO.setName(name);
		invitedDTO.setPassword(password);
		invitedDTO.setActivateOnSignup(isActive);
		invitedDTO.setUserType('c');
		invitedDTO.setIsInvitationSent(false);
		invitedDTO.setTimestamp(new Date());

		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(account);
		em.persist(invitedDTO);
		em.getTransaction().commit();
		em.close();
		renderJSON("{\"ret\": \"true\"}");
	}
	
	public static void allMemberList() {
		List<UserTypeDTO> userTypelist = UserTypeDAO.getUserTypeList();
		UserBean user = CommonUtil.loadCachedUser(session);
		List<UserDTO> list = UserDAO.getAll("0",null);
		flash("uname", "");
		render(user,list,userTypelist);
	}
	public static void filterUsers(String userType,String uname) {
		System.out.println(userType);
		List<UserTypeDTO> userTypelist = UserTypeDAO.getUserTypeList();
		UserBean user = CommonUtil.loadCachedUser(session);
		List<UserDTO> list = UserDAO.getAll(userType,uname);
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
	
	public static void editUser(int userId) {
		UserBean user = CommonUtil.loadCachedUser(session);
		render(user);
	}
	
	public static void careTeam() {
		UserBean user = CommonUtil.loadCachedUser(session);
		List<CareTeamMasterDTO> list = CareTeamDAO.getAllCareTeam();
		render(user,list);
	}
	
	public static void editCareTeam(int careTeamId) {
		UserBean user = CommonUtil.loadCachedUser(session);
		CareTeamMasterDTO careTeam = CareTeamDAO.getCareTeamByField("id", careTeamId);
		List<CareTeamMemberDTO>  memberList = CareTeamDAO.getCareTeamMembersByField("careteamid", careTeamId);
		render(user,careTeam,memberList);
	}
	
	public static void editCareTeamOperation(String operation,int careTeamId,int memberid,String memberName) {
		operation = operation==null?"":operation;
		if("makeprimary".equals(operation)) {
			CareTeamDAO.makePrimary(careTeamId,memberid);
		} else if("addMember".equals(operation)) {
			UserDTO usr = UserDAO.getUserBasicByField("name", memberName);
			if(usr != null) {
				CareTeamMasterDTO careTeam = CareTeamDAO.getCareTeamByField("id", careTeamId);
				CareTeamDAO.addMember(careTeam,usr);
			}
		} else if ("removeMember".equals(operation)) {
			CareTeamDAO.removeMember(careTeamId,memberid);
		}
		editCareTeam(careTeamId);
	}
	
	public static void getExpertList() {
//		List<UserTypeDTO> userTypelist = UserTypeDAO.getUserTypeList();
//		UserBean user = CommonUtil.loadCachedUser(session);
		List<UserDTO> list = UserDAO.getAll("5","");
		list.addAll(UserDAO.getAll("4",""));
		JsonArray data = new JsonArray();
		JsonObject object;
		for (UserDTO userDTO : list) {
			object = new JsonObject();
			object.add("id", new JsonPrimitive(userDTO.getId()));
			object.add("name", new JsonPrimitive(userDTO.getName()));
			data.add(object);
		}		
		renderText(data.toString());
	}
}