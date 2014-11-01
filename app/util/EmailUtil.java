package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
 
import com.sailthru.client.SailthruClient;
import com.sailthru.client.exceptions.ApiException;
import com.sailthru.client.handler.response.JsonResponse;
import com.sailthru.client.params.Send;

 

/**
 * Uses Sailthru API, http://sailthru.com/
 */
public class EmailUtil {
	private static final String SAILTHRU_APIKEY = "4007bc4d4b48586353eb44012172eaf3";
	private static final String SAILTHRU_SECRET = "4ba0a437f0f138fceba76dac5c33e567";

	/**
	 * Names are the same (only uppercase) as templates' names on http://sailthru.com/
	 */

	public static final String MOFFITT_WELCOME = "Moffitt-Welcome";
	public static final String MOFFITT_THANKYOU_FOR_SIGNUP = "Moffitt-Thankyou-For-Signing-Up";
	public static final String MOFFITT_PASSWORDRECOVERY = "Moffitt-PasswordRecovery";
	public static final String MOFFITT_WELCOMEREMINDER = "Moffitt-Welcome-Reminder";
	
	public static boolean sendEmail(String template,Map<String, Object> vars,String to ){
        SailthruClient client = new SailthruClient(SAILTHRU_APIKEY, SAILTHRU_SECRET);
        boolean isEmailSent = false;
        try {
            Send send = new Send();
            send.setTemplate(template);
            send.setEmail(to);
            send.setVars(vars);
            JsonResponse response = client.send(send);
            if (response.isOK()) {
                System.out.println(response.getResponse());
                isEmailSent = true;
            } else {
                System.out.println(response.getResponse().get("error").toString());
            }
        } catch (ApiException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return isEmailSent;
	}
}