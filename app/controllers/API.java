package controllers;

import java.util.List;

import com.google.gson.Gson;

import models.ContactTypeDTO;
import models.SecurityQuestionDTO;
import models.UserDetailsDTO;
import models.UserTypeDTO;
import nav.dao.ContactTypeDAO;
import nav.dao.SecurityQuestionDAO;
import nav.dao.UserDAO;
import nav.dao.UserTypeDAO;
import nav.dto.UserBean;
import play.mvc.Controller;
import util.CommonUtil;

public class API extends Controller {

	public static void contactTypeList() {
		List<ContactTypeDTO> contactTypes =  ContactTypeDAO.getContactTypeList();
		renderJSON(contactTypes);
	}
	
	public static void userTypeList() {
		List<UserTypeDTO> userTypelist = UserTypeDAO.getUserTypeList();
		renderJSON(userTypelist);
	}
	
	public static void securityQuestions() {
		List<SecurityQuestionDTO> securityQuestionList =  SecurityQuestionDAO.getSecurityQuestions();
		renderJSON(securityQuestionList);
	}
	
	public static void getUser(String userId) {
		System.out.println("userId : " + userId);
		UserBean userBean =  UserDAO.getByUserId(userId);
		renderJSON(userBean);
	}
	
	public static void getUserDetails(String userId) {
		System.out.println("userId : " + userId);
		UserDetailsDTO userBean =  UserDAO.getDetailsById(userId);
		Gson gson = new Gson();
		renderJSON(gson.toJson(userBean));
	}
}
