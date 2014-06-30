package controllers;

import java.util.ArrayList;

import models.UserDTO;
import nav.dao.AdminDAO;
import nav.dao.UserDAO;
import nav.dao.UserImageDAO;
import nav.dto.UserBean;
import play.data.FileUpload;
import play.data.parsing.DataParser;
import play.mvc.Controller;
import play.mvc.With;
import util.CommonUtil;
import util.XHRFileItem;

@Check({"user","user"})
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
        	params.flash();
        	UserBean user = CommonUtil.loadCachedUser(session);
        	oldpassword = CommonUtil.hashPassword(oldpassword);
        	System.out.println("Old : " + oldpassword);
        	UserDTO userDto = AdminDAO.getAdminAuth(user.getEmail(), oldpassword);
        	if(userDto != null) {
        		userDto.setPassword(CommonUtil.hashPassword(password));
            	UserDAO.updateUserBasic(userDto);
        	} else {
        		renderText("Error: Please enter correct current password.");
        	}
  			renderText("Password updated!");
        }
	}
	
	public static void updateProfile() {
		renderText("Profile updated");
	}
}