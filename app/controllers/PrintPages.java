package controllers;

import java.util.Map;

import nav.dao.CancerDAO;
import nav.dao.PatientDetailDAO;
import nav.dao.UserDAO;
import models.BreastCancerInfoDTO;
import models.PatientDetailDTO;
import models.UserDTO;
import models.UserDetailsDTO;
import play.mvc.Controller;
import play.mvc.With;

@With({Secure.class})
public class PrintPages extends Controller {
	public static void patient(Integer patientId, String[] filter) {
		UserDTO user = UserDAO.getUserBasicByField("id", patientId);
		UserDetailsDTO userDetails = UserDAO.getDetailsByField("id", patientId);
		PatientDetailDTO patientDetails = PatientDetailDAO.getDetailsByField("id", patientId);
		Map<String, String> cancerInfo = CancerDAO.cancerInfo(patientId);
		System.out.println("PatientId: " + patientId.toString());
		System.out.println("Name: " + userDetails.getFirstName() + " " + userDetails.getLastName());
		
		for(String str: filter) {
			renderArgs.put(str, true);
		}	
		
		if (cancerInfo != null) {
			System.out.println(cancerInfo.get("diseaseName"));
		}
		renderArgs.put("user", user);
		renderArgs.put("userDetails", userDetails);
		renderArgs.put("patientDetails", patientDetails);
		renderArgs.put("cancerInfo", cancerInfo);
		render();
	}
	
	public static void care() {
		render();
	}
}
