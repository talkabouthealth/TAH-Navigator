package nav.dao;

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
}
