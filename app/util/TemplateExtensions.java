package util;

import models.AddressDTO;
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
//			ExpertDetailDTO exp = ExpertDetailDAO.getDetailsByField("id", id);// ExpertByField("id", id);
//			if(exp != null) {
//				if(exp.getDesignation() != null)
//				name = exp.getDesignation().getAbbr() + ". " + name;//details.getFirstName() + " " + details.getLastName();
//			}
			name = details.getFirstName() ;
		}
		if(StringUtils.isNotBlank(name))
			return name;
		else
			return userId;
	}
	
	public static Object usreNameNew(String userId,Integer id) {
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
			name = details.getFirstName() ;
		}
		if(StringUtils.isBlank(name)) {
			name = userId;
		}
		return JavaExtensions.raw(name);
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
	
	public static Object printAddress(AddressDTO address) {
		String htmlText = "";
		boolean isBefore = false;
		
		if(StringUtils.isNotEmpty(address.getLine1())) {
			htmlText = "<span>"+ address.getLine1() +"</span>";
			isBefore = true;
		}
		if(StringUtils.isNotEmpty(address.getLine1())) {
			if(isBefore)
				htmlText += ", ";
			htmlText += address.getLine2();
			isBefore =true;
		}else
			isBefore=false;
		
		if(StringUtils.isNotEmpty(address.getCity())) {
			if(isBefore)
				htmlText += ", ";
			htmlText += address.getCity();
			isBefore =true;
		}else
			isBefore=false;
		
		if(StringUtils.isNotEmpty(address.getState())) {
			if(isBefore)
				htmlText += ", ";
			htmlText += address.getState();
//			isBefore =true;
		}
//		else
//			isBefore=false;
		if(StringUtils.isNotEmpty(address.getCountry())) {
			htmlText += " " + address.getCountry();
		}
		if(StringUtils.isNotEmpty(address.getZip())) {
			htmlText += " " + address.getZip();
		}
		
		return JavaExtensions.raw(htmlText);
	}
}
