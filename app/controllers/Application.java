package controllers;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.AppointmentDTO;
import models.ContactTypeDTO;
import models.InvitedDTO;
import models.PatientDistressDTO;
import models.SecurityQuestionDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import models.UserTypeDTO;
import nav.dao.BaseDAO;
import nav.dao.ContactTypeDAO;
import nav.dao.DistressDAO;
import nav.dao.InvitationDAO;
import nav.dao.LoginHistoryDAO;
import nav.dao.SecurityQuestionDAO;
import nav.dao.UserDAO;
import nav.dao.UserTypeDAO;
import nav.dto.SignUpMemberBean;
import nav.dto.UserBean;
import notifiers.Mail;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import play.data.validation.Valid;
import play.mvc.Before;
import play.mvc.Controller;
import util.CommonUtil;
import util.EmailUtil;
import util.TemplateExtensions;

public class Application extends Controller {

	@Before
	static void prepareParams() {
		//used for Tw/Fb sharing and SEO
        String currentURL = "http://"+request.host+request.path;
        renderArgs.put("currentURL", currentURL);        
	}

    public static void index() throws Throwable {
    	if (Security.isConnected()) {
    		UserBean user = CommonUtil.loadCachedUser(session);
    		if(user != null) {
	    		if(user.getUserType() == 'a') {
	    			Admin.index();
	    		} else if(user.getUserType() == 'c') {
	        		Care.index();
	        	} else if(user.getUserType() == 'p') {
	        		Patient.index();
	        	}
	    		System.out.println("Connected.........");
    		} else {
    			System.out.println("Logout.........");
    			Secure.logout();
    		}
    	}
    	System.out.println("Index");
        Secure.login();
    }
    public static void distressthermometer() {
    	session.put("requestPath", request.path);    	
    	Patient.index();
    }

    public static void home() throws Throwable {
    	if (Security.isConnected()) {
    		UserBean user = CommonUtil.loadCachedUser(session);
    		if(user.getUserType() == 'a') {
    			Admin.index();
    		} else if(user.getUserType() == 'c') {
        		Care.index();
        	} else if(user.getUserType() == 'p') {
        		Patient.index();
        	}
    		System.out.println("Connected.........");
    	}
    	 render("Application/index.html");
    }
    public static void forgot() {
    	render();
    }
    
    public static void forgotForm(String email) {
    	if(StringUtils.isNotBlank(email)) {
    		UserBean user = UserDAO.getByUserEmail(email);
    		validation.isTrue(user != null).message("email.nosuchemail");
        	if (validation.hasErrors()) {
                params.flash();
                validation.keep();
                forgot();
                return;
            } else {
            	UserDetailsDTO detailDto = UserDAO.getDetailsById(user.getId()+"");
	    	    String newpassword = CommonUtil.generateRandomPassword();
	    	    detailDto.getUser().setPassword(CommonUtil.hashPassword(newpassword));
	    	    UserDAO.updateUserDetails(detailDto);
//	    	    Mail.forgot(detailDto,newpassword);

	    	    Map<String, Object> vars = new HashMap<String, Object>();
	   		 	vars.put("newpassword", newpassword);
	   		 	EmailUtil.sendEmail(EmailUtil.MOFFITT_PASSWORDRECOVERY,vars,user.getEmail());

	    		flash.success("ok");
	    		forgot();
            }
    	} else {
    		validation.isTrue(true).message("email.nosuchemail");
    	}
    	render();
    }
    
    public static void create() {
    	List<SecurityQuestionDTO> securityQuestionList =  SecurityQuestionDAO.getSecurityQuestions();
    	List<UserTypeDTO> userTypelist = UserTypeDAO.getUserTypeList();
    	List<ContactTypeDTO> contactTypes =  ContactTypeDAO.getContactTypeList();
    	render(securityQuestionList,userTypelist,contactTypes);
    }
    
    public static void createOther() {
    	List<SecurityQuestionDTO> securityQuestionList =  SecurityQuestionDAO.getSecurityQuestions();
    	List<UserTypeDTO> userTypelist = UserTypeDAO.getUserTypeList();
    	List<ContactTypeDTO> contactTypes =  ContactTypeDAO.getContactTypeList();
    	render(securityQuestionList,userTypelist,contactTypes);
    }
    
    public static void register(@Valid SignUpMemberBean member) throws Throwable {
    	 validateMember(member);
    	 System.out.println(validation.hasErrors());
    	 if (validation.hasErrors()) {
             params.flash();
             validation.keep();
             System.out.println(validation.errors().size());
             System.out.println(validation.errors().get(0).message("username.empty"));
             create(); 
         } 
    	 if(UserDAO.parseAndSaveMember(member)) {
    		 UserBean user = UserDAO.getByUserEmail(member.getEmail());
    		 UserDetailsDTO detailDto = UserDAO.getDetailsById(user.getId()+"");
    		 //Mail.welcome(detailDto);
    		 //Mail for welcome user may needed in future.
    		 System.out.println("Application.java - User email: " + detailDto.getEmail());
    		 
    		
    		 LoginHistoryDAO.saveLogin(member.getEmail(),"local",false,session.getId());
    		 if(user.getUserType() == 'p') {
//    			 String userId = user.getId()+"";
//    			 String fromPage = "s";
    			 session.put("username", user.getEmail());
    			 CommonUtil.refreshCachedUser(session);
    			 session.put("usertype", "user");
    			 session.put("showdistress", "true");
    			 
    			
//    			Integer intId = new Integer(member.getInvitationId());
			    InvitedDTO invitationdto = InvitationDAO.getDetailsByEmail("email",member.getEmail());
    			 if(invitationdto != null) {
    					AppointmentDTO app = new AppointmentDTO();
    					app.setAddedby(invitationdto.getAddedby());
    					app.setAddedon(invitationdto.getAddedon());
    					app.setAddressid(invitationdto.getAddressid());
    					app.setAppointmentcenter(invitationdto.getAppointmentcenter());
    					app.setAppointmentdate(invitationdto.getAppointmentdate());
    					app.setAppointmenttime(invitationdto.getAppointmenttime());
    					
   						app.setPurposeText(invitationdto.getPurposeText());
   						app.setTreatementStep(invitationdto.getTreatementStep());
    					app.setPatientid(detailDto.getUser());

    					if(invitationdto.getPurpose() != null) {
    						app.setPurpose(invitationdto.getPurpose());	
    					}

    					if(invitationdto.getAppointmentid() != null) {
    						app.setAppointmentid(invitationdto.getAppointmentid());
    					}

    					if(invitationdto.getCaremember() != null) {
    						app.setCaremember(invitationdto.getCaremember());
    					}
    					app.setCareMemberName(invitationdto.getCareMemberName());

    					BaseDAO.save(app);

    					UserDTO usr = detailDto.getUser();
						usr.setIsverified(true);
						usr.setActive(true);
						BaseDAO.update(usr);

						invitationdto.setActivateOnSignup(true);
						BaseDAO.update(invitationdto);
    			 } else {
//    				 String url = "http://"+request.host;
//    	    		 Mail.activation(detailDto,url);
    			 }
    	   		 Map<String, Object> vars = new HashMap<String, Object>();
    	   		 vars.put("username", UserDAO.getUserName(new Integer(user.getId())));
    	   		 EmailUtil.sendEmail(EmailUtil.MOFFITT_THANKYOU_FOR_SIGNUP,vars,user.getEmail());

    			 Patient.index();
//    			 render("Application/distress.html",userId,fromPage);
    		 } else {
    			 Map<String, Object> vars = new HashMap<String, Object>();
    			 vars.put("username", UserDAO.getUserName(new Integer(user.getId())));
    	   		 EmailUtil.sendEmail(EmailUtil.MOFFITT_THANKYOU_FOR_SIGNUP,vars,user.getEmail());

//    			 String url = "http://"+request.host;
//	    		 Mail.activation(detailDto,url);
    			 Static.success();
    		 }
    	 }
    	 validation.clear();
    	 index();
    }
    
    public static void distress(String userId) {
    	if(StringUtils.isBlank(userId)) {
    		notFound();
    	} else {
    		render(userId);
    	}
    }
    public static void distressSave(String userId,String curDist,String [] distressType,String fromPage,String otherDetail,String daterecrded) {
    	
    	
    	System.out.println("userId : " + userId);
		System.out.println("dist : " + curDist);
		if(StringUtils.isBlank(otherDetail)) {
			otherDetail = "";
		}
//		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(userId);
		int distInt = Integer.parseInt(curDist);
		Date dtCreated = new Date();
		try {
			dtCreated = new SimpleDateFormat("M/d/yyyy h:m a").parse(daterecrded);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PatientDistressDTO dto = DistressDAO.savePatientDistress(distInt,userDto.getUser(),otherDetail,dtCreated);

		if(distressType != null) {
			for (String string : distressType) {
				System.out.println("string : " + string);
				int distresstypeid = Integer.parseInt(string);
				boolean distressvalue = true;
				DistressDAO.savePatientDistressDetails(distresstypeid,distressvalue,dto);
			}
		}
		if(StringUtils.isNotBlank(fromPage) && "s".equals(fromPage)) {
			Static.success();
		} else {
			Static.distRessThanks();
		}
    }
    
    private static void validateMember(SignUpMemberBean member) {
    	if(member != null) {
    		if (!validation.hasError("member.email")) {
    			System.out.println("Not null email");
    			System.out.println(member.getEmail());
    			UserBean user = UserDAO.getByUserEmail(member.getEmail());
    			if(user != null) {
    				validation.addError("member.email", "email.exists", "");
    			}
    		} else {
    			System.out.println("Not null email");
    		}
    	}

    	if(StringUtils.isNotBlank(member.getDob())) {
	    	DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
			try {
				df.parse(member.getDob());
			} catch(Exception e) {
				validation.addError("member.dob", "dob.wrong", "");
			}
    	}
	}
    
    public static void validateEmail(String email) {
    	Map<String, Object> jsonData = new HashMap<String, Object>();
    	if(email != null) {
   			UserBean user = UserDAO.getByUserEmail(email);
   			if(user != null) {
   				jsonData.put("emailused", true);
   			} else {
   				jsonData.put("emailused", false);
   			}
   		} else {
   			jsonData.put("emailused", false);
    	}
    	renderJSON(jsonData);
    }
    
    public static void verifyEmail(String verifyCode) throws Throwable {
    	System.out.println(verifyCode);
		notFoundIfNull(verifyCode);
		UserDetailsDTO user = UserDAO.getDetailsByVerificationCode(verifyCode);
		if(user != null) {
			System.out.println(" Found user: " + user.getVerificationcode());
			if (verifyCode.equals(user.getVerificationcode().toString())) {
				user.setVerificationcode(null);
				user.getUser().setActive(true);
				//user.getUser().setIsverified(true);
				UserDAO.updateUserDetails(user);
				//render();
				flash.success("secure.verified");
				Secure.login();
			} else {
				System.out.println(" Not Found user: ");
				Static.pageNotFound();
			}
		} else {
			System.out.println(" Not Found user he is null: ");
			Static.pageNotFound();
		}
	}

    public static void createinvited(String id) throws Throwable {
    	System.out.println("ID: " + id);
    	Integer intId = new Integer(id);
    	InvitedDTO invitationdto = InvitationDAO.getDetailsByField("id",intId);
    	
    	if (invitationdto == null) {
    		render("Application/createerror.html");
    	} else {
	    	String invitationId = id;
	    	String fname = invitationdto.getFirstname();
	    	String lname = invitationdto.getLastname();
	    	String email = invitationdto.getEmail();
	
//	    	List<SecurityQuestionDTO> securityQuestionList =  SecurityQuestionDAO.getSecurityQuestions();
//	    	List<UserTypeDTO> userTypelist = UserTypeDAO.getUserTypeList();
//	    	List<ContactTypeDTO> contactTypes =  ContactTypeDAO.getContactTypeList();
	    	
	    	render("Application/createinvited.html",fname,lname,email,invitationId);
    	}
    }
    
    public static void registerinvited(@Valid SignUpMemberBean member) throws Throwable {
//   	 validateMember(member);
   	 System.out.println(validation.hasErrors());
   	 
   	 
  	Integer intId = new Integer(member.getInvitationId());
  	InvitedDTO invitationdto = InvitationDAO.getDetailsByField("id",intId);
  	if(invitationdto!=null && member.getEmail().trim().equalsIgnoreCase(invitationdto.getEmail().trim())) {
  		UserDTO userDto = Security.authenticate(member.getEmail(), member.getPassword());
  	   	 if(userDto != null) {
  	   		String hashed = CommonUtil.hashPassword(member.getPassword());
  			if(userDto.getPassword().trim().equals(hashed.trim())) {
  				Secure.authenticate(member.getEmail(), member.getPassword(), true);
  			} else {
  				validation.addError("password", "secure.error.password", "");
  			}
  	   	 }
  	} else {
  		validation.addError("email", "secure.error.email", "");
  	}
  	if(member.getPassword().trim().length()<8) {
  		validation.addError("invited.create.passwrod", "invited.create.passwrod", "");
  	}
   	 if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            System.out.println(validation.errors().size());
            System.out.println(validation.errors().get(0).message("username.empty"));
            createinvited(member.getInvitationId());
     }

   	 if(UserDAO.parseAndSaveMember(member)) {
   		UserBean user = UserDAO.getByUserEmail(member.getEmail());
   		UserDetailsDTO detailDto = UserDAO.getDetailsById(user.getId()+"");
   		System.out.println("Application.java - User email: " + detailDto.getEmail());
   		LoginHistoryDAO.saveLogin(member.getEmail(),"local",false,session.getId());
		session.put("username", user.getEmail());
		CommonUtil.refreshCachedUser(session);
		session.put("usertype", "user");
		session.put("showdistress", "true");
//    	intId = new Integer(member.getInvitationId());
//    	invitationdto = InvitationDAO.getDetailsByField("id",intId);
		AppointmentDTO app = new AppointmentDTO();
		app.setAddedby(invitationdto.getAddedby());
		app.setAddedon(invitationdto.getAddedon());
		app.setAddressid(invitationdto.getAddressid());
		app.setAppointmentcenter(invitationdto.getAppointmentcenter());
		app.setAppointmentdate(invitationdto.getAppointmentdate());
		app.setAppointmenttime(invitationdto.getAppointmenttime());
		
			app.setPurposeText(invitationdto.getPurposeText());
			app.setTreatementStep(invitationdto.getTreatementStep());
		app.setPatientid(detailDto.getUser());

		if(invitationdto.getPurpose() != null) {
			app.setPurpose(invitationdto.getPurpose());	
		}

		if(invitationdto.getAppointmentid() != null) {
			app.setAppointmentid(invitationdto.getAppointmentid());
		}

		if(invitationdto.getCaremember() != null) {
			app.setCaremember(invitationdto.getCaremember());
		}
		app.setCareMemberName(invitationdto.getCareMemberName());

		BaseDAO.save(app);

		UserDTO usr = detailDto.getUser();
		usr.setIsverified(true);
		usr.setActive(true);
		BaseDAO.update(usr);

		invitationdto.setActivateOnSignup(true);
		BaseDAO.update(invitationdto);
				
   		Map<String, Object> vars = new HashMap<String, Object>();
   		vars.put("username", UserDAO.getUserName(new Integer(user.getId())));
   		EmailUtil.sendEmail(EmailUtil.MOFFITT_THANKYOU_FOR_SIGNUP,vars,user.getEmail());
		Patient.index();
   	 }
   }

    public static void userName(String userId) {
    	render("Navigator demo User");
    }
}