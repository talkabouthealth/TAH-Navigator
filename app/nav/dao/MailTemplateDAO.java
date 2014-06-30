package nav.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.MailTemplateDTO;
import models.UserDTO;
import nav.dto.UserBean;
import util.JPAUtil;

public class MailTemplateDAO {

	public static MailTemplateDTO getByTemplateName(String templateName) {
		return getByField("template", templateName);
	}
	
	public static MailTemplateDTO getByField(String fieldName, Object value) {
		MailTemplateDTO dto = null;
		EntityManager em = JPAUtil.getEntityManager();
		try {
			TypedQuery<MailTemplateDTO> query = em.createQuery("SELECT c FROM MailTemplateDTO c WHERE c."+fieldName+" = :field", MailTemplateDTO.class); 
			query.setParameter("field", value);
			dto = query.getSingleResult();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			em.close();
		}
		return dto;
	}
}