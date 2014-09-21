package controllers;

import java.text.SimpleDateFormat;

import org.joda.time.Period;

import models.LoginHistoryDTO;
import models.UserDTO;
import nav.dao.AdminDAO;
import nav.dao.LoginHistoryDAO;
import util.CommonUtil;
import util.DateTimeUtil;

/**
 * Handles authentication  
 *
 */
public class Security extends Secure.Security {
	
    static UserDTO authenticate(String usernameOrEmail, String password) {
    		return AdminDAO.getAdminAuth(usernameOrEmail, CommonUtil.hashPassword(password));
    }

    static UserDTO authenticate(String usernameOrEmail, String password, boolean isHash) {
    	if(!isHash)
    		return authenticate(usernameOrEmail, CommonUtil.hashPassword(password));
    	else
    		return authenticate(usernameOrEmail, password);
    }

    /**
     * Check if authenticated user has given profile (i.e. role)
     */
    static boolean check(String profile) {
        if("admin".equals(profile)) {
            return isConnected();
        }
        return false;
    }

    /**
     * Check if authenticated user has given profile (i.e. role)
     */
    static boolean check(String profile,String user) {
    	String currentUserType = session.get("usertype")==null?"":session.get("usertype");
        if("admin".equals(profile) && currentUserType.equals(user)) {
            return isConnected();//.equals("admin");
        } else if("care".equals(profile) && currentUserType.equals(user)) {
        	return isConnected();
        } else if("user".equals(profile) && currentUserType.equals(user)) {
        	return isConnected();
        } else {
        	return false;
        }
    }

    /**
     * After successful authentication
     */
    static void onAuthenticated() {
 
	    String url = params.get("url");
	    if (url != null && url.trim().length() != 0) {
	    	flash.put("url", url);
	    }
	    System.out.println("This user has beed auto perfect.....");
	    String sessionUser = session.get("username");

	    //saveLogin(String loginFrom,boolean rememberChecked,String sessionid)
	    LoginHistoryDTO historyDTO = LoginHistoryDAO.getLastLoginByUserId(sessionUser);
	    SimpleDateFormat smf = new SimpleDateFormat("MM/dd/yyyy H:mm");
	    //2008-07-17T09:24:17Z
	    String lastLoginTime = smf.format(historyDTO.getLogintime());
	    String isFirstTime =  LoginHistoryDAO.getIsFirstTime(sessionUser);
	    
	    session.put("showdistress", isFirstTime);
	    session.put("lastLoginTime", lastLoginTime);
	    LoginHistoryDAO.saveLogin(sessionUser, "login",false,session.getId());
	    
	    url = flash.get("url");
        if(url != null && url.trim().length() == 0) {
        	flash.put("url", "/home");
        }
    }

    static void onDisconnected() {
        try {
			Application.index();
		} catch (Throwable e) {
			e.printStackTrace();
		}
    }
}