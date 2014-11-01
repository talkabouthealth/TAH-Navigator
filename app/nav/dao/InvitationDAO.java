package nav.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.InvitedDTO;
import util.JPAUtil;

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
}
