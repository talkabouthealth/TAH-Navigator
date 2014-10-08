package nav.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.ApplicationSettingsDTO;
import util.JPAUtil;

public class ApplicationSettingDAO {

	public static ApplicationSettingsDTO getDetailsByField(String fieldName, Object value) {
		ApplicationSettingsDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<ApplicationSettingsDTO> query = em.createQuery("SELECT c FROM ApplicationSettingsDTO c WHERE c."+fieldName+" = :field", ApplicationSettingsDTO.class); 
			query.setParameter("field", value);
			dto = query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return dto;
	}
}