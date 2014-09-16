package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import nav.dao.DistressDAO;
import nav.dao.UserDAO;
import nav.dto.UserBean;
import models.PatientDistressDTO;
import models.UserDTO;
import models.UserDetailsDTO;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import play.mvc.Controller;
import play.mvc.With;
import util.CommonUtil;

@Check({"user","user"})
@With( { Secure.class } )
public class Distress  extends Controller {

	public static void save(String curDist,String [] distressType,String otherDetail,String daterecrded) {

//		System.out.println("dist : " + curDist);
//		System.out.println("otherDetail : " + otherDetail);
//		System.out.println("daterecrded : " + daterecrded );
		Date dtCreated = new Date();
		try {
			dtCreated = new SimpleDateFormat("M/d/yyyy h:m a").parse(daterecrded);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(StringUtils.isBlank(otherDetail)) {
			otherDetail = "";
		}
		UserBean user = CommonUtil.loadCachedUser(session);
		UserDetailsDTO userDto = UserDAO.getDetailsById(user.getId());
		int distInt = Integer.parseInt(curDist);
		PatientDistressDTO dto = DistressDAO.savePatientDistress(distInt,userDto.getUser(),otherDetail,dtCreated);
		if(distressType != null) {
			for (String string : distressType) {
				System.out.println("string : " + string);
				int distresstypeid = Integer.parseInt(string);
				boolean distressvalue = true;
				DistressDAO.savePatientDistressDetails(distresstypeid,distressvalue,dto);
			}
		}
		JsonObject obj = new JsonObject();
		obj.add("status", new JsonPrimitive("200"));
		renderJSON(obj);
	}
}