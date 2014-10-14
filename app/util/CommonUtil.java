package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import nav.dao.UserDAO;
import nav.dto.UserBean;
import play.Logger;
import play.cache.Cache;
import play.mvc.Http;
import play.mvc.Router;
import play.mvc.Router.ActionDefinition;
import play.mvc.Scope.Session;

/**
 * Different utility methods used through application
 *
 */
public class CommonUtil {
	
	//pattern for locating links in the text
	public static final String WEB_URL_PATTERN = " ((https?|ftp)://[a-zA-Z0-9+\\-&@#/%?=~_|!:,.;]*[a-zA-Z0-9+&@#/%=~_|])";
	
	private static final MessageDigest MD5_MESSAGE_DIGEST;
	static {
		try {
			MD5_MESSAGE_DIGEST = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsae) {
			throw new IllegalStateException(nsae);
		}
	}

	public static String hashPassword(String password) {
		byte[] md5hash = null;
		synchronized (MD5_MESSAGE_DIGEST) {
			try {
				md5hash = MD5_MESSAGE_DIGEST.digest(password.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				Logger.error(e, "CommonUtil.java : hashPassword");
			}
			MD5_MESSAGE_DIGEST.reset();
		}
		if (md5hash == null) {
			return null;
		}

		//convert to string
		BigInteger bigInt = new BigInteger(1, md5hash);
		String hashText = bigInt.toString(16);
		//integer can remove leading zeroes - add them back
		while (hashText.length() < 32) {
			hashText = "0"+hashText;
		}
//		Logger.info("hashPassword : " + 32);
		return hashText;
	}

	/**
	 * Get cached logged-in talker instance or load a new one
	 * @param session
	 * @return
	 */
	public static UserBean loadCachedUser(Session session) {
		UserBean user = Cache.get(session.getId() + "-user", UserBean.class);
	    if (user == null) {
	        //nothing in cache - load a new one
	    	user = refreshCachedUser(session);
	    }
	    return user;
	}
	/**
	 * Loads logged-in talker instance and updates cache
	 * @param session
	 * @return
	 */
	public static UserBean refreshCachedUser(Session session) {
		String sessionUserName = session.get("username");
		if (sessionUserName == null) {
			return null;
		}
		UserBean talker = UserDAO.getByUserEmail(sessionUserName);
		if (talker != null) {
			Cache.set(session.getId() + "-user", talker, "60mn");
		}
        return talker;
	}
	
	/**
	 * Generates unique verification code for emails
	 * @return
	 */
	public static String generateVerifyCode() {
		String verifyCode = null;
		boolean unique = true;
		do {
			verifyCode = UUID.randomUUID().toString();
		} while (!unique);
		return verifyCode;
	}

	/**
	 * Parse given value as comma separated list of strings
	 * @param otherItems
	 * @param defaultValue Default value used in text input, doesn't include it in return list
	 * @return
	 */
	public static List<String> parseCommaSerapatedList(String otherItems, String defaultValue) {
		if (otherItems == null) {
			return null;
		}

		String[] otherArray = otherItems.split(",");
		//validate and add
		List<String> itemsList = new ArrayList<String>();
		for (String otherItem : otherArray) {
			otherItem = otherItem.trim();
			if (otherItem.length() != 0 && !otherItem.equalsIgnoreCase(defaultValue)) {
				itemsList.add(otherItem);
			}
		}
		return itemsList;
	}
	
	public static String generateRandomPassword() {
		SecureRandom random = new SecureRandom();
	    String newPassword = new BigInteger(60, random).toString(32);
	    return newPassword;
	}

	/**
	 * Generate absolute url to some application page
	 * @param action Play action in format "Controller.method", e.g. "ViewDispatcher.view"
	 * @param paramName 
	 * @param paramValue
	 * @return
	 */
	public static String generateAbsoluteURL(String action, String paramName, Object paramValue) {
		//we can't generate url without request (e.g. calling this method from some Job)
		if (Http.Request.current() == null) {
			if (action.equals("ViewDispatcher.view")) {
				return "http://talkabouthealth.com/"+paramValue;
			}
			else if (action.equals("Talk.talkApp")) {
				return "http://talkabouthealth.com/chat/"+paramValue;
			}
			else {
				return null;
			}
		}

		//prepare parameters if they exist
		Map<String, Object> args = new HashMap<String, Object>(1);
		if (paramName != null) {
			args.put(paramName, paramValue);
		}
		
		//Generate absolute url from given params
		ActionDefinition actionDef = Router.reverse(action, args);
		actionDef.absolute();
		return actionDef.url;
	}

	public static String generateAbsoluteURL(String action) {
		return generateAbsoluteURL(action, null, null);
	}
	
	/**
	 * Create BufferedReader for importer classes
	 * @param fileName
	 * @return
	 */
	public static BufferedReader createImportReader(String fileName) {
		InputStream is = CommonUtil.class.getResourceAsStream("/util/importers/data/"+fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		return br;
	}

	/**
	 * Replaces plain text links with html links.
	 * 
	 * @param text
	 * @return
	 */
	public static String linkify(String text) {
		if (text == null) {
			return null;
		}
		String replacedText = text.replaceAll(WEB_URL_PATTERN, "<a href=\"$1\" target=\"_blank\">$1</a>");
		return replacedText;
	}

	public static String prepareTwitterThought(String htmlText) {
		htmlText = CommonUtil.linkify(htmlText);
		
		String searchRegex = "(\\s|\\A)#(\\w+)";
		String userRegex = "(\\s|\\A)@(\\w+)";
		
		htmlText = htmlText.replaceAll(searchRegex, "$1<a href=\"http://twitter.com/search?q=%23$2\" target=\"_blank\">#$2</a>");
		htmlText = htmlText.replaceAll(userRegex, "$1<a href=\"http://twitter.com/$2\" target=\"_blank\">@$2</a>");
		
		return htmlText;
	}
	
	public static String messageToHTML(String text) {
		if (text == null) {
			return "";
		}
		//delinkify because previously we store links in db
		text = text.replaceAll("<a[^>]*>", "");
		text = text.replaceAll("</a>", "");
		String htmlText = text;
		htmlText = htmlText.replace("\n", "<br/>");
		
		return htmlText;
	}
	
	public static Character getHormoneStatus(String str) {
		Character c;
		switch (str) {
		case "yes":
			c = new Character('+');
			break;
		case "no":
			c = new Character('-');
			break;
		default:
			c = null;
			break;
		}
		return c;
	}
	public static String getHormoneText(Character c) {
		String str = null;
		if (c != null) {
		switch (c) {
			case '+':
				str = "yes";
				break;
			case '-':
				str = "no";
				break;
			default:
				break;
			}
		}
		return str;
	}
}