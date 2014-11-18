package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import nav.dao.DistressDAO;
import nav.dao.PatientAlert;
import nav.dao.UserDAO;
import nav.dto.DistressBean;
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

@With( { Secure.class } )
public class Distress  extends Controller {

	public static void save(String curDist,String [] distressType,String otherDetail,String daterecrded, Integer updateBy, Integer patientId, Integer distressId) {

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
		UserBean user = null;
		UserDetailsDTO userDto = null;
		int distInt = Integer.parseInt(curDist);
		PatientDistressDTO dto = null;
		if (updateBy != null) {
			UserDTO uDto = UserDAO.getUserBasicByField("id", patientId);
			dto = DistressDAO.updateDistressByCareTeam(distInt, uDto, updateBy, dtCreated, otherDetail);
		}
		else {
			user = CommonUtil.loadCachedUser(session);
			userDto = UserDAO.getDetailsById(user.getId());
			if (distressId != null) {				
				dto = DistressDAO.updatePatientDistress(distInt,userDto.getUser(),otherDetail,dtCreated, distressId.intValue());
			}
			else {
				dto = DistressDAO.savePatientDistress(distInt,userDto.getUser(),otherDetail,dtCreated);
			}
		}
		if(distressType != null) {
			for (String string : distressType) {
				System.out.println("string : " + string);
				int distresstypeid = Integer.parseInt(string);
				boolean distressvalue = true;
				DistressDAO.savePatientDistressDetails(distresstypeid,distressvalue,dto);
			}
		}
		JsonObject obj = new JsonObject();
		
		obj.add("distressId", new JsonPrimitive(String.valueOf(dto.getId())));
		obj.add("status", new JsonPrimitive("200"));
		renderJSON(obj);
	}
	public static void lastDistressIn24Hours(Integer patientId) {
		Map<String, Object> jsonData = new HashMap<String, Object>();
		UserDTO user = UserDAO.getUserBasicByField("id", patientId);
		DistressBean obj = DistressDAO.getLastDistress(user);
		boolean flag = false;
		if (obj != null) {
			Date now = new Date();
			long diff = now.getTime() -obj.getDistressDate().getTime();
			if (diff <= 86400000L) {
				flag = true;
			}
		}
		if (flag) {
			jsonData.put("lastDistress", obj);
		}
		renderJSON(jsonData);
	}
}