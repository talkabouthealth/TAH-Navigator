package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import models.AddressDTO;
import models.PatientDetailDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import models.UserOtherEmailDTO;
import nav.dao.AddressDAO;
import nav.dao.AdminDAO;
import nav.dao.ContactTypeDAO;
import nav.dao.PatientDetailDAO;
import nav.dao.ProfileDAO;
import nav.dao.UserDAO;
import nav.dao.UserImageDAO;
import nav.dao.UserOtherEmailDAO;
import nav.dto.UserBean;
import play.data.FileUpload;
import play.data.parsing.DataParser;
import play.mvc.Controller;
import play.mvc.With;
import util.CommonUtil;
import util.XHRFileItem;

//@Check({"user","user"})
@With( { Secure.class } )
public class Profile extends Controller {

	/**
	 * Delete current or upload new image
	 * @param submitAction 'Remove current image' or 'Upload'
	 */
	public static void uploadImage() {
			UserBean user = CommonUtil.loadCachedUser(session);
		 	FileUpload qqfile = null;
		    DataParser parser = DataParser.parsers.get(request.contentType);
		    if (parser != null) {
		        // normal upload. I have to manually parse this because
		        // play kills the body input stream for XHR-requests when I put the file upload as a method
		        // argument to {@link #uploadFile)
		        parser.parse(request.body);
		        @SuppressWarnings({"unchecked"})
		        ArrayList<FileUpload> uploads = (ArrayList<FileUpload>) request.args.get("__UPLOADS");
		        for (FileUpload upload : uploads) {
		            if ("qqfile".equals(upload.getFieldName())) {
		                qqfile = upload;
		                break;
		            }
		        }
		    } else {
		        //  XHR upload
		        qqfile = new FileUpload(new XHRFileItem("qqfile"));
		    }
		    if (qqfile == null) {
		        badRequest();
		        return;
		    } else {
		    	//UserDTO user,byte[] image,String imagename, String fileExt
		    	UserDTO user1 =  UserDAO.getAccountByUserEmail(user.getEmail());
		    	UserImageDAO.updateUserImage(user1,qqfile.asBytes(),qqfile.getFileName(),qqfile.getContentType());
		    	System.out.println("File is : "+  qqfile.getFileName());
		    }
		    // and now do something with your Fileupload object here (e.g. write it to db or something else) 

		renderJSON("{\"success\": true}");
	}
	
	public static void updatePassword(String oldpassword, String password, String repassword) {
		validation.isTrue(password != null && password.equals(repassword)).message("password.different");
		if (validation.hasErrors()) {
			params.flash();
			flash.success("");
			validation.keep();
			renderText("Error");
        } else {
//        	params.flash();
        	UserBean user = CommonUtil.loadCachedUser(session);
        	oldpassword = CommonUtil.hashPassword(oldpassword);
        	System.out.println("Old : " + oldpassword);
        	UserDTO userDto = AdminDAO.getAdminAuth(user.getEmail(), oldpassword);
        	if(userDto != null && userDto.isActive()) {
        		userDto.setPassword(CommonUtil.hashPassword(password));
            	UserDAO.updateUserBasic(userDto);
        	} else {
        		renderText("Error: Please enter correct current password.");
        	}
  			renderText("Password updated!");
        }
	}
	
	public static void updateProfile(String contactMethod,String mobile,String homephone,String street1,String street2,String city,String state,String country,String zip) {

//		params.flash();
    	UserBean user = CommonUtil.loadCachedUser(session);
    	UserDTO userDto = UserDAO.getUserBasicByField("id", user.getId());
    	if(userDto != null) {
        	UserDetailsDTO detailsDTO = UserDAO.getDetailsByField("id", user.getId());
        	detailsDTO.setContactMethod(ContactTypeDAO.getEntityById(contactMethod));
        	detailsDTO.setEditdate(new Date());
        	detailsDTO.setEditedBy(userDto);
        	detailsDTO.setHomePhone(homephone);
        	detailsDTO.setMobile(mobile);
        	UserDAO.updateUserDetails(detailsDTO);

        	PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
        	if(patientOtherDetails != null) {
	        	AddressDTO addressDto = patientOtherDetails.getAddress();
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
	        		System.out.print("This is new address");
	        		addressDto = AddressDAO.save(addressDto);
	        		patientOtherDetails.setAddress(addressDto);
	        		PatientDetailDAO.update(patientOtherDetails);
	        	} else {
	        		System.out.print("This Old");
	        		addressDto = AddressDAO.update(addressDto);
	        	}
	        	patientOtherDetails.setUser(userDto);
	        	patientOtherDetails.setId(userDto.getId());
	        	patientOtherDetails.setAddress(addressDto);
        		PatientDetailDAO.update(patientOtherDetails);
        	} else {
        		AddressDTO addressDto =  new AddressDTO();
        		addressDto.setLine1(street1);
	    		addressDto.setLine2(street2);
	    		addressDto.setCity(city);
	    		addressDto.setState(state);
	    		addressDto.setCountry(country);
	    		addressDto.setZip(zip);
	    		addressDto = AddressDAO.save(addressDto);

        		patientOtherDetails = new PatientDetailDTO();
        		patientOtherDetails.setUser(userDto);
        		patientOtherDetails.setId(userDto.getId());
        		patientOtherDetails.setAddress(addressDto);
        		PatientDetailDAO.save(patientOtherDetails);
        	}
    	}
		renderText("Profile updated");
	}
	
	public static void updateAditionalEmail(String email,String op) {
		UserBean user = CommonUtil.loadCachedUser(session);
		Integer id = new Integer(user.getId());
    	UserDTO userDto = UserDAO.getUserBasicByField("id", id);
    	System.out.println("New Rec : " + email);
    	String message ="";
    	if(userDto != null) {
    		UserOtherEmailDTO emailDto = UserOtherEmailDAO.getDetailsByUserEmail(email,id);
    		UserDTO isExist = UserDAO.getUserBasicByField("email", email);
    		if("a".equals(op)) {
    			if(isExist != null )
    				message ="d";
    			else {
		    		if(emailDto != null) {
		    			if(emailDto.getUser().getId() != user.getId()) {
		    				message ="d";
		    			} else {
			    			emailDto.setActive(true);
			    			emailDto.setAddDate(new Date());
			    			UserOtherEmailDAO.update(emailDto);
			    			message ="d";
		    			}
		    		} else {
		    			emailDto = new UserOtherEmailDTO();
		    			emailDto.setUser(userDto);
		    			emailDto.setActive(true);
		    			emailDto.setPrimary(false);
		    			emailDto.setAddDate(new Date());
		    			emailDto.setEmail(email);
		    			emailDto.setVerificationcode(UUID.randomUUID());
		    			UserOtherEmailDAO.save(emailDto);
		    			message =email;
		    		}
    			}
    		} else if("r".equals(op)) {
    			if(emailDto != null) {
    				emailDto.setUser(userDto);
    				UserOtherEmailDAO.remove(emailDto);
    			}
    			message =email;
    		} else if("mp".equals(op)) {
    			//message = "This email is not verified. Please verify the email first";
//    			UserDTO isExist = UserDAO.getUserBasicByField("email", email);
    			if(isExist == null) {
	    			String prevEmail = userDto.getEmail();
	    			userDto.setEmail(email);
	    			UserDAO.updateUserBasic(userDto);
	    			session.put("username", email);
	    			CommonUtil.refreshCachedUser(session);
	    			emailDto.setEmail(prevEmail);
	    			UserOtherEmailDAO.update(emailDto);
	    			message = email;
    			} else {
    				message ="Already used email";	
    			}
    		}
    	}
		renderText(message);
	}
	
	public static void updateContact(int ssnLast4,String dob, String ec1name,String ec1number,String ec2name,String ec2number,
		String kinname,String kinnumber,String proxyname,String proxynumber) {
		 
		String message ="Contact information updated";
		UserBean user = CommonUtil.loadCachedUser(session);
		PatientDetailDTO patientOtherDetails = ProfileDAO.getPatientByField("id", user.getId());
		
		boolean isNew = false;
		if(patientOtherDetails == null) {
			isNew = true;
			patientOtherDetails = new PatientDetailDTO();
		}
		patientOtherDetails.setEc1name(ec1name);
		patientOtherDetails.setEc1number(ec1number);
		
		patientOtherDetails.setEc1name(ec1name);
		patientOtherDetails.setEc1number(ec1number);
		
		patientOtherDetails.setEc2name(ec2name);
		patientOtherDetails.setEc2number(ec2number);
		
		patientOtherDetails.setKinname(kinname);
		patientOtherDetails.setKinnumber(kinnumber);
		
		patientOtherDetails.setProxyname(proxyname);
		patientOtherDetails.setProxynumber(proxynumber);
		if(isNew) {
			UserDTO userDto = UserDAO.getUserBasicByField("id", user.getId());
			patientOtherDetails.setUser(userDto);
			patientOtherDetails.setId(userDto.getId());
			PatientDetailDAO.save(patientOtherDetails);
		} else {
			PatientDetailDAO.update(patientOtherDetails);
		}
		System.out.println("updateContact ssnLast4: " + ssnLast4);
		System.out.println("updateContact DOB: " + dob);
		
		UserDetailsDTO userdetailsDTO = UserDAO.getDetailsById(user.getId());
			
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		
		try {
	 
			Date date = formatter.parse(dob);
			userdetailsDTO.setDob(date);
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		userdetailsDTO.setSsnLast4(ssnLast4);
		UserDAO.updateUserDetails(userdetailsDTO);
						
		renderText(message);
	}
}