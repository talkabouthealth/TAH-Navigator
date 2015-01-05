package nav.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.AddressDTO;
import models.InvitedDTO;
import models.UserDTO;
import util.EmailUtil;
import util.JPAUtil;
import util.TemplateExtensions;

public class InvitationDAO {

	public static InvitedDTO getDetailsByEmail(String fieldName, Object value) {
		InvitedDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<InvitedDTO> query = em.createQuery("SELECT c FROM InvitedDTO c WHERE c."+fieldName+" = :field and c.activateOnSignup=false", InvitedDTO.class); // 
			query.setParameter("field", value);
			dto = query.getSingleResult();
		} catch(Exception e) {
//			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static InvitedDTO getDetailsByField(String fieldName, Object value) {
		InvitedDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<InvitedDTO> query = em.createQuery("SELECT c FROM InvitedDTO c WHERE c."+fieldName+" = :field", InvitedDTO.class); //and c.activateOnSignup=false  
			query.setParameter("field", value);
			dto = query.getSingleResult();
		} catch(Exception e) {
		} finally {
			em.close();
		}
		return dto;
	}
	
	
	public static List<InvitedDTO> getReminders() {
		List<InvitedDTO> dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			/*
			select email, addedon, age(addedon ) as agea from nav.invited 
			where  ((age(addedon ) >= '2 days' and age(addedon ) <  '3 days') or ( age(addedon ) >= '7 days' and age(addedon ) <  '8 days')) and activateonsignup = false

			select email, addedon, age(addedon ) as agea from nav.invited where activateonsignup = false
			select * from nav.invited where activateonsignup = false
			 */
			TypedQuery<InvitedDTO> query = em.createQuery("SELECT c FROM InvitedDTO c where c.activateOnSignup=false and ((age(c.addedon)>= '2 days' and  age(c.addedon) <  '3 days') or (age(c.addedon)>= '7 days' and  age(c.addedon) <  '8 days'))", InvitedDTO.class);   // 
			dto = query.getResultList();
		} catch(Exception e) {
		} finally {
			em.close();
		}
		return dto;
	}
	
	public static Map<String, Object> mailVariables(String templateName, InvitedDTO dto) {
		Map<String, Object> vars = new HashMap<String, Object>();
		String username = dto.getFirstname() + " " + dto.getLastname();
		String signupurl = "http://tvrhnavigator.com/invited-registration/" + dto.getId();
		String clinicPhone = "";
	 	AddressDTO address = dto.getAddressid();
	 	if (address != null && address.getPhone() != null) {
	 		clinicPhone = address.getPhone();
	 	}	 			
		vars.put("username", username);
		vars.put("signupurl", signupurl);
		vars.put("clinic_phone", clinicPhone);
		if (templateName.compareToIgnoreCase(EmailUtil.TVRH_INVITE_APPOINTMENT_SCHEDULED) == 0 || templateName.compareToIgnoreCase(EmailUtil.TVRH_INVITE_REMINDER_APPOINTMENT_SCHEDULED) == 0) {
			UserDTO doctor = dto.getCaremember();
			String doctorName;			
			if (doctor != null) {
				doctorName = TemplateExtensions.usreNameNew(dto.getCareMemberName(), doctor.getId()).toString();
			}
			else {
				doctorName = dto.getCareMemberName();
			}			
			String date = new SimpleDateFormat("MM/dd/yyyy").format(dto.getAppointmentdate());
			StringBuilder clinicAddress = new StringBuilder();
			if (address != null) {				
				clinicAddress.append(address.getLine1());
				if (address.getLine2() != null && !address.getLine2().isEmpty()) {
					clinicAddress.append(", " + address.getLine2());
				}
				if (address.getCity() != null && !address.getCity().isEmpty()) {
					clinicAddress.append(", " + address.getCity());
				}
				if (address.getState() != null && !address.getState().isEmpty()) {
					clinicAddress.append(", " + address.getState());
				}
			}
			
			vars.put("doctor_name", doctorName);
			vars.put("date", date);			
			vars.put("appointment_time", dto.getAppointmenttime());			
			vars.put("clinic_address", clinicAddress.toString());			
		}
		else if (templateName.compareToIgnoreCase(EmailUtil.TVRH_INVITE_NO_APPOINTMENT_SCHEDULED) == 0 || templateName.compareToIgnoreCase(EmailUtil.TVRH_INVITE_REMINDER_NO_APPOINTMENT_SCHEDULED) == 0) {
			// empty
		}
		return vars;
	}
}
