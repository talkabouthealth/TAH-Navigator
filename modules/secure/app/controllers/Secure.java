package controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

import controllers.Patient;
import models.UserDTO;
import nav.dto.UserBean;
import play.Play;
import play.mvc.*;
import play.data.validation.*;
import play.libs.*;
import play.utils.*;
import util.CommonUtil;

public class Secure extends Controller {

    @Before(unless={"login", "authenticate", "logout"})
    static void checkAccess() throws Throwable {    	
        // Authent
    	session.put("requestPath", request.path);
        if(!session.contains("username")) {
            flash.put("url", "GET".equals(request.method) ? request.url : Play.ctxPath + "/"); // seems a good default
            login();
        } 
        // Checks
        Check check = getActionAnnotation(Check.class);
        if(check != null) {
            check(check);
        }
        check = getControllerInheritedAnnotation(Check.class);
        if(check != null) {
            check(check);
        }
    }
    
    private static void check(Check check) throws Throwable {
    	boolean hasProfile = (Boolean)Security.invoke("check", check.value()[0],check.value()[1]);
    	 if(!hasProfile) {
             Security.invoke("onCheckFailed", check.value()[0]);
         }
    }

    // ~~~ Login
    public static void login() throws Throwable {
        Http.Cookie remember = request.cookies.get("rememberme");
        if(remember != null) {
            int firstIndex = remember.value.indexOf("-");
            int lastIndex = remember.value.lastIndexOf("-");
            if (lastIndex > firstIndex) {
            	String [] tokesn = remember.value.split("-");
                String sign = tokesn[0];
                String restOfCookie = remember.value.substring(firstIndex + 1);
                String username = tokesn[1];
                char userType = tokesn[2].charAt(0);
                String time = tokesn[3];
                Date expirationDate = new Date(Long.parseLong(time));
                Date now = new Date();
                if (expirationDate == null || expirationDate.before(now)) {
                    logout();
                }
                if(Crypto.sign(restOfCookie).equals(sign)) {
                	 session.put("username", username);
                    redirectToOriginalURL(userType);
                }
            }
        }        
        flash.keep("url");
        render();
    }

    public static void authenticate(@Required String email, String password, boolean remember) throws Throwable {
        // Check tokens
    	UserDTO allowed = null;
        UserBean user = null;
        try {
        	allowed = (UserDTO)Security.invoke("authenticate", email, password);
        	if(allowed != null) {
        		if(!allowed.isActive() && allowed.getUserType() == 'p') {
        			System.out.println("User is not verified");
        			validation.addError("email", "secure.verify", "");
        		} else {
        			System.out.println("User verified");
        		}
        	} else {
        		System.out.println("There is no user");
        		validation.addError("email", "secure.error", "");
        	}
        } catch (UnsupportedOperationException e ) {
        	e.printStackTrace();
            // This is the official method name
        }
        if(validation.hasErrors()) {
            validation.keep();
            flash.keep("url");
            params.flash();
            session.remove("username");
            login();
        } else {
        	  session.put("username", email);
        	  user = CommonUtil.loadCachedUser(session);
        }
        // Mark user as connected
        // Remember if needed
        if(remember) {
        	String duration = Play.configuration.getProperty("secure.rememberme.duration","30d");
            Calendar cal = Calendar.getInstance();
        	cal.add(Calendar.DAY_OF_YEAR, 30);
            response.setCookie("rememberme", Crypto.sign(email +"-" + user.getUserType() + "-" + cal.getTime().getTime()) + "-" + email +"-" + user.getUserType() + "-" + cal.getTime().getTime(), duration);
        }
        // Redirect to the original URL (or /)
        redirectToOriginalURL(user.getUserType());
    }

    public static void logout() throws Throwable {
        Security.invoke("onDisconnect");
        session.clear();
        response.removeCookie("rememberme");
        Security.invoke("onDisconnected");
        flash.success("secure.logout");
        login();
    }

    static void redirectToOriginalURL(char ut) throws Throwable {
        Security.invoke("onAuthenticated");
        String url = flash.get("url");
        if(url == null) {
            url = Play.ctxPath + "/";
        }
        if('a' == ut) {
        	session.put("usertype", "admin");
        	Admin.index();
        } else if('c' == ut) {
        	 session.put("usertype", "care");
        	Care.index();
        } else if('p' == ut) {
       	 	session.put("usertype", "user");
       	 	String requestPath = session.get("requestPath");
       	 	if (requestPath != null && requestPath.equalsIgnoreCase("/patient/appointment")) {
       	 		session.remove("requestPath");
       	 		Patient.appointment();
       	 	}
       	 	else {
       	 		Patient.index();
       	 	}
       }
    }

    static void redirectToOriginalURL() throws Throwable {
        Security.invoke("onAuthenticated");
        String url = flash.get("url");
        if(url == null) {
            url = Play.ctxPath + "/";
        }
        Admin.index();
    }

    public static class Security extends Controller {

        /**
         * @Deprecated
         * 
         * @param username
         * @param password
         * @return
         */
        static boolean authentify(String username, String password) {
            throw new UnsupportedOperationException();
        }

        /**
         * This method is called during the authentication process. This is where you check if
         * the user is allowed to log in into the system. This is the actual authentication process
         * against a third party system (most of the time a DB).
         *
         * @param username
         * @param password
         * @return true if the authentication process succeeded
         */
        static UserDTO authenticate(String username, String password) {
            return null;
        }

        /**
         * This method checks that a profile is allowed to view this page/method. This method is called prior
         * to the method's controller annotated with the @Check method. 
         *
         * @param profile
         * @return true if you are allowed to execute this controller method.
         */
        static boolean check(String profile) {
            return true;
        }

        /**
         * This method returns the current connected username
         * @return
         */
        static String connected() {
            return session.get("username");
        }

        /**
         * Indicate if a user is currently connected
         * @return  true if the user is connected
         */
        static boolean isConnected() {
            Http.Cookie remember = request.cookies.get("rememberme");
            if(remember != null) {
                int firstIndex = remember.value.indexOf("-");
                int lastIndex = remember.value.lastIndexOf("-");
                if (lastIndex > firstIndex) {
                	String [] tokesn = remember.value.split("-");
                    String sign = tokesn[0];
                    String restOfCookie = remember.value.substring(firstIndex + 1);
                    String username = tokesn[1];
                    String ut = tokesn[2];
                    String time = tokesn[3];
                    Date expirationDate = new Date(Long.parseLong(time));
                    Date now = new Date();
                    if (expirationDate == null || expirationDate.before(now)) {
                    	try {
							logout();
						} catch (Throwable e) {
							e.printStackTrace();
						}
                    }
                    if(Crypto.sign(restOfCookie).equals(sign)) {
                    	 if("a".equals(ut)) {
                         	session.put("usertype", "admin");
                         } else if("c".equals(ut)) {
                         	 session.put("usertype", "care");
                         } else if("p".equals(ut)) {
                        	 	session.put("usertype", "user");
                        }
                    	 session.put("username", username);
                    }
                }
            }
            return session.contains("username");
        }

        /**
         * This method is called after a successful authentication.
         * You need to override this method if you with to perform specific actions (eg. Record the time the user signed in)
         */
        static void onAuthenticated() {
        }

         /**
         * This method is called before a user tries to sign off.
         * You need to override this method if you wish to perform specific actions (eg. Record the name of the user who signed off)
         */
        static void onDisconnect() {
        }

         /**
         * This method is called after a successful sign off.
         * You need to override this method if you wish to perform specific actions (eg. Record the time the user signed off)
         */
        static void onDisconnected() {
        }

        /**
         * This method is called if a check does not succeed. By default it shows the not allowed page (the controller forbidden method).
         * @param profile
         */
        static void onCheckFailed(String profile) {
            forbidden();
        }

        private static Object invoke(String m, Object... args) throws Throwable {
            try {
                return Java.invokeChildOrStatic(Security.class, m, args);       
            } catch(InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
    }
}