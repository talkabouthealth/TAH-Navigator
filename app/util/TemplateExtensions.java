package util;

import models.ExpertDetailDTO;
import models.UserDetailsDTO;
import nav.dao.ExpertDetailDAO;
import nav.dao.UserDAO;

import org.apache.commons.lang.StringUtils;

import play.templates.JavaExtensions;

/**
 * Extensions that can be used in Play! templates.
 * Must extend "JavaExtensions" class, so Play! could find it on start-up. 
 *
 */
public class TemplateExtensions extends JavaExtensions {

	/**
	 * Converts list of strings to comma-separated string.
	 * Used for "other" fields in the forms.
	 * 
	 */
	public static String usreName(String userId,Integer id) {
		System.out.println(userId + " : " + id);
		String name="";
		UserDetailsDTO details = UserDAO.getDetailsById(id);
		if(StringUtils.isBlank(details.getFirstName()) && StringUtils.isBlank(details.getLastName())) {
			name = details.getUser().getName();
		} else {
			if(StringUtils.isBlank(details.getFirstName()) && !StringUtils.isBlank(details.getLastName()))
				name = details.getLastName();
			else if(!StringUtils.isBlank(details.getFirstName()) && StringUtils.isBlank(details.getLastName()))
				name = details.getFirstName();
			else {
				name = details.getFirstName() + " " + details.getLastName();
			}
		}
		if(details.getUser().getUserType() == 'c') {
			ExpertDetailDTO exp = ExpertDetailDAO.getDetailsByField("id", id);// ExpertByField("id", id);
			if(exp != null) {
				if(exp.getDesignation() != null)
				name = exp.getDesignation().getAbbr() + ". " + name;//details.getFirstName() + " " + details.getLastName();
			}
		}
		return name;
	}
	
	public static Object printMessage(String msg) {
		String htmlText = msg;
		if (htmlText == null) {
			return "";
		}
		htmlText = htmlText.replace("\n", "<br/>");
		htmlText = htmlText.replace("&lt;br/&gt;", "<br/>");
		System.out.println(htmlText);
		return JavaExtensions.raw(htmlText);
	}
}
